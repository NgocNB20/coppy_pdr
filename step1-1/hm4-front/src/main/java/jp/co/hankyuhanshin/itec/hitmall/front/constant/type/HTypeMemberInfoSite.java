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
 * 会員登録サイト種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeMemberInfoSite implements EnumType {

    /**
     * PC
     */
    PC("PC", "0"),

    /**
     * SP
     */
    SP("SP", "1"),

    /**
     * 管理者登録
     */
    ADMIN("管理者登録", "3");

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
    public static HTypeMemberInfoSite of(String value) {

        HTypeMemberInfoSite hType = EnumTypeUtil.getEnumFromValue(HTypeMemberInfoSite.class, value);
        return hType;
    }
}
