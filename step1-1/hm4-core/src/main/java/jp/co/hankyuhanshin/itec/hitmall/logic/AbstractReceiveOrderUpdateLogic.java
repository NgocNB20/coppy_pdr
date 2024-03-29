/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import com.gmo_pg.g_pay.client.output.AlterTranOutput;
import com.gmo_pg.g_pay.client.output.ChangeTranOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranCvsOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranPayEasyOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGmoReleaseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipedGoodsStockReturn;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockOrderReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRollBackShipmentUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockShipmentUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ConveniEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditAlterTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditChangeTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.PayeasyEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAdditionalChargeRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderGoodsReserveService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderGoodsRollbackService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 受注情報を修正する抽象クラス<br/>
 * 受注修正処理を行なう場合、このクラスを継承してください。<br/>
 *
 * @author hirata
 * @author Kaneko(Itec) 2011/05/25 #2659
 * @author tomo (itec) 2011/08/30 #2717 GMO側に取引データが存在しない場合の対応
 * @author Kaneko (itec) 2011/09/08 #2739
 * @author matsumoto(itec) 2011/12/16 #2698, #2781対応
 * @author hakogi(itec) 2012/01/26 チケット #2732対応
 * @author hakogi(itec) 2012/02/14 チケット #2815対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 * @author hasegawa (itec) 2012/10/16 HM3.3リファクタリング
 * @author STS Nakamura 2020/04/09 GMO経由AmazonPay GMO連携解除対応
 */
