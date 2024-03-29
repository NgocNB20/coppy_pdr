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
import java.sql.Timestamp;

/**
 * 注文フロー共通　取りおき情報アイテム Model
 *
 * @author satoh
 */
@Data
@Component
@Scope("prototype")
// PDR Migrate Customization from here
public class ReserveDeliveryItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 規格1表示フラグ
     */
    private boolean viewUnit1;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 規格値1
     */
    private String unitValue1;

    /**
     * 規格2表示フラグ
     */
    private boolean viewUnit2;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /**
     * 規格値2
     */
    private String unitValue2;

    /**
     * 商品数量
     */
    private BigDecimal orderGoodsCount;

    /**
     * 単位
     */
    private String unit;

    /**
     * 単価(税抜）
     */
    private BigDecimal goodsPrice;

    /**
     * 小計(税抜)
     */
    private BigDecimal goodsPriceTotalOutTax;

    /**
     * 商品画像
     */
    private String goodsImage;

    /**
     * 画像のsrc属性
     */
    private String goodsImageSrc;

    /**
     * 画像のalt属性
     */
    private String goodsImageAlt;

    /**
     * 商品お知らせ情報
     */
    private String goodsInformation;

    /**
     * 取りおき商品の過去受注数
     */
    private Integer reserveDeliveryGoodsHistoryCount;

    /**
     * お届け希望日
     */
    private Timestamp reserveDeliveryDate;

    /**
     * コンディション<br/>
     * 取りおき商品の過去受注数を表示するかどうか
     *
     * @return true...表示
     */
    public boolean isDispReserveDeliveryGoodsHistoryCount() {
        return reserveDeliveryGoodsHistoryCount != null && reserveDeliveryGoodsHistoryCount > 0;
    }

}
// PDR Migrate Customization to here
