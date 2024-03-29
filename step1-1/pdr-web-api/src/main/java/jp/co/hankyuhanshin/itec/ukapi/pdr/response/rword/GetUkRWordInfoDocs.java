/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword;

import java.util.List;

/**
 * UK-API連携 処理結果のレスポンスモデル<br/>
 * @author tt32117
 */
public class GetUkRWordInfoDocs {

    /** item */
    private List<Object> item;

    /**
     * @return the item
     */
    public List<Object> getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(List<Object> item) {
        this.item = item;
    }
}
