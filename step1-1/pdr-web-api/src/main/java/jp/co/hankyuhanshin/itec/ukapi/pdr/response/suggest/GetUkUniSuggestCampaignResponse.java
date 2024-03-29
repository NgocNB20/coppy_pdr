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
public class GetUkUniSuggestCampaignResponse {
    /** direct_campaign_url */
    private String direct_campaign_url;

    /** direct_campaign_title */
    private String direct_campaign_title;

    /**
     * @return the direct_campaign_url
     */
    public String getDirect_campaign_url() {
        return direct_campaign_url;
    }

    /**
     * @param direct_campaign_url the direct_campaign_url to set
     */
    public void setDirect_campaign_url(String direct_campaign_url) {
        this.direct_campaign_url = direct_campaign_url;
    }

    /**
     * @return the direct_campaign_title
     */
    public String getDirect_campaign_title() {
        return direct_campaign_title;
    }

    /**
     * @param direct_campaign_title the direct_campaign_title to set
     */
    public void setDirect_campaign_title(String direct_campaign_title) {
        this.direct_campaign_title = direct_campaign_title;
    }
}
