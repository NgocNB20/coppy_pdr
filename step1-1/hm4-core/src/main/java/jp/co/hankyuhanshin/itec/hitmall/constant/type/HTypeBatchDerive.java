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
 * バッチタイプ状態
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeBatchDerive implements EnumType {

    /**
     * オンライン
     */
    ONLINE("オンライン", "1"),

    /**
     * オフライン
     */
    OFFLINE("オフライン", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeBatchDerive of(String value) {

        HTypeBatchDerive hType = EnumTypeUtil.getEnumFromValue(HTypeBatchDerive.class, value);
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
