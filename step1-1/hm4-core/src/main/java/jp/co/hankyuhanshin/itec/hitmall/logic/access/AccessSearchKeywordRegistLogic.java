/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access;

import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;

/**
 * アクセス検索キーワード情報登録ロジック<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface AccessSearchKeywordRegistLogic {

    /**
     * アクセス検索キーワード情報登録メソッド<br/>
     *
     * @param accessSearchKeywordEntity アクセス検索キーワード情報エンティティ
     * @return 登録件数
     */
    int execute(AccessSearchKeywordEntity accessSearchKeywordEntity);

}
