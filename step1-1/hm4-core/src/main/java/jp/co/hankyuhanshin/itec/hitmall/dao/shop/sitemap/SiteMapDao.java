/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.sitemap;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.sitemap.SiteMapEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * サイトマップDao
 */
@Dao
@ConfigAutowireable
public interface SiteMapDao {
    /**
     * インサート
     *
     * @param entity サイトEntity
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SiteMapEntity entity);

    /**
     * アップデート
     *
     * @param entity サイトEntity
     * @return 処理件数
     */
    @Update
    int update(SiteMapEntity entity);

    /**
     * デリート
     *
     * @param entity サイトEntity
     * @return 処理件数
     */
    @Delete
    int delete(SiteMapEntity entity);

    /**
     * エンティティ取得
     *
     * @param siteMapSeq サイトマップSEQ
     * @return サイトEntity
     */
    @Select
    SiteMapEntity getEntity(Integer siteMapSeq);

    /**
     * サイトマップXML出力対象となるエンティティリストを取得
     *
     * @return サイトマップエンティティ
     */
    @Select
    List<SiteMapEntity> getEntityByOutputFlag();
}
