/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.TradedCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 決済後カード登録通信ロジック
 *
 * @author s_nose
 */
public interface TradedCardLogic {

    /**
     * 通信処理中エラー発生時
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000041";

    /**
     * 実行メソッド<br />
     *
     * @param dto     受注DTO
     * @param orderId 受注番号
     * @return 決済後カード登録出力パラメータ
     */
    TradedCardOutput execute(ReceiveOrderDto dto, String orderId);

}
