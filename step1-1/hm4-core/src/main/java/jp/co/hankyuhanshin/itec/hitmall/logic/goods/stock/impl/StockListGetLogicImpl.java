/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 在庫情報リスト取得ロジッククラス
 *
 * @author negishi
 * @version $Revision: 1.4 $
 */
@Component
public class StockListGetLogicImpl extends AbstractShopLogic implements StockListGetLogic {

    /**
     * 在庫情報Dao
     */
    private final StockDao stockDao;

    @Autowired
    public StockListGetLogicImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    /**
     * 実行メソッド
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 在庫情報DTOリスト
     */
    @Override
    public List<StockDto> execute(List<Integer> goodsSeqList) {
        // 在庫情報リスト取得メソッド実行
        return stockDao.getStockList(goodsSeqList);
    }
}
