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
 * 管理者状態
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAdministratorStatus implements EnumType {

    /**
     * 使用中:0
     */
    USE("使用中", "0"),

    /**
     * 停止中:1
     */
    STOP("停止中", "1");

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
    public static HTypeAdministratorStatus of(String value) {

        HTypeAdministratorStatus hType = EnumTypeUtil.getEnumFromValue(HTypeAdministratorStatus.class, value);
        return hType;
    }
}
