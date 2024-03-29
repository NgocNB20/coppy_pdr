/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.sql.Timestamp;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 取りおき情報取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetReserveResponse {

    /** 商品コード */
    private String goodsCode;

    /** 取りおき可否 */
    private String reserveFlag;

    /** 適用割引 */
    private String discountFlag;

    // 2023-renew No14 from here
    /** 予約可能開始日 */
    private Timestamp possibleReserveFromDay;

    /** 予約可能終了日 */
    private Timestamp possibleReserveToDay;
    // 2023-renew No14 to here

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
     * @return the reserveFlag
     */
    public String getReserveFlag() {
        return reserveFlag;
    }

    /**
     * @param reserveFlag the reserveFlag to set
     */
    public void setReserveFlag(String reserveFlag) {
        this.reserveFlag = reserveFlag;
    }

    /**
     * @return the discountFlag
     */
    public String getDiscountFlag() {
        return discountFlag;
    }

    /**
     * @param discountFlag the discountFlag to set
     */
    public void setDiscountFlag(String discountFlag) {
        this.discountFlag = discountFlag;
    }

    // 2023-renew No14 from here

    /**
     * @return the possibleReserveFromDay
     */
    public Timestamp getPossibleReserveFromDay() {
        return possibleReserveFromDay;
    }

    /**
     * @param possibleReserveFromDay the possibleReserveFromDay to set
     */
    public void setPossibleReserveFromDay(Timestamp possibleReserveFromDay) {
        this.possibleReserveFromDay = possibleReserveFromDay;
    }

    /**
     * @return the possibleReserveToDay
     */
    public Timestamp getPossibleReserveToDay() {
        return possibleReserveToDay;
    }

    /**
     * @param possibleReserveToDay the possibleReserveToDay to set
     */
    public void setPossibleReserveToDay(Timestamp possibleReserveToDay) {
        this.possibleReserveToDay = possibleReserveToDay;
    }

    // 2023-renew No14 to here
}
