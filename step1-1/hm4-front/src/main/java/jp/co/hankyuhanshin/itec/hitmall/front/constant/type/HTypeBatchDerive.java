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
 * バッチタイプ状態
 *
 * @author kaneda
 */
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
    public static HTypeBatchDerive of(String value) {

        HTypeBatchDerive hType = EnumTypeUtil.getEnumFromValue(HTypeBatchDerive.class, value);
        return hType;
    }
}
