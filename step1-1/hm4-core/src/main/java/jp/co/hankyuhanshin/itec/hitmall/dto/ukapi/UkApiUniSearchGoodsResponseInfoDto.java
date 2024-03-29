/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import net.arnx.jsonic.JSONHint;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * UK-API連携 ユニサーチ（商品）responseDto
 * @author tk32120
 */
@Data
@Component
@Scope("prototype")
public class UkApiUniSearchGoodsResponseInfoDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** ヒット件数 */
    @JSONHint(ordinal = 10, name = "numFound")
    private Integer numFound;

    /** 検索結果ページ数 */
    @JSONHint(ordinal = 20, name = "page")
    private Integer page;

    /** フォールバック */
    @JSONHint(ordinal = 30, name = "docs")
    private List<UkApiUniSearchGoodsResponseDocsDto> docs;
}
