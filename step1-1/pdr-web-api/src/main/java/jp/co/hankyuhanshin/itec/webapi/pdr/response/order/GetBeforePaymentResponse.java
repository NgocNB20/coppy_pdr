/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

/**
 * WEB-API連携 前回支払方法取得APIのレスポンスモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No14 from here
public class GetBeforePaymentResponse {

    /** 前回支払方法 */
    private String beforePaymentType;

    /** 決済ID */
    private String paymentId;

    /**
     * @return the beforePaymentType
     */
    public String getBeforePaymentType() {
        return beforePaymentType;
    }

    /**
     * @param beforePaymentType the beforePaymentType to set
     */
    public void setBeforePaymentType(String beforePaymentType) {
        this.beforePaymentType = beforePaymentType;
    }

    /**
     * @return the paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId the paymentId to set
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
// 2023-renew No14 to here
