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
 * 税率区分：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeTaxRateType implements EnumType {

    /**
     * 軽減税率
     */
    REDUCED("軽減税率", "0"),

    /**
     * 標準税率
     */
    STANDARD("標準税率", "1");

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
    public static HTypeTaxRateType of(String value) {

        HTypeTaxRateType hType = EnumTypeUtil.getEnumFromValue(HTypeTaxRateType.class, value);
        return hType;
    }
}
