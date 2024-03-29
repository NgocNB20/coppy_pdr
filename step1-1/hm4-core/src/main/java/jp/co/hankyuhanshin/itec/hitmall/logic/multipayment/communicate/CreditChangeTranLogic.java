/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.ChangeTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * クレジット金額変更通信ロジック
 *
 * @author yt13605
 */
public interface CreditChangeTranLogic {

    /**
     * 通信中に例外発生
     */
    public static final String CHANGETRAN_COM_ERR_MSG_ID = "LMC000031";

    /**
     * JOBCODEに誤り
     */
    public static final String CHANGETRAN_JOBCD_ERR_MSG_ID = "LMC000032";

    /**
     * 実行メソッド<br />
     * modifiedOrderPrice へ金額を変更する通信処理を行う
     *
     * @param mulPayBillEntity   最新マルチペイメント請求
     * @param modifiedOrderPrice 修正受注金額
     * @return 金額変更出力パラメータ
     */
    ChangeTranOutput execute(MulPayBillEntity mulPayBillEntity, BigDecimal modifiedOrderPrice);

    /**
     * 金額変更出力用エラーチェック
     *
     * @param output 金額変更出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    List<CheckMessageDto> checkOutput(ChangeTranOutput output);
}
