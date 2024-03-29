/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

/**
 * ニュース更新処理
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface NewsUpdateLogic {

    /**
     * ニュース更新
     *
     * @param newsEntity ニュースエンティティ
     * @return 処理件数
     */
    int execute(NewsEntity newsEntity);
}
