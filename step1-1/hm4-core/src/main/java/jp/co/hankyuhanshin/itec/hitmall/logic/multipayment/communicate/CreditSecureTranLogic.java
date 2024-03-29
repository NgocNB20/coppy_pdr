/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.SecureTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;

import java.util.List;

/**
 * クレジット認証後決済通信ロジック
 *
 * @author oa13612
 */
public interface CreditSecureTranLogic {

    /**
     * 通信処理中エラー発生時
     */
    String MSGCD_PAYMENT_COM_FAIL = "LMC001001";

    /**
     * 実行メソッド<br />
     *
     * @param md    md
     * @param paRes PaRes
     * @return 認証後決済出力パラメータ
     */
    SecureTranOutput execute(String md, String paRes);

    /**
     * 通信後決済出力用エラーチェック
     *
     * @param output 認証後決済出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    List<CheckMessageDto> checkOutput(SecureTranOutput output);

}
