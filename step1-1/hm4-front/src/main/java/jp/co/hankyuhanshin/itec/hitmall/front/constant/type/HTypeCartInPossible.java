// 2023-renew No2 from here
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
 * カートイン可能
 *
 * @author thangdoan VJP
 */
@Getter
@AllArgsConstructor
public enum HTypeCartInPossible implements EnumType {
    /** カートイン不可 */
    NG("カートイン不可", "0"),

    /** カートイン可能 */
    OK("カートイン可能", "1"),;

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
    public static HTypeCartInPossible of(String value) {

        HTypeCartInPossible hType = EnumTypeUtil.getEnumFromValue(HTypeCartInPossible.class, value);

        if (hType == null) {
            throw new IllegalArgumentException(value);
        } else {
            return hType;
        }
    }
}
// 2023-renew No2 to here
