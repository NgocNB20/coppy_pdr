/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card.MemberCardModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card.validation.group.MemberCardGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HCreditCardExpirationDateValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HCreditCardNumberValidator;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.Map;
import java.util.Objects;

/**
 * カード情報画面 Validator
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
// 2023-renew No60 from here
public class MemberCardValidator implements SmartValidator {

    /**
     * カード番号 フィールド名
     */
    protected static final String FILED_NAME_CARD_NUMBER = "cardNumber";

    /**
     * 有効期限：月 フィールド名
     */
    protected static final String FILED_NAME_EXPIRATION_DATE_MONTH = "expirationDateMonth";

    /**
     * 有効期限：年 フィールド名
     */
    protected static final String FILED_NAME_EXPIRATION_DATE_YEAR = "expirationDateYear";

    /**
     * クレジットカード会社 フィールド名
     */
    protected static final String FILED_NAME_CARD_BRAND = "cardBrand";

    /**
     * メッセージID:トークン不備エラー
     */
    public static final String MSGCD_TOKEN = "TOKEN-001-001-A-E";

    /**
     * メッセージID:入力必須チェック
     */
    public static final String MSGCD_REQUIRED_INPUT = "ORDER-PAYMENT-001";

    /**
     * メッセージID:選択必須チェック
     */
    public static final String MSGCD_REQUIRED_SELECT = "ORDER-PAYMENT-010";

    /**
     * メッセージID:区分値チェック用
     */
    public static final String MSGCD_INVALID = "ORDER-PAYMENT-002";

    /**
     * メッセージID:区分値チェック用（クレジットカード会社用）
     */
    public static final String MSGCD_INVALID_CARD_BRAND = "PDR-2023RENEW-60-001-E";

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
        return MemberCardModel.class.isAssignableFrom(clazz);
    }

    /**
     * クレジットカード用の動的バリデータ
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

        if (!MemberCardGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、MemberCardGroup以外の場合、チェックしない
            return;
        }

        // カード情報画面 Model
        MemberCardModel model = MemberCardModel.class.cast(target);

        // トークンチェック
        checkToken(model, errors);

        // カード番号チェック
        checkCardNo(model, errors);

        // 有効期限チェック
        checkExpirationDate(model, errors);

        // クレジットカード会社チェック
        checkCardBand(model, errors);

    }

    /**
     * トークンがセットされているか<br/>
     *
     * @param model カード情報画面 Model
     * @return true = 値がセットされている
     */
    public boolean isTokenSetOrUseRegisteredCard(MemberCardModel model) {
        if (StringUtils.isEmpty(model.getToken())) {
            return false;
        }
        if (StringUtils.isEmpty(model.getKeepToken())) {
            return false;
        }
        return true;
    }

    /**
     * トークンチェック
     *
     * @param model カード情報画面 Model
     * @param errors エラー
     */
    public void checkToken(MemberCardModel model, Errors errors) {
        if (!isTokenSetOrUseRegisteredCard(model)) {
            // 必須
            if (StringUtils.isEmpty(model.getToken())) {
                errors.rejectValue(FILED_NAME_CARD_NUMBER, MSGCD_TOKEN);
            }
            // 必須
            if (StringUtils.isEmpty(model.getKeepToken())) {
                errors.rejectValue(FILED_NAME_CARD_NUMBER, MSGCD_TOKEN);
            }
        }
    }

    /**
     * カード番号チェック
     *
     * @param model カード情報画面 Model
     * @param errors エラー
     */
    private void checkCardNo(MemberCardModel model, Errors errors) {

        String cardNumber = model.getCardNumber();

        // トークンがセットされている場合、チェックしない
        if (!isTokenSetOrUseRegisteredCard(model)) {

            // 必須
            if (StringUtils.isEmpty(cardNumber)) {
                errors.rejectValue(FILED_NAME_CARD_NUMBER, MSGCD_REQUIRED_INPUT);
            }

            // カード妥当性チェック
            HCreditCardNumberValidator creditCardNumberValidator = new HCreditCardNumberValidator();
            // Paygent Customization from here
            creditCardNumberValidator.setMinLimit(PAYGENT_MIN_LIMIT);
            // Paygent Customization to here
            if (!creditCardNumberValidator.isValid(cardNumber)) {
                if (VALID_PATTERN_CREDIT_CARD_NUMBER_INVALID.equals(creditCardNumberValidator.getMessageId())) {
                    // カード番号として不適合の場合
                    errors.rejectValue(FILED_NAME_CARD_NUMBER, MSGCD_CREDIT_CARD_NUMBER_INVALID);
                }
                if (VALID_PATTERN_CREDIT_CARD_NUMBER_LENGTH.equals(creditCardNumberValidator.getMessageId())) {
                    // カード番号の桁数誤りの場合
                    errors.rejectValue(FILED_NAME_CARD_NUMBER, MSGCD_CREDIT_CARD_NUMBER_LENGTH);
                }
                if (VALID_PATTERN_CREDIT_CARD_NUMBER_NOT_NUMBER.equals(creditCardNumberValidator.getMessageId())) {
                    // カード番号が数値以外の場合
                    errors.rejectValue(FILED_NAME_CARD_NUMBER, MSGCD_CREDIT_CARD_NUMBER_NOT_NUMBER);
                }
            }
        }
    }

    /**
     * 有効期限チェック
     *
     * @param model カード情報画面 Model
     * @param errors エラー
     */
    public void checkExpirationDate(MemberCardModel model, Errors errors) {

        String expirationDateYear = model.getExpirationDateYear();
        String expirationDateMonth = model.getExpirationDateMonth();
        Map<String, String> expirationDateYearItems = model.getExpirationDateYearItems();
        Map<String, String> expirationDateMonthItems = model.getExpirationDateMonthItems();

        // トークンがセットされている場合、チェックしない
        if (!isTokenSetOrUseRegisteredCard(model)) {

            // 必須
            if (StringUtils.isEmpty(expirationDateYear)) {
                errors.rejectValue(FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_REQUIRED_INPUT);
            }

            // 必須
            if (StringUtils.isEmpty(expirationDateMonth)) {
                errors.rejectValue(FILED_NAME_EXPIRATION_DATE_MONTH, MSGCD_REQUIRED_INPUT);
            }

            // 何らかの操作で有効期限-年が不正に書き換えた場合を想定しチェック処理を追加
            // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
            if (!StringUtils.isEmpty(expirationDateYear) && !expirationDateYearItems.containsKey(expirationDateYear)) {
                // 有効期限の年が不正
                errors.rejectValue(FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_INVALID);
            }

            // 何らかの操作で有効期限-月が不正に書き換えた場合を想定しチェック処理を追加
            // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
            if (!StringUtils.isEmpty(expirationDateMonth) && !expirationDateMonthItems.containsKey(
                            expirationDateMonth)) {
                // 有効期限の月が不正
                errors.rejectValue(FILED_NAME_EXPIRATION_DATE_MONTH, MSGCD_INVALID);
            }

            // 有効期限妥当性チェック
            HCreditCardExpirationDateValidator creditCardExpirationDateValidator =
                            new HCreditCardExpirationDateValidator();
            if (!creditCardExpirationDateValidator.isValid(expirationDateMonth, expirationDateYear)) {
                if (VALID_PATTERN_EXPIRED_CARD.equals(creditCardExpirationDateValidator.getMessageId())) {
                    // 有効期限が切れている場合
                    errors.rejectValue(FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_EXPIRED_CARD);
                }
                if (VALID_PATTERN_NOT_DATE.equals(creditCardExpirationDateValidator.getMessageId())) {
                    // 有効期限が数値以外の場合
                    errors.rejectValue(FILED_NAME_EXPIRATION_DATE_YEAR, MSGCD_NOT_DATE);
                }
            }
        }
    }

    /**
     * クレジットカード会社チェック
     *
     * @param model カード情報画面 Model
     * @param errors エラー
     */
    public void checkCardBand(MemberCardModel model, Errors errors) {

        String cardBrand = model.getCardBrand();
        Map<String, String> cardBrandItems = model.getCardBrandItems();

        // 必須
        if (StringUtils.isEmpty(cardBrand)) {
            errors.rejectValue(FILED_NAME_CARD_BRAND, MSGCD_REQUIRED_SELECT);
        }

        // 不正値が設定され、以降の処理で想定外の動作が発生するのを防ぐ為。
        if (!StringUtils.isEmpty(cardBrand) && !cardBrandItems.containsKey(cardBrand)) {
            // クレジットカード会社が不正
            errors.rejectValue(FILED_NAME_CARD_BRAND, MSGCD_INVALID_CARD_BRAND);
        }
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }

}
// 2023-renew No60 to here
