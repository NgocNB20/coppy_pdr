/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.sql.Timestamp;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 配送情報取得APIのレスポンスモデル（日付）<br/>
 *
 * @author k-katoh
 */
public class GetDeliveryInformationDateResponse {

    /** お届け可能日 */
    private Timestamp receiveDate;

    /** 時間帯コード */
    private String dispTimeZoneCode;

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

    /**
     * @return the dispTimeZoneCode
     */
    public String getDispTimeZoneCode() {
        return dispTimeZoneCode;
    }

    /**
     * @param dispTimeZoneCode the dispTimeZoneCode to set
     */
    public void setDispTimeZoneCode(String dispTimeZoneCode) {
        this.dispTimeZoneCode = dispTimeZoneCode;
    }
}
