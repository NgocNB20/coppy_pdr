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
 * 分析結果（集計）：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAnalysis implements EnumType {

    /**
     * RF分析
     */
    RF("RF分析", "1"),

    /**
     * RM分析
     */
    RM("RM分析", "2"),

    /**
     * FM分析
     */
    FM("FM分析", "3");

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
    public static HTypeAnalysis of(String value) {

        HTypeAnalysis hType = EnumTypeUtil.getEnumFromValue(HTypeAnalysis.class, value);
        return hType;
    }
}
