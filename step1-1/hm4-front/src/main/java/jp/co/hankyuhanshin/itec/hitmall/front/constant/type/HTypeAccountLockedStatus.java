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

    /**
     * ラベル
     */
    private String label;
    /**
     * 区分値
     */
    private String value;

    public static HTypeAccountLockedStatus of(String value) {

        HTypeAccountLockedStatus hType = EnumTypeUtil.getEnumFromValue(HTypeAccountLockedStatus.class, value);
        return hType;
    }
}
