/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 * $Id:$
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.shop.sitemap;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * サイトマップ出力<br/>
 * サイトマップ用XMLクラス
 */
@XmlRootElement(name = "urlset")
@Data
@Component
@Scope("prototype")
public class SiteMapUrlSetXmlDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * サイトマップXML用URL格納リスト
     */
    private List<UrlXmlDto> url;

}
