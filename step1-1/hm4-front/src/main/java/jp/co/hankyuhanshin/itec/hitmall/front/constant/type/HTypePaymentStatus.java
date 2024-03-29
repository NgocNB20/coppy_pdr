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
 * 入金状態（受注検索）：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypePaymentStatus implements EnumType {

    /**
     * 未入金
     */
    UNPAID("未入金", "1"),

    /**
     * 入金済み
     */
    PAID("入金済み", "2"),

    /**
     * 過不足あり
     */
    EXCESS("過不足あり", "3");

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
    public static HTypePaymentStatus of(String value) {

        HTypePaymentStatus hType = EnumTypeUtil.getEnumFromValue(HTypePaymentStatus.class, value);
        return hType;
    }
}
