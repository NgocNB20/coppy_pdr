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
 * 配送方法種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeDeliveryMethodType implements EnumType {

    /**
     * 全国一律
     */
    FLAT("全国一律", "0"),

    /**
     * 都道府県別
     */
    PREFECTURE("都道府県別", "1"),

    /**
     * 金額別
     */
    AMOUNT("金額別", "2");

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
    public static HTypeDeliveryMethodType of(String value) {

        HTypeDeliveryMethodType hType = EnumTypeUtil.getEnumFromValue(HTypeDeliveryMethodType.class, value);
        return hType;
    }
}
