package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenCategoryPassListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.UkapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品一覧画面 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/goods/")
@Controller
public class GoodsListController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsListController.class);

    /**
     * 商品一覧画面：デフォルトページ番号
     */
    public static final String DEFAULT_PNUM = "1";

    /**
     * 商品一覧画面：１ページ当たりのリスト形式デフォルト最大表示件数
     */
    public static final String DEFAULT_LIST_LIMIT = "50";

    /**
     * 商品一覧画面：１ページ当たりのサムネイル形式デフォルト最大表示件数
     */
    public static final String DEFAULT_THUMBNAIL_LIMIT = "50";

    /**
     * 商品一覧画面：デフォルト表示形式キー
     */
    public static final String DEFAULT_VIEW_TYPE_KEY = PageInfo.VIEW_TYPE_THUMBNAIL_KEY;

    /**
     * 商品一覧画面：デフォルト並び順キー
     */
    public static final String DEFAULT_SORT_TYPE_KEY = PageInfo.SORT_TYPE_NORMAL_KEY;

    /**
     * 商品一覧画面：デフォルト昇順キー
     */
    public static final String DEFAULT_ASC_TYPE_KEY = PageInfo.ASC_TYPE_TRUE_KEY;

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * 商品一覧画面：デフォルトUK並び順キー
     */
    public static final String DEFAULT_UK_SORT_TYPE_KEY = PageInfo.UK_SORT_TYPE_RECOMMEND_KEY;
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * サムネイル表示改行位置
     */
    public static final String DEFAULT_CHANGE_LINE_INDEX_THUMBNAIL = "5";

    /**
     * 商品一覧画面：ページ番号表示数
     */
    public static final int PAGE_LINK_COUNT = 3;

    /**
     * Unisearchとの通信エラーメッセージ
     */
    public static final String MSGCD_HTTP_ERROR = "GOODS-SEARCH-003-I";

    /**
     * helper
     */
    private final GoodsListHelper goodsListHelper;

    /**
     * 商品Api
     */
    private final GoodsApi goodsApi;

    /**
     * UK-API
     */
    private final UkapiApi ukApi;

    /**
     * 商品Helper
     */
    private final GoodsHelper goodsHelper;

    /**
     * コンストラクタ
     *
     * @param goodsListHelper 商品一覧画面 Helper
     * @param goodsApi        商品Api
     * @param ukApi           UKApi
     * @param goodsHelper     商品Helper
     */
    @Autowired
    public GoodsListController(GoodsListHelper goodsListHelper,
                               GoodsApi goodsApi,
                               UkapiApi ukApi,
                               GoodsHelper goodsHelper) {
        this.goodsListHelper = goodsListHelper;
        this.goodsApi = goodsApi;
        this.ukApi = ukApi;
        this.goodsHelper = goodsHelper;
    }

    /**
     * 商品一覧画面：初期処理
     *
     * @param goodsListModel 商品一覧画面 Model
     * @param pnum           ページ番号
     * @param listLimit      リスト形式デフォルト最大表示件数
     * @param thumbnailLimit サムネイル形式デフォルト最大表示件数
     * @param vtype          表示形式
     * @param stype          並び順
     * @param asc            昇順
     * @param col            サムネイル表示改行位置
     * @param model          モデル
     * @param fromView       遷移元画面
     * @return 商品検索画面
     */
    @GetMapping(value = {"/list", "/list.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error.html")
    protected String doLoadIndex(GoodsListModel goodsListModel,
                                 @RequestParam(required = false, defaultValue = DEFAULT_PNUM) String pnum,
                                 @RequestParam(required = false, defaultValue = DEFAULT_LIST_LIMIT) int listLimit,
                                 @RequestParam(required = false, defaultValue = DEFAULT_THUMBNAIL_LIMIT)
                                                 int thumbnailLimit,
                                 @RequestParam(required = false, defaultValue = DEFAULT_VIEW_TYPE_KEY) String vtype,
                                 @RequestParam(required = false) String stype,
                                 @RequestParam(required = false, defaultValue = DEFAULT_ASC_TYPE_KEY) boolean asc,
                                 @RequestParam(required = false, defaultValue = DEFAULT_CHANGE_LINE_INDEX_THUMBNAIL)
                                                 int col,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) String fromView,
                                 RedirectAttributes redirectAttrs,
                                 Model model) {

        // カテゴリID
        String cid = goodsListModel.getCid();

        if (StringUtils.isNotEmpty(cid)) {
            // 2023-renew No36-1, No61,67,95 from here
            getGoodsGroupListByCategoryId(cid, goodsListModel, pnum, listLimit, thumbnailLimit, vtype, stype, asc, col,
                                          sort, fromView, redirectAttrs, model
                                         );
            // 2023-renew No36-1, No61,67,95 to here
        } else {
            // PDR Migrate Customization from here
            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
            throwMessage(GoodsListModel.MSGCD_CATEGORY_NOT_FOUND_ERROR, new Object[] {appComplementUrl});
            // PDR Migrate Customization to here
        }

        return "goods/list";

    }

    /**
     * カテゴリ商品一覧表示
     *
     * @param cid            カテゴリID
     * @param goodsListModel 商品一覧画面 Model
     * @param pnum           ページ番号
     * @param listLimit      リスト形式デフォルト最大表示件数
     * @param thumbnailLimit サムネイル形式デフォルト最大表示件数
     * @param vType          表示形式
     * @param sType          並び順
     * @param asc            昇順
     * @param col            サムネイル表示改行位置
     * @param sort           並び順（ユニサーチ）
     * @param fromView       遷移元画面
     * @param model          モデル
     */
    protected void getGoodsGroupListByCategoryId(String cid,
                                                 GoodsListModel goodsListModel,
                                                 String pnum,
                                                 int listLimit,
                                                 int thumbnailLimit,
                                                 String vType,
                                                 String sType,
                                                 boolean asc,
                                                 int col,
                                                 String sort,
                                                 String fromView,
                                                 RedirectAttributes redirectAttrs,
                                                 Model model) {

        // 自カテゴリ情報の取得
        CategoryDetailsDto categoryDetailsDto = getCategoryDto(goodsListModel);
        goodsListHelper.toPageForLoad(categoryDetailsDto, goodsListModel);

        // カテゴリが取得できない場合 エラーページ
        if (categoryDetailsDto == null) {
            // PDR Migrate Customization from here
            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
            throwMessage(GoodsListModel.MSGCD_CATEGORY_NOT_FOUND_ERROR, new Object[] {appComplementUrl});
            // PDR Migrate Customization to here
        }

        // パンくず情報の取得
        OpenCategoryPassListGetRequest openCategoryPassListGetRequest = new OpenCategoryPassListGetRequest();

        openCategoryPassListGetRequest.setCategorySeqPath(categoryDetailsDto.getCategorySeqPath());

        List<CategoryDetailsDtoResponse> categoryDetailsDtoResponseList = null;
        try {
            categoryDetailsDtoResponseList = goodsApi.getOpenCategoryPassList(openCategoryPassListGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        List<CategoryDetailsDto> categoryDetailsDtoList =
                        goodsHelper.toCategoryDetailsDtoList(categoryDetailsDtoResponseList);

        goodsListHelper.toPageForLoadForTopicPath(categoryDetailsDtoList, goodsListModel);

        // 2023-renew No36-1, No61,67,95 from here
        // 指定したカテゴリに属する商品グループ情報を取得
        List<UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse> categoryGoodsList =
                        getCategoryGoodsList(cid, goodsListModel, pnum, listLimit, thumbnailLimit, vType, sType, asc,
                                             sort, fromView, redirectAttrs, model
                                            );
        // 2023-renew No36-1, No61,67,95 to here

        try {
            goodsListHelper.toPageForLoadCurrentCategoryGoods(categoryGoodsList, col, goodsListModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

    }

    /**
     * 自カテゴリリストの取得
     *
     * @param goodsListModel 商品一覧画面 Model
     * @return 商品詳細情報DTO
     */
    protected CategoryDetailsDto getCategoryDto(GoodsListModel goodsListModel) {

        CategorySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        conditionDto.setCategoryId(goodsListModel.getCid());
        conditionDto.setOpenStatus(HTypeOpenStatus.OPEN);

        CategoryDetailsGetRequest categoryDetailsGetRequest = new CategoryDetailsGetRequest();
        categoryDetailsGetRequest.setCategoryId(conditionDto.getCategoryId());
        if (conditionDto.getOpenStatus() != null) {
            categoryDetailsGetRequest.setOpenStatus(conditionDto.getOpenStatus().getValue());
        }

        CategoryDetailsDtoResponse categoryDetailsDtoResponse = null;
        try {
            categoryDetailsDtoResponse = goodsApi.getCategoryDetails(categoryDetailsGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        return goodsListHelper.toCategoryDetailsDto(categoryDetailsDtoResponse);
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * 指定したカテゴリに属する商品グループ情報を取得する。
     *
     * @param cid            カテゴリID
     * @param goodsListModel 商品一覧画面 Model
     * @param pnum           ページ番号
     * @param listLimit      リスト形式デフォルト最大表示件数
     * @param thumbnailLimit サムネイル形式デフォルト最大表示件数
     * @param vType          表示形式
     * @param sType          並び順
     * @param asc            昇順
     * @param fromView       遷移元画面
     * @param model          モデル
     * @return 商品グループ情報
     */
    protected List<UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse> getCategoryGoodsList(String cid,
                                                                                             GoodsListModel goodsListModel,
                                                                                             String pnum,
                                                                                             int listLimit,
                                                                                             int thumbnailLimit,
                                                                                             String vType,
                                                                                             String sType,
                                                                                             boolean asc,
                                                                                             String sort,
                                                                                             String fromView,
                                                                                             RedirectAttributes redirectAttrs,
                                                                                             Model model) {

        // PageInfoヘルパー取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);

        // conditionDtoをセットする　ユニサーチから商品リストを取得するため未使用
        // GoodsGroupSearchForDaoConditionDto conditionDto = setConditionDto(cid);

        // UKAPIリクエストDto設定
        UkApiGetUkUniSearchGoodsRequest uniSearchRequest =
                        goodsListHelper.setRequest(goodsListModel, cid, pnum, sort, sType,
                                                   getAsc(sType, asc, goodsListModel), fromView
                                                  );

        // 検索前ページャーセットアップ
        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        pageInfoHelper.setupPageRequest(pageInfoRequest, Integer.valueOf(pnum),
                                        getLimit(vType, listLimit, thumbnailLimit), getStypeMap().get(sType),
                                        getAsc(sType, asc, goodsListModel)
                                       );

        UkApiGetUkUniSearchGoodsResponse uniSearchResponse = null;
        try {
            // ユニサーチAPI実行
            uniSearchResponse = ukApi.getUkUniSearchGoods(uniSearchRequest, pageInfoRequest);
            if (uniSearchResponse.getResponse() == null) {
                addInfoMessage(MSGCD_HTTP_ERROR, new String[] {uniSearchResponse.getResponseHeader().getStatus()},
                               redirectAttrs, model
                              );
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // 2023-renew No3-taglog from here
        goodsListModel.setReqIdOfGoods(uniSearchResponse.getResponseHeader().getReqID());
        if (uniSearchResponse.getResponse() != null) {
            goodsListModel.setFqCategory(uniSearchResponse.getResponse().getCategory());
            // 2023-renew No3-taglog to here

            PageInfoResponse pageInfoResponse = uniSearchResponse.getResponse().getPageInfo();

            pageInfoHelper.setupPageInfo(goodsListModel, pageInfoResponse.getPage(), pageInfoResponse.getLimit(),
                                         pageInfoResponse.getNextPage(), pageInfoResponse.getPrevPage(),
                                         pageInfoResponse.getTotal(), pageInfoResponse.getTotalPages(), getStypeMap(),
                                         getStypeMap().get(sType), null, getAsc(sType, asc, goodsListModel),
                                         getVType(vType), getSort(uniSearchRequest.getSortType())
                                        );

            // 検索後ページングセットアップ
            pageInfoHelper.setupViewPager(goodsListModel.getPageInfo(), model, PAGE_LINK_COUNT);
            // 総件数をModelにセット
            goodsListModel.setTotalCount(pageInfoResponse.getTotal() != null ? pageInfoResponse.getTotal() : 0);

            return uniSearchResponse.getResponse().getDocs();
        }
        return null;
    }
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * conditionDtoをセットする
     *
     * @param categoryId カテゴリID
     * @return goodsGroupSearchForDaoConditionDto
     */
    private GoodsGroupSearchForDaoConditionDto setConditionDto(String categoryId) {

        GoodsGroupSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(GoodsGroupSearchForDaoConditionDto.class);
        conditionDto.setCategoryId(categoryId);
        conditionDto.setMinPrice(null);
        conditionDto.setMaxPrice(null);
        conditionDto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);

        return conditionDto;

    }

    /**
     * 画面最大表示件数取得
     *
     * @param vtype          表示形式
     * @param listLimit      リスト形式デフォルト最大表示件数
     * @param thumbnailLimit サムネイル形式デフォルト最大表示件数
     * @return 画面最大表示件数
     */
    private int getLimit(String vtype, int listLimit, int thumbnailLimit) {

        int limit;
        // 表示形式がリストならば
        if (PageInfo.VIEW_TYPE_LIST_KEY.equals(vtype)) {
            // リストの画面最大表示件数取得
            limit = listLimit;
        } else {
            // サムネイルの画面最大表示件数取得
            limit = thumbnailLimit;
        }
        return limit;
    }

    /**
     * ソート項目の省略文字と正式名称のMapを取得
     *
     * @return ソート項目の省略文字と正式名称のMap
     */
    private Map<String, String> getStypeMap() {
        Map<String, String> stypeMap = new HashMap<String, String>();
        stypeMap.put(PageInfo.SORT_TYPE_REGISTDATE_KEY, "whatsnewdate");
        stypeMap.put(PageInfo.SORT_TYPE_GOODSPRICE_KEY, "goodsGroupMinPrice");
        stypeMap.put(PageInfo.SORT_TYPE_SALECOUNT_KEY, "popularityCount");
        stypeMap.put(PageInfo.SORT_TYPE_NORMAL_KEY, "normal");
        return stypeMap;
    }

    /**
     * 表示形式の取得
     *
     * @param vType 表示形式
     * @return 表示形式
     */
    private String getVType(String vType) {
        // 表示形式がリストでないならば
        if (!PageInfo.VIEW_TYPE_LIST_KEY.equals(vType)) {
            // 表示形式はサムネイルにする
            vType = PageInfo.VIEW_TYPE_THUMBNAIL_KEY;
        }
        return vType;
    }

    /**
     * 並び順の取得
     *
     * @param sType          並び順
     * @param goodsListModel 商品一覧画面 Model
     * @return 並び順
     */
    private String getSType(String sType, GoodsListModel goodsListModel) {

        // sTypeに値が設定されていれば
        if (sType != null) {
            // そのままsTypeを返す
            return sType;
        }

        // 手動表示フラグがONならば
        if (HTypeManualDisplayFlag.ON == goodsListModel.getManualDisplayFlag()) {
            // 並び順を標準順とする
            sType = PageInfo.SORT_TYPE_NORMAL_KEY;
        } else {
            // 並び順を新着順とする
            sType = PageInfo.SORT_TYPE_REGISTDATE_KEY;
        }
        return sType;
    }

    /**
     * 並び順の取得
     *
     * @param sType          並び順
     * @param asc            昇順
     * @param goodsListModel 商品一覧画面 Model
     * @return 昇順かどうか
     */
    private boolean getAsc(String sType, boolean asc, GoodsListModel goodsListModel) {

        // sTypeに値が設定されていれば
        if (sType != null) {
            // そのままascを返す
            return asc;
        }

        // 手動表示フラグがONならば
        if (HTypeManualDisplayFlag.ON == goodsListModel.getManualDisplayFlag()) {
            asc = false;
        }
        return asc;
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * UK並び順の取得
     *
     * @param sort     UK並び順
     * @return 昇順かどうか
     */
    private String getSort(String sort) {

        // sTypeに値が設定されていれば
        if (sort != null) {
            // そのままascを返す
            return sort;
        }

        return PageInfo.UK_SORT_TYPE_RECOMMEND_KEY;
    }
    // 2023-renew No36-1, No61,67,95 to here
}
