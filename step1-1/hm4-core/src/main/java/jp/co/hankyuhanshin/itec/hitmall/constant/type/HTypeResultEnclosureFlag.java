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
 * 同梱フラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeResultEnclosureFlag implements EnumType {

    /**
     * 同梱フラグOFF
     */
    OFF("－", "0"),

    /**
     * 同梱フラグON
     */
    ON("○", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeResultEnclosureFlag of(String value) {

        HTypeResultEnclosureFlag hType = EnumTypeUtil.getEnumFromValue(HTypeResultEnclosureFlag.class, value);
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
