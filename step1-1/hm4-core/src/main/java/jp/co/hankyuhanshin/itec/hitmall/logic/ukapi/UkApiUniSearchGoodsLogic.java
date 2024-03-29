/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsResponseDto;

/**
 * UK-API連携 ユニサーチ（商品）Logic
 * @author tk32120
 */
public interface UkApiUniSearchGoodsLogic {
    /** UK-API連携名称 */
    public static final String UK_API_NAME = "ユニサーチ（商品）";

    /** UK-API 接続URL 取得キー  */
    public static final String UK_API_URL_KEY = "ukapi.url.unisearch.goods";

    /**
     * UK-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    UkApiUniSearchGoodsResponseDto execute(UkApiUniSearchGoodsRequestDto requestDto);
}
