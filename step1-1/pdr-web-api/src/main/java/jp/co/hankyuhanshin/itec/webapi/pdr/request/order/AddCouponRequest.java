/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

/**
 * WEB-API連携 クーポン取得APIのリクエストモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No24 from here
public class AddCouponRequest {

    /** 顧客番号 */
    private Integer customerNo;

    /** クーポン番号 */
    private String couponNo;

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

}
// 2023-renew No24 to here
