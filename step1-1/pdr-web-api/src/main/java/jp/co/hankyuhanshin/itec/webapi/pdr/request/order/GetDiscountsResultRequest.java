/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

// PDR#15 20161115 kato 新規作成

/**
 * PDR#15 WEB-API連携 数量割引適用結果取得APIのリクエストモデル<br/>
 *
 * @author kato
 */
public class GetDiscountsResultRequest {

    /** 顧客番号 */
    private String customerNo;

    /** 申込商品 */
    private String goodsCode;

    /** 数量 */
    private String quantity;

    /** 表示順 */
    private String orderDisplay;

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

    /**
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the orderDisplay
     */
    public String getOrderDisplay() {
        return orderDisplay;
    }

    /**
     * @param orderDisplay the orderDisplay to set
     */
    public void setOrderDisplay(String orderDisplay) {
        this.orderDisplay = orderDisplay;
    }
}
