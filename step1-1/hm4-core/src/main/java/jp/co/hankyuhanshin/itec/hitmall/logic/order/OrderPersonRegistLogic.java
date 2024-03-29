/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;

/**
 * 受注ご注文主登録ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderPersonRegistLogic {

    // LOO0011;

    /**
     * ロジック実行<br/>
     *
     * @param orderPersonEntity 受注ご注文主エンティティ
     * @return 登録件数
     */
    int execute(OrderPersonEntity orderPersonEntity);
}
