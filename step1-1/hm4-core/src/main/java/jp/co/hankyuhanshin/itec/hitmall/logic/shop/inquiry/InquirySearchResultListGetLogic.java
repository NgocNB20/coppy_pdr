/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchResultDto;

import java.util.List;

/**
 * 問い合わせ検索結果リスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquirySearchResultListGetLogic {

    /**
     * 問い合わせ検索結果リスト取得
     *
     * @param conditionDto 検索条件
     * @return 問い合わせ検索結果Dtoリスト
     */
    List<InquirySearchResultDto> execute(InquirySearchDaoConditionDto conditionDto);

    /**
     * 問い合わせ検索結果リスト取得（フロント）
     *
     * @param conditionDto 検索条件
     * @return 問い合わせ検索結果Dtoリスト
     */
    List<InquirySearchResultDto> executeFront(InquirySearchDaoConditionDto conditionDto);
}
