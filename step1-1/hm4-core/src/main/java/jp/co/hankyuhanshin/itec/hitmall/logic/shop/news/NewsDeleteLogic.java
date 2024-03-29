/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

/**
 * ニュース削除処理
 *
 * @author nose
 */
public interface NewsDeleteLogic {

    /**
     * ニュース削除
     *
     * @param newsEntity ニュースエンティティ
     * @return 処理件数
     */
    int execute(NewsEntity newsEntity);
}
