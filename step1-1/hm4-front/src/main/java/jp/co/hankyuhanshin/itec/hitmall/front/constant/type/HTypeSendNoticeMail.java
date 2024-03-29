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
 * 顧客番号・パスワード通知メール送信
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeSendNoticeMail implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 送信しない(OFF)
     */
    OFF("送信しない", "0"),

    /**
     * 送信する(ON)
     */
    ON("送信する", "1");

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
    public static HTypeSendNoticeMail of(String value) {

        HTypeSendNoticeMail hType = EnumTypeUtil.getEnumFromValue(HTypeSendNoticeMail.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
