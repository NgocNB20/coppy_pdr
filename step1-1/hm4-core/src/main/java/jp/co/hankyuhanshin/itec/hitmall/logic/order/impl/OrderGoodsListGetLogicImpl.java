/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注商品リスト取得ロジック<br/>
 *
 * @author ueshima
 */
@Component
public class OrderGoodsListGetLogicImpl extends AbstractShopLogic implements OrderGoodsListGetLogic {

    /**
     * 受注商品Dao
     */
    private final OrderGoodsDao orderGoodsDao;

    @Autowired
    public OrderGoodsListGetLogicImpl(OrderGoodsDao orderGoodsDao) {
        this.orderGoodsDao = orderGoodsDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 受注商品エンティティリスト
     */
    @Override
    public List<OrderGoodsEntity> execute(Integer orderSeq, Integer orderGoodsVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);

        // 受注商品検索
        return orderGoodsDao.getOrderGoodsList(orderSeq, orderGoodsVersionNo);
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 受注商品エンティティリスト
     */
    @Override
    public List<OrderGoodsEntity> execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);
        ArgumentCheckUtil.assertNotNull("orderConsecutiveNo", orderConsecutiveNo);

        // 受注商品検索
        return orderGoodsDao.getDtoListEachOfDelivery(orderSeq, orderGoodsVersionNo, orderConsecutiveNo);
    }

}
