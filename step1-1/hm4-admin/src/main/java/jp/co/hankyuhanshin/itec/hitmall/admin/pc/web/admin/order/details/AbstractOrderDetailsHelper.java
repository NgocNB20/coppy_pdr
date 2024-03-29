/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 受注詳細Helper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Data
public class AbstractOrderDetailsHelper {

    /**
     * DateUtility
     */
    private DateUtility dateUtility;

    /**
     * ConversionUtility
     */
    private ConversionUtility conversionUtility;

    /**
     * CommonInfoUtility
     */
    private CommonInfoUtility commonInfoUtility;

    /**
     * 受注関連ユーティリティ
     */
    private OrderUtility orderUtility;

    /**
     * 金額計算ユーティリティ
     */
    private CalculatePriceUtility calculatePriceUtility;

    /**
     * コンストラクタ
     *
     * @param dateUtility
     * @param conversionUtility
     * @param commonInfoUtility
     * @param orderUtility
     * @param calculatePriceUtility
     */
    @Autowired
    public AbstractOrderDetailsHelper(DateUtility dateUtility,
                                      ConversionUtility conversionUtility,
                                      CommonInfoUtility commonInfoUtility,
                                      OrderUtility orderUtility,
                                      CalculatePriceUtility calculatePriceUtility) {
        this.dateUtility = dateUtility;
        this.conversionUtility = conversionUtility;
        this.commonInfoUtility = commonInfoUtility;
        this.orderUtility = orderUtility;
        this.calculatePriceUtility = calculatePriceUtility;
    }

