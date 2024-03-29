/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.math.BigDecimal;

/**
 * PDR#15 WEB-API連携 数量割引情報取得APIのレスポンスモデル<br/>
 *
 * @author AgileJP
 */
public class GetQuantityDiscountSalePriceResponse {

    /** 申込商品 */
    private BigDecimal salePrice;

    /** 数量閾値 */
    private String saleLevel;

    /**
     * @return the salePrice
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice the salePrice to set
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @return the saleLevel
     */
    public String getSaleLevel() {
        return saleLevel;
    }

    /**
     * @param saleLevel the saleLevel to set
     */
    public void setSaleLevel(String saleLevel) {
        this.saleLevel = saleLevel;
    }
}
