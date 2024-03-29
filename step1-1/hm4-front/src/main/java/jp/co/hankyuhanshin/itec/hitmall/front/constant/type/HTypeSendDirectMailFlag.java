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
 * PDR#11 08_データ連携（顧客情報）<br/>
 * <pre>
 * ダイレクトメール配信希望フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeSendDirectMailFlag implements EnumType {
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
    public static HTypeSendDirectMailFlag of(String value) {

        HTypeSendDirectMailFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
