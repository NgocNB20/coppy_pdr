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
 * ログインサイト種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeLoginSiteType implements EnumType {

    /**
     * PC:0
     */
    PC("PC", "0"),

    /**
     * SP:1
     */
    SP("SP", "1"),

    /**
     * モバイル:2
     */
    MB("モバイル", "2");

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
    public static HTypeLoginSiteType of(String value) {

        HTypeLoginSiteType hType = EnumTypeUtil.getEnumFromValue(HTypeLoginSiteType.class, value);
        return hType;
    }
}
