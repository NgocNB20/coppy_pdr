/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.math.BigDecimal;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 消費税率取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 *
 */
public class GetConsumptionTaxRateResponse {

    /** 申込商品 */
    private String goodsCode;

    /** 消費税率 */
    private BigDecimal taxRate;

    /**
     * @return the goodsCode
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * @param goodsCode the goodsCode to set
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
