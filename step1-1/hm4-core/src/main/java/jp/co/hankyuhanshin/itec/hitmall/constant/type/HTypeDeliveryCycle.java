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
 * 配送サイクル
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeDeliveryCycle implements EnumType {

    /**
     * 1ヶ月
     */
    MONTH_1("1ヶ月", "1"),

    /**
     * 2ヶ月
     */
    MONTH_2("2ヶ月", "2"),

    /**
     * 3ヶ月
     */
    MONTH_3("3ヶ月", "3"),

    /**
     * 4ヶ月
     */
    MONTH_4("4ヶ月", "4"),

    /**
     * 6ヶ月
     */
    MONTH_6("6ヶ月", "6");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeDeliveryCycle of(String value) {

        HTypeDeliveryCycle hType = EnumTypeUtil.getEnumFromValue(HTypeDeliveryCycle.class, value);
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
