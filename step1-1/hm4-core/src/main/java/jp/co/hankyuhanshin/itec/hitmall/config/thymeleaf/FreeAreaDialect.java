/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.config.thymeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

/**
 * フリーエリアDialect<br/>
 * thymeleafでのカスタムタグオブジェクト作成用
 *
 * @author yt23807
 */
public class FreeAreaDialect extends AbstractProcessorDialect {

    /**
     * Dialect名
     */
    private static final String DIALECT_NAME = "FreeArea Dialect";
    /**
     * 処理対象タグプレフィックス
     */
    private static final String DIALECT_PREFIX = "hm";

    public FreeAreaDialect() {
        //    第1引数にDialect名、第2に引数に処理対象のタグプレフィックスを引き渡す
        super(DIALECT_NAME, DIALECT_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        //    FreeAreaProcessorをProcessorに登録する
        processors.add(new FreeAreaProcessor(dialectPrefix));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }

}
