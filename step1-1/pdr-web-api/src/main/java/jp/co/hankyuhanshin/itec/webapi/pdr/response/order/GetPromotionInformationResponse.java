/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 プロモーションAPIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetPromotionInformationResponse {

    /** 金額情報リスト */
    private List<GetPromotionAmountInformationResponse> priceInfo;

    /** 同梱情報リスト */
    private List<GetPromotionBundledInformationResponse> bundleInfo;

    /** 割引情報リスト */
    private List<GetPromotionDiscountInformationResponse> discountInfo;

    /** クーポン以外プロモーション適用結果 */
    private String promApplyFlag;

    /** クーポンプロモーション適用結果 */
    private String couponApplyFlag;

    /** クーポン名称 */
    private String couponName;

    /**
     * @return the discountInfo
     */
    public List<GetPromotionDiscountInformationResponse> getDiscountInfo() {
        return discountInfo;
    }

    /**
     * @param discountInfo the discountInfo to set
     */
    public void setDiscountInfo(List<GetPromotionDiscountInformationResponse> discountInfo) {
        this.discountInfo = discountInfo;
    }

    /**
     * @return the priceInfo
     */
    public List<GetPromotionAmountInformationResponse> getPriceInfo() {
        return priceInfo;
    }

    /**
     * @param priceInfo the priceInfo to set
     */
    public void setPriceInfo(List<GetPromotionAmountInformationResponse> priceInfo) {
        this.priceInfo = priceInfo;
    }

    /**
     * @return the bundleInfo
     */
    public List<GetPromotionBundledInformationResponse> getBundleInfo() {
        return bundleInfo;
    }

    /**
     * @param bundleInfo the bundleInfo to set
     */
    public void setBundleInfo(List<GetPromotionBundledInformationResponse> bundleInfo) {
        this.bundleInfo = bundleInfo;
    }

    /**
     * @return the promApplyFlag
     */
    public String getPromApplyFlag() {
        return promApplyFlag;
    }

    /**
     * @param promApplyFlag the promApplyFlag to set
     */
    public void setPromApplyFlag(String promApplyFlag) {
        this.promApplyFlag = promApplyFlag;
    }

    /**
     * @return the couponApplyFlag
     */
    public String getCouponApplyFlag() {
        return couponApplyFlag;
    }

    /**
     * @param couponApplyFlag the couponApplyFlag to set
     */
    public void setCouponApplyFlag(String couponApplyFlag) {
        this.couponApplyFlag = couponApplyFlag;
    }

    /**
     * @return the couponName
     */
    public String getCouponName() {
        return couponName;
    }

    /**
     * @param couponName the couponName to set
     */
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
}
