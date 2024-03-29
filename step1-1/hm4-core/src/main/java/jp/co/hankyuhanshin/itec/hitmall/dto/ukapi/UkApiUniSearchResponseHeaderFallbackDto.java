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

/**
 * UK-API連携 ユニサーチ（商品）レスポンスヘッダーFallbackDto
 * @author tk32120
 */
@Data
@Component
@Scope("prototype")
public class UkApiUniSearchResponseHeaderFallbackDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** フォールバック種別 */
    @JSONHint(ordinal = 10, name = "type")
    private String type;

    /** フォールバックキーワード */
    @JSONHint(ordinal = 20, name = "keyword")
    private String keyword;
}
