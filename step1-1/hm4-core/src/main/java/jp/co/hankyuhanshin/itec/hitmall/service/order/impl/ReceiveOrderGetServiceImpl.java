/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.PointUseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryDtoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.TaxGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsDetailsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 受注取得サービス
 *
 * @author ueshima
 * @author hakogi(itec) 2012/01/26 チケット #2732 対応
 */
@Service
public class ReceiveOrderGetServiceImpl extends AbstractShopService implements ReceiveOrderGetService {

    /**
     * 受注サマリ情報取得ロジック<br/>
     */
    private final OrderSummaryGetLogic orderSummaryGetLogic;

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
    private final OrderDeliveryDtoGetLogic orderDeliveryDtoGetLogic;

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
     * 受注メモ取得ロジック
     */
    private final OrderMemoGetLogic orderMemoGetLogic;

    /**
     * クーポンDAO
     */
    private final CouponDao couponDao;

    /**
     * 商品詳細リスト取得サービス
     */
    private final GoodsDetailsListGetService goodsDetailsListGetService;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 税率取得ロジック
     */
    private final TaxGetLogic taxGetLogic;

    @Autowired
    public ReceiveOrderGetServiceImpl(OrderSummaryGetLogic orderSummaryGetLogic,
                                      OrderIndexGetLogic orderIndexGetLogic,
                                      OrderPersonGetLogic orderPersonGetLogic,
                                      OrderDeliveryDtoGetLogic orderDeliveryDtoGetLogic,
                                      OrderSettlementGetLogic orderSettlementGetLogic,
                                      OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic,
                                      OrderBillGetLogic orderBillGetLogic,
                                      OrderReceiptOfMoneyListGetLogic orderReceiptOfMoneyListGetLogic,
                                      MulPayBillDao mulPayBillDao,
                                      SettlementMethodGetLogic settlementMethodGetLogic,
                                      OrderMemoGetLogic orderMemoGetLogic,
                                      CouponDao couponDao,
                                      GoodsDetailsListGetService goodsDetailsListGetService,
                                      OrderUtility orderUtility,
                                      TaxGetLogic taxGetLogic) {

        this.orderSummaryGetLogic = orderSummaryGetLogic;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderPersonGetLogic = orderPersonGetLogic;
        this.orderDeliveryDtoGetLogic = orderDeliveryDtoGetLogic;
        this.orderSettlementGetLogic = orderSettlementGetLogic;
        this.orderAdditionalChargeListGetLogic = orderAdditionalChargeListGetLogic;
        this.orderBillGetLogic = orderBillGetLogic;
        this.orderReceiptOfMoneyListGetLogic = orderReceiptOfMoneyListGetLogic;
        this.mulPayBillDao = mulPayBillDao;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.orderMemoGetLogic = orderMemoGetLogic;
        this.couponDao = couponDao;
        this.goodsDetailsListGetService = goodsDetailsListGetService;
        this.orderUtility = orderUtility;
        this.taxGetLogic = taxGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode 受注コード
     * @return 受注詳細DTO
     */
    @Override
    public ReceiveOrderDto execute(String orderCode) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);

        if (orderCode.length() > 12) {
            this.throwMessage(MSGCD_ORDERSUMMARYENTITYDTO_NULL);
        }

        // 共通情報チェック
        Integer shopSeq = 1001;

        // 受注サマリー情報存在チェック
        OrderSummaryEntity orderSummaryEntity = orderSummaryGetLogic.execute(orderCode);
        if (orderSummaryEntity == null) {
            this.throwMessage(MSGCD_ORDERSUMMARYENTITYDTO_NULL);
        }

