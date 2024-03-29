/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 プロモーションAPIのレスポンスモデル（同梱情報）<br/>
 *
 * @author k-katoh
 */
public class GetPromotionBundledInformationResponse {

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

    /** 明細番号 */
    private String orderSeq;

    /** 同梱商品 */
    private String bundleGoodsCode;

    /** 同梱数量 */
    private String bundleGoodsCount;

    /** 納品書印字フラグ */
    private String deliverySlipFlag;

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
     * @return the orderSeq
     */
    public String getOrderSeq() {
        return orderSeq;
    }

    /**
     * @param orderSeq the orderSeq to set
     */
    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    /**
     * @return the bundleGoodsCode
     */
    public String getBundleGoodsCode() {
        return bundleGoodsCode;
    }

    /**
     * @param bundleGoodsCode the bundleGoodsCode to set
     */
    public void setBundleGoodsCode(String bundleGoodsCode) {
        this.bundleGoodsCode = bundleGoodsCode;
    }

    /**
     * @return the bundleGoodsCount
     */
    public String getBundleGoodsCount() {
        return bundleGoodsCount;
    }

    /**
     * @param bundleGoodsCount the bundleGoodsCount to set
     */
    public void setBundleGoodsCount(String bundleGoodsCount) {
        this.bundleGoodsCount = bundleGoodsCount;
    }

    /**
     * @return the deliverySlipFlag
     */
    public String getDeliverySlipFlag() {
        return deliverySlipFlag;
    }

    /**
     * @param deliverySlipFlag the deliverySlipFlag to set
     */
    public void setDeliverySlipFlag(String deliverySlipFlag) {
        this.deliverySlipFlag = deliverySlipFlag;
    }
}
