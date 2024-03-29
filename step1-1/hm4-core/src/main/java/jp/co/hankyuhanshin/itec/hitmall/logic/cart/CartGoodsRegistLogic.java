/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;

/**
 * カート商品登録<br/>
 * カートに商品を登録する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface CartGoodsRegistLogic {

    /**
     * 同一商品カートマージフラグ<br/>
     * <code>CART_GOODS_MERGE</code><br/>
     */
    public static final String CART_GOODS_MERGE = "cartgoods.merge";

    /**
     * カート商品登録<br/>
     * カートに商品を登録する。<br/>
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 登録件数
     */
    int execute(CartGoodsEntity cartGoodsEntity);

}
