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
 * 入金処理済みフラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeProcessedFlag implements EnumType {

    /**
     * 要処理 ※ラベル未使用
     */
    PROCESSING_REQUIRED("", "0"),

    /**
     * 処理済/処理不要 ※ラベル未使用
     */
    PROCESSED("", "1"),

    /**
     * 請求決済エラー ※ラベル未使用
     */
    BILL_SETTLEMENT_ERROR("", "9");

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
    public static HTypeProcessedFlag of(String value) {

        HTypeProcessedFlag hType = EnumTypeUtil.getEnumFromValue(HTypeProcessedFlag.class, value);
        return hType;
    }
}
