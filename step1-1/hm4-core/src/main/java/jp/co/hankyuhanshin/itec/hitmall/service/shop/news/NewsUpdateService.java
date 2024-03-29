/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

/**
 * ニュース更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface NewsUpdateService {

    /* メッセージ SSN0005 */

    /**
     * ニュース更新失敗エラー<br/>
     * <code>MSGCD_NEWS_UPDATE_FAIL</code>
     */
    String MSGCD_NEWS_UPDATE_FAIL = "SSN000501";

    /**
     * ニュース更新
     *
     * @param newsEntity ニュースエンティティ
     * @return 処理件数
     */
    int execute(NewsEntity newsEntity);

}
