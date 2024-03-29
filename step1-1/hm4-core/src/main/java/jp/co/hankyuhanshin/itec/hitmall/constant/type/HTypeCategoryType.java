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
 * カテゴリ種別
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCategoryType implements EnumType {

    /**
     * 通常
     */
    NORMAL("通常", "0"),

    /**
     * 特殊
     */
    RECOMMEND("特殊", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCategoryType of(String value) {

        HTypeCategoryType hType = EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class, value);
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
