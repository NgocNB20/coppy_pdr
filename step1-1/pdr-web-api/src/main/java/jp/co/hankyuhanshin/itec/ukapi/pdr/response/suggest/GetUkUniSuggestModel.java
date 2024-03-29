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
public class GetUkUniSuggestModel {
    /** docs */
    GetUkUniSuggestModelDocs docs;

    /**
     * @return the docs
     */
    public GetUkUniSuggestModelDocs getDocs() {
        return docs;
    }

    /**
     * @param docs the docs to set
     */
    public void setDocs(GetUkUniSuggestModelDocs docs) {
        this.docs = docs;
    }
}
