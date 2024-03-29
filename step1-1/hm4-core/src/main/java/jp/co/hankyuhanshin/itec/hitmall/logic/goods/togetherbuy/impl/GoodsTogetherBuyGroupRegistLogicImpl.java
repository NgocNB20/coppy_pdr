// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsTogetherBuyGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
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
 * よく一緒に購入される商品登録<br/>
 *
 * @author hirata
 */
@Component
public class GoodsTogetherBuyGroupRegistLogicImpl extends AbstractShopLogic
                implements GoodsTogetherBuyGroupRegistLogic {

    /**
     * よく一緒に購入される商品DAO
     */
    private final GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao;

    @Autowired
    public GoodsTogetherBuyGroupRegistLogicImpl(GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao) {
        this.goodsTogetherBuyGroupDao = goodsTogetherBuyGroupDao;
    }

    /**
     * よく一緒に購入される商品登録<br/>
     *
     * @param goodsGroupSeq           商品グループSEQ
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @return 処理件数マップ
     */
    @Override
    public Map<String, Integer> execute(Integer goodsGroupSeq,
                                        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList) {

        // (1) パラメータチェック
        checkParameter(goodsGroupSeq, goodsTogetherBuyGroupEntityList);

        // (2) よく一緒に購入される商品情報リスト取得
        List<GoodsTogetherBuyGroupEntity> masterGoodsTogetherBuyGroupEntityList =
                        getMasterGoodsTogetherBuyGroupEntityList(goodsGroupSeq);

        // よく一緒に購入される商品情報リストマップ（KEY:よく一緒に購入される商品グループSEQ）を作成する
        Map<Integer, GoodsTogetherBuyGroupEntity> masterGoodsRelationEntityMap =
                        createMasterGoodsTogetherBuyGroupEntityMap(masterGoodsTogetherBuyGroupEntityList);

        // （削除用）よく一緒に購入される商品エンティティリスト
        List<GoodsTogetherBuyGroupEntity> entityListForDelete = masterGoodsTogetherBuyGroupEntityList;
        // （更新用）よく一緒に購入される商品エンティティリスト
        List<GoodsTogetherBuyGroupEntity> entityListForUpdate = new ArrayList<>();
        // （登録用）よく一緒に購入される商品エンティティリスト
        List<GoodsTogetherBuyGroupEntity> entityListForRegist = new ArrayList<>();

        // (3) （登録用/更新用/削除用）よく一緒に購入される商品エンティティリストの編集
        setGoodsTogetherBuyGroupEntityList(goodsTogetherBuyGroupEntityList, masterGoodsRelationEntityMap,
                                           entityListForDelete, entityListForUpdate, entityListForRegist
                                          );

        // 処理件数マップ生成
        Map<String, Integer> resultMap = new HashMap<>();

        // (4) よく一緒に購入される商品情報の削除処理
        deleteGoodsTogetherBuyGroup(entityListForDelete, resultMap);

        // (5) よく一緒に購入される商品情報の更新処理
        updateGoodsTogetherBuyGroup(entityListForUpdate, resultMap);

        // (6) よく一緒に購入される商品情報の登録処理
        registGoodsTogetherBuyGroup(entityListForRegist, resultMap);

        // (7) 戻り値
        return resultMap;
    }

    /**
     * よく一緒に購入される商品登録<br/>
     *
     * @param goodsTogetherBuyGroupEntity           よく一緒に購入される商品クラス
     * @return 登録件数
     */
    @Override
    public int execute(GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity) {
        // 登録
        return goodsTogetherBuyGroupDao.insert(goodsTogetherBuyGroupEntity);
    }

    /**
     * パラメータチェック<br/>
     *
     * @param goodsGroupSeq           商品グループSEQ
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @param customParams            案件用引数
     */
    protected void checkParameter(Integer goodsGroupSeq,
                                  List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                  Object... customParams) {
        // 商品グループSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);
        // よく一緒に購入される商品エンティティリストが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsRelationEntityList", goodsTogetherBuyGroupEntityList);

        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            if (!goodsGroupSeq.equals(goodsTogetherBuyGroupEntity.getGoodsGroupSeq())) {
                // 商品グループSEQ不一致エラーを投げる。
                throwMessage(MSGCD_GOODSGROUP_MISMATCH_FAIL);
            }
        }
    }

    /**
     * よく一緒に購入される商品情報リスト取得<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return よく一緒に購入される商品情報リスト
     */
    protected List<GoodsTogetherBuyGroupEntity> getMasterGoodsTogetherBuyGroupEntityList(Integer goodsGroupSeq,
                                                                                         Object... customParams) {
        return goodsTogetherBuyGroupDao.getGoodsTogetherBuyGroupListByGoodsGroupSeq(goodsGroupSeq);
    }

    /**
     * よく一緒に購入される商品情報リストマップ（KEY:よく一緒に購入される商品グループSEQ）を作成<br/>
     *
     * @param goodsTogetherBuyGroupEntityList （DB）よく一緒に購入される商品情報リスト
     * @param customParams                  案件用引数
     * @return よく一緒に購入される商品情報リストマップ
     */
    protected Map<Integer, GoodsTogetherBuyGroupEntity> createMasterGoodsTogetherBuyGroupEntityMap(List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                                                                                   Object... customParams) {
        Map<Integer, GoodsTogetherBuyGroupEntity> masterGoodsTogetherBuyGroupEntityMap = new HashMap<>();
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            masterGoodsTogetherBuyGroupEntityMap.put(
                            goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq(), goodsTogetherBuyGroupEntity);
        }
        return masterGoodsTogetherBuyGroupEntityMap;
    }

    /**
     * （登録用/更新用/削除用）よく一緒に購入される商品エンティティリストの編集<br/>
     *
     * @param goodsTogetherBuyGroupEntityList      よく一緒に購入される商品情報リスト
     * @param masterGoodsTogetherBuyGroupEntityMap （DB）よく一緒に購入される商品情報リスト
     * @param entityListForDelete          （削除用）よく一緒に購入される商品エンティティリスト
     * @param entityListForUpdate          （更新用）よく一緒に購入される商品エンティティリスト
     * @param entityListForRegist          （登録用）よく一緒に購入される商品エンティティリスト
     * @param customParams                 案件用引数
     */
    protected void setGoodsTogetherBuyGroupEntityList(List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                                      Map<Integer, GoodsTogetherBuyGroupEntity> masterGoodsTogetherBuyGroupEntityMap,
                                                      List<GoodsTogetherBuyGroupEntity> entityListForDelete,
                                                      List<GoodsTogetherBuyGroupEntity> entityListForUpdate,
                                                      List<GoodsTogetherBuyGroupEntity> entityListForRegist,
                                                      Object... customParams) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            // （パラメータ）よく一緒に購入される商品のよく一緒に購入される商品グループSEQが存在する場合
            if (!HTypeRegisterMethodType.BATCH.equals(goodsTogetherBuyGroupEntity.getRegistMethod())) {
                if (masterGoodsTogetherBuyGroupEntityMap.get(goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq())
                    != null && HTypeRegisterMethodType.BACK.equals(masterGoodsTogetherBuyGroupEntityMap.get(
                                goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq()))) {
                    // （削除用）リストから削除
                    entityListForDelete.remove(masterGoodsTogetherBuyGroupEntityMap.get(
                                    goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq()));

                    if (!masterGoodsTogetherBuyGroupEntityMap.get(
                                                                             goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq())
                                                             .getOrderDisplay()
                                                             .equals(goodsTogetherBuyGroupEntity.getOrderDisplay())) {
                        // 表示順が異なる場合⇒更新リストに追加
                        // 登録・更新日時の設定
                        Timestamp currentTime = dateUtility.getCurrentTime();
                        goodsTogetherBuyGroupEntity.setUpdateTime(currentTime);
                        goodsTogetherBuyGroupEntity.setRegistTime(masterGoodsTogetherBuyGroupEntityMap.get(
                                        goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq()).getRegistTime());
                        // （更新用）リストに追加
                        entityListForUpdate.add(goodsTogetherBuyGroupEntity);
                    }
                }
                // （パラメータ）よく一緒に購入される商品のよく一緒に購入される商品グループSEQが存在しない場合
                else {
                    // 登録・更新日時の設定
                    Timestamp currentTime = dateUtility.getCurrentTime();
                    goodsTogetherBuyGroupEntity.setRegistTime(currentTime);
                    goodsTogetherBuyGroupEntity.setUpdateTime(currentTime);
                    // （登録用）リストに追加
                    entityListForRegist.add(goodsTogetherBuyGroupEntity);
                }
            }
        }
    }

    /**
     * よく一緒に購入される商品情報の削除処理<br/>
     *
     * @param entityListForDelete （削除用）よく一緒に購入される商品エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void deleteGoodsTogetherBuyGroup(List<GoodsTogetherBuyGroupEntity> entityListForDelete,
                                               Map<String, Integer> resultMap,
                                               Object... customParams) {
        // よく一緒に購入される商品削除件数
        int goodsTogetherBuyGroupDelete = 0;
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : entityListForDelete) {
            if (HTypeRegisterMethodType.BACK.equals(goodsTogetherBuyGroupEntity.getRegistMethod())) {
                int ret = goodsTogetherBuyGroupDao.delete(goodsTogetherBuyGroupEntity);
                goodsTogetherBuyGroupDelete += ret;
            }
        }
        resultMap.put(GOODS_TOGETHER_BUY_GROUP_DELETE, goodsTogetherBuyGroupDelete);
    }

    /**
     * よく一緒に購入される商品情報の更新処理<br/>
     *
     * @param entityListForUpdate （更新用）よく一緒に購入される商品エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void updateGoodsTogetherBuyGroup(List<GoodsTogetherBuyGroupEntity> entityListForUpdate,
                                               Map<String, Integer> resultMap,
                                               Object... customParams) {
        // よく一緒に購入される商品更新件数
        int goodsTogetherBuyGroupUpdate = 0;
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : entityListForUpdate) {
            int ret = goodsTogetherBuyGroupDao.update(goodsTogetherBuyGroupEntity);
            goodsTogetherBuyGroupUpdate += ret;
        }
        resultMap.put(GOODS_TOGETHER_BUY_GROUP_UPDATE, goodsTogetherBuyGroupUpdate);
    }

    /**
     * よく一緒に購入される商品情報の登録処理<br/>
     *
     * @param entityListForRegist （登録用）よく一緒に購入される商品エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void registGoodsTogetherBuyGroup(List<GoodsTogetherBuyGroupEntity> entityListForRegist,
                                               Map<String, Integer> resultMap,
                                               Object... customParams) {
        // よく一緒に購入される商品登録件数
        int goodsTogetherBuyGroupRegist = 0;
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : entityListForRegist) {
            goodsTogetherBuyGroupEntity.setRegistMethod(HTypeRegisterMethodType.BACK);
            int ret = goodsTogetherBuyGroupDao.insert(goodsTogetherBuyGroupEntity);
            goodsTogetherBuyGroupRegist += ret;
        }
        resultMap.put(GOODS_TOGETHER_BUY_GROUP_REGIST, goodsTogetherBuyGroupRegist);
    }
}
// 2023-renew No21 to here
