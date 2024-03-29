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
 * PDR#006 03_取りおきサービス<br/>
 *
 * <pre>
 * 配送方法振分区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeAllocationDeliveryType implements EnumType {
    // PDR Migrate Customization from here
    /** 今すぐお届け分 */
    DELIVER_NOW("今すぐお届け", "1"),

    /** 予約可能分(取りおき) */
    RESERVABLE("予約", "2"),

    /** 入荷次第お届け分 */
    DEPENDING_ON_RECEIPT("入荷次第お届け", "3");

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
    public static HTypeAllocationDeliveryType of(String value) {

        HTypeAllocationDeliveryType hType = EnumTypeUtil.getEnumFromValue(HTypeAllocationDeliveryType.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
