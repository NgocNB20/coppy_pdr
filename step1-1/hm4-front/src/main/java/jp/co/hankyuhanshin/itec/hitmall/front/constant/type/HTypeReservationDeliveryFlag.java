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
 * 予約配送フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeReservationDeliveryFlag implements EnumType {

    /**
     * 通常配送
     */
    OFF("通常配送", "0"),

    /**
     * 予約配送
     */
    ON("予約配送", "1");

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
    public static HTypeReservationDeliveryFlag of(String value) {

        HTypeReservationDeliveryFlag hType = EnumTypeUtil.getEnumFromValue(HTypeReservationDeliveryFlag.class, value);
        return hType;
    }
}
