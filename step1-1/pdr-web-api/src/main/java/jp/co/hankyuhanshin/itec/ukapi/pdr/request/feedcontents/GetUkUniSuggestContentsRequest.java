/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedcontents;

/**
 * UK-API連携 ユニサジェスト（コンテンツ）のリクエストモデル<br/>
 * @author tt32117
 */
public class GetUkUniSuggestContentsRequest {
    /** 検索キーワード */
    private String kw;

    /** 検索結果ページ数 */
    private Integer rows;

    /** 特集サジェストの検索結果件数 */
    private Integer drCampaign;

    /** 履歴サジェストフラグ */
    private String history;

    /** タグログ「_setLoginID」の値 */
    private String loginId;

    /** ユーザ識別情報 */
    private String uid;

    /**
     * @return the kw
     */
    public String getKw() {
        return kw;
    }

    /**
     * @param kw the kw to set
     */
    public void setKw(String kw) {
        this.kw = kw;
    }

    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * @return the drCampaign
     */
    public Integer getDrCampaign() {
        return drCampaign;
    }

    /**
     * @param drCampaign the drCampaign to set
     */
    public void setDrCampaign(Integer drCampaign) {
        this.drCampaign = drCampaign;
    }

    /**
     * @return the history
     */
    public String getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(String history) {
        this.history = history;
    }

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * @param loginId the loginId to set
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }
}
