/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 配送可否取得APIのリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetShippingProprietyRequest {

    /** 注文者顧客番号 */
    private String orderCustomerNo;

    /** 配送先顧客番号 */
    private String deliveryCustomerNo;

    /** 申込商品（パイプ区切り） */
    private String goodsCode;

    /**
     * @return the orderCustomerNo
     */
    public String getOrderCustomerNo() {
        return orderCustomerNo;
    }

    /**
     * @param orderCustomerNo the orderCustomerNo to set
     */
    public void setOrderCustomerNo(String orderCustomerNo) {
        this.orderCustomerNo = orderCustomerNo;
    }

    /**
     * @return the deliveryCustomerNo
     */
    public String getDeliveryCustomerNo() {
        return deliveryCustomerNo;
    }

    /**
     * @param deliveryCustomerNo the deliveryCustomerNo to set
     */
    public void setDeliveryCustomerNo(String deliveryCustomerNo) {
        this.deliveryCustomerNo = deliveryCustomerNo;
    }

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
}
