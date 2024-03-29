/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.scheduler.config.hitmall;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * HIT-MALLメッセージプロパティ階層管理 設定クラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
public class BatchMessagePropertiesConfiguration {

    /**
     * キャッシュ無しメッセージプロパティの定義
     */
    @Bean
    MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // 【ポイント1】 複数ファイルを読み込む ★同じキーがあった場合、先に記述したもので上書きされる
        // 【ポイント2】 ファイルの拡張子は記述不要
        messageSource.addBasenames("classpath:config/hitmall/batchMessages");
        messageSource.addBasenames("classpath:config/hitmall/coreMessages");

        // 【ポイント3】 キャッシュする時間を設定する
        messageSource.setCacheSeconds(0);

        return messageSource;
    }

}
