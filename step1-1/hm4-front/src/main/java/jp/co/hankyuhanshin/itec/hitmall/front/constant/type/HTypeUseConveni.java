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
 * コンビニ利用フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeUseConveni implements EnumType {

    /**
     * 使わない ※ラベル未使用
     */
    OFF("", "0"),

    /**
     * 使う ※ラベル未使用
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
    public static HTypeUseConveni of(String value) {

        HTypeUseConveni hType = EnumTypeUtil.getEnumFromValue(HTypeUseConveni.class, value);
        return hType;
    }
}
