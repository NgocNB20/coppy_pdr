/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;

/**
 * お気に入り情報登録ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface FavoriteRegistLogic {

    /**
     * ロジック実行<br/>
     *
     * @param favoriteEntity お気に入りエンティティ
     * @return 登録件数
     */
    int execute(FavoriteEntity favoriteEntity);
}
