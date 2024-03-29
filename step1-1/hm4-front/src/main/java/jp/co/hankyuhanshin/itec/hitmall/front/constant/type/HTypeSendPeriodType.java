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
 * 配信期間種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSendPeriodType implements EnumType {

    /**
     * START_DATE ※ラベル未使用
     */
    START_DATE("", "0"),

    /**
     * STOP_DATE ※ラベル未使用
     */
    STOP_DATE("", "1");

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
    public static HTypeSendPeriodType of(String value) {

        HTypeSendPeriodType hType = EnumTypeUtil.getEnumFromValue(HTypeSendPeriodType.class, value);
        return hType;
    }
}
