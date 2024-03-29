/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.SaveCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;

/**
 * カード登録通信Logicクラス
 *
 * @author s_nose
 */
public interface SaveCardLogic {

    /**
     * 通信処理中エラー発生時：LMC000051
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000051";

    /**
     * 実行メソッド<br />
     *
     * @param cardDto カードDto
     * @return カード登録出力パラメータ
     */
    SaveCardOutput execute(CardDto cardDto);
}
