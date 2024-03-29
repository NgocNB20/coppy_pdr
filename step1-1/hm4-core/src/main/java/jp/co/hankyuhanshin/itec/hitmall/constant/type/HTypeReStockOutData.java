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
 * 入荷お知らせ商品CSVデータタイプ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeReStockOutData implements EnumType {

    /**
     * 入荷お知らせ商品CSV
     */
    RESTOCK_CSV("入荷お知らせ商品CSV", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeReStockOutData of(String value) {

        HTypeReStockOutData hType = EnumTypeUtil.getEnumFromValue(HTypeReStockOutData.class, value);
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
