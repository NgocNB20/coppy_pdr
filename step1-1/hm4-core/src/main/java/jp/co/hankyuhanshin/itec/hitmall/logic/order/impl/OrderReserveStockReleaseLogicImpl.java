/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockReleaseLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 在庫解放ロジック
 *
 * @author yt13605
 */
@Component
public class OrderReserveStockReleaseLogicImpl extends AbstractShopLogic implements OrderReserveStockReleaseLogic {

    /**
     * 在庫Dao
     */
    private final StockDao stockDao;

    /**
     * 受注商品Dao
     */
    private final OrderGoodsDao orderGoodsDao;

    @Autowired
    public OrderReserveStockReleaseLogicImpl(StockDao stockDao, OrderGoodsDao orderGoodsDao) {
        this.stockDao = stockDao;
        this.orderGoodsDao = orderGoodsDao;
    }

    /**
     * 実行メソッド
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void execute(Integer orderSeq, Integer orderGoodsVersionNo) {

        // 確保在庫戻し処理
        stockDao.updateStockRollback(orderSeq, orderGoodsVersionNo);

        // 受注商品リスト削除処理
        orderGoodsDao.deleteOrderGoodsList(orderSeq, orderGoodsVersionNo);
    }

}
