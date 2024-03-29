/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 受注配送Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderDeliveryDao {

    /**
     * インサート
     *
     * @param orderDeliveryEntity 受注配送エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderDeliveryEntity orderDeliveryEntity);

    /**
     * アップデート
     *
     * @param orderDeliveryEntity 受注配送エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderDeliveryEntity orderDeliveryEntity);

    /**
     * デリート
     *
     * @param orderDeliveryEntity 受注配送エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderDeliveryEntity orderDeliveryEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @param orderConsecutiveNo     注文連番
     * @return 受注配送エンティティ
     */
    @Select
    OrderDeliveryEntity getEntity(Integer orderSeq, Integer orderDeliveryVersionNo, Integer orderConsecutiveNo);

    /**
     * エンティティリスト取得
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return 受注配送エンティティリスト
     */
    @Select
    List<OrderDeliveryEntity> getEntityList(Integer orderSeq, Integer orderDeliveryVersionNo);

    /**
     * エンティティ取得
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return 受注配送エンティティ
     */
    @Select
    List<OrderDeliveryEntity> getOrderDeliveryListForUpdate(Integer orderSeq, Integer orderDeliveryVersionNo);

    /**
     * 受注配送エンティティリスト取得
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @return List&lt;OrderDeliveryDto&gt; 受注配送エンティティリスト
     */
    @Select
    List<OrderDeliveryEntity> getDtoListByOrderSeq(Integer orderSeq, Integer orderDeliveryVersionNo);
}
