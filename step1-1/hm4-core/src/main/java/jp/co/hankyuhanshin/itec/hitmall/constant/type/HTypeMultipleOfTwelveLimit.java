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
 * 12ヶ月単位の表示件数：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMultipleOfTwelveLimit implements EnumType {

    /**
     * 12件
     */
    TWELVE("12件", "12"),

    /**
     * 24件
     */
    TWENTYFOUR("24件", "24"),

    /**
     * 36件
     */
    THIRTYSIX("36件", "36"),

    /**
     * 48件
     */
    FORTYEIGHT("48件", "48"),

    /**
     * 60件
     */
    SIXTY("60件", "60");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMultipleOfTwelveLimit of(String value) {

        HTypeMultipleOfTwelveLimit hType = EnumTypeUtil.getEnumFromValue(HTypeMultipleOfTwelveLimit.class, value);
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
