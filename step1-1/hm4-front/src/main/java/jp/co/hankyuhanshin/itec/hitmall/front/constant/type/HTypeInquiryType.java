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
 * 問い合わせ種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeInquiryType implements EnumType {

    /**
     * 一般
     */
    GENERAL("一般", "0"),

    /**
     * 注文
     */
    ORDER("注文", "1");

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
    public static HTypeInquiryType of(String value) {

        HTypeInquiryType hType = EnumTypeUtil.getEnumFromValue(HTypeInquiryType.class, value);
        return hType;
    }
}
