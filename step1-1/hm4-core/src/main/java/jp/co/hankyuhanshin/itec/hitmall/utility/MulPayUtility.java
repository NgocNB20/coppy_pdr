/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import com.gmo_pg.g_pay.client.output.BaseOutput;
import com.gmo_pg.g_pay.client.output.ErrHolder;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmPaymentClientInput;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * マルチペイメントヘルパークラス<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/08 UtilからHelperへ変更
 */
@Component
public class MulPayUtility {

    /**
     * 処理種別 : 受注修正
     */
    public static final String ORDER_MODIFY = "ORDER_MODIFY";

    /**
     * 処理種別 : 受注キャンセル
     */
    public static final String ORDER_CANCEL = "ORDER_CANCEL";

    /**
     * 処理種別 : 出荷
     */
    public static final String SHIPMENT = "SHIPMENT";

    /**
     * エラー種別 : カードエラー
     */
    public static final String ERR_CARD = "MulPayUtility.ERR_CARD";

    /**
     * エラー種別 : 通信エラー
     */
    public static final String ERR_SYSTEM = "MulPayUtility.ERR_SYSTEM";

    /**
     * エラー種別 : エラー無
     */
    public static final String ERR_NONE = "MulPayUtility.ERR_NONE";

    /**
     * GMOのカードエラーのPREFIX
     */
    public static final String GMO_CARD_ERR_PREFIX_REGEX = "^[G|C].*";

    // Paygent Customization from here
    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * 決済方法：クレジット
     */
    protected static final String PAYTYPE_CREDIT = "0";

    /**
     * 決済方法：コンビニ
     */
    protected static final String PAYTYPE_CONVENI = "3";

    /**
     * 確認番号(固定値)
     */
    public static final String CONF_NO_DEFAULT = "400008";

    /**
     * トランザクション種別：決済開始
     */
    public static final String TRANTYPE_ENTRY = "EntryTran";

    /**
     * トランザクション種別：決済実行
     */
    public static final String TRANTYPE_EXEC = "ExecTran";

    /**
     * トランザクション種別：3Dセキュア本人認証後決済実行
     */
    public static final String TRANTYPE_SECURE = "SecureTran";

    /**
     * トランザクション種別：金額変更(減額)
     */
    public static final String TRANTYPE_CHANGE = "ChangeTran";

    /**
     * トランザクション種別：変更(キャンセル・請求確定)
     */
    public static final String TRANTYPE_ALTER = "AlterTran";

    /**
     * トランザクション種別：コンビニ
     */
    public static final String TRANTYPE_CVS = "EntryExecTranCvs";

    /**
     * 3Dセキュア：不使用
     */
    public static final String TD_FLAG_OFF = "0";

    /**
     * 3Dセキュア：使用
     */
    public static final String TD_FLAG_ON = "1";

    /**
     * 3Dセキュア不要区分：不要/未契約
     */
    public static final String SECURE_RYAKU = "1";

    /**
     * ACS：呼出なし(通常与信)
     */
    protected static final String ACS_OFF = "0";

    /**
     * ACS：呼出あり(3Dセキュア与信)
     */
    protected static final String ACS_ON = "1";

    /**
     * JOBCODEをペイジェントの決済ステータスと対応させるためのマップ
     */
    protected static final Map<HTypeJobCode, List<String>> JOB_CODE_TO_PAYGENT_MAP = new HashMap<>();

    /**
     * コンストラクタ<br/>
     */
    @Autowired
    public MulPayUtility(ComTransactionUtility comTransactionUtility) {
        this.comTransactionUtility = comTransactionUtility;

        List<String> tmpList = new ArrayList<>();
        tmpList.add("20");// 【ペイジェント】オーソリOK
        tmpList.add("30");// 【ペイジェント】オーソリ期限切
        JOB_CODE_TO_PAYGENT_MAP.put(HTypeJobCode.AUTH, tmpList);// 仮売上

        tmpList = new ArrayList<>();
        tmpList.add("40");// 【ペイジェント】消込済
        tmpList.add("41");// 【ペイジェント】消込済(売上取消期限切)
        JOB_CODE_TO_PAYGENT_MAP.put(HTypeJobCode.SALES, tmpList);// 実売上

        tmpList = new ArrayList<>();
        tmpList.add("32");// 【ペイジェント】オーソリ取消済
        tmpList.add("60");// 【ペイジェント】売上取消済
        JOB_CODE_TO_PAYGENT_MAP.put(HTypeJobCode.VOID, tmpList);// 取消
    }
    // Paygent Customization to here

