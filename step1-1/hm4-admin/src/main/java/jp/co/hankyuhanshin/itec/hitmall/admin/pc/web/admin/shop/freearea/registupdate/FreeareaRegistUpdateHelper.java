/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUkFeedInfoSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * フリーエリア登録・更新画面のHelper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class FreeareaRegistUpdateHelper {

    /**
     * 初期処理時の画面反映
     *
     * @param freeareaRegistUpdateModel フリーエリア登録更新画面
     * @param freeAreaEntity            フリーエリアエンティティ
     */
    public void toPageForLoad(FreeareaRegistUpdateModel freeareaRegistUpdateModel, FreeAreaEntity freeAreaEntity) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        freeareaRegistUpdateModel.setNormality(true);
        // 反映情報が指定されている場合
        if (freeAreaEntity != null) {

            // 画面へ反映
            freeareaRegistUpdateModel.setFreeAreaSeq(freeAreaEntity.getFreeAreaSeq());
            freeareaRegistUpdateModel.setFreeAreaKey(freeAreaEntity.getFreeAreaKey());
            freeareaRegistUpdateModel.setFreeAreaTitle(freeAreaEntity.getFreeAreaTitle());
            freeareaRegistUpdateModel.setFreeAreaBodyPc(freeAreaEntity.getFreeAreaBodyPc());
            freeareaRegistUpdateModel.setTargetGoods(freeAreaEntity.getTargetGoods());
            freeareaRegistUpdateModel.setSiteMapFlag(freeAreaEntity.getSiteMapFlag().getValue());
            freeareaRegistUpdateModel.setOpenStartDate(conversionUtility.toYmd(freeAreaEntity.getOpenStartTime()));
            freeareaRegistUpdateModel.setOpenStartTime(conversionUtility.toHms(freeAreaEntity.getOpenStartTime()));
            // 2023-renew No36-1, No61,67,95 from here
            freeareaRegistUpdateModel.setUkFeedInfoSendFlag(freeAreaEntity.getUkFeedInfoSendFlag().getValue());
            freeareaRegistUpdateModel.setUkTransitionUrl(freeAreaEntity.getUkTransitionUrl());
            freeareaRegistUpdateModel.setUkContentImageUrl(freeAreaEntity.getUkContentImageUrl());
            freeareaRegistUpdateModel.setUkSearchKeyword(freeAreaEntity.getUkSearchKeyword());
            // 2023-renew No36-1, No61,67,95 to here
            freeareaRegistUpdateModel.setFreeAreaEntity(freeAreaEntity);

        }

    }

    /**
     * 画面表示・再表示 フリーエリア
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面
     * @param freeAreaEntity            フリーエリアエンティティ
     */
    public void toPageForLoadPreview(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                                     FreeAreaEntity freeAreaEntity) {

        freeareaRegistUpdateModel.setFreeAreaTitle(freeAreaEntity.getFreeAreaTitle());
        freeareaRegistUpdateModel.setFreeAreaBodyPc(freeAreaEntity.getFreeAreaBodyPc());

    }

    /**
     * 確認画面へ遷移前の画面反映
     *
     * @param freeareaRegistUpdateIndexModel フリーエリア登録更新画面
     */
    public void toPageForConfirm(FreeareaRegistUpdateModel freeareaRegistUpdateIndexModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 時刻反映
        freeareaRegistUpdateIndexModel.setOpenStartTime(
                        conversionUtility.toDefaultHms(freeareaRegistUpdateIndexModel.getOpenStartDate(),
                                                       freeareaRegistUpdateIndexModel.getOpenStartTime(),
                                                       ConversionUtility.DEFAULT_START_TIME
                                                      ));

        // 2023-renew No36-1, No61,67,95 from here
        // コンテンツ画像URLが入力されている場合は
        // 未入力の場合はnoimageを表示する
        if (freeareaRegistUpdateIndexModel.getUkContentImageUrl() != null) {
            freeareaRegistUpdateIndexModel.setContentsImage(
                            PropertiesUtil.getSystemPropertiesValue("images.path.contents") + "/"
                            + freeareaRegistUpdateIndexModel.getUkContentImageUrl());
        } else {
            freeareaRegistUpdateIndexModel.setContentsImage(
                            PropertiesUtil.getSystemPropertiesValue("images.path.goods") + "/noimg.jpg");
        }
        // 2023-renew No36-1, No61,67,95 to here
    }

    /**
     * フリーエリア登録更新時の処理
     *
     * @param freeareaRegistUpdateModel 画面情報
     * @return フリーエリアエンティティ
     */
    public FreeAreaEntity toFreeAreaEntityForNewsRegist(FreeareaRegistUpdateModel freeareaRegistUpdateModel) {

        FreeAreaEntity freeAreaEntity = freeareaRegistUpdateModel.getFreeAreaEntity();

        freeAreaEntity.setFreeAreaKey(freeareaRegistUpdateModel.getFreeAreaKey());
        freeAreaEntity.setFreeAreaTitle(freeareaRegistUpdateModel.getFreeAreaTitle());
        freeAreaEntity.setFreeAreaBodyPc(freeareaRegistUpdateModel.getFreeAreaBodyPc());
        freeAreaEntity.setTargetGoods(freeareaRegistUpdateModel.getTargetGoods());
        freeAreaEntity.setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class,
                                                                    freeareaRegistUpdateModel.getSiteMapFlag()
                                                                   ));
        freeAreaEntity.setOpenStartTime(this.ymdhmsToTimestamp(freeareaRegistUpdateModel.getOpenStartDate(),
                                                               freeareaRegistUpdateModel.getOpenStartTime()
                                                              ));
        // 2023-renew No36-1, No61,67,95 from here
        freeAreaEntity.setUkFeedInfoSendFlag(EnumTypeUtil.getEnumFromValue(HTypeUkFeedInfoSendFlag.class,
                                                                           freeareaRegistUpdateModel.getUkFeedInfoSendFlag()
                                                                          ));
        freeAreaEntity.setUkTransitionUrl(freeareaRegistUpdateModel.getUkTransitionUrl());
        freeAreaEntity.setUkContentImageUrl(freeareaRegistUpdateModel.getUkContentImageUrl());
        freeAreaEntity.setUkSearchKeyword(freeareaRegistUpdateModel.getUkSearchKeyword());
        // 2023-renew No36-1, No61,67,95 to here
        return freeAreaEntity;
    }

    /**
     * コピー用に半角変換した値を返す
     *
     * @param valuePC      PC値
     * @param customParams 案件用引数
     * @return 携帯値
     */
    protected String convertToMB(String valuePC, Object... customParams) {

        String ret = null;
        if (valuePC != null) {
            // 全角、半角の変換Helper取得
            ZenHanConversionUtility zenHanConversionUtility =
                            ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

            ret = zenHanConversionUtility.toHankaku(valuePC, new Character[] {'＆', '＜', '＞', '”', '’', '￥'});
        }

        return ret;
    }

    /**
     * 年月日・時分秒→タイムスタンプ
     *
     * @param ymd 日付項目値
     * @param hms 時刻項目値
     * @return 引数から取得されるタイムスタンプ
     */
    protected Timestamp ymdhmsToTimestamp(String ymd, String hms) {

        Timestamp ret = null;
        if (ymd != null && hms != null) {
            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            ret = conversionUtility.toTimeStamp(ymd, hms);
        }
        return ret;

    }

}
