/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.annotation.validator;

import jp.co.hankyuhanshin.itec.hitmall.validator.HSpecialCharacterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <span class="defaultValidator">★デフォルトバリデータ</span><br />
 * <span class="logicName">【特殊文字】</span>特殊文字チェックバリデータのアノテーション。<br/>
 *
 * @author kimura
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = HSpecialCharacterValidator.class)
public @interface HVSpecialCharacter {

    /**
     * メッセージID
     *
     * @return メッセージID
     */
    String message() default HSpecialCharacterValidator.MESSAGE_ID;

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

    /**
     * 許可するキャラクタ
     *
     * @return 許可するキャラクタ
     */
    char[] allowCharacters() default {};

    /**
     * 句読文字とtab, 改行を許可するかどうか
     *
     * @return 句読文字とtab, 改行を許可する:true しない:false
     */
    boolean allowPunctuation() default false;
}
