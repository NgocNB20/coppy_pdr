/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.news.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.news.registupdate.validation.NewsRegistUpdateValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsViewableMemberCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsViewableMemberDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsViewableMemberRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.NewsDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.NewsRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.NewsUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/news")
@Controller
@SessionAttributes(value = "newsRegistUpdateModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class NewsRegistUpdateController extends AbstractController {

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASN000102";
    /**
     * メッセージコード：更新中データ削除
     */
    protected static final String MSGCD_DATA_NOT_EXIST = "ASN000105";
    /**
     * メッセージコード：バリデーション失敗
     */
    protected static final String MSGCD_CSV_INVALID = "PKG-3642-001-A-";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 遷移元画面_確認画面
     */
    protected static final String FROM_VIEW_CONFIRM = "confirm";

    /**
     * helper
     */
    private final NewsRegistUpdateHelper newsRegistUpdateHelper;

    /**
     * ニュース取得サービス
     */
    private final NewsDetailsGetService newsGetService;

    /**
     * ニュース詳細情報取得Logic
     */
    private final NewsGetLogic newsGetLogic;

    /**
     * ニュース表示対象会員件数取得ロジック
     */
    private final NewsViewableMemberCountGetLogic newsViewableMemberCountGetLogic;

    /**
     * ニュース登録サービス
     */
    private final NewsRegistService newsRegistService;

    /**
     * ニュース更新サービス
     */
    private final NewsUpdateService newsUpdateService;

    /**
     * ニュース表示対象会員登録ロジック
     */
    private final NewsViewableMemberRegistLogic newsViewableMemberRegistLogic;

    /**
     * ニュース表示対象会員削除ロジック
     */
    private final NewsViewableMemberDeleteLogic newsViewableMemberDeleteLogic;

    /**
     * 登録更新の動的バリデーション
     */
    private final NewsRegistUpdateValidator newsRegistUpdateValidator;

    @Autowired
    public NewsRegistUpdateController(NewsRegistUpdateHelper newsRegistUpdateHelper,
                                      NewsDetailsGetService newsGetService,
                                      NewsGetLogic newsGetLogic,
                                      NewsViewableMemberCountGetLogic newsViewableMemberCountGetLogic,
                                      NewsRegistService newsRegistService,
                                      NewsUpdateService newsUpdateService,
                                      NewsViewableMemberRegistLogic newsViewableMemberRegistLogic,
                                      NewsViewableMemberDeleteLogic newsViewableMemberDeleteLogic,
                                      NewsRegistUpdateValidator newsRegistUpdateValidator) {
        this.newsRegistUpdateHelper = newsRegistUpdateHelper;
        this.newsGetService = newsGetService;
        this.newsGetLogic = newsGetLogic;
        this.newsViewableMemberCountGetLogic = newsViewableMemberCountGetLogic;
        this.newsRegistService = newsRegistService;
        this.newsUpdateService = newsUpdateService;
        this.newsViewableMemberRegistLogic = newsViewableMemberRegistLogic;
        this.newsViewableMemberDeleteLogic = newsViewableMemberDeleteLogic;
        this.newsRegistUpdateValidator = newsRegistUpdateValidator;
    }

    @InitBinder(value = "newsRegistUpdateModel")
    public void initBinder(WebDataBinder error) {
        // 登録更新の動的バリデータをセット
        error.addValidators(newsRegistUpdateValidator);
    }

    /**
     * 初期表示用メソッド
     *
     * @param newsSeq               RequestParam
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param redirectAttributes
     * @param model                 Model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/registupdate/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<Integer> newsSeq,
                              NewsRegistUpdateModel newsRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // プルダウンアイテム情報を取得
        initDropDownValue(newsRegistUpdateModel);

        NewsEntity newsEntity = null;

        // 確認画面からの遷移でなければ
        if (!newsRegistUpdateModel.isFromConfirm()) {
            // モデルのクリア処理
            clearModel(NewsRegistUpdateModel.class, newsRegistUpdateModel, model);
            initDropDownValue(newsRegistUpdateModel);

            if (newsSeq.isPresent()) {
                // 更新の場合再検索フラグをセット
                newsEntity = newsGetService.execute(newsSeq.get());
                if (newsEntity == null) {
                    addMessage(NewsRegistUpdateModel.MSGCD_NEWS_GET_FAIL, redirectAttributes, model);
                    return "redirect:/error";
                }
                // 変更前情報
                newsRegistUpdateModel.setOriginalNewsEntity(CopyUtil.deepCopy(newsEntity));
                newsRegistUpdateModel.setViewableMemberCount(newsViewableMemberCountGetLogic.execute(newsSeq.get()));
            } else {
                newsEntity = ApplicationContextUtility.getBean(NewsEntity.class);
                newsRegistUpdateModel.setOriginalNewsEntity(null);
                newsRegistUpdateModel.setViewableMemberCount(0);
            }
            newsRegistUpdateModel.setViewableMemberList(null);

        }

        // 画面へ反映
        newsRegistUpdateHelper.toPageForLoad(newsRegistUpdateModel, newsEntity);

        // 修正画面の場合、画面用ニュースSEQを設定
        if (newsRegistUpdateModel.getNewsEntity() != null
            && newsRegistUpdateModel.getNewsEntity().getNewsSeq() != null) {
            newsRegistUpdateModel.setScNewsSeq(newsRegistUpdateModel.getNewsEntity().getNewsSeq());
        }

        // フラグリセット
        newsRegistUpdateModel.setFromConfirm(false);

        // 実行前処理
        String check = preDoAction(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // ニュースデータ存在チェック
        if (hasErrorMessage()) {
            throwMessage();
        }

        return "news/registupdate/index";
    }

    /**
     * ニュース登録更新確認画面へ遷移
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param error                 BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @param model                 Model
     * @return ニュース登録更新確認画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/registupdate/index")
    public String doConfirm(@Validated(ConfirmGroup.class) NewsRegistUpdateModel newsRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 実行前処理
        String check = preDoAction(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "news/registupdate/index";
        }

        // 不正操作チェック
        if (!newsRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/news/";
        }

        // ニュースデータ存在チェック
        // preDoActionのニュースデータ存在チェックで、メッセージが設定される
        if (hasErrorMessage()) {
            throwMessage();
        }

        // 入力チェック
        if (inputCheck(newsRegistUpdateModel, model, redirectAttributes)) {
            return "news/registupdate/index";
        }

        // 時刻追記
        newsRegistUpdateHelper.toPageForConfirm(newsRegistUpdateModel);

        // 問題がない場合、確認画面へ
        return "redirect:/news/registupdate/confirm";
    }

    /**
     * 確認画面：初期表示用
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param redirectAttributes
     * @param model                 Model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @GetMapping(value = "/registupdate/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/news/")
    public String doLoadConfirm(NewsRegistUpdateModel newsRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // ブラウザバックの場合、処理しない
        if (newsRegistUpdateModel.getNewsEntity() == null) {
            return "redirect:/news/";
        }

        // 実行前処理
        String check = preDoAction(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 入力値整合性チェック
        if (this.hasErrorInput(newsRegistUpdateModel)) {
            // 整合性が取れない場合、不正遷移と見なして検索画面へ
            return "redirect:/news/";
        }

        // 修正の場合、画面用ニュースSEQを設定
        if (newsRegistUpdateModel.getNewsEntity().getNewsSeq() != null) {
            newsRegistUpdateModel.setScNewsSeq(newsRegistUpdateModel.getNewsEntity().getNewsSeq());

            // 入力値からエンティティを作成（変更後データ）
            NewsEntity modifiedNewsEntity = newsRegistUpdateHelper.toNewsEntityForNewsRegist(newsRegistUpdateModel);

            // 変更前データと変更後データの差異リスト作成
            List<String> modifiedList =
                            DiffUtil.diff(newsRegistUpdateModel.getOriginalNewsEntity(), modifiedNewsEntity);
            newsRegistUpdateModel.setModifiedList(modifiedList);
        }

        return "news/registupdate/confirm";
    }

    /**
     * 登録更新処理
     * 成功時、ニュース検索画面へ遷移
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param error                 BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @param sessionStatus         SessionStatus
     * @param model                 Model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/registupdate/confirm", params = "doOnceNewsRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/registupdate/confirm")
    public String doOnceNewsRegist(NewsRegistUpdateModel newsRegistUpdateModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   SessionStatus sessionStatus,
                                   Model model) {

        // 実行前処理
        String check = preDoAction(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 入力値整合性チェック
        if (this.hasErrorInput(newsRegistUpdateModel)) {
            // 整合性が取れない場合、不正遷移と見なして検索画面へ
            return "redirect:/news/";
        }

        // 画面入力値をエンティティに反映
        NewsEntity newsEntity = newsRegistUpdateHelper.toNewsEntityForNewsRegist(newsRegistUpdateModel);

        // 登録時
        if (newsRegistUpdateModel.getNewsEntity().getNewsSeq() == null) {
            newsRegistService.execute(newsEntity);
        } else {
            // 更新時
            newsUpdateService.execute(newsEntity);

            if (newsRegistUpdateModel.getViewableMemberList() != null) {
                newsViewableMemberDeleteLogic.execute(newsEntity.getNewsSeq());
            }

            // 再検索フラグをセット
            redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        }

        if (CollectionUtil.isNotEmpty(newsRegistUpdateModel.getViewableMemberList())) {
            newsViewableMemberRegistLogic.execute(
                            newsEntity.getNewsSeq(), newsRegistUpdateModel.getViewableMemberList());
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/news/";
    }

    /**
     * キャンセル
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param redirectAttributes
     * @param model                 Model
     * @return
     */
    @PostMapping(value = "/registupdate/confirm", params = "doCancel")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/registupdate/confirm")
    public String doCancel(NewsRegistUpdateModel newsRegistUpdateModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // 実行前処理
        String check = preDoAction(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        newsRegistUpdateModel.setFromConfirm(true);

        return "redirect:/news/registupdate";
    }

    /**
     * 初期表示用メソッド
     *
     * @param newsSeq               RequestParam
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param redirectAttributes
     * @param model                 Model
     * @return
     */
    @GetMapping(value = "/registupdate/preview")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/index")
    public String doLoadPreview(@RequestParam(required = false) Optional<String> fromView,
                                @RequestParam(required = false) Optional<Integer> newsSeq,
                                NewsRegistUpdateModel newsRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        if (fromView.isPresent() && FROM_VIEW_CONFIRM.equals(fromView.get())) {
            // 確認画面から遷移時、 セッションから値が取得できない場合は、エラー
            if (newsRegistUpdateModel.getNewsDate() == null) {
                throwMessage(MSGCD_ILLEGAL_OPERATION);
            }
        } else {
            // 検索画面から遷移時、DBよりデータを取得する

            // ニュース情報取得
            NewsEntity newsEntity = newsGetService.execute(newsSeq.get());

            // 取得データを画面へ反映
            newsRegistUpdateHelper.toPageForLoad(newsRegistUpdateModel, newsEntity);
        }

        return "news/previewpc";
    }

    /**
     * ニュース詳細プレビューPC画面
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param redirectAttributes
     * @param model                 Model
     * @return
     */
    @GetMapping(value = "/registupdate/previewpcnews")
    public String doLoadPreviewPc(NewsRegistUpdateModel newsRegistUpdateModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        return "news/previewpcnews";
    }

    /**
     * アクション実行前処理
     *
     * @param newsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    public String preDoAction(NewsRegistUpdateModel newsRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        String returnView = null;

        // 不正操作チェック
        returnView = checkIllegalOperation(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        // ニュースデータ存在チェック
        returnView = checkNewsDataExist(newsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        // チェックＯＫ（null返却）
        return returnView;
    }

    /**
     * 不正操作チェック
     *
     * @param newsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return 遷移先画面（チェックNGの場合は遷移先画面パス。チェックOK時はNULL。）
     */
    protected String checkIllegalOperation(NewsRegistUpdateModel newsRegistUpdateModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer scNewsSeq = newsRegistUpdateModel.getScNewsSeq();
        Integer dbNewsSeq = null;
        if (newsRegistUpdateModel.getNewsEntity() != null) {
            dbNewsSeq = newsRegistUpdateModel.getNewsEntity().getNewsSeq();
        }

        boolean isError = false;

        // 登録画面にも関わらず、ニュースSEQのDB情報を保持している場合エラー
        if (scNewsSeq == null && dbNewsSeq != null) {
            isError = true;

            // 修正画面にも関わらず、ニュースSEQのDB情報を保持していない場合エラー
        } else if (scNewsSeq != null && dbNewsSeq == null) {
            isError = true;

            // 画面用ニュースSEQとDB用ニュースSEQが異なる場合エラー
        } else if (scNewsSeq != null && !scNewsSeq.equals(dbNewsSeq)) {
            isError = true;
        }

        if (isError) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/news/";
        }

        return null;
    }

    /**
     * ニュースデータ存在チェック<br />
     * ニュース更新時に該当ニュースが削除されていた場合、エラーメッセージを登録する
     *
     * @param newsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return 遷移先画面（チェックNGの場合は遷移先画面パス。チェックOK時はNULL。）
     */
    protected String checkNewsDataExist(NewsRegistUpdateModel newsRegistUpdateModel,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        Integer newsSeq = newsRegistUpdateModel.getNewsEntity().getNewsSeq();
        if (newsSeq == null) {
            // 新規登録処理の場合（ニュースSEQを保持していない場合）、チェック終了
            return null;
        }
        // 編集中ニュース取得
        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        NewsEntity newsEntity = newsGetLogic.execute(shopSeq, newsSeq);
        if (newsEntity == null) {
            // 編集中ニュースが削除されている場合、エラー
            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
            addMessage(MSGCD_DATA_NOT_EXIST, new Object[] {appComplementUrl}, redirectAttributes, model);
            return "news/registupdate/index";
        }
        return null;
    }

    /**
     * 必須保持値・入力項目の有無で整合性をチェックする
     *
     * @return true:エラーあり false:エラーなし
     */
    private boolean hasErrorInput(NewsRegistUpdateModel newsRegistUpdateModel) {
        NewsEntity newsEntity = newsRegistUpdateModel.getNewsEntity();
        if (newsEntity == null) {
            return true;
        }

        // 必須項目
        if (newsRegistUpdateModel.getNewsDate() == null || newsRegistUpdateModel.getNewsOpenStatusPC() == null
            || newsRegistUpdateModel.getNewsOpenStatusMB() == null) {

            return true;
        }

        return false;
    }

    /**
     * 入力チェック<br/>
     *
     * @return boolean PC でも MB でもない時、false を返却
     */
    private boolean inputCheck(NewsRegistUpdateModel newsRegistUpdateModel,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        boolean isError = false;
        // ニュース詳細、ニュースURL同時設定不可チェック（PC）
        if (!StringUtil.isEmpty(newsRegistUpdateModel.getNewsNotePC()) && !StringUtil.isEmpty(
                        newsRegistUpdateModel.getNewsUrlPC())) {
            addWarnMessage("ASN000101", new Object[] {"PC"}, redirectAttributes, model);
            isError = true;
        }
        // ニュース詳細、ニュースURL同時設定不可チェック（スマートフォン）
        if (!StringUtil.isEmpty(newsRegistUpdateModel.getNewsNoteSP()) && !StringUtil.isEmpty(
                        newsRegistUpdateModel.getNewsUrlSP())) {
            addWarnMessage("ASN000101", new Object[] {"スマートフォン"}, redirectAttributes, model);
            isError = true;
        }
        // ニュース詳細、ニュースURL同時設定不可チェック（モバイル）
        if (!StringUtil.isEmpty(newsRegistUpdateModel.getNewsNoteMB()) && !StringUtil.isEmpty(
                        newsRegistUpdateModel.getNewsUrlMB())) {
            addWarnMessage("ASN000101", new Object[] {"モバイル"}, redirectAttributes, model);
            isError = true;
        }
        return isError;
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param newsRegistUpdateModel フリーエリア登録・更新画面
     */
    private void initDropDownValue(NewsRegistUpdateModel newsRegistUpdateModel) {
        newsRegistUpdateModel.setNewsOpenStatusPCItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
    }
}
