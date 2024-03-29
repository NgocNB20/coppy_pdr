/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

/**
 * WEB-API連携 注文キャンセルAPIのリクエストモデル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No68 from here
public class CancelOrderRequest {

    /** 受注番号 */
    private Integer orderNo;

    /**
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

}
// 2023-renew No68 to here
