/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.search;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.AccessApi;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.UkapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.base.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hitmall.front.base.application.HmMessages;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.NumberUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.thymeleaf.NumberConverterViewUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品検索画面 Controller
 * <p>
 * // PDR Migrate Customization from here
 *
 * @author kato
 * // PDR Migrate Customization to here
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@SessionAttributes(value = "searchModel")
@RequestMapping("/search/")
@Controller
public class SearchController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    /**
     * 価格帯From フィールド名
     **/
    protected static final String FILED_NAME_LL = "ll";

    /**
     * 価格帯To フィールド名
     **/
    protected static final String FILED_NAME_UL = "ul";

    /**
     * メッセージID:不正値
     */
    public static final String MSGCD_INVALID = "GOODS-SEARCH-001-E";

    /**
     * メッセージID:範囲外
     **/
    public static final String MSGCD_OUT_OF_RANGE = "GOODS-SEARCH-002-E";

    /**
     * Unisearchとの通信エラーメッセージ
     */
    public static final String MSGCD_HTTP_ERROR = "GOODS-SEARCH-003-I";

    /**
     * 価格下限値
     **/
    public static final int PRICE_MIN = 0;

    /**
     * 価格上限値
     **/
    public static final int PRICE_MAX = 99999999;

    /**
     * 商品検索画面：デフォルトページ番号
     */
    public static final String DEFAULT_PNUM = "1";

    /**
     * 商品検索画面：１ページ当たりのリスト形式デフォルト最大表示件数
     */
    // PDR Customiation from here
    public static final int DEFAULT_LIST_LIMIT = 50;
    // PDR Customiation to here

    /**
     * 商品検索画面：１ページ当たりのサムネイル形式デフォルト最大表示件数
     */
    // PDR Customiation from here
    public static final int DEFAULT_THUMBNAIL_LIMIT = 50;
    // PDR Customiation to here

    /**
     * 商品検索画面：デフォルト表示形式キー
     */
    public static final String DEFAULT_VIEW_TYPE_KEY = PageInfo.VIEW_TYPE_THUMBNAIL_KEY;

    /**
     * 商品検索画面：デフォルト並び順キー
     */
    // 2023-renew No36-1, No61,67,95 from here
    public static final String DEFAULT_SORT_TYPE_KEY = null;
    // 2023-renew No36-1, No61,67,95 to here
    /**
     * 商品検索画面：デフォルトサムネイル表示改行位置
     */
    // PDR Customiation from here
    public static final int DEFAULT_CHANGE_LINE_INDEX_THUMBNAIL = 5;
    // 2023-renew No36-1, No61,67,95 from here
    /**
     * 商品検索画面：デフォルトUK並び順キー
     */
    public static final String DEFAULT_UK_SORT_TYPE_KEY = PageInfo.UK_SORT_TYPE_RECOMMEND_KEY;

    /**
     * 商品検索画面：１ページ当たりの商品表示件数
     */
    public static final int DEFAULT_GOODS_LIMIT = 50;

    /**
     * 商品検索画面：コンテンツ表示件数
     */
    public static final int DEFAULT_CONTENTS_LIMIT = 20;

    /**
     * 商品検索画面：関連キーワード表示件数
     */
    public static final int DEFAULT_RWORD_LIMIT = 5;

    /**
     * 商品検索画面：ページ番号表示数
     */
    public static final int PAGE_LINK_COUNT = 3;
    // 2023-renew No36-1, No61,67,95 to here
    // PDR Customiation to here

    /**
     * helperクラス
     */
    private final SearchHelper searchHelper;

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * アクセスAPI
     */
    private final AccessApi accessApi;

    /**
     * UK-API
     */
    private final UkapiApi ukApi;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * コンストラクタ
     *
     * @param searchHelper      helperクラス
     * @param goodsApi          商品API
     * @param accessApi         アクセスAPI
     * @param ukApi             ukAPI
     * @param conversionUtility 変換ユーティリティクラス
     */
    @Autowired
    public SearchController(SearchHelper searchHelper,
                            GoodsApi goodsApi,
                            AccessApi accessApi,
                            UkapiApi ukApi,
                            ConversionUtility conversionUtility,
                            CommonInfoUtility commonInfoUtility) {
        this.searchHelper = searchHelper;
        this.goodsApi = goodsApi;
        this.accessApi = accessApi;
        this.ukApi = ukApi;
        this.conversionUtility = conversionUtility;
        this.commonInfoUtility = commonInfoUtility;

    }

    /**
     * 商品検索画面：初期処理
     *
     * @param fromView    遷移元画面
     * @param searchModel 商品検索Model
     * @param error       エラー
     * @param model       Model
     * @return 商品検索画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error.html")
    protected String doLoadIndex(@RequestParam(required = false) String fromView,
                                 SearchModel searchModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttrs,
                                 Model model) {

        // Model初期化
        searchHelper.toPageForLoad(searchModel, fromView);

        // （１） パラメータの情報を元に、商品情報の一覧を取得する。
        //        manualValidateAndConvert(searchModel, error);

        if (error.hasErrors()) {
            model.addAttribute(PageInfo.ATTRIBUTE_NAME_KEY, searchModel.getPageInfo());
            return "search/index";
        }

        // 表示条件にデフォルト値を設定
        setModelDefaultValue(searchModel);
        // 2023-renew No36-1, No61,67,95 from here
        // ユニサーチ（商品）APIのレスポンスを受け取る
        UkApiGetUkUniSearchGoodsResponse response = getSearchResultList(searchModel, model);
        if (response.getResponse() == null) {
            addInfoMessage(MSGCD_HTTP_ERROR, new String[] {response.getResponseHeader().getStatus()}, redirectAttrs,
                           model
                          );
        }

        // （２） 取得した商品一覧情報と検索条件を、ページ情報にセットする。
        try {
            searchHelper.toPageForLoad(response, searchModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // 2023-renew No36-1, No61,67,95 from here
        // ユニサーチ（コンテンツ）APIのレスポンスを受け取る
        UkApiGetUkUniSearchContentsResponse contentsResponse = getSearchContentsResultList(searchModel);
        if (contentsResponse.getResponse() == null) {
            HmMessages hmMessages = (HmMessages) model.getAttribute("allMessages");
            if (hmMessages != null) {
                boolean sameMessage = false;
                // 既にユニサーチエラーメッセージが設定されている場合はエラーメッセージを追加しない
                for (AppLevelFacesMessage message : hmMessages) {
                    if (message.getMessageCode().equals(MSGCD_HTTP_ERROR)) {
                        sameMessage = true;
                        break;
                    }
                }
                if (!sameMessage) {
                    addInfoMessage(MSGCD_HTTP_ERROR, new String[] {contentsResponse.getResponseHeader().getStatus()},
                                   redirectAttrs, model
                                  );
                }
            } else {
                addInfoMessage(MSGCD_HTTP_ERROR, new String[] {contentsResponse.getResponseHeader().getStatus()},
                               redirectAttrs, model
                              );
            }
        }

        // 取得したコンテンツ情報を、ページ情報にセットする。
        try {
            searchHelper.toPageForLoad(contentsResponse, searchModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // 2023-renew No36-1, No61,67,95 to here

        //prerender(searchModel);
        // 2023-renew No36-1, No61,67,95 to here

        if (searchModel.getKeyword() != null) {
            UkApiGetUkRWordRequest rWordReqDto = new UkApiGetUkRWordRequest();
            rWordReqDto.setKw(searchModel.getKeyword());
            rWordReqDto.setRows(DEFAULT_RWORD_LIMIT);
            UkApiGetUkRWordResponse resDto = null;
            try {
                resDto = ukApi.getUkRWord(rWordReqDto);
                if (resDto.getResponse() == null) {
                    HmMessages hmMessages = (HmMessages) model.getAttribute("allMessages");
                    if (hmMessages != null) {
                        boolean sameMessage = false;
                        // 既にユニサーチエラーメッセージが設定されている場合はエラーメッセージを追加しない
                        for (AppLevelFacesMessage message : hmMessages) {
                            if (message.getMessageCode().equals(MSGCD_HTTP_ERROR)) {
                                sameMessage = true;
                                break;
                            }
                        }
                        if (!sameMessage) {
                            addInfoMessage(MSGCD_HTTP_ERROR, new String[] {resDto.getResponseHeader().getStatus()},
                                           redirectAttrs, model
                                          );
                        }
                    } else {
                        addInfoMessage(MSGCD_HTTP_ERROR, new String[] {resDto.getResponseHeader().getStatus()},
                                       redirectAttrs, model
                                      );
                    }
                }
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            searchHelper.toPageForLoad(resDto, searchModel);
        }

        return "search/index";
    }

    /**
     * 検索<br/>
     *
     * @param fromView           遷移元画面
     * @param searchModel        商品検索Model
     * @param error              エラー
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return 商品検索画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "search/index")
    public String doSearch(@RequestParam(required = false) String fromView,
                           @Validated SearchModel searchModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (error.hasErrors()) {
            model.addAttribute(PageInfo.ATTRIBUTE_NAME_KEY, searchModel.getPageInfo());
            return "search/index";
        }

        // Model初期化
        searchHelper.toPageForLoad(searchModel, fromView);

        // （１） パラメータの情報を元に、商品情報の一覧を取得する。
        // 検索条件
        // キーワード：画面で入力したキーワード
        // 最大表示数：画面で選択されている最大表示数
        // ページ：1
        // ソート：画面で選択されている並び順
        searchModel.setPnum(DEFAULT_PNUM);
        setModelDefaultValue(searchModel);
        // 2023-renew No36-1, No61,67,95 from here
        // 検索キーワード登録機能削除
        UkApiGetUkUniSearchGoodsResponse response = getSearchResultList(searchModel, model);
        // 2023-renew No36-1, No61,67,95 to here

        // （２） 取得した商品一覧情報を、ページ情報にセットする。
        try {
            // 2023-renew No36-1, No61,67,95 from here
            searchHelper.toPageForLoad(response, searchModel);
            // 2023-renew No36-1, No61,67,95 to here
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 自画面を表示
        return "search/index";
    }

    /**
     * 手動バリデータ＆コンバータ
     * LEB#731にてPOST⇒GETに方式変更したため、手動バリデータを実施
     * ※Modelクラスに定義されたバリデータのうち、最低限必要なもののみ実施する
     *
     * @param searchModel   商品検索Model
     * @param error         エラー
     */
    //    protected void manualValidateAndConvert(SearchModel searchModel, BindingResult error) {
    //
    //        // 価格帯(FROM)チェック開始
    //        searchModel.setLl(checkValidateAndConvert(searchModel.getLl(), FILED_NAME_LL, error));
    //        // 価格帯(TO)チェック開始
    //        searchModel.setUl(checkValidateAndConvert(searchModel.getUl(), FILED_NAME_UL, error));
    //    }

    /**
     * 手動バリデータ＆コンバータのチェックメソッド
     *
     * @param value     実行前の値
     * @param filedName FiledName
     * @param error     エラー
     * @return result 実行後の値
     */
    protected String checkValidateAndConvert(String value, String filedName, BindingResult error) {

        // 数値コンバータ
        NumberConverterViewUtil util = new NumberConverterViewUtil();

        // 数値関連Helper取得
        NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);

        // 数値コンバート @HCNumber
        String result = util.convert(value, "#");

        if (!StringUtils.isEmpty(result)) {
            // 数値かどうか @HVNumber
            if (!numberUtility.isNumber(result)) {
                error.rejectValue(filedName, MSGCD_INVALID);
            } else {
                // 範囲内かどうか @Range
                if (PRICE_MIN > Integer.parseInt(result) || Integer.parseInt(result) > PRICE_MAX) {
                    Object[] arg = new Object[] {Integer.toString(PRICE_MIN), Integer.toString(PRICE_MAX)};
                    error.rejectValue(filedName, MSGCD_OUT_OF_RANGE, arg, null);
                }
            }
        }
        return result;
    }

    /**
     * 表示条件にデフォルト値を設定<br/>
     *
     * @param searchModel 商品検索Model
     */
    protected void setModelDefaultValue(SearchModel searchModel) {

        if (searchModel.getPnum() == null) {
            searchModel.setPnum(DEFAULT_PNUM);
        }
        if (searchModel.getVtype() == null) {
            searchModel.setVtype(DEFAULT_VIEW_TYPE_KEY);
        }
        if (searchModel.getStype() == null) {
            searchModel.setStype(DEFAULT_SORT_TYPE_KEY);
        }
        if (searchModel.getListLimit() == 0) {
            searchModel.setListLimit(DEFAULT_LIST_LIMIT);
        }
        if (searchModel.getThumbnailLimit() == 0) {
            searchModel.setThumbnailLimit(DEFAULT_THUMBNAIL_LIMIT);
        }
        if (searchModel.getCol() == 0) {
            searchModel.setCol(DEFAULT_CHANGE_LINE_INDEX_THUMBNAIL);
        }
        // 2023-renew No36-1, No61,67,95 from here
        if (searchModel.getSort() == null) {
            searchModel.setSort(DEFAULT_UK_SORT_TYPE_KEY);
        }
        // 2023-renew No36-1, No61,67,95 to here

    }

    /**
     * 検索結果商品リストの取得
     *
     * @param searchModel 商品検索Model
     * @param model       Model
     * @return ユニサーチ（商品）レスポンス
     */
    protected UkApiGetUkUniSearchGoodsResponse getSearchResultList(SearchModel searchModel, Model model) {

        // PageInfoヘルパー取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);

        // 表示形式を取得
        String vType = searchModel.getVtype();

        // Modelの内容を元にrequestをセットする
        UkApiGetUkUniSearchGoodsRequest ukApiGetUkUniSearchGoodsRequest = setUkUniSearchGoodsRequest(searchModel);

        // 検索前ページャーセットアップ
        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        // リクエスト用のページャー項目をセット
        pageInfoHelper.setupPageRequest(pageInfoRequest, this.conversionUtility.toInteger(searchModel.getPnum()),
                                        getLimit(vType, searchModel), getStypeMap().get(searchModel.getStype()),
                                        searchModel.isAsc()
                                       );

        UkApiGetUkUniSearchGoodsResponse uniSearchResponse = null;
        try {
            // ユニサーチAPI実行
            uniSearchResponse = ukApi.getUkUniSearchGoods(ukApiGetUkUniSearchGoodsRequest, pageInfoRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        if (uniSearchResponse != null && uniSearchResponse.getResponse() != null) {
            PageInfoResponse pageInfoResponse = uniSearchResponse.getResponse().getPageInfo();
            // 検索前ページャーセットアップ
            pageInfoHelper.setupPageInfo(searchModel, pageInfoResponse.getPage(), pageInfoResponse.getLimit(),
                                         pageInfoResponse.getNextPage(), pageInfoResponse.getPrevPage(),
                                         pageInfoResponse.getTotal(), pageInfoResponse.getTotalPages(), getStypeMap(),
                                         getStypeMap().get(searchModel.getStype()), null, searchModel.isAsc(),
                                         searchModel.getVtype(), searchModel.getSort()
                                        );

            // 検索後ページングセットアップ
            pageInfoHelper.setupViewPager(searchModel.getPageInfo(), model, PAGE_LINK_COUNT);

            // 総件数をModelにセット
            searchModel.setTotalCount(pageInfoResponse.getTotal() != null ? pageInfoResponse.getTotal() : 0);
        }
        // 2023-renew No36-1, No61,67,95 from here
        // 商品検索結果を返却
        return uniSearchResponse;
        // 2023-renew No36-1, No61,67,95 to here
    }

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * 検索結果コンテンツリストの取得
     *
     * @param searchModel 商品検索Model
     * @return ユニサーチ（コンテンツ）レスポンス
     */
    protected UkApiGetUkUniSearchContentsResponse getSearchContentsResultList(SearchModel searchModel) {

        // Modelの内容を元にrequestをセットする
        UkApiGetUkUniSearchContentsRequest ukApiGetUkUniSearchContentsRequest =
                        setUkUniSearchContentsRequest(searchModel);

        UkApiGetUkUniSearchContentsResponse uniSearchResponse = null;
        try {
            // ユニサーチAPI実行
            uniSearchResponse = ukApi.getUkUniSearchContents(ukApiGetUkUniSearchContentsRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // コンテンツ検索結果を返却
        return uniSearchResponse;
    }

    /**
     * Modelの内容を元にrequestをセットする
     *
     * @param searchModel 商品検索Model
     * @return UkApiGetUkUniSearchGoodsRequest
     */
    private UkApiGetUkUniSearchGoodsRequest setUkUniSearchGoodsRequest(SearchModel searchModel) {

        UkApiGetUkUniSearchGoodsRequest req = new UkApiGetUkUniSearchGoodsRequest();

        // 検索キーワードをセット
        req.setKw(searchModel.getKeyword());
        // ページ数をセット
        req.setPage(Integer.parseInt(searchModel.getPnum()));
        // 検索結果数をセット
        req.setRows(DEFAULT_GOODS_LIMIT);

        // UKソート順をセット
        // sortの指定がなく、stypeの指定がある場合はsortに変換する
        String sort = searchModel.getSort();
        if (StringUtils.isEmpty(sort) && !StringUtils.isEmpty(searchModel.getStype())) {
            sort = searchHelper.getSortType(searchModel.getStype(), searchModel.isAsc());
            searchModel.setSort(sort);
            searchModel.setStype(null);
        }
        req.setSortType(sort);
        // ユーザーIDをセット
        CommonInfoUser user = searchModel.getCommonInfo().getCommonInfoUser();
        String cryptUser = "";
        if (commonInfoUtility.isLogin(searchModel.getCommonInfo())) {
            cryptUser = user.getCryptUserId();
        }
        req.setUser(cryptUser);

        return req;

    }

    /**
     * Modelの内容を元にrequestをセットする
     *
     * @param searchModel 商品検索Model
     * @return UkApiGetUkUniSearchGoodsRequest
     */
    private UkApiGetUkUniSearchContentsRequest setUkUniSearchContentsRequest(SearchModel searchModel) {

        UkApiGetUkUniSearchContentsRequest req = new UkApiGetUkUniSearchContentsRequest();

        req.setKw(searchModel.getKeyword());

        // ページ数をセット（コンテンツは1固定）
        req.setPage(1);
        // 検索結果数をセット
        req.setRows(DEFAULT_CONTENTS_LIMIT);
        
        // ユーザーIDをセット
        CommonInfoUser user = searchModel.getCommonInfo().getCommonInfoUser();
        String cryptUser = "";
        if (commonInfoUtility.isLogin(searchModel.getCommonInfo())) {
            cryptUser = user.getCryptUserId();
        }
        req.setUser(cryptUser);

        return req;

    }
    // 2023-renew No36-1, No61,67,95 to here

    /**
     * 画面最大表示件数取得
     *
     * @param vtype       表示形式
     * @param searchModel 商品検索Model
     * @return 画面最大表示件数
     */
    private int getLimit(String vtype, SearchModel searchModel) {

        int limit;
        // 表示形式がリストならば
        if (PageInfo.VIEW_TYPE_LIST_KEY.equals(vtype)) {
            // リストの画面最大表示件数取得
            limit = searchModel.getListLimit();
        } else {
            // サムネイルの画面最大表示件数取得
            limit = searchModel.getThumbnailLimit();
        }
        return limit;
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
     * 表示前処理<br/>
     *
     * @param searchModel 商品検索Model
     * @return 遷移先ページクラス
     */
    public Class<?> prerender(SearchModel searchModel) {

        // ルートカテゴリ一覧情報をページクラスにセット
        // 検索条件プルダウンに、項目をセット
        CategoryDto categoryDto = getCategoryDto();
        if (categoryDto != null) {
            searchHelper.toPageForLoad(categoryDto, searchModel);
        }

        return null;
    }

    /**
     * ルートカテゴリ一覧情報の取得
     *
     * @return カテゴリDTO
     */
    protected CategoryDto getCategoryDto() {

        String topCategoryId = PropertiesUtil.getSystemPropertiesValue("category.id.top");
        String viewLevel = PropertiesUtil.getSystemPropertiesValue("goods.search.category.view.level");

        // 検索条件の作成
        CategoryTreeNodeGetRequest categoryTreeNodeGetRequest = new CategoryTreeNodeGetRequest();
        categoryTreeNodeGetRequest.setCategoryId(topCategoryId);
        categoryTreeNodeGetRequest.setMaxHierarchical(Integer.valueOf(viewLevel));
        categoryTreeNodeGetRequest.setSiteType(null);
        categoryTreeNodeGetRequest.setOpenStatus(HTypeOpenStatus.OPEN.getValue());

        categoryTreeNodeGetRequest.setOrderField(CategorySearchForDaoConditionDto.CATEGORY_FIELD_CATEGORY_PATH);
        categoryTreeNodeGetRequest.setOrderAsc(true);

        CategoryTreeNodeResponse categoryTreeNodeResponse = null;
        try {
            categoryTreeNodeResponse = goodsApi.getCategoryTreeNode(categoryTreeNodeGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return searchHelper.toCategoryDto(categoryTreeNodeResponse);
    }

}
