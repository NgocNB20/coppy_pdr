/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.config.hitmall.cache;

import jp.co.hankyuhanshin.itec.hitmall.front.aop.cache.CacheInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * キャッシュConfigクラス
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Configuration
public class CacheConfigurer implements WebMvcConfigurer {

    /**
     * インターセプタを追加
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CacheInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
