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
 * 送り先注意フラグ：列挙型
 *
 * @author kaneda
 */
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
    public static HTypeResultSendAlertFlag of(String value) {

        HTypeResultSendAlertFlag hType = EnumTypeUtil.getEnumFromValue(HTypeResultSendAlertFlag.class, value);
        return hType;
    }
}
