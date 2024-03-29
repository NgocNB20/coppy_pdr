/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.config.properties;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * リロード可能システムプロパティ 生成クラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
public class ReloadablePropertiesFactory extends DefaultPropertySourceFactory {

    /**
     * 指定されたリソースをラップするリロード可能なプロパティソースを生成する<br/>
     *
     * @param name     プロパティソースの名前 (nullにすることができる。その場合、ファクトリ実装は、指定されたリソースに基づいて名前を生成する必要がある。)
     * @param resource ラップする（潜在的にエンコードされた）リソース
     * @return リロード可能なプロパティソース
     * @throws IOException リソースの解決に失敗した場合
     * @PropertySourceのfactory属性で該当システムプロパティと紐づけられ、呼び出される
     */
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        return new ReloadablePropertySource(name, resource.getResource().getFile().getAbsolutePath());
    }

}
