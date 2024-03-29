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
 * 販売可否判定結果
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
// 2023-renew No11 from here
public enum HTypeSaleCheckType implements EnumType {

    /**
     * 販売不可
     */
    NO("販売不可", "0"),

    /**
     * 販売可能
     */
    YES("販売可能", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSaleCheckType of(String value) {

        HTypeSaleCheckType hType = EnumTypeUtil.getEnumFromValue(HTypeSaleCheckType.class, value);
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
// 2023-renew No11 to here
