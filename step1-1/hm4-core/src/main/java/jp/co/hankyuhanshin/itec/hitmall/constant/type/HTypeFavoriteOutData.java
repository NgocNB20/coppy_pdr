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
 * お気に入り商品CSVデータタイプ：列挙型
 *
 * @author takashima
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeFavoriteOutData implements EnumType {

    /**
     * お気に入り商品CSV
     */
    FAVORITE_CSV("お気に入り商品CSV", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeFavoriteOutData of(String value) {

        HTypeFavoriteOutData hType = EnumTypeUtil.getEnumFromValue(HTypeFavoriteOutData.class, value);
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
