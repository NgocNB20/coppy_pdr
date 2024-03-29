/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.favorite.FavoriteSearchDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite.FavoriteAllCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * お気に入り商品検索CSV一括ダウンロードロジック<br/>
 *
 * @author takashima
 */
@Component
public class FavoriteAllCsvDownloadLogicImpl extends AbstractShopLogic implements FavoriteAllCsvDownloadLogic {

    /**
     * お気に入り商品DAO
     */
    private final FavoriteSearchDao favoriteSearchDao;

    @Autowired
    public FavoriteAllCsvDownloadLogicImpl(FavoriteSearchDao favoriteSearchDao) {
        this.favoriteSearchDao = favoriteSearchDao;
    }

    @Override
    public Stream<FavoriteCsvDto> execute(FavoriteSearchForBackDaoConditionDto conditionDto) {
        return this.favoriteSearchDao.exportCsvByFavoriteSearchForBackDaoConditionDto(conditionDto);
    }

}
