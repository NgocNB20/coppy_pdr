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
 * 有効無効フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeEffectiveFlag of(String value) {

        HTypeEffectiveFlag hType = EnumTypeUtil.getEnumFromValue(HTypeEffectiveFlag.class, value);
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
