/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.goods;

import java.sql.Timestamp;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 価格情報取得のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetPriceRequest {

    /** 指定日時 */
    private Timestamp designationDate;

    /**
     * @return the designationDate
     */
    public Timestamp getDesignationDate() {
        return designationDate;
    }

    /**
     * @param designationDate the designationDate to set
     */
    public void setDesignationDate(Timestamp designationDate) {
        this.designationDate = designationDate;
    }
}
