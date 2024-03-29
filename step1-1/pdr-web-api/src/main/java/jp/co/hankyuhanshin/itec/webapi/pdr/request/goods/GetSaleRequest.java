/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.goods;

import java.sql.Timestamp;

/**
 * WEB-API連携 商品セール情報取得のリクエストモデル<br/>
 *
 * @author st75001
 */
public class GetSaleRequest {

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
