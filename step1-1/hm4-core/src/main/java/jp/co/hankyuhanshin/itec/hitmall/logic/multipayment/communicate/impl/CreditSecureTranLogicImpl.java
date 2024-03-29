/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.SecureTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSecureTranInput;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditSecureTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * クレジット認証後決済通信ロジック
 *
 * @author oa13612
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CreditSecureTranLogicImpl extends AbstractShopLogic implements CreditSecureTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditSecureTranLogicImpl.class);

    /**
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    @Autowired
    public CreditSecureTranLogicImpl(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    /**
     * 実行メソッド
     *
     * @param md    md
     * @param paRes PaRes
     * @return 認証後決済出力パラメータ
     */
    @Override
    public SecureTranOutput execute(String md, String paRes) {
        HmSecureTranInput input = getSecureTranInput(md, paRes);

        SecureTranOutput output = null;

        try {
            output = paymentClient.doSecureTran(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, e);
        }
        return output;
    }

    /**
     * 認証後決済入力パラメータの取得
     *
     * @param md    md
     * @param paRes PaRes
     * @return 認証後決済入力パラメータ
     */
    protected HmSecureTranInput getSecureTranInput(String md, String paRes) {
        HmSecureTranInput input = new HmSecureTranInput();

        input.setMd(md);
        input.setPaRes(paRes);

        return input;
    }

    /**
     * 認証後決済出力用エラーチェック
     *
     * @param output 認証後決済出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    @Override
    public List<CheckMessageDto> checkOutput(SecureTranOutput output) {
        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        return communicateUtility.checkOutput(output);
    }

}
