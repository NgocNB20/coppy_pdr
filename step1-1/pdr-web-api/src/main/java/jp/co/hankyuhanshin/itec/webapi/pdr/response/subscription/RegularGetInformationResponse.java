/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription;

/**
 * WEB-API連携 お届け曜日・支払方法取得のレスポンスモデル<br/>
 *
 * @author tt32117
 */
public class RegularGetInformationResponse {

    /** お届け曜日 */
    private String weekday;

    /** 支払方法 */
    private String paymentType;

    /** 顧客カードID */
    private String customerCardId;

    /**
     * @return the weekday
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     * @param weekday the weekday to set
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the customerCardId
     */
    public String getCustomerCardId() {
        return customerCardId;
    }

    /**
     * @param customerCardId the customerCardId to set
     */
    public void setCustomerCardId(String customerCardId) {
        this.customerCardId = customerCardId;
    }
}
