/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.factory;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankakuTrim;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.impl.HHankakuTrimConverterImpl;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <span class="logicName">【データ両端の空白・半角変換】</span>データの先頭／末尾のスペースを除去し、半角変換するコンバータのアノテーションファクトリクラス<br/>
 *
 * @author kamei
 */
public class HHankakuTrimConverterFactory implements AnnotationFormatterFactory<HCHankakuTrim> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(String.class));
    }

    @Override
    public Printer<String> getPrinter(HCHankakuTrim annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    @Override
    public Parser<String> getParser(HCHankakuTrim annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    private Formatter<String> configureFormatterFrom(HCHankakuTrim annotation) {
        return new HHankakuTrimConverterImpl();
    }
}
