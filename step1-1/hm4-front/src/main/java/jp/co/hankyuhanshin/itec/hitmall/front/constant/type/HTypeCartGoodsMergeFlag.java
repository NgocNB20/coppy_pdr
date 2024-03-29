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
 * カート内同一商品マージフラグ
 * <p>
 * 使用用途については下記画面設計書参照
 * 　\document\02_外部設計\03_画面設計書\P04_HM3_画面設計書_フロントPC_カート.xlsx
 * ・【補足】カート商品制御
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeCartGoodsMergeFlag implements EnumType {

    /**
     * カートマージする ※ラベル未使用
     */
    CART_MERGE("", "1"),

    /**
     * カートマージしない ※ラベル未使用
     */
    NOT_CART__MERGE("", "0");

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
    public static HTypeCartGoodsMergeFlag of(String value) {

        HTypeCartGoodsMergeFlag hType = EnumTypeUtil.getEnumFromValue(HTypeCartGoodsMergeFlag.class, value);
        return hType;
    }
}
