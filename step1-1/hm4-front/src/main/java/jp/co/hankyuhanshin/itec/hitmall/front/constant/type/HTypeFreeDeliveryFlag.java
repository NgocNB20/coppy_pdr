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
 * 無料配送フラグ
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeFreeDeliveryFlag implements EnumType {

    /**
     * 有料
     */
    OFF("通常送料", "0"),

    /**
     * 無料
     */
    ON("送料込み", "1");

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
    public static HTypeFreeDeliveryFlag of(String value) {

        HTypeFreeDeliveryFlag hType = EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class, value);
        return hType;
    }
}
