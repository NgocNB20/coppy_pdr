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
 * SNS連携フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSnsLinkFlag implements EnumType {

    /**
     * 連携しない
     */
    OFF("連携しない", "0"),

    /**
     * 連携する
     */
    ON("連携する", "1");

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
    public static HTypeSnsLinkFlag of(String value) {

        HTypeSnsLinkFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, value);
        return hType;
    }
}
