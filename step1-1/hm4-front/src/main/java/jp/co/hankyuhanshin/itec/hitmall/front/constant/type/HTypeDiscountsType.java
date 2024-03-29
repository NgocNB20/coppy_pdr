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
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * 適用割引区分
 * WEB-API連携 割引適用結果取得で使用
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
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
    public static HTypeDiscountsType of(String value) {

        HTypeDiscountsType hType = EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
