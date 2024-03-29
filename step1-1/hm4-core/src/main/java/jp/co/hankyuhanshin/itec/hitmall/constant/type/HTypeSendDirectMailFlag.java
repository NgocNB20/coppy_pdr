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
 * PDR#11 08_データ連携（顧客情報）<br/>
 * <pre>
 * ダイレクトメール配信希望フラグ
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeSendDirectMailFlag of(String value) {

        HTypeSendDirectMailFlag hType = EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class, value);
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
