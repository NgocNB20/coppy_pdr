/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractOrderRegisterLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.DeleteCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.TradedCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockHoldLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockReleaseLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.SecureOrderRegisterLogic;
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

import java.util.List;

/**
 * 認証後注文登録ロジック<br/>
 *
 * @author ozaki
 * @author Nishigaki Mio (itec) 2011/03/30 3Dセキュア認証画面を非ポップアップで表示する対応
 * @author tomo (itec) 2011/08/24 #2719「マルチペイメント決済通信エラーになると引き当てた在庫が開放されない」対応
 * @author Kaneko (itec) 2011/09/08 #2726　対応
 */
@Component
public class SecureOrderRegisterLogicImpl extends AbstractOrderRegisterLogic implements SecureOrderRegisterLogic {

    /**
     * 受注商品 DAO
     */
    private final OrderGoodsDao orderGoodsDao;

    /**
     * マルチペイメント請求ロジック
     */
    private final MulPayBillLogic mulPayBillLogic;

    @Autowired
    public SecureOrderRegisterLogicImpl(ConversionUtility conversionUtility,
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
                                        OrderGoodsDao orderGoodsDao,
                                        MulPayBillLogic mulPayBillLogic) {
        super(conversionUtility, zenHanConversionUtility, dateUtility, orderUtility, mulPayUtility, newOrderSeqGetLogic,
              orderSummaryCountGetLogic, orderSummaryRegistLogic, orderPersonRegistLogic, orderDeliveryRegistLogic,
              orderSettlementRegistLogic, orderBillRegistLogic, orderReceiptOfMoneyRegistLogic, orderIndexRegistLogic,
              orderReserveStockHoldLogic, orderReserveStockReleaseLogic, orderGoodsListDeleteLogic, cardBrandGetLogic,
              cardBrandGetMaxCardBrandSeqLogic, cardBrandRegistLogic, deleteCardLogic, cardRegistUpdateLogic,
              creditEntryExecTranLogic, simultaneousOrderExclusionGetLogic, simultaneousOrderExclusionRegistLogic,
              simultaneousOrderExclusionUpdateLogic, saveMemberLogic, tradedCardLogic
             );
        this.orderGoodsDao = orderGoodsDao;
        this.mulPayBillLogic = mulPayBillLogic;
    }

    /**
     * 受注情報登録の前処理。<br/>
     * 受注情報を登録する際の受注ＳＥＱ採番と在庫確保を行ないます。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, HTypeDeviceType deviceType) {
        // 受注商品情報が取得できない場合はエラー
        List<OrderGoodsEntity> orderGoodsList =
                        orderGoodsDao.getOrderGoodsList(receiveOrderDto.getOrderSummaryEntity().getOrderSeq(),
                                                        receiveOrderDto.getOrderDeliveryDto()
                                                                       .getOrderGoodsEntityList()
                                                                       .get(0)
                                                                       .getOrderGoodsVersionNo()
                                                       );
        if (orderGoodsList == null || orderGoodsList.size() == 0) {
            // 在庫が解放されている場合はエラー
            throwMessage(MSGCD_STOCK_RELASED_FAIL);
        }

        // MulPayBill取得
        Integer orderSeq = receiveOrderDto.getOrderSummaryEntity().getOrderSeq();
        Integer orderVersionNo = receiveOrderDto.getOrderSummaryEntity().getOrderVersionNo();
        MulPayBillEntity mulPayBillEntity =
                        mulPayBillLogic.getMulPayBillByOrderSeqAndOrderVersionNo(orderSeq, orderVersionNo);
        receiveOrderDto.setMulPayBillEntity(mulPayBillEntity);
    }

}
