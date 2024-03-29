/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockShipmentUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 在庫情報出荷更新ロジック
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.5 $
 */
@Component
public class StockShipmentUpdateLogicImpl extends AbstractShopLogic implements StockShipmentUpdateLogic {

    /**
     * 在庫Dao
     */
    private final StockDao stockDao;

    @Autowired
    public StockShipmentUpdateLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 実行メソッド
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @param mode                更新モード 0=通常, 1=差分
     * @return 更新件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo, Integer mode) {
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);
        ArgumentCheckUtil.assertNotNull("orderConsecutiveNo", orderConsecutiveNo);

        // 在庫更新処理
        return stockDao.updateStockShipment(orderSeq, orderGoodsVersionNo, orderConsecutiveNo, mode);
    }

}
