// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.inquiry;
//
//import java.lang.reflect.InvocationTargetException;
//
//import org.springframework.ui.Model;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailTemplateType;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryRegistLogic;
//import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
//import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySendMailService;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.AsyncTaskUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.InquiryUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//
///**
// * 問合せコントローラ基底クラス<br/>
// *
// * @author kn23834
// * @version $Revision: 1.0 $
// */
//public abstract class AbstractInquiryController extends AbstractController {
//
//    /**
//     * メッセージコード：不正遷移
//     */
//    private static final String MSGCD_REFERER_FAIL = "AIX000201";
//
//    /**
//     * メッセージコード：該当の問合わせ情報がない場合
//     */
//    private static final String MSGCD_GET_INQUIRY_FAIL = "PKG-3720-027-A-";
//
//    /**
//     * 問い合わせ情報取得Logic
//     */
//    private InquiryGetLogic inquiryGetLogic;
//
//    /**
//     * 問い合わせ情報登録ロジック
//     */
//    private InquiryRegistLogic inquiryRegistLogic;
//
//    /**
//     * お問合せ詳細画面Dxo
//     */
//    private AbstractInquiryHelper abstractInquiryHelper;
//
//    /**
//     * お問い合わせ受付メール送信サービス
//     */
//    private InquirySendMailService inquirySendMailService;
//
//    /**
//     * 非同期処理サービス
//     */
//    private AsyncService asyncService;
//
//    /**
//     * コンストラクタ
//     *
//     * @param inquiryGetLogic
//     * @param inquiryRegistLogic
//     * @param abstractInquiryHelper
//     * @param inquirySendMailService
//     * @param asyncService
//     */
//    public AbstractInquiryController(InquiryGetLogic inquiryGetLogic, InquiryRegistLogic inquiryRegistLogic, AbstractInquiryHelper abstractInquiryHelper, InquirySendMailService inquirySendMailService, AsyncService asyncService) {
//        this.inquiryGetLogic = inquiryGetLogic;
//        this.inquiryRegistLogic = inquiryRegistLogic;
//        this.abstractInquiryHelper = abstractInquiryHelper;
//        this.inquirySendMailService = inquirySendMailService;
//        this.asyncService = asyncService;
//    }
//
//    /**
//     * 初期処理<br/>
//     *
//     * @param abstractInquiryModel
//     * @param redirectAttributes
//     * @param model
//     * @return 遷移先画面
//     */
//    protected String doLoad(AbstractInquiryModel abstractInquiryModel, RedirectAttributes redirectAttributes, Model model) {
//
//        // 受付フラグを初期化
//        abstractInquiryModel.setReceived(false);
//
//        // 認証画面から遷移した時や、URLにクエリーがついている場合は icd に値が存在するが
//        // 当画面でリロードを行うと icd にはないため、保管用の変数から取得する。
//        String inquiryCode = abstractInquiryModel.getIcd() == null ? abstractInquiryModel.getSaveIcd() : abstractInquiryModel.getIcd();
//
//        // パラメータ：お問い合わせ番号のチェックを行う
//        if (inquiryCode == null || !inquiryCode.matches(ValidatorConstants.REGEX_INQUIRY_CODE)) {
//            addMessage(getMsgcdRefererFail(), redirectAttributes, model);
//            return getBackpage();
//        }
//
//        // 指定した問合せが認証済かをチェック、未認証の場合は認証画面へ遷移
//        if (!isAttested(inquiryCode)) {
//            return getBackpage();
//        }
//
//        try {
//            return initInquiry(inquiryCode, abstractInquiryModel, redirectAttributes, model);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//    }
//
//    /**
//     * 登録処理
//     *
//     * @param abstractInquiryModel
//     * @param redirectAttributes
//     * @param model
//     * @return 遷移先画面
//     */
//    public String doOnceInquiryUpdate(AbstractInquiryModel abstractInquiryModel, RedirectAttributes redirectAttributes, Model model) {
//
//        if (abstractInquiryModel.getReedOnlyDto() == null) {
//            // セッションタイムアウトが発生した場合
//            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//
//        // 問い合わせ・問い合わせ内容の登録
//        InquiryDetailsDto inquiryDetailsDto = abstractInquiryHelper.toInquiryDetailsDtoForPage(abstractInquiryModel);
//        inquiryRegistLogic.executeInquiryRegist(inquiryDetailsDto);
//
//        // お問い合わせ受付メール送信 レスポンス後処理メール
//        Object[] args = new Object[]{inquiryDetailsDto.getInquiryEntity().getInquiryCode(), HTypeMailTemplateType.INQUIRY_RECEPTION};
//        Class<?>[] argsClass = new Class<?>[]{String.class, HTypeMailTemplateType.class};
//        // 非同期処理を実行
//        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
//            asyncService.asyncService(inquirySendMailService, args, argsClass);
//        });
//
//        InquiryUtility inquiryUtility = ApplicationContextUtility.getBean(InquiryUtility.class);
//
//        // ログ出力
//        inquiryUtility.writeInquiryLog(inquiryDetailsDto.getInquiryEntity().getInquiryCode());
//
//        // 画面を受付完了状態にする
//        abstractInquiryHelper.changeAccepted(abstractInquiryModel);
//
//        try {
//            return initInquiry(inquiryDetailsDto.getInquiryEntity().getInquiryCode(), abstractInquiryModel, redirectAttributes, model);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//    }
//
//    /**
//     * 問合せ詳細画面の初期処理<br>
//     * 問合せ情報を取得しPageに設定
//     *
//     * @param inquiryCode          お問い合わせ番号
//     * @param abstractInquiryModel
//     * @param redirectAttributes
//     * @param model
//     * @return 遷移先画面
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    protected String initInquiry(String inquiryCode, AbstractInquiryModel abstractInquiryModel, RedirectAttributes redirectAttributes, Model model) throws IllegalAccessException, InvocationTargetException {
//        // 問い合わせ詳細DTOの取得
//        InquiryDetailsDto inquiryDetailsDto = inquiryGetLogic.execute(inquiryCode);
//        if (inquiryDetailsDto == null) {
//            addMessage(MSGCD_GET_INQUIRY_FAIL, redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//        if (checkInquiryMember(inquiryDetailsDto, redirectAttributes, model)) {
//            addMessage(getMsgcdRefererFail(), redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//
//        abstractInquiryHelper.toPageForLoad(abstractInquiryModel, inquiryDetailsDto);
//
//        return null;
//    }
//
//    /**
//     * 問合せ情報の会員情報についてチェックを行う
//     * <pre>
//     * 会員問合せ詳細の場合、アクセス中の会員の問合せかをチェック
//     * ゲスト問合せ詳細の場合、ゲストの問合せかをチェック
//     * </pre>
//     *
//     * @param dto                問い合わせ詳細Dto
//     * @param redirectAttributes
//     * @param model
//     * @return true：上記チェックでエラーがある場合
//     */
//    protected boolean checkInquiryMember(InquiryDetailsDto dto, RedirectAttributes redirectAttributes, Model model) {
//        return false;
//    }
//
//    /**
//     * 問合せ認証済かをチェック<br>
//     * ゲスト問合せ詳細の場合に使用する
//     *
//     * @param inquiryCode お問い合わせ番号
//     * @return true：認証済
//     */
//    protected boolean isAttested(String inquiryCode) {
//        return true;
//    }
//
//    /**
//     * 戻り先ページ取得
//     *
//     * @return url
//     */
//    public abstract String getBackpage();
//
//    /**
//     * エラーメッセージ取得
//     *
//     * @return String
//     */
//    public abstract String getMsgcdRefererFail();
//
//}
