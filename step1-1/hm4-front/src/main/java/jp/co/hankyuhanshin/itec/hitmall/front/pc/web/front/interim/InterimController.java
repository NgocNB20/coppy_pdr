///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.interim;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.temp.TempMemberInfoRegistService;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
///**
// * 利用規約画面 Controller<br/>
// *
// * @author kimura
// */
//@SessionAttributes(value = "interimModel")
//@RequestMapping("/interim")
//@Controller
//public class InterimController extends AbstractController {
//
//    /**
//     * DB一意制約例外
//     */
//    protected static final String MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL = "AMR000101";
//
//    /**
//     * 仮会員登録サービス
//     */
//    private TempMemberInfoRegistService tempMemberInfoRegistService;
//
//    /**
//     * コンストラクタ
//     *
//     * @param tempMemberInfoRegistService
//     */
//    @Autowired
//    public InterimController(TempMemberInfoRegistService tempMemberInfoRegistService) {
//        this.tempMemberInfoRegistService = tempMemberInfoRegistService;
//    }
//
//    /**
//     * 入力画面：初期処理
//     *
//     * @param interimModel
//     * @param model
//     * @return 入力画面
//     */
//    @GetMapping(value = {"/", "/index.html"})
//    protected String doLoadIndex(InterimModel interimModel, Model model) {
//
//        if (!model.containsAttribute(FLASH_MESSAGES)) {
//            // リダイレクト以外の場合、初期化処理
//            clearModel(InterimModel.class, interimModel, model);
//        }
//
//        return "interim/index";
//    }
//
//    /**
//     * 仮会員登録処理<br/>
//     *
//     * @param interimModel
//     * @param error
//     * @param redirectAttributes
//     * @param model
//     * @return 完了画面
//     */
//    @PostMapping(value = {"/", "/index.html"}, params = "doOnceTempMemberInfoRegist")
//    @HEHandler(exception = AppLevelListException.class, returnView = "interim/index")
//    public String doOnceTempMemberInfoRegist(@Validated InterimModel interimModel, BindingResult error, RedirectAttributes redirectAttributes, Model model) {
//
//        if (error.hasErrors()) {
//            return "interim/index";
//        }
//
//        try {
//            // 仮会員登録サービス実行
//            tempMemberInfoRegistService.execute(interimModel.getMemberInfoMail(), null);
//        } catch (DuplicateKeyException dke) {
//            // Exceptionログを出力しておく
//            ApplicationLogUtility appLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
//            appLogUtility.writeExceptionLog(dke);
//
//            // 万が一ユニークキー制約違反が発生した場合、再度の入力を促す
//            addMessage(MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL, redirectAttributes, model);
//            return "interim/index";
//        }
//
//        // 完了画面へ遷移
//        return "redirect:/interim/complete.html";
//    }
//
//    /**
//     * 完了画面：画面表示処理
//     *
//     * @return 完了画面
//     */
//    @GetMapping(value = {"/complete", "/complete.html"})
//    protected String doLoadComplete() {
//
//        return "interim/complete";
//    }
//}
