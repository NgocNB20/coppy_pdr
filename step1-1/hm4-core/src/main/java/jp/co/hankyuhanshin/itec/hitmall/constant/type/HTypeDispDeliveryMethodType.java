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
 * 配送方法区分(画面表示用)
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeDispDeliveryMethodType implements EnumType {

    /**
     * 全国一律
     */
    FLAT("全国一律", "0"),

    /**
     * 都道府県別
     */
    PREFECTURE("都道府県別", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeDispDeliveryMethodType of(String value) {

        HTypeDispDeliveryMethodType hType = EnumTypeUtil.getEnumFromValue(HTypeDispDeliveryMethodType.class, value);
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
