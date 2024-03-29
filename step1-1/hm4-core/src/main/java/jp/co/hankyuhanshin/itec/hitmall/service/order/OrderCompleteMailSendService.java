/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * 注文受付完了メール送信サービス
 *
 * @author negishi
 * @version $Revision: 1.4 $
 */
public interface OrderCompleteMailSendService {

    // SOO0019

    /**
     * 受注情報の取得に失敗した。
     */
    public static final String MSGCD_RECEIVEORDERDTO_GET_NULL = "SOO001901";

    /**
     * サービス実行
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @return true..成功 / false..失敗
     */
    boolean execute(Integer orderSeq, Integer orderVersionNo);

    // PDR Migrate Customization from here

    /**
     * 注文完了メールを送信します。
     *
     * @param receiveOrderDtoList 受注DTOリスト
     * @return true..成功 / false..失敗
     */
    public boolean execute(List<ReceiveOrderDto> receiveOrderDtoList);
    // PDR Migrate Customization to here

}
