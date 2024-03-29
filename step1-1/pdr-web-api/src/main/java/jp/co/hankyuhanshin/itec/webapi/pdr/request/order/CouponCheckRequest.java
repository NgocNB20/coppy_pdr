/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

/**
 * PDR#15 WEB-API連携 クーポン有効性チェックAPIのリクエストモデル<br/>
 *
 * @author kato
 */
public class CouponCheckRequest {

    /** クーポン名称 */
    private String couponCode;

    /**
     * @return the CouponName
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
}
