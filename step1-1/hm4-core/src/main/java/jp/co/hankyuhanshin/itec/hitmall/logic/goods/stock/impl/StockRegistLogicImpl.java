/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockResultDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 在庫登録<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class StockRegistLogicImpl extends AbstractShopLogic implements StockRegistLogic {

    /**
     * 在庫DAO
     */
    private final StockDao stockDao;

    /**
     * 入庫実績Dao
     */
    private final StockResultDao stockResultDao;

    @Autowired
    public StockRegistLogicImpl(StockDao stockDao, StockResultDao stockResultDao) {
        this.stockDao = stockDao;
        this.stockResultDao = stockResultDao;
    }

    /**
     * 在庫登録<br/>
     *
     * @param stockEntityList 在庫エンティティリスト
     * @return 処理件数マップ
     */
    @Override
    public int execute(List<StockEntity> stockEntityList, String administratorName) {

        // (1) パラメータチェック
        // 在庫エンティティリストが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("stockEntityList", stockEntityList);
        // 在庫登録件数
        int stockRegist = 0;

        // (2) 在庫設定情報の登録処理
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        for (StockEntity stockEntity : stockEntityList) {
            // 登録・更新日時の設定
            Timestamp currentTime = dateUtility.getCurrentTime();
            stockEntity.setRegistTime(currentTime);
            stockEntity.setUpdateTime(currentTime);
            int ret = stockDao.insert(stockEntity);
            stockRegist += ret;

            // 実在庫数が0以外で登録した場合、入庫実績登録を実施する
            if (stockEntity.getRealStock().compareTo(BigDecimal.ZERO) != 0) {

                stockResultDao.insert(createStockResultEntity(stockEntity, currentTime, administratorName));
            }

        }

        // (3) 戻り値
        // 登録した件数を返す。
        return stockRegist;
    }

    /**
     * 入庫実績エンティティを作成<br/>
     *
     * @param stockEntity 在庫エンティティ
     * @param currentTime
     * @return stockResultEntity　入庫実績エンティティ
     */
    protected StockResultEntity createStockResultEntity(StockEntity stockEntity,
                                                        Timestamp currentTime,
                                                        String administratorName) {

        StockResultEntity stockResultEntity = ApplicationContextUtility.getBean(StockResultEntity.class);

        // 入庫実績エンティティへ設定する。
        // 商品SEQ
        stockResultEntity.setGoodsSeq(stockEntity.getGoodsSeq());
        // 入庫数（新規登録用の為、実在庫数と等しい）
        stockResultEntity.setSupplementCount(stockEntity.getRealStock());
        // 実在庫数（新規登録用の為、入庫数と等しい）
        stockResultEntity.setRealStock(stockEntity.getRealStock());

        stockResultEntity.setSupplementTime(currentTime);
        stockResultEntity.setRegistTime(currentTime);
        stockResultEntity.setUpdateTime(currentTime);

        stockResultEntity.setProcessPersonName(administratorName);

        Integer stockResultSeq = stockResultDao.getStockResultSeqNextVal();
        stockResultEntity.setStockResultSeq(stockResultSeq);

        return stockResultEntity;
    }
}
