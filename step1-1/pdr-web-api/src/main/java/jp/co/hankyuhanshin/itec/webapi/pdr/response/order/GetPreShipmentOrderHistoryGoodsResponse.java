/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 注文履歴（配送済み）取得APIのレスポンスモデル（商品）<br/>
 *
 * @author k-katoh
 */
public class GetPreShipmentOrderHistoryGoodsResponse {

    /** 申込商品 */
    private String goodsCode;

    /** 商品名 */
    private String goodsName;

    /** 数量 */
    private String goodsCount;

    /** 単位 */
    private String unitName;

    /** 適用割引 */
    private String discountFlag;

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
     * @return the goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * @return the goodsCount
     */
    public String getGoodsCount() {
        return goodsCount;
    }

    /**
     * @param goodsCount the goodsCount to set
     */
    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the discountFlag
     */
    public String getDiscountFlag() {
        return discountFlag;
    }

    /**
     * @param discountFlag the discountFlag to set
     */
    public void setDiscountFlag(String discountFlag) {
        this.discountFlag = discountFlag;
    }
}
