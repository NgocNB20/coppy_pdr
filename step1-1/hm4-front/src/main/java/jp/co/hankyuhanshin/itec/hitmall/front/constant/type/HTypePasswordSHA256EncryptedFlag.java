/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * パスワードSHA256暗号化済みフラグ：列挙型
 *
 * @author kaneda
 */
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
     * ラベル<br/>
     */
    private String label;
    /**
     * 区分値<br/>
     */
    private String value;

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
}
