/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CheckPermissionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring-Security ログイン成功ハンドラ
 *
 * @author kaneda
 */
@Component
public class HmAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 会員ログイン情報更新失敗エラー<br/>
     * <code>MSGCD_MEMBERINFO_LOGIN_UPDATE_FAIL</code>
     */
    public static final String MSGCD_MEMBERINFO_LOGIN_UPDATE_FAIL = "SMM001103";

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

        // onAuthenticationSuccessの親メソッドで参照される、SavedRequestの有無によりSpringSecurityの遷移先URLの取得が異なる
        //   ・SavedRequestなし　⇒　DefaultTargetUrl の設定値（getTargetUrlの戻り値）が遷移先
        //   ・SavedRequestあり　⇒　SavedRequest から取得したリクエストURLが遷移先（DefaultTargetUrl の値は無視される）
        // ※「SavedRequestなし」となるケースはログイン画面に直接遷移したときのみ、DefaultTargetUrl に遷移される。それ以外はSavedRequest から取得
        CheckPermissionUtility checkPermissionUtility = ApplicationContextUtility.getBean(CheckPermissionUtility.class);
        super.setDefaultTargetUrl(checkPermissionUtility.getTargetUrl());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
