/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.orderperson;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * 受注ご注文主Daoクラス
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface OrderPersonDao {

    /**
     * インサート
     *
     * @param orderPersonEntity 受注ご注文エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderPersonEntity orderPersonEntity);

    /**
     * アップデート
     *
     * @param orderPersonEntity 受注ご注文エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderPersonEntity orderPersonEntity);

    /**
     * デリート
     *
     * @param orderPersonEntity 受注ご注文エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderPersonEntity orderPersonEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq             受注SEQ
     * @param orderPersonVersionNo 受注ご注文連番
     * @return 受注ご注文エンティティ
     */
    @Select
    OrderPersonEntity getEntity(Integer orderSeq, Integer orderPersonVersionNo);

}
