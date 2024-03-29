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
 * 公開状態
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeOpenStatus implements EnumType {

    /**
     * 非公開
     */
    NO_OPEN("非公開", "0"),

    /**
     * 公開中
     */
    OPEN("公開中", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeOpenStatus of(String value) {

        HTypeOpenStatus hType = EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, value);
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
