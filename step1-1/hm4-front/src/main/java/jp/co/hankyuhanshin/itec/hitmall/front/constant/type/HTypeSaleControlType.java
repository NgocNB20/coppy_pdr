// 2023-renew No11 from here
package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 販売制御区分
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum HTypeSaleControlType implements EnumType {
    /**
     * 通常
     */
    NORMAL("通常", "0"),

    /**
     * 在庫限り
     */
    LIMITEDSTOCKNORMAL("在庫限り", "1"),

    /**
     * 取り扱い中止
     */
    DISCONTINUED("取り扱い中止", "2"),

    /**
     * 受注残不可
     */
    NOBACKORDER("受注残不可", "3");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeSaleControlType of(String value) {
        HTypeSaleControlType hType = EnumTypeUtil.getEnumFromValue(HTypeSaleControlType.class, value);
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
// 2023-renew No11 to here
