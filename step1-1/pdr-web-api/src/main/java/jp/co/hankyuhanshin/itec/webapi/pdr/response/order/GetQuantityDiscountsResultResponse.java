/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161220 kato 新規作成

/**
 * PDR#15 WEB-API連携 数量割引適用結果取得APIのレスポンスモデル<br/>
 *
 * @author kato
 */
public class GetQuantityDiscountsResultResponse {

    /** 申込商品 */
    private String goodsCode;

    /** 適用割引 */
    private String saleType;

    /** 数量割引グループコード */
    private String saleGroupCode;

    /** 割引価格 */
    private String salePrice;

    /** 数量 */
    private String quantity;

    /** セールコード */
    private String saleCode;

    /** 備考 */
    private String note;

    /** 注意事項 */
    private String hints;

    /** 価格 */
    private String price;

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
     * @return the saleType
     */
    public String getSaleType() {
        return saleType;
    }

    /**
     * @param saleType the saleType to set
     */
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    /**
     * @return the saleGroupCode
     */
    public String getSaleGroupCode() {
        return saleGroupCode;
    }

    /**
     * @param saleGroupCode the saleGroupCode to set
     */
    public void setSaleGroupCode(String saleGroupCode) {
        this.saleGroupCode = saleGroupCode;
    }

    /**
     * @return the salePrice
     */
    public String getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice the salePrice to set
     */
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the saleCode
     */
    public String getSaleCode() {
        return saleCode;
    }

    /**
     * @param saleCode the saleCode to set
     */
    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the hints
     */
    public String getHints() {
        return hints;
    }

    /**
     * @param hints the hints to set
     */
    public void setHints(String hints) {
        this.hints = hints;
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
}
