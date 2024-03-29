/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;

/**
 * 受注請求登録ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderBillRegistLogic {

    // LOO0014;

    /**
     * ロジック実行<br/>
     *
     * @param orderBillEntity 受注請求エンティティ
     * @return 登録件数
     */
    int execute(OrderBillEntity orderBillEntity);
}
