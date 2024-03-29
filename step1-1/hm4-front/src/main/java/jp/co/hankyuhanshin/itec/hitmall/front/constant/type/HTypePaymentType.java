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
 * 支払い種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypePaymentType implements EnumType {

    /**
     * 一括
     */
    SINGLE("一括", "1"),

    /**
     * 分割
     */
    INSTALLMENT("分割", "2"),

    /**
     * リボ
     */
    REVOLVING("リボ", "5");

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
    public static HTypePaymentType of(String value) {

        HTypePaymentType hType = EnumTypeUtil.getEnumFromValue(HTypePaymentType.class, value);
        return hType;
    }
}
