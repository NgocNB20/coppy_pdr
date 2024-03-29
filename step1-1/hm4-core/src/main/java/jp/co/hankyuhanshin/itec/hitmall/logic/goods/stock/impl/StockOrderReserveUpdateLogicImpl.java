/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockOrderReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注文確保在庫数更新ロジック
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
@Component
public class StockOrderReserveUpdateLogicImpl extends AbstractShopLogic implements StockOrderReserveUpdateLogic {

    /**
     * StockDao
     */
    private final StockDao stockDao;

    @Autowired
    public StockOrderReserveUpdateLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @param reserveMode         在庫確保モード (0=通常モード, 1=在庫確保モード, 2=在庫戻しモード, 9=出荷後在庫引当)
     * @return 処理件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo, int reserveMode) {

        ArgumentCheckUtil.assertGreaterThanZero("orderSeq", orderSeq);
        ArgumentCheckUtil.assertGreaterThanZero("orderGoodsVersionNo", orderGoodsVersionNo);
        ArgumentCheckUtil.assertGreaterThanZero("orderConsecutiveNo", orderConsecutiveNo);

        if (reserveMode != 9 && reserveMode != StockOrderReserveUpdateLogic.NOT_RESERVE_SHIPPED) {
            // 在庫確保モード：在庫確保、在庫戻し、差分在庫戻し の場合
            return stockDao.updateStockAgainByOrderConsecutiveNo(orderSeq, orderGoodsVersionNo - 1, orderSeq,
                                                                 orderGoodsVersionNo, orderConsecutiveNo, reserveMode
                                                                );
        } else {
            // 在庫確保モード：出荷後の在庫引き当て、出荷後の在庫戻し の場合
            return stockDao.updateShipedStockAgainByOrderConsecutiveNo(
                            orderSeq, orderGoodsVersionNo, orderConsecutiveNo, reserveMode);
        }
    }
}
