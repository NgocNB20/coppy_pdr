/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Google Analytics ModelItem
 * eコマース用データ送信
 *
 * @author ota-s5
 */
@Data
public class GoogleAnalyticsSalesItem {

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 規格値１
     */
    private String unitValue1;

    /**
     * 規格値２
     */
    private String unitValue2;

    /**
     * カテゴリー名
     */
    private String category;

    /**
     * 商品単価
     */
    private BigDecimal goodsPrice;

    /**
     * 商品数量
     */
    private BigDecimal orderGoodsCount;

}
