/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.shopinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAutoApprovalFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopInfoHelper {

    /**
     * 変換Helper取得
     */
    public ConversionUtility conversionUtility;

    @Autowired
    public ShopInfoHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 初期処理時の画面反映
     *
     * @param page   店舗情報詳細画面
     * @param entity ショップエンティティ
     */
    public void toPageForLoadIndex(ShopInfoModel shopInfoModel, ShopEntity entity) {
        // ショップ情報のページクラス設定
        if (entity != null) {
            // 店舗設定
            setupShopSettings(shopInfoModel, entity);
            // メタ情報
            setupMetaInfo(shopInfoModel, entity);
            // ポイント設定
            setupPointSettings(shopInfoModel, entity);
        }
    }

    /**
     * 店舗設定を画面に反映する<br/>
     *
     * @param page   ページ
     * @param entity ショップEntity
     */
    protected void setupShopSettings(ShopInfoModel shopInfoModel, ShopEntity entity) {
        // ショップSEQ
        shopInfoModel.setShopSeq(conversionUtility.toString(entity.getShopSeq()));
        // ショップ名PC
        shopInfoModel.setShopNamePC(entity.getShopNamePC());
        // ショップ名MB
        shopInfoModel.setShopNameMB(entity.getShopNameMB());
        // 公開状態PC
        shopInfoModel.setShopOpenStatusPC(entity.getShopOpenStatusPC().getLabel());
        // 公開状態MB
        shopInfoModel.setShopOpenStatusMB(entity.getShopOpenStatusMB().getLabel());
        // 公開開始日時PC
        shopInfoModel.setShopOpenStartTimePC(conversionUtility.toHms(entity.getShopOpenStartTimePC()));
        // 公開終了日時PC
        shopInfoModel.setShopOpenEndTimePC(conversionUtility.toHms(entity.getShopOpenEndTimePC()));
        // 公開開始日時MB
        shopInfoModel.setShopOpenStartTimeMB(conversionUtility.toHms(entity.getShopOpenStartTimeMB()));
        // 公開終了日時MB
        shopInfoModel.setShopOpenEndTimeMB(conversionUtility.toHms(entity.getShopOpenEndTimeMB()));
        // 自動承認フラグ設定
        shopInfoModel.setAutoApprovalFlag(entity.getAutoApprovalFlag().getLabel());
        // デフォルトポイント付与数
        shopInfoModel.setReviewDefaultPoint(entity.getReviewDefaultPoint());
    }

    /**
     * メタ情報を画面に反映する<br/>
     *
     * @param page   ページ
     * @param entity ショップEntity
     */
    protected void setupMetaInfo(ShopInfoModel shopInfoModel, ShopEntity entity) {
        // メタ説明文
        shopInfoModel.setMetaDescription(entity.getMetaDescription());
        // メタキーワード
        shopInfoModel.setMetaKeyword(entity.getMetaKeyword());
    }

    /**
     * ポイント設定を画面に反映する<br/>
     *
     * @param page   ページ
     * @param entity ショップEntity
     */
    protected void setupPointSettings(ShopInfoModel shopInfoModel, ShopEntity entity) {
        // 基本
        // ポイント付与基準金額
        shopInfoModel.setPointAddBasePriceDefault(conversionUtility.toString(entity.getPointAddBasePriceDefault()));
        // ポイント付与率
        shopInfoModel.setPointAddRateDefault(conversionUtility.toString(entity.getPointAddRateDefault()));

        // 設定1
        // ポイント適用開始日時1
        shopInfoModel.setPointStartTime1(conversionUtility.toHms(entity.getPointStartTime1()));
        // ポイント適用終了日時1
        shopInfoModel.setPointEndTime1(conversionUtility.toHms(entity.getPointEndTime1()));
        // ポイント付与基準金額1
        shopInfoModel.setPointAddBasePrice1(conversionUtility.toString(entity.getPointAddBasePrice1()));
        // ポイント付与率1
        shopInfoModel.setPointAddRate1(conversionUtility.toString(entity.getPointAddRate1()));

        // 設定2
        // ポイント適用開始日時2
        shopInfoModel.setPointStartTime2(conversionUtility.toHms(entity.getPointStartTime2()));
        // ポイント適用終了日時2
        shopInfoModel.setPointEndTime2(conversionUtility.toHms(entity.getPointEndTime2()));
        // ポイント付与基準金額2
        shopInfoModel.setPointAddBasePrice2(conversionUtility.toString(entity.getPointAddBasePrice2()));
        // ポイント付与率2
        shopInfoModel.setPointAddRate2(conversionUtility.toString(entity.getPointAddRate2()));
    }

    /**
     * 初期処理時の画面反映
     *
     * @param updatePage 店舗情報設定画面
     * @param shopEntity ショップエンティティ
     */
    public void toPageForLoadUpdate(ShopInfoModel shopInfoModel, ShopEntity shopEntity) {

        // ショップ情報のページクラス設定
        if (shopEntity != null) {

            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            // ショップ情報を画面に反映
            if (shopEntity.getShopSeq() != null) {
                shopInfoModel.setShopSeq(shopEntity.getShopSeq().toString());
            }
            shopInfoModel.setShopNamePC(shopEntity.getShopNamePC());
            shopInfoModel.setShopNameMB(shopEntity.getShopNameMB());
            if (shopEntity.getShopOpenStatusPC() != null) {
                shopInfoModel.setShopOpenStatusPC(shopEntity.getShopOpenStatusPC().getValue());
            } else {
                shopInfoModel.setShopOpenStatusPC(null);
            }
            if (shopEntity.getShopOpenStatusMB() != null) {
                shopInfoModel.setShopOpenStatusMB(shopEntity.getShopOpenStatusMB().getValue());
            } else {
                shopInfoModel.setShopOpenStatusMB(null);
            }
            if (shopEntity.getShopOpenStartTimePC() != null) {
                shopInfoModel.setShopOpenStartDatePC(conversionUtility.toYmd(shopEntity.getShopOpenStartTimePC()));
                shopInfoModel.setShopOpenStartTimePC(conversionUtility.toHms(shopEntity.getShopOpenStartTimePC()));
            } else {
                shopInfoModel.setShopOpenStartDatePC(null);
                shopInfoModel.setShopOpenStartTimePC(null);
            }
            if (shopEntity.getShopOpenEndTimePC() != null) {
                shopInfoModel.setShopOpenEndDatePC(conversionUtility.toYmd(shopEntity.getShopOpenEndTimePC()));
                shopInfoModel.setShopOpenEndTimePC(conversionUtility.toHms(shopEntity.getShopOpenEndTimePC()));
            } else {
                shopInfoModel.setShopOpenEndDatePC(null);
                shopInfoModel.setShopOpenEndTimePC(null);
            }
            if (shopEntity.getShopOpenStartTimeMB() != null) {
                shopInfoModel.setShopOpenStartDateMB(conversionUtility.toYmd(shopEntity.getShopOpenStartTimeMB()));
                shopInfoModel.setShopOpenStartTimeMB(conversionUtility.toHms(shopEntity.getShopOpenStartTimeMB()));
            } else {
                shopInfoModel.setShopOpenStartDateMB(null);
                shopInfoModel.setShopOpenStartTimeMB(null);
            }
            if (shopEntity.getShopOpenEndTimeMB() != null) {
                shopInfoModel.setShopOpenEndDateMB(conversionUtility.toYmd(shopEntity.getShopOpenEndTimeMB()));
                shopInfoModel.setShopOpenEndTimeMB(conversionUtility.toHms(shopEntity.getShopOpenEndTimeMB()));
            } else {
                shopInfoModel.setShopOpenEndDateMB(null);
                shopInfoModel.setShopOpenEndTimeMB(null);
            }
            shopInfoModel.setMetaDescription(shopEntity.getMetaDescription());
            shopInfoModel.setMetaKeyword(shopEntity.getMetaKeyword());

            // ポイント付与基準金額
            shopInfoModel.setPointAddBasePriceDefault(
                            conversionUtility.toString(shopEntity.getPointAddBasePriceDefault()));
            // ポイント付与率
            shopInfoModel.setPointAddRateDefault(conversionUtility.toString(shopEntity.getPointAddRateDefault()));
            // ポイント適用開始日1
            // ポイント適用開始時刻1
            if (shopEntity.getPointStartTime1() != null) {
                shopInfoModel.setPointStartDate1(conversionUtility.toYmd(shopEntity.getPointStartTime1()));
                shopInfoModel.setPointStartTime1(conversionUtility.toHms(shopEntity.getPointStartTime1()));
            } else {
                shopInfoModel.setPointStartDate1(null);
                shopInfoModel.setPointStartTime1(null);
            }

            // ポイント適用終了日1
            // ポイント適用終了時刻1
            if (shopEntity.getPointEndTime1() != null) {
                shopInfoModel.setPointEndDate1(conversionUtility.toYmd(shopEntity.getPointEndTime1()));
                shopInfoModel.setPointEndTime1(conversionUtility.toHms(shopEntity.getPointEndTime1()));
            } else {
                shopInfoModel.setPointEndDate1(null);
                shopInfoModel.setPointEndTime1(null);
            }

            // ポイント付与基準金額1
            shopInfoModel.setPointAddBasePrice1(conversionUtility.toString(shopEntity.getPointAddBasePrice1()));
            // ポイント付与率1
            shopInfoModel.setPointAddRate1(conversionUtility.toString(shopEntity.getPointAddRate1()));

            // ポイント適用開始日2
            // ポイント適用開始時刻2
            if (shopEntity.getPointStartTime2() != null) {
                shopInfoModel.setPointStartDate2(conversionUtility.toYmd(shopEntity.getPointStartTime2()));
                shopInfoModel.setPointStartTime2(conversionUtility.toHms(shopEntity.getPointStartTime2()));
            } else {
                shopInfoModel.setPointStartDate2(null);
                shopInfoModel.setPointStartTime2(null);
            }

            // ポイント適用終了日2
            // ポイント適用終了時刻2
            if (shopEntity.getPointEndTime2() != null) {
                shopInfoModel.setPointEndDate2(conversionUtility.toYmd(shopEntity.getPointEndTime2()));
                shopInfoModel.setPointEndTime2(conversionUtility.toHms(shopEntity.getPointEndTime2()));
            } else {
                shopInfoModel.setPointEndDate2(null);
                shopInfoModel.setPointEndTime2(null);
            }

            // ポイント付与基準金額2
            shopInfoModel.setPointAddBasePrice2(conversionUtility.toString(shopEntity.getPointAddBasePrice2()));
            // ポイント付与率2
            shopInfoModel.setPointAddRate2(conversionUtility.toString(shopEntity.getPointAddRate2()));

            // 自動承認フラグ設定
            shopInfoModel.setAutoApprovalFlag(shopEntity.getAutoApprovalFlag().getValue());
            // デフォルトポイント付与数
            shopInfoModel.setReviewDefaultPoint(shopEntity.getReviewDefaultPoint());
        }
    }

    /**
     * ショップ情報エンティティを生成して画面にセット
     *
     * @param shopInfoModel 画面情報
     */
    public void toShopEntityForUpdate(ShopInfoModel shopInfoModel) {
        ShopEntity shopEntity = shopInfoModel.getShopEntity();

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 公開開始日時
        shopInfoModel.setShopOpenStartDateTimePC(
                        conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getShopOpenStartDatePC(),
                                                                    shopInfoModel.getShopOpenStartTimePC(),
                                                                    ConversionUtility.DEFAULT_START_TIME
                                                                   ));
        shopInfoModel.setShopOpenStartDateTimeMB(
                        conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getShopOpenStartDateMB(),
                                                                    shopInfoModel.getShopOpenStartTimeMB(),
                                                                    ConversionUtility.DEFAULT_START_TIME
                                                                   ));
        // 公開終了日時
        shopInfoModel.setShopOpenEndDateTimePC(
                        conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getShopOpenEndDatePC(),
                                                                    shopInfoModel.getShopOpenEndTimePC(),
                                                                    ConversionUtility.DEFAULT_END_TIME
                                                                   ));
        shopInfoModel.setShopOpenEndDateTimeMB(
                        conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getShopOpenEndDateMB(),
                                                                    shopInfoModel.getShopOpenEndTimeMB(),
                                                                    ConversionUtility.DEFAULT_END_TIME
                                                                   ));

        shopEntity.setShopNamePC(shopInfoModel.getShopNamePC());
        shopEntity.setShopNameMB(shopInfoModel.getShopNameMB());
        shopEntity.setShopOpenStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, shopInfoModel.getShopOpenStatusPC()));
        shopEntity.setShopOpenStatusMB(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, shopInfoModel.getShopOpenStatusMB()));
        shopEntity.setShopOpenStartTimePC(shopInfoModel.getShopOpenStartDateTimePC());
        shopEntity.setShopOpenEndTimePC(shopInfoModel.getShopOpenEndDateTimePC());
        shopEntity.setShopOpenStartTimeMB(shopInfoModel.getShopOpenStartDateTimeMB());
        shopEntity.setShopOpenEndTimeMB(shopInfoModel.getShopOpenEndDateTimeMB());
        shopEntity.setMetaDescription(shopInfoModel.getMetaDescription());
        shopEntity.setMetaKeyword(shopInfoModel.getMetaKeyword());

        // ポイント付与基準金額
        shopEntity.setPointAddBasePriceDefault(
                        conversionUtility.toBigDecimal(shopInfoModel.getPointAddBasePriceDefault()));
        // ポイント付与率
        shopEntity.setPointAddRateDefault(conversionUtility.toBigDecimal(shopInfoModel.getPointAddRateDefault()));

        // ポイント適用開始日＆時刻1
        shopEntity.setPointStartTime1(conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getPointStartDate1(),
                                                                                  shopInfoModel.getPointStartTime1(),
                                                                                  ConversionUtility.DEFAULT_START_TIME
                                                                                 ));

        // ポイント適用終了日＆時刻1
        shopEntity.setPointEndTime1(conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getPointEndDate1(),
                                                                                shopInfoModel.getPointEndTime1(),
                                                                                ConversionUtility.DEFAULT_END_TIME
                                                                               ));

        // ポイント付与基準金額1
        shopEntity.setPointAddBasePrice1(conversionUtility.toBigDecimal(shopInfoModel.getPointAddBasePrice1()));
        // ポイント付与率1
        shopEntity.setPointAddRate1(conversionUtility.toBigDecimal(shopInfoModel.getPointAddRate1()));

        // ポイント適用開始日＆時刻2
        shopEntity.setPointStartTime2(conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getPointStartDate2(),
                                                                                  shopInfoModel.getPointStartTime2(),
                                                                                  ConversionUtility.DEFAULT_START_TIME
                                                                                 ));

        // ポイント適用終了日＆時刻2
        shopEntity.setPointEndTime2(conversionUtility.toTimeStampWithDefaultHms(shopInfoModel.getPointEndDate2(),
                                                                                shopInfoModel.getPointEndTime2(),
                                                                                ConversionUtility.DEFAULT_END_TIME
                                                                               ));
        // ポイント付与基準金額2
        shopEntity.setPointAddBasePrice2(conversionUtility.toBigDecimal(shopInfoModel.getPointAddBasePrice2()));
        // ポイント付与率2
        shopEntity.setPointAddRate2(conversionUtility.toBigDecimal(shopInfoModel.getPointAddRate2()));

        // 自動承認フラグ設定
        shopEntity.setAutoApprovalFlag((EnumTypeUtil.getEnumFromValue(HTypeAutoApprovalFlag.class,
                                                                      shopInfoModel.getAutoApprovalFlag()
                                                                     )));
        // デフォルトポイント付与数
        shopEntity.setReviewDefaultPoint(shopInfoModel.getReviewDefaultPoint());

        return;
    }
}
