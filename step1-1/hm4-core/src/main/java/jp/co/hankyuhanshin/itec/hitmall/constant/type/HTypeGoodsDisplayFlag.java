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
 * 商品閲覧可能フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsDisplayFlag implements EnumType {

    /**
     * 閲覧不可 ※ラベル未使用
     */
    OFF("", "0"),

    /**
     * 閲覧可 ※ラベル未使用
     */
    ON("", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsDisplayFlag of(String value) {

        HTypeGoodsDisplayFlag hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsDisplayFlag.class, value);
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
