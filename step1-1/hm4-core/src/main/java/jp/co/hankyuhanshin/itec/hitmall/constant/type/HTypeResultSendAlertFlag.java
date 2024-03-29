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
 * 送り先注意フラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeResultSendAlertFlag implements EnumType {

    /**
     * 送り先注意フラグOFF
     */
    OFF("－", "0"),

    /**
     * 送り先注意フラグON
     */
    ON("○", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeResultSendAlertFlag of(String value) {

        HTypeResultSendAlertFlag hType = EnumTypeUtil.getEnumFromValue(HTypeResultSendAlertFlag.class, value);
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
