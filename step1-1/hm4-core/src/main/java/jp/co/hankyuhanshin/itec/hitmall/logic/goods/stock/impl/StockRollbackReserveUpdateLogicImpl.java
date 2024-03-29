/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRollbackReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 在庫情報注文在庫戻しロジック実装クラス
 *
 * @author negishi
 * @version $Revision: 1.4 $
 */
@Component
public class StockRollbackReserveUpdateLogicImpl extends AbstractShopLogic implements StockRollbackReserveUpdateLogic {

    /**
     * 在庫情報Dao
     */
    private final StockDao stockDao;

    @Autowired
    public StockRollbackReserveUpdateLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * ロジック実行
     *
     * @param orderSeq           受注Seq
     * @param orderVersionNo     受注履歴連番
     * @param orderConsecutiveNo 注文連番
     * @return 更新件数
     */
    @Override
    public int execute(Integer orderSeq, Integer orderVersionNo, Integer orderConsecutiveNo) {
        // パラメータチェック
        checkParameter(orderSeq, orderVersionNo, orderConsecutiveNo);

        // 在庫情報注文在庫戻し処理実行
        return stockDao.updateStockRollbackByOrderConsecutiveNo(orderSeq, orderVersionNo, orderConsecutiveNo);

    }

    /**
     * パラメータチェック
     *
     * @param orderSeq           受注Seq
     * @param orderVersionNo     受注履歴連番
     * @param orderConsecutiveNo 注文連番
     */
    protected void checkParameter(Integer orderSeq, Integer orderVersionNo, Integer orderConsecutiveNo) {
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderVersionNo", orderVersionNo);
        ArgumentCheckUtil.assertNotNull("orderConsecutiveNo", orderConsecutiveNo);
    }
}
