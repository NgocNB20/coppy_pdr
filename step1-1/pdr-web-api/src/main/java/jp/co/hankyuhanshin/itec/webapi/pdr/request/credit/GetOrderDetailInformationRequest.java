/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.credit;

import java.sql.Timestamp;

/**
 * PDR#351 WEB-API連携 受注詳細情報取得のリクエストモデル<br/>
 *
 * @author junichi-iwata
 */
public class GetOrderDetailInformationRequest {

    /** 顧客番号 */
    private String customerNo;

    /** 出荷状態 */
    private String shipmentStatus;

    /** 出荷日-From */
    private Timestamp shipmentDateFrom;

    /** 出荷日-To */
    private Timestamp shipmentDateTo;

    /** 受注番号 */
    private String orderNo;

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
     * @return the shipmentStatus
     */
    public String getShipmentStatus() {
        return shipmentStatus;
    }

    /**
     * @param shipmentStatus the shipmentStatus to set
     */
    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    /**
     * @return the shipmentDateFrom
     */
    public Timestamp getShipmentDateFrom() {
        return shipmentDateFrom;
    }

    /**
     * @param shipmentDateFrom the shipmentDateFrom to set
     */
    public void setShipmentDateFrom(Timestamp shipmentDateFrom) {
        this.shipmentDateFrom = shipmentDateFrom;
    }

    /**
     * @return the shipmentDateTo
     */
    public Timestamp getShipmentDateTo() {
        return shipmentDateTo;
    }

    /**
     * @param shipmentDateTo the shipmentDateTo to set
     */
    public void setShipmentDateTo(Timestamp shipmentDateTo) {
        this.shipmentDateTo = shipmentDateTo;
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

}
