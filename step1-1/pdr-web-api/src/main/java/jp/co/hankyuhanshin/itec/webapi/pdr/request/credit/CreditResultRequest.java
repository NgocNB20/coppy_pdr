/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.credit;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * PDR#352 WEB-API連携 オーソリ結果連携のリクエストモデル<br/>
 *
 * @author r-yasunaga
 */

public class CreditResultRequest {

    /** 受注番号 */
    private String orderNo;

    /** クレジット決済ID */
    private String creditPaymentID;

    /** マーチャント取引ID */
    private String tradingID;

    /** 請求金額 */
    private BigDecimal billingPrice;

    /** 与信取得日時 */
    private Timestamp creditDate;

    /** 担当者コード */
    private String userID;

    /**
     * @return the orderNo;
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
     * @return the creditPaymentID;
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
     * @return the creditDate
     */
    public Timestamp getCreditDate() {
        return creditDate;
    }

    /**
     * @param creditDate the creditDate to set
     */
    public void setCreditDate(Timestamp creditDate) {
        this.creditDate = creditDate;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
