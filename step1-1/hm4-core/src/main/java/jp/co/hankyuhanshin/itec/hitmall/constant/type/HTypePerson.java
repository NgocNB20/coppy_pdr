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
 * 顧客（受注検索）：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypePerson implements EnumType {

    /**
     * お客様情報
     */
    CUSTOMERINFO("お客様情報", "1"),

    /**
     * お届け先
     */
    ADDRESSEEINFO("お届け先", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypePerson of(String value) {

        HTypePerson hType = EnumTypeUtil.getEnumFromValue(HTypePerson.class, value);
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
