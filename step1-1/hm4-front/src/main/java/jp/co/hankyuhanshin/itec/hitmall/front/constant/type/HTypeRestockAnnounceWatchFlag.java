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
 * 入荷お知らせ通知の既読状況フラグ
 *
 * @author Thang Doan (VJP)
 */
@Getter
@AllArgsConstructor
public enum HTypeRestockAnnounceWatchFlag implements EnumType {

    /**
     * 未読
     */
    NO_SEEN("未読", "0"),

    /**
     * 既読
     */
    SEEN("既読", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeRestockAnnounceWatchFlag of(String value) {

        HTypeRestockAnnounceWatchFlag hType = EnumTypeUtil.getEnumFromValue(HTypeRestockAnnounceWatchFlag.class, value);
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
