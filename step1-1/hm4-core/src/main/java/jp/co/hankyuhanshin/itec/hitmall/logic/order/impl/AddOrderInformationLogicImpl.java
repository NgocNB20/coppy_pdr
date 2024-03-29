// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePaymentMethod;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverTimeZoneJapanPost;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverTimeZoneYamato;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddOrderInformationRequestDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddOrderInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddOrderInformationRequestGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.AddOrderInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiAddOrderInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 受注連携ロジック
 * </pre>
 *
 * @author satoh
 */
@Component
public class AddOrderInformationLogicImpl extends AbstractShopLogic implements AddOrderInformationLogic {

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 受注業務ユーティリティクラス
     */
    private final OrderUtility orderUtility;

    /**
     * WEB-API連携 受注連携
     */
    private final WebApiAddOrderInformationLogic webApiAddOrderInformationLogic;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public AddOrderInformationLogicImpl(DateUtility dateUtility,
                                        ConversionUtility conversionUtility,
                                        OrderUtility orderUtility,
                                        WebApiAddOrderInformationLogic webApiAddOrderInformationLogic,
                                        GoodsUtility goodsUtility) {
        this.dateUtility = dateUtility;
        this.conversionUtility = conversionUtility;
        this.orderUtility = orderUtility;
        this.webApiAddOrderInformationLogic = webApiAddOrderInformationLogic;
        this.goodsUtility = goodsUtility;
    }

