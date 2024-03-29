/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * お問い合わせ分類登録更新チェックロジック<br/>
 *
 * @author Nishigaki Mio (itec)
 */
public interface InquiryGroupRegistUpdateCheckLogic {

    /**
     * メッセージコード：問い合わせ分類名重複エラー
     */
    public static final String MSGCD_INQUIRYGROUP_NAME_OVERLAP = "LSI000702";

    /**
     * お問い合わせ分類登録更新チェック<br/>
     * ・問い合わせ分類名重複チェック
     *
     * @param inquiryGroupEntity 問い合わせ分類エンティティ
     */
    void execute(InquiryGroupEntity inquiryGroupEntity);
}
