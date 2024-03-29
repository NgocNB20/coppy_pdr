package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyRequiredFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyValidatePattern;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/questionnaire")
@Controller
@SessionAttributes(value = "questionnaireRegistUpdateModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class QuestionnaireRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireRegistUpdateController.class);

    /**
     * 登録完了メッセージ
     */
    protected static final String MSGCD_REGIST_COMPLETE = "PKG34-3552-001-A-I";
    /**
     * アンケート情報が取得できなかった場合エラー
     */
    protected static final String MSGCD_DONOT_GET_QUESTIONNAIREDATA = "PKG34-3552-002-A-W";

    /**
     * アンケート設問情報の追加上限件数を超過した場合エラー
     */
    protected static final String MSGCD_DONOT_ADD_QUESTION = "PKG34-3552-006-A-W";

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "PKG34-3552-007-A-W";

    protected static final String MSGCD_NUMBER_FORMAT = "PKG34-3552-202-L-W";

    protected static final String MSGCD_QUESTION_REQUIRED = "PKG34-3552-203-L-W";

    protected static final String MSGCD_REPLY_MAX_LENGTH_MAX = "PKG34-3552-204-L-W";

    private static final String MSGCD_REPLY_VALIDATE_PATTERN = "PKG34-3552-205-L-W";
    /**
     * アンケート登録ロジック
     */
    private final QuestionnaireRegistLogic questionnaireRegistLogic;

    /**
     * アンケート更新ロジック
     */
    private final QuestionnaireUpdateLogic questionnaireUpdateLogic;

    private final QuestionnaireGetLogic questionnaireGetLogic;

    /**
     * アンケート登録更新用DXO
     */
    private final QuestionnaireRegistUpdateHelper questionnaireRegistUpdateHelper;

    /**
     * アンケートチェックロジック
     */
    private final QuestionnaireCheckLogic questionnaireCheckLogic;

    /**
     * 日付関連ユーティリティクラス
     */
    private final DateUtility dateUtility;

    @Autowired
    public QuestionnaireRegistUpdateController(QuestionnaireRegistUpdateHelper questionnaireRegistUpdateHelper,
                                               QuestionnaireGetLogic questionnaireGetLogic,
                                               QuestionnaireCheckLogic questionnaireCheckLogic,
                                               QuestionnaireRegistLogic questionnaireRegistLogic,
                                               QuestionnaireUpdateLogic questionnaireUpdateLogic,
                                               DateUtility dateUtility) {

        this.questionnaireRegistUpdateHelper = questionnaireRegistUpdateHelper;
        this.questionnaireCheckLogic = questionnaireCheckLogic;
        this.questionnaireGetLogic = questionnaireGetLogic;
        this.questionnaireRegistLogic = questionnaireRegistLogic;
        this.questionnaireUpdateLogic = questionnaireUpdateLogic;
        this.dateUtility = dateUtility;
    }

    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doLoadIndex(@RequestParam(required = false) String seq,
                              @RequestParam(required = false) String md,
                              QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // (1)再表示と(2)初期表示では、それぞれ以下の処理を行う
        initComponentValue(questionnaireRegistUpdateModel);
        if (StringUtils.equals(md, "new")) {
            // 新規登録モード指定でアクセスした場合を最優先
            questionnaireRegistUpdateHelper.toPageForNewRegistPage(questionnaireRegistUpdateModel);

        } else if (questionnaireRegistUpdateModel.isFromConfirm()) {
            // 確認画面からの戻り
            questionnaireRegistUpdateHelper.toPageForLoad(questionnaireRegistUpdateModel);

        } else if (seq != null) {
            // 更新の場合
            Integer questionnaireSeq = null;
            try {
                questionnaireSeq = Integer.parseInt(seq);

            } catch (NumberFormatException e) {
                LOGGER.error("例外処理が発生しました", e);
                throwMessage(MSGCD_DONOT_GET_QUESTIONNAIREDATA);
            }

            // アンケートSEQからアンケート情報を取得する

            QuestionnaireEntity questionnaire = questionnaireGetLogic.getQuestionnaire(questionnaireSeq);
            List<QuestionEntity> questionList = questionnaireGetLogic.getQuestionList(questionnaireSeq);

            // 他のユーザによる削除対応
            // 指定されたアンケートSEQに対応するアンケート・アンケート設問が存在しない場合、検索画面に遷移しエラーメッセージを表示する
            if (questionnaire == null || questionList == null) {

                throwMessage(MSGCD_DONOT_GET_QUESTIONNAIREDATA);
            }

            // ページに取得したアンケート情報をセットする
            questionnaireRegistUpdateModel.setPreUpdateQuestionnaire(questionnaire);
            questionnaireRegistUpdateModel.setPreUpdateQuestionList(questionList);
            questionnaireRegistUpdateHelper.toPageForLoadUpdate(questionnaireRegistUpdateModel);

        } else {
            // この場合も新規扱い
            questionnaireRegistUpdateHelper.toPageForNewRegistPage(questionnaireRegistUpdateModel);
        }

        //    questionnaireRegistUpdateModel.setQuestionnaireKey("abc");
        return "questionnaire/registupdate/index";
    }

    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doConfirm(
                    @Validated(ConfirmGroup.class) QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                    BindingResult error,
                    RedirectAttributes redirectAttributes,
                    Model model) {

        if (hasErrorMessage()) {
            throwMessage();
        }

        if (error.hasErrors()) {
            return "questionnaire/registupdate/index";
        }

        checkDataPageItems(questionnaireRegistUpdateModel);
        QuestionnaireEntity questionnaire = ApplicationContextUtility.getBean(QuestionnaireEntity.class);
        List<QuestionEntity> questionList = new ArrayList<>();

        // 更新時（アンケートSEQがnullでない場合）は修正前の情報を修正後の情報ににコピーする
        // 修正された内容のみ更新し差分を出すため
        if (questionnaireRegistUpdateModel.getQuestionnaireSeq() != null) {
            questionnaire = CopyUtil.deepCopy(questionnaireRegistUpdateModel.getPreUpdateQuestionnaire());
            questionList = questionnaireRegistUpdateModel.getPreUpdateQuestionList();
        }

        // 確認画面遷移前にページ情報をアンケートエンティティにセット
        questionnaireRegistUpdateHelper.toPageForConfirmQuestionnaire(questionnaire, questionnaireRegistUpdateModel);
        // 確認画面遷移前にページ情報をアンケート設問エンティティリストにセット
        questionnaireRegistUpdateHelper.toPageForConfirmQuestion(questionList, questionnaireRegistUpdateModel);

        // アンケートチェックを行う
        // 新規登録時（アンケートSEQがnullの場合）
        if (questionnaire.getQuestionnaireSeq() == null) {
            questionnaireCheckLogic.checkForRegistAtConfirm(questionnaireRegistUpdateModel.getPostUpdateQuestionnaire(),
                                                            questionnaireRegistUpdateModel.getPostUpdateQuestionList()
                                                           );

        } else {
            // 更新時
            // 公開開始日が変更可能な場合、アンケートチェックを行う
            questionnaireRegistUpdateModel.setSeq(questionnaire.getQuestionnaireSeq());
            questionnaireCheckLogic.checkForUpdateAtConfirm(questionnaireRegistUpdateModel.getPreUpdateQuestionnaire(),
                                                            questionnaireRegistUpdateModel.getPostUpdateQuestionnaire(),
                                                            questionnaireRegistUpdateModel.getPostUpdateQuestionList(),
                                                            questionnaireRegistUpdateModel.isUpdateOpenStartTime()
                                                           );
        }

        return "redirect:/questionnaire/registupdate/confirm";
    }

    private void checkDataPageItems(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        for (int i = 0; i < questionnaireRegistUpdateModel.getIndexPageItems().size(); i++) {
            QuestionnaireRegistUpdateModelItem item = questionnaireRegistUpdateModel.getIndexPageItems().get(i);

            if (item.getReplyType().equals(HTypeReplyType.TEXTBOX.getValue()) || item.getReplyType()
                                                                                     .equals(HTypeReplyType.TEXTAREA.getValue())) {
                if (item.getReplyType().equals(HTypeReplyType.TEXTBOX.getValue()) && StringUtils.isEmpty(
                                item.getReplyValidatePattern())) {
                    addErrorMessage(MSGCD_REPLY_VALIDATE_PATTERN, new Object[] {item.getDspNo()});
                }

                try {
                    Integer.parseInt(item.getReplyMaxLength());
                } catch (NumberFormatException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    addErrorMessage(MSGCD_NUMBER_FORMAT, new Object[] {item.getDspNo()});
                }

                if (item.getReplyMaxLength() != null) {
                    if (item.getReplyMaxLength().length() > 4) {
                        addErrorMessage(MSGCD_REPLY_MAX_LENGTH_MAX, new Object[] {item.getDspNo()});
                    }
                }
            } else {
                if (StringUtils.isEmpty(item.getChoices())) {
                    addErrorMessage(MSGCD_QUESTION_REQUIRED, new Object[] {item.getDspNo()});
                }
            }
        }
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * 設問内容追加処理<br/>
     *
     * @return ページクラス
     */
    @PostMapping(value = "/registupdate", params = "doAddQuestion")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doAddQuestion(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // 設問内容登録数チェック
        List<QuestionnaireRegistUpdateModelItem> indexPageItems = questionnaireRegistUpdateModel.getIndexPageItems();
        int max = PropertiesUtil.getSystemPropertiesValueToInt("question.regist.maxcount");
        if (CollectionUtil.getSize(indexPageItems) >= max) {
            // アンケート設問の登録件数が最大値以上の場合、追加不可エラー
            throwMessage(MSGCD_DONOT_ADD_QUESTION, new Object[] {max});
        }

        // アンケート設問情報の追加
        questionnaireRegistUpdateHelper.toPageForAddQestion(questionnaireRegistUpdateModel);

        return "/questionnaire/registupdate/index";
    }

    /**
     * アンケート設問内容削除処理<br/>
     *
     * @return ページクラス
     */
    @PostMapping(value = "/registupdate", params = "doDeleteQuestion")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doDeleteQuestion(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // アンケート設問情報の追加削除
        questionnaireRegistUpdateHelper.toPageForDeleteQuestion(questionnaireRegistUpdateModel);

        return "/questionnaire/registupdate/index";
    }

    private void initComponentValue(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        // set openStatusPCItems
        Map<String, String> openStatusPCItems = new HashMap<>();
        openStatusPCItems.put(HTypeOpenStatus.OPEN.getValue(), HTypeOpenStatus.OPEN.getLabel());
        openStatusPCItems.put(HTypeOpenStatus.NO_OPEN.getValue(), HTypeOpenStatus.NO_OPEN.getLabel());
        questionnaireRegistUpdateModel.setOpenStatusPcItems(openStatusPCItems);

        // set siteMapFlagItems
        Map<String, String> siteMapFlagItemsMap = new HashMap<>();
        siteMapFlagItemsMap.put(HTypeSiteMapFlag.ON.getValue(), HTypeSiteMapFlag.ON.getLabel());
        siteMapFlagItemsMap.put(HTypeSiteMapFlag.OFF.getValue(), HTypeSiteMapFlag.OFF.getLabel());

        questionnaireRegistUpdateModel.setSiteMapFlagItems(siteMapFlagItemsMap);

        // set openStatusItems
        Map<String, String> openStatusItemsMap = new HashMap<>();
        openStatusItemsMap.put(HTypeOpenStatus.OPEN.getValue(), HTypeOpenStatus.OPEN.getLabel());
        openStatusItemsMap.put(HTypeOpenStatus.NO_OPEN.getValue(), HTypeOpenStatus.NO_OPEN.getLabel());
        questionnaireRegistUpdateModel.setOpenStatusItems(openStatusItemsMap);

        //replyRequiredFlagItems
        Map<String, String> replyRequiredFlagItemsMap = new HashMap<>();
        replyRequiredFlagItemsMap.put(
                        HTypeReplyRequiredFlag.OPTIONAL.getValue(), HTypeReplyRequiredFlag.OPTIONAL.getLabel());
        replyRequiredFlagItemsMap.put(
                        HTypeReplyRequiredFlag.REQUIRED.getValue(), HTypeReplyRequiredFlag.REQUIRED.getLabel());
        questionnaireRegistUpdateModel.setReplyRequiredFlagItems(replyRequiredFlagItemsMap);

        //replyTypeItems
        Map<String, String> replyTypeItemsMap = new HashMap<>();
        replyTypeItemsMap.put(HTypeReplyType.TEXTBOX.getValue(), HTypeReplyType.TEXTBOX.getLabel());
        replyTypeItemsMap.put(HTypeReplyType.TEXTAREA.getValue(), HTypeReplyType.TEXTAREA.getLabel());
        replyTypeItemsMap.put(HTypeReplyType.RADIOBUTTON.getValue(), HTypeReplyType.RADIOBUTTON.getLabel());
        replyTypeItemsMap.put(HTypeReplyType.PULLDOWN.getValue(), HTypeReplyType.PULLDOWN.getLabel());
        replyTypeItemsMap.put(HTypeReplyType.CHECKBOX.getValue(), HTypeReplyType.CHECKBOX.getLabel());

        questionnaireRegistUpdateModel.setReplyTypeItems(replyTypeItemsMap);

        //replyValidatePatternItems
        Map<String, String> replyValidatePatternItemsMap = new HashMap<>();
        replyValidatePatternItemsMap.put(
                        HTypeReplyValidatePattern.NO_LIMIT.getValue(), HTypeReplyValidatePattern.NO_LIMIT.getLabel());
        replyValidatePatternItemsMap.put(HTypeReplyValidatePattern.ONLY_EM_SIZE.getValue(),
                                         HTypeReplyValidatePattern.ONLY_EM_SIZE.getLabel()
                                        );
        replyValidatePatternItemsMap.put(HTypeReplyValidatePattern.ONLY_AN_CHAR.getValue(),
                                         HTypeReplyValidatePattern.ONLY_AN_CHAR.getLabel()
                                        );
        replyValidatePatternItemsMap.put(HTypeReplyValidatePattern.ONLY_A_CHAR.getValue(),
                                         HTypeReplyValidatePattern.ONLY_A_CHAR.getLabel()
                                        );
        replyValidatePatternItemsMap.put(HTypeReplyValidatePattern.ONLY_N_CHAR.getValue(),
                                         HTypeReplyValidatePattern.ONLY_N_CHAR.getLabel()
                                        );

        questionnaireRegistUpdateModel.setReplyValidatePatternItems(replyValidatePatternItemsMap);

        questionnaireRegistUpdateModel.setUpdateOpenStartTime(true);

    }

    /**
     * 画面初期表示時の処理。<br />
     * <pre>
     * 更新時は変更前後で差異項目の背景色を変更する。
     * </pre>
     *
     * @return 自画面
     */
    @GetMapping(value = "/registupdate/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/confirm")
    public String doLoadConfirm(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // 不正遷移チェック
        if (questionnaireRegistUpdateModel.getPostUpdateQuestionnaire() == null || StringUtils.isEmpty(
                        questionnaireRegistUpdateModel.getPostUpdateQuestionnaire().getQuestionnaireKey())) {
            return "redirect:/error";
        }

        // ページ情報のセット
        questionnaireRegistUpdateHelper.toPageForLoad(questionnaireRegistUpdateModel);

        // 更新時に修正前後の差分を求める
        if (questionnaireRegistUpdateModel.isUpdateQ()) {
            // 変更内容に差異がある場合は背景色を変更する
            questionnaireRegistUpdateModel.setModifiedQuestionnaireList(
                            DiffUtil.diff(questionnaireRegistUpdateModel.getPreUpdateQuestionnaire(),
                                          questionnaireRegistUpdateModel.getPostUpdateQuestionnaire()
                                         ));
            List<String> modifiedQuestionList = DiffUtil.diff(questionnaireRegistUpdateModel.getPreUpdateQuestionList(),
                                                              questionnaireRegistUpdateModel.getPostUpdateQuestionList()
                                                             );
            questionnaireRegistUpdateModel.setModifiedQuestionList(modifiedQuestionList);
        }

        // 公開中アンケートのフロント表示項目の修正があった場合は変更確認ダイアログを表示する
        if (confirmUpdate(questionnaireRegistUpdateModel)) {
            questionnaireRegistUpdateModel.setOpen(true);
        } else {
            questionnaireRegistUpdateModel.setOpen(false);
        }

        return "questionnaire/registupdate/confirm";
    }

    /**
     * 確認ダイアログを表示するかをチェックする。<br />
     * <pre>
     * 修正前後でフロント表示項目（名称、管理メモ以外）が変更されているかを判断する。
     * 終了したアンケートはダイアログが表示される前にエラーチェックではじかれる。
     * </pre>
     *
     * @return フロント表示項目に差異がある場合はtrueを返す
     */
    protected boolean confirmUpdate(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        // 新規登録時はダイアログを表示しないのでfalseを返す
        if (questionnaireRegistUpdateModel.getQuestionnaireSeq() == null) {
            return false;
        }

        // 公開開始日が未来日のアンケートはダイアログを表示しないのでfalseを返す
        if (dateUtility.isAfterCurrentTime(
                        questionnaireRegistUpdateModel.getPreUpdateQuestionnaire().getOpenStartTime())) {
            return false;
        }

        // 差分のあった項目名のリスト(アンケート用)を取得する
        List<String> itemNameList = new ArrayList<>();
        itemNameList.addAll(questionnaireRegistUpdateModel.getModifiedQuestionnaireList());

        List<String> itemNameCopyList = new ArrayList<>(itemNameList);

        // 差分にフロント表示項目以外が含まれている場合はリストから削除する
        for (String itemName : itemNameCopyList) {
            if (itemName.equals("QuestionnaireEntity.name") || itemName.equals("QuestionnaireEntity.memo")) {
                itemNameList.remove(itemName);
            }
        }

        // 差分のあった項目名のリスト（アンケート設問用）を取得する
        itemNameList.addAll(questionnaireRegistUpdateModel.getModifiedQuestionList());

        // 変更リストサイズが"0"ならば変更はないのでfalseを返す
        if (itemNameList.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * 登録ボタン押下時の処理。<br />
     * <pre>
     * アンケート登録可能チェック後、登録更新処理を行う。
     * 新規登録時はアンケートテーブル・アンケートインデックステーブルに新規登録を行う。
     * 更新時はアンケートテーブルに新規登録し、アンケートインデックステーブルを更新する。
     * </pre>
     *
     * @return アンケート検索画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceRegistUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/confirm")
    public String doOnceRegistUpdate(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                     BindingResult error,
                                     RedirectAttributes redirectAttributes,
                                     SessionStatus sessionStatus,
                                     Model model) {

        // 更新処理
        if (questionnaireRegistUpdateModel.isUpdateQ()) {

            // アンケートチェックを行う
            questionnaireCheckLogic.checkForUpdateAtOnceRegist(
                            questionnaireRegistUpdateModel.getPreUpdateQuestionnaire(),
                            questionnaireRegistUpdateModel.getPostUpdateQuestionnaire(),
                            questionnaireRegistUpdateModel.getPreUpdateQuestionList(),
                            questionnaireRegistUpdateModel.getPostUpdateQuestionList(),
                            questionnaireRegistUpdateModel.isUpdateOpenStartTime()
                                                              );

            // 更新時、アンケート情報を更新する
            questionnaireUpdateLogic.execute(questionnaireRegistUpdateModel.getPostUpdateQuestionnaire(),
                                             questionnaireRegistUpdateModel.getPreUpdateQuestionList(),
                                             questionnaireRegistUpdateModel.getPostUpdateQuestionList()
                                            );

            // 新規登録処理
        } else {
            // アンケートチェックを行う
            questionnaireCheckLogic.checkForRegistAtOnceRegist(
                            questionnaireRegistUpdateModel.getPostUpdateQuestionnaire(),
                            questionnaireRegistUpdateModel.getPostUpdateQuestionList()
                                                              );

            // 新規登録時、アンケート情報をアンケート・アンケート設問TBLに新規登録する
            questionnaireRegistLogic.execute(questionnaireRegistUpdateModel.getPostUpdateQuestionnaire(),
                                             questionnaireRegistUpdateModel.getPostUpdateQuestionList()
                                            );
        }
        // 登録完了後メッセージを表示する
        addInfoMessage(MSGCD_REGIST_COMPLETE, null, redirectAttributes, model);

        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/questionnaire/?md=list";
    }

    @PostMapping(value = "/registupdate/", params = "doCancel")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/")
    public String doCancel(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           SessionStatus sessionStatus,
                           Model model) {

        return "redirect:/questionnaire/";
    }

    @PostMapping(value = "/registupdate/confirm", params = "doCancelConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doCancelConfirm(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  SessionStatus sessionStatus,
                                  Model model) {

        questionnaireRegistUpdateHelper.toPageForLoad(questionnaireRegistUpdateModel);

        return "questionnaire/registupdate/index";
    }

    /**
     * アンケート設問表示順変更(上)<br/>
     *
     * @return ページクラス
     */
    @PostMapping(value = "/registupdate", params = "doUpQuestion")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doUpQuestion(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {
        questionnaireRegistUpdateHelper.toPageForQuestionChange(questionnaireRegistUpdateModel, true);
        return "questionnaire/registupdate/index";
    }

    /**
     * アンケート設問表示順変更(下)<br/>
     *
     * @return ページクラス
     */
    @PostMapping(value = "/registupdate", params = "doDownQuestion")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/registupdate/index")
    public String doDownQuestion(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 SessionStatus sessionStatus,
                                 Model model) {
        questionnaireRegistUpdateHelper.toPageForQuestionChange(questionnaireRegistUpdateModel, false);
        return "questionnaire/registupdate/index";
    }
}

