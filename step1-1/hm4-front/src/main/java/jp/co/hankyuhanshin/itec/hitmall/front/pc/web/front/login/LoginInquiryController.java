// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.login;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.InquiryUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.support.SessionStatus;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
///**
// * 問合せ認証Controller
// *
// * @author kaneda
// */
//@SessionAttributes(value = "loginInquiryModel")
//@RequestMapping("/login/inquiry")
//@Controller
//public class LoginInquiryController extends AbstractController {
//
//    /**
//     * メッセージコード：該当の問い合わせ情報がない場合
//     */
//    private static final String MSGCD_GET_FEILED_INQUIRY = "PKG-3720-001-A-";
//
//    /**
//     * メッセージコード：会員のお問い合わせの場合
//     */
//    private static final String MSGCD_MEMBER_INQUIRY = "PKG-3720-002-A-";
//
//    /**
//     * InquiryUtility
//     */
//    public InquiryUtility inquiryUtility;
//
//    /**
//     * コンストラクタ
//     *
//     * @param inquiryUtility
//     */
//    @Autowired
//    public LoginInquiryController(InquiryUtility inquiryUtility) {
//        this.inquiryUtility = inquiryUtility;
//    }
//
//    /**
//     * 問合せ認証：初期処理
//     *
//     * @param icd
//     * @param loginInquiryModel
//     * @param model
//     * @return 問合せ認証画面
//     */
//    @GetMapping("/")
//    protected String doLoadIndex(@RequestParam(required = false) String icd, LoginInquiryModel loginInquiryModel, Model model) {
//
//        // 初期化処理
//        clearModel(LoginInquiryModel.class, loginInquiryModel, model);
//
//        // URLパラメータを設定
//        loginInquiryModel.setInquiryCode(icd);
//
//        return "login/inquiry";
//    }
//
//    /**
//     * 問合せ認証処理
//     *
//     * @param loginInquiryModel
//     * @param error
//     * @param redirectAttributes
//     * @return sessionStatus
//     * @return model
//     * @return 問合せ詳細画面
//     */
//    @PostMapping(value = {"/", "/inquiry.html"}, params = "doLogin")
//    @HEHandler(exception = AppLevelListException.class, returnView = "login/inquiry")
//    public String doLogin(@Validated LoginInquiryModel loginInquiryModel, BindingResult error, RedirectAttributes redirectAttributes, SessionStatus sessionStatus, Model model) {
//
//        if (error.hasErrors()) {
//            return "login/inquiry";
//        }
//
//        // 廃止不要機能
////        // 問い合わせ情報を取得
////        InquiryEntity inquiry = inquiryGetLogic.execute(loginInquiryModel.getInquiryCode(), loginInquiryModel.getInquiryTel());
////
////        // 認証対象のデータかをチェック
////        checkData(inquiry);
////
////        // Sessionに認証済みのお問い合わせ番号として登録
////        inquiryUtility.saveCode(inquiry.getInquiryCode());
//
//        // 問合せ詳細画面へ
//        redirectAttributes.addFlashAttribute("icd", loginInquiryModel.getInquiryCode());
//
//        // Modelをセッションより破棄
//        sessionStatus.setComplete();
//
//        return "redirect:/guest/inquiry/index.html";
//    }
//
//    /**
//     * 認証対象のデータかをチェック
//     *
//     * @param inquiry 問い合わせEntity
//     */
//    private void checkData(InquiryEntity inquiry) {
//        if (inquiry == null) {
//            throwMessage(MSGCD_GET_FEILED_INQUIRY);
//        }
//        Integer memberInfoSeq = inquiry.getMemberInfoSeq();
//        if (!(memberInfoSeq == null || memberInfoSeq == 0)) {
//            // 問い合わせ情報が「会員」の場合はエラーメッセージを表示
//            throwMessage(MSGCD_MEMBER_INQUIRY);
//        }
//    }
//}
