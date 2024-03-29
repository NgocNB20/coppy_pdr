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
 * 購入者検索　デバイス：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeCarrier of(String value) {

        HTypeCarrier hType = EnumTypeUtil.getEnumFromValue(HTypeCarrier.class, value);
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
