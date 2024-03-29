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
 * メールテンプレート標準フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeMailTemplateDefaultFlag implements EnumType {

    /**
     * 一般 ※ラベル未使用
     */
    OFF("", "0"),

    /**
     * 標準 ※ラベル未使用
     */
    ON("", "1");

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
    public static HTypeMailTemplateDefaultFlag of(String value) {

        HTypeMailTemplateDefaultFlag hType = EnumTypeUtil.getEnumFromValue(HTypeMailTemplateDefaultFlag.class, value);
        return hType;
    }
}
