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

@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeAccountLockedStatus implements EnumType {

    /**
     * 正常
     */
    UNLOCK("正常", "0"),

    /**
     * ロック
     */
    LOCK("ロック", "1");

    public static HTypeAccountLockedStatus of(String value) {

        HTypeAccountLockedStatus hType = EnumTypeUtil.getEnumFromValue(HTypeAccountLockedStatus.class, value);
        return hType;
    }

    /**
     * ラベル
     */
    private String label;

    /**
     * 区分値
     */
    private String value;
}
