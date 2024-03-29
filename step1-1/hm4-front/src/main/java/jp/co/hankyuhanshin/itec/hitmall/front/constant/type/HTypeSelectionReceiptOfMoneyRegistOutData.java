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
 * 入金登録データタイプ(選択出力用)
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSelectionReceiptOfMoneyRegistOutData implements EnumType {

    /**
     * 入金登録CSV
     */
    PAYMENT_CSV("入金登録CSV", "0");

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
    public static HTypeSelectionReceiptOfMoneyRegistOutData of(String value) {

        HTypeSelectionReceiptOfMoneyRegistOutData hType =
                        EnumTypeUtil.getEnumFromValue(HTypeSelectionReceiptOfMoneyRegistOutData.class, value);
        return hType;
    }
}
