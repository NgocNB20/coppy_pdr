/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Uk タグログ連携
 * @author tt32117
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
// 2023-renew No3-taglog from here
public class UkTaglogCheckoutInfo {

    /**
     * 商品管理番号 ※checkoutString作成用
     */
    private String goodsGroupCode;

    /**
     * 商品数量（合計） ※checkoutString作成用
     */
    private BigDecimal goodsCountTotal;

    /**
     * 商品価格（税抜） ※checkoutString作成用
     */
    private BigDecimal goodsPrice;

    /**
     * UK連携値
     */
    private String checkoutString;

}
// 2023-renew No3-taglog to here
