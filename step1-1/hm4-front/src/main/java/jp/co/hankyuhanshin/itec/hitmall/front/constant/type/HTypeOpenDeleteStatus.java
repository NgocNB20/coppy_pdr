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
 * 削除状態付き公開状態
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeOpenDeleteStatus implements EnumType {

    /**
     * 非公開
     */
    NO_OPEN("非公開", "0"),

    /**
     * 公開中
     */
    OPEN("公開中", "1"),

    /**
     * 削除
     */
    DELETED("削除", "9");

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
    public static HTypeOpenDeleteStatus of(String value) {

        HTypeOpenDeleteStatus hType = EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, value);
        return hType;
    }
}
