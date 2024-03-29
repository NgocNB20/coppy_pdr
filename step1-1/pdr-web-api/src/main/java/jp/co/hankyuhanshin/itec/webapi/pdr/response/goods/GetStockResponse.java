/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.sql.Timestamp;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携商品在庫数取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetStockResponse {

    /** 商品コード */
    private String goodsCode;

    /** 在庫数 */
    private String stockQuantity;

    /** 販売可否判定結果 */
    private Integer saleYesNo;

    /** 販売不可メッセージ */
    private String saleNgMessage;

    /** 入荷予定日 */
    private Timestamp stockDate;

    /** 入荷次第お届け可否 */
    private String deliveryYesNo;

    // 2023-renew No11,33,75,112 from here
    /** 販売制御区分 */
    private String saleControl;
    // 2023-renew No11,33,75,112 to here

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
     * @return the stockQuantity
     */
    public String getStockQuantity() {
        return stockQuantity;
    }

    /**
     * @param stockQuantity the stockQuantity to set
     */
    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * @return the orderableQuantity
     */
    public Integer getSaleYesNo() {
        return saleYesNo;
    }

    /**
     * @param saleYesNo the saleYesNo to set
     */
    public void setSaleYesNo(Integer saleYesNo) {
        this.saleYesNo = saleYesNo;
    }

    /**
     * @return the saleNgMessage
     */
    public String getSaleNgMessage() {
        return saleNgMessage;
    }

    /**
     * @param saleNgMessage the saleNgMessage to set
     */
    public void setSaleNgMessage(String saleNgMessage) {
        this.saleNgMessage = saleNgMessage;
    }

    /**
     * @return the stockDate
     */
    public Timestamp getStockDate() {
        return stockDate;
    }

    /**
     * @param stockDate the stockDate to set
     */
    public void setStockDate(Timestamp stockDate) {
        this.stockDate = stockDate;
    }

    /**
     * @return the deliveryYesNo
     */
    public String getDeliveryYesNo() {
        return deliveryYesNo;
    }

    /**
     * @param deliveryYesNo the deliveryYesNo to set
     */
    public void setDeliveryYesNo(String deliveryYesNo) {
        this.deliveryYesNo = deliveryYesNo;
    }

    // 2023-renew No11,33,75,112 from here
    /**
     * @return the saleControl
     */
    public String getSaleControl() {
        return saleControl;
    }

    /**
     * @param saleControl the saleControl to set
     */
    public void setSaleControl(String saleControl) {
        this.saleControl = saleControl;
    }
    // 2023-renew No11,33,75,112 to here

}
