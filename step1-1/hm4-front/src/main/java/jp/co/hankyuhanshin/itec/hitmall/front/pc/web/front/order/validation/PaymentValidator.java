/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditCardType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.ConfirmModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.DeliveryModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentModelItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.ReceiverModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.PaymentGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HCreditCardExpirationDateValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HCreditCardNumberValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HSecurityCodeValidator;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType.INSTALLMENT;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.CONVENIENCE;
import static jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType.CREDIT;

/**
 * 注文：お支払方法選択画面の動的バリデータクラス
 *
 * @author kimura
 */
@Data
@Component
public class PaymentValidator implements SmartValidator {

    /**
     * paymentModelItems フィールド名
     **/
    protected static final String FILED_NAME_ITEMS = "paymentModelItems[";

    /**
     * カード番号 フィールド名
     **/
    protected static final String FILED_NAME_CARD_NUMBER = "].cardNumber";

    /**
     * 有効期限：月 フィールド名
     **/
    protected static final String FILED_NAME_EXPIRATION_DATE_MONTH = "].expirationDateMonth";

    /**
     * 有効期限：年 フィールド名
     **/
    protected static final String FILED_NAME_EXPIRATION_DATE_YEAR = "].expirationDateYear";

    /**
     * セキュリティコード フィールド名
     **/
    protected static final String FILED_NAME_SECURITY_CODE = "].securityCode";

    /**
     * 決済種別 フィールド名
     **/
    protected static final String FILED_NAME_PAYMENT_TYPE = "].paymentType";

    /**
     * 分割回数 フィールド名
     **/
    protected static final String FILED_NAME_DIVIDED_NUMBER = "].dividedNumber";

    // PDR Migrate Customization from here
    /**
     * クレジットカード会社 フィールド名
     **/
    protected static final String FILED_NAME_CARD_BRAND = "].cardBrand";

    /**
     * 保持カード フィールド名
     **/
    protected static final String FILED_NAME_REGIST_CARD_SELECT = "].registCardSelect";

    /**
     * クレジットカード区分 フィールド名
     **/
    protected static final String FILED_NAME_CREDIT_CARD_TYPE = "].creditCardType";
    // PDR Migrate Customization to here

    /**
     * コンビニ フィールド名
     **/
    protected static final String FILED_NAME_CONVENIENCE = "].convenience";

    /**
     * 決済方法選択値 フィールド名
     **/
    protected static final String FILED_NAME_SETTLEMENT_METHOD = "settlementMethod";

    /**
     * メッセージID:トークン不備エラー
     */
    public static final String MSGCD_TOKEN = "TOKEN-001-001-A-E";

    /**
     * メッセージID:入力必須チェック
     **/
    public static final String MSGCD_REQUIRED_INPUT = "ORDER-PAYMENT-001";

    /**
     * メッセージID:選択必須チェック
     **/
    public static final String MSGCD_REQUIRED_SELECT = "ORDER-PAYMENT-010";

    /**
     * メッセージID:区分値チェック用
     */
    public static final String MSGCD_INVALID = "ORDER-PAYMENT-002";

    /**
     * メッセージID:カード番号イレギュラーチェック
     */
    public static final String MSGCD_CREDIT_CARD_NUMBER_INVALID = "ORDER-PAYMENT-003";

    /**
     * メッセージID:カード番号桁数誤りチェック
     */
    public static final String MSGCD_CREDIT_CARD_NUMBER_LENGTH = "ORDER-PAYMENT-004";

    /**
     * メッセージID:カード番号数値以外チェック
     */
    public static final String MSGCD_CREDIT_CARD_NUMBER_NOT_NUMBER = "ORDER-PAYMENT-005";

    /**
     * メッセージID:有効期限の期限切れチェック
     */
    public static final String MSGCD_EXPIRED_CARD = "ORDER-PAYMENT-006";

    /**
     * メッセージID:有効期限の日付不正チェック
     */
    public static final String MSGCD_NOT_DATE = "ORDER-PAYMENT-007";

    /**
     * メッセージID:セキュリティコード桁数誤りチェック
     */
    public static final String MSGCD_SECURITY_CODE_LENGTH = "ORDER-PAYMENT-008";

    /**
     * メッセージID:セキュリティコード数値以外チェック
     */
    public static final String MSGCD_SECURITY_CODE_NOT_NUMBER = "ORDER-PAYMENT-009";

