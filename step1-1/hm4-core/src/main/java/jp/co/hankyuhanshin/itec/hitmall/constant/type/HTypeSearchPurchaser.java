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
 * 購入者検索 購入者：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSearchPurchaser implements EnumType {

    /**
     * 会員とゲスト
     */
    MEMBERANDGUEST("会員とゲスト", ""),

    /**
     * 会員のみ
     */
    MEMBER("会員のみ", "1"),

    /**
     * ゲストのみ
     */
    GUEST("ゲストのみ", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSearchPurchaser of(String value) {

        HTypeSearchPurchaser hType = EnumTypeUtil.getEnumFromValue(HTypeSearchPurchaser.class, value);
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
