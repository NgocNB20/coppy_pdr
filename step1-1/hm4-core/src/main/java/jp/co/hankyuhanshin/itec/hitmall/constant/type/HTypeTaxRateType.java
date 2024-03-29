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
 * 税率区分：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeTaxRateType of(String value) {

        HTypeTaxRateType hType = EnumTypeUtil.getEnumFromValue(HTypeTaxRateType.class, value);
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
