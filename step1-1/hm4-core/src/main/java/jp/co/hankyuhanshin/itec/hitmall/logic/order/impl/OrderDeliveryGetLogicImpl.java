/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.delivery.OrderDeliveryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注配送取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderDeliveryGetLogicImpl extends AbstractShopLogic implements OrderDeliveryGetLogic {

    /**
     * 受注配送Dao
     */
    private final OrderDeliveryDao orderDeliveryDao;

    @Autowired
    public OrderDeliveryGetLogicImpl(OrderDeliveryDao orderDeliveryDao) {
        this.orderDeliveryDao = orderDeliveryDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @param orderConsecutiveNo     注文連番
     * @return 受注配送エンティティ
     */
    @Override
    public OrderDeliveryEntity execute(Integer orderSeq, Integer orderDeliveryVersionNo, Integer orderConsecutiveNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderDeliveryVersionNo", orderDeliveryVersionNo);
        ArgumentCheckUtil.assertNotNull("orderConsecutiveNo", orderConsecutiveNo);

        // 受注配送の検索
        return orderDeliveryDao.getEntity(orderSeq, orderDeliveryVersionNo, orderConsecutiveNo);
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return 受注配送エンティティリスト
     */
    @Override
    public List<OrderDeliveryEntity> execute(Integer orderSeq, Integer orderDeliveryVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderDeliveryVersionNo", orderDeliveryVersionNo);

        // 受注配送の検索
        return orderDeliveryDao.getEntityList(orderSeq, orderDeliveryVersionNo);
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return 受注配送エンティティリスト
     */
    @Override
    public List<OrderDeliveryEntity> getOrderDeliveryListForUpdate(Integer orderSeq, Integer orderDeliveryVersionNo) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderDeliveryVersionNo", orderDeliveryVersionNo);

        // 受注配送の検索
        return orderDeliveryDao.getOrderDeliveryListForUpdate(orderSeq, orderDeliveryVersionNo);
    }
}
