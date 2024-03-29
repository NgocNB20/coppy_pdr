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
 * 商品販売状態
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsSaleStatus implements EnumType {

    /**
     * 非販売
     */
    NO_SALE("非販売", "0"),

    /**
     * 販売中
     */
    SALE("販売中", "1"),

    /**
     * 削除
     */
    DELETED("削除", "9");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsSaleStatus of(String value) {

        HTypeGoodsSaleStatus hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, value);
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
