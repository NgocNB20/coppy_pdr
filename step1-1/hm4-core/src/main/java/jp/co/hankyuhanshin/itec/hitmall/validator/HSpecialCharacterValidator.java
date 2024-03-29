/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.validator;

import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <span class="defaultValidator">★デフォルトバリデータ</span><br />
 * <span class="logicName">【特殊文字】</span>画面コンポーネント用特殊文字チェックバリデータ。<br />
 * <br />
 * デフォルトの挙動：文字列に記号や制御文字（タブなど）が存在する場合エラー<br />
 * バリデータを指定すれば、記号、制御文字が入力可能となる<br />
 * <br />
 *
 * <pre>
 * 以下の文字が入力されている場合、入力エラーとする
 * 0x00-0x9Fまでの文字のうち
 * ・制御文字
 *  0x00-0x1F, 0x7F-0x9F
 * ・句読文字
 *  !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
 *
 * 句読文字、タブ(0x09), LF(0x0A), CR(0x0D)についてはallowCharactersに指定することで入力を許可することが可能
 * </pre>
 *
 * @author kimura
 */
@Data
public class HSpecialCharacterValidator implements ConstraintValidator<HVSpecialCharacter, Object> {

    /**
     * 入力されていない場合
     */
    public static final String MESSAGE_ID = "{HSpecialCharacterValidator.INVALID_detail}";

    /**
     * 制御文字 0x80 - 0x9F \p{Cntrl}には含まれない為独自に定義する
     */
    public static final String CONTROL_CHARCTER_0X80_0X9F = "\\x80\\x81\\x82\\x83\\x84\\x85\\x86"
                                                            + "\\x87\\x88\\x89\\x8a\\x8b\\x8c\\x8d\\x8e\\x8f\\x90\\x91\\x92\\x93\\x94"
                                                            + "\\x95\\x96\\x97\\x98\\x99\\x9a\\x9b\\x9c\\x9d\\x9e\\x9f";

    /**
     * 特殊文字が含まれない正規表現
     */
    public static final String NO_SPECIAL_CHARACTER_REGEX =
                    "[^\\p{Cntrl}\\p{Punct}" + CONTROL_CHARCTER_0X80_0X9F + "]*";

    /**
     * 常に拒否する文字列
     */
    protected static final String DENY_CHARACTERS = "\\x00\\x01\\x02\\x03\\x04\\x05\\x06\\x07\\x08"
                                                    + "\\x0b\\x0c\\x0e\\x0f\\x10\\x11\\x12\\x13\\x14\\x15\\x16\\x17\\x18\\x19\\x1a\\x1b\\x1c\\x1d\\x1e\\x1f\\x7f"
                                                    + CONTROL_CHARCTER_0X80_0X9F;

    /**
     * 指定された場合に許可する文字とエスケープ後の値のマップ
     */
    protected static final Map<Character, String> ALLOWABLE_CHARACTERS_MAP;

    /**
     * 許可する文字
     */
    protected char[] allowCharacters;

    /**
     * 句読文字を許可するかどうか
     */
    protected boolean allowPunctuation;

    /**
     * メッセージID
     */
    private String messageId;

    static {
        /*
         * デフォルトでは拒否するが設定によって許可可能な文字
         */
        ALLOWABLE_CHARACTERS_MAP = new LinkedHashMap<>();

        // \t, \r, \nは許可可能
        ALLOWABLE_CHARACTERS_MAP.put((char) 9, "\\x0" + Integer.toHexString(9));
        ALLOWABLE_CHARACTERS_MAP.put((char) 10, "\\x0" + Integer.toHexString(10));
        ALLOWABLE_CHARACTERS_MAP.put((char) 13, "\\x0" + Integer.toHexString(13));

        for (int i = 33; i <= 47; i++) {
            ALLOWABLE_CHARACTERS_MAP.put((char) i, "\\x" + Integer.toHexString(i));
        }

        for (int i = 58; i <= 64; i++) {
            ALLOWABLE_CHARACTERS_MAP.put((char) i, "\\x" + Integer.toHexString(i));
        }

        for (int i = 91; i <= 96; i++) {
            ALLOWABLE_CHARACTERS_MAP.put((char) i, "\\x" + Integer.toHexString(i));
        }

        for (int i = 123; i <= 126; i++) {
            ALLOWABLE_CHARACTERS_MAP.put((char) i, "\\x" + Integer.toHexString(i));
        }
    }

    /**
     * アノテーションの情報設定
     */
    @Override
    public void initialize(HVSpecialCharacter annotation) {
        messageId = annotation.message();
        allowCharacters = annotation.allowCharacters();
        allowPunctuation = annotation.allowPunctuation();
    }

    /**
     * バリデーション実行
     *
     * @return チェックエラーの場合 false
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        // null or 空の場合未実施
        if (Objects.equals(value, null) || Objects.equals(value, "")) {
            return true;
        }

        String strValue = value.toString();
        if (!Pattern.matches(createPattern(), strValue)) {
            return false;
        }
        return true;
    }

    /**
     * 設定条件よりパターンを作成する。<br/>
     *
     * @return パターン
     */
    protected String createPattern() {
        String pattern;
        if (allowPunctuation) {
            // 句読文字とtab,改行を許可する場合
            pattern = "[^" + DENY_CHARACTERS + "]*";
        } else if (allowCharacters != null && allowCharacters.length != 0) {
            // 一旦、すべての許可可能文字を拒否とし、許可された文字のみ除外する。
            List<Character> denyCharacterList = new ArrayList<>(ALLOWABLE_CHARACTERS_MAP.keySet());
            for (Character allow : allowCharacters) {
                denyCharacterList.remove(allow);
            }

            // パターンを作成
            StringBuilder customizePattern = new StringBuilder();
            customizePattern.append("[^").append(DENY_CHARACTERS);

            for (Character deny : denyCharacterList) {
                customizePattern.append(ALLOWABLE_CHARACTERS_MAP.get(deny));
            }

            customizePattern.append("]*");
            pattern = customizePattern.toString();
        } else {
            // 許可する文字が設定されていない場合、デフォルトの条件を利用
            pattern = NO_SPECIAL_CHARACTER_REGEX;
        }

        return pattern;
    }

}
