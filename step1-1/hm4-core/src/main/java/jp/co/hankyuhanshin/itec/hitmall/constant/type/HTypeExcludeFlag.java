// 2023-renew No21 from here
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
 * 除外フラグ
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeExcludeFlag implements EnumType {

    /**
     * 通常
     */
    OFF("通常", "0"),

    /**
     * 除外
     */
    ON("除外", "1");

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
    public static HTypeExcludeFlag of(String value) {

        HTypeExcludeFlag hType = EnumTypeUtil.getEnumFromValue(HTypeExcludeFlag.class, value);
        return hType;
    }

}
// 2023-renew No21 to here
