/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockSettingEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Script;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 在庫設定Dao
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface StockSettingDao {

    /**
     * インサート<br/>
     *
     * @param stockSettingEntity 在庫設定
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(StockSettingEntity stockSettingEntity);

    /**
     * アップデート<br/>
     *
     * @param stockSettingEntity 在庫設定
     * @return 処理件数
     */
    @Update
    int update(StockSettingEntity stockSettingEntity);

    /**
     * デリート<br/>
     *
     * @param stockSettingEntity 在庫設定
     * @return 処理件数
     */
    @Delete
    int delete(StockSettingEntity stockSettingEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param goodsSeq 商品SEQ
     * @return 在庫設定エンティティ
     */
    @Select
    StockSettingEntity getEntity(Integer goodsSeq);

    /**
     * 在庫設定エンティティリスト取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 在庫設定エンティティリスト
     */
    @Select
    List<StockSettingEntity> getStockSettingListByGoodsGroupSeq(Integer goodsGroupSeq);

    /**
     * 在庫設定テーブルロック<br/>
     */
    @Script
    void updateLockTableShareModeNowait();
}
