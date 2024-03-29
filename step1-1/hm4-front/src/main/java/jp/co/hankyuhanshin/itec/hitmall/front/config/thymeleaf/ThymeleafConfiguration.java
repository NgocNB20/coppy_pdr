/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.config.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 独自DialectのDI登録クラス<br/>
 *
 * @author kaneda
 */
@Configuration
public class ThymeleafConfiguration {

    /**
     * カスタムDialect<br/>
     * おもに、フォーマッター用独自関数利用のために必要
     *
     * @return CustomDialect
     */
    @Bean
    public CustomDialect customDialect() {
        return new CustomDialect();
    }

    /**
     * フリーエリアDialect<br/>
     * フリーエリア用カスタムタグのために必要
     *
     * @return FreeAreaDialect
     */
    @Bean
    public FreeAreaDialect freeAreaDialect() {
        return new FreeAreaDialect();
    }
}
