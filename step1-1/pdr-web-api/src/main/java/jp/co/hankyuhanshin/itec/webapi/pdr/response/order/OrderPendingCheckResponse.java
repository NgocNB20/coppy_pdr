/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.order;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 注文保留チェックAPIのレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class OrderPendingCheckResponse {

    /** 判定結果 */
    private String checkResult;

    /** 保留区分 */
    private String holdType;

    /**
     * @return the checkResult
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * @param checkResult the checkResult to set
     */
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    /**
     * @return the holdType
     */
    public String getHoldType() {
        return holdType;
    }

    /**
     * @param holdType the holdType to set
     */
    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }
}
