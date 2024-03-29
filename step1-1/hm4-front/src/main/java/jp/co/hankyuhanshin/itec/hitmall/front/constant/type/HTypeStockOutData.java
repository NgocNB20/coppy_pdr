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
 * 在庫データタイプ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeStockOutData implements EnumType {

    /**
     * 在庫CSV
     */
    STOCK_CSV("在庫CSV", "0"),

    /**
     * 入庫履歴CSV
     */
    STORING_HISTORY_CSV("入庫履歴CSV", "1");

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
    public static HTypeStockOutData of(String value) {

        HTypeStockOutData hType = EnumTypeUtil.getEnumFromValue(HTypeStockOutData.class, value);
        return hType;
    }
}
