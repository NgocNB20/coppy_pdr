// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsTogetherBuyGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * よく一緒に購入される商品削除<br/>
 *
 * @author hirata
 */
@Component
public class GoodsTogetherBuyGroupDeleteLogicImpl extends AbstractShopLogic
                implements GoodsTogetherBuyGroupDeleteLogic {

    /**
     * よく一緒に購入される商品DAO
     */
    private final GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao;

    @Autowired
    public GoodsTogetherBuyGroupDeleteLogicImpl(GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao) {
        this.goodsTogetherBuyGroupDao = goodsTogetherBuyGroupDao;
    }

    /**
     * 一緒によく購入される商品削除<br/>
     *
     * @param registMethod           登録方法
     * @return 削除件数
     */
    @Override
    public int execute(String registMethod) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("registMethod", registMethod);

        // 有効な確認メール情報取得
        return goodsTogetherBuyGroupDao.deleteByRegistMethod(registMethod);
    }
}
// 2023-renew No21 to here
