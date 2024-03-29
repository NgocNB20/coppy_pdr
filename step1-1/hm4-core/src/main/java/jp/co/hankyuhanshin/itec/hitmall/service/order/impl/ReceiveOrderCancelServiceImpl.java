/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import com.gmo_pg.g_pay.client.output.AlterTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCouponLimitTargetType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGmoReleaseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipedGoodsStockReturn;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRollBackShipmentUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRollbackReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditAlterTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderCancelService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 受注キャンセルサービス
 *
 * @author hirata
 * @author tomo (itec) 2011/08/30 #2717 GMO側に取引データが存在しない場合の対応
 * @author hakogi(itec) 2012/02/14 チケット #2815対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 * @author STS Nakamura 2020/04/09 GMO経由AmazonPay GMO連携解除対応
 */
@Service
public class ReceiveOrderCancelServiceImpl extends AbstractShopService implements ReceiveOrderCancelService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveOrderCancelServiceImpl.class);

    /**
     * 追加料金相殺項目名
     */
    public static final String ADDITIONALCHARGE_CANCEL_NAME = "＊＊キャンセル精算＊＊";

    /**
     * 受注サマリ情報取得ロジック
     */
    private final OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic;

    /**
     * 受注インデックス取得ロジック
     */
    private final OrderIndexGetLogic orderIndexGetLogic;

    /**
     * 受注配送取得ロジック
     */
    private final OrderDeliveryGetLogic orderDeliveryGetLogic;

    /**
     * 受注決済取得ロジック
     */
    private final OrderSettlementGetLogic orderSettlementGetLogic;

    /**
     * 受注商品リスト取得ロジック
     */
    private final OrderGoodsListGetLogic orderGoodsListGetLogic;

    /**
     * 受注請求取得ロジック
     */
    private final OrderBillGetLogic orderBillGetLogic;

    /**
     * 受注メモ取得ロジック
     */
    private final OrderMemoGetLogic orderMemoGetLogic;

    /**
     * 受注追加料金取得ロジック
     */
    private final OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic;

    /**
     * 受注決済登録ロジック
     */
    private final OrderSettlementRegistLogic orderSettlementRegistLogic;

    /**
     * 受注商品登録ロジック
     */
    private final OrderGoodsRegistLogic orderGoodsRegistLogic;

    /**
     * 受注入金登録ロジック
     */
    private final OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic;

    /**
     * 受注請求登録ロジック
     */
    private final OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注メモ登録ロジック
     */
    private final OrderMemoRegistLogic orderMemoRegistLogic;

    /**
     * 受注追加料金登録ロジック
     */
    private final OrderAdditionalChargeRegistLogic orderAdditionalChargeRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    private final OrderIndexRegistLogic orderIndexRegistLogic;

    /**
     * 受注サマリー更新ロジック
     */
    private final OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    /**
     * 在庫戻しロジック
     */
    private final StockRollbackReserveUpdateLogic stockRollbackReserveUpdateLogic;

    /**
     * 在庫戻しロジック(出荷済み)
     */
    private final StockRollBackShipmentUpdateLogic stockRollBackShipmentUpdateLogic;

    /**
     * マルチペイ請求情報取得ロジック
     */
    private final MulPayBillLogic mulPayBillLogic;

    /**
     * クレジット通信（変更）処理ロジック
     */
    private final CreditAlterTranLogic creditAlterTranLogic;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    @Autowired
    public ReceiveOrderCancelServiceImpl(OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic,
                                         OrderIndexGetLogic orderIndexGetLogic,
                                         OrderDeliveryGetLogic orderDeliveryGetLogic,
                                         OrderSettlementGetLogic orderSettlementGetLogic,
                                         OrderGoodsListGetLogic orderGoodsListGetLogic,
                                         OrderBillGetLogic orderBillGetLogic,
                                         OrderMemoGetLogic orderMemoGetLogic,
                                         OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic,
                                         OrderSettlementRegistLogic orderSettlementRegistLogic,
                                         OrderGoodsRegistLogic orderGoodsRegistLogic,
                                         OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic,
                                         OrderBillRegistLogic orderBillRegistLogic,
                                         OrderMemoRegistLogic orderMemoRegistLogic,
                                         OrderAdditionalChargeRegistLogic orderAdditionalChargeRegistLogic,
                                         OrderIndexRegistLogic orderIndexRegistLogic,
                                         OrderSummaryUpdateLogic orderSummaryUpdateLogic,
                                         StockRollbackReserveUpdateLogic stockRollbackReserveUpdateLogic,
                                         StockRollBackShipmentUpdateLogic stockRollBackShipmentUpdateLogic,
                                         MulPayBillLogic mulPayBillLogic,
                                         CreditAlterTranLogic creditAlterTranLogic,
                                         OrderUtility orderUtility) {

        this.orderSummaryGetForUpdateLogic = orderSummaryGetForUpdateLogic;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderDeliveryGetLogic = orderDeliveryGetLogic;
        this.orderSettlementGetLogic = orderSettlementGetLogic;
        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
        this.orderBillGetLogic = orderBillGetLogic;
        this.orderMemoGetLogic = orderMemoGetLogic;
        this.orderAdditionalChargeListGetLogic = orderAdditionalChargeListGetLogic;
        this.orderSettlementRegistLogic = orderSettlementRegistLogic;
        this.orderGoodsRegistLogic = orderGoodsRegistLogic;
        this.orderReceiptOfMoneyRegistLogic = orderReceiptOfMoneyRegistLogic;
        this.orderBillRegistLogic = orderBillRegistLogic;
        this.orderMemoRegistLogic = orderMemoRegistLogic;
        this.orderAdditionalChargeRegistLogic = orderAdditionalChargeRegistLogic;
        this.orderIndexRegistLogic = orderIndexRegistLogic;
        this.orderSummaryUpdateLogic = orderSummaryUpdateLogic;
        this.stockRollbackReserveUpdateLogic = stockRollbackReserveUpdateLogic;
        this.stockRollBackShipmentUpdateLogic = stockRollBackShipmentUpdateLogic;
        this.mulPayBillLogic = mulPayBillLogic;
        this.creditAlterTranLogic = creditAlterTranLogic;
        this.orderUtility = orderUtility;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode      受注番号
     * @param orderVersionNo 受注履歴連番
     * @return チェックメッセージDTO（エラー時のみ）
     */
    @Override
    public CheckMessageDto execute(String orderCode, Integer orderVersionNo, String administratorName) {
        return this.execute(orderCode, orderVersionNo, null, null, administratorName);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode             受注番号
     * @param orderVersionNo        受注履歴連番
     * @param memo                  管理者メモ
     * @param couponLimitTargetType クーポン利用制限対象種別
     * @return チェックメッセージDTO（エラー時のみ）
     */
    @Override
    public CheckMessageDto execute(String orderCode,
                                   Integer orderVersionNo,
                                   String memo,
                                   HTypeCouponLimitTargetType couponLimitTargetType,
                                   String administratorName) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);
        ArgumentCheckUtil.assertGreaterThanZero("orderVersionNo", orderVersionNo);

        // 共通情報チェック
        Integer shopSeq = 1001;

        CheckMessageDto checkMessageDto = null;
        try {
            checkMessageDto =
                            cancel(orderCode, orderVersionNo, shopSeq, memo, couponLimitTargetType, administratorName);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            if (e instanceof AppLevelListException) {
                throw (AppLevelListException) e;
            } else {
                this.printErrorLog(e);
            }
            this.throwMessage(MSGCD_ORDERCANCEL_FAIL, null, e);
        }

        if (this.hasErrorMessage()) {
            if (checkMessageDto != null) {
                this.addErrorMessage(checkMessageDto.getMessageId(), new Object[] {checkMessageDto.getMessage()});
            }
            this.throwMessage();
        }

        return checkMessageDto;
    }

    /**
     * キャンセル処理実行<br/>
     *
     * @param orderCode             受注番号（受注コード）
     * @param orderVersionNo        受注履歴連番
     * @param shopSeq               ショップSEQ
     * @param memo                  メモ
     * @param couponLimitTargetType クーポン利用制限対象種別
     * @return チェックメッセージDTO（エラー時のみ）
     */
    protected CheckMessageDto cancel(String orderCode,
                                     Integer orderVersionNo,
                                     Integer shopSeq,
                                     String memo,
                                     HTypeCouponLimitTargetType couponLimitTargetType,
                                     String administratorName) {

        // 受注サマリー情報存在チェック
        OrderSummaryEntity orderSummaryEntity =
                        orderSummaryGetForUpdateLogic.execute(orderCode, orderVersionNo, shopSeq);
        if (orderSummaryEntity == null) {
            this.throwMessage(MSGCD_ORDERSUMMARYENTITYDTO_NULL);
        }

        Integer orderSeq = orderSummaryEntity.getOrderSeq();

        CheckMessageDto checkMessageDto = null;

        // 受注インデックス取得
        OrderIndexEntity orderIndexEntity =
                        orderIndexGetLogic.execute(orderSeq, orderSummaryEntity.getOrderVersionNo());

        Integer orderSettlementVersionNo = orderIndexEntity.getOrderSettlementVersionNo();
        Integer orderBillVersionNo = orderIndexEntity.getOrderBillVersionNo();

        orderIndexEntity.setCouponLimitTargetType(couponLimitTargetType);

        // 受注決済取得
        OrderSettlementEntity orderSettlementEntity =
                        orderSettlementGetLogic.execute(orderSeq, orderSettlementVersionNo);

        // 受注請求を取得
        OrderBillEntity orderBillEntity = orderBillGetLogic.execute(orderSeq, orderBillVersionNo);

        // 受注履歴連番更新
        Integer newOrderVersionNo = orderVersionNo + 1;

        // クレジットの場合は取引取消実行オーソリを実行する
        MulPayBillEntity mulPayBillEntity = null;
        if (HTypeSettlementMethodType.CREDIT.equals(orderSettlementEntity.getSettlementMethodType())
            && orderSummaryEntity.getOrderPrice().compareTo(BigDecimal.ZERO) > 0) {
            // キャンセル前のマルチペイメント請求を取得（クレジットの場合のみ）
            mulPayBillEntity = communicateByCredit(orderSeq, orderVersionNo, orderBillEntity.getGmoReleaseFlag());
        }

        try {
            updateReceiveOrderData(orderSummaryEntity, orderIndexEntity, orderSettlementEntity, orderBillEntity,
                                   mulPayBillEntity, newOrderVersionNo, memo, checkMessageDto, administratorName
                                  );
        } catch (AppLevelListException ale) {
            LOGGER.error("例外処理が発生しました", ale);
            this.addErrorMessage(ale);
        }
        return checkMessageDto;
    }

    /**
     * クレジット決済の取引取消を実行
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @param gmoReleaseFlag GMO連携解除フラグ
     * @return マルチペイメント請求エンティティ
     */
    protected MulPayBillEntity communicateByCredit(Integer orderSeq,
                                                   Integer orderVersionNo,
                                                   HTypeGmoReleaseFlag gmoReleaseFlag) {
        if (HTypeGmoReleaseFlag.RELEASE == gmoReleaseFlag) {
            return null;
        }
        // 返金情報登録チェックにて使用
        MulPayBillEntity mulPayBillEntity = mulPayBillLogic.getMulPayBillByOrderSeq(orderSeq);
        // 受注履歴連番を更新
        mulPayBillEntity.setOrderVersionNo(orderVersionNo + 1);
        // 取引取消実行オーソリの実行
        AlterTranOutput result = creditAlterTranLogic.doCancelTran(mulPayBillEntity);
        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);
        // GMOが返却するエラーは画面に表示し、処理は続行する
        communicateUtility.setGmoCancelFailureMessage(result);
        return mulPayBillEntity;
    }

    /**
     * 受注系各情報をキャンセル状態へ変更した内容を登録する<br/>
     *
     * @param orderSummaryEntity    受注サマリー
     * @param orderIndexEntity      受注インデックス
     * @param orderSettlementEntity 受注決済
     * @param orderBillEntity       受注請求
     * @param mulPayBillEntity      マルチペイメント請求
     * @param newOrderVersionNo     受注履歴連番（最新）
     * @param memo                  メモ
     * @param checkMessageDto       チェックメッセージDTO
     */
    protected void updateReceiveOrderData(OrderSummaryEntity orderSummaryEntity,
                                          OrderIndexEntity orderIndexEntity,
                                          OrderSettlementEntity orderSettlementEntity,
                                          OrderBillEntity orderBillEntity,
                                          MulPayBillEntity mulPayBillEntity,
                                          Integer newOrderVersionNo,
                                          String memo,
                                          CheckMessageDto checkMessageDto,
                                          String administratorName) {

        Integer orderSeq = orderSummaryEntity.getOrderSeq();
        Integer orderGoodsVersionNo = orderIndexEntity.getOrderGoodsVersionNo();
        Integer orderDeliveryVersionNo = orderIndexEntity.getOrderDeliveryVersionNo();
        Integer orderAdditionalChargeVersionNo = orderIndexEntity.getOrderAdditionalChargeVersionNo();

        // 受注商品リスト取得
        List<OrderGoodsEntity> orderGoodsList = orderGoodsListGetLogic.execute(orderSeq, orderGoodsVersionNo);

        // 受注配送リスト取得
        List<OrderDeliveryEntity> orderDeliveryEntityList =
                        orderDeliveryGetLogic.execute(orderSeq, orderDeliveryVersionNo);

        // 受注追加料金リスト取得
        List<OrderAdditionalChargeEntity> addChergeList = null;
        if (orderAdditionalChargeVersionNo != null) {
            addChergeList = orderAdditionalChargeListGetLogic.execute(orderSeq, orderAdditionalChargeVersionNo);
        }

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp processTime = dateUtility.getCurrentTime();
        // 受注決済を登録
        if (registOrderSettlement(newOrderVersionNo, processTime, orderSettlementEntity) > 0) {
            orderIndexEntity.setOrderSettlementVersionNo(orderSettlementEntity.getOrderSettlementVersionNo());
        }

        // 利用ポイント返還及び獲得ポイントを無効化

        // 受注情報を生成
        ReceiveOrderDto receiveOrderDto = ApplicationContextUtility.getBean(ReceiveOrderDto.class);
        // 受注サマリを設定
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        // 受注インデックスを設定
        receiveOrderDto.setOrderIndexEntity(orderIndexEntity);
        // 受注決済を設定
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);

        // 獲得ポイントを設定
        // 受注キャンセル時は、ポイントを付与する対象金額がない為、獲得ポイントは0になる
        receiveOrderDto.setAcquisitionPoint(BigDecimal.ZERO);

        // キャンセルフラグを設定
        // 受注DTOに設定した受注サマリのキャンセルフラグをONとする
        // ポイントの有効期限日制御に利用される
        OrderSummaryEntity orderSummary = receiveOrderDto.getOrderSummaryEntity();
        orderSummary.setCancelFlag(HTypeCancelFlag.ON);

        // 受注商品リストを登録
        if (registOrderGoods(newOrderVersionNo, processTime, orderGoodsList) > 0) {
            orderIndexEntity.setOrderGoodsVersionNo(orderGoodsVersionNo + 1);
        }

        // 受注追加料金リストを登録
        if (orderAdditionalChargeVersionNo != null) {
            orderAdditionalChargeVersionNo = orderAdditionalChargeVersionNo + 1;
            this.registOrderAdditionalCharge(
                            orderSeq, newOrderVersionNo, orderAdditionalChargeVersionNo, processTime, addChergeList);
            orderIndexEntity.setOrderAdditionalChargeVersionNo(orderAdditionalChargeVersionNo);
        }

        // 受注入金を登録処理チェック
        HTypeShipmentStatus shipmentStatus =
                        orderUtility.getShipmentStatusByOrderStatus(orderSummaryEntity.getOrderStatus());
        if (checkRegistorOrderReceiptOfMoney(orderSeq, orderSummaryEntity, checkMessageDto, orderSettlementEntity,
                                             shipmentStatus, orderBillEntity, mulPayBillEntity
                                            )) {

            BigDecimal orderPrice = getOrderPrice(orderIndexEntity, orderIndexEntity.getOrderSettlementVersionNo(),
                                                  orderBillEntity, orderSummaryEntity.getOrderPrice(),
                                                  orderSettlementEntity
                                                 );

            // 受注サマリー.入金累計金額設定
            orderSummaryEntity.setReceiptPriceTotal(orderSummaryEntity.getReceiptPriceTotal().subtract(orderPrice));

            Integer orderReceiptOfMoneyVersionNo = orderIndexEntity.getOrderReceiptOfMoneyVersionNo();
            if (orderReceiptOfMoneyVersionNo == null) {
                orderReceiptOfMoneyVersionNo = 0;
            }
            // 新受注入金連番
            orderReceiptOfMoneyVersionNo = orderReceiptOfMoneyVersionNo + 1;
            // 受注入金を登録
            if (registOrderReceiptOfMoney(orderSummaryEntity, newOrderVersionNo, orderReceiptOfMoneyVersionNo,
                                          orderPrice, processTime
                                         ) > 0) {
                orderIndexEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
            }
        }

        // 受注請求を登録
        if (registOrderBill(newOrderVersionNo, checkMessageDto, orderBillEntity, processTime) > 0) {
            orderIndexEntity.setOrderBillVersionNo(orderBillEntity.getOrderBillVersionNo());
        }

        // 受注メモを登録
        registOrderMemo(orderIndexEntity, newOrderVersionNo, memo, orderSummaryEntity.getOrderCode());

        // 受注インデックスを登録
        registOrderIndex(orderIndexEntity, newOrderVersionNo, processTime, administratorName);

        // 受注サマリーを更新
        if (updateOrderSummary(orderSummaryEntity, newOrderVersionNo, processTime) == 0) {
            throwMessage(MSGCD_ORDERCANCEL_FAIL);
        }

        // 在庫更新
        for (OrderDeliveryEntity orderDeliveryEntity : orderDeliveryEntityList) {

            if (HTypeShipmentStatus.UNSHIPMENT.equals(orderDeliveryEntity.getShipmentStatus())) {
                // 未出荷の場合、注文確保の在庫戻し処理実行
                stockRollbackReserveUpdateLogic.execute(
                                orderSeq, orderGoodsVersionNo, orderDeliveryEntity.getOrderConsecutiveNo());
            } else {
                // 出荷済みの場合、実在庫の在庫戻し処理実行
                String returnFlag = PropertiesUtil.getSystemPropertiesValue("shipedGoodsStockReturn");
                if (HTypeShipedGoodsStockReturn.ON.getValue().equals(returnFlag)) {
                    stockRollBackShipmentUpdateLogic.execute(
                                    orderSeq, orderGoodsVersionNo + 1, orderDeliveryEntity.getOrderConsecutiveNo());
                }
            }
        }
    }

    /**
     * 受注決済登録処理<br/>
     *
     * @param orderVersionNo        受注履歴連番
     * @param processTime           処理日時
     * @param orderSettlementEntity 受注決済
     * @return 処理件数
     */
    protected int registOrderSettlement(Integer orderVersionNo,
                                        Timestamp processTime,
                                        OrderSettlementEntity orderSettlementEntity) {
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        orderSettlementEntity.setOrderSettlementVersionNo(orderSettlementEntity.getOrderSettlementVersionNo() + 1);
        orderSettlementEntity.setProcessTime(processTime);
        orderSettlementEntity.setProcessYm(dateUtility.formatYm(processTime));
        orderSettlementEntity.setProcessYmd(dateUtility.formatYmd(processTime));
        orderSettlementEntity.setTotalingType(HTypeTotalingType.CANCELLATION);

        // 前回金額を設定
        orderSettlementEntity.setPreCarriage(orderSettlementEntity.getCarriage());
        orderSettlementEntity.setPreGoodsCostTotal(orderSettlementEntity.getGoodsCostTotal());
        orderSettlementEntity.setPreGoodsPriceTotal(orderSettlementEntity.getGoodsPriceTotal());
        orderSettlementEntity.setPreOthersPriceTotal(orderSettlementEntity.getOthersPriceTotal());
        orderSettlementEntity.setPreSettlementCommission(orderSettlementEntity.getSettlementCommission());
        orderSettlementEntity.setPreCouponDiscountPrice(orderSettlementEntity.getCouponDiscountPrice());
        orderSettlementEntity.setPreUsePoint(orderSettlementEntity.getUsePoint());
        orderSettlementEntity.setPreTaxPrice(orderSettlementEntity.getTaxPrice());
        orderSettlementEntity.setPreOrderPrice(orderSettlementEntity.getOrderPrice());
        orderSettlementEntity.setPreBeforeDiscountOrderPrice(orderSettlementEntity.getBeforeDiscountOrderPrice());

        // 金額を0で初期化
        orderSettlementEntity.setCarriage(BigDecimal.ZERO);
        orderSettlementEntity.setGoodsCostTotal(BigDecimal.ZERO);
        orderSettlementEntity.setGoodsPriceTotal(BigDecimal.ZERO);
        orderSettlementEntity.setOthersPriceTotal(BigDecimal.ZERO);
        orderSettlementEntity.setSettlementCommission(BigDecimal.ZERO);
        orderSettlementEntity.setCouponDiscountPrice(BigDecimal.ZERO);
        orderSettlementEntity.setUsePoint(BigDecimal.ZERO);
        orderSettlementEntity.setTaxPrice(BigDecimal.ZERO);
        orderSettlementEntity.setOrderPrice(BigDecimal.ZERO);
        orderSettlementEntity.setBeforeDiscountOrderPrice(BigDecimal.ZERO);

        orderSettlementEntity.setStandardTaxTargetPrice(BigDecimal.ZERO);
        orderSettlementEntity.setStandardTaxPrice(BigDecimal.ZERO);
        orderSettlementEntity.setReducedTaxTargetPrice(BigDecimal.ZERO);
        orderSettlementEntity.setReducedTaxPrice(BigDecimal.ZERO);

        return orderSettlementRegistLogic.execute(orderSettlementEntity);
    }

    /**
     * 受注商品登録処理<br/>
     *
     * @param orderVersionNo 受注履歴連番
     * @param processTime    処理日時
     * @param orderGoodsList 受注商品リスト
     * @return 処理件数
     */
    protected int registOrderGoods(Integer orderVersionNo,
                                   Timestamp processTime,
                                   List<OrderGoodsEntity> orderGoodsList) {
        int count = 0;

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsList) {
            orderGoodsEntity.setOrderGoodsVersionNo(orderGoodsEntity.getOrderGoodsVersionNo() + 1);
            orderGoodsEntity.setTotalingType(HTypeTotalingType.CANCELLATION);
            orderGoodsEntity.setProcessTime(processTime);
            orderGoodsEntity.setPreGoodsCount(orderGoodsEntity.getGoodsCount());
            orderGoodsEntity.setGoodsCount(BigDecimal.ZERO);
            count += orderGoodsRegistLogic.execute(orderGoodsEntity);
        }

        return count;
    }

    /**
     * 受注追加料金登録処理
     *
     * @param orderSeq                       受注SEQ
     * @param orderVersionNo                 受注履歴連番
     * @param orderAdditionalChargeVersionNo 受注追加料金連番
     * @param processTime                    処理日時
     * @param addChergeList                  受注追加料金リスト
     * @return 処理件数
     */
    protected int registOrderAdditionalCharge(Integer orderSeq,
                                              Integer orderVersionNo,
                                              Integer orderAdditionalChargeVersionNo,
                                              Timestamp processTime,
                                              List<OrderAdditionalChargeEntity> addChergeList) {
        int count = 0;

        if (addChergeList == null) {
            return count;
        }

        BigDecimal price = BigDecimal.ZERO;
        for (OrderAdditionalChargeEntity additionalCharge : addChergeList) {
            additionalCharge.setOrderAdditionalChargeVersionNo(orderAdditionalChargeVersionNo);
            additionalCharge.setRegistTime(processTime);
            additionalCharge.setUpdateTime(processTime);
            price = price.add(additionalCharge.getAdditionalDetailsPrice());
            count += orderAdditionalChargeRegistLogic.execute(additionalCharge);
        }

        // 「**キャンセル精算**」登録
        if (!BigDecimal.ZERO.equals(price)) {
            OrderAdditionalChargeEntity additionalCharge =
                            ApplicationContextUtility.getBean(OrderAdditionalChargeEntity.class);
            additionalCharge.setAdditionalDetailsName(ADDITIONALCHARGE_CANCEL_NAME);
            additionalCharge.setAdditionalDetailsPrice(price.negate());
            additionalCharge.setOrderAdditionalChargeVersionNo(orderAdditionalChargeVersionNo);
            additionalCharge.setOrderDisplay(addChergeList.size() + 1);
            additionalCharge.setOrderSeq(orderSeq);
            additionalCharge.setRegistTime(processTime);
            additionalCharge.setUpdateTime(processTime);
            count += orderAdditionalChargeRegistLogic.execute(additionalCharge);
        }
        return count;
    }

    /**
     * 受注請求登録処理<br/>
     *
     * @param orderVersionNo  受注履歴連番
     * @param checkMessageDto チェックメッセージDTO
     * @param orderBillEntity 受注請求
     * @param processTime     処理日時
     * @return 処理件数
     */
    protected int registOrderBill(Integer orderVersionNo,
                                  CheckMessageDto checkMessageDto,
                                  OrderBillEntity orderBillEntity,
                                  Timestamp processTime) {
        orderBillEntity.setOrderBillVersionNo(orderBillEntity.getOrderBillVersionNo() + 1);
        orderBillEntity.setBillPrice(orderBillEntity.getBillPrice().negate());
        orderBillEntity.setSettlementCommission(orderBillEntity.getSettlementCommission().negate());
        // キャンセルしたものについてはオーソリ通信でエラーが発生していても、請求決済エラーは設定しない。
        orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.OFF);
        orderBillEntity.setRegistTime(processTime);
        orderBillEntity.setUpdateTime(processTime);
        return orderBillRegistLogic.execute(orderBillEntity);
    }

    /**
     * 受注入金登録処理<br/>
     *
     * @param orderSummaryEntity           受注サマリー
     * @param orderVersionNo               受注履歴連番
     * @param orderReceiptOfMoneyVersionNo 受注入金連番
     * @param orderPrice                   受注金額
     * @param processTime                  処理日時
     * @return 処理件数
     */
    protected int registOrderReceiptOfMoney(OrderSummaryEntity orderSummaryEntity,
                                            Integer orderVersionNo,
                                            Integer orderReceiptOfMoneyVersionNo,
                                            BigDecimal orderPrice,
                                            Timestamp processTime) {
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
        orderReceiptOfMoneyEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        orderReceiptOfMoneyEntity.setOrderSiteType(orderSummaryEntity.getOrderSiteType());
        orderReceiptOfMoneyEntity.setOrderTime(orderSummaryEntity.getOrderTime());
        orderReceiptOfMoneyEntity.setReceiptMethodSeq(null);
        orderReceiptOfMoneyEntity.setReceiptPrice(orderPrice.negate());
        orderReceiptOfMoneyEntity.setReceiptPriceTotal(orderSummaryEntity.getReceiptPriceTotal());
        orderReceiptOfMoneyEntity.setReceiptTime(processTime);
        orderReceiptOfMoneyEntity.setReceiptYm(dateUtility.formatYm(processTime));
        orderReceiptOfMoneyEntity.setReceiptYmd(dateUtility.formatYmd(processTime));
        orderReceiptOfMoneyEntity.setSalesTime(orderSummaryEntity.getSalesTime());
        orderReceiptOfMoneyEntity.setSettlementMethodSeq(orderSummaryEntity.getSettlementMethodSeq());
        orderReceiptOfMoneyEntity.setShopSeq(orderSummaryEntity.getShopSeq());

        return orderReceiptOfMoneyRegistLogic.execute(orderReceiptOfMoneyEntity);
    }

    /**
     * 受注メモ登録処理<br/>
     *
     * @param orderIndexEntity 受注インデックス
     * @param orderVersionNo   受注履歴連番
     * @param memo             メモ
     * @param orderCode        受注番号。受注インデックスと受注メモ間に不整合がある場合にログに出力する。
     * @return 処理件数
     */
    protected int registOrderMemo(OrderIndexEntity orderIndexEntity,
                                  Integer orderVersionNo,
                                  String memo,
                                  String orderCode) {
        OrderMemoEntity orderMemoEntity = null;
        // 受注 Index テーブルより、受注メモの受注メモ連番を取得
        Integer orderMemoVersionNo = orderIndexEntity.getOrderMemoVersionNo();

        /*
         * 受注メモの状態は以下の３パターン ・受注メモの登録あり ・受注メモの登録なし ・受注メモの不整合
         *
         * 受注メモは前回と今回の入力内容に違いがある場合のみ登録を行えばよいので、 受注メモの作成を行うのは以下の３パターンのみ
         * １．受注メモの登録あり-変更あり ２．受注メモの登録なし-変更あり（入力あり） ３．受注メモ不整合-変更あり（入力あり）
         *
         * 以下のパターンでは受注メモの編集は不要 ４．受注メモの登録あり-変更なし ５．受注メモの登録なし-変更なし（入力なし）
         * ６．受注メモ不整合-変更なし（入力なし）
         */
        if (orderMemoVersionNo != null) {
            // 受注メモが登録されていた時
            // 該当の受注メモテーブルを取得
            orderMemoEntity = orderMemoGetLogic.execute(orderIndexEntity.getOrderSeq(), orderMemoVersionNo);

            if (orderMemoEntity != null) {
                /*
                 * 受注メモが登録されている場合
                 */
                // 入力された値と保持されている値が異なっているかチェックする
                List<String> diff = DiffUtil.diff(orderMemoEntity.getMemo(), memo);
                if (diff.isEmpty()) {
                    // ４．受注メモの登録あり-変更なし
                    // メモの更新は不要なので処理を終了する。
                    return 0;
                } else {
                    // １．受注メモの登録あり-変更あり
                    // メモに変更が発生しているので、メモを登録する。
                    orderMemoVersionNo++;
                }
            } else {
                /*
                 * 受注メモに不整合が発生している場合
                 */
                // 受注インデックスが更新されているが受注メモが作成されていない場合でも、
                // 受注キャンセルを行えるようにするため、受注メモを1進めてDBへ登録する。
                // 理由：不整合を検出した場合でもキャンセル処理を継続したいため。
                // 不整合の発生をシステム管理者が検知できるよう、エラーログの出力を行う。
                // ただし、受注メモが作成されたにも関わらず、受注インデックスが更新されていない場合には対応できない。
                // -> 未使用の受注メモ連番が不明であるため。
                LOGGER.error(String.format("受注番号:%sの受注に不整合が発生しています。受注メモなし。", orderCode));

                // execute(String orderCode, Integer orderVersionNo)経由で実行される場合、
                // memo が null となる。
                if (memo == null || memo.isEmpty()) {
                    // ６．受注メモ不整合-変更なし（入力なし）
                    // メモの入力がないので処理を終了する
                    return 0;
                } else {
                    // ３．受注メモ不整合-変更あり（入力あり）
                    // メモ連番を1つ進めて登録する。
                    // 受注インデックスが正しく、受注メモが登録されてい場合にはキャンセル処理が完了する。
                    // 受注インデックスが正しくない場合は、受注メモの登録時にプライマリーキー重複エラーが発生する。
                    orderMemoVersionNo++;
                }
            }
        } else {
            /*
             * 受注メモが登録されていない場合
             */
            if (memo == null || memo.isEmpty()) {
                // ５．受注メモの登録なし-変更なし（入力なし）
                // メモの入力がないので処理を終了する
                return 0;
            } else {
                // ２．受注メモの登録なし-変更あり（入力あり）
                // 新たに受注メモを作成するので連番は 1
                orderMemoVersionNo = 1;
            }
        }

        // 受注メモの登録
        orderMemoEntity = ApplicationContextUtility.getBean(OrderMemoEntity.class);
        orderMemoEntity.setOrderSeq(orderIndexEntity.getOrderSeq());
        orderMemoEntity.setMemo(memo);
        orderMemoEntity.setOrderMemoVersionNo(orderMemoVersionNo);
        orderIndexEntity.setOrderMemoVersionNo(orderMemoVersionNo);

        return orderMemoRegistLogic.execute(orderMemoEntity);
    }

    /**
     * 受注インデックス登録処理<br/>
     *
     * @param orderIndexEntity 受注インデックス
     * @param orderVersionNo   受注履歴連番
     * @param processTime      処理日時
     * @return 処理件数
     */
    protected int registOrderIndex(OrderIndexEntity orderIndexEntity,
                                   Integer orderVersionNo,
                                   Timestamp processTime,
                                   String administratorName) {
        orderIndexEntity.setProcessPersonName(administratorName);
        orderIndexEntity.setProcessTime(processTime);
        orderIndexEntity.setProcessType(HTypeProcessType.CANCELLATION);
        orderIndexEntity.setOrderVersionNo(orderVersionNo);
        return orderIndexRegistLogic.execute(orderIndexEntity);
    }

    /**
     * 受注サマリー更新処理<br/>
     *
     * @param orderSummaryEntity 受注サマリー
     * @param orderVersionNo     受注履歴連番
     * @param cancelTime         受注キャンセル日時
     * @return 処理件数
     */
    protected int updateOrderSummary(OrderSummaryEntity orderSummaryEntity,
                                     Integer orderVersionNo,
                                     Timestamp cancelTime) {
        orderSummaryEntity.setOrderPrice(BigDecimal.ZERO);
        orderSummaryEntity.setBeforeDiscountOrderPrice(BigDecimal.ZERO);
        orderSummaryEntity.setOrderVersionNo(orderVersionNo);
        orderSummaryEntity.setCancelFlag(HTypeCancelFlag.ON);
        orderSummaryEntity.setCancelTime(cancelTime);

        return orderSummaryUpdateLogic.execute(orderSummaryEntity);
    }

    /**
     * 返金情報登録チェック<br/>
     * <br/>
     * ①クレジット決済且つ（前請求又は（後請求且つ出荷済み））の場合<br/>
     * ②異常フラグ=正常<br/>
     * ③キャンセル前処理区分=売上・実売上<br/>
     * ④取消オーソリ実行後の処理区分=取消・返品・月跨返品<br/>
     * 上記条件の場合、返金情報を登録が必要となる
     *
     * @param orderSeq              受注SEQ
     * @param orderSummaryEntity    受注サマリ
     * @param checkMessageDto       チェックメッセージDTO
     * @param orderSettlementEntity 受注決済
     * @param shipmentStatus        出荷状態
     * @param orderBillEntity       受注請求
     * @param mulPayBillEntity      マルチペイメント請求
     * @return true = 登録必要 、false = 登録不要
     */
    protected boolean checkRegistorOrderReceiptOfMoney(Integer orderSeq,
                                                       OrderSummaryEntity orderSummaryEntity,
                                                       CheckMessageDto checkMessageDto,
                                                       OrderSettlementEntity orderSettlementEntity,
                                                       HTypeShipmentStatus shipmentStatus,
                                                       OrderBillEntity orderBillEntity,
                                                       MulPayBillEntity mulPayBillEntity) {

        HTypeBillType billType = orderSettlementEntity.getBillType();
        HTypeSettlementMethodType preSettlementType = orderSettlementEntity.getSettlementMethodType();
        HTypeEmergencyFlag emergencyFlag = orderBillEntity.getEmergencyFlag();

        // オーソリエラーなし 且つ 決済種別 = クレジット
        if (checkMessageDto == null && HTypeSettlementMethodType.CREDIT.equals(preSettlementType)
            // 且つ ( 請求種別 = 前払い 又は (請求種別 = 後払い 且つ 出荷状態 = 出荷済み)) の場合
            && (HTypeBillType.PRE_CLAIM.equals(billType) || (HTypeBillType.POST_CLAIM.equals(billType)
                                                             && HTypeShipmentStatus.SHIPPED.equals(shipmentStatus)))
            && orderSummaryEntity.getOrderPrice().compareTo(BigDecimal.ZERO) > 0) {

            MulPayBillEntity tmpMulPayBillEntity = mulPayBillLogic.getMulPayBillByOrderSeq(orderSeq);

            // 請求異常フラグ = 異常なし 且つ GMO連携解除フラグ = 通常
            if (HTypeEmergencyFlag.OFF == emergencyFlag
                && HTypeGmoReleaseFlag.NORMAL == orderBillEntity.getGmoReleaseFlag()) {
                HTypeJobCode preJobCode =
                                EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, mulPayBillEntity.getJobCd());
                HTypeJobCode lastJobCode =
                                EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, tmpMulPayBillEntity.getJobCd());
                // 処理種別 = 実売上 又は 処理種別 = 売上
                if (HTypeJobCode.CAPTURE == preJobCode || HTypeJobCode.SALES == preJobCode) {
                    if (HTypeJobCode.VOID == lastJobCode || HTypeJobCode.RETURN == lastJobCode
                        || HTypeJobCode.RETURNX == lastJobCode) {
                        return true;
                    }
                }
            }
        }
        if (checkMessageDto == null && HTypeSettlementMethodType.AMAZON_PAYMENT == preSettlementType
            // 且つ ( 請求種別 = 前払い 又は (請求種別 = 後払い 且つ 出荷状態 = 出荷済み)) の場合
            && (HTypeBillType.PRE_CLAIM.equals(billType) || (HTypeBillType.POST_CLAIM.equals(billType)
                                                             && HTypeShipmentStatus.SHIPPED.equals(shipmentStatus)))
            && orderSummaryEntity.getOrderPrice().compareTo(BigDecimal.ZERO) > 0) {
            // 出荷登録が行われていれば、請求決済エラーの有無、GMO連携解除にかかわらず入金情報が登録されている
            return true;
        }
        return false;
    }

    /**
     * 修正エラー前受注金額取得
     *
     * @param orderIndexEntity         受注インデックス
     * @param orderSettlementVersionNo 受注決済
     * @param orderBillEntity          受注請求
     * @param orderPrice               受注金額
     * @param orderSettlementEntity    受注決済
     * @return 修正エラー前受注金額
     */
    protected BigDecimal getOrderPrice(OrderIndexEntity orderIndexEntity,
                                       Integer orderSettlementVersionNo,
                                       OrderBillEntity orderBillEntity,
                                       BigDecimal orderPrice,
                                       OrderSettlementEntity orderSettlementEntity) {

        HTypeEmergencyFlag emergencyFlag = orderBillEntity.getEmergencyFlag();
        // 請求異常フラグ = 異常なし
        if (HTypeEmergencyFlag.OFF == emergencyFlag) {
            return orderPrice;
        }

        if (HTypeSettlementMethodType.AMAZON_PAYMENT == orderSettlementEntity.getSettlementMethodType()
            && HTypeEmergencyFlag.ON == emergencyFlag) {
            return orderPrice;
        }

        OrderSettlementEntity preErrOrderSettlementEntity =
                        orderSettlementGetLogic.execute(orderIndexEntity.getOrderSeq(), orderSettlementVersionNo - 2);
        BigDecimal carriage = preErrOrderSettlementEntity.getCarriage();
        BigDecimal goodsPriceTotal = preErrOrderSettlementEntity.getGoodsPriceTotal();
        BigDecimal othersPriceTotal = preErrOrderSettlementEntity.getOthersPriceTotal();
        BigDecimal settlementCommission = preErrOrderSettlementEntity.getSettlementCommission();
        // 修正エラー前受注金額
        BigDecimal preErrOrderPrice = goodsPriceTotal.add(carriage).add(othersPriceTotal).add(settlementCommission);
        return preErrOrderPrice;
    }

    /**
     * エラー内容をログに出力
     *
     * @param e Exception
     */
    protected void printErrorLog(Exception e) {
        LOGGER.error(e.getMessage());
    }

}
