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
 * 問い合わせ発信者種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeInquiryRequestType implements EnumType {

    /**
     * お客様
     */
    CONSUMER("お客様", "0"),

    /**
     * 運用者
     */
    OPERATOR("運用者", "1");

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
    public static HTypeInquiryRequestType of(String value) {

        HTypeInquiryRequestType hType = EnumTypeUtil.getEnumFromValue(HTypeInquiryRequestType.class, value);
        return hType;
    }
}
