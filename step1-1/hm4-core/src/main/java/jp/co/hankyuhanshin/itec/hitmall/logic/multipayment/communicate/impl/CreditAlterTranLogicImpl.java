/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.AlterTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmAlterTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditAlterTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * クレジット通信ロジック実装クラス
 *
 * @author yt13605
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CreditAlterTranLogicImpl extends AbstractShopLogic implements CreditAlterTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditAlterTranLogicImpl.class);

    /**
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    @Autowired
    public CreditAlterTranLogicImpl(PaymentClient paymentClient, OrderUtility orderUtility) {
        this.paymentClient = paymentClient;
        this.orderUtility = orderUtility;
    }

    /**
     * 取引取消実行<br />
     * VOID・RETURN・RETURNX　の何れかに更新
     *
     * @param mulPayBillEntity 前回マルチペイメント請求
     * @return 変更出力パラメータ
     */
    @Override
    public AlterTranOutput doCancelTran(MulPayBillEntity mulPayBillEntity) {

        // 最終のJOBCODE
        HTypeJobCode jobcode = EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, mulPayBillEntity.getJobCd());

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp currentdate = dateUtility.getCurrentTime();
        Timestamp trandate = dateUtility.toTimestampValue(mulPayBillEntity.getTranDate(), "yyyyMMddHHmmss");

        if (dateUtility.compareDate(trandate, currentdate)) {
            // 前回処理日と現在日が同日
            jobcode = HTypeJobCode.VOID;
        } else if (dateUtility.compareMonth(trandate, currentdate)) {
            // 前回処理月と現在月が同月
            jobcode = HTypeJobCode.RETURN;
        } else {
            // 前回処理月と現在月が異なる
            if (HTypeJobCode.AUTH == jobcode) {
                // 前回JOBCODEがAUTH
                jobcode = HTypeJobCode.RETURN;
            } else {
                jobcode = HTypeJobCode.RETURNX;
            }
        }

        HmAlterTranInput input = getAlterTranInput(mulPayBillEntity);
        input.setJobCd(jobcode.getValue());

        return doAlterTran(input);
    }

    /**
     * 取引再開実行<br />
     * 取消状態の取引を　AUTH・CAPTURE・SALES　の何れかに更新し<br />
     * 指定した受注金額で取引を再開する
     *
     * @param dto 修正内容-受注情報
     * @return 変更出力パラメータ
     */
    @Override
    public AlterTranOutput doReTran(ReceiveOrderDto dto) {

        MulPayBillEntity mulPayBillEntity = dto.getMulPayBillEntity();
        HTypeJobCode jobcode = EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, mulPayBillEntity.getJobCd());

        // 最終JOBCODEチェック
        if (HTypeJobCode.AUTH == jobcode || HTypeJobCode.CAPTURE == jobcode || HTypeJobCode.SALES == jobcode) {
            this.throwMessage(ALTERTRAN_JOBCD_ERR_MSG_ID);
        }

        // 出荷状態
        HTypeShipmentStatus shipmentstatus =
                        orderUtility.getShipmentStatusByOrderStatus(dto.getOrderSummaryEntity().getOrderStatus());
        // 請求種別
        HTypeBillType billtype = dto.getOrderSettlementEntity().getBillType();

        // JOBCODEの設定
        if (HTypeShipmentStatus.SHIPPED == shipmentstatus || HTypeBillType.PRE_CLAIM == billtype) {
            // 出荷状態＝出荷済み 又は 請求種別＝前請求
            jobcode = HTypeJobCode.CAPTURE;
        } else {
            jobcode = HTypeJobCode.AUTH;
        }

        HmAlterTranInput input = getAlterTranInput(mulPayBillEntity);

        input.setAmount(dto.getOrderSummaryEntity().getOrderPrice().intValue());
        input.setJobCd(jobcode.getValue());

        return doAlterTran(input);
    }

    /**
     * 売上実行<br />
     * 状態　AUTH　の取引を　SALES　へ更新
     *
     * @param mulPayBillEntity 前回マルチペイメント請求
     * @return 変更出力パラメータ
     */
    @Override
    public AlterTranOutput doSalesFixTran(MulPayBillEntity mulPayBillEntity) {
        HTypeJobCode jobcode = EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, mulPayBillEntity.getJobCd());

        // 最終JOBCODEチェック
        if (HTypeJobCode.AUTH != jobcode) {
            this.throwMessage(ALTERTRAN_JOBCD_ERR_MSG_ID);
        }

        HmAlterTranInput input = getAlterTranInput(mulPayBillEntity);

        if (mulPayBillEntity.getAmount() != null) {
            input.setAmount(mulPayBillEntity.getAmount().intValue());
        }
        input.setJobCd(HTypeJobCode.SALES.getValue());

        return doAlterTran(input);
    }

    /**
     * 変更入力パラメータ取得
     *
     * @param mulPayBillEntity マルチペイメント請求
     * @return 変更入力パラメータ
     */
    protected HmAlterTranInput getAlterTranInput(MulPayBillEntity mulPayBillEntity) {
        HmAlterTranInput input = new HmAlterTranInput();

        input.setOrderSeq(mulPayBillEntity.getOrderSeq());
        input.setOrderVersionNo(mulPayBillEntity.getOrderVersionNo());
        input.setAccessId(mulPayBillEntity.getAccessId());
        input.setAccessPass(mulPayBillEntity.getAccessPass());
        input.setMethod(mulPayBillEntity.getMethod());
        input.setPayTimes(mulPayBillEntity.getPayTimes());

        return input;
    }

    /**
     * 変更通信実行
     *
     * @param input 変更入力パラメータ
     * @return 変更出力パラメータ
     */
    protected AlterTranOutput doAlterTran(HmAlterTranInput input) {
        AlterTranOutput output = null;

        try {
            output = paymentClient.doAlterTran(input);
        } catch (PaymentException e) {
            // 通信実行エラー
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(ALTERTRAN_COM_ERR_MSG_ID, null, e);
        }
        return output;
    }

    /**
     * 変更通信結果チェック
     *
     * @param alterTranOutput 変更出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    @Override
    public List<CheckMessageDto> checkOutput(AlterTranOutput alterTranOutput) {
        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        return communicateUtility.checkOutput(alterTranOutput);
    }
}
