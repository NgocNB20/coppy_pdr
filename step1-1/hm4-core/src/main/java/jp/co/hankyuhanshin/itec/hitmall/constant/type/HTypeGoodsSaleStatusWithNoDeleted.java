package jp.co.hankyuhanshin.itec.hitmall.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Domain;

@Domain(valueType = String.class, factoryMethod = "of")
@Getter
@AllArgsConstructor
public enum HTypeGoodsSaleStatusWithNoDeleted implements EnumType {
    /**
     * 非販売
     */
    NO_SALE("非販売", "0"),

    /**
     * 販売中
     */
    SALE("販売中", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeGoodsSaleStatusWithNoDeleted of(String value) {

        HTypeGoodsSaleStatusWithNoDeleted hType =
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatusWithNoDeleted.class, value);
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
