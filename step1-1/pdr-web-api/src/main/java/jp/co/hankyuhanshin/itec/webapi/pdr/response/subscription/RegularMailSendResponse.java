/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription;

import java.sql.Timestamp;

/**
 *
 * 定期便出荷情報メール送信API レスポンスモデル<br/>
 *
 * @author Agilejp
 *
 */
public class RegularMailSendResponse {

    /** メール区分 */
    private String mailType;
    /** 顧客番号 */
    private Integer customerNo;
    /** 受注番号 */
    private Integer orderNo;
    /** 事業所名 */
    private String officeName;
    /** 支払方法 */
    private String paymentName;
    /** 配送方法 */
    private String deliveryName;
    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;
    /** 配達指定時間 */
    private String deliveryDesignatedTime;
    /** 配送確認URL */
    private String deliveryComfirmURL;
    /** 送り状番号 */
    private String invoiceNo;
    /** 次回お届け予定日 */
    private Timestamp nextDeliveryDate;
    /** お届け先顧客番号 */
    private Integer receiveCustomerNo;
    /** お届け事業所名 */
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
    /** 送料 */
    private String shippingPrice;
    /** 消費税 */
    private String taxPrice;
    /** 合計金額 */
    private String totalPrice;
    /** 詳細番号 */
    private Integer detailNo;
    /** 申込商品 */
    private String goodsCode;
    /** 商品名 */
    private String goodsName;
    /** 数量 */
    private Integer quantity;
    /** 単位 */
    private String unit;
    /** 金額 */
    private String price;
    /** 受付可否区分 */
    private String permissionType;
    /** 出荷可否区分 */
    private String shippableType;
    /** 後継品フラグ */
    private String replaceFlag;
    /** 後継品申込商品 */
    private String replaceGoodsCode;
    /** 後継品商品名 */
    private String replaceGoodsName;
    /** コメント */
    private String comment;
    /** メールアドレス */
    private String mailAddress;

    /**
     * @return the mailType
     */
    public String getMailType() {
        return mailType;
    }

    /**
     * @param mailType the mailType to set
     */
    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    /**
     * @return the customerNo
     */
    public Integer getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(Integer customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(Integer orderNo) {
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
     * @return the deliveryDesignatedTime
     */
    public String getDeliveryDesignatedTime() {
        return deliveryDesignatedTime;
    }

    /**
     * @param deliveryDesignatedTime the deliveryDesignatedTime to set
     */
    public void setDeliveryDesignatedTime(String deliveryDesignatedTime) {
        this.deliveryDesignatedTime = deliveryDesignatedTime;
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
     * @return the nextDeliveryDate
     */
    public Timestamp getNextDeliveryDate() {
        return nextDeliveryDate;
    }

    /**
     * @param nextDeliveryDate the nextDeliveryDate to set
     */
    public void setNextDeliveryDate(Timestamp nextDeliveryDate) {
        this.nextDeliveryDate = nextDeliveryDate;
    }

    /**
     * @return the receiveCustomerNo
     */
    public Integer getReceiveCustomerNo() {
        return receiveCustomerNo;
    }

    /**
     * @param receiveCustomerNo the receiveCustomerNo to set
     */
    public void setReceiveCustomerNo(Integer receiveCustomerNo) {
        this.receiveCustomerNo = receiveCustomerNo;
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
     * @return the detailNo
     */
    public Integer getDetailNo() {
        return detailNo;
    }

    /**
     * @param detailNo the detailNo to set
     */
    public void setDetailNo(Integer detailNo) {
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
     * @return the goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the permissionType
     */
    public String getPermissionType() {
        return permissionType;
    }

    /**
     * @param permissionType the permissionType to set
     */
    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * @return the shippableType
     */
    public String getShippableType() {
        return shippableType;
    }

    /**
     * @param shippableType the shippableType to set
     */
    public void setShippableType(String shippableType) {
        this.shippableType = shippableType;
    }

    /**
     * @return the replaceFlag
     */
    public String getReplaceFlag() {
        return replaceFlag;
    }

    /**
     * @param replaceFlag the replaceFlag to set
     */
    public void setReplaceFlag(String replaceFlag) {
        this.replaceFlag = replaceFlag;
    }

    /**
     * @return the replaceGoodsCode
     */
    public String getReplaceGoodsCode() {
        return replaceGoodsCode;
    }

    /**
     * @param replaceGoodsCode the replaceGoodsCode to set
     */
    public void setReplaceGoodsCode(String replaceGoodsCode) {
        this.replaceGoodsCode = replaceGoodsCode;
    }

    /**
     * @return the replaceGoodsName
     */
    public String getReplaceGoodsName() {
        return replaceGoodsName;
    }

    /**
     * @param replaceGoodsName the replaceGoodsName to set
     */
    public void setReplaceGoodsName(String replaceGoodsName) {
        this.replaceGoodsName = replaceGoodsName;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
}
