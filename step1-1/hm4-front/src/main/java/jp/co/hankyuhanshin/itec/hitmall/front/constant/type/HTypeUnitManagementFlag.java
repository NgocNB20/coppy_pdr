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
 * 規格管理フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeUnitManagementFlag implements EnumType {

    /**
     * 規格管理する
     */
    ON("規格表示あり", "1"),

    /**
     * 規格管理しない
     */
    OFF("規格表示なし", "0");

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
    public static HTypeUnitManagementFlag of(String value) {

        HTypeUnitManagementFlag hType = EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class, value);
        return hType;
    }
}
