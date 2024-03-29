/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.access;

import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;

/**
 * アクセス検索キーワード情報登録
 * 検索キーワード情報を登録する
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface AccessSearchKeywordRegistService {

    /**
     * アクセス検索キーワード情報登録
     * アクセス検索キーワード情報を登録する
     *
     * @param accessSearchKeywordEntity アクセス検索キーワード情報エンティティ
     * @param accessUid 端末識別情報
     * @return 更新・登録件数
     */
    int execute(AccessSearchKeywordEntity accessSearchKeywordEntity, String accessUid);

}
