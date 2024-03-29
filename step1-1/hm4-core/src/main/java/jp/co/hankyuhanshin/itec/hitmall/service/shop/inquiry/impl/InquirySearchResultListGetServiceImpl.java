/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquirySearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySearchResultListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 問い合わせ検索結果リスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class InquirySearchResultListGetServiceImpl extends AbstractShopService
                implements InquirySearchResultListGetService {

    /**
     * 問い合わせ検索ロジック
     */
    private final InquirySearchResultListGetLogic inquirySearchResultListGetLogic;

    @Autowired
    public InquirySearchResultListGetServiceImpl(InquirySearchResultListGetLogic inquirySearchResultListGetLogic) {
        this.inquirySearchResultListGetLogic = inquirySearchResultListGetLogic;
    }

    /**
     * 問い合わせ検索結果リスト取得
     *
     * @param conditionDto 検索条件
     * @return 問い合わせ検索結果リスト
     */
    @Override
    public List<InquirySearchResultDto> execute(InquirySearchDaoConditionDto conditionDto) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // 共通情報の取得
        Integer shopSeq = 1001;

        // 検索条件に追加
        conditionDto.setShopSeq(shopSeq);

        // 検索処理
        return inquirySearchResultListGetLogic.execute(conditionDto);
    }

}
