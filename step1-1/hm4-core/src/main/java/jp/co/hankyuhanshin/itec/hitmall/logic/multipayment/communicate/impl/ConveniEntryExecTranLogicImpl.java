/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.input.EntryExecTranCvsInput;
import com.gmo_pg.g_pay.client.output.EntryExecTranCvsOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmEntryExecTranCvsInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ConveniEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * コンビニ請求実行ロジック実装クラス
 *
 * @author yt13605 *
 * @author Kawaguchi  (itec)  2010/08/27 チケット #2235 対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class ConveniEntryExecTranLogicImpl extends AbstractShopLogic implements ConveniEntryExecTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConveniEntryExecTranLogicImpl.class);

    /**
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    @Autowired
    public ConveniEntryExecTranLogicImpl(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    /**
     * 実行メソッド
     *
     * @param dto 受注DTO
     * @return コンビニ登録・決済出力パラメータ
     */
    @Override
    public EntryExecTranCvsOutput execute(ReceiveOrderDto dto) {

        // 通信回数削減のために、GMOの制約がある項目について通信前にチェック

        OrderPersonEntity orderPersonEntity = dto.getOrderPersonEntity();
        // 注文者氏名
        String orderName = orderPersonEntity.getOrderLastName() + "　" + orderPersonEntity.getOrderFirstName();
        // 注文者氏名桁数チェック
        if (orderName.length() > GMOCHECK_ORDER_NAME_LIMIT) {
            this.throwMessage(MSGCD_ORDER_NAME_LIMIT_FAIL);
        }
        // 注文者氏名カナ
        String orderKana = orderPersonEntity.getOrderLastKana() + "　" + orderPersonEntity.getOrderFirstKana();
        // 注文者氏名カナ桁数チェック
        if (orderKana.length() > GMOCHECK_ORDER_KANA_LIMIT) {
            this.throwMessage(MSGCD_ORDER_KANA_LIMIT_FAIL);
        }

        // 通信処理

        EntryExecTranCvsInput input = getInput(dto);

        EntryExecTranCvsOutput output = null;
        try {
            output = paymentClient.doEntryExecTranCvs(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }
        return output;
    }

    /**
     * コンビニ登録・決済入力パラメータ取得
     *
     * @param dto 受注DTO
     * @return コンビニ登録・決済入力パラメータ
     */
    protected HmEntryExecTranCvsInput getInput(ReceiveOrderDto dto) {
        HmEntryExecTranCvsInput input = new HmEntryExecTranCvsInput();

        OrderPersonEntity orderPersonEntity = dto.getOrderPersonEntity();
        OrderSummaryEntity orderSummaryEntity = dto.getOrderSummaryEntity();

        input.setOrderSeq(orderSummaryEntity.getOrderSeq());
        input.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
        input.setOrderId(orderSummaryEntity.getOrderCode());
        input.setAmount(orderSummaryEntity.getOrderPrice().intValue());

        input.setConvenience(dto.getOrderTempDto().getConvenience());

        input.setCustomerKana(orderPersonEntity.getOrderLastKana() + "　" + orderPersonEntity.getOrderFirstKana());
        input.setCustomerName(orderPersonEntity.getOrderLastName() + "　" + orderPersonEntity.getOrderFirstName());

        input.setPaymentTermDay(dto.getSettlementMethodEntity().getPaymentTimeLimitDayCount());

        input.setTelNo(orderPersonEntity.getOrderTel());

        return input;
    }

    /**
     * コンビニ取引登録決済出力用エラーチェック
     *
     * @param output コンビニ取引登録決済出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    @Override
    public List<CheckMessageDto> checkCsvOutput(EntryExecTranCvsOutput output) {

        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        if (output.isEntryErrorOccurred()) {
            return communicateUtility.checkOutput(output.getEntryTranOutput());
        } else if (output.isExecErrorOccurred()) {
            return communicateUtility.checkOutput(output.getExecTranOutput());
        }
        return null;
    }
}
