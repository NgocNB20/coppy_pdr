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
 * PDR#004 01_販売可能商品の制御<br/>
 *
 * <pre>
 * 商品区分⇒　薬品区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsClassType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 医薬品(歯科）
     */
    DRUG_DENTAL("医薬品(歯科）", "11"),

    /**
     * 医薬品（人体）
     */
    DRUG_HUMAN("医薬品（人体）", "12"),

    /**
     * 医薬品（その他）
     */
    DRUG_OTHER("医薬品（その他）", "13"),

    /**
     * 医療機器（注射針）
     */
    MEDICAL_INJECTION("医療機器（注射針）", "21"),

    /**
     * 医療機器（特定保守）
     */
    MEDICAL_MAINTENANCE("医療機器（特定保守）", "22"),

    /**
     * 医療機器（高度管理）
     */
    MEDICAL_HIGH("医療機器（高度管理）", "23"),

    /**
     * 医療機器（管理）
     */
    MEDICAL_MANAGE("医療機器（管理）", "24"),

    /**
     * 医療機器（一般）
     */
    MEDICAL_GENERAL("医療機器（一般）", "25"),

    /**
     * 医薬部外品
     */
    QUASI_DRUG("医薬部外品", "91"),

    /**
     * 化粧品
     */
    COSMETICS("化粧品", "92"),

    /**
     * 食品（軽減税率対象品）
     */
    FOOD("食品（軽減税率対象品）", "93"),

    /**
     * 雑品
     */
    SUNDRIES("雑品", "94");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsClassType of(String value) {

        HTypeGoodsClassType hType = EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class, value);
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

    /**
     * 医薬品フラグを返す（商品フィード出力バッチ用）
     * @return 医薬品フラグ
     */
    public String getDrugFlag() {
        if (this == DRUG_DENTAL || this == DRUG_HUMAN || this == DRUG_OTHER || this == MEDICAL_INJECTION
            || this == MEDICAL_MAINTENANCE || this == MEDICAL_HIGH || this == MEDICAL_MANAGE
            || this == MEDICAL_GENERAL) {
            return "1";
        } else {
            return "0";
        }
    }
}
