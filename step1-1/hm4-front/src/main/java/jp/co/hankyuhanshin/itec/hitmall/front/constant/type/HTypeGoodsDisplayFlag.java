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
 * 商品閲覧可能フラグ
 *
 * @author kaneda
 */
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
    public static HTypeGoodsDisplayFlag of(String value) {

        HTypeGoodsDisplayFlag hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsDisplayFlag.class, value);
        return hType;
    }
}
