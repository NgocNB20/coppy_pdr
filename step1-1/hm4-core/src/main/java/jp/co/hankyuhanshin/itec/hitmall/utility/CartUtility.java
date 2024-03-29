/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * カート業務Utility
 *
 * @author kaneda
 */
@Component
public class CartUtility {

    // PDR Migrate Customization from here
    /**
     * 消費税率が8%
     */
    public static final double TAXRATE08 = 0.08;

    /**
     * 消費税率が10%
     */
    public static final double TAXRATE10 = 0.1;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;
    // PDR Migrate Customization to here

    /**
     * コンストラクタ<br/>
     *
     * @param goodsUtility GoodsUtility
     */
    @Autowired
    public CartUtility(GoodsUtility goodsUtility) {
        // PDR Migrate Customization from here
        this.goodsUtility = goodsUtility;
        // PDR Migrate Customization to here
    }

    // PDR Migrate Customization from here

    /**
     * WEB-API連携 で取得した情報を<br/>
     * カートDTOに反映する。
     *
     * <pre>
     * 以下WEB-APIで取得した値をカート商品DTOに設定します。
     * ・数量割引適用結果取得
     * ・消費税率取得
     * ・商品在庫数在庫数取得
     * </pre>
     *
     * @param cartDto  カートDTO
     * @param stockMap 商品在庫数MAP
     */
    public void createCartDtoByWebApiInfo(CartDto cartDto, Map<String, WebApiGetStockResponseDetailDto> stockMap) {
        List<CartGoodsDto> cartGoodsDtoList = cartDto.getCartGoodsDtoList();

        // 2023-renew No14 from here
        // 数量割引適用結果MAP
        Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> discountsDtlDtoMap =
                        cartDto.getQuantityDiscountsResponseDetailMap();
        // 2023-renew No14 to here

        // 消費税率MAP
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateDtlDtoMap = cartDto.getConsumptionTaxRateMap();

        if (discountsDtlDtoMap == null || taxRateDtlDtoMap == null || stockMap == null) {
            return;
        }

        BigDecimal taxRate08 = new BigDecimal(0);
        BigDecimal taxRate10 = new BigDecimal(0);

        for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
            GoodsDetailsDto goodsDetailsDto = cartGoodsDto.getGoodsDetailsDto();

            // 商品コード
            String goodsCode = cartGoodsDto.getGoodsDetailsDto().getGoodsCode();

            // 2023-renew No14 from here
            // 数量割引適用結果
            WebApiGetQuantityDiscountResultResponseDetailDto discountsDto = discountsDtlDtoMap.get(goodsCode);
            // 2023-renew No14 to here

            // 数量割引適用結果MAP以外は商品コードkpつきで格納されていないため、
            // 心意気商品コードの場合、kpを削除する
            goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode);

            // 消費税率
            WebApiGetConsumptionTaxRateResponseDetailDto taxDto = taxRateDtlDtoMap.get(goodsCode);

            // 商品在庫数
            WebApiGetStockResponseDetailDto stockDto = stockMap.get(goodsCode);

            if (discountsDto != null) {
                // 2023-renew No14 from here
                // セールde予約により、通常／心意気以外でもカート内で同商品が存在するケースが生じるので、数量割引適用結果ではなくカートDTOから個数を取得
                // ※割引適用結果でカート再構築した後に当メソッドを呼んでいれば問題なし
                BigDecimal goodsQuantity = cartGoodsDto.getCartGoodsEntity().getCartGoodsCount();
                // 2023-renew No14 to here

                // 値引き後単価にWEB-APIで取得した割引価格を設定
                goodsDetailsDto.setGoodsPrice(discountsDto.getSalePrice());

                // 消費税率を設定
                if (taxDto != null) {
                    goodsDetailsDto.setTaxRate(taxDto.getTaxRate());
                    // 消費税額を計算
                    if (goodsDetailsDto.getTaxRate().compareTo(BigDecimal.valueOf(TAXRATE08)) == 0) {
                        taxRate08 = taxRate08.add(goodsDetailsDto.getGoodsPrice().multiply(goodsQuantity));
                    }
                    if (goodsDetailsDto.getTaxRate().compareTo(BigDecimal.valueOf(TAXRATE10)) == 0) {
                        taxRate10 = taxRate10.add(goodsDetailsDto.getGoodsPrice().multiply(goodsQuantity));
                    }
                }
            }

