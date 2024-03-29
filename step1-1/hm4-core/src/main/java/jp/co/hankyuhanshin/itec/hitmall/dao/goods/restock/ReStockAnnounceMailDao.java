/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceMailEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 入荷お知らせメールDaoクラス
 *
 * @author st75001
 */
@Dao
@ConfigAutowireable
public interface ReStockAnnounceMailDao {

    /**
     * デリート
     *
     * @param reStockAnnounceMailEntity 入荷お知らせメールエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(ReStockAnnounceMailEntity reStockAnnounceMailEntity);

    /**
     * インサート
     *
     * @param reStockAnnounceMailEntity 入荷お知らせメールエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(ReStockAnnounceMailEntity reStockAnnounceMailEntity);

    /**
     * アップデート
     *
     * @param reStockAnnounceMailEntity 入荷お知らせメールエンティティ
     * @return 処理件数
     */
    @Update
    int update(ReStockAnnounceMailEntity reStockAnnounceMailEntity);

    /**
     * エンティティ取得
     *
     * @param goodsSeq 商品SEQ
     * @param memberInfoSeq 会員SEQ
     * @param versionNo 更新カウンタ
     * @return 入荷お知らせメールエンティティ
     */
    @Select
    ReStockAnnounceMailEntity getEntity(Integer goodsSeq, Integer memberInfoSeq, Integer versionNo);

    /**
     * 商品単位のエンティティリスト取得
     *
     * @param goodsSeq 商品SEQ
     * @return 入荷お知らせメールエンティティ
     */
    @Select
    List<ReStockAnnounceMailEntity> getReStockAnnounceMailEntityGoodsSeqList(Integer goodsSeq);

    /**
     * 会員単位の未配信エンティティリスト取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return 入荷お知らせメールエンティティ
     */
    @Select
    List<ReStockAnnounceMailEntity> getReStockAnnounceMailEntityMemberInfoSeqListForNotDelivery(Integer memberInfoSeq);

    /**
     * 配信中の入荷お知らせメールリストを取得
     *
     * @return 入荷お知らせメールエンティティ
     */
    @Select
    List<ReStockAnnounceMailEntity> getReStockAnnounceMailDeliveryedList();

    /**
     * 最新入荷お知らせメール取得
     *
     * @param goodsSeq 商品SEQ
     * @param memberInfoSeq 会員SEQ
     * @return 入荷お知らせメールエンティティ
     */
    @Select
    ReStockAnnounceMailEntity getNewEntity(Integer goodsSeq, Integer memberInfoSeq);

}
