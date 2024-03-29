/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockDetailsGetByGoodsSeqLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 在庫詳細情報取得<br/>
 *
 * @author MN7017
 * @version $Revision: 1.1 $
 */
@Component
public class StockDetailsGetByGoodsSeqLogicImpl extends AbstractShopLogic implements StockDetailsGetByGoodsSeqLogic {

    /**
     * 在庫Dao<br/>
     */
    private final StockDao stockDao;

    @Autowired
    public StockDetailsGetByGoodsSeqLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 在庫詳細情報取得処理実行<br/>
     *
     * @param goodsSeq 商品SEQ
     * @return 在庫詳細Dto
     */
    @Override
    public StockDetailsDto execute(Integer goodsSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertGreaterThanZero("goodsSeq", goodsSeq);

        // 詳細情報取得
        return stockDao.getStockDetailsByGoodsSeq(goodsSeq);
    }
}