    /**
     * WEB-API連携 受注連携を行います。
     *
     * @param receiveOrderDtoList 受注DTOリスト
     */
    public void execute(List<ReceiveOrderDto> receiveOrderDtoList) {
        WebApiAddOrderInformationRequestDto reqDto = new WebApiAddOrderInformationRequestDto();

        List<WebApiAddOrderInformationRequestDetailDto> orderInfo = new ArrayList<>();
        // 商品リストの受注連番
        int orderGoodsIndex = 0;

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {

            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

            WebApiAddOrderInformationRequestDetailDto detailDto = new WebApiAddOrderInformationRequestDetailDto();

            // 受注番号
            detailDto.setOrderNo(receiveOrderDto.getOrderSummaryEntity().getOrderCode());
            // 関連受注番号
            detailDto.setRelOrderNo(receiveOrderDto.getRelationOrderCode());
            // 入力担当者(990)
            detailDto.setInputUserID(INPUT_USER_ID_FIXED_VALUE);
            // 確定担当者(990)
            detailDto.setComfirmUserID(COMFIRM_USER_ID_FIXED_VALUE);
            // 受付方法(5)
            detailDto.setOrderType(ORDER_TYPE_FIXED_VALUE);
            // 受注日
            detailDto.setOrderDate(receiveOrderDto.getOrderSummaryEntity().getOrderTime());

            String deliveryYesNo = orderDeliveryDto.getOrderGoodsEntityList().get(0).getDeliveryYesNo();
            HTypeStockManagementFlag stockManagementFlag =
                            orderDeliveryDto.getOrderGoodsEntityList().get(0).getStockManagementFlag();
            Boolean setStockDateFlg = true;
            // 出荷予定日
            if (!HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(receiveOrderDto.getAllocationDeliveryType())) {
                detailDto.setShipmentDate(orderDeliveryDto.getOrderDeliveryEntity().getShipmentDate());
                setStockDateFlg = false;
            } else if (HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            receiveOrderDto.getAllocationDeliveryType()) && HTypeStockManagementFlag.ON.equals(
                            stockManagementFlag) && "1".equals(deliveryYesNo)) {
                // 売切り商品かつ、入荷次第お届け可の場合は出荷予定日をセットする。
                detailDto.setShipmentDate(orderDeliveryDto.getOrderDeliveryEntity().getShipmentDate());
                setStockDateFlg = false;
            }
            // 入荷予定日（入荷次第お届けの場合）
            if (setStockDateFlg) {
                // １受注に対し、全ての商品は入荷予定日が一致しているので、１件目指定
                detailDto.setStockDate(orderDeliveryDto.getOrderGoodsEntityList().get(0).getStockDate());
            }
            // 顧客番号(注文者)
            detailDto.setCustomerNo(conversionUtility.toString(receiveOrderDto.getOrderPersonEntity().getCustomerNo()));
            // 電話番号(注文者)
            detailDto.setTelNo(receiveOrderDto.getOrderPersonEntity().getOrderTel());
            // 広告媒体(00000001)
            detailDto.setMediaCode(MEDIA_CODE_FIXED_VALUE);
            // 倉庫(1)
            detailDto.setStockroomCode(STOCKROOM_CODE_FIXED_VALUE);
            // お届け先顧客番号
            detailDto.setDeliveryCustomerNo(conversionUtility.toString(orderDeliveryDto.getCustomerNo()));
            // お届け先連絡番号
            detailDto.setDeliveryTelNo(orderDeliveryDto.getOrderDeliveryEntity().getReceiverTel());
            // 使用ポイント(0)
            detailDto.setUsePoint(USE_POINT_FIXED_VALUE);
            // 支払方法
            detailDto.setPaymentType(orderUtility.conversionPaymentMethod(
                                                                 receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType().getValue())
                                                 .getValue());

            // 支払方法がクレジットカードの場合
            if (HTypePaymentMethod.CREDIT_CARD.getValue().equals(detailDto.getPaymentType())) {
                // クレジット会社
                detailDto.setCreditCompanyCode(CREDIT_COMPANY_CODE_FIXED_VALUE);
                // クレジット番号
                detailDto.setCreditCardNo(CREDIT_CARD_NO_FIXED_VALUE);
                // クレジット有効期限
                detailDto.setCreditExpirationDate(CREDIT_EXPIRATION_DATE_FIXED_VALUE);
                // クレジット支払回数
                detailDto.setCreditSplitNumber(CREDIT_SPLIT_NUMBER_FIXED_VALUE);
                // クレジット決済ID
                detailDto.setCreditPaymentID(receiveOrderDto.getOrderTempDto().getPaymentId());
            }

            // 配送情報取得で詳細情報が取得できている場合
            if (orderDeliveryDto.getDeliveryInformationDetailDto() != null) {
                // 2023-renew No14 from here
                // 配送方法
                detailDto.setDeliveryType(orderDeliveryDto.getDeliveryInformationDetailDto().getDeliveryCompanyCode());
                // 2023-renew No14 to here
            }

            // 請求書　1：同梱　2：別送
            detailDto.setRequisitionType(orderDeliveryDto.getRequisitionType().getValue());

            // 保留区分
            if (receiveOrderDto.getPendingType() != null) {
                detailDto.setHoldType(receiveOrderDto.getPendingType().getValue());
            }

            // 入荷次第お届け以外
            if (!HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(receiveOrderDto.getAllocationDeliveryType())
                && orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() != null) {
                // 配達指定日
                detailDto.setDeliveryDesignatedDay(orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate());
            }

            // 今すぐお届け かつ 配送情報取得 詳細情報が存在するの場合
            if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())
                && orderDeliveryDto.getDeliveryInformationDetailDto() != null) {
                // お届け時間帯
                if (HTypeDeliveryType.YAMATO.getValue()
                                            .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                    .getDeliveryCompanyCode())
                    // 2023-renew No14 from here
                    || HTypeDeliveryType.AUTOMATIC.getValue()
                                                  .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                          .getDeliveryCompanyCode())) {
                    // ヤマト 又は 自動設定の場合
                    // 2023-renew No14 to here
                    detailDto.setDeliveryDesignatedTimeCode(EnumTypeUtil.getEnumFromValue(
                                    HTypeReceiverTimeZoneYamato.class,
                                    orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone()
                                                                                         ).getValue());
                    if (HTypeReceiverTimeZoneYamato.UNSPECIFIED.getValue()
                                                               .equals(detailDto.getDeliveryDesignatedTimeCode())) {
                        // 指定なしの場合はnullに変換
                        detailDto.setDeliveryDesignatedTimeCode(null);
                    }
                }
                if (HTypeDeliveryType.JAPANPOST.getValue()
                                               .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                       .getDeliveryCompanyCode())) {
                    // 日本郵便
                    detailDto.setDeliveryDesignatedTimeCode(EnumTypeUtil.getEnumFromValue(
                                    HTypeReceiverTimeZoneJapanPost.class,
                                    orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone()
                                                                                         ).getValue());
                    if (HTypeReceiverTimeZoneJapanPost.UNSPECIFIED.getValue()
                                                                  .equals(detailDto.getDeliveryDesignatedTimeCode())) {
                        // 指定なしの場合はnullに変換
                        detailDto.setDeliveryDesignatedTimeCode(null);
                    }
                }
            }

            // 合計金額
            detailDto.setTotalPrice(receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal());
            // ポイント値引(0)
            detailDto.setPointDiscountPrice(POINT_DISCOUNT_PRICE_FIXED_VALUE);
            // プロモ値引(0)
            detailDto.setPromotionDiscountPrice(PROMOTION_DISCOUNT_PRICE_FIXED_VALUE);
            // 値引(0)
            detailDto.setDiscountPrice(DISCOUNT_PRICE_FIXED_VALUE);
            // 合計値引(プロモーション連携で返却された値)
            detailDto.setTotalDiscountPrice(orderDeliveryDto.getDiscountPrice());
            // 送料
            detailDto.setShippingPrice(orderDeliveryDto.getOrderDeliveryEntity().getCarriage());
            // 請求金額
            detailDto.setBillingPrice(receiveOrderDto.getOrderSummaryEntity().getOrderPrice());
            // 内消費税
            detailDto.setTaxPrice(orderDeliveryDto.getTotalTax());
            // 商品リスト 作成
            detailDto.setGoodsInfo(new ArrayList<>());
            for (OrderGoodsEntity entity : orderDeliveryDto.getOrderGoodsEntityList()) {
                WebApiAddOrderInformationRequestGoodsDto resGoodsDto =
                                setAddOrderInformationGoodsInto(entity, receiveOrderDto.getAllocationDeliveryType());
                // 受注連番のみここで採番
                resGoodsDto.setOrderSeq(String.valueOf(++orderGoodsIndex));
                detailDto.getGoodsInfo().add(resGoodsDto);
            }
            // マーチャント取引ID
            if (receiveOrderDto.isExecuteAuthori()) {
                detailDto.setTradingID(detailDto.getOrderNo() + "_001");
            } else {
                detailDto.setTradingID(null);
            }
            // クーポンコード
            detailDto.setCouponCode(receiveOrderDto.getCouponCode());
            // プロモーションコード(「プロモーション連携」で取得したプロモーションコードを設定する)
            detailDto.setPromotionCode(receiveOrderDto.getPromotionCode());

            orderInfo.add(detailDto);
        }

        reqDto.setOrderInfo(orderInfo);

        // 受注連携 実行
        webApiAddOrderInformationLogic.execute(reqDto);
    }

    /**
     * 受注連携リクエスト用の商品を作成します。
     *
     * @param orderGoodsEntity 受注商品エンティティ
     * @param deliveryType     配送方法
     * @return 商品リスト
     */
    public WebApiAddOrderInformationRequestGoodsDto setAddOrderInformationGoodsInto(OrderGoodsEntity orderGoodsEntity,
                                                                                    HTypeAllocationDeliveryType deliveryType) {

        WebApiAddOrderInformationRequestGoodsDto resGoodsDto = new WebApiAddOrderInformationRequestGoodsDto();

        // 申込商品
        // 心意気商品の場合、商品コード末尾のkpが連携されないよう削除
        resGoodsDto.setGoodsNo(
                        goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode()));
        // 数量
        resGoodsDto.setQuantity(conversionUtility.toString(orderGoodsEntity.getGoodsCount()));
        // 単価(税抜)
        resGoodsDto.setUnitPrice(orderGoodsEntity.getGoodsPrice());
        // 金額 (数量×単価(税抜))
        resGoodsDto.setPrice(orderGoodsEntity.getGoodsCount().multiply(orderGoodsEntity.getGoodsPrice()));
        // 状態フラグ
        resGoodsDto.setStateFlag(deliveryType.getValue());
        // セールコード
        resGoodsDto.setSaleCode(orderGoodsEntity.getSaleCode());
        // 備考
        resGoodsDto.setRemarks(orderGoodsEntity.getNote());
        // 注意事項
        resGoodsDto.setHints(orderGoodsEntity.getHints());
        // 同梱商品フラグ
        resGoodsDto.setBundleFlag(orderGoodsEntity.isBundleFlag() ? BUNDLE_FLAG_ON : BUNDLE_FLAG_OFF);
        // 明細番号
        if (!StringUtils.isEmpty(orderGoodsEntity.getOrderSerial())) {
            // 明細番号が存在する場合は明細番号を設定
            resGoodsDto.setDetailNo(orderGoodsEntity.getOrderSerial());
        }
        // グループ
        resGoodsDto.setGroupCode(orderGoodsEntity.getGroupCode());
        // 適用割引(「割引適用結果取得」で取得した適用割引を設定する)
        if (orderGoodsEntity.getDiscountsType() != null) {
            resGoodsDto.setDiscountFlag(orderGoodsEntity.getDiscountsType().getValue());
        }
        // 標準単価（数量割引適用結果取得APIで取得した価格を設定）
        if (orderGoodsEntity.getPrice() != null) {
            resGoodsDto.setBasePrice(orderGoodsEntity.getPrice());
        } else {
            // 取得できない場合は0円を設定
            resGoodsDto.setBasePrice(BigDecimal.ZERO);
        }

        // セール値引額（数量割引適用結果取得APIの「標準単価」－「割引価格」を設定）
        if (orderGoodsEntity.getPrice() != null && orderGoodsEntity.getGoodsPrice() != null) {
            resGoodsDto.setSaleDiscount(orderGoodsEntity.getPrice().subtract(orderGoodsEntity.getGoodsPrice()));
        } else {
            // 取得できない場合は0円を設定
            resGoodsDto.setSaleDiscount(BigDecimal.ZERO);
        }

        // 単価値引額（プロモーション連携APIで取得した単価値引額を設定）
        if (!StringUtils.isEmpty(orderGoodsEntity.getOrderSerial())
            && orderGoodsEntity.getUnitDiscountPrice() != null) {
            // 明細番号が存在する場合は単価値引額を設定
            resGoodsDto.setUnitDiscountPrice(orderGoodsEntity.getUnitDiscountPrice());
        } else {
            // プロモーションが取得できない場合は0円を設定
            resGoodsDto.setUnitDiscountPrice(BigDecimal.ZERO);
        }

        return resGoodsDto;
    }
}
// PDR Migrate Customization to here
