/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * ショップDaoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface ShopDao {

    /**
     * インサート<br/>
     *
     * @param shopEntity ショップエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(ShopEntity shopEntity);

    /**
     * アップデート<br/>
     *
     * @param shopEntity ショップエンティティ
     * @return 処理件数
     */
    @Update
    int update(ShopEntity shopEntity);

    /**
     * デリート<br/>
     *
     * @param shopEntity ショップエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(ShopEntity shopEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return ショップエンティティ
     */
    @Select
    ShopEntity getEntity(Integer shopSeq);

    /**
     * エンティティ取得<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param siteType   サイト種別
     * @param openStatus ショップ公開状態
     * @return ショップエンティティ
     */
    @Select
    ShopEntity getEntityBySiteTypeStatus(Integer shopSeq, HTypeSiteType siteType, HTypeOpenStatus openStatus);

    /**
     * エンティティ取得<br/>
     * 指定されたサイトの公開状態・公開期間までを条件にショップ情報を取得する
     *
     * @param shopSeq    ショップSEQ
     * @param siteType   サイト種別
     * @param openStatus ショップ公開状態
     * @return ショップエンティティ
     */
    @Select
    ShopEntity getEntityBySiteTypeStatusTime(Integer shopSeq, HTypeSiteType siteType, HTypeOpenStatus openStatus);
}
