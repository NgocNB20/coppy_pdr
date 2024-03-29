/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.member;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 会員情報取得のレスポンスモデル<br/>
 *
 * @author k-katoh
 */
public class GetUserInformationResponse {

    /** 顧客番号 */
    private String memberNo;

    /** メールアドレス */
    private String mailAddress;

    /** 事業所名 */
    private String officeName;

    /** 事業所名フリガナ */
    private String officeKana;

    /** 代表者名 */
    private String representative;

    /** 顧客区分 */
    private String businessType;

    /** 電話番号 */
    private String tel;

    /** FAX番号 */
    private String fax;

    /** 郵便番号 */
    private String zipCode;

    /** 住所1 */
    private String city;

    /** 住所2 */
    private String address;

    /** 住所3 */
    private String building;

    /** 住所4 */
    private String other1;

    /** 住所5 */
    private String other2;

    /** 休診曜日 */
    private String nonConsultationDay;

    /** Eメールによる情報提供 */
    private String mailPermitFlag;

    /** FAXによる情報提供 */
    private String faxPermitFlag;

    /** DMによる情報提供 */
    private String sendDirectMailFlag;

    /** 歯科専売品販売区分 */
    private String dentalMonopolySalesType;

    /** 医療機器販売区分 */
    private String medicalEquipmentSalesType;

    /** 医薬品注射針販売区分 */
    private String drugSalesType;

    /** クレジット決済使用可否 */
    private String creditPaymentUseFlag;

    /** コンビニ郵便振込使用可否 */
    private String transferPaymentUseFlag;

    /** 代金引換使用可否 */
    private String cashDeliveryUseFlag;

    /** 口座自動引落使用可否 */
    private String directDebitUseFlag;

    /** 月締請求使用可否 */
    private String monthlyPayUseFlag;

    /** 名簿区分 */
    private String memberListType;

    /** オンライン登録フラグ */
    private String onlineRegistFlag;

    /** 診療項目 */
    private String treatmentType;

    /** 診療項目その他テキスト */
    private String treatmentTypeMemo;

    /** 金属商品価格お知らせメール */
    private String metalPermitFlag;

    /** 確認書類 */
    private String kakuninShoKbn;

    /** 経理区分 */
    private String keiriKbn;

    /** オンラインログイン可否 */
    private String onlineYesNo;

    /**
     * @return the memberNo
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * @param memberNo the memberNo to set
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
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

    /**
     * @return the officeName
     */
    public String getOfficeName() {
        return officeName;
    }

    /**
     * @param officeName the officeName to set
     */
    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    /**
     * @return the officeKana
     */
    public String getOfficeKana() {
        return officeKana;
    }

    /**
     * @param officeKana the officeKana to set
     */
    public void setOfficeKana(String officeKana) {
        this.officeKana = officeKana;
    }

    /**
     * @return the representative
     */
    public String getRepresentative() {
        return representative;
    }

    /**
     * @param representative the representative to set
     */
    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    /**
     * @return the businessType
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType the businessType to set
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the building
     */
    public String getBuilding() {
        return building;
    }

    /**
     * @param building the building to set
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * @return the other1
     */
    public String getOther1() {
        return other1;
    }

    /**
     * @param other1 the other1 to set
     */
    public void setOther1(String other1) {
        this.other1 = other1;
    }

    /**
     * @return the other2
     */
    public String getOther2() {
        return other2;
    }

    /**
     * @param other2 the other2 to set
     */
    public void setOther2(String other2) {
        this.other2 = other2;
    }

    /**
     * @return the nonConsultationDay
     */
    public String getNonConsultationDay() {
        return nonConsultationDay;
    }

