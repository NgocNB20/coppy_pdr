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
 * アドレス種別<br/>
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAddressType implements EnumType {

    /**
     * PC
     */
    PC("PC", "0"),

    /**
     * 携帯
     */
    MB("モバイル", "1"),

    /**
     * PCと携帯 ※ラベル未使用
     */
    BOTH("", "2");

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
    public static HTypeAddressType of(String value) {

        HTypeAddressType hType = EnumTypeUtil.getEnumFromValue(HTypeAddressType.class, value);
        return hType;
    }
}
