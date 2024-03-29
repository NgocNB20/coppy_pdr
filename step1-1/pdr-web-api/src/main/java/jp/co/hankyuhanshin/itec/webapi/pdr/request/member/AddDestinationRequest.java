/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.member;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 * WEB-API連携 お届け先登録のリクエストモデル<br/>
 *
 * @author st60204
 * @version $Revision:$
 *
 */
public class AddDestinationRequest {

    /** 顧客番号 */
    private String customerNo;

    /** お届け先顧客番号 */
    private String receiveCustomerNo;

    /** お届け先事業所名 */
    private String officeName;

    /** お届け先事業所名フリガナ */
    private String officeKana;

    /** お届け先代表者名 */
    private String representative;

    /** お届け先電話番号 */
    private String tel;

    /** お届け先郵便番号 */
    private String zipCode;

    /** お届け先都道府県コード */
    private String cityCd;

    /** お届け先住所１(都道府県・市区町村) */
    private String city;

    /** お届け先住所２(丁目・番地) */
    private String address;

    /** お届け先住所３(建物名・部屋番号) */
    private String building;

    /** お届け先住所４(方書１、２) */
    private String other;

    /** お届け先顧客区分 */
    private String businessType;

    /** お届け先確認書類 */
    private String kakuninShoKbn;

    /** お届け先経理区分 */
    private String keiriKbn;

    /** お届け先オンラインログイン可否区分 */
    private String onlineYesNo;

    /** お届け先名簿区分 */
    private String memberListType;

    /**
     * @return the receiveCustomerNo
     */
    public String getReceiveCustomerNo() {
        return receiveCustomerNo;
    }

    /**
     * @param receiveCustomerNo the receiveCustomerNo to set
     */
    public void setReceiveCustomerNo(String receiveCustomerNo) {
        this.receiveCustomerNo = receiveCustomerNo;
    }

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
     * @return the cityCd
     */
    public String getCityCd() {
        return cityCd;
    }

    /**
     * @param cityCd the cityCd to set
     */
    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
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
}
