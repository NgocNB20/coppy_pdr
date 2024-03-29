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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ニュース削除
 *
 * @author nose
 */
@Component
public class NewsDeleteLogicImpl extends AbstractShopLogic implements NewsDeleteLogic {

    /**
     * ニュースDao
     */
    private final NewsDao newsDao;

    @Autowired
    public NewsDeleteLogicImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * ニュース削除
     *
     * @param newsEntity ニュースエンティティ
     * @return 削除件数
     */
    @Override
    public int execute(NewsEntity newsEntity) {

        // パラメータチェック
        this.checkParam(newsEntity);

        return newsDao.delete(newsEntity);
    }

    /**
     * パラメータチェック
     * (削除時の必須項目がnullでないかチェック)
     *
     * @param newsEntity ニュースエンティティ
     */
    protected void checkParam(NewsEntity newsEntity) {

        ArgumentCheckUtil.assertNotNull("newsEntity", newsEntity);
        ArgumentCheckUtil.assertGreaterThanZero("newsSeq", newsEntity.getNewsSeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", newsEntity.getShopSeq());
    }

}
