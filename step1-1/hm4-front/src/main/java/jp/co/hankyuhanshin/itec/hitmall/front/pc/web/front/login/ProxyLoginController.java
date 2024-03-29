/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.login;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ログイン Controller
 * ※SpringSecurityでパラメータ制御されるのでModelのSession化は行わない
 *
 * @author kaneda
 */
@Controller
public class ProxyLoginController extends AbstractController {

    private static final String EMPTY_FROM_ERROR_MESSAGE = "PKG-3786-001-A-E";

    private static final String EMPTY_MEMBER_INFO_SEQ_ERROR_MESSAGE = "PKG-3786-002-A-E";

    /**
     * ログインHelper
     */
    private final LoginHelper loginHelper;

    /**
     * コンストラクタ
     *
     * @param loginHelper ログインHelper
     */
    @Autowired
    public ProxyLoginController(LoginHelper loginHelper) {
        this.loginHelper = loginHelper;
    }

    /**
     * ログイン画面表示処理
     *
     * @param loginModel LoginModel
     * @return 自画面
     */
    @RequestMapping(value = {"/login/proxy/", "/login/proxy.html/"}, method = {RequestMethod.GET, RequestMethod.POST})
    protected String doLoadIndex(LoginModel loginModel,
                                 @RequestParam(required = false) Integer memberInfoSeq,
                                 @RequestParam(required = false) String from,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (StringUtils.isEmpty(from)) {
            addMessage(EMPTY_FROM_ERROR_MESSAGE, redirectAttributes, model);
            return "redirect:/error.html";
        }

        if (memberInfoSeq == null) {
            addMessage(EMPTY_MEMBER_INFO_SEQ_ERROR_MESSAGE, redirectAttributes, model);
            return "redirect:/error.html";
        }

        loginHelper.toPageForLoad(loginModel);
        response.addHeader("Cache-Control", "private, no-store, no-cache, max-age=0, must-revalidate");
        return "login/proxy/index";
    }

}
