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
 * PDR#10 07_データ連携（商品情報）<br/>
 * <pre>
 * セール価格記号表示フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSalePriceMarkDispFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 表示しない */
    OFF("表示しない", "0"),

    /** 表示する */
    ON("表示する", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSalePriceMarkDispFlag of(String value) {

        HTypeSalePriceMarkDispFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class, value);
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
    // PDR Migrate Customization to here
}
