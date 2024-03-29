/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeAreaOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * 特集ページ Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class SpecialHelper {

    /**
     * 画面表示・再表示<br/>
     *
     * @param freeAreaEntity フリーエリアエンティティ情報
     * @param specialModel   特集ページModel
     */
    public void toPageForLoad(FreeAreaEntity freeAreaEntity, SpecialModel specialModel) {

        specialModel.setFreeAreaTitle(freeAreaEntity.getFreeAreaTitle());
        specialModel.setFreeAreaBodyPc(freeAreaEntity.getFreeAreaBodyPc());
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
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        FreeAreaEntity freeAreaEntity = new FreeAreaEntity();

        freeAreaEntity.setFreeAreaSeq(freeAreaEntityResponse.getFreeAreaSeq());
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
