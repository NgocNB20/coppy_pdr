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
 * 表示件数
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeDispalyListCount implements EnumType {

    /**
     * 10件
     */
    TEN("10件", "10"),

    /**
     * 20件
     */
    TWENTY("20件", "20"),

    /**
     * 30件
     */
    THIRTY("30件", "30"),

    /**
     * 50件
     */
    FIFTY("50件", "50"),

    /**
     * 100件
     */
    HUNDRED("100件", "100");

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
    public static HTypeDispalyListCount of(String value) {

        HTypeDispalyListCount hType = EnumTypeUtil.getEnumFromValue(HTypeDispalyListCount.class, value);
        return hType;
    }

}
