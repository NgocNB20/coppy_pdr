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
 * リピート購入種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeRepeatPurchaseType implements EnumType {

    /**
     * ゲスト ※ラベル未使用
     */
    GUEST("", "0"),

    /**
     * 会員初回購入 ※ラベル未使用
     */
    MEMBER_FIRST("", "1"),

    /**
     * 会員2回目以降購入 ※ラベル未使用
     */
    MEMBER_REPEATER("", "2");

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
    public static HTypeRepeatPurchaseType of(String value) {

        HTypeRepeatPurchaseType hType = EnumTypeUtil.getEnumFromValue(HTypeRepeatPurchaseType.class, value);
        return hType;
    }
}