    /**
     * @param nonConsultationDay the nonConsultationDay to set
     */
    public void setNonConsultationDay(String nonConsultationDay) {
        this.nonConsultationDay = nonConsultationDay;
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
     * @return the faxPermitFlag
     */
    public String getFaxPermitFlag() {
        return faxPermitFlag;
    }

    /**
     * @param faxPermitFlag the faxPermitFlag to set
     */
    public void setFaxPermitFlag(String faxPermitFlag) {
        this.faxPermitFlag = faxPermitFlag;
    }

    /**
     * @return the sendDirectMailFlag
     */
    public String getSendDirectMailFlag() {
        return sendDirectMailFlag;
    }

    /**
     * @param sendDirectMailFlag the sendDirectMailFlag to set
     */
    public void setSendDirectMailFlag(String sendDirectMailFlag) {
        this.sendDirectMailFlag = sendDirectMailFlag;
    }

    /**
     * @return the dentalMonopolySalesType
     */
    public String getDentalMonopolySalesType() {
        return dentalMonopolySalesType;
    }

    /**
     * @param dentalMonopolySalesType the dentalMonopolySalesType to set
     */
    public void setDentalMonopolySalesType(String dentalMonopolySalesType) {
        this.dentalMonopolySalesType = dentalMonopolySalesType;
    }

    /**
     * @return the medicalEquipmentSalesType
     */
    public String getMedicalEquipmentSalesType() {
        return medicalEquipmentSalesType;
    }

    /**
     * @param medicalEquipmentSalesType the medicalEquipmentSalesType to set
     */
    public void setMedicalEquipmentSalesType(String medicalEquipmentSalesType) {
        this.medicalEquipmentSalesType = medicalEquipmentSalesType;
    }

    /**
     * @return the drugSalesType
     */
    public String getDrugSalesType() {
        return drugSalesType;
    }

    /**
     * @param drugSalesType the drugSalesType to set
     */
    public void setDrugSalesType(String drugSalesType) {
        this.drugSalesType = drugSalesType;
    }

    /**
     * @return the creditPaymentUseFlag
     */
    public String getCreditPaymentUseFlag() {
        return creditPaymentUseFlag;
    }

    /**
     * @param creditPaymentUseFlag the creditPaymentUseFlag to set
     */
    public void setCreditPaymentUseFlag(String creditPaymentUseFlag) {
        this.creditPaymentUseFlag = creditPaymentUseFlag;
    }

    /**
     * @return the transferPaymentUseFlag
     */
    public String getTransferPaymentUseFlag() {
        return transferPaymentUseFlag;
    }

    /**
     * @param transferPaymentUseFlag the transferPaymentUseFlag to set
     */
    public void setTransferPaymentUseFlag(String transferPaymentUseFlag) {
        this.transferPaymentUseFlag = transferPaymentUseFlag;
    }

    /**
     * @return the cashDeliveryUseFlag
     */
    public String getCashDeliveryUseFlag() {
        return cashDeliveryUseFlag;
    }

    /**
     * @param cashDeliveryUseFlag the cashDeliveryUseFlag to set
     */
    public void setCashDeliveryUseFlag(String cashDeliveryUseFlag) {
        this.cashDeliveryUseFlag = cashDeliveryUseFlag;
    }

    /**
     * @return the directDebitUseFlag
     */
    public String getDirectDebitUseFlag() {
        return directDebitUseFlag;
    }

    /**
     * @param directDebitUseFlag the directDebitUseFlag to set
     */
    public void setDirectDebitUseFlag(String directDebitUseFlag) {
        this.directDebitUseFlag = directDebitUseFlag;
    }

    /**
     * @return the monthlyPayUseFlag
     */
    public String getMonthlyPayUseFlag() {
        return monthlyPayUseFlag;
    }

    /**
     * @param monthlyPayUseFlag the monthlyPayUseFlag to set
     */
    public void setMonthlyPayUseFlag(String monthlyPayUseFlag) {
        this.monthlyPayUseFlag = monthlyPayUseFlag;
    }

    /**
     * @return the memberListType
     */
    public String getMemberListType() {
        return memberListType;
    }

    /**
     * @param memberListType the memberListType to set
     */
    public void setMemberListType(String memberListType) {
        this.memberListType = memberListType;
    }

    /**
     * @return the onlineRegistFlag
     */
    public String getOnlineRegistFlag() {
        return onlineRegistFlag;
    }

    /**
     * @param onlineRegistFlag the onlineRegistFlag to set
     */
    public void setOnlineRegistFlag(String onlineRegistFlag) {
        this.onlineRegistFlag = onlineRegistFlag;
    }

    /**
     * @return the treatmentType
     */
    public String getTreatmentType() {
        return treatmentType;
    }

    /**
     * @param treatmentType the treatmentType to set
     */
    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    /**
     * @return the treatmentTypeMemo
     */
    public String getTreatmentTypeMemo() {
        return treatmentTypeMemo;
    }

    /**
     * @param treatmentTypeMemo the treatmentTypeMemo to set
     */
    public void setTreatmentTypeMemo(String treatmentTypeMemo) {
        this.treatmentTypeMemo = treatmentTypeMemo;
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

    /**
     * @return the kakuninShoKbn
     */
    public String getKakuninShoKbn() {
        return kakuninShoKbn;
    }

    /**
     * @param kakuninShoKbn the kakuninShoKbn to set
     */
    public void setKakuninShoKbn(String kakuninShoKbn) {
        this.kakuninShoKbn = kakuninShoKbn;
    }

    /**
     * @return the keiriKbn
     */
    public String getKeiriKbn() {
        return keiriKbn;
    }

    /**
     * @param keiriKbn the keiriKbn to set
     */
    public void setKeiriKbn(String keiriKbn) {
        this.keiriKbn = keiriKbn;
    }

    /**
     * @return the onlineYesNo
     */
    public String getOnlineYesNo() {
        return onlineYesNo;
    }

    /**
     * @param onlineYesNo the onlineYesNo to set
     */
    public void setOnlineYesNo(String onlineYesNo) {
        this.onlineYesNo = onlineYesNo;
    }
}
