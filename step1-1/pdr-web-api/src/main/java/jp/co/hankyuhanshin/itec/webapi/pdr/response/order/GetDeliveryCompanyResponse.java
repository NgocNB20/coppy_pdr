/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 配送会社取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetDeliveryCompanyResponse {

    /** 配送会社コード */
    private String deliveryCompanyCode;

    /**
     * @return the deliveryCompanyCode
     */
    public String getDeliveryCompanyCode() {
        return deliveryCompanyCode;
    }

    /**
     * @param deliveryCompanyCode the deliveryCompanyCode to set
     */
    public void setDeliveryCompanyCode(String deliveryCompanyCode) {
        this.deliveryCompanyCode = deliveryCompanyCode;
    }
}
