/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.response;

import net.arnx.jsonic.JSONHint;

/**
 * UK-API連携 共通レスポンスモデル<br/>
 * @author tt32117
 */
public class UkResponse {

    /** レスポンスヘッダー */
    @JSONHint(ordinal = 10, name = "responseHeader")
    private UkResponseHeaderInfo responseHeader;

    /** レスポンス */
    @JSONHint(ordinal = 20, name = "response")
    private UkResponseInfo response;

    /** メッセージ */
    @JSONHint(ordinal = 30, name = "message")
    private String message;

    /**
     * @return the responseHeader
     */
    public UkResponseHeaderInfo getResponseHeader() {
        return responseHeader;
    }

    /**
     * @param responseHeader the responseHeader to set
     */
    public void setResponseHeader(UkResponseHeaderInfo responseHeader) {
        this.responseHeader = responseHeader;
    }

    /**
     * @return the response
     */
    public UkResponseInfo getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(UkResponseInfo response) {
        this.response = response;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
