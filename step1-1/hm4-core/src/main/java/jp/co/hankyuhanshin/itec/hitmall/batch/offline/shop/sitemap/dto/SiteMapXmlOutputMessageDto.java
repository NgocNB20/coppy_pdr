/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.sitemap.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * サイトマップXML出力用のレポートメッセージ用Dto
 */
@Data
@Component
@Scope("prototype")
public class SiteMapXmlOutputMessageDto implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 出力ファイル名
     */
    private String fileName;

    /**
     * 出力ファイルパス
     */
    private String filePath;

    /**
     * 出力ファイルサイズ(bytes)
     */
    private long fileSize;

    /**
     * 件数
     */
    private int countUrl;
}