            // 在庫数・販売可否判定結果・販売不可メッセージを設定
            if (stockDto != null) {
                goodsDetailsDto.setSalesPossibleStock(stockDto.getStockQuantity());
                goodsDetailsDto.setSaleYesNo(stockDto.getSaleYesNo());
                goodsDetailsDto.setSaleNgMessage(stockDto.getSaleNgMessage());
                goodsDetailsDto.setDeliveryYesNo(stockDto.getDeliveryYesNo());
            }
        }

        // 消費税額を計算
        BigDecimal taxTotalPrice = new BigDecimal(0);
        taxTotalPrice = taxTotalPrice.add(
                        goodsUtility.calculationGoodsPriceTax(taxRate08, BigDecimal.valueOf(TAXRATE08)));
        taxTotalPrice = taxTotalPrice.add(
                        goodsUtility.calculationGoodsPriceTax(taxRate10, BigDecimal.valueOf(TAXRATE10)));
        cartDto.setTotalPriceTax(taxTotalPrice);
    }

    /**
     * カートDTOリストから<br/>
     * 商品コードリストを作成します。
     *
     * @param cartGoodsDtoList カートDTOリスト
     * @return 商品コードリスト
     */
    public List<String> getGoodsCodeList(List<CartGoodsDto> cartGoodsDtoList) {
        List<String> goodsCodeList = new ArrayList<>();
        for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
            goodsCodeList.add(cartGoodsDto.getGoodsDetailsDto().getGoodsCode());
        }
        return goodsCodeList;
    }

    /**
     * カートDTOリストから<br/>
     * 数量リストを作成します。
     *
     * @param cartGoodsDtoList カートDTOリスト
     * @return 数量コードリスト
     */
    public List<String> getQuantityList(List<CartGoodsDto> cartGoodsDtoList) {
        List<String> quantityList = new ArrayList<>();
        for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
            quantityList.add(cartGoodsDto.getCartGoodsEntity().getCartGoodsCount().toString());
        }
        return quantityList;
    }

    // PDR Migrate Customization to here
    // 2023-renew No2 from here

    /**
     * 販売可否判定チェック
     *
     * @param goodsCode    商品コード（チェック対象のカート商品）
     * @param saleCheckMap 販売可否判定Map
     * @return TRUE：可否チェックエラー、FALSE：問題なし
     */
    public boolean checkSaleByWebApi(String goodsCode, Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap) {

        // 対象商品の販売可否判定を取得（元商品の商品コードで取得）
        WebApiGetSaleCheckResponseDetailDto saleCheckDetailDto =
                        saleCheckMap.get(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode));

        return HTypeSaleCheckType.NO.getValue().equals(saleCheckDetailDto.getGoodsSaleYesNo().toString());
    }

    // 2023-renew No2 to here
    // 2023-renew No14 from here

    /**
     * 取りおき可否チェック
     *
     * @param goodsCode                        商品コード        （チェック対象のカート商品）
     * @param reserveDeliveryDate              取りおきお届け希望日（チェック対象のカート商品）
     * @param reserveFlag                      取りおきフラグ     （チェック対象のカート商品）
     * @param reserveMap                       取りおき情報MAP
     * @param calendarNotSelectDateEntityList  カレンダー選択不可日付リスト
     * @return TRUE：可否チェックエラー、FALSE：問題なし
     */
    public boolean checkReserveAvailability(String goodsCode,
                                            Timestamp reserveDeliveryDate,
                                            HTypeReserveDeliveryFlag reserveFlag,
                                            Map<String, WebApiGetReserveResponseDetailDto> reserveMap,
                                            List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList) {

        // 通常のカートINの場合はチェックしない
        if (HTypeReserveDeliveryFlag.OFF.equals(reserveFlag)) {
            return false;
        }

        // セールde予約によるカートINの場合のみ、以降の処理を行う。

        // 対象商品の取りおき情報を取得
        WebApiGetReserveResponseDetailDto reserveDetailDto = reserveMap.get(goodsCode);

        // 取りおき情報取得．取りおき可否＝取りおき不可の場合、エラー
        if (HTypeReserveDeliveryFlag.OFF.getValue().equals(reserveDetailDto.getReserveFlag())) {
            return true;
        }

        // 取りおき情報取得．予約可能開始日～予約可能終了日の期間外の場合、エラー
        Timestamp possibleReserveFromDay = reserveDetailDto.getPossibleReserveFromDay();
        Timestamp possibleReserveToDay = reserveDetailDto.getPossibleReserveToDay();
        if (reserveDeliveryDate == null || (possibleReserveFromDay != null && !reserveDeliveryDate.equals(
                        possibleReserveFromDay) && reserveDeliveryDate.before(possibleReserveFromDay)) || (
                            possibleReserveToDay != null && !reserveDeliveryDate.equals(possibleReserveToDay)
                            && reserveDeliveryDate.after(possibleReserveToDay))) {
            return true;
        }

        // カレンダー選択不可日付TBL．予約不可日に存在する日付がお届け日予定日の場合、エラー
        for (CalendarNotSelectDateEntity calendarNotSelectDateEntity : calendarNotSelectDateEntityList) {
            if (calendarNotSelectDateEntity.getNotPossibleReserveDate().equals(reserveDeliveryDate)) {
                return true;
            }
        }

        return false;
    }

    // 2023-renew No14 to here

}
