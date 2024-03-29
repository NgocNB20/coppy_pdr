/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.output.SaveMemberOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 会員登録通信ロジック
 *
 * @author s_nose
 */
public interface SaveMemberLogic {

    /**
     * 通信処理中エラー発生時：LMC000051
     */
    public static final String MSGCD_PAYMENT_COM_FAIL = "LMC000051";

    /**
     * 実行メソッド<br />
     *
     * @param dto 受注DTO
     * @return 会員登録出力パラメータ
     */
    SaveMemberOutput execute(ReceiveOrderDto dto);

    /**
     * 実行メソッド<br />
     *
     * @param cardDto カードDto
     * @return 会員登録出力パラメータ
     */
    SaveMemberOutput execute(CardDto cardDto);

}
