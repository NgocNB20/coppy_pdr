/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 出荷情報取得APIのリクエストモデル（商品）<br/>
 *
 * @author k-katoh
 */
public class GetShipmentInformationGoodsResponse {

    /** 申込商品 */
    private String goodsNo;

    /** 商品名 */
    private String goodsName;

    /** 数量 */
    private String count;

    /** 単位 */
    private String unitName;

    /** 単価 */
    private String unitPrice;

    /** 金額 */
    private String price;

    /** 適用割引 */
    private String discountFlag;

    /**
     * @return the goodsNo
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * @param goodsNo the goodsNo to set
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
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
     * @return the count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(String count) {
        this.count = count;
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
     * @return the unitPrice
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
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
