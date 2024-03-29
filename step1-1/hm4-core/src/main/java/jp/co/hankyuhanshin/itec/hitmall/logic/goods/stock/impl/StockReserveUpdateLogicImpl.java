/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 在庫情報注文確保更新ロジック実装クラス。
 *
 * @author negishi
 * @version $Revision: 1.3 $
 */
@Component
public class StockReserveUpdateLogicImpl extends AbstractShopLogic implements StockReserveUpdateLogic {

    /**
     * 在庫Dao
     */
    private final StockDao stockDao;

    @Autowired
    public StockReserveUpdateLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 実行処理。
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 更新件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderGoodsVersionNo) {
        // パラメータチェック
        checkParameter(orderSeq, orderGoodsVersionNo);
        // 在庫更新処理
        return stockDao.updateStock(orderSeq, orderGoodsVersionNo);
    }

    /**
     * パラメータチェック
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     */
    protected void checkParameter(Integer orderSeq, Integer orderVersionNo) {
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderVersionNo", orderVersionNo);
    }
}
