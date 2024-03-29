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
 * 会員登録サイト種別：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeMemberInfoSite of(String value) {

        HTypeMemberInfoSite hType = EnumTypeUtil.getEnumFromValue(HTypeMemberInfoSite.class, value);
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
