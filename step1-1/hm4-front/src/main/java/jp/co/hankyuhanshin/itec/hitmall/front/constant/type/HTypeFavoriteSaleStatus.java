// 2023-renew No65 from here
package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * お気に入り商品セール状態
 *
 * @author Thang Doan (VJP)
 */
@Getter
@AllArgsConstructor
public enum HTypeFavoriteSaleStatus implements EnumType {

    /**
     * 非セール中
     */
    NO_SALE("非セール", "0"),

    /**
     * セール中
     */
    SALE("セール中", "1");

    /**
     * doma用ファクトリメソッド
     */
    public static HTypeFavoriteSaleStatus of(String value) {

        HTypeFavoriteSaleStatus hType = EnumTypeUtil.getEnumFromValue(HTypeFavoriteSaleStatus.class, value);
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
// 2023-renew No65 to here
