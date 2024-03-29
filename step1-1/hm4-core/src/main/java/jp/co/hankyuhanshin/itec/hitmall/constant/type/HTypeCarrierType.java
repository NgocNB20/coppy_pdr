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
 * キャリア種別
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCarrierType implements EnumType {

    /**
     * PC ※ラベル未使用
     */
    PC("", "0"),

    /**
     * NTT docomo ※ラベル未使用
     */
    DOCOMO("", "1"),

    /**
     * SoftBank mobile ※ラベル未使用
     */
    SOFTBANK("", "2"),

    /**
     * au by KDDI ※ラベル未使用
     */
    AU("", "3"),

    /**
     * Disney ※ラベル未使用
     */
    DISNEY("", "4"),

    /**
     * Emobile ※ラベル未使用
     */
    EMOBILE("", "5"),

    /**
     * Willcom ※ラベル未使用
     */
    WILLCOM("", "6");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCarrierType of(String value) {

        HTypeCarrierType hType = EnumTypeUtil.getEnumFromValue(HTypeCarrierType.class, value);
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
