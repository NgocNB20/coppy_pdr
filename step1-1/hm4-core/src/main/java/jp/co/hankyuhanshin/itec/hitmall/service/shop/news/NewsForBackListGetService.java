/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

import java.util.List;

/**
 * ニュース管理機能用リスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface NewsForBackListGetService {

    /**
     * 指定条件を満たす情報を取得
     *
     * @param newsSearchForBackDaoConditionDto ニュースDao用検索条件Dto(管理機能用）
     * @return ニュースエンティティのリスト
     */
    List<NewsEntity> execute(NewsSearchForBackDaoConditionDto newsSearchForBackDaoConditionDto);
}
