/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

/**
 * WEB-API連携 利用可能クーポン一覧取得APIのレスポンスモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No24 from here
public class GetCouponListResponse {

    /** クーポン番号 */
    private String couponNo;

    /** クーポン名 */
    private String couponName;

    /** 適用条件 */
    private String couponConditions;

    /** 詳細説明 */
    private String couponExplain;

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

    /**
     * @return the couponConditions
     */
    public String getCouponConditions() {
        return couponConditions;
    }

    /**
     * @param couponConditions the couponConditions to set
     */
    public void setCouponConditions(String couponConditions) {
        this.couponConditions = couponConditions;
    }

    /**
     * @return the couponExplain
     */
    public String getCouponExplain() {
        return couponExplain;
    }

    /**
     * @param couponExplain the couponExplain to set
     */
    public void setCouponExplain(String couponExplain) {
        this.couponExplain = couponExplain;
    }

}
// 2023-renew No24 to here
