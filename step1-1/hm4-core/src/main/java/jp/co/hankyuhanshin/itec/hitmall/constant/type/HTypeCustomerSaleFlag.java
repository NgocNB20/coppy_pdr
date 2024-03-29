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
 * PDR#004 01_販売可能商品の制御<br/>
 *
 * <pre>
 * 顧客セール割引適用有無
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeCustomerSaleFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 割引なし */
    OFF("", "0"),

    /** 割引あり */
    ON("", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeCustomerSaleFlag of(String value) {

        HTypeCustomerSaleFlag hType = EnumTypeUtil.getEnumFromValue(HTypeCustomerSaleFlag.class, value);
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
