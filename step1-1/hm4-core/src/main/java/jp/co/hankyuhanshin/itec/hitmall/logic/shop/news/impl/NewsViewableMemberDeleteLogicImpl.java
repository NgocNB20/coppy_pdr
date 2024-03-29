/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsViewableMemberDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ニュース表示対象会員削除ロジック<br/>
 */
@Component
public class NewsViewableMemberDeleteLogicImpl extends AbstractShopLogic implements NewsViewableMemberDeleteLogic {

    /**
     * ニュース表示対象会員Dao
     */
    private final NewsViewableMemberDao newsViewableMemberDao;

    @Autowired
    public NewsViewableMemberDeleteLogicImpl(NewsViewableMemberDao newsViewableMemberDao) {
        this.newsViewableMemberDao = newsViewableMemberDao;
    }

    /**
     * ニュース表示対象会員削除
     *
     * @param newsSeq ニュースSEQ
     * @return 処理件数
     */
    @Override
    public int execute(Integer newsSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("newsSeq", newsSeq);

        // 削除処理
        return newsViewableMemberDao.deleteByNewsSeq(newsSeq);
    }
}
