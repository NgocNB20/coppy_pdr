// 2023-renew No85-1 from here
package jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.HiraganaKatakanaConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ZenHanConversionUtility;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * <span class="logicName">【半角カタカナ変換】</span>半角カタカナコンバータのアノテーションlogic実装クラス<br/>
 *
 * @author kimura
 */
public class HHankakuKanaConverterImpl implements Formatter<String> {
    /**
     * model → view<br/>
     * ※こちらはthymeleaf側で対応しているため未使用
     *
     * @param fromController
     * @param locale
     * @return fromController
     */
    @Override
    public String print(String fromController, Locale locale) {
        return fromController;
    }

    /**
     * view → model
     *
     * @param fromScreen
     * @param locale
     * @return 変換後の値 or 未変換の値
     */
    @Override
    public String parse(String fromScreen, Locale locale) throws ParseException {

        HiraganaKatakanaConversionUtility hiraganaKatakanaConversionUtility =
                        ApplicationContextUtility.getBean(HiraganaKatakanaConversionUtility.class);
        // ひらがなをカタカナに変換
        String toKatakana = hiraganaKatakanaConversionUtility.toKatakana(fromScreen);

        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);
        //先に半角
        return zenHanConversionUtility.toHankaku(toKatakana);
    }
}
// 2023-renew No85-1 to here
