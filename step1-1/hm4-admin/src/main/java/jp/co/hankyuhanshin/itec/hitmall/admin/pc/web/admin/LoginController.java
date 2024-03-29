/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログインコントローラ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Controller
public class LoginController extends AbstractController {

    /**
     * ログイン画面の初期表示
     *
     * @param error
     * @param loginModel
     * @param model
     * @return ログイン画面
     */
    @RequestMapping(value = "/login/", method = {RequestMethod.GET})
    public String doLoadIndex(@RequestParam(required = false) String error, LoginModel loginModel, Model model) {
        return "login";
    }
}
