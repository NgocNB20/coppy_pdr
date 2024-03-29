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
 * 保留中フラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeWaitingFlag implements EnumType {

    /**
     * 通常
     */
    OFF("通常", "0"),

    /**
     * 保留中
     */
    ON("保留中", "1");

    /**
     * boolean に対応するフラグを返します。<br/>
     *
     * @param bool true, false
     * @return 引数がtrue:ON false:OFF
     */
    public static HTypeWaitingFlag getFlagByBoolean(boolean bool) {
        return bool ? ON : OFF;
    }

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeWaitingFlag of(String value) {

        HTypeWaitingFlag hType = EnumTypeUtil.getEnumFromValue(HTypeWaitingFlag.class, value);
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
