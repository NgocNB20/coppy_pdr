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
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 * <pre>
 * ADMIN_歯科専売可否フラグ（商品）
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeDentalMonopolySalesFlg implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 不可（通常商品）
     */
    SALES_OFF("通常商品", "0"),

    /**
     * 可（歯科専売品）
     */
    SALES_ON("歯科専売品", "1");

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
    public static HTypeDentalMonopolySalesFlg of(String value) {

        HTypeDentalMonopolySalesFlg hType = EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
