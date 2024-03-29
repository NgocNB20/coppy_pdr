// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliverySlipFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionInformationCreateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponseBundleDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponseDiscountInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPromotionResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPromotionInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPromotionInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * プロモーション連携ロジッククラス
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderPromotionInformationLogicImpl extends AbstractShopLogic implements OrderPromotionInformationLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPromotionInformationLogicImpl.class);

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 注文保留Utilityクラス
     */
    private final OrderPendingUtility orderPendingUtility;

    /**
     * WEB-API連携クラス プロモーション
     */
    private final WebApiGetPromotionInformationLogic webApiGetPromotionInformationLogic;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 受注関連Utility
     */
    private final OrderUtility orderUtility;

    /**
     * 商品詳細情報MAP取得
     */
    private final GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    // 2023-renew No24 from here
    /**
     * クーポン関連ユーティリティクラス
     */
    private final CouponUtility couponUtility;
    // 2023-renew No24 to here

    @Autowired
    public OrderPromotionInformationLogicImpl(DateUtility dateUtility,
                                              OrderPendingUtility orderPendingUtility,
                                              WebApiGetPromotionInformationLogic webApiGetPromotionInformationLogic,
                                              ConversionUtility conversionUtility,
                                              OrderUtility orderUtility,
                                              GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic,
                                              GoodsUtility goodsUtility,
                                              CouponUtility couponUtility) {
        this.dateUtility = dateUtility;
        this.webApiGetPromotionInformationLogic = webApiGetPromotionInformationLogic;
        this.orderPendingUtility = orderPendingUtility;
        this.conversionUtility = conversionUtility;
        this.orderUtility = orderUtility;
        this.goodsDetailsGetByCodeLogic = goodsDetailsGetByCodeLogic;
        this.goodsUtility = goodsUtility;
        // 2023-renew No24 from here
        this.couponUtility = couponUtility;
        // 2023-renew No24 to here
    }

    /**
     * プロモーション連携を行い
     * 受注DTOリストに反映を行います。
     *
     * @param receiveOrderDtoList 受注DTOリスト
     * @param checkMessageDtoList エラーメッセージ用List
     */
    public void execute(List<ReceiveOrderDto> receiveOrderDtoList, List<CheckMessageDto> checkMessageDtoList) {
        // 受注連番Mapを作成
        Map<String, Map<String, Integer>> orderSerialMap = new HashMap<>();

        WebApiGetPromotionInformationRequestDto reqDto =
                        createWebApiGetPromotionInformationRequest(receiveOrderDtoList, orderSerialMap);
        if (reqDto == null) {
            // 99明細を超えたためエラー
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(MSGCD_DETAIL_MAX);
            checkMessageDto.setError(true);
            checkMessageDtoList.add(checkMessageDto);
            return;
        }

        WebApiGetPromotionInformationResponseDto resDto =
                        (WebApiGetPromotionInformationResponseDto) webApiGetPromotionInformationLogic.execute(reqDto);

        // プロモ商品対象外
        if (WebApiGetPromotionInformationLogic.WEB_API_STATUS_SUCCESS_EXCLUDED.equals(resDto.getResult().getStatus())) {
            //【値引きデータパターン No.1】クーポン・プロモーションどちらも未適用
            return;
        }

        // 必要な情報が無ければnull
        if (resDto.getInfo() == null || resDto.getInfo().isEmpty()) {

            // 保留判定用にプロモ取得エラーを設定
            for (ReceiveOrderDto dto : receiveOrderDtoList) {
                orderPendingUtility.checkPrimaryPending(dto, HTypePendingType.PROMO_READ_ERROR);
            }
            return;
        }

        WebApiGetPromotionResponseDetailDto promotionInformationDto = resDto.getInfo().get(0);

        // 金額情報
        Map<String, WebApiGetPromotionResponsePriceDto> priceInfoMap = promotionInformationDto.getPriceInfoMap();

        // 同梱情報
        Map<String, List<WebApiGetPromotionResponseBundleDto>> bundleDtoMap =
                        promotionInformationDto.getBundleInfoMap();

        // 値引率情報
        Map<String, WebApiGetPromotionResponseDiscountInfoDto> discountInfoMap =
                        promotionInformationDto.getDiscountInfoMap();

        // 金額情報、同梱情報、値引率情報が存在しない場合
        if (priceInfoMap.isEmpty() && bundleDtoMap.isEmpty() && discountInfoMap.isEmpty()) {
            // 処理終了
            return;
        }

        // 2023-renew No24 from here
        // API処理結果メッセージ
        String apiMessage = resDto.getResult().getMessage();
        // 単一表示メッセージがセット済かどうか
        boolean isExistSingletonMessage = false;
        // クーポンコードが入力済かどうか（1件目の受注の値で全てチェックする）
        boolean isInputCouponCode = receiveOrderDtoList.get(0).getCouponCode() != null;
        // 2023-renew No24 to here

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {

            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

            // 出荷予定日
            String shippingDate;
            if (orderDeliveryDto.getOrderDeliveryEntity().getShipmentDate() != null
                && !HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            receiveOrderDto.getAllocationDeliveryType())) {
                shippingDate = dateUtility.format(orderDeliveryDto.getOrderDeliveryEntity().getShipmentDate(),
                                                  DateUtility.YMD
                                                 );
            } else {
                shippingDate = WebApiGetPromotionInformationCreateRequestDto.SHIPPINGDATE_FIXED;
            }

            // 2023-renew No14 from here
            // 配達指定日
            String deliveryDesignatedDay;
            if (orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() != null
                && !HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            receiveOrderDto.getAllocationDeliveryType())) {
                deliveryDesignatedDay = dateUtility.format(orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate(),
                                                           DateUtility.YMD
                                                          );
            } else {
                deliveryDesignatedDay = WebApiGetPromotionInformationCreateRequestDto.DELIVERYDESIGNATEDDAY_FIXED;
            }
            // 2023-renew No14 to here

            // 入荷予定日
            String stockDate;
            if ((orderDeliveryDto.getOrderGoodsEntityList().get(0)).getStockDate() != null
                && HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            receiveOrderDto.getAllocationDeliveryType())) {
                stockDate = dateUtility.format((orderDeliveryDto.getOrderGoodsEntityList().get(0)).getStockDate(),
                                               DateUtility.YMD
                                              );
            } else {
                stockDate = WebApiGetPromotionInformationCreateRequestDto.STOCKDATE_FIXED;
            }

            // 金額情報を取得
            WebApiGetPromotionResponsePriceDto priceDto = priceInfoMap.get(shippingDate
                                                                           // 2023-renew No14 from here
                                                                           + deliveryDesignatedDay
                                                                           // 2023-renew No14 to here
                                                                           + stockDate);

            // 出荷予定日(メッセージ表示用)
            String delivery = orderUtility.getDeliveryDate(receiveOrderDto);

            boolean isDiscount = false;
            // 金額情報が存在する場合
            if (priceDto != null) {
                isDiscount = priceDto.getDiscountPrice() != null
                             && priceDto.getDiscountPrice().compareTo(BigDecimal.ZERO) != 0;

                if (isDiscount) {
                    // 値引あり
                    orderDeliveryDto.setDiscountPrice(priceDto.getDiscountPrice());
                }

                if (priceDto.getTaxPrice() != null && priceDto.getTaxPrice().compareTo(BigDecimal.ZERO) != 0) {
                    // 消費税
                    orderDeliveryDto.setTaxPrice(priceDto.getTaxPrice());
                }

                if (priceDto.getShippingPrice() != null && BigDecimal.ZERO.compareTo(priceDto.getShippingPrice()) < 0) {
                    // 送料
                    orderDeliveryDto.getOrderDeliveryEntity().setCarriage(priceDto.getShippingPrice());
                    receiveOrderDto.getOrderSettlementEntity().setCarriage(priceDto.getShippingPrice());
                }
            }

            if (!discountInfoMap.isEmpty()) {
                // 値引率情報が存在する場合
                Map<String, Integer> orderSerialGoodsMap = orderSerialMap.get(shippingDate
                                                                              // 2023-renew No14 from here
                                                                              + deliveryDesignatedDay
                                                                              // 2023-renew No14 to here
                                                                              + stockDate);
                // 受注連番を紐付 受注商品に明細番号を設定
                setOrderSerial(orderDeliveryDto.getOrderGoodsEntityList(), orderSerialGoodsMap, discountInfoMap);
            }

            // 値引結果メッセージ判定処理
            if (priceDto != null) {
                // 2023-renew No24 from here
                String messageId = null;
                Object[] args = null;
                boolean isError = false;
                // 2023-renew No24 to here

                // クーポン以外プロモーション適用結果：0（未適用）
                if (promotionInformationDto.getPromApplyFlag() == 0) {
                    // 値引あり
                    if (isDiscount) {
                        // クーポンプロモーション適用結果：0（未適用）
                        if (promotionInformationDto.getCouponApplyFlag() == 0) {
                            // 正常
                            if ("0".equals(resDto.getResult().getStatus())) {
                                //【値引きデータパターン該当なし、No.4と同等として扱う】クーポン未適用
                                // 2023-renew No24 from here
                                messageId = MSGCD_NOT_COUPON_DISCOUNT;
                                args = new String[] {StringUtils.defaultString(promotionInformationDto.getCouponName()),
                                                StringUtils.defaultString(delivery)};
                                isError = true;
                                // 2023-renew No24 to here
                            }
                        }
                        // クーポンプロモーション適用結果：2（存在なし（クーポンM未登録））
                        else if (promotionInformationDto.getCouponApplyFlag() == 2) {
                            //【値引きデータパターン該当なし、No.6と同等として扱う】クーポン不可
                            // 2023-renew No24 from here
                            messageId = MSGCD_OUT_OF_SERVICE_COUPON;
                            isError = true;
                            // 2023-renew No24 to here
                        }
                    }
                    // 値引なし
                    else {
                        // クーポンコード入力あり
                        if (isInputCouponCode) {
                            // クーポンプロモーション適用結果：0（未適用）
                            if (promotionInformationDto.getCouponApplyFlag() == 0) {
                                //【値引きデータパターン No.4】クーポン未適用
                                // 2023-renew No24 from here
                                messageId = MSGCD_NOT_COUPON_DISCOUNT;
                                args = new String[] {StringUtils.defaultString(promotionInformationDto.getCouponName()),
                                                StringUtils.defaultString(delivery)};
                                isError = true;
                                // 2023-renew No24 to here
                            }
                            // クーポンプロモーション適用結果：2（存在なし（クーポンM未登録））
                            else if (promotionInformationDto.getCouponApplyFlag() == 2) {
                                //【値引きデータパターン No.6】クーポン不可
                                // 2023-renew No24 from here
                                messageId = MSGCD_OUT_OF_SERVICE_COUPON;
                                isError = true;
                                // 2023-renew No24 to here
                            }
                        }
                    }
                }
                // クーポン以外プロモーション適用結果：1（適用）
                else {
                    // 値引あり
                    if (isDiscount) {
                        // クーポンプロモーション適用結果：0（未適用）
                        if (promotionInformationDto.getCouponApplyFlag() == 0) {
                            // クーポンコード入力あり
                            if (isInputCouponCode) {
                                //【値引きデータパターン No.5】プロモーションのみ適用（クーポン未適用）
                                // 2023-renew No24 from here
                                messageId = MSGCD_ONLY_PROMOTION_NOT_COUPON;
                                args = new String[] {StringUtils.defaultString(promotionInformationDto.getCouponName()),
                                                StringUtils.defaultString(delivery)};
                                isError = true;
                                // 2023-renew No24 to here
                            }
                            // クーポンコード入力なし
                            else {
                                //【値引きデータパターン No.3】プロモーションのみ適用（値引あり）
                                // 2023-renew No24 from here
                                messageId = MSGCD_PROMOTION_DISCOUNT;
                                args = new String[] {StringUtils.defaultString(delivery)};
                                // 2023-renew No24 to here
                            }
                        }
                        // クーポンプロモーション適用結果：1（適用）
                        else if (promotionInformationDto.getCouponApplyFlag() == 1) {
                            // クーポンコード入力あり
                            if (isInputCouponCode) {
                                //【値引きデータパターン No.9】クーポン、プロモーションどちらも適用
                                // 2023-renew No24 from here
                                messageId = MSGCD_PROMOTION_AND_COUPON;
                                args = new String[] {StringUtils.defaultString(promotionInformationDto.getCouponName()),
                                                StringUtils.defaultString(delivery)};
                                // 2023-renew No24 to here
                            }
                        }
                        // クーポンプロモーション適用結果：2（存在なし（クーポンM未登録））
                        else if (promotionInformationDto.getCouponApplyFlag() == 2) {
                            // クーポンコード入力あり
                            if (isInputCouponCode) {
                                //【値引きデータパターン No.7】プロモーションのみ適用（クーポン不可）
                                // 2023-renew No24 from here
                                messageId = MSGCD_PROMOTION_OUT_OF_SERVICE_COUPON;
                                args = new String[] {StringUtils.defaultString(promotionInformationDto.getCouponName()),
                                                StringUtils.defaultString(delivery)};
                                isError = true;
                                // 2023-renew No24 to here
                            }
                        }
                    }
                    // 値引なし
                    else {
                        // クーポン入力あり
                        if (isInputCouponCode) {
                            // クーポンプロモーション適用結果：1（適用）
                            if (promotionInformationDto.getCouponApplyFlag() == 1) {
                                //【値引きデータパターン No.8】値引きなし、クーポン、プロモーションどちらも適用（プレゼントの場合）
                                // 2023-renew No24 from here
                                messageId = MSGCD_PROMOTION_AND_COUPON_FOR_PRESENT;
                                args = new String[] {StringUtils.defaultString(promotionInformationDto.getCouponName()),
                                                StringUtils.defaultString(delivery)};
                                // 2023-renew No24 to here
                            }
                        }
                        // クーポンコード入力なし
                        else {
                            // 2023-renew No24 from here
                            // クーポンプロモーション適用結果：0（未適用）
                            if (promotionInformationDto.getCouponApplyFlag() == 0) {
                                // 正常
                                if ("0".equals(resDto.getResult().getStatus())) {
                                    //【値引きデータパターン No.2】プロモーションのみ適用（値引きなし）
                                    messageId = MSGCD_PROMOTION_NOT_DISCOUNT;
                                    args = new String[] {StringUtils.defaultString(delivery)};
                                }
                            }
                            // 2023-renew No24 to here
                        }
                    }
                }

                // 2023-renew No24 from here
                if (messageId != null) {
                    // クーポン未適用 且つ プロモーションAPIからメッセージが返却された場合
                    if (couponUtility.isCouponErrorMessageList(messageId) && StringUtil.isNotEmpty(apiMessage)) {
                        // メッセージを書き換える（APIから返却されたメッセージをそのまま返す）
                        messageId = MSGCD_PROMOTION_API_MESSAGE;
                        args = new String[] {StringUtils.defaultString(apiMessage)};
                        isError = true;
                    }

                    // 単一表示（重複表示しない）メッセージかどうか
                    boolean isSingleton = couponUtility.isSingletonMessage(messageId);
                    // 単一表示メッセージでない 又は 単一表示メッセージが未セットの場合
                    if (!isSingleton || !isExistSingletonMessage) {
                        // メッセージをセット
                        CheckMessageDto checkMessageDto = new CheckMessageDto();
                        checkMessageDto.setMessageId(messageId);
                        checkMessageDto.setArgs(args);
                        checkMessageDto.setError(isError);
                        checkMessageDtoList.add(checkMessageDto);
                        if (isSingleton) {
                            // 単一表示メッセージ：セット済に変更
                            isExistSingletonMessage = true;
                        }
                    }
                }
                // 2023-renew No24 to here

                // プロモーションコードを設定
                receiveOrderDto.setPromotionCode(priceDto.getPromotionCode());
            }

            List<WebApiGetPromotionResponseBundleDto> bundleDtoGoodsList = bundleDtoMap.get(shippingDate
                                                                                            // 2023-renew No14 from here
                                                                                            + deliveryDesignatedDay
                                                                                            // 2023-renew No14 to here
                                                                                            + stockDate);
            // 出荷予定日に紐づく同梱情報なし
            if (bundleDtoGoodsList == null) {
                continue;
            }
            // 受注商品Entity 追加
            addOrderGoodsEntityByBundleGoods(bundleDtoGoodsList, receiveOrderDto);
        }

    }

    /**
     * プロモーション連携用のリクエストデータを作成します。
     * <pre>
     * 99明細を超えた場合はnullで返却
     * </pre>
     *
     * @param receiveOrderDtoList 受注DTOリスト
     * @param orderSerialMap      受注連番Map
     * @return プロモーション情報
     */
    public WebApiGetPromotionInformationRequestDto createWebApiGetPromotionInformationRequest(List<ReceiveOrderDto> receiveOrderDtoList,
                                                                                              Map<String, Map<String, Integer>> orderSerialMap) {

        // 受注連番用
        int orderSerialNum = 0;

        // プロモーション連携リクエスト 詳細情報
        List<String> detailInfo = new ArrayList<>();

        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = receiveOrderDtoList.get(0).getMasterDto().getGoodsMaster();

        // 受注番号
        String orderCode = receiveOrderDtoList.get(0).getOrderSummaryEntity().getOrderCode();
        // クーポンコード
        String couponCode = receiveOrderDtoList.get(0).getCouponCode();
        // 受注全体の合計金額を計算
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            totalPrice = totalPrice.add(orderUtility.getGoodsPriceTotalNoTax(
                            receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()));
        }

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {

            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

            // 予約フラグ(取りおきかどうか)
            String reservationFlag =
                            HTypeAllocationDeliveryType.RESERVABLE.equals(receiveOrderDto.getAllocationDeliveryType()) ?
                                            WebApiGetPromotionInformationCreateRequestDto.RESERVATION_FLAG_ON :
                                            WebApiGetPromotionInformationCreateRequestDto.RESERVATION_FLAG_OFF;

            // 出荷予定日
            String shippingDate;
            if (orderDeliveryDto.getOrderDeliveryEntity().getShipmentDate() != null
                && !HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            receiveOrderDto.getAllocationDeliveryType())) {
                shippingDate = dateUtility.format(orderDeliveryDto.getOrderDeliveryEntity().getShipmentDate(),
                                                  DateUtility.YMD
                                                 );
            } else {
                shippingDate = WebApiGetPromotionInformationCreateRequestDto.SHIPPINGDATE_FIXED;
            }

            // 2023-renew No14 from here
            // 配達指定日
            String deliveryDesignatedDay;
            if (orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() != null
                && !HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                            receiveOrderDto.getAllocationDeliveryType())) {
                deliveryDesignatedDay = dateUtility.format(orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate(),
                                                           DateUtility.YMD
                                                          );
            } else {
                deliveryDesignatedDay = WebApiGetPromotionInformationCreateRequestDto.DELIVERYDESIGNATEDDAY_FIXED;
            }
            // 2023-renew No14 to here

            // 商品に紐づく受注連番Map生成
            Map<String, Integer> map = new HashMap<>();
            String stockDate = null;

            for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {
                // 入荷予定日
                if ((orderGoodsEntity.getStockDate() != null && HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                                receiveOrderDto.getAllocationDeliveryType()))) {
                    stockDate = dateUtility.format(orderGoodsEntity.getStockDate(), DateUtility.YMD);
                }

                // 商品詳細情報
                GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(orderGoodsEntity.getGoodsSeq());

                // リクエスト 作成用
                WebApiGetPromotionInformationCreateRequestDto reqDtlDto =
                                new WebApiGetPromotionInformationCreateRequestDto();

                // 受注番号
                reqDtlDto.setOrderNo(orderCode);
                // 顧客番号
                reqDtlDto.setCustomerNo(
                                conversionUtility.toString(receiveOrderDto.getOrderPersonEntity().getCustomerNo()));
                // 管理商品コード
                reqDtlDto.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
                // 申込商品コード
                // 心意気商品の場合、商品コード末尾のkpが連携されないよう削除
                reqDtlDto.setGoodsCode(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                orderGoodsEntity.getGoodsCode()));
                // 商品分類コード
                reqDtlDto.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
                // カテゴリー1
                reqDtlDto.setCategory1(goodsDetailsDto.getGoodsCategory1());
                // カテゴリー2
                reqDtlDto.setCategory2(goodsDetailsDto.getGoodsCategory2());
                // カテゴリー3
                reqDtlDto.setCategory3(goodsDetailsDto.getGoodsCategory3());
                // セールコード
                reqDtlDto.setSaleCode(orderGoodsEntity.getSaleCode());
                // 予約フラグ
                reqDtlDto.setReservationFlag(reservationFlag);
                // 合計金額(単価 × 商品数量) 小数点切り捨て
                reqDtlDto.setTotalGoodsPrice(totalPrice.setScale(0, RoundingMode.DOWN).toString());
                // 数量
                reqDtlDto.setGoodsCount(orderGoodsEntity.getGoodsCount().toString());
                // 単価 小数点切り捨て
                reqDtlDto.setGoodsPrice(orderGoodsEntity.getGoodsPrice()
                                                        .multiply(orderGoodsEntity.getGoodsCount())
                                                        .setScale(0, RoundingMode.DOWN)
                                                        .toString());
                // 出荷予定日
                reqDtlDto.setShippingDate(shippingDate);

                // 2023-renew No14 from here
                // 配達指定日
                reqDtlDto.setDeliveryDesignatedDay(deliveryDesignatedDay);
                // 2023-renew No14 to here

                // 受注連番
                reqDtlDto.setOrderSeq(String.valueOf(++orderSerialNum));
                // クーポンコード
                reqDtlDto.setCouponCode(couponCode);
                // 適用割引（「割引適用結果取得」で取得した適用割引を設定する）
                if (orderGoodsEntity.getDiscountsType() != null) {
                    reqDtlDto.setSaleType(orderGoodsEntity.getDiscountsType().getValue());
                }
                // お届け先顧客番号
                if (ADD_TYPE_RECEIVER.equals(receiveOrderDto.getOrderDeliveryDto().getAddType())) {
                    // 新しいお届け先の場合
                    reqDtlDto.setReceiveCustomerNo(SPACE);
                } else {
                    reqDtlDto.setReceiveCustomerNo(orderDeliveryDto.getCustomerNo().toString());
                }

                // 入荷予定日
                reqDtlDto.setStockDate(stockDate);

                // リストに追加
                detailInfo.add(reqDtlDto.createGetPromotionInformationRequestDetailInfo());

                // 99明細を超えたら 処理終了
                if (orderSerialNum > WebApiGetPromotionInformationCreateRequestDto.LIST_MAX_COUNT) {
                    return null;
                }

                map.put(goodsDetailsDto.getGoodsCode(), orderSerialNum);
            }

            // 出荷予定日＋配達指定日＋入荷予定日、商品コードで紐付けた受注連番Mapを作成
            if (StringUtils.isEmpty(shippingDate)) {
                // 出荷予定日がnullの場合は9999/12/31で設定
                shippingDate = WebApiGetPromotionInformationCreateRequestDto.SHIPPINGDATE_FIXED;
            }
            // 2023-renew No14 from here
            if (StringUtils.isEmpty(deliveryDesignatedDay)) {
                // 配達指定日がnullの場合は9999/12/31で設定
                deliveryDesignatedDay = WebApiGetPromotionInformationCreateRequestDto.DELIVERYDESIGNATEDDAY_FIXED;
            }
            // 2023-renew No14 to here
            if (StringUtils.isEmpty(stockDate)) {
                // 入荷予定日がnullの場合は9999/12/31で設定
                stockDate = WebApiGetPromotionInformationCreateRequestDto.STOCKDATE_FIXED;
            }
            orderSerialMap.put(shippingDate + deliveryDesignatedDay + stockDate, map);
        }

        WebApiGetPromotionInformationRequestDto reqDto = new WebApiGetPromotionInformationRequestDto();
        // 明細内容 設定
        reqDto.setDetailInfo(detailInfo);

        return reqDto;
    }

    /**
     * 同梱商品を受注商品エンティティに追加します。
     *
     * @param bundleDtoGoodsList  同梱情報リスト
     * @param receiveOrderDto     受注DTO
     */
    public void addOrderGoodsEntityByBundleGoods(List<WebApiGetPromotionResponseBundleDto> bundleDtoGoodsList,
                                                 ReceiveOrderDto receiveOrderDto) {

        List<OrderGoodsEntity> orderGoodsEntityList = receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList();

        // 注文連番を取得
        Integer orderConsecutiveNo = orderGoodsEntityList.get(0).getOrderConsecutiveNo();

        for (WebApiGetPromotionResponseBundleDto bundleDto : bundleDtoGoodsList) {
            String goodsCode = bundleDto.getBundleGoodsCode();
            GoodsDetailsDto goodsDetailsDto = goodsDetailsGetByCodeLogic.execute(1001, goodsCode, null, null);

            // 受注商品Entity作成
            // 使用しない項目に関しても念のため設定を行う。
            OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();

            // 商品情報が取得できなかった場合
            if (goodsDetailsDto == null) {
                // 注文保留処理を行う。
                orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.PROMO_READ_ERROR);

                // ダミーの商品情報を取得する。
                String dummyGoodsCode = PropertiesUtil.getSystemPropertiesValue(DUMMY_GOODS_CODE);
                goodsDetailsDto = goodsDetailsGetByCodeLogic.execute(1001, dummyGoodsCode, null, null);
                // 納品書印字フラグ 印字しないを設定
                orderGoodsEntity.setDeliverySlipFlag(HTypeDeliverySlipFlag.OFF);
                LOGGER.warn("プロモーション連携で返却された同梱商品がDBに存在しません。 受注番号(代表)：" + receiveOrderDto.getOrderSummaryEntity()
                                                                                        .getOrderCode() + " 顧客番号："
                            + receiveOrderDto.getOrderPersonEntity().getCustomerNo() + " 同梱商品：" + goodsCode);
            } else {
                // 納品書印字フラグ
                orderGoodsEntity.setDeliverySlipFlag(EnumTypeUtil.getEnumFromValue(HTypeDeliverySlipFlag.class,
                                                                                   bundleDto.getDeliverySlipFlag()
                                                                                  ));
            }

            // マスタ情報に追加
            receiveOrderDto.getMasterDto().getGoodsMaster().put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);

            // 商品SEQ
            orderGoodsEntity.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
            // 注文連番
            orderGoodsEntity.setOrderConsecutiveNo(orderConsecutiveNo);
            // 商品コード
            orderGoodsEntity.setGoodsCode(goodsCode);
            // 商品グループコード
            orderGoodsEntity.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
            // 商品グループ表示名
            orderGoodsEntity.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
            // 商品単価（税抜）
            orderGoodsEntity.setGoodsPrice(BigDecimal.ZERO);
            // 税率
            orderGoodsEntity.setTaxRate(goodsDetailsDto.getTaxRate());
            // 商品消費税種別
            orderGoodsEntity.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());
            // 注文数量
            orderGoodsEntity.setGoodsCount(new BigDecimal(bundleDto.getBundleGoodsCount()));
            // 無料配送フラグ
            orderGoodsEntity.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag());
            // 商品個別配送種別
            orderGoodsEntity.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType());
            // 規格値１
            orderGoodsEntity.setUnitValue1(goodsDetailsDto.getUnitValue1());
            // 規格値２
            orderGoodsEntity.setUnitValue2(goodsDetailsDto.getUnitValue2());
            // JANコード
            orderGoodsEntity.setJanCode(goodsDetailsDto.getJanCode());
            // カタログコード
            orderGoodsEntity.setCatalogCode(goodsDetailsDto.getCatalogCode());
            // 納期
            orderGoodsEntity.setDeliveryType(goodsDetailsDto.getDeliveryType());
            // 受注連携設定01
            orderGoodsEntity.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
            // 受注連携設定02
            orderGoodsEntity.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
            // 受注連携設定03
            orderGoodsEntity.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
            // 受注連携設定04
            orderGoodsEntity.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
            // 受注連携設定05
            orderGoodsEntity.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
            // 受注連携設定06
            orderGoodsEntity.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
            // 受注連携設定07
            orderGoodsEntity.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
            // 受注連携設定08
            orderGoodsEntity.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
            // 受注連携設定09
            orderGoodsEntity.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
            // 受注連携設定10
            orderGoodsEntity.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
            // 同梱商品フラグ
            orderGoodsEntity.setBundleFlag(true);
            // 受注商品Entityに追加
            orderGoodsEntityList.add(orderGoodsEntity);
        }
    }

    /**
     * 受注商品にプロモーションで返却された受注連番を紐付けます。
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @param orderSerialGoodsMap  商品番号に紐づいた受注連番
     * @param discountInfoMap      割引率情報マップ
     */
    public void setOrderSerial(List<OrderGoodsEntity> orderGoodsEntityList,
                               Map<String, Integer> orderSerialGoodsMap,
                               Map<String, WebApiGetPromotionResponseDiscountInfoDto> discountInfoMap) {

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {

            // 商品に紐づく受注連番を取得
            Integer orderSerial = orderSerialGoodsMap.get(orderGoodsEntity.getGoodsCode());
            if (orderSerial == null) {
                // 該当する受注連番なし スキップ
                continue;
            }

            if (discountInfoMap.get(String.valueOf(orderSerial)) != null) {
                // 割引率情報マップに存在する場合
                // 受注連番を設定
                orderGoodsEntity.setOrderSerial(String.valueOf(orderSerial));
                // 単価値引額を設定
                orderGoodsEntity.setUnitDiscountPrice(
                                discountInfoMap.get(String.valueOf(orderSerial)).getUnitDiscountPrice());
            }
        }
    }
}
// PDR Migrate Customization to here
