/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginInfoUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByMemberInfoIdOrCustumerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByShopUniqueIdGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultCountResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.memberinfo.MemberInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.service.common.impl.HmFrontUserDetailsServiceImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.AccessDeviceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.MemberInfoUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Spring-Security ログイン成功ハンドラ
 *
 * @author kaneda
 */
@Component
public class HmAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 会員ログイン情報更新失敗エラー
     * <code>MSGCD_MEMBERINFO_LOGIN_UPDATE_FAIL</code>
     */
    public static final String MSGCD_MEMBERINFO_LOGIN_UPDATE_FAIL = "SMM001103";

    /**
     * ユーザーApi
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * 会員Helper
     */
    private final MemberInfoHelper memberInfoHelper;

    @Autowired
    public HmAuthenticationSuccessHandler(MemberInfoApi memberInfoApi, MemberInfoHelper memberInfoHelper) {
        this.memberInfoApi = memberInfoApi;
        this.memberInfoHelper = memberInfoHelper;
    }

    /**
     * ログイン成功時の処理
     *
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        // 2023-renew No71 from here
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        // 2023-renew No71 to here
        AccessDeviceUtility accessDeviceUtility = ApplicationContextUtility.getBean(AccessDeviceUtility.class);
        HmFrontUserDetails userDetails = commonInfoUtility.getFrontUserDetailsFromSpringSecurity();
        HmFrontUserDetailsServiceImpl userDetailsService =
                        ApplicationContextUtility.getBean(HmFrontUserDetailsServiceImpl.class);

        // ユニークIDで取得する 大文字小文字の区別をなくす為
        Integer shopSeq = 1001;
        String memberInfoIdOrCustomerNo = request.getParameter("memberInfoIdOrCustomerNo");
        // 会員ID 又は 顧客番号から会員情報取得
        MemberInfoByMemberInfoIdOrCustumerNoGetRequest memberInfoByMemberInfoIdOrCustumerNoGetRequest =
                        new MemberInfoByMemberInfoIdOrCustumerNoGetRequest();
        memberInfoByMemberInfoIdOrCustumerNoGetRequest.setMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);
        MemberInfoEntityResponse memberInfoPdrEntityResponse = null;
        try {
            memberInfoPdrEntityResponse =
                            memberInfoApi.getByMemberInfoOrCustomerNo(memberInfoByMemberInfoIdOrCustumerNoGetRequest);

        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(
                            new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }
        String shopUniqueId =
                        memberInfoUtility.createShopUniqueId(shopSeq, memberInfoPdrEntityResponse.getMemberInfoId());

        // 会員情報取得
        MemberInfoByShopUniqueIdGetRequest memberInfoByShopUniqueIdGetRequest =
                        new MemberInfoByShopUniqueIdGetRequest();
        memberInfoByShopUniqueIdGetRequest.setShopUniqueId(shopUniqueId);
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByShopUniqueId(memberInfoByShopUniqueIdGetRequest);

        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(
                            new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        MemberInfoEntity memberInfoEntity = memberInfoHelper.toMemberInfoEntity(memberInfoEntityResponse);

        // ログイン情報更新（ログイン日時等）
        String userAgent = accessDeviceUtility.getUserAgent(request);
        if (StringUtils.isNotEmpty(userAgent)) {
            HTypeDeviceType deviceType = accessDeviceUtility.getDeviceType(userAgent);

            LoginInfoUpdateRequest loginInfoUpdateRequest = new LoginInfoUpdateRequest();
            loginInfoUpdateRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
            loginInfoUpdateRequest.setUserAgent(userAgent);
            loginInfoUpdateRequest.setDeviceType(deviceType != null ? deviceType.getValue() : null);

            ResultCountResponse resultCountResponse = null;
            try {
                resultCountResponse = memberInfoApi.updateLoginInfo(loginInfoUpdateRequest);

            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(
                                new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
            }

            int result = resultCountResponse.getResultCount() != null ? resultCountResponse.getResultCount() : 0;
            if (result == 0) {
                String errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSGCD_MEMBERINFO_LOGIN_UPDATE_FAIL, null)
                                                              .getMessage();
                throw new RuntimeException(errorMessage);
            }
        }

        // ログイン情報更新（アカウントロック用のログイン失敗回数を初期化）
        if (memberInfoUtility.isAvailableAccountLock()) {
            if (memberInfoEntity.getLoginFailureCount() != null && memberInfoEntity.getLoginFailureCount() > 0) {
                int result = userDetailsService.resetLoginFailureCount(memberInfoEntity.getMemberInfoSeq());
                if (result == 0) {
                    String errorMessage =
                                    AppLevelFacesMessageUtil.getAllMessage(MSGCD_MEMBERINFO_LOGIN_UPDATE_FAIL, null)
                                                            .getMessage();
                    throw new RuntimeException(errorMessage);
                }
            }
        }
        // 2023-renew No71 from here
        // データを commonInfoBase に設定する
        commonInfo.getCommonInfoBase().setTopSaleAnnounceFlg(memberInfoEntity.getTopSaleAnnounceFlg());
        commonInfo.getCommonInfoBase().setSaleAnnounceWatchFlg(memberInfoEntity.getSaleAnnounceWatchFlg());
        commonInfo.getCommonInfoBase().setTopStockAnnounceFlg(memberInfoEntity.getTopStockAnnounceFlg());
        commonInfo.getCommonInfoBase().setStockAnnounceWatchFlg(memberInfoEntity.getStockAnnounceWatchFlg());
        // 2023-renew No71 to here

        // PDR Migrate Customization from here
        if (userDetails.getMemberInfoEntity().isPasswordNeedChange()) {
            // PDR Migrate Customization to here
            HttpSession session = request.getSession();
            session.setAttribute(
                            "memberInfoSeq", userDetailsService.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
            response.sendRedirect(PropertiesUtil.getSystemPropertiesValue("password.change.uri"));
        } else if (request.getSession().getAttribute("referer") != null) {
            super.setDefaultTargetUrl(request.getSession().getAttribute("referer").toString());
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            super.setDefaultTargetUrl("/index.html");
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }

}
