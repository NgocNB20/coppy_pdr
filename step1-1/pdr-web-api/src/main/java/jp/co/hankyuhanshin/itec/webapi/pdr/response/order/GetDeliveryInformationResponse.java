/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.sql.Timestamp;
import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 配送情報取得APIのレスポンスモデル
 *
 * @author k-katoh
 */
public class GetDeliveryInformationResponse {

    /** お届け希望日、時間帯指定可否 */
    private String deliveryAssignFlag;

    /** 配送会社コード */
    private String deliveryCompanyCode;

    /** お届け可否 */
    private String deliveryFlag;

    /** お届け不可申込商品 */
    private String nodeliveryGoodsCode;

    // 2023-renew No14 from here
    /** 共通最短お届け日 **/
    private Timestamp comEarlyReceiveDate;
    // 2023-renew No14 to here

    /** 日付情報リスト */
    private List<GetDeliveryInformationDateResponse> dateInfo;

    /**
     * @return the deliveryAssignFlag
     */
    public String getDeliveryAssignFlag() {
        return deliveryAssignFlag;
    }

    /**
     * @param deliveryAssignFlag the deliveryAssignFlag to set
     */
    public void setDeliveryAssignFlag(String deliveryAssignFlag) {
        this.deliveryAssignFlag = deliveryAssignFlag;
    }

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

    /**
     * @return the deliveryFlag
     */
    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    /**
     * @param deliveryFlag the deliveryFlag to set
     */
    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    /**
     * @return the nodeliveryGoodsCode
     */
    public String getNodeliveryGoodsCode() {
        return nodeliveryGoodsCode;
    }

    /**
     * @param nodeliveryGoodsCode the nodeliveryGoodsCode to set
     */
    public void setNodeliveryGoodsCode(String nodeliveryGoodsCode) {
        this.nodeliveryGoodsCode = nodeliveryGoodsCode;
    }

    // 2023-renew No14 from here

    /**
     * @return the comEarlyReceiveDate
     */
    public Timestamp getComEarlyReceiveDate() {
        return comEarlyReceiveDate;
    }

    /**
     * @param comEarlyReceiveDate the comEarlyReceiveDate to set
     */
    public void setComEarlyReceiveDate(Timestamp comEarlyReceiveDate) {
        this.comEarlyReceiveDate = comEarlyReceiveDate;
    }

    // 2023-renew No14 to here

    /**
     * @return the dateInfo
     */
    public List<GetDeliveryInformationDateResponse> getDateInfo() {
        return dateInfo;
    }

    /**
     * @param dateInfo the dateInfo to set
     */
    public void setDateInfo(List<GetDeliveryInformationDateResponse> dateInfo) {
        this.dateInfo = dateInfo;
    }

}
