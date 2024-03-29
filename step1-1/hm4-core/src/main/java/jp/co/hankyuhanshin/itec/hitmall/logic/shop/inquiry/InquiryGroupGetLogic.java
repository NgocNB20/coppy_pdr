/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

import java.util.Map;

/**
 * お問い合わせ分類取得ロジック
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryGroupGetLogic {

    /**
     * お問い合わせ分類取得
     *
     * @param inquiryGroupSeq お問い合わせ分類SEQ
     * @param shopSeq         ショップSEQ
     * @return お問い合わせ分類
     */
    InquiryGroupEntity execute(Integer inquiryGroupSeq, Integer shopSeq);

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 問い合わせ結果を格納したMap
     */
    Map<String, String> getItemMapList(Integer shopSeq);
}
