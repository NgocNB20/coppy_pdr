/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.member;

/**
 *
 * PDR#429 1.7次　基幹リニューアル対応　【要望No.6,7,8】　フロント会員更新追加<br/>
 * WEB-API連携 会員情報お知らせ更新のリクエストモデル<br/>
 *
 * @author st60204
 * @version $Revision:$
 *
 */
public class RepUserNoticeRequest {

    /** 顧客番号 */
    private String customerNo;

    /** Eメールによる情報提供 */
    private String mailPermitFlag;

    /** 金属商品価格お知らせメール */
    private String metalPermitFlag;

    // 2023-renew No85-1 from here
    /** 休診曜日 */
    private String nonConsultationDay;

    /** 診療内容 */
    private String treatmentType;

    /** その他診療項目 */
    private String treatmentTypeMemo;
    // 2023-renew No85-1 to here

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
     * @return the mailPermitFlag
     */
    public String getMailPermitFlag() {
        return mailPermitFlag;
    }

    /**
     * @param mailPermitFlag the mailPermitFlag to set
     */
    public void setMailPermitFlag(String mailPermitFlag) {
        this.mailPermitFlag = mailPermitFlag;
    }

    /**
     * @return the metalPermitFlag
     */
    public String getMetalPermitFlag() {
        return metalPermitFlag;
    }

    /**
     * @param metalPermitFlag the metalPermitFlag to set
     */
    public void setMetalPermitFlag(String metalPermitFlag) {
        this.metalPermitFlag = metalPermitFlag;
    }

    // 2023-renew No85-1 from here
    /**
     * Gets non consultation day.
     *
     * @return the non consultation day
     */
    public String getNonConsultationDay() {
        return nonConsultationDay;
    }

    /**
     * Sets non consultation day.
     *
     * @param nonConsultationDay the non consultation day
     */
    public void setNonConsultationDay(String nonConsultationDay) {
        this.nonConsultationDay = nonConsultationDay;
    }

    /**
     * Gets treatment type.
     *
     * @return the treatment type
     */
    public String getTreatmentType() {
        return treatmentType;
    }

    /**
     * Sets treatment type.
     *
     * @param treatmentType the treatment type
     */
    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    /**
     * Gets treatment type memo.
     *
     * @return the treatment type memo
     */
    public String getTreatmentTypeMemo() {
        return treatmentTypeMemo;
    }

    /**
     * Sets treatment type memo.
     *
     * @param treatmentTypeMemo the treatment type memo
     */
    public void setTreatmentTypeMemo(String treatmentTypeMemo) {
        this.treatmentTypeMemo = treatmentTypeMemo;
    }
    // 2023-renew No85-1 to here
}
