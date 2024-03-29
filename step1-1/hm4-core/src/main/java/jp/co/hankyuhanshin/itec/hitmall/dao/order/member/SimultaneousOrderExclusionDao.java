/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.member;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * 同時注文排他Daoクラス
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface SimultaneousOrderExclusionDao {

    /**
     * インサート
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity);

    /**
     * アップデート
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他エンティティ
     * @return 処理件数
     */
    @Update
    int update(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity);

    /**
     * デリート
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity);

    /**
     * エンティティ取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return 同時注文排他
     */
    @Select
    SimultaneousOrderExclusionEntity getEntity(Integer memberInfoSeq);

}