@Component
public abstract class AbstractReceiveOrderUpdateLogic extends AbstractShopLogic implements OrderRegistable {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReceiveOrderUpdateLogic.class);

    /**
     * 受注サマリ排他取得ロジック
     */
    private final OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic;

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
     * 受注決済取得ロジック
     */
    private final OrderSettlementGetLogic orderSettlementGetLogic;

    /**
     * 受注請求取得ロジック
     */
    private final OrderBillGetLogic orderBillGetLogic;

    /**
     * 受注追加料金リスト取得ロジック
     */
    private final OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic;

    /**
     * 受注メモ取得ロジック
     */
    private final OrderMemoGetLogic orderMemoGetLogic;

    /**
     * 受注ご注文主登録ロジック
     */
    private final OrderPersonRegistLogic orderPersonRegistLogic;

    /**
     * 受注配送登録ロジック
     */
    private final OrderDeliveryRegistLogic orderDeliveryRegistLogic;

    /**
     * 受注決済登録ロジック
     */
    private final OrderSettlementRegistLogic orderSettlementRegistLogic;

    /**
     * 受注入金登録ロジック
     */
    private final OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic;

    /**
     * 受注請求登録ロジック
     */
    private final OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注追加料金登録ロジック
     */
    private final OrderAdditionalChargeRegistLogic orderAdditionalChargeRegistLogic;

    /**
     * 受注メモ登録ロジック
     */
    private final OrderMemoRegistLogic orderMemoRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    private final OrderIndexRegistLogic orderIndexRegistLogic;

    /**
     * 受注サマリー更新ロジック
     */
    private final OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    /**
     * 在庫確保ロジック
     */
    private final StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic;

    /**
     * 在庫出荷更新ロジック
     */
    private final StockShipmentUpdateLogic stockShipmentUpdateLogic;

    /**
     * 実在庫戻しロジック
     */
    private final StockRollBackShipmentUpdateLogic stockRollBackShipmentUpdateLogic;

    /**
     * 在庫確保処理
     */
    private final OrderGoodsReserveService orderGoodsReserveService;

    /**
     * 在庫確保ロールバック処理
     */
    private final OrderGoodsRollbackService orderGoodsRollbackService;

    /**
     * 決済方法取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * マルチペイメント請求ロジック
     */
    private final MulPayBillLogic mulPayBillLogic;

    /**
     * クレジット変更通信ロジック
     */
    private final CreditAlterTranLogic creditAlterTranLogic;

    /**
     * クレジット金額変更通信ロジック
     */
    private final CreditChangeTranLogic creditChangeTranLogic;

    /**
     * クレジット取引登録・決済通信ロジック
     */
    private final CreditEntryExecTranLogic creditEntryExecTranLogic;

    /**
     * コンビニ登録変更通信ロジック
     */
    private final ConveniEntryExecTranLogic conveniEntryExecTranLogic;

    /**
     * Pay-easy登録変更通信ロジック
     */
    private final PayeasyEntryExecTranLogic payeasyEntryExecTranLogic;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 通信Utility
     */
    private final CommunicateUtility communicateUtility;

    /**
     * キャンセル受注の更新を行うかどうか？
     * <pre>
     * true..行う / false..行わない（デフォルト）
     * falseの場合は、従来通り受注キャンセルチェックが行われる。
     * </pre>
     */
    protected boolean cancelOrderUpdate;

    @Autowired
    public AbstractReceiveOrderUpdateLogic(OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic,
                                           OrderIndexGetLogic orderIndexGetLogic,
                                           OrderPersonGetLogic orderPersonGetLogic,
                                           OrderDeliveryGetLogic orderDeliveryGetLogic,
                                           OrderSettlementGetLogic orderSettlementGetLogic,
                                           OrderBillGetLogic orderBillGetLogic,
                                           OrderAdditionalChargeListGetLogic orderAdditionalChargeListGetLogic,
                                           OrderMemoGetLogic orderMemoGetLogic,
                                           OrderPersonRegistLogic orderPersonRegistLogic,
                                           OrderDeliveryRegistLogic orderDeliveryRegistLogic,
                                           OrderSettlementRegistLogic orderSettlementRegistLogic,
                                           OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic,
                                           OrderBillRegistLogic orderBillRegistLogic,
                                           OrderAdditionalChargeRegistLogic orderAdditionalChargeRegistLogic,
                                           OrderMemoRegistLogic orderMemoRegistLogic,
                                           OrderIndexRegistLogic orderIndexRegistLogic,
                                           OrderSummaryUpdateLogic orderSummaryUpdateLogic,
                                           StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic,
                                           StockShipmentUpdateLogic stockShipmentUpdateLogic,
                                           StockRollBackShipmentUpdateLogic stockRollBackShipmentUpdateLogic,
                                           OrderGoodsReserveService orderGoodsReserveService,
                                           OrderGoodsRollbackService orderGoodsRollbackService,
                                           SettlementMethodGetLogic settlementMethodGetLogic,
                                           MulPayBillLogic mulPayBillLogic,
                                           CreditAlterTranLogic creditAlterTranLogic,
                                           CreditChangeTranLogic creditChangeTranLogic,
                                           CreditEntryExecTranLogic creditEntryExecTranLogic,
                                           ConveniEntryExecTranLogic conveniEntryExecTranLogic,
                                           PayeasyEntryExecTranLogic payeasyEntryExecTranLogic,
                                           DateUtility dateUtility,
                                           OrderUtility orderUtility,
                                           CommunicateUtility communicateUtility) {

        this.orderSummaryGetForUpdateLogic = orderSummaryGetForUpdateLogic;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderPersonGetLogic = orderPersonGetLogic;
        this.orderDeliveryGetLogic = orderDeliveryGetLogic;
        this.orderSettlementGetLogic = orderSettlementGetLogic;
        this.orderBillGetLogic = orderBillGetLogic;
        this.orderAdditionalChargeListGetLogic = orderAdditionalChargeListGetLogic;
        this.orderMemoGetLogic = orderMemoGetLogic;
        this.orderPersonRegistLogic = orderPersonRegistLogic;
        this.orderDeliveryRegistLogic = orderDeliveryRegistLogic;
        this.orderSettlementRegistLogic = orderSettlementRegistLogic;
        this.orderReceiptOfMoneyRegistLogic = orderReceiptOfMoneyRegistLogic;
        this.orderBillRegistLogic = orderBillRegistLogic;
        this.orderAdditionalChargeRegistLogic = orderAdditionalChargeRegistLogic;
        this.orderMemoRegistLogic = orderMemoRegistLogic;
        this.orderIndexRegistLogic = orderIndexRegistLogic;
        this.orderSummaryUpdateLogic = orderSummaryUpdateLogic;
        this.stockOrderReserveUpdateLogic = stockOrderReserveUpdateLogic;
        this.stockShipmentUpdateLogic = stockShipmentUpdateLogic;
        this.stockRollBackShipmentUpdateLogic = stockRollBackShipmentUpdateLogic;
        this.orderGoodsReserveService = orderGoodsReserveService;
        this.orderGoodsRollbackService = orderGoodsRollbackService;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.mulPayBillLogic = mulPayBillLogic;
        this.creditAlterTranLogic = creditAlterTranLogic;
        this.creditChangeTranLogic = creditChangeTranLogic;
        this.creditEntryExecTranLogic = creditEntryExecTranLogic;
        this.conveniEntryExecTranLogic = conveniEntryExecTranLogic;
        this.payeasyEntryExecTranLogic = payeasyEntryExecTranLogic;
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.communicateUtility = communicateUtility;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param receiveOrderDto 修正内容を保持した受注DTO
     */
    @Override
    public void execute(ReceiveOrderDto receiveOrderDto,
                        String memberInfoId,
                        HTypeSiteType siteType,
                        HTypeDeviceType deviceType,
                        Integer memberInfoSeq,
                        String userAgent,
                        String administratorLastName,
                        String administratorFirstName) {

        // 共通情報チェック
        Integer shopSeq = 1001;

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("receiveOrderDto", receiveOrderDto);

        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        Integer orderSeq = orderSummaryEntity.getOrderSeq();
        Integer orderVersionNo = orderSummaryEntity.getOrderVersionNo();
        String orderCode = orderSummaryEntity.getOrderCode();

        // 受注サマリー排他取得＆存在チェック
        OrderSummaryEntity preOrderSummaryEntity =
                        getOrderSummaryEntityForUpDate(orderCode, orderSeq, orderVersionNo, shopSeq);

        // 処理時間
        Timestamp processTime = dateUtility.getCurrentTime();

        // 前回受注インデックス取得
        OrderIndexEntity orderIndexEntity = orderIndexGetLogic.execute(orderSeq, orderVersionNo);
        orderIndexEntity.setSettlementMailRequired(receiveOrderDto.getOrderIndexEntity().getSettlementMailRequired());
        orderIndexEntity.setCouponLimitTargetType(receiveOrderDto.getOrderIndexEntity().getCouponLimitTargetType());

        // 在庫確保処理実行
        Integer newOrderGoodsVersionNo = null;

        try {
            newOrderGoodsVersionNo = orderGoodsReserveService.execute(receiveOrderDto, orderIndexEntity, processTime);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            if (e instanceof AppLevelListException) {
                throw (AppLevelListException) e;
            } else {
                this.printErrorLog(e);
            }
            this.throwMessage(OrderUpdatable.MSGCD_GOODS_RESERVE_ERROR, null, e);
        }

        // 受注更新処理実行
        updateReceiveOrder(receiveOrderDto, preOrderSummaryEntity, orderIndexEntity, processTime,
                           administratorFirstName + administratorLastName
                          );

        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
        HTypeShipmentStatus shipmentStatus = orderDeliveryEntity.getShipmentStatus();
        if (this.hasErrorList()) {
            try {
                // エラー発生時在庫戻し処理
                orderGoodsRollbackService.execute(orderSeq, newOrderGoodsVersionNo,
                                                  orderDeliveryEntity.getOrderConsecutiveNo(), shipmentStatus
                                                 );
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                if (!(e instanceof AppLevelListException)) {
                    this.printErrorLog(e);
                }
                this.addErrorMessage(OrderUpdatable.MSGCD_ORDER_ROLLBACK_ERROR, null, e);
            }
        } else {
            // 商品数量登録ありの場合、在庫更新処理実行
            finallyStockUpdate(shipmentStatus, orderSeq, newOrderGoodsVersionNo,
                               orderDeliveryEntity.getOrderConsecutiveNo()
                              );
        }

        if (this.hasErrorList()) {
            this.throwMessage();
        }
    }

    /**
     * 受注サマリー排他取得＆存在チェック
     *
     * @param orderCode      受注コード
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @param shopSeq        ショップSEQ
     * @return 受注サマリ
     */
    protected OrderSummaryEntity getOrderSummaryEntityForUpDate(String orderCode,
                                                                Integer orderSeq,
                                                                Integer orderVersionNo,
                                                                Integer shopSeq) {

        // 受注サマリー情報存在チェック
        OrderSummaryEntity orderSummaryEntity =
                        orderSummaryGetForUpdateLogic.execute(orderCode, orderVersionNo, shopSeq);
        if (orderSummaryEntity == null) {
            this.throwMessage(OrderUpdatable.MSGCD_SELECT_SUMMARY_ERROR);
        } else if (!cancelOrderUpdate && HTypeCancelFlag.ON.equals(orderSummaryEntity.getCancelFlag())) {
            this.throwMessage(OrderUpdatable.MSGCD_ORDER_CANCEL_ERROR);
        }
        return orderSummaryEntity;
    }

    /**
     * 最終在庫更新処理<br/>
     *
     * @param shipmentStatus         出荷状態
     * @param orderSeq               受注SEQ
     * @param newOrderGoodsVersionNo 受注商品履歴連番
     * @param orderConsecutiveNo     注文連番
     */
    protected void finallyStockUpdate(HTypeShipmentStatus shipmentStatus,
                                      Integer orderSeq,
                                      Integer newOrderGoodsVersionNo,
                                      Integer orderConsecutiveNo) {
        try {
            if (newOrderGoodsVersionNo != null) {

                if (HTypeShipmentStatus.SHIPPED.equals(shipmentStatus)) {
                    // 差分出荷
                    // 在庫管理のなし場合は、実在庫を減らさない
                    stockShipmentUpdateLogic.execute(orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo,
                                                     StockShipmentUpdateLogic.DIFF
                                                    );

                    // 出荷済みの場合、差分を在庫戻しする
                    String returnFlag = PropertiesUtil.getSystemPropertiesValue("shipedGoodsStockReturn");
                    if (HTypeShipedGoodsStockReturn.ON.getValue().equals(returnFlag)) {
                        stockRollBackShipmentUpdateLogic.execute(orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo);
                    }

                } else {
                    // 未出荷差分在庫戻し
                    stockOrderReserveUpdateLogic.execute(orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo,
                                                         StockOrderReserveUpdateLogic.ROLLBACK
                                                        );
                }
            }
        } catch (RuntimeException e) {
            // キャッチしたエラーをログに書き出し
            LOGGER.error("最終在庫確定処理に失敗しました。", e);
            // 在庫更新処理中にエラーが発生した場合は、在庫を戻す
            orderGoodsRollbackService.execute(orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo, shipmentStatus);

            throw e;
        }
    }

    /**
     * 受注情報更新メソッド<br/>
     * トランザクション指定あり<br/>
     *
     * @param receiveOrderDto       受注
     * @param preOrderSummaryEntity 受注サマリ編集前
     * @param orderIndexEntity      受注インデックス
     * @param processTime           処理日時
     * @return チェックメッセージ
     */
    protected CheckMessageDto updateReceiveOrder(ReceiveOrderDto receiveOrderDto,
                                                 OrderSummaryEntity preOrderSummaryEntity,
                                                 OrderIndexEntity orderIndexEntity,
                                                 Timestamp processTime,
                                                 String administratorName) {
        try {
            return execUpdateReceiveOrder(
                            receiveOrderDto, preOrderSummaryEntity, orderIndexEntity, processTime, administratorName);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            if (e instanceof AppLevelListException) {
                if (!this.hasErrorList()) {
                    this.addErrorMessage((AppLevelListException) e);
                }
            } else {
                this.printErrorLog(e);
                this.addErrorMessage(OrderUpdatable.MSGCD_ORDER_CHANGE_SYS_ERROR, null, e);
            }
        }
        return null;
    }

    /**
     * 受注情報更新実行メソッド<br/>
     *
     * @param dto              受注
     * @param preEntity        受注サマリ編集前
     * @param orderIndexEntity 受注インデックス
     * @param processTime      処理日時
     * @return チェックメッセージ
     */
    protected CheckMessageDto execUpdateReceiveOrder(ReceiveOrderDto dto,
                                                     OrderSummaryEntity preEntity,
                                                     OrderIndexEntity orderIndexEntity,
                                                     Timestamp processTime,
                                                     String administratorName) {

        ReceiveOrderDto receiveOrderDto = CopyUtil.deepCopy(dto);
        OrderSummaryEntity preOrderSummaryEntity = CopyUtil.deepCopy(preEntity);

        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        // 前回受注情報の取得
        ReceiveOrderDto preReceiveOrderDto =
                        getPreReceiveOrderDto(receiveOrderDto, preOrderSummaryEntity, orderIndexEntity);

        CheckMessageDto checkMessageDto = null;

        // 新受注処理連番
        orderIndexEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo() + 1);
        orderSummaryEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo());
        if (receiveOrderDto.getMulPayBillEntity() != null) {
            receiveOrderDto.getMulPayBillEntity().setOrderVersionNo(orderIndexEntity.getOrderVersionNo());
        }

        // マルチペイメント通信処理
        mulPayCommunicate(receiveOrderDto, preReceiveOrderDto);

        // --------- 更新処理実行 --------//
        // 受注ご注文主登録
        registOrderPerson(receiveOrderDto.getOrderPersonEntity(), preReceiveOrderDto.getOrderPersonEntity(),
                          orderIndexEntity
                         );

        // 受注配送登録
        registOrderDelivery(receiveOrderDto.getOrderDeliveryDto(), preReceiveOrderDto.getOrderDeliveryDto(),
                            orderIndexEntity
                           );

        // 受注追加料金登録
        registOrderAdditionalCharge(receiveOrderDto.getOrderAdditionalChargeEntityList(),
                                    preReceiveOrderDto.getOrderAdditionalChargeEntityList(), orderIndexEntity
                                   );

        // 受注メモ登録
        registOrderMemoRegistLogic(receiveOrderDto.getOrderMemoEntity(), preReceiveOrderDto.getOrderMemoEntity(),
                                   orderIndexEntity
                                  );

        // 受注決済入金登録
        registSettlementOrderReceiptOfMoney(receiveOrderDto, preReceiveOrderDto, orderIndexEntity, processTime);

        // 受注請求登録
        registOrderBill(receiveOrderDto.getOrderBillEntity(), preReceiveOrderDto.getOrderBillEntity(),
                        orderIndexEntity
                       );

        // 受注インデックス登録
        HTypeWaitingFlag waitingFlag = orderSummaryEntity.getWaitingFlag();
        registOrderIndex(orderIndexEntity, waitingFlag, processTime, receiveOrderDto.isRecoveryAuthoryFlag(),
                         administratorName
                        );

        // 受注サマリー更新
        orderSummaryEntity.setSettlementMailRequired(orderIndexEntity.getSettlementMailRequired());
        updateOrderSummary(orderSummaryEntity, receiveOrderDto.getOrderSettlementEntity(),
                           receiveOrderDto.getOrderDeliveryDto(), orderIndexEntity
                          );

        return checkMessageDto;
    }

    /**
     * 前回受注情報取得<br/>
     *
     * @param receiveOrderDto       受注
     * @param preOrderSummaryEntity 前回受注サマリ
     * @param orderIndexEntity      受注インデックス
     * @return 前回受注
     */
    protected ReceiveOrderDto getPreReceiveOrderDto(ReceiveOrderDto receiveOrderDto,
                                                    OrderSummaryEntity preOrderSummaryEntity,
                                                    OrderIndexEntity orderIndexEntity) {

        // 戻り値用
        ReceiveOrderDto preReceiveOrderDto = ApplicationContextUtility.getBean(ReceiveOrderDto.class);
        preReceiveOrderDto.setOrderSummaryEntity(preOrderSummaryEntity);
        Integer orderSeq = preOrderSummaryEntity.getOrderSeq();

        // 前回受注ご注文主取得
        preReceiveOrderDto.setOrderPersonEntity(
                        orderPersonGetLogic.execute(orderSeq, orderIndexEntity.getOrderPersonVersionNo()));

        // TODO-QUAD-1222
        // 前回受注配送取得
        List<OrderDeliveryEntity> orderDeliveryEntityList =
                        orderDeliveryGetLogic.execute(orderSeq, orderIndexEntity.getOrderDeliveryVersionNo());
        for (OrderDeliveryEntity orderDeliveryEntity : orderDeliveryEntityList) {
            OrderDeliveryDto orderDeliveryDto = ApplicationContextUtility.getBean(OrderDeliveryDto.class);
            orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
            preReceiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        }

        // 前回受注決済取得
        preReceiveOrderDto.setOrderSettlementEntity(
                        orderSettlementGetLogic.execute(orderSeq, orderIndexEntity.getOrderSettlementVersionNo()));

        // 前回受注請求取得
        preReceiveOrderDto.setOrderBillEntity(
                        orderBillGetLogic.execute(orderSeq, orderIndexEntity.getOrderBillVersionNo()));

        // 前回受注追加料金リスト取得
        if (orderIndexEntity.getOrderAdditionalChargeVersionNo() != null) {
            preReceiveOrderDto.setOrderAdditionalChargeEntityList(orderAdditionalChargeListGetLogic.execute(orderSeq,
                                                                                                            orderIndexEntity.getOrderAdditionalChargeVersionNo()
                                                                                                           ));
        }

        // 前回受注メモ取得
        if (orderIndexEntity.getOrderMemoVersionNo() != null) {
            preReceiveOrderDto.setOrderMemoEntity(
                            orderMemoGetLogic.execute(orderSeq, orderIndexEntity.getOrderMemoVersionNo()));
        }

        // 前回マルチペイメント請求取得
        preReceiveOrderDto.setMulPayBillEntity(mulPayBillLogic.getMulPayBillByOrderSeqAndOrderVersionNo(orderSeq,
                                                                                                        orderIndexEntity.getOrderVersionNo()
                                                                                                       ));

        return preReceiveOrderDto;
    }

    /**
     * マルチペイメント通信メソッド<br/>
     *
     * @param receiveOrderDto    受注
     * @param preReceiveOrderDto 編集前受注
     */
    protected void mulPayCommunicate(ReceiveOrderDto receiveOrderDto, ReceiveOrderDto preReceiveOrderDto) {

        // 受注決済情報の比較
        List<String> diff = DiffUtil.diff(receiveOrderDto.getOrderSettlementEntity(),
                                          preReceiveOrderDto.getOrderSettlementEntity()
                                         );

        // 通常の受注修正時は処理終了
        if (diff.isEmpty() && !receiveOrderDto.isReAuthoryFlag() && !receiveOrderDto.isRecoveryAuthoryFlag()) {
            return;
        }

        if (receiveOrderDto.isRecoveryAuthoryFlag()) {
            // 受注リカバリフラグがONの場合は、取引＆決済実行処理を行う
            communicateByCredit(receiveOrderDto);

        } else {
            // クレジットに関する処理
            mulPayCommunicateForCredit(receiveOrderDto, preReceiveOrderDto, diff);
        }
        // コンビニに関する処理
        mulPayCommunicateForConveni(receiveOrderDto);

        // Pay-easyに関する処理
        mulPayCommunicateForPayEasy(receiveOrderDto);

    }

    /**
     * クレジット取引登録を実行<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicateByCredit(ReceiveOrderDto receiveOrderDto) {
        // 取引＆決済実行
        EntryExecTranOutput output = creditEntryExecTranLogic.execute(receiveOrderDto, false);

        if (output.getEntryTranOutput().isErrorOccurred() || output.getExecTranOutput().isErrorOccurred()) {

            List<CheckMessageDto> checkMessageDtoList = creditEntryExecTranLogic.checkOutput(output);
            for (CheckMessageDto dto : checkMessageDtoList) {
                addErrorMessage(dto.getMessageId(), dto.getArgs());
            }
            throwMessage();
        }
    }

    /**
     * クレジットに関するマルチペイメント通信メソッド
     *
     * @param receiveOrderDto    受注DTO
     * @param preReceiveOrderDto 前回受注DTO
     * @param customParams       案件用引数
     * @param diff               受注決済情報の比較
     */
    protected void mulPayCommunicateForCredit(ReceiveOrderDto receiveOrderDto,
                                              ReceiveOrderDto preReceiveOrderDto,
                                              List<String> diff,
                                              Object... customParams) {

        // GMO連携解除時にはGMOと通信しない
        if (HTypeGmoReleaseFlag.RELEASE == receiveOrderDto.getOrderBillEntity().getGmoReleaseFlag()) {
            return;
        }
        // マルチペイメント通信処理種別を取得
        Integer mulPayCom = getMulPayCom(receiveOrderDto, preReceiveOrderDto, diff);

        // 取消時に編集前受注（preReceiveOrderDto）を使用するかを判断
        boolean preReceiveOrderFlg = getPreReceiveOrderFlg(receiveOrderDto, preReceiveOrderDto, diff);

        // マルチペイメント クレジット通信
        List<CheckMessageDto> message = null;

        if (mulPayCom != null && mulPayCom == OrderUpdatable.MULPAY_KINGAKUHENKOU) {
            // 金額変更
            ChangeTranOutput result = creditChangeTranLogic.execute(receiveOrderDto.getMulPayBillEntity(),
                                                                    receiveOrderDto.getOrderSummaryEntity()
                                                                                   .getOrderPrice()
                                                                   );
            message = creditChangeTranLogic.checkOutput(result);
        } else if (mulPayCom != null && mulPayCom == OrderUpdatable.MULPAY_TORIKESHI) {
            // 取消
            AlterTranOutput result = null;
            if (preReceiveOrderFlg) {
                // 受注履歴連番を更新
                preReceiveOrderDto.getMulPayBillEntity()
                                  .setOrderVersionNo(receiveOrderDto.getOrderSummaryEntity().getOrderVersionNo());
                result = creditAlterTranLogic.doCancelTran(preReceiveOrderDto.getMulPayBillEntity());
            } else {
                result = creditAlterTranLogic.doCancelTran(receiveOrderDto.getMulPayBillEntity());
            }

            // GMOが返却するエラーは画面に表示し、処理は続行する
            communicateUtility.setGmoCancelFailureMessage(result);

        } else if (mulPayCom != null && mulPayCom == OrderUpdatable.MULPAY_HENKOU) {
            // 変更
            AlterTranOutput result = creditAlterTranLogic.doReTran(receiveOrderDto);
            message = creditAlterTranLogic.checkOutput(result);
        }

        // クレジット決済エラーが発生した場合、又は例外が発生した場合はエラーとして処理を行う。
        if (message != null && message.size() > 0) {
            for (CheckMessageDto dto : message) {
                addErrorMessage(dto.getMessageId(), dto.getArgs());
            }
            throwMessage();
        }

    }

    /**
     * マルチペイメント通信処理種別を取得<br/>
     * <pre>
     * 前回と今回の受注決済情報を比較し、
     * マルチペイメント通信処理種別を返す。
     * </pre>
     *
     * @param dto          受注DTO
     * @param preDto       前回受注DTO
     * @param diff         受注決済情報の比較
     * @param customParams 案件用引数
     * @return マルチペイメント通信処理種別
     */
    protected Integer getMulPayCom(ReceiveOrderDto dto,
                                   ReceiveOrderDto preDto,
                                   List<String> diff,
                                   Object... customParams) {

        // 受注決済情報の比較結果に差が存在する場合
        if (diff.isEmpty() && dto.isReAuthoryFlag()) {
            // 再オーソリ処理時は金額変更オーソリを実施する
            return OrderUpdatable.MULPAY_KINGAKUHENKOU;
        }

        // 受注決済情報の比較結果に差が存在しない場合

        // 再オーソリ処理時の場合、または、前回決済種別がクレジット以外の場合
        if (dto.isReAuthoryFlag() || HTypeSettlementMethodType.CREDIT != preDto.getOrderSettlementEntity()
                                                                               .getSettlementMethodType()) {
            return null;
        }

        // 通常受注修正時の場合、かつ
        // 前回決済種別がクレジットの場合

        // 今回決済種別がクレジット以外の場合
        if (HTypeSettlementMethodType.CREDIT != dto.getOrderSettlementEntity().getSettlementMethodType() && isSales(
                        preDto.getMulPayBillEntity().getJobCd())) {
            // 処理区分が売上に関する区分の場合は取消オーソリを実施する
            return OrderUpdatable.MULPAY_TORIKESHI;
        }

        // 今回決済種別がクレジットの場合

        // 受注金額変更時
        if (dto.getOrderSummaryEntity().getOrderPrice().compareTo(preDto.getOrderSummaryEntity().getOrderPrice())
            == 0) {
            return null;
        }

        if (!isSales(dto.getMulPayBillEntity().getJobCd())) {
            // 処理区分が売上に関する区分以外の場合は変更オーソリを実施する
            return OrderUpdatable.MULPAY_HENKOU;
        }
        if (dto.getOrderSummaryEntity().getOrderPrice().compareTo(BigDecimal.ZERO) > 0) {
            // 受注金額が0より多いの場合は金額変更オーソリを実施する
            return OrderUpdatable.MULPAY_KINGAKUHENKOU;
        }
        // 受注金額が0以下の場合は取消オーソリを実施する
        return OrderUpdatable.MULPAY_TORIKESHI;
    }

    /**
     * 編集前受注フラグを取得<br/>
     * <pre>
     * 前回と今回の受注決済情報を比較し、
     * 編集前受注フラグを返す。
     * </pre>
     *
     * @param dto          受注DTO
     * @param preDto       前回受注DTO
     * @param customParams 案件用引数
     * @return 編集前受注フラグ
     */
    protected boolean getPreReceiveOrderFlg(ReceiveOrderDto dto, ReceiveOrderDto preDto, Object... customParams) {

        // 再オーソリ処理時の場合、または、前回決済種別がクレジット以外の場合
        if (dto.isReAuthoryFlag() || HTypeSettlementMethodType.CREDIT != preDto.getOrderSettlementEntity()
                                                                               .getSettlementMethodType()) {
            return false;
        }

        // 通常受注修正時の場合、かつ
        // 前回決済種別がクレジットの場合

        // 今回決済種別がクレジット以外の場合
        if (HTypeSettlementMethodType.CREDIT != dto.getOrderSettlementEntity().getSettlementMethodType()) {
            if (isSales(preDto.getMulPayBillEntity().getJobCd())) {
                // 処理区分が売上に関する区分の場合
                // 編集前受注（preReceiveOrderDto）にてオーソリを実行する
                return true;
            }
        }

        return false;
    }

    /**
     * コンビニに関するマルチペイメント通信メソッド
     *
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用引数
     */
    protected void mulPayCommunicateForConveni(ReceiveOrderDto receiveOrderDto, Object... customParams) {

        // 今回決済種別がコンビニ以外の場合
        if (HTypeSettlementMethodType.CONVENIENCE != receiveOrderDto.getOrderSettlementEntity()
                                                                    .getSettlementMethodType()) {
            return;
        }

        if (receiveOrderDto.getOrderTempDto() == null) {
            receiveOrderDto.setOrderTempDto(ApplicationContextUtility.getBean(OrderTempDto.class));
        }
        receiveOrderDto.getOrderTempDto().setConvenience(receiveOrderDto.getMulPayBillEntity().getConvenience());

        entryExecTranForConveni(receiveOrderDto);
    }

    /**
     * Pay-easyに関するマルチペイメント通信メソッド
     *
     * @param receiveOrderDto 受注DTO
     * @param customParams    案件用引数
     */
    protected void mulPayCommunicateForPayEasy(ReceiveOrderDto receiveOrderDto, Object... customParams) {

        // 今回決済種別がPay-easy以外の場合
        if (HTypeSettlementMethodType.PAY_EASY != receiveOrderDto.getOrderSettlementEntity()
                                                                 .getSettlementMethodType()) {
            return;
        }

        EntryExecTranPayEasyOutput result = payeasyEntryExecTranLogic.execute(receiveOrderDto);
        List<CheckMessageDto> message = payeasyEntryExecTranLogic.checkPayEasyOutput(result);
        if (message != null && message.size() > 0) {
            // エラー処理
            for (CheckMessageDto dto : message) {
                addErrorMessage(dto.getMessageId());
            }
            throwMessage();
        }
    }

    /**
     * コンビニ決済用通信処理
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void entryExecTranForConveni(ReceiveOrderDto receiveOrderDto) {
        EntryExecTranCvsOutput result = conveniEntryExecTranLogic.execute(receiveOrderDto);
        List<CheckMessageDto> message = conveniEntryExecTranLogic.checkCsvOutput(result);
        if (message != null && message.size() > 0) {
            // エラー処理
            for (CheckMessageDto dto : message) {
                addErrorMessage(dto.getMessageId());
            }
            throwMessage();
        }
    }

    /**
     * 処理区分が売上に関する区分かどうかを判別します。<br/>
     * <pre>
     * 処理区分が「実売上」「即時売上」「仮売上」の場合はtrueを返す。
     * 上記以外の場合はfalseを返す。
     * </pre>
     *
     * @param jobCd        処理区分
     * @param customParams 案件用引数
     * @return 処理区分が売上に関する区分かどうか
     */
    protected boolean isSales(String jobCd, Object... customParams) {
        if (HTypeJobCode.SALES.toString().equals(jobCd) || HTypeJobCode.CAPTURE.toString().equals(jobCd)
            || HTypeJobCode.AUTH.toString().equals(jobCd)) {
            return true;
        }
        return false;
    }

    /**
     * 受注ご注文主登録<br/>
     *
     * @param orderPersonEntity    受注ご注文主
     * @param preOrderPersonEntity 変更前受注ご注文主
     * @param orderIndexEntity     受注インデックス
     */
    protected void registOrderPerson(OrderPersonEntity orderPersonEntity,
                                     OrderPersonEntity preOrderPersonEntity,
                                     OrderIndexEntity orderIndexEntity) {
        Integer orderPersonVersionNo = preOrderPersonEntity.getOrderPersonVersionNo();
        List<String> diffList = DiffUtil.diff(preOrderPersonEntity, orderPersonEntity);
        // 相違点が見つからない場合は処理を抜ける
        if (diffList == null || diffList.isEmpty()) {
            return;
        }
        orderPersonVersionNo++;
        orderPersonEntity.setOrderPersonVersionNo(orderPersonVersionNo);
        orderPersonRegistLogic.execute(orderPersonEntity);
        orderIndexEntity.setOrderPersonVersionNo(orderPersonVersionNo);
    }

    /**
     * 受注配送登録<br/>
     *
     * @param orderDeliveryDto    受注配送Dto
     * @param preOrderDeliveryDto 受注配送Dto編集前
     * @param orderIndexEntity    受注インデックス
     */
    protected void registOrderDelivery(OrderDeliveryDto orderDeliveryDto,
                                       OrderDeliveryDto preOrderDeliveryDto,
                                       OrderIndexEntity orderIndexEntity) {
        boolean hasDiff = false;
        if (orderDeliveryDto != null && preOrderDeliveryDto != null) {

            List<String> diffList = DiffUtil.diff(orderDeliveryDto.getOrderDeliveryEntity(),
                                                  preOrderDeliveryDto.getOrderDeliveryEntity()
                                                 );
            if (CollectionUtil.isNotEmpty(diffList)) {
                hasDiff = true;
            }

        } else {
            hasDiff = true;
        }
        if (!hasDiff) {
            // 相違点が見つからない場合は処理を抜ける
            return;
        }

        Integer orderDeliveryVersionNo = preOrderDeliveryDto.getOrderDeliveryEntity().getOrderDeliveryVersionNo();

        orderDeliveryVersionNo++;
        orderDeliveryDto.getOrderDeliveryEntity().setOrderDeliveryVersionNo(orderDeliveryVersionNo);
        orderDeliveryRegistLogic.execute(orderDeliveryDto.getOrderDeliveryEntity());

        orderIndexEntity.setOrderDeliveryVersionNo(orderDeliveryVersionNo);
    }

    /**
     * 受注追加料金<br/>
     *
     * @param orderAdditionalChargeEntityList    受注追加料金
     * @param preOrderAdditionalChargeEntityList 受注追加料金編集前
     * @param orderIndexEntity                   受注インデックス
     */
    protected void registOrderAdditionalCharge(List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList,
                                               List<OrderAdditionalChargeEntity> preOrderAdditionalChargeEntityList,
                                               OrderIndexEntity orderIndexEntity) {
        List<String> diffList = DiffUtil.diff(preOrderAdditionalChargeEntityList, orderAdditionalChargeEntityList);
        if (diffList == null || diffList.isEmpty()) {
            return;
        }
        Integer orderAdditionalChargeVersionNo = orderIndexEntity.getOrderAdditionalChargeVersionNo();
        if (orderAdditionalChargeVersionNo == null) {
            orderAdditionalChargeVersionNo = 0;
        }

        Integer orderDisplay = 1;
        orderAdditionalChargeVersionNo++;
        for (OrderAdditionalChargeEntity orderAdditionalChargeEntity : orderAdditionalChargeEntityList) {
            orderAdditionalChargeEntity.setOrderSeq(orderIndexEntity.getOrderSeq());
            orderAdditionalChargeEntity.setOrderAdditionalChargeVersionNo(orderAdditionalChargeVersionNo);
            orderAdditionalChargeEntity.setOrderDisplay(orderDisplay);
            orderAdditionalChargeRegistLogic.execute(orderAdditionalChargeEntity);
            orderDisplay++;
        }
        // 受注追加料金の追加があった場合、受注追加料金連番をセットする
        if (!orderAdditionalChargeEntityList.isEmpty()) {
            orderIndexEntity.setOrderAdditionalChargeVersionNo(orderAdditionalChargeVersionNo);
        }

    }

    /**
     * 受注メモ登録<br/>
     *
     * @param orderMemoEntity    受注メモ
     * @param preOrderMemoEntity 受注メモ編集前
     * @param orderIndexEntity   受注インデックス
     */
    protected void registOrderMemoRegistLogic(OrderMemoEntity orderMemoEntity,
                                              OrderMemoEntity preOrderMemoEntity,
                                              OrderIndexEntity orderIndexEntity) {

        Integer orderMemoVersionNo = null;
        if (preOrderMemoEntity != null) {
            orderMemoVersionNo = preOrderMemoEntity.getOrderMemoVersionNo();
        } else if (orderMemoEntity == null) {
            return;
        }
        List<String> diffList = DiffUtil.diff(preOrderMemoEntity, orderMemoEntity);
        if (diffList == null || diffList.isEmpty()) {
            return;
        } else if (orderMemoVersionNo == null) {
            orderMemoVersionNo = 0;
        }

        orderMemoVersionNo++;
        orderMemoEntity.setOrderSeq(orderIndexEntity.getOrderSeq());
        orderMemoEntity.setOrderMemoVersionNo(orderMemoVersionNo);
        orderMemoRegistLogic.execute(orderMemoEntity);
        orderIndexEntity.setOrderMemoVersionNo(orderMemoVersionNo);
    }

    /**
     * 受注決済入金登録<br/>
     *
     * <pre>
     * クレジット or AmazonPay 受注決済入金登録処理
     *
     * 修正前がクレジット or AmazonPayの場合で
     * 以下の条件に当てはまる場合処理を行う。
     * ①金額変更がされた場合、または決済方法が変更の場合
     * ②修正後の状態が出荷済み または 修正前の請求が前請求の場合
     * 　②－１修正前の入金累計に金額が設定されている場合、
     * 　　　　マイナス入金データを登録
     * 　②－２修正後の決済方法がクレジット又はAmazonPayで受注金額が変更された場合
     * 　　　　入金データを登録
     * </pre>
     *
     * @param receiveOrderDto    受注DTO
     * @param preReceiveOrderDto 編集前受注DTO
     * @param orderIndexEntity   受注インデックス
     * @param processTime        処理日時
     */
    protected void registSettlementOrderReceiptOfMoney(ReceiveOrderDto receiveOrderDto,
                                                       ReceiveOrderDto preReceiveOrderDto,
                                                       OrderIndexEntity orderIndexEntity,
                                                       Timestamp processTime) {

        // 修正前後の受注決済情報に差異がある場合に必ず実施する処理
        // 現在の受注決済情報に対する赤伝を作成して、現在の決済情報を打ち消す
        // 今回の受注決済情報で黒伝を作成

        OrderSettlementEntity orderSettlement = receiveOrderDto.getOrderSettlementEntity();
        OrderSettlementEntity preOrderSettlement = preReceiveOrderDto.getOrderSettlementEntity();

        List<String> diffList = DiffUtil.diff(preOrderSettlement, orderSettlement);
        // 相違点がある場合のみ
        if (CollectionUtil.isEmpty(diffList)) {
            return;
        }

        OrderSummaryEntity orderSummary = receiveOrderDto.getOrderSummaryEntity();
        OrderSummaryEntity preOrderSummary = preReceiveOrderDto.getOrderSummaryEntity();
        // 修正前がクレジット or AmazonPayの場合
        if (HTypeSettlementMethodType.CREDIT == preOrderSettlement.getSettlementMethodType()
            || HTypeSettlementMethodType.AMAZON_PAYMENT == preOrderSettlement.getSettlementMethodType()) {

            // 金額変更がない場合は除外 。 金額変更がなくても決済方法が変更の場合は対象
            if (orderSummary.getOrderPrice().compareTo(preOrderSummary.getOrderPrice()) != 0 || (
                            HTypeSettlementMethodType.CREDIT != orderSettlement.getSettlementMethodType()
                            && HTypeSettlementMethodType.AMAZON_PAYMENT != orderSettlement.getSettlementMethodType())) {

                // 修正後が出荷済み or 修正前が前請求の場合（入金がある場合）
                if (HTypeOrderStatus.SHIPMENT_COMPLETION == orderSummary.getOrderStatus()
                    || HTypeBillType.PRE_CLAIM == preOrderSettlement.getBillType()) {

                    // 修正前に入金がある場合
                    if (preOrderSummary.getReceiptPriceTotal().compareTo(BigDecimal.ZERO) != 0) {
                        // 受注入金全額返金データ登録
                        registOrderReceiptOfMoneyRepayment(orderSummary, preOrderSummary, preOrderSettlement,
                                                           orderIndexEntity, processTime
                                                          );
                    }

                    if ((HTypeSettlementMethodType.CREDIT == orderSettlement.getSettlementMethodType()
                         || HTypeSettlementMethodType.AMAZON_PAYMENT == orderSettlement.getSettlementMethodType())
                        && orderSummary.getOrderPrice().compareTo(BigDecimal.ZERO) != 0) {
                        // 受注入金登録
                        registOrderReceiptOfMoney(
                                        orderSummary, preOrderSummary, orderSettlement, orderIndexEntity, processTime);
                    }
                }
            }
        }
        // 今回決済方法≠クレジット
        if (HTypeSettlementMethodType.CREDIT != orderSettlement.getSettlementMethodType()) {
            receiveOrderDto.getOrderBillEntity().setCreditCompanyCode(null);
            receiveOrderDto.getOrderBillEntity().setCreditCompany(null);
        }
        // GMO連携解除、請求決済エラー時に決済方法を変更した場合、通常に戻す。
        if (preReceiveOrderDto.getOrderBillEntity().getEmergencyFlag() == HTypeEmergencyFlag.ON
            || preReceiveOrderDto.getOrderBillEntity().getGmoReleaseFlag() == HTypeGmoReleaseFlag.RELEASE) {
            // クレジット、AmazonPayは決済方法変更後の対象にはならない
            if (preOrderSettlement.getSettlementMethodType() != orderSettlement.getSettlementMethodType()) {
                receiveOrderDto.getOrderBillEntity().setEmergencyFlag(HTypeEmergencyFlag.OFF);
                receiveOrderDto.getOrderBillEntity().setGmoReleaseFlag(HTypeGmoReleaseFlag.NORMAL);
            }
        }

        // 受注決済登録（赤伝・黒伝）
        registOrderSettlementCancelDate(preOrderSummary, orderSummary, orderSettlement, preOrderSettlement,
                                        orderIndexEntity, processTime
                                       );
        registOrderSettlement(orderSettlement, preOrderSettlement, orderIndexEntity, processTime);
    }

    /**
     * 受注入金登録<br/>
     *
     * @param orderSummaryEntity    受注サマリ
     * @param preOrderSummaryEntity 編集前受注サマリ
     * @param orderSettlementEntity 受注決済
     * @param orderIndexEntity      受注インデックス
     * @param processTime           処理日時
     */
    protected void registOrderReceiptOfMoney(OrderSummaryEntity orderSummaryEntity,
                                             OrderSummaryEntity preOrderSummaryEntity,
                                             OrderSettlementEntity orderSettlementEntity,
                                             OrderIndexEntity orderIndexEntity,
                                             Timestamp processTime) {

        Integer orderReceiptOfMoneyVersionNo = orderIndexEntity.getOrderReceiptOfMoneyVersionNo();
        BigDecimal orderPrice = orderSummaryEntity.getOrderPrice();

        if (orderReceiptOfMoneyVersionNo == null) {
            orderReceiptOfMoneyVersionNo = 0;
        }
        orderReceiptOfMoneyVersionNo++;

        // カード決済の場合、受注金額と同額の入金データを作成する仕様。
        BigDecimal receiptPriceTotal = orderSummaryEntity.getReceiptPriceTotal().add(orderPrice);

        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
        orderReceiptOfMoneyEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        orderReceiptOfMoneyEntity.setOrderSiteType(orderSummaryEntity.getOrderSiteType());
        orderReceiptOfMoneyEntity.setOrderTime(orderSummaryEntity.getOrderTime());
        orderReceiptOfMoneyEntity.setReceiptMethodSeq(null);
        orderReceiptOfMoneyEntity.setReceiptPrice(orderPrice);
        orderReceiptOfMoneyEntity.setReceiptPriceTotal(receiptPriceTotal);
        orderReceiptOfMoneyEntity.setReceiptTime(processTime);
        orderReceiptOfMoneyEntity.setReceiptYm(dateUtility.formatYm(processTime));
        orderReceiptOfMoneyEntity.setReceiptYmd(dateUtility.formatYmd(processTime));
        orderReceiptOfMoneyEntity.setSalesTime(orderSummaryEntity.getSalesTime());
        orderReceiptOfMoneyEntity.setSettlementMethodSeq(orderSummaryEntity.getSettlementMethodSeq());
        orderReceiptOfMoneyEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        orderReceiptOfMoneyRegistLogic.execute(orderReceiptOfMoneyEntity);

        orderSummaryEntity.setReceiptPriceTotal(receiptPriceTotal);
        orderIndexEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
    }

    /**
     * 受注入金返金データ登録<br/>
     *
     * @param orderSummaryEntity       受注サマリ
     * @param preOrderSummaryEntity    受注サマリ編集前
     * @param preOrderSettlementEntity 受注決済編集前
     * @param orderIndexEntity         受注インデックス
     * @param processTime              処理日時
     */
    protected void registOrderReceiptOfMoneyRepayment(OrderSummaryEntity orderSummaryEntity,
                                                      OrderSummaryEntity preOrderSummaryEntity,
                                                      OrderSettlementEntity preOrderSettlementEntity,
                                                      OrderIndexEntity orderIndexEntity,
                                                      Timestamp processTime) {

        Integer orderReceiptOfMoneyVersionNo = orderIndexEntity.getOrderReceiptOfMoneyVersionNo();

        /*
         * このメソッドは修正前の決済方法がカード決済・AmazonPayの場合にしか実行されない
         */

        orderReceiptOfMoneyVersionNo++;

        // 作成する返金データ
        // 入金額 = 修正前受注金額 * -1
        // 入金累計 = 修正前.入金額合計 - 修正前.受注金額
        BigDecimal receiptPriceTotal =
                        preOrderSummaryEntity.getReceiptPriceTotal().subtract(preOrderSummaryEntity.getOrderPrice());

        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
        orderReceiptOfMoneyEntity.setOrderSeq(preOrderSummaryEntity.getOrderSeq());
        orderReceiptOfMoneyEntity.setOrderSiteType(preOrderSummaryEntity.getOrderSiteType());
        orderReceiptOfMoneyEntity.setOrderTime(preOrderSummaryEntity.getOrderTime());
        orderReceiptOfMoneyEntity.setReceiptMethodSeq(null);

        // 受注金額を全額返金する
        orderReceiptOfMoneyEntity.setReceiptPrice(preOrderSummaryEntity.getOrderPrice().negate());
        orderReceiptOfMoneyEntity.setReceiptPriceTotal(receiptPriceTotal);
        orderReceiptOfMoneyEntity.setReceiptTime(processTime);
        orderReceiptOfMoneyEntity.setReceiptYm(dateUtility.formatYm(processTime));
        orderReceiptOfMoneyEntity.setReceiptYmd(dateUtility.formatYmd(processTime));
        orderReceiptOfMoneyEntity.setSalesTime(preOrderSummaryEntity.getSalesTime());
        orderReceiptOfMoneyEntity.setSettlementMethodSeq(preOrderSummaryEntity.getSettlementMethodSeq());
        orderReceiptOfMoneyEntity.setShopSeq(preOrderSummaryEntity.getShopSeq());
        orderReceiptOfMoneyRegistLogic.execute(orderReceiptOfMoneyEntity);

        // 修正後.入金累計を更新
        orderSummaryEntity.setReceiptPriceTotal(receiptPriceTotal);
        orderIndexEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
    }

    /**
     * 受注決済データ登録（黒伝）<br/>
     *
     * @param orderSettlementEntity    受注決済
     * @param preOrderSettlementEntity 受注決済編集前
     * @param orderIndexEntity         受注インデックス
     * @param processTime              処理日時
     */
    protected void registOrderSettlement(OrderSettlementEntity orderSettlementEntity,
                                         OrderSettlementEntity preOrderSettlementEntity,
                                         OrderIndexEntity orderIndexEntity,
                                         Timestamp processTime) {

        List<String> diffList = DiffUtil.diff(preOrderSettlementEntity, orderSettlementEntity);
        Integer orderSettlementVersionNo = orderIndexEntity.getOrderSettlementVersionNo();
        // 相違点が見つからない場合は処理を抜ける
        if (diffList == null || diffList.isEmpty()) {
            return;
        }

        orderSettlementVersionNo++;
        orderSettlementEntity.setOrderSettlementVersionNo(orderSettlementVersionNo);
        orderSettlementEntity.setProcessTime(processTime);
        orderSettlementEntity.setProcessYm(dateUtility.formatYm(processTime));
        orderSettlementEntity.setProcessYmd(dateUtility.formatYmd(processTime));
        orderSettlementEntity.setTotalingType(HTypeTotalingType.CHANGE);
        orderSettlementEntity.setPreCarriage(preOrderSettlementEntity.getCarriage());
        orderSettlementEntity.setPreGoodsCostTotal(preOrderSettlementEntity.getGoodsCostTotal());
        orderSettlementEntity.setPreGoodsPriceTotal(preOrderSettlementEntity.getGoodsPriceTotal());
        orderSettlementEntity.setPreOthersPriceTotal(preOrderSettlementEntity.getOthersPriceTotal());
        orderSettlementEntity.setPreSettlementCommission(preOrderSettlementEntity.getSettlementCommission());
        orderSettlementEntity.setPreOrderPrice(preOrderSettlementEntity.getOrderPrice());
        orderSettlementEntity.setPreBeforeDiscountOrderPrice(preOrderSettlementEntity.getBeforeDiscountOrderPrice());
        orderSettlementEntity.setPreTaxPrice(preOrderSettlementEntity.getTaxPrice());
        if (!HTypeSettlementMethodType.CREDIT.equals(orderSettlementEntity.getSettlementMethodType())) {
            orderSettlementEntity.setCreditCompanyCode(null);
            orderSettlementEntity.setCreditCompany(null);
        }

        // この処理は集計処理のために必要

        // 前回利用ポイント
        orderSettlementEntity.setPreUsePoint(preOrderSettlementEntity.getUsePoint());

        // 前回クーポン割引額
        orderSettlementEntity.setPreCouponDiscountPrice(preOrderSettlementEntity.getCouponDiscountPrice());

        orderSettlementRegistLogic.execute(orderSettlementEntity);
        orderIndexEntity.setOrderSettlementVersionNo(orderSettlementVersionNo);
    }

    /**
     * 受注決済データ登録（赤伝）<br/>
     *
     * @param preOrderSummaryEntity    編集前受注サマリ
     * @param orderSummaryEntity       受注サマリ
     * @param orderSettlementEntity    受注決済
     * @param preOrderSettlementEntity 編集前受注決済履歴番号
     * @param orderIndexEntity         受注インデックス
     * @param processTime              処理日時
     */
    protected void registOrderSettlementCancelDate(OrderSummaryEntity preOrderSummaryEntity,
                                                   OrderSummaryEntity orderSummaryEntity,
                                                   OrderSettlementEntity orderSettlementEntity,
                                                   OrderSettlementEntity preOrderSettlementEntity,
                                                   OrderIndexEntity orderIndexEntity,
                                                   Timestamp processTime) {

        Integer orderSettlementVersionNo = preOrderSettlementEntity.getOrderSettlementVersionNo();

        Integer preSettlementMethodSeq = preOrderSettlementEntity.getSettlementMethodSeq();
        HTypeSettlementMethodType preSettlementMethodType = preOrderSettlementEntity.getSettlementMethodType();
        Integer settlementMethodSeq = orderSettlementEntity.getSettlementMethodSeq();
        BigDecimal preOrderPrice = preOrderSummaryEntity.getOrderPrice();
        BigDecimal orderPrice = orderSummaryEntity.getOrderPrice();

        if (!preSettlementMethodSeq.equals(settlementMethodSeq) || (
                        HTypeSettlementMethodType.CREDIT.equals(preSettlementMethodType) && !preOrderPrice.equals(
                                        orderPrice))) {

            // ・決済方法が変更された場合：無条件に赤伝の作成が必要
            // ・決済方法が変更されてない場合：受注金額に差異が発生しているカード決済
            orderSettlementVersionNo++;
            preOrderSettlementEntity.setOrderSettlementVersionNo(orderSettlementVersionNo);
            preOrderSettlementEntity.setProcessTime(processTime);
            preOrderSettlementEntity.setProcessYm(dateUtility.formatYm(processTime));
            preOrderSettlementEntity.setProcessYmd(dateUtility.formatYmd(processTime));
            preOrderSettlementEntity.setTotalingType(HTypeTotalingType.CHANGE);
            preOrderSettlementEntity.setPreCarriage(preOrderSettlementEntity.getCarriage());
            preOrderSettlementEntity.setPreGoodsCostTotal(preOrderSettlementEntity.getGoodsCostTotal());
            preOrderSettlementEntity.setPreGoodsPriceTotal(preOrderSettlementEntity.getGoodsPriceTotal());
            preOrderSettlementEntity.setPreOthersPriceTotal(preOrderSettlementEntity.getOthersPriceTotal());
            preOrderSettlementEntity.setPreSettlementCommission(preOrderSettlementEntity.getSettlementCommission());
            preOrderSettlementEntity.setPreOrderPrice(preOrderSettlementEntity.getOrderPrice());
            preOrderSettlementEntity.setPreBeforeDiscountOrderPrice(
                            preOrderSettlementEntity.getBeforeDiscountOrderPrice());
            preOrderSettlementEntity.setPreTaxPrice(preOrderSettlementEntity.getTaxPrice());
            preOrderSettlementEntity.setCarriage(BigDecimal.ZERO);
            preOrderSettlementEntity.setGoodsCostTotal(BigDecimal.ZERO);
            preOrderSettlementEntity.setGoodsPriceTotal(BigDecimal.ZERO);
            preOrderSettlementEntity.setOthersPriceTotal(BigDecimal.ZERO);
            preOrderSettlementEntity.setSettlementCommission(BigDecimal.ZERO);
            preOrderSettlementEntity.setOrderPrice(BigDecimal.ZERO);
            preOrderSettlementEntity.setBeforeDiscountOrderPrice(BigDecimal.ZERO);
            preOrderSettlementEntity.setTaxPrice(BigDecimal.ZERO);

            // この処理は集計処理のために必要
            // 前回利用ポイント
            preOrderSettlementEntity.setPreUsePoint(preOrderSettlementEntity.getUsePoint());
            preOrderSettlementEntity.setUsePoint(BigDecimal.ZERO);
            // 前回クーポン割引額
            preOrderSettlementEntity.setPreCouponDiscountPrice(preOrderSettlementEntity.getCouponDiscountPrice());
            preOrderSettlementEntity.setCouponDiscountPrice(BigDecimal.ZERO);
            orderSettlementRegistLogic.execute(preOrderSettlementEntity);
        }
        orderIndexEntity.setOrderSettlementVersionNo(orderSettlementVersionNo);
    }

    /**
     * 受注請求データ登録処理<br/>
     *
     * @param orderBillEntity    受注請求
     * @param preOrderBillEntity 編集前受注請求
     * @param orderIndexEntity   受注インデックス
     */
    protected void registOrderBill(OrderBillEntity orderBillEntity,
                                   OrderBillEntity preOrderBillEntity,
                                   OrderIndexEntity orderIndexEntity) {

        String diffObjectName = ApplicationContextUtility.getBean(OrderBillEntity.class).getClass().getSimpleName();

        Integer orderBillVersionNo = orderIndexEntity.getOrderBillVersionNo();
        if (HTypeEmergencyFlag.OFF.equals(preOrderBillEntity.getEmergencyFlag())) {
            List<String> diffList = DiffUtil.diff(preOrderBillEntity, orderBillEntity);
            // 相違点が見つからない場合は処理を抜ける
            if ((diffList == null || diffList.isEmpty() ||
                 // 金額または決済方法が変更にならない場合は請求情報の更新はしない
                 (diffList.size() == 1 && diffList.get(0)
                                                  .equals(DiffUtil.joinStr(diffObjectName, "paymentTimeLimitDate"))))) {
                return;
            }
        }

        // 受注請求登録（赤伝）
        orderBillVersionNo++;
        preOrderBillEntity.setOrderBillVersionNo(orderBillVersionNo);
        preOrderBillEntity.setBillPrice(preOrderBillEntity.getBillPrice().negate());
        preOrderBillEntity.setSettlementCommission(preOrderBillEntity.getSettlementCommission().negate());
        preOrderBillEntity.setEmergencyFlag(orderBillEntity.getEmergencyFlag());
        orderBillRegistLogic.execute(preOrderBillEntity);

        // 受注請求登録（黒伝）
        OrderBillEntity insertOrderBillEntity = CopyUtil.deepCopy(orderBillEntity);
        orderBillVersionNo++;
        insertOrderBillEntity.setOrderBillVersionNo(orderBillVersionNo);
        insertOrderBillEntity.setEmergencyFlag(orderBillEntity.getEmergencyFlag());
        orderBillRegistLogic.execute(insertOrderBillEntity);

        orderIndexEntity.setOrderBillVersionNo(orderBillVersionNo);
        if (!preOrderBillEntity.getSettlementMethodSeq().equals(insertOrderBillEntity.getSettlementMethodSeq())
            || preOrderBillEntity.getBillPrice().compareTo(insertOrderBillEntity.getBillPrice()) != 0) {
            orderIndexEntity.setReminderSentFlag(HTypeSend.UNSENT);
            orderIndexEntity.setExpiredSentFlag(HTypeSend.UNSENT);
        }
    }

    /**
     * 受注インデックス登録<br/>
     *
     * @param orderIndexEntity    受注インデックス
     * @param waitingFlag         保留中フラグ
     * @param processTime         処理日時
     * @param recoveryAuthoryFlag リカバリフラグ
     */
    protected void registOrderIndex(OrderIndexEntity orderIndexEntity,
                                    HTypeWaitingFlag waitingFlag,
                                    Timestamp processTime,
                                    boolean recoveryAuthoryFlag,
                                    String administratorName) {
        String processPersonName;
        if (recoveryAuthoryFlag) {
            processPersonName = "カード情報登録 受注リカバリ";
        } else {
            processPersonName = administratorName;
        }
        orderIndexEntity.setProcessPersonName(processPersonName);
        orderIndexEntity.setProcessTime(processTime);
        orderIndexEntity.setProcessType(HTypeProcessType.CHANGE);
        orderIndexEntity.setWaitingFlag(waitingFlag);
        orderIndexRegistLogic.execute(orderIndexEntity);
    }

    /**
     * 受注サマリー更新<br/>
     *
     * @param orderSummaryEntity    受注サマリ
     * @param orderSettlementEntity 受注決済
     * @param orderDeliveryDto      受注配送Dto
     * @param orderIndexEntity      受注インデックス
     */
    protected void updateOrderSummary(OrderSummaryEntity orderSummaryEntity,
                                      OrderSettlementEntity orderSettlementEntity,
                                      OrderDeliveryDto orderDeliveryDto,
                                      OrderIndexEntity orderIndexEntity) {
        orderSummaryEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo());

        HTypeBillType billtype = orderSettlementEntity.getBillType();
        BigDecimal orderPrice = orderSummaryEntity.getOrderPrice();
        BigDecimal receiptPriceTotal = orderSummaryEntity.getReceiptPriceTotal();
        // 受注状態
        HTypeOrderStatus orderstatus;

        // 請求種別=前払い 且つ 請求金額 != 入金累計額
        if (HTypeBillType.PRE_CLAIM.equals(billtype) && !orderPrice.equals(receiptPriceTotal)) {
            orderstatus = HTypeOrderStatus.PAYMENT_CONFIRMING;
        } else {
            orderstatus = HTypeOrderStatus.GOODS_PREPARING;
        }

        // 受注状態を設定
        orderSummaryEntity.setOrderStatus(orderUtility.getOrderStatusByOrderDelivery(orderstatus, orderDeliveryDto));

        orderSummaryEntity.setReminderSentFlag(orderIndexEntity.getReminderSentFlag());
        orderSummaryEntity.setExpiredSentFlag(orderIndexEntity.getExpiredSentFlag());

        orderSummaryUpdateLogic.execute(orderSummaryEntity);
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
