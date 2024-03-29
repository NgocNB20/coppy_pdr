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
 * メール配信希望フラグ：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSendMailPermitFlag implements EnumType {

    /**
     * 希望しない
     */
    OFF("希望しない", "0"),

    /**
     * 希望する
     */
    ON("希望する", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSendMailPermitFlag of(String value) {

        HTypeSendMailPermitFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class, value);
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
