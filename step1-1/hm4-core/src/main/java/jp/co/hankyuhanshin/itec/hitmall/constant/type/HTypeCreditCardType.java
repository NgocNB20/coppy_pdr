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
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 *
 * <pre>
 * クレジットカード区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCreditCardType implements EnumType {
    // PDR Migrate Customization from here
    /** 保持カード */
    REGISTERED_CARD("保持カード", "0"),

    /** 新しいカード */
    NEW_CARD("新しいカード", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCreditCardType of(String value) {

        HTypeCreditCardType hType = EnumTypeUtil.getEnumFromValue(HTypeCreditCardType.class, value);
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
    // PDR Migrate Customization to here
}
