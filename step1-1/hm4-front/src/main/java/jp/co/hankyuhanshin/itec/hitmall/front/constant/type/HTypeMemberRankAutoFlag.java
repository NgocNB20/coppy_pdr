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
 * 会員ランク自動設定フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeMemberRankAutoFlag implements EnumType {

    /**
     * 自動設定しない
     */
    OFF("手動設定", "0"),

    /**
     * 自動設定する
     */
    ON("自動設定", "1");

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
    public static HTypeMemberRankAutoFlag of(String value) {

        HTypeMemberRankAutoFlag hType = EnumTypeUtil.getEnumFromValue(HTypeMemberRankAutoFlag.class, value);
        return hType;
    }
}
