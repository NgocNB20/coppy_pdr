/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberRank;

/**
 * ユーザー情報（共通情報）<br/>
 *
 * @author natume
 */
public class CommonInfoUserImpl implements CommonInfoUser {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 会員ID
     */
    private String memberInfoId;

    /**
     * 会員ランク
     */
    private HTypeMemberRank memberInfoRank;

    /**
     * 会員氏名(姓)
     */
    private String memberInfoLastName;

    /**
     * 会員氏名(名)
     */
    private String memberInfoFirstName;

    /**
     * isAdminLoginAsMember
     */
    private Boolean isAdminLoginAsMember;

    // 2023-renew No3-suggest-goods-front from here
    /**
     * ハッシュ化した会員情報（UK-API連携に利用）
     */
    private String cryptUserId;
    // 2023-renew No3-suggest-goods-front to here

    /**
     * @return the memberInfoSeq
     */
    @Override
    public Integer getMemberInfoSeq() {
        return memberInfoSeq;
    }

    /**
     * @param memberInfoSeq the memberInfoSeq to set
     */
    public void setMemberInfoSeq(Integer memberInfoSeq) {
        this.memberInfoSeq = memberInfoSeq;
    }

    /**
     * @return the memberInfoLastName
     */
    @Override
    public String getMemberInfoLastName() {
        return memberInfoLastName;
    }

    /**
     * @param memberInfoLastName the memberInfoLastName to set
     */
    public void setMemberInfoLastName(String memberInfoLastName) {
        this.memberInfoLastName = memberInfoLastName;
    }

    /**
     * @return the memberInfoFirstName
     */
    @Override
    public String getMemberInfoFirstName() {
        return memberInfoFirstName;
    }

    /**
     * @param memberInfoFirstName the memberInfoFirstName to set
     */
    public void setMemberInfoFirstName(String memberInfoFirstName) {
        this.memberInfoFirstName = memberInfoFirstName;
    }

    /**
     * @return the memberInfoId
     */
    @Override
    public String getMemberInfoId() {
        return memberInfoId;
    }

    /**
     * @param memberInfoId the memberInfoId to set
     */
    public void setMemberInfoId(String memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    /**
     * @return the memberInfoRank
     */
    @Override
    public HTypeMemberRank getMemberInfoRank() {
        return memberInfoRank;
    }

    /**
     * @param memberInfoRank the memberInfoRank to set
     */
    public void setMemberInfoRank(HTypeMemberRank memberInfoRank) {
        this.memberInfoRank = memberInfoRank;
    }

    /**
     * @return isAdminLoginAsMember
     */

    @Override
    public Boolean isAdminLoginAsMember() {
        return isAdminLoginAsMember;
    }

    /**
     * @param isAdminLoginAsMember isAdminLoginAsMember to set
     */
    public void setIsAdminLoginAsMember(Boolean isAdminLoginAsMember) {
        this.isAdminLoginAsMember = isAdminLoginAsMember;
    }

    // 2023-renew No3-suggest-goods-front from here

    /**
     * @return the cryptUserId
     */
    @Override
    public String getCryptUserId() {
        return cryptUserId;
    }

    /**
     * @param cryptUserId the cryptUserId to set
     */
    public void setCryptUserId(String cryptUserId) {
        this.cryptUserId = cryptUserId;
    }
    // 2023-renew No3-suggest-goods-front to here

}
