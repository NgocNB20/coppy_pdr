/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.ChangeTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmChangeTranInput;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditChangeTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * クレジット金額変更通信ロジック
 *
 * @author yt13605
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CreditChangeTranLogicImpl extends AbstractShopLogic implements CreditChangeTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditChangeTranLogicImpl.class);

    /**
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    @Autowired
    public CreditChangeTranLogicImpl(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    /**
     * 実行メソッド<br />
     * modifiedOrderPrice へ金額を変更する通信処理を行う
     *
     * @param mulPayBillEntity   最新マルチペイメント請求
     * @param modifiedOrderPrice 修正受注金額
     * @return 金額変更出力パラメータ
     */
    @Override
    public ChangeTranOutput execute(MulPayBillEntity mulPayBillEntity, BigDecimal modifiedOrderPrice) {

        // 最終のJOBCODE
        HTypeJobCode jobcode = EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, mulPayBillEntity.getJobCd());

        // 最終JOBCODEチェック
        if (modifiedOrderPrice.compareTo(BigDecimal.ZERO) != 0) {
            if (HTypeJobCode.AUTH == jobcode) {
            } else if (HTypeJobCode.CAPTURE == jobcode || HTypeJobCode.SALES == jobcode) {
                jobcode = HTypeJobCode.CAPTURE;
            } else {
                this.throwMessage(CHANGETRAN_JOBCD_ERR_MSG_ID);
            }
        }

        HmChangeTranInput input = new HmChangeTranInput();

        input.setOrderSeq(mulPayBillEntity.getOrderSeq());
        input.setOrderVersionNo(mulPayBillEntity.getOrderVersionNo());
        input.setAccessId(mulPayBillEntity.getAccessId());
        input.setAccessPass(mulPayBillEntity.getAccessPass());
        input.setMethod(mulPayBillEntity.getMethod());
        input.setPayTimes(mulPayBillEntity.getPayTimes());
        input.setAmount(modifiedOrderPrice.intValue());
        input.setJobCd(jobcode.getValue());

        return doAlterTran(input);
    }

    /**
     * 金額変更通信実行
     *
     * @param input 変更入力パラメータ
     * @return 変更出力パラメータ
     */
    protected ChangeTranOutput doAlterTran(HmChangeTranInput input) {
        ChangeTranOutput output = null;

        try {
            output = paymentClient.doChangeTran(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(CHANGETRAN_COM_ERR_MSG_ID, null, e);
        }
        return output;
    }

    /**
     * 金額変更通信結果チェック
     *
     * @param output 金額変更出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    @Override
    public List<CheckMessageDto> checkOutput(ChangeTranOutput output) {
        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        return communicateUtility.checkOutput(output);
    }
}
