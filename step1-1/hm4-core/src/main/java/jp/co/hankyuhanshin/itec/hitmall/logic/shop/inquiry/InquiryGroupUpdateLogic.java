/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * 問い合わせ分類登録処理
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryGroupUpdateLogic {

    /**
     * 問い合わせ分類登録処理
     *
     * @param inquiryGroupEntity 問い合わせ分類
     * @return 処理件数
     */
    int execute(InquiryGroupEntity inquiryGroupEntity);
}
