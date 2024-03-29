/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

/**
 * WEB-API商品入荷情報取得APIのレスポンスモデル<br/>
 *
 * @author st75001
 */
public class GetReStockResponse {

    /** 商品コード */
    private String goodsCode;

    /** 在庫数 */
    private String stockQuantity;

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
}
