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
 * カテゴリCSVデータタイプ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCategoryOutData implements EnumType {

    /**
     * カテゴリCSV
     */
    OUTDATA("カテゴリCSV", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCategoryOutData of(String value) {

        HTypeCategoryOutData hType = EnumTypeUtil.getEnumFromValue(HTypeCategoryOutData.class, value);
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