    /**
     * 受注詳細系ページ項目設定<br/>
     *
     * @param orderAbstractDetailsModel 受注詳細ページ
     * @param receiveOrderDto           受注DTO
     */
    public void toPage(AbstractOrderDetailsModel orderAbstractDetailsModel, ReceiveOrderDto receiveOrderDto) {

        putOrderSummaryInOrder(receiveOrderDto);
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        Set<String> ignoreFieldNameSet = new HashSet<>();

        // 受注インデックス（受注履歴連番をセット)
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderIndexEntity(), orderAbstractDetailsModel,
                                                ignoreFieldNameSet
                                               ));

        // 受注お客様情報の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderPersonEntity(), orderAbstractDetailsModel,
                                                ignoreFieldNameSet
                                               ));

        // 受注配送情報、受注商品情報の値をセット
        setOrderDeliveryDto(orderAbstractDetailsModel, receiveOrderDto);

        // 受注決済の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderSettlementEntity(), orderAbstractDetailsModel,
                                                ignoreFieldNameSet
                                               ));
        orderAbstractDetailsModel.setPostTaxGoodsPriceTotal(
                        calculatePriceUtility.getTaxIncludedGoodsPriceTotal(receiveOrderDto));
        // PDR Migrate Customization from here
        orderAbstractDetailsModel.setOrderPriceExceptPromotionDiscount(
                        calculatePriceUtility.getOrderPriceExceptPromotionDiscount(
                                        receiveOrderDto.getOrderSettlementEntity()));
        // PDR Migrate Customization to here

        // 受注請求の値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(receiveOrderDto.getOrderBillEntity(), orderAbstractDetailsModel,
                                                ignoreFieldNameSet
                                               ));

        // 受注メモの値をセット
        if (receiveOrderDto.getOrderMemoEntity() != null) {
            ignoreFieldNameSet.addAll(
                            CopyUtil.copyProperties(receiveOrderDto.getOrderMemoEntity(), orderAbstractDetailsModel,
                                                    ignoreFieldNameSet
                                                   ));
        }

        CouponEntity coupon = receiveOrderDto.getCoupon();
        if (coupon != null) {
            // クーポンが適用されている場合のみクーポン情報を画面に反映する
            // クーポン名
            orderAbstractDetailsModel.setCouponName(coupon.getCouponDisplayNamePC());
            // クーポン割引額 画面上マイナス表示
            orderAbstractDetailsModel.setCouponDiscountPrice(
                            receiveOrderDto.getOrderSettlementEntity().getCouponDiscountPrice().negate());
            // 適用クーポン名
            orderAbstractDetailsModel.setApplyCouponName(coupon.getCouponName());
            // 適用クーポンID
            orderAbstractDetailsModel.setApplyCouponId(coupon.getCouponId());
            // クーポン利用制限フラグ
            orderAbstractDetailsModel.setCouponLimitTargetType(
                            receiveOrderDto.getOrderIndexEntity().getCouponLimitTargetType());
            // クーポン対象商品適用
            setCouponTargetGoods(orderAbstractDetailsModel, coupon);
        } else {
            // クーポンが適用されていない場合はクーポン情報を初期化する
            // クーポン割引額 割引額を0円として処理する
            orderAbstractDetailsModel.setCouponDiscountPrice(BigDecimal.ZERO);
            // 適用クーポン名
            orderAbstractDetailsModel.setApplyCouponName(null);
            // 適用クーポンID
            orderAbstractDetailsModel.setApplyCouponId(null);
        }

        // 受注サマリの値をセット
        ignoreFieldNameSet.addAll(
                        CopyUtil.copyProperties(orderSummaryEntity, orderAbstractDetailsModel, ignoreFieldNameSet));
        orderAbstractDetailsModel.setSettlementMailRequired(
                        receiveOrderDto.getOrderIndexEntity().getSettlementMailRequired());

        // 受注入金の値をセット
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList =
                        receiveOrderDto.getOrderReceiptOfMoneyEntityList();
        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        if (orderReceiptOfMoneyEntityList != null && !orderReceiptOfMoneyEntityList.isEmpty()) {
            int index = orderReceiptOfMoneyEntityList.size() - 1;
            orderReceiptOfMoneyEntity = orderReceiptOfMoneyEntityList.get(index);
        }
        ignoreFieldNameSet.addAll(CopyUtil.copyProperties(orderReceiptOfMoneyEntity, orderAbstractDetailsModel,
                                                          ignoreFieldNameSet
                                                         ));
        orderAbstractDetailsModel.setReceiptTime(orderReceiptOfMoneyEntity.getReceiptTime());

        // マルチペイメント請求情報の値をセット
        if (receiveOrderDto.getMulPayBillEntity() != null) {
            ignoreFieldNameSet.addAll(
                            CopyUtil.copyProperties(receiveOrderDto.getMulPayBillEntity(), orderAbstractDetailsModel,
                                                    ignoreFieldNameSet
                                                   ));

            if (orderAbstractDetailsModel.getConvenience() != null) {
                String conveniName = "";
                for (ConvenienceEntity ce : orderAbstractDetailsModel.getConvenienceAllList()) {
                    if (orderAbstractDetailsModel.getConvenience() != null && orderAbstractDetailsModel.getConvenience()
                                                                                                       .equals(ce.getConveniCode())) {
                        conveniName = orderUtility.getConveniName(ce);
                        break;
                    }
                }
                orderAbstractDetailsModel.setConveniName(conveniName);
            }
        }

        // 入金状態をセット
        orderAbstractDetailsModel.setPaymentStatus(getPaymentStatus(orderSummaryEntity, orderReceiptOfMoneyEntity));

        // 受注追加料金をセット
        setOrderAdditionalChargeDtoList(orderAbstractDetailsModel, receiveOrderDto);

        // 決済方法名をセット
        orderAbstractDetailsModel.setSettlementMethodName(
                        receiveOrderDto.getSettlementMethodEntity().getSettlementMethodName());
        orderAbstractDetailsModel.setCardBrandDisplayPc(receiveOrderDto.getOrderBillEntity().getCardBrandDisplayPC());

        // 請求金額に受注金額をセット
        // キャンセル後に表示される値がマイナス値になってしまう為
        orderAbstractDetailsModel.setBillPrice(orderSummaryEntity.getOrderPrice());
        // PDR Migrate Customization from here
        orderAbstractDetailsModel.setBillPriceExceptPromotionDiscount(
                        calculatePriceUtility.getOrderPriceExceptPromotionDiscount(
                                        receiveOrderDto.getOrderSettlementEntity()));
        // PDR Migrate Customization to here

        // オーソリ期限日（決済日付＋オーソリ保持期間）
        orderAbstractDetailsModel.setAuthoryLimitDate(receiveOrderDto.getAuthoryLimitDate());

        // 注文保留理由
        orderAbstractDetailsModel.setOrderWaitingMemo(receiveOrderDto.getOrderIndexEntity().getOrderWaitingMemo());
    }

    /**
     * クーポン対象商品取得<br/>
     *
     * @param orderAbstractDetailsModel ページクラス
     * @param coupon                    クーポンエンティティ
     */
    protected void setCouponTargetGoods(AbstractOrderDetailsModel orderAbstractDetailsModel, CouponEntity coupon) {
        // クーポン対象商品が入力されていない場合は判定しない
        if (coupon.getTargetGoods() == null) {
            return;
        }
        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(coupon.getTargetGoods()));
        for (OrderGoodsUpdateItem orderGoodsUpdateItem : orderAbstractDetailsModel.getOrderReceiverItem()
                                                                                  .getOrderGoodsUpdateItems()) {
            if (targetGoodsList.contains(orderGoodsUpdateItem.getGoodsCode())) {
                orderGoodsUpdateItem.setCouponTargetGoodsFlg(true);
            }
        }
    }

    /**
     * 受注サマリー情報の精査<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void putOrderSummaryInOrder(ReceiveOrderDto receiveOrderDto) {
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList =
                        receiveOrderDto.getOrderReceiptOfMoneyEntityList();

        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        if (orderReceiptOfMoneyEntityList != null && !orderReceiptOfMoneyEntityList.isEmpty()) {
            int index = orderReceiptOfMoneyEntityList.size() - 1;
            orderReceiptOfMoneyEntity = orderReceiptOfMoneyEntityList.get(index);
        }
        orderSummaryEntity.setReceiptPriceTotal(orderReceiptOfMoneyEntity.getReceiptPriceTotal());
        orderSummaryEntity.setOrderPrice(orderSettlementEntity.getOrderPrice());
    }

    /**
     * 入金ステータス取得<br/>
     *
     * @param orderSummaryEntity        受注サマリーエンティティ
     * @param orderReceiptOfMoneyEntity 受注入金エンティティ
     * @return 入金ステータス
     */
    protected String getPaymentStatus(OrderSummaryEntity orderSummaryEntity,
                                      OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity) {
        BigDecimal receiptPriceTotal = BigDecimal.ZERO;
        BigDecimal orderPrice = orderSummaryEntity.getOrderPrice();
        if (orderReceiptOfMoneyEntity != null) {
            receiptPriceTotal = orderReceiptOfMoneyEntity.getReceiptPriceTotal();
        }

        if (receiptPriceTotal.equals(BigDecimal.ZERO)) {
            return "1";
        }

        int status = orderPrice.compareTo(receiptPriceTotal);
        if (status == 0) {
            return "2";
        } else {
            return "3";
        }
    }

    /**
     * 追加料金情報リストをセット<br/>
     *
     * @param orderAbstractDetailsModel セットするページ
     * @param receiveOrderDto           受注DTO
     */
    protected void setOrderAdditionalChargeDtoList(AbstractOrderDetailsModel orderAbstractDetailsModel,
                                                   ReceiveOrderDto receiveOrderDto) {
        List<OrderAdditionalChargeItem> orderAdditionalChargeItems = new ArrayList<>();
        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList =
                        receiveOrderDto.getOrderAdditionalChargeEntityList();
        for (OrderAdditionalChargeEntity entity : orderAdditionalChargeEntityList) {
            OrderAdditionalChargeItem item = toOrderAdditionalChargeItem(entity);
            orderAdditionalChargeItems.add(item);
        }
        orderAbstractDetailsModel.setOrderAdditionalChargeItems(orderAdditionalChargeItems);
    }

    /**
     * @param orderAdditionalChargeEntity 受注追加料金エンティティ
     * @return 追加料金アイテム
     */
    protected OrderAdditionalChargeItem toOrderAdditionalChargeItem(OrderAdditionalChargeEntity orderAdditionalChargeEntity) {
        OrderAdditionalChargeItem item = ApplicationContextUtility.getBean(OrderAdditionalChargeItem.class);
        CopyUtil.copyProperties(orderAdditionalChargeEntity, item, null);
        return item;
    }

    /**
     * 受注配送情報リストをセット<br/>
     * 配送情報内の商品情報リストをセット<br/>
     *
     * @param orderAbstractDetailsModel セットするページ
     * @param receiveOrderDto           受注DTO
     */
    protected void setOrderDeliveryDto(AbstractOrderDetailsModel orderAbstractDetailsModel,
                                       ReceiveOrderDto receiveOrderDto) {

        // お届け先情報をセット
        BigDecimal orderGoodsCountTotal = BigDecimal.ZERO;
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 納品書 全配送共通の為、itemでは保持しない
        orderAbstractDetailsModel.setInvoiceAttachmentFlag(
                        orderDeliveryDto.getOrderDeliveryEntity().getInvoiceAttachmentFlag());
        OrderReceiverUpdateItem receiverItem = toOrderReceiverItem(orderDeliveryDto.getOrderDeliveryEntity(),
                                                                   orderDeliveryDto.getDeliveryMethodEntity()
                                                                  );

        // 商品情報リストをセット
        List<OrderGoodsEntity> orderGoodsList = orderDeliveryDto.getOrderGoodsEntityList();
        List<OrderGoodsUpdateItem> orderGoodsItems = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsList) {
            OrderGoodsUpdateItem goodsItem = toOrderGoodsUpdateItem(orderGoodsEntity);
            orderGoodsItems.add(goodsItem);
            orderGoodsCountTotal = orderGoodsCountTotal.add(orderGoodsEntity.getGoodsCount());
        }

        // 配送アイテムに商品アイテムリストをセット
        receiverItem.setOrderGoodsUpdateItems(orderGoodsItems);

        orderAbstractDetailsModel.setOrderReceiverItem(receiverItem);
        orderAbstractDetailsModel.setOrderGoodsCountTotal(orderGoodsCountTotal);
    }

    /**
     * @param orderDeliveryEntity  受注配送エンティティ
     * @param deliveryMethodEntity 配送エンティティ
     * @return お届け先アイテム
     */
    protected OrderReceiverUpdateItem toOrderReceiverItem(OrderDeliveryEntity orderDeliveryEntity,
                                                          DeliveryMethodEntity deliveryMethodEntity) {
        OrderReceiverUpdateItem receiverItem = ApplicationContextUtility.getBean(OrderReceiverUpdateItem.class);
        CopyUtil.copyProperties(orderDeliveryEntity, receiverItem, null);
        CopyUtil.copyProperties(deliveryMethodEntity, receiverItem, null);
        return receiverItem;
    }

    /**
     * @param orderGoodsEntity 受注商品エンティティ
     * @return 商品アイテム
     */
    protected OrderGoodsUpdateItem toOrderGoodsUpdateItem(OrderGoodsEntity orderGoodsEntity) {
        Set<String> ignoreFieldNameSet = new HashSet<>();
        ignoreFieldNameSet.add("messageCardOrderType");
        ignoreFieldNameSet.add("bagOrderType");
        ignoreFieldNameSet.add("packingOrderType");

        OrderGoodsUpdateItem goodsItem = ApplicationContextUtility.getBean(OrderGoodsUpdateItem.class);
        CopyUtil.copyProperties(orderGoodsEntity, goodsItem, ignoreFieldNameSet);

        // 税込み価格を設定
        goodsItem.setGoodsPriceInTax(calculatePriceUtility.getTaxIncludedPrice(orderGoodsEntity.getGoodsPrice(),
                                                                               orderGoodsEntity.getTaxRate()
                                                                              ));

        return goodsItem;
    }
}
