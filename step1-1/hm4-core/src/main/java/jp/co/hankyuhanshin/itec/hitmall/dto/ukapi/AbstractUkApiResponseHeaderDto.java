/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import net.arnx.jsonic.JSONHint;

import java.io.Serializable;

/**
 * UK-API連携 レスポンスヘッダーDTO共通情報クラス
 * @author tt32117
 */
@Data
public class AbstractUkApiResponseHeaderDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** ステータス */
    @JSONHint(ordinal = 10, name = "status")
    private String status;

    /** 処理時間 */
    @JSONHint(ordinal = 20, name = "QTime")
    private String qTime;

    /** エラーメッセージ */
    @JSONHint(ordinal = 30, name = "errorMessage")
    private String errorMessage;

    /** リクエストID */
    @JSONHint(ordinal = 40, name = "reqID")
    private String reqID;

    /** フォールバックDTO */
    @JSONHint(ordinal = 50, name = "fallback")
    private UkApiUniSearchResponseHeaderFallbackDto fallback;
}
