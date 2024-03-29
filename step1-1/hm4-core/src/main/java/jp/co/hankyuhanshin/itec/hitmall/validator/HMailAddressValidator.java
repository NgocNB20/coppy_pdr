/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.validator;

import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddress;
import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <span class="logicName">【メールアドレス】</span>メールアドレス用バリデータクラス。<br />
 * <br />
 * 文字列がメールアドレスとして妥当でない場合エラー<br />
 *
 * @author kamei
 *
 */
@Data
public class HMailAddressValidator implements ConstraintValidator<HVMailAddress, Object> {

    /**
     * メールアドレスではなかった場合
     */
    public static final String INVALID_MESSAGE_ID = "{HMailAddressValidator.INVALID_detail}";

    /**
     * メールアドレスに半角以外が入力されていた場合
     */
    public static final String NOT_HANKAKU_MESSAGE_ID = "{HMailAddressValidator.NOT_HANKAKU_detail}";

    /** メールアドレスの正規表現 */
    public static final String MAIL_ADDRESS_REGEX =
                    "^[a-zA-Z0-9!#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\|\\}~\\.]+@(([-a-z0-9]+\\.)*[a-z]+|\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\])";

    /** 半角 */
    public static final String MAIL_ADDRESS_HANKAKU = "^[ -~｡-ﾟ\\s]+$";

    /**
     * バリデーション実行
     *
     * @param value 値
     * @param context コンテキスト
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // null or 空の場合未実施
        if (Objects.equals(value, null) || Objects.equals(value, "")) {
            return true;
        }

        String strValue = value.toString();
        // 半角文字以外が入力されている場合エラー
        if (!Pattern.matches(MAIL_ADDRESS_HANKAKU, strValue)) {
            // 動的バリデーションによる手動バリデータの発火はこちらの設定は不要
            if (context != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(NOT_HANKAKU_MESSAGE_ID).addConstraintViolation();
            }
            return false;
        }

        // メールアドレスの正規表現にマッチしない場合エラー
        if (!Pattern.matches(MAIL_ADDRESS_REGEX, strValue)) {
            // 動的バリデーションによる手動バリデータの発火はこちらの設定は不要
            if (context != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(INVALID_MESSAGE_ID).addConstraintViolation();
            }
            return false;
        }
        return true;
    }

}
