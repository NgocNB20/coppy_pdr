/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopStockAnnounceFlg;

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
     * クーポンコード取得
     *
     * @return the couponCode
     */
    String getCouponCode();

    /**
     * クーポンコード設定
     *
     * @param couponCode
     */
    void setCouponCode(String couponCode);

    // PDR Migrate Customization to here
    // 2023-renew No24 from here

    /**
     * クーポン名取得
     *
     * @return the couponName
     */
    String getCouponName();

    /**
     * クーポン名設定
     *
     * @param couponName
     */
    void setCouponName(String couponName);

    /**
     * 適用条件取得
     *
     * @return the couponConditions
     */
    String getCouponConditions();

    /**
     * 適用条件設定
     *
     * @param couponConditions
     */
    void setCouponConditions(String couponConditions);

    /**
     * 詳細説明取得
     *
     * @return the couponExplain
     */
    String getCouponExplain();

    /**
     * 詳細説明設定
     *
     * @param couponExplain
     */
    void setCouponExplain(String couponExplain);

    /**
     * クーポン数取得
     *
     * @return the couponCount
     */
    Integer getCouponCount();

    /**
     * クーポン数
     *
     * @param couponCount
     */
    void setCouponCount(Integer couponCount);

    // 2023-renew No24 to here
    // 2023-renew No60 from here

    /**
     * クレジットカードエラー回数（集計値）取得
     *
     * @return the couponCode
     */
    Integer getCreditErrorCount();

    /**
     * クレジットカードエラー回数（集計値）設定
     *
     * @param couponCode
     */
    void setCreditErrorCount(Integer couponCode);

    // 2023-renew No60 to here
    // 2023-renew No71 from here

    /**
     * トップセール通知フラグを取得する
     * */
    HTypeTopSaleAnnounceFlg getTopSaleAnnounceFlg();

    /**
     * トップセール通知フラグを設定する
     * */
    void setTopSaleAnnounceFlg(HTypeTopSaleAnnounceFlg topSaleAnnounceFlg);

    /**
     * セール通知既読フラグを取得する
     */
    HTypeSaleAnnounceWatchFlg getSaleAnnounceWatchFlg();

    /**
     * セール通知既読フラグを設定する
     */
    void setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg saleAnnounceWatchFlg);

    /**
     * 入荷通知フラグを取得
     */
    HTypeTopStockAnnounceFlg getTopStockAnnounceFlg();

    /**
     * 入荷通知フラグを設定する
     */
    void setTopStockAnnounceFlg(HTypeTopStockAnnounceFlg topStockAnnounceFlg);

    /**
     * 入荷通知既読フラグを取得する
     */
    HTypeStockAnnounceWatchFlg getStockAnnounceWatchFlg();

    /**
     * 入荷通知既読フラグを設定する
     */
    void setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg stockAnnounceWatchFlg);

    /**
     * マイページのアナウンスフラグを取得する
     */
    Boolean getMyPageAnnounceFlg();
    // 2023-renew No71 to here
}
