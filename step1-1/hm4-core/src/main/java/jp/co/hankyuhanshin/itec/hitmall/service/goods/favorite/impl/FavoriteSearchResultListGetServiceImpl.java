/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */


package jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.favorite.FavoriteSearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.FavoriteSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * お気に入り商品管理機能詳細リスト取得サービス実装クラス<br/>
 *
 * @author takashima
 * @version $Revision: 1.2 $
 */
@Service
public class FavoriteSearchResultListGetServiceImpl extends AbstractShopService
                implements FavoriteSearchResultListGetService {

    /**
     * 商品検索結果リスト取得ロジック
     */
    private final FavoriteSearchResultListGetLogic favoriteSearchResultListGetLogic;

    @Autowired
    public FavoriteSearchResultListGetServiceImpl(FavoriteSearchResultListGetLogic favoriteSearchResultListGetLogic) {
        this.favoriteSearchResultListGetLogic = favoriteSearchResultListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition お気に入り商品Dao用検索条件Dto
     * @return お気に入り商品検索結果Dtoリスト
     */
    @Override
    public List<FavoriteSearchResultDto> execute(FavoriteSearchForBackDaoConditionDto searchCondition,
                                                 HTypeSiteType siteType) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("searchCondition", searchCondition);

        // (2) 共通情報チェック
        // ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        // サイト区分 ： null(or 空文字) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;

        AssertionUtil.assertNotNull("siteType", siteType);

        // (3)検索条件Dtoの編集
        searchCondition.setShopSeq(shopSeq);
        searchCondition.setSiteType(siteType);

        // (4)Logic処理を実行
        List<FavoriteSearchResultDto> favoriteSearchResultDtoList =
                        favoriteSearchResultListGetLogic.execute(searchCondition);

        // (5)戻り値
        return favoriteSearchResultDtoList;
    }
}
