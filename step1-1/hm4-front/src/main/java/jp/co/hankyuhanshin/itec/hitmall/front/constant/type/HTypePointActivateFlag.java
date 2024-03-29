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
 * ポイント確定状態定義：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypePointActivateFlag implements EnumType {

    /**
     * 未確定:0
     */
    OFF("未確定", "0"),

    /**
     * 確定:1
     */
    ON("確定", "1");

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
    public static HTypePointActivateFlag of(String value) {

        HTypePointActivateFlag hType = EnumTypeUtil.getEnumFromValue(HTypePointActivateFlag.class, value);
        return hType;
    }
}
