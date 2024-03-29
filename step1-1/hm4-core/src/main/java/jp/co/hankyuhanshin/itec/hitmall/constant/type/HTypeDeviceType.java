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
 * デバイス種別
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeDeviceType implements EnumType {

    /**
     * PC:0
     */
    PC("PC", "0"),

    /**
     * SP:1
     */
    SP("SP", "1"),

    /**
     * タブレット:2
     */
    TAB("タブレット", "2"),

    /**
     * モバイル:3
     */
    MB("モバイル", "3");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeDeviceType of(String value) {

        HTypeDeviceType hType = EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class, value);
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
