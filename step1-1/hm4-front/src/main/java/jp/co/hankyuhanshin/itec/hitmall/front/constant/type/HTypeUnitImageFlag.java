package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HTypeUnitImageFlag implements EnumType {
    // 2023-renew No76 from here
    /**
     * 規格画像あり
     */
    ON("規格画像あり", "1"),

    /**
     * 規格画像なし
     */
    OFF("規格画像なし", "0");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeUnitImageFlag of(String value) {
        HTypeUnitImageFlag hType = EnumTypeUtil.getEnumFromValue(HTypeUnitImageFlag.class, value);
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
    // 2023-renew No76 to here
}
