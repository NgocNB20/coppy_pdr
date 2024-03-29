/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;

/**
 * 問い合わせ登録ロジック
 *
 * @author wh4200
 * @version $Revision: 1.1 $
 */
public interface InquiryRegistLogic {

    /**
     * 問い合わせ情報登録エラーメッセージ
     */
    public static final String MSGCD_INQUIRY_REGIST_ERROR = "PKG-3720-004-L-E";

    /**
     * 一般・注文用問い合わせ登録<br/>
     * 問い合わせ・問い合わせ内容を登録する<br/>
     *
     * @param inquiryDetailsDto お問い合わせ詳細DTO
     */
    void executeInquiryRegist(InquiryDetailsDto inquiryDetailsDto);
}
