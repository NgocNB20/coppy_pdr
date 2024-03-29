/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

/**
 * ニュース詳細情報取得Service<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface NewsDetailsGetService {

    /* メッセージ SSN0002 */

    /**
     * ニュース取得失敗エラー<br/>
     * <code>MSGCD_NEWS_GET_FAIL</code>
     */
    String MSGCD_NEWS_GET_FAIL = "SSN000201";

    /**
     * ニュース詳細情報を取得する<br/>
     *
     * @param newsSeq ニュースSEQ
     * @return ニュースエンティティ
     */
    NewsEntity execute(Integer newsSeq);
}
