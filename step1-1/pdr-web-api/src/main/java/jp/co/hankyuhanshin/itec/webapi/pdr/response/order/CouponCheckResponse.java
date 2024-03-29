/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

/**
 * PDR#15 WEB-API連携 クーポン有効性チェックAPIのレスポンスモデル<br/>
 *
 * @author fukui
 */
public class CouponCheckResponse {

    /** クーポン名称 */
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
