/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.CommonInfoAdministratorImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.CommonInfoUserImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmAdminUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmFrontUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.front.base.helper.crypto.MD5Helper;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetCouponListResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 共通情報ヘルパークラス
 *
 * @author natume
 * @author kaneko　(itec) チケット対応　#2644　訪問者数にクローラが含まれている
 */
@Component
public class CommonInfoUtility {

    /**
     * 会員ログインしているか否かの判定<br/>
     *
     * @param commonInfo 共通情報
     * @return true=ログイン済, false=ログイン未 又は必須情報がnullの場合
     */
    public boolean isLogin(CommonInfo commonInfo) {

        if (commonInfo == null) {
            return false;
        }
        final CommonInfoUser commonInfoUser = commonInfo.getCommonInfoUser();
        if (commonInfoUser == null) {
            return false;
        }
        if (commonInfoUser.getMemberInfoId() == null) {
            return false;
        }
        return true;
    }

    /**
     * サイト＝フロント（PC＆MB）か否かの判定<br/>
     *
     * @param commonInfo 共通情報
     * @return true=フロント, false=フロント以外 または、必須情報がnullの場合
     */
    public boolean isSiteFront(CommonInfo commonInfo) {

        if (commonInfo == null) {
            return false;
        }
        final CommonInfoBase commonBaseInfo = commonInfo.getCommonInfoBase();
        if (commonBaseInfo == null) {
            return false;
        }
        if (commonBaseInfo.getSiteType() == null) {
            return false;
        }
        return commonBaseInfo.getSiteType().isFront();
    }

    /**
     * サイト＝バックか否かの判定<br/>
     *
     * @param commonInfo 共通情報
     * @return true=バック, false=バック以外
     */
    public boolean isSiteBack(CommonInfo commonInfo) {

        if (commonInfo == null) {
            return false;
        }
        final CommonInfoBase commonBaseInfo = commonInfo.getCommonInfoBase();
        if (commonBaseInfo == null) {
            return false;
        }
        if (commonBaseInfo.getSiteType() == null) {
            return false;
        }
        return commonBaseInfo.getSiteType().isBack();
    }

    /**
     * 管理者氏名を返却
     *
     * @param commonInfo 共通情報
     * @return 管理者氏名
     */
    public String getAdministratorName(CommonInfo commonInfo) {
        if (commonInfo == null) {
            return null;
        }
        CommonInfoAdministrator adminInfo = commonInfo.getCommonInfoAdministrator();
        if (adminInfo == null) {
            return null;
        }
        String lastName = adminInfo.getAdministratorLastName();
        String firstName = adminInfo.getAdministratorFirstName();
        if (StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(firstName)) {
            return lastName + "　" + firstName;
        }
        // 以下はありえないはず
        if (StringUtils.isNotEmpty(lastName)) {
            return lastName;
        }
        if (StringUtils.isNotEmpty(firstName)) {
            return firstName;
        }
        return null;
    }

    /**
     * 会員ユーザ情報生成<br/>
     * SpringSecurity認証済の場合は、認証ユーザ詳細情報をもとに生成<br/>
     * 未認証の場合は、未認証状態で生成
     *
     * @return 会員ユーザ情報
     */
    public CommonInfoUser createCommonInfoUser() {
        // SpringSecurityから認証済ユーザ情報取得
        // ※未認証の場合はnullが返される
        HmFrontUserDetails userDetails = this.getFrontUserDetailsFromSpringSecurity();
        HmAdminUserDetails adminUserDetails = this.getAdminUserDetailsFromSpringSecurity();

        if (userDetails != null) {
            return initUser(userDetails);
        } else {
            return initUser(adminUserDetails);
        }

    }

    /**
     * 管理者ユーザ情報生成<br/>
     * SpringSecurity認証済の場合は、認証ユーザ詳細情報をもとに生成<br/>
     * 未認証の場合は、未認証状態で生成
     *
     * @return 管理者ユーザ情報
     */
    public CommonInfoAdministrator createCommonInfoAdministrator() {
        // SpringSecurityから認証済ユーザ情報取得
        // ※未認証の場合はnullが返される
        HmAdminUserDetails userDetails = this.getAdminUserDetailsFromSpringSecurity();

        return initAdministrator(userDetails);
    }

