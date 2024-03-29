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
 * 受注データタイプ(選択出力用)
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeSelectionOrderOutData implements EnumType {

    /**
     * 納品書
     */
    INVOICE_ATTACHMENT("納品書", "0"),

    /**
     * 受注明細
     */
    ORDER_DETAIL("受注明細", "1"),

    /**
     * 受注CSV
     */
    ORDER_CSV("受注CSV", "6");

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
    public static HTypeSelectionOrderOutData of(String value) {

        HTypeSelectionOrderOutData hType = EnumTypeUtil.getEnumFromValue(HTypeSelectionOrderOutData.class, value);
        return hType;
    }
}
