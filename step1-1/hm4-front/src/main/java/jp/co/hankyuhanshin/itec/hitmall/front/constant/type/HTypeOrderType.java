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
 * 注文種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeOrderType implements EnumType {

    /**
     * 通常注文
     */
    NORMAL("通常注文", "0"),

    /**
     * 定期注文
     */
    PERIODIC("定期注文", "1"),

    /**
     * 予約注文
     */
    RESERVATION("予約注文", "2");

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
    public static HTypeOrderType of(String value) {

        HTypeOrderType hType = EnumTypeUtil.getEnumFromValue(HTypeOrderType.class, value);
        return hType;
    }
}
