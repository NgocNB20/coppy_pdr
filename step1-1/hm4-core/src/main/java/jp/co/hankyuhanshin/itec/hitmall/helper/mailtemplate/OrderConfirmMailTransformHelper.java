/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliverySlipFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverTimeZoneJapanPost;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverTimeZoneYamato;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.ReceiverDateDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailTemplateDummyMapUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 注文確認メールデータ変換ヘルパークラス
 * </pre>
 *
 * @author satoh
 * @author takahiro-soeta 【1.3次】注文完了メールへの金額記載 対応
 */

@Component
public class OrderConfirmMailTransformHelper implements Transformer {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConfirmMailTransformHelper.class);

    /**
     * 日付関連Utilityクラス
     */
    public DateUtility dateUtility;

    /**
     * 受注業務ユーティリティクラス
     */
    public OrderUtility orderUtility;

    /**
     * GoodsUtility
     */
    public GoodsUtility goodsUtility;

    /**
     * 日付フォーマット yyyy/M/d
     */
    public static final String DATE_FORMAT = "yyyy/M/d";

    /**
     * DecimalFormat
     */
    protected Format priceFormat = new DecimalFormat("#,##0");

    /**
     * 変換Helper取得
     */
    public ConversionUtility conversionUtility;

    /**
     * 改行
     */
    public static final String NEW_LINE = "<br />";

    /**
     * 区切り
     */
    public static final String SECTION = "------------------------------------------------------------";

    /**
     * スペース
     */
    public static final String SPACE = " ";

    /**
     * 掛ける
     */
    public static final String MULTIPLY = "×";

    /**
     * 等号
     */
    public static final String EQUAL = "＝";

    /**
     * 円
     */
    public static final String YEN = "円";

    /**
     * (
     */
    public static final String OPEN = "(";

    /**
     * )
     */
    public static final String CLOSE = ")";

    /**
     * 受注番号
     */
    public static final String ORDER_CODE = "受注番号：";

    /**
     * 入荷次第お届け注意事項
     */
    public static final String PRECAUTIONS = "※お預り金のある方は充当の上、発送させていただきます。";

    /**
     * リソースファイル名<br/>
     *
     * @return リソースファイル名
     */
    @Override
    public String getResourceName() {
        return "OrderConfirmMailTransformHelper";
    }

    @Autowired
    public OrderConfirmMailTransformHelper(DateUtility dateUtility,
                                           OrderUtility orderUtility,
                                           GoodsUtility goodsUtility,
                                           ConversionUtility conversionUtility) {
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 値マップを取得する
     *
     * @param arguments 引数
     * @return 値マップ
     */
    @Override
    public Map<String, String> toValueMap(Object... arguments) {
        // 引数チェック
        this.checkArguments(arguments);
        // メールテンプレート用ダミー値マップ取得用Helper取得
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);

        if (arguments == null) {
            return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
        } else if (arguments.length == 0) {
            return new HashMap<>();
        }

        @SuppressWarnings("unchecked")
        List<ReceiveOrderDto> receiveOrderDtoList = (List<ReceiveOrderDto>) arguments[0];

        Map<String, String> valueMap = new LinkedHashMap<>();

        // 入荷次第お届け情報をサマリする。
        List<ReceiveOrderDto> dispReceiveOrderDtoList = new ArrayList<>();
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            dispReceiveOrderDtoList.add(CopyUtil.deepCopy(receiveOrderDto));
        }
        // 初期化
        receiveOrderDtoList = new ArrayList<>();
        // 再設定
        // 入荷次第お届け以外はそのまま格納
        for (ReceiveOrderDto tmpReceiveOrderDto : dispReceiveOrderDtoList) {
            if (!HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            tmpReceiveOrderDto.getAllocationDeliveryType())) {
                receiveOrderDtoList.add(tmpReceiveOrderDto);
            }
        }
        BigDecimal goodsPriceTotalOutTax = null;
        BigDecimal carriage = null;
        BigDecimal discountPrice = null;
        BigDecimal totalTax = null;
        BigDecimal orderPrice = null;
        // 入荷次第お届けDto
        ReceiveOrderDto dipendingOnReceiptDto = null;
        // 入荷次第お届け商品リスト
        List<OrderGoodsEntity> dipendingOnReceiptOrderGoodsEntityList = new ArrayList<>();
        // 入荷次第お届けをサマリ
        for (ReceiveOrderDto tmpReceiveOrderDto : dispReceiveOrderDtoList) {
            OrderDeliveryDto orderDeliveryDto = tmpReceiveOrderDto.getOrderDeliveryDto();
            if (HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            tmpReceiveOrderDto.getAllocationDeliveryType())) {
                // 小計（商品税抜金額合計）
                if (goodsPriceTotalOutTax != null) {
                    goodsPriceTotalOutTax = goodsPriceTotalOutTax.add(
                                    tmpReceiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal());
                } else {
                    goodsPriceTotalOutTax = tmpReceiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal();
                }

                // 送料
                if (carriage != null) {
                    carriage = carriage.add(orderDeliveryDto.getOrderDeliveryEntity().getCarriage());
                } else {
                    carriage = orderDeliveryDto.getOrderDeliveryEntity().getCarriage();
                }

                // 値引
                if (discountPrice != null) {
                    discountPrice = discountPrice.add(orderDeliveryDto.getDiscountPrice());
                } else {
                    discountPrice = orderDeliveryDto.getDiscountPrice();
                }

                // 消費税
                if (totalTax != null) {
                    totalTax = totalTax.add(orderDeliveryDto.getTotalTax());
                } else {
                    totalTax = orderDeliveryDto.getTotalTax();
                }

                // 合計金額（受注金額）
                if (orderPrice != null) {
                    orderPrice = orderPrice.add(tmpReceiveOrderDto.getOrderSummaryEntity().getOrderPrice());
                } else {
                    orderPrice = tmpReceiveOrderDto.getOrderSummaryEntity().getOrderPrice();
                }

                tmpReceiveOrderDto.getOrderSettlementEntity().setGoodsPriceTotal(goodsPriceTotalOutTax);
                orderDeliveryDto.getOrderDeliveryEntity().setCarriage(carriage);
                orderDeliveryDto.setDiscountPrice(discountPrice);
                orderDeliveryDto.setTotalTax(totalTax);
                tmpReceiveOrderDto.getOrderSummaryEntity().setOrderPrice(orderPrice);

                // 商品情報設定
                List<OrderGoodsEntity> orderGoodsEntityList = orderDeliveryDto.getOrderGoodsEntityList();
                if (orderGoodsEntityList != null) {
                    for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
                        orderGoodsEntity.setDipendingOnReceiptOrderCode(
                                        tmpReceiveOrderDto.getOrderSummaryEntity().getOrderCode());
                        dipendingOnReceiptOrderGoodsEntityList.add(orderGoodsEntity);
                    }
                }
                tmpReceiveOrderDto.getOrderDeliveryDto()
                                  .setOrderGoodsEntityList(dipendingOnReceiptOrderGoodsEntityList);
                // 入荷次第お届けは複数件メール表示しないので、上書き
                dipendingOnReceiptDto = tmpReceiveOrderDto;

            }
        }
        // 入荷次第お届けDtoを追加
        if (dipendingOnReceiptDto != null) {
            receiveOrderDtoList.add(dipendingOnReceiptDto);
        }

        // 配送情報（受注番号単位の情報）
        int receiverCounter = 1;
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            toValueMapDelivery(valueMap, receiveOrderDto, receiverCounter);
            receiverCounter++;
        }
        valueMap.put("RECEIVER_COUNTER", String.valueOf(receiverCounter));
        ReceiveOrderDto receiveOrderDto = receiveOrderDtoList.get(0);
        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();

        // 受注日
        valueMap.put("ORDER_DATE",
                     dateUtility.format(receiveOrderDto.getOrderSummaryEntity().getOrderTime(), DATE_FORMAT)
                    );
        // ご注文事業所名
        valueMap.put("ORDER_NAME", receiveOrderDto.getOrderPersonEntity().getOrderLastName());

        // お届け先郵便番号
        String[] zipCodeArray = conversionUtility.toZipCodeArray(orderDeliveryEntity.getReceiverZipCode());
        String receiverZipCode = "〒" + zipCodeArray[0] + "-" + zipCodeArray[1];
        valueMap.put("ORDER_ZIP_CODE", receiverZipCode);
        // お届け先住所
        String address = StringUtils.defaultString(orderDeliveryEntity.getReceiverAddress1())
                         + StringUtils.defaultString(orderDeliveryEntity.getReceiverAddress2())
                         + StringUtils.defaultString(orderDeliveryEntity.getReceiverAddress3())
                         + StringUtils.defaultString(orderDeliveryEntity.getReceiverAddress4());
        valueMap.put("ORDER_ADDRESS", address);

        // お支払方法
        String settlementMethod = receiveOrderDto.getSettlementMethodEntity().getSettlementMethodDisplayNamePC();
        valueMap.put("ORDER_SETTLEMENT_METHOD", settlementMethod);

        return valueMap;
    }

    /**
     * 値マップに配送情報を設定
     *
     * @param valueMap        値マップ
     * @param receiveOrderDto 受注情報
     * @param receiverCounter 配送リスト連番
     */
    public void toValueMapDelivery(Map<String, String> valueMap, ReceiveOrderDto receiveOrderDto, int receiverCounter) {

        // 配送方法振分区分
        HTypeAllocationDeliveryType allocationDeliveryType = receiveOrderDto.getAllocationDeliveryType();
        valueMap.put("ALLOCATION_DELIVERY_TYPE" + LOOP_STR + receiverCounter, allocationDeliveryType.getLabel());

        // 受注番号
        if (!allocationDeliveryType.equals(HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT)) {
            valueMap.put("ORDER_CODE" + LOOP_STR + receiverCounter,
                         receiveOrderDto.getOrderSummaryEntity().getOrderCode()
                        );
        }

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();

        String deliveryDate = null;
        String receiverDate = null;
        String receiverTimeZone = null;
        // 配送方法振分区分が「今すぐお届け分」の場合
        if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(allocationDeliveryType)) {
            // お届け希望日取得
            if (orderDeliveryEntity.getReceiverDate() != null) {
                StringBuilder sb = new StringBuilder();
                // 日付を取得してyyyy/M/d形式に変換
                String date = dateUtility.format(orderDeliveryEntity.getReceiverDate(), DATE_FORMAT);
                // 曜日取得
                String week = ReceiverDateDto.getDayOfTheWeek(orderDeliveryEntity.getReceiverDate());
                // 日付と曜日を結合
                sb.append(date).append(week);
                receiverDate = sb.toString();
            } else {
                receiverDate = ReceiverDateDto.NON_SELECT_VALUE;
            }

            // お届け時間帯取得
            if (HTypeDeliveryType.YAMATO.getValue()
                                        .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                .getDeliveryCompanyCode())
                // 2023-renew No14 from here
                || HTypeDeliveryType.AUTOMATIC.getValue()
                                              .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                      .getDeliveryCompanyCode())) {
                // ヤマトまたは自動設定の場合
                // ※自動設定の注文時にヤマトの時間帯から選択するため、佐川急便の時間帯もヤマトの区分値から設定
                // 2023-renew No14 to here
                receiverTimeZone = EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneYamato.class,
                                                                 orderDeliveryDto.getOrderDeliveryEntity()
                                                                                 .getReceiverTimeZone()
                                                                ).getLabel();
            }
            if (HTypeDeliveryType.JAPANPOST.getValue()
                                           .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                   .getDeliveryCompanyCode())) {
                // 日本郵便
                receiverTimeZone = EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneJapanPost.class,
                                                                 orderDeliveryDto.getOrderDeliveryEntity()
                                                                                 .getReceiverTimeZone()
                                                                ).getLabel();
            }
        } else if (HTypeAllocationDeliveryType.RESERVABLE.equals(allocationDeliveryType)) {
            // 配送方法振分区分が「予約可能分(取りおき)」の場合
            // お届け時期取得
            deliveryDate = orderDeliveryDto.getDeliveryDate();
        }
        // お届け希望日
        valueMap.put("RECEIVER_DATE" + LOOP_STR + receiverCounter, receiverDate);
        // お届け時間帯
        valueMap.put("RECEIVER_TIME_ZONE" + LOOP_STR + receiverCounter, receiverTimeZone);
        // お届け時期
        valueMap.put("DELIVERY_DATE" + LOOP_STR + receiverCounter, deliveryDate);

        // 2023-renew No24 from here
        // 適用クーポン名（1件目の受注にのみ設定）
        valueMap.put("COUPON_NAME" + LOOP_STR + receiverCounter,
                     receiverCounter == 1 ? receiveOrderDto.getCouponName() : null
                    );
        // 適用クーポンコード（1件目の受注にのみ設定）
        valueMap.put("COUPON_CODE" + LOOP_STR + receiverCounter,
                     receiverCounter == 1 ? receiveOrderDto.getCouponCode() : null
                    );
        // 2023-renew No24 to here

        // 商品情報取得
        List<OrderGoodsEntity> orderGoodsEntityList = orderDeliveryDto.getOrderGoodsEntityList();
        if (orderGoodsEntityList != null) {
            StringBuilder orderGoodsInfo = new StringBuilder();
            orderGoodsInfo.append(SECTION).append(NEW_LINE);
            for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
                if (HTypeDeliverySlipFlag.OFF == orderGoodsEntity.getDeliverySlipFlag()) {
                    continue;
                }
                String goodsInfo =
                                createOrderGoodsInfo(orderGoodsEntity, receiveOrderDto.getMasterDto().getGoodsMaster(),
                                                     allocationDeliveryType
                                                    );
                orderGoodsInfo.append(goodsInfo);
                orderGoodsInfo.append(NEW_LINE).append(SECTION).append(NEW_LINE);
            }
            // 末尾の改行（"\n"）を除去
            orderGoodsInfo.setLength(orderGoodsInfo.length() - 1);

            // 商品情報
            valueMap.put("ORDER_GOODS_INFO" + LOOP_STR + receiverCounter, orderGoodsInfo.toString());
        }

        // 小計（商品税抜金額合計）
        valueMap.put("SUB_TOTAL_PRICE" + LOOP_STR + receiverCounter,
                     priceFormat.format(receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal())
                    );

        // 送料
        valueMap.put("CARRIAGE" + LOOP_STR + receiverCounter, priceFormat.format(orderDeliveryEntity.getCarriage()));

        // 値引
        String discountPrice = null;
        if (BigDecimal.ZERO.compareTo(orderDeliveryDto.getDiscountPrice()) != 0) {
            discountPrice = priceFormat.format(orderDeliveryDto.getDiscountPrice());
        }
        valueMap.put("DISCOUNT" + LOOP_STR + receiverCounter, discountPrice);

        // 消費税
        valueMap.put("TAX" + LOOP_STR + receiverCounter, priceFormat.format(orderDeliveryDto.getTotalTax()));

        // 合計金額（受注金額）
        valueMap.put("TOTAL_PRICE" + LOOP_STR + receiverCounter,
                     priceFormat.format(receiveOrderDto.getOrderSummaryEntity().getOrderPrice())
                    );

        // 注意事項（入荷次第お届けが存在する場合のみ）
        if (allocationDeliveryType.equals(HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT)) {
            valueMap.put("PRECAUTIONS" + LOOP_STR + receiverCounter, PRECAUTIONS);
        }
    }

    /**
     * 商品情報を作成
     *
     * @param orderGoodsEntity 商品情報
     * @param goodsMaster      商品マスタマップ
     * @return goodsInfo 商品情報
     */
    public String createOrderGoodsInfo(OrderGoodsEntity orderGoodsEntity,
                                       Map<Integer, GoodsDetailsDto> goodsMaster,
                                       HTypeAllocationDeliveryType allocationDeliveryType) {
        StringBuilder goodsInfo = new StringBuilder();

        // 商品コード
        goodsInfo.append(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode()))
                 .append(SPACE);
        // 商品名 + 規格があれば「（規格値1/規格値2）」
        goodsInfo.append(orderUtility.createErrDispGoodsName(orderGoodsEntity)).append(SPACE);
        // 数量
        goodsInfo.append(orderGoodsEntity.getGoodsCount());
        // 単位
        goodsInfo.append(goodsMaster.get(orderGoodsEntity.getGoodsSeq()).getUnit()).append(SPACE).append(MULTIPLY);
        // 単価（税抜き）
        goodsInfo.append(priceFormat.format(orderGoodsEntity.getGoodsPrice()))
                 .append(YEN)
                 .append(SPACE)
                 .append(EQUAL)
                 .append(SPACE);
        // 金額
        goodsInfo.append(
                                 priceFormat.format(orderGoodsEntity.getGoodsCount().multiply(orderGoodsEntity.getGoodsPrice())))
                 .append(YEN);

        // 入荷次第お届けの場合商品情報に受注番号を設定
        if (allocationDeliveryType.equals(HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT)) {
            goodsInfo.append(NEW_LINE)
                     .append(OPEN)
                     .append(ORDER_CODE)
                     .append(orderGoodsEntity.getDipendingOnReceiptOrderCode())
                     .append(CLOSE);
        }
        return goodsInfo.toString();
    }

    /**
     * ダミープレースホルダを返す
     *
     * @return ダミープレースホルダ
     */
    @Override
    public Map<String, String> getDummyPlaceholderMap() {
        // メールテンプレート用ダミー値マップ取得用Helper取得
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);

        return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
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
            LOGGER.warn("引数の数が合いません。", e);
            throw e;
        }

        if (!(arguments[0] instanceof List<?>)) {
            String v = "null";
            if (arguments[0] != null) {
                v = v.getClass().getSimpleName();
            }
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の型が合いません。" + v);
            LOGGER.warn("引数の型が合いません。", e);
            throw e;
        }
    }

}
