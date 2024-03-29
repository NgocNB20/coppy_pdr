/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.annotation.validator;

import jp.co.hankyuhanshin.itec.hitmall.validator.HMailAddressExtendedArrayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <span class="logicName">【メールアドレス（拡張版
 * 複数用）】</span>カンマ区切りで複数の人名&lt;mail@address&gt; 形式のメールアドレスバリデータのアノテーション。<br />
 *
 * @author kimura
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = HMailAddressExtendedArrayValidator.class)
public @interface HVMailAddressExtendedArray {

    /**
     * メッセージID
     *
     * @return メッセージID
     */
    String message() default HMailAddressExtendedArrayValidator.INVALID_MESSAGE_ID;

    /**
     * バリデーショングループ
     *
     * @return バリデーショングループ
     */
    Class<?>[] groups() default {};

    /**
     * ペイロード
     *
     * @return ペイロード
     */
    Class<? extends Payload>[] payload() default {};
}