        // 詳細情報取得
        ReceiveOrderDto receiveOrderDto = this.getReceiveOrderDto(orderSummaryEntity);
        if (receiveOrderDto == null) {
            return null;
        }
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);

        // 以下判定メソッドの条件に一致する場合にオーソリ期限日（決済日付＋オーソリ保持期間）を取得する
        Timestamp authoryLimitDate = null;
        if (judgeGetAuthoryLimit(receiveOrderDto)) {
            // オーソリ保持期間（日）
            String authoryHoldPeriod = PropertiesUtil.getSystemPropertiesValue("authory.hold.period");
            // オーソリ期限日（決済日付＋オーソリ保持期間）の取得
            authoryLimitDate = mulPayBillDao.getAuthoryLimitDate(authoryHoldPeriod, orderSummaryEntity.getOrderSeq());
        }
        receiveOrderDto.setAuthoryLimitDate(authoryLimitDate);

        receiveOrderDto.setActivatePoint(BigDecimal.ZERO);

        // 受注修正時はポイント利用方法を一部利用固定とする
        receiveOrderDto.setPointUseType(PointUseType.PART);
        receiveOrderDto.setUsePoint(receiveOrderDto.getOrderSettlementEntity().getUsePoint());

        return receiveOrderDto;
    }

    /**
     * オーソリ期限日を取得するか否かを判定する<br />
     * <pre>
     * 以下条件に一致する場合取得対象
     * ①受注状態：キャンセル以外
     * ②決済方法：クレジット
     * ③請求種別：後請求
     * ④出荷状態：未出荷
     * ⑤請求決済エラーが発生していない
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     * @return true:取得対象
     */
    protected boolean judgeGetAuthoryLimit(ReceiveOrderDto receiveOrderDto) {

        HTypeShipmentStatus shipmentStatus = orderUtility.getShipmentStatusByOrderStatus(
                        receiveOrderDto.getOrderSummaryEntity().getOrderStatus());

        return HTypeCancelFlag.OFF == receiveOrderDto.getOrderSummaryEntity().getCancelFlag()
               && HTypeSettlementMethodType.CREDIT == receiveOrderDto.getOrderSettlementEntity()
                                                                     .getSettlementMethodType()
               && HTypeBillType.POST_CLAIM == receiveOrderDto.getOrderSettlementEntity().getBillType()
               && HTypeShipmentStatus.UNSHIPMENT == shipmentStatus
               && HTypeEmergencyFlag.OFF == receiveOrderDto.getOrderBillEntity().getEmergencyFlag();
    }

    /**
     * 実行メソッド
     *
     * @param orderCode      受注コード
     * @param orderVersionNo 受注履歴番号
     * @return 受注Dto
     */
    @Override
    public ReceiveOrderDto execute(String orderCode, Integer orderVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);
        ArgumentCheckUtil.assertNotNull("orderVersionNo", orderVersionNo);

        OrderSummaryEntity orderSummaryEntity = orderSummaryGetLogic.execute(orderCode);
        if (orderSummaryEntity == null) {
            this.throwMessage(MSGCD_ORDERSUMMARYENTITYDTO_NULL);
        }
        ReceiveOrderDto receiveOrderDto = getReceiveOrderDto(orderSummaryEntity, orderVersionNo);
        if (receiveOrderDto == null) {
            return null;
        }
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        return receiveOrderDto;
    }

    /**
     * 実行メソッド
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return 受注Dto
     */
    @Override
    public ReceiveOrderDto execute(Integer orderSeq, Integer orderVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderVersionNo", orderVersionNo);

        OrderSummaryEntity orderSummaryEntity = orderSummaryGetLogic.execute(orderSeq);
        ReceiveOrderDto receiveOrderDto = getReceiveOrderDto(orderSummaryEntity);
        if (receiveOrderDto == null) {
            return null;
        }
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        return receiveOrderDto;
    }

    @Override
    public ReceiveOrderDto execute(Integer orderSeq) {
        ArgumentCheckUtil.assertGreaterThanZero("orderSeq", orderSeq);
        OrderSummaryEntity orderSummary = orderSummaryGetLogic.execute(orderSeq);
        return execute(orderSummary.getOrderSeq(), orderSummary.getOrderVersionNo());
    }

    /**
     * 実行メソッド
     *
     * @param orderSummaryEntity 受注サマリ
     * @return 受注Dto
     */
    protected ReceiveOrderDto getReceiveOrderDto(OrderSummaryEntity orderSummaryEntity) {
        Integer orderVersionNo = orderSummaryEntity.getOrderVersionNo();
        return getReceiveOrderDto(orderSummaryEntity, orderVersionNo);
    }

    /**
     * 実行メソッド
     *
     * @param orderSummaryEntity 受注サマリ
     * @param orderVersionNo     オーダーバージョンナンバー
     * @return 受注Dto
     */
    protected ReceiveOrderDto getReceiveOrderDto(OrderSummaryEntity orderSummaryEntity, Integer orderVersionNo) {

        Integer orderSeq = orderSummaryEntity.getOrderSeq();

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

        // セール情報、商品情報をマスターに格納
        OrderInfoMasterDto orderInfoMasterDto = getSaleGoodsMaster(orderDeliveryDto);

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
        SettlementMethodEntity settlementMethodEntity =
                        settlementMethodGetLogic.execute(orderSettlementEntity.getSettlementMethodSeq());
        if (settlementMethodEntity == null) {
            throwMessage(MSGCD_SETTLEMENTMETHODENTITYDTO_NULL);
        }

        // 受注メモの取得
        OrderMemoEntity orderMemoEntity = null;
        if (orderIndexEntity.getOrderMemoVersionNo() != null) {
            orderMemoEntity = orderMemoGetLogic.execute(orderSeq, orderIndexEntity.getOrderMemoVersionNo());
        }

        CouponEntity couponEntity = getCouponEntity(orderIndexEntity);

        // 受注DTOの作成
        ReceiveOrderDto receiveOrderDto = ApplicationContextUtility.getBean(ReceiveOrderDto.class);
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.setOrderIndexEntity(orderIndexEntity);
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
        receiveOrderDto.setOriginalCommission(orderSettlementEntity.getSettlementCommission());
        receiveOrderDto.setOriginalCarriage(orderSettlementEntity.getCarriage());
        receiveOrderDto.setOrderAdditionalChargeEntityList(orderAdditionalChargeEntityList);
        receiveOrderDto.setOrderBillEntity(orderBillEntity);
        receiveOrderDto.setOrderReceiptOfMoneyEntityList(orderReceiptOfMoneyEntityList);
        receiveOrderDto.setMulPayBillEntity(mulPayBillEntity);
        receiveOrderDto.setSettlementMethodEntity(settlementMethodEntity);
        receiveOrderDto.setOrderMemoEntity(orderMemoEntity);
        receiveOrderDto.setCoupon(couponEntity);
        receiveOrderDto.setMasterDto(orderInfoMasterDto);
        receiveOrderDto.getMasterDto()
                       .setTaxRateMaster(taxGetLogic.getEffectiveTaxRateMapByTaxSeq(orderSummaryEntity.getTaxSeq()));

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

    /**
     * 受注商品にセールSEQがあればセールマスターとしてデータを取得します<br/>
     *
     * @param orderDeliveryDto 受注配送Dto
     * @return OrderInfoMasterDto
     */
    protected OrderInfoMasterDto getSaleGoodsMaster(OrderDeliveryDto orderDeliveryDto) {
        OrderInfoMasterDto orderInfoMasterDto = ApplicationContextUtility.getBean(OrderInfoMasterDto.class);

        List<Integer> goodsSeqList = new ArrayList<>();

        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {
            goodsSeqList.add(orderGoodsEntity.getGoodsSeq());
        }

        // 商品情報の取得
        List<GoodsDetailsDto> goodsList = goodsDetailsListGetService.execute(goodsSeqList);
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = new HashMap<>();
        for (GoodsDetailsDto goodsDetailsDto : goodsList) {
            goodsDetailsDtoMap.put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);
        }
        orderInfoMasterDto.setGoodsMaster(goodsDetailsDtoMap);

        return orderInfoMasterDto;
    }
}
