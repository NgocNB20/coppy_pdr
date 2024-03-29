/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;

/**
 * 問い合わせメール送信サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public interface InquirySendMailService {

    /**
     * 問い合わせ情報取得失敗エラー<br/>
     * <code>MSGCD_INQUIRY_NULL</code>
     */
    String MSGCD_INQUIRY_NULL = "PKG-3720-024-S-";

    /**
     * 問い合わせメール送信失敗エラー<br/>
     * <code>MSGCD_INQUIRY_ERROR</code>
     */
    String MSGCD_INQUIRY_ERROR = "SSI000401F";

    /**
     * 問い合わせメール送信処理<br/>
     *
     * @param inquirySeq       問い合わせSEQ
     * @param mailTemplateType メールテンプレート(問い合わせ受付/問い合わせ回答)
     * @return メール送信結果
     */
    boolean execute(Integer inquirySeq, HTypeMailTemplateType mailTemplateType);

    /**
     * 問い合わせメール送信処理<br/>
     *
     * @param inquiryCode      問い合わせSEQ
     * @param mailTemplateType メールテンプレート(問い合わせ受付/問い合わせ回答)
     * @return メール送信結果
     */
    boolean execute(String inquiryCode, HTypeMailTemplateType mailTemplateType);

}
