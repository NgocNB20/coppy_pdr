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
 * アラート区分
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeAlertType implements EnumType {

    /**
     * 受注検索 ※ラベル未使用
     */
    ORDER_SEARCH("", "0"),

    /**
     * 定期受注検索 ※ラベル未使用
     */
    PERIODIC_ORDER_SEARCH("", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeAlertType of(String value) {

        HTypeAlertType hType = EnumTypeUtil.getEnumFromValue(HTypeAlertType.class, value);
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
