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
 * 在庫表示方法：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeStockDisplayType implements EnumType {

    /**
     * 0:日本語表示
     */
    JAPANESE("日本語表示", "0"),

    /**
     * 1:数値表示
     */
    NUMERIC("数値表示", "1");

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
    public static HTypeStockDisplayType of(String value) {

        HTypeStockDisplayType hType = EnumTypeUtil.getEnumFromValue(HTypeStockDisplayType.class, value);
        return hType;
    }
}