    /**
     * カード番号イレギュラーチェック用エラーパターン
     */
    public static final String VALID_PATTERN_CREDIT_CARD_NUMBER_INVALID = "{HCreditCardNumberValidator.INVALID_detail}";

    /**
     * カード番号桁数誤りチェック用エラーパターン
     */
    public static final String VALID_PATTERN_CREDIT_CARD_NUMBER_LENGTH = "{HCreditCardNumberValidator.LENGTH_detail}";

    /**
     * カード番号数値以外チェック用エラーパターン
     */
    public static final String VALID_PATTERN_CREDIT_CARD_NUMBER_NOT_NUMBER =
                    "{HCreditCardNumberValidator.NOT_NUMBER_detail}";

    /**
     * 有効期限の期限切れチェック用エラーパターン
     */
    public static final String VALID_PATTERN_EXPIRED_CARD = "{HCreditCardExpirationDateValidator.EXPIRED_CARD_detail}";

    /**
     * 有効期限の日付不正チェック用エラーパターン
     */
    public static final String VALID_PATTERN_NOT_DATE = "{HDateValidator.NOT_DATE_detail}";

    /**
     * セキュリティコードの桁数誤りチェック用エラーパターン
     */
    public static final String VALID_PATTERN_SECURITY_CODE_LENGTH = "{HSecurityCodeValidator.LENGTH_detail}";

    /**
     * セキュリティコードが数値以外チェック用エラーパターン
     */
    public static final String VALID_PATTERN_SECURITY_CODE_NOT_NUMBER = "{HSecurityCodeValidator.NOT_NUMBER_detail}";

    // Paygent Customization from here
    /**
     * ペイジェント最小入力文字数
     */
    public static final int PAYGENT_MIN_LIMIT = 14;
    // Paygent Customization to here

