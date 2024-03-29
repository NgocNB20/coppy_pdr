/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 購入済み商品情報取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetPurchasedCommodityInformationResponse {

    /** 申込商品 */
    private String goodsCode;

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
}
