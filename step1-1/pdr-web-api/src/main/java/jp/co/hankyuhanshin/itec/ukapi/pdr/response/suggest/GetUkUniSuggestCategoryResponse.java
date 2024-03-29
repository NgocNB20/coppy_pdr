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
public class GetUkUniSuggestCategoryResponse {
    /** direct_category_id */
    private String direct_category_id;

    /** direct_category_name */
    private String direct_category_name;

    /**
     * @return the direct_category_id
     */
    public String getDirect_category_id() {
        return direct_category_id;
    }

    /**
     * @param direct_category_id the direct_category_id to set
     */
    public void setDirect_category_id(String direct_category_id) {
        this.direct_category_id = direct_category_id;
    }

    /**
     * @return the direct_category_name
     */
    public String getDirect_category_name() {
        return direct_category_name;
    }

    /**
     * @param direct_category_name the direct_category_name to set
     */
    public void setDirect_category_name(String direct_category_name) {
        this.direct_category_name = direct_category_name;
    }
}
