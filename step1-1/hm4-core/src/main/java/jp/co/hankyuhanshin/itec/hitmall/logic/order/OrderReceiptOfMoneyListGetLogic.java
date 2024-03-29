/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;

import java.util.List;

/**
 * 受注入金リスト取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderReceiptOfMoneyListGetLogic {

    // LOO0024;

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq                     受注SEQ
     * @param orderReceiptOfMoneyVersionNo 受注入金連番
     * @return 受注入金エンティティリスト
     */
    List<OrderReceiptOfMoneyEntity> execute(Integer orderSeq, Integer orderReceiptOfMoneyVersionNo);
}
