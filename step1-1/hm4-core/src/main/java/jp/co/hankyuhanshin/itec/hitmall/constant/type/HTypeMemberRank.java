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
 * 会員ランク
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMemberRank implements EnumType {

    /**
     * ゲスト
     */
    GUEST("ゲスト", "0"),

    /**
     * ブロンズ
     */
    BRONZE("ブロンズ", "1"),

    /**
     * シルバー
     */
    SILVER("シルバー", "2"),

    /**
     * ゴールド
     */
    GOLD("ゴールド", "3"),

    /**
     * プラチナ
     */
    PLATINUM("プラチナ", "4"),

    /**
     * 特別
     */
    SPECIAL("特別", "5");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMemberRank of(String value) {

        HTypeMemberRank hType = EnumTypeUtil.getEnumFromValue(HTypeMemberRank.class, value);
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
