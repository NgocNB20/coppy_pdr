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
 * 購入者データタイプ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeCustomerOutputData implements EnumType {

    /**
     * 購入者CSV
     */
    OUTDATA("購入者CSV", "1");

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
    public static HTypeCustomerOutputData of(String value) {

        HTypeCustomerOutputData hType = EnumTypeUtil.getEnumFromValue(HTypeCustomerOutputData.class, value);
        return hType;
    }
}
