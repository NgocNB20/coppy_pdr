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
 * 対象データ（会員）（集計）：列挙型
 *
 * @author kaneda
 */
@Domain(valueType = String.class, factoryMethod = "of")
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
     * doma用ファクトリメソッド
     */
    public static HTypeTargetMemberInfoData of(String value) {

        HTypeTargetMemberInfoData hType = EnumTypeUtil.getEnumFromValue(HTypeTargetMemberInfoData.class, value);
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
