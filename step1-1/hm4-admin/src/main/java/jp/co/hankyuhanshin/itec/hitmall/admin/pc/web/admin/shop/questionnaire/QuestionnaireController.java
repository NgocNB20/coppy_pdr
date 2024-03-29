/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.validation.group.QuestionnaireSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchForBackDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * 受注検索アクション<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/questionnaire")
@Controller
@SessionAttributes(value = "questionnaireModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class QuestionnaireController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireController.class);

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDER_FIELD = "openStartTime";

    /**
     * デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDER_ASC = false;

    /**
     * アンケート削除成功メッセージコード
     */
    protected static final String MSGCD_QUESTIONNAIRE_DELETE_SUCCESS = "PKG34-3552-003-A-I";

    /**
     * アンケート削除失敗メッセージコード
     */
    protected static final String MSGCD_QUESTIONNAIRE_DELETE_FAIL = "PKG34-3552-004-A-W";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * アンケート検索画面Helper
     */
    public final QuestionnaireHelper questionnaireHelper;

    /**
     * アンケート取得ロジック
     */
    public final QuestionnaireGetLogic questionnaireGetLogic;

    /**
     * アンケート更新ロジック
     */
    public final QuestionnaireUpdateLogic questionnaireUpdateLogic;

    /**
     * コンストラクタ
     *
     * @param questionnaireHelper      アンケート検索画面Helper
     * @param questionnaireGetLogic    アンケート取得ロジック
     * @param questionnaireUpdateLogic アンケート更新ロジック
     */
    @Autowired
    public QuestionnaireController(QuestionnaireHelper questionnaireHelper,
                                   QuestionnaireGetLogic questionnaireGetLogic,
                                   QuestionnaireUpdateLogic questionnaireUpdateLogic) {
        this.questionnaireHelper = questionnaireHelper;
        this.questionnaireGetLogic = questionnaireGetLogic;
        this.questionnaireUpdateLogic = questionnaireUpdateLogic;
    }

    /**
     * 初期処理<br/>
     *
     * @param questionnaireModel アンケート検索モデル
     * @param model              the model
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              QuestionnaireModel questionnaireModel,
                              Model model) {

        // 再検索の場合
        if (model.containsAttribute(FLASH_MD) || (md.isPresent())) {

            if (questionnaireModel.getLimit() > 0) {
                // 再検索を実行
                search(questionnaireModel, model);
            }
        } else {
            clearModel(QuestionnaireModel.class, questionnaireModel, model);
            // 回答集計日時の取得
            questionnaireModel.setRegistTime(questionnaireGetLogic.getCurrentRegistTime());
        }

        // 実行前処理
        preDoAction(questionnaireModel);
        // プルダウンアイテム情報を取得
        initComponentValue(questionnaireModel);
        return "questionnaire/index";
    }

    /**
     * Do questionnaire search string.
     *
     * @param questionnaireModel アンケート検索クラス
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doQuestionnaireSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/index")
    public String doQuestionnaireSearch(
                    @Validated(QuestionnaireSearchGroup.class) QuestionnaireModel questionnaireModel,
                    BindingResult error,
                    RedirectAttributes redirectAttributes,
                    Model model) {

        // 実行前処理
        preDoAction(questionnaireModel);

        if (error.hasErrors()) {
            return "questionnaire/index";
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        questionnaireModel.setPageNumber(DEFAULT_PNUM);
        questionnaireModel.setOrderField(DEFAULT_ORDER_FIELD);
        questionnaireModel.setOrderAsc(DEFAULT_ORDER_ASC);

        // 検索
        search(questionnaireModel, model);
        return "questionnaire/index";
    }

    /**
     * 表示順変更
     *
     * @param questionnaireModel アンケート検索モデル
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) QuestionnaireModel questionnaireModel,
                                  BindingResult error,
                                  Model model) {

        // 実行前処理
        preDoAction(questionnaireModel);

        if (error.hasErrors()) {
            return "questionnaire/index";
        }

        // 検索
        this.search(questionnaireModel, model);
        return "questionnaire/index";
    }

    /**
     * フリーエリア削除
     *
     * @param questionnaireModel アンケート検索モデル
     * @param redirectAttrs
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doQuestionnaireDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/index")
    public String doQuestionnaireDelete(QuestionnaireModel questionnaireModel,
                                        RedirectAttributes redirectAttrs,
                                        Model model) {

        // 論理削除対象アンケート情報取得
        Integer deleteQuestionnaireSeq = questionnaireModel.getDeleteQuestionnaireSeq();
        QuestionnaireEntity entity = questionnaireGetLogic.getQuestionnaire(deleteQuestionnaireSeq);
        List<QuestionEntity> entityList = questionnaireGetLogic.getQuestionList(deleteQuestionnaireSeq);

        // 論理削除するため、すべての公開状態に削除（DELETED）をセットする
        logicalDelete(entity, entityList);

        try {
            // 論理削除対象アンケートを更新する
            questionnaireUpdateLogic.execute(entity, entityList);

        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_QUESTIONNAIRE_DELETE_FAIL);
        }

        // 削除成功メッセージ登録
        addInfoMessage(MSGCD_QUESTIONNAIRE_DELETE_SUCCESS, null, redirectAttrs, model);

        // 前回検索条件を設定
        // セッションの検索条件取得
        QuestionnaireSearchForBackDto conditionDto =
                        ApplicationContextUtility.getBean(QuestionnaireSearchForBackDto.class);
        // セッションの検索条件をページに反映
        questionnaireHelper.toPageForLoad(conditionDto, questionnaireModel);

        // 削除後再検索
        this.search(questionnaireModel, model);

        return "questionnaire/index";
    }

    /**
     * アクション実行前に処理結果表示をクリア
     *
     * @param questionnaireModel アンケート検索モデル
     */
    public void preDoAction(QuestionnaireModel questionnaireModel) {
        questionnaireModel.setCheckMessageItems(null);
        if (questionnaireModel.getPageNumber() == null) {
            questionnaireModel.setPageNumber(DEFAULT_PNUM);
        }
    }

    /**
     * アンケート・アンケート設問の論理削除
     *
     * @param questionnaireEntity アンケートエンティティ
     * @param questionEntityList  アンケート設問エンティティリスト
     */
    protected void logicalDelete(QuestionnaireEntity questionnaireEntity, List<QuestionEntity> questionEntityList) {

        // アンケートエンティティ
        questionnaireEntity.setOpenStatusPc(HTypeOpenDeleteStatus.DELETED);

        for (QuestionEntity questionEntity : questionEntityList) {
            questionEntity.setOpenStatus(HTypeOpenDeleteStatus.DELETED);
        }
    }

    /**
     * 検索
     */
    private void search(QuestionnaireModel questionnaireModel, Model model) {

        // 検索条件作成
        QuestionnaireSearchForBackDto conditionDto =
                        questionnaireHelper.toQuestionnaireSearchForBackDaoConditionDtoForSearch(questionnaireModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, questionnaireModel.getPageNumber(), questionnaireModel.getLimit(),
                                     questionnaireModel.getOrderField(), questionnaireModel.isOrderAsc()
                                    );

        // 検索
        List<QuestionnaireSearchResultDto> questionnaireResultList =
                        questionnaireGetLogic.getSearchResultDto(conditionDto);

        // 回答集計日時の取得
        Timestamp maxRegistTime = questionnaireGetLogic.getCurrentRegistTime();

        // ページへ反映
        questionnaireHelper.toPageForSearch(maxRegistTime, questionnaireResultList, questionnaireModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, questionnaireModel);

        // 件数セット
        questionnaireModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param questionnaireModel アンケート検索モデル
     */
    private void initComponentValue(QuestionnaireModel questionnaireModel) {
        // プルダウンアイテム情報を取得
        questionnaireModel.setSearchSiteMapFlagItems(EnumTypeUtil.getEnumMap(HTypeSiteMapFlag.class));
        questionnaireModel.setSearchOpenStatusItems(EnumTypeUtil.getEnumMapWithIgnore(HTypeOpenDeleteStatus.class,
                                                                                      HTypeOpenDeleteStatus.DELETED.getValue()
                                                                                     ));
    }
}
