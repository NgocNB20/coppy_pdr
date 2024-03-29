/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 定期便登録内容確認API レスポンスモデル<br/>
 *
 * @author tt32117
 */
public class RegularEntryCheckResponse {

    /** 初回お届け日 */
    private Timestamp firstDeliveryDate;

    /** 商品代金 */
    private BigDecimal goodsPriceTotal;

    /** 消費税 */
    private BigDecimal tax;

    /** 送料 */
    private BigDecimal carriage;

    /** 送料コメント */
    private String carriageMemo;

    /** 請求金額 */
    private BigDecimal billPriceTotal;

    /**
     * @return the firstDeliveryDate
     */
    public Timestamp getFirstDeliveryDate() {
        return firstDeliveryDate;
    }

    /**
     * @param firstDeliveryDate the firstDeliveryDate to set
     */
    public void setFirstDeliveryDate(Timestamp firstDeliveryDate) {
        this.firstDeliveryDate = firstDeliveryDate;
    }

    /**
     * @return the goodsPriceTotal
     */
    public BigDecimal getGoodsPriceTotal() {
        return goodsPriceTotal;
    }

    /**
     * @param goodsPriceTotal the goodsPriceTotal to set
     */
    public void setGoodsPriceTotal(BigDecimal goodsPriceTotal) {
        this.goodsPriceTotal = goodsPriceTotal;
    }

    /**
     * @return the tax
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    /**
     * @return the carriage
     */
    public BigDecimal getCarriage() {
        return carriage;
    }

    /**
     * @param carriage the carriage to set
     */
    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }

    /**
     * @return the carriageMemo
     */
    public String getCarriageMemo() {
        return carriageMemo;
    }

    /**
     * @param carriageMemo the carriageMemo to set
     */
    public void setCarriageMemo(String carriageMemo) {
        this.carriageMemo = carriageMemo;
    }

    /**
     * @return the billPriceTotal
     */
    public BigDecimal getBillPriceTotal() {
        return billPriceTotal;
    }

    /**
     * @param billPriceTotal the billPriceTotal to set
     */
    public void setBillPriceTotal(BigDecimal billPriceTotal) {
        this.billPriceTotal = billPriceTotal;
    }
}
