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
 * 対象データ（会員）（集計）：列挙型
 *
 * @author kaneda
 */
@Getter
@AllArgsConstructor
public enum HTypeTargetMemberInfoData implements EnumType {

    /**
     * 対象期間最終日まで
     */
    UNTIL_TO_TARGET_TIME("対象期間最終日まで", "0"),

    /**
     * 現時点までの全て
     */
    UNTIL_TO_NOW("現時点までの全て", "1");

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
    public static HTypeTargetMemberInfoData of(String value) {

        HTypeTargetMemberInfoData hType = EnumTypeUtil.getEnumFromValue(HTypeTargetMemberInfoData.class, value);
        return hType;
    }
}
