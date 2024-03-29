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
 * AmazonPayment入金確認ステータス<br/>
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAmazonPaymentConfirmStatus implements EnumType {

    /**
     * 未入金 ※ラベル未使用
     */
    NOT_PAYMENT("", "0"),

    /**
     * 入金確認対象 ※ラベル未使用
     */
    PAYMENT_CONFIRMATION_TARGET("", "1"),

    /**
     * 入金確認済み ※ラベル未使用
     */
    PAYMENT_CONFIRMED("", "2"),

    /**
     * 決済エラー発生 ※ラベル未使用
     */
    ERROR_OCCURED("", "9");

    /**
     * ラベル<br/>
     */
    private String label;
    /**
     * 区分値<br/>
     */
    private String value;

    /**
     * doma用ファクトリメソッド ※ラベル未使用
     */
    public static HTypeAmazonPaymentConfirmStatus of(String value) {

        HTypeAmazonPaymentConfirmStatus hType =
                        EnumTypeUtil.getEnumFromValue(HTypeAmazonPaymentConfirmStatus.class, value);
        return hType;
    }
}
