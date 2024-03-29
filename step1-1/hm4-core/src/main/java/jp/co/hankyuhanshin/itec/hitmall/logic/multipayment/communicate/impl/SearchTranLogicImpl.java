/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.ComTransactionLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SearchTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 【決済オプション部品（ペイジェント）】
 * マルチペイメント検索通信処理ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
// Paygent Customization from here
public class SearchTranLogicImpl extends AbstractShopLogic implements SearchTranLogic {

    /**
     * 取引状態参照に失敗した場合のメッセージ
     */
    public static final String MSGCD_PAYMENT_EXCEPTION_ERROR = "LOX000316";

    /** ペイジェントの通信APIラッパー */
    private final ComTransactionLogic comTransactionLogic;

    /** 通信ユーティリティ */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * コンストラクタ
     *
     * @param comTransactionLogic ペイジェントとの通信をインターセプトする為だけに作成
     * @param comTransactionUtility 通信ユーティリティ
     */
    @Autowired
    public SearchTranLogicImpl(ComTransactionLogic comTransactionLogic, ComTransactionUtility comTransactionUtility) {
        this.comTransactionLogic = comTransactionLogic;
        this.comTransactionUtility = comTransactionUtility;
    }

    /**
     * 検索実行
     * @param orderId 決済ID
     * @return レスポンス情報
     */
    @Override
    public ComResultDto getPaymentInfo(String orderId) {

        // 決済情報照会
        try {

            /*
             * 前処理
             */
            // 決済情報照会用リクエスト情報を作成する
            ComRequestDto request = getExecTranInputPayInfo(orderId);

            /*
             * 本処理
             */
            // 通信
            ComResultDto result = comTransactionLogic.execute(request);

            /*
             * 後処理
             */
            // ペイジェントエラーメッセージをHIT-MALL用のメッセージに変換する
            List<CheckMessageDto> messageList =
                            comTransactionUtility.checkResultOutput(result, ComTransactionUtility.MSG_TYPE_PAYMENT);

            // 戻り値に変換したエラーメッセージを設定する
            return comTransactionUtility.makeOutput(result, messageList);

        } catch (Throwable e) {
            throwMessage(MSGCD_PAYMENT_EXCEPTION_ERROR, null, e);
        }

        // 非到達
        return null;
    }

    /**
     * 決済情報照会用リクエスト情報を作成する
     *
     * @param orderId  決済ID
     * @return リクエスト情報
     */
    protected ComRequestDto getExecTranInputPayInfo(String orderId) {

        ComRequestDto input = ApplicationContextUtility.getBean(ComRequestDto.class);

        // 電文種別ID
        input.setVal("telegram_kind", ComTransactionUtility.KIND_PAYMENT_INFO);
        // 決済ID
        input.setVal("payment_id", orderId);

        return input;
    }
}
// Paygent Customization to here
