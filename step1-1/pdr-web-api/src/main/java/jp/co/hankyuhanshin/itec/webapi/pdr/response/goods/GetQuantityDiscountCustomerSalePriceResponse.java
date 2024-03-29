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
public class GetQuantityDiscountCustomerSalePriceResponse {

    /** セール価格 */
    private BigDecimal customerSalePrice;

    /** 数量閾値 */
    private String customerSaleLevel;

    /**
     * @return the customerSalePrice
     */
    public BigDecimal getCustomerSalePrice() {
        return customerSalePrice;
    }

    /**
     * @param customerSalePrice the customerSalePrice to set
     */
    public void setCustomerSalePrice(BigDecimal customerSalePrice) {
        this.customerSalePrice = customerSalePrice;
    }

    /**
     * @return the customerSaleLevel
     */
    public String getCustomerSaleLevel() {
        return customerSaleLevel;
    }

    /**
     * @param customerSaleLevel the customerSaleLevel to set
     */
    public void setCustomerSaleLevel(String customerSaleLevel) {
        this.customerSaleLevel = customerSaleLevel;
    }
}
