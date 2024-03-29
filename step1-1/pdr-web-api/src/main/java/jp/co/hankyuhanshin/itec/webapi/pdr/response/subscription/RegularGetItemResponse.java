/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription;

/**
 *
 * 定期便対象商品リスト取得API レスポンスモデル<br/>
 *
 * @author nh32114
 *
 */
public class RegularGetItemResponse {

    /** 申込商品 */
    private String goodsCode;

    /** 定期便対象区分 */
    private String targetType;

    /** 受付可否区分 */
    private String permissionType;

    /** おススメフラグ */
    private String recommendFlag;

    /**
     * @return the goodsCode
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * @param goodsCode the goodsCode to set
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * @return the permissionType
     */
    public String getPermissionType() {
        return permissionType;
    }

    /**
     * @return the targetType
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * @param targetType the targetType to set
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     * @param permissionType the permissionType to set
     */
    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * @return the recommendFlag
     */
    public String getRecommendFlag() {
        return recommendFlag;
    }

    /**
     * @param recommendFlag the recommendFlag to set
     */
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }
}
