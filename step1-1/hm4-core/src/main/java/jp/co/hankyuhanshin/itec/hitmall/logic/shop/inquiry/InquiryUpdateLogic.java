/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;

/**
 * 問い合わせ更新ロジック
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryUpdateLogic {

    /**
     * 問い合わせ更新ロジック
     *
     * @param inquiryEntity 問い合わせエンティティ
     * @return 処理件数
     */
    int execute(InquiryEntity inquiryEntity);

}
