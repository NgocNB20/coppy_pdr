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
 * 商品CSVデータタイプ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsOutData implements EnumType {

    /**
     * 商品CSV
     */
    GOODS_CSV("商品CSV", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsOutData of(String value) {

        HTypeGoodsOutData hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsOutData.class, value);
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
