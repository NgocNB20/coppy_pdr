/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */


package jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;

import java.util.List;

/**
 * お気に入り商品検索結果リスト取得<br/>
 *
 * @author takashima
 * @version $Revision: 1.2 $
 */
public interface FavoriteSearchResultListGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition お気に入り商品Dao用検索条件（管理機能）Dto
     * @return お気に入り商品検索結果DTOリスト
     */
    List<FavoriteSearchResultDto> execute(FavoriteSearchForBackDaoConditionDto searchCondition);
}
