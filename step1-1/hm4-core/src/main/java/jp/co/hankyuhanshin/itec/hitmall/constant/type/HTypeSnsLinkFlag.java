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
 * SNS連携フラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeSnsLinkFlag of(String value) {

        HTypeSnsLinkFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, value);
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
