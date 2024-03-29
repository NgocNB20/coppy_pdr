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
 * アドレス種別<br/>
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeAddressType of(String value) {

        HTypeAddressType hType = EnumTypeUtil.getEnumFromValue(HTypeAddressType.class, value);
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
