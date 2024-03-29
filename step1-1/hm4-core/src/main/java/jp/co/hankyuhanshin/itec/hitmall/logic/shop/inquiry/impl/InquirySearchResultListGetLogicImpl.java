/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquirySearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 問い合わせ検索結果リスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class InquirySearchResultListGetLogicImpl extends AbstractShopLogic implements InquirySearchResultListGetLogic {

    /**
     * 問い合わせDao
     */
    private final InquiryDao inquiryDao;

    @Autowired
    public InquirySearchResultListGetLogicImpl(InquiryDao inquiryDao) {
        this.inquiryDao = inquiryDao;
    }

    /**
     * 問い合わせ検索結果リスト取得
     *
     * @param conditionDto 検索条件
     * @return 問い合わせ検索結果Dtoリスト
     */
    @Override
    public List<InquirySearchResultDto> execute(InquirySearchDaoConditionDto conditionDto) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("InquirySearchDaoConditionDto", conditionDto);

        return inquiryDao.getSearchInquiryForBackList(conditionDto, conditionDto.getPageInfo().getSelectOptions());
    }

    /**
     * 問い合わせ検索結果リスト取得（フロント）
     *
     * @param conditionDto 検索条件
     * @return 問い合わせ検索結果Dtoリスト
     */
    @Override
    public List<InquirySearchResultDto> executeFront(InquirySearchDaoConditionDto conditionDto) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // 共通情報の取得
        Integer shopSeq = 1001;
        // 検索条件に追加
        conditionDto.setShopSeq(shopSeq);

        return inquiryDao.getSearchInquiryForFrontList(conditionDto, conditionDto.getPageInfo().getSelectOptions());
    }
}
