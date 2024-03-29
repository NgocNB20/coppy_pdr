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
 * 配信状態：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSelectMapSendStatus implements EnumType {

    /**
     * 配信中
     */
    SENDING("配信中", "0"),

    /**
     * 配信停止
     */
    SEND_STOP("配信停止", "1"),

    /**
     * 配信解除
     */
    REMOVE("配信解除", "2");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSelectMapSendStatus of(String value) {

        HTypeSelectMapSendStatus hType = EnumTypeUtil.getEnumFromValue(HTypeSelectMapSendStatus.class, value);
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
