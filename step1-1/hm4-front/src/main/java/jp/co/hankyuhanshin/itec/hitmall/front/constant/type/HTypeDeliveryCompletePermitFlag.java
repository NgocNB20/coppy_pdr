/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 発送完了メールフラグ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
// 2023-renew No79 from here
public enum HTypeDeliveryCompletePermitFlag implements EnumType {

    /**
     * 希望しない(OFF)
     */
    OFF("希望しない", "0"),

    /**
     * 希望する(ON)
     */
    ON("希望する", "1");

    /**
     * ラベル
     */
    private String label;
    /**
     * 区分値
     */
    private String value;

}
// 2023-renew No79 to here
