/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.util.List;

/**
 * WEB-API連携 出荷予定日取得APIのリクエストモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No14 from here
public class GetShipmentDateRequest {

    /** 注文者顧客番号 **/
    private Integer orderCustomerNo;

    /** 配送先顧客番号 **/
    private Integer deliveryCustomerNo;

    /** 配送先郵便番号 **/
    private String deliveryZipcode;

    /** 配送会社コード **/
    private String deliveryCompanyCode;

    /** 情報 **/
    private List<GetShipmentDateInfoRequest> info;

    /**
     * @return the orderCustomerNo
     */
    public Integer getOrderCustomerNo() {
        return orderCustomerNo;
    }

    /**
     * @param orderCustomerNo the orderCustomerNo to set
     */
    public void setOrderCustomerNo(Integer orderCustomerNo) {
        this.orderCustomerNo = orderCustomerNo;
    }

    /**
     * @return the deliveryCustomerNo
     */
    public Integer getDeliveryCustomerNo() {
        return deliveryCustomerNo;
    }

    /**
     * @param deliveryCustomerNo the deliveryCustomerNo to set
     */
    public void setDeliveryCustomerNo(Integer deliveryCustomerNo) {
        this.deliveryCustomerNo = deliveryCustomerNo;
    }

    /**
     * @return the deliveryZipcode
     */
    public String getDeliveryZipcode() {
        return deliveryZipcode;
    }

    /**
     * @param deliveryZipcode the deliveryZipcode to set
     */
    public void setDeliveryZipcode(String deliveryZipcode) {
        this.deliveryZipcode = deliveryZipcode;
    }

    /**
     * @return the deliveryCompanyCode
     */
    public String getDeliveryCompanyCode() {
        return deliveryCompanyCode;
    }

    /**
     * @param deliveryCompanyCode the deliveryCompanyCode to set
     */
    public void setDeliveryCompanyCode(String deliveryCompanyCode) {
        this.deliveryCompanyCode = deliveryCompanyCode;
    }

    /**
     * @return the info
     */
    public List<GetShipmentDateInfoRequest> getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(List<GetShipmentDateInfoRequest> info) {
        this.info = info;
    }

}
// 2023-renew No14 to here
