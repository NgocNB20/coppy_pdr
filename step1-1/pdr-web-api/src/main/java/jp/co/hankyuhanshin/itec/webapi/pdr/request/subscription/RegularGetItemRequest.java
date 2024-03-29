/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription;

import java.sql.Timestamp;

/**
 *
 * 定期便対象商品リスト取得API リクエストモデル<br/>
 *
 * @author nh32114
 *
 */
public class RegularGetItemRequest {

    /** 指定日 */
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
