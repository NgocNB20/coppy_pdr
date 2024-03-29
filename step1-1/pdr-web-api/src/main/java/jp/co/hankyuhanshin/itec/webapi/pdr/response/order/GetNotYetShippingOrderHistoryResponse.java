/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.sql.Timestamp;
import java.util.List;

/**
 * PDR#15 WEB-API連携 注文履歴（未配送）取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetNotYetShippingOrderHistoryResponse {

    /** 受注番号 */
    private String orderNo;

    /** 受付方法 */
    private String receptionTypeName;

    /** 注文日時 */
    private Timestamp orderDate;

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

    /** お届け日 */
    private Timestamp receiveDate;

    /** 支払方法 */
    private String paymentType;

    /** お支払い合計(税込) */
    private String paymentTotalPrice;

    // 2023-renew No24 from here
    /** 適用クーポン番号 */
    private String couponNo;

    /** 適用クーポン名 */
    private String couponName;
    // 2023-renew No24 to here

    // 2023-renew No68 from here
    /** キャンセル可否フラグ */
    private Integer cancelYesNo;
    // 2023-renew No68 to here

    /** 商品リスト */
    private List<GetNotYetShippingOrderHistoryGoodsResponse> goodsList;

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
     * @return the receptionTypeName
     */
    public String getReceptionTypeName() {
        return receptionTypeName;
    }

    /**
     * @param receptionTypeName the receptionTypeName to set
     */
    public void setReceptionTypeName(String receptionTypeName) {
        this.receptionTypeName = receptionTypeName;
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
     * @return the receiveDate
     */
    public Timestamp getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate the receiveDate to set
     */
    public void setReceiveDate(Timestamp receiveDate) {
        this.receiveDate = receiveDate;
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
     * @return the paymentTotalPrice
     */
    public String getPaymentTotalPrice() {
        return paymentTotalPrice;
    }

    /**
     * @param paymentTotalPrice the paymentTotalPrice to set
     */
    public void setPaymentTotalPrice(String paymentTotalPrice) {
        this.paymentTotalPrice = paymentTotalPrice;
    }

    // 2023-renew No24 from here

    /**
     * @return the couponNo
     */
    public String getCouponNo() {
        return couponNo;
    }

    /**
     * @param couponNo the couponNo to set
     */
    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    /**
     * @return the couponName
     */
    public String getCouponName() {
        return couponName;
    }

    /**
     * @param couponName the couponName to set
     */
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    // 2023-renew No24 to here

    /**
     * @return the goodsList
     */
    public List<GetNotYetShippingOrderHistoryGoodsResponse> getGoodsList() {
        return goodsList;
    }

    /**
     * @param goodsList the goodsList to set
     */
    public void setGoodsList(List<GetNotYetShippingOrderHistoryGoodsResponse> goodsList) {
        this.goodsList = goodsList;
    }

    // 2023-renew No68 from here

    /**
     * @return the cancelYesNo
     */
    public Integer getCancelYesNo() {
        return cancelYesNo;
    }

    /**
     * @param cancelYesNo the cancelYesNo to set
     */
    public void setCancelYesNo(Integer cancelYesNo) {
        this.cancelYesNo = cancelYesNo;
    }
    // 2023-renew No68 to here

}
