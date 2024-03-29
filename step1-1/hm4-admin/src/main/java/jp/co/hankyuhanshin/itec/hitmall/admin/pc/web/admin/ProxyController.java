/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * #035 代理注文時のフロントサイト利用<br/>
 * リダイレクト用アクションクラス<br/>
 */
@Controller
public class ProxyController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyController.class);

    /**
     * 初期表示処理を行います
     *
     * @param response
     * @param proxyModel
     * @param sessionStatus
     * @return リダイレクト
     */
    @RequestMapping(value = {"/proxy/", "/proxy.html"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String doLoadIndex(@RequestParam(required = false) HttpServletResponse response,
                              ProxyModel proxyModel,
                              SessionStatus sessionStatus) {

        // 処理成功時は代理ログイン画面へリダイレクト
        try {
            String url = PropertiesUtil.getSystemPropertiesValue("proxy.login.url");
            String from = proxyModel.getFrom();
            Integer memberInfoSeq = proxyModel.getMemberInfoSeq();

            response.sendRedirect(url + "?memberInfoSeq=" + memberInfoSeq + "&from=" + from);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/proxy";
    }
}
