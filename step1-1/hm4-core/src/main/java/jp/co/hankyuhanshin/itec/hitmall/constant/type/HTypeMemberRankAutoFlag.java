/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Domain;

/**
 * 会員ランク自動設定フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeMemberRankAutoFlag of(String value) {

        HTypeMemberRankAutoFlag hType = EnumTypeUtil.getEnumFromValue(HTypeMemberRankAutoFlag.class, value);
        return hType;
    }

    /**
     * ラベル<br/>
     */
    private String label;

    /**
     * 区分値<br/>
     */
    private String value;
}
