/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.sql.Timestamp;

/**
 * WEB-API連携 出荷予定日取得APIのレスポンスモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No14 from here
public class GetShipmentDateResponse {

    /** 申込商品 **/
    private String goodsCode;

    /** 配達指定日 **/
    private Timestamp deliveryDesignatedDay;

    /** 出荷予定日 **/
    private Timestamp shipmentDate;

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
     * @return the shipmentDate
     */
    public Timestamp getShipmentDate() {
        return shipmentDate;
    }

    /**
     * @param shipmentDate the shipmentDate to set
     */
    public void setShipmentDate(Timestamp shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

}
// 2023-renew No14 to here
