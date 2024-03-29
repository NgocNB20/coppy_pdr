/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.CONVENIENCE;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.CREDIT;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.RECEIPT_PAYMENT;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.MONTHLY_BILLING;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 * 決済方法選択画面アイテム Model
 * <pre>
 * クレジットカード区分 リスト 追加
 * クレジットカード区分 追加
 * 保持カードリスト 追加
 * お支払回数 表示 追加
 * 保持カード値 追加
 * 保持カード表示用 追加
 * クレジットカード会社 追加
 * 支払方法使用可否フラグ 追加
 * </pre>
 *
 * @author kimura
 */
@Data
public class PaymentModelItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 決済方法値
     */
    private String settlementMethodValue;

    /**
     * 決済方法名
     */
    private String settlementMethodLabel;

    /**
     * 決済タイプ
     */
    private HTypeSettlementMethodType settlementMethodType;

    /**
     * 決済タイプ名
     */
    private String settlementMethodTypeName;

    /**
     * 請求タイプ
     */
    private HTypeBillType billType;

    /**
     * 決済方法説明文PC
     */
    private String settlementNotePC;

    /**
     * ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableInstallment;

    /**
     * ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableRevolving;

    /**
     * カード番号（動的バリデータ）
     */
    @HCHankaku
    private String cardNumber;

    /**
     * 有効期限（月）（動的バリデータ）
     */
    private String expirationDateMonth;

    /**
     * 有効期限（年）（動的バリデータ）
     */
    private String expirationDateYear;

    /**
     * 支払区分
     */
    private String paymentType;

    /**
     * 分割回数（動的バリデータ）
     */
    private String dividedNumber;

    /**
     * セキュリティコード（動的バリデータ）
     */
    @HCHankaku
    private String securityCode;

    /**
     * コンビニ選択値（動的バリデータ）
     */
    private String convenience;

    /**
     * カード保存フラグ
     */
    private boolean saveFlg;

    /**
     * コンディション<br />
     * クレジットかどうか
     *
     * @return true..クレジット / false..クレジットではない
     */
    public boolean isCreditType() {
        if (this.settlementMethodType == null) {
            return CREDIT == getSettlementMethodType();
        }
        return CREDIT == this.settlementMethodType;
    }

    /**
     * コンディション<br />
     * コンビニかどうか
     *
     * @return true..コンビニ / false..コンビニではない
     */
    public boolean isConvenienceType() {
        if (settlementMethodType == null) {
            return CONVENIENCE == getSettlementMethodType();
        }
        return CONVENIENCE == this.settlementMethodType;
    }

    /**
     * コンディション
     * 月締請求かどうか
     *
     * @return true..月締請求 / false..月締請求ではない
     */
    public boolean isMonthlyBilling() {
        if (this.settlementMethodType == null) {
            return MONTHLY_BILLING == getSettlementMethodType();
        }
        return MONTHLY_BILLING == this.settlementMethodType;
    }

    /**
     * 分割可能の決済かどうか判断<br/>
     *
     * @return true 分割可
     */
    public boolean isPossibleInstallment() {
        return HTypeEffectiveFlag.VALID == getEnableInstallment();
    }

    /**
     * リボ払い可能の決済かどうか判断<br/>
     *
     * @return true リボ払い可
     */
    public boolean isPossibleRevolving() {
        return HTypeEffectiveFlag.VALID == getEnableRevolving();
    }

    // PDR Migrate Customization from here
    /**
     * クレジットカード区分 リスト
     */
    private Map<String, String> creditCardTypeItems;

    /**
     * クレジットカード区分
     */
    private String creditCardType;

    /**
     * 保持カードリスト
     */
    private List<PaymentModelRegistedCardItem> registedCardItems;

    /**
     * お支払回数 表示用
     */
    private String dispPaymentType;

    /**
     * 保持カード 表示用
     */
    private String registCardSelect;

    /**
     * クレジットカード会社
     */
    private String cardBrand;

    /**
     * 支払方法使用可否フラグ
     */
    private boolean usabilityFlag;

    /**
     * 保持カードが存在するかどうか判定します。
     *
     * @return true..存在する / false..存在しない
     */
    public boolean isRegistedCard() {
        if (registedCardItems == null || registedCardItems.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * コンビニ・郵便振込が使用不可の場合<br />
     * 固定文言を表示するかどうか<br />
     *
     * @return true..コンビニ・郵便振込かつ使用不可 / false..コンビニ・郵便振込かつ使用可 または 別支払方法
     */
    public boolean isConveniencePostaltransferType() {
        if (this.settlementMethodType == null) {
            return CONVENIENCE_POSTALTRANSFER == getSettlementMethodType();
        }
        return CONVENIENCE_POSTALTRANSFER == this.settlementMethodType;
    }

    /**
     * 代金引換が使用不可の場合<br />
     * 固定文言を表示するかどうか<br />
     *
     * @return true..代金引換かつ使用不可 / false..代金引換かつ使用可 または 別支払方法
     */
    public boolean isReceiptPaymentType() {
        if (this.settlementMethodType == null) {
            return RECEIPT_PAYMENT == getSettlementMethodType();
        }
        return RECEIPT_PAYMENT == this.settlementMethodType;
    }

    /**
     * 口座自動引落が使用不可の場合<br />
     * 固定文言を表示するかどうか<br />
     *
     * @return true..口座自動引落かつ使用不可 / false..口座自動引落かつ使用可 または 別支払方法
     */
    public boolean isAutomaticWithdrawalType() {
        if (this.settlementMethodType == null) {
            return AUTOMATIC_WITHDRAWAL == getSettlementMethodType();
        }
        return AUTOMATIC_WITHDRAWAL == this.settlementMethodType;
    }

    /**
     * 決済方法の説明文(PC)が存在するかどうか<br />
     *
     * @return true..説明文表示 / false..説明文非表示
     */
    public boolean isDispSettlementNotePC() {
        if (this.settlementMethodType == null) {
            return !StringUtils.isEmpty(getSettlementNotePC());
        }
        return !StringUtils.isEmpty(this.settlementNotePC);
    }
    // PDR Migrate Customization to here
}