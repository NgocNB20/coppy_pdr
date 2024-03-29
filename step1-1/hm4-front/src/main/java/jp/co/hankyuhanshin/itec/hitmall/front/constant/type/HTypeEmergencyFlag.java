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
 * 異常フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeEmergencyFlag implements EnumType {

    /**
     * 正常
     */
    OFF("通常", "0"),

    /**
     * 異常
     */
    ON("異常あり", "1");

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
     * @return 引数がtrue:ON false:OFF
     */
    public static HTypeEmergencyFlag getFlagByBoolean(boolean bool) {
        return bool ? ON : OFF;
    }

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeEmergencyFlag of(String value) {

        HTypeEmergencyFlag hType = EnumTypeUtil.getEnumFromValue(HTypeEmergencyFlag.class, value);
        return hType;
    }
}
