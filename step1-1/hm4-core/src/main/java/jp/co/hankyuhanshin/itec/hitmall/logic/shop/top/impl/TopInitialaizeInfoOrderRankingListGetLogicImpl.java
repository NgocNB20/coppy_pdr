/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.top.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.top.GoodsRankingDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.top.TopInitialaizeInfoOrderRankingListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * トップ画面初期表示用Logic実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Component
public class TopInitialaizeInfoOrderRankingListGetLogicImpl extends AbstractShopLogic
                implements TopInitialaizeInfoOrderRankingListGetLogic {
    /**
     * 商品ランキングDao
     */
    private final GoodsRankingDao goodsRankingDao;

    @Autowired
    public TopInitialaizeInfoOrderRankingListGetLogicImpl(GoodsRankingDao goodsRankingDao) {
        this.goodsRankingDao = goodsRankingDao;
    }

    /**
     * 商品ランキング情報を取得します
     *
     * @param shopSeq Integer
     * @return List&lt;GoodsRankingEntity&gt;
     */
    @Override
    public List<GoodsRankingEntity> execute(Integer shopSeq) {
        return goodsRankingDao.getOrderRanking(shopSeq);
    }

}
