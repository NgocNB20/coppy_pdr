/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.thymeleaf;

import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;

/**
 * マスクコンバータ カスタムユーティリティオブジェク用Utility<br/>
 * <p>
 * HM3ではアノテーションのため、マスク範囲が指定可能だが<br/>
 * こちらはマスク範囲指定不可<br/>
 *
 * @author kimura
 */
public class MaskConverterViewUtil {

    /**
     * 値をマスク<br/>
     *
     * @param value
     * @return マスクされた値
     */
    public String convert(final String value) {

        if (StringUtils.isEmpty(value)) {
            return "";
        }

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        return conversionUtility.toMaskString(value, '*');
    }

    // Paygent Customization from here

    /**
     * クレジットカード番号の下４桁以外をマスク<br/>
     *
     * @param value
     * @return マスクされた値
     */
    public String convertForCreditCardNumber(final String value) {

        if (StringUtils.isEmpty(value)) {
            return "";
        }

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        int valueIndex = conversionUtility.toString(value).length();
        return conversionUtility.toMaskString(value, '*', valueIndex - 4);
    }
    // Paygent Customization to here
}
