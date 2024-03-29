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
 * 管理画面表示状態
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeAdminDisplayStatus implements EnumType {

    /**
     * 表示しない
     */
    OFF("表示しない", "0"),

    /**
     * 表示する
     */
    ON("表示する", "1");

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
    public static HTypeAdminDisplayStatus of(String value) {

        HTypeAdminDisplayStatus hType = EnumTypeUtil.getEnumFromValue(HTypeAdminDisplayStatus.class, value);
        return hType;
    }
}
