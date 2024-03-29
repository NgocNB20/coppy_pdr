/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceGoodsEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 入荷お知らせ商品Daoクラス
 *
 * @author st75001
 */
@Dao
@ConfigAutowireable
public interface ReStockAnnounceGoodsDao {

    /**
     * デリート
     *
     * @param reStockAnnounceGoodsEntity 入荷お知らせ商品エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity);

    /**
     * インサート
     *
     * @param reStockAnnounceGoodsEntity 入荷お知らせ商品エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity);

    /**
     * アップデート
     *
     * @param reStockAnnounceGoodsEntity 入荷お知らせ商品エンティティ
     * @return 処理件数
     */
    @Update
    int update(ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity);

    /**
     * エンティティ取得
     *
     * @param goodsSeq 商品SEQ
     * @return 入荷お知らせ商品エンティティ
     */
    @Select
    ReStockAnnounceGoodsEntity getEntity(Integer goodsSeq);
}
