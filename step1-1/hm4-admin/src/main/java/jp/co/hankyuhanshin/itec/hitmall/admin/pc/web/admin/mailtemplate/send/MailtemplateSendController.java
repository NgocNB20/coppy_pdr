package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send.validation.MailBodyValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send.validation.MailTitleValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send.validation.group.SelectGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send.validation.group.SendTestGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.util.IdenticalDataCheckUtil;
import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.application.mailtemplate.MailTemplateTypeEntry;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailSendDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.mail.MailTemplateIndexDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.GeneralMemberTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.CreateMailBodyLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateBodyGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMarkMailSendingLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.AsyncMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.MailTemplateGetIndexListService;
import jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.MailTemplateGetService;
import jp.co.hankyuhanshin.itec.hitmall.thymeleaf.PreConverterViewUtil;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/mailtemplate/send")
@Controller
@SessionAttributes(value = "mailtemplateSendModel")
// 2023-renew general-mail from here
@PreAuthorize("hasAnyAuthority('MEMBER:8')")
// 2023-renew general-mail to here
public class MailtemplateSendController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailtemplateSendController.class);

    /**
     * 表示モード(md):list 検索画面の再検索実行<br/>
     */
    public static final String MODE_LIST = "list";

    /**
     * DXO
     */
    private final MailtemplateSendHelper mailtemplateSendHelper;

    /**
     * 管理者情報取得ロジック（サービスがないのでそのまま使用)
     */
    private final AdminLogic adminLogic;

    /**
     * メールテンプレート取得サービス
     */
    private final MailTemplateGetService mailTemplateGetService;

    /**
     * 督促／期限切れメール送信済フラグ変更ロジック
     */
    private final OrderMarkMailSendingLogic orderMarkMailSendingLogic;

    /**
     * メール送信サービス（同期送信）
     */
    private final MailSendService mailSendService;

    /**
     * メール送信サービス（非同期送信）
     */
    private final AsyncMailSendService asyncMailSendService;

    /**
     * 見出し取得サービス
     */
    private final MailTemplateGetIndexListService mailTemplateGetIndexListService;

    // 2023-renew general-mail from here
    /**
     * メールテンプレート取得ロジック
     */
    private final MailTemplateBodyGetLogic mailTemplateBodyGetLogic;

    /**
     * メール本文生成ロジック
     */
    private final CreateMailBodyLogic createMailBodyLogic;

    /**
     * メール本文用動的バリデータ
     */
    private final MailBodyValidator mailBodyValidator;

    // 2023-renew general-mail to here

    /**
     * 件名用動的バリデータ
     */
    private final MailTitleValidator mailTitleValidator;

    /**
     * 受注詳細のviewId
     */
    public static final String ORDER_DETAILS_VIEWID = "/order/details";
    // 2023-renew general-mail from here
    /**
     * メール本文を表示するテンプレートタイプ
     */
    private static HTypeMailTemplateType DISPLAY_BODY_MAIL_TEMPLATE_TYPE = HTypeMailTemplateType.GENERAL_MEMBER;
    // 2023-renew general-mail to here
    /**
     * mailTemplateType.dicon の内容
     */
    private List<MailTemplateTypeEntry> mailTemplateTypeList;

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYM000401";

    // 2023-renew general-mail from here
    /**
     * メール送信失敗メッセージ
     */
    private final static String MAIL_SEND_FAIL_MESSAGE_CODE = "PDR-2023RENEW-GENERALMAIL-001-E";
    // 2023-renew general-mail to here

    @Autowired
    public MailtemplateSendController(MailtemplateSendHelper mailtemplateSendHelper,
                                      AdminLogic adminLogic,
                                      MailTemplateGetService mailTemplateGetService,
                                      OrderMarkMailSendingLogic orderMarkMailSendingLogic,
                                      MailSendService mailSendService,
                                      AsyncMailSendService asyncMailSendService,
                                      MailTemplateGetIndexListService mailTemplateGetIndexListService,
                                      MailTitleValidator mailTitleValidator,
                                      // 2023-renew general-mail from here
                                      MailBodyValidator mailBodyValidator,
                                      MailTemplateBodyGetLogic mailTemplateBodyGetLogic,
                                      CreateMailBodyLogic createMailBodyLogic
                                      // 2023-renew general-mail to here
                                     ) {
        this.mailtemplateSendHelper = mailtemplateSendHelper;
        this.adminLogic = adminLogic;
        this.mailTemplateGetService = mailTemplateGetService;
        this.orderMarkMailSendingLogic = orderMarkMailSendingLogic;
        this.mailSendService = mailSendService;
        this.asyncMailSendService = asyncMailSendService;
        this.mailTemplateGetIndexListService = mailTemplateGetIndexListService;
        this.mailTitleValidator = mailTitleValidator;
        // 2023-renew general-mail from here
        this.mailBodyValidator = mailBodyValidator;
        this.mailTemplateBodyGetLogic = mailTemplateBodyGetLogic;
        this.createMailBodyLogic = createMailBodyLogic;
        // 2023-renew general-mail to here
    }

    @InitBinder(value = "mailtemplateSendModel")
    public void initBinder(WebDataBinder error) {
        // メール件名の動的バリデータをセット
        error.addValidators(mailTitleValidator);
        // 2023-renew general-mail from here
        // メール本文の動的バリデータをセット
        error.addValidators(mailBodyValidator);
        // 2023-renew general-mail to here
    }

    /**
     * 画面初期表示処理
     *
     * @return クラス
     */
    @GetMapping("/select")
    @HEHandler(exception = AppLevelListException.class, returnView = "mailtemplate/send/select")
    protected String doLoadSelect(HttpServletRequest request,
                                  MailtemplateSendModel mailtemplateSendModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (model.containsAttribute("mailSendDto")) {
            clearModel(MailtemplateSendModel.class, mailtemplateSendModel, model);
            MailSendDto mailSendDto = (MailSendDto) model.getAttribute("mailSendDto");
            mailtemplateSendModel.setMailSendDto(mailSendDto);
            mailtemplateSendModel.setInitialized(false);
        }

        // あるべきものがない場合
        if (mailtemplateSendModel.getMailSendDto().getApplication() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 既に初期化済みの場合
        if (mailtemplateSendModel.isInitialized()) {
            return "mailtemplate/send/select";
        }

        // 2023-renew general-mail from here
        // 遷移元ページを取得
        String previousPage = request.getHeader("referer");

        if (previousPage != null) {
            mailtemplateSendModel.setPreviousPage(previousPage);
        } else {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 送信対象メール件数が０件の場合、元の画面へ戻す
        if (mailtemplateSendModel.getMailSendDto() == null
            || mailtemplateSendModel.getMailSendDto().getMailDtoList() == null || mailtemplateSendModel.getMailSendDto()
                                                                                                       .getMailDtoList()
                                                                                                       .isEmpty()) {
            this.addMessage("AYM001701", redirectAttributes, model);
            return "redirect:" + previousPage;
        }

        // 引数をもらっていない場合は元のページに返す
        if (mailtemplateSendModel.getMailSendDto().getApplication() == null) {
            LOGGER.info("テンプレートメール送信に処理に必要な引数を受け取っていません。" + previousPage + " ページへ送り返します。");
            return "redirect:" + previousPage;
        }
        // 2023-renew general-mail to here

        // 見出し一覧を取得
        final HTypeMailTemplateType[] typeArray = mailtemplateSendModel.getMailSendDto()
                                                                       .getAvailableTemplateTypeList()
                                                                       .toArray(new HTypeMailTemplateType[] {});
        final List<MailTemplateIndexDto> indexListOrig = this.mailTemplateGetIndexListService.execute(typeArray);
        List<MailTemplateIndexDto> indexList = null;
        // 受注詳細から来たときのみ順番を保持する
        if (!isFromOrderDetails(previousPage)) {
            indexList = indexListOrig;
        } else {
            indexList = new ArrayList<>();
            for (HTypeMailTemplateType type : typeArray) {
                for (MailTemplateIndexDto dto : indexListOrig) {
                    if (dto.getMailTemplateType() == type) {
                        indexList.add(dto);
                    }
                }
            }
        }
        this.mailtemplateSendHelper.toPageSelect(indexList, mailtemplateSendModel);

        // 初期化完了
        mailtemplateSendModel.setInitialized(true);

        // 選択可能なテンプレートが１件しかない場合はそれを選択し、自動で編集画面へ進む
        // メールテンプレートが未登録のメールテンプレートタイプも１件とカウント
        int availableCount = 0;
        Integer mailTemplateSeq = null;
        String mailTemplateType = null;

        for (MailtemplateSelectItem item : mailtemplateSendModel.getIndexItems()) {
            availableCount++;
            if (item.isEmptyTemplate()) {
                continue;
            }
            mailTemplateSeq = item.getMailTemplateSeq();
            mailTemplateType = item.getMailTemplateType();

        }

        // 選択画面をスキップする
        if (availableCount == 1 && mailTemplateSeq != null && mailTemplateType != null) {
            mailtemplateSendModel.setMailTemplateSeq(mailTemplateSeq);
            mailtemplateSendModel.setMailTemplateType(mailTemplateType);
            mailtemplateSendModel.setSkippedSelectPage(true);
            return "redirect:/mailtemplate/send/edit";
        }

        return "mailtemplate/send/select";
    }

    /**
     * 受注修正から遷移してきたか<br/>
     *
     * @param previousPage 元ページ
     * @return true:受注修正から遷移してきた / false:それ以外
     */
    protected boolean isFromOrderDetails(String previousPage) {
        return ORDER_DETAILS_VIEWID.equals(previousPage);
    }

    /**
     * 遷移元ページへ戻る
     *
     * @param mailtemplateSendModel
     * @param redirectAttributes
     * @param model
     * @param sessionStatus
     * @return 遷移元ページ
     */
    @PostMapping(value = "/select", params = "doPreviousPage")
    public String doPreviousPageSelect(MailtemplateSendModel mailtemplateSendModel,
                                       RedirectAttributes redirectAttributes,
                                       Model model,
                                       SessionStatus sessionStatus) {

        // 遷移元ページ
        String previousPage = mailtemplateSendModel.getPreviousPage();

        // 遷移元ページが見つからない場合はエラー画面に遷移
        if (previousPage == null) {
            this.addMessage("AYM001703", redirectAttributes, model);
            return "redirect:/error";
        }

        sessionStatus.setComplete();

        return "redirect:" + previousPage;
    }

    /**
     * メール編集ページへ遷移
     *
     * @return 編集ページ
     */
    @PostMapping(value = "/select", params = "doEditPage")
    public String doEditPage(@Validated(SelectGroup.class) MailtemplateSendModel mailtemplateSendModel,
                             BindingResult error,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (error.hasErrors()) {
            return "mailtemplate/send/select";
        }

        // 遷移元ページ
        String previousPage = mailtemplateSendModel.getPreviousPage();

        // 遷移元ページが見つからない場合はエラー画面に遷移
        if (previousPage == null) {
            this.addMessage("AYM001703", redirectAttributes, model);
            return "redirect:/error";
        }

        return "redirect:/mailtemplate/send/edit";
    }

    /**
     * ロード処理
     *
     * @return クラス
     */
    @GetMapping("/edit")
    @HEHandler(exception = AppLevelListException.class, returnView = "mailtemplate/send/edit")
    protected String doLoadEdit(MailtemplateSendModel mailtemplateSendModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // あるべきものがない場合
        if (mailtemplateSendModel.getMailSendDto().getApplication() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 2023-renew general-mail from here
        // 遷移元ページを取得
        String previousPage = mailtemplateSendModel.getPreviousPage();
        if (previousPage == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }
        // 2023-renew general-mail to here

        mailtemplateSendModel.setMailSent(false);
        mailtemplateSendModel.setMailSendFailure(false);

        // 2023-renew general-mail from here
        // 確認画面から戻ってきた場合
        if (mailtemplateSendModel.isBackwardTransition()) {
            mailtemplateSendModel.setBackwardTransition(false);
            return "mailtemplate/send/edit";
        }

        // テストメール送信先にログインユーザのアドレスを設定する
        AdministratorEntity aEntity =
                        adminLogic.getAdministrator(getCommonInfo().getCommonInfoAdministrator().getAdministratorSeq());
        if (aEntity != null) {
            mailtemplateSendModel.setTestAddress(aEntity.getMail());
        }

        // SEQに該当するメールテンプレートを取得し画面項目へ設定する
        MailTemplateEntity entity = ApplicationContextUtility.getBean(MailTemplateEntity.class);

        // ページのパラメータとして受け取った情報を元にエンティティを取得する
        if (mailtemplateSendModel.getMailTemplateType() != null) {
            HTypeMailTemplateType type = EnumTypeUtil.getEnumFromValue(HTypeMailTemplateType.class,
                                                                       mailtemplateSendModel.getMailTemplateType()
                                                                      );
            Integer seq = mailtemplateSendModel.getMailTemplateSeq();
            entity = this.mailTemplateGetService.execute(type, seq);

            // 確認画面でメール本文のプレビュー表示が必要な汎用テンプレートの場合
            if (type == DISPLAY_BODY_MAIL_TEMPLATE_TYPE) {
                mailtemplateSendModel.setConfirmDisplayMailBody(mailTemplateBodyGetLogic.execute(type));
                mailtemplateSendModel.setMailBodyDisplayFlag(true);
            } else {
                mailtemplateSendModel.setMailBodyDisplayFlag(false);
            }
            // 2023-renew general-mail to here
        }

        mailtemplateSendHelper.toPageForLoad(entity, mailtemplateSendModel);

        return "mailtemplate/send/edit";
    }

    /**
     * テストメール送信
     *
     * @return クラス
     */
    @PostMapping(value = "/edit", params = "doSendTestMail")
    @HEHandler(exception = AppLevelListException.class, returnView = "mailtemplate/send/edit")
    public String doSendTestMail(@Validated(SendTestGroup.class) MailtemplateSendModel mailtemplateSendModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (error.hasErrors()) {
            return "mailtemplate/send/edit";
        }

        HTypeMailTemplateType mailTemplateType = EnumTypeUtil.getEnumFromLabel(HTypeMailTemplateType.class,
                                                                               mailtemplateSendModel.getMailTemplateTypeName()
                                                                              );

        List<MailDto> mailDtoList = mailtemplateSendModel.getMailSendDto().getMailDtoList();
        for (MailDto mailDto : mailDtoList) {
            MailDto testMail = new MailDto();
            testMail.setMailTemplateType(mailTemplateType);
            testMail.setSubject(mailtemplateSendModel.getMailTitle());
            testMail.setFrom(mailtemplateSendModel.getFromAddress());
            testMail.setToList(Collections.singletonList(mailtemplateSendModel.getTestAddress()));
            // 2023-renew general-mail from here
            // 確認画面でメール本文のプレビュー表示が必要な汎用テンプレートの場合
            if (mailTemplateType == DISPLAY_BODY_MAIL_TEMPLATE_TYPE) {
                testMail.setMailContentsMap(makeMailBodyMap(mailtemplateSendModel));
            } else {
                testMail.setMailContentsMap(mailDto.getMailContentsMap());
            }
            // 2023-renew general-mail to here
            try {
                mailSendService.execute(testMail);
                mailtemplateSendModel.setMailSent(true);
                mailtemplateSendModel.setMailSendFailure(false);
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                mailtemplateSendModel.setMailSent(false);
                mailtemplateSendModel.setMailSendFailure(true);
            }
        }
        return "mailtemplate/send/edit";
    }

    /**
     * 送信確認ページへ遷移
     *
     * @return クラス
     */
    @PostMapping(value = "/edit", params = "doConfirmSendPage")
    @HEHandler(exception = AppLevelListException.class, returnView = "mailtemplate/send/confirm")
    public String doConfirmSendPage(@Validated(ConfirmGroup.class) MailtemplateSendModel mailtemplateSendModel,
                                    BindingResult error,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (error.hasErrors()) {
            return "mailtemplate/send/edit";
        }

        // テスト送信先が空の場合、管理者のアドレスをセットしなおす
        if (StringUtil.isEmpty(mailtemplateSendModel.getTestAddress())) {
            AdministratorEntity aEntity = adminLogic.getAdministrator(
                            getCommonInfo().getCommonInfoAdministrator().getAdministratorSeq());
            if (aEntity != null) {
                mailtemplateSendModel.setTestAddress(aEntity.getMail());
            }
        }

        return "redirect:/mailtemplate/send/confirm";
    }

    /**
     * 遷移元アプリケーションに戻る
     *
     * @param mailtemplateSendModel
     * @param redirectAttributes
     * @param model
     * @param sessionStatus
     * @return 遷移元ページ
     */
    @PostMapping(value = "/edit", params = "doGoBackPreviousSubapplication")
    public String doGoBackPreviousSubapplication(MailtemplateSendModel mailtemplateSendModel,
                                                 RedirectAttributes redirectAttributes,
                                                 Model model,
                                                 SessionStatus sessionStatus) {

        // 遷移元ページ
        String previousPage = mailtemplateSendModel.getPreviousPage();

        // 遷移元ページが見つからない場合はエラー画面に遷移
        if (previousPage == null) {
            this.addMessage("AYM001703", redirectAttributes, model);
            return "redirect:/error";
        }

        redirectAttributes.addFlashAttribute("md", MODE_LIST);
        sessionStatus.setComplete();
        return "redirect:" + previousPage;
    }

    /**
     * 選択画面に戻る
     *
     * @return 遷移元ページ
     */
    @PostMapping(value = "/edit", params = "doSelectPage")
    public String doBackSelect() {
        return "redirect:/mailtemplate/send/select";
    }

    /**
     * ページロード時
     *
     * @return クラス
     */
    @GetMapping("/confirm")
    protected String doLoadConfirm(MailtemplateSendModel mailtemplateSendModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // あるべきものがない場合
        if (mailtemplateSendModel.getMailSendDto().getApplication() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 2023-renew general-mail from here
        // 遷移元ページを取得
        String previousPage = mailtemplateSendModel.getPreviousPage();
        if (previousPage == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }
        // 2023-renew general-mail to here

        // 2023-renew general-mail from here
        HTypeMailTemplateType type = EnumTypeUtil.getEnumFromValue(HTypeMailTemplateType.class,
                                                                   mailtemplateSendModel.getMailTemplateType()
                                                                  );
        // 確認画面でメール本文のプレビュー表示が必要な汎用テンプレートの場合
        if (type == DISPLAY_BODY_MAIL_TEMPLATE_TYPE) {

            // メール用値マップの作成
            mailtemplateSendModel.setMailContentsMap(makeMailBodyMap(mailtemplateSendModel));

            // プレビュー表示するメール本文をマップから生成
            mailtemplateSendModel.setConfirmDisplayMailBody(
                            createMailBodyLogic.execute(mailtemplateSendModel.getMailContentsMap(),
                                                        HTypeMailTemplateType.GENERAL_MEMBER
                                                       ));
        }
        // 2023-renew general-mail to here

        mailtemplateSendModel.setBackwardTransition(true);

        return "mailtemplate/send/confirm";
    }

    /**
     * メールを送信する
     *
     * @return クラス
     */
    @PostMapping(value = "/confirm", params = "doOnceSendMail")
    @HEHandler(exception = AppLevelListException.class, returnView = "mailtemplate/send/confirm")
    public String doOnceSendMail(MailtemplateSendModel mailtemplateSendModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 SessionStatus sessionStatus) {

        HTypeMailTemplateType mailTemplateType = EnumTypeUtil.getEnumFromLabel(HTypeMailTemplateType.class,
                                                                               mailtemplateSendModel.getMailTemplateTypeName()
                                                                              );

        List<MailDto> mailDtoList = new ArrayList<>();
        List<String> bccAddressList = null;
        if (mailtemplateSendModel.getBccAddress() != null) {
            bccAddressList = Arrays.asList(mailtemplateSendModel.getBccAddress().split(","));
        }
        for (MailDto mailDto : mailtemplateSendModel.getMailSendDto().getMailDtoList()) {
            mailDto.setMailTemplateType(mailTemplateType);
            mailDto.setSubject(mailtemplateSendModel.getMailTitle());
            mailDto.setFrom(mailtemplateSendModel.getFromAddress());
            mailDto.setBccList(bccAddressList);
            // 2023-renew general-mail from here
            // 確認画面でメール本文のプレビュー表示が必要な汎用テンプレートの場合
            if (mailTemplateType == DISPLAY_BODY_MAIL_TEMPLATE_TYPE) {
                mailDto.setMailContentsMap(makeMailBodyMap(mailtemplateSendModel));
            }
            // 2023-renew general-mail to here
            mailDtoList.add(mailDto);

            // SendMeToo機能対応
            if (mailtemplateSendModel.isSendMeToo()) {
                MailDto mailDtoTest = new MailDto();
                mailDtoTest.setMailTemplateType(mailTemplateType);
                mailDtoTest.setSubject(mailtemplateSendModel.getMailTitle());
                mailDtoTest.setFrom(mailtemplateSendModel.getFromAddress());
                mailDtoTest.setToList(Collections.singletonList(mailtemplateSendModel.getTestAddress()));
                // 2023-renew general-mail from here
                // 確認画面でメール本文のプレビュー表示が必要な汎用テンプレートの場合
                if (mailTemplateType == DISPLAY_BODY_MAIL_TEMPLATE_TYPE) {
                    mailDtoTest.setMailContentsMap(makeMailBodyMap(mailtemplateSendModel));
                } else {
                    mailDtoTest.setMailContentsMap(mailDto.getMailContentsMap());
                }
                // 2023-renew general-mail to here
                mailDtoList.add(mailDtoTest);
            }
        }

        // 非同期メール送信フラグ
        boolean async = mailtemplateSendModel.getMailSendDto().isAsyncRequest();

        mailtemplateSendModel.setFailureList(new ArrayList<>());
        if (!async) {
            // 同期メールを送信する
            for (MailDto dto : mailDtoList) {
                try {
                    mailSendService.execute(dto);
                } catch (Exception e) {
                    LOGGER.error("例外処理が発生しました", e);
                    mailtemplateSendModel.getFailureList().add(dto);
                }
            }
        } else {
            // 非同期メールを送信する
            try {
                asyncMailSendService.execute(mailDtoList);
            } catch (JsonProcessingException e) {
                LOGGER.error("例外処理が発生しました", e);
                mailtemplateSendModel.setFailureList(mailDtoList);
            }
        }

        // 共通情報ヘルパークラスを取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);

        // メールタイプが受注決済督促の場合は督促メール送信済みフラグをONにする
        if (mailtemplateSendModel.getMailTemplateType().equals(HTypeMailTemplateType.SETTLEMENT_REMINDER.getValue())) {
            if (mailtemplateSendModel.getMailSendDto().getMailDtoList() != null) {
                Integer orderSeq = mailtemplateSendModel.getMailSendDto().getOrderSeq();
                orderMarkMailSendingLogic.markMailSending(orderSeq, HTypeSend.SENT, null, null,
                                                          commonInfoUtility.getAdministratorName(getCommonInfo())
                                                         );
            }
        }

        // メールタイプが受注決済期限切れの場合は期限切れメール送信済みフラグをONにする
        if (mailtemplateSendModel.getMailTemplateType()
                                 .equals(HTypeMailTemplateType.SETTLEMENT_EXPIRATION_NOTIFICATION.getValue())) {
            if (mailtemplateSendModel.getMailSendDto().getMailDtoList() != null) {
                Integer orderSeq = mailtemplateSendModel.getMailSendDto().getOrderSeq();
                orderMarkMailSendingLogic.markMailSending(orderSeq, null, HTypeSend.SENT, null,
                                                          commonInfoUtility.getAdministratorName(getCommonInfo())
                                                         );
            }
        }

        // メール送信フラグをオンにする
        mailtemplateSendModel.setSendOk(true);

        // 送信処理に成功か失敗か判定
        if (mailtemplateSendModel.getFailureList().size() == 0) {
            mailtemplateSendModel.setAnySuccesser(true);
            mailtemplateSendModel.setAnyFailure(false);
        } else if (mailtemplateSendModel.getFailureList().size() == mailDtoList.size()) {
            mailtemplateSendModel.setAnySuccesser(false);
            mailtemplateSendModel.setAnyFailure(true);
        } else if (mailtemplateSendModel.getFailureList().size() != mailDtoList.size()) {
            mailtemplateSendModel.setAnySuccesser(true);
            mailtemplateSendModel.setAnyFailure(true);
        }

        // 2023-renew general-mail from here
        if (!mailtemplateSendModel.isAnySuccesser()) {
            this.addMessage(MAIL_SEND_FAIL_MESSAGE_CODE, redirectAttributes, model);
            return "redirect:/mailtemplate/send/confirm";
        }
        // 2023-renew general-mail to here

        // 完了画面をスキップする指定がある場合は、完了画面ではなく呼び出し元へ帰る
        if (mailtemplateSendModel.getMailSendDto().isSkipCompletePage()) {

            // 再検索フラグをセット
            mailtemplateSendModel.setMd(MODE_LIST);
            sessionStatus.setComplete();
            return "redirect:" + mailtemplateSendModel.getPreviousPage();
        }

        return "redirect:/mailtemplate/send/complete";
    }

    /**
     * 編集画面に戻る
     *
     * @return 遷移元ページ
     */
    @PostMapping(value = "/confirm", params = "doEditPage")
    public String doBackEdit() {
        return "redirect:/mailtemplate/send/edit";
    }

    /**
     * ロード時の処理
     *
     * @return クラス
     */
    @GetMapping("/complete")
    protected String doLoadComplete(HttpServletRequest request,
                                    MailtemplateSendModel mailtemplateSendModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // ブラウザバックの場合、処理しない
        if (mailtemplateSendModel.getMailSendDto() == null) {
            addMessage(IdenticalDataCheckUtil.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/order/";
        }

        // あるべきものがない場合
        if (mailtemplateSendModel.getMailSendDto().getApplication() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }

        // 2023-renew general-mail from here
        // 遷移元ページを取得
        String previousPage = mailtemplateSendModel.getPreviousPage();
        if (previousPage == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }
        // 2023-renew general-mail to here

        return "mailtemplate/send/complete";
    }

    /**
     * 遷移元サブアプリケーションへ戻る
     *
     * @return クラス
     */
    @PostMapping(value = "/complete", params = "doPreviousPage")
    public String doPreviousPageComplete(MailtemplateSendModel mailtemplateSendModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model,
                                         SessionStatus sessionStatus) {
        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute("md", MODE_LIST);

        // メール送信していた場合は送信フラグをONにする
        if (mailtemplateSendModel.isSendOk()) {
            redirectAttributes.addFlashAttribute("mailSentFlag", true);
        }

        String previousPage = mailtemplateSendModel.getPreviousPage();

        sessionStatus.setComplete();

        return "redirect:" + previousPage;
    }
    // 2023-renew general-mail from here

    /**
     * メール本文のマップを作成する
     *
     * @param mailtemplateSendModel
     * @return マップ
     */
    public Map<String, Object> makeMailBodyMap(MailtemplateSendModel mailtemplateSendModel) {
        PreConverterViewUtil pre = new PreConverterViewUtil();
        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(GeneralMemberTransformHelper.class);
        Map<String, Object> mailContentsMap = new HashMap<>();
        mailContentsMap.put("mailContentsMap",
                            transformer.toValueMap(pre.convert(mailtemplateSendModel.getMailBody(), false))
                           );
        return mailContentsMap;
    }
    // 2023-renew general-mail to here
}
