/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.utility;

import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoBatchAdministratorImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.HmAdminUserDetails;
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
public class CommonInfoBatchUtility {

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
    protected CommonInfoAdministrator initAdministrator(HmAdminUserDetails userDetails) {
        // 共通管理者情報作成
        CommonInfoBatchAdministratorImpl info = new CommonInfoBatchAdministratorImpl();
        if (userDetails != null) {
            info.setAdministratorSeq(userDetails.getAdministratorEntity().getAdministratorSeq());
            info.setAdministratorId(userDetails.getAdministratorEntity().getAdministratorId());
            info.setAdministratorFirstName(userDetails.getAdministratorEntity().getAdministratorFirstName());
            info.setAdministratorLastName(userDetails.getAdministratorEntity().getAdministratorLastName());
            info.setAdminAuthgroup(userDetails.getAdministratorEntity().getAdminAuthGroup());
        }
        return info;
    }
}
