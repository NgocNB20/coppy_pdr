/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDetailsDtoListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * アイコン詳細Dtoリスト取得Logic(実装)
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsInformationIconDetailsDtoListGetLogicImpl extends AbstractShopLogic
                implements GoodsInformationIconDetailsDtoListGetLogic {

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconDetailsDtoListGetLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * アイコン詳細Dtoリスト取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return アイコン詳細Dtoリスト
     */
    @Override
    public List<GoodsInformationIconDetailsDto> execute(Integer shopSeq) {

        // アイコン詳細Dto取得
        List<GoodsInformationIconDetailsDto> detailsList =
                        goodsInformationIconDao.getGoodsInformationIconDetailsDtoList(shopSeq);

        return detailsList;
    }

}
