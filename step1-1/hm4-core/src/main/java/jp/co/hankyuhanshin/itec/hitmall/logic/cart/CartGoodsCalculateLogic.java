/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;

/**
 * カート内計算<br/>
 *
 * @author ozaki
 */
public interface CartGoodsCalculateLogic {

    // LCC0009

    /**
     * カート内計算<br/>
     *
     * @param cartDto カートDTO
     */
    void execute(CartDto cartDto);
}
