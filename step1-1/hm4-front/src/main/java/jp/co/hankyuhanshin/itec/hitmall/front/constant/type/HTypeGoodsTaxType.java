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
 * 商品消費税種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeGoodsTaxType implements EnumType {

    /**
     * 内税 ※ラベル未使用
     */
    IN_TAX("", "0"),

    /**
     * 外税 ※ラベル未使用
     */
    OUT_TAX("", "1"),

    /**
     * 非課税 ※ラベル未使用
     */
    NO_TAX("", "2");

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
    public static HTypeGoodsTaxType of(String value) {

        HTypeGoodsTaxType hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class, value);
        return hType;
    }
}
