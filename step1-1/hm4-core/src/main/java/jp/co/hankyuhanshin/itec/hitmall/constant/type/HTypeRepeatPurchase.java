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
 * 顧客区分（集計）：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeRepeatPurchase implements EnumType {

    /**
     * 会員
     */
    MEMBER("会員", "1"),

    /**
     * ゲスト
     */
    GUEST("ゲスト", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeRepeatPurchase of(String value) {

        HTypeRepeatPurchase hType = EnumTypeUtil.getEnumFromValue(HTypeRepeatPurchase.class, value);
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
