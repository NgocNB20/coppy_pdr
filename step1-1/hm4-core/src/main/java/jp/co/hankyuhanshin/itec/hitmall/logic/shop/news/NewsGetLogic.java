/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

/**
 * ニュース詳細情報取得Logic<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface NewsGetLogic {

    /**
     * ニュース詳細情報取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @param newsSeq ニュースSEQ
     * @return ニュースエンティティ
     */
    NewsEntity execute(Integer shopSeq, Integer newsSeq);

    /**
     * ニュース詳細情報取得<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param newsSeq       ニュースSEQ
     * @param memberInfoSeq 会員SEQ
     * @return ニュースエンティティ
     */
    NewsEntity execute(Integer shopSeq, Integer newsSeq, Integer memberInfoSeq);

}
