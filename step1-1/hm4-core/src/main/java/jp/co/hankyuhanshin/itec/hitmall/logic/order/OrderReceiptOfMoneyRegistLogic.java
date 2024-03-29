/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;

/**
 * 受注入金登録ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderReceiptOfMoneyRegistLogic {

    // LOO0015;

    /**
     * ロジック実行<br/>
     *
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 登録件数
     */
    int execute(OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity);
}
