/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.coop;

import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 基幹連携日時履歴Daoクラス<br/>
 *
 * @author s.kume(NSK)
 */
@Dao
@ConfigAutowireable
public interface CoopDateHistoryDao {
    // PDR Migrate Customization from here

    /**
     * デリート
     *
     * @param coopDateHistoryEntity 基幹連携日時履歴エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(CoopDateHistoryEntity coopDateHistoryEntity);

    /**
     * インサート
     *
     * @param coopDateHistoryEntity 基幹連携日時履歴エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(CoopDateHistoryEntity coopDateHistoryEntity);

    /**
     * アップデート
     *
     * @param coopDateHistoryEntity 基幹連携日時履歴エンティティ
     * @return 処理件数
     */
    @Update
    int update(CoopDateHistoryEntity coopDateHistoryEntity);

    /**
     * エンティティ取得
     *
     * @param coopId 基幹連携ID
     * @return 基幹連携日時履歴エンティティ
     */
    @Select
    CoopDateHistoryEntity getEntity(String coopId);
    // PDR Migrate Customization to here
}
