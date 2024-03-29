/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;

/**
 * お気に入り更新ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public interface FavoriteUpdateLogic {

    // LMF0005

    /**
     * お気に入り更新処理<br/>
     *
     * @param favoriteEntity お気に入りエンティティ
     * @return 更新処理
     */
    int execute(FavoriteEntity favoriteEntity);
}
