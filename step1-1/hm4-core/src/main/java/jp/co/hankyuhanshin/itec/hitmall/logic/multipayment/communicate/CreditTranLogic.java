/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 【決済オプション部品（ペイジェント）】<br />
 * クレジットカード用通信処理
 *
 * @author y.kawai
 */
// Paygent Customization from here
public interface CreditTranLogic {

    /**
     * オーソリ要求実行
     * <pre>
     * 与信枠の確保を行う
     * </pre>
     *
     * @param dto 受注情報
     * @param enable3dSecure true=有効にする。false=無効にする。
     * @return レスポンス情報
     */
    ComResultDto doEntryExecTran(ReceiveOrderDto dto, boolean enable3dSecure);

}
// Paygent Customization to here
