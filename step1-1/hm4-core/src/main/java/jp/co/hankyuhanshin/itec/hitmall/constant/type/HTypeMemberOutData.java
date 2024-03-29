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
 * 会員データタイプ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMemberOutData implements EnumType {

    /**
     * 会員CSV
     */
    MEMBER_CSV("会員CSV", "0"),

    /**
     * 会員追加属性CSV
     */
    MEMBER_ATTRIBUTE_CSV("会員追加属性CSV", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMemberOutData of(String value) {

        HTypeMemberOutData hType = EnumTypeUtil.getEnumFromValue(HTypeMemberOutData.class, value);
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
