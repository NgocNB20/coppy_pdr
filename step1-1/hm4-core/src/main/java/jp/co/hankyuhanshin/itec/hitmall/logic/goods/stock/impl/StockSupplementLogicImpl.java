/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockResultDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSupplementLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入庫登録<br/>
 * 入庫情報を登録する。<br/>
 *
 * @author MN7017
 * @version $Revision: 1.11 $
 */
@Component
public class StockSupplementLogicImpl extends AbstractShopLogic implements StockSupplementLogic {

    /**
     * 入庫実績Dao<br/>
     */
    private final StockResultDao stockResultDao;

    /**
     * 在庫Dao<br/>
     */
    private final StockDao stockDao;

    @Autowired
    public StockSupplementLogicImpl(StockResultDao stockResultDao, StockDao stockDao) {
        this.stockResultDao = stockResultDao;
        this.stockDao = stockDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param stockResultEntity 入庫実績エンティティ
     * @param shopSeq           ショップSEQ
     * @param goodsCode         商品コード
     * @return 登録件数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int execute(StockResultEntity stockResultEntity, int shopSeq, String goodsCode) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("入庫情報が取得できません。", stockResultEntity);
        ArgumentCheckUtil.assertNotNull("ショップSEQが取得できません。", shopSeq);
        ArgumentCheckUtil.assertNotNull("商品コードが取得できません。", goodsCode);

        // (2)在庫情報の入庫登録処理
        int result = stockDao.updateStockSupplement(shopSeq, goodsCode, stockResultEntity.getSupplementCount());
        // 更新件数無しの場合は、０件を返して処理終了する。
        if (result <= 0) {
            return result;
        }

        // (3)入庫実績SEQ取得
        Integer stockResultSeq = stockResultDao.getStockResultSeqNextVal();
        stockResultEntity.setStockResultSeq(stockResultSeq);

        // (4)入庫実績情報の登録処理
        return stockResultDao.insertStockSupplementHistory(shopSeq, goodsCode, stockResultEntity);

    }
}
