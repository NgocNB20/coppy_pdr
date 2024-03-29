/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 金額計算のUtilityクラス
 */
@Component
public class CalculatePriceUtility {

    /**
     * 税金算出時のROUNDINGモード（切り上げ、切り捨て）の定数
     */
    // PDR Migrate Customization from here
    // PDR案件では切り捨て
    public static final RoundingMode DEFAULT_TAX_ROUNDING_MODE = RoundingMode.DOWN;
    // PDR Migrate Customization from here

    /**
     * 税率算出用の定数 100
     */
    protected static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 消費税額を算出
     *
     * @param price   金額(税抜)
     * @param taxRate 消費税率（10, 8）
     * @return 消費税額
     */
    public BigDecimal getTaxPrice(BigDecimal price, BigDecimal taxRate) {
        if (price == null) {
            return null;
        }

        return getTaxPrice(price, taxRate, DEFAULT_TAX_ROUNDING_MODE);
    }

    /**
     * 消費税額を算出(丸めモードを指定)
     *
     * @param price   金額(税抜)
     * @param taxRate 消費税率(10, 8)
     * @param mode    丸めモード
     * @return 消費税額
     */
    protected BigDecimal getTaxPrice(BigDecimal price, BigDecimal taxRate, RoundingMode mode) {

        // 税率を算出
        BigDecimal rate = taxRate.divide(ONE_HUNDRED);
        // 金額(税込)を計算
        BigDecimal resultPrice = price.multiply(rate);
        // 端数切り上げ
        resultPrice = resultPrice.setScale(0, mode);

        return resultPrice;
    }

    /**
     * 税込金額を算出
     *
     * @param price   金額(税抜)
     * @param taxRate 消費税率（％）
     * @return 金額(税込)
     */
    public BigDecimal getTaxIncludedPrice(BigDecimal price, BigDecimal taxRate) {
        if (price == null) {
            return null;
        }

        return price.add(getTaxPrice(price, taxRate, DEFAULT_TAX_ROUNDING_MODE));
    }

    /**
     * 税込商品合計金額を算出
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品合計金額(税込)
     */
    public BigDecimal getTaxIncludedGoodsPriceTotal(ReceiveOrderDto receiveOrderDto) {
        BigDecimal postTaxGoodsPriceTotal = BigDecimal.ZERO;

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        List<OrderGoodsEntity> orderGoodsList = orderDeliveryDto.getOrderGoodsEntityList();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsList) {
            // 税込金額を算出
            BigDecimal postTaxPrice =
                            getTaxIncludedPrice(orderGoodsEntity.getGoodsPrice(), orderGoodsEntity.getTaxRate());
            // 合計を算出
            BigDecimal postTaxTotal = postTaxPrice.multiply(orderGoodsEntity.getGoodsCount());
            // 合計を加算
            postTaxGoodsPriceTotal = postTaxGoodsPriceTotal.add(postTaxTotal);
        }

