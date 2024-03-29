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
 * 表示状態
 *
 * @author Doan Thang
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeDisplayStatus of(String value) {

        HTypeDisplayStatus hType = EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class, value);
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
