package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.validation.TargetDateTimeValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.validation.group.FreeAreaSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeAreaOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea.FreeAreaSearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * フリーエリア検索コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/freearea")
@Controller
@SessionAttributes(value = "freeareaModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class FreeareaController extends AbstractController {

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDER_FIELD = "freeAreaKey";

    /**
     * デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDER_ASC = true;

    /**
     * フリーエリア削除成功メッセージコード<br/>
     * <code>MSGCD_FREEAREA_DELETE_SUCCESS</code>
     */
    public static final String MSGCD_FREEAREA_DELETE_SUCCESS = "ASF000102I";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * フリーエリア削除失敗メッセージコード<br/>
     * <code>MSGCD_FREEAREA_DELETE_FAIL</code>
     */
    public static final String MSGCD_FREEAREA_DELETE_FAIL = "ASF000103W";

    /**
     * helper
     */
    private final FreeareaHelper freeareaHelper;

    /**
     * フリーエリアリスト取得サービス
     */
    private final FreeAreaListGetService freeAreaListGetService;

    /**
     * フリーエリア取得ロジック
     */
    private final FreeAreaGetLogic freeAreaGetLogic;

    /**
     * フリーエリア削除ロジック
     */
    private final FreeAreaDeleteLogic freeAreaDeleteLogic;

    /**
     * 表示状態-日付（日、時間）
     */
    private final TargetDateTimeValidator targetDateTimeValidator;

    /**
     * コンストラクタ
     *
     * @param freeareaHelper
     * @param freeAreaListGetService
     * @param freeAreaGetLogic
     * @param freeAreaDeleteLogic
     * @param targetDateTimeValidator
     */
    @Autowired
    public FreeareaController(FreeareaHelper freeareaHelper,
                              FreeAreaListGetService freeAreaListGetService,
                              FreeAreaGetLogic freeAreaGetLogic,
                              FreeAreaDeleteLogic freeAreaDeleteLogic,
                              TargetDateTimeValidator targetDateTimeValidator) {
        this.freeareaHelper = freeareaHelper;
        this.freeAreaListGetService = freeAreaListGetService;
        this.freeAreaGetLogic = freeAreaGetLogic;
        this.freeAreaDeleteLogic = freeAreaDeleteLogic;
        this.targetDateTimeValidator = targetDateTimeValidator;
    }

    @InitBinder(value = "freeareaModel")
    public void initBinder(WebDataBinder error) {
        // 登録更新の動的バリデータをセット
        error.addValidators(targetDateTimeValidator);
    }

    /**
     *
     *
     *
     * @return 自画面
     */
    /**
     * リスト画面：初期表示
     *
     * @param freeareaModel フリーエリア検索モデル
     * @param model         Model
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              FreeareaModel freeareaModel,
                              Model model) {

        // 再検索の場合
        if (model.containsAttribute(FLASH_MD) || (md.isPresent())) {
            // 再検索を実行
            search(freeareaModel, model);
        } else {
            clearModel(FreeareaModel.class, freeareaModel, model);
        }
        // プルダウンアイテム情報を取得
        initComponentValue(freeareaModel);
        return "freearea/index";
    }

    /**
     * 表示順変更
     *
     * @param freeareaModel フリーエリア検索モデル
     * @param error
     * @param model         Model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) FreeareaModel freeareaModel,
                                  BindingResult error,
                                  Model model) {

        if (error.hasErrors()) {
            return "freearea/index";
        }

        // 検索
        this.search(freeareaModel, model);
        return "freearea/index";
    }

    /**
     * フリーエリア検索
     *
     * @param freeareaModel      フリーエリア検索モデル
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doFreeAreaSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/index")
    public String doFreeAreaSearch(@Validated(FreeAreaSearchGroup.class) FreeareaModel freeareaModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (error.hasErrors()) {
            return "freearea/index";
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        freeareaModel.setPageNumber(DEFAULT_PNUM);
        freeareaModel.setOrderField(DEFAULT_ORDER_FIELD);
        freeareaModel.setOrderAsc(DEFAULT_ORDER_ASC);

        // 検索
        search(freeareaModel, model);
        return "freearea/index";
    }

    /**
     * フリーエリア削除
     *
     * @param freeareaModel フリーエリア検索モデル
     * @param redirectAttrs
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doFreeAreaDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/index")
    public String doFreeAreaDelete(FreeareaModel freeareaModel, RedirectAttributes redirectAttrs, Model model) {
        // 共通情報の取得
        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        // 削除対象フリーエリア情報取得
        FreeAreaEntity entity = freeAreaGetLogic.execute(shopSeq, freeareaModel.getDeleteFreeAreaSeq());

        int result = 0;
        if (entity != null) {
            result = freeAreaDeleteLogic.execute(entity);
        }

        // 削除失敗（削除済み）
        if (result == 0) {
            // 削除失敗メッセージ登録
            addMessage(MSGCD_FREEAREA_DELETE_FAIL, null, redirectAttrs, model);
        } else {
            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
            // 公開開始日を画面表示用に変換
            String openStartTime = dateUtility.format(entity.getOpenStartTime(), DateUtility.YMD_SLASH_HMS);
            // 削除成功メッセージ登録
            addInfoMessage(MSGCD_FREEAREA_DELETE_SUCCESS, new Object[] {entity.getFreeAreaKey(), openStartTime},
                           redirectAttrs, model
                          );
        }

        // 削除後再検索
        this.search(freeareaModel, model);
        return "freearea/index";
    }

    /**
     * フリーエリア登録画面へ遷移
     *
     * @param freeareaModel フリーエリア検索モデル
     * @param model         Model
     * @return フリーエリア登録画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doRegist")
    public String doRegist(FreeareaModel freeareaModel, Model model) {

        return "redirect:/freearea/registupdate";
    }

    /**
     * 検索処理
     *
     * @param freeareaModel
     * @param model
     */
    private void search(FreeareaModel freeareaModel, Model model) {

        // 条件取得
        FreeAreaSearchDaoConditionDto conditionDto =
                        freeareaHelper.toFreeAreaSearchDaoConditionDtoForSearch(freeareaModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, freeareaModel.getPageNumber(), freeareaModel.getLimit(),
                                     freeareaModel.getOrderField(), freeareaModel.isOrderAsc()
                                    );

        // 検索
        List<FreeAreaEntity> freeAreaEntityList = freeAreaListGetService.execute(conditionDto);

        // ページへ反映
        freeareaHelper.toPageForSearch(freeAreaEntityList, freeareaModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, freeareaModel);

        // 件数セット
        freeareaModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param freeareaModel フリーエリア検索モデル
     */
    private void initComponentValue(FreeareaModel freeareaModel) {

        // プルダウンアイテム情報を取得
        freeareaModel.setSearchOpenStateArrayItems(EnumTypeUtil.getEnumMap(HTypeFreeAreaOpenStatus.class));
        freeareaModel.setSearchSiteMapFlagItems(EnumTypeUtil.getEnumMap(HTypeSiteMapFlag.class));

    }
}
