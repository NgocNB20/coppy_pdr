/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.member;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 * WEB-API連携 お届け先削除のリクエストモデル<br/>
 *
 * @author st60204
 * @version $Revision:$
 *
 */
public class DelDestinationRequest {

    /** 顧客番号 */
    private Integer customerNo;

    /** お届け先顧客番号 */
    private Integer receiveCustomerNo;

    /**
     * @return the customerNo
     */
    public Integer getCustomerNo() {
        return customerNo;
    }

    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(Integer customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * @return the receiveCustomerNo
     */
    public Integer getReceiveCustomerNo() {
        return receiveCustomerNo;
    }

    /**
     * @param receiveCustomerNo the receiveCustomerNo to set
     */
    public void setReceiveCustomerNo(Integer receiveCustomerNo) {
        this.receiveCustomerNo = receiveCustomerNo;
    }
}
