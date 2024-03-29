package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.validation.GoodsValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 商品検索コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/goods")
@Controller
@SessionAttributes(value = "goodsModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class GoodsController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 商品検索：デフォルトページ番号
     */
    private static final String DEFAULT_GOODSSEARCH_PNUM = "1";

    /**
     * 商品検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_GOODSSEARCH_ORDER_FIELD = "goodsGroupCode";

    /**
     * 商品検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_GOODSSEARCH_ORDER_ASC = true;

    /**
     * 商品検索ヘルパー<br/>
     */
    private final GoodsHelper goodsHelper;

    /**
     * 商品検索結果リスト取得サービス<br/>
     */
    private final GoodsSearchResultListGetService goodsSearchResultListGetService;

    /**
     * 商品検索CSV一括ダウンロードサービス<br/>
     */
    private final GoodsAllCsvDownloadService goodsAllCsvDownloadService;

    /**
     * 商品検索CSVダウンロードサービス<br/>
     */
    private final GoodsCsvDownloadService goodsCsvDownloadService;

    /**
     * カテゴリリスト取得
     */
    private final CategoryListGetService categoryListGetService;

    /**
     * 分類リスト取得サービス
     */
    private final DivisionMapGetService divisionMapGetService;

    /**
     * 商品検索一覧の動的バリデータ
     */
    private final GoodsValidator goodsValidator;

    /**
     * コンストラクター
     *
     * @param goodsHelper
     * @param goodsSearchResultListGetService
     * @param categoryListGetService
     * @param divisionMapGetService
     * @param goodsValidator
     */
    @Autowired
    public GoodsController(GoodsHelper goodsHelper,
                           GoodsSearchResultListGetService goodsSearchResultListGetService,
                           CategoryListGetService categoryListGetService,
                           DivisionMapGetService divisionMapGetService,
                           GoodsAllCsvDownloadService goodsAllCsvDownloadService,
                           GoodsCsvDownloadService goodsCsvDownloadService,
                           GoodsValidator goodsValidator) {
        this.goodsHelper = goodsHelper;
        this.goodsSearchResultListGetService = goodsSearchResultListGetService;
        this.categoryListGetService = categoryListGetService;
        this.divisionMapGetService = divisionMapGetService;
        this.goodsAllCsvDownloadService = goodsAllCsvDownloadService;
        this.goodsCsvDownloadService = goodsCsvDownloadService;
        this.goodsValidator = goodsValidator;
    }

    @InitBinder(value = "goodsModel")
    public void initBinder(WebDataBinder error) {
        // 商品検索一覧の動的バリデータをセット
        error.addValidators(goodsValidator);
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param md
     * @param goodsModel
     * @param model
     * @return
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md, GoodsModel goodsModel, Model model) {

        // サブアプリケーション内の情報を初期化
        goodsModel.setInputingFlg(false);

        // カテゴリパスを保持するマップを作成
        this.setCategoryPathMap(goodsModel);

        // 再検索の場合
        if (md.isPresent() || model.containsAttribute(FLASH_MD)) {
            // 再検索を実行
            search(goodsModel, model);
        } else {
            clearModel(GoodsModel.class, goodsModel, model);
        }

        // プルダウンアイテム情報を取得
        initDropDownValue(goodsModel);

        return "goods/index";
    }

    /**
     * 新規登録画面遷移イベント<br/>
     *
     * @param sessionStatus
     * @param model
     * @return 商品登録更新(基本情報設定)画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doRegist")
    public String doRegist(SessionStatus sessionStatus, Model model) {
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/registupdate?from=menu";
    }

    /**
     * 商品管理 商品一括アップロード遷移イベント(Csv)
     *
     * @param sessionStatus
     * @param model
     * @return 商品管理 商品一括アップロード画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doBundledUpload")
    public String doBundledUpload(SessionStatus sessionStatus, Model model) {
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/bundledupload/csv/";
    }

    /**
     * 商品管理 商品一括アップロード遷移イベント(Image)
     *
     * @param sessionStatus
     * @param model
     * @return 商品管理 商品一括アップロード画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doBundledImageUpload")
    public String doBundledImageUpload(SessionStatus sessionStatus, Model model) {
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/bundledupload/image/";
    }

    /**
     * 検索イベント<br/>
     *
     * @param goodsModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doGoodsSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/index")
    public String doGoodsSearch(@Validated(SearchGroup.class) GoodsModel goodsModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            return "goods/index";
        }
        // 初期ソートと1ページをセット
        goodsModel.setOrderField(DEFAULT_GOODSSEARCH_ORDER_FIELD);
        goodsModel.setOrderAsc(DEFAULT_GOODSSEARCH_ORDER_ASC);
        goodsModel.setPageNumber(DEFAULT_GOODSSEARCH_PNUM);

        // 検索
        search(goodsModel, model);

        return "goods/index";
    }

    /**
     * カテゴリパスを保持するマップを作成<br/>
     *
     * @param goodsModel GoodsModel
     */
    protected void setCategoryPathMap(GoodsModel goodsModel) {

        // 検索条件
        CategorySearchForDaoConditionDto condition =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);

        // ソート順（カテゴパス）固定
        // ※カテゴリの検索ではページング制御不要のため、PageInfoは使わない（AbstractConditionDtoの継承はしない）
        condition.setOrderField("categorypath");
        condition.setOrderAsc(true);

        // 検索
        List<CategoryEntity> list = categoryListGetService.execute(condition, HTypeSiteType.BACK);

        // 結果設定
        goodsHelper.setCategoryPathMap(goodsModel, list);
    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) GoodsModel goodsModel,
                                  BindingResult error,
                                  Model model) {

        if (error.hasErrors()) {
            return "goods/index";
        }

        // 検索結果チェック
        resultListCheck(goodsModel);

        // 検索条件作成
        search(goodsModel, model);

        return "goods/index";
    }

    /**
     * 検索処理<br/>
     */
    protected void search(GoodsModel goodsModel, Model model) {

        // カテゴリパスがnullの場合、マップを作成
        if (goodsModel.getCategoryPathMap() == null) {
            this.setCategoryPathMap(goodsModel);
        }

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto goodsSearchForBackDaoConditionDto =
                        goodsHelper.toGoodsSearchForBackDaoConditionDtoForSearch(goodsModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(goodsSearchForBackDaoConditionDto, goodsModel.getPageNumber(),
                                     goodsModel.getLimit(), goodsModel.getOrderField(), goodsModel.isOrderAsc()
                                    );

        // 取得
        List<GoodsSearchResultDto> goodsSearchResultDtoList =
                        goodsSearchResultListGetService.execute(goodsSearchForBackDaoConditionDto, HTypeSiteType.BACK);

        // ページにセット
        goodsHelper.toPageForSearch(goodsSearchResultDtoList, goodsSearchForBackDaoConditionDto, goodsModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(goodsSearchForBackDaoConditionDto, goodsModel);
    }

    /**
     * CSVダウンロードイベント<br/>
     *
     * @param goodsModel
     * @param error
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadAll")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/index")
    public void doCsvDownloadAll(@Validated(AllDownloadGroup.class) GoodsModel goodsModel,
                                 BindingResult error,
                                 HttpServletResponse response,
                                 Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // ブラウザバック対応 - カテゴリパスがnullの場合、マップを作成
        if (goodsModel.getCategoryPathMap() == null) {
            this.setCategoryPathMap(goodsModel);
        }

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto goodsSearchForBackDaoConditionDto =
                        goodsHelper.toGoodsSearchForBackDaoConditionDtoForSearch(goodsModel);

        // 検索条件に基づいて会員CSV一括出力
        try {
            goodsAllCsvDownloadService.execute(goodsSearchForBackDaoConditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSVダウンロードイベント(検索結果上のボタン)
     *
     * @param goodsModel
     * @param error
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadSelectTop")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/index")
    public void doCsvDownloadSelectTop(@Validated(DownloadTopGroup.class) GoodsModel goodsModel,
                                       BindingResult error,
                                       HttpServletResponse response,
                                       Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        try {
            csvDownloadSelect(goodsModel, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSVダウンロードイベント(検索結果下のボタン)
     *
     * @param goodsModel
     * @param error
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadSelectBottom")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/index")
    public void doCsvDownloadSelectBottom(@Validated(DownloadBottomGroup.class) GoodsModel goodsModel,
                                          BindingResult error,
                                          HttpServletResponse response,
                                          Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        try {
            csvDownloadSelect(goodsModel, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSV選択ダウンロード<br/>
     *
     * @param goodsModel
     * @param response
     */
    protected void csvDownloadSelect(GoodsModel goodsModel, HttpServletResponse response) throws IOException {

        // 検索結果チェック
        resultListCheck(goodsModel);

        // チェックボックスから、チェックされた商品を取得する。
        List<Integer> goodsSeqList = goodsHelper.toGoodsSeqList(goodsModel);

        // チェック選択なし
        if (goodsSeqList.isEmpty()) {
            throwMessage("AGG000102");
        }

        // 検索条件に基づいて会員CSV一括出力
        goodsCsvDownloadService.execute(goodsSeqList, response);

    }

    /**
     * 商品検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    protected void resultListCheck(GoodsModel goodsModel) {
        if (!goodsModel.isResult() || goodsModel.getResultItems().size() == 0) {
            return;
        }
        if (StringUtil.isEmpty(goodsModel.getResultItems().get(0).getGoodsGroupCode())) {
            goodsModel.setResultItems(null);
            this.throwMessage("AGG000103");
        }
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param goodsModel 商品検索モデル
     */
    protected void initDropDownValue(GoodsModel goodsModel) {

        // プルダウンアイテム情報を取得
        goodsModel.setGoodsOpenStatusItems(EnumTypeUtil.getEnumMap(HTypeOpenDeleteStatus.class));
        goodsModel.setGoodsSaleStatusItems(EnumTypeUtil.getEnumMap(HTypeGoodsSaleStatus.class));
        goodsModel.setGoodsOutDataAllItems(EnumTypeUtil.getEnumMap(HTypeGoodsOutData.class));
        goodsModel.setSearchCategoryIdItems(divisionMapGetService.getCategoryMapList());

    }
}
