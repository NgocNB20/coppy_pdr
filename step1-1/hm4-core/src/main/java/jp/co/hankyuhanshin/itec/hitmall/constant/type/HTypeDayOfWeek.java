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
 * 曜日
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeDayOfWeek implements EnumType {

    /**
     * 日曜日
     */
    SUN("日", "1"),

    /**
     * 月曜日
     */
    MON("月", "2"),

    /**
     * 火曜日
     */
    TUE("火", "3"),

    /**
     * 水曜日
     */
    WED("水", "4"),

    /**
     * 木曜日
     */
    THU("木", "5"),

    /**
     * 金曜日
     */
    FRI("金", "6"),

    /**
     * 土曜日
     */
    SAT("土", "7");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeDayOfWeek of(String value) {

        HTypeDayOfWeek hType = EnumTypeUtil.getEnumFromValue(HTypeDayOfWeek.class, value);
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
