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
 * PDR#429 1.7次 基幹リニューアル対応 【要望No.6,7,8】　フロント会員更新追加<br/>
 * <pre>
 * 金属商品価格お知らせメールフラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeMetalPermitFlag implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 希望しない(OFF)
     */
    OFF("希望しない", "0"),

    /**
     * 希望する(ON)
     */
    ON("希望する", "1");

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
    public static HTypeMetalPermitFlag of(String value) {

        HTypeMetalPermitFlag hType = EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
