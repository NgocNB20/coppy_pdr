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
 * 同梱フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeResultEnclosureFlag implements EnumType {

    /**
     * 同梱フラグOFF
     */
    OFF("－", "0"),

    /**
     * 同梱フラグON
     */
    ON("○", "1");

    /**
     * ラベル<br/>
     */
    private String label;
    /**
     * 区分値<br/>
     */
    private String value;

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeResultEnclosureFlag of(String value) {

        HTypeResultEnclosureFlag hType = EnumTypeUtil.getEnumFromValue(HTypeResultEnclosureFlag.class, value);
        return hType;
    }
}
