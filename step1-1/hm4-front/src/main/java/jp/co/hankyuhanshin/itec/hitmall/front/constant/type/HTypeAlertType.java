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
 * アラート区分
 *
 * @author kaneda
 */
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
    public static HTypeAlertType of(String value) {

        HTypeAlertType hType = EnumTypeUtil.getEnumFromValue(HTypeAlertType.class, value);
        return hType;
    }
}
