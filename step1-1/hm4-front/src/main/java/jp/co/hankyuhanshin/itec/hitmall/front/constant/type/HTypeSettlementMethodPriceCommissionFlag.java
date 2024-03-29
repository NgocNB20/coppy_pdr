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
 * 決済方法金額別手数料フラグ：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSettlementMethodPriceCommissionFlag implements EnumType {

    /**
     * 一律
     */
    FLAT("一律", "0"),

    /**
     * 金額別
     */
    EACH_AMOUNT("金額別", "1");

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
    public static HTypeSettlementMethodPriceCommissionFlag of(String value) {

        HTypeSettlementMethodPriceCommissionFlag hType =
                        EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodPriceCommissionFlag.class, value);
        return hType;
    }
}
