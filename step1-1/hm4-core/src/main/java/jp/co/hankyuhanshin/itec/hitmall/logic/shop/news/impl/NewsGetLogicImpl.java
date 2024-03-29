/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ニュース詳細情報取得Logic<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class NewsGetLogicImpl extends AbstractShopLogic implements NewsGetLogic {

    /**
     * ニュースDAO
     */
    private final NewsDao newsDao;

    @Autowired
    public NewsGetLogicImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * ニュース詳細情報取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @param newsSeq ニュースSEQ
     * @return ニュースエンティティ
     */
    @Override
    public NewsEntity execute(Integer shopSeq, Integer newsSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("newsSeq", newsSeq);

        // 取得
        return newsDao.getEntityByShopSeq(shopSeq, newsSeq);

    }

    /**
     * 会員別ニュース詳細情報取得<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param newsSeq       ニュースSEQ
     * @param memberInfoSeq 会員SEQ
     * @return ニュースエンティティ
     */
    @Override
    public NewsEntity execute(Integer shopSeq, Integer newsSeq, Integer memberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("newsSeq", newsSeq);

        // 取得
        return newsDao.getEntityByShopSeqAndMember(shopSeq, newsSeq, memberInfoSeq);

    }

}
