/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.summary.OrderSummaryForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryDtoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryForHistoryDtoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.OrderHistoryDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 注文履歴詳細情報取得サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
@Service
public class OrderHistoryDetailsGetServiceImpl extends AbstractShopService implements OrderHistoryDetailsGetService {

    /**
     * 受注サマリDto取得ロジック<br/>
     */
    private final OrderSummaryForHistoryDtoGetLogic orderSummaryForHistoryDtoGetLogic;

    /**
     * 受注インデックス取得ロジック
     */
    private final OrderIndexGetLogic orderIndexGetLogic;

    /**
     * 受注ご注文主取得ロジック
     */
    private final OrderPersonGetLogic orderPersonGetLogic;

    /**
     * 受注配送取得ロジック
     */
    private final OrderDeliveryGetLogic orderDeliveryGetLogic;

    /**
     * 受注配送取得ロジック
     */
    private final OrderDeliveryDtoGetLogic orderDeliveryDtoGetLogic;

    /**
     * 受注商品リスト取得ロジック
     */
    private final OrderGoodsListGetLogic orderGoodsListGetLogic;

    /**
     * 受注決済取得ロジック
     */
    private final OrderSettlementGetLogic orderSettlementGetLogic;

    /**
     * 受注追加料金リスト取得ロジック
     */
    private final OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic;

    /**
     * 受注請求取得ロジック
     */
    private final OrderBillGetLogic orderBillGetLogic;

    /**
     * 受注入金リスト取得ロジック
     */
    private final OrderReceiptOfMoneyListGetLogic orderReceiptOfMoneyListGetLogic;

    /**
     * マルチペイ請求情報取得ロジック
     */
    private final MulPayBillDao mulPayBillDao;

    /**
     * 決済方法取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * 配送方法取得ロジック
     */
    private final DeliveryMethodGetLogic deliveryMethodGetLogic;

    /**
     * 受注メモ取得ロジック
     */
    private final OrderMemoGetLogic orderMemoGetLogic;

    /**
     * クーポンDAO
     */
    private final CouponDao couponDao;

    /**
     * 会員業務ユーティリティクラス
     */
    private final MemberInfoUtility memberInfoUtility;

    @Autowired
    public OrderHistoryDetailsGetServiceImpl(OrderSummaryForHistoryDtoGetLogic orderSummaryForHistoryDtoGetLogic,
                                             OrderIndexGetLogic orderIndexGetLogic,
                                             OrderPersonGetLogic orderPersonGetLogic,
                                             OrderDeliveryGetLogic orderDeliveryGetLogic,
                                             OrderDeliveryDtoGetLogic orderDeliveryDtoGetLogic,
                                             OrderGoodsListGetLogic orderGoodsListGetLogic,
                                             OrderSettlementGetLogic orderSettlementGetLogic,
                                             OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic,
                                             OrderBillGetLogic orderBillGetLogic,
                                             OrderReceiptOfMoneyListGetLogic orderReceiptOfMoneyListGetLogic,
                                             MulPayBillDao mulPayBillDao,
                                             SettlementMethodGetLogic settlementMethodGetLogic,
                                             DeliveryMethodGetLogic deliveryMethodGetLogic,
                                             OrderMemoGetLogic orderMemoGetLogic,
                                             CouponDao couponDao,
                                             MemberInfoUtility memberInfoUtility) {
        this.orderSummaryForHistoryDtoGetLogic = orderSummaryForHistoryDtoGetLogic;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderPersonGetLogic = orderPersonGetLogic;
        this.orderDeliveryGetLogic = orderDeliveryGetLogic;
        this.orderDeliveryDtoGetLogic = orderDeliveryDtoGetLogic;
        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
        this.orderSettlementGetLogic = orderSettlementGetLogic;
        this.orderAdditionalChargeListGetLogic = orderAdditionalChargeListGetLogic;
        this.orderBillGetLogic = orderBillGetLogic;
        this.orderReceiptOfMoneyListGetLogic = orderReceiptOfMoneyListGetLogic;
        this.mulPayBillDao = mulPayBillDao;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
        this.orderMemoGetLogic = orderMemoGetLogic;
        this.couponDao = couponDao;
        this.memberInfoUtility = memberInfoUtility;
    }

