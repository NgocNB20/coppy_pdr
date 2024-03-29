/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmPaymentClientInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ComTransactionLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditCardInfoTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 * 【決済オプション部品（ペイジェント）】<br />
 * クレジットカード用通信処理
 * <pre>
 * 仕様はインタフェースを参照
 * </pre>
 *
 * @author satoh
 */
@Component
// Paygent Customization from here
public class CreditTranLogicImpl extends AbstractShopLogic implements CreditTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditTranLogicImpl.class);

    /**
     * ペイジェントとの通信APIのラッパー
     */
    private final ComTransactionLogic comTransactionLogic;

    /**
     * 通信Utility
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * クレジットカード情報お預かり用通信処理
     */
    private final CreditCardInfoTranLogic creditCardInfoTranLogic;

    /**
     * マルチペイメントUtility
     */
    private final MulPayUtility mulPayUtility;

    /**
     * マルチペイメント請求Logic
     */
    private final MulPayBillLogic mulPayBillLogic;

    /**
     * HTTP_USER_AGENT
     */
    public static final String HTTP_ACCEPT = "Accept";
    /**
     * HTTP_USER_AGENT
     */
    public static final String HTTP_USER_AGENT = "User-Agent";

    // PDR Migrate Customization from here
    /**
     * マーチャント取引ID 付与連番(固定)
     */
    public static final String TRADING_ID_SERIAL_NUMBER = "_001";
    // PDR Migrate Customization to here

    @Autowired
    public CreditTranLogicImpl(ComTransactionLogic comTransactionLogic,
                               ComTransactionUtility comTransactionUtility,
                               CreditCardInfoTranLogic creditCardInfoTranLogic,
                               MulPayUtility mulPayUtility,
                               MulPayBillLogic mulPayBillLogic) {
        this.comTransactionLogic = comTransactionLogic;
        this.comTransactionUtility = comTransactionUtility;
        this.creditCardInfoTranLogic = creditCardInfoTranLogic;
        this.mulPayUtility = mulPayUtility;
        this.mulPayBillLogic = mulPayBillLogic;
    }

    @Override
    public ComResultDto doEntryExecTran(ReceiveOrderDto dto, boolean enable3dSecure) {

        // オーソリ
        try {

            /*
             * 前処理
             */
            // オーソリ用リクエスト情報を作成する
            ComRequestDto request = getExecTranInputAuthori(dto, enable3dSecure);

            // マルチペイメント請求登録
            MulPayBillEntity mulPayBill =
                            mulPayUtility.getMulPayBillEntity(request, null, MulPayUtility.TRANTYPE_ENTRY);
            mulPayBillLogic.registAnotherTran(mulPayBill);

            /*
             * 本処理
             */
            // 通信
            ComResultDto result = comTransactionLogic.execute(request);

            /*
             * 後処理
             */
            // EntryTranを更新
            MulPayBillEntity updMulPayBill = getUpdMulPayBillEntity(request.getOrderSeq(), result);
            mulPayBillLogic.updateAnotherTran(updMulPayBill);

            // マルチペイメント請求登録
            mulPayBill = mulPayUtility.getMulPayBillEntity(request, result, MulPayUtility.TRANTYPE_EXEC);
            mulPayBillLogic.registAnotherTran(mulPayBill);

            // ペイジェントエラーメッセージをHIT-MALL用のメッセージに変換する
            List<CheckMessageDto> messageList =
                            comTransactionUtility.checkResultOutput(result, ComTransactionUtility.MSG_TYPE_PAYMENT);

            // 戻り値に変換したエラーメッセージを設定する
            return comTransactionUtility.makeOutput(result, messageList);

        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(CreditEntryExecTranLogic.MSGCD_PAYMENT_COM_FAIL, null, e);
        }

        // 非到達
        return null;
    }

    /**
     * オーソリ用リクエスト情報を作成する
     * <pre>
     * 顧客カード番号をDBからではなく入力値から取得
     * </pre>
     *
     * @param dto            受注Dto
     * @param enable3dSecure true=有効にする。false=無効にする。
     * @return リクエスト情報
     */
    protected ComRequestDto getExecTranInputAuthori(ReceiveOrderDto dto, boolean enable3dSecure) {

        ComRequestDto input = ApplicationContextUtility.getBean(ComRequestDto.class);
        OrderSummaryEntity orderSummaryEntity = dto.getOrderSummaryEntity();
        OrderTempDto orderTempDto = dto.getOrderTempDto();

        // 受注Dtoから受注情報をセット
        comTransactionUtility.setReceiveOrderInfo(input, dto);

        // トークン　※カード預かり用ではない方
        String token = orderTempDto.getToken();

        // 処理区分
        input.setJobCd(HTypeJobCode.AUTH);

        // 電文種別ID
        input.setVal("telegram_kind", ComTransactionUtility.KIND_AUTHORI);

        // PDR Migrate Customization from here
        // 決済金額
        input.setVal("payment_amount", orderSummaryEntity.getOrderPrice().stripTrailingZeros().toPlainString());
        // マーチャント取引ID
        input.setVal("trading_id", orderSummaryEntity.getOrderCode() + TRADING_ID_SERIAL_NUMBER);
        // PDR Migrate Customization to here

        if (orderTempDto.isUseRegistCardFlg()) {

            // カード情報お預りモード
            input.setVal("stock_card_mode", "1");

            // 顧客ID
            input.setVal("customer_id", orderTempDto.getPaymentMemberId());

            // PDR Migrate Customization from here
            // 顧客カードID
            input.setVal("customer_card_id", orderTempDto.getCustomerCardId());
            // PDR Migrate Customization to here

            // セキュリティカードトークン利用設定
            // PDR様ではセキュリティカードトークン利用しない
            //if (StringUtil.isNotEmpty(token)) {
            //    input.setVal("card_token", token);
            //    input.setVal("security_code_token", "1");
            //}
            // PDR Migrate Customization to here

        } else {

            if (StringUtil.isEmpty(token)) {
                // カード番号
                input.setVal("card_number", orderTempDto.getCardNo());

                // カード有効期限
                String cardValidTerm = null;
                if (!StringUtil.isEmpty(orderTempDto.getExpireMonth()) && !StringUtil.isEmpty(
                                orderTempDto.getExpireYear())) {
                    cardValidTerm = orderTempDto.getExpireMonth() + orderTempDto.getExpireYear();
                }
                input.setVal("card_valid_term", cardValidTerm);
            } else {
                input.setVal("card_token", token);
                input.setVal("security_code_token", "0");
            }
        }

        // カード確認番号
        input.setVal("card_conf_number", orderTempDto.getSecurityCode());

        // 支払区分・分割回数
        // 分割払いの場合
        if (HmPaymentClientInput.METHOD_INSTALLMENT.equals(orderTempDto.getMethod())) {
            input.setVal("payment_class", ComTransactionUtility.PAYGENT_METHOD_INSTALLMENT);
            input.setVal("split_count", String.valueOf(orderTempDto.getPayTimes()));
        }
        // ボーナス1回払いの場合
        else if (HmPaymentClientInput.METHOD_BONUS_LUMP_SUM.equals(orderTempDto.getMethod())) {
            input.setVal("payment_class", ComTransactionUtility.PAYGENT_METHOD_BONUS_LUMP_SUM);
        }
        // リボルビング払いの場合
        else if (HmPaymentClientInput.METHOD_REVOLVING.equals(orderTempDto.getMethod())) {
            input.setVal("payment_class", ComTransactionUtility.PAYGENT_METHOD_REVOLVING);
        }
        // 一括払いの場合
        else {
            input.setVal("payment_class", ComTransactionUtility.PAYGENT_METHOD_LUMP_SUM);
        }

        if (enable3dSecure) {
            // PDR様では3Dセキュアは非対応のため、実装省略
        } else {
            input.setVal("3dsecure_ryaku", MulPayUtility.SECURE_RYAKU);
        }

        return input;
    }

    /**
     * EntryTranのマルチペイメント請求情報にオーダーIDと取引IDを設定<br />
     * <pre>
     * クレジットカード用
     * </pre>
     *
     * @param orderSeq 受注seq
     * @param output   レスポンス情報
     * @return マルチペイメント請求情報
     */
    private MulPayBillEntity getUpdMulPayBillEntity(Integer orderSeq, ComResultDto output) {

        // マルチペイメント請求情報取得
        MulPayBillEntity mulPayBillEntity = mulPayBillLogic.getMulPayBillByOrderSeq(orderSeq);

        if (output.getResultMap() != null && output.getResultMap().containsKey("payment_id")) {
            // オーダーID
            mulPayBillEntity.setOrderId(output.getResultMap().get("payment_id"));
            // 取引ID
            mulPayBillEntity.setAccessId(output.getResultMap().get("payment_id"));
        }

        return mulPayBillEntity;
    }
}
// Paygent Customization to here
