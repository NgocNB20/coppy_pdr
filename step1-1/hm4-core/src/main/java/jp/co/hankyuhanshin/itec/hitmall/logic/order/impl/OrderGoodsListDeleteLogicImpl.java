/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注商品リスト削除ロジック<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
@Component
public class OrderGoodsListDeleteLogicImpl extends AbstractShopLogic implements OrderGoodsListDeleteLogic {

    /**
     * 受注商品DAO
     */
    private final OrderGoodsDao orderGoodsDao;

    @Autowired
    public OrderGoodsListDeleteLogicImpl(OrderGoodsDao orderGoodsDao) {
        this.orderGoodsDao = orderGoodsDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 削除件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderGoodsVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);

        return orderGoodsDao.deleteOrderGoodsList(orderSeq, orderGoodsVersionNo);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 削除件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);
        ArgumentCheckUtil.assertNotNull("orderConsecutiveNo", orderConsecutiveNo);

        return orderGoodsDao.deleteOrderGoodsListByOrderConsecutiveNo(
                        orderSeq, orderGoodsVersionNo, orderConsecutiveNo);
    }
}
