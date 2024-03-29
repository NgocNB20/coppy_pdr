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
 * 配送方法区分(画面表示用)
 *
 * @author kaneda
 */
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
    public static HTypeDispDeliveryMethodType of(String value) {

        HTypeDispDeliveryMethodType hType = EnumTypeUtil.getEnumFromValue(HTypeDispDeliveryMethodType.class, value);
        return hType;
    }
}
