// 2023-renew No85-1 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.member;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 会員FAX情報更新のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class RepUserFaxRequest {

    /** 顧客番号 */
    private String customerNo;

    /** FAX番号 */
    private String fax;

    /**
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * @return FAX番号
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax FAX番号
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
}
// 2023-renew No85-1 to here
