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
 * 売上フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSalesFlag implements EnumType {

    /**
     * 未売上 ※ラベル未使用
     */
    OFF("", "0"),

    /**
     * 売上 ※ラベル未使用
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
    public static HTypeSalesFlag of(String value) {

        HTypeSalesFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSalesFlag.class, value);
        return hType;
    }
}
