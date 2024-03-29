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
 * UK-API連携 ユニサーチ（商品）responseDocsDto
 * @author tk32120
 */
@Data
@Component
@Scope("prototype")
public class UkApiUniSearchContentsResponseDocsDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** コンテンツID */
    @JSONHint(ordinal = 10, name = "content_id")
    private String contentId;

    /** コンテンツ名 */
    @JSONHint(ordinal = 20, name = "content_name")
    private String contentName;

    /** 遷移先URL */
    @JSONHint(ordinal = 30, name = "transition_url")
    private String transitionUrl;

    /** コンテンツ画像URL */
    @JSONHint(ordinal = 40, name = "content_image_url")
    private String contentImageUrl;

    /** 検索キーワード */
    @JSONHint(ordinal = 50, name = "search_keyword")
    private List<String> searchKeyword;
}
