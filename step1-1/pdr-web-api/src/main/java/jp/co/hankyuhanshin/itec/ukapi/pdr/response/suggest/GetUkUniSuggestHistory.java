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
public class GetUkUniSuggestHistory {
    /** docs */
    GetUkUniSuggestHistoryDocs docs;

    /**
     * @return the docs
     */
    public GetUkUniSuggestHistoryDocs getDocs() {
        return docs;
    }

    /**
     * @param docs the docs to set
     */
    public void setDocs(GetUkUniSuggestHistoryDocs docs) {
        this.docs = docs;
    }
}
