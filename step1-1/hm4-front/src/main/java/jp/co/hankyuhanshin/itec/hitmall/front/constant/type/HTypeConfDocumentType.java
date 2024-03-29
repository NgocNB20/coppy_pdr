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
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 *
 * <pre>
 * 確認書類
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeConfDocumentType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 開設届確認済
     */
    OPEN_CONF("開設届確認済", "1"),

    /**
     * 公的機関確認済
     */
    PUBLIC_SECTOR_CONF("公的機関確認済", "2"),

    /**
     * 未確認
     */
    UNCONF("未確認", "3"),

    /**
     * 資格免許確認済
     */
    QUALIFICATION_CONF("資格免許確認済", "4"),

    /**
     * HP確認済
     */
    HP_CONF("HP確認済", "5"),

    /**
     * 学生証確認済
     */
    STUDENT_ID_CONF("学生証確認済", "6"),

    /**
     * ―
     */
    NOT_SET("確認書類なし", "7"),

    /**
     * 介護老人保健施設（開設許可確認済）
     */
    NORSING_HOME("介護老人保健施設（開設許可確認済）", "8"),

    /**
     * 特別養護老人ホーム（医療法上に基づく開設許可確認済）
     */
    SPECIAL_NORSING_HOME("特別養護老人ホーム（医療法上に基づく開設許可確認済）", "9"),

    /**
     * 許可証確認済
     */
    PERMIT_CONF("医療機関コード確認済", "10"),

    /**
     * 医薬品/医療機器販売業許可証確認済
     */
    MEDICAL_LICENSE_CONF("医薬品/医療機器販売業許可証確認済", "11");

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
    public static HTypeConfDocumentType of(String value) {

        HTypeConfDocumentType hType = EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class, value);
        return hType;
    }
    // PDR Migrate Customization to here
}
