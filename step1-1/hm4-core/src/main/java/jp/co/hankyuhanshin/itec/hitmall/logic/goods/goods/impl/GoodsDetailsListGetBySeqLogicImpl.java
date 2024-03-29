/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsListGetBySeqLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品エンティティリスト取得ロジック<br/>
 *
 * @author USER
 * @version $Revision: 1.3 $
 */
@Component
public class GoodsDetailsListGetBySeqLogicImpl extends AbstractShopLogic implements GoodsDetailsListGetBySeqLogic {

    /**
     * 商品Dao
     */
    private final GoodsDao goodsDao;

    /**
     * 商品規格画像Dao
     */
    private final GoodsImageDao goodsImageDao;

    @Autowired
    public GoodsDetailsListGetBySeqLogicImpl(GoodsDao goodsDao, GoodsImageDao goodsImageDao) {
        this.goodsDao = goodsDao;
        this.goodsImageDao = goodsImageDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 商品エンティティリスト
     */
    @Override
    public List<GoodsDetailsDto> execute(List<Integer> goodsSeqList) {
        if (goodsSeqList == null || goodsSeqList.isEmpty()) {
            return new ArrayList<>();
        }

        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDao.getGoodsDetailsList(goodsSeqList);

        this.settingGoodsUnitImage(goodsDetailsDtoList, goodsSeqList);

        return goodsDetailsDtoList;
    }

    /**
     * 設定商品ユニットイメージ
     *
     * @param goodsDetailsDtoList
     * @param goodsSeqList
     *
     */
    protected void settingGoodsUnitImage(List<GoodsDetailsDto> goodsDetailsDtoList, List<Integer> goodsSeqList) {
        if (!goodsSeqList.isEmpty()) {
            List<GoodsImageEntity> goodsImageEntityList = goodsImageDao.getGoodsImageListByGoodsSeqList(goodsSeqList);

            goodsDetailsDtoList.forEach(goods -> {
                goods.setUnitImage(goodsImageEntityList.stream()
                                                       .filter(item -> item.getGoodsSeq().equals(goods.getGoodsSeq()))
                                                       .findFirst()
                                                       .orElse(null));
            });
        }
    }
}
