// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.delete;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccessType;
//import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessRegistService;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.AsyncTaskUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.support.SessionStatus;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailTemplateType;
//import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoProcessCompleteMailSendService;
//import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoRemoveService;
//import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 会員登録解除 controller
// *
// * @author kimura
// */
//@RequestMapping("/member/delete")
//@Controller
//@SessionAttributes(value = "memberDeleteModel")
//public class MemberDeleteController extends AbstractController {
//
//    /**
//     * 会員退会更新サービス
//     */
//    private final MemberInfoRemoveService memberInfoRemoveService;
//
//    /**
//     * 退会完了メール送信サービス
//     */
//    private final MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService;
//
//    /**
//     * 非同期処理サービス
//     */
//    private final AsyncService asyncService;
//
//    // PDR Migrate Customization for v4 from here
////    /**
////     * Persistent Token方式を利用する場合のトークンリポジトリ
////     */
////    private final PersistentTokenBasedRememberMeServices rememberMeTokenService;
//    // PDR Migrate Customization for v4 to here
//
//    /**
//     * アクセス情報登録
//     */
//    private final AccessRegistService accessRegistService;
//
//    /**
//     * コンストラクタ
//     *
//     * @param memberInfoRemoveService
//     * @param memberInfoProcessCompleteMailSendService
//     * @param asyncService
//     * @param rememberMeTokenService
//     * @param accessRegistService
//     */
//    @Autowired
//    public MemberDeleteController(MemberInfoRemoveService memberInfoRemoveService, MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService, AsyncService asyncService,
//// PDR Migrate Customization for v4 from here
////                                  PersistentTokenBasedRememberMeServices rememberMeTokenService,
//// PDR Migrate Customization for v4 to here
//                                  AccessRegistService accessRegistService) {
//        this.memberInfoRemoveService = memberInfoRemoveService;
//        this.memberInfoProcessCompleteMailSendService = memberInfoProcessCompleteMailSendService;
//        this.asyncService = asyncService;
//        // PDR Migrate Customization for v4 from here
////        this.rememberMeTokenService = rememberMeTokenService;
//        // PDR Migrate Customization for v4 to here
//        this.accessRegistService = accessRegistService;
//    }
//
//    /**
//     * 入力画面：初期処理
//     *
//     * @param memberDeleteModel
//     * @param model
//     * @return 入力画面
//     */
//    @GetMapping(value = {"/", "/index.html"})
//    protected String doLoadIndex(MemberDeleteModel memberDeleteModel, Model model) {
//
//        // モデル初期化
//        clearModel(MemberDeleteModel.class, memberDeleteModel, model);
//
//        return "member/delete/index";
//    }
//
//    /**
//     * 会員登録解除処理<br/>
//     *
//     * @param memberDeleteModel
//     * @param error
//     * @param sessionStatus
//     * @param model
//     * @return 完了画面
//     */
//    @PostMapping(value = {"/", "/index.html"}, params = "doOnceMemberInfoDelete")
//    @HEHandler(exception = AppLevelListException.class, returnView = "member/delete/index")
//    public String doOnceMemberInfoDelete(@Validated MemberDeleteModel memberDeleteModel, BindingResult error, SessionStatus sessionStatus, Model model, HttpServletRequest request, HttpServletResponse response) {
//
//        if (error.hasErrors()) {
//            return "member/delete/index";
//        }
//
//        // 会員SEQ取得
//        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
//
//        // 会員登録解除サービス実行
//        memberInfoRemoveService.execute(memberDeleteModel.getMemberInfoId(), memberDeleteModel.getMemberInfoPassWord());
//
//        // サービス登録
//        Object[] argsAccessInfo = new Object[] { HTypeAccessType.MEMBER_REMOVE_COUNT, 0, 1 };
//        Class<?>[] argsClassAccessInfo = new Class<?>[] { HTypeAccessType.class, Integer.class, Integer.class };
//        // 非同期処理を登録
//        AsyncTaskUtility.executeAfterTransactionCommit(() -> asyncService.asyncService(accessRegistService, argsAccessInfo, argsClassAccessInfo));
//
//        // 退会完了メール送信（非同期処理）
//        // パラメータ設定
//        Object[] args = new Object[]{memberInfoSeq, HTypeMailTemplateType.MEMBER_UNREGISTRATION};
//        Class<?>[] argsClass = new Class<?>[]{Integer.class, HTypeMailTemplateType.class};
//        // 非同期処理を実行
//        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
//            asyncService.asyncService(memberInfoProcessCompleteMailSendService, args, argsClass);
//        });
//
//        // PDR Migrate Customization for v4 from here
////        // ログアウト通知
////        SecurityContextHolder.clearContext();
////        rememberMeTokenService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        // PDR Migrate Customization for v4 to here
//
//        // Modelをセッションより破棄
//        sessionStatus.setComplete();
//
//        // 完了画面へ遷移
//        return "redirect:/delete/complete.html";
//    }
//}
