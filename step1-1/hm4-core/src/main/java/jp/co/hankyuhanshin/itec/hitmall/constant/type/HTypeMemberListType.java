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
 * 名簿区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeMemberListType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 顧客
     */
    ONLINE_GENERAL_CUSTOMER("顧客", "1"),

    /**
     * 一時休業
     */
    OFF_DEMAND("一時休業", "2"),

    /**
     * 転居先不明
     */
    ADDRESS_UNKNOWN("転居先不明", "3"),

    /**
     * CSブラック
     */
    CS_BLACK("CSブラック", "4"),

    /**
     * CSイエロー
     */
    CS_YELLOW("CSイエロー", "5"),

    /**
     * 特別値引客
     */
    ONLINE_SPECIAL_REDUCTIONS_GUEST("特別値引客", "6"),

    /**
     * 顧客（EC販促不可）
     */
    OFFLINE_GENERAL_CUSTOMER("顧客（EC販促不可）", "7"),

    /**
     * 特別値引客（EC販促不可）
     */
    OFFLINE_SPECIAL__REDUCTIONS_GUEST("特別値引客（EC販促不可）", "8"),

    /**
     * 特別対応
     */
    ONLINE_SPECIAL_CORRESPONDENCE("特別対応", "9"),

    /**
     * 特別対応（EC販促不可）
     */
    OFFLINE_SPECIAL_CORRESPONDENCE("特別対応（EC販促不可）", "10");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeMemberListType of(String value) {

        HTypeMemberListType hType = EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class, value);
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
