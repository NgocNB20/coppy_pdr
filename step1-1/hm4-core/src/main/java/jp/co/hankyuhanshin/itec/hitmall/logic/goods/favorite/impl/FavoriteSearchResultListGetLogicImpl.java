/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */


package jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.favorite.FavoriteSearchDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite.FavoriteSearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * お気に入り商品検索結果リスト取得ロジック
 *
 * @author takashima
 * @version $Revision: 1.3 $
 */
@Component
public class FavoriteSearchResultListGetLogicImpl extends AbstractShopLogic
                implements FavoriteSearchResultListGetLogic {

    /**
     * お気に入り商品検索DAO
     */
    private final FavoriteSearchDao favoriteSearchDao;

    @Autowired
    public FavoriteSearchResultListGetLogicImpl(FavoriteSearchDao favoriteSearchDao) {
        this.favoriteSearchDao = favoriteSearchDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition お気に入り商品Dao用検索条件Dto(管理機能)
     * @return お気に入り商品検索結果DTOリスト
     */
    @Override
    public List<FavoriteSearchResultDto> execute(FavoriteSearchForBackDaoConditionDto searchCondition) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsSearchForBackDaoConditionDto", searchCondition);

        // (2)商品検索結果リスト取得処理
        List<FavoriteSearchResultDto> favoriteSearchResultDtoList =
                        favoriteSearchDao.getSearchFavoriteForBackList(searchCondition,
                                                                       searchCondition.getPageInfo().getSelectOptions()
                                                                      );

        return favoriteSearchResultDtoList;
    }
}
