/*
HTypeSelectionReceiptOfMoneyRegistOutData.java * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 受注検索(商品別)出力帳票リスト
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeOrderGoodsOutData implements EnumType {

    /**
     * 受注商品別明細
     */
    ORDER_GOODS_DETAIL("受注商品別明細", "2"),

    /**
     * 受注商品CSV
     */
    ORDER_GOODS_CSV("受注商品CSV", "7");

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
    public static HTypeOrderGoodsOutData of(String value) {

        HTypeOrderGoodsOutData hType = EnumTypeUtil.getEnumFromValue(HTypeOrderGoodsOutData.class, value);
        return hType;
    }
}
