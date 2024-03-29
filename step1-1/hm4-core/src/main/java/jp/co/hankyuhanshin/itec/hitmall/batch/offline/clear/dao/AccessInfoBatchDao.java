/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * アクセス情報Daoクラス（バッチ用）<br/>
 *
 * @author kk4625
 * @version $Revision:$
 */
@Dao
@ConfigAutowireable
public interface AccessInfoBatchDao {

    /**
     * 指定日のレコード件数取得
     *
     * @param shopSeq      ショップSEQ
     * @param specifiedDay 指定日数
     * @return レコード件数
     */
    @Select
    int getAccessInfoRecordCount(Integer shopSeq, String specifiedDay);

    /**
     * レコード総件数取得
     *
     * @param shopSeq ショップSEQ
     * @return レコード件数
     */
    @Select
    int getAccessInfoRecordAllCount(Integer shopSeq);

}
