/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.SearchCardOutput;

/**
 * カード参照通信Logicクラス
 *
 * @author s_nose
 */
public interface SearchCardLogic {

    /**
     * 通信処理中エラー発生時：LMC000051
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000051";

    /**
     * 実行メソッド<br />
     *
     * @param paymentMemberId 決済代行会員ID
     * @return カード参照出力パラメータ
     */
    SearchCardOutput execute(String paymentMemberId);
}
