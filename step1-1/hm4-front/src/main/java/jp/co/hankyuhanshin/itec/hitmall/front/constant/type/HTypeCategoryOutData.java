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
 * カテゴリCSVデータタイプ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeCategoryOutData implements EnumType {

    /**
     * カテゴリCSV
     */
    OUTDATA("カテゴリCSV", "0");

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
    public static HTypeCategoryOutData of(String value) {

        HTypeCategoryOutData hType = EnumTypeUtil.getEnumFromValue(HTypeCategoryOutData.class, value);
        return hType;
    }
}
