/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.scheduler.config.hitmall;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HIT-MALLシステムプロパティ階層管理　設定クラス
 * 【ポイント1】 複数ファイルを読み込む ★同じキーがあった場合、後に記述したもので上書きされる
 * 【ポイント2】 ignoreResourceNotFound=trueと指定し、読み込むファイルが存在しない場合に発生する例外を無視する
 * 【ポイント3】 factory属性で、リロード可能システムプロパティ生成用のPropertySourceFactoryの実装クラスと紐づける
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
@PropertySource(value = {"classpath:config/hitmall/coreSystem.properties",
                "classpath:config/hitmall/batchSystem.properties"}, ignoreResourceNotFound = true, encoding = "UTF-8")
public class BatchSystemPropertiesConfiguration {

}
