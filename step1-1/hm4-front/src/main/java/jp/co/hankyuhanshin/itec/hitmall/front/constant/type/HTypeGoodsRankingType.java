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
 * 商品ランキング種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeGoodsRankingType implements EnumType {

    /**
     * クリック数 ※ラベル未使用
     */
    CLICK_COUNT("", "0"),

    /**
     * 受注数 ※ラベル未使用
     */
    ORDER_COUNT("", "1");

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
    public static HTypeGoodsRankingType of(String value) {

        HTypeGoodsRankingType hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsRankingType.class, value);
        return hType;
    }
}
