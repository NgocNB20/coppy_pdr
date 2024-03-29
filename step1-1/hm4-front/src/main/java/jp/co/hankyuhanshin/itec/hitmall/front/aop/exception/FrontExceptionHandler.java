/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.aop.exception;

import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 例外ハンドラクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@ControllerAdvice
public class FrontExceptionHandler extends AbstractExceptionHandler {
    @Override
    protected String getDefaultMessageCode() {
        return "SQL-EXCEPTION-E";
    }

    /**
     * AppLevelListExceptionハンドラメソッド
     *
     * @param request            HttpServletRequest
     * @param eList              AppLevelListException
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @param handlerMethod      HandlerMethod
     * @return 遷移先のビュー名
     */
    @ExceptionHandler
    public String handleAppLevelListException(HttpServletRequest request,
                                              AppLevelListException eList,
                                              RedirectAttributes redirectAttributes,
                                              Model model,
                                              HandlerMethod handlerMethod) {

        // -------------------
        // アプリケーションログを出力
        // -------------------
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
        applicationLogUtility.writeLogicErrorLog(eList);

        // -------------------
        // メッセージをセット
        // -------------------
        setAllMessages(eList, redirectAttributes, model, handlerMethod);

        // -------------------
        // モデルをセット
        // -------------------
        setModel(request, redirectAttributes, model, handlerMethod);

        // -------------------
        // メッセージ表示画面へ遷移
        // -------------------
        return getReturnView(AppLevelListException.class, handlerMethod);
    }

    /**
     * AppLevelExceptionハンドラメソッド
     *
     * @param request            HttpServletRequest
     * @param e                  AppLevelException
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @param handlerMethod      HandlerMethod
     * @return 遷移先のビュー名
     */
    @ExceptionHandler
    public String handleAppLevelException(HttpServletRequest request,
                                          AppLevelException e,
                                          RedirectAttributes redirectAttributes,
                                          Model model,
                                          HandlerMethod handlerMethod) {

        List<AppLevelException> exceptionList = new ArrayList<>();
        exceptionList.add(e);
        AppLevelListException eList = new AppLevelListException(exceptionList);

        // AppLevelListExceptionハンドリングメソッドに委譲
        return handleAppLevelListException(request, eList, redirectAttributes, model, handlerMethod);
    }

    /**
     * 例外ハンドラメソッド
     *
     * @param th    その他例外
     * @param model Model
     * @return 遷移先のビュー名
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleThrowable(Throwable th, Model model) {

        // -------------------
        // アプリケーションログを出力
        // -------------------
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
        applicationLogUtility.writeExceptionLog(th);

        // -------------------
        // エラー画面へ遷移
        // -------------------
        return getDefaultErrorView();
    }

    @Override
    protected String getDefaultErrorView() {
        return "error";
    }
}
