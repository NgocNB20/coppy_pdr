// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiOrderPendingCheckRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiOrderPendingCheckResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPendingCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountInCurrentDayGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiOrderPendingCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * #028 注文情報の保留<br/>
 *
 * <pre>
 * 注文保留ロジッククラス
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderPendingCheckLogicImpl extends AbstractShopLogic implements OrderPendingCheckLogic {

    /** 受注業務ユーティリティクラス */
    private final OrderUtility orderUtility;

    /** 変換ユーティリティクラス */
    private final ConversionUtility conversionUtility;

    /** 注文保留ユーティリティクラス */
    private final OrderPendingUtility orderPendingUtility;

    /** 商品ユーティリティクラス */
    private final GoodsUtility goodsUtility;

    /** WAB-API連携 注文保留チェック */
    private final WebApiOrderPendingCheckLogic webApiOrderPendingCheckLogic;

    /** 商品詳細情報MAP取得 */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    // 2023-renew No15 from here
    /** 会員情報取得Logic */
    private final MemberInfoGetLogic memberInfoGetLogic;
    // 2023-renew No15 to here

    // 2023-renew No26 from here
    /** 受注サマリカウント情報取得ロジック */
    private final OrderSummaryCountInCurrentDayGetLogic orderSummaryCountInCurrentDayGetLogic;
    // 2023-renew No26 to here

    @Autowired
    public OrderPendingCheckLogicImpl(OrderUtility orderUtility,
                                      ConversionUtility conversionUtility,
                                      OrderPendingUtility orderPendingUtility,
                                      GoodsUtility goodsUtility,
                                      WebApiOrderPendingCheckLogic webApiOrderPendingCheckLogic,
                                      GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                                      MemberInfoGetLogic memberInfoGetLogic,
                                      OrderSummaryCountInCurrentDayGetLogic orderSummaryCountInCurrentDayGetLogic) {
        this.orderUtility = orderUtility;
        this.conversionUtility = conversionUtility;
        this.orderPendingUtility = orderPendingUtility;
        this.goodsUtility = goodsUtility;
        this.webApiOrderPendingCheckLogic = webApiOrderPendingCheckLogic;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        // 2023-renew No15 from here
        this.memberInfoGetLogic = memberInfoGetLogic;
        // 2023-renew No15 to here
        // 2023-renew No26 from here
        this.orderSummaryCountInCurrentDayGetLogic = orderSummaryCountInCurrentDayGetLogic;
        // 2023-renew No26 to here
    }

    /**
     * 注文保留チェックを行います。
     *
     * @param receiveOrderDto 受注DTO
     * @param memberInfoSeq 会員SEQ
     */
    public void checkOrderPending(ReceiveOrderDto receiveOrderDto, Integer memberInfoSeq) {

        HTypePendingType pendingType = receiveOrderDto.getPendingType();

        // 以下の場合処理終了
        // ・既に優先順位の高い「プロモ読取エラー」が設定されている
        // ※プロモーション連携の際に設定
        // ・既に優先順位の高い「最短お届日取得エラー」が設定されている
        // ※配送方法設定画面で設定
        // ・既に優先順位の高い「新規お届け先」が設定されている
        // ※配送方法設定画面で設定
        if (HTypePendingType.PROMO_READ_ERROR.equals(pendingType) || HTypePendingType.ZIP_CODE_UNKNOWN.equals(
                        pendingType) || HTypePendingType.NEW_RECEPTION_DESK.equals(pendingType)) {
            // 処理終了
            return;
        }

        // WEB-API連携 注文保留チェック
        executeWebApiOrderPendingCheck(receiveOrderDto);

        // 支払方法
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
        // 注文合計金額
        BigDecimal orderPrice = receiveOrderDto.getOrderSummaryEntity().getOrderPrice();

        // 2023-renew No15 from here
        // 1伝票の注文金額
        // 会員情報のコンビニ・郵便振込使用可否が「2：初回」かつ、
        // 決済方法がコンビニ・郵便振込
        // 5万円を超える場合

        // 初回購入上限金額（コンビニ・郵便振込）
        BigDecimal amount = conversionUtility.toBigDecimal(
                        PropertiesUtil.getSystemPropertiesValue(KEY_TRANSFERPAYMENTUSE_FIRSTTIME_PURCHASE_LIMIT));
        // 会員情報 取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(memberInfoSeq);

        if (HTypeTransferPaymentUseFlag.FIRST_TIME.equals(memberInfoEntity.getTransferPaymentUseFlag())
            && HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER.equals(settlementMethodType)
            && orderPrice.compareTo(amount) >= 0) {

            // 注文保留 設定
            orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.FIRST_CONVENIENCE_ORDER_LIMIT);
            return;
        }
        // 2023-renew No15 to here

        // 保留商品、陸送クールチェック
        // 以下商品が存在するかチェック
        // ・保留フラグが立っている商品
        // ・陸送又はクール便に設定されている商品
        List<String> goodsCodeList = new ArrayList<String>();
        for (OrderGoodsEntity key : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            goodsCodeList.add(key.getGoodsCode());
        }

        Map<String, GoodsDetailsDto> goodsDetailsDtoMap = goodsDetailsMapGetLogic.executeByGoodsCode(goodsCodeList);

        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoMap.values()) {
            // 保留商品が存在した場合
            if (HTypeReserveFlag.ON.equals(goodsDetailsDto.getReserveFlag())) {
                // 注文保留
                orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.SPECIFIED_MERCHANDISE_HOLD);
                return;
            }
        }

        // 沖縄チェック
        if (receiveOrderDto.getOrderDeliveryDto()
                           .getOrderDeliveryEntity()
                           .getReceiverAddress1()
                           .startsWith(HTypePrefectureType.OKINAWA.getLabel())) {

            // クールフラグ
            boolean coolFlag = false;
            // 陸送フラグ
            boolean landFlag = false;

            for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoMap.values()) {
                if (HTypeCoolSendFlag.ON.equals(goodsDetailsDto.getCoolSendFlag()) && goodsUtility.checkCoolSend(
                                goodsDetailsDto.getCoolSendFrom(), goodsDetailsDto.getCoolSendTo())) {
                    // クール便チェック
                    coolFlag = true;
                }

                if (HTypeLandSendFlag.ON.equals(goodsDetailsDto.getLandSendFlag())) {
                    // 陸送便チェック
                    landFlag = true;

                }
            }

            if (coolFlag && landFlag) {
                // クール便と陸送便の商品が混在する場合
                // 注文保留
                orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.LAND_COOL_SEND);
                return;
            }
        }

        // 0円注文チェック
        if (BigDecimal.ZERO.compareTo(orderPrice) == 0) {
            // 注文保留
            orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.ZERO_YEN_SLIP);
            return;
        }

        // カード承認待ちチェック
        if (HTypeSettlementMethodType.CREDIT.equals(settlementMethodType)
            && !HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
            // 注文保留
            orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.WAIT_CARD_APPROVAL);
            return;
        }

        // 2023-renew No26 from here
        // 注文保留とする同一顧客の1日の複数回注文数
        int count = conversionUtility.toInteger(PropertiesUtil.getSystemPropertiesValue(KEY_MULTIPLE_ORDER_COUNT));
        int ordersInCurrentDay = orderSummaryCountInCurrentDayGetLogic.execute(memberInfoSeq);
        if (ordersInCurrentDay >= count) {
            // 1日に同一顧客でプロパティ値以上の注文は保留
            orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.MULTIPLE_ORDER);
        }
        // 2023-renew No26 to here
    }

    /**
     * WEB-API連携  注文保留チェックを行います。
     *
     * @param receiveOrderDto 受注DTO
     */
    public void executeWebApiOrderPendingCheck(ReceiveOrderDto receiveOrderDto) {

        WebApiOrderPendingCheckRequestDto reqDto = new WebApiOrderPendingCheckRequestDto();
        // 顧客番号
        reqDto.setCustomerNo(receiveOrderDto.getOrderPersonEntity().getCustomerNo());
        // 支払方法
        reqDto.setPaymentType(orderUtility.conversionPaymentMethod(
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType().getValue()).getValue());
        // 注文金額合計
        reqDto.setOrderTotalPrice(conversionUtility.toString(receiveOrderDto.getOrderSummaryEntity().getOrderPrice()));

        WebApiOrderPendingCheckResponseDto resDto =
                        (WebApiOrderPendingCheckResponseDto) webApiOrderPendingCheckLogic.execute(reqDto);

        // 取得できなかった場合はエラー
        if (resDto == null || CollectionUtil.isEmpty(resDto.getInfo())) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        // 保留不要の場合
        if (NOT_PENDING.equals(resDto.getInfo().get(0).getCheckResult())) {
            // 処理終了
            return;
        }

        HTypePendingType pendingType =
                        EnumTypeUtil.getEnumFromValue(HTypePendingType.class, resDto.getInfo().get(0).getHoldType());
        // 注文保留 チェック
        orderPendingUtility.checkPrimaryPending(receiveOrderDto, pendingType);
    }

}
// PDR Migrate Customization to here
