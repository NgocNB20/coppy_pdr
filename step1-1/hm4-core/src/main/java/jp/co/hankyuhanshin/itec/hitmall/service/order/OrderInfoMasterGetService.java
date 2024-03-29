/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;

/**
 * 注文情報マスタ取得Service<br/>
 *
 * @author h_hakogi
 */
public interface OrderInfoMasterGetService {

    /**
     * サービス実行<br/>
     *
     * @param cartDto カートDto
     * @return 注文情報マスタDto
     */
    OrderInfoMasterDto execute(CartDto cartDto);
}
