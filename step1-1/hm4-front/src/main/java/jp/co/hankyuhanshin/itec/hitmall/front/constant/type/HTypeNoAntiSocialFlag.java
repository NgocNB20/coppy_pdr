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
 * 反社会的勢力ではないことの保証フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeNoAntiSocialFlag implements EnumType {

    /**
     * 保証しない
     */
    OFF("保証しない", "0"),

    /**
     * 保証する
     */
    ON("保証する", "1");

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
    public static HTypeNoAntiSocialFlag of(String value) {

        HTypeNoAntiSocialFlag hType = EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class, value);
        return hType;
    }
}
