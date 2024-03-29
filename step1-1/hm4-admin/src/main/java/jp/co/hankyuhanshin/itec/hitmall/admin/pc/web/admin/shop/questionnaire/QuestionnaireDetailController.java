package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyTotalEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * アンケート詳細コントローラー<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/questionnaire/details")
@Controller
@SessionAttributes(value = "questionnaireDetailModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class QuestionnaireDetailController extends AbstractController {

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "PKG34-3552-007-A-W";

    /**
     * アンケート情報が取得できなかった場合エラー
     */
    protected static final String MSGCD_DONOT_GET_QUESTIONNAIREDATA = "PKG34-3552-002-A-W";

    /** アンケート詳細ページHelper */
    private final QuestionnaireDetailHelper questionnaireDetailHelper;

    /**
     * アンケート取得ロジック
     */
    private final QuestionnaireGetLogic questionnaireGetLogic;

    /**
     * コンストラクタ
     *
     * @param questionnaireDetailHelper
     * @param questionnaireGetLogic
     */
    @Autowired
    public QuestionnaireDetailController(QuestionnaireDetailHelper questionnaireDetailHelper,
                                         QuestionnaireGetLogic questionnaireGetLogic) {
        this.questionnaireDetailHelper = questionnaireDetailHelper;
        this.questionnaireGetLogic = questionnaireGetLogic;
    }

    /**
     * 画面初期表示時の処理。<br />
     *
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "/questionnaire/details")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> seq,
                              QuestionnaireDetailModel questionnaireDetailModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // モデル初期化
        clearModel(QuestionnaireDetailModel.class, questionnaireDetailModel, model);

        seq.ifPresent(s -> questionnaireDetailModel.setSeq(Integer.parseInt(s)));

        QuestionnaireEntity questionnaireEntity = null;
        List<QuestionDetailsDto> questionDetailsDtoList = null;
        QuestionnaireReplyTotalEntity questionnaireReplyTotalEntity = null;

        Integer questionnaireSeq = questionnaireDetailModel.getSeq();
        if (questionnaireSeq == null) {
            // 不正操作エラー対応
            // アンケートSEQが取得できなかった場合は、検索画面に遷移しエラーメッセージを表示する
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/error";
        }
        // アンケート情報取得
        questionnaireEntity = questionnaireGetLogic.getQuestionnaire(questionnaireSeq);

        // 他のユーザによる削除対応
        // 指定されたアンケートSEQに対応するアンケート・アンケート設問が存在しない場合、検索画面に遷移しエラーメッセージを表示する
        if (questionnaireEntity == null) {
            addMessage(MSGCD_DONOT_GET_QUESTIONNAIREDATA, redirectAttributes, model);
            return "redirect:/error";
        }

        // アンケート設問情報・アンケート設問回答集計情報Dtoリスト取得
        questionDetailsDtoList = questionnaireGetLogic.getDetailsDtoList(questionnaireSeq);

        // アンケート回答集計情報取得
        questionnaireReplyTotalEntity = questionnaireGetLogic.getQuestionnaireReplyTotal(questionnaireSeq);

        // ページに反映
        questionnaireDetailHelper.toPageForLoadQuestionaire(questionnaireEntity, questionnaireDetailModel);
        questionnaireDetailHelper.toPageForLoadQuestion(questionDetailsDtoList, questionnaireDetailModel);
        questionnaireDetailHelper.toPageForLoadQuestionnaireReplyTotal(
                        questionnaireReplyTotalEntity, questionnaireDetailModel);

        return "questionnaire/details";
    }

    /**
     * 戻るイベント<br/>
     *
     * @param questionnaireDetailModel
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return 遷移元
     */
    @PostMapping(value = "/", params = "doBack")
    public String doBack(QuestionnaireDetailModel questionnaireDetailModel,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus,
                         Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/questionnaire/";
    }
}
