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
 * 試供品のみ購入可能フラグ
 *
 * @author hh32100
 */
@Getter
@AllArgsConstructor
public enum HTypeSampleOnlyPurchaseFlag implements EnumType {

    // PDR Migrate Customization from here
    /** 購入不可 */
    OFF("OFF", "0"),

    /** 購入可 */
    ON("ON", "1");

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
    public static HTypeSampleOnlyPurchaseFlag of(String value) {

        HTypeSampleOnlyPurchaseFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSampleOnlyPurchaseFlag.class, value);

        if (hType == null) {
            throw new IllegalArgumentException(value);
        } else {
            return hType;
        }
    }
    // PDR Migrate Customization to here
}
