/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.additional;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 受注追加料金Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderAdditionalChargeDao {

    /**
     * インサート
     *
     * @param orderAdditionalChargeEntity 受注追加料金エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderAdditionalChargeEntity orderAdditionalChargeEntity);

    /**
     * アップデート
     *
     * @param orderAdditionalChargeEntity 受注追加料金エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderAdditionalChargeEntity orderAdditionalChargeEntity);

    /**
     * デリート
     *
     * @param orderAdditionalChargeEntity 受注追加料金エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderAdditionalChargeEntity orderAdditionalChargeEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq                       受注SEQ
     * @param orderAdditionalChargeVersionNo 受注追加料金連番
     * @param orderDisplay                   表示順
     * @return 受注追加料金エンティティ
     */
    @Select
    OrderAdditionalChargeEntity getEntity(Integer orderSeq,
                                          Integer orderAdditionalChargeVersionNo,
                                          Integer orderDisplay);

    /**
     * 受注追加料金エンティティリスト取得
     *
     * @param orderSeq                       受注SEQ
     * @param orderAdditionalChargeVersionNo 受注追加料金連番
     * @return 受注追加料金エンティティリスト
     */
    @Select
    List<OrderAdditionalChargeEntity> getOrderAdditionalChargeList(Integer orderSeq,
                                                                   Integer orderAdditionalChargeVersionNo);
}
