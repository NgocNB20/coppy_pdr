/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 基本情報(共通情報)
 *
 * @author natume
 */
public interface CommonInfoBase extends Serializable {

    /**
     * @return the shopSeq
     */
    Integer getShopSeq();

    /**
     * @return the hTypeSiteType
     */
    HTypeSiteType getSiteType();

    /**
     * @return the userAgent
     */
    String getUserAgent();

    /**
     * @return the hTypeDeviceType
     */
    HTypeDeviceType getDeviceType();

    /**
     * @return the sessionId
     */
    String getSessionId();

    /**
     * @return the campaignCode
     */
    String getCampaignCode();

    /**
     * @return the url
     */
    String getUrl();

    /**
     * @return the pageId
     */
    String getPageId();

    /**
     * @return the accessUid
     */
    String getAccessUid();

    /**
     * カート合計数量取得
     *
     * @return the cartGoodsSumCount
     */
    BigDecimal getCartGoodsSumCount();

    /**
     * カート合計数量設定
     *
     * @param cartGoodsSumCount
     */
    void setCartGoodsSumCount(BigDecimal cartGoodsSumCount);

    /**
     * カート合計金額取得
     *
     * @return the cartGoodsSumPrice
     */
    BigDecimal getCartGoodsSumPrice();

    /**
     * カート合計金額設定
     *
     * @param cartGoodsSumPrice
     */
    void setCartGoodsSumPrice(BigDecimal cartGoodsSumPrice);

    /**
     * 問合せ認証の認証済みお問い合わせ番号のリスト設定
     *
     * @return the inquiryCodeAttestationList
     */
    List<String> getInquiryCodeAttestationList();

    /**
     * 問合せ認証の認証済みお問い合わせ番号のリスト設定
     *
     * @param inquiryCodeAttestationList
     */
    void setInquiryCodeAttestationList(List<String> inquiryCodeAttestationList);

    // PDR Migrate Customization from here

    /**
     * セッションにクーポンコードを取得する
     */
    String getCouponCode();

    /**
     * セッションにクーポンコードを設定する
     *
     * @param couponCode クーポンコード
     */
    void setCouponCode(String couponCode);
    // PDR Migrate Customization to here
}
