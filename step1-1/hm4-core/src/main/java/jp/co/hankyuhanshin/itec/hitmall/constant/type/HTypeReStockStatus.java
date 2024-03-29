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
 * 入荷状態
 *
 * @author st75001
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeReStockStatus implements EnumType {

    /**
     * 未入荷
     */
    NO_RESTOCK("未入荷", "0"),

    /**
     * 入荷済み
     */
    RESTOCK("入荷済み", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeReStockStatus of(String value) {

        return EnumTypeUtil.getEnumFromValue(HTypeReStockStatus.class, value);
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
