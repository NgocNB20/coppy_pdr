/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockResultRegistByStockManagementFlagLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品登録<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsRegistLogicImpl extends AbstractShopLogic implements GoodsRegistLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRegistLogicImpl.class);

    // PDR Migrate Customization from here
    /**
     * 心意気価格商品番号末尾文字
     */
    private static final String emotionPriceCode = "kp";
    // PDR Migrate Customization to here

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    /**
     * 在庫管理フラグ変更入庫実績登録Logic
     */
    private final StockResultRegistByStockManagementFlagLogic stockResultRegistByStockManagementFlagLogic;

    @Autowired
    public GoodsRegistLogicImpl(GoodsDao goodsDao,
                                StockResultRegistByStockManagementFlagLogic stockResultRegistByStockManagementFlagLogic) {
        this.goodsDao = goodsDao;
        this.stockResultRegistByStockManagementFlagLogic = stockResultRegistByStockManagementFlagLogic;
    }

    /**
     * 商品登録<br/>
     *
     * @param goodsGroupSeq   商品グループSEQ
     * @param goodsEntityList 商品エンティティリスト
     * @return 処理件数マップ
     */
    @Override
    public Map<String, Integer> execute(Integer goodsGroupSeq,
                                        List<GoodsEntity> goodsEntityList,
                                        String administratorName) {

        // (1) パラメータチェック
        checkParameter(goodsGroupSeq, goodsEntityList);

        // (2) 商品情報リストマップ（KEY:商品SEQ）を作成する
        Map<Integer, GoodsEntity> masterGoodsEntityMap = createMasterGoodsEntityMap(goodsGroupSeq);

        // （更新用）商品エンティティリスト
        List<GoodsEntity> entityListForUpdate = new ArrayList<>();
        // （登録用）商品エンティティリスト
        List<GoodsEntity> entityListForRegist = new ArrayList<>();

        // (3) （登録用/更新用）商品エンティティリストの編集
        setEntityList(goodsEntityList, masterGoodsEntityMap, entityListForUpdate, entityListForRegist);

        // 処理件数マップ
        Map<String, Integer> resultMap = new HashMap<>();

        // (4) 商品情報の更新処理
        updateGoodsEntity(entityListForUpdate, resultMap, administratorName);

        // (5) 商品情報の登録処理
        registGoodsEntity(entityListForRegist, resultMap);

        // (6) 戻り値
        return resultMap;
    }

    /**
     * パラメータチェック<br/>
     *
     * @param goodsGroupSeq   商品グループSEQ
     * @param goodsEntityList 商品エンティティリスト
     * @param customParams    案件用引数
     */
    protected void checkParameter(Integer goodsGroupSeq, List<GoodsEntity> goodsEntityList, Object... customParams) {
        // 商品グループSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);
        // 商品エンティティリストが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsEntityList", goodsEntityList);
        // 商品グループSEQが不一致でないかをチェック
        for (GoodsEntity goodsEntity : goodsEntityList) {
            if (!goodsGroupSeq.equals(goodsEntity.getGoodsGroupSeq())) {
                // 商品グループSEQ不一致エラーを投げる。
                throwMessage(MSGCD_GOODSGROUP_MISMATCH_FAIL);
            }
        }
    }

    /**
     * 商品情報リストマップ（KEY:商品SEQ）を作成する<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 商品情報リストマップ
     */
    protected Map<Integer, GoodsEntity> createMasterGoodsEntityMap(Integer goodsGroupSeq, Object... customParams) {
        // 商品情報リスト取得
        List<GoodsEntity> masterGoodsEntityList = getMasterGoodsEntityList(goodsGroupSeq);

        // 商品情報リストマップ（KEY:商品SEQ）を作成する
        Map<Integer, GoodsEntity> masterGoodsEntityMap = new HashMap<>();
        for (GoodsEntity goodsEntity : masterGoodsEntityList) {
            masterGoodsEntityMap.put(goodsEntity.getGoodsSeq(), goodsEntity);
        }
        return masterGoodsEntityMap;
    }

    /**
     * 商品情報リスト取得<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 商品情報リスト
     */
    protected List<GoodsEntity> getMasterGoodsEntityList(Integer goodsGroupSeq, Object... customParams) {
        return goodsDao.getGoodsListByGoodsGroupSeq(goodsGroupSeq);
    }

    /**
     * （登録用/更新用）商品エンティティリストの編集<br/>
     *
     * @param goodsEntityList      商品エンティティリスト
     * @param masterGoodsEntityMap （DB）商品エンティティリスト
     * @param entityListForUpdate  （更新用）商品エンティティリスト
     * @param entityListForRegist  （登録用）商品エンティティリスト
     * @param customParams         案件用引数
     */
    protected void setEntityList(List<GoodsEntity> goodsEntityList,
                                 Map<Integer, GoodsEntity> masterGoodsEntityMap,
                                 List<GoodsEntity> entityListForUpdate,
                                 List<GoodsEntity> entityListForRegist,
                                 Object... customParams) {
        for (GoodsEntity goodsEntity : goodsEntityList) {
            // （パラメータ）商品の商品SEQが存在する場合
            if (masterGoodsEntityMap.get(goodsEntity.getGoodsSeq()) != null) {
                // （更新用）リストに追加
                setEntityListForUpdate(masterGoodsEntityMap, entityListForUpdate, goodsEntity);
            }
            // （パラメータ）関連商品の関連商品グループSEQが存在しない場合
            else {
                // （登録用）リストに追加
                setEntityListForRegist(entityListForRegist, goodsEntity);
            }
        }
    }

    /**
     * （更新用）リストに追加<br/>
     *
     * @param masterGoodsEntityMap （DB）商品エンティティリスト
     * @param entityListForUpdate  （更新用）商品エンティティリスト
     * @param goodsEntity          商品エンティティ
     * @param customParams         案件用引数
     */
    protected void setEntityListForUpdate(Map<Integer, GoodsEntity> masterGoodsEntityMap,
                                          List<GoodsEntity> entityListForUpdate,
                                          GoodsEntity goodsEntity,
                                          Object... customParams) {
        List<String> diffResult = DiffUtil.diff(masterGoodsEntityMap.get(goodsEntity.getGoodsSeq()), goodsEntity);

        boolean updateFlg = false;
        for (int i = 0; !updateFlg && i < diffResult.size(); i++) {
            // 登録日時・更新日時以外が異なる場合
            if (!diffResult.get(i).endsWith(".registTime") && !diffResult.get(i).endsWith(".updateTime")) {
                // 公開状態が削除の場合、商品番号を再利用可能とするため、商品番号を書き換える
                if (goodsEntity.getSaleStatusPC().equals(HTypeGoodsSaleStatus.DELETED)) {
                    // 書き換えた商品番号が被らないよう、画面から入力不可である「_」+商品テーブル内で一意の「商品SEQ」を設定する
                    goodsEntity.setGoodsCode("_" + goodsEntity.getGoodsSeq());
                }
                // 更新日時の設定
                Timestamp currentTime = ApplicationContextUtility.getBean(DateUtility.class).getCurrentTime();
                goodsEntity.setUpdateTime(currentTime);
                entityListForUpdate.add(goodsEntity);
                updateFlg = true;

            }
        }
    }

    /**
     * （登録用）リストに追加<br/>
     *
     * @param entityListForRegist （登録用）商品エンティティリスト
     * @param goodsEntity         商品エンティティ
     * @param customParams        案件用引数
     */
    protected void setEntityListForRegist(List<GoodsEntity> entityListForRegist,
                                          GoodsEntity goodsEntity,
                                          Object... customParams) {
        // 登録・更新日時の設定
        Timestamp currentTime = ApplicationContextUtility.getBean(DateUtility.class).getCurrentTime();
        goodsEntity.setRegistTime(currentTime);
        goodsEntity.setUpdateTime(currentTime);
        // （登録用）リストに追加
        entityListForRegist.add(goodsEntity);
    }

    /**
     * 商品情報の更新処理<br/>
     *
     * @param entityListForUpdate （更新用）商品エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void updateGoodsEntity(List<GoodsEntity> entityListForUpdate,
                                     Map<String, Integer> resultMap,
                                     String administratorName,
                                     Object... customParams) {
        // 商品更新件数
        int goodsUpdate = 0;
        for (GoodsEntity goodsEntity : entityListForUpdate) {
            // 更新前の商品情報を取得する
            GoodsEntity tmpGoodsEntity = goodsDao.getEntity(goodsEntity.getGoodsSeq());

            if (HTypeGoodsSaleStatus.DELETED == goodsEntity.getSaleStatusPC()) {
                goodsUpdate += goodsDao.update(getGoodsEntityByOverwritedRequiredItem(goodsEntity));
            } else {
                goodsUpdate += goodsDao.update(goodsEntity);
            }

            // PDR Migrate Customization from here
            // 心意気価格保持区分が通常商品（心意気あり）の場合
            if (goodsEntity.getEmotionPriceType() == HTypeEmotionPriceType.NORMAL_WITH_EMOTION) {
                // 紐づく心意気商品が存在するか確認
                Integer shopSeq = 1001;
                GoodsEntity emotionGoodsEntity =
                                goodsDao.getGoodsByCode(shopSeq, goodsEntity.getGoodsCode() + emotionPriceCode);

                // 紐づく心意気商品が存在する場合、心意気元商品の情報で心意気商品を更新
                if (emotionGoodsEntity != null) {
                    GoodsEntity updateGoodsEntity = setGoodsEntityValue(emotionGoodsEntity, goodsEntity);
                    goodsDao.update(updateGoodsEntity);
                }
            }
            // PDR Migrate Customization to here
            if (!tmpGoodsEntity.getStockManagementFlag().equals(goodsEntity.getStockManagementFlag())
                && !goodsEntity.getSaleStatusPC().equals(HTypeGoodsSaleStatus.DELETED)) {
                // 在庫管理フラグが「あり⇔なし」に更新され、かつ更新後の商品販売状態が削除以外の場合、在庫管理フラグ変更の入庫実績を登録
                stockResultRegistByStockManagementFlagLogic.execute(goodsEntity, administratorName);
            }
        }
        resultMap.put(GOODS_UPDATE, goodsUpdate);
    }

    /**
     * 商品情報の登録処理<br/>
     *
     * @param entityListForRegist （登録用）商品エンティティリスト
     * @param resultMap           処理件数マップ
     * @param customParams        案件用引数
     */
    protected void registGoodsEntity(List<GoodsEntity> entityListForRegist,
                                     Map<String, Integer> resultMap,
                                     Object... customParams) {
        // 商品登録件数
        int goodsRegist = 0;
        for (GoodsEntity goodsEntity : entityListForRegist) {
            goodsRegist += goodsDao.insert(goodsEntity);
            // 表示順が設定されていない場合(CSVアップロード時)用に表示順をアップデートする
            if (goodsEntity.getOrderDisplay() == null) {
                goodsDao.updateOrderDisplay(goodsEntity.getGoodsGroupSeq(), goodsEntity.getGoodsSeq());
            }
        }
        resultMap.put(GOODS_REGIST, goodsRegist);
    }

    /**
     * パラメータのエンティティを必須項目をDBの値で上書きして返却する
     * <pre>
     * 本来は新たにエンティティを取得して、販売状態を削除＆更新日時を現在日時で設定し直したエンティティを返却した方が
     * 良いのだが、他への影響が調査し切れないため、エラーを回避するためだけの対応としている
     * </pre>
     *
     * @param goodsEntity 画面で入力した値
     * @return 商品管理：商品登録更新：規格設定画面（admin/goods/registupdate/unit.html）で入力可能な必須項目（最大注文可能数・価格・仕入れ値）の値をDB値で更新したエンティティ
     */
    private GoodsEntity getGoodsEntityByOverwritedRequiredItem(GoodsEntity goodsEntity) {
        GoodsEntity selected = goodsDao.getEntity(goodsEntity.getGoodsSeq());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("GoodsEntity GoodsCode[%s] GoodsSeq[%s]", goodsEntity.getGoodsCode(),
                                       goodsEntity.getGoodsSeq()
                                      ));
            LOGGER.debug(String.format("更新前GoodsEntity PurchasedMax[%s]", goodsEntity.getPurchasedMax()));
            LOGGER.debug(String.format("更新後GoodsEntity PurchasedMax[%s]", selected.getPurchasedMax()));
        }

        goodsEntity.setPurchasedMax(selected.getPurchasedMax());

        return goodsEntity;
    }
    // PDR Migrate Customization from here

    /**
     * 心意気商品情報に心意気元商品の情報をセット<br/>
     *
     * @param emotionGoodsEntity 商品エンティティ（心意気商品）
     * @param goodsEntity        商品エンティティ（心意気元商品）
     * @return　updateGoodsEntity　商品エンティティ（心意気商品更新用）
     */
    private GoodsEntity setGoodsEntityValue(GoodsEntity emotionGoodsEntity, GoodsEntity goodsEntity) {
        GoodsEntity updateGoodsEntity = new GoodsEntity();
        updateGoodsEntity = CopyUtil.deepCopy(goodsEntity);

        // 商品SEQ
        updateGoodsEntity.setGoodsSeq(emotionGoodsEntity.getGoodsSeq());

        // 心意気価格保持区分
        updateGoodsEntity.setEmotionPriceType(emotionGoodsEntity.getEmotionPriceType());

        if (goodsEntity.getGoodsCode() != null && !goodsEntity.getGoodsCode().endsWith(emotionPriceCode)) {
            // 通常商品の商品番号末尾に「kp」を追加
            updateGoodsEntity.setGoodsCode(goodsEntity.getGoodsCode() + emotionPriceCode);
        }
        // カタログ番号にnullを設定
        updateGoodsEntity.setCatalogCode(null);
        // 販売状態PCにnullを設定
        updateGoodsEntity.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
        // 販売開始日時PCにnullを設定
        updateGoodsEntity.setSaleStartTimePC(null);
        // 販売終了日時PCにnullを設定
        updateGoodsEntity.setSaleEndTimePC(null);
        // 最大注文可能数に”1”を設定
        updateGoodsEntity.setPurchasedMax(new BigDecimal(1));
        // カタログ表示順に”9999”を設定
        updateGoodsEntity.setOrderDisplay(9999);
        // 更新カウンタ
        updateGoodsEntity.setVersionNo(emotionGoodsEntity.getVersionNo());
        // 登録日時
        updateGoodsEntity.setRegistTime(emotionGoodsEntity.getRegistTime());

        return updateGoodsEntity;
    }
    // PDR Migrate Customization to here
}
