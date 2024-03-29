/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsEntityGetByGoodsCodeLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品エンティティ取得（商品コード）<br/>
 * 商品コード、ショップSEQをキーに商品エンティティを取得する。<br/>
 *
 * @author MN7017
 * @version $Revision: 1.1 $
 */
@Component
public class GoodsEntityGetByGoodsCodeLogicImpl extends AbstractShopLogic implements GoodsEntityGetByGoodsCodeLogic {

    /**
     * 商品Dao<br/>
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsEntityGetByGoodsCodeLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 取得処理実行<br/>
     * メソッドの説明・概要<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param goodsCode 商品コード
     * @return 商品エンティティ
     */
    @Override
    public GoodsEntity execute(Integer shopSeq, String goodsCode) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("goodsCode", goodsCode);

        // 取得処理実行
        return goodsDao.getGoodsByCode(shopSeq, goodsCode);

    }
}
