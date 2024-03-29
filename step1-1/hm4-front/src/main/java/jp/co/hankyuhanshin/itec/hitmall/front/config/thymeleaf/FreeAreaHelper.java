/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.config.thymeleaf;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeAreaOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * フリーエリアHelper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class FreeAreaHelper {

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility 変換ユーティリティクラス
     */
    public FreeAreaHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * フリーエリアに変換
     *
     * @param freeAreaEntityResponse フリーエリアEntityレスポンス
     * @return フリーエリア
     */
    public FreeAreaEntity toFreeAreaEntity(FreeAreaEntityResponse freeAreaEntityResponse) {

        if (ObjectUtils.isEmpty(freeAreaEntityResponse)) {
            return null;
        }

        FreeAreaEntity freeAreaEntity = new FreeAreaEntity();

        freeAreaEntity.setFreeAreaSeq(freeAreaEntityResponse.getFreeAreaSeq());
        freeAreaEntity.setShopSeq(1001);
        freeAreaEntity.setFreeAreaKey(freeAreaEntityResponse.getFreeAreaKey());
        freeAreaEntity.setOpenStartTime(conversionUtility.toTimeStamp(freeAreaEntityResponse.getOpenStartTime()));
        freeAreaEntity.setFreeAreaTitle(freeAreaEntityResponse.getFreeAreaTitle());
        freeAreaEntity.setFreeAreaBodyPc(freeAreaEntityResponse.getFreeAreaBody());
        freeAreaEntity.setTargetGoods(freeAreaEntityResponse.getTargetGoods());
        freeAreaEntity.setRegistTime(conversionUtility.toTimeStamp(freeAreaEntityResponse.getRegistTime()));
        freeAreaEntity.setUpdateTime(conversionUtility.toTimeStamp(freeAreaEntityResponse.getUpdateTime()));
        freeAreaEntity.setFreeAreaOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeFreeAreaOpenStatus.class,
                                                                           freeAreaEntityResponse.getFreeAreaOpenStatus()
                                                                          ));
        freeAreaEntity.setSiteMapFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, freeAreaEntityResponse.getSiteMapFlag()));

        return freeAreaEntity;
    }
}
