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
 * パスワードSHA256暗号化済みフラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypePasswordSHA256EncryptedFlag implements EnumType {

    /**
     * SHA-256暗号化：未 ※ラベル未使用
     */
    UNENCRYPTED("", "0"),

    /**
     * SHA-256暗号化：済 ※ラベル未使用
     */
    ENCRYPTED("", "1");

    /**
     * boolean に対応するフラグを返します。<br/>
     *
     * @param bool true, false
     * @return 引数がtrue:ENCRYPTED false:UNENCRYPTED
     */
    public static HTypePasswordSHA256EncryptedFlag getFlagByBoolean(boolean bool) {
        return bool ? ENCRYPTED : UNENCRYPTED;
    }

    /**
     * doma用ファクトリメソッド
     */
    public static HTypePasswordSHA256EncryptedFlag of(String value) {

        HTypePasswordSHA256EncryptedFlag hType =
                        EnumTypeUtil.getEnumFromValue(HTypePasswordSHA256EncryptedFlag.class, value);
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
