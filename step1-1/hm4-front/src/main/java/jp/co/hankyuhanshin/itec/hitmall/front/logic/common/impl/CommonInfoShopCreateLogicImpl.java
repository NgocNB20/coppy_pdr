/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.logic.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ShopResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoShop;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.CommonInfoShopImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAutoApprovalFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.common.CommonInfoShopCreateLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

/**
 * ショップ情報作成ロジック(共通情報)<br/>
 *
 * @author natume
 * @author sakai
 * @version $Revision: 1.3 $
 */
@Component
public class CommonInfoShopCreateLogicImpl implements CommonInfoShopCreateLogic {

    /**
     * ショップAPI
     */
    private final ShopApi shopApi;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param shopApi           ショップAPI
     * @param conversionUtility 変換ユーティリティクラス
     */
    @Autowired
    public CommonInfoShopCreateLogicImpl(ShopApi shopApi, ConversionUtility conversionUtility) {
        this.shopApi = shopApi;
        this.conversionUtility = conversionUtility;
    }

    /**
     * ショップ情報作成処理<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param siteType   サイトタイプ
     * @param openStatus 公開状態
     * @return ショップ情報(共通情報)
     */
    //    @Override
    //    public CommonInfoShop execute(Integer shopSeq, HTypeSiteType siteType, HTypeOpenStatus openStatus) {
    //
    //        // パラメータチェック
    //        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", shopSeq);
    //
    //        // ショップ情報取得
    //        ShopEntity shopEntity = shopApi.get();
    //        if (shopEntity == null) {
    //            return null;
    //        }
    //
    //        // ショップ情報の作成
    //        return createCommonInfoShop(shopEntity);
    //    }

    /**
     * ショップ情報作成（共通情報）<br/>
     *
     * @param shopEntity ショップエンティティ
     * @return ショップ情報(共通情報)
     */
    protected CommonInfoShop createCommonInfoShop(ShopEntity shopEntity) {

        // ショップ情報作成
        CommonInfoShopImpl commonInfoShopImpl = new CommonInfoShopImpl();
        commonInfoShopImpl.setShopMetaDescription(shopEntity.getMetaDescription());
        commonInfoShopImpl.setShopMetaKeyword(shopEntity.getMetaKeyword());
        commonInfoShopImpl.setShopNamePC(shopEntity.getShopNamePC());
        commonInfoShopImpl.setUrlPC(shopEntity.getUrlPC());
        return commonInfoShopImpl;
    }

    /**
     * ショップ情報作成処理<br/>
     *
     * @param shopSeq ショップSEQ
     * @return ショップ情報(共通情報)
     */
    @Override
    public CommonInfoShop execute(Integer shopSeq) {

        // ショップ情報取得
        ShopResponse shopResponse = null;
        try {
            shopResponse = shopApi.get();

        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }
        ShopEntity shopEntity = toShopEntity(shopResponse, shopSeq);
        CommonInfoShopImpl commonInfoShopImpl = (CommonInfoShopImpl) createCommonInfoShop(shopEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 店舗公開期間での判定
        boolean isShopOpen = dateUtility.isOpen(shopEntity.getShopOpenStartTimePC(), shopEntity.getShopOpenEndTimePC());

        // 店舗情報公開状態判定
        if (shopEntity.getShopOpenStatusPC() == HTypeOpenStatus.NO_OPEN || !isShopOpen) {
            commonInfoShopImpl.setCloseFlag(true);
        }

        // 2023-renew No60 from here
        // クレジットカードエラー回数上限
        commonInfoShopImpl.setCreditErrorCountLimit(shopEntity.getCreditErrorCount());
        // 2023-renew No60 to here

        return commonInfoShopImpl;
    }

    /**
     * ショップエンティティに変換
     *
     * @param shopResponse ショップ情報レスポンス
     * @param shopSeq      ショップSEQ
     * @return ショップエンティティ
     */
    protected ShopEntity toShopEntity(ShopResponse shopResponse, Integer shopSeq) {

        ShopEntity shopEntity = new ShopEntity();

        shopEntity.setShopSeq(shopSeq);
        shopEntity.setShopId(shopResponse.getShopId());
        shopEntity.setShopNamePC(shopResponse.getShopNamePC());
        shopEntity.setUrlPC(shopResponse.getUrlPC());
        shopEntity.setMetaDescription(shopResponse.getMetaDescription());
        shopEntity.setMetaKeyword(shopResponse.getMetaKeyword());
        shopEntity.setVersionNo(shopResponse.getVersionNo());
        shopEntity.setAutoApprovalFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeAutoApprovalFlag.class, shopResponse.getAutoApprovalFlag()));

        if (StringUtil.isNotEmpty(shopResponse.getShopOpenStatus())) {
            shopEntity.setShopOpenStatusPC(
                            EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, shopResponse.getShopOpenStatus()));
        }

        shopEntity.setShopOpenStartTimePC(conversionUtility.toTimeStamp(shopResponse.getShopOpenStartTime()));
        shopEntity.setShopOpenEndTimePC(conversionUtility.toTimeStamp(shopResponse.getShopOpenEndTime()));
        // 2023-renew No60 from here
        shopEntity.setCreditErrorCount(shopResponse.getCreditErrorCount());
        // 2023-renew No60 to here

        return shopEntity;
    }

}
