/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.login;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * ログイン Controller
 * ※SpringSecurityでパラメータ制御されるのでModelのSession化は行わない
 *
 * @author kaneda
 */
@Controller
public class LoginController extends AbstractController {

    /**
     * ログインHelper
     */
    private final LoginHelper loginHelper;

    /**
     * referer保存不要のURL
     */
    private static final List<String> REDIRECT_TOP_URL = Arrays.asList("/login", "/regist", "/reset");

    /**
     * コンストラクタ
     *
     * @param loginHelper ログインHelper
     */
    @Autowired
    public LoginController(LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    /**
     * ログイン画面表示処理
     *
     * @param loginModel LoginModel
     * @return 自画面
     */
    @RequestMapping(value = {"/login/", "/login/member.html", "/login/order.html"},
                    method = {RequestMethod.GET, RequestMethod.POST})
    protected String doLoadIndex(LoginModel loginModel, HttpServletRequest request, HttpServletResponse response) {

        // ログイン後の遷移先をセッションに保存する
        String refererUrl = request.getHeader("referer");
        if (refererUrl != null && REDIRECT_TOP_URL.stream().noneMatch(refererUrl::contains)) {
            request.getSession().setAttribute("referer", request.getHeader("referer"));
        }

        // "Cache-Control"はCacheControlFilterで管理をしているが、ログイン成功直後にブラウザバックし、再ログインを行うと SpringSecurity が不正操作と判断するため一時的に指定
        response.addHeader("Cache-Control", "private, no-store, no-cache, max-age=0, must-revalidate");

        if (request.getSession().getAttribute("memberInfoId") != null) {
            loginModel.setMemberInfoIdOrCustomerNo((String) request.getSession().getAttribute("memberInfoId"));
            request.getSession().setAttribute("memberInfoId", null);
        }

        loginHelper.toPageForLoad(loginModel);
        return "login/index";
    }
}
