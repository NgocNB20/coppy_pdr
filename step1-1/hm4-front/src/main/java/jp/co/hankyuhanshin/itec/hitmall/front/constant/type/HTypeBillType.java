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
 * 請求種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeBillType implements EnumType {

    /**
     * 前請求
     */
    PRE_CLAIM("前請求", "0"),

    /**
     * 後請求
     */
    POST_CLAIM("後請求", "1");

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
    public static HTypeBillType of(String value) {

        HTypeBillType hType = EnumTypeUtil.getEnumFromValue(HTypeBillType.class, value);
        return hType;
    }
}
