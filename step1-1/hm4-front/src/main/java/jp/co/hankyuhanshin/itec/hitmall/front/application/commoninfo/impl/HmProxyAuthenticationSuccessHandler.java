/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring-Security Proxy ログイン成功ハンドラ
 *
 * @author kaneda
 */
@Component
public class HmProxyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * MEMBER_FULL_AUTHORITY
     */
    private static final String MEMBER_FULL_AUTHORITY = "MEMBER:8";

    /**
     * ORDER_FULL_AUTHORITY
     */
    private static final String ORDER_FULL_AUTHORITY = "ORDER:8";

    /**
     * ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE
     */
    private static final String ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE = "PKG-3786-003-A-E";

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
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);

        // 代理ログインの場合は、権限の確認を行う
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        HmAdminUserDetails hmAdminUserDetails = commonInfoUtility.getAdminUserDetailsFromSpringSecurity();
        if (hmAdminUserDetails != null) {
            //
            if (!(hmAdminUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(MEMBER_FULL_AUTHORITY))
                  && hmAdminUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(ORDER_FULL_AUTHORITY)))) {
                throw new BadCredentialsException(ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE);
            }

            // 2023-renew No71 from here
            // データを commonInfoBase に設定する
            MemberInfoEntity memberInfoEntity = hmAdminUserDetails.getMemberInfoEntity();
            commonInfo.getCommonInfoBase().setTopSaleAnnounceFlg(memberInfoEntity.getTopSaleAnnounceFlg());
            commonInfo.getCommonInfoBase().setSaleAnnounceWatchFlg(memberInfoEntity.getSaleAnnounceWatchFlg());
            commonInfo.getCommonInfoBase().setTopStockAnnounceFlg(memberInfoEntity.getTopStockAnnounceFlg());
            commonInfo.getCommonInfoBase().setStockAnnounceWatchFlg(memberInfoEntity.getStockAnnounceWatchFlg());
            // 2023-renew No71 to here
        }

        if (commonInfo.getCommonInfoUser().getMemberInfoSeq() == null) {
            SecurityContextHolder.clearContext();
            response.sendRedirect("/");
        } else {
            super.setDefaultTargetUrl("/");
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
