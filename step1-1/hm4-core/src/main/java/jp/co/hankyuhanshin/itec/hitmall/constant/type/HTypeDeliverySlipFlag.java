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
 * PDR#014 10_データ連携（プロモーション情報）<br/>
 *
 * <pre>
 * WEB-API連携 プロモーション
 * 納品書印字フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeDeliverySlipFlag implements EnumType {
    // PDR Migrate Customization from here
    /** 印字しない */
    OFF("", "1"),

    /** 印字する */
    ON("", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeDeliverySlipFlag of(String value) {

        HTypeDeliverySlipFlag hType = EnumTypeUtil.getEnumFromValue(HTypeDeliverySlipFlag.class, value);
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
