/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionReplyEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyDisplayDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionnaireReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.validation.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.questionnaire.validation.QuestionnaireReplyDisplayDtoValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * アンケート Controller
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/questionnaire")
@Controller
@SessionAttributes(value = "questionnaireModel")
public class QuestionnaireController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireController.class);

    /**
     * 注文アンケートキー
     */
    public static final String ORDER_QKEY = "order";

    /**
     * 不正遷移
     */
    public static final String MSGCD_REFERER_FAIL = "AMH000201";

    /**
     * アンケートHelper
     */
    public final QuestionnaireHelper questionnaireHelper;

    /**
     * 動的バリデータ
     */
    private final QuestionnaireReplyDisplayDtoValidator questionaireValidator;

    /**
     * ショップApi
     */
    private final ShopApi shopApi;

    /**
     * コンストラク
     *
     * @param questionnaireHelper   アンケートHelper
     * @param shopApi               ショップApi
     * @param questionaireValidator 動的バリデータ
     */
    @Autowired
    public QuestionnaireController(QuestionnaireHelper questionnaireHelper,
                                   QuestionnaireReplyDisplayDtoValidator questionaireValidator,
                                   ShopApi shopApi) {
        this.questionnaireHelper = questionnaireHelper;
        this.questionaireValidator = questionaireValidator;
        this.shopApi = shopApi;
    }

    @InitBinder
    public void initBinder(WebDataBinder error) {
        // アンケート画面の動的バリデータをセット
        error.addValidators(questionaireValidator);
    }

    /**
     * アクション前処理<br/>
     *
     * @param questionnaireModel アンケートModel
     */
    public String preDoAction(QuestionnaireModel questionnaireModel) {
        List<QuestionnaireReplyDisplayDto> items = questionnaireModel.getQuestionnaireReplyDisplayDtoItems();

        if (items == null || items.size() < 1 || items.get(0).getDisplayNumber() == null) {
            // 設問リストがセッションにない場合、エラー画面に遷移
            return "redirect:/error.html";
        }

        return null;
    }

    /**
     * アンケート画面：初期処理
     *
     * @param questionnaireModel アンケートModel
     * @param model              Model
     * @param redirectAttributes RedirectAttributes
     * @return アンケート画面
     */
    @GetMapping(value = {"", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/index")
    protected String doLoadIndex(@RequestParam(required = false) String qkey,
                                 QuestionnaireModel questionnaireModel,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        // URLパラメータを設定
        questionnaireModel.setQkey(qkey);

        // アンケート回答確認画面からの遷移の場合 セッション情報を表示
        if (questionnaireModel.isEdit()) {
            questionnaireHelper.toPageForReLoad(questionnaireModel);
            questionnaireModel.setEdit(false);
            return "questionnaire/index";
        }

        // 注文アンケートのアンケートキーが指定された場合はエラー画面に遷移
        if (ORDER_QKEY.equals(questionnaireModel.getQkey())) {
            return "redirect:/error.html";
        }

        // アンケート情報取得
        QuestionnaireEntity questionnaireEntity = null;
        if (StringUtil.isNotEmpty(questionnaireModel.getQkey())) {
            QuestionnaireEntityResponse questionnaireEntityResponse = null;
            try {
                questionnaireEntityResponse = shopApi.getByQuestionnaireKey(questionnaireModel.getQkey());
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            questionnaireEntity = questionnaireHelper.toQuestionnaireEntity(questionnaireEntityResponse);
        }

        // アンケート情報の取得に失敗した場合エラー（アンケートが非公開状態/公開終了/削除/存在しない場合）
        if (questionnaireEntity == null) {
            LOGGER.debug("存在しない、もしくは公開されていないアンケートが選択されました");
            return "redirect:/error.html";
        }

        // アンケート設問リストからアンケート回答画面表示用DTOリスト作成
        // 第1引数:アンケートSEQ
        QuestionnaireReplyDisplayDtoListResponse questionnaireReplyDisplayDtoListResponse = null;
        try {
            questionnaireReplyDisplayDtoListResponse =
                            shopApi.getByQuestionnaireSeq(questionnaireEntity.getQuestionnaireSeq());
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        List<QuestionnaireReplyDisplayDto> questionDtoList = null;
        try {
            questionDtoList = questionnaireHelper.toQuestionnaireReplyDisplayDtoList(
                            questionnaireReplyDisplayDtoListResponse.getQuestionnaireReplyDisplayDtoResponse());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        // 公開可能なアンケート設問リストが空の場合エラー（非公開状態/削除/存在しない場合）
        if (questionDtoList.size() < 1) {
            return "redirect:/error.html";
        }

        // 取得したアンケート情報、アンケート設問情報を画面にセット
        questionnaireHelper.toPageForLoad(questionnaireEntity, questionDtoList, questionnaireModel);

        // 実行前処理
        String check = preDoAction(questionnaireModel);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "questionnaire/index";
    }

    /**
     * アンケート画面：問い合わせ確認画面に遷移
     *
     * @param questionnaireModel アンケートModel
     * @param error              BindingResult
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return アンケート
     */
    // PDR Migrate Customization from here
    @PostMapping(value = {"/", "/index.html"}, params = "doConfirm")
    // PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/index")
    public String doConfirm(@Validated(ConfirmGroup.class) QuestionnaireModel questionnaireModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (error.hasErrors()) {
            return "questionnaire/index";
        }

        // 異なるキーを持ったアンケートの回答画面からブラウザバックより遷移を戻し、確認ボタンを押下した場合エラー
        if (!questionnaireModel.getQuestionnaireEntity()
                               .getQuestionnaireSeq()
                               .equals(questionnaireModel.getQuestionnaireSeq())) {
            return "redirect:/error.html";
        }

        // アンケート情報が回答可能である（非公開状態/公開終了/削除/存在しないのいずれでもない）ことをチェックする
        // 第1引数:アンケートSEQ
        // 第2引数:モバイルではないのでfalse。（取得条件としてカラムopenStatusPcを参照する）
        QuestionnaireCheckRequest questionnaireCheckRequest = new QuestionnaireCheckRequest();
        questionnaireCheckRequest.setQuestionnaireSeq(questionnaireModel.getQuestionnaireSeq());

        ResultFlagResponse resultFlagResponse = null;
        try {
            resultFlagResponse = shopApi.checkQuestionnaire(questionnaireCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        if (!resultFlagResponse.getResultFlag()) {
            LOGGER.debug("申し訳ございませんが、受付は終了しております。");
            return "redirect:/error.html";
        }
        // ページ情報をエンティティにセット
        questionnaireHelper.toPageForConfirm(questionnaireModel);

        // 確認画面へ遷移
        return "redirect:/questionnaire/confirm.html";
    }

    /**
     * 注文内容確認画面：初期処理
     *
     * @param questionnaireModel アンケートModel
     * @param model              BindingResult
     * @param redirectAttributes RedirectAttributes
     * @return 注文内容確認画面
     */
    @GetMapping(value = {"/confirm", "/confirm.html"})
    protected String doLoadConfirm(QuestionnaireModel questionnaireModel,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {

        // 不正遷移チェック 必須項目の有無でエラーページへ遷移
        if (questionnaireModel.getQuestionnaireReplyDisplayDtoItems() == null) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        // 自画面表示
        return "/questionnaire/confirm";
    }

    /**
     * ２重サブミットの防止<br />
     * 確認画面：DBに登録内容の登録<br />
     * 問い合わせ完了画面への遷移
     *
     * @param questionnaireModel アンケートModel
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return 問い合わせ完了画面
     */
    // TODO-QUAD-1027
    // @TakeOver(type = TakeOverType.INCLUDE, properties = "namePc, completeTextPc, completeTextSp, point")
    @PostMapping(value = {"/", "/index.html"}, params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/regist")
    public String doOnceRegist(QuestionnaireModel questionnaireModel,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // アンケート回答情報の作成
        QuestionnaireReplyEntity questionnaireReplyEntity =
                        questionnaireHelper.toQuestionnaireReplyEntityForRegist(questionnaireModel);
        List<QuestionReplyEntity> questionReplyEntityList =
                        questionnaireHelper.toQuestionReplyEntityForRegist(questionnaireModel);

        QuestionnaireReplyEntityRequest questionnaireReplyEntityRequest =
                        questionnaireHelper.toQuestionnaireReplyEntityRequest(questionnaireReplyEntity);
        List<QuestionReplyEntityRequest> questionReplyEntityListRequest =
                        questionnaireHelper.toQuestionReplyEntityListRequest(questionReplyEntityList);

        QuestionnaireRegistRequest questionnaireRegistRequest = new QuestionnaireRegistRequest();
        questionnaireRegistRequest.setQuestionnaireReplyEntityRequest(questionnaireReplyEntityRequest);
        questionnaireRegistRequest.setQuestionReplyEntityRequest(questionReplyEntityListRequest);
        try {
            shopApi.registQuestionnaire(questionnaireRegistRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 完了画面に引き継ぐため、セッションエンティティからPageのフィールドにセット
        questionnaireModel.setNamePc(questionnaireModel.getQuestionnaireEntity().getNamePc());
        questionnaireModel.setCompleteTextPc(questionnaireModel.getQuestionnaireEntity().getCompleteTextPc());

        // メルマガ登録完了画面へ遷移
        return "redirect:/questionnaire/complete.html";
    }

    /**
     * 完了画面：初期処理
     *
     * @param questionnaireModel アンケートModel
     * @param sessionStatus      SessionStatus
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return 登録完了画面
     */
    @GetMapping(value = {"/complete", "/complete.html"})
    protected String doLoadComplete(QuestionnaireModel questionnaireModel,
                                    SessionStatus sessionStatus,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "questionnaire/complete";
    }

    /**
     * 戻るイベント<br/>
     *
     * @param questionnaireModel アンケートModel
     * @return 遷移元
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doBack")
    public String doBack(QuestionnaireModel questionnaireModel, RedirectAttributes redirectAttributes, Model model) {

        questionnaireModel.setEdit(true);

        return "redirect:/questionnaire/index.html";
    }

}
