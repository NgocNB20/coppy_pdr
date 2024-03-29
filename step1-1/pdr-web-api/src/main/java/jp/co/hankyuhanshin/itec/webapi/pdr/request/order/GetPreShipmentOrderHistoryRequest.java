/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

// PDR#343 20170411 y.tagami 新規作成

import java.sql.Timestamp;

/**
 * PDR#343 WEB-API連携 注文履歴（配送済み）取得APIのリクエストモデル<br/>
 *
 * @author y.tagami
 */
public class GetPreShipmentOrderHistoryRequest {

    /** 顧客番号 */
    private String customerNo;

    // 2023-renew No52 from here
    /** 絞り込み開始日 */
    private Timestamp searchStartDay;

    /** 絞り込み終了日 */
    private Timestamp searchEndDay;
    // 2023-renew No52 to here

    /**
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    // 2023-renew No52 from here
    /**
     * @return the searchStartDay
     */
    public Timestamp getSearchStartDay() {
        return searchStartDay;
    }

    /**
     * @param searchStartDay the searchStartDay to set
     */
    public void setSearchStartDay(Timestamp searchStartDay) {
        this.searchStartDay = searchStartDay;
    }

    /**
     * @return the searchEndDay
     */
    public Timestamp getSearchEndDay() {
        return searchEndDay;
    }

    /**
     * @param searchEndDay the searchEndDay to set
     */
    public void setSearchEndDay(Timestamp searchEndDay) {
        this.searchEndDay = searchEndDay;
    }
    // 2023-renew No52 to here
}
