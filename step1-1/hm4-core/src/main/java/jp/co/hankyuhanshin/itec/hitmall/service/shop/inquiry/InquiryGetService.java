/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;

import java.util.List;

/**
 * 問い合わせ取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryGetService {

    /* メッセージ SSI0004 */

    /**
     * 問い合わせ取得失敗エラー<br/>
     * <code>MSGCD_INQUIRY_GET_FAIL</code>
     */
    public static final String MSGCD_INQUIRY_GET_FAIL = "SSI000401";
    /**
     * 問い合わせ分類取得失敗エラー<br/>
     * <code>MSGCD_INQUIRYGROUP_GET_FAIL</code>
     */
    public static final String MSGCD_INQUIRYGROUP_GET_FAIL = "SSI000402";

    /**
     * 問い合わせ取得
     *
     * @param inquirySeq 問い合わせSEQ
     * @return 問い合わせ詳細Dto
     */
    InquiryDetailsDto execute(Integer inquirySeq);

    /**
     * 問い合わせ取得
     *
     * @param orderCode 受注番号
     * @return 問い合わせ詳細Dto
     */
    List<InquiryDetailsDto> execute(String orderCode);

    /**
     * 問い合わせ取得（ソート順指定）
     *
     * @param orderCode 受注番号
     * @param asc       昇順/降順フラグ
     * @return 問い合わせ詳細Dto
     */
    List<InquiryDetailsDto> execute(String orderCode, boolean asc);
}
