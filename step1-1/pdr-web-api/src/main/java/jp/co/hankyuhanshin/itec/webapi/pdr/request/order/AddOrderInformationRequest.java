/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 受注連携APIのリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class AddOrderInformationRequest {

    /** 注文内容 */
    private List<AddOrderInformationOrderRequest> orderInfo;

    /**
     * @return the orderInfo
     */
    public List<AddOrderInformationOrderRequest> getOrderInfo() {
        return orderInfo;
    }

    /**
     * @param orderInfo the orderInfo to set
     */
    public void setOrderInfo(List<AddOrderInformationOrderRequest> orderInfo) {
        this.orderInfo = orderInfo;
    }

}
