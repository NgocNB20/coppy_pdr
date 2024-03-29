/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.logic.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoShop;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.CommonInfoBaseImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.CommonInfoImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.common.AbstractCommonProcessLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.common.CommonInfoShopCreateLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.common.CommonProcessFrontPcLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.common.EffectiveCampaignCodeGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.AccessDeviceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 共通処理：フロントPC<br/>
 *
 * @author natume
 * @author sakai
 */
@Component
public class CommonProcessFrontPcLogicImpl extends AbstractCommonProcessLogic implements CommonProcessFrontPcLogic {

    /**
     * 有効なキャンペーンコードのセッション登録用キーのprefix
     */
    protected static final String SESSION_KEY_CAMPAIGN = "CAMPAIGN";

    @Autowired
    public CommonProcessFrontPcLogicImpl(CommonInfoUtility commonInfoUtility,
                                         ApplicationLogUtility applicationLogUtility,
                                         AccessDeviceUtility accessDeviceUtility) {
        super(commonInfoUtility, applicationLogUtility, accessDeviceUtility);
    }

    /**
     * 共通処理<br/>
     *
     * @param request  リクエスト
     * @param response レスポンス
     * @return 処理続行フラグ true=処理中止, false=処理続行
     */
    @Override
    public CommonInfo execute(HttpServletRequest request, HttpServletResponse response) {

        // 各種共通情報の作成
        CommonInfo commonInfo = createCommonInfo(request);

        /* AccessUidを生成＆Cookieにセット */
        super.setAccessUid(request, response, commonInfo);

        // キャンペーンコードをcookieにセット
        super.setCookieCampaignCode(request, response);

        /* 認証チェック */
        // SpringSecurityで実施するため不要

        return commonInfo;
    }

    /**
     * 共通情報の作成<br/>
     * <ul>
     *  <li>基本情報の作成</li>
     *  <li>ショップ情報の作成</li>
     *  <li>ユーザ情報の作成</li>
     * </ul>
     *
     * @param request リクエスト
     * @return 共通情報
     */
    protected CommonInfo createCommonInfo(HttpServletRequest request) {

        // 共通情報の取得・作成
        CommonInfoImpl commonInfo = new CommonInfoImpl();

        /* 基本情報の作成・セット */
        createCommonInfoBase(request, commonInfo);

        /* ショップ情報の作成 */
        createCommonInfoShop(commonInfo);

        /* ユーザー情報の作成 */
        // Spring Security管理とするため、ここでは何も行わない

        return commonInfo;
    }

    /**
     * ショップ情報（共通情報）の作成<br/>
     * <pre>
     * 毎回取得するように変更。（Ehcacheでキャッシュするようにはしている。）
     * 非公開の場合もショップ情報を作成するように変更。
     * 非公開の場合の処理は呼び出し元で実施。
     * </pre>
     *
     * @param commonInfo 共通情報
     */
    protected void createCommonInfoShop(CommonInfoImpl commonInfo) {

        // DBショップ情報取得
        final CommonInfoBase commonInfoBase = commonInfo.getCommonInfoBase();
        CommonInfoShopCreateLogic commonInfoShopCreateLogic =
                        ApplicationContextUtility.getBean(CommonInfoShopCreateLogic.class);
        CommonInfoShop commonInfoShop = commonInfoShopCreateLogic.execute(commonInfoBase.getShopSeq());

        commonInfo.setCommonInfoShop(commonInfoShop);
    }

    /**
     * 共通基本情報の作成<br/>
     * <ul>
     * <li>共通基本情報の各情報をセット</li>
     * <li>共通情報にセット</li>
     * </ul>
     *
     * @param request        リクエスト
     * @param commonInfoImpl 共通情報
     */
    protected void createCommonInfoBase(HttpServletRequest request, CommonInfoImpl commonInfoImpl) {

        // 基本情報作成
        CommonInfoBaseImpl commonBaseInfoImpl = new CommonInfoBaseImpl();

        // ショップSEQ
        commonBaseInfoImpl.setShopSeq(super.getShopSeq());

        // URL
        String url = request.getRequestURL().toString();
        commonBaseInfoImpl.setUrl(url);

        // サイト区分
        commonBaseInfoImpl.setSiteType(HTypeSiteType.getSiteType(url));

        // User-Agent
        commonBaseInfoImpl.setUserAgent(super.getAccessDeviceUtility().getUserAgent(request));

        // デバイス種別
        commonBaseInfoImpl.setDeviceType(super.getAccessDeviceUtility().getDeviceType(request));

        // セッションID
        commonBaseInfoImpl.setSessionId(request.getSession().getId());

        // 画面ID
        commonBaseInfoImpl.setPageId(request.getServletPath());

        // 端末IDセット
        commonBaseInfoImpl.setAccessUid(super.getAccessUid(request));

        // キャンペーンコードセット
        commonBaseInfoImpl.setCampaignCode(this.getEffectiveCampaignCode(commonBaseInfoImpl.getShopSeq(),
                                                                         commonBaseInfoImpl.getAccessUid(), request
                                                                        ));

        // 共通情報にセット
        commonInfoImpl.setCommonInfoBase(commonBaseInfoImpl);
    }

    /**
     * 有効なキャンペーンコードの取得<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別番号
     * @param request   リクエスト
     * @return 有効なキャンペーンコード（ない場合は、空文字）
     */
    protected String getEffectiveCampaignCode(Integer shopSeq, String accessUid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // Sessionキーを生成
        String key = this.createCampaignCodeSessionKey(shopSeq);

        // キャンペーンコードの取得
        Object campaignCode = session.getAttribute(key);
        if (campaignCode == null) {
            // DBモード
            EffectiveCampaignCodeGetLogic effectiveCampaignCodeGetLogic =
                            ApplicationContextUtility.getBean(EffectiveCampaignCodeGetLogic.class);
            campaignCode = effectiveCampaignCodeGetLogic.execute(shopSeq, accessUid);

            session.setAttribute(key, campaignCode);
        }
        return (String) campaignCode;
    }

    /**
     * キャンペーンコード保存用セッションキー作成<br/>
     *
     * @param shopSeq ショップSEQ
     * @return key キャンペーンコード保存用セッションキー
     */
    protected String createCampaignCodeSessionKey(Integer shopSeq) {
        // Sessionキーを生成
        StringBuilder key = new StringBuilder(SESSION_KEY_CAMPAIGN);
        key.append(shopSeq);
        return key.toString();
    }

}
