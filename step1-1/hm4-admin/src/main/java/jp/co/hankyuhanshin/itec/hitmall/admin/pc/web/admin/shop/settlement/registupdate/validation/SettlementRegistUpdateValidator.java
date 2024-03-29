package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement.registupdate.validation;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement.registupdate.SettlementRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement.registupdate.SettlementRegistUpdateModelItem;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NumberUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Component
public class SettlementRegistUpdateValidator implements SmartValidator {

    /**
     * 一律手数料（円）
     */
    public static final String FILED_NAME_EQUALS_COMMISSION_YEN = "equalsCommissionYen";

    /**
     * 最大購入金額（円）
     */
    public static final String FILED_NAME_MAX_PURCHASED_PRICE_YEN = "maxPurchasedPriceYen";

    /**
     * 高額割引下限金額
     */
    public static final String FILED_NAME_LARGE_AMOUNT_DISCOUNT_PRICE_YEN = "largeAmountDiscountPriceYen";

    /**
     * 高額割引手数料
     */
    public static final String FILED_NAME_LARGE_AMOUNT_DISCOUNT_COMMISSION_YEN = "largeAmountDiscountCommissionYen";

    /**
     * 支払期限猶予日数
     */
    public static final String FILED_NAME_PAYMENT_TIME_LIMIT_DAY_COUNT = "paymentTimeLimitDayCount";

    /**
     * 期限後取消猶予日数
     */
    public static final String FILED_NAME_CANCEL_TIME_LIMIT_DAY_COUNT = "cancelTimeLimitDayCount";

    /**
     * 決済方法金額別手数料リスト（円）
     */
    public static final String FILED_NAME_PRICE_COMMISSION_YEN = "priceCommissionYen[";

    /**
     * 決済方法金額別手数料リスト（円）の上限金額
     */
    public static final String FILED_NAME_PRICE_COMMISSION_YEN_MAX_PRICE = "].maxPrice";

    /**
     * 決済方法金額別手数料リスト（円）の手数料
     */
    public static final String FILED_NAME_PRICE_COMMISSION_YEN_COMMISSION = "].commission";

    /**
     * エラーコード：必須
     */
    public static final String MSGCD_REQUIRED_INPUT =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HRequiredValidator.INPUT_detail";

    /**
     * エラーコード：よりも大きい値
     */
    public static final String MSGCD_GREATER_THAN_CONSTANT =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberGreaterThanConstantValidator.GREATER_THAN_CONSTANT_detail";

    /**
     * エラーコード：必須
     */
    public static final String MSGCD_REQUIRED_INPUT_NO_PARAM = "javax.validation.constraints.NotEmpty.message";

    @Override
    public boolean supports(Class<?> clazz) {
        return SettlementRegistUpdateModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!ConfirmGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、ConfirmGroup以外の場合、チェックしない
            return;
        }

        SettlementRegistUpdateModel model = (SettlementRegistUpdateModel) target;

        if (!StringUtils.isEmpty(model.getSettlementMethodCommissionType())) {
            // 一律（円）の場合、最大購入金額（円）と一律手数料（円）の必須チェック
            if (model.getSettlementMethodCommissionType()
                     .equals(HTypeSettlementMethodCommissionType.FLAT_YEN.getValue())) {
                if (StringUtils.isEmpty(model.getMaxPurchasedPriceYen())) {
                    errors.rejectValue(FILED_NAME_MAX_PURCHASED_PRICE_YEN, MSGCD_REQUIRED_INPUT_NO_PARAM);
                }

                if (StringUtils.isEmpty(model.getEqualsCommissionYen())) {
                    errors.rejectValue(FILED_NAME_EQUALS_COMMISSION_YEN, MSGCD_REQUIRED_INPUT_NO_PARAM);
                }
            }

            // 一律（円）の場合、最大購入金額（円）と一律手数料（円）の必須チェック
            if (model.getSettlementMethodCommissionType()
                     .equals(HTypeSettlementMethodCommissionType.EACH_AMOUNT_YEN.getValue())) {
                for (int i = 0; i < model.getPriceCommissionYen().size(); i++) {
                    SettlementRegistUpdateModelItem item = model.getPriceCommissionYen().get(i);
                    if (!StringUtils.isEmpty(item.getMaxPrice()) && StringUtils.isEmpty(item.getCommission())) {
                        errors.rejectValue(FILED_NAME_PRICE_COMMISSION_YEN + i
                                           + FILED_NAME_PRICE_COMMISSION_YEN_COMMISSION, MSGCD_REQUIRED_INPUT_NO_PARAM);
                    }

                    if (StringUtils.isEmpty(item.getMaxPrice()) && !StringUtils.isEmpty(item.getCommission())) {
                        errors.rejectValue(
                                        FILED_NAME_PRICE_COMMISSION_YEN + i + FILED_NAME_PRICE_COMMISSION_YEN_MAX_PRICE,
                                        MSGCD_REQUIRED_INPUT_NO_PARAM
                                          );
                    }
                }
            }
        }

