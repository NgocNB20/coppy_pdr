/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword;

/**
 * UK-API連携 処理結果のレスポンスモデル<br/>
 * @author tt32117
 */
public class GetUkRWordInfoWord {

    /** docs */
    private GetUkRWordInfoDocs docs;

    /**
     * @return the docs
     */
    public GetUkRWordInfoDocs getDocs() {
        return docs;
    }

    /**
     * @param docs the docs to set
     */
    public void setDocs(GetUkRWordInfoDocs docs) {
        this.docs = docs;
    }

}
