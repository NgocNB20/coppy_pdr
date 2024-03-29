/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.DeleteCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * カード削除通信ロジック
 */
public interface DeleteCardLogic {

    /**
     * 通信処理中エラー発生時：LMC000051
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000051";

    /**
     * ロジック実行
     *
     * @param receiveOrderDto 受注Dto
     * @return 結果
     */
    DeleteCardOutput execute(ReceiveOrderDto receiveOrderDto);

    /**
     * 実行メソッド
     *
     * @param cardDto      カードDto
     * @param dbUpdateFlag DB更新フラグ true:カード情報変更後会員情報TBLを更新します false:更新しません
     * @return 結果
     */
    DeleteCardOutput execute(CardDto cardDto, boolean dbUpdateFlag);
}
