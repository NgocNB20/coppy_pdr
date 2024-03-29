/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * お気に入り情報リスト取得ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.5 $
 */
@Component
public class FavoriteListGetLogicImpl extends AbstractShopLogic implements FavoriteListGetLogic {

    /**
     * お気に入りDao
     **/
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteListGetLogicImpl(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param favoriteConditionDto お気に入り検索条件DTO
     * @return お気に入りエンティティリスト
     */
    @Override
    public List<FavoriteEntity> execute(FavoriteSearchForDaoConditionDto favoriteConditionDto) {

        // 引数チェック
        AssertionUtil.assertNotNull("favoriteConditionDto", favoriteConditionDto);

        return favoriteDao.getSearchFavoriteList(
                        favoriteConditionDto, favoriteConditionDto.getPageInfo().getSelectOptions());
    }

}
