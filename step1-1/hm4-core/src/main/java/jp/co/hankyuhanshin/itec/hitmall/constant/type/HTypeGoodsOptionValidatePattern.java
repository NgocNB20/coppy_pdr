// PDR Migrate Customization from here
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
 * 商品オプション入力制限
 *
 * @author hirata
 * @author Negishi (itec) 2012/01/18 チケット #2796 対応
 * @author tomo (itec) 2012/01/30 チケット #2796 対応
 * @author Kaneko 2012/08/31 Enum廃止対応
 */
@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsOptionValidatePattern implements EnumType {

    /**
     * 制限なし
     */
    NO_LIMIT("制限なし", "0"),

    /**
     * 全角のみ
     */
    ONLY_EM_SIZE("全角のみ", "1"),

    /**
     * 半角英数のみ
     */
    ONLY_AN_CHAR("半角英数のみ", "2"),

    /**
     * 半角英字のみ
     */
    ONLY_A_CHAR("半角英字のみ", "3"),

    /**
     * 半角数字のみ
     */
    ONLY_N_CHAR("半角数字のみ", "4");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsOptionValidatePattern of(String value) {

        HTypeGoodsOptionValidatePattern hType =
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsOptionValidatePattern.class, value);
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
// PDR Migrate Customization to here
