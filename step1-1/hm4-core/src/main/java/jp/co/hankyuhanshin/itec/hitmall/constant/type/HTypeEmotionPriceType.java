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
 * <pre>
 * 心意気価格保持区分
 * </pre>
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeEmotionPriceType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 通常商品
     */
    NORMAL("通常商品", "0"),

    /**
     * 通常商品（心意気あり）
     */
    NORMAL_WITH_EMOTION("通常商品（心意気あり）", "1"),

    /**
     * 心意気商品
     */
    EMOTIONPRICE("心意気商品", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeEmotionPriceType of(String value) {

        HTypeEmotionPriceType hType = EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class, value);
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
