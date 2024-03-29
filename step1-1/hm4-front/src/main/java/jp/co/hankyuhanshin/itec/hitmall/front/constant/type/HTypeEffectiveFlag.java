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
 * 有効無効フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeEffectiveFlag implements EnumType {

    /**
     * 無効
     */
    INVALID("無効", "0"),

    /**
     * 有効
     */
    VALID("有効", "1");

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
    public static HTypeEffectiveFlag of(String value) {

        HTypeEffectiveFlag hType = EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, value);
        return hType;
    }
}
