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
 *
 * <pre>
 * アウトレットアイコンフラグ
 * </pre>
 *
 * @author tk32120
 */
@Getter
@AllArgsConstructor
public enum HTypeOutletIconFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 非表示 */
    OFF("-", "0"),

    /** 表示 */
    ON("○", "1");

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
    public static HTypeOutletIconFlag of(String value) {

        HTypeOutletIconFlag hType = EnumTypeUtil.getEnumFromValue(HTypeOutletIconFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
