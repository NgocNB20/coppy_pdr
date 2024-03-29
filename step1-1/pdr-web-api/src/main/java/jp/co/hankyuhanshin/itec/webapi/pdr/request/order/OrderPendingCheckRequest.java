/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.math.BigDecimal;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 注文保留チェックAPIのリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class OrderPendingCheckRequest {

    /** 顧客番号 */
    private String customerNo;

    /** 支払方法 */
    private String paymentType;

    /** 注文金額合計 */
    private BigDecimal orderTotalPrice;

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
     * @return the orderTotalPrice
     */
    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    /**
     * @param orderTotalPrice the orderTotalPrice to set
     */
    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
}
