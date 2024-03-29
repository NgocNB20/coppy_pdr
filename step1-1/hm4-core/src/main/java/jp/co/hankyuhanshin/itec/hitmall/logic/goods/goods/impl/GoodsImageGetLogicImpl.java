/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsImageGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品規格画像リスト取得<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Component
public class GoodsImageGetLogicImpl extends AbstractShopLogic implements GoodsImageGetLogic {

    /**
     * 商品DAO
     */
    private final GoodsImageDao goodsImageDao;

    @Autowired
    public GoodsImageGetLogicImpl(GoodsImageDao goodsImageDao) {
        this.goodsImageDao = goodsImageDao;
    }

    /**
     * 商品規格画像リスト取得<br/>
     *
     * @param goodsGroupSeq  商品グループSeq
     */
    @Override
    public List<GoodsImageEntity> execute(Integer goodsGroupSeq) {
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        goodsGroupSeqList.add(goodsGroupSeq);
        return execute(goodsGroupSeqList);
    }

    /**
     * 商品規格画像リスト取得<br/>
     *
     * @param goodsGroupSeqList  商品グループSeqリスト
     */
    @Override
    public List<GoodsImageEntity> execute(List<Integer> goodsGroupSeqList) {
        return goodsImageDao.getGoodsImageListByGoodsGroupSeqList(goodsGroupSeqList);
    }
}
