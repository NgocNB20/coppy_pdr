/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.config.hitmall.converter;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HDateConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HHankakuConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HHankakuKanaConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HHankakuTrimConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HLowerConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HNumberConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HZenkakuConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory.HZenkakuKanaConverterFactory;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.impl.HBlankToNullConverterImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 独自コンバータConfigクラス
 *
 * @author kimura
 */
@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(String.class, new HBlankToNullConverterImpl());
        registry.addFormatterForFieldAnnotation(new HDateConverterFactory());
        registry.addFormatterForFieldAnnotation(new HHankakuConverterFactory());
        registry.addFormatterForFieldAnnotation(new HLowerConverterFactory());
        registry.addFormatterForFieldAnnotation(new HNumberConverterFactory());
        registry.addFormatterForFieldAnnotation(new HZenkakuConverterFactory());
        registry.addFormatterForFieldAnnotation(new HZenkakuKanaConverterFactory());
        // 2023-renew No85-1 from here
        registry.addFormatterForFieldAnnotation(new HHankakuKanaConverterFactory());
        // 2023-renew No85-1 to here
        registry.addFormatterForFieldAnnotation(new HHankakuTrimConverterFactory());
    }
}
