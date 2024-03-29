/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.sql.Timestamp;
import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 出荷情報取得APIのリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetShipmentInformationResponse {

    /** 受注番号 */
    private String orderNo;

    /** 事業所名 */
    private String officeName;

    /** お届け先事業所名 */
    private String receiveOfficeName;

    /** お届け先郵便番号 */
    private String receiveZipcode;

    /** お届け先住所(都道府県・市区町村) */
    private String receiveAddress1;

    /** お届け先住所(丁目・番地) */
    private String receiveAddress2;

    /** お届け先住所(建物名・部屋番号) */
    private String receiveAddress3;

    /** お届け先住所(方書1) */
    private String receiveAddress4;

    /** お届け先住所(方書2) */
    private String receiveAddress5;

    /** 小計 */
    private String subTotalPrice;

    /** 値引 */
    private String discountPrice;

    /** 送料 */
    private String shippingPrice;

    /** 消費税 */
    private String taxPrice;

    /** 合計金額 */
    private String totalPrice;

    /** 配送方法 */
    private String deliveryName;

    /** 支払方法 */
    private String paymentName;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 配達指定時間 */
    private String deliveryDesignatedTimeName;

    /** 配送確認URL */
    private String deliveryComfirmURL;

    /** 送り状番号 */
    private String invoiceNo;

    /** メールアドレス */
    private String mailAddress;

    /** 商品リスト */
    private List<GetShipmentInformationGoodsResponse> goodsList;

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
     * @return the receiveOfficeName
     */
    public String getReceiveOfficeName() {
        return receiveOfficeName;
    }

    /**
     * @param receiveOfficeName the receiveOfficeName to set
     */
    public void setReceiveOfficeName(String receiveOfficeName) {
        this.receiveOfficeName = receiveOfficeName;
    }

    /**
     * @return the receiveZipcode
     */
    public String getReceiveZipcode() {
        return receiveZipcode;
    }

    /**
     * @param receiveZipcode the receiveZipcode to set
     */
    public void setReceiveZipcode(String receiveZipcode) {
        this.receiveZipcode = receiveZipcode;
    }

    /**
     * @return the receiveAddress1
     */
    public String getReceiveAddress1() {
        return receiveAddress1;
    }

    /**
     * @param receiveAddress1 the receiveAddress1 to set
     */
    public void setReceiveAddress1(String receiveAddress1) {
        this.receiveAddress1 = receiveAddress1;
    }

    /**
     * @return the receiveAddress2
     */
    public String getReceiveAddress2() {
        return receiveAddress2;
    }

    /**
     * @param receiveAddress2 the receiveAddress2 to set
     */
    public void setReceiveAddress2(String receiveAddress2) {
        this.receiveAddress2 = receiveAddress2;
    }

    /**
     * @return the receiveAddress3
     */
    public String getReceiveAddress3() {
        return receiveAddress3;
    }

    /**
     * @param receiveAddress3 the receiveAddress3 to set
     */
    public void setReceiveAddress3(String receiveAddress3) {
        this.receiveAddress3 = receiveAddress3;
    }

    /**
     * @return the receiveAddress4
     */
    public String getReceiveAddress4() {
        return receiveAddress4;
    }

    /**
     * @param receiveAddress4 the receiveAddress4 to set
     */
    public void setReceiveAddress4(String receiveAddress4) {
        this.receiveAddress4 = receiveAddress4;
    }

    /**
     * @return the receiveAddress5
     */
    public String getReceiveAddress5() {
        return receiveAddress5;
    }

    /**
     * @param receiveAddress5 the receiveAddress5 to set
     */
    public void setReceiveAddress5(String receiveAddress5) {
        this.receiveAddress5 = receiveAddress5;
    }

    /**
     * @return the subTotalPrice
     */
    public String getSubTotalPrice() {
        return subTotalPrice;
    }

    /**
     * @param subTotalPrice the subTotalPrice to set
     */
    public void setSubTotalPrice(String subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    /**
     * @return the discountPrice
     */
    public String getDiscountPrice() {
        return discountPrice;
    }

    /**
     * @param discountPrice the discountPrice to set
     */
    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * @return the shippingPrice
     */
    public String getShippingPrice() {
        return shippingPrice;
    }

    /**
     * @param shippingPrice the shippingPrice to set
     */
    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /**
     * @return the taxPrice
     */
    public String getTaxPrice() {
        return taxPrice;
    }

    /**
     * @param taxPrice the taxPrice to set
     */
    public void setTaxPrice(String taxPrice) {
        this.taxPrice = taxPrice;
    }

    /**
     * @return the totalPrice
     */
    public String getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the deliveryName
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * @param deliveryName the deliveryName to set
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    /**
     * @return the paymentName
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * @param paymentName the paymentName to set
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
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
     * @return the deliveryDesignatedTimeName
     */
    public String getDeliveryDesignatedTimeName() {
        return deliveryDesignatedTimeName;
    }

    /**
     * @param deliveryDesignatedTimeName the deliveryDesignatedTimeName to set
     */
    public void setDeliveryDesignatedTimeName(String deliveryDesignatedTimeName) {
        this.deliveryDesignatedTimeName = deliveryDesignatedTimeName;
    }

    /**
     * @return the deliveryComfirmURL
     */
    public String getDeliveryComfirmURL() {
        return deliveryComfirmURL;
    }

    /**
     * @param deliveryComfirmURL the deliveryComfirmURL to set
     */
    public void setDeliveryComfirmURL(String deliveryComfirmURL) {
        this.deliveryComfirmURL = deliveryComfirmURL;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * @param mailAddress the mailAddress to set
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * @return the goodsList
     */
    public List<GetShipmentInformationGoodsResponse> getGoodsList() {
        return goodsList;
    }

    /**
     * @param goodsList the goodsList to set
     */
    public void setGoodsList(List<GetShipmentInformationGoodsResponse> goodsList) {
        this.goodsList = goodsList;
    }

}
