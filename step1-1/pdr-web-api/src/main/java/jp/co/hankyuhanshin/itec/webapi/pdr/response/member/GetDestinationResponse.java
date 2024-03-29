/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.response.member;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 * WEB-API連携 お届け先参照のレスポンスモデル<br/>
 *
 * @author st60204
 * @version $Revision:$
 *
 */
public class GetDestinationResponse {

    /** お届け先顧客番号 */
    private Integer receiveCustomerNo;
    /** お届け先事業所名 */
    private String officeName;
    /** お届け先事業所名フリガナ */
    private String officeKana;
    /** お届け先代表者名 */
    private String representative;
    /** お届け先顧客区分 */
    private Integer businessType;
    /** お届け先電話番号 */
    private String tel;
    /** お届け先郵便番号 */
    private String zipCode;
    /** お届け先住所１(都道府県・市区町村) */
    private String city;
    /** お届け先住所２(丁目・番地) */
    private String address;
    /** お届け先住所３(建物名・部屋番号) */
    private String building;
    /** お届け先住所４(方書１、２) */
    private String other;
    /** 承認フラグ */
    private Integer approvalFlag;

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
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType the businessType to set
     */
    public void setBusinessType(Integer businessType) {
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
     * @return the other
     */
    public String getOther() {
        return other;
    }

    /**
     * @param other the other to set
     */
    public void setOther(String other) {
        this.other = other;
    }

    /**
     * @return the approvalFlag
     */
    public Integer getApprovalFlag() {
        return approvalFlag;
    }

    /**
     * @param approvalFlag the approvalFlag to set
     */
    public void setApprovalFlag(Integer approvalFlag) {
        this.approvalFlag = approvalFlag;
    }
}
