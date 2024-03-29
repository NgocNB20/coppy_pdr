/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockResultDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockResultRegistByStockManagementFlagLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 在庫管理フラグ変更の入庫実績を登録<br/>
 * <pre>
 * 入庫数は0固定
 * 商品稼働率分析の棚卸在庫数を計算する際に、
 * 指定された集計日の商品情報が必要となるため、
 * 在庫管理フラグ変更時に入庫実績を登録する。
 * </pre>
 *
 * @author ito
 */
@Component
public class StockResultRegistByStockManagementFlagLogicImpl extends AbstractShopLogic
                implements StockResultRegistByStockManagementFlagLogic {

    /**
     * 入庫実績Dao
     */
    private final StockResultDao stockResultDao;

    @Autowired
    public StockResultRegistByStockManagementFlagLogicImpl(StockResultDao stockResultDao) {
        this.stockResultDao = stockResultDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param goodsEntity 商品エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(GoodsEntity goodsEntity, String administratorName) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("商品エンティティが取得できません。", goodsEntity);

        // (2)入庫実績エンティティセット
        StockResultEntity stockResultEntity = createStockResultEntity(goodsEntity, administratorName);

        // (3)入庫実績情報の登録処理
        return stockResultDao.insertStockSupplementHistory(
                        goodsEntity.getShopSeq(), goodsEntity.getGoodsCode(), stockResultEntity);

    }

    /**
     * 商品エンティティから入庫実績エンティティ作成<br/>
     *
     * @param goodsEntity GoodsEntity
     * @return 入庫実績エンティティ
     */
    protected StockResultEntity createStockResultEntity(GoodsEntity goodsEntity, String administratorName) {
        StockResultEntity stockResultEntity = ApplicationContextUtility.getBean(StockResultEntity.class);
        stockResultEntity.setGoodsSeq(goodsEntity.getGoodsSeq());
        stockResultEntity.setSupplementCount(BigDecimal.ZERO);
        stockResultEntity.setStockManagementFlag(goodsEntity.getStockManagementFlag());

        stockResultEntity.setProcessPersonName(administratorName);

        // 入庫実績SEQ取得
        Integer stockResultSeq = stockResultDao.getStockResultSeqNextVal();
        stockResultEntity.setStockResultSeq(stockResultSeq);

        return stockResultEntity;
    }
}
