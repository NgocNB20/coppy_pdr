/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.bill;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * 受注請求Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderBillDao {

    /**
     * インサート
     *
     * @param orderBillEntity 受注請求エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderBillEntity orderBillEntity);

    /**
     * アップデート
     *
     * @param orderBillEntity 受注請求エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderBillEntity orderBillEntity);

    /**
     * デリート
     *
     * @param orderBillEntity 受注請求エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderBillEntity orderBillEntity);

    /**
     * エンティティ取得（＋カードブランド名）
     *
     * @param orderSeq           受注SEQ
     * @param orderBillVersionNo 受注請求連番
     * @return 受注請求エンティティ
     */
    @Select
    OrderBillEntity getEntityWithCardbrand(Integer orderSeq, Integer orderBillVersionNo);
}
