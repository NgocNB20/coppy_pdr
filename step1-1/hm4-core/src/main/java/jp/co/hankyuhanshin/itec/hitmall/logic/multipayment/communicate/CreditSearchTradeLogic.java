/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.SearchTradeOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;

import java.util.List;

/**
 * 取引状態参照ロジック
 *
 * @author yt13605
 */
public interface CreditSearchTradeLogic {

    /**
     * 通信処理中エラー発生時
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000061";

    /**
     * 実行メソッド
     *
     * @param orderId マルチペイメント請求.オーダーID
     * @return 取引状態照会出力パラメータ
     */
    SearchTradeOutput execute(String orderId);

    /**
     * 取引状態参照出力用エラーチェック
     *
     * @param output 取引状態参照出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    List<CheckMessageDto> checkOutput(SearchTradeOutput output);
}
