/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverTimeZoneJapanPost;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverTimeZoneYamato;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.result.ComResultPaygentDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractComOrderRegisterLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo.CardInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ConveniEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.DeleteCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.PayeasyEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.TradedCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPendingCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderRegisterLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockHoldLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockReleaseLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * 注文登録ロジック<br/>
 *
 * @author ozaki
 * @author tomo (itec) 2011/08/24 #2719「マルチペイメント決済通信エラーになると引き当てた在庫が開放されない」対応
 * @author Kaneko (itec) 2011/09/08 #2726　対応
 * @author satoh
 */
@Component
public class OrderRegisterLogicImpl extends AbstractComOrderRegisterLogic implements OrderRegisterLogic {

    // Paygent Customization from here
    /**
     * カード情報操作（取得・登録・削除）ロジック
     */
    private final CardInfoLogic cardInfoLogic;

    /**
     * クレジットカード用通信ロジック
     */
    private final CreditTranLogic creditTranLogic;

    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;
    // Paygent Customization to here

    // PDR Migrate Customization from here
    /**
     * 注文保留チェックロジック
     */
    private final OrderPendingCheckLogic orderPendingCheckLogic;
    // PDR Migrate Customization to here

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRegisterLogicImpl.class);

    @Autowired
    public OrderRegisterLogicImpl(ConversionUtility conversionUtility,
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
                                  CardInfoLogic cardInfoLogic,
                                  CreditTranLogic creditTranLogic,
                                  ComTransactionUtility comTransactionUtility,
                                  OrderPendingCheckLogic orderPendingCheckLogic) {
        super(conversionUtility, zenHanConversionUtility, dateUtility, orderUtility, mulPayUtility, newOrderSeqGetLogic,
              orderSummaryCountGetLogic, orderSummaryRegistLogic, orderPersonRegistLogic, orderDeliveryRegistLogic,
              orderSettlementRegistLogic, orderBillRegistLogic, orderReceiptOfMoneyRegistLogic, orderIndexRegistLogic,
              orderReserveStockHoldLogic, orderReserveStockReleaseLogic, orderGoodsListDeleteLogic, cardBrandGetLogic,
              cardBrandGetMaxCardBrandSeqLogic, cardBrandRegistLogic, deleteCardLogic, cardRegistUpdateLogic,
              creditEntryExecTranLogic, simultaneousOrderExclusionGetLogic, simultaneousOrderExclusionRegistLogic,
              simultaneousOrderExclusionUpdateLogic, saveMemberLogic, tradedCardLogic, conveniEntryExecTranLogic,
              payeasyEntryExecTranLogic, mulPayBillLogic
             );

        // Paygent Customization from here
        this.cardInfoLogic = cardInfoLogic;
        this.creditTranLogic = creditTranLogic;
        this.comTransactionUtility = comTransactionUtility;
        // Paygent Customization to here

        // PDR Migrate Customization from here
        this.orderPendingCheckLogic = orderPendingCheckLogic;
        // PDR Migrate Customization to here
    }

    /**
     * ３Dセキュア利用かどうか判断
     * 各継承元画面にて実装
     *
     * @return システム設定値
     */
    @Override
    protected boolean isSecure() {
        return true;
    }

    // Paygent Customization from here

    /**
     * クレジット取引登録を実行<br/>
     * <pre>
     * オーソリ実行フラグを追加
     * 決済ID 追加
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void communicateByCredit(ReceiveOrderDto receiveOrderDto) {
        // PDR Migrate Customization from here
        // オーソリ実行フラグ
        if (!receiveOrderDto.isExecuteAuthori()) {
            // 実行しない場合は 処理終了
            return;
        }
        // PDR Migrate Customization to here

        // 3Dセキュア利用可能かどうか
        boolean enable3dSecure = false;
        if (isSecure() && HTypeEffectiveFlag.VALID == receiveOrderDto.getSettlementMethodEntity().getEnable3dSecure()) {
            enable3dSecure = true;
        }

        // 取引＆決済実行
        ComResultDto result = creditTranLogic.doEntryExecTran(receiveOrderDto, enable3dSecure);

        if (comTransactionUtility.isErrorOccurred(result)) {
            for (CheckMessageDto dto : result.getMessageList()) {
                addErrorMessage(dto.getMessageId(), dto.getArgs());
            }
            throwMessage();
        }

        // PDR Migrate Customization from here
        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
        orderTempDto.setDispHtml(result.getResultMap().get("out_acs_html"));
        // 決済ID
        orderTempDto.setPaymentId(result.getResultMap().get(ComResultPaygentDto.KEY_PAYMENT_ID));
        // PDR Migrate Customization to here
    }

    /**
     * 受注情報登録の後処理<br/>
     * カード情報預かり登録・削除対応
     *
     * @param receiveOrderDto 受注DTO
     * @param memberInfoSeq 会員SEQ
     */
    @Override
    protected void afterProcess(ReceiveOrderDto receiveOrderDto, Integer memberInfoSeq) {
        super.afterProcess(receiveOrderDto, memberInfoSeq);
        cardInfoProcess(receiveOrderDto);

        // PDR Migrate Customization from here
        // 注文情報の保留チェック
        orderPendingCheckLogic.checkOrderPending(receiveOrderDto, memberInfoSeq);
        // PDR Migrate Customization to here
    }

    /**
     * カード情報の 登録/更新 or 削除 を行う
     * <pre>
     * 以下の場合に処理する
     * ・クレジットカード決済
     * ・保持カードでの注文でない（カード番号を入力しての注文）
     * </pre>
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void cardInfoProcess(ReceiveOrderDto receiveOrderDto) {
        // PDR Migrate Customization from here
        if (HTypeSettlementMethodType.CREDIT != receiveOrderDto.getSettlementMethodEntity().getSettlementMethodType()
            || !receiveOrderDto.isExecuteAuthori()) {
            // クレジットカード決済以外 又は オーソリ実行していない 場合は処理しない
            return;
        }
        // 3Dセキュアの場合は3Dセキュア認証後に処理を行う 3Dセキュアは行わないため削除
        // PDR Migrate Customization to here

        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
        if (orderTempDto.isUseRegistCardFlg()) {
            // 保持カードでの注文の場合は処理しない
            return;
        }

        //カード保存フラグが設定されている場合、カード情報を更新する
        if (orderTempDto.isSaveFlg()) {
            // カード情報を登録する
            cardInfoLogic.registCardInfo(receiveOrderDto, true);
        }
    }

    // Paygent Customization to here
    // PDR Migrate Customization from here

    /**
     * 受注情報登録の前処理。<br/>
     * 受注情報を登録する際の受注ＳＥＱ採番と在庫確保を行ないます。<br/>
     * <pre>
     * 在庫確保処理 削除
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, HTypeDeviceType deviceType) {

        // パラメータチェック
        argumentCheck(receiveOrderDto);

        // 受注SEQ採番
        getNewOrderSeq(receiveOrderDto, siteType, deviceType);

        // PDR Migrate Customization from here
        // 受注商品の登録
        orderReserveStockHoldLogic.registOrderGoods(receiveOrderDto);

        // 通信処理実行
        communicate(receiveOrderDto);
        // PDR Migrate Customization to here
    }

    /**
     * 決済方法ごとに通信処理を行ないます。<br/>
     * ・代引<br/>
     * ・入金確認<br/>
     * ・クレジット<br/>
     * ・コンビニ<br/>
     * ・Pay-easy<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicateBySettlementType(ReceiveOrderDto receiveOrderDto) {
        super.communicateBySettlementType(receiveOrderDto);
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
        // PDR Migrate Customization from here
        if (settlementMethodType == HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER) {
            // 決済種別＝コンビニ・郵便振込の場合は、通信処理から抜ける
        } else if (settlementMethodType == HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL) {
            // 決済種別＝口座自動引落の場合は、通信処理から抜ける
        } else if (settlementMethodType == HTypeSettlementMethodType.MONTHLY_BILLING) {
            // 決済種別＝月締請求の場合は、通信処理から抜ける
        }
        // PDR Migrate Customization to here
    }

    /**
     * 受注情報登録処理<br/>
     * 注文内容をもとに受注情報の登録を行ないます。<br/>
     * このメソッドは、前処理で受注番号の採番と在庫確保が行なわれていることが前提です。
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void process(ReceiveOrderDto receiveOrderDto,
                           String memberInfoId,
                           HTypeSiteType siteType,
                           Integer memberInfoSeq,
                           String userAgent,
                           String administratorLastName,
                           String administratorFirstName) {

        try {

            // PDR Migrate Customization from here
            // 定期注文の場合の処理は削除
            // PDR Migrate Customization from here

            // 受注サマリー登録
            registOrderSummary(receiveOrderDto, memberInfoId, siteType, memberInfoSeq, userAgent);
            // 受注ご注文主登録
            registOrderPerson(receiveOrderDto);
            // 受注配送登録
            registOrderDelivery(receiveOrderDto);
            // 受注決済登録
            registOrderSettlement(receiveOrderDto);
            // 受注請求登録
            registOrderBill(receiveOrderDto);
            // 受注入金登録（前請求のクレジット決済の場合のみ処理を行なう）
            HTypeSettlementMethodType settlementMethodType =
                            receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
            HTypeBillType billType = receiveOrderDto.getOrderSettlementEntity().getBillType();
            if (settlementMethodType.equals(HTypeSettlementMethodType.CREDIT) && billType.equals(
                            HTypeBillType.PRE_CLAIM)) {
                registOrderReceiptOfMoney(receiveOrderDto);
            }
            // その他受注情報登録
            registOther(receiveOrderDto);
            // 受注インデックス登録
            registOrderIndex(receiveOrderDto, administratorLastName, administratorFirstName);
            // PDR Migrate Customization from here
            // カードブランド登録（クレジット決済の場合のみ処理を行なう） 削除
            // PDR Migrate Customization to here
            // クーポン利用がない場合は処理終了
            if (orderUtility.isMember(memberInfoId, siteType, receiveOrderDto) && receiveOrderDto.getCoupon() != null) {
                // 同時注文排他チェックを行う
                checkSimultaneousOrderExclusion(receiveOrderDto);
                // 同時注文排他情報を登録、更新する
                registUpdateSimultaneousOrderExclusion(receiveOrderDto);
            }

        } catch (Exception e) {

            // #2719
            // 在庫戻し処理を除去。
            // 受注登録メソッド内で在庫戻し処理は行わない。
            // 呼び出し元で確保した商品を在庫に戻す処理を行う。

            // #2719
            // メール送信処理を除去。
            // ここではGMOが返したエラー内容を管理者に通知するメッセージは送信しない
            // 呼び出し元で受注登録に失敗したメールを管理者に送る。

            // エラーメッセージが登録済みかつAppLevelListException型の場合は、そのままスロー
            if (e instanceof AppLevelListException) {
                throw (AppLevelListException) e;
            }

            // 同時注文排他エラー
            if (e instanceof DataAccessException) {
                throwMessage(MSGCD_SIMULTANEOUSORDEREXCLUSION_ERR);
            }

            LOGGER.error("注文登録処理でエラーが発生しました。登録した受注商品と、引き当てた在庫を戻します。", e);
            throwMessage(MSGCD_ORDER_REGIST_FAIL);
        }
    }

    /**
     * 受注SEQ採番<br/>
     * 受注SEQを採番し、受注サマリに設定する。<br/>
     * <pre>
     * 受注コード、現在日時の設定削除
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void getNewOrderSeq(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, HTypeDeviceType deviceType) {
        // 受注SEQ採番
        Integer newOrderSeq = newOrderSeqGetLogic.execute();
        // PDR Migrate Customization from here
        // 現在日時取得 削除
        // 受注コード作成 削除
        // 別の場所で設定するため
        // PDR Migrate Customization to here
        // キャリア種別取得
        HTypeCarrierType carrierType = null;
        if (siteType.isFront()) {
            // フロントサイトの場合登録直前にPCサイトとSPサイトが切替される場合がある為、再度サイト種別を取得しなおす
            receiveOrderDto.getOrderSummaryEntity().setOrderSiteType(siteType);
            receiveOrderDto.getOrderSummaryEntity().setOrderDeviceType(deviceType);
        } else {
            // バックからの注文時はPC
            carrierType = HTypeCarrierType.PC;
        }
        // 受注履歴連番
        Integer orderVersionNo = 1;

        // 取得した情報＆初期値を受注サマリーにセット
        // PDR Migrate Customization from here
        receiveOrderDto.getOrderSummaryEntity().setOrderSeq(newOrderSeq);
        // 受注コード設定 削除
        receiveOrderDto.getOrderSummaryEntity().setCarrierType(carrierType);
        // 受注日設定 削除
        receiveOrderDto.getOrderSummaryEntity().setOrderVersionNo(orderVersionNo);
        receiveOrderDto.getOrderSummaryEntity().setReceiptPriceTotal(new BigDecimal(0));
        // PDR Migrate Customization to here
    }

    /**
     * 受注配送登録
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void registOrderDelivery(ReceiveOrderDto receiveOrderDto) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        // 注文DTOから受注配送エンティティを取得
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();
        OrderDeliveryEntity orderDeliveryEntity = orderDeliveryDto.getOrderDeliveryEntity();
        // ショップSEQ
        orderDeliveryEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 受注SEQ
        orderDeliveryEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注配送連番
        orderDeliveryEntity.setOrderDeliveryVersionNo(1);
        // 予約配送フラグ
        orderDeliveryEntity.setReservationDeliveryFlag(
                        orderUtility.getReservationDeliveryFlag(receiveOrderDto, orderDeliveryDto));

        // PDR Migrate Customization from here
        // 都道府県：固定値「東京」に設定
        orderDeliveryEntity.setReceiverPrefecture(HTypePrefectureType.TOKYO.getLabel());
        // 情報をコピーする
        OrderDeliveryEntity copyOrderDeliveryEntity = CopyUtil.deepCopy(orderDeliveryDto.getOrderDeliveryEntity());
        // 「お届け先住所-方書1」に値が設定されていた場合は「お届け先住所-それ以降の住所」に結合
        copyOrderDeliveryEntity.setReceiverAddress3(
                        conversionUtility.toSpaceConnect(orderDeliveryEntity.getReceiverAddress3(),
                                                         orderDeliveryEntity.getReceiverAddress4()
                                                        ));

        // お届け希望日のラベル値を設定
        if (!StringUtils.isEmpty(copyOrderDeliveryEntity.getReceiverTimeZone())
            && orderDeliveryDto.getDeliveryInformationDetailDto() != null) {
            if (HTypeDeliveryType.YAMATO.getValue()
                                        .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                .getDeliveryCompanyCode())
                // 2023-renew No14 from here
                || HTypeDeliveryType.AUTOMATIC.getValue()
                                              .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                      .getDeliveryCompanyCode())) {
                // ヤマト 又は 自動設定
                // 2023-renew No14 to here
                copyOrderDeliveryEntity.setReceiverTimeZone(
                                EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneYamato.class,
                                                              orderDeliveryDto.getOrderDeliveryEntity()
                                                                              .getReceiverTimeZone()
                                                             ).getLabel());
            }
            if (HTypeDeliveryType.JAPANPOST.getValue()
                                           .equals(orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                   .getDeliveryCompanyCode())) {
                // 日本郵便
                copyOrderDeliveryEntity.setReceiverTimeZone(
                                EnumTypeUtil.getEnumFromValue(HTypeReceiverTimeZoneJapanPost.class,
                                                              orderDeliveryDto.getOrderDeliveryEntity()
                                                                              .getReceiverTimeZone()
                                                             ).getLabel());
            }
        }

        if (orderDeliveryDto.getDeliveryInformationDetailDto() != null) {
            // 配送方法備考に、連携した配送方法を設定
            copyOrderDeliveryEntity.setDeliveryNote(EnumTypeUtil.getEnumFromValue(HTypeDeliveryType.class,
                                                                                  orderDeliveryDto.getDeliveryInformationDetailDto()
                                                                                                  .getDeliveryCompanyCode()
                                                                                 ).getLabel());
        }

        // 受注配送登録ロジック実行
        orderDeliveryRegistLogic.execute(copyOrderDeliveryEntity);
        // PDR Migrate Customization to here
    }

    /**
     * 受注決済登録
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void registOrderSettlement(ReceiveOrderDto receiveOrderDto) {
        // PDR Migrate Customization from here
        // 送料
        receiveOrderDto.getOrderSettlementEntity()
                       .setCarriage(receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().getCarriage());
        // PDR Migrate Customization to here
        super.registOrderSettlement(receiveOrderDto);
    }

    // PDR Migrate Customization to here
}


