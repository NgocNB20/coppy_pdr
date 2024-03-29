/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.PaymentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderReceiptOfMoneyRegistService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 入金登録処理<br/>
 *
 * @author yamaguchi
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 * @author Kaneko 2012/09/05 Enum廃止対応
 */
@Service
public class OrderReceiptOfMoneyRegistServiceImpl extends AbstractShopService
                implements OrderReceiptOfMoneyRegistService {

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * CheckMessageUtility
     */
    private final CheckMessageUtility checkMessageUtility;

    /**
     * 受注サマリー情報取得ロジック（排他制御あり）
     */
    private final OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic;

    /**
     * 受注インデックス取得ロジック
     */
    private final OrderIndexGetLogic orderIndexGetLogic;

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
     * 受注決済取得ロジック
     */
    private final OrderSettlementGetLogic orderSettlementGetLogic;

    @Autowired
    public OrderReceiptOfMoneyRegistServiceImpl(DateUtility dateUtility,
                                                CheckMessageUtility checkMessageUtility,
                                                OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic,
                                                OrderIndexGetLogic orderIndexGetLogic,
                                                OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic,
                                                OrderIndexRegistLogic orderIndexRegistLogic,
                                                OrderSummaryUpdateLogic orderSummaryUpdateLogic,
                                                OrderSettlementGetLogic orderSettlementGetLogic) {

        this.dateUtility = dateUtility;
        this.checkMessageUtility = checkMessageUtility;
        this.orderSummaryGetForUpdateLogic = orderSummaryGetForUpdateLogic;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderReceiptOfMoneyRegistLogic = orderReceiptOfMoneyRegistLogic;
        this.orderIndexRegistLogic = orderIndexRegistLogic;
        this.orderSummaryUpdateLogic = orderSummaryUpdateLogic;
        this.orderSettlementGetLogic = orderSettlementGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param paymentRegistDto 入金登録DTO<br />
     *                         入金登録DTO.受注SEQ （必須）<br/>
     *                         入金登録DTO.受注履歴連番 （必須）<br/>
     *                         入金登録DTO.入金日時 （任意）null値の場合、システム日時が設定される<br/>
     *                         入金登録DTO.入金金額 （任意）null値の場合、入金累計=受注金額になる入金金額が設定される<br/>
     * @return 処理件数
     */
    @Override
    public CheckMessageDto execute(PaymentRegistDto paymentRegistDto, String administratorName) {
        return execute(paymentRegistDto, null, administratorName);
    }

    /**
     * 実行メソッド<br/>
     * <p>
     * 受注入金エンティティ.受注SEQ （必須）<br/>
     * 受注入金エンティティ.受注履歴連番 （必須）<br/>
     * 受注入金エンティティ.入金日時 （任意）null値の場合、システム日時が設定される<br/>
     * 受注入金エンティティ.入金金額 （任意）null値の場合、入金累計=受注金額になる入金金額が設定される<br/>
     *
     * @param paymentRegistDto   入金情報
     * @param orderSummaryEntity 受注サマリ
     * @return 処理件数
     */
    @Override
    public CheckMessageDto execute(PaymentRegistDto paymentRegistDto,
                                   OrderSummaryEntity orderSummaryEntity,
                                   String administratorName) {
        // ショップSEQ取得
        Integer shopSeq = 1001;

        // レコードロック処理
        if (orderSummaryEntity == null) {
            // 受注サマリー取得（排他処理）
            orderSummaryEntity = orderSummaryGetForUpdateLogic.execute(paymentRegistDto.getOrderCode(),
                                                                       paymentRegistDto.getOrderVersionNo(), shopSeq
                                                                      );
        } else {
            if (paymentRegistDto.getOrderVersionNo() != null
                && paymentRegistDto.getOrderVersionNo().intValue() != orderSummaryEntity.getOrderVersionNo()
                                                                                        .intValue()) {
                throwMessage(MSGCD_ORDERVERSIONNO_DEF, new Object[] {orderSummaryEntity.getOrderCode()});
            }
        }

        if (orderSummaryEntity == null) {
            // orderSummaryEntityを取得できなかった場合エラーメッセージを設定
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {paymentRegistDto.getOrderCode()});
        }

        // 受注インデックスを取得
        OrderIndexEntity orderIndexEntity = orderIndexGetLogic.execute(orderSummaryEntity.getOrderSeq(),
                                                                       orderSummaryEntity.getOrderVersionNo()
                                                                      );
        if (orderIndexEntity == null) {
            // orderIndexEntityを取得できなかった場合エラーメッセージを設定
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {orderSummaryEntity.getOrderCode()});
        }

        // 受注決済情報を取得
        OrderSettlementEntity orderSettlementEntity = orderSettlementGetLogic.execute(orderIndexEntity.getOrderSeq(),
                                                                                      orderIndexEntity.getOrderSettlementVersionNo()
                                                                                     );
        if (orderSettlementEntity == null) {
            // orderIndexEntityを取得できなかった場合エラーメッセージを設定
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {orderSummaryEntity.getOrderCode()});
        }

        // 受注入金登録内容を作成
        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        setOrderReceiptOfMoneyData(orderSummaryEntity, orderIndexEntity, paymentRegistDto);
        if (orderReceiptOfMoneyEntity == null) {
            // orderReceiptOfMoneyEntityを取得できなかった場合エラーメッセージを設定
            throwMessage(MSGCD_ORDERINDEX_NULL, new Object[] {orderSummaryEntity.getOrderCode()});
        }

        if (BigDecimal.ZERO.equals(orderReceiptOfMoneyEntity.getReceiptPrice())) {
            return toCheckMessageDto(
                            MSGCD_RECEIPTPRICE_ZERO_INFO, new Object[] {orderSummaryEntity.getOrderCode()}, false);
        }

        // 最大入金額チェック
        if (orderReceiptOfMoneyEntity.getReceiptPriceTotal().compareTo(MAX_RECEIPT_TOTAL_PRICE) > 0) {
            throwMessage(MAX_RECEIPT_TOTAL_PRICE_OVER,
                         new Object[] {orderSummaryEntity.getOrderCode(), MAX_RECEIPT_TOTAL_PRICE}
                        );
        }

        // 最小入金額チェック(-99,999,999)
        if (orderReceiptOfMoneyEntity.getReceiptPriceTotal().compareTo(MIN_RECEIPT_TOTAL_PRICE) < 0) {
            throwMessage(MIN_RECEIPT_TOTAL_PRICE_OVER,
                         new Object[] {orderSummaryEntity.getOrderCode(), MIN_RECEIPT_TOTAL_PRICE}
                        );
        }

        // 受注インデックス登録内容を作成
        setOrderIndexData(orderIndexEntity, orderReceiptOfMoneyEntity, administratorName);

        // 受注サマリー更新内容作成
        setOrderSummaryData(orderSummaryEntity, orderReceiptOfMoneyEntity, orderSettlementEntity);

        // 受注入金を登録
        orderReceiptOfMoneyRegistLogic.execute(orderReceiptOfMoneyEntity);

        // 受注インデックスを登録
        orderIndexRegistLogic.execute(orderIndexEntity);

        // 受注サマリーを更新
        if (orderSummaryUpdateLogic.execute(orderSummaryEntity) == 0) {
            throwMessage(MSGCD_SUCCESS_ZERO, new Object[] {orderSummaryEntity.getOrderCode()});
        }
        return null;
    }

    /**
     * 受注入金情報設定<br/>
     *
     * @param orderSummaryEntity 更新前受注サマリー
     * @param orderIndexEntity   DB既存最新受注インデックス
     * @param paymentRegistDto   入金情報
     * @return 入金情報
     */
    protected OrderReceiptOfMoneyEntity setOrderReceiptOfMoneyData(OrderSummaryEntity orderSummaryEntity,
                                                                   OrderIndexEntity orderIndexEntity,
                                                                   PaymentRegistDto paymentRegistDto) {
        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                        ApplicationContextUtility.getBean(OrderReceiptOfMoneyEntity.class);
        // ショップSEQ
        orderReceiptOfMoneyEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 受注SEQ
        orderReceiptOfMoneyEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注サイト
        orderReceiptOfMoneyEntity.setOrderSiteType(orderSummaryEntity.getOrderSiteType());
        // 受注日時
        orderReceiptOfMoneyEntity.setOrderTime(orderSummaryEntity.getOrderTime());
        // 売上日時
        orderReceiptOfMoneyEntity.setSalesTime(orderSummaryEntity.getSalesTime());
        // 決済方法SEQ
        orderReceiptOfMoneyEntity.setSettlementMethodSeq(orderSummaryEntity.getSettlementMethodSeq());
        // 入金日時設定
        Timestamp receiptTime;
        if (paymentRegistDto.getReceiptTime() == null) {
            receiptTime = dateUtility.getCurrentTime();
        } else {
            receiptTime = paymentRegistDto.getReceiptTime();
        }
        orderReceiptOfMoneyEntity.setReceiptTime(receiptTime);
        orderReceiptOfMoneyEntity.setReceiptYm(dateUtility.formatYm(receiptTime));
        orderReceiptOfMoneyEntity.setReceiptYmd(dateUtility.formatYmd(receiptTime));
        // 入金金額設定
        if (paymentRegistDto.getReceiptPrice() != null) {
            orderReceiptOfMoneyEntity.setReceiptPrice(paymentRegistDto.getReceiptPrice());
            orderReceiptOfMoneyEntity.setReceiptPriceTotal(
                            orderSummaryEntity.getReceiptPriceTotal().add(paymentRegistDto.getReceiptPrice()));
        } else {
            orderReceiptOfMoneyEntity.setReceiptPrice(
                            orderSummaryEntity.getOrderPrice().subtract(orderSummaryEntity.getReceiptPriceTotal()));
            orderReceiptOfMoneyEntity.setReceiptPriceTotal(orderSummaryEntity.getOrderPrice());
        }
        // 受注入金連番
        int orderReceiptOfMoneyVersionNo = 1;
        if (orderIndexEntity.getOrderReceiptOfMoneyVersionNo() != null) {
            orderReceiptOfMoneyVersionNo = orderIndexEntity.getOrderReceiptOfMoneyVersionNo() + 1;
        }
        orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyVersionNo);
        return orderReceiptOfMoneyEntity;
    }

    /**
     * 受注インデックス情報設定<br/>
     *
     * @param orderIndexEntity          DB既存最新受注インデックス
     * @param orderReceiptOfMoneyEntity 登録受注入金情報
     */
    protected void setOrderIndexData(OrderIndexEntity orderIndexEntity,
                                     OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity,
                                     String administratorName) {
        // 受注入金連番
        orderIndexEntity.setOrderReceiptOfMoneyVersionNo(orderReceiptOfMoneyEntity.getOrderReceiptOfMoneyVersionNo());
        // 受注連番
        orderIndexEntity.setOrderVersionNo(orderIndexEntity.getOrderVersionNo() + 1);
        // 処理担当者名
        orderIndexEntity.setProcessPersonName(administratorName);
        // 処理時間
        orderIndexEntity.setProcessTime(dateUtility.getCurrentTime());
        // 処理区分（入金金額≧０：入金、入金金額＜０：返金）
        if (orderReceiptOfMoneyEntity.getReceiptPrice().compareTo(BigDecimal.ZERO) >= 0) {
            orderIndexEntity.setProcessType(HTypeProcessType.PAYMENT);
        } else {
            orderIndexEntity.setProcessType(HTypeProcessType.REPAYMENT);
        }
    }

    /**
     * 受注サマリー情報設定<br/>
     *
     * @param orderSummaryEntity        更新前受注サマリー
     * @param orderReceiptOfMoneyEntity 登録受注入金
     * @param orderSettlementEntity     受注決済
     */
    protected void setOrderSummaryData(OrderSummaryEntity orderSummaryEntity,
                                       OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity,
                                       OrderSettlementEntity orderSettlementEntity) {
        // 入金累計
        orderSummaryEntity.setReceiptPriceTotal(orderReceiptOfMoneyEntity.getReceiptPriceTotal());
        // 受注連番
        orderSummaryEntity.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo() + 1);
        // 受注状態
        if (HTypeOrderStatus.PAYMENT_CONFIRMING.equals(orderSummaryEntity.getOrderStatus())
            && orderSummaryEntity.getReceiptPriceTotal().compareTo(orderSummaryEntity.getOrderPrice()) >= 0) {
            // （入金確認中→商品準備中）
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.GOODS_PREPARING);
        } else if (HTypeBillType.PRE_CLAIM.equals(orderSettlementEntity.getBillType())
                   && HTypeOrderStatus.GOODS_PREPARING.equals(orderSummaryEntity.getOrderStatus())
                   && orderSummaryEntity.getReceiptPriceTotal().compareTo(orderSummaryEntity.getOrderPrice()) < 0) {
            // （商品準備中→入金確認中）
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.PAYMENT_CONFIRMING);
        }
    }

    /**
     * エラー内容からメッセージリスト作成<br/>
     *
     * @param msgCode メッセージコード
     * @param args    引数
     * @param isError エラーフラグ
     * @return エラーメッセージ情報
     */
    protected CheckMessageDto toCheckMessageDto(String msgCode, Object[] args, boolean isError) {
        CheckMessageDto checkMessageDto = checkMessageUtility.createCheckMessageDto(msgCode, isError, args);
        return checkMessageDto;
    }

}
