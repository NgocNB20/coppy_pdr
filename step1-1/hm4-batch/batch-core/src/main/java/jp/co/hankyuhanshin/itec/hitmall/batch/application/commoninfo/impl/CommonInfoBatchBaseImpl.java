/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 基本情報（共通クラス）<br/>
 *
 * @author natume
 */
@Component
public class CommonInfoBatchBaseImpl implements CommonInfoBase {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * サイト区分
     */
    private HTypeSiteType siteType;

    /**
     * User-Agent
     */
    private String userAgent;

    /**
     * デバイス種別
     */
    private HTypeDeviceType deviceType;

    /**
     * セッションID
     */
    private String sessionId;

    /**
     * キャンペーンコード
     */
    private String campaignCode;

    /**
     * URL
     */
    private String url;

    /**
     * ページID
     */
    private String pageId;

    /**
     * 端末識別番号
     */
    private String accessUid;

    /**
     * 商品合計点数<br/>
     */
    private BigDecimal cartGoodsSumCount;

    /**
     * 商品合計金額<br/>
     */
    private BigDecimal cartGoodsSumPrice;

    /**
     * 問合せ認証の認証済みお問い合わせ番号のリスト
     */
    private List<String> inquiryCodeAttestationList;

    // PDR Migrate Customization from here
    /**
     * クーポンコード
     */
    private String couponCode;
    // PDR Migrate Customization to here

    /**
     * @return the shopSeq
     */
    @Override
    public Integer getShopSeq() {
        return shopSeq;
    }

    /**
     * @param shopSeq the shopSeq to set
     */
    public void setShopSeq(Integer shopSeq) {
        this.shopSeq = shopSeq;
    }

    /**
     * @return the siteType
     */
    @Override
    public HTypeSiteType getSiteType() {
        return siteType;
    }

    /**
     * @param siteType the siteType to set
     */
    public void setSiteType(HTypeSiteType siteType) {
        this.siteType = siteType;
    }

    /**
     * @return the userAgent
     */
    @Override
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * @return the deviceType
     */
    @Override
    public HTypeDeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(HTypeDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return the sessionId
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the campaignCode
     */
    @Override
    public String getCampaignCode() {
        return campaignCode;
    }

    /**
     * @param campaignCode the sessionId to set
     */
    public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
    }

    /**
     * @return the url
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the pageId
     */
    @Override
    public String getPageId() {
        return pageId;
    }

    /**
     * @param pageId the pageId to set
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    /**
     * @return the accessUid
     */
    @Override
    public String getAccessUid() {
        return accessUid;
    }

    /**
     * @param accessUid the accessUid to set
     */
    public void setAccessUid(String accessUid) {
        this.accessUid = accessUid;
    }

    @Override
    public BigDecimal getCartGoodsSumCount() {
        return cartGoodsSumCount;
    }

    @Override
    public void setCartGoodsSumCount(BigDecimal cartGoodsSumCount) {
        this.cartGoodsSumCount = cartGoodsSumCount;
    }

    @Override
    public BigDecimal getCartGoodsSumPrice() {
        return cartGoodsSumPrice;
    }

    @Override
    public void setCartGoodsSumPrice(BigDecimal cartGoodsSumPrice) {
        this.cartGoodsSumPrice = cartGoodsSumPrice;
    }

    @Override
    public List<String> getInquiryCodeAttestationList() {
        return inquiryCodeAttestationList;
    }

    @Override
    public void setInquiryCodeAttestationList(List<String> inquiryCodeAttestationList) {
        this.inquiryCodeAttestationList = inquiryCodeAttestationList;
    }

    // PDR Migrate Customization from here
    @Override
    public String getCouponCode() {
        return couponCode;
    }

    @Override
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    // PDR Migrate Customization to here
}
