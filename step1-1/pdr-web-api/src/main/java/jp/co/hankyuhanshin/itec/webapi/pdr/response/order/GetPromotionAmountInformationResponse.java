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
 * PDR#15 WEB-API連携 プロモーションAPIのレスポンスモデル（金額情報）<br/>
 *
 * @author k-katoh
 */
public class GetPromotionAmountInformationResponse {

    /** 受注番号 */
    private String orderNo;

    /** 出荷予定日 */
    private String shipmentDate;

    // 2023-renew No14 from here
    /** 配達指定日 */
    private String deliveryDesignatedDay;
    // 2023-renew No14 to here

    /** 入荷予定日 */
    private String stockDate;

    /** 値引 */
    private BigDecimal discountPrice;

    /** 送料 */
    private BigDecimal shippingPrice;

    /** 消費税 */
    private BigDecimal taxPrice;

    /** プロモーションコード */
    private String promotionCode;

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the shipmentDate
     */
    public String getShipmentDate() {
        return shipmentDate;
    }

    /**
     * @param shipmentDate the shipmentDate to set
     */
    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    // 2023-renew No14 from here

    /**
     * @return the deliveryDesignatedDay
     */
    public String getDeliveryDesignatedDay() {
        return deliveryDesignatedDay;
    }

    /**
     * @param deliveryDesignatedDay the deliveryDesignatedDay to set
     */
    public void setDeliveryDesignatedDay(String deliveryDesignatedDay) {
        this.deliveryDesignatedDay = deliveryDesignatedDay;
    }

    // 2023-renew No14 to here

    /**
     * @return the stockDate
     */
    public String getStockDate() {
        return stockDate;
    }

    /**
     * @param stockDate the stockDate to set
     */
    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    /**
     * @return the discountPrice
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * @param discountPrice the discountPrice to set
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * @return the shippingPrice
     */
    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    /**
     * @param shippingPrice the shippingPrice to set
     */
    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /**
     * @return the taxPrice
     */
    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    /**
     * @param taxPrice the taxPrice to set
     */
    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    /**
     * @return the promotionCode
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * @param promotionCode the promotionCode to set
     */
    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }
}
