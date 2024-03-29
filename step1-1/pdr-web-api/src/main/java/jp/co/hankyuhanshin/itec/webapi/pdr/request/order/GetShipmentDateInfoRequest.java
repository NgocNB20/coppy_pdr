/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.sql.Timestamp;

/**
 * WEB-API連携 出荷予定日取得APIのリクエストモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No14 from here
public class GetShipmentDateInfoRequest {

    /** 申込商品 **/
    private String goodsCode;

    /** 配達指定日 **/
    private Timestamp deliveryDesignatedDay;

    /** 配達指定時間 **/
    private Integer deliveryDesignatedTimeCode;

    /**
     * @return the goodsCode
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * @param goodsCode the goodsCode to set
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * @return the deliveryDesignatedDay
     */
    public Timestamp getDeliveryDesignatedDay() {
        return deliveryDesignatedDay;
    }

    /**
     * @param deliveryDesignatedDay the deliveryDesignatedDay to set
     */
    public void setDeliveryDesignatedDay(Timestamp deliveryDesignatedDay) {
        this.deliveryDesignatedDay = deliveryDesignatedDay;
    }

    /**
     * @return the deliveryDesignatedTimeCode
     */
    public Integer getDeliveryDesignatedTimeCode() {
        return deliveryDesignatedTimeCode;
    }

    /**
     * @param deliveryDesignatedTimeCode the deliveryDesignatedTimeCode to set
     */
    public void setDeliveryDesignatedTimeCode(Integer deliveryDesignatedTimeCode) {
        this.deliveryDesignatedTimeCode = deliveryDesignatedTimeCode;
    }

}
// 2023-renew No14 to here
