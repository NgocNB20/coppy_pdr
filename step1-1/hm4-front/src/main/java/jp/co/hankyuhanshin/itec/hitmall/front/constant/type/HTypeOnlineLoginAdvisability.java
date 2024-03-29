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
 * <pre>
 * オンラインログイン可否
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum HTypeOnlineLoginAdvisability implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 仮登録
     */
    TEMPORARY_ACCOUNT("仮登録", "0"),

    /**
     * 本登録
     */
    REGULAR_ACCOUNT("本登録", "1"),

    /**
     * 取引中止
     */
    CLOSE_ACCOUNT("取引中止", "9");

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
    public static HTypeOnlineLoginAdvisability of(String value) {

        HTypeOnlineLoginAdvisability hType = EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
