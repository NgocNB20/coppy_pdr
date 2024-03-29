/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.footprint;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.footprint.FootprintSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * あしあと商品Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface FootprintDao {

    /**
     * インサート
     *
     * @param footprintEntity あしあとエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(FootprintEntity footprintEntity);

    /**
     * アップデート
     *
     * @param footprintEntity あしあとエンティティ
     * @return 処理件数
     */
    @Update
    int update(FootprintEntity footprintEntity);

    /**
     * デリート
     *
     * @param footprintEntity あしあとエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(FootprintEntity footprintEntity);

    /**
     * エンティティ取得
     *
     * @param accessUid     端末識別情報
     * @param goodsGroupSeq 商品グループSEQ
     * @return あしあとエンティティ
     */
    @Select
    FootprintEntity getEntity(String accessUid, Integer goodsGroupSeq);

    /**
     * あしあと削除
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別情報
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteFootprint(Integer shopSeq, String accessUid);

    /**
     * あしあと商品リスト取得
     *
     * @param conditionDto あしあと検索条件DTO
     * @return あしあとエンティティリスト
     */
    @Select
    List<FootprintEntity> getSearchFootprintList(FootprintSearchForDaoConditionDto conditionDto,
                                                 SelectOptions selectOptions);
}
