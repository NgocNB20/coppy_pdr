/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.EntryExecTranPayEasyOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * Pay-easy取引登録・実行ロジック
 *
 * @author yt13605
 * @author Kawaguchi  (itec)  2010/09/06 チケット #2235 対応
 */
public interface PayeasyEntryExecTranLogic {

    /**
     * 通信処理中エラー発生時
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000051";

    /**
     * GMO制約：注文者氏名桁数
     */
    public static final int GMOCHECK_ORDER_NAME_LIMIT = 20;
    /**
     * GMO制約：注文者氏名カナ桁数
     */
    public static final int GMOCHECK_ORDER_KANA_LIMIT = 20;

    /**
     * エラーメッセージコード：注文者氏名桁数
     */
    public static final String MSGCD_ORDER_NAME_LIMIT_FAIL = "LMC002005";
    /**
     * エラーメッセージコード：注文者氏名カナ桁数
     */
    public static final String MSGCD_ORDER_KANA_LIMIT_FAIL = "LMC002007";

    /**
     * 実行メソッド
     *
     * @param dto 受注DTO
     * @return コンビニ登録・決済出力パラメータ
     */
    EntryExecTranPayEasyOutput execute(ReceiveOrderDto dto);

    /**
     * Pay-easy取引登録決済出力用エラーチェック
     *
     * @param output Pay-easy取引登録決済出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    List<CheckMessageDto> checkPayEasyOutput(EntryExecTranPayEasyOutput output);
}
