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
 * モバイルメールフラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeResultMobileMailFlag implements EnumType {

    /**
     * モバイルメールフラグOFF
     */
    OFF("－", "0"),

    /**
     * モバイルメールフラグON
     */
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
    public static HTypeResultMobileMailFlag of(String value) {

        HTypeResultMobileMailFlag hType = EnumTypeUtil.getEnumFromValue(HTypeResultMobileMailFlag.class, value);
        return hType;
    }
}
