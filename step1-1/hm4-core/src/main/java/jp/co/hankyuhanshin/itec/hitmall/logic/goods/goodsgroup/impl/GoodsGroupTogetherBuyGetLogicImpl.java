// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTogetherBuyGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 過去半年間で購入された商品リスト取得<br/>
 *
 * @author ozaki
 */
@Component
public class GoodsGroupTogetherBuyGetLogicImpl extends AbstractShopLogic implements GoodsGroupTogetherBuyGetLogic {

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    @Autowired
    public GoodsGroupTogetherBuyGetLogicImpl(GoodsGroupDao goodsGroupDao) {
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 過去半年間で購入された商品リスト取得<br/>
     *
     * @return 商品グループSEQリスト
     */
    @Override
    public List<Integer> execute() {
        return goodsGroupDao.getGoodsGroupSeqList();
    }

}
// 2023-renew No21 to here