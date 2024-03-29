/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.ukapi.pdr.response;

import jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedgoods.GetUkUniSearchGoodsHeaderFallback;
import net.arnx.jsonic.JSONHint;

/**
 * UK-API連携 レスポンスヘッダー共通モデル<br/>
 * @author tt32117
 */
public class UkResponseHeaderInfo {

    /** ステータスコード */
    @JSONHint(ordinal = 10, name = "status")
    private int status;

    /** 処理時間（正常時のみ） */
    @JSONHint(ordinal = 20, name = "QTime")
    private String qTime;

    /** エラーメッセージ（異常時のみ） */
    @JSONHint(ordinal = 30, name = "errorMessage")
    private String errorMessage;

    /** リクエストID */
    @JSONHint(ordinal = 40, name = "reqID")
    private String reqID;

    /** フォールバックDto */
    @JSONHint(ordinal = 50, name = "fallback")
    private GetUkUniSearchGoodsHeaderFallback fallback;

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the qTime
     */
    public String getQTime() {
        return qTime;
    }

    /**
     * @param qTime the qTime to set
     */
    public void setQTime(String qTime) {
        this.qTime = qTime;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the reqId
     */
    public String getReqID() {
        return reqID;
    }

    /**
     * @param reqId the reqId to set
     */
    public void setReqId(String reqId) {
        this.reqID = reqId;
    }

    /**
     * @return the fallback
     */
    public GetUkUniSearchGoodsHeaderFallback getFallback() {
        return fallback;
    }

    /**
     * @param fallback the fallback to set
     */
    public void setFallback(GetUkUniSearchGoodsHeaderFallback fallback) {
        this.fallback = fallback;
    }
}
