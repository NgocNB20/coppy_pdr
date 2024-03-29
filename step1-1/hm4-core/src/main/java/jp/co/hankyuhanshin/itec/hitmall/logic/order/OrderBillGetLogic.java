/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;

/**
 * 受注請求取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderBillGetLogic {

    // LOO0014;

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq           受注SEQ
     * @param orderBillVersionNo 受注請求連番
     * @return 受注請求エンティティ
     */
    OrderBillEntity execute(Integer orderSeq, Integer orderBillVersionNo);
}
