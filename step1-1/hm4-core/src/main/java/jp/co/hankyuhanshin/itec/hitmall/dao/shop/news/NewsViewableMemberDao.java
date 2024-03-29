/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsViewableMemberEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * ニュース表示対象会員Dao<br/>
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface NewsViewableMemberDao {

    /**
     * インサート
     *
     * @param newsViewableMemberEntity ニュース表示対象会員エンティティ
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(NewsViewableMemberEntity newsViewableMemberEntity);

    /**
     * アップデート
     *
     * @param newsViewableMemberEntity ニュース表示対象会員エンティティ
     * @return 更新件数
     */
    @Update
    int update(NewsViewableMemberEntity newsViewableMemberEntity);

    /**
     * デリート
     *
     * @param newsViewableMemberEntity ニュース表示対象会員エンティティ
     * @return 削除件数
     */
    @Delete
    int delete(NewsViewableMemberEntity newsViewableMemberEntity);

    /**
     * エンティティ取得
     *
     * @param newsSeq       ニュースSEQ
     * @param memberInfoSeq 会員SEQ
     * @return ニュース表示対象会員エンティティ
     */
    @Select
    NewsViewableMemberEntity getEntity(Integer newsSeq, Integer memberInfoSeq);

    /**
     * ニュース表示対象会員件数取得
     *
     * @param newsSeq ニュースSEQ
     * @return 件数
     */
    @Select
    int getCountByNewsSeq(Integer newsSeq);

    /**
     * デリート
     *
     * @param newsSeq ニュースSEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteByNewsSeq(Integer newsSeq);

}
