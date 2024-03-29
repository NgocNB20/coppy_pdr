/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.ConvenienceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailTemplateDummyMapUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文系メールテンプレート用トランスフォーマ。
 *
 * @author tm27400
 * @author natsume
 * @author Tomo (itec) ダミー値マップ動的差し替え対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component("orderTransformHelper")
public class OrderTransformHelper implements Transformer {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTransformHelper.class);

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 受注業務Utility
     */
    private final OrderUtility orderUtility;

    /**
     * 金額計算Utility
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * コンビニUtility
     */
    private final ConvenienceUtility convenienceUtility;

    /**
     * DecimalFormat
     */
    protected Format priceFormat = new DecimalFormat("#,##0");

    @Autowired
    public OrderTransformHelper(ConversionUtility conversionUtility,
                                DateUtility dateUtility,
                                OrderUtility orderUtility,
                                CalculatePriceUtility calculatePriceUtility,
                                ConvenienceUtility convenienceUtility) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.convenienceUtility = convenienceUtility;
    }

    /**
     * ダミープレースホルダを返す
     *
     * @return ダミープレースホルダ
     */
    @Override
    public Map<String, String> getDummyPlaceholderMap() {
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);
        return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
    }

    /**
     * 値マップを取得する
     *
     * @param arguments 変換元引数
     * @return 値マップ
     */
    @Override
    public Map<String, String> toValueMap(Object... arguments) {

        // 引数チェック
        this.checkArguments(arguments);

        if (arguments == null) {
            MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                            ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);
            return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
        } else if (arguments.length == 0) {
            return new HashMap<>();
        }

        // 受注Dto
        ReceiveOrderDto receiveOrderDto = (ReceiveOrderDto) arguments[0];

        Map<String, String> valueMap = new LinkedHashMap<>();

        // 受注サマリ
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        valueMap.put("O_MEMBER_INFO_SEQ", Integer.toString(orderSummaryEntity.getMemberInfoSeq()));
        valueMap.put("O_ORDER_CODE", orderSummaryEntity.getOrderCode());
        valueMap.put("O_ORDER_TIME", dateUtility.format(orderSummaryEntity.getOrderTime(), "yyyy年M月d日H時m分"));
        valueMap.put("O_SALES_TIME", dateUtility.format(orderSummaryEntity.getSalesTime(), "yyyy年M月d日H時m分"));
        valueMap.put("O_CANCEL_TIME", dateUtility.format(orderSummaryEntity.getCancelTime(), "yyyy年M月d日H時m分"));
        valueMap.put("O_SALES_FLAG", EnumTypeUtil.getValue(orderSummaryEntity.getSalesFlag()));
        valueMap.put("O_CANCEL_FLAG", EnumTypeUtil.getValue(orderSummaryEntity.getCancelFlag()));
        valueMap.put("O_WATING_FLAG", EnumTypeUtil.getValue(orderSummaryEntity.getWaitingFlag()));
        valueMap.put("O_ORDER_STATUS", EnumTypeUtil.getValue(orderSummaryEntity.getOrderStatus()));
        valueMap.put("O_ORDER_PRICE", priceFormat.format(orderSummaryEntity.getOrderPrice()));
        valueMap.put("O_RECEIPT_PRICE_TOTAL", priceFormat.format(orderSummaryEntity.getReceiptPriceTotal()));
        valueMap.put("O_ORDER_SITE_TYPE", EnumTypeUtil.getValue(orderSummaryEntity.getOrderSiteType()));
        valueMap.put("O_CARRIER_TYPE", EnumTypeUtil.getValue(orderSummaryEntity.getCarrierType()));
        valueMap.put("O_SETTLEMENTMETHOD_SEQ", orderSummaryEntity.getSettlementMethodSeq().toString());
        valueMap.put("O_ORDER_SEX", EnumTypeUtil.getValue(orderSummaryEntity.getOrderSex()));
        valueMap.put("O_ORDER_AGE_TYPE", EnumTypeUtil.getValue(orderSummaryEntity.getOrderAgeType()));
        valueMap.put("O_REGIST_TIME", dateUtility.format(orderSummaryEntity.getRegistTime(), "yyyy年M月d日H時m分"));
        valueMap.put("O_UPDATE_TIME", dateUtility.format(orderSummaryEntity.getUpdateTime(), "yyyy年M月d日H時m分"));
        valueMap.put("O_POINT_ACTIVATE_FLAG", EnumTypeUtil.getValue(orderSummaryEntity.getPointActivateFlag()));

        // ご注文主
        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();
        valueMap.put("O_ORDER_LAST_NAME", orderPersonEntity.getOrderLastName());
        valueMap.put("O_ORDER_FIRST_NAME", orderPersonEntity.getOrderFirstName());
        valueMap.put("O_ORDER_LAST_KANA", orderPersonEntity.getOrderLastKana());
        valueMap.put("O_ORDER_FIRST_KANA", orderPersonEntity.getOrderFirstKana());
        valueMap.put("O_ORDER_ZIP_CODE", orderPersonEntity.getOrderZipCode());

        String[] orderZipcode = conversionUtility.toZipCodeArray(orderPersonEntity.getOrderZipCode());
        valueMap.put("O_ORDER_ZIP_CODE1", orderZipcode[0]);
        valueMap.put("O_ORDER_ZIP_CODE2", orderZipcode[1]);
        valueMap.put("O_ORDER_PREFECTURE", orderPersonEntity.getOrderPrefecture());
        valueMap.put("O_ORDER_ADDRESS1", orderPersonEntity.getOrderAddress1());
        valueMap.put("O_ORDER_ADDRESS2", orderPersonEntity.getOrderAddress2());
        valueMap.put("O_ORDER_ADDRESS3", orderPersonEntity.getOrderAddress3());
        valueMap.put("O_ORDER_TEL", orderPersonEntity.getOrderTel());
        valueMap.put("O_ORDER_CONTACT_TEL", orderPersonEntity.getOrderContactTel());
        valueMap.put("O_ORDER_MAIL", orderPersonEntity.getOrderMail());
        valueMap.put("O_ORDER_BIRTHDAY", dateUtility.format(orderPersonEntity.getOrderBirthday(), "yyyy年M月d日"));

        // 受注決済
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        valueMap.put("O_ORDER_DATE", dateUtility.format(orderSettlementEntity.getOrderDate(), "yyyy年M月d日"));
        valueMap.put("O_SETTLEMENTMETHOD_TYPE", orderSettlementEntity.getSettlementMethodType().toString());
        valueMap.put("O_CREDIT_COMPANY", orderSettlementEntity.getCreditCompany());
        valueMap.put("O_GOODS_PRICE_TOTAL", priceFormat.format(orderSettlementEntity.getGoodsPriceTotal()));
        valueMap.put("O_TAX_PRICE", priceFormat.format(orderSettlementEntity.getTaxPrice()));
        valueMap.put("O_STANDARD_TAX_TARGET_PRICE",
                     priceFormat.format(orderSettlementEntity.getStandardTaxTargetPrice())
                    );
        valueMap.put("O_STANDARD_TAX_PRICE", priceFormat.format(orderSettlementEntity.getStandardTaxTargetPrice()));
        valueMap.put("O_REDUCED_TAX_TARGET_PRICE",
                     priceFormat.format(orderSettlementEntity.getReducedTaxTargetPrice())
                    );
        valueMap.put("O_REDUCED_TAX_PRICE", priceFormat.format(orderSettlementEntity.getReducedTaxPrice()));
        valueMap.put("O_SETTLEMENT_COMMISSION", priceFormat.format(orderSettlementEntity.getSettlementCommission()));
        valueMap.put("O_CARRIAGE", priceFormat.format(orderSettlementEntity.getCarriage()));
        valueMap.put("O_OTHERS_PRICE_TOTAL", priceFormat.format(orderSettlementEntity.getOthersPriceTotal()));
        valueMap.put("O_COUPON_DISCOUNT_PRICE", priceFormat.format(orderSettlementEntity.getCouponDiscountPrice()));
        valueMap.put("O_PRE_COUPON_DISCOUNT_PRICE",
                     priceFormat.format(orderSettlementEntity.getPreCouponDiscountPrice())
                    );
        BigDecimal usePoint = orderSettlementEntity.getUsePoint();
        BigDecimal preUsePoint = orderSettlementEntity.getPreUsePoint();
        valueMap.put("O_USE_POINT", priceFormat.format(usePoint));
        valueMap.put("O_PRE_USE_POINT", priceFormat.format(preUsePoint));

        // 決済
        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();
        valueMap.put("O_SETTLEMENTMETHOD_DISPLAY_NAME_PC", settlementMethodEntity.getSettlementMethodDisplayNamePC());
        valueMap.put("O_SETTLEMENTMETHOD_DISPLAY_NAME_MB", settlementMethodEntity.getSettlementMethodDisplayNameMB());

        // 受注配送
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 配送追跡 URLを表示すれば、「発送内容」 表示する
        valueMap.put("O_DELIVERY_ORDER_DISPLAY_FLAG", "0");

        String deliveryChaseURL = orderUtility.getDeliveryChaseURL(orderDeliveryDto.getDeliveryMethodEntity(),
                                                                   orderDeliveryDto.getOrderDeliveryEntity()
                                                                  );
        if (StringUtil.isNotEmpty(deliveryChaseURL)) {
            valueMap.put("O_DELIVERY_ORDER_DISPLAY_FLAG", "1");
        }

        // 納品書希望フラグは全配送同一の為、連番付与しない
        valueMap.put("O_INVOICE_ATTACHMENT_FLAG",
                     EnumTypeUtil.getValue(orderDeliveryDto.getOrderDeliveryEntity().getInvoiceAttachmentFlag())
                    );

        // TODO-QUAD-1222
        int receiverCounter = 1;
        receiverCounter = toValueMapDelively(valueMap, orderDeliveryDto, receiverCounter);

        // 受注追加料金
        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList =
                        receiveOrderDto.getOrderAdditionalChargeEntityList();
        if (orderAdditionalChargeEntityList != null) {
            int counter = 1;
            for (OrderAdditionalChargeEntity orderAdditionalChargeEntity : orderAdditionalChargeEntityList) {
                valueMap.put("O_ADDITIONAL_DETAILS_NAME" + LOOP_STR + counter,
                             orderAdditionalChargeEntity.getAdditionalDetailsName()
                            );
                valueMap.put("O_ADDITIONAL_DETAILS_PRICE" + LOOP_STR + counter,
                             priceFormat.format(orderAdditionalChargeEntity.getAdditionalDetailsPrice())
                            );
                counter++;
            }
            valueMap.put("O_ADDITIONAL_DETAILS_TOTAL", String.valueOf(counter - 1));
        }

        // 受注請求
        OrderBillEntity orderBillEntity = receiveOrderDto.getOrderBillEntity();
        valueMap.put("O_BILL_STATUS", EnumTypeUtil.getValue(orderBillEntity.getBillStatus()));
        valueMap.put("O_BILL_TIME", dateUtility.format(orderBillEntity.getBillTime(), "yyyy年M月d日H時m分"));
        valueMap.put("O_BILL_PRICE", priceFormat.format(orderBillEntity.getBillPrice()));
        valueMap.put("O_EMERGENCY_FLAG", EnumTypeUtil.getValue(orderBillEntity.getEmergencyFlag()));
        valueMap.put("O_PAYMENT_TIME_LIMIT_DATE",
                     dateUtility.format(orderBillEntity.getPaymentTimeLimitDate(), "yyyy年M月d日")
                    );
        valueMap.put("O_CARDBRAND_DISPLAY_PC", orderBillEntity.getCardBrandDisplayPC());
        valueMap.put("O_CARDBRAND_DISPLAY_MB", orderBillEntity.getCardBrandDisplayMB());

        // 受注入金
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyList = receiveOrderDto.getOrderReceiptOfMoneyEntityList();
        if (orderReceiptOfMoneyList != null) {
            int counter = 1;
            for (OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity : orderReceiptOfMoneyList) {
                valueMap.put("O_RECEIPT_TIME" + LOOP_STR + counter,
                             dateUtility.format(orderReceiptOfMoneyEntity.getReceiptTime(), "yyyy年M月d日H時m分")
                            );
                valueMap.put("O_RECEIPT_YMD" + LOOP_STR + counter,
                             dateUtility.getYmdFormatValue(orderReceiptOfMoneyEntity.getReceiptYmd(), "yyyy年M月d日")
                            );
                valueMap.put("O_RECEIPT_PRICE" + LOOP_STR + counter,
                             priceFormat.format(orderReceiptOfMoneyEntity.getReceiptPrice())
                            );
                valueMap.put("O_RECEIPT_PRICE_TOTAL" + LOOP_STR + counter,
                             priceFormat.format(orderReceiptOfMoneyEntity.getReceiptPriceTotal())
                            );
                counter++;
            }
            valueMap.put("O_RECEIPT_TOTAL", String.valueOf(counter - 1));
        }

        // マルチペイメント決済請求
        MulPayBillEntity mulPayBillEntity = receiveOrderDto.getMulPayBillEntity();
        if (mulPayBillEntity != null && mulPayBillEntity.getPayType() != null) {
            if (mulPayBillEntity.getPayType().equals("0")) {
                // クレジット
                valueMap.put("O_METHOD", mulPayBillEntity.getMethod());
                if (mulPayBillEntity.getPayTimes() != null) {
                    valueMap.put("O_PAYTIMES", mulPayBillEntity.getPayTimes().toString());
                }
                valueMap.put("O_APPROVE", mulPayBillEntity.getApprove());
            } else if (mulPayBillEntity.getPayType().equals("1")) {
                // Suica
                valueMap.put("O_MAILADDRESS", mulPayBillEntity.getMailAddress());
                valueMap.put("O_SUICA_ORDER_NO", mulPayBillEntity.getSuicaOrderNo());
            } else if (mulPayBillEntity.getPayType().equals("2")) {
                // Edy
                valueMap.put("O_MAILADDRESS", mulPayBillEntity.getMailAddress());
                valueMap.put("O_EDY_ORDER_NO", mulPayBillEntity.getEdyOrderNo());
            } else if (mulPayBillEntity.getPayType().equals("3")) {
                // コンビニ
                valueMap.put("O_CONVENIENCE", mulPayBillEntity.getConvenience());
                valueMap.put("O_CONF_NO", mulPayBillEntity.getConfNo());

                // 該当のコンビニの場合、オンライン決済番号を編集する。
                String receiptNo = CopyUtil.deepCopy(mulPayBillEntity.getReceiptNo());
                if (convenienceUtility.isConveni2(mulPayBillEntity.getConvenience())) {
                    // オンライン決済番号は4桁-7桁に区切る
                    // (WNT25126649 → WNT2-5126649 のように編集する)
                    StringBuilder builder = new StringBuilder();
                    builder.append(receiptNo.substring(0, 4)).append("-").append(receiptNo.substring(4));
                    receiptNo = builder.toString();
                }
                valueMap.put("O_RECEIPT_NO", receiptNo);

            } else if (mulPayBillEntity.getPayType().equals("4")) {
                // Pay-easy
                valueMap.put("O_CONF_NO", mulPayBillEntity.getConfNo());
                valueMap.put("O_BKCODE", mulPayBillEntity.getBkCode());
                valueMap.put("O_CUST_ID", mulPayBillEntity.getCustId());
            }
        }

        // クーポン
        CouponEntity coupon = receiveOrderDto.getCoupon();
        String couponDisplayNamePC = "";
        String couponDisplayNameMB = "";
        if (coupon != null) {
            couponDisplayNamePC = coupon.getCouponDisplayNamePC();
            couponDisplayNameMB = coupon.getCouponDisplayNameMB();
        }
        valueMap.put("O_COUPON_DISPLAY_NAME_PC", couponDisplayNamePC);
        valueMap.put("O_COUPON_DISPLAY_NAME_MB", couponDisplayNameMB);

        return valueMap;
    }

    /**
     * 値マップに配送情報を設定
     *
     * @param valueMap         値マップ
     * @param orderDeliveryDto 配送情報
     * @param receiverCounter  receiverCounter
     * @return receiverCounter
     */
    protected int toValueMapDelively(Map<String, String> valueMap,
                                     OrderDeliveryDto orderDeliveryDto,
                                     int receiverCounter) {
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();
        valueMap.put("O_CONSECUTIVE_NO" + LOOP_STR + receiverCounter,
                     orderDeliveryEntity.getOrderConsecutiveNo().toString()
                    );
        valueMap.put("O_DELIVERYMETHOD_SEQ" + LOOP_STR + receiverCounter,
                     orderDeliveryEntity.getDeliveryMethodSeq().toString()
                    );
        valueMap.put("O_SHIPMENT_STATUS" + LOOP_STR + receiverCounter,
                     EnumTypeUtil.getValue(orderDeliveryEntity.getShipmentStatus())
                    );
        valueMap.put("O_PLAN_DATE" + LOOP_STR + receiverCounter,
                     dateUtility.format(orderDeliveryEntity.getPlanDate(), "yyyy年M月d日")
                    );
        valueMap.put("O_SHIPMENT_DATE" + LOOP_STR + receiverCounter,
                     dateUtility.format(orderDeliveryEntity.getShipmentDate(), "yyyy年M月d日")
                    );
        valueMap.put("O_DELIVERY_CODE" + LOOP_STR + receiverCounter, orderDeliveryEntity.getDeliveryCode());
        valueMap.put("O_RECEIVER_LAST_NAME" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverLastName());
        valueMap.put("O_RECEIVER_FIRST_NAME" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverFirstName());
        valueMap.put("O_RECEIVER_LAST_KANA" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverLastKana());
        valueMap.put("O_RECEIVER_FIRST_KANA" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverFirstKana());
        valueMap.put("O_RECEIVER_TEL" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverTel());
        valueMap.put("O_RECEIVER_ZIP_CODE" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverZipCode());
        String[] receiverZipcode = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
        valueMap.put("O_RECEIVER_ZIP_CODE1" + LOOP_STR + receiverCounter, receiverZipcode[0]);
        valueMap.put("O_RECEIVER_ZIP_CODE2" + LOOP_STR + receiverCounter, receiverZipcode[1]);
        valueMap.put("O_RECEIVER_PREFECTURE" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverPrefecture());
        valueMap.put("O_RECEIVER_ADDRESS1" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverAddress1());
        valueMap.put("O_RECEIVER_ADDRESS2" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverAddress2());
        valueMap.put("O_RECEIVER_ADDRESS3" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverAddress3());
        String receiverDate = null;
        if (HTypeReceiverDateDesignationFlag.ON.equals(orderDeliveryEntity.getReceiverDateDesignationFlag())) {
            if (orderDeliveryEntity.getReceiverDate() != null) {
                receiverDate = ReceiverDateDto.getFormatMdWithWeek(orderDeliveryEntity.getReceiverDate());
            } else {
                receiverDate = ReceiverDateDto.NON_SELECT_VALUE;
            }
        }
        valueMap.put("O_RECEIVER_DATE" + LOOP_STR + receiverCounter, receiverDate);
        valueMap.put("O_RECEIVER_TIME_ZONE" + LOOP_STR + receiverCounter, orderDeliveryEntity.getReceiverTimeZone());
        valueMap.put("O_DELIVERY_NOTE" + LOOP_STR + receiverCounter, orderDeliveryEntity.getDeliveryNote());
        valueMap.put("O_OTHERS_NOTE" + LOOP_STR + receiverCounter, orderDeliveryEntity.getOthersNote());

        // 配送
        DeliveryMethodEntity deliveryMethodEntity = orderDeliveryDto.getDeliveryMethodEntity();
        valueMap.put("O_DELIVERYMETHOD_DISPLAY_NAME_PC" + LOOP_STR + receiverCounter,
                     deliveryMethodEntity.getDeliveryMethodDisplayNamePC()
                    );
        valueMap.put("O_DELIVERYMETHOD_DISPLAY_NAME_MB" + LOOP_STR + receiverCounter,
                     deliveryMethodEntity.getDeliveryMethodDisplayNameMB()
                    );

        // 配送追跡URLを表示
        valueMap.put("O_DELIVERY_CHASEURL_DISPLAY_FLAG" + LOOP_STR + receiverCounter, "0");
        String deliveryChaseURL = orderUtility.getDeliveryChaseURL(deliveryMethodEntity, orderDeliveryEntity);
        if (StringUtil.isNotEmpty(deliveryChaseURL)) {
            valueMap.put("O_DELIVERY_CHASEURL_DISPLAY_FLAG" + LOOP_STR + receiverCounter, "1");
            valueMap.put("O_DELIVERY_CHASEURL" + LOOP_STR + receiverCounter, deliveryChaseURL);
        }
        valueMap.put("O_DELIVERY_TOTAL", String.valueOf(receiverCounter));

        // 受注商品
        List<OrderGoodsEntity> orderGoodsEntityList = orderDeliveryDto.getOrderGoodsEntityList();
        if (orderGoodsEntityList != null) {
            int goodsCounter = 1;
            for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
                toValueMapGoods(valueMap, orderGoodsEntity, receiverCounter);
                valueMap.put("O_GOODS_COUNTER" + LOOP_STR + receiverCounter, priceFormat.format(goodsCounter));
                receiverCounter++;
                goodsCounter++;
            }
            valueMap.put("O_GOODS_COUNTER_TOTAL", String.valueOf(goodsCounter - 1));
        }
        return receiverCounter;
    }

    /**
     * 値マップに商品情報を設定
     *
     * @param valueMap         値マップ
     * @param orderGoodsEntity 商品情報
     * @param receiverCounter  receiverCounter
     */
    protected void toValueMapGoods(Map<String, String> valueMap,
                                   OrderGoodsEntity orderGoodsEntity,
                                   int receiverCounter) {
        valueMap.put("O_GOODS_COUNTER" + LOOP_STR + receiverCounter, StringConversionUtil.toString(receiverCounter));
        valueMap.put("O_GOODS_CODE" + LOOP_STR + receiverCounter, orderGoodsEntity.getGoodsCode());
        valueMap.put("O_JAN_CODE" + LOOP_STR + receiverCounter, orderGoodsEntity.getJanCode());
        valueMap.put("O_CATALOG_CODE" + LOOP_STR + receiverCounter, orderGoodsEntity.getCatalogCode());
        BigDecimal goodsPrice = orderGoodsEntity.getGoodsPrice();
        valueMap.put("O_GOODS_PRICE" + LOOP_STR + receiverCounter, priceFormat.format(goodsPrice));
        valueMap.put(
                        "O_GOODS_PRICE_INTAX" + LOOP_STR + receiverCounter, priceFormat.format(
                                        calculatePriceUtility.getTaxIncludedPrice(goodsPrice,
                                                                                  orderGoodsEntity.getTaxRate()
                                                                                 )));
        valueMap.put("O_GOODS_COUNT" + LOOP_STR + receiverCounter,
                     priceFormat.format(orderGoodsEntity.getGoodsCount())
                    );
        valueMap.put("O_UNIT_VALUE1" + LOOP_STR + receiverCounter, orderGoodsEntity.getUnitValue1());
        valueMap.put("O_UNIT_VALUE2" + LOOP_STR + receiverCounter, orderGoodsEntity.getUnitValue2());
        valueMap.put("O_GOODS_GROUP_CODE" + LOOP_STR + receiverCounter, orderGoodsEntity.getGoodsGroupCode());
        valueMap.put("O_GOODS_GROUP_NAME" + LOOP_STR + receiverCounter, orderGoodsEntity.getGoodsGroupName());

        // 商品金額小計
        BigDecimal subTotal = orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount());
        valueMap.put("O_GOODS_PRICE_SUB_TOTAL" + LOOP_STR + receiverCounter, priceFormat.format(subTotal));
        valueMap.put("O_GOODS_DELIVERY" + LOOP_STR + receiverCounter, orderGoodsEntity.getDeliveryType());
    }

    /**
     * 引数の有効性を確認する
     *
     * @param arguments 引数
     */
    protected void checkArguments(Object[] arguments) {

        // オブジェクトがない場合はテスト送信用とみなす
        if (arguments == null) {
            return;
        }

        if (arguments.length != 1) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の数が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }

        if (!(arguments[0] instanceof ReceiveOrderDto)) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の型が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }
    }

    /**
     * リソースファイル名<br/>
     *
     * @return リソースファイル名
     */
    @Override
    public String getResourceName() {
        return "OrderTransformHelper";
    }
}
