/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.sql.Timestamp;

/**
 * WEB-API連携 注文キャンセルAPIのレスポンスモデル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No68 from here
public class CancelOrderResponse {

    /** 注文キャンセル結果 */
    private Integer cancelResult;

    /** 受注番号 */
    private Integer orderNo;

    /** お届け希望日 */
    private Timestamp receiveDate;

    /**
     * @return the cancelResult
     */
    public Integer getCancelResult() {
        return cancelResult;
    }

    /**
     * @param cancelResult the cancelResult to set
     */
    public void setCancelResult(Integer cancelResult) {
        this.cancelResult = cancelResult;
    }

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

}
// 2023-renew No68 to here