    /**
     * ログイン認証ユーザー情報取得<br/>
     *
     * @return フロントユーザ情報
     */
    public HmFrontUserDetails getFrontUserDetailsFromSpringSecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof HmFrontUserDetails) {
                // SpringSecurityの管理する領域から、ユーザ情報が取れればそのインスタンスを返却
                return (HmFrontUserDetails) authentication.getPrincipal();
            }
        }
        // 取得できなければnullを返却
        return null;
    }

    /**
     * ユーザー情報取得<br/>
     *
     * @return 管理画面ユーザ情報
     */
    public HmAdminUserDetails getAdminUserDetailsFromSpringSecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof HmAdminUserDetails) {
                // SpringSecurityの管理する領域から、ユーザ情報が取れればそのインスタンスを返却
                return (HmAdminUserDetails) authentication.getPrincipal();
            }
        }
        // 取得できなければnullを返却
        return null;
    }

    /**
     * 管理者ユーザ情報の初期化
     *
     * @param userDetails 管理者ユーザ情報
     * @return 管理者ユーザ情報
     */
    protected CommonInfoUser initUser(HmFrontUserDetails userDetails) {
        // 暫定ロジック
        // ※CommonInfoUserCreateLogicImplを流用（※一部修正もしつつ）
        CommonInfoUserImpl commonUserInfoImpl = new CommonInfoUserImpl();
        if (userDetails == null) {
            commonUserInfoImpl.setMemberInfoLastName(PropertiesUtil.getLabelPropertiesValue("guest.lastname"));
            commonUserInfoImpl.setMemberInfoFirstName(PropertiesUtil.getLabelPropertiesValue("guest.firstname"));
        } else {
            commonUserInfoImpl.setMemberInfoSeq(userDetails.getMemberInfoEntity().getMemberInfoSeq());
            commonUserInfoImpl.setMemberInfoId(userDetails.getMemberInfoEntity().getMemberInfoId());
            commonUserInfoImpl.setMemberInfoLastName(userDetails.getMemberInfoEntity().getMemberInfoLastName());
            commonUserInfoImpl.setMemberInfoFirstName(userDetails.getMemberInfoEntity().getMemberInfoFirstName());
            // 2023-renew No3-suggest-goods-front from here
            MD5Helper helper = ApplicationContextUtility.getBean(MD5Helper.class);
            commonUserInfoImpl.setCryptUserId(helper.createHash(
                            commonUserInfoImpl.getMemberInfoId() + commonUserInfoImpl.getMemberInfoSeq()));
            // 2023-renew No3-suggest-goods-front to here
        }

        // 不要機能対象項目
        // ※項目自体削除したいところだが、現在進行中の画面移行にそこそこ影響あるので、
        // 　　ひとまず暫定値をセットするにとどめておく。。
        commonUserInfoImpl.setMemberInfoRank(HTypeMemberRank.BRONZE);

        return commonUserInfoImpl;
    }

    protected CommonInfoUser initUser(HmAdminUserDetails userDetails) {
        // 暫定ロジック
        // ※CommonInfoUserCreateLogicImplを流用（※一部修正もしつつ）
        CommonInfoUserImpl commonUserInfoImpl = new CommonInfoUserImpl();
        if (userDetails == null) {
            commonUserInfoImpl.setMemberInfoLastName(PropertiesUtil.getLabelPropertiesValue("guest.lastname"));
            commonUserInfoImpl.setMemberInfoFirstName(PropertiesUtil.getLabelPropertiesValue("guest.firstname"));
        } else {
            if (ObjectUtils.isNotEmpty(userDetails.getMemberInfoEntity())) {
                commonUserInfoImpl.setMemberInfoSeq(userDetails.getMemberInfoEntity().getMemberInfoSeq());
                commonUserInfoImpl.setMemberInfoId(userDetails.getMemberInfoEntity().getMemberInfoId());
                commonUserInfoImpl.setMemberInfoLastName(userDetails.getMemberInfoEntity().getMemberInfoLastName());
                commonUserInfoImpl.setMemberInfoFirstName(userDetails.getMemberInfoEntity().getMemberInfoFirstName());
                if (Boolean.TRUE.equals(userDetails.getIsAdminLoginAsMember())) {
                    commonUserInfoImpl.setIsAdminLoginAsMember(Boolean.TRUE);
                }
                // 2023-renew No3-suggest-goods-front from here
                MD5Helper helper = ApplicationContextUtility.getBean(MD5Helper.class);
                commonUserInfoImpl.setCryptUserId(helper.createHash(
                                commonUserInfoImpl.getMemberInfoId() + commonUserInfoImpl.getMemberInfoSeq()));
                // 2023-renew No3-suggest-goods-front to here
            }

        }

        // 不要機能対象項目
        // ※項目自体削除したいところだが、現在進行中の画面移行にそこそこ影響あるので、
        // 　　ひとまず暫定値をセットするにとどめておく。。
        commonUserInfoImpl.setMemberInfoRank(HTypeMemberRank.BRONZE);

        return commonUserInfoImpl;
    }

    /**
     * 管理者ユーザ情報の初期化
     *
     * @param userDetails 管理者ユーザ情報
     * @return 管理者ユーザ情報
     */
    protected CommonInfoAdministrator initAdministrator(HmAdminUserDetails userDetails) {
        // 共通管理者情報作成
        CommonInfoAdministratorImpl info = new CommonInfoAdministratorImpl();
        if (userDetails != null) {
            info.setAdministratorSeq(userDetails.getAdministratorEntity().getAdministratorSeq());
            info.setAdministratorId(userDetails.getAdministratorEntity().getAdministratorId());
            info.setAdministratorFirstName(userDetails.getAdministratorEntity().getAdministratorFirstName());
            info.setAdministratorLastName(userDetails.getAdministratorEntity().getAdministratorLastName());
            info.setAdminAuthgroup(userDetails.getAdministratorEntity().getAdminAuthGroup());
        }
        return info;
    }

    /**
     * ログイン中の会員エンティティを取得する
     *
     * @param commonInfo 共通情報
     * @return 会員エンティティ
     */
    public MemberInfoEntity getMemberInfoEntity(CommonInfo commonInfo) {
        if (Boolean.TRUE.equals(commonInfo.getCommonInfoUser().isAdminLoginAsMember())) {
            // 管理者ログインの場合
            return getAdminUserDetailsFromSpringSecurity().getMemberInfoEntity();
        } else {
            // フロントログインの場合
            return getFrontUserDetailsFromSpringSecurity().getMemberInfoEntity();
        }
    }

    /**
     * ログイン中の会員エンティティの顧客番号を取得する
     *
     * @param commonInfo 共通情報
     * @return 顧客番号
     */
    public Integer getCustomerNo(CommonInfo commonInfo) {
        return getMemberInfoEntity(commonInfo).getCustomerNo();
    }

    // 2023-renew No24 from here

    /**
     * セッション情報の利用可能クーポン一覧数を更新する
     *
     * @param commonInfo 共通情報
     * @param webApiGetCouponListResponseDto 利用可能クーポン一覧取得レスポンス
     */
    public void setCouponCount(CommonInfo commonInfo, WebApiGetCouponListResponseDto webApiGetCouponListResponseDto) {
        if (webApiGetCouponListResponseDto == null || CollectionUtil.isEmpty(
                        webApiGetCouponListResponseDto.getInfo())) {
            commonInfo.getCommonInfoBase().setCouponCount(null);
        } else {
            commonInfo.getCommonInfoBase().setCouponCount(webApiGetCouponListResponseDto.getInfo().size());
        }
    }

    // 2023-renew No24 to here
}
