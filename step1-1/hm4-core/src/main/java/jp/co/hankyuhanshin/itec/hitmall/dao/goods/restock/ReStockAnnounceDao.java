/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 入荷お知らせDaoクラス
 *
 * @author st75001
 */
@Dao
@ConfigAutowireable
public interface ReStockAnnounceDao {

    /**
     * デリート
     *
     * @param reStockAnnounceEntity 入荷お知らせエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(ReStockAnnounceEntity reStockAnnounceEntity);

    /**
     * インサート
     *
     * @param reStockAnnounceEntity 入荷お知らせエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(ReStockAnnounceEntity reStockAnnounceEntity);

    /**
     * アップデート
     *
     * @param reStockAnnounceEntity 入荷お知らせエンティティ
     * @return 処理件数
     */
    @Update
    int update(ReStockAnnounceEntity reStockAnnounceEntity);

    /**
     * エンティティ取得
     *
     * @param goodsSeq 商品SEQ
     * @param memberInfoSeq 会員SEQ
     * @return 入荷お知らせエンティティ
     */
    @Select
    ReStockAnnounceEntity getEntity(Integer goodsSeq, Integer memberInfoSeq);

    /**
     * 全件取得
     *
     * @param goodsSeq 商品SEQ
     * @return 入荷お知らせEntityList(全件)
     */
    @Select
    List<ReStockAnnounceEntity> getGoodsEntityList(Integer goodsSeq);

    /**
     * 商品コードリスト取得
     *
     * @return 入荷お知らせ商品コードリスト（心意気商品を除く）
     */
    @Select
    List<String> getGoodsCodeList();

}
