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
 * 対象データ（集計）：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeTargetData implements EnumType {

    /**
     * 受注
     */
    ORDER("受注", "1"),

    /**
     * 売上
     */
    SALES("売上", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeTargetData of(String value) {

        HTypeTargetData hType = EnumTypeUtil.getEnumFromValue(HTypeTargetData.class, value);
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
