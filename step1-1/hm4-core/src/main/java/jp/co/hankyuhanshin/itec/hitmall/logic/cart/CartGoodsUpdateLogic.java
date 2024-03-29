/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;

/**
 * カート商品更新<br/>
 * カートに商品を更新する。<br/>
 *
 * @author h_hakogi
 */
public interface CartGoodsUpdateLogic {

    /**
     * カート商品更新<br/>
     * カートに商品を更新する。<br/>
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 更新件数
     */
    int execute(CartGoodsEntity cartGoodsEntity);
}
