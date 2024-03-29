/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.logic.common;

import jp.co.hankyuhanshin.itec.hitmall.api.user.UsersApi;
import jp.co.hankyuhanshin.itec.hitmall.api.user.param.AccessUidResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.crypto.CryptoHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.AccessDeviceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 共通処理ロジックの抽象クラス<br/>
 *
 * @author natume
 */
@Component
@Data
public abstract class AbstractCommonProcessLogic {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommonProcessLogic.class);

    /**
     * Cookie 名：端末識別番号  accessUid
     */
    private static final String COOKIE_NAME_ACCESSUID = "AUI";

    /**
     * 文字コード UTF-8<br/>
     */
    private static final String UTF_8 = "UTF-8";

    /**
     * クッキー有効期限:90日(3ヶ月)/view/<br/>
     */
    private static final int COOKIE_EXPIRY = 60 * 60 * 24 * 90;

    /**
     * クッキー有効期限:0 削除/view/<br/>
     */
    private static final int COOKIE_CLEAR = 0;

    /**
     * コロン：
     */
    private static final String COLON = ":";

    /**
     * 共通情報ユーティリティ
     */
    private static CommonInfoUtility commonInfoUtility;

    /**
     * ApplicationLog ユーティリティ
     */
    private static ApplicationLogUtility applicationLogUtility;

    /**
     * アクセスデバイスの解析用Utility
     */
    private final AccessDeviceUtility accessDeviceUtility;

    /**
     * Cookie 名：キャンペーンコード campaignCode
     */
    protected static final String COOKIE_NAME_CAMPAIGN_CODE = "CMPCD";

    /**
     * コンストラクタ<br/>
     *
     * @param commonInfoUtility
     * @param applicationLogUtility
     * @param accessDeviceUtility
     */
    @Autowired
    public AbstractCommonProcessLogic(CommonInfoUtility commonInfoUtility,
                                      ApplicationLogUtility applicationLogUtility,
                                      AccessDeviceUtility accessDeviceUtility) {
        this.commonInfoUtility = commonInfoUtility;
        this.applicationLogUtility = applicationLogUtility;
        this.accessDeviceUtility = accessDeviceUtility;
    }

    /**
     * 端末識別番号の作成<br/>
     *
     * @return 端末識別番号
     */
    protected String createAccessUid() {

        // 端末識別番号作成
        UsersApi usersApi = ApplicationContextUtility.getBean(UsersApi.class);

        AccessUidResponse accessUidResponse = null;
        try {
            accessUidResponse = usersApi.getAccessUid();
        } catch (HttpClientErrorException e) {
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        return accessUidResponse.getAccessUid();
    }

    /**
     * 端末識別番号のセット
     *
     * @param request リクエスト
     * @return 端末識別番号
     */
    protected String getAccessUid(HttpServletRequest request) {

        // クッキーから取得
        String accessUid = null;
        String encryptedAccessUid = null;
        Cookie cookie = getCookie(request, COOKIE_NAME_ACCESSUID);
        if (cookie != null) {
            try {
                // 暗号化された端末識別番号
                encryptedAccessUid = URLDecoder.decode(cookie.getValue(), UTF_8);

                // 復号
                CryptoHelper cryptoHelper = ApplicationContextUtility.getBean(CryptoHelper.class);
                accessUid = cryptoHelper.decryptAccessUid(encryptedAccessUid);
            } catch (Exception e) {
                RuntimeException re = new RuntimeException("Cookie decrypt fail accessUid:" + encryptedAccessUid, e);
                applicationLogUtility.writeExceptionLog(re);
            }
        }

        // ない場合作成
        if (accessUid == null) {
            accessUid = createAccessUid();
        }
        return accessUid;
    }

    /**
     * クッキーに端末番号をセット<br/>
     * ※PCの場合のみ<br/>
     *
     * @param request    リクエスト
     * @param response   レスポンス
     * @param commonInfo 共通情報
     */
    protected void setAccessUid(HttpServletRequest request, HttpServletResponse response, CommonInfo commonInfo) {

        // クッキーから取得
        Cookie cookie = getCookie(request, COOKIE_NAME_ACCESSUID);

        // 端末識別番号取得
        String accessUid = commonInfo.getCommonInfoBase().getAccessUid();

        // クッキーの端末識別番号の暗号化
        String encryptedAccessUid = null;
        try {
            CryptoHelper cryptoHelper = ApplicationContextUtility.getBean(CryptoHelper.class);
            encryptedAccessUid = cryptoHelper.encryptAccessUid(accessUid);
        } catch (Exception e) {
            RuntimeException re = new RuntimeException("Cookie encrypt fail accessUid:" + accessUid, e);
            applicationLogUtility.writeExceptionLog(re);
            LOGGER.error("The deletion of the cookie is executed.");

            /* 暗号化失敗 クッキー削除 */
            deleteCookie(request, response, cookie);
            return;
        }

        // クッキーがある場合
        if (cookie == null) {
            cookie = new Cookie(COOKIE_NAME_ACCESSUID, encryptedAccessUid);
        } else {
            cookie.setValue(encryptedAccessUid);
        }

        // 有効期間
        updateCookie(request, response, cookie);
    }

    /**
     * クッキー取得<br/>
     *
     * @param request    リクエスト
     * @param cookieName クッキー名
     * @return Cookie クッキー情報
     */
    protected Cookie getCookie(HttpServletRequest request, String cookieName) {
        // 現行HM HttpUtilityから移植
        // クッキーから取得
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * クッキーを更新する<br/>
     *
     * @param request  リクエスト
     * @param response レスポンス
     * @param cookie   クッキー
     */
    protected void updateCookie(HttpServletRequest request, HttpServletResponse response, Cookie cookie) {

        // クッキーがある場合
        if (cookie != null) {

            // 有効期間
            cookie.setMaxAge(COOKIE_EXPIRY);
            cookie.setPath(request.getContextPath());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
        }
    }

    /**
     * クッキーを削除する<br/>
     *
     * @param request  リクエスト
     * @param response レスポンス
     * @param cookie   クッキー
     */
    protected void deleteCookie(HttpServletRequest request, HttpServletResponse response, Cookie cookie) {

        // クッキーがある場合
        if (cookie != null) {

            // 有効期間
            cookie.setMaxAge(COOKIE_CLEAR);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
    }

    /**
     * 起動アプリのショップSEQを取得<br/>
     * ※ショップSEQをどこで指定するかは変更される可能性があるので、
     * 専用メソッドを作成しておく
     *
     * @return ショップSEQ
     */
    public Integer getShopSeq() {
        // 現行ApplicationUtilityから移植
        // system.propertiesから取得
        String shopSeqStr = PropertiesUtil.getSystemPropertiesValue("shopseq");
        if (shopSeqStr == null || !shopSeqStr.matches("^[1-9]$|^[1-9][0-9]{1,3}$")) {
            throw new RuntimeException("system.properties shopseqの値が不正です。");
        }
        return Integer.valueOf(shopSeqStr);
    }

    /**
     * キャンペーンパラメータkey取得<br/>
     *
     * @param customParams 案件用引数
     * @return キャンペーンパラメータkey
     */
    protected String getCampaignParamKey(Object... customParams) {
        String campaignParamKey = PropertiesUtil.getSystemPropertiesValue("campaign.param.key");
        if (StringUtil.isEmpty(campaignParamKey)) {
            RuntimeException re = new RuntimeException(
                            "The setting is incomplete. system.properties [campaign.param.key] fail");
            applicationLogUtility.writeExceptionLog(re);
            throw re;
        }
        return campaignParamKey;
    }

    /**
     * リクエストパラメータからキャンペーンコードを取得し、
     * cookieに設定.<br/>
     * キャンペーンコードに変更がない場合は、cookieの更新は行わない.
     *
     * @param request  リクエスト
     * @param response レスポンス
     */
    protected void setCookieCampaignCode(HttpServletRequest request, HttpServletResponse response) {
        // リクエストパラメータからからキャンペーンコードを取得
        String ckey = getCampaignParamKey();

        // リクエストから取得
        String campaignCode = request.getParameter(ckey);
        if (StringUtil.isNotEmpty(campaignCode)) {
            // クッキーを取得
            Cookie cookie = this.getCookie(request, COOKIE_NAME_CAMPAIGN_CODE);
            if (cookie == null) {
                cookie = new Cookie(COOKIE_NAME_CAMPAIGN_CODE, campaignCode);
            } else if (campaignCode.equals(cookie.getValue())) {
                return;
            } else {
                cookie.setValue(campaignCode);
            }

            // 有効期限を更新
            updateCookie(request, response, cookie);
        }
    }

}
