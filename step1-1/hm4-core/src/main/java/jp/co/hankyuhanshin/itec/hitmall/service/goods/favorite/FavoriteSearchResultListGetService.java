/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */


package jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;

import java.util.List;

/**
 * お気に入り商品検索結果リスト取得サービス<br/>
 *
 * @author takashima
 * @version $Revision: 1.2 $
 */
public interface FavoriteSearchResultListGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition お気に入り商品Dao用検索条件Dto
     * @return お気に入り商品検索結果Dtoリスト
     */
    List<FavoriteSearchResultDto> execute(FavoriteSearchForBackDaoConditionDto searchCondition, HTypeSiteType siteType);
}
