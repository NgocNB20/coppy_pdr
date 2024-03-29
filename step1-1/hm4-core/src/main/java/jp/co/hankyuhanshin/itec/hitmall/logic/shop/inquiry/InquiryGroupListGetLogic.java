/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

import java.util.List;

/**
 * 問い合わせ分類取得ロジック
 *
 * @author wh4200
 * @version $Revision: 1.2 $
 */
public interface InquiryGroupListGetLogic {

    /**
     * 問い合わせ分類リスト取得ロジック(検索条件指定)
     *
     * @param inquiryGroupSearchForDaoConditionDto お問い合わせ分類情報DAO用検索条件DTO
     * @return お問い合わせ分類情報リスト
     */
    List<InquiryGroupEntity> execute(InquiryGroupSearchForDaoConditionDto inquiryGroupSearchForDaoConditionDto);

    /**
     * 問い合わせ分類リスト取得ロジック(ショップSEQのみ指定)
     *
     * @param shopSeq ショップSEQ
     * @return お問い合わせ分類情報リスト
     */
    List<InquiryGroupEntity> execute(Integer shopSeq);

}
