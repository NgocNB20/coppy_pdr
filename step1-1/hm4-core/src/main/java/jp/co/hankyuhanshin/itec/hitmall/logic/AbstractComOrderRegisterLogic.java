/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import com.gmo_pg.g_pay.client.output.EntryExecTranCvsOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranPayEasyOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockHoldLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockReleaseLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryRegistLogic;
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
 * 注文内容をもとに受注情報を登録する抽象クラス（通信機能付き）<br/>
 * 受注登録処理を行なう場合、このクラスを継承してください。<br/>
 * 受注登録処理の初期処理、後処理を意識することなく、受注登録処理の実装が行えます。<br/>
 *
 * @author ozaki
 * @author marutani チケット #2192 対応
 * @author tomo (itec) 2011/08/24 #2719「マルチペイメント決済通信エラーになると引き当てた在庫が開放されない」対応
 * @author matsumoto(itec) 2012/02/06 #2761 対応
 */
@Component
public abstract class AbstractComOrderRegisterLogic extends AbstractOrderRegisterLogic {

    /**
     * コンビニ取引登録・決済通信ロジック
     */
    private final ConveniEntryExecTranLogic conveniEntryExecTranLogic;

    /**
     * ペイジー取引登録・決済通信ロジック
     */
    private final PayeasyEntryExecTranLogic payeasyEntryExecTranLogic;

    /**
     * マルチペイメント請求ロジック
     */
    private final MulPayBillLogic mulPayBillLogic;

    @Autowired
    public AbstractComOrderRegisterLogic(ConversionUtility conversionUtility,
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
                                         MulPayBillLogic mulPayBillLogic) {
        super(conversionUtility, zenHanConversionUtility, dateUtility, orderUtility, mulPayUtility, newOrderSeqGetLogic,
              orderSummaryCountGetLogic, orderSummaryRegistLogic, orderPersonRegistLogic, orderDeliveryRegistLogic,
              orderSettlementRegistLogic, orderBillRegistLogic, orderReceiptOfMoneyRegistLogic, orderIndexRegistLogic,
              orderReserveStockHoldLogic, orderReserveStockReleaseLogic, orderGoodsListDeleteLogic, cardBrandGetLogic,
              cardBrandGetMaxCardBrandSeqLogic, cardBrandRegistLogic, deleteCardLogic, cardRegistUpdateLogic,
              creditEntryExecTranLogic, simultaneousOrderExclusionGetLogic, simultaneousOrderExclusionRegistLogic,
              simultaneousOrderExclusionUpdateLogic, saveMemberLogic, tradedCardLogic
             );
        this.conveniEntryExecTranLogic = conveniEntryExecTranLogic;
        this.payeasyEntryExecTranLogic = payeasyEntryExecTranLogic;
        this.mulPayBillLogic = mulPayBillLogic;
    }

    /**
     * ConveniEntryExecTranLogic をゲットする
     *
     * @return ConveniEntryExecTranLogic
     */
    public ConveniEntryExecTranLogic getConveniEntryExecTranLogic() {
        return this.conveniEntryExecTranLogic;
    }

    /**
     * PayeasyEntryExecTranLogic をゲットする
     * @return PayeasyEntryExecTranLogic
     */
    /**
     * ペイジー取引登録・決済通信ロジック
     */
    public PayeasyEntryExecTranLogic getPayeasyEntryExecTranLogic() {
        return this.payeasyEntryExecTranLogic;
    }

