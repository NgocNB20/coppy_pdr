/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Domain;

/**
 * 確認メール機能ID
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeConfirmMailType implements EnumType {

    /**
     * 仮会員登録 ※ラベル未使用
     */
    TEMP_MEMBERINFO_REGIST("", "0"),

    /**
     * 会員メールアドレス変更 ※ラベル未使用
     */
    MEMBERINFO_MAIL_UPDATE("", "1"),

    /**
     * パスワード再発行 ※ラベル未使用
     */
    PASSWORD_REISSUE("", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeConfirmMailType of(String value) {

        HTypeConfirmMailType hType = EnumTypeUtil.getEnumFromValue(HTypeConfirmMailType.class, value);
        return hType;
    }

    /**
     * ラベル<br/>
     */
    private String label;

    /**
     * 区分値<br/>
     */
    private String value;
}
