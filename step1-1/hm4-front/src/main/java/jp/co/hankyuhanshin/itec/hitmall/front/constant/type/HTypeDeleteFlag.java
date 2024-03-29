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
 * 削除フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeDeleteFlag implements EnumType {

    /**
     * 未削除 ※ラベル未使用
     */
    OFF("", "0"),

    /**
     * 削除済み ※ラベル未使用
     */
    ON("", "1");

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
    public static HTypeDeleteFlag of(String value) {

        HTypeDeleteFlag hType = EnumTypeUtil.getEnumFromValue(HTypeDeleteFlag.class, value);
        return hType;
    }
}
