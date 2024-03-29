// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.inquiry;
//
//import java.util.List;
//
//import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
//import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySendMailService;
//import org.apache.commons.lang3.StringUtils;
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
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquirySearchDaoConditionDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquirySearchResultDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.inquiry.AbstractInquiryController;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryRegistLogic;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquirySearchResultListGetLogic;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//
///**
// * お問い合わせ一覧画面 Controller
// *
// * @author kimura
// */
//@RequestMapping("/member/inquiry")
//@Controller
//@SessionAttributes(value = "memberInquiryModel")
//public class MemberInquiryController extends AbstractInquiryController {
//
//    /**
//     * 問い合わせ一覧：デフォルトページ番号
//     */
//    public static final String DEFAULT_INQUIRY_PNUM = "1";
//    /**
//     * 問い合わせ一覧：１ページ当たりのデフォルト最大表示件数
//     */
//    public static final int DEFAULT_INQUIRY_LIMIT = 10;
//    /**
//     * モデルクリア時のクリア対象外フィールド
//     */
//    public static final String[] CLEAR_EXCLUDED_FIELDS = {"pnum", "limit"};
//
//    /**
//     * デフォルト戻り先ページ
//     */
//    private static final String BACKPAGE_VIEW = "redirect:/member/inquiry/index.html";
//
//    /**
//     * 不正遷移
//     */
//    public static final String MSGCD_REFERER_FAIL = "AMH000201";
//
//    /**
//     * メッセージコード：会員の問い合わせでない場合
//     */
//    private static final String MSGCD_NOT_MEMBER_INQUIRY = "PKG-3720-027-A-";
//
//    /**
//     * 問い合わせ一覧画面Helper
//     */
//    public MemberInquiryHelper memberInquiryHelper;
//
//    /**
//     * 問い合わせ検索結果リスト取得Logic
//     */
//    public InquirySearchResultListGetLogic inquirySearchResultListGetLogic;
//
//    /**
//     * コンストラクタ
//     *
//     * @param inquiryGetLogic
//     * @param inquiryRegistLogic
//     * @param memberInquiryHelper
//     * @param inquirySearchResultListGetLogic
//     * @param inquirySendMailService
//     * @param asyncService
//     */
//    @Autowired
//    public MemberInquiryController(InquiryGetLogic inquiryGetLogic, InquiryRegistLogic inquiryRegistLogic, MemberInquiryHelper memberInquiryHelper, InquirySearchResultListGetLogic inquirySearchResultListGetLogic, InquirySendMailService inquirySendMailService, AsyncService asyncService) {
//        super(inquiryGetLogic, inquiryRegistLogic, memberInquiryHelper, inquirySendMailService, asyncService);
//        this.memberInquiryHelper = memberInquiryHelper;
//        this.inquirySearchResultListGetLogic = inquirySearchResultListGetLogic;
//    }
//
//    /**
//     * 一覧画面：初期処理
//     *
//     * @param memberInquiryModel 会員問い合わせModel
//     * @param model              Model
//     * @return 一覧画面
//     */
//    @GetMapping(value = {"/", "/detail.html"})
//    @HEHandler(exception = AppLevelListException.class, returnView = "member/inquiry/index")
//    protected String doLoadIndex(MemberInquiryModel memberInquiryModel, Model model) {
//
//        // ページング情報初期化
//        if (memberInquiryModel.getPnum() == null) {
//            memberInquiryModel.setPnum(DEFAULT_INQUIRY_PNUM);
//        }
//        if (memberInquiryModel.getLimit() == 0) {
//            memberInquiryModel.setLimit(DEFAULT_INQUIRY_LIMIT);
//        }
//
//        // モデル初期化
//        clearModel(MemberInquiryModel.class, memberInquiryModel, CLEAR_EXCLUDED_FIELDS, model);
//
//        // 問い合わせ一覧の検索
//        searchInquiryList(memberInquiryModel, model);
//
//        return "member/inquiry/index";
//    }
//
//    /**
//     * 詳細画面：初期処理
//     *
//     * @param icd                一般問い合わせURLパラメータ
//     * @param memberInquiryModel 会員問い合わせModel
//     * @param redirectAttributes
//     * @param model              Model
//     * @return 詳細画面
//     */
//    @GetMapping(value = {"/detail", "/detail.html"})
//    @HEHandler(exception = AppLevelListException.class, returnView = BACKPAGE_VIEW)
//    protected String doLoadDetail(@RequestParam(required = false) String icd, MemberInquiryModel memberInquiryModel, RedirectAttributes redirectAttributes, Model model) {
//
//        if (StringUtils.isEmpty(icd)) {
//            // URLパラメータが不足した遷移の場合（session破棄ができないため、こちらで制御）
//            addMessage(getMsgcdRefererFail(), redirectAttributes, model);
//            return getBackpage();
//        }
//
//        String viewName = super.doLoad(memberInquiryModel, redirectAttributes, model);
//
//        if (viewName == null) {
//            viewName = "member/inquiry/detail";
//        }
//
//        return viewName;
//    }
//
//    /**
//     * 詳細画面：問い合わせ更新処理<br/>
//     *
//     * @param memberInquiryModel 会員問い合わせModel
//     * @param redirectAttributes
//     * @param error              BindingResult
//     * @param model              Model
//     * @return 詳細画面
//     */
//    @PostMapping(value = {"/detail", "/detail.html"}, params = "doOnceInquiryUpdate")
//    @HEHandler(exception = AppLevelListException.class, returnView = "member/inquiry/detail")
//    public String doOnceInquiryUpdate(@Validated MemberInquiryModel memberInquiryModel, BindingResult error, RedirectAttributes redirectAttributes, Model model) {
//
//        // 受付フラグを初期化
//        memberInquiryModel.setReceived(false);
//
//        if (error.hasErrors()) {
//            return "member/inquiry/detail";
//        }
//
//        String viewName = super.doOnceInquiryUpdate(memberInquiryModel, redirectAttributes, model);
//
//        if (viewName == null) {
//            viewName = "redirect:/member/inquiry/detail.html?icd=" + memberInquiryModel.getIcd();
//        }
//
//        return viewName;
//    }
//
//    /**
//     * 問い合わせ一覧の検索<br/>
//     *
//     * @param memberInquiryModel 会員問い合わせModel
//     * @param model              Model
//     */
//    protected void searchInquiryList(MemberInquiryModel memberInquiryModel, Model model) {
//
//        // PageInfoヘルパー取得
//        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
//
//        // 問合せ情報を取得し画面に設定
//        InquirySearchDaoConditionDto conditionDto = memberInquiryHelper.toInquirySearchDaoConditionDtoForLoad(memberInquiryModel);
//
//        // ページングセットアップ
//        conditionDto = pageInfoHelper.setupPageInfo(conditionDto, memberInquiryModel.getPnum(), memberInquiryModel.getLimit());
//
//        List<InquirySearchResultDto> resultList = inquirySearchResultListGetLogic.executeFront(conditionDto);
//        memberInquiryHelper.toPageForLoad(resultList, conditionDto, memberInquiryModel);
//
//        // ぺージャーセットアップ
//        pageInfoHelper.setupViewPager(conditionDto, model);
//    }
//
//    /**
//     * 問い合せ情報の会員情報についてチェックを行う
//     * <pre>
//     * アクセス中の会員の問い合せかチェック
//     * </pre>
//     *
//     * @param dto                問い合わせ詳細Dto
//     * @param redirectAttributes RedirectAttributes
//     * @param model              Model
//     * @return true：上記チェックでエラーがある場合
//     */
//    @Override
//    protected boolean checkInquiryMember(InquiryDetailsDto dto, RedirectAttributes redirectAttributes, Model model) {
//
//        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
//
//        if (!dto.getInquiryEntity().getMemberInfoSeq().equals(memberInfoSeq)) {
//            addErrorMessage(MSGCD_NOT_MEMBER_INQUIRY);
//            return true;
//        }
//        return false;
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
//}
