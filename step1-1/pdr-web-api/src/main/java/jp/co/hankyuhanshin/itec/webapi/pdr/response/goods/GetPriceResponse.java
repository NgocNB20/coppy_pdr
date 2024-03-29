/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.math.BigDecimal;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 価格情報取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetPriceResponse {

    /** 申込商品 */
    private String goodsCode;

    // 2023-renew AddNo5 from here
    /** 価格（最低） */
    private BigDecimal priceLow;

    /** 価格（最高） */
    private BigDecimal priceHight;

    /** セール価格（最低） */
    private BigDecimal salePriceLow;

    /** セール価格（最高） */
    private BigDecimal salePriceHight;
    // 2023-renew AddNo5 to here

    /** セールコメント */
    private String saleComment;

    // 2023-renew No11,33,75,112 from here
    /** 販売制御区分 */
    private Integer saleControl;
    // 2023-renew No11,33,75,112 to here

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

    // 2023-renew AddNo5 from here
    /**
     * @return the priceLow
     */
    public BigDecimal getPriceLow() {
        return priceLow;
    }

    /**
     * @param priceLow the goodsCode to set
     */
    public void setPriceLow(BigDecimal priceLow) {
        this.priceLow = priceLow;
    }

    /**
     * @return the priceHight
     */
    public BigDecimal getPriceHight() {
        return priceHight;
    }

    /**
     * @param priceHight the goodsCode to set
     */
    public void setPriceHight(BigDecimal priceHight) {
        this.priceHight = priceHight;
    }

    /**
     * @return the salePriceLow
     */
    public BigDecimal getSalePriceLow() {
        return salePriceLow;
    }

    /**
     * @param salePriceLow the goodsCode to set
     */
    public void setSalePriceLow(BigDecimal salePriceLow) {
        this.salePriceLow = salePriceLow;
    }

    /**
     * @return the salePriceHight
     */
    public BigDecimal getSalePriceHight() {
        return salePriceHight;
    }

    /**
     * @param salePriceHight the goodsCode to set
     */
    public void setSalePriceHight(BigDecimal salePriceHight) {
        this.salePriceHight = salePriceHight;
    }
    // 2023-renew AddNo5 to here

    /**
     * @return the saleComment
     */
    public String getSaleComment() {
        return saleComment;
    }

    /**
     * @param saleComment the saleComment to set
     */
    public void setSaleComment(String saleComment) {
        this.saleComment = saleComment;
    }

    // 2023-renew No11,33,75,112 from here
    /**
     * @return the saleControl
     */
    public Integer getSaleControl() {
        return saleControl;
    }

    /**
     * @param saleControl the saleControl to set
     */
    public void setSaleControl(Integer saleControl) {
        this.saleControl = saleControl;
    }
    // 2023-renew No11,33,75,112 to here
}
