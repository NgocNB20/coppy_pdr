/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * 運営者用確認メールDaoクラス（バッチ用）<br/>
 *
 * @author MN7017
 * @version $Revision:$
 */
@Dao
@ConfigAutowireable
public interface AdminConfirmMailBatchDao {

    /**
     * レコード削除
     *
     * @param shopSeq      ショップSEQ
     * @param specifiedDay 指定日数
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteAdminConfirmMailClearRecord(Integer shopSeq, String specifiedDay);

    /**
     * レコード総件数取得
     *
     * @param shopSeq ショップSEQ
     * @return レコード件数
     */
    @Select
    int getAdminConfirmMailRecordCount(Integer shopSeq);

}
