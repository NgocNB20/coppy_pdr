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
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditCardInfoTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 * 【決済オプション部品（ペイジェント）】<br />
 * クレジットカード番号保持用通信処理
 * <pre>
 * 指定クレジットカード番号削除用メソッド追加
 * </pre>
 *
 * @author s.kume
 */
@Component
// Paygent Customization from here
public class CreditCardInfoTranLogicImpl extends AbstractShopLogic implements CreditCardInfoTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardInfoTranLogicImpl.class);

    /**
     * 通信ロジック
     */
    private final ComTransactionLogic comTransactionLogic;

    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * カード情報設定エラー
     */
    protected static final String MSGCD_CREDIT_CARD_INFO_REGIST_ERROR = "PG-0001-012-L-E";

    /**
     * カード情報照会エラー
     */
    protected static final String MSGCD_CREDIT_CARD_INFO_GET_ERROR = "PG-0001-013-L-E";

    /**
     * カード情報削除エラー
     */
    protected static final String MSGCD_CREDIT_CARD_INFO_DELETE_ERROR = "PG-0001-014-L-E";

    @Autowired
    public CreditCardInfoTranLogicImpl(ComTransactionLogic comTransactionLogic,
                                       ComTransactionUtility comTransactionUtility) {
        this.comTransactionLogic = comTransactionLogic;
        this.comTransactionUtility = comTransactionUtility;
    }

    @Override
    public ComResultDto doRegistCardInfoTran(String customerId, String cardNumber, String cardValidTerm, String token) {

        try {

            // インプット取得
            ComRequestDto input = getInput(customerId, cardNumber, cardValidTerm, token);
            // 通信実行
            return execTran(input, ComTransactionUtility.MSG_TYPE_REG_CARDINFO);

        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_CREDIT_CARD_INFO_REGIST_ERROR, null, e);
        }

        // 非到達
        return null;
    }

    @Override
    public ComResultDto doGetCardInfoTran(String customerId) {

        // カード情報照会失敗はフロントでエラーとしない為、画面側で例外捕捉処理を行う
        // ここでは、catch＆throwしない

        // インプット取得
        ComRequestDto input = getInput(customerId, ComTransactionUtility.KIND_GET);

        // 通信実行
        return execTran(input, ComTransactionUtility.MSG_TYPE_GET_CARDINFO);
    }

    @Override
    public ComResultDto doDeleteCardInfoTran(String customerId) {

        try {

            // インプット取得
            ComRequestDto input = getInput(customerId, ComTransactionUtility.KIND_DELETE);
            // 通信実行
            return execTran(input, ComTransactionUtility.MSG_TYPE_DEL_CARDINFO);

        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_CREDIT_CARD_INFO_DELETE_ERROR, null, e);
        }

        // 非到達
        return null;
    }

    /**
     * カード情報設定<br />
     * リクエスト情報作成
     *
     * @param customerId 顧客ID
     * @param type       処理区分
     * @return リクエスト情報
     */
    protected ComRequestDto getInput(String customerId, String type) {

        ComRequestDto input = ApplicationContextUtility.getBean(ComRequestDto.class);
        // 引数の値を設定する

        input.setVal("telegram_kind", type); // 電文種別ID
        input.setVal("customer_id", customerId); // 顧客ID

        return input;
    }

    /**
     * カード情報削除・照会<br />
     * リクエスト情報作成
     *
     * @param customerId    顧客ID
     * @param cardNumber    カード番号
     * @param cardValidTerm カード有効期限
     * @param token         トークン
     * @return リクエスト情報
     */
    protected ComRequestDto getInput(String customerId, String cardNumber, String cardValidTerm, String token) {

        ComRequestDto input = getInput(customerId, ComTransactionUtility.KIND_REGIST);

        // カード情報を設定する

        if (StringUtil.isEmpty(token)) {
            input.setVal("card_number", cardNumber); // カード番号
            input.setVal("card_valid_term", cardValidTerm); // カード有効期限
        } else {
            input.setVal("card_token", token); // カード情報トークン
        }

        input.setVal(
                        "valid_check_flg",
                        PropertiesUtil.getSystemPropertiesValue("paygent.valid.check.flg")
                    ); // 有効性チェックフラグ

        return input;
    }

    /**
     * 通信実行
     *
     * @param input   リクエスト情報
     * @param msgType メッセージ切り分け
     * @return レスポンス情報
     */
    protected ComResultDto execTran(ComRequestDto input, String msgType) {

        // 通信実行
        ComResultDto output = comTransactionLogic.execute(input);
        List<CheckMessageDto> messageList = comTransactionUtility.checkResultOutput(output, msgType);
        if (messageList != null) {
            output.setMessageList(messageList);
        }

        return output;
    }

    // PDR Migrate Customization from here

    /**
     * 削除対象クレジットカードの顧客IDと顧客カードIDでカード情報を削除<br/>
     *
     * @param customerId 顧客ID
     * @param cardId     顧客カードID
     * @return 処理結果
     */
    @Override
    public ComResultDto doDeleteDesignateCardInfoTran(String customerId, String cardId) {

        try {

            // インプット取得
            ComRequestDto input = getDesignateInput(customerId, cardId, ComTransactionUtility.KIND_DELETE);
            // 通信実行
            return execTran(input, ComTransactionUtility.MSG_TYPE_DEL_CARDINFO);

        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_CREDIT_CARD_INFO_DELETE_ERROR, null, e);
        }

        // 非到達
        return null;
    }

    /**
     * カード情報設定<br />
     * リクエスト情報作成
     *
     * @param customerId 顧客ID
     * @param cardId     顧客カードID
     * @param type       処理区分
     * @return リクエスト情報
     */
    protected ComRequestDto getDesignateInput(String customerId, String cardId, String type) {

        ComRequestDto input = ApplicationContextUtility.getBean(ComRequestDto.class);

        // 引数の値を設定する
        input.setVal("telegram_kind", type); // 電文種別ID
        input.setVal("customer_id", customerId); // 顧客ID
        input.setVal("customer_card_id", cardId); // 顧客カードID

        return input;
    }

    // PDR Migrate Customization to here
}
// Paygent Customization to here
