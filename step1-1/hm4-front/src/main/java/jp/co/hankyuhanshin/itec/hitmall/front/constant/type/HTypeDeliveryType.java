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
 * PDR#027 配送情報の取り込み<br/>
 *
 * <pre>
 * 配送方法
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
// PDR Migrate Customization from here
public enum HTypeDeliveryType implements EnumType {

    // 2023-renew No14 from here
    /** 自動設定 */
    AUTOMATIC("自動設定", "0"),
    // 2023-renew No14 to here

    /** ヤマト */
    YAMATO("ヤマト", "1"),

    /** 日本郵便 */
    JAPANPOST("日本郵便", "2");

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
    public static HTypeDeliveryType of(String value) {

        HTypeDeliveryType hType = EnumTypeUtil.getEnumFromValue(HTypeDeliveryType.class, value);
        return hType;
    }

}
// PDR Migrate Customization to here
