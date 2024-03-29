/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;

import java.util.List;

/**
 * 受注配送取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderDeliveryGetLogic {

    // LOO0009

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @param orderConsecutiveNo     注文連番
     * @return 受注配送エンティティ
     */
    OrderDeliveryEntity execute(Integer orderSeq, Integer orderDeliveryVersionNo, Integer orderConsecutiveNo);

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return 受注配送エンティティリスト
     */
    List<OrderDeliveryEntity> execute(Integer orderSeq, Integer orderDeliveryVersionNo);

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return 受注配送エンティティリスト
     */
    List<OrderDeliveryEntity> getOrderDeliveryListForUpdate(Integer orderSeq, Integer orderDeliveryVersionNo);

}
