/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.sql.Timestamp;
import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 数量割引情報取得（商品）APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetQuantityDiscountResponse {

    /** 申込商品 */
    private String goodsCode;

    /** 価格リスト */
    private List<GetQuantityDiscountPriceResponse> priceList;

    /** 割引価格リスト */
    private List<GetQuantityDiscountSalePriceResponse> salePriceList;

    /** 顧客セール価格リスト */
    private List<GetQuantityDiscountCustomerSalePriceResponse> customerSalePriceList;

    /** 数量割引適用有無 */
    private String saleFlag;

    /** 時価フラグ */
    private String marketPriceFlag;

    /** 顧客セール割引適用有無 */
    private Integer customerSaleFlag;

    // 2023-renew No5 from here
    /** セール終了日 */
    private Timestamp saleEndDay;

    public void setSaleEndDay(Timestamp saleEndDay) {
        this.saleEndDay = saleEndDay;
    }

    public Timestamp getSaleEndDay() {
        return saleEndDay;
    }
    // 2023-renew No5 to here

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
     * @return the priceList
     */
    public List<GetQuantityDiscountPriceResponse> getPriceList() {
        return priceList;
    }

    /**
     * @param priceList the priceList to set
     */
    public void setPriceList(List<GetQuantityDiscountPriceResponse> priceList) {
        this.priceList = priceList;
    }

    /**
     * @return the salePriceList
     */
    public List<GetQuantityDiscountSalePriceResponse> getSalePriceList() {
        return salePriceList;
    }

    /**
     * @param salePriceList the salePriceList to set
     */
    public void setSalePriceList(List<GetQuantityDiscountSalePriceResponse> salePriceList) {
        this.salePriceList = salePriceList;
    }

    /**
     * @return the customerSalePriceList
     */
    public List<GetQuantityDiscountCustomerSalePriceResponse> getCustomerSalePriceList() {
        return customerSalePriceList;
    }

    /**
     * @param customerSalePriceList the customerSalePriceList to set
     */
    public void setCustomerSalePriceList(List<GetQuantityDiscountCustomerSalePriceResponse> customerSalePriceList) {
        this.customerSalePriceList = customerSalePriceList;
    }

    /**
     * @return the saleFlag
     */
    public String getSaleFlag() {
        return saleFlag;
    }

    /**
     * @param saleFlag the saleFlag to set
     */
    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    /**
     * @return the marketPriceFlag
     */
    public String getMarketPriceFlag() {
        return marketPriceFlag;
    }

    /**
     * @param marketPriceFlag the marketPriceFlag to set
     */
    public void setMarketPriceFlag(String marketPriceFlag) {
        this.marketPriceFlag = marketPriceFlag;
    }

    /**
     * @return the customerSaleFlag
     */
    public Integer getCustomerSaleFlag() {
        return customerSaleFlag;
    }

    /**
     * @param customerSaleFlag the customerSaleFlag to set
     */
    public void setCustomerSaleFlag(Integer customerSaleFlag) {
        this.customerSaleFlag = customerSaleFlag;
    }

}
