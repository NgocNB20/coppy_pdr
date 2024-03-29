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
 * 受注サイト種別
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeOrderSite implements EnumType {

    /**
     * PC
     */
    PC("PC", "0"),

    /**
     * SP
     */
    SP("SP", "1"),

    /**
     * 管理者注文
     */
    ADMIN("管理者注文", "3");

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
    public static HTypeOrderSite of(String value) {

        HTypeOrderSite hType = EnumTypeUtil.getEnumFromValue(HTypeOrderSite.class, value);
        return hType;
    }
}
