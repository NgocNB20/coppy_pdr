package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.update;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.update.validation.group.CompletionGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.update.validation.group.OnceRegistMemoGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.update.validation.group.OnceRegistStatusGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.update.validation.group.OnceRelationMemberGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySendMailService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

/**
 * InquiryUpdateController Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/inquiry/update")
@Controller
@SessionAttributes(value = "inquiryUpdateModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class InquiryUpdateController extends AbstractController {

    /**
     * 問い合わせ詳細画面Dxo
     */
    private final InquiryUpdateHelper inquiryUpdateHelper;

    /**
     * ロガー
     */
    private final Logger LOGGER = LoggerFactory.getLogger(InquiryUpdateHelper.class);

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASI000402";

    /**
     * 問い合わせ詳細取得エラー
     */
    private static final String MSGCD_INQUIRY_DETAIL_ERROR = "PKG-3720-017-A-";

    /**
     * 必須情報取得エラー
     */
    private static final String MSGCD_INQUIRY_ERROR = "PKG-3720-018-A-";

    /**
     * 会員との紐づけ登録完了メッセージ
     */
    private static final String MSGCD_MEMBER_REGIST_INFO = "PKG-3720-009-A-";

    /**
     * 会員との紐づけ解除完了メッセージ
     */
    private static final String MSGCD_MEMBER_REGIST_RELEASE_INFO = "PKG-3720-012-A-";

    /**
     * 管理メモ登録完了メッセージ
     */
    private static final String MSGCD_INQUIRY_MEMO_REGIST_INFO = "PKG-3720-013-A-";

    /**
     * 問い合わせ種別エラーメッセージ
     */
    private static final String MSGCD_ILLEGAL_INQUERYTYPE_ERROR = "PKG-3720-020-A-";

    /**
     * 問い合わせ状態登録完了メッセージ
     */
    private static final String MSGCD_INQUIRY_STATUS_REGIST_INFO = "PKG-3720-006-A-";

    private static final String MSGCD_INQUIRY_STATUS_NO_CHOOSE = "AYO000601W";

    /**
     * 問い合わせ状態変更前後で差異がない場合のメッセージ
     */
    private static final String MSGCD_STATUS_NO_CHANGE_INFO = "PKG-3720-007-A-";

    private static final String MSGCD_MEMBER_REGIST_ID_NULL = "AYC000206W";
    private static final String MEMBER_INFO_ID = "会員ID（メールアドレス）";

    private static final String MODE_LIST = "list";

    /**
     * メール送信失敗メッセージ
     */
    private static final String MSGCD_MAILSEND_ERROR = "PKG-3720-020-A-";

    /**
     * 問い合わせ情報登録ロジック
     */
    private final InquiryRegistLogic inquiryRegistLogic;

    /**
     * 問い合わせ更新サービス
     */
    private final InquiryUpdateService inquiryUpdateService;

    /**
     * 問い合わせ取得ロジック
     */
    private final InquiryGetLogic inquiryGetLogic;

    /**
     * メール送信サービス
     */
    private final InquirySendMailService inquirySendMailService;

    @Autowired
    public InquiryUpdateController(InquiryUpdateHelper inquiryUpdateHelper,
                                   InquiryUpdateService inquiryUpdateService,
                                   InquiryGetLogic inquiryGetLogic,
                                   InquiryRegistLogic inquiryRegistLogic,
                                   InquirySendMailService inquirySendMailService) {
        this.inquiryUpdateHelper = inquiryUpdateHelper;
        this.inquiryUpdateService = inquiryUpdateService;
        this.inquiryGetLogic = inquiryGetLogic;
        this.inquiryRegistLogic = inquiryRegistLogic;
        this.inquirySendMailService = inquirySendMailService;
    }

    /**
     * 初期処理
     *
     * @return 自画面
     */
    @GetMapping(value = "")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/index")
    public String doLoadIndex(@RequestParam(required = false) Integer seq,
                              InquiryUpdateModel inquiryUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // throwMessageをキャッチする
        if (model.getAttribute(FLASH_MESSAGES) != null) {
            return "inquiry/update/index";
        }

        // 確認画面からの遷移である場合
        if (inquiryUpdateModel.isFromConfirm()) {
            inquiryUpdateModel.setFromConfirm(false);
            // 必須情報がなければ、エラー画面へ遷移
            InquiryEntity inquiryEntity = inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity();
            if (inquiryEntity == null) {
                addMessage(MSGCD_INQUIRY_ERROR, redirectAttributes, model);
                return "redirect:/error";
            }
            // 問合せ状態を画面遷移前の値に戻す
            inquiryUpdateModel.setInquiryStatus(inquiryUpdateModel.getPreInquiryStatus());

        } else {
            // 問い合わせSEQ
            Integer inquirySeq;
            if (seq != null) {
                clearModel(InquiryUpdateModel.class, inquiryUpdateModel, model);
                inquirySeq = seq;
            } else {
                inquirySeq = inquiryUpdateModel.getInquirySeq();
            }
            if (inquirySeq == null) {
                LOGGER.debug("問い合わせSEQを取得できませんでした。");
                // 必須情報がなければ、エラー画面へ遷移
                addMessage(MSGCD_INQUIRY_ERROR, redirectAttributes, model);
                return "redirect:/error";
            }

            inquiryUpdateModel.setFromConfirm(false);

            initComponentValue(inquiryUpdateModel);

            // 確認画面からの遷移でない場合
            inquiryUpdateModel.setNormality(true);

            // 問い合わせ情報の再取得し、画面に反映
            String resultPage = getInquiryDetailsDto(inquirySeq, redirectAttributes, inquiryUpdateModel, model);
            if (resultPage.equals("error")) {
                return "redirect:/error";
            }

            // 問い合わせ状態変更時のメッセージに利用する為、変更前の問い合わせ状態を退避させる
            if (inquiryUpdateModel.getPreInquiryStatus() == null) {
                inquiryUpdateModel.setPreInquiryStatus(inquiryUpdateModel.getInquiryStatus());
            }
        }

        return "inquiry/update/index";
    }

    /**
     * 検索処理
     *
     * @param inquirySeq お問い合わせSEQ
     * @return 自画面
     */
    private String getInquiryDetailsDto(Integer inquirySeq,
                                        RedirectAttributes redirectAttributes,
                                        InquiryUpdateModel inquiryUpdateModel,
                                        Model model) {

        // 問い合わせ詳細DTOの取得
        InquiryDetailsDto inquiryDetailsDto = inquiryGetLogic.execute(inquirySeq);

        // 問い合わせ詳細情報が取得出来ない場合エラー
        if (inquiryDetailsDto == null) {
            addMessage(MSGCD_INQUIRY_DETAIL_ERROR, redirectAttributes, model);
            return "error";
        }

        // 注文のお問い合わせの場合エラー
        if (HTypeInquiryType.ORDER == (inquiryDetailsDto.getInquiryEntity()).getInquiryType()) {
            addMessage(MSGCD_ILLEGAL_INQUERYTYPE_ERROR, redirectAttributes, model);
            return "error";
        }

        // 画面に反映
        inquiryUpdateHelper.toPageForInquiryItem(inquiryDetailsDto, inquiryUpdateModel);
        return "inquiry/update/index";
    }

    /**
     * 「送信内容を確認」押下時の画面反映
     *
     * @return 問い合わせ確認画面
     */
    @PostMapping(value = "", params = "doCompletion")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/index")
    public String doCompletion(@Validated(CompletionGroup.class) InquiryUpdateModel inquiryUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (error.hasErrors()) {
            return "inquiry/update/index";
        }

        // セッションチェック
        if (isNotExsitsSession(inquiryUpdateModel)) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 確認画面遷移前処理
        if (!inquiryUpdateModel.isCompletionReportFlag()) {
            return processConfirm(inquiryUpdateModel, redirectAttributes, model, HTypeInquiryStatus.SENDING.getValue());
        }
        return processConfirm(inquiryUpdateModel, redirectAttributes, model, HTypeInquiryStatus.COMPLETION.getValue());

    }

    /**
     * 確認画面遷移前処理
     *
     * @param inquiryUpdateModel 問い合わせモデル
     * @param redirectAttributes
     * @param model              問い合わせモデル
     * @param inquiryStatus      問い合わせ状態
     * @return 自画面
     */
    protected String processConfirm(InquiryUpdateModel inquiryUpdateModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model,
                                    String inquiryStatus) {
        // 問い合わせ情報整合性チェック
        String check = this.checkInquirySeq(inquiryUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 返信・完了時に確認画面遷移前準備処理
        this.createConfirm(inquiryStatus, inquiryUpdateModel);
        // 確認画面へ
        return "redirect:/inquiry/update/confirm";
    }

    /**
     * 返信・完了時に確認画面遷移前準備処理
     * 問い合わせ状態の設定
     * 問い合わせ詳細DTOをコピーを作成(排他の為)
     * 問い合わせ内容リストをコピーを作成（簡易化の為）
     *
     * @param inquiryStatus 問い合わせ状態
     */
    private void createConfirm(String inquiryStatus, InquiryUpdateModel inquiryUpdateModel) {

        // 問い合わせ状態の設定
        inquiryUpdateModel.setInquiryStatusFlg(inquiryStatus);
        // 新規問い合わせフラグ
        inquiryUpdateModel.setNewInquiryFlg(false);

        // 確認画面用に問い合わせ詳細DTOをDeepコピーする(排他処理の為)
        inquiryUpdateModel.setConfirmInquiryDetailsDto(CopyUtil.deepCopy(inquiryUpdateModel.getInquiryDetailsDto()));
    }

    /**
     * お問い合わせ状態を変更する<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOnceRegistStatus")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/index")
    public String doOnceRegistStatus(@Validated(OnceRegistStatusGroup.class) InquiryUpdateModel inquiryUpdateModel,
                                     BindingResult error,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        if (error.hasErrors()) {
            return "inquiry/update/index";
        }

        // セッションチェック
        if (isNotExsitsSession(inquiryUpdateModel)) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 問い合わせ状態を取得
        InquiryEntity inquiryEntity = inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity();
        inquiryEntity.setInquiryStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeInquiryStatus.class, inquiryUpdateModel.getInquiryStatus()));

        if (inquiryEntity.getInquiryStatus() == null) {
            throwMessage(MSGCD_INQUIRY_STATUS_NO_CHOOSE);
        }

        // 問い合わせ状態の変更前後で差異がない場合は、変更を促すメッセージを表示する
        if (inquiryUpdateModel.getPreInquiryStatus().equals(inquiryEntity.getInquiryStatus().getValue())) {
            throwMessage(MSGCD_STATUS_NO_CHANGE_INFO, new Object[] {inquiryEntity.getInquiryStatus().getLabel()});
        }

        // 問い合わせ情報更新
        try {
            inquiryUpdateService.executeStatusRegist(inquiryEntity);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            setAllMessages(e, redirectAttributes, model);
            return "redirect:/inquiry/";
        }

        // 正常終了メッセージ出力
        HTypeInquiryStatus preStatus = EnumTypeUtil.getEnumFromValue(HTypeInquiryStatus.class,
                                                                     inquiryUpdateModel.getPreInquiryStatus()
                                                                    );
        addInfoMessage(MSGCD_INQUIRY_STATUS_REGIST_INFO,
                       new Object[] {preStatus.getLabel(), inquiryEntity.getInquiryStatus().getLabel()},
                       redirectAttributes, model
                      );

        // 問い合わせ状態を変更したため、変更前の問い合わせ状態を変更後の状態に反映する
        inquiryUpdateModel.setPreInquiryStatus(inquiryUpdateModel.getInquiryStatus());

        return "inquiry/update/index";
    }

    /**
     * 「会員と紐づける」押下時の処理
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOnceRelationMember")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/index")
    public String doOnceRelationMember(@Validated(OnceRelationMemberGroup.class) InquiryUpdateModel inquiryUpdateModel,
                                       BindingResult error,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        if (error.hasErrors()) {
            return "inquiry/update/index";
        }

        // セッションチェック
        if (isNotExsitsSession(inquiryUpdateModel)) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 会員SEQをDTOに設定
        InquiryEntity inquiryEntity = inquiryUpdateHelper.toPageForInquiryEntityMember(inquiryUpdateModel);

        if (StringUtils.isEmpty(inquiryUpdateModel.getMemberInfoId())) {
            throwMessage(MSGCD_MEMBER_REGIST_ID_NULL, new Object[] {MEMBER_INFO_ID});
        }

        // 会員情報の有無チェック ＋ 問い合わせ情報更新
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);

        // 問い合わせ情報更新
        try {
            memberInfoEntity = inquiryUpdateService.executeMemberRegist(inquiryEntity);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            setAllMessages(e, redirectAttributes, model);
            inquiryUpdateModel.setResetMemberInfoId(true);
            for (AppLevelException exception : e.getErrorList()) {
                if (exception.getMessageCode().startsWith(InquiryUpdateService.MSGCD_INQUIRY_UPDATE_RETRY_FAIL)) {
                    return "redirect:/inquiry/";
                }
            }
            throw e;
        }
        inquiryUpdateModel.setResetMemberInfoId(false);

        // 正常終了メッセージ出力
        addInfoMessage(MSGCD_MEMBER_REGIST_INFO, new Object[] {memberInfoEntity.getMemberInfoLastName()},
                       redirectAttributes, model
                      );

        // 問い合わせ情報の再取得し、画面に反映
        String resultPage =
                        getInquiryDetailsDto(inquiryUpdateModel.getInquirySeq(), redirectAttributes, inquiryUpdateModel,
                                             model
                                            );
        if (resultPage.equals("error")) {
            return "redirect:/error";
        }
        return "inquiry/update/index";
    }

    /**
     * 「会員との紐づけ解除」押下時の処理
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOnceRelationMemberRelease")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/index")
    public String doOnceRelationMemberRelease(InquiryUpdateModel inquiryUpdateModel,
                                              RedirectAttributes redirectAttributes,
                                              Model model) {

        // セッションチェック
        if (isNotExsitsSession(inquiryUpdateModel)) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 会員SEQを問い合わせDTOに設定
        InquiryEntity inquiryEntity = inquiryUpdateHelper.toPageForInquiryEntityMemberRelease(inquiryUpdateModel);

        // 問い合わせ情報更新
        try {
            inquiryUpdateService.executeMemberRegistRelease(inquiryEntity);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            setAllMessages(e, redirectAttributes, model);
            return "redirect:/inquiry/";
        }

        // 正常終了メッセージ出力
        addInfoMessage(MSGCD_MEMBER_REGIST_RELEASE_INFO, null, redirectAttributes, model);

        // 問い合わせ情報の再取得し、画面に反映
        String resultPage =
                        getInquiryDetailsDto(inquiryUpdateModel.getInquirySeq(), redirectAttributes, inquiryUpdateModel,
                                             model
                                            );
        if (resultPage.equals("error")) {
            return "redirect:/error";
        }
        return "inquiry/update/index";
    }

    /**
     * 管理メモ登録処理
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOnceRegistMemo")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/index")
    public String doOnceRegistMemo(@Validated(OnceRegistMemoGroup.class) InquiryUpdateModel inquiryUpdateModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (error.hasErrors()) {
            return "inquiry/update/index";
        }

        // セッションチェック
        if (isNotExsitsSession(inquiryUpdateModel)) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 管理メモを問い合わせDTOに設定
        InquiryEntity inquiryEntity = inquiryUpdateHelper.toPageForInquiryEntityMemo(inquiryUpdateModel);

        // 問い合わせ情報を更新し、完了メッセージを表示

        // 問い合わせ情報更新
        try {
            inquiryUpdateService.executeMemoRegist(inquiryEntity);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            setAllMessages(e, redirectAttributes, model);
            return "redirect:/inquiry/";
        }

        addInfoMessage(MSGCD_INQUIRY_MEMO_REGIST_INFO, null, redirectAttributes, model);

        // 問い合わせ情報の再取得し、画面に反映
        String resultPage =
                        getInquiryDetailsDto(inquiryUpdateModel.getInquirySeq(), redirectAttributes, inquiryUpdateModel,
                                             model
                                            );
        if (resultPage.equals("error")) {
            return "redirect:/error";
        }
        return "inquiry/update/index";
    }

    /**
     * セッションがあるかどうか（別サブアプリからのブラウザバック や セッションタイムアウト）
     *
     * @return true..セッション切れ
     */
    protected boolean isNotExsitsSession(InquiryUpdateModel inquiryUpdateModel) {
        if (inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity() == null) {
            return true;
        }
        return false;
    }

    /**
     * 問い合わせ情報整合性チェック<br/>
     * チェックメソッド<br/>
     *
     * @return エラーの場合のみ指定画面を返す
     */
    public String checkInquirySeq(InquiryUpdateModel inquiryUpdateModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        // 問い合わせSEQが取得できない場合は、データ不整合とみなしエラーとする
        if (!inquiryUpdateModel.isNormality()) {
            // 再検索フラグをセット
            inquiryUpdateModel.setMd(MODE_LIST);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/inquiry/";
        }
        return null;
    }

    /**
     * 初期処理
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @GetMapping(value = "/confirm")
    public String doLoadConfirm(InquiryUpdateModel inquiryUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (hasErrorInput(inquiryUpdateModel)) {
            return "redirect:/error";
        }

        // 完了報告・問い合わせ内容をリストに追加
        if (inquiryUpdateModel.getConfirmInquiryDetailsDto() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }
        // 確認画面用に問い合わせ内容リストをコピーする（確認画面で問い合わせ内容表示処理の簡易化の為）
        inquiryUpdateModel.setConfirmInquiryDetailItems(new ArrayList<>(inquiryUpdateModel.getInquiryDetailItems()));
        inquiryUpdateHelper.toPageForLoad(inquiryUpdateModel);
        return "inquiry/update/confirm";

    }

    /**
     * 更新処理
     *
     * @return 問い合わせ詳細画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/confirm", params = "doOnceInquiryUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/update/confirm")
    public String doOnceInquiryUpdate(InquiryUpdateModel inquiryUpdateModel, RedirectAttributes redirect, Model model) {

        // 問い合わせ情報の更新
        InquiryEntity inquiryEntity = inquiryUpdateHelper.toInquiryEntityForInquiryUpdate(inquiryUpdateModel);
        // 問い合わせ詳細の登録
        InquiryDetailsDto inquiryDetailsDto =
                        inquiryUpdateHelper.toInquiryDetailForInquiryDetailRegist(inquiryUpdateModel);
        // 問い合わせ・問い合わせ内容の登録
        inquiryRegistLogic.executeInquiryRegist(inquiryDetailsDto);

        //不使用 ※エラーになるので残しておく
        // 完了報告の場合
        //        if (HTypeInquiryStatus.COMPLETION.getValue().equals(inquiryUpdateModel.getInquiryStatus()) && inquiryUpdateModel.isSendMailFlag()) {
        //            // メール送信
        //            sendMail(inquiryEntity.getInquiryCode(), HTypeMailTemplateType.INQUIRY_ANSWER, redirect, model);
        //            // 問い合わせ返信の場合
        //        } else if (HTypeInquiryStatus.SENDING.getValue().equals(inquiryUpdateModel.getInquiryStatus()) && inquiryUpdateModel.isSendMailFlag()) {
        //            // メール送信
        //            sendMail(inquiryEntity.getInquiryCode(), HTypeMailTemplateType.INQUIRY_ANSWER, redirect, model);
        //        }

        // 問い合せ状態を変更したので、修正前問い合せ状態に反映する
        inquiryUpdateModel.setPreInquiryStatus(inquiryUpdateModel.getInquiryStatus());

        inquiryUpdateHelper.initData(inquiryUpdateModel);

        return "redirect:/inquiry/update";

    }

    /**
     * メール送信処理<br/>
     * メール送信処理を実行する<br/>
     * 　問い合わせ回答・問い合わせ運用者
     *
     * @param inquiryCode      ご連絡番号
     * @param mailTemplateType メールテンプレートタイプ
     */
    private void sendMail(String inquiryCode,
                          HTypeMailTemplateType mailTemplateType,
                          RedirectAttributes redirect,
                          Model model) {
        try {

            boolean ret = inquirySendMailService.execute(inquiryCode, mailTemplateType);
            // メール送信失敗
            if (!ret) {
                addMessage(MSGCD_MAILSEND_ERROR, null, redirect, model);
            }
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_MAILSEND_ERROR, redirect, model);
        }
    }

    /**
     * キャンセル
     *
     * @return 問い合わせ詳細画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/confirm", params = "doCancel")
    public String doCancel(InquiryUpdateModel inquiryUpdateModel) {
        inquiryUpdateModel.setFromConfirm(true);

        return "redirect:/inquiry/update";
    }

    /**
     * 必須入力項目が存在するかチェック
     *
     * @return エラーがあった場合:true // 正常な場合:false
     */
    protected boolean hasErrorInput(InquiryUpdateModel inquiryUpdateModel) {

        InquiryDetailsDto detailsDto = inquiryUpdateModel.getInquiryDetailsDto();
        if (detailsDto == null || detailsDto.getInquiryEntity() == null) {
            return true;

        }

        return false;
    }

    private void initComponentValue(InquiryUpdateModel inquiryUpdateModel) {
        inquiryUpdateModel.setInquiryStatusItems(EnumTypeUtil.getEnumMap(HTypeInquiryStatus.class));
    }

}
