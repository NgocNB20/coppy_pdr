/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupListGetForBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * お問い合わせ分類リスト取得サービス(管理者用)
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class InquiryGroupListGetForBackServiceImpl extends AbstractShopService
                implements InquiryGroupListGetForBackService {

    /**
     * お問い合わせ分類リスト取得ロジック
     */
    private final InquiryGroupListGetLogic inquiryGroupListGetLogic;

    @Autowired
    public InquiryGroupListGetForBackServiceImpl(InquiryGroupListGetLogic inquiryGroupListGetLogic) {
        this.inquiryGroupListGetLogic = inquiryGroupListGetLogic;
    }

    /**
     * お問い合わせ分類リスト取得サービス(管理者用)
     *
     * @return お問い合わせ分類リスト
     */
    @Override
    public List<InquiryGroupEntity> execute() {

        // 共通情報の取得
        Integer shopSeq = 1001;

        // 問題なければ取得
        return inquiryGroupListGetLogic.execute(shopSeq);
    }

}
