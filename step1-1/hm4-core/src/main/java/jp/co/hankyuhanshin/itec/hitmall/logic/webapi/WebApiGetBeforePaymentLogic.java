/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;

/**
 * WEB-API連携クラス
 * 前回支払方法取得
 */
// 2023-renew No14 from here
public interface WebApiGetBeforePaymentLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "前回支払方法取得";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.order.getbeforepayment";

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);
}
// 2023-renew No14 to here
