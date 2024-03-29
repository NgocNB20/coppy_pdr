/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;

/**
 * 受注配送登録ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderDeliveryRegistLogic {

    // LOO0012

    /**
     * ロジック実行<br/>
     *
     * @param orderDeliveryEntity 受注配送エンティティ
     * @return 登録件数
     */
    int execute(OrderDeliveryEntity orderDeliveryEntity);
}
