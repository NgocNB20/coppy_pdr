/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.PaymentRegistDto;

import java.util.List;

/**
 * 入金リスト登録<br/>
 *
 * @author USER
 * @version $Revision: 1.5 $
 */
public interface OrderReceiptOfMoneyListRegistService {

    /**
     * 入金対象受注番号件数とレコードロック受注件数が不一致
     */
    public static final String MSGCD_LISTCOUNT_LOCKCOUNT_DEF = "SOO001101";

    /**
     * 入金対象の受注が他者によって更新されている場合（受注履歴番号が異なる）
     */
    public static final String MSGCD_ORDERVERSIONNO_DEF = "SOO001102";

    /**
     * 処理件数=0件の場合
     */
    public static final String MSGCD_RESULT_ZERO = "SOO001103";

    /**
     * 成功処理件数メッセージ
     */
    public static final String MSGCD_SUCCESS_COUNT_INFO = "SOO001104";

    /**
     * 実行メソッド<br/>
     *
     * @param paymentRegistDtoList 入金登録DTO
     * @return 処理件数（受注単位）
     */
    List<CheckMessageDto> execute(List<PaymentRegistDto> paymentRegistDtoList, String administratorName);

}
