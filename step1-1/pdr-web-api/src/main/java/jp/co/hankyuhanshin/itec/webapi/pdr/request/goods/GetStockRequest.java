/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.goods;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 数量割引情報取得のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetStockRequest {

    /** 商品コード */
    private String goodsCode;

    /** 数量 */
    private String quantity;

    /** 顧客番号 */
    private String customerNo;

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
     * @return the quantity
     */
    public String getquantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
