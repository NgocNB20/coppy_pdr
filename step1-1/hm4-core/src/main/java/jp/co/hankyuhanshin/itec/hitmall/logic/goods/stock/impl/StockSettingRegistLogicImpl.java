/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockSettingDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockSettingEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSettingRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在庫設定登録<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class StockSettingRegistLogicImpl extends AbstractShopLogic implements StockSettingRegistLogic {

    /**
     * 在庫設定DAO
     */
    private final StockSettingDao stockSettingDao;

    @Autowired
    public StockSettingRegistLogicImpl(StockSettingDao stockSettingDao) {
        this.stockSettingDao = stockSettingDao;
    }

    /**
     * 在庫設定登録<br/>
     *
     * @param goodsGroupSeq          商品グループSEQ
     * @param stockSettingEntityList 在庫設定エンティティリスト
     * @return 処理件数マップ
     */
    @Override
    public Map<String, Integer> execute(Integer goodsGroupSeq, List<StockSettingEntity> stockSettingEntityList) {

        // (1) パラメータチェック
        checkParameter(goodsGroupSeq, stockSettingEntityList);

        // (2) 在庫設定情報リストマップ（KEY:商品SEQ）を作成する
        Map<Integer, StockSettingEntity> masterStockSettingEntityMap = createMasterStockSettingEntityMap(goodsGroupSeq);

        // （更新用）在庫設定エンティティリスト
        List<StockSettingEntity> entityListForUpdate = new ArrayList<>();
        // （登録用）在庫設定エンティティリスト
        List<StockSettingEntity> entityListForRegist = new ArrayList<>();

        // (3) （登録用/更新用）在庫設定エンティティリストの編集
        setEntityList(stockSettingEntityList, masterStockSettingEntityMap, entityListForUpdate, entityListForRegist);

        // 処理件数マップ
        Map<String, Integer> resultMap = new HashMap<>();

        // (4) 在庫設定情報の更新処理
        updateStockSetting(entityListForUpdate, resultMap);

        // (5) 在庫設定情報の登録処理
        registStockSetting(entityListForRegist, resultMap);

        // (6) 戻り値
        return resultMap;
    }

    /**
     * パラメータチェック<br/>
     *
     * @param goodsGroupSeq          商品グループSEQ
     * @param stockSettingEntityList 在庫設定エンティティリスト
     * @param customParams           案件用引数
     */
    protected void checkParameter(Integer goodsGroupSeq,
                                  List<StockSettingEntity> stockSettingEntityList,
                                  Object... customParams) {
        // 商品グループSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);
        // 在庫設定エンティティリストが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("stockSettingEntityList", stockSettingEntityList);
    }

    /**
     * 在庫設定情報リストマップ（KEY:商品SEQ）を作成する<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 在庫設定情報リストマップ
     */
    protected Map<Integer, StockSettingEntity> createMasterStockSettingEntityMap(Integer goodsGroupSeq,
                                                                                 Object... customParams) {
        // 在庫設定情報リスト取得
        List<StockSettingEntity> masterStockSettingEntityList = getMasterStockSettingEntityList(goodsGroupSeq);

        // 在庫設定情報リストマップ（KEY:商品SEQ）を作成する
        Map<Integer, StockSettingEntity> masterStockSettingEntityMap = new HashMap<>();
        for (StockSettingEntity stockSettingEntity : masterStockSettingEntityList) {
            masterStockSettingEntityMap.put(stockSettingEntity.getGoodsSeq(), stockSettingEntity);
        }
        return masterStockSettingEntityMap;
    }

    /**
     * 在庫設定情報リスト取得<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 在庫設定情報リスト
     */
    protected List<StockSettingEntity> getMasterStockSettingEntityList(Integer goodsGroupSeq, Object... customParams) {
        return stockSettingDao.getStockSettingListByGoodsGroupSeq(goodsGroupSeq);
    }

    /**
     * （登録用/更新用）在庫設定エンティティリストの編集<br/>
     *
     * @param stockSettingEntityList      在庫設定エンティティリスト
     * @param masterStockSettingEntityMap （DB）在庫設定エンティティリスト
     * @param entityListForUpdate         （更新用）在庫設定エンティティリスト
     * @param entityListForRegist         （登録用）在庫設定エンティティリスト
     * @param customParams                案件用引数
     */
    protected void setEntityList(List<StockSettingEntity> stockSettingEntityList,
                                 Map<Integer, StockSettingEntity> masterStockSettingEntityMap,
                                 List<StockSettingEntity> entityListForUpdate,
                                 List<StockSettingEntity> entityListForRegist,
                                 Object... customParams) {
        for (StockSettingEntity stockSettingEntity : stockSettingEntityList) {
            // （パラメータ）在庫設定の商品SEQが存在する場合
            if (masterStockSettingEntityMap.get(stockSettingEntity.getGoodsSeq()) != null) {
                // （更新用）リストに追加
                setEntityListForUpdate(masterStockSettingEntityMap, entityListForUpdate, stockSettingEntity);
            }
            // （パラメータ）関連商品の関連商品グループSEQが存在しない場合
            else {
                // （登録用）リストに追加
                setEntityListForRegist(entityListForRegist, stockSettingEntity);
            }
        }
    }

    /**
     * （更新用）リストに追加<br/>
     *
     * @param masterStockSettingEntityMap （DB）在庫設定エンティティリスト
     * @param entityListForUpdate         （更新用）在庫設定エンティティリスト
     * @param stockSettingEntity          在庫設定エンティ
     * @param customParams                案件用引数
     */
    protected void setEntityListForUpdate(Map<Integer, StockSettingEntity> masterStockSettingEntityMap,
                                          List<StockSettingEntity> entityListForUpdate,
                                          StockSettingEntity stockSettingEntity,
                                          Object... customParams) {
        List<String> diffResults = DiffUtil.diff(masterStockSettingEntityMap.get(stockSettingEntity.getGoodsSeq()),
                                                 stockSettingEntity
                                                );

        // 日付関連Utility取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        for (String diffResult : diffResults) {
            // 登録日時・更新日時以外が異なる場合
            if (!diffResult.endsWith(".registTime") && !diffResult.endsWith(".updateTime")) {
                // 更新日時の設定
                stockSettingEntity.setUpdateTime(dateUtility.getCurrentTime());
                // （更新用）リストに追加
                entityListForUpdate.add(stockSettingEntity);
                break;
            }
        }
    }

    /**
     * （登録用）リストに追加<br/>
     *
     * @param entityListForRegist （登録用）在庫設定エンティティリスト
     * @param stockSettingEntity  在庫設定エンティ
     * @param customParams        案件用引数
     */
    protected void setEntityListForRegist(List<StockSettingEntity> entityListForRegist,
                                          StockSettingEntity stockSettingEntity,
                                          Object... customParams) {
        // 登録・更新日時の設定
        Timestamp currentTime = ApplicationContextUtility.getBean(DateUtility.class).getCurrentTime();
        stockSettingEntity.setRegistTime(currentTime);
        stockSettingEntity.setUpdateTime(currentTime);
        // （登録用）リストに追加
        entityListForRegist.add(stockSettingEntity);
    }

    /**
     * 在庫設定情報の更新処理<br/>
     *
     * @param entityListForUpdate （更新用）在庫設定エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void updateStockSetting(List<StockSettingEntity> entityListForUpdate,
                                      Map<String, Integer> resultMap,
                                      Object... customParams) {
        // 在庫設定更新件数
        int stockSettingUpdate = 0;
        for (StockSettingEntity stockSettingEntity : entityListForUpdate) {
            int ret = stockSettingDao.update(stockSettingEntity);
            stockSettingUpdate += ret;
        }
        resultMap.put(STOCK_SETTING_UPDATE, stockSettingUpdate);
    }

    /**
     * 在庫設定情報の登録処理<br/>
     *
     * @param entityListForRegist （登録用）在庫設定エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void registStockSetting(List<StockSettingEntity> entityListForRegist,
                                      Map<String, Integer> resultMap,
                                      Object... customParams) {
        // 在庫設定登録件数
        int stockSettingRegist = 0;
        for (StockSettingEntity stockSettingEntity : entityListForRegist) {
            int ret = stockSettingDao.insert(stockSettingEntity);
            stockSettingRegist += ret;
        }
        resultMap.put(STOCK_SETTING_REGIST, stockSettingRegist);
    }
}
