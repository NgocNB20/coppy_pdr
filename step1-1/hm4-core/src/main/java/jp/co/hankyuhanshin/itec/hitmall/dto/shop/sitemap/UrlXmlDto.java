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

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * サイトマップ出力<br/>
 * サイトマップ用XMLクラス
 */
@Data
@Component
@Scope("prototype")
public class UrlXmlDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * loc
     */
    private String loc;

    /**
     * lastmod
     */
    private String lastmod;

    /**
     * changefreq
     */
    private String changefreq;

    /**
     * priority
     */
    private BigDecimal priority;

}
