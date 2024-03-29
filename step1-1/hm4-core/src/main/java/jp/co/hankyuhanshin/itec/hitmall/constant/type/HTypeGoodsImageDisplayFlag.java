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
 * 商品画像表示状態フラグ
 *
 * @author hh32100
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsImageDisplayFlag implements EnumType {

    // PDR Migrate Customization from here
    /** 表示しない */
    OFF("OFF", "0"),

    /** 表示する */
    ON("ON", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsImageDisplayFlag of(String value) {

        HTypeGoodsImageDisplayFlag hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsImageDisplayFlag.class, value);

        if (hType == null) {
            throw new IllegalArgumentException(value);
        } else {
            return hType;
        }
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
