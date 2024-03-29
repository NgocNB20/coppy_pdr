/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 注文フロー共通　期間別 取りおき情報アイテム Model
 *
 * @author satoh
 */
@Data
@Component
@Scope("prototype")
// PDR Migrate Customization from here
public class ReserveDeliveryByPeriodItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 取りおき情報
     */
    private List<ReserveDeliveryItem> reserveDeliveryItems;

    /**
     * 注文合計(税抜)
     */
    private BigDecimal allGoodsPriceOutTaxByReserveDelivery;

    /**
     * 値引き価格
     */
    private BigDecimal goodsDiscountPriceByReserveDelivery;

    /**
     * 送料
     */
    private BigDecimal carriageByReserveDelivery;

    /**
     * 消費税
     */
    private BigDecimal totalTaxByReserveDelivery;

    /**
     * お支払い合計(税込)
     */
    private BigDecimal totalPriceInTaxByReserveDelivery;

    /**
     * お届け時期
     */
    private String deliveryTimeByReserveDelivery;

    /**
     * コンディション<br/>
     * 取りおき情報の値引き価格表示判定
     *
     * @return true...表示
     */
    public boolean isDispGoodsDiscountPriceByReserveDelivery() {
        return this.goodsDiscountPriceByReserveDelivery != null
               && BigDecimal.ZERO.compareTo(this.goodsDiscountPriceByReserveDelivery) < 0;
    }
}
// PDR Migrate Customization to here
