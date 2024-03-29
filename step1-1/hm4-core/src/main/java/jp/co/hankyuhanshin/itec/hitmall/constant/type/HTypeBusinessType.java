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
 * ADMIN_顧客区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeBusinessType implements EnumType {
    // PDR Migrate Customization from here
    /**
     * 歯科医院
     */
    DENTALCLINIC("歯科医院", "1"),

    /**
     * 歯科技工所
     */
    DENTALLABORATORY("歯科技工所", "2"),

    /**
     * 歯科関連学校
     */
    DENTALSCHOOL("歯科関連学校", "3"),

    /**
     * 歯科医師（有資格者）
     */
    DENTALDOCTOR("歯科医師（有資格者）", "4"),

    /**
     * 歯科技工士（有資格者）
     */
    DENTALTECHNICIAN("歯科技工士（有資格者）", "5"),

    /**
     * 歯科衛生士（有資格者）
     */
    DENTALHIGIENIST("歯科衛生士（有資格者）", "6"),

    /**
     * 歯科学生
     */
    DENTALSTUDENT("歯科学生", "7"),

    /**
     * 歯科以外の医師（有資格者）
     */
    OTHER_DOCTOR("歯科以外の医師（有資格者）", "8"),

    /**
     * 歯科関連事業所
     */
    DENTALOFFICE("歯科関連事業所", "9"),

    /**
     * 歯科以外の病院
     */
    OTHER_HOSPITAL("歯科以外の病院", "10"),

    /**
     * 福祉・介護施設
     */
    NORSING_HOME("福祉・介護施設", "11"),

    /**
     * 保健所・保健センター・口腔センター
     */
    HEALTH_CENTER("保健所・保健センター・口腔センター", "12"),

    /**
     * 従業員
     */
    EMPLOYEE("従業員", "13"),

    /**
     * 歯科材料店・競合
     */
    MATERIAL_COMPETING("歯科材料店・競合", "14"),

    /**
     * 卸売販売
     */
    WHOLESALE("卸売販売", "15"),

    /**
     * 仕入先・取引先
     */
    SUPPLIER("仕入先・取引先", "16"),

    /**
     * 一般企業・官公庁・役場・学校・保育園
     */
    GENERAL_COMPANY("一般企業・官公庁・役場・学校・保育園", "17"),

    /**
     * その他（上記以外）
     */
    OTHERS("その他（上記以外）", "18");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeBusinessType of(String value) {

        HTypeBusinessType hType = EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class, value);
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
