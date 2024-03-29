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
 * サイト
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSite implements EnumType {

    /**
     * PC
     */
    ONLY_PC("PCのみ", "1");

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
    public static HTypeSite of(String value) {

        HTypeSite hType = EnumTypeUtil.getEnumFromValue(HTypeSite.class, value);
        return hType;
    }
}
