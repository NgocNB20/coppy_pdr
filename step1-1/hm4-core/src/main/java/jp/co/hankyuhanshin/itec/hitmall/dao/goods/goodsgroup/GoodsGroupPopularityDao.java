/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupPopularityEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * 商品グループ人気Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface GoodsGroupPopularityDao {

    /**
     * インサート
     *
     * @param goodsGroupPopularityEntity 商品グループ人気エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(GoodsGroupPopularityEntity goodsGroupPopularityEntity);

    /**
     * アップデート
     *
     * @param goodsGroupPopularityEntity 商品グループ人気エンティティ
     * @return 処理件数
     */
    @Update
    int update(GoodsGroupPopularityEntity goodsGroupPopularityEntity);

    /**
     * デリート
     *
     * @param goodsGroupPopularityEntity 商品グループ人気エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(GoodsGroupPopularityEntity goodsGroupPopularityEntity);

    /**
     * エンティティ取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 商品グループ人気エンティティ
     */
    @Select
    GoodsGroupPopularityEntity getEntity(Integer goodsGroupSeq);

    /**
     * 人気カウント更新
     *
     * @param shopSeq     ショップSEQ
     * @param totalPeriod 集計期間
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updatePopularityOrder(Integer shopSeq, String totalPeriod);
}
