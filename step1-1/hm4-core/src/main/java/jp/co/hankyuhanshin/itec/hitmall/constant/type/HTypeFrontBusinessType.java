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
 *
 * <pre>
 * FRONT_顧客区分
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeFrontBusinessType implements EnumType {
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
     * その他
     */
    OTHER("その他", "90");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeFrontBusinessType of(String value) {

        HTypeFrontBusinessType hType = EnumTypeUtil.getEnumFromValue(HTypeFrontBusinessType.class, value);
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
