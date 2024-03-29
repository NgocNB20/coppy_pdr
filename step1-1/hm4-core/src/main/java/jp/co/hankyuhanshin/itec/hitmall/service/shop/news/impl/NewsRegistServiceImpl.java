/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.news.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.NewsRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ニュース登録
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class NewsRegistServiceImpl extends AbstractShopService implements NewsRegistService {

    /**
     * ニュース登録Logic
     */
    private final NewsRegistLogic newsRegistLogic;

    @Autowired
    public NewsRegistServiceImpl(NewsRegistLogic newsRegistLogic) {
        this.newsRegistLogic = newsRegistLogic;
    }

    /**
     * ニュース登録
     *
     * @param newsEntity ニュースエンティティ
     * @return 処理件数
     */
    @Override
    public int execute(NewsEntity newsEntity) {

        // パラメータチェック
        this.checkParam(newsEntity);
        // 共通情報チェック
        Integer shopSeq = 1001;

        // 共通情報セット
        newsEntity.setShopSeq(shopSeq);

        // 登録
        int result = this.insert(newsEntity);

        return result;
    }

    /**
     * パラメータチェック
     * (登録時の必須項目がnullでないかチェック)
     *
     * @param newsEntity ニュースエンティティ
     */
    protected void checkParam(NewsEntity newsEntity) {

        ArgumentCheckUtil.assertNotNull("newsEntity", newsEntity);
        ArgumentCheckUtil.assertNotNull("newsTime", newsEntity.getNewsTime());
        ArgumentCheckUtil.assertNotNull("newsOpenStatusPC", newsEntity.getNewsOpenStatusPC());
        ArgumentCheckUtil.assertNotNull("newsOpenStatusMB", newsEntity.getNewsOpenStatusMB());

    }

    /**
     * ニュース登録処理
     *
     * @param newsEntity ニュースエンティティ
     * @return 処理件数
     */
    protected int insert(NewsEntity newsEntity) {
        int result = newsRegistLogic.execute(newsEntity);
        if (result == 0) {
            throwMessage(MSGCD_NEWS_REGIST_FAIL);
        }

        return result;
    }
}
