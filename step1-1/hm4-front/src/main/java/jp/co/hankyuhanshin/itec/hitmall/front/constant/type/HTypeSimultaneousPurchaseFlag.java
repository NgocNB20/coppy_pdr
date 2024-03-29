// PDR Migrate Customization from here
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
 * 同時購入可能フラグ
 *
 * @author s_nose
 */
@Getter
@AllArgsConstructor
public enum HTypeSimultaneousPurchaseFlag implements EnumType {

    /**
     * 同時購入不可
     */
    OFF("表示しない", "0"),

    /**
     * 同時購入可
     */
    ON("表示する", "1");

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
    public static HTypeSimultaneousPurchaseFlag of(String value) {

        HTypeSimultaneousPurchaseFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSimultaneousPurchaseFlag.class, value);
        return hType;
    }

}
// PDR Migrate Customization to here
