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
 * 決済方法手数料種別：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSettlementMethodCommissionType implements EnumType {

    /**
     * 一律（円）
     */
    FLAT_YEN("一律（円）", "0"),

    /**
     * 金額別（円）
     */
    EACH_AMOUNT_YEN("金額別（円）", "1");

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
    public static HTypeSettlementMethodCommissionType of(String value) {

        HTypeSettlementMethodCommissionType hType =
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodCommissionType.class, value);
        return hType;
    }
}
