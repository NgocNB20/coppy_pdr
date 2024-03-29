/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRollBackShipmentUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 出荷済み在庫戻しロジック
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
@Component
public class StockRollBackShipmentUpdateLogicImpl extends AbstractShopLogic
                implements StockRollBackShipmentUpdateLogic {

    /**
     * 在庫DAO
     */
    private final StockDao stockDao;

    @Autowired
    public StockRollBackShipmentUpdateLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 実行メソッド
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 更新件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo) {
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);
        ArgumentCheckUtil.assertNotNull("orderConsecutiveNo", orderConsecutiveNo);
        // 在庫更新処理
        return stockDao.updateStockShipmentRollback(orderSeq, orderGoodsVersionNo, orderConsecutiveNo);
    }

}
