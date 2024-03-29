/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.access;

import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * アクセス検索キーワードDaoクラス<br/>
 *
 * @author nakagawa
 * @version $Revision: 1.2 $
 */
@Dao
@ConfigAutowireable
public interface AccessSearchKeywordDao {

    /**
     * インサート<br/>
     *
     * @param accessSearchKeywordEntity アクセス検索キーワードエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(AccessSearchKeywordEntity accessSearchKeywordEntity);

}
