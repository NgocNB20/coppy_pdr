/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response;

import java.util.List;


/**
 * CUENOTE-API連携 共通レスポンスモデル<br/>
 *
 * @author st75001
 */
public class Result {

    /** 処理結果 */
    private ResultInfo result;

    /**
     * @return the result
     */
    public ResultInfo getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(ResultInfo result) {
        this.result = result;
    }
}
