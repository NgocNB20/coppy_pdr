/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.math.BigDecimal;

// PDR#15 20170313 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 プロモーションAPIのレスポンスモデル（割引情報）<br/>
 *
 * @author k-katoh
 */
public class GetPromotionDiscountInformationResponse {

    /** 受注番号 */
    private String orderNo;

    /** 受注連番 */
    private String orderSerial;

    /** 設定パターン */
    private String settingPattern;

    /** 割引率 */
    private BigDecimal discountRate;

    /** 単価値引額 */
    private BigDecimal unitDiscountPrice;

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the orderSerial
     */
    public String getOrderSerial() {
        return orderSerial;
    }

    /**
     * @param orderSerial the orderSerial to set
     */
    public void setOrderSerial(String orderSerial) {
        this.orderSerial = orderSerial;
    }

    /**
     * @return the settingPattern
     */
    public String getSettingPattern() {
        return settingPattern;
    }

    /**
     * @param settingPattern the settingPattern to set
     */
    public void setSettingPattern(String settingPattern) {
        this.settingPattern = settingPattern;
    }

    /**
     * @return the discountRate
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * @param discountRate the discountRate to set
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * @return the unitDiscountPrice
     */
    public BigDecimal getUnitDiscountPrice() {
        return unitDiscountPrice;
    }

    /**
     * @param unitDiscountPrice the unitDiscountPrice to set
     */
    public void setUnitDiscountPrice(BigDecimal unitDiscountPrice) {
        this.unitDiscountPrice = unitDiscountPrice;
    }
}
