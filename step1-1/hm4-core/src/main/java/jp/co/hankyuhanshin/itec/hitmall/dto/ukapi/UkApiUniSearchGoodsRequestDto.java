/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * UK-API連携 ユニサーチ（商品）リクエストDto
 * @author tk32120
 */
@Data
@Component
@Scope("prototype")
public class UkApiUniSearchGoodsRequestDto extends AbstractUkApiRequestDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** カテゴリID */
    private String category;

    /** 検索キーワード */
    private String kw;

    /** 検索結果ページ数 */
    private Integer page;

    /** 検索結果数 */
    private Integer rows;

    /** ソート種別 */
    private String sort;

    /** ユーザID */
    private String user;
}
