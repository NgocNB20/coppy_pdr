/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

/**
 * WEB-API連携 クーポン取得APIのレスポンスモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No24 from here
public class AddCouponResponse {

    /** クーポン名 */
    private String couponName;

    /**
     * @return the goodsCode
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

}
// 2023-renew No24 to here
