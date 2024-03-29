/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.credit;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * PDR#351 WEB-API連携 受注詳細情報取得のレスポンスモデル<br/>
 *
 * @author junichi-iwata
 */
public class GetOrderDetailInformationResponse {

    /** 顧客番号 */
    private String customerNo;

    /** 事業所名 */
    private String officeName;

    /** 電話番号 */
    private String tel;

    /** 受注番号 */
    private String orderNo;

    /** 受付方法 */
    private String receptionTypeName;

    /** 受注日 */
    private Timestamp orderDate;

    /** 出荷予定日 */
    private Timestamp estimatedShipmentDate;

    /** 出荷日 */
    private Timestamp shipmentDate;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 請求金額 */
    private BigDecimal billingPrice;

    /** 預り金充当金額 */
    private BigDecimal depositPrice;

    /** 請求残高金額 */
    private BigDecimal billingBalancePrice;

    /** クレジット決済ID */
    private String creditPaymentID;

    /** マーチャント取引ID */
    private String tradingID;

    /** 決済処理結果 */
    private String creditResult;

    /** 保留区分 */
    private String holdType;

    /** 保留区分名称 */
    private String holdTypeName;

    /**
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * @return the officeName
     */
    public String getOfficeName() {
        return officeName;
    }

    /**
     * @param officeName the officeName to set
     */
    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

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
     * @return the receptionTypeName
     */
    public String getReceptionTypeName() {
        return receptionTypeName;
    }

    /**
     * @param receptionTypeName the receptionTypeName to set
     */
    public void setReceptionTypeName(String receptionTypeName) {
        this.receptionTypeName = receptionTypeName;
    }

    /**
     * @return the orderDate
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the estimatedShipmentDate
     */
    public Timestamp getEstimatedShipmentDate() {
        return estimatedShipmentDate;
    }

    /**
     * @param estimatedShipmentDate the estimatedShipmentDate to set
     */
    public void setEstimatedShipmentDate(Timestamp estimatedShipmentDate) {
        this.estimatedShipmentDate = estimatedShipmentDate;
    }

    /**
     * @return the shipmentDate
     */
    public Timestamp getShipmentDate() {
        return shipmentDate;
    }

    /**
     * @param shipmentDate the shipmentDate to set
     */
    public void setShipmentDate(Timestamp shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    /**
     * @return the deliveryDesignatedDay
     */
    public Timestamp getDeliveryDesignatedDay() {
        return deliveryDesignatedDay;
    }

    /**
     * @param deliveryDesignatedDay the deliveryDesignatedDay to set
     */
    public void setDeliveryDesignatedDay(Timestamp deliveryDesignatedDay) {
        this.deliveryDesignatedDay = deliveryDesignatedDay;
    }

    /**
     * @return the billingPrice
     */
    public BigDecimal getBillingPrice() {
        return billingPrice;
    }

    /**
     * @param billingPrice the billingPrice to set
     */
    public void setBillingPrice(BigDecimal billingPrice) {
        this.billingPrice = billingPrice;
    }

    /**
     * @return the depositPrice
     */
    public BigDecimal getDepositPrice() {
        return depositPrice;
    }

    /**
     * @param depositPrice the depositPrice to set
     */
    public void setDepositPrice(BigDecimal depositPrice) {
        this.depositPrice = depositPrice;
    }

    /**
     * @return the billingBalancePrice
     */
    public BigDecimal getBillingBalancePrice() {
        return billingBalancePrice;
    }

    /**
     * @param billingBalancePrice the billingBalancePrice to set
     */
    public void setBillingBalancePrice(BigDecimal billingBalancePrice) {
        this.billingBalancePrice = billingBalancePrice;
    }

    /**
     * @return the creditPaymentID
     */
    public String getCreditPaymentID() {
        return creditPaymentID;
    }

    /**
     * @param creditPaymentID the creditPaymentID to set
     */
    public void setCreditPaymentID(String creditPaymentID) {
        this.creditPaymentID = creditPaymentID;
    }

    /**
     * @return the tradingID
     */
    public String getTradingID() {
        return tradingID;
    }

    /**
     * @param tradingID the tradingID to set
     */
    public void setTradingID(String tradingID) {
        this.tradingID = tradingID;
    }

    /**
     * @return the creditResult
     */
    public String getCreditResult() {
        return creditResult;
    }

    /**
     * @param creditResult the creditResult to set
     */
    public void setCreditResult(String creditResult) {
        this.creditResult = creditResult;
    }

    /**
     * @return the holdType
     */
    public String getHoldType() {
        return holdType;
    }

    /**
     * @param holdType the holdType to set
     */
    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    /**
     * @return the holdTypeName
     */
    public String getHoldTypeName() {
        return holdTypeName;
    }

    /**
     * @param holdTypeName the holdTypeName to set
     */
    public void setHoldTypeName(String holdTypeName) {
        this.holdTypeName = holdTypeName;
    }

}
