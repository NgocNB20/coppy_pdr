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
 * 注文完了メールフラグ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
// 2023-renew No79 from here
public enum HTypeOrderCompletePermitFlag implements EnumType {

    /**
     * 希望しない(OFF)
     */
    OFF("希望しない", "0"),

    /**
     * 希望する(ON)
     */
    ON("希望する", "1");

    /**
     * ラベル
     */
    private String label;
    /**
     * 区分値
     */
    private String value;

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeOrderCompletePermitFlag of(String value) {

        HTypeOrderCompletePermitFlag hType = EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class, value);
        return hType;
    }
}
// 2023-renew No79 to here