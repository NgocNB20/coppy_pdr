/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.bill;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 受注入金Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderReceiptOfMoneyDao {

    /**
     * インサート
     *
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity);

    /**
     * アップデート
     *
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity);

    /**
     * デリート
     *
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq                     受注SEQ
     * @param orderReceiptOfMoneyVersionNo 受注入金連番
     * @return 受注入金エンティティ
     */
    @Select
    OrderReceiptOfMoneyEntity getEntity(Integer orderSeq, Integer orderReceiptOfMoneyVersionNo);

    /**
     * 受注入金エンティティリスト取得
     *
     * @param orderSeq                     受注SEQ
     * @param orderReceiptOfMoneyVersionNo 受注入金連番
     * @return 受注入金エンティティリスト
     */
    @Select
    List<OrderReceiptOfMoneyEntity> getOrderReceiptOfMoneyList(Integer orderSeq, Integer orderReceiptOfMoneyVersionNo);

}