    /**
     * 受注情報登録の前処理。<br/>
     * 受注情報を登録する際の受注ＳＥＱ採番と在庫確保を行ない、<br/>
     * 決済ごとに通信処理を行ないます。
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, HTypeDeviceType deviceType) {
        // 前処理実行
        super.beforeProcess(receiveOrderDto, siteType, deviceType);

        // 通信処理実行
        communicate(receiveOrderDto);
    }

    /**
     * 通信処理を行い、結果を受注DTOにセットします。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicate(ReceiveOrderDto receiveOrderDto) {

        // 決済種別ごとに通信を開始する。
        communicateBySettlementType(receiveOrderDto);

        // MulPayBill取得
        Integer orderSeq = receiveOrderDto.getOrderSummaryEntity().getOrderSeq();
        Integer orderVersionNo = receiveOrderDto.getOrderSummaryEntity().getOrderVersionNo();
        MulPayBillEntity mulPayBillEntity =
                        mulPayBillLogic.getMulPayBillByOrderSeqAndOrderVersionNo(orderSeq, orderVersionNo);
        receiveOrderDto.setMulPayBillEntity(mulPayBillEntity);
    }

    /**
     * 決済方法ごとに通信処理を行ないます。<br/>
     * ・代引<br/>
     * ・入金確認<br/>
     * ・クレジット<br/>
     * ・コンビニ<br/>
     * ・Pay-easy<br/>
     * .AmazonPay<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicateBySettlementType(ReceiveOrderDto receiveOrderDto) {
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();

        if (settlementMethodType == HTypeSettlementMethodType.RECEIPT_PAYMENT) {
            // 決済種別＝代金引換の場合は、通信処理から抜ける
        } else if (settlementMethodType == HTypeSettlementMethodType.PRE_PAYMENT) {
            // 決済種別＝入金確認系の場合は、通信処理から抜ける
        } else if (settlementMethodType == HTypeSettlementMethodType.CREDIT) {
            // 決済種別＝クレジット決済の場合の処理
            communicateByCredit(receiveOrderDto);
        } else if (settlementMethodType == HTypeSettlementMethodType.CONVENIENCE) {
            // 決済種別＝コンビニ決済の場合の処理
            // PDRでは通らない想定
            communicateByConveni(receiveOrderDto);
        } else if (settlementMethodType == HTypeSettlementMethodType.PAY_EASY) {
            // 決済種別＝Pay-easyの場合の処理
            // PDRでは通らない想定
            communicateByPayEasy(receiveOrderDto);
        }
    }

    /**
     * クレジット取引登録を実行<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicateByCredit(ReceiveOrderDto receiveOrderDto) {
        // 取引＆決済実行
        EntryExecTranOutput output = super.getCreditEntryExecTranLogic().execute(receiveOrderDto, isSecure());

        if (output.getEntryTranOutput().isErrorOccurred() || output.getExecTranOutput().isErrorOccurred()) {

            // #2719
            // 在庫戻し処理を除去。
            // 通信処理呼び出しメソッドで在庫戻し処理は行わない。
            // 呼び出し元で確保した商品を在庫に戻す処理を行う。

            List<CheckMessageDto> checkMessageDtoList = super.getCreditEntryExecTranLogic().checkOutput(output);
            for (CheckMessageDto dto : checkMessageDtoList) {
                addErrorMessage(dto.getMessageId(), dto.getArgs());
            }

            // #2719
            // メール送信処理を除去。
            // ここではGMOが返したエラー内容を管理者に通知するメッセージは送信しない
            // 呼び出し元で受注登録に失敗したメールを管理者に送る。
            // GMOが返したエラーは専用のインタセプタが通知メールを送信している。

            throwMessage();
        }

        receiveOrderDto.getOrderTempDto().setAcsUrl(output.getExecTranOutput().getAcsUrl());
        receiveOrderDto.getOrderTempDto().setPaReq(output.getExecTranOutput().getPaReq());

        Integer memberInfoSeq = receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq();
        if (memberInfoSeq == null) {
            return;
        }

        // 3Dセキュアを必要とする注文の場合はここでは会員登録、カード登録は行わない
        if ("0".equals(output.getExecTranOutput().getAcs())) {
            OrderTempDto tmpDto = receiveOrderDto.getOrderTempDto();

            // 登録済みカードがあり、今回はそのカード使用しない かつ、別のカードを保存する場合、
            // 登録済みカードを削除する
            if (tmpDto.isRegistCredit() && !tmpDto.isUseRegistCardFlg() && tmpDto.isSaveFlg()) {
                getDeleteCardLogic().execute(receiveOrderDto);
            }
            // 登録済みカードがなく、カードを保存する場合、
            // 会員登録を行う
            if (!tmpDto.isRegistCredit() && tmpDto.isSaveFlg()) {
                getSaveMemberLogic().execute(receiveOrderDto);
            }
            // 今回はそのカード使用しない かつ、カードを保存する場合
            // 決済で利用したカードを登録する
            if (!tmpDto.isUseRegistCardFlg() && tmpDto.isSaveFlg()) {
                getTradedCardLogic().execute(receiveOrderDto, output.getExecTranOutput().getOrderId());
            }
        }
    }

    /**
     * 決済種別＝コンビニ決済　の場合の通信処理<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicateByConveni(ReceiveOrderDto receiveOrderDto) {

        // コンビニ登録・決済実行
        // ○コンビニ登録・決済実行結果登録 → コンビニ登録・決済実行の出力パラメータをマルチペイ請求情報に登録する
        EntryExecTranCvsOutput output = conveniEntryExecTranLogic.execute(receiveOrderDto);

        if (output.isErrorOccurred()) {

            // #2719
            // 在庫戻し処理を除去
            // 通信処理呼び出しメソッドで在庫戻し処理は行わない。
            // 呼び出し元で確保した商品を在庫に戻す処理を行う。

            List<CheckMessageDto> checkMessageDtoList = conveniEntryExecTranLogic.checkCsvOutput(output);
            for (CheckMessageDto dto : checkMessageDtoList) {
                addErrorMessage(dto.getMessageId());
            }

            // #2719
            // メール送信処理を除去。
            // ここではGMOが返したエラー内容を管理者に通知するメッセージは送信しない
            // 呼び出し元で受注登録に失敗したメールを管理者に送る。
            // GMOが返したエラーは専用のインタセプタが通知メールを送信している。

            throwMessage();
        }
    }

    /**
     * P決済種別＝Pay-easy決済　の場合の通信処理<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void communicateByPayEasy(ReceiveOrderDto receiveOrderDto) {

        // Pay-easy登録・決済実行
        // ○Pay-easy登録・決済実行結果登録 → Pay-easy登録・決済実行の出力パラメータをマルチペイ請求情報に登録する
        EntryExecTranPayEasyOutput output = payeasyEntryExecTranLogic.execute(receiveOrderDto);

        if (output.isErrorOccurred()) {

            // #2719
            // 在庫戻し処理を除去
            // 通信処理呼び出しメソッドで在庫戻し処理は行わない。
            // 呼び出し元で確保した商品を在庫に戻す処理を行う。

            List<CheckMessageDto> checkMessageDtoList = payeasyEntryExecTranLogic.checkPayEasyOutput(output);
            for (CheckMessageDto dto : checkMessageDtoList) {
                addErrorMessage(dto.getMessageId());
            }

            // #2719
            // メール送信処理を除去。
            // ここではGMOが返したエラー内容を管理者に通知するメッセージは送信しない
            // 呼び出し元で受注登録に失敗したメールを管理者に送る。
            // GMOが返したエラーは専用のインタセプタが通知メールを送信している。

            throwMessage();
        }
    }

    /**
     * ３Dセキュア利用かどうか判断
     * 各継承元画面にて実装
     *
     * @return true:３Dセキュア対象、false:対象でない
     */
    protected boolean isSecure() {
        return false;
    }
}
