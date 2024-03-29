// 2023-renew No65 from here
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
 * 入荷状態
 *
 * @author Thang Doan (VJP)
 */
@Getter
@AllArgsConstructor
public enum HTypeRestockStatus implements EnumType {

    /**
     * 未入荷
     */
    NO_RESTOCK("未入荷", "0"),

    /**
     * 入荷済み
     */
    RESTOCK("入荷済み", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeRestockStatus of(String value) {

        HTypeRestockStatus hType = EnumTypeUtil.getEnumFromValue(HTypeRestockStatus.class, value);
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
}
// 2023-renew No65 to here
