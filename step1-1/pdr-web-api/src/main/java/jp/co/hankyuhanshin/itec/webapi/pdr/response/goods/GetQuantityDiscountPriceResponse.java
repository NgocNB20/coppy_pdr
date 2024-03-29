/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.goods;

import java.math.BigDecimal;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 数量割引情報取得APIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetQuantityDiscountPriceResponse {

    /** 申込商品 */
    private BigDecimal price;

    /** 数量閾値 */
    private String level;

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
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }
}
