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
 * お気に入り商品セール状態
 *
 * @author takashima
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeFavoriteSaleStatus implements EnumType {

    /**
     * 非セール中
     */
    NO_SALE("非セール", "0"),

    /**
     * セール中
     */
    SALE("セール中", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeFavoriteSaleStatus of(String value) {

        HTypeFavoriteSaleStatus hType = EnumTypeUtil.getEnumFromValue(HTypeFavoriteSaleStatus.class, value);
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
