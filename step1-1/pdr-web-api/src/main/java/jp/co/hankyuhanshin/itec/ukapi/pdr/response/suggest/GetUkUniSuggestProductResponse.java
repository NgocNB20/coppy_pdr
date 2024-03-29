/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest;

/**
 * UK-API連携 処理結果のレスポンスモデル<br/>
 * @author tt32117
 */
public class GetUkUniSuggestProductResponse {
    /** direct_group_id */
    private String direct_group_id;

    /** direct_item_name */
    private String direct_item_name;

    /** direct_min_price */
    private String direct_min_price;

    /** direct_max_price */
    private String direct_max_price;

    /** direct_sale_min_price */
    private String direct_sale_min_price;

    /** direct_sale_max_price */
    private String direct_sale_max_price;

    /**
     * @return the direct_group_id
     */
    public String getDirect_group_id() {
        return direct_group_id;
    }

    /**
     * @param direct_group_id the direct_group_id to set
     */
    public void setDirect_group_id(String direct_group_id) {
        this.direct_group_id = direct_group_id;
    }

    /**
     * @return the direct_item_name
     */
    public String getDirect_item_name() {
        return direct_item_name;
    }

    /**
     * @param direct_item_name the direct_item_name to set
     */
    public void setDirect_item_name(String direct_item_name) {
        this.direct_item_name = direct_item_name;
    }

    /**
     * @return the direct_min_price
     */
    public String getDirect_min_price() {
        return direct_min_price;
    }

    /**
     * @param direct_min_price the direct_min_price to set
     */
    public void setDirect_min_price(String direct_min_price) {
        this.direct_min_price = direct_min_price;
    }

    /**
     * @return the direct_max_price
     */
    public String getDirect_max_price() {
        return direct_max_price;
    }

    /**
     * @param direct_max_price the direct_max_price to set
     */
    public void setDirect_max_price(String direct_max_price) {
        this.direct_max_price = direct_max_price;
    }

    /**
     * @return the direct_sale_min_price
     */
    public String getDirect_sale_min_price() {
        return direct_sale_min_price;
    }

    /**
     * @param direct_sale_min_price the direct_sale_min_price to set
     */
    public void setDirect_sale_min_price(String direct_sale_min_price) {
        this.direct_sale_min_price = direct_sale_min_price;
    }

    /**
     * @return the direct_sale_max_price
     */
    public String getDirect_sale_max_price() {
        return direct_sale_max_price;
    }

    /**
     * @param direct_sale_max_price the direct_sale_max_price to set
     */
    public void setDirect_sale_max_price(String direct_sale_max_price) {
        this.direct_sale_max_price = direct_sale_max_price;
    }
}
