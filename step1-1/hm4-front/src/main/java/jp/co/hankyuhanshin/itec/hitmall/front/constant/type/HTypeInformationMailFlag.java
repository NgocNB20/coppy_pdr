// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.constant.type;

import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * お知らせメール設定
 *
 * @author hirata
 * @author Kaneko 2012/09/03 Enum廃止対応
 */
@Getter
@AllArgsConstructor
public enum HTypeInformationMailFlag implements EnumType {

    /**
     * 設定しない
     */
    OFF("設定しない", "0"),

    /**
     * 設定する
     */
    ON("設定する", "1");

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
    public static HTypeInformationMailFlag of(String value) {

        HTypeInformationMailFlag hType = EnumTypeUtil.getEnumFromValue(HTypeInformationMailFlag.class, value);
        return hType;
    }

}
// PDR Migrate Customization to here
