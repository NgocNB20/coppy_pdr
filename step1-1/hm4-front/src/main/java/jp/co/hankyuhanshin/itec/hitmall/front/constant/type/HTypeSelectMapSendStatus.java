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
 * 配信状態：列挙型
 *
 * @author kaneda
 */
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
    public static HTypeSelectMapSendStatus of(String value) {

        HTypeSelectMapSendStatus hType = EnumTypeUtil.getEnumFromValue(HTypeSelectMapSendStatus.class, value);
        return hType;
    }
}
