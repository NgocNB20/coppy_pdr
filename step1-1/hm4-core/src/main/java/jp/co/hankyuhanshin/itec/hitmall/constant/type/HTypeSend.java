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
 * 送信状態：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSend implements EnumType {

    /**
     * 未送信 0
     */
    UNSENT("未送信", "0"),

    /**
     * 送信済 1
     */
    SENT("送信済", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSend of(String value) {

        HTypeSend hType = EnumTypeUtil.getEnumFromValue(HTypeSend.class, value);
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
