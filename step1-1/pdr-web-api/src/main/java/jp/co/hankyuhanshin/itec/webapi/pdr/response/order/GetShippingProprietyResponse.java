/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 配送可否取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetShippingProprietyResponse {

    /** お届け可否 */
    private String deliveryVoteFlag;

    /**
     * @return the deliveryVoteFlag
     */
    public String getDeliveryVoteFlag() {
        return deliveryVoteFlag;
    }

    /**
     * @param deliveryVoteFlag the deliveryVoteFlag to set
     */
    public void setDeliveryVoteFlag(String deliveryVoteFlag) {
        this.deliveryVoteFlag = deliveryVoteFlag;
    }
}
