/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsViewableMemberCountGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ニュース表示対象会員件数取得ロジック<br/>
 */
@Component
public class NewsViewableMemberCountGetLogicImpl extends AbstractShopLogic implements NewsViewableMemberCountGetLogic {

    /**
     * ニュース表示対象会員Dao
     */
    private final NewsViewableMemberDao newsViewableMemberDao;

    @Autowired
    public NewsViewableMemberCountGetLogicImpl(NewsViewableMemberDao newsViewableMemberDao) {
        this.newsViewableMemberDao = newsViewableMemberDao;
    }

    /**
     * ニュース表示対象会員件数取得処理
     *
     * @param newsSeq ニュースSEQ
     * @return 件数
     */
    @Override
    public int execute(Integer newsSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("newsSeq", newsSeq);

        // 件数取得
        return newsViewableMemberDao.getCountByNewsSeq(newsSeq);
    }

}
