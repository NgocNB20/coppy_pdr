// 2023-renew No71 from here
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
 * セール通知の既読状況（0：未読、1：既読）
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeSaleAnnounceWatchFlg implements EnumType {

    /**
     * 未読
     */
    UNREAD("未読", "0"),

    /**
     * 既読
     */
    READ("既読", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSaleAnnounceWatchFlg of(String value) {

        HTypeSaleAnnounceWatchFlg hType = EnumTypeUtil.getEnumFromValue(HTypeSaleAnnounceWatchFlg.class, value);
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
// 2023-renew No71 to here
