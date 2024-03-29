/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.math.BigDecimal;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 受注連携APIのリクエストモデル（商品）<br/>
 *
 * @author k-katoh
 */
public class AddOrderMerchandiseInformationRequest {

    /** 受注連番 */
    private String orderSeq;

    /** 申込商品 */
    private String goodsNo;

    /** 数量 */
    private String quantity;

    /** 単価 */
    private BigDecimal unitPrice;

    /** 金額 */
    private BigDecimal price;

    /** 状態フラグ */
    private String stateFlag;

    /** セールコード */
    private String saleCode;

    /** 備考 */
    private String remarks;

    /** 注意事項 */
    private String hints;

    /** 同梱商品フラグ */
    private String bundleFlag;

    /** グループ */
    private String groupCode;

    /** 明細番号 */
    private String detailNo;

    /** 適用割引 */
    private String discountFlag;

    /** 標準単価 */
    private BigDecimal basePrice;

    /** セール値引額 */
    private BigDecimal saleDiscount;

    /** 単価値引額 */
    private BigDecimal unitDiscountPrice;

    /**
     * @return the orderSeq
     */
    public String getOrderSeq() {
        return orderSeq;
    }

    /**
     * @param orderSeq the orderSeq to set
     */
    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    /**
     * @return the goodsNo
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * @param goodsNo the goodsNo to set
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
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
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the stateFlag
     */
    public String getStateFlag() {
        return stateFlag;
    }

    /**
     * @param stateFlag the stateFlag to set
     */
    public void setStateFlag(String stateFlag) {
        this.stateFlag = stateFlag;
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
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the hints
     */
    public String getHints() {
        return hints;
    }

    /**
     * @param hints the hints to set
     */
    public void setHints(String hints) {
        this.hints = hints;
    }

    /**
     * @return the bundleFlag
     */
    public String getBundleFlag() {
        return bundleFlag;
    }

    /**
     * @param bundleFlag the bundleFlag to set
     */
    public void setBundleFlag(String bundleFlag) {
        this.bundleFlag = bundleFlag;
    }

    /**
     * @return the groupCode
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * @param groupCode the groupCode to set
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

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

    /**
     * @return the basePrice
     */
    public BigDecimal getBasePrice() {
        return basePrice;
    }

    /**
     * @param basePrice the basePrice to set
     */
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * @return the saleDiscount
     */
    public BigDecimal getSaleDiscount() {
        return saleDiscount;
    }

    /**
     * @param saleDiscount the saleDiscount to set
     */
    public void setSaleDiscount(BigDecimal saleDiscount) {
        this.saleDiscount = saleDiscount;
    }

    /**
     * @return the unitDiscountPrice
     */
    public BigDecimal getUnitDiscountPrice() {
        return unitDiscountPrice;
    }

    /**
     * @param unitDiscountPrice the unitDiscountPrice to set
     */
    public void setUnitDiscountPrice(BigDecimal unitDiscountPrice) {
        this.unitDiscountPrice = unitDiscountPrice;
    }

}
