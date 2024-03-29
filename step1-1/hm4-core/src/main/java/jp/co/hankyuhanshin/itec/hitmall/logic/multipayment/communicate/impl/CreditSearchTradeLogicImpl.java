/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.input.SearchTradeInput;
import com.gmo_pg.g_pay.client.output.SearchTradeOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditSearchTradeLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 取引状態参照ロジック
 *
 * @author yt13605
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CreditSearchTradeLogicImpl extends AbstractShopLogic implements CreditSearchTradeLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditSearchTradeLogicImpl.class);

    /**
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    @Autowired
    public CreditSearchTradeLogicImpl(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    /**
     * 実行メソッド
     *
     * @param orderId マルチペイメント請求.オーダーID
     * @return 取引状態照会出力パラメータ
     */
    @Override
    public SearchTradeOutput execute(String orderId) {
        SearchTradeInput input = new SearchTradeInput();

        input.setOrderId(orderId);

        SearchTradeOutput output = null;

        try {
            output = paymentClient.doSearchTrade(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, e);
        }
        return output;
    }

    /**
     * 変更通信結果チェック
     *
     * @param output 変更出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    @Override
    public List<CheckMessageDto> checkOutput(SearchTradeOutput output) {
        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        return communicateUtility.checkOutput(output);
    }
}
