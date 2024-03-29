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
 * ポイント種別定義：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypePointType implements EnumType {

    /**
     * 仮:0 ※ラベル未使用
     */
    TEMPORARY("", "0"),

    /**
     * 確定:1 ※ラベル未使用
     */
    ACTIVATE("", "1"),

    /**
     * 利用:2 ※ラベル未使用
     */
    USE("", "2"),

    /**
     * 返還:3 ※ラベル未使用
     */
    RETURN("", "3"),

    /**
     * 調整:4 ※ラベル未使用
     */
    ADJUST("", "4"),

    /**
     * 確定後無効化:5 ※ラベル未使用
     */
    RETURN_GOODS_INVALID("", "5"),

    /**
     * 期限切れ無効化:6 ※ラベル未使用
     */
    OVER_TERM_INVALID("", "6"),

    /**
     * 退会無効化:7 ※ラベル未使用
     */
    SECESSION_INVALID("", "7"),

    /**
     * 誕生月:8 ※ラベル未使用
     */
    BIRTH("", "8");

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
    public static HTypePointType of(String value) {

        HTypePointType hType = EnumTypeUtil.getEnumFromValue(HTypePointType.class, value);
        return hType;
    }
}
