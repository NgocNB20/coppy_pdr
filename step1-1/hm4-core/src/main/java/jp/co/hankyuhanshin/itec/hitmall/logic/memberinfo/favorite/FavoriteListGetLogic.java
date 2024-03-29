/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;

import java.util.List;

/**
 * お気に入り情報リスト取得ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
public interface FavoriteListGetLogic {

    /**
     * ロジック実行<br/>
     *
     * @param favoriteConditionDto お気に入り検索条件DTO
     * @return お気に入りエンティティリスト
     */
    List<FavoriteEntity> execute(FavoriteSearchForDaoConditionDto favoriteConditionDto);
}
