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
 * 注文キャンセル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No68 from here
public interface WebApiCancelOrderLogic {

    /** WEB-API連携名称 */
    public static final String WEB_API_NAME = "注文キャンセル";

    /** WEB-API 接続URL 取得キー */
    public static final String WEB_API_URL_KEY = "webapi.url.order.cancelorder";

    // 2023-renew No24 from here
    /** 注文キャンセル結果：成功 */
    public static final int CANCEL_SUCCESS = 1;

    /** エラーメッセージ：注文キャンセル失敗時（ステータスは正常で返却され注文キャンセル結果が失敗の時） */
    public static final String MSGCD_CANCEL_ORDER_FAIL = "PDR-2023RENEW-68-001-";
    // 2023-renew No68 to here

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto);

}
// 2023-renew No68 to here
