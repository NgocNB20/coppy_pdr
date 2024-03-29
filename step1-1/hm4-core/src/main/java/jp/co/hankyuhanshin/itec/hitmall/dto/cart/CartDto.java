/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * カートDtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class CartDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 商品合計点数 */
    private BigDecimal goodsTotalCount;

    /** 商品合計金額（税抜） */
    private BigDecimal goodsTotalPrice;

    /** 商品合計金額（税込） */
    private BigDecimal goodsTotalPriceInTax;

    /** カート商品Dtoリスト */
    private List<CartGoodsDto> cartGoodsDtoList;

    /** 決済方法種別 */
    private HTypeSettlementMethodType settlementMethodType;

    // PDR Migrate Customization from here
    /** 割引適用結果MAP */
    private Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailMap;

    /** 消費税率MAP */
    private Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateMap;

    /** 振替前心意気商品コードMAP(公開中の商品のみ) */
    private Map<String, String> beforeTransferEmotionGoodsCodeMap;

    /** 商品合計消費税額 */
    private BigDecimal totalPriceTax;
    // PDR Migrate Customization to here

    // 2023-renew No14 from here
    /** 数量割引適用結果MAP */
    private Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountsResponseDetailMap;

    /** 取りおき情報MAP */
    private Map<String, WebApiGetReserveResponseDetailDto> reserveMap;

    /** 販売可否判定MAP */
    private Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap;
    // 2023-renew No14 to here

}