    /**
     * 引数で渡ったクラスが、バリデーション対象か否か
     *
     * @param clazz クラス
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // PaymentModelがサポート対象
        // 加えて、各Modelと画面間引き渡し項目である「CartDto」も対象に含める
        //
        // ＜各ModelとCartDtoを対象に含める理由＞
        // 各ModelとCartDtoをFlashAttribute渡しにした場合、OrderControllerのInitBinderで落ちてしまうため・・・
        // supports対象でないDtoが渡されると、「バリデータチェックスルー」ではなく、「不正なオブジェクトが渡された」ということで例外発生する
        // ⇒各ModelとCartDtoも一旦supports対象にしつつ、validateをスルーさせる必要があるようだ・・・
        return OrderCommonModel.class.isAssignableFrom(clazz) || CartDto.class.isAssignableFrom(clazz)
               || ReceiverModel.class.isAssignableFrom(clazz) || DeliveryModel.class.isAssignableFrom(clazz)
               || PaymentModel.class.isAssignableFrom(clazz) || ConfirmModel.class.isAssignableFrom(clazz);
    }

    /**
     * クレジットカード及びコンビニ用の動的バリデータ
     *
     * @param target ターゲット
     * @param errors エラー
     */
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!PaymentGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、PaymentGroup以外の場合、チェックしない
            return;
        }

        PaymentModel model = PaymentModel.class.cast(target);

        String settlementMethod = model.getSettlementMethod();
        Map<String, SettlementDto> settlementDtoMap = model.getSettlementDtoMap();

        // 決済方法のチェック
        if (settlementMethod == null) {
            // 受注金額が 0円 の場合
            if (model.isOrderPriceZero()) {
                // 無料決済はdoConfirmでセットされるため、ここではチェック終了
                return;
            } else {
                // 決済方法未選択
                errors.rejectValue(FILED_NAME_SETTLEMENT_METHOD, MSGCD_REQUIRED_SELECT);
            }
        }

        if (settlementDtoMap == null || !settlementDtoMap.containsKey(settlementMethod)) {
            // 不正操作のため、ここではチェックしない
            return;
        }

        // エラーメッセージ対象フィールドのindexを作成
        PaymentModelItem checkItem = null;
        int index = 0;
        List<PaymentModelItem> paymentModelItems = model.getPaymentModelItems();
        for (PaymentModelItem curItem : paymentModelItems) {
            if (curItem.getSettlementMethodValue().equals(settlementMethod)) {
                // 選択した決済方法と利用可能決済手段が一致する場合、取得
                checkItem = curItem;

                break;
            }
            index++;
        }

        if (checkItem == null) {
            // 対象決済がない場合、チェックしない
            return;
        }

        SettlementDetailsDto settlementDetailsDto = settlementDtoMap.get(settlementMethod).getSettlementDetailsDto();

        // クレジットカードのチェック
        if (settlementDetailsDto.getSettlementMethodType().equals(CREDIT)) {
            errors = checkCredit(model, checkItem, index, errors);
            return;
        }

        // コンビニのチェック
        if (settlementDetailsDto.getSettlementMethodType().equals(CONVENIENCE)) {
            errors = checkConvenience(model, checkItem, index, errors);
            return;
        }
        return;
    }

    /**
     * 決済方法がクレジットカードの場合のチェック<br/>
     *
     * @param model     お支払い方法選択画面Model
     * @param checkItem 決済方法選択画面アイテム
     * @param index     index
     * @param errors    エラー
     * @return errors
     */
    public Errors checkCredit(PaymentModel model, PaymentModelItem checkItem, int index, Errors errors) {

        // チェック対象項目を取得
        // items項目を取得
        String cardNumber = checkItem.getCardNumber();
        String expirationDateYear = checkItem.getExpirationDateYear();
        String expirationDateMonth = checkItem.getExpirationDateMonth();
        String securityCode = checkItem.getSecurityCode();// 新規カード入力時はnull
        String paymentType = checkItem.getPaymentType();
        String dividedNumber = checkItem.getDividedNumber();
        // PDR Migrate Customization from here
        String cardBrand = checkItem.getCardBrand();
        String registCardSelect = checkItem.getRegistCardSelect();
        String creditCardType = checkItem.getCreditCardType();
        // PDR Migrate Customization to here

        // model項目を取得
        String token = model.getToken();
        Map<String, String> expirationDateYearItems = model.getExpirationDateYearItems();
        Map<String, String> expirationDateMonthItems = model.getExpirationDateMonthItems();
        Map<String, String> dividedNumberItems = model.getDividedNumberItems();
        // PDR Migrate Customization from here
        Map<String, String> cardBrandItems = model.getCardBrandItems();

        // トークンチェック開始（トークンがセットされている 又は 保持カードを使っている場合、チェックしない）
        if (!isTokenSetOrUseRegisteredCard(model, checkItem)) {
            // 必須
            if (StringUtils.isEmpty(token)) {
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CARD_NUMBER, MSGCD_TOKEN);
            }
            // カードを保存する場合、必須
            if (checkItem.isSaveFlg() && StringUtils.isEmpty(model.getKeepToken())) {
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CARD_NUMBER, MSGCD_TOKEN);
            }
        }

        // カード番号チェック開始（新しいカード選択時のみ）
        if (StringUtils.isNotEmpty(creditCardType) && HTypeCreditCardType.NEW_CARD.getValue().equals(creditCardType)) {
            // トークンがセットされている 又は 保持カードを使っている場合、チェックしない
            if (!isTokenSetOrUseRegisteredCard(model, checkItem)) {
                // PDR Migrate Customization to here
                // 必須
                if (StringUtils.isEmpty(cardNumber)) {
                    errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CARD_NUMBER, MSGCD_REQUIRED_INPUT);
                }

                // カード妥当性チェック
                HCreditCardNumberValidator creditCardNumberValidator = new HCreditCardNumberValidator();
                // Paygent Customization from here
                creditCardNumberValidator.setMinLimit(PAYGENT_MIN_LIMIT);
                // Paygent Customization to here
                if (!creditCardNumberValidator.isValid(cardNumber)) {
                    if (VALID_PATTERN_CREDIT_CARD_NUMBER_INVALID.equals(creditCardNumberValidator.getMessageId())) {
                        // カード番号として不適合の場合
                        errors.rejectValue(
                                        FILED_NAME_ITEMS + index + FILED_NAME_CARD_NUMBER,
                                        MSGCD_CREDIT_CARD_NUMBER_INVALID
                                          );
                    }
                    if (VALID_PATTERN_CREDIT_CARD_NUMBER_LENGTH.equals(creditCardNumberValidator.getMessageId())) {
                        // カード番号の桁数誤りの場合
                        errors.rejectValue(
                                        FILED_NAME_ITEMS + index + FILED_NAME_CARD_NUMBER,
                                        MSGCD_CREDIT_CARD_NUMBER_LENGTH
                                          );
                    }
                    if (VALID_PATTERN_CREDIT_CARD_NUMBER_NOT_NUMBER.equals(creditCardNumberValidator.getMessageId())) {
                        // カード番号が数値以外の場合
                        errors.rejectValue(
                                        FILED_NAME_ITEMS + index + FILED_NAME_CARD_NUMBER,
                                        MSGCD_CREDIT_CARD_NUMBER_NOT_NUMBER
                                          );
                    }
                }
            }
        }

        // PDR Migrate Customization from here
        // 有効期限チェック開始（新しいカード選択時のみ）
        if (StringUtils.isNotEmpty(creditCardType) && HTypeCreditCardType.NEW_CARD.getValue().equals(creditCardType)) {
            // トークンがセットされている 又は 保持カードを使っている場合、チェックしない
            if (!isTokenSetOrUseRegisteredCard(model, checkItem)) {
                // PDR Migrate Customization to here
                // 必須
                if (StringUtils.isEmpty(expirationDateYear)) {
                    errors.rejectValue(
                                    FILED_NAME_ITEMS + index + FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_REQUIRED_INPUT);
                }

                // 必須
                if (StringUtils.isEmpty(expirationDateMonth)) {
                    errors.rejectValue(
                                    FILED_NAME_ITEMS + index + FILED_NAME_EXPIRATION_DATE_MONTH, MSGCD_REQUIRED_INPUT);
                }

                // 何らかの操作で有効期限-年が不正に書き換えた場合を想定しチェック処理を追加
                // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
                if (!StringUtils.isEmpty(expirationDateYear) && !expirationDateYearItems.containsKey(
                                expirationDateYear)) {
                    // 有効期限の年が不正
                    errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_INVALID);
                }

                // 何らかの操作で有効期限-月が不正に書き換えた場合を想定しチェック処理を追加
                // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
                if (!StringUtils.isEmpty(expirationDateMonth) && !expirationDateMonthItems.containsKey(
                                expirationDateMonth)) {
                    // 有効期限の月が不正
                    errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_EXPIRATION_DATE_MONTH, MSGCD_INVALID);
                }

                // 有効期限妥当性チェック
                HCreditCardExpirationDateValidator creditCardExpirationDateValidator =
                                new HCreditCardExpirationDateValidator();
                if (!creditCardExpirationDateValidator.isValid(expirationDateMonth, expirationDateYear)) {
                    if (VALID_PATTERN_EXPIRED_CARD.equals(creditCardExpirationDateValidator.getMessageId())) {
                        // 有効期限が切れている場合
                        errors.rejectValue(
                                        FILED_NAME_ITEMS + index + FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_EXPIRED_CARD);
                    }
                    if (VALID_PATTERN_NOT_DATE.equals(creditCardExpirationDateValidator.getMessageId())) {
                        // 有効期限が数値以外の場合
                        errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_NOT_DATE);
                    }
                }
            }
        }

        // PDR Migrate Customization from here
        // セキュリティコードチェック開始（トークンがセットされているかまたは保持カードを使っている場合は検証しない）
        if (!isTokenSetOrUseRegisteredCard(model, checkItem)) {
            // PDR Migrate Customization to here
            // 必須
            if (StringUtils.isEmpty(securityCode)) {
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_SECURITY_CODE, MSGCD_REQUIRED_INPUT);
            }

            // セキュリティコード妥当性チェック
            HSecurityCodeValidator securityCodeValidator = new HSecurityCodeValidator();
            if (!securityCodeValidator.isValid(securityCode)) {
                if (VALID_PATTERN_SECURITY_CODE_LENGTH.equals(securityCodeValidator.getMessageId())) {
                    // セキュリティコードの桁数誤りの場合
                    errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_SECURITY_CODE, MSGCD_SECURITY_CODE_LENGTH);
                }
                if (VALID_PATTERN_SECURITY_CODE_NOT_NUMBER.equals(securityCodeValidator.getMessageId())) {
                    // セキュリティコードが数値以外の場合
                    errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_SECURITY_CODE,
                                       MSGCD_SECURITY_CODE_NOT_NUMBER
                                      );
                }
            }
        }

        // 支払区分チェック開始
        // 必須
        if (StringUtils.isEmpty(paymentType)) {
            errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_PAYMENT_TYPE, MSGCD_REQUIRED_INPUT);
        }

        // 支払区分に「分割」を選択している場合、分割回数は必須
        if (!StringUtils.isEmpty(paymentType) && paymentType.equals(INSTALLMENT.getValue())) {

            // 必須
            if (StringUtils.isEmpty(dividedNumber)) {
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_DIVIDED_NUMBER, MSGCD_REQUIRED_SELECT);
            }

            // 何らかの操作で分割回数が不正に書き換えた場合を想定しチェック処理を追加
            // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
            if (!StringUtils.isEmpty(dividedNumber) && !dividedNumberItems.containsKey(dividedNumber)) {
                // 分割回数が不正
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_DIVIDED_NUMBER, MSGCD_INVALID);
            }
        }

        // PDR Migrate Customization from here
        // クレジットカード会社チェック開始（新しいカード選択時のみ）
        if (StringUtils.isNotEmpty(creditCardType) && HTypeCreditCardType.NEW_CARD.getValue().equals(creditCardType)) {
            // 必須
            if (StringUtils.isEmpty(cardBrand)) {
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CARD_BRAND, MSGCD_REQUIRED_SELECT);
            }

            // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
            if (!StringUtils.isEmpty(cardBrand) && !cardBrandItems.containsKey(cardBrand)) {
                // クレジットカード会社が不正
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CARD_BRAND, MSGCD_INVALID);
            }
        }

        // 保持カードチェック開始（保持カード選択時のみ）
        if (StringUtils.isNotEmpty(creditCardType) && HTypeCreditCardType.REGISTERED_CARD.getValue()
                                                                                         .equals(creditCardType)) {
            // 必須
            if (StringUtils.isEmpty(registCardSelect)) {
                errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_REGIST_CARD_SELECT, MSGCD_REQUIRED_SELECT);
            }
        }

        // クレジットカード区分チェック開始
        // 必須
        if (StringUtils.isEmpty(creditCardType)) {
            errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CREDIT_CARD_TYPE, MSGCD_REQUIRED_SELECT);
        }
        // PDR Migrate Customization to here

        return errors;
    }

    /**
     * 決済方法がコンビニの場合のチェック<br/>
     *
     * @param model     お支払い方法選択画面Model
     * @param checkItem 決済方法選択画面アイテム
     * @param index     index
     * @param errors    エラー
     * @return errors
     */
    public Errors checkConvenience(PaymentModel model, PaymentModelItem checkItem, int index, Errors errors) {

        // チェック対象項目を取得
        String convenience = checkItem.getConvenience();
        Map<String, String> convenienceItems = model.getConvenienceItems();

        // 必須
        if (StringUtils.isEmpty(convenience)) {
            errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CONVENIENCE, MSGCD_REQUIRED_SELECT);
        }

        // 何らからの操作でコンビニが不正に書き換えた場合を想定しチェック処理を追加
        // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
        if (!StringUtils.isEmpty(convenience) && !convenienceItems.containsKey(convenience)) {
            errors.rejectValue(FILED_NAME_ITEMS + index + FILED_NAME_CONVENIENCE, MSGCD_INVALID);
        }

        return errors;
    }

    /**
     * トークンがセットされているかまたは保持カードを使っているか<br/>
     *
     * @param model     お支払い方法選択画面Model
     * @param checkItem 決済方法選択画面アイテム
     * @return true = 値がセットされている
     */
    public boolean isTokenSetOrUseRegisteredCard(PaymentModel model, PaymentModelItem checkItem) {

        // PDR Migrate Customization from here
        // 登録済みカードを使っている場合はトークンがセットされていることと同一視する
        if (HTypeCreditCardType.REGISTERED_CARD == EnumTypeUtil.getEnumFromValue(
                        HTypeCreditCardType.class, checkItem.getCreditCardType())) {
            return true;
        }
        // PDR Migrate Customization to here

        if (StringUtils.isEmpty(model.getToken())) {
            return false;
        }

        // Paygent Customization from here
        // カードを保存する場合、必須
        if (checkItem.isSaveFlg() && StringUtils.isEmpty(model.getKeepToken())) {
            return false;
        }
        // Paygent Customization to here

        return true;
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
