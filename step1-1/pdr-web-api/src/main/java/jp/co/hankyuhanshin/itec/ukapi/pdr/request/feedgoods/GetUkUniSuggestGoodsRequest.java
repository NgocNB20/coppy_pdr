/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedgoods;

/**
 * UK-API連携 ユニサジェスト（商品）のリクエストモデル<br/>
 * @author tt32117
 */
public class GetUkUniSuggestGoodsRequest {
    /** 検索キーワード */
    private String kw;

    /** 検索結果ページ数 */
    private Integer rows;

    /** カテゴリサジェストの検索結果件数 */
    private Integer drCategory;

    /** 型番サジェストの検索結果件数 */
    private Integer drModelNumber;

    /** 商品名サジェストの検索結果件数 */
    private Integer drProductName;

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
     * @return the drCategory
     */
    public Integer getDrCategory() {
        return drCategory;
    }

    /**
     * @param drCategory the drCategory to set
     */
    public void setDrCategory(Integer drCategory) {
        this.drCategory = drCategory;
    }

    /**
     * @return the drModelNumber
     */
    public Integer getDrModelNumber() {
        return drModelNumber;
    }

    /**
     * @param drModelNumber the drModelNumber to set
     */
    public void setDrModelNumber(Integer drModelNumber) {
        this.drModelNumber = drModelNumber;
    }

    /**
     * @return the drProductName
     */
    public Integer getDrProductName() {
        return drProductName;
    }

    /**
     * @param drProductName the drProductName to set
     */
    public void setDrProductName(Integer drProductName) {
        this.drProductName = drProductName;
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
