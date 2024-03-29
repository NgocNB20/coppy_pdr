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
 * 反社会的勢力ではないことの保証フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeNoAntiSocialFlag of(String value) {

        HTypeNoAntiSocialFlag hType = EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class, value);
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
