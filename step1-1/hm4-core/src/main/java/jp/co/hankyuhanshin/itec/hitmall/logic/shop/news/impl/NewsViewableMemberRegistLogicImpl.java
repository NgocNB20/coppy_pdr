/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsViewableMemberEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsViewableMemberRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * ニュース表示対象会員登録ロジック<br/>
 */
@Component
public class NewsViewableMemberRegistLogicImpl extends AbstractShopLogic implements NewsViewableMemberRegistLogic {

    /**
     * ニュース表示対象会員Dao
     */
    private final NewsViewableMemberDao newsViewableMemberDao;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    @Autowired
    public NewsViewableMemberRegistLogicImpl(NewsViewableMemberDao newsViewableMemberDao, DateUtility dateUtility) {
        this.newsViewableMemberDao = newsViewableMemberDao;
        this.dateUtility = dateUtility;
    }

    /**
     * ニュース表示対象会員情報登録処理<br/>
     *
     * @param newsSeq           ニュースSEQ
     * @param memberInfoSeqList 対象会員リスト
     * @return 処理件数
     */
    @Override
    public int execute(Integer newsSeq, List<Integer> memberInfoSeqList) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("newsSeq", newsSeq);
        ArgumentCheckUtil.assertNotNull("memberInfoSeqList", memberInfoSeqList);

        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        Timestamp currentTime = dateUtility.getCurrentTime();

        // ニュース表示対象会員情報登録
        for (Integer memberInfoSeq : memberInfoSeqList) {
            NewsViewableMemberEntity entity = ApplicationContextUtility.getBean(NewsViewableMemberEntity.class);

            // ニュースSEQを設定
            entity.setNewsSeq(newsSeq);
            // 会員SEQを設定
            entity.setMemberInfoSeq(memberInfoSeq);
            // ショップSEQを設定
            entity.setShopSeq(shopSeq);

            entity.setRegistTime(currentTime);
            entity.setUpdateTime(currentTime);

            newsViewableMemberDao.insert(entity);
        }
        return 0;
    }

}