        return postTaxGoodsPriceTotal;
    }

    /**
     * 利用可能な決済方法を取得するSQL用ConditionDtoを生成する
     *
     * @param receiveOrderDto       受注Dto
     * @param siteType              サイト種別：列挙型
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 決済方法Dao用検索条件DTO
     */
    public SettlementSearchForDaoConditionDto getSettlementSearchForDaoConditionDto(ReceiveOrderDto receiveOrderDto,
                                                                                    HTypeSiteType siteType,
                                                                                    OrderSettlementEntity orderSettlementEntity) {
        SettlementSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(SettlementSearchForDaoConditionDto.class);

        // 公開状態
        if (siteType.isFrontPC() || siteType.isFrontSP()) {
            // サイト種別がPCなら、PCに公開中のものを取得
            conditionDto.setOpenStatusPC(HTypeOpenDeleteStatus.OPEN);
        } else if (siteType.isFrontMB()) {
            // サイト種別が携帯なら、携帯に公開中のものを取得
            conditionDto.setOpenStatusMB(HTypeOpenDeleteStatus.OPEN);
        }

        // 手数料算定基準金額(税込)を計算する
        // ①商品金額合計 + ②送料 + ③その他金額合計 + ①②③の消費税 - クーポン割引額 - ポイント利用額
        // この金額は決済手数料の取得条件ともなる
        BigDecimal commissionLessOrderPrice = getCommissionLessTaxIncludedOrderPrice(orderSettlementEntity);
        conditionDto.setSettlementCharge(commissionLessOrderPrice);
        // ショップSEQ
        conditionDto.setShopSeq(1001);
        // SQLでの消費税計算用
        conditionDto.setReducedTaxTargetPrice(orderSettlementEntity.getReducedTaxTargetPrice());
        conditionDto.setReducedTaxRate(
                        BigDecimal.ONE.add(receiveOrderDto.getMasterDto().getTaxRateReduced().divide(ONE_HUNDRED)));
        conditionDto.setStandardTaxTargetPrice(orderSettlementEntity.getStandardTaxTargetPrice());
        conditionDto.setStandardTaxRate(
                        BigDecimal.ONE.add(receiveOrderDto.getMasterDto().getTaxRateStandard().divide(ONE_HUNDRED)));
        conditionDto.setCouponDiscountPrice(orderSettlementEntity.getCouponDiscountPrice());
        conditionDto.setPointDiscountPrice(orderSettlementEntity.getPointDiscountPrice());
        return conditionDto;
    }

    /**
     * 手数料算出の基になる決済金額(税込)を取得する。<br />
     * <p>
     * 商品金額合計（税抜）+ 送料 + その他金額合計 + 消費税額 - クーポン割引額 - ポイント割引額
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 決済金額(税込)
     */
    public BigDecimal getCommissionLessTaxIncludedOrderPrice(OrderSettlementEntity orderSettlementEntity) {
        // 商品金額合計
        BigDecimal goodsPrice = orderSettlementEntity.getGoodsPriceTotal();
        // 送料
        BigDecimal carriage = orderSettlementEntity.getCarriage();
        // その他金額合計
        BigDecimal othersPrice = orderSettlementEntity.getOthersPriceTotal();
        // 消費税
        BigDecimal taxPrice = orderSettlementEntity.getTaxPrice();
        // クーポン割引額
        BigDecimal couponDiscountPrice = orderSettlementEntity.getCouponDiscountPrice();
        // ポイント割引額
        BigDecimal pointDiscountPrice = orderSettlementEntity.getPointDiscountPrice();

        return goodsPrice.add(carriage)
                         .add(othersPrice)
                         .add(taxPrice)
                         .subtract(couponDiscountPrice)
                         .subtract(pointDiscountPrice);
    }

    /**
     * 割引前受注金額(税込)を取得
     *
     * @param receiveOrderDto 受注Dto
     * @return 割引前受注金額(税込)
     */
    public BigDecimal getTaxIncludedBeforeDiscountOrderPrice(ReceiveOrderDto receiveOrderDto) {
        OrderSettlementEntity settlementEntity = receiveOrderDto.getOrderSettlementEntity();
        // 商品金額合計
        BigDecimal goodsPrice = settlementEntity.getGoodsPriceTotal();
        // 送料
        BigDecimal carriage = settlementEntity.getCarriage();
        // その他金額合計
        BigDecimal othersPrice = settlementEntity.getOthersPriceTotal();
        // 決済手数料
        BigDecimal commission = settlementEntity.getSettlementCommission();
        // 消費税
        BigDecimal taxPrice = settlementEntity.getTaxPrice();

        return goodsPrice.add(carriage).add(othersPrice).add(commission).add(taxPrice);
    }

    // PDR Migrate Customization from here

    /**
     * プロモーション値引を除いた受注金額(税込)を取得する。<br />
     * <p>
     * 商品金額合計（税抜）+ 送料 + その他金額合計 + 決済手数料 + 消費税額 - クーポン割引額
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 決済金額(税込)
     */
    public BigDecimal getOrderPriceExceptPromotionDiscount(OrderSettlementEntity orderSettlementEntity) {
        // 商品金額合計
        BigDecimal goodsPrice = orderSettlementEntity.getGoodsPriceTotal();
        // 送料
        BigDecimal carriage = orderSettlementEntity.getCarriage();
        // その他金額合計
        BigDecimal othersPrice = orderSettlementEntity.getOthersPriceTotal();
        // 決済手数料
        BigDecimal commission = orderSettlementEntity.getSettlementCommission();
        // 消費税
        BigDecimal taxPrice = orderSettlementEntity.getTaxPrice();
        // クーポン割引額
        BigDecimal couponDiscountPrice = orderSettlementEntity.getCouponDiscountPrice();

        return goodsPrice.add(carriage).add(othersPrice).add(commission).add(taxPrice).subtract(couponDiscountPrice);
    }

    // PDR Migrate Customization to here
}
