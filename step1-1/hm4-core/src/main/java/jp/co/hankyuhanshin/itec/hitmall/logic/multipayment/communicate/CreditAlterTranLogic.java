/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.AlterTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;

import java.util.List;

/**
 * クレジット変更通信ロジック
 *
 * @author yt13605
 */
public interface CreditAlterTranLogic {

    /**
     * 通信中に例外発生
     */
    public static final String ALTERTRAN_COM_ERR_MSG_ID = "LMC000021";

    /**
     * JOBCODEに誤り
     */
    public static final String ALTERTRAN_JOBCD_ERR_MSG_ID = "LMC000022";

    /**
     * 取引取消実行<br />
     * VOID・RETURN・RETURNX　の何れかに更新
     *
     * @param mulPayBillEntity 最新マルチペイメント請求
     * @return 変更出力パラメータ
     */
    AlterTranOutput doCancelTran(MulPayBillEntity mulPayBillEntity);

    /**
     * 取引再開実行<br />
     * 取消状態の取引を　AUTH・CAPTURE・SALES　の何れかに更新し<br />
     * 指定した受注金額で取引を再開する
     *
     * @param dto 修正受注情報
     * @return 変更出力パラメータ
     */
    AlterTranOutput doReTran(ReceiveOrderDto dto);

    /**
     * 売上実行<br />
     * 状態　AUTH　の取引を　SALES　へ更新
     *
     * @param mulPayBillEntity 最新マルチペイメント請求
     * @return 変更出力パラメータ
     */
    AlterTranOutput doSalesFixTran(MulPayBillEntity mulPayBillEntity);

    /**
     * 変更通信結果チェック<br />
     * 変更出力パラメータにエラーリストが設定されているかをチェックする
     *
     * @param output 変更出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    List<CheckMessageDto> checkOutput(AlterTranOutput output);
}
