/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import com.gmo_pg.g_pay.client.output.BaseOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockShipmentUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.SettlememtMismatchCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditAlterTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReservationGoodsShipmentCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentRegistService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 出荷登録処理<br/>
 *
 * @author yamaguchi
 * @author tomo (itec) 2011/08/29 #2717 GMO側に取引データが存在しない場合の対応
 * @author hakogi(itec) 2012/02/14 チケット #2815対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class ShipmentRegistServiceImpl extends AbstractShopService implements ShipmentRegistService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentRegistServiceImpl.class);

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * 請求情報の不整合チェックLogic
     */
    private final SettlememtMismatchCheckLogic settlememtMismatchCheckLogic;

    /**
     * 通信関連Utility
     */
    private final CommunicateUtility communicateUtility;

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
     * 決済方法取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * クレジット変更通信ロジック
     */
    private final CreditAlterTranLogic creditAlterTranLogic;

    /**
     * 受注配送登録ロジック
     */
    private final OrderDeliveryRegistLogic orderDeliveryRegistLogic;

    /**
     * 受注商品登録ロジック
     */
    private final OrderGoodsRegistLogic orderGoodsRegistLogic;

    /**
     * 受注決済登録ロジック
     */
    private final OrderSettlementRegistLogic orderSettlementRegistLogic;

    /**
     * 受注請求登録ロジック
     */
    private final OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注入金登録ロジック
     */
    private final OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    private final OrderIndexRegistLogic orderIndexRegistLogic;

    /**
     * 受注サマリー更新ロジック
     */
    private final OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    /**
     * 在庫出荷更新ロジック
     */
    private final StockShipmentUpdateLogic stockShipmentUpdateLogic;

    /**
     * マルチペイメント情報取得ロジック
     */
    private final MulPayBillLogic mulPayBillLogic;

    /**
     * 受注サマリー情報取得ロジック（排他制御あり
     */
    private final OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic;

    /**
     * 受注予約商品出荷チェックロジック
     */
    private final OrderReservationGoodsShipmentCheckLogic orderReservationGoodsShipmentCheckLogic;

    @Autowired
    public ShipmentRegistServiceImpl(DateUtility dateUtility,
                                     SettlememtMismatchCheckLogic settlememtMismatchCheckLogic,
                                     CommunicateUtility communicateUtility,
                                     OrderIndexGetLogic orderIndexGetLogic,
                                     OrderDeliveryGetLogic orderDeliveryGetLogic,
                                     OrderSettlementGetLogic orderSettlementGetLogic,
                                     OrderGoodsListGetLogic orderGoodsListGetLogic,
                                     OrderBillGetLogic orderBillGetLogic,
                                     SettlementMethodGetLogic settlementMethodGetLogic,
                                     CreditAlterTranLogic creditAlterTranLogic,
                                     OrderDeliveryRegistLogic orderDeliveryRegistLogic,
                                     OrderGoodsRegistLogic orderGoodsRegistLogic,
                                     OrderSettlementRegistLogic orderSettlementRegistLogic,
                                     OrderBillRegistLogic orderBillRegistLogic,
                                     OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic,
                                     OrderIndexRegistLogic orderIndexRegistLogic,
                                     OrderSummaryUpdateLogic orderSummaryUpdateLogic,
                                     StockShipmentUpdateLogic stockShipmentUpdateLogic,
                                     MulPayBillLogic mulPayBillLogic,
                                     OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic,
                                     OrderReservationGoodsShipmentCheckLogic orderReservationGoodsShipmentCheckLogic) {

        this.dateUtility = dateUtility;
        this.settlememtMismatchCheckLogic = settlememtMismatchCheckLogic;
        this.communicateUtility = communicateUtility;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderDeliveryGetLogic = orderDeliveryGetLogic;
        this.orderSettlementGetLogic = orderSettlementGetLogic;
        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
        this.orderBillGetLogic = orderBillGetLogic;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.creditAlterTranLogic = creditAlterTranLogic;
        this.orderDeliveryRegistLogic = orderDeliveryRegistLogic;
        this.orderGoodsRegistLogic = orderGoodsRegistLogic;
        this.orderSettlementRegistLogic = orderSettlementRegistLogic;
        this.orderBillRegistLogic = orderBillRegistLogic;
        this.orderReceiptOfMoneyRegistLogic = orderReceiptOfMoneyRegistLogic;
        this.orderIndexRegistLogic = orderIndexRegistLogic;
        this.orderSummaryUpdateLogic = orderSummaryUpdateLogic;
        this.stockShipmentUpdateLogic = stockShipmentUpdateLogic;
        this.mulPayBillLogic = mulPayBillLogic;
        this.orderSummaryGetForUpdateLogic = orderSummaryGetForUpdateLogic;
        this.orderReservationGoodsShipmentCheckLogic = orderReservationGoodsShipmentCheckLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param shipmentRegistDto 出荷登録DTO
     * @return 処理結果メッセージ（オーソリエラー発生時のみ）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public CheckMessageDto execute(ShipmentRegistDto shipmentRegistDto, String administratorName) {
        // ショップSEQを取得
        Integer shopSeq = 1001;

        return execute(shipmentRegistDto, shopSeq, administratorName);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param shipmentRegistDto 出荷登録DTO
     * @param shopSeq           ショップSEQ
     * @return 処理結果メッセージ（オーソリエラー発生時のみ）
     */
    protected CheckMessageDto execute(ShipmentRegistDto shipmentRegistDto, Integer shopSeq, String administratorName) {

        // 出荷日が設定されていない場合、カレント日時を設定
        setShipmentDate(shipmentRegistDto);

        // 受注サマリー
        OrderSummaryEntity orderSummaryEntity = getOrderSummaryEntity(shipmentRegistDto, shopSeq);
        // 元々の受注サマリが持つ受注履歴連番
        // 後の受注インデックス取得処理の中で受注サマリの持つ受注履歴連番のインクリメントがされるので、ここで退避しておく
        Integer preOrderVersionNo = orderSummaryEntity.getOrderVersionNo();

        // 受注SEQ
        Integer orderSeq = orderSummaryEntity.getOrderSeq();
        // 受注番号
        String orderCode = orderSummaryEntity.getOrderCode();
        // 出荷登録日
        Timestamp shipmentDate = shipmentRegistDto.getShipmentDate();

        // 販売前商品存在チェック
        // 予約注文の商品が販売開始となっているかを確認
        if (!orderReservationGoodsShipmentCheckLogic.execute(orderSeq, shipmentRegistDto)) {
            throwMessage(MSGCD_SHIPMENT_DATE_BEFORE_SALE_END,
                         new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }

        /**************************/
        /** 出荷登録情報取得処理 **/
        /**************************/
        // 受注インデックス取得
        OrderIndexEntity orderIndexEntity = getOrderIndexEntity(orderSummaryEntity, shipmentRegistDto);

        // 受注内の配送リストを取得
        List<OrderDeliveryEntity> orderDeliveryEntityList =
                        getOrderDeliveryEntityList(orderSeq, orderIndexEntity, orderCode, shipmentRegistDto);

        // 出荷種別 1：1回目の出荷登録（一部出荷） 0：受注内で、最後の出荷登録（すべて出荷済）
        // 出荷の状態により、出荷種別を取得
        ShipmentType shipmentType = getShipmentType(orderDeliveryEntityList, shipmentRegistDto);

        // 受注サマリに売上情報を設定
        setShipmentInfoToOrderSummaryEntity(orderSummaryEntity, shipmentRegistDto, shipmentType);

        // 受注商品情報を取得
        List<OrderGoodsEntity> orderGoodsEntityList =
                        getOrderGoodsEntityList(orderSeq, orderIndexEntity, orderCode, shipmentRegistDto, shipmentType);

        // すべて出荷済になっていれば、売上フラグを売上に設定
        OrderSettlementEntity orderSettlementEntity = null;
        OrderBillEntity orderBillEntity = null;
        SettlementMethodEntity settlementMethodEntity = null;

        // 受注決済を取得
        orderSettlementEntity =
                        getOrderSettlementEntity(orderSeq, orderIndexEntity, orderCode, shipmentDate, shipmentRegistDto,
                                                 shipmentType
                                                );

        // 決済方法情報を取得
        settlementMethodEntity = settlementMethodGetLogic.execute(orderSettlementEntity.getSettlementMethodSeq());
        if (settlementMethodEntity == null) {
            throwMessage(MSGCD_SETTLEMENTMETHOD_NULL,
                         new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }

        // 受注請求を取得
        orderBillEntity = getOrderBillEntity(orderSummaryEntity, orderIndexEntity, orderSettlementEntity,
                                             settlementMethodEntity, shipmentRegistDto, shipmentType
                                            );

        if (ShipmentType.SHIPMENT_COMPLETION == shipmentType) {
            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.initShipmentRegist(orderCode);
        }

        try {

            CheckMessageDto result = null;
            // すべて出荷済になっていれば、売上フラグを売上を計上する
            if (ShipmentType.SHIPMENT_COMPLETION == shipmentType) {

                // 通信処理
                result = communicateBySettlementType(orderSeq, preOrderVersionNo, orderIndexEntity, orderBillEntity,
                                                     orderCode, orderSettlementEntity.getSettlementMethodType()
                                                    );
            }

            // 登録・更新処理
            registShipment(result, orderSummaryEntity, orderIndexEntity, orderDeliveryEntityList, orderSettlementEntity,
                           orderGoodsEntityList, settlementMethodEntity, orderBillEntity, shipmentType,
                           shipmentRegistDto, administratorName
                          );

            if (this.hasErrorMessage()) {
                this.throwMessage();
            }

            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute();
            return result;

        } catch (Throwable th) {
            LOGGER.error("例外処理が発生しました", th);
            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute(th);
            throw th;
        }
    }

    /**
     * 決済方法に応じて売上計上処理を行う
     *
     * @param orderSeq             受注シーケンス
     * @param preOrderVersionNo    受注履歴連番
     * @param orderIndexEntity     受注インデックスエンティティ
     * @param orderBillEntity      受注請求エンティティ
     * @param orderCode            受注番号（受注コード）
     * @param settlementMethodType 決済方法
     * @return CheckMessageDto
     */
    protected CheckMessageDto communicateBySettlementType(Integer orderSeq,
                                                          Integer preOrderVersionNo,
                                                          OrderIndexEntity orderIndexEntity,
                                                          OrderBillEntity orderBillEntity,
                                                          String orderCode,
                                                          HTypeSettlementMethodType settlementMethodType) {

        // クレジット決済とAmazonPay決済以外の場合は以下の処理を行わない。
        if (HTypeSettlementMethodType.CREDIT != settlementMethodType
            && HTypeSettlementMethodType.AMAZON_PAYMENT != settlementMethodType) {
            return null;
        }
        MulPayBillEntity mulPayBillEntity =
                        mulPayBillLogic.getMulPayBillByOrderSeqAndOrderVersionNo(orderSeq, preOrderVersionNo);

        if (mulPayBillEntity == null) {
            throwMessage(MSGCD_MULPAYBILL_NULL, new Object[] {orderCode});
        }

        HTypeJobCode jobCd = EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, mulPayBillEntity.getJobCd());
        // 後請求（現状=仮売上）の場合、売上計上を行う
        if (HTypeJobCode.AUTH == jobCd) {
            mulPayBillEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo());
            List<CheckMessageDto> list = new ArrayList<>();
            // クレジット決済の売上計上
            if (HTypeSettlementMethodType.CREDIT == settlementMethodType) {
                list = communicateByCredit(mulPayBillEntity, orderCode);
            }
            if (CollectionUtil.isNotEmpty(list)) {
                orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.ON);
                // 決済エラーの内容を戻り値に設定
                return list.get(0);
            }
        } else if (HTypeJobCode.CAPTURE != jobCd && HTypeJobCode.SALES != jobCd) {
            // 処理区分がSALES：実売上とCAPTURE：即時売上の場合は売上計上を行わないが、
            // その他の場合は、想定していない為、以降の処理を行わない。
            throwMessage(MSGCD_AUTHORI_JOBCD_ERROR, new Object[] {orderCode});
        }
        return null;

    }

    /**
     * クレジット決済の売上計上を実行
     *
     * @param mulPayBillEntity マルチペイメント請求
     * @param orderCode        受注番号
     * @return CheckMessageDto
     */
    protected List<CheckMessageDto> communicateByCredit(MulPayBillEntity mulPayBillEntity, String orderCode) {
        // 売上計上
        BaseOutput output = null;
        try {
            output = creditAlterTranLogic.doSalesFixTran(mulPayBillEntity);
        } catch (Exception e) {
            // マルチペイメント通信中に例外が発生した場合は、以降の処理を行わない。
            // 本来は、actionやバッチでそれぞれ例外を判定してメッセージを付与すべきだが、
            // 既存ソースコードを考慮してメッセージの付け替えを行う
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_MULPAY_ERROR, new Object[] {orderCode}, e);
        }

        // オーソリ通信で決済エラーが帰ってきた場合
        if (output.isErrorOccurred()) {
            // トランザクション情報がないか、180日以上経過していた場合
            if (communicateUtility.isOutdatedTran(output)) {
                throwMessage(MSGCD_INVALID_TRAN_ID_WARNING, new Object[] {orderCode});
            }
            // 仮売上の有効期限が切れていた場合
            if (communicateUtility.isAuthExpired(output)) {
                throwMessage(MSGCD_AUTH_EXPIRED_WARNING, new Object[] {orderCode});
            }
        }

        // 決済エラーの内容を戻り値に設定
        return communicateUtility.checkOutput(output);
    }

    /**
     * 登録・更新処理を行います<br/>
     * <pre>
     * ・受注配送を登録
     * ・受注決済を登録
     * ・受注商品リストを登録
     * ・受注請求を登録
     * ・受注入金を登録
     * ・受注インデックスを登録
     * ・受注サマリを更新
     * ・在庫情報を更新
     * </pre>
     *
     * @param result                  チェックメッセージDTO
     * @param orderSummaryEntity      受注サマリエンティティ
     * @param orderIndexEntity        受注インデックスエンティティ
     * @param orderDeliveryEntityList 受注配送エンティティリスト
     * @param orderSettlementEntity   受注決済エンティティ
     * @param orderGoodsEntityList    受注商品エンティティリスト
     * @param settlementMethodEntity  決済方法エンティティ
     * @param orderBillEntity         受注請求エンティティ
     * @param shipmentType            出荷種別
     * @param shipmentRegistDto       出荷登録Dto
     */
    protected void registShipment(CheckMessageDto result,
                                  OrderSummaryEntity orderSummaryEntity,
                                  OrderIndexEntity orderIndexEntity,
                                  List<OrderDeliveryEntity> orderDeliveryEntityList,
                                  OrderSettlementEntity orderSettlementEntity,
                                  List<OrderGoodsEntity> orderGoodsEntityList,
                                  SettlementMethodEntity settlementMethodEntity,
                                  OrderBillEntity orderBillEntity,
                                  ShipmentType shipmentType,
                                  ShipmentRegistDto shipmentRegistDto,
                                  String administratorName) {

        // 受注番号
        String orderCode = orderSummaryEntity.getOrderCode();

        try {
            /**************/
            /** 登録処理 **/
            /**************/
            // 受注配送リストを登録
            registOrderDeliveryList(orderIndexEntity, orderDeliveryEntityList, shipmentRegistDto);

            // 受注商品リストを登録
            int goodsseqcount = registOrderGoods(orderGoodsEntityList, shipmentType, shipmentRegistDto);

            // 全て出荷済みの場合のみ登録
            if (ShipmentType.SHIPMENT_COMPLETION == shipmentType) {
                // 受注決済を登録
                registOrderSettlement(orderSettlementEntity);
                // 受注請求を登録
                registOrderBill(orderBillEntity);
                // 請求種別=後請求（orderBillEntityに値が設定されている場合は後請求として判断）且つ異常フラグ=正常である場合（クレジット・AmazonPay・代金引換時は入金処理を行う）
                if (orderBillEntity != null && orderBillEntity.getEmergencyFlag() == HTypeEmergencyFlag.OFF) {
                    // 受注入金を登録
                    registOrderReceiptOfMoney(orderSummaryEntity, orderIndexEntity, settlementMethodEntity);
                }
            }
            // 受注インデックスを登録
            registOrderIndex(orderIndexEntity, shipmentType, administratorName);

            /**************/
            /** 更新処理 **/
            /**************/
            // 受注サマリを更新
            updateOrderSummary(orderSummaryEntity);

            // 在庫情報を更新
            updateStockShipment(orderIndexEntity, orderCode, goodsseqcount, shipmentType, shipmentRegistDto);

        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            this.addErrorMessage(MSGCD_DB_INSERT_FATAL,
                                 new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()}, e
                                );
            if (result != null) {
                this.addErrorMessage(result.getMessageId(), result.getArgs(), e);
            }
        }
    }

    /**
     * 受注サマリーを取得<br/>
     *
     * @param shipmentRegistDto 出荷登録DTO
     * @param shopSeq           ショップSEQ
     * @return 受注サマリー
     */
    protected OrderSummaryEntity getOrderSummaryEntity(ShipmentRegistDto shipmentRegistDto, Integer shopSeq) {
        // レコードロック処理
        OrderSummaryEntity orderSummaryEntity = null;
        try {
            orderSummaryEntity = orderSummaryGetForUpdateLogic.execute(shipmentRegistDto.getOrderCode(),
                                                                       shipmentRegistDto.getOrderVersionNo(), shopSeq
                                                                      );
        } catch (Exception e) {
            // 本来は、actionやバッチでそれぞれ例外を判定してメッセージを付与すべきだが、
            // 既存ソースコードを考慮してメッセージの付け替えを行う
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_SELECT_FORUPDATE_FATAL,
                         new Object[] {shipmentRegistDto.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}, e
                        );
        }
        if (orderSummaryEntity == null) {
            throwMessage(MSGCD_ORDERVERSIONNO_DEF,
                         new Object[] {shipmentRegistDto.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        } else if (orderSummaryEntity.getOrderStatus().equals(HTypeOrderStatus.SHIPMENT_COMPLETION)) {
            throwMessage(MSGCD_SHIPMENT_COMPLETION_ERROR,
                         new Object[] {shipmentRegistDto.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        } else if (orderSummaryEntity.getCancelFlag().equals(HTypeCancelFlag.ON)) {
            throwMessage(MSGCD_CANCEL_ERROR,
                         new Object[] {shipmentRegistDto.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }
        return orderSummaryEntity;
    }

    /**
     * 受注サマリに売上情報を設定する<br/>
     *
     * @param orderSummaryEntity 受注サマリー
     * @param shipmentRegistDto  出荷登録Dto
     * @param shipmentType       出荷種別
     */
    protected void setShipmentInfoToOrderSummaryEntity(OrderSummaryEntity orderSummaryEntity,
                                                       ShipmentRegistDto shipmentRegistDto,
                                                       ShipmentType shipmentType) {
        // 全て出荷済みの場合は売上情報を設定
        if (ShipmentType.SHIPMENT_COMPLETION == shipmentType) {
            orderSummaryEntity.setSalesTime(shipmentRegistDto.getShipmentDate());
            orderSummaryEntity.setSalesFlag(HTypeSalesFlag.ON);
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.SHIPMENT_COMPLETION);
        } else {
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.PART_SHIPMENT);
        }
    }

    /**
     * 受注インデックスを取得<br/>
     *
     * @param orderSummaryEntity 受注サマリー
     * @param shipmentRegistDto  出荷登録DTO
     * @return 受注インデックス
     */
    protected OrderIndexEntity getOrderIndexEntity(OrderSummaryEntity orderSummaryEntity,
                                                   ShipmentRegistDto shipmentRegistDto) {
        OrderIndexEntity orderIndexEntity = orderIndexGetLogic.execute(orderSummaryEntity.getOrderSeq(),
                                                                       orderSummaryEntity.getOrderVersionNo()
                                                                      );
        if (orderIndexEntity == null) {
            throwMessage(MSGCD_ORDERINDEX_NULL,
                         new Object[] {orderSummaryEntity.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }
        // 受注履歴連番をカウントアップする
        Integer orderVersionNo = orderSummaryEntity.getOrderVersionNo() + 1;

        orderSummaryEntity.setOrderVersionNo(orderVersionNo);
        orderIndexEntity.setOrderVersionNo(orderVersionNo);

        return orderIndexEntity;
    }

    /**
     * 受注配送リストを取得<br/>
     *
     * @param orderSeq          ショップSEQ
     * @param orderIndexEntity  受注インデックス
     * @param orderCode         受注番号（受注コード）
     * @param shipmentRegistDto 出荷登録DTO
     * @return 受注配送
     */
    protected List<OrderDeliveryEntity> getOrderDeliveryEntityList(Integer orderSeq,
                                                                   OrderIndexEntity orderIndexEntity,
                                                                   String orderCode,
                                                                   ShipmentRegistDto shipmentRegistDto) {
        List<OrderDeliveryEntity> orderDeliveryEntityList =
                        orderDeliveryGetLogic.getOrderDeliveryListForUpdate(orderSeq,
                                                                            orderIndexEntity.getOrderDeliveryVersionNo()
                                                                           );

        if (orderDeliveryEntityList == null || orderDeliveryEntityList.isEmpty()) {
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()});
        }

        // 注文連番をチェック
        boolean matchedFlg = false;
        for (OrderDeliveryEntity orderDeliveryEntity : orderDeliveryEntityList) {
            if (orderDeliveryEntity.getOrderConsecutiveNo().compareTo(shipmentRegistDto.getOrderConsecutiveNo()) == 0) {
                matchedFlg = true;
            }
        }

        // 受注配送リスト内に一致する注文連番が存在しない場合はエラーとする
        if (!matchedFlg) {
            throwMessage(MSGCD_ORDERINDEX_NULL,
                         new Object[] {shipmentRegistDto.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }

        return orderDeliveryEntityList;
    }

    /**
     * 受注決済を取得<br/>
     *
     * @param orderSeq          ショップSEQ
     * @param orderIndexEntity  受注インデックス
     * @param orderCode         受注番号（受注コード）
     * @param shipmentDate      出荷日（出荷日時）
     * @param shipmentRegistDto 出荷登録Dto
     * @param shipmentType      出荷登録の処理種別
     * @return 受注決済
     */
    protected OrderSettlementEntity getOrderSettlementEntity(Integer orderSeq,
                                                             OrderIndexEntity orderIndexEntity,
                                                             String orderCode,
                                                             Timestamp shipmentDate,
                                                             ShipmentRegistDto shipmentRegistDto,
                                                             ShipmentType shipmentType) {
        OrderSettlementEntity orderSettlementEntity =
                        orderSettlementGetLogic.execute(orderSeq, orderIndexEntity.getOrderSettlementVersionNo());
        if (orderSettlementEntity == null) {
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()});
        }

        if (ShipmentType.SHIPMENT_COMPLETION != shipmentType) {
            return orderSettlementEntity;
        }

        // 受注決済連番をカウントアップ
        Integer orderSettlementVersionNo = orderIndexEntity.getOrderSettlementVersionNo() + 1;

        // 更新内容をセット
        // 受注インデックスに
        orderIndexEntity.setOrderSettlementVersionNo(orderSettlementVersionNo);
        // 受注決済に
        orderSettlementEntity.setOrderSettlementVersionNo(orderSettlementVersionNo);
        orderSettlementEntity.setProcessTime(shipmentDate);
        orderSettlementEntity.setProcessYm(dateUtility.formatYm(shipmentDate));
        orderSettlementEntity.setProcessYmd(dateUtility.formatYmd(shipmentDate));
        orderSettlementEntity.setSalesTime(shipmentDate);
        orderSettlementEntity.setTotalingType(HTypeTotalingType.SALES);
        orderSettlementEntity.setSalesFlag(HTypeSalesFlag.ON);
        orderSettlementEntity.setPreCarriage(BigDecimal.ZERO);
        orderSettlementEntity.setPreGoodsCostTotal(BigDecimal.ZERO);
        orderSettlementEntity.setPreGoodsPriceTotal(BigDecimal.ZERO);
        orderSettlementEntity.setPreOthersPriceTotal(BigDecimal.ZERO);
        orderSettlementEntity.setPreSettlementCommission(BigDecimal.ZERO);
        orderSettlementEntity.setPreTaxPrice(BigDecimal.ZERO);
        orderSettlementEntity.setPreOrderPrice(BigDecimal.ZERO);
        orderSettlementEntity.setPreBeforeDiscountOrderPrice(BigDecimal.ZERO);
        return orderSettlementEntity;
    }

    /**
     * 受注商品リストを取得<br/>
     *
     * @param orderSeq          ショップSEQ
     * @param orderIndexEntity  受注インデックス
     * @param orderCode         受注番号（受注コード）
     * @param shipmentRegistDto 出荷登録Dto
     * @param shipmentType      出荷種別
     * @return 受注商品リスト
     */
    protected List<OrderGoodsEntity> getOrderGoodsEntityList(Integer orderSeq,
                                                             OrderIndexEntity orderIndexEntity,
                                                             String orderCode,
                                                             ShipmentRegistDto shipmentRegistDto,
                                                             ShipmentType shipmentType) {
        List<OrderGoodsEntity> orderGoodsEntityList =
                        orderGoodsListGetLogic.execute(orderSeq, orderIndexEntity.getOrderGoodsVersionNo());
        if (orderGoodsEntityList == null || orderGoodsEntityList.size() == 0) {
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()});
        }

        // 出荷完了の場合のみ、集計種別を売上に変更する為、登録する
        if (ShipmentType.SHIPMENT_COMPLETION != shipmentType) {
            return orderGoodsEntityList;
        }

        Timestamp shipmentDate = shipmentRegistDto.getShipmentDate();

        // 受注決済連番をカウントアップ
        Integer orderGoodsVersionNo = orderIndexEntity.getOrderGoodsVersionNo() + 1;

        // 更新内容をセット
        // 受注インデックスに
        orderIndexEntity.setOrderGoodsVersionNo(orderGoodsVersionNo);
        // 受注商品に
        for (int index = 0; index < orderGoodsEntityList.size(); index++) {
            OrderGoodsEntity orderGoodsEntity = orderGoodsEntityList.get(index);
            orderGoodsEntity.setOrderGoodsVersionNo(orderGoodsVersionNo);

            // 全出荷の場合は売上情報を更新する
            orderGoodsEntity.setTotalingType(HTypeTotalingType.SALES);
            orderGoodsEntity.setSalesFlag(HTypeSalesFlag.ON);
            orderGoodsEntity.setProcessTime(shipmentDate);
            orderGoodsEntity.setPreGoodsCount(BigDecimal.ZERO);
        }
        return orderGoodsEntityList;
    }

    /**
     * 受注請求を取得<br/>
     *
     * @param orderSummaryEntity     受注サマリー
     * @param orderIndexEntity       受注インデックス
     * @param orderSettlementEntity  受注決済
     * @param settlementMethodEntity 決済方法
     * @param shipmentRegistDto      出荷登録Dto
     * @param shipmentType           出荷登録の処理種別
     * @return 受注請求
     */
    protected OrderBillEntity getOrderBillEntity(OrderSummaryEntity orderSummaryEntity,
                                                 OrderIndexEntity orderIndexEntity,
                                                 OrderSettlementEntity orderSettlementEntity,
                                                 SettlementMethodEntity settlementMethodEntity,
                                                 ShipmentRegistDto shipmentRegistDto,
                                                 ShipmentType shipmentType) {

        OrderBillEntity orderBillEntity = orderBillGetLogic.execute(orderSummaryEntity.getOrderSeq(),
                                                                    orderIndexEntity.getOrderBillVersionNo()
                                                                   );
        // 請求情報の取得失敗の場合
        if (orderBillEntity == null) {
            throwMessage(MSGCD_ORDERINDEX_NULL,
                         new Object[] {orderSummaryEntity.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }
        // 異常フラグがONの場合
        if (orderBillEntity.getEmergencyFlag().equals(HTypeEmergencyFlag.ON)) {
            throwMessage(MSGCD_EMERGENCY_ERROR,
                         new Object[] {orderSummaryEntity.getOrderCode(), shipmentRegistDto.getOrderConsecutiveNo()}
                        );
        }

        if (ShipmentType.SHIPMENT_COMPLETION != shipmentType) {
            return orderBillEntity;
        }

        // 請求タイプが後払いであるかをチェック
        if (settlementMethodEntity.getBillType().equals(HTypeBillType.POST_CLAIM)) {
            // 受注決済連番をカウントアップ
            Integer orderBillVersionNo = orderIndexEntity.getOrderBillVersionNo() + 1;

            // 更新内容をセット
            // 受注インデックスに
            orderIndexEntity.setOrderBillVersionNo(orderBillVersionNo);

            // 受注請求に
            orderBillEntity.setOrderBillVersionNo(orderBillVersionNo);
            orderBillEntity.setBillStatus(HTypeBillStatus.BILL_CLAIM);
            orderBillEntity.setBillTime(dateUtility.getCurrentTime());
            orderBillEntity.setBillPrice(orderSummaryEntity.getOrderPrice());
            orderBillEntity.setSettlementCommission(orderSettlementEntity.getSettlementCommission());
            orderBillEntity.setSettlementMethodSeq(orderSettlementEntity.getSettlementMethodSeq());
            orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.OFF);
            return orderBillEntity;
        }
        return null;
    }

    /**
     * 出荷日が設定されているかをチェック<br/>
     * 出荷日が設定されていない場合はカレント日時を設定<br/>
     *
     * @param shipmentRegistDto 出荷登録DTO
     */
    protected void setShipmentDate(ShipmentRegistDto shipmentRegistDto) {
        if (shipmentRegistDto.getShipmentDate() == null) {
            shipmentRegistDto.setShipmentDate(dateUtility.getCurrentTime());
        }
    }

    /**
     * 受注配送リストを登録<br/>
     *
     * @param orderIndexEntity        受注インデックスDTO
     * @param orderDeliveryEntityList 受注配送リスト
     * @param shipmentRegistDto       shipmentRegistDto
     */
    protected void registOrderDeliveryList(OrderIndexEntity orderIndexEntity,
                                           List<OrderDeliveryEntity> orderDeliveryEntityList,
                                           ShipmentRegistDto shipmentRegistDto) {

        // 受注配送連番をカウントアップ
        Integer orderDeliveryVersionNo = orderIndexEntity.getOrderDeliveryVersionNo() + 1;
        // 受注インデックスに
        orderIndexEntity.setOrderDeliveryVersionNo(orderDeliveryVersionNo);

        // 受注配送リスト登録する
        for (OrderDeliveryEntity orderDeliveryEntity : orderDeliveryEntityList) {
            // 更新内容をセット
            orderDeliveryEntity.setOrderDeliveryVersionNo(orderDeliveryVersionNo);
            // 選択した受注配送を出荷完了状態とする
            if (orderDeliveryEntity.getOrderConsecutiveNo().compareTo(shipmentRegistDto.getOrderConsecutiveNo()) == 0) {
                orderDeliveryEntity.setShipmentStatus(HTypeShipmentStatus.SHIPPED);
                orderDeliveryEntity.setShipmentDate(shipmentRegistDto.getShipmentDate());
                orderDeliveryEntity.setDeliveryCode(shipmentRegistDto.getDeliveryCode());
            }
            orderDeliveryRegistLogic.execute(orderDeliveryEntity);
        }
    }

    /**
     * 受注決済を登録<br/>
     *
     * @param orderSettlementEntity 受注決済エンティティ
     */
    protected void registOrderSettlement(OrderSettlementEntity orderSettlementEntity) {
        orderSettlementRegistLogic.execute(orderSettlementEntity);
    }

    /**
     * 受注商品リストを登録<br/>
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @param shipmentType         出荷種別
     * @param shipmentRegistDto    出荷登録Dto
     * @return 処理件数
     */
    protected int registOrderGoods(List<OrderGoodsEntity> orderGoodsEntityList,
                                   ShipmentType shipmentType,
                                   ShipmentRegistDto shipmentRegistDto) {
        List<Integer> goodsseqList = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {

            if (ShipmentType.SHIPMENT_COMPLETION == shipmentType) {
                // 出荷完了の場合のみ、集計種別を売上に変更する為、登録する
                orderGoodsRegistLogic.execute(orderGoodsEntity);
            }

            if (orderGoodsEntity.getGoodsCount().compareTo(BigDecimal.ZERO) > 0) {
                // 今回出荷対象の商品数量が全て0の場合は、メールを送信しない
                if (orderGoodsEntity.getOrderConsecutiveNo().compareTo(shipmentRegistDto.getOrderConsecutiveNo())
                    == 0) {
                    shipmentRegistDto.setGoodsCountNotZeroFlag(true);

                    if (!goodsseqList.contains(orderGoodsEntity.getGoodsSeq())) {
                        goodsseqList.add(orderGoodsEntity.getGoodsSeq());
                    }
                }
            }
        }
        return goodsseqList.size();
    }

    /**
     * 受注請求を登録<br/>
     *
     * @param orderBillEntity 受注請求エンティティ
     */
    protected void registOrderBill(OrderBillEntity orderBillEntity) {
        if (orderBillEntity != null) {
            orderBillRegistLogic.execute(orderBillEntity);
        }
    }

    /**
     * 受注入金を登録<br/>
     *
     * @param orderSummaryEntity     受注サマリー
     * @param orderIndexEntity       受注インデックスエンティティ
     * @param settlementMethodEntity 決済方法エンティティ
     */
    protected void registOrderReceiptOfMoney(OrderSummaryEntity orderSummaryEntity,
                                             OrderIndexEntity orderIndexEntity,
                                             SettlementMethodEntity settlementMethodEntity) {
        if (settlementMethodEntity.getBillType().equals(HTypeBillType.POST_CLAIM)
            && orderSummaryEntity.getOrderPrice().compareTo(BigDecimal.ZERO) > 0 && (
                            settlementMethodEntity.getSettlementMethodType().equals(HTypeSettlementMethodType.CREDIT)
                            || settlementMethodEntity.getSettlementMethodType()
                                                     .equals(HTypeSettlementMethodType.RECEIPT_PAYMENT)
                            || settlementMethodEntity.getSettlementMethodType()
                               == HTypeSettlementMethodType.AMAZON_PAYMENT)) {
            OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                            ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);

            // 入金日時設定
            Timestamp receiptTime = dateUtility.getCurrentTime();
            if (settlementMethodEntity.getSettlementMethodType().equals(HTypeSettlementMethodType.RECEIPT_PAYMENT)) {
                receiptTime = orderSummaryEntity.getSalesTime();
            }

            orderReceiptOfMoneyEntity.setShopSeq(orderSummaryEntity.getShopSeq());
            orderReceiptOfMoneyEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
            orderReceiptOfMoneyEntity.setOrderSiteType(orderSummaryEntity.getOrderSiteType());
            orderReceiptOfMoneyEntity.setOrderTime(orderSummaryEntity.getOrderTime());
            orderReceiptOfMoneyEntity.setSalesTime(orderSummaryEntity.getSalesTime());
            orderReceiptOfMoneyEntity.setSettlementMethodSeq(orderSummaryEntity.getSettlementMethodSeq());
            orderReceiptOfMoneyEntity.setReceiptTime(receiptTime);
            orderReceiptOfMoneyEntity.setReceiptYm(dateUtility.formatYm(receiptTime));
            orderReceiptOfMoneyEntity.setReceiptYmd(dateUtility.formatYmd(receiptTime));
            orderReceiptOfMoneyEntity.setReceiptPrice(orderSummaryEntity.getOrderPrice());
            orderReceiptOfMoneyEntity.setReceiptPriceTotal(
                            orderSummaryEntity.getReceiptPriceTotal().add(orderSummaryEntity.getOrderPrice()));
            // 既存受注入金情報ありの場合
            if (orderIndexEntity.getOrderReceiptOfMoneyVersionNo() != null) {
                orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(
                                orderIndexEntity.getOrderReceiptOfMoneyVersionNo().intValue() + 1);
            } else {
                orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(1);
            }

            // 受注入金登録処理を実行

            orderReceiptOfMoneyRegistLogic.execute(orderReceiptOfMoneyEntity);

            // 受注サマリーに入金累計を設定
            orderSummaryEntity.setReceiptPriceTotal(orderReceiptOfMoneyEntity.getReceiptPriceTotal());
            // 受注インデックスに受注入金連番を設定
            orderIndexEntity.setOrderReceiptOfMoneyVersionNo(
                            orderReceiptOfMoneyEntity.getOrderReceiptOfMoneyVersionNo());
        }
    }

    /**
     * 受注インデックスを登録<br/>
     *
     * @param orderIndexEntity 受注インデックスDTO
     * @param shipmentType     出荷種別
     */
    protected void registOrderIndex(OrderIndexEntity orderIndexEntity,
                                    ShipmentType shipmentType,
                                    String administratorName) {
        String processPersonName = administratorName;
        orderIndexEntity.setProcessTime(dateUtility.getCurrentTime());
        orderIndexEntity.setProcessPersonName(processPersonName);
        if (ShipmentType.SHIPMENT_COMPLETION == shipmentType) {
            orderIndexEntity.setProcessType(HTypeProcessType.SHIPMENT);
        } else {
            orderIndexEntity.setProcessType(HTypeProcessType.PART_SHIPMENT);
        }
        orderIndexRegistLogic.execute(orderIndexEntity);
    }

    /**
     * 受注サマリを更新<br/>
     *
     * @param orderSummaryEntity 受注サマリー
     */
    protected void updateOrderSummary(OrderSummaryEntity orderSummaryEntity) {
        orderSummaryUpdateLogic.execute(orderSummaryEntity);
    }

    /**
     * 在庫情報を更新<br/>
     *
     * @param orderIndexEntity  受注インデックス
     * @param orderCode         受注番号（受注コード）
     * @param goodsseqcount     商品数
     * @param shipmentType      出荷種別
     * @param shipmentRegistDto 出荷登録Dto
     */
    protected void updateStockShipment(OrderIndexEntity orderIndexEntity,
                                       String orderCode,
                                       int goodsseqcount,
                                       ShipmentType shipmentType,
                                       ShipmentRegistDto shipmentRegistDto) {

        int count = stockShipmentUpdateLogic.execute(orderIndexEntity.getOrderSeq(),
                                                     orderIndexEntity.getOrderGoodsVersionNo(),
                                                     shipmentRegistDto.getOrderConsecutiveNo(),
                                                     StockShipmentUpdateLogic.NOMAL
                                                    );

        if (count != goodsseqcount) {
            this.addErrorMessage(MSGCD_STOCK_UPDATE_ERROR,
                                 new Object[] {orderCode, shipmentRegistDto.getOrderConsecutiveNo()}
                                );
        }
    }

    /**
     * 受注内の受注配送が、一部出荷かすべて出荷済となるかを判定する。<br/>
     *
     * @param orderDeliveryEntityList 受注配送リスト
     * @param shipmentRegistDto       出荷登録DTO
     * @return 1：1回目の出荷登録（一部出荷） 0：受注内で、最後の出荷登録（すべて出荷済）
     */
    protected ShipmentType getShipmentType(List<OrderDeliveryEntity> orderDeliveryEntityList,
                                           ShipmentRegistDto shipmentRegistDto) {

        ShipmentType shipmentType = ShipmentType.PART_SHIPMENT;
        // すでに出荷済のカウント
        int shippedCount = 0;

        for (OrderDeliveryEntity orderDeliveryEntity : orderDeliveryEntityList) {
            // 今回出荷予定の受注配送をカウント
            if (shipmentRegistDto.getOrderConsecutiveNo().equals(orderDeliveryEntity.getOrderConsecutiveNo())) {
                // すでに出荷済であれば、エラー
                if (HTypeShipmentStatus.SHIPPED == orderDeliveryEntity.getShipmentStatus()) {
                    addErrorMessage(
                                    MSGCD_SHIPMENT_COMPLETION_ERROR, new Object[] {shipmentRegistDto.getOrderCode(),
                                                    shipmentRegistDto.getOrderConsecutiveNo()});
                }
            }
            // すでに出荷済の受注配送をカウント
            if (HTypeShipmentStatus.SHIPPED == orderDeliveryEntity.getShipmentStatus()) {
                shippedCount++;
            }
        }

        // 出荷状態の判定
        if (orderDeliveryEntityList.size() == shippedCount + 1) {
            // すべての受注配送件数と取得件数（出荷済のもの+出荷予定のもの）が同じ場合、最後の出荷登録（すべて出荷済）
            shipmentType = ShipmentType.SHIPMENT_COMPLETION;
        } else {
            // それ以外の場合、出荷登録（一部出荷）
            shipmentType = ShipmentType.PART_SHIPMENT;
        }
        // エラーがあった場合、ここで終了
        if (this.hasErrorMessage()) {
            this.throwMessage();
        }

        return shipmentType;
    }

    /**
     * 出荷登録の処理種別
     * 1：1回目の出荷登録（一部出荷） 0：受注内で、最後の出荷登録（すべて出荷済）
     */
    public enum ShipmentType {
        /**
         * 受注内で、最後の出荷登録（すべて出荷済）
         */
        SHIPMENT_COMPLETION(0),

        /**
         * 1回目の出荷登録（一部出荷）
         */
        PART_SHIPMENT(1);

        /**
         * コンストラクタ<br/>
         *
         * @param shipmentType 出荷登録の処理種別
         */
        private ShipmentType(Integer shipmentType) {

        }
    }
}
