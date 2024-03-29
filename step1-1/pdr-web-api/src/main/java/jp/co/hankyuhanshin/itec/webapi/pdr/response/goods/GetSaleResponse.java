/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * WEB-API商品セール情報取得APIのレスポンスモデル<br/>
 *
 * @author st75001
 */
public class GetSaleResponse {

    /** 商品コード */
    private String goodsCode;

    /** セール状態 */
    private String saleStatus;

    /** セールコード */
    private String saleCode;

    /** セール期間From */
    private Timestamp saleFrom;

    /** セール期間To */
    private Timestamp saleTo;

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
     * @return the saleStatus
     */
    public String getSaleStatus() {
        return saleStatus;
    }

    /**
     * @param saleStatus the saleStatus to set
     */
    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    /**
     * @return the saleCode
     */
    public String getSaleCode() {
        return saleCode;
    }

    /**
     * @param saleCode the saleCode to set
     */
    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    /**
     * @return the saleFrom
     */
    public Timestamp getSaleFrom() {
        return saleFrom;
    }

    /**
     * @param saleFrom the saleFrom to set
     */
    public void setSaleFrom(Timestamp saleFrom) {
        this.saleFrom = saleFrom;
    }

    /**
     * @return the saleTo
     */
    public Timestamp getSaleTo() {
        return saleTo;
    }

    /**
     * @param saleTo the saleTo to set
     */
    public void setSaleTo(Timestamp saleTo) {
        this.saleTo = saleTo;
    }
}
