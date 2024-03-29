/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.memo;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * 受注メモDaoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderMemoDao {

    /**
     * インサート
     *
     * @param orderMemoEntity 受注メモエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderMemoEntity orderMemoEntity);

    /**
     * アップデート
     *
     * @param orderMemoEntity 受注メモエンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderMemoEntity orderMemoEntity);

    /**
     * デリート
     *
     * @param orderMemoEntity 受注メモエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderMemoEntity orderMemoEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq           受注SEQ
     * @param orderMemoVersionNo 受注履歴連番
     * @return 受注メモエンティティ
     */
    @Select
    OrderMemoEntity getEntity(Integer orderSeq, Integer orderMemoVersionNo);

}
