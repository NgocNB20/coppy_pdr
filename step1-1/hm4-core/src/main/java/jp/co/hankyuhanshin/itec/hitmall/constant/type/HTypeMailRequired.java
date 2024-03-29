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
 * メール送信要否
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMailRequired implements EnumType {

    /**
     * 送信不要
     */
    NO_NEED("送信不要", "0"),

    /**
     * 送信する
     */
    REQUIRED("送信する", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMailRequired of(String value) {

        HTypeMailRequired hType = EnumTypeUtil.getEnumFromValue(HTypeMailRequired.class, value);
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
