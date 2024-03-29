/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 商品ランキングDaoクラス<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface GoodsRankingDao {

    /**
     * インサート<br/>
     *
     * @param goodsRankingEntity 商品ランキングエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(GoodsRankingEntity goodsRankingEntity);

    /**
     * アップデート<br/>
     *
     * @param goodsRankingEntity 商品ランキングエンティティ
     * @return 処理件数
     */
    @Update
    int update(GoodsRankingEntity goodsRankingEntity);

    /**
     * デリート<br/>
     *
     * @param goodsRankingEntity 商品ランキングエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(GoodsRankingEntity goodsRankingEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param shopSeq          ショップSEQ
     * @param goodsRankingType 商品ランキング区分
     * @param goodsGroupSeq    商品グループSEQ
     * @return 商品ランキングエンティティ
     */
    @Select
    GoodsRankingEntity getEntity(Integer shopSeq, String goodsRankingType, Integer goodsGroupSeq);

    /**
     * 全て削除<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 処理件数
     */
    @Delete(sqlFile = true)
    int deleteAll(Integer shopSeq);

    /**
     * クリック数別商品ランキング取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 商品ランキングエンティティリスト
     */
    @Select
    List<GoodsRankingEntity> getClickRanking(Integer shopSeq);

    /**
     * 受注数別商品ランキング取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 商品ランキングエンティティリスト
     */
    @Select
    List<GoodsRankingEntity> getOrderRanking(Integer shopSeq);

    /**
     * クリック数別商品ランキング結果インサート<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param totalPeriod 集計月数
     * @param limit       最大取得件数
     * @return 処理件数
     */
    @Insert(sqlFile = true, excludeNull = true)
    int insertClickRankingResult(Integer shopSeq, String totalPeriod, Integer limit);

    /**
     * 受注数別商品ランキング結果インサート<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param totalPeriod 集計月数
     * @param limit       最大取得件数
     * @return 処理件数
     */
    @Insert(sqlFile = true, excludeNull = true)
    int insertOrderRankingResult(Integer shopSeq, String totalPeriod, Integer limit);

}