        // 高額割引手数料入力する場合、高額割引下限金額の必須チェック
        if (!StringUtils.isEmpty(model.getLargeAmountDiscountCommissionYen())) {
            if (StringUtils.isEmpty(model.getLargeAmountDiscountPriceYen())) {
                errors.rejectValue(FILED_NAME_LARGE_AMOUNT_DISCOUNT_PRICE_YEN, MSGCD_REQUIRED_INPUT_NO_PARAM);
            }
        }

        // 高額割引下限金額入力する場合、高額割引手数料の必須チェック
        if (!StringUtils.isEmpty(model.getLargeAmountDiscountPriceYen())) {
            if (StringUtils.isEmpty(model.getLargeAmountDiscountCommissionYen())) {
                errors.rejectValue(FILED_NAME_LARGE_AMOUNT_DISCOUNT_COMMISSION_YEN, MSGCD_REQUIRED_INPUT_NO_PARAM);
            }
        }

        // クレジット または 前払い(代金引換以外)の決済の場合入力チェックを行う
        if (model.isSettlementMethodTypeCredit() || (
                        !HTypeSettlementMethodType.RECEIPT_PAYMENT.getValue().equals(model.getSettlementMethodType())
                        && HTypeBillType.PRE_CLAIM.getValue().equals(model.getBillType()))) {

            NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);

            if (StringUtils.isEmpty(model.getPaymentTimeLimitDayCount())) {
                errors.rejectValue(FILED_NAME_PAYMENT_TIME_LIMIT_DAY_COUNT, MSGCD_REQUIRED_INPUT_NO_PARAM);
            } else if (!model.isSettlementMethodTypeCredit()) {
                // 指定された数値より大きいかのチェック
                if (numberUtility.isNumber(model.getPaymentTimeLimitDayCount())) {
                    BigDecimal paymentTimeLimitDayCount = new BigDecimal(model.getPaymentTimeLimitDayCount());
                    BigDecimal paymentTimeLimitDayCountTarget = new BigDecimal("0");
                    if (!compareToTarget(paymentTimeLimitDayCount, paymentTimeLimitDayCountTarget)) {
                        errors.rejectValue(FILED_NAME_PAYMENT_TIME_LIMIT_DAY_COUNT, MSGCD_GREATER_THAN_CONSTANT,
                                           new String[] {"0", "支払期限猶予日数"}, null
                                          );
                    }
                }
            }

            if (StringUtils.isEmpty(model.getCancelTimeLimitDayCount())) {
                errors.rejectValue(FILED_NAME_CANCEL_TIME_LIMIT_DAY_COUNT, MSGCD_REQUIRED_INPUT_NO_PARAM);
            } else if (!model.isSettlementMethodTypeCredit()) {
                // 指定された数値より大きいかのチェック
                if (numberUtility.isNumber(model.getCancelTimeLimitDayCount())) {
                    BigDecimal cancelTimeLimitDayCount = new BigDecimal(model.getCancelTimeLimitDayCount());
                    BigDecimal cancelTimeLimitDayCountTarget = new BigDecimal("0");
                    if (!compareToTarget(cancelTimeLimitDayCount, cancelTimeLimitDayCountTarget)) {
                        errors.rejectValue(FILED_NAME_CANCEL_TIME_LIMIT_DAY_COUNT, MSGCD_GREATER_THAN_CONSTANT,
                                           new String[] {"0", "期限後取消猶予日数"}, null
                                          );
                    }
                }
            }
        }

    }

    private boolean compareToTarget(BigDecimal inputValue, BigDecimal targetValue) {
        return inputValue.compareTo(targetValue) > 0;
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
