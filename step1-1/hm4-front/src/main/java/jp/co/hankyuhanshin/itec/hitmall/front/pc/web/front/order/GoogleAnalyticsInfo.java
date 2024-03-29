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
import java.util.List;

/**
 * Google Analytics
 * eコマース用データ送信
 *
 * @author ota-s5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAnalyticsInfo {

    /**
     * 受注コード
     */
    private String orderCode;

    /**
     * ショップ名
     */
    private String shopName;

    /**
     * 受注金額合計
     */
    private BigDecimal orderPriceTotal;

    /**
     * 消費税額
     */
    private BigDecimal taxPrice;

    /**
     * 送料
     */
    private BigDecimal carriage;

    /**
     * 商品リスト
     */
    private List<GoogleAnalyticsSalesItem> goodsItemList;

}
