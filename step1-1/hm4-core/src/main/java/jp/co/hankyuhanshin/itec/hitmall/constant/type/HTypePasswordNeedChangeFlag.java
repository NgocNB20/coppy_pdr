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
 * パスワード変更要求フラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypePasswordNeedChangeFlag implements EnumType {

    /**
     * 要求しない
     */
    OFF("要求しない", "0"),

    /**
     * 要求する
     */
    ON("要求する", "1");

    /**
     * boolean に対応するフラグを返します。<br/>
     *
     * @param bool true, false
     * @return 引数がtrue:ON false:OFF
     */
    public static HTypePasswordNeedChangeFlag getFlagByBoolean(boolean bool) {
        return bool ? ON : OFF;
    }

    /**
     * doma用ファクトリメソッド
     */
    public static HTypePasswordNeedChangeFlag of(String value) {

        HTypePasswordNeedChangeFlag hType = EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class, value);
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
