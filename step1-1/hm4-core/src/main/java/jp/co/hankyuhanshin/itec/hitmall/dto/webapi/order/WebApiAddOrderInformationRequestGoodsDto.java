/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 受注連携 詳細 商品情報
 * </pre>
 *
 * @author kato
 */
@Data
@Component
@Scope("prototype")
public class WebApiAddOrderInformationRequestGoodsDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

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
    // PDR Migrate Customization to here
}
