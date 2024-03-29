/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.index;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.index.OrderIndexSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * 受注インデックスDaoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderIndexDao {

    /**
     * インサート<br/>
     *
     * @param orderIndexEntity 受注インデックス
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderIndexEntity orderIndexEntity);

    /**
     * アップデート<br/>
     *
     * @param orderIndexEntity 受注インデックス
     * @return 処理件数
     */
    @Update
    int update(OrderIndexEntity orderIndexEntity);

    /**
     * デリート<br/>
     *
     * @param orderIndexEntity 受注インデックス
     * @return 処理件数
     */
    @Delete
    int delete(OrderIndexEntity orderIndexEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return 受注インデックスエンティティ
     */
    @Select
    OrderIndexEntity getEntity(Integer orderSeq, Integer orderVersionNo);

    /**
     * エンティティ取得(最新バージョン)<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注インデックスエンティティ
     */
    @Select
    OrderIndexEntity getEntityOfMaxOrderVersionNo(Integer orderSeq);

    /**
     * 受注SEQ採番<br/>
     *
     * @return 受注SEQ
     */
    @Select
    int getOrderSeqNextVal();

    /**
     * 受注インデックスリスト取得<br/>
     *
     * @param conditionDto  検索条件DTO
     * @param selectOptions 検索オプション
     * @return 受注インデックスエンティティリスト
     */
    @Select
    List<OrderIndexEntity> getSearchOrderIndex(OrderIndexSearchForDaoConditionDto conditionDto,
                                               SelectOptions selectOptions);

    /**
     * 異常発生時受注インデックスリスト取得<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注インデックスエンティティ
     */
    @Select
    OrderIndexEntity getAtEmergencyIndexData(Integer orderSeq);
}
