// 2023-renew No71 from here
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
 * トップ入荷通知フラグ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeTopStockAnnounceFlg implements EnumType {

    /**
     * 通知なし
     */
    OFF("通知なし", "0"),

    /**
     * 通知あり
     */
    ON("通知あり", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeTopStockAnnounceFlg of(String value) {

        HTypeTopStockAnnounceFlg hType = EnumTypeUtil.getEnumFromValue(HTypeTopStockAnnounceFlg.class, value);
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
