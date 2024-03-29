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
 * GMO連携解除フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeGmoReleaseFlag implements EnumType {

    /**
     * 正常 ※ラベル未使用
     */
    NORMAL("", "0"),

    /**
     * 連携解除
     */
    RELEASE("GMO連携解除", "1");

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
    public static HTypeGmoReleaseFlag of(String value) {

        HTypeGmoReleaseFlag hType = EnumTypeUtil.getEnumFromValue(HTypeGmoReleaseFlag.class, value);
        return hType;
    }
}
