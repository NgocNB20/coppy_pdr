/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ニュース情報リスト取得Logic<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class NewsListGetLogicImpl extends AbstractShopLogic implements NewsListGetLogic {

    /**
     * ニュースDAO
     */
    private final NewsDao newsDao;

    @Autowired
    public NewsListGetLogicImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * ニュース情報リスト取得<br/>
     *
     * @param newsSearchForDaoConditionDto 検索条件DTO
     * @return ニュース情報リスト
     */
    @Override
    public List<NewsEntity> execute(NewsSearchForDaoConditionDto newsSearchForDaoConditionDto) {

        // (1) パラメータチェック
        // ニュースDao用検索条件DTOが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("newsSearchForDaoConditionDto", newsSearchForDaoConditionDto);

        // (2) ニュース情報リスト取得
        // ニュースDaoのニュース情報リスト取得処理を実行する。
        // DAO NewsDao
        // メソッド List<ニュースエンティティ> getSearchNewsList( （パラメータ）ニュース情報Dao用検索条件DTO)
        List<NewsEntity> newsList = newsDao.getSearchNewsList(newsSearchForDaoConditionDto, newsSearchForDaoConditionDto
                        .getPageInfo()
                        .getSelectOptions());

        // (3) 戻り値
        // 取得したニュースエンティティリストを返す。
        return newsList;
    }

    /**
     * ニュースエンティティリスト取得(※管理者画面用)
     *
     * @param newsSearchForBackDaoConditionDto 検索条件Dto(管理者画面用)
     * @return ニュースエンティティリスト
     */
    @Override
    public List<NewsEntity> execute(NewsSearchForBackDaoConditionDto newsSearchForBackDaoConditionDto) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("newsSearchForBackDaoConditionDto", newsSearchForBackDaoConditionDto);

        // ニュースリスト取得
        return newsDao.getSearchNewsForBackList(newsSearchForBackDaoConditionDto,
                                                newsSearchForBackDaoConditionDto.getPageInfo().getSelectOptions()
                                               );
    }

}
