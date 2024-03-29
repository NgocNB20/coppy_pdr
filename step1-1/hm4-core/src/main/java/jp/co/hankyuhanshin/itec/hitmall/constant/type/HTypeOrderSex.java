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
 * ご注文主性別：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeOrderSex implements EnumType {

    /**
     * 不明
     */
    UNKNOWN("未選択", "0"),

    /**
     * 男
     */
    MALE("男性", "1"),

    /**
     * 女
     */
    FEMALE("女性", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeOrderSex of(String value) {

        HTypeOrderSex hType = EnumTypeUtil.getEnumFromValue(HTypeOrderSex.class, value);
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
