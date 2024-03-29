/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

import java.util.List;

/**
 * ニュース情報リスト取得Logic<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface NewsListGetLogic {

    /**
     * ニュース情報リスト取得<br/>
     *
     * @param newsSearchForDaoConditionDto 検索条件DTO
     * @return ニュース情報リスト
     */
    List<NewsEntity> execute(NewsSearchForDaoConditionDto newsSearchForDaoConditionDto);

    /**
     * ニュースエンティティリスト取得
     *
     * @param newsSearchForBackDaoConditionDto 検索条件Dto(管理者画面用)
     * @return ニュースエンティティリスト
     */
    List<NewsEntity> execute(NewsSearchForBackDaoConditionDto newsSearchForBackDaoConditionDto);
}
