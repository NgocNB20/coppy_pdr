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
 * 会員種別
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMemberInfoType implements EnumType {

    /**
     * 個人 ※ラベル未使用
     */
    INDIVIDUAL("", "0"),

    /**
     * 法人 ※ラベル未使用
     */
    CORPORATION("", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMemberInfoType of(String value) {

        HTypeMemberInfoType hType = EnumTypeUtil.getEnumFromValue(HTypeMemberInfoType.class, value);
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
