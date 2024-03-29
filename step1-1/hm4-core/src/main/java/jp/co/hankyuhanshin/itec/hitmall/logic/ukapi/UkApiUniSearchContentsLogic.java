/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchContentsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchContentsResponseDto;

/**
 * UK-API連携 ユニサーチ（コンテンツ）Logic
 * @author tk32120
 */
public interface UkApiUniSearchContentsLogic {
    /** UK-API連携名称 */
    public static final String UK_API_NAME = "ユニサーチ（コンテンツ）";

    /** UK-API 接続URL 取得キー  */
    public static final String UK_API_URL_KEY = "ukapi.url.unisearch.contents";

    /**
     * UK-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    UkApiUniSearchContentsResponseDto execute(UkApiUniSearchContentsRequestDto requestDto);
}
