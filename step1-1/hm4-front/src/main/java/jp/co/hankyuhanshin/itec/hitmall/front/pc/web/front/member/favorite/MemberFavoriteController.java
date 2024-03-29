/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.favorite;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsRegistCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CartHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CatalogHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsIndexModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会員お気に入り Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/member/favorite")
// PDR Migrate Customization from here
@SessionAttributes(value = "memberFavoriteModel")
// PDR Migrate Customization to here
@Controller
public class MemberFavoriteController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberFavoriteController.class);

    // PDR Migrate Customization from here
    /**
     * エラーコード：必須
     */
    public static final String MSGCD_REQUIRED_INPUT =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HRequiredValidator.INPUT_detail";
    // PDR Migrate Customization to here

    /**
     * 商品詳細画面
     */
    public static final String FLASH_FROM_GOODS_DETAILS = "fromGoodsDetails";

    /**
     * お気に入り一覧：１ページ当たりのデフォルトページ番号
     */
    public static final String DEFAULT_MYLIST_PNUM = "1";

    /**
     * お気に入り一覧：１ページ当たりのデフォルト最大表示件数
     */
    public static final String DEFAULT_MYLIST_LIMIT = "10";

    /**
     * 削除モード
     */
    public static final String DELETE_MODE = "d";

    /**
     * 商品コード(商品グループコードも兼ねる)
     */
    protected static final String PARAM_GOODS_CODE = "gcd";

    /**
     * フラッシュスコープパラメータ：商品詳細画面から商品グループ番号受取用
     */
    protected static final String PARAM_GOODS_GROUP_CODE = "ggcd";

    /**
     * 会員お気に入り Helper
     */
    private final MemberFavoriteHelper memberFavoriteHelper;

    // PDR Migrate Customization from here
    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * カート一括投入用DTOリストのセッションキー
     */
    public static final String CART_GOODS_DTO_LIST_KEY = "CartGoodsForTakeOverDtoList";
    // PDR Migrate Customization to here

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * カートApi
     */
    private final CartApi cartApi;

    // 2023-renew No11 from here
    /**
     * 商品Helper
     */
    private final GoodsHelper goodsHelper;

    /**
     * 商品系ヘルパー
     */
    private final GoodsUtility goodsUtility;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * WebapiApi
     */
    private final WebapiApi webapiApi;

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * コンストラクタ
     *
     * @param memberFavoriteHelper 会員お気に入り Helperクラス
     * @param conversionUtility    変換ユーティリティクラス
     * @param memberInfoApi        会員Api
     * @param cartApi              カートApi
     */
    @Autowired
    public MemberFavoriteController(MemberFavoriteHelper memberFavoriteHelper,
                                    GoodsHelper goodsHelper,
                                    ConversionUtility conversionUtility,
                                    GoodsUtility goodsUtility,
                                    CommonInfoUtility commonInfoUtility,
                                    MemberInfoApi memberInfoApi,
                                    CartApi cartApi,
                                    WebapiApi webapiApi,
                                    GoodsApi goodsApi) {
        this.memberFavoriteHelper = memberFavoriteHelper;
        // PDR Migrate Customization from here
        this.conversionUtility = conversionUtility;

        // 2023-renew No11 from here
        this.goodsHelper = goodsHelper;
        this.goodsUtility = goodsUtility;
        this.commonInfoUtility = commonInfoUtility;
        this.webapiApi = webapiApi;
        this.goodsApi = goodsApi;
        // 2023-renew No11 to here

        // PDR Migrate Customization to here

        this.memberInfoApi = memberInfoApi;
        this.cartApi = cartApi;
    }
    // 2023-renew No11 to here

    /**
     * お気に入り画面：初期処理
     *
     * @param memberFavoriteModel 会員お気に入りModel
     * @param pnum                ページ番号(デフォルト1）
     * @param limit               最大表示件数（デフォルト10）
     * @param model               Model
     * @return お気に入り画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error.html")
    protected String doLoadIndex(MemberFavoriteModel memberFavoriteModel,
                                 @RequestParam(required = false, defaultValue = DEFAULT_MYLIST_PNUM) String pnum,
                                 @RequestParam(required = false, defaultValue = DEFAULT_MYLIST_LIMIT) int limit,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (model.containsAttribute("fromCart")) {
            memberFavoriteModel.setFromCart(true);
            memberFavoriteModel.setFromGoodsDetails(false);
        } else {
            memberFavoriteModel.setFromCart(false);
        }

        if (model.containsAttribute(FLASH_FROM_GOODS_DETAILS) && model.containsAttribute(PARAM_GOODS_GROUP_CODE)) {
            memberFavoriteModel.setPathGgcdFromGoodsDetails(true);
            memberFavoriteModel.setGcd(null);
            memberFavoriteModel.setGgcd((String) model.getAttribute(PARAM_GOODS_GROUP_CODE));
            memberFavoriteModel.setFromGoodsDetails(true);
        }

        if (model.containsAttribute(FLASH_FROM_GOODS_DETAILS) && model.containsAttribute(PARAM_GOODS_CODE)) {
            memberFavoriteModel.setPathGgcdFromGoodsDetails(false);
            memberFavoriteModel.setGcd((String) model.getAttribute(PARAM_GOODS_CODE));
            memberFavoriteModel.setGgcd(null);
            memberFavoriteModel.setFromGoodsDetails(true);
        }

        // 商品詳細orカートでゲストがお気に入り追加押した場合
        if (!model.containsAttribute(FLASH_FROM_GOODS_DETAILS) && model.containsAttribute(PARAM_GOODS_CODE)) {
            memberFavoriteModel.setFromGoodsDetails(true);
            memberFavoriteModel.setGcd(model.getAttribute(PARAM_GOODS_CODE).toString());
            return "redirect:/goods/index.html?gcd=" + memberFavoriteModel.getGcd();
        }

        try {
            if (memberFavoriteModel.getMd() == null) {
                // 追加モード
                if (memberFavoriteModel.getGcd() != null) {
                    FavoriteRegistRequest request = new FavoriteRegistRequest();
                    request.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
                    request.setGcd(memberFavoriteModel.getGcd());
                    try {
                        memberInfoApi.registFavorite(request);
                    } catch (HttpClientErrorException e) {
                        LOGGER.error("例外処理が発生しました", e);
                        // AppLevelListExceptionへ変換
                        addAppLevelListException(e);
                        throwMessage();
                    }

                }
            } else {
                // 削除モード
                if ((memberFavoriteModel.getMd().equals(DELETE_MODE) && memberFavoriteModel.getGcd() != null)) {
                    FavoriteListDeleteRequest request = new FavoriteListDeleteRequest();
                    request.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
                    request.setGcd(memberFavoriteModel.getGcd());
                    try {
                        memberInfoApi.deleteFavoriteList(request);
                    } catch (HttpClientErrorException e) {
                        LOGGER.error("例外処理が発生しました", e);
                        // AppLevelListExceptionへ変換
                        addAppLevelListException(e);
                        throwMessage();
                    }

                    memberFavoriteModel.setMd(null);
                    // PDR Migrate Customization from here
                    memberFavoriteModel.setGcd(null);

                    return "redirect:/member/favorite/index.html";
                    // PDR Migrate Customization to here
                }
            }

        } finally {

            // お気に入り一覧の検索
            searchFavoriteList(memberFavoriteModel, pnum, limit, model);

        }

        // 2023-renew No71 from here
        try {
            // 通知フラグ=OFFまたは、セッションがすでに既読の場合はAPI呼出しやセッション設定処理をスキップする
            if (!(HTypeTopSaleAnnounceFlg.OFF.equals(getCommonInfo().getCommonInfoBase().getTopSaleAnnounceFlg())
                  || HTypeSaleAnnounceWatchFlg.READ.equals(
                            getCommonInfo().getCommonInfoBase().getSaleAnnounceWatchFlg()))) {
                // 更新失敗時は、ワーニングログを出力して、エラー画面に遷移しないようにする
                MemberInfoAnnounceUpdateRequest memberInfoAnnounceUpdateRequest =
                                memberFavoriteHelper.createRequestUpdateAnnounce(Boolean.TRUE, Boolean.FALSE);
                memberInfoApi.updateAnnounce(memberInfoAnnounceUpdateRequest);
                memberFavoriteHelper.updateAnnounceStatus(Boolean.TRUE, Boolean.FALSE);
            }
        } catch (Exception e) {
            LOGGER.warn("アナウンスデータの更新に失敗しました");
        }
        // 2023-renew No71 to here

        return "member/favorite/index";
    }

    /**
     * お気に入り一覧の検索
     */
    protected void searchFavoriteList(MemberFavoriteModel memberFavoriteModel, String pnum, int limit, Model model) {
        // PageInfoヘルパー取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);

        // お気に入り一覧の検索

        // 検索条件Dto作成
        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        memberFavoriteHelper.toFavoriteConditionDtoForSearchFavoriteList(memberFavoriteModel);
        // ページングセットアップ
        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setPnum(pnum);
        pageInfo.setLimit(limit);
        pageInfo.setOrderField("updateTime");
        pageInfo.setOrderAsc(false);
        favoriteConditionDto.setPageInfo(pageInfo);

        // 検索実行
        FavoriteListGetRequest favoriteListGetRequest =
                        memberFavoriteHelper.toFavoriteListGetRequest(favoriteConditionDto);
        PageInfoRequest pageInfoRequest = new PageInfoRequest();

        // リクエスト用のページャー項目をセット
        pageInfoHelper.setupPageRequest(pageInfoRequest,
                                        StringUtil.isNotEmpty(pnum) ? conversionUtility.toInteger(pnum) : null, limit,
                                        "updateTime", false
                                       );

        FavoriteDtoListResponse favoriteDtoListResponse = null;
        try {
            favoriteDtoListResponse = memberInfoApi.getFavoriteList(favoriteListGetRequest, pageInfoRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        GoodsHelper goodsHelper = ApplicationContextUtility.getBean(GoodsHelper.class);
        List<FavoriteDto> favoriteDtoList = goodsHelper.toFavoriteDtoList(
                        favoriteDtoListResponse == null ? null : favoriteDtoListResponse.getFavoriteDtoListResponse());

        PageInfoResponse pageInfoResponse =
                        favoriteDtoListResponse != null ? favoriteDtoListResponse.getPageInfo() : null;

        Integer pageInfoResponsePage = pageInfoResponse != null ? pageInfoResponse.getPage() : null;
        Integer pageInfoResponseLimit = pageInfoResponse != null ? pageInfoResponse.getLimit() : null;
        Integer pageInfoResponseNextPage = pageInfoResponse != null ? pageInfoResponse.getNextPage() : null;
        Integer pageInfoResponsePrevPage = pageInfoResponse != null ? pageInfoResponse.getPrevPage() : null;
        Integer pageInfoResponseTotal = pageInfoResponse != null ? pageInfoResponse.getTotal() : null;
        Integer pageInfoResponseTotalPages = pageInfoResponse != null ? pageInfoResponse.getTotalPages() : null;

        // 検索前ページャーセットアップ
        pageInfoHelper.setupPageInfo(memberFavoriteModel, pageInfoResponsePage, pageInfoResponseLimit,
                                     pageInfoResponseNextPage, pageInfoResponsePrevPage, pageInfoResponseTotal,
                                     pageInfoResponseTotalPages, null, null, null, false, null
                                    );

        CartHelper cartHelper = ApplicationContextUtility.getBean(CartHelper.class);
        FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest =
                        cartHelper.toFavoriteGoodsStockStatusGetRequest(favoriteDtoList);
        FavoriteDtoListResponse stockFavoriteResponse = null;
        try {
            stockFavoriteResponse = memberInfoApi.getFavoriteByFavoriteGoodsStockStatus(
                            favoriteGoodsStockStatusGetRequest == null ?
                                            new FavoriteGoodsStockStatusGetRequest() :
                                            favoriteGoodsStockStatusGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        List<FavoriteDto> stockFavoriteDtoList = goodsHelper.toFavoriteDtoList(
                        stockFavoriteResponse == null ? null : stockFavoriteResponse.getFavoriteDtoListResponse());

        // 検索結果を画面に設定
        try {
            // 2023-renew No11 from here
            setGoodsFavoriteInfo(stockFavoriteDtoList, memberFavoriteModel);
            // 2023-renew No11 to here
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // ぺージャーセットアップ
        pageInfoHelper.setupViewPager(memberFavoriteModel.getPageInfo(), model);
    }

    // 2023-renew No11 from here

    /**
     * 商品在庫情報の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param favoriteDtos          商品グループDTO
     * @param memberFavoriteModel   商品詳細ページ
     */
    protected void setGoodsFavoriteInfo(List<FavoriteDto> favoriteDtos, MemberFavoriteModel memberFavoriteModel) {

        // PageInfoヘルパー取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        // 在庫表示用リスト
        List<GoodsStockItem> goodsStockItems = new ArrayList<>();

        // WEB-API（数量割引情報取得）から数量割引情報を取得する
        WebApiGetQuantityDiscountResponseDto getQuantityDiscountDto = executeWebApiGetQuantityDiscount(favoriteDtos);

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockMap = executeWebApiGetStock(favoriteDtos);

        WebApiGetStockResponseDetailDto webApiGetStockResponseDetailPdrDto;

        // 扱いやすいように商品番号をキーにしたマップに詰め替え
        Map<String, FavoriteDto> goodsDtoMap = new LinkedHashMap<>();
        for (FavoriteDto goodsDto : favoriteDtos) {
            goodsDtoMap.put(goodsDto.getGoodsDetailsDto().getGoodsCode(), goodsDto);
        }

        NumberFormat df = NumberFormat.getNumberInstance();
        int rowSpanCount = 0;
        for (WebApiGetQuantityDiscountResponseDetailDto responseInfo : getQuantityDiscountDto.getInfo()) {
            // １商品につき何行表示するのかを割り出す
            // 2023-renew No11 from here
            if (CollectionUtil.isNotEmpty(responseInfo.getSalePriceList()) || CollectionUtil.isNotEmpty(
                            responseInfo.getCustomerSalePriceList())) {
                // 2023-renew No11 to here
                // 数量割引情報が存在している場合、または顧客セール情報が存在している場合は、数量割引パターン分表示
                // 価格は何件あっても先頭行しか表示しない
                if (!CollectionUtil.isEmpty(responseInfo.getCustomerSalePriceList())) {
                    // 数量割引情報が存在していない場合
                    // 2023-renew No11 from here
                    if (responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList().size()) {
                        rowSpanCount = responseInfo.getCustomerSalePriceList().size();
                    } else {
                        rowSpanCount = responseInfo.getPriceList().size();
                    }
                    // 2023-renew No11 to here
                } else if (!CollectionUtil.isEmpty(responseInfo.getSalePriceList())) {
                    // 顧客セール情報が存在していない場合
                    // 2023-renew No11 from here
                    if (responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                        rowSpanCount = responseInfo.getSalePriceList().size();
                    } else {
                        rowSpanCount = responseInfo.getPriceList().size();
                    }
                    // 2023-renew No11 to here
                }
            } else {
                // 価格パターン分表示
                rowSpanCount = responseInfo.getPriceList().size();
            }

            for (int i = 0; i < rowSpanCount; i++) {
                GoodsStockItem favoriteItem = ApplicationContextUtility.getBean(GoodsStockItem.class);

                // 結合数
                favoriteItem.setGoodsCodeRowSpan(rowSpanCount);

                // 商品番号
                favoriteItem.setGoodsCode(responseInfo.getGoodsCode());

                // 在庫
                if (stockMap != null) {
                    webApiGetStockResponseDetailPdrDto = stockMap.get(responseInfo.getGoodsCode());

                    // 在庫情報が取得できない場合はログを出力し、在庫なしとして処理を進める
                    if (webApiGetStockResponseDetailPdrDto == null) {
                        LOGGER.warn("商品番号：" + responseInfo.getGoodsCode() + "の在庫情報が取得できませんでした。在庫なしとして進めます。");
                        webApiGetStockResponseDetailPdrDto =
                                        ApplicationContextUtility.getBean(WebApiGetStockResponseDetailDto.class);
                        webApiGetStockResponseDetailPdrDto.setStockQuantity(BigDecimal.ZERO);
                    }
                } else {
                    LOGGER.warn("商品番号：" + responseInfo.getGoodsCode() + "の在庫情報が取得できませんでした。在庫なしとして進めます。");
                    webApiGetStockResponseDetailPdrDto =
                                    ApplicationContextUtility.getBean(WebApiGetStockResponseDetailDto.class);
                    webApiGetStockResponseDetailPdrDto.setStockQuantity(BigDecimal.ZERO);
                }

                // 問い合わせを行っていない商品番号が返却された場合はスキップする
                // 運用時はありえない前提
                if (goodsDtoMap.get(responseInfo.getGoodsCode()) == null) {
                    LOGGER.debug("数量割引情報取得で問合せを行っていない商品が返却されましたので、スキップします。商品番号：" + responseInfo.getGoodsCode());
                    continue;
                }

                // 2023-renew No11 from here
                // 商品の販売状態をチェック
                GoodsDetailsDto goodsDetailsDto = goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsDetailsDto();
                if (goodsUtility.isGoodsItemNoSales(goodsDetailsDto.getSaleStatusPC(),
                                                    goodsDetailsDto.getSaleStartTimePC(),
                                                    goodsDetailsDto.getSaleEndTimePC()
                                                   )) {
                    // 非販売の場合 在庫表示に[×」を設定
                    favoriteItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                } else {
                    // 販売中の場合
                    HTypeStockManagementFlag stockManagementFlag = goodsDtoMap.get(responseInfo.getGoodsCode())
                                                                              .getGoodsDetailsDto()
                                                                              .getStockManagementFlag();
                    // 売切対象商品の場合
                    int stockQuantity = webApiGetStockResponseDetailPdrDto.getStockQuantity().intValue();
                    int remainStockQuantity = goodsDtoMap.get(responseInfo.getGoodsCode())
                                                         .getGoodsDetailsDto()
                                                         .getRemainderFewStock()
                                                         .intValue();
                    if (HTypeStockManagementFlag.ON.equals(stockManagementFlag)) {

                        if (stockQuantity > remainStockQuantity) {
                            favoriteItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                        } else if (remainStockQuantity >= stockQuantity && stockQuantity > 0 || (stockQuantity > 0
                                                                                                 && "1".equals(
                                        webApiGetStockResponseDetailPdrDto.getDeliveryYesNo()))) {
                            favoriteItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                        } else if (stockQuantity == 0 && "0".equals(
                                        webApiGetStockResponseDetailPdrDto.getDeliveryYesNo())) {
                            favoriteItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                        }
                    } else {
                        // 売切対象外商品の場合
                        if (stockQuantity > 0) {
                            favoriteItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                        } else if (stockQuantity == 0) {
                            favoriteItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                        } else {
                            favoriteItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                        }
                    }
                }
                // 2023-renew No11 to here

                // 数量割引が設定されている、または、顧客セールが設定されている場合
                // 2023-renew No11 from here
                if (CollectionUtil.isNotEmpty(responseInfo.getSalePriceList()) || CollectionUtil.isNotEmpty(
                                responseInfo.getCustomerSalePriceList())) {
                    // 価格
                    if (responseInfo.getPriceList() != null && responseInfo.getPriceList().get(0) != null) {

                        if (responseInfo.getMarketPriceFlag().equals("1")) {
                            favoriteItem.setPrice("時価");
                        } else {
                            favoriteItem.setPrice(df.format(responseInfo.getPriceList().get(i).getPrice()) + "円");
                            favoriteItem.setLevel(responseInfo.getPriceList().get(i).getLevel());
                        }
                        // 2023-renew No11 to here

                    }
                    // 数量
                    // 数量割引が設定されていない場合
                    if (CollectionUtil.isEmpty(responseInfo.getSalePriceList())) {
                        // 顧客セール閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel() == null) {
                            favoriteItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList()
                                                                                     .size()) {
                                favoriteItem.setLevel(
                                                responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                        // 顧客セールが設定されていない場合
                    } else if (CollectionUtil.isEmpty(responseInfo.getCustomerSalePriceList())) {
                        // 数量割引閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getSalePriceList().get(i).getSaleLevel() == null) {
                            favoriteItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                                favoriteItem.setLevel(responseInfo.getSalePriceList().get(i).getSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                    } else {
                        // 顧客セール閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel() == null) {
                            favoriteItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList()
                                                                                     .size()) {
                                favoriteItem.setLevel(
                                                responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                    }

                    // セール価格
                    // 数量割引と顧客セールが設定されている場合
                    if (!CollectionUtil.isEmpty(responseInfo.getSalePriceList()) && !CollectionUtil.isEmpty(
                                    responseInfo.getCustomerSalePriceList())) {

                        // 顧客セール価格を設定
                        // 2023-renew No11 from here
                        if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                            && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList().size()) {
                            favoriteItem.setCustomerSalePrice(df.format(responseInfo.getCustomerSalePriceList()
                                                                                    .get(i)
                                                                                    .getCustomerSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            favoriteItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            favoriteItem.setSalePrice(responseInfo.getSalePriceList()
                                                                  .stream()
                                                                  .filter(salePriceDto -> detailDto.getLevel()
                                                                                                   .equals(salePriceDto.getSaleLevel()))
                                                                  .findFirst()
                                                                  .map(salePriceDto -> df.format(
                                                                                  salePriceDto.getSalePrice()) + "円")
                                                                  .orElse(null));
                            favoriteItem.setCustomerSalePrice(responseInfo.getCustomerSalePriceList()
                                                                          .stream()
                                                                          .filter(customerSalePriceDto -> detailDto.getLevel()
                                                                                                                   .equals(customerSalePriceDto.getCustomerSaleLevel()))
                                                                          .findFirst()
                                                                          .map(customerSalePriceDto ->
                                                                                               df.format(customerSalePriceDto.getCustomerSalePrice())
                                                                                               + "円")
                                                                          .orElse(null));
                        }
                        // 2023-renew No11 to here

                        // 数量割引のみが設定されている場合
                    } else if (!CollectionUtil.isEmpty(responseInfo.getSalePriceList()) && CollectionUtil.isEmpty(
                                    responseInfo.getCustomerSalePriceList())) {

                        // 数量割引価格を設定
                        // 2023-renew No11 from here
                        if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                            && responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                            favoriteItem.setSalePrice(
                                            df.format(responseInfo.getSalePriceList().get(i).getSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            favoriteItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            favoriteItem.setSalePrice(responseInfo.getSalePriceList()
                                                                  .stream()
                                                                  .filter(salePriceDto -> detailDto.getLevel()
                                                                                                   .equals(salePriceDto.getSaleLevel()))
                                                                  .findFirst()
                                                                  .map(salePriceDto -> df.format(
                                                                                  salePriceDto.getSalePrice()) + "円")
                                                                  .orElse(null));
                        }
                        // 2023-renew No11 to here
                        // 顧客セールのみが設定されている場合
                    } else if (CollectionUtil.isEmpty(responseInfo.getSalePriceList()) && !CollectionUtil.isEmpty(
                                    responseInfo.getCustomerSalePriceList())) {

                        // 顧客セール価格を設定
                        // 2023-renew No11 from here
                        if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                            && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList().size()) {
                            favoriteItem.setCustomerSalePrice(df.format(responseInfo.getCustomerSalePriceList()
                                                                                    .get(i)
                                                                                    .getCustomerSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            favoriteItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            favoriteItem.setCustomerSalePrice(responseInfo.getCustomerSalePriceList()
                                                                          .stream()
                                                                          .filter(customerSalePriceDto -> detailDto.getLevel()
                                                                                                                   .equals(customerSalePriceDto.getCustomerSaleLevel()))
                                                                          .findFirst()
                                                                          .map(customerSalePriceDto ->
                                                                                               df.format(customerSalePriceDto.getCustomerSalePrice())
                                                                                               + "円")
                                                                          .orElse(null));
                        }
                        // 2023-renew No11 to here
                    }
                } else {
                    // 数量割引と顧客セールが設定されていない場合
                    // 価格
                    if (responseInfo.getMarketPriceFlag().equals("1")) {
                        favoriteItem.setPrice("時価");
                    } else {
                        favoriteItem.setPrice(df.format(responseInfo.getPriceList().get(i).getPrice()) + "円");
                    }

                    // 数量
                    if (responseInfo.getPriceList().get(i).getLevel() == null || responseInfo.getPriceList()
                                                                                             .get(i)
                                                                                             .getLevel()
                                                                                             .isEmpty()) {
                        favoriteItem.setLevel("1～");
                    } else {
                        favoriteItem.setLevel(responseInfo.getPriceList().get(i).getLevel());
                    }
                }

                if (StringUtils.isBlank(favoriteItem.getGcnt())) {
                    favoriteItem.setGcnt("1");
                }

                GoodsDetailsDto goodsEntity = goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsDetailsDto();
                favoriteItem.setSaleStatus(goodsEntity.getSaleStatusPC());
                favoriteItem.setSaleStartTime(goodsEntity.getSaleStartTimePC());
                favoriteItem.setSaleEndTime(goodsEntity.getSaleEndTimePC());
                if (Objects.nonNull(stockMap) && Objects.nonNull(stockMap.get(responseInfo.getGoodsCode()))) {
                    favoriteItem.setSaleControl(stockMap.get(responseInfo.getGoodsCode()).getSaleControl());
                    favoriteItem.setStockQuantity(stockMap.get(responseInfo.getGoodsCode()).getStockQuantity());
                }

                // 2023-renew No11 from here
                if (commonInfoUtility.isLogin(getCommonInfo())) {
                    // WEB-API連携 割引適用結果取得実行
                    Map<String, WebApiGetDiscountsResponseDetailDto> getDiscountsResponseDtoMap =
                                    executeWebApiGetDiscountsResult(favoriteDtos);
                    if (Objects.nonNull(getDiscountsResponseDtoMap) && Objects.nonNull(
                                    getDiscountsResponseDtoMap.get(responseInfo.getGoodsCode() + "kp"))) {
                        favoriteItem.setSaleType(EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class,
                                                                               getDiscountsResponseDtoMap.get(
                                                                                               responseInfo.getGoodsCode()
                                                                                               + "kp").getSaleType()
                                                                              ));
                        favoriteItem.setSaleEmotionPrice(
                                        df.format(getDiscountsResponseDtoMap.get(responseInfo.getGoodsCode() + "kp")
                                                                            .getSalePrice()) + "円");

                    }
                }
                // 2023-renew No11 to here

                // リスト追加
                memberFavoriteHelper.toPageInfoResponse(favoriteItem, goodsDtoMap.get(responseInfo.getGoodsCode()),
                                                        memberFavoriteModel, goodsUtility
                                                       );
                // 2023-renew No2 from here
                // WEB-API販売可否判定チェック
                executeWebApiGetSaleCheck(memberFavoriteModel, favoriteItem);
                // 2023-renew No2 to here
                goodsStockItems.add(favoriteItem);
            }
        }

        // 商品コードリスト
        if (!ListUtils.isEmpty(goodsStockItems)) {
            // 取りおき情報取得API
            WebApiGetReserveResponseDto dto = this.executeWebApiGetReserve(goodsStockItems);
            if (ObjectUtils.isNotEmpty(dto)) {
                goodsStockItems.forEach(stockItem -> {
                    stockItem.setReserveFlag(dto.getMap().get(stockItem.getGoodsCode()).getReserveFlag());
                });
            }

            // 販売可否判定API
            List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                            this.executeWebApiGetSaleCheck(goodsStockItems);
            if (CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses)) {
                goodsStockItems.forEach(stockItem -> {
                    webApiGetSaleCheckDetailResponses.forEach(res -> {
                        if (stockItem.getGoodsCode().equals(res.getGoodsCode())) {
                            stockItem.setSaleCheck(res.getGoodsSaleYesNo());
                        }
                    });
                });
            }
        }

        // ぺージャーセットアップ
        // 価格情報をマージする
        memberFavoriteModel.setFavoriteItems(
                        goodsUtility.mergePriceInfo(goodsStockItems, goodsDtoMap.keySet(), LOGGER));
        // 複数数量閾値フラグ設定
        goodsUtility.setMultiLevelFlag(memberFavoriteModel.getFavoriteItems());
    }

    // 2023-renew No11 from here

    /**
     * WEB-API連携 割引適用結果取得を行います。
     *
     * @param favoriteDtos 商品DTOリスト
     * @return 割引適用結果取得MAP
     */
    public Map<String, WebApiGetDiscountsResponseDetailDto> executeWebApiGetDiscountsResult(List<FavoriteDto> favoriteDtos) {
        List<String> goodsCodeList = new ArrayList<>();
        List<String> orderDisplayList = new ArrayList<>();
        for (FavoriteDto favoriteDto : favoriteDtos) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            favoriteDto.getGoodsDetailsDto().getGoodsCode());
            String orderDisplay = String.valueOf(favoriteDto.getGoodsDetailsDto().getOrderDisplay());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
            if (!orderDisplayList.contains(orderDisplay)) {
                orderDisplayList.add(orderDisplay);
            }
        }

        try {
            WebApiGetDiscountsResultRequest webApiGetDiscountsResultRequest = new WebApiGetDiscountsResultRequest();
            webApiGetDiscountsResultRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
            webApiGetDiscountsResultRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
            webApiGetDiscountsResultRequest.setQuantity("1");
            webApiGetDiscountsResultRequest.setOrderDisplay(WebApiUtility.createStrPipeByList(orderDisplayList));

            WebApiGetDiscountsResultResponse webApiGetDiscountsResultResponse = null;
            try {
                webApiGetDiscountsResultResponse = webapiApi.getDiscountResult(webApiGetDiscountsResultRequest);

            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            WebApiGetDiscountsResponseDto discountsResponseDto =
                            goodsHelper.toWebApiGetDiscountsResponseDto(webApiGetDiscountsResultResponse);
            return discountsResponseDto.getMap();
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;
    }
    // 2023-renew No11 to here

    /**
     * WEB-API連携 取りおき情報取得
     *
     * @param goodsStockItems 在庫商品リスト
     * @return 連携取得結果DTOレスポンスリスト
     */
    protected WebApiGetReserveResponseDto executeWebApiGetReserve(List<GoodsStockItem> goodsStockItems) {
        List<String> goodsCodeList = new ArrayList<>();
        goodsStockItems.forEach(stockItem -> {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(stockItem.getGoodsCode());

            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        });

        // 取りおき情報取得APIのリクエストを生成する
        WebApiGetReserveRequest goodsGetReserveRequest = new WebApiGetReserveRequest();
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        MemberInfoEntity memberInfoEntity = commonInfoUtility.getMemberInfoEntity(getCommonInfo());
        Integer customerNo = memberInfoEntity.getCustomerNo();
        String zipcode = memberInfoEntity.getMemberInfoZipCode();
        goodsGetReserveRequest.setCustomerNo(customerNo);
        goodsGetReserveRequest.setDeliveryCustomerNo(customerNo);
        goodsGetReserveRequest.setDeliveryZipcode(zipcode);
        goodsGetReserveRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        // 取りおき情報取得API
        WebApiGetReserveResponse webapiGetReserveResponse = null;
        try {
            webapiGetReserveResponse = webapiApi.getReserve(goodsGetReserveRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        if (Objects.isNull(webapiGetReserveResponse)) {
            throwMessage("PDR-0015-001-A-");
        }

        return memberFavoriteHelper.toWebApiGetReserveResponseDto(webapiGetReserveResponse);
    }

    /**
     * WEB-API連携 販売可否判定
     *
     * @param goodsStockItems 在庫商品リスト
     * @return 販売可否判定 詳細情報レスポンスリスト
     */
    protected List<WebApiGetSaleCheckDetailResponse> executeWebApiGetSaleCheck(List<GoodsStockItem> goodsStockItems) {
        List<String> goodsCodeList = new ArrayList<>();
        goodsStockItems.forEach(stockItem -> {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(stockItem.getGoodsCode());

            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        });

        WebApiGetSaleCheckRequest webApiGetSaleCheckRequest = new WebApiGetSaleCheckRequest();
        webApiGetSaleCheckRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        webApiGetSaleCheckRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        // 販売可否判定API
        WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = null;
        try {
            webApiGetSaleCheckResponse = webapiApi.getSaleCheck(webApiGetSaleCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        if (Objects.isNull(webApiGetSaleCheckResponse)) {
            throwMessage("PDR-0015-001-A-");
        }
        return webApiGetSaleCheckResponse.getInfo();
    }

    // 2023-renew No11 to here

    /**
     * WEB-API販売可否判定チェック
     *
     * @param memberFavoriteModel 商品詳細ページ
     * @param favoriteItem 商品詳細Item
     *
     */
    public void executeWebApiGetSaleCheck(MemberFavoriteModel memberFavoriteModel, GoodsStockItem favoriteItem) {
        Integer customerNo = commonInfoUtility.getCustomerNo(memberFavoriteModel.getCommonInfo());
        WebApiGetSaleCheckRequest webApiGetSaleCheckRequest = new WebApiGetSaleCheckRequest();
        webApiGetSaleCheckRequest.setCustomerNo(customerNo);
        webApiGetSaleCheckRequest.setGoodsCode(favoriteItem.getGoodsCode());
        WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = webapiApi.getSaleCheck(webApiGetSaleCheckRequest);
        if (CollectionUtil.isNotEmpty(webApiGetSaleCheckResponse.getInfo())) {
            for (WebApiGetSaleCheckDetailResponse res : webApiGetSaleCheckResponse.getInfo())
                if (res.getGoodsCode().equals(favoriteItem.getGoodsCode())) {
                    favoriteItem.setSaleCheck(res.getGoodsSaleYesNo());
                }
        }
    }

    /**
     * WEB-API連携 商品在庫数取得を行います。
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> executeWebApiGetStock(List<FavoriteDto> goodsDtoList) {
        List<String> goodsCodeList = new ArrayList<String>();
        for (FavoriteDto goodsDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            goodsDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        WebApiGetStockRequest webApiGetStockRequest = new WebApiGetStockRequest();
        webApiGetStockRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        WebApiGetStockResponse webApiGetStockResponse = null;
        try {
            try {
                webApiGetStockResponse = webapiApi.getStock(webApiGetStockRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            WebApiGetStockResponseDto stockDto = goodsHelper.toWebApiGetStockResponseDto(webApiGetStockResponse);

            return stockDto.getMap();
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;
    }

    /**
     * WEB-API連携 数量割引情報取得を行います。
     *
     * @param goodsDtoList    商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    protected WebApiGetQuantityDiscountResponseDto executeWebApiGetQuantityDiscount(List<FavoriteDto> goodsDtoList) {

        List<String> goodsCodeList = new ArrayList<>();
        for (FavoriteDto goodsDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            goodsDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        WebApiGetQuantityDiscountRequest webApiGetQuantityDiscountRequest = new WebApiGetQuantityDiscountRequest();
        webApiGetQuantityDiscountRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        webApiGetQuantityDiscountRequest.setCustomerNo(commonInfoUtility.isLogin(getCommonInfo()) ?
                                                                       commonInfoUtility.getCustomerNo(
                                                                                       getCommonInfo()) :
                                                                       null);
        WebApiGetQuantityDiscountResponse webApiGetQuantityDiscountResponse = null;
        try {
            webApiGetQuantityDiscountResponse = webapiApi.getQuantityDiscount(webApiGetQuantityDiscountRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        WebApiGetQuantityDiscountResponseDto quantityDiscountDto =
                        goodsHelper.toWebApiGetQuantityDiscountResponseDto(webApiGetQuantityDiscountResponse);
        if (quantityDiscountDto == null) {
            throwMessage("PDR-0015-001-A-");
        }

        // 詳細情報が返却されなかった場合
        if (CollectionUtil.isEmpty(quantityDiscountDto.getInfo())) {
            // ログを出力しエラーページへ遷移
            LOGGER.error("数量割引適用結果取得連携で詳細情報が取得できません。顧客番号：" + webApiGetQuantityDiscountRequest.getCustomerNo() + " 申込商品:"
                         + webApiGetQuantityDiscountRequest.getGoodsCode());
            throwMessage("PDR-0015-001-A-");
        }

        return quantityDiscountDto;
    }
    // 2023-renew No11 to here

    // PDR Migrate Customization from here

    /**
     * 個別カートイン処理
     *
     * @return ページクラス
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doAddCart")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/favorite/index")
    public String doAddCart(MemberFavoriteModel memberFavoriteModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // クリックした「カートに入れる」ボタンと対になる注文数のチェック
        GoodsItem goodsItem = memberFavoriteModel.getFavoriteItems().get(memberFavoriteModel.getFavoriteIndex());
        if (goodsItem.getGcnt() == null) {
            String name = "memberFavoriteModel.getDefaultFormId()" + ":" + "favoriteItems" + ":"
                          + memberFavoriteModel.getFavoriteIndex() + ":" + "gcnt";
            throwMessage(MSGCD_REQUIRED_INPUT, new Object[] {"注文数"}, name, null);
        }

        // カートへ引き継ぐ項目をセット
        memberFavoriteModel.setGcd(goodsItem.getGcd());

        // カートに遷移
        return "redirect:/cart/index.html";

    }

    /**
     * まとめてカートイン処理
     * <p>
     * カート一括登録用のロジックを流用して、まとめてカートインする
     *
     * @return ページクラス
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doAddCartAll")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/favorite/index")
    public String doAddCartAll(MemberFavoriteModel memberFavoriteModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model,
                               HttpServletRequest request) {

        // カート一括登録用引継ぎDTOを作成
        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList = setPageForGoodsDetails(memberFavoriteModel);

        // カート内最大商品件数超過チェック
        CatalogHelper catalogHelper = ApplicationContextUtility.getBean(CatalogHelper.class);
        CartGoodsRegistCheckRequest cartGoodsRegistCheckRequest = new CartGoodsRegistCheckRequest();
        cartGoodsRegistCheckRequest.setCartGoodsForTakeOverDtoListRequest(
                        catalogHelper.toCartGoodsForTakeOverDtoRequestList(cartGoodsForTakeOverDtoList));
        cartGoodsRegistCheckRequest.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        cartGoodsRegistCheckRequest.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());

        // カート内最大商品件数超過チェック
        try {
            cartApi.checkCartGoodsRegist(cartGoodsRegistCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 作成したDtoをセッションに格納する
        redirectAttributes.addFlashAttribute(CART_GOODS_DTO_LIST_KEY, cartGoodsForTakeOverDtoList);
        redirectAttributes.addFlashAttribute("accessUid", cartGoodsRegistCheckRequest.getAccessUid());
        redirectAttributes.addFlashAttribute("memberInfoSeq", cartGoodsRegistCheckRequest.getMemberInfoSeq());
        // カートに遷移
        return "redirect:/cart/index.html";

    }

    /**
     * 商品詳細情報を取得し、Pageクラスにセットする
     * また、カート一括登録用引継DTOを作成し、返却する
     *
     * @return カート一括登録用引継DTOリスト
     */
    protected List<CartGoodsForTakeOverDto> setPageForGoodsDetails(MemberFavoriteModel memberFavoriteModel) {
        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList = new ArrayList<>();

        //
        for (int i = 0; i < memberFavoriteModel.getFavoriteItems().size(); i++) {
            GoodsItem goodsItem = memberFavoriteModel.getFavoriteItems().get(i);

            // 注文数を入力があったもののみ処理対象とする
            if (goodsItem.getGcnt() == null) {
                continue;
            }

            // カート一括登録用引継DTOを作成
            CartGoodsForTakeOverDto cartGoodsForTakeOverDto =
                            ApplicationContextUtility.getBean(CartGoodsForTakeOverDto.class);
            // 商品グループSEQ
            cartGoodsForTakeOverDto.setGoodsGroupSeq(goodsItem.getGoodsGroupSeq());
            // 商品SEQ
            cartGoodsForTakeOverDto.setGoodsSeq(goodsItem.getGoodsSeq());
            // 商品番号
            cartGoodsForTakeOverDto.setGoodsCode(goodsItem.getGcd());
            // 商品数量
            cartGoodsForTakeOverDto.setGoodsCount(conversionUtility.toBigDecimal(goodsItem.gcnt));
            cartGoodsForTakeOverDtoList.add(cartGoodsForTakeOverDto);
        }

        // 画面から入力された順序のListをそのままカート画面に渡すと、順序が逆に表示されてしまう
        // そのため、後ろに入力された商品から順にListを作り直す
        Collections.reverse(cartGoodsForTakeOverDtoList);

        return cartGoodsForTakeOverDtoList;
    }
    // PDR Migrate Customization to here
}
