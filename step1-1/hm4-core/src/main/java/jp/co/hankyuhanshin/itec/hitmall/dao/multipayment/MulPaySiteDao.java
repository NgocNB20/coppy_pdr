/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPaySiteEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * マルチペイメント用サイト設定Daoクラス<br/>
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface MulPaySiteDao {

    /**
     * エンティティ取得
     *
     * @return マルチペイメント用サイト設定エンティティ
     */
    @Select
    MulPaySiteEntity getEntity();

}
