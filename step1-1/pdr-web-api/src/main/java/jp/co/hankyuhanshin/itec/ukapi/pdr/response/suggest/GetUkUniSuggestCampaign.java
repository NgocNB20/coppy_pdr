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
public class GetUkUniSuggestCampaign {
    /** docs */
    GetUkUniSuggestCampaignDocs docs;

    /**
     * @return the docs
     */
    public GetUkUniSuggestCampaignDocs getDocs() {
        return docs;
    }

    /**
     * @param docs the docs to set
     */
    public void setDocs(GetUkUniSuggestCampaignDocs docs) {
        this.docs = docs;
    }
}
