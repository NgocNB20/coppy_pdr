/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response;

import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 共通レスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class Result {

    /** 処理結果 */
    private ResultInfo result;

    /** 情報 */
    private List<Object> info;

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

    /**
     * @return the info
     */
    public List<Object> getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(List<Object> info) {
        this.info = info;
    }
}
