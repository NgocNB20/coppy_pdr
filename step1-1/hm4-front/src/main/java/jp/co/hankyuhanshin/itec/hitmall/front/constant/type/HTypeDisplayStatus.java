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
 * 表示状態
 *
 * @author Doan Thang
 */
@Getter
@AllArgsConstructor
public enum HTypeDisplayStatus implements EnumType {

    /**
     * 表示しない
     */
    NO_DISPLAY("表示しない", "0"),

    /**
     * 表示する
     */
    DISPLAY("表示する", "1");

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
    public static HTypeDisplayStatus of(String value) {

        HTypeDisplayStatus hType = EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class, value);
        return hType;
    }
}
