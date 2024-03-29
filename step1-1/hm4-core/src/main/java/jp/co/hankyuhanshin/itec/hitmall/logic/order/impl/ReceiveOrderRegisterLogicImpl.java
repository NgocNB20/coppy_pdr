/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractComOrderRegisterLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ConveniEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.DeleteCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.PayeasyEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.TradedCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockHoldLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockReleaseLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderRegisterLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新規受注登録ロジック<br/>
 *
 * @author ozaki
 * @version $Revision: 1.0 $
 */
@Component
public class ReceiveOrderRegisterLogicImpl extends AbstractComOrderRegisterLogic implements ReceiveOrderRegisterLogic {

    /**
     * 受注メモ登録ロジック
     */
    private final OrderMemoRegistLogic orderMemoRegistLogic;

    @Autowired
    public ReceiveOrderRegisterLogicImpl(ConversionUtility conversionUtility,
                                         ZenHanConversionUtility zenHanConversionUtility,
                                         DateUtility dateUtility,
                                         OrderUtility orderUtility,
                                         MulPayUtility mulPayUtility,
                                         NewOrderSeqGetLogic newOrderSeqGetLogic,
                                         OrderSummaryCountGetLogic orderSummaryCountGetLogic,
                                         OrderSummaryRegistLogic orderSummaryRegistLogic,
                                         OrderPersonRegistLogic orderPersonRegistLogic,
                                         OrderDeliveryRegistLogic orderDeliveryRegistLogic,
                                         OrderSettlementRegistLogic orderSettlementRegistLogic,
                                         OrderBillRegistLogic orderBillRegistLogic,
                                         OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic,
                                         OrderIndexRegistLogic orderIndexRegistLogic,
                                         OrderReserveStockHoldLogic orderReserveStockHoldLogic,
                                         OrderReserveStockReleaseLogic orderReserveStockReleaseLogic,
                                         OrderGoodsListDeleteLogic orderGoodsListDeleteLogic,
                                         CardBrandGetLogic cardBrandGetLogic,
                                         CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic,
                                         CardBrandRegistLogic cardBrandRegistLogic,
                                         DeleteCardLogic deleteCardLogic,
                                         CardRegistUpdateLogic cardRegistUpdateLogic,
                                         CreditEntryExecTranLogic creditEntryExecTranLogic,
                                         SimultaneousOrderExclusionGetLogic simultaneousOrderExclusionGetLogic,
                                         SimultaneousOrderExclusionRegistLogic simultaneousOrderExclusionRegistLogic,
                                         SimultaneousOrderExclusionUpdateLogic simultaneousOrderExclusionUpdateLogic,
                                         SaveMemberLogic saveMemberLogic,
                                         TradedCardLogic tradedCardLogic,
                                         ConveniEntryExecTranLogic conveniEntryExecTranLogic,
                                         PayeasyEntryExecTranLogic payeasyEntryExecTranLogic,
                                         MulPayBillLogic mulPayBillLogic,
                                         OrderMemoRegistLogic orderMemoRegistLogic) {
        super(conversionUtility, zenHanConversionUtility, dateUtility, orderUtility, mulPayUtility, newOrderSeqGetLogic,
              orderSummaryCountGetLogic, orderSummaryRegistLogic, orderPersonRegistLogic, orderDeliveryRegistLogic,
              orderSettlementRegistLogic, orderBillRegistLogic, orderReceiptOfMoneyRegistLogic, orderIndexRegistLogic,
              orderReserveStockHoldLogic, orderReserveStockReleaseLogic, orderGoodsListDeleteLogic, cardBrandGetLogic,
              cardBrandGetMaxCardBrandSeqLogic, cardBrandRegistLogic, deleteCardLogic, cardRegistUpdateLogic,
              creditEntryExecTranLogic, simultaneousOrderExclusionGetLogic, simultaneousOrderExclusionRegistLogic,
              simultaneousOrderExclusionUpdateLogic, saveMemberLogic, tradedCardLogic, conveniEntryExecTranLogic,
              payeasyEntryExecTranLogic, mulPayBillLogic
             );
        this.orderMemoRegistLogic = orderMemoRegistLogic;
    }

    // #2220 START

    /**
     * 受注インデックス登録
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void registOrderIndex(ReceiveOrderDto receiveOrderDto,
                                    String administratorLastName,
                                    String administratorFirstName) {

        // 受注メモ情報が存在する場合、履歴番号を受注インデックスにセットしておく。
        if (receiveOrderDto.getOrderMemoEntity() != null) {
            // 受注インデックスエンティティを作成
            OrderIndexEntity orderIndexEntity = receiveOrderDto.getOrderIndexEntity();
            if (orderIndexEntity == null) {
                receiveOrderDto.setOrderIndexEntity(getComponent(OrderIndexEntity.class));
                orderIndexEntity = receiveOrderDto.getOrderIndexEntity();
            }

            orderIndexEntity.setOrderMemoVersionNo(receiveOrderDto.getOrderMemoEntity().getOrderMemoVersionNo());
        }

        // 受注インデックス登録処理を実行する。
        super.registOrderIndex(receiveOrderDto, administratorLastName, administratorFirstName);
    }

    // #2220 START

    /**
     * その他情報を登録（サブクラスで実装）
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void registOther(ReceiveOrderDto receiveOrderDto) {
        // 受注メモ登録
        if (receiveOrderDto.getOrderMemoEntity() != null) {
            OrderMemoEntity orderMemoEntity = receiveOrderDto.getOrderMemoEntity();
            orderMemoEntity.setOrderSeq(receiveOrderDto.getOrderSummaryEntity().getOrderSeq());
            orderMemoEntity.setOrderMemoVersionNo(1);

            // 「受注メモ登録ロジック」実行
            orderMemoRegistLogic.execute(orderMemoEntity);
        }
    }

}
