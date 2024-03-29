/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.annotation.validator;

import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual.List;
import jp.co.hankyuhanshin.itec.hitmall.validator.HDateGreaterEqualValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <span class="logicName">【日付比較（対象項目 ≧
 * 比較項目）】</span>日付比較チェック用バリデータのアノテーション。<br/>
 * targetに紐づけた項目がチェック対象
 *
 * @author kimura
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Constraint(validatedBy = HDateGreaterEqualValidator.class)
@Repeatable(List.class)
public @interface HVRDateGreaterEqual {

    /**
     * メッセージID
     *
     * @return メッセージID
     */
    String message() default HDateGreaterEqualValidator.NOT_GREATER_EQUAL_MESSAGE_ID;

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
     * 相関チェック対象のフィールド名<br/>
     * エラーメッセージを表示する項目
     *
     * @return 相関チェック対象のフィールド名
     */
    String target() default "";

    /**
     * 相関チェック比較対象のフィールド名
     *
     * @return 相関チェック比較対象のフィールド名
     */
    String comparison() default "";

    /**
     * 日付書式パターン
     *
     * @return 日付書式パターン
     */
    String pattern() default "yyyy/MM/dd";

    /**
     * 同一アノテーションの重複指定
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = ElementType.TYPE)
    public @interface List {
        HVRDateGreaterEqual[] value();
    }
}
