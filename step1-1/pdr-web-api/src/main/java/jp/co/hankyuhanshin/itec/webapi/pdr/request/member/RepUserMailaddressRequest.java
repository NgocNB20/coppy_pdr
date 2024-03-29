/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.member;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 会員情報更新のリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class RepUserMailaddressRequest {

    /** 顧客番号 */
    private String customerNo;

    /** メールアドレス */
    private String mailAddress;

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
     * @return the mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * @param mailAddress the mailAddress to set
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
