/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce;

import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * セールお知らせメールDaoクラス
 *
 * @author takashima
 */
@Dao
@ConfigAutowireable
public interface SaleAnnounceMailDao {

    /**
     * デリート
     *
     * @param saleAnnounceMailEntity セールお知らせメールエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(SaleAnnounceMailEntity saleAnnounceMailEntity);

    /**
     * インサート
     *
     * @param saleAnnounceMailEntity セールお知らせメールエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SaleAnnounceMailEntity saleAnnounceMailEntity);

    /**
     * アップデート
     *
     * @param saleAnnounceMailEntity セールお知らせメールエンティティ
     * @return 処理件数
     */
    @Update
    int update(SaleAnnounceMailEntity saleAnnounceMailEntity);

    /**
     * エンティティ取得
     *
     * @param goodsSeq 商品SEQ
     * @param memberInfoSeq 会員SEQ
     * @return セールお知らせメールエンティティ
     */
    @Select
    SaleAnnounceMailEntity getEntity(Integer goodsSeq, Integer memberInfoSeq);

    /**
     * 配信中のセールお知らせメールリストを取得
     *
     * @return セールお知らせメールエンティティ
     */
    @Select
    List<SaleAnnounceMailEntity> getSaleAnnounceMailDeliveryedList();

    /**
     * 会員単位の未配信エンティティリスト取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return セールお知らせメールエンティティ
     */
    @Select
    List<SaleAnnounceMailEntity> getSaleAnnounceMailEntityMemberInfoSeqListForNotDelivery(Integer memberInfoSeq);

}
