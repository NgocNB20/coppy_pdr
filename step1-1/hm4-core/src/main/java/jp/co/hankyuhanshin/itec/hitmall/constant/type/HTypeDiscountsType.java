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
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * 適用割引区分
 * WEB-API連携 割引適用結果取得で使用
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeDiscountsType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 割引なし
     */
    SALEOFF("", "0"),

    /**
     * 割引あり
     */
    SALEON("", "1"),

    /**
     * 心意気価格
     */
    SALEON_EMOTION_PRICE("", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeDiscountsType of(String value) {

        HTypeDiscountsType hType = EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class, value);
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
