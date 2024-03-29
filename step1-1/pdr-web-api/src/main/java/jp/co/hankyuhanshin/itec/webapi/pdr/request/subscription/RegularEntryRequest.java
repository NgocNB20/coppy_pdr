/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription;

/**
 * WEB-API連携定期便登録のリクエストモデル<br/>
 *
 * @author tt32117
 */
public class RegularEntryRequest {

    /** 顧客番号 */
    private String customerNo;

    /** 区分 */
    private String type;

    /** 詳細番号 */
    private String detailNo;

    /** 申込商品 */
    private String goodsCode;

    /** 数量 */
    private String quantity;

    /** サイクル */
    private String cycle;

    /** スキップフラグ */
    private String skipFlag;

    /** お届け先顧客番号 */
    private String receiveCustomerNo;

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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the detailNo
     */
    public String getDetailNo() {
        return detailNo;
    }

    /**
     * @param detailNo the detailNo to set
     */
    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
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
     * @return the cycle
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * @param cycle the cycle to set
     */
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    /**
     * @return the skipFlag
     */
    public String getSkipFlag() {
        return skipFlag;
    }

    /**
     * @param skipFlag the skipFlag to set
     */
    public void setSkipFlag(String skipFlag) {
        this.skipFlag = skipFlag;
    }

    /**
     * @return the receiveCustomerNo
     */
    public String getReceiveCustomerNo() {
        return receiveCustomerNo;
    }

    /**
     * @param receiveCustomerNo the receiveCustomerNo to set
     */
    public void setReceiveCustomerNo(String receiveCustomerNo) {
        this.receiveCustomerNo = receiveCustomerNo;
    }
}
