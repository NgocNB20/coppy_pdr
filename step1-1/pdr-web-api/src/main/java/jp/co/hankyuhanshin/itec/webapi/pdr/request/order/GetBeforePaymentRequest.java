/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

/**
 * WEB-API連携 前回支払方法取得APIのリクエストモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No14 from here
public class GetBeforePaymentRequest {

    /** 顧客番号 */
    private Integer customerNo;

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

}
// 2023-renew No14 to here
