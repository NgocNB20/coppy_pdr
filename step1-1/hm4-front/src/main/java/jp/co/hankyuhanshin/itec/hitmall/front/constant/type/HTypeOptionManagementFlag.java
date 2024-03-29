// PDR Migrate Customization from here
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
 * オプション管理フラグ
 *
 * @author ozaki
 * @author Kaneko 2012/09/04 Enum廃止対応
 */
@Getter
@AllArgsConstructor
public enum HTypeOptionManagementFlag implements EnumType {
    /**
     * オプション表示あり
     */
    ON("オプション表示あり", "1"),

    /**
     * オプション表示なし
     */
    OFF("オプション表示なし", "2");

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
    public static HTypeOptionManagementFlag of(String value) {

        HTypeOptionManagementFlag hType = EnumTypeUtil.getEnumFromValue(HTypeOptionManagementFlag.class, value);
        return hType;
    }
}
// PDR Migrate Customization to here
