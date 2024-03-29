// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.guest.inquiry;
//
//import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
//import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySendMailService;
//import org.springframework.beans.factory.annotation.Autowired;
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
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.inquiry.AbstractInquiryController;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryRegistLogic;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.InquiryUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//
///**
// * ゲスト問合せ Controller<br/>
// *
// * @author kn23834
// * @version $Revision: 1.0 $
// */
//@SessionAttributes(value = "guestInquiryModel")
//@RequestMapping("/guest/inquiry/")
//@Controller
//public class GuestInquiryController extends AbstractInquiryController {
//
//    /**
//     * デフォルト戻り先ページ
//     */
//    private static final String BACKPAGE_VIEW = "redirect:/login/inquiry.html";
//
//    /**
//     * 不正遷移<br/>
//     */
//    private static final String MSGCD_REFERER_FAIL = "PKG-3720-001-A-";
//
//    /**
//     * メッセージコード：会員の問合わせの場合
//     */
//    private static final String MSGCD_MEMBER_INQUIRY = "PKG-3720-001-A-";
//
//    /**
//     * メッセージコード：問い合わせ登録失敗
//     */
//    private static final String MSGCD_REGIST_FAILED_INQUIRY = "PKG-3720-004-A-";
//
//    /**
//     * InquiryUtility
//     */
//    private InquiryUtility inquiryUtility;
//
//    /**
//     * コンストラクタ
//     *
//     * @param inquiryGetLogic
//     * @param inquiryRegistLogic
//     * @param guestInquiryHelper
//     * @param inquiryUtility
//     * @param inquirySendMailService
//     * @param asyncService
//     */
//    @Autowired
//    public GuestInquiryController(InquiryGetLogic inquiryGetLogic, InquiryRegistLogic inquiryRegistLogic, GuestInquiryHelper guestInquiryHelper, InquiryUtility inquiryUtility, InquirySendMailService inquirySendMailService, AsyncService asyncService) {
//        super(inquiryGetLogic, inquiryRegistLogic, guestInquiryHelper, inquirySendMailService, asyncService);
//        this.inquiryUtility = inquiryUtility;
//    }
//
//    /**
//     * ゲスト問合せ画面：初期処理
//     *
//     * @param guestInquiryModel
//     * @param model
//     * @return ゲスト問合せ詳細画面
//     */
//    @GetMapping(value = {"/", "/detail.html"})
//    @HEHandler(exception = AppLevelListException.class, returnView = BACKPAGE_VIEW)
//    protected String doLoadIndex(GuestInquiryModel guestInquiryModel, RedirectAttributes redirectAttributes, Model model) {
//
//        // FlashAttributeからお問い合わせ番号を受け渡されていた場合
//        // Modelにセットする（saveIcdに保存）
//        if (model.containsAttribute("icd")) {
//            guestInquiryModel.setSaveIcd((String) model.getAttribute("icd"));
//        }
//
//        String viewName = doLoad(guestInquiryModel, redirectAttributes, model);
//
//        if (viewName == null) {
//            viewName = "guest/inquiry/detail";
//        }
//
//        return viewName;
//    }
//
//    /**
//     * 登録処理
//     *
//     * @param guestInquiryModel
//     * @param error
//     * @param redirectAttributes
//     * @param model
//     * @return ゲスト問合せ詳細画面
//     */
//    @PostMapping(value = {"/", "/detail.html"}, params = "doOnceInquiryUpdate")
//    @HEHandler(exception = AppLevelListException.class, returnView = "guest/inquiry/detail")
//    public String doOnceInquiryUpdate(@Validated GuestInquiryModel guestInquiryModel, BindingResult error, RedirectAttributes redirectAttributes, Model model) {
//
//        // 受付フラグを初期化
//        guestInquiryModel.setReceived(false);
//
//        if (error.hasErrors()) {
//            return "guest/inquiry/detail";
//        }
//
//        String viewName = super.doOnceInquiryUpdate(guestInquiryModel, redirectAttributes, model);
//
//        if (viewName == null) {
//            viewName = "redirect:/guest/inquiry/detail.html?icd=" + guestInquiryModel.getIcd();
//        }
//
//        return viewName;
//    }
//
//    /**
//     * 問合せ情報の会員情報についてチェックを行う
//     * <pre>
//     * ゲストの問合せかをチェック
//     * </pre>
//     *
//     * @param dto                問い合わせ詳細Dto
//     * @param redirectAttributes
//     * @return true：上記チェックでエラーがある場合
//     */
//    @Override
//    protected boolean checkInquiryMember(InquiryDetailsDto dto, RedirectAttributes redirectAttributes, Model model) {
//        Integer memberInfoSeq = dto.getInquiryEntity().getMemberInfoSeq();
//        if (!(memberInfoSeq == null || memberInfoSeq == 0)) {
//            addMessage(MSGCD_MEMBER_INQUIRY, redirectAttributes, model);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 問合せ認証済かをチェック
//     *
//     * @param inquiryCode 注文番号
//     * @return true：認証済
//     */
//    @Override
//    protected boolean isAttested(String inquiryCode) {
//        return inquiryUtility.isAttested(inquiryCode);
//    }
//
//    /**
//     * @return 戻り先ページ取得
//     */
//    @Override
//    public String getBackpage() {
//        return BACKPAGE_VIEW;
//    }
//
//    /**
//     * エラーメッセージ取得
//     *
//     * @return MSGCD_REFERER_FAIL
//     */
//    @Override
//    public String getMsgcdRefererFail() {
//        return MSGCD_REFERER_FAIL;
//    }
//
//}
