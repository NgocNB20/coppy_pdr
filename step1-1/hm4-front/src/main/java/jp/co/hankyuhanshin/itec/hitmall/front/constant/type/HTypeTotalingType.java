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
 * 集計種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeTotalingType implements EnumType {

    /**
     * 受注
     */
    ORDER("受注", "0"),

    /**
     * 売上
     */
    SALES("売上", "1"),

    /**
     * 変更 ※ラベル未使用
     */
    CHANGE("", "2"),

    /**
     * キャンセル ※ラベル未使用
     */
    CANCELLATION("", "9");

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
    public static HTypeTotalingType of(String value) {

        HTypeTotalingType hType = EnumTypeUtil.getEnumFromValue(HTypeTotalingType.class, value);
        return hType;
    }
}
