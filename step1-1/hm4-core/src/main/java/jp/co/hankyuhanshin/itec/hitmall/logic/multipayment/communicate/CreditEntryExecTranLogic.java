/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.EntryExecTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * クレジット取引登録・決済通信ロジック
 *
 * @author yt13605
 */
public interface CreditEntryExecTranLogic {

    /**
     * 通信処理中エラー発生時
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000011";

    /**
     * HTTP_USER_AGENT
     */
    // public static final String HTTP_ACCEPT = "HTTP_ACCEPT";
    public static final String HTTP_ACCEPT = "Accept";

    /**
     * HTTP_USER_AGENT
     */
    // public static final String HTTP_USER_AGENT = "HTTP_USER_AGENT";
    public static final String HTTP_USER_AGENT = "User-Agent";

    /**
     * 実行メソッド<br />
     * enable3dSecure が有効の場合は、決済方法のｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞの設定を反映して通信を行う。<br />
     * 管理画面からの新規受注はfalseを設定して下さい。
     *
     * @param dto            受注DTO
     * @param enable3dSecure true=有効にする。false=無効にする。
     * @return 取引登録・決済出力パラメータ
     */
    EntryExecTranOutput execute(ReceiveOrderDto dto, boolean enable3dSecure);

    /**
     * 実行メソッド<br />
     *
     * @param cardDto カード登録情報
     * @return 有効性チェック出力パラメータ
     */
    EntryExecTranOutput executeCheck(CardDto cardDto);

    /**
     * 取引登録・決済出力用エラーチェック
     *
     * @param output 取引登録・決済出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    List<CheckMessageDto> checkOutput(EntryExecTranOutput output);
}
