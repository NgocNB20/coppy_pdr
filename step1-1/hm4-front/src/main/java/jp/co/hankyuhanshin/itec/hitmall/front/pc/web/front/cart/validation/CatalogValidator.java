/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.NumberUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CatalogModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CatalogModelItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoAddCartGroup;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * 一括注文画面の動的バリデータクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
// PDR Migrate Customization from here
public class CatalogValidator implements SmartValidator {

    /**
     * 一括注文商品リスト
     **/
    protected static final String FILED_NAME_CATALOG_MODEL_ITEMS = "catalogItems[";

    /**
     * 注文数
     */
    protected static final String FIELD_NAME_ORDER_QUANTITY = "].orderQuantity";

    /**
     * 空でない
     */
    public static final String MSGCD_NOT_EMPTY = "javax.validation.constraints.NotEmpty.message";

    /**
     * マイナスの数値だった場合（マイナス不許可だった場合）
     */
    public static final String MINUS_NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.MINUS_NUMBER_detail";

    /**
     * 数値でなかった場合
     */
    public static final String NOT_NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.NOT_NUMBER_detail";

    /**
     * エラーコード：整数
     */
    public static final String FRACTION_MESSAGE_ID_MAX_ZERO =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberLengthValidator.FRACTION_MAX_ZERO_detail";

    /**
     * エラーコード：整数
     */
    public static final String INTEGRAL_MESSAGE_ID_DETAIL =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberLengthValidator.INTEGRAL_detail";

    /**
     * エラーコード：範囲外
     */
    public static final String NOT_IN_RANGE_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberRangeValidator.NOT_IN_RANGE_detail";

    /**
     * FRACTION数
     */
    public static final Integer ORDER_QUANTITY_MAX_FRACTION = 0;

    /**
     * INTEGRAL
     */
    public static final int INTEGRAL_DEFAULT_MIN = 1;

    /**
     * 引数で渡ったクラスが、バリデーション対象か否か
     *
     * @param clazz クラス
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return CatalogModel.class.isAssignableFrom(clazz);
    }

    /**
     * 一括注文画面用の動的バリデータ
     *
     * @param target          ターゲット
     * @param errors          エラー
     * @param validationHints validationHints
     */
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (ObjectUtils.isEmpty(validationHints)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!DoAddCartGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupがDoAddCartGroup以外の場合、チェックしない
            return;
        }

        CatalogModel catalogModel = CatalogModel.class.cast(target);

        // 現在処理中の行に入力されている商品番号を取得する
        // このタイミングではPageクラスに入力値が反映されていないため、リクエストから値を取得

        for (int i = 0; i < catalogModel.getCatalogItems().size(); i++) {
            CatalogModelItem catalogModelItem = catalogModel.getCatalogItems().get(i);
            String goodsCode = catalogModelItem.getGoodsCode();

            // 商品番号が入力されていない行は破棄するため、バリデート処理を実施しない
            if (goodsCode == null) {
                continue;
            }

            // 注文数の入力チェックを行う
            String fieldNameOrderQuantity = FILED_NAME_CATALOG_MODEL_ITEMS + i + FIELD_NAME_ORDER_QUANTITY;
            String orderQuantity = catalogModelItem.getOrderQuantity();

            // 必須チェック
            if (StringUtil.isEmpty(orderQuantity)) {
                errors.rejectValue(fieldNameOrderQuantity, MSGCD_NOT_EMPTY);
                continue;
            }

            // 数値関連Helper取得
            NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);
            // 数値かどうか
            if (!numberUtility.isNumber(orderQuantity)) {
                errors.rejectValue(fieldNameOrderQuantity, NOT_NUMBER_MESSAGE_ID, new String[] {"注文数"}, null);
                continue;
            }

            // 負の数値かどうか
            if (orderQuantity.charAt(0) == '-') {
                errors.rejectValue(fieldNameOrderQuantity, MINUS_NUMBER_MESSAGE_ID, new String[] {"注文数"}, null);
                continue;
            }

            String orderQuantityDigits = StringUtils.getDigits(orderQuantity);
            String fractionOrderQuantity = orderQuantity.substring(orderQuantity.indexOf('.') + 1);
            if (orderQuantity.indexOf('.') != -1) {
                if (fractionOrderQuantity.length() > ORDER_QUANTITY_MAX_FRACTION) {
                    errors.rejectValue(
                                    fieldNameOrderQuantity, FRACTION_MESSAGE_ID_MAX_ZERO, new String[] {"注文数"}, null);
                    continue;
                }
            }

            final boolean integralSuccess = validateIntegral(orderQuantityDigits);
            if (!integralSuccess) {
                errors.rejectValue(fieldNameOrderQuantity, INTEGRAL_MESSAGE_ID_DETAIL,
                                   new String[] {"注文数", String.valueOf(INTEGRAL_DEFAULT_MIN),
                                                   getIntegerMaxValueString()}, null
                                  );
                continue;
            }

            if (Integer.parseInt(orderQuantity) < 1 || Integer.parseInt(orderQuantity) > 999) {
                errors.rejectValue(fieldNameOrderQuantity, NOT_IN_RANGE_MESSAGE_ID, new String[] {"1", "999", "注文数"},
                                   null
                                  );
                continue;
            }

        }
    }

    /**
     * 整数部の桁数チェック
     *
     * @param digits digits
     * @return true..OK / false..NG
     */
    protected boolean validateIntegral(String digits) {

        if (digits.length() < INTEGRAL_DEFAULT_MIN || String.valueOf(Integer.MAX_VALUE).length() < digits.length()) {
            return false;
        }
        return true;
    }

    /**
     * 整数の最大値を取得する
     *
     * @return 文字列
     */
    private static String getIntegerMaxValueString() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(Integer.MAX_VALUE);
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
// PDR Migrate Customization to here