    /**
     * マルチペイメント請求情報/決済結果情報を持つ決済方法かどうか<br/>
     *
     * @param settlementMethodType 決済方法種別
     * @return マルチペイメント請求情報有無(true : あり false : なし)
     */
    public boolean isMulPaySettlement(HTypeSettlementMethodType settlementMethodType) {
        if (HTypeSettlementMethodType.CREDIT == settlementMethodType
            || HTypeSettlementMethodType.CONVENIENCE == settlementMethodType
            || HTypeSettlementMethodType.PAY_EASY == settlementMethodType
            || HTypeSettlementMethodType.AMAZON_PAYMENT == settlementMethodType) {
            return true;
        }
        return false;
    }

    /**
     * 決済代行会員IDを作成する<br/>
     *
     * @param memberSeq 会員SEQ
     * @return 決済代行会員ID
     */
    public String createPaymentMemberId(Integer memberSeq) {
        if (memberSeq == null) {
            return null;
        }

        // Paygent Customization from here
        // システムプロパティから環境値取得
        String gmoPrefix = PropertiesUtil.getSystemPropertiesValue("paygent.member.prefix");
        // Paygent Customization to here

        // プロパティ設定をしていない場合、
        // 決済代行会員IDが"null会員SEQ"となるのを回避する
        if (gmoPrefix == null) {
            gmoPrefix = "";
        }

        return gmoPrefix + memberSeq.toString();
    }

    /**
     * GMOと通信した結果のエラーの種別を取得します。<br/>
     *
     * @param output GMO結果基底クラス
     * @return エラー種別
     */
    public String getErrorType(BaseOutput output) {

        if (!output.isErrorOccurred()) {
            return ERR_NONE;
        }

        for (Object obj : output.getErrList()) {
            if (obj instanceof ErrHolder) {
                String errCode = ((ErrHolder) obj).getErrCode();
                if (errCode.matches(GMO_CARD_ERR_PREFIX_REGEX)) {
                    return ERR_CARD;
                }
            }
        }

        return ERR_SYSTEM;
    }

    // Paygent Customization from here

    /**
     * リクエスト情報とレスポンス情報より、マルチペイメント請求情報を作成<br />
     * <pre>
     * クレジットカード用
     * </pre>
     *
     * @param input    リクエスト情報
     * @param output   レスポンス情報
     * @param tranType トランザクション種別
     * @return マルチペイメント請求情報
     */
    public MulPayBillEntity getMulPayBillEntity(ComRequestDto input, ComResultDto output, String tranType) {

        // 日付関連Utility取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        // マルチペイメント請求情報取得
        MulPayBillEntity mulPayBillEntity = ApplicationContextUtility.getBean(MulPayBillEntity.class);

        // レスポンス情報が無い場合(通信前)
        if (output == null) {
            output = ApplicationContextUtility.getBean(ComResultDto.class);
        }

        // Entityへのセット ここから

        Map<String, String> outputMap = output.getResultMap();
        Map<String, String> inputMap = input.getRequestMap();

        // 現在日時を取得する
        Timestamp nowDate = dateUtility.getCurrentTime();

        // 共通項目にinput情報をセットする
        getMulPayBillEntitySecureInput(mulPayBillEntity, input, tranType, nowDate);

        // 3Dセキュア使用フラグ
        if (!StringUtil.isEmpty(inputMap.get("3dsecure_use_type"))) {
            mulPayBillEntity.setTdFlag(TD_FLAG_ON);
        } else if (!StringUtil.isEmpty(input.getTdFlag())) {
            mulPayBillEntity.setTdFlag(input.getTdFlag());
        } else {
            mulPayBillEntity.setTdFlag(TD_FLAG_OFF);
        }

        // ACS呼出判定
        String acs = null;
        if (TRANTYPE_EXEC.equals(tranType)) {
            // 「ExecTran」の場合、結果コードで判定
            acs = (comTransactionUtility.isSecureTran(output) ? ACS_ON : ACS_OFF);
        } else if (TRANTYPE_SECURE.equals(tranType)) {
            // 「SecureTran」の場合、既に3Dセキュアで通信済
            acs = ACS_ON;
        } else if (!StringUtil.isEmpty(input.getAcs())) {
            acs = input.getAcs();
        }
        mulPayBillEntity.setAcs(acs);

        if (comTransactionUtility.isErrorOccurred(output)) {

            // エラーコード
            mulPayBillEntity.setErrCode(output.getResponseCode());
            // エラー情報
            mulPayBillEntity.setErrInfo(output.getResponseDetail());
        } else if (outputMap != null && outputMap.size() > 0) {

            // オーダーID
            mulPayBillEntity.setOrderId(outputMap.get("payment_id"));
            // 取引ID
            mulPayBillEntity.setAccessId(outputMap.get("payment_id"));

            // クレジットカード会社コード(仕向先コード)
            // カード発行会社コード
            if (!StringUtil.isEmpty(outputMap.get("issur_id"))) {
                mulPayBillEntity.setForward(outputMap.get("issur_id"));
            }
            // 取扱カード会社コード
            else {
                mulPayBillEntity.setForward(outputMap.get("acq_id"));
            }

            // 決済日付
            mulPayBillEntity.setTranDate(dateUtility.format(nowDate, "yyyyMMddHHmmss"));
        }

        return mulPayBillEntity;
    }

