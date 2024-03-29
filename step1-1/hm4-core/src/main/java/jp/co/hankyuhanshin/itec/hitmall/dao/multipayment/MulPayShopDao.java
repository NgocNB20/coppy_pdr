/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayShopEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * マルチペイメント用ショップ設定Daoクラス<br/>
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface MulPayShopDao {

    /**
     * ショップSEQからエンティティの取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return マルチペイメント用ショップ設定エンティティ
     */
    @Select
    MulPayShopEntity getEntityByShopSeq(Integer shopSeq);

    /**
     * ショップIDからエンティティ取得
     *
     * @param shopId ショップID
     * @return マルチペイメント用サイト設定エンティティ
     */
    @Select
    MulPayShopEntity getEntityByShopId(String shopId);

}
