/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsStatusCheckLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品の公開&販売状態チェック
 *
 * @author kimura
 */
@Component
public class GoodsStatusCheckLogicImpl extends AbstractShopLogic implements GoodsStatusCheckLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsStatusCheckLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * * 商品の公開&販売状態チェック
     *
     * @param shopSeq   ショップSEQ
     * @param goodsCode 商品コード
     * @return true 公開&販売ともに削除ではない, false 公開か販売状態が削除されている
     */
    @Override
    public boolean execute(Integer shopSeq, String goodsCode) {
        if (goodsDao.getStatus(shopSeq, goodsCode) > 0) {
            return true;
        }
        return false;
    }
}
