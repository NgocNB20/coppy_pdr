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
 * 定期便登録内容取得API レスポンスモデル<br/>
 *
 * @author tt32117
 */
public class RegularGetEntryResponse {

    /** 詳細番号 */
    private String detailNo;

    /** 申込商品 */
    private String goodsCode;

    /** 数量 */
    private String quantity;

    /** サイクル */
    private String cycle;

    /** 前回出荷日 */
    private Timestamp preShipmentDate;

    /** 次回お届け予定日 */
    private Timestamp nextDeliveryDate;

    /** お届け先顧客番号 */
    private String receiveCustomerNo;

    /** お届け先事業所名 */
    private String receiveOfficeName;

    /** お届け先事業所名フリガナ */
    private String receiveOfficeKana;

    /** お届け先郵便番号 */
    private String receiveZipcode;

    /** お届け先住所(都道府県・市区町村) */
    private String receiveAddress1;

    /** お届け先住所(丁目・番地) */
    private String receiveAddress2;

    /** お届け先住所(建物名・部屋番号) */
    private String receiveAddress3;

    /** お届け先住所(方書1) */
    private String receiveAddress4;

    /** お届け先住所(方書2) */
    private String receiveAddress5;

    /** 送料コメント */
    private String carriageMemo;

    /** 出荷準備中フラグ */
    private String prepareFlag;

    /** 出荷可否区分 */
    private String shippableType;

    /** 後継品フラグ */
    private String replaceFlag;

    /** 後継品申込商品 */
    private String replaceGoodsCode;

    /** 商品代金 */
    private BigDecimal goodsPriceTotal;

    /** 消費税 */
    private BigDecimal tax;

    /** 送料 */
    private BigDecimal carriage;

    /** ご請求金額合計（目安） */
    private BigDecimal billPriceTotal;

    /**
     * @return the detailNo
     */
    public String getDetailNo() {
        return detailNo;
    }

    /**
     * @param detailNo the detailNo to set
     */
    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

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
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the cycle
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * @param cycle the cycle to set
     */
    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    /**
     * @return the preShipmentDate
     */
    public Timestamp getPreShipmentDate() {
        return preShipmentDate;
    }

    /**
     * @param preShipmentDate the preShipmentDate to set
     */
    public void setPreShipmentDate(Timestamp preShipmentDate) {
        this.preShipmentDate = preShipmentDate;
    }

    /**
     * @return the nextDeliveryDate
     */
    public Timestamp getNextDeliveryDate() {
        return nextDeliveryDate;
    }

    /**
     * @param nextDeliveryDate the nextDeliveryDate to set
     */
    public void setNextDeliveryDate(Timestamp nextDeliveryDate) {
        this.nextDeliveryDate = nextDeliveryDate;
    }

    /**
     * @return the receiveCustomerNo
     */
    public String getReceiveCustomerNo() {
        return receiveCustomerNo;
    }

    /**
     * @param receiveCustomerNo the receiveCustomerNo to set
     */
    public void setReceiveCustomerNo(String receiveCustomerNo) {
        this.receiveCustomerNo = receiveCustomerNo;
    }

    /**
     * @return the receiveOfficeName
     */
    public String getReceiveOfficeName() {
        return receiveOfficeName;
    }

    /**
     * @param receiveOfficeName the receiveOfficeName to set
     */
    public void setReceiveOfficeName(String receiveOfficeName) {
        this.receiveOfficeName = receiveOfficeName;
    }

    /**
     * @return the receiveOfficeKana
     */
    public String getReceiveOfficeKana() {
        return receiveOfficeKana;
    }

    /**
     * @param receiveOfficeKana the receiveOfficeKana to set
     */
    public void setReceiveOfficeKana(String receiveOfficeKana) {
        this.receiveOfficeKana = receiveOfficeKana;
    }

    /**
     * @return the receiveZipcode
     */
    public String getReceiveZipcode() {
        return receiveZipcode;
    }

    /**
     * @param receiveZipcode the receiveZipcode to set
     */
    public void setReceiveZipcode(String receiveZipcode) {
        this.receiveZipcode = receiveZipcode;
    }

    /**
     * @return the receiveAddress1
     */
    public String getReceiveAddress1() {
        return receiveAddress1;
    }

    /**
     * @param receiveAddress1 the receiveAddress1 to set
     */
    public void setReceiveAddress1(String receiveAddress1) {
        this.receiveAddress1 = receiveAddress1;
    }

    /**
     * @return the receiveAddress2
     */
    public String getReceiveAddress2() {
        return receiveAddress2;
    }

    /**
     * @param receiveAddress2 the receiveAddress2 to set
     */
    public void setReceiveAddress2(String receiveAddress2) {
        this.receiveAddress2 = receiveAddress2;
    }

    /**
     * @return the receiveAddress3
     */
    public String getReceiveAddress3() {
        return receiveAddress3;
    }

    /**
     * @param receiveAddress3 the receiveAddress3 to set
     */
    public void setReceiveAddress3(String receiveAddress3) {
        this.receiveAddress3 = receiveAddress3;
    }

    /**
     * @return the receiveAddress4
     */
    public String getReceiveAddress4() {
        return receiveAddress4;
    }

    /**
     * @param receiveAddress4 the receiveAddress4 to set
     */
    public void setReceiveAddress4(String receiveAddress4) {
        this.receiveAddress4 = receiveAddress4;
    }

    /**
     * @return the receiveAddress5
     */
    public String getReceiveAddress5() {
        return receiveAddress5;
    }

    /**
     * @param receiveAddress5 the receiveAddress5 to set
     */
    public void setReceiveAddress5(String receiveAddress5) {
        this.receiveAddress5 = receiveAddress5;
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
     * @return the prepareFlag
     */
    public String getPrepareFlag() {
        return prepareFlag;
    }

    /**
     * @param prepareFlag the prepareFlag to set
     */
    public void setPrepareFlag(String prepareFlag) {
        this.prepareFlag = prepareFlag;
    }

    /**
     * @return the shippableType
     */
    public String getShippableType() {
        return shippableType;
    }

    /**
     * @param shippableType the shippableType to set
     */
    public void setShippableType(String shippableType) {
        this.shippableType = shippableType;
    }

    /**
     * @return the replaceFlag
     */
    public String getReplaceFlag() {
        return replaceFlag;
    }

    /**
     * @param replaceFlag the replaceFlag to set
     */
    public void setReplaceFlag(String replaceFlag) {
        this.replaceFlag = replaceFlag;
    }

    /**
     * @return the replaceGoodsCode
     */
    public String getReplaceGoodsCode() {
        return replaceGoodsCode;
    }

    /**
     * @param replaceGoodsCode the replaceGoodsCode to set
     */
    public void setReplaceGoodsCode(String replaceGoodsCode) {
        this.replaceGoodsCode = replaceGoodsCode;
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
