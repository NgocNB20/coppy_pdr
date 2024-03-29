/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest;

import java.util.List;

/**
 * UK-API連携 処理結果のレスポンスモデル<br/>
 * @author tt32117
 */
public class GetUkUniSuggestModelDocs {
    /** item */
    private List<GetUkUniSuggestModelResponse> item;

    /**
     * @return the item
     */
    public List<GetUkUniSuggestModelResponse> getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(List<GetUkUniSuggestModelResponse> item) {
        this.item = item;
    }
}
