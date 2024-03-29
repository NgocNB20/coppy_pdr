/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.FreeAreaViewableMemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaViewableMemberEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.stream.Stream;

/**
 * フリーエリア表示対象会員Dao<br/>
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface FreeAreaViewableMemberDao {

    /**
     * インサート
     *
     * @param freeAreaViewableMemberEntity フリーエリア表示対象会員エンティティ
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(FreeAreaViewableMemberEntity freeAreaViewableMemberEntity);

    /**
     * アップデート
     *
     * @param freeAreaViewableMemberEntity フリーエリア表示対象会員エンティティ
     * @return 更新件数
     */
    @Update
    int update(FreeAreaViewableMemberEntity freeAreaViewableMemberEntity);

    /**
     * デリート
     *
     * @param freeAreaViewableMemberEntity フリーエリア表示対象会員エンティティ
     * @return 削除件数
     */
    @Delete
    int delete(FreeAreaViewableMemberEntity freeAreaViewableMemberEntity);

    /**
     * エンティティ取得
     *
     * @param freeAreaSeq   フリーエリアSEQ
     * @param memberInfoSeq 会員SEQ
     * @return フリーエリア表示対象会員エンティティ
     */
    @Select
    FreeAreaViewableMemberEntity getEntity(Integer freeAreaSeq, Integer memberInfoSeq);

    /**
     * フリーエリア表示対象会員件数取得
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return 件数
     */
    @Select
    int getCountByFreeAreaSeq(Integer freeAreaSeq);

    /**
     * デリート
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteByFreeAreaSeq(Integer freeAreaSeq);

    /**
     * CSVダウンロード<br/>
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return ダウンロード取得件数
     */
    @Select
    Stream<FreeAreaViewableMemberCsvDto> exportCsvByFreeAreaSeq(Integer freeAreaSeq);

}
