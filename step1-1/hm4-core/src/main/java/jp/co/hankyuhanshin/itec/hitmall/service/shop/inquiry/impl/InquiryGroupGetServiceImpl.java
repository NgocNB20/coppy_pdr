/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * お問い合わせ分類取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class InquiryGroupGetServiceImpl extends AbstractShopService implements InquiryGroupGetService {

    /**
     * 問い合わせ分類取得ロジック
     */
    private final InquiryGroupGetLogic inquiryGroupGetLogic;

    @Autowired
    public InquiryGroupGetServiceImpl(InquiryGroupGetLogic inquiryGroupGetLogic) {
        this.inquiryGroupGetLogic = inquiryGroupGetLogic;
    }

    /**
     * お問い合わせ分類取得
     *
     * @param inquiryGroupSeq 問い合わせ分類SEQ
     * @return お問い合わせ分類エンティティ
     */
    @Override
    public InquiryGroupEntity execute(Integer inquiryGroupSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("inquiryGroupSeq", inquiryGroupSeq);

        // 共通情報の取得
        Integer shopSeq = 1001;

        // 問題なければ取得
        InquiryGroupEntity entity = inquiryGroupGetLogic.execute(inquiryGroupSeq, shopSeq);
        if (entity == null) {
            throwMessage(MSGCD_INQUIRYGROUP_GET_FAIL);
        }

        return entity;
    }

}