    /**
     * 注文履歴詳細情報取得処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param orderCode     受注番号
     * @return 受注Dto
     */
    @Override
    public ReceiveOrderForHistoryDto execute(Integer memberInfoSeq, String orderCode) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);

        // 受注サマリDto取得
        OrderSummaryForHistoryDto orderSummaryDto = orderSummaryForHistoryDtoGetLogic.execute(orderCode);
        if (orderSummaryDto == null) {
            throwMessage(MSGCD_ORDERSUMMARYENTITYDTO_NULL);
        }

        // 会員の注文情報の場合
        if (memberInfoUtility.isMember(orderSummaryDto.getMemberInfoSeq())) {
            if (memberInfoUtility.isGuest(memberInfoSeq)) {
                // ゲストで会員の注文履歴詳細を参照しようとした場合エラー
                throwMessage(MSGCD_MEMBER_DEFERENT_ORDER);
            } else {
                // 同一会員チェック
                if (!memberInfoSeq.equals(orderSummaryDto.getMemberInfoSeq())) {
                    throwMessage(MSGCD_MEMBER_DEFERENT_ORDER);
                }
            }
        }

        // 受注取得サービス実行
        ReceiveOrderForHistoryDto receiveOrderDto = createReceiveOrderDtoForHistory(orderSummaryDto.getOrderSeq(),
                                                                                    orderSummaryDto.getOrderVersionNo()
                                                                                   );
        receiveOrderDto.setOrderSummaryDto(orderSummaryDto);
        return receiveOrderDto;
    }

    /**
     * 注文履歴用注文Dtoを作成する<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 注文番号連番
     * @return ReceiveOrderDtoForHistory
     */
    protected ReceiveOrderForHistoryDto createReceiveOrderDtoForHistory(Integer orderSeq, Integer orderVersionNo) {

        // 受注インデックスの取得
        OrderIndexEntity orderIndexEntity = orderIndexGetLogic.execute(orderSeq, orderVersionNo);
        if (orderIndexEntity == null) {
            return null;
        }

        // 受注ご注文主の取得
        OrderPersonEntity orderPersonEntity =
                        orderPersonGetLogic.execute(orderSeq, orderIndexEntity.getOrderPersonVersionNo());
        if (orderPersonEntity == null) {
            throwMessage(MSGCD_ORDERPERSONENTITYDTO_NULL);
        }

        // 受注配送Dtoリストの取得
        OrderDeliveryDto orderDeliveryDto =
                        orderDeliveryDtoGetLogic.execute(orderSeq, orderIndexEntity.getOrderDeliveryVersionNo(),
                                                         orderIndexEntity.getOrderGoodsVersionNo()
                                                        );
        if (orderDeliveryDto == null || orderDeliveryDto == null) {
            throwMessage(MSGCD_ORDERDELIVERYENTITYDTO_NULL);
        }

        // 受注決済の取得
        OrderSettlementEntity orderSettlementEntity =
                        orderSettlementGetLogic.execute(orderSeq, orderIndexEntity.getOrderSettlementVersionNo());
        if (orderSettlementEntity == null) {
            throwMessage(MSGCD_ORDERSETTLEMENTENTITYDTO_NULL);
        }

        // 受注追加料金の取得
        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList = new ArrayList<>();
        if (orderIndexEntity.getOrderAdditionalChargeVersionNo() != null) {
            orderAdditionalChargeEntityList = orderAdditionalChargeListGetLogic.execute(orderSeq,
                                                                                        orderIndexEntity.getOrderAdditionalChargeVersionNo()
                                                                                       );
        }

        // 受注請求の取得
        OrderBillEntity orderBillEntity = null;
        if (orderIndexEntity.getOrderBillVersionNo() != null) {
            orderBillEntity = orderBillGetLogic.execute(orderSeq, orderIndexEntity.getOrderBillVersionNo());
        }

        // 受注入金の取得
        List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList = new ArrayList<>();
        if (orderIndexEntity.getOrderReceiptOfMoneyVersionNo() != null) {
            orderReceiptOfMoneyEntityList = orderReceiptOfMoneyListGetLogic.execute(orderSeq,
                                                                                    orderIndexEntity.getOrderReceiptOfMoneyVersionNo()
                                                                                   );
        }

        // マルチペイメント請求情報の取得
        MulPayBillEntity mulPayBillEntity = mulPayBillDao.getMulPayBill(orderSeq, orderVersionNo);

        // 決済方法の取得
        SettlementMethodEntity settlementMethodEntity = null;
        if (orderSettlementEntity != null) {
            settlementMethodEntity = settlementMethodGetLogic.execute(orderSettlementEntity.getSettlementMethodSeq());
            if (settlementMethodEntity == null) {
                throwMessage(MSGCD_SETTLEMENTMETHODENTITYDTO_NULL);
            }
        }

        // 受注メモの取得
        OrderMemoEntity orderMemoEntity = null;
        if (orderIndexEntity.getOrderMemoVersionNo() != null) {
            orderMemoEntity = orderMemoGetLogic.execute(orderSeq, orderIndexEntity.getOrderMemoVersionNo());
        }

        CouponEntity couponEntity = getCouponEntity(orderIndexEntity);

        // 受注DTOの作成
        ReceiveOrderForHistoryDto receiveOrderDto = getComponent(ReceiveOrderForHistoryDto.class);
        receiveOrderDto.setOrderIndexEntity(orderIndexEntity);
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
        if (orderSettlementEntity != null) {
            receiveOrderDto.setOriginalCommission(orderSettlementEntity.getSettlementCommission());
        }
        receiveOrderDto.setOrderAdditionalChargeEntityList(orderAdditionalChargeEntityList);
        receiveOrderDto.setOrderBillEntity(orderBillEntity);
        receiveOrderDto.setOrderReceiptOfMoneyEntityList(orderReceiptOfMoneyEntityList);
        receiveOrderDto.setMulPayBillEntity(mulPayBillEntity);
        receiveOrderDto.setSettlementMethodEntity(settlementMethodEntity);
        receiveOrderDto.setOrderMemoEntity(orderMemoEntity);
        receiveOrderDto.setCoupon(couponEntity);

        return receiveOrderDto;
    }

    /**
     * クーポン情報を取得<br/>
     *
     * @param orderIndexEntity 受注インデックス
     * @return クーポン
     */
    protected CouponEntity getCouponEntity(OrderIndexEntity orderIndexEntity) {
        // クーポンは現在のクーポンではなく、受注時のクーポン情報を取得する
        Integer couponSeq = orderIndexEntity.getCouponSeq();

        if (couponSeq == null) {
            // 受注時にクーポンが利用されていないので処理を終了
            return null;
        }
        // 受注時に利用されたクーポンを取得してセットする
        // 受注時のクーポンSEQとクーポン履歴番号からクーポン情報を取得している
        Integer couponVersionNo = orderIndexEntity.getCouponVersionNo();
        CouponEntity couponEntity = couponDao.getCouponByCouponVersionNo(couponSeq, couponVersionNo);

        return couponEntity;
    }

}
