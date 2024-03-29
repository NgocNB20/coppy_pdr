/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractReceiveOrderUpdateLogic;
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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderGoodsReserveService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderGoodsRollbackService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 受注修正ロジック実装クラス
 *
 * @author hirata
 * @version $Revision: 1.12 $
 */
@Component
public class ReceiveOrderUpdateLogicImpl extends AbstractReceiveOrderUpdateLogic implements ReceiveOrderUpdateLogic {

    @Autowired
    public ReceiveOrderUpdateLogicImpl(OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic,
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
        super(orderSummaryGetForUpdateLogic, orderIndexGetLogic, orderPersonGetLogic, orderDeliveryGetLogic,
              orderSettlementGetLogic, orderBillGetLogic, orderAdditionalChargeListGetLogic, orderMemoGetLogic,
              orderPersonRegistLogic, orderDeliveryRegistLogic, orderSettlementRegistLogic,
              orderReceiptOfMoneyRegistLogic, orderBillRegistLogic, orderAdditionalChargeRegistLogic,
              orderMemoRegistLogic, orderIndexRegistLogic, orderSummaryUpdateLogic, stockOrderReserveUpdateLogic,
              stockShipmentUpdateLogic, stockRollBackShipmentUpdateLogic, orderGoodsReserveService,
              orderGoodsRollbackService, settlementMethodGetLogic, mulPayBillLogic, creditAlterTranLogic,
              creditChangeTranLogic, creditEntryExecTranLogic, conveniEntryExecTranLogic, payeasyEntryExecTranLogic,
              dateUtility, orderUtility, communicateUtility
             );
    }

    /**
     * マルチペイメント通信メソッド<br/>
     *
     * @param receiveOrderDto    受注
     * @param preReceiveOrderDto 編集前受注
     */
    @Override
    protected void mulPayCommunicate(ReceiveOrderDto receiveOrderDto, ReceiveOrderDto preReceiveOrderDto) {
        // 編集後決済方法種別
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();

        // 編集前決済方法種別
        HTypeSettlementMethodType preSettlementMethodType =
                        preReceiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();

        // 修正後受注金額
        BigDecimal orderPrice = receiveOrderDto.getOrderSummaryEntity().getOrderPrice();

        // 修正前受注金額
        BigDecimal preOrderPrice = preReceiveOrderDto.getOrderSummaryEntity().getOrderPrice();

        if (settlementMethodType == preSettlementMethodType) {
            if (HTypeSettlementMethodType.CONVENIENCE == settlementMethodType
                || HTypeSettlementMethodType.PAY_EASY == settlementMethodType) {
                // 修正前＆修正後の決済方法＝コンビニ決済 or Pay-easy決済
                return;
            } else if (HTypeSettlementMethodType.AMAZON_PAYMENT == settlementMethodType) {
                // 修正前＆修正後の決済方法＝AmazonPay決済
                if (preOrderPrice.compareTo(orderPrice) == 0) {
                    // 修正前の受注金額＝受注金額
                    return;
                }
            }
        }

        super.mulPayCommunicate(receiveOrderDto, preReceiveOrderDto);

    }

    /**
     * キャンセル受注の更新を行う<br/>
     * 受注キャンセルチェックを行わない
     *
     * @param receiveOrderDto 修正内容を保持した受注DTO
     */
    @Override
    public void executeCancelOrderUpdate(ReceiveOrderDto receiveOrderDto,
                                         String memberInfoId,
                                         HTypeSiteType siteType,
                                         HTypeDeviceType deviceType,
                                         Integer memberInfoSeq,
                                         String userAgent,
                                         String administratorLastName,
                                         String administratorFirstName) {
        super.cancelOrderUpdate = true;
        super.execute(receiveOrderDto, memberInfoId, siteType, deviceType, memberInfoSeq, userAgent,
                      administratorLastName, administratorFirstName
                     );
    }

}
