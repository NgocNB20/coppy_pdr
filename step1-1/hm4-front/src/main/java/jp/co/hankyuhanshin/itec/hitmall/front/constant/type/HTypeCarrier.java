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
 * 購入者検索　デバイス：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeCarrier implements EnumType {

    /**
     * PCとモバイル
     */
    PCANDMB("PCとモバイル", ""),

    /**
     * PCのみ
     */
    PC("PCのみ", "0"),

    /**
     * モバイルのみ
     */
    MB("モバイルのみ", "1");

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
    public static HTypeCarrier of(String value) {

        HTypeCarrier hType = EnumTypeUtil.getEnumFromValue(HTypeCarrier.class, value);
        return hType;
    }
}
