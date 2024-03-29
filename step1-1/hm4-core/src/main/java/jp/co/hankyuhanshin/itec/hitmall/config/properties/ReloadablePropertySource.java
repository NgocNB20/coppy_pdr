/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.config.properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.PropertySource;

/**
 * リロード可能システムプロパティソース
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
public class ReloadablePropertySource extends PropertySource {

    /**
     * commons.configuration.PropertiesConfiguration
     */
    PropertiesConfiguration propertiesConfiguration;

    /**
     * コンストラクタ<br/>
     * 指定された名前と新しいObjectインスタンスを使用して、<br/>
     * 新しいPropertySourceを作成する。
     *
     * @param name 関連する名前
     * @param path 関連するパス
     */
    public ReloadablePropertySource(String name, String path) {

        super(StringUtils.isEmpty(name) ? path : name);
        try {
            // 文字コードUTF-8でロード
            this.propertiesConfiguration = new PropertiesConfiguration();
            this.propertiesConfiguration.setEncoding("UTF-8");
            this.propertiesConfiguration.load(path);
            // 【ポイント】 プロパティファイルが変更されれば、リロードさせる
            this.propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 指定された名前に関連付けられた値を返す。<br/>
     * 見つからない場合はnullを返す。<br/>
     *
     * @param name 検索するプロパティ
     * @return 指定された名前に関連付けられた値
     */
    @Override
    public Object getProperty(String name) {
        return propertiesConfiguration.getProperty(name);
    }

}
