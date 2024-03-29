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
 * 集計結果（集計）：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeProcessDate implements EnumType {

    /**
     * 月別
     */
    MONTHLY("月別", "1"),

    /**
     * 日別
     */
    DAILY("日別", "0");

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
    public static HTypeProcessDate of(String value) {

        HTypeProcessDate hType = EnumTypeUtil.getEnumFromValue(HTypeProcessDate.class, value);
        return hType;
    }
}
