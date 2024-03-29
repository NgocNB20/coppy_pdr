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
 * アンケート回答必須フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeReplyRequiredFlag implements EnumType {

    /**
     * 任意
     */
    OPTIONAL("任意", "0"),

    /**
     * 必須
     */
    REQUIRED("必須", "1");

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
    public static HTypeReplyRequiredFlag of(String value) {

        HTypeReplyRequiredFlag hType = EnumTypeUtil.getEnumFromValue(HTypeReplyRequiredFlag.class, value);
        return hType;
    }
}
