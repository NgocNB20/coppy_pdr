/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 処理結果のレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class ResultInfo {

    /** 処理結果 */
    private int status;

    /** メッセージ */
    private String message;

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