    /**
     * リクエスト情報とレスポンス情報より、マルチペイメント請求情報を作成(共通INPUT)<br />
     * <pre>
     * クレジットカード用
     * </pre>
     *
     * @param mulPayBillEntity マルチペイメント請求
     * @param input            リクエスト情報
     * @param tranType         トランザクション種別
     * @param nowDate          現在日時
     */
    public void getMulPayBillEntitySecureInput(MulPayBillEntity mulPayBillEntity,
                                               ComRequestDto input,
                                               String tranType,
                                               Timestamp nowDate) {

        Map<String, String> inputMap = input.getRequestMap();

        // 決済方法
        mulPayBillEntity.setPayType(PAYTYPE_CREDIT);
        // トランザクション種別
        mulPayBillEntity.setTranType(tranType);
        // 受注SEQ
        mulPayBillEntity.setOrderSeq(input.getOrderSeq());
        // 受注履歴連番
        mulPayBillEntity.setOrderVersionNo(input.getOrderVersionNo());
        // オーダーID
        mulPayBillEntity.setOrderId(input.getOrderId());
        // 取引ID
        mulPayBillEntity.setAccessId(input.getOrderId());

        // 処理区分
        mulPayBillEntity.setJobCd(input.getJobCd().getValue());

        // 支払方法
        String method = null;
        // 分割払いの場合
        if (ComTransactionUtility.PAYGENT_METHOD_INSTALLMENT.equals(inputMap.get("payment_class"))) {
            method = HmPaymentClientInput.METHOD_INSTALLMENT;
        }
        // ボーナス1回払いの場合
        else if (ComTransactionUtility.PAYGENT_METHOD_BONUS_LUMP_SUM.equals(inputMap.get("payment_class"))) {
            method = HmPaymentClientInput.METHOD_BONUS_LUMP_SUM;
        }
        // リボルビング払いの場合
        else if (ComTransactionUtility.PAYGENT_METHOD_REVOLVING.equals(inputMap.get("payment_class"))) {
            method = HmPaymentClientInput.METHOD_REVOLVING;
        }
        // 一括払いの場合
        else if (ComTransactionUtility.PAYGENT_METHOD_LUMP_SUM.equals(inputMap.get("payment_class"))) {
            method = HmPaymentClientInput.METHOD_LUMP_SUM;
        }
        mulPayBillEntity.setMethod(method);

        // 支払回数
        mulPayBillEntity.setPayTimes(
                        inputMap.containsKey("split_count") ? Integer.valueOf(inputMap.get("split_count")) : 0);

        // 利用金額 ※キャンセル時は金額設定しない
        if (!StringUtil.isEmpty(inputMap.get("payment_amount"))) {
            mulPayBillEntity.setAmount(new BigDecimal(inputMap.get("payment_amount")));
        }

        // 登録日時
        mulPayBillEntity.setRegistTime(nowDate);
        // 更新日時
        mulPayBillEntity.setUpdateTime(nowDate);
    }
    // Paygent Customization to here
}
