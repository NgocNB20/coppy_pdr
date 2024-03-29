// 2023-renew No85-1 from here
package jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankakuKana;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.impl.HHankakuKanaConverterImpl;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <span class="logicName">【半角カタカナ変換】</span>半角カタカナコンバータのアノテーションファクトリクラス<br/>
 *
 * @author kimura
 */
public class HHankakuKanaConverterFactory implements AnnotationFormatterFactory<HCHankakuKana> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(String.class));
    }

    @Override
    public Printer<?> getPrinter(HCHankakuKana annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    @Override
    public Parser<?> getParser(HCHankakuKana annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    private Formatter<String> configureFormatterFrom(HCHankakuKana annotation) {
        return new HHankakuKanaConverterImpl();
    }
}
// 2023-renew No85-1 to here