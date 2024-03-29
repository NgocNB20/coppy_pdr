/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import org.springframework.stereotype.Component;

@Component
public class InquiryGroupRegistUpdateHelper {

    /**
     * 初期処理時の画面反映
     *
     * @param indexPage          問い合わせ分類登録更新画面
     * @param inquiryGroupEntity 問い合わせ分類エンティティ
     */
    public void toPageForLoad(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel,
                              InquiryGroupEntity inquiryGroupEntity) {
        inquiryGroupRegistUpdateModel.setNormality(true);
        // 指定時
        if (inquiryGroupEntity != null) {
            // 画面へ反映
            inquiryGroupRegistUpdateModel.setInquiryGroupSeq(inquiryGroupEntity.getInquiryGroupSeq());

            inquiryGroupRegistUpdateModel.setInquiryGroupName(inquiryGroupEntity.getInquiryGroupName());
            inquiryGroupRegistUpdateModel.setOpenStatus(inquiryGroupEntity.getOpenStatus().getValue());

            inquiryGroupRegistUpdateModel.setInquiryGroupEntity(inquiryGroupEntity);
        }
    }

    /**
     * 問い合わせ分類登録更新時の処理
     *
     * @param indexPage 問い合わせ分類登録更新確認画面
     * @return 問い合わせ分類エンティティ
     */
    public InquiryGroupEntity toInquiryGroupEntityForInquiryGroupRegist(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel) {

        InquiryGroupEntity inquiryGroupEntity = inquiryGroupRegistUpdateModel.getInquiryGroupEntity();

        inquiryGroupEntity.setInquiryGroupName(inquiryGroupRegistUpdateModel.getInquiryGroupName());
        inquiryGroupEntity.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                       inquiryGroupRegistUpdateModel.getOpenStatus()
                                                                      ));

        return inquiryGroupEntity;

    }

}
