/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 受注連携APIのリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class AddOrderInformationOrderRequest {

    /** 受注番号 */
    private String orderNo;

    /** 関連受注番号 */
    private String relOrderNo;

    /** 入力担当者 */
    private String inputUserID;

    /** 確定担当者 */
    private String comfirmUserID;

    /** 受付方法 */
    private String orderType;

    /** 受注日 */
    private Timestamp orderDate;

    /** 出荷予定日 */
    private Timestamp shipmentDate;

    /** 入荷予定日 */
    private Timestamp stockDate;

    /** 顧客番号 */
    private String customerNo;

    /** 電話番号 */
    private String telNo;

    /** 広告媒体 */
    private String mediaCode;

    /** 倉庫 */
    private String stockroomCode;

    /** お届け先顧客番号 */
    private String deliveryCustomerNo;

    /** お届け先連絡番号 */
    private String deliveryTelNo;

    /** 使用ポイント */
    private String usePoint;

    /** 支払方法 */
    private String paymentType;

    /** クレジット会社 */
    private String creditCompanyCode;

    /** クレジット番号 */
    private String creditCardNo;

    /** クレジット有効期限 */
    private String creditExpirationDate;

    /** クレジット支払回数 */
    private String creditSplitNumber;

    /** クレジット決済ID */
    private String creditPaymentID;

    /** 配送方法 */
    private String deliveryType;

    /** 請求書 */
    private String requisitionType;

    /** 保留区分 */
    private String holdType;

    /** 配達指定日 */
    private Timestamp deliveryDesignatedDay;

    /** 配達指定時間 */
    private String deliveryDesignatedTimeCode;

    /** 出荷場メモ１ */
    private String shippingMemo1;

    /** 出荷場メモ２ */
    private String shippingMemo2;

    /** 合計金額 */
    private BigDecimal totalPrice;

    /** ポイント値引 */
    private BigDecimal pointDiscountPrice;

    /** プロモ値引 */
    private BigDecimal promotionDiscountPrice;

    /** 値引 */
    private BigDecimal discountPrice;

    /** 合計値引 */
    private BigDecimal totalDiscountPrice;

    /** 送料 */
    private BigDecimal shippingPrice;

    /** 請求金額 */
    private BigDecimal billingPrice;

    /** 内消費税 */
    private BigDecimal taxPrice;

    /** マーチャント取引ID */
    private String tradingID;

    /** クーポンコード */
    private String couponCode;

    /** プロモーションコード */
    private String promotionCode;

    /** 商品リスト */
    private List<AddOrderMerchandiseInformationRequest> goodsInfo =
                    new ArrayList<AddOrderMerchandiseInformationRequest>();

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
     * @return the relOrderNo
     */
    public String getRelOrderNo() {
        return relOrderNo;
    }

    /**
     * @param relOrderNo the relOrderNo to set
     */
    public void setRelOrderNo(String relOrderNo) {
        this.relOrderNo = relOrderNo;
    }

    /**
     * @return the inputUserID
     */
    public String getInputUserID() {
        return inputUserID;
    }

    /**
     * @param inputUserID the inputUserID to set
     */
    public void setInputUserID(String inputUserID) {
        this.inputUserID = inputUserID;
    }

    /**
     * @return the comfirmUserID
     */
    public String getComfirmUserID() {
        return comfirmUserID;
    }

    /**
     * @param comfirmUserID the comfirmUserID to set
     */
    public void setComfirmUserID(String comfirmUserID) {
        this.comfirmUserID = comfirmUserID;
    }

    /**
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the orderDate
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the shipmentDate
     */
    public Timestamp getShipmentDate() {
        return shipmentDate;
    }

    /**
     * @param shipmentDate the shipmentDate to set
     */
    public void setShipmentDate(Timestamp shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    /**
     * @return the stockDate
     */
    public Timestamp getStockDate() {
        return stockDate;
    }

    /**
     * @param stockDate the stockDate to set
     */
    public void setStockDate(Timestamp stockDate) {
        this.stockDate = stockDate;
    }

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
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @return the mediaCode
     */
    public String getMediaCode() {
        return mediaCode;
    }

    /**
     * @param mediaCode the mediaCode to set
     */
    public void setMediaCode(String mediaCode) {
        this.mediaCode = mediaCode;
    }

    /**
     * @return the stockroomCode
     */
    public String getStockroomCode() {
        return stockroomCode;
    }

    /**
     * @param stockroomCode the stockroomCode to set
     */
    public void setStockroomCode(String stockroomCode) {
        this.stockroomCode = stockroomCode;
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
     * @return the deliveryTelNo
     */
    public String getDeliveryTelNo() {
        return deliveryTelNo;
    }

    /**
     * @param deliveryTelNo the deliveryTelNo to set
     */
    public void setDeliveryTelNo(String deliveryTelNo) {
        this.deliveryTelNo = deliveryTelNo;
    }

    /**
     * @return the usePoint
     */
    public String getUsePoint() {
        return usePoint;
    }

    /**
     * @param usePoint the usePoint to set
     */
    public void setUsePoint(String usePoint) {
        this.usePoint = usePoint;
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
     * @return the creditCompanyCode
     */
    public String getCreditCompanyCode() {
        return creditCompanyCode;
    }

    /**
     * @param creditCompanyCode the creditCompanyCode to set
     */
    public void setCreditCompanyCode(String creditCompanyCode) {
        this.creditCompanyCode = creditCompanyCode;
    }

    /**
     * @return the creditCardNo
     */
    public String getCreditCardNo() {
        return creditCardNo;
    }

    /**
     * @param creditCardNo the creditCardNo to set
     */
    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    /**
     * @return the creditExpirationDate
     */
    public String getCreditExpirationDate() {
        return creditExpirationDate;
    }

    /**
     * @param creditExpirationDate the creditExpirationDate to set
     */
    public void setCreditExpirationDate(String creditExpirationDate) {
        this.creditExpirationDate = creditExpirationDate;
    }

    /**
     * @return the creditSplitNumber
     */
    public String getCreditSplitNumber() {
        return creditSplitNumber;
    }

    /**
     * @param creditSplitNumber the creditSplitNumber to set
     */
    public void setCreditSplitNumber(String creditSplitNumber) {
        this.creditSplitNumber = creditSplitNumber;
    }

    /**
     * @return the creditPaymentID
     */
    public String getCreditPaymentID() {
        return creditPaymentID;
    }

    /**
     * @param creditPaymentID the creditPaymentID to set
     */
    public void setCreditPaymentID(String creditPaymentID) {
        this.creditPaymentID = creditPaymentID;
    }

    /**
     * @return the deliveryType
     */
    public String getDeliveryType() {
        return deliveryType;
    }

    /**
     * @param deliveryType the deliveryType to set
     */
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     * @return the requisitionType
     */
    public String getRequisitionType() {
        return requisitionType;
    }

    /**
     * @param requisitionType the requisitionType to set
     */
    public void setRequisitionType(String requisitionType) {
        this.requisitionType = requisitionType;
    }

    /**
     * @return the holdType
     */
    public String getHoldType() {
        return holdType;
    }

    /**
     * @param holdType the holdType to set
     */
    public void setHoldType(String holdType) {
        this.holdType = holdType;
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
     * @return the deliveryDesignatedTimeCode
     */
    public String getDeliveryDesignatedTimeCode() {
        return deliveryDesignatedTimeCode;
    }

    /**
     * @param deliveryDesignatedTimeCode the deliveryDesignatedTimeCode to set
     */
    public void setDeliveryDesignatedTimeCode(String deliveryDesignatedTimeCode) {
        this.deliveryDesignatedTimeCode = deliveryDesignatedTimeCode;
    }

    /**
     * @return the shippingMemo1
     */
    public String getShippingMemo1() {
        return shippingMemo1;
    }

    /**
     * @param shippingMemo1 the shippingMemo1 to set
     */
    public void setShippingMemo1(String shippingMemo1) {
        this.shippingMemo1 = shippingMemo1;
    }

    /**
     * @return the shippingMemo2
     */
    public String getShippingMemo2() {
        return shippingMemo2;
    }

    /**
     * @param shippingMemo2 the shippingMemo2 to set
     */
    public void setShippingMemo2(String shippingMemo2) {
        this.shippingMemo2 = shippingMemo2;
    }

    /**
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the pointDiscountPrice
     */
    public BigDecimal getPointDiscountPrice() {
        return pointDiscountPrice;
    }

    /**
     * @param pointDiscountPrice the pointDiscountPrice to set
     */
    public void setPointDiscountPrice(BigDecimal pointDiscountPrice) {
        this.pointDiscountPrice = pointDiscountPrice;
    }

    /**
     * @return the promotionDiscountPrice
     */
    public BigDecimal getPromotionDiscountPrice() {
        return promotionDiscountPrice;
    }

    /**
     * @param promotionDiscountPrice the promotionDiscountPrice to set
     */
    public void setPromotionDiscountPrice(BigDecimal promotionDiscountPrice) {
        this.promotionDiscountPrice = promotionDiscountPrice;
    }

    /**
     * @return the discountPrice
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * @param discountPrice the discountPrice to set
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * @return the totalDiscountPrice
     */
    public BigDecimal getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    /**
     * @param totalDiscountPrice the totalDiscountPrice to set
     */
    public void setTotalDiscountPrice(BigDecimal totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    /**
     * @return the shippingPrice
     */
    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    /**
     * @param shippingPrice the shippingPrice to set
     */
    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /**
     * @return the billingPrice
     */
    public BigDecimal getBillingPrice() {
        return billingPrice;
    }

    /**
     * @param billingPrice the billingPrice to set
     */
    public void setBillingPrice(BigDecimal billingPrice) {
        this.billingPrice = billingPrice;
    }

    /**
     * @return the taxPrice
     */
    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    /**
     * @param taxPrice the taxPrice to set
     */
    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    /**
     * @return the tradingID
     */
    public String getTradingID() {
        return tradingID;
    }

    /**
     * @param tradingID the tradingID to set
     */
    public void setTradingID(String tradingID) {
        this.tradingID = tradingID;
    }

    /**
     * @return the couponCode
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * @param couponCode the couponCode to set
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    /**
     * @return the promotionCode
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * @param promotionCode the promotionCode to set
     */
    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    /**
     * @return the goodsInfo
     */
    public List<AddOrderMerchandiseInformationRequest> getGoodsInfo() {
        return goodsInfo;
    }

    /**
     * @param goodsInfo the goodsInfo to set
     */
    public void setGoodsInfo(List<AddOrderMerchandiseInformationRequest> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
