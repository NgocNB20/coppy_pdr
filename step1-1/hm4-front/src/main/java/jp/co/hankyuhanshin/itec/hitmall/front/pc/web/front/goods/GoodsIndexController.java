/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsFootPrintDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsFootPrintRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListByGoodsCodesGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupTogetherBuyRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenCategoryPassListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenFootPrintListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenGoodsGroupDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenRelatedGoodsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteForGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsResponse;
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
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCustomerSaleFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.footprint.FootprintEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.inquiry.InquiryController;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ListUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品詳細画面 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@SessionAttributes(value = "goodsIndexModel")
@RequestMapping("/goods/")
@Controller
public class GoodsIndexController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsIndexController.class);

    /**
     * 商品詳細画面
     */
    public static final String FLASH_FROM_GOODS_DETAILS = "fromGoodsDetails";

    /**
     * 商品情報取得スキップフラグ
     */
    public static final String FLASH_SKIP_GETGOODSINFO = "skipGetGoodsInfo";

    /**
     * 商品詳細画面：あしあと商品デフォルト件数
     */
    public static final String DEFAULT_FOOT_PRINT_GOODS_LIMIT = "10";

    /**
     * 商品詳細画面：お気に入り商品デフォルト件数
     */
    public static final String DEFAULT_FAVORITE_GOODS_LIMIT = "6";

    /**
     * 商品詳細画面：関連商品デフォルト件数
     */
    public static final String DEFAULT_RELATED_GOODS_LIMIT = "6";

    // 2023-renew No5 from here
    /**
     * 商品詳細画面：価格情報最大文字数
     */
    public static final Integer MAX_LENGTH_PRICE_INFO_LIMIT = 10;
    // 2023-renew No5 to here

    // 2023-renew No21 from here
    /**
     * 商品詳細画面：一緒に購入される製品のデフォルト数
     */
    public static final String DEFAULT_TOGETHER_BUY_GOODS_LIMIT = "6";
    // 2023-renew No21 to here

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * 商品API
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * WebapiApi
     */
    private final WebapiApi webapiApi;

    /**
     * 商品詳細画面Helper
     */
    private final GoodsIndexHelper goodsIndexHelper;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * PageInfoヘルパー
     */
    private final PageInfoHelper pageInfoHelper;

    /**
     * 商品Helper
     */
    private final GoodsHelper goodsHelper;

    // PDR Migrate Customization from here
    /**
     * 商品系ヘルパー
     */
    private final GoodsUtility goodsUtility;

    // PDR Migrate Customization to here

    /**
     * コンストラクタ
     *
     * @param goodsApi          商品API
     * @param memberInfoApi     会員情報API
     * @param webapiApi         Web-API API
     * @param goodsIndexHelper  商品検索画面 Helper
     * @param commonInfoUtility 共通情報ヘルパークラス
     * @param pageInfoHelper    PageInfoヘルパー
     * @param goodsHelper       商品Helper
     * @param goodsUtility      商品系ヘルパー
     */
    @Autowired
    public GoodsIndexController(GoodsApi goodsApi,
                                MemberInfoApi memberInfoApi,
                                WebapiApi webapiApi,
                                GoodsIndexHelper goodsIndexHelper,
                                CommonInfoUtility commonInfoUtility,
                                PageInfoHelper pageInfoHelper,
                                GoodsHelper goodsHelper,
                                GoodsUtility goodsUtility) {
        this.goodsApi = goodsApi;
        this.memberInfoApi = memberInfoApi;
        this.webapiApi = webapiApi;
        this.goodsIndexHelper = goodsIndexHelper;
        this.commonInfoUtility = commonInfoUtility;
        this.pageInfoHelper = pageInfoHelper;
        this.goodsHelper = goodsHelper;
        this.goodsUtility = goodsUtility;
    }

    /**
     * 商品詳細画面：初期処理
     *
     * @param goodsIndexModel     商品詳細画面 Model
     * @param gcd                 商品コード
     * @param ggcd                商品グループコード
     * @param cid                 カテゴリID
     * @param footPrintGoodsLimit あしあと商品件数
     * @param favoriteGoodsLimit  お気に入り商品件数
     * @param relatedGoodsLimit   関連商品件数
     * @param togetherBuyGoodsLimit   一緒に購入した商品の数
     * @param model               モデル
     * @return 商品詳細画面
     */
    @GetMapping(value = {"/index", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error")
    protected String doLoadIndex(GoodsIndexModel goodsIndexModel,
                                 @RequestParam(required = false) String gcd,
                                 @RequestParam(required = false) String ggcd,
                                 @RequestParam(required = false) String cid,
                                 @RequestParam(required = false, defaultValue = DEFAULT_FOOT_PRINT_GOODS_LIMIT)
                                                 int footPrintGoodsLimit,
                                 @RequestParam(required = false, defaultValue = DEFAULT_FAVORITE_GOODS_LIMIT)
                                                 int favoriteGoodsLimit,
                                 @RequestParam(required = false, defaultValue = DEFAULT_RELATED_GOODS_LIMIT)
                                                 int relatedGoodsLimit,
                                 // 2023-renew No21 from here
                                 @RequestParam(required = false, defaultValue = DEFAULT_TOGETHER_BUY_GOODS_LIMIT)
                                                 int togetherBuyGoodsLimit,
                                 // 2023-renew No21 to here
                                 Model model) {

        // 商品情報の再取得が必要であれば
        if (isNeedGoodsInfo(goodsIndexModel, model)) {

            // 商品詳細画面モデルをクリア
            clearModel(GoodsIndexModel.class, goodsIndexModel, model);

            // 商品数量に1をセットする
            goodsIndexModel.setGcnt("1");
            // リクエストパラメータのgcd・ggcd・cidをセット
            goodsIndexModel.setGcd(gcd);
            goodsIndexModel.setGgcd(ggcd);
            goodsIndexModel.setCid(cid);

            if (StringUtil.isNotEmpty(gcd)) {
                goodsIndexModel.setGcdPath(true);
            } else {
                goodsIndexModel.setGcdPath(false);
            }

            // 画面表示処理
            try {
                // 2023-renew No21 from here
                setUpGoodsInfo(goodsIndexModel, footPrintGoodsLimit, favoriteGoodsLimit, relatedGoodsLimit,
                               togetherBuyGoodsLimit
                              );
                // 2023-renew No21 to here
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

        }

        // あしあと商品登録
        footPrintRegist(goodsIndexModel.getGoodsGroupSeq());

        return "goods/index";
    }

    /**
     * あしあと情報クリア処理(Ajax)
     * あしあとを削除するボタン押下時に呼び出される。
     * 自分のあしあと情報を全てクリアする。
     *
     * @param goodsIndexModel 商品詳細画面 Model
     */
    @GetMapping("/doClearAccessGoods")
    @ResponseBody
    public void doClearAccessGoods(GoodsIndexModel goodsIndexModel) {
        // あしあと商品クリアサービス実行
        GoodsFootPrintDeleteRequest goodsFootPrintDeleteRequest = new GoodsFootPrintDeleteRequest();

        goodsFootPrintDeleteRequest.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());

        try {
            goodsApi.deleteFootprintGoods(goodsFootPrintDeleteRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // セッションで保持しているモデルの足あと履歴をクリア
        goodsIndexModel.setFootPrintGoodsItems(null);
    }

    /**
     * お気に入り商品追加処理
     * ・ゲストがお気に入り追加した場合
     * ・Ajax通信でエラー発生した場合
     * にcall
     *
     * @param goodsIndexModel    商品詳細画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return ページクラス
     */
    @PostMapping(value = {"/index", "/index.html"}, params = "doFavoriteAdd")
    public String doFavoriteAdd(GoodsIndexModel goodsIndexModel, RedirectAttributes redirectAttributes, Model model) {

        if (!StringUtils.isEmpty(goodsIndexModel.getGcd())) {
            redirectAttributes.addFlashAttribute("gcd", goodsIndexModel.getGcd());
        } else {
            // gcdが不足している場合、エラーメッセージ＋エラー画面を表示
            // goods/detailにredirectしても、最終的にエラーメッセージ＋エラー画面のため、この時点でエラー画面に遷移させる
            // 現行ではエラーメッセージ＋商品詳細画面を返すので、
            // それに合わせて、return goods/detail とすると、パラメータが残ってしまうので、ひとまずredirect:/errorで対応　
            addMessage(GoodsIndexModel.MSGCD_GOODS_NOT_FOUND_ERROR, redirectAttributes, model);
            return "redirect:/error.html";
        }

        return "redirect:/member/favorite/index.html";
    }

    // 2023-renew No65 from here

    /**
     * 入荷お知らせ商品追加処理
     *
     * @param goodsIndexModel    商品詳細画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return ページクラス
     */
    @PostMapping(value = {"/index", "/index.html"}, params = "doRestockAdd")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/member/restock/index.html")
    public String doRestockAdd(GoodsIndexModel goodsIndexModel, RedirectAttributes redirectAttributes, Model model) {

        if (!StringUtils.isEmpty(goodsIndexModel.getGcdStock())) {
            RestockAnnounceRegistRequest restockAnnounceRegistRequest = new RestockAnnounceRegistRequest();
            restockAnnounceRegistRequest.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
            restockAnnounceRegistRequest.setGcd(goodsIndexModel.getGcdStock());
            try {
                memberInfoApi.registRestockAnnounce(restockAnnounceRegistRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                addAppLevelListException(e);
                throwMessage();
            }
        } else {
            // gcdが不足している場合、エラーメッセージ＋エラー画面を表示
            // goods/detailにredirectしても、最終的にエラーメッセージ＋エラー画面のため、この時点でエラー画面に遷移させる
            // 現行ではエラーメッセージ＋商品詳細画面を返すので、
            // それに合わせて、return goods/detail とすると、パラメータが残ってしまうので、ひとまずredirect:/errorで対応　
            addMessage(GoodsIndexModel.MSGCD_GOODS_NOT_FOUND_ERROR, redirectAttributes, model);
        }

        return "redirect:/member/restock/index.html";
    }
    // 2023-renew No65 to here

    /**
     * Direct to 会員お気に入り
     *
     * @param goodsIndexModel    商品詳細画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return
     */
    @PostMapping(value = {"/index", "/index.html"}, params = "goFavoriteView")
    public String goFavoriteView(GoodsIndexModel goodsIndexModel, RedirectAttributes redirectAttributes, Model model) {

        if (goodsIndexModel.isGcdPath()) {

            String[] gcdArray = goodsIndexModel.getGcd().split(",");

            redirectAttributes.addFlashAttribute("gcd", gcdArray[0]);
        } else {
            redirectAttributes.addFlashAttribute("ggcd", goodsIndexModel.getGgcd());
        }

        redirectAttributes.addFlashAttribute(FLASH_FROM_GOODS_DETAILS, true);
        return "redirect:/member/favorite/index.html";
    }

    /**
     * redirect to inquiry page
     *
     * @param goodsIndexModel    商品詳細画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return お問い合わせ画面
     */
    @PostMapping(value = {"/detail", "/detail.html"}, params = "doInquiry")
    public String doInquiry(GoodsIndexModel goodsIndexModel, RedirectAttributes redirectAttributes, Model model) {

        // 商品コードをお問い合わせ画面に引き継ぐ
        redirectAttributes.addFlashAttribute(InquiryController.FLASH_GCD, goodsIndexModel.getGcd());
        // 商品グループコードをお問い合わせ画面に引き継ぐ
        redirectAttributes.addFlashAttribute(InquiryController.FLASH_GGCD, goodsIndexModel.getGgcd());

        if (StringUtils.isNotEmpty(goodsIndexModel.getUnitSelect1())) {
            if (goodsIndexModel.getUnitSelect1Items() != null) {

                for (Map.Entry<String, String> unitSelect1ItemEntry : goodsIndexModel.getUnitSelect1Items()
                                                                                     .entrySet()) {
                    if (unitSelect1ItemEntry.getKey().equals(goodsIndexModel.getUnitSelect1())) {
                        // 選択された商品規格1の値をお問い合わせ画面に引き継ぐ
                        redirectAttributes.addFlashAttribute(
                                        InquiryController.FLASH_UNIT, unitSelect1ItemEntry.getValue());
                        break;
                    }
                }
            }
        }

        return "redirect:/inquiry/";
    }

    /**
     * 商品情報の再取得が必要か判断する
     *
     * @param goodsIndexModel 商品詳細画面 Model
     * @param model           モデル
     * @return true:必要、false:不要
     */
    protected boolean isNeedGoodsInfo(GoodsIndexModel goodsIndexModel, Model model) {

        // 商品情報取得スキップフラグが設定されていれば
        if (model.containsAttribute(FLASH_SKIP_GETGOODSINFO)) {
            // 商品情報の再取得は不要
            return false;
        }

        // 商品情報の再取得は必要
        return true;

    }

    /**
     * 商品情報の取得と設定
     * 商品情報が取得できない場合はエラー画面に遷移させる（URL直接遷移・非公開商品等）
     *
     * @param goodsIndexModel     商品詳細画面 Model
     * @param footPrintGoodsLimit あしあと商品件数
     * @param favoriteGoodsLimit  お気に入り商品件数
     * @param relatedGoodsLimit   関連商品件数
     */
    protected void setUpGoodsInfo(GoodsIndexModel goodsIndexModel,
                                  int footPrintGoodsLimit,
                                  int favoriteGoodsLimit,
                                  int relatedGoodsLimit,
                                  // 2023-renew No21 from here
                                  int togetherBuyGoodsLimit) {
        // 2023-renew No21 to here

        // 遷移元のカテゴリ情報をページにセット
        setCategoryInfo(goodsIndexModel);

        // 商品グループコード、商品コードの両方が指定されていない場合はエラーとする
        // ブラウザバック対応
        if (StringUtils.isEmpty(goodsIndexModel.getGgcd()) && StringUtils.isEmpty(goodsIndexModel.getGcd())) {
            // PDR Migrate Customization from here
            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
            this.throwMessage(GoodsIndexModel.MSGCD_GOODS_CODE_GET_ERROR, new Object[] {appComplementUrl});
            // PDR Migrate Customization to here
        }

        // 商品情報取得
        OpenGoodsGroupDetailsGetRequest openGoodsGroupDetailsGetRequest = new OpenGoodsGroupDetailsGetRequest();

        openGoodsGroupDetailsGetRequest.setGoodsGroupCode(goodsIndexModel.getGgcd());
        openGoodsGroupDetailsGetRequest.setGoodsCode(goodsIndexModel.getGcd());

        GoodsGroupDtoResponse goodsGroupDtoResponse = null;
        try {
            goodsGroupDtoResponse = goodsApi.getOpenGoodsGroupDetails(openGoodsGroupDetailsGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        GoodsGroupDto goodsGroupDto = goodsHelper.toGoodsGroupDto(goodsGroupDtoResponse);

        if (goodsGroupDto == null || goodsGroupDto.getGoodsGroupEntity() == null) {
            throwMessage(GoodsIndexModel.MSGCD_GOODS_NOT_FOUND_ERROR);
        }

        // 2023-renew No11 from here
        List<GoodsUnitImageItem> goodsUnitImageItems = new ArrayList<>();
        try {
            goodsIndexHelper.toPageForLoad(goodsGroupDto, goodsIndexModel, goodsUnitImageItems);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // 2023-renew No11 to here
        // PDR Migrate Customization from here

        // マイリスト投入後の商品区分変更やURL直接遷移を考慮し、閲覧不可の商品の場合はエラーとする
        // 2023-renew No2 from here

        // 2023-renew No2 to here
        // PDR Migrate Customization to here

        // 2023-renew No11 from here
        // excludeNoSaleGoodsFromStockListを削除
        // 2023-renew No11 to here

        // 公開あしあと商品詳細情報リスト取得サービス実行
        OpenFootPrintListGetRequest openFootPrintListGetRequest = new OpenFootPrintListGetRequest();

        openFootPrintListGetRequest.setLimit(footPrintGoodsLimit);
        openFootPrintListGetRequest.setExceptGoodsGroupSeq(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
        openFootPrintListGetRequest.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());

        GoodsGroupDtoListResponse goodsGroupDtoListResponse = null;
        try {
            goodsGroupDtoListResponse = goodsApi.getFootprintOpenList(openFootPrintListGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        List<GoodsGroupDto> footprintGoodsGroupDtoList = null;
        if (Objects.nonNull(goodsGroupDtoListResponse)) {
            footprintGoodsGroupDtoList =
                            goodsHelper.toGoodsGroupDtoList(goodsGroupDtoListResponse.getGoodsGroupDtoListResponse());
        }

        if (CollectionUtil.isEmpty(footprintGoodsGroupDtoList)) {
            goodsIndexModel.setFootPrintGoodsItems(null);

        } else {
            try {
                goodsIndexHelper.toPageForLoadFootprint(footprintGoodsGroupDtoList, goodsIndexModel);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        }

        List<FavoriteDto> favoriteDtoList = null;
        // ※ゲストの場合は、この処理を行わない。
        if (commonInfoUtility.isLogin(getCommonInfo())) {
            FavoriteSearchForDaoConditionDto favoriteConditionDto =
                            goodsIndexHelper.toFavoriteConditionDtoForSearchFavoriteList(favoriteGoodsLimit,
                                                                                         goodsIndexModel
                                                                                        );
            // お気に入り情報リスト取得サービス実行
            FavoriteListGetRequest favoriteListGetRequest = goodsHelper.toFavoriteListGetRequest(favoriteConditionDto);

            PageInfoRequest pageInfoRequest = new PageInfoRequest();
            pageInfoHelper.setupPageRequest(pageInfoRequest, favoriteConditionDto.getPageInfo().getPnum(),
                                            favoriteConditionDto.getPageInfo().getLimit(),
                                            favoriteConditionDto.getPageInfo().getOrderField(),
                                            favoriteConditionDto.getPageInfo().isOrderAsc()
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

            if (Objects.nonNull(favoriteDtoListResponse)) {
                favoriteDtoList = goodsHelper.toFavoriteDtoList(favoriteDtoListResponse.getFavoriteDtoListResponse());
            }

            // お気に入り情報に在庫状況表示を付与
            List<FavoriteDto> stockFavoriteDtoList = null;

            FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest =
                            new FavoriteGoodsStockStatusGetRequest();

            List<FavoriteDtoRequest> favoriteDtoListRequest = goodsHelper.toFavoriteDtoListRequest(favoriteDtoList);

            favoriteGoodsStockStatusGetRequest.setFavoriteDtoListRequest(favoriteDtoListRequest);

            FavoriteDtoListResponse stockFavoriteDtoListResponse = null;
            try {
                stockFavoriteDtoListResponse =
                                memberInfoApi.getFavoriteByFavoriteGoodsStockStatus(favoriteGoodsStockStatusGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            if (Objects.nonNull(stockFavoriteDtoListResponse)) {
                stockFavoriteDtoList = goodsHelper.toFavoriteDtoList(
                                stockFavoriteDtoListResponse.getFavoriteDtoListResponse());
            }

            if (CollectionUtil.isNotEmpty(stockFavoriteDtoList)) {
                try {
                    // 2023-renew No11 from here
                    this.setGoodsFavoriteInfo(stockFavoriteDtoList, goodsIndexModel);
                    // 2023-renew No11 to here
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }
            }

            Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

            FavoriteForGoodsCheckRequest favoriteForGoodsCheckRequest = new FavoriteForGoodsCheckRequest();

            favoriteForGoodsCheckRequest.setGoodsDtoList(
                            goodsHelper.toGoodsDtoListRequest(goodsGroupDto.getGoodsDtoList()));
            favoriteForGoodsCheckRequest.setMemberInfoSeq(memberInfoSeq);

            List<FavoriteEntityResponse> favoriteEntityResponseList = null;
            try {
                favoriteEntityResponseList = memberInfoApi.getFavoriteForGoodsCheck(favoriteForGoodsCheckRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            List<FavoriteEntity> favoriteList = goodsHelper.toFavoriteEntityList(favoriteEntityResponseList);
            goodsIndexHelper.setFavoriteView(favoriteList, goodsGroupDto, goodsIndexModel);
        }

        // 関連商品表示処理
        goodsRelationDisplay(relatedGoodsLimit, goodsIndexModel);

        // PDR Migrate Customization from here
        Set<String> orderedSet = new LinkedHashSet<>();
        // 全規格の在庫状況一覧設定
        setGoodsStockInfo(goodsGroupDto, goodsIndexModel, goodsUnitImageItems, orderedSet);
        // PDR Migrate Customization to here

        // 2023-renew No5 from here
        // 価格情報をマージする
        goodsIndexModel.setGoodsStockInfoItems(
                        goodsUtility.mergePriceInfo(goodsIndexModel.getGoodsStockInfoItems(), orderedSet, LOGGER));
        // 複数数量閾値フラグ設定
        goodsUtility.setMultiLevelFlag(goodsIndexModel.getGoodsStockInfoItems());

        // 商品詳細にセール期間を表示
        if (CollectionUtil.isNotEmpty(goodsIndexModel.getGoodsStockInfoItems())) {
            Timestamp firstSaleEndDay = null;
            for (GoodsStockItem goodsStockItem : goodsIndexModel.getGoodsStockInfoItems()) {
                if (goodsStockItem.isValidSaleEndDay()) {
                    if (Objects.isNull(firstSaleEndDay) || goodsStockItem.getSaleEndDay().before(firstSaleEndDay)) {
                        firstSaleEndDay = goodsStockItem.getSaleEndDay();
                    }
                }
                goodsIndexModel.setSaleEndDay(firstSaleEndDay);
            }
        }
        // 2023-renew No5 to here

        // 2023-renew No11 from here
        if (!goodsIndexModel.getGoodsStockInfoItems().isEmpty()) {
            // 日付関連Helper取得
            DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
            List<GoodsStockItem> goodsDtoList = new ArrayList<>();
            goodsIndexModel.getGoodsStockInfoItems().forEach(goodsDto -> {
                // 販売前 -> 規格一覧自体非表示
                if (!(HTypeGoodsSaleStatus.SALE.equals(goodsDto.getSaleStatus()) && dateHelper.isBeforeSale(
                                goodsDto.getSaleStartTime(), dateHelper.getCurrentTime()))) {
                    goodsDtoList.add(goodsDto);
                }
            });
            goodsIndexModel.setGoodsStockInfoItems(goodsDtoList);
        }
        // 2023-renew No11 to here

        // 2023-renew No21 from here
        // よく一緒に購入される商品
        getTogetherBuyGoodsList(goodsIndexModel, togetherBuyGoodsLimit);
        // 2023-renew No21 to here

    }
    // PDR Migrate Customization from here

    // 2023-renew No11 from here

    /**
     * お気に入りの商品情報の設定
     *
     * @param favoriteDtoList   お気に入りDTOリスト
     * @param goodsIndexModel   商品詳細画面 Model
     */
    protected void setGoodsFavoriteInfo(List<FavoriteDto> favoriteDtoList, GoodsIndexModel goodsIndexModel) {
        goodsIndexHelper.toPageForLoadFavorite(favoriteDtoList, goodsIndexModel);

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockMap = executeWebApiGetStockByFavoriteList(favoriteDtoList);

        goodsIndexModel.getFavoriteGoodsItems().forEach(favoriteGoodsItem -> {
            if (favoriteGoodsItem.getStockQuantity() == null) {
                favoriteGoodsItem.setStockQuantity(BigDecimal.ZERO);
            }

            if (Objects.nonNull(stockMap) && Objects.nonNull(stockMap.get(favoriteGoodsItem.getGoodsCode()))) {
                favoriteGoodsItem.setSaleControl(stockMap.get(favoriteGoodsItem.getGoodsCode()).getSaleControl());
                favoriteGoodsItem.setStockQuantity(stockMap.get(favoriteGoodsItem.getGoodsCode()).getStockQuantity());
            }
        });

        if (commonInfoUtility.isLogin(getCommonInfo())) {
            // 販売可否判定API
            List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                            this.executeWebApiGetSaleCheck(goodsIndexModel.getFavoriteGoodsItems());
            if (CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses)) {
                goodsIndexModel.getFavoriteGoodsItems().forEach(stockItem -> {
                    webApiGetSaleCheckDetailResponses.forEach(res -> {
                        if (Objects.equals(res.getGoodsCode(), stockItem.getGoodsCode())) {
                            stockItem.setSaleCheck(res.getGoodsSaleYesNo());
                        }
                    });
                });
            }
        }
    }
    // 2023-renew No11 to here

    /**
     * 商品在庫情報の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細ページ
     * @param customParams    案件用引数
     */
    protected void setGoodsStockInfo(GoodsGroupDto goodsGroupDto,
                                     GoodsIndexModel goodsIndexModel,
                                     Object... customParams) {
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();
        // 在庫表示用リスト
        List<GoodsStockItem> goodsStockItems = new ArrayList<>();

        // 2023-renew No2 from here
        AtomicReference<HTypeSaleCheckType> salable = new AtomicReference<>(HTypeSaleCheckType.NO);
        // 2023-renew No2 to here

        // 2023-renew No11 from here
        AtomicBoolean isAllSaleControlDiscontinues = new AtomicBoolean(true);
        List<GoodsStockItem> goodsOutOfDates = new ArrayList<>();
        List<GoodsStockItem> goodsNoSales = new ArrayList<>();
        List<GoodsStockItem> goodsNoSalesOrOutOfDates = new ArrayList<>();
        // 2023-renew No11 to here

        // 共通情報Helper取得
        // 2023-renew No11 from here
        // WEB-API（数量割引情報取得）から数量割引情報を取得する
        WebApiGetQuantityDiscountResponseDto getQuantityDiscountDto =
                        executeWebApiGetQuantityDiscount(goodsDtoList, goodsIndexModel);

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockMap = executeWebApiGetStock(goodsDtoList);

        WebApiGetStockResponseDetailDto webApiGetStockResponseDetailPdrDto;

        // 扱いやすいように商品番号をキーにしたマップに詰め替え
        // 2023-renew No11 from here
        Map<String, GoodsDto> goodsDtoMap = new LinkedHashMap<>();
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            goodsDtoMap.put(goodsDto.getGoodsEntity().getGoodsCode(), goodsDto);
        }
        if (customParams.length > 1) {
            ((LinkedHashSet<String>) customParams[1]).addAll(goodsDtoMap.keySet());
        }
        // 2023-renew No11 to here

        // セール価格が存在するかどうかのフラグ
        goodsIndexModel.setPattern1Use(false);
        goodsIndexModel.setPattern2Use(false);

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
                goodsIndexModel.setExistSalePrice(true);
            } else {
                // 価格パターン分表示
                rowSpanCount = responseInfo.getPriceList().size();
            }

            // 顧客セールアイコン
            if (HTypeCustomerSaleFlag.ON.getValue().equals(String.valueOf(responseInfo.getCustomerSaleFlag()))) {
                goodsIndexModel.setCustomerSaleIconFlag(true);
            }

            for (int i = 0; i < rowSpanCount; i++) {
                GoodsStockItem goodsItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
                // 2023-renew No5 from here
                if (responseInfo.getSaleEndDay() != null) {
                    goodsItem.setSaleEndDay(responseInfo.getSaleEndDay());
                }
                // 2023-renew No5 to here
                // 結合数
                goodsItem.setGoodsCodeRowSpan(rowSpanCount);

                // 商品番号
                goodsItem.setGoodsCode(responseInfo.getGoodsCode());

                if (goodsDtoMap.get(responseInfo.getGoodsCode()) != null
                    && goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity().getUnitValue1() != null
                    && !goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity().getUnitValue1().equals("")) {
                    // 規格１
                    goodsItem.setPatternTitle1(
                                    goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity().getUnitValue1());
                    goodsIndexModel.setPattern1Use(true);
                }
                if (goodsDtoMap.get(responseInfo.getGoodsCode()) != null
                    && goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity().getUnitValue2() != null
                    && !goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity().getUnitValue2().equals("")) {
                    // 規格２
                    goodsItem.setPatternTitle2(
                                    goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity().getUnitValue2());
                    goodsIndexModel.setPattern2Use(true);
                }

                // 在庫
                if (stockMap != null) {
                    webApiGetStockResponseDetailPdrDto = stockMap.get(responseInfo.getGoodsCode());

                    // 在庫情報が取得できない場合はログを出力し、在庫なしとして処理を進める
                    if (webApiGetStockResponseDetailPdrDto == null) {
                        LOGGER.warn("商品番号：" + responseInfo.getGoodsCode() + "の在庫情報が取得できませんでした。在庫なしとして進めます。");
                        webApiGetStockResponseDetailPdrDto =
                                        ApplicationContextUtility.getBean(WebApiGetStockResponseDetailDto.class);
                        webApiGetStockResponseDetailPdrDto.setStockQuantity(BigDecimal.ZERO);
                    } else if (webApiGetStockResponseDetailPdrDto.getSaleControl() == null || (
                                    webApiGetStockResponseDetailPdrDto.getSaleControl() != null
                                    && !HTypeSaleControlType.DISCONTINUED.equals(
                                                    webApiGetStockResponseDetailPdrDto.getSaleControl()))) {
                        isAllSaleControlDiscontinues.set(false);
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
                GoodsEntity goodsEntity = goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsEntity();
                if (goodsUtility.isGoodsItemNoSales(goodsEntity.getSaleStatusPC(), goodsEntity.getSaleStartTimePC(),
                                                    goodsEntity.getSaleEndTimePC()
                                                   )) {
                    // 非販売の場合 在庫表示に[×」を設定
                    goodsItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                } else {
                    // 販売中の場合
                    HTypeStockManagementFlag stockManagementFlag = goodsDtoMap.get(responseInfo.getGoodsCode())
                                                                              .getGoodsEntity()
                                                                              .getStockManagementFlag();
                    // 売切対象商品の場合
                    int stockQuantity = webApiGetStockResponseDetailPdrDto.getStockQuantity().intValue();
                    int remainStockQuantity = goodsDtoMap.get(responseInfo.getGoodsCode())
                                                         .getStockDto()
                                                         .getRemainderFewStock()
                                                         .intValue();
                    if (HTypeStockManagementFlag.ON.equals(stockManagementFlag)) {

                        if (stockQuantity > remainStockQuantity) {
                            goodsItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                        } else if (remainStockQuantity >= stockQuantity && stockQuantity > 0 || (stockQuantity > 0
                                                                                                 && "1".equals(
                                        webApiGetStockResponseDetailPdrDto.getDeliveryYesNo()))) {
                            goodsItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                        } else if (stockQuantity == 0 && "0".equals(
                                        webApiGetStockResponseDetailPdrDto.getDeliveryYesNo())) {
                            goodsItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                        }
                    } else {
                        // 売切対象外商品の場合
                        if (stockQuantity > 0) {
                            goodsItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                        } else if (stockQuantity == 0) {
                            goodsItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                        } else {
                            goodsItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                        }
                    }
                }
                // 2023-renew No11 to here

                // 数量割引が設定されている、または、顧客セールが設定されている場合
                // 2023-renew No11 from here
                if (CollectionUtil.isNotEmpty(responseInfo.getSalePriceList()) || CollectionUtil.isNotEmpty(
                                responseInfo.getCustomerSalePriceList())) {
                    // 価格
                    if (responseInfo.getPriceList() != null && responseInfo.getPriceList().get(i) != null) {

                        if (responseInfo.getMarketPriceFlag().equals("1")) {
                            goodsItem.setPrice("時価");
                        } else {
                            goodsItem.setPrice(df.format(responseInfo.getPriceList().get(i).getPrice()) + "円");
                            goodsItem.setLevel(responseInfo.getPriceList().get(i).getLevel());
                        }
                        // 2023-renew No11 to here
                    }
                    // 数量
                    // 数量割引が設定されていない場合
                    if (CollectionUtil.isEmpty(responseInfo.getSalePriceList())) {
                        // 顧客セール閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel() == null) {
                            goodsItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList()
                                                                                     .size()) {
                                goodsItem.setLevel(
                                                responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                        // 顧客セールが設定されていない場合
                    } else if (CollectionUtil.isEmpty(responseInfo.getCustomerSalePriceList())) {
                        // 数量割引閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getSalePriceList().get(i).getSaleLevel() == null) {
                            goodsItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                                goodsItem.setLevel(responseInfo.getSalePriceList().get(i).getSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                    } else {
                        // 顧客セール閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel() == null) {
                            goodsItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList()
                                                                                     .size()) {
                                goodsItem.setLevel(
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
                            goodsItem.setCustomerSalePrice(df.format(responseInfo.getCustomerSalePriceList()
                                                                                 .get(i)
                                                                                 .getCustomerSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            goodsItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            goodsItem.setSalePrice(responseInfo.getSalePriceList()
                                                               .stream()
                                                               .filter(salePriceDto -> detailDto.getLevel()
                                                                                                .equals(salePriceDto.getSaleLevel()))
                                                               .findFirst()
                                                               .map(salePriceDto -> df.format(
                                                                               salePriceDto.getSalePrice()) + "円")
                                                               .orElse(null));
                            goodsItem.setCustomerSalePrice(responseInfo.getCustomerSalePriceList()
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
                            goodsItem.setSalePrice(
                                            df.format(responseInfo.getSalePriceList().get(i).getSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            goodsItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            goodsItem.setSalePrice(responseInfo.getSalePriceList()
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
                            goodsItem.setCustomerSalePrice(df.format(responseInfo.getCustomerSalePriceList()
                                                                                 .get(i)
                                                                                 .getCustomerSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            goodsItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            goodsItem.setCustomerSalePrice(responseInfo.getCustomerSalePriceList()
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
                        goodsItem.setPrice("時価");
                    } else {
                        goodsItem.setPrice(df.format(responseInfo.getPriceList().get(i).getPrice()) + "円");
                    }

                    // 数量
                    if (responseInfo.getPriceList().get(i).getLevel() == null || responseInfo.getPriceList()
                                                                                             .get(i)
                                                                                             .getLevel()
                                                                                             .isEmpty()) {
                        goodsItem.setLevel("1～");
                    } else {
                        goodsItem.setLevel(responseInfo.getPriceList().get(i).getLevel());
                    }
                }

                // 2023-renew No11 from here
                if (StringUtils.isBlank(goodsItem.getGcnt())) {
                    goodsItem.setGcnt("1");
                }
                goodsItem.setGoodsOpenStatus(goodsGroupDto.getGoodsGroupEntity().getGoodsOpenStatusPC());
                goodsItem.setSaleStatus(goodsEntity.getSaleStatusPC());
                goodsItem.setSaleStartTime(goodsEntity.getSaleStartTimePC());
                goodsItem.setSaleEndTime(goodsEntity.getSaleEndTimePC());
                if (Objects.nonNull(stockMap) && Objects.nonNull(stockMap.get(responseInfo.getGoodsCode()))) {
                    goodsItem.setSaleControl(stockMap.get(responseInfo.getGoodsCode()).getSaleControl());
                    goodsItem.setStockQuantity(stockMap.get(responseInfo.getGoodsCode()).getStockQuantity());
                }
                if (commonInfoUtility.isLogin(getCommonInfo())) {
                    // WEB-API連携 割引適用結果取得実行
                    Map<String, WebApiGetDiscountsResponseDetailDto> getDiscountsResponseDtoMap =
                                    executeWebApiGetDiscountsResult(goodsDtoList);
                    if (Objects.nonNull(getDiscountsResponseDtoMap) && Objects.nonNull(
                                    getDiscountsResponseDtoMap.get(responseInfo.getGoodsCode() + "kp"))) {
                        goodsItem.setSaleType(EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class,
                                                                            getDiscountsResponseDtoMap.get(
                                                                                            responseInfo.getGoodsCode()
                                                                                            + "kp").getSaleType()
                                                                           ));
                        goodsItem.setSaleEmotionPrice(
                                        df.format(getDiscountsResponseDtoMap.get(responseInfo.getGoodsCode() + "kp")
                                                                            .getSalePrice()) + "円");

                    }
                }

                if (customParams.length > 0) {
                    List<GoodsUnitImageItem> goodsUnitImageItems = (List<GoodsUnitImageItem>) customParams[0];
                    if (CollectionUtil.isNotEmpty(goodsUnitImageItems)) {
                        goodsItem.setGoodsImageItems(goodsUnitImageItems.stream()
                                                                        .filter(goodsUnitImageItem -> goodsUnitImageItem.getGoodsCode()
                                                                                                                        .equalsIgnoreCase(
                                                                                                                                        goodsItem.getGoodsCode()))
                                                                        .map(GoodsUnitImageItem::getImagePath)
                                                                        .collect(Collectors.toList()));
                    }
                }
                // 2023-renew No11 to here

                goodsStockItems.add(goodsItem);
            }
        }

        // 2023-renew No11 from here
        // 商品コードリスト
        if (!ListUtils.isEmpty(goodsStockItems)) {
            // 販売可否判定API
            Map<String, WebApiGetSaleCheckDetailResponse> saleCheckMap;
            if (commonInfoUtility.isLogin(getCommonInfo())) {
                // 取りおき情報取得API
                WebApiGetReserveResponseDto dto = this.executeWebApiGetReserve(goodsStockItems);
                if (ObjectUtils.isNotEmpty(dto)) {
                    goodsStockItems.forEach(stockItem -> {
                        if (dto.getMap().get(stockItem.getGoodsCode()) != null) {
                            stockItem.setReserveFlag(dto.getMap().get(stockItem.getGoodsCode()).getReserveFlag());
                        }
                    });
                }

                // 2023-renew AddNo5 from here
                // 販売可否判定API
                List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                                this.executeWebApiGetSaleCheck(goodsStockItems);
                if (CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses)) {
                    goodsStockItems.forEach(stockItem -> {
                        webApiGetSaleCheckDetailResponses.forEach(res -> {
                            if (Objects.equals(res.getGoodsCode(), stockItem.getGoodsCode())) {
                                stockItem.setSaleCheck(res.getGoodsSaleYesNo());
                                // 2023-renew No2 from here
                                if (HTypeSaleCheckType.YES.getValue().equals(stockItem.getSaleCheck().toString())) {
                                    salable.set(HTypeSaleCheckType.YES);
                                }
                                // 2023-renew No2 to here
                            }
                        });
                    });
                }
                saleCheckMap = CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses) ?
                                webApiGetSaleCheckDetailResponses.stream()
                                                                 .filter(saleCheckDetail -> StringUtil.isNotBlank(
                                                                                 saleCheckDetail.getGoodsCode()))
                                                                 .collect(Collectors.toMap(
                                                                                 WebApiGetSaleCheckDetailResponse::getGoodsCode,
                                                                                 Function.identity(),
                                                                                 (saleCheckDetail1, saleCheckDetail2) -> saleCheckDetail1
                                                                                          )) :
                                null;
            } else {
                saleCheckMap = null;
            }

            // 商品ヘッダ部の価格計算
            if (CollectionUtil.isNotEmpty(getQuantityDiscountDto.getInfo()) && !CollectionUtils.isEmpty(stockMap)
                && !CollectionUtils.isEmpty(saleCheckMap)) {
                List<WebApiGetQuantityDiscountResponseDetailDto> quantityDiscountResponseDetailDtos =
                                getQuantityDiscountDto.getInfo().stream().filter(quantityDiscountDetail -> {
                                                          // 商品TBL.販売状態PC = 販売中
                                                          // 商品TBL.販売開始日時PC ≦ 現在日時 ≦ 商品TBL.販売終了日時PC
                                                          if (Objects.isNull(goodsDtoMap.get(quantityDiscountDetail.getGoodsCode()))) {
                                                              return false;
                                                          }
                                                          GoodsEntity goodsEntity = goodsDtoMap.get(quantityDiscountDetail.getGoodsCode())
                                                                                               .getGoodsEntity();
                                                          return goodsUtility.isGoodsSales(goodsEntity.getSaleStatusPC(),
                                                                                           goodsEntity.getSaleStartTimePC(),
                                                                                           goodsEntity.getSaleEndTimePC()
                                                                                          );
                                                      })
                                                      // 販売制御区分　≠　「2：取扱中止」
                                                      // 販売可否判定結果 = 「1：販売可」
                                                      .filter(quantityDiscountDetail -> {
                                                          if (Objects.isNull(
                                                                          stockMap.get(quantityDiscountDetail.getGoodsCode()))
                                                              || Objects.isNull(saleCheckMap.get(
                                                                          quantityDiscountDetail.getGoodsCode()))) {
                                                              return false;
                                                          }
                                                          return !HTypeSaleControlType.DISCONTINUED.equals(
                                                                          stockMap.get(quantityDiscountDetail.getGoodsCode())
                                                                                  .getSaleControl())
                                                                 && HTypeSaleCheckType.YES.getValue()
                                                                                          .equals(String.valueOf(
                                                                                                          saleCheckMap.get(
                                                                                                                                      quantityDiscountDetail.getGoodsCode())
                                                                                                                      .getGoodsSaleYesNo()));
                                                      }).collect(Collectors.toList());
                // セール価格リストによる価格を設定する
                goodsIndexHelper.setGoodsPricesByQuantityDiscountList(
                                goodsIndexModel, quantityDiscountResponseDetailDtos);
            }
            // 2023-renew AddNo5 to here

            // 2023-renew No92 from here
            // 後継品代替品情報取得API
            List<WebApiGetOtherGoodsDetailResponse> webApiGetOtherGoodsDetailResponses =
                            this.executeWebApiGetOtherGoods(goodsStockItems);
            if (CollectionUtil.isNotEmpty(webApiGetOtherGoodsDetailResponses)) {
                // 製品コードの後継製品を製品コードに基づいてグループ化します。
                Map<String, List<String>> otherGoodsCodeMap = webApiGetOtherGoodsDetailResponses.stream()
                                                                                                .filter(res -> StringUtil.isNotBlank(
                                                                                                                res.getGoodsCode())
                                                                                                               && StringUtil.isNotBlank(
                                                                                                                res.getOtherGoodsCode()))
                                                                                                .collect(Collectors.groupingBy(
                                                                                                                WebApiGetOtherGoodsDetailResponse::getGoodsCode,
                                                                                                                Collectors.mapping(
                                                                                                                                WebApiGetOtherGoodsDetailResponse::getOtherGoodsCode,
                                                                                                                                Collectors.toList()
                                                                                                                                  )
                                                                                                                              ));
                // 重複のない後継製品の製品コードリストをフィルタリングする
                List<String> otherGoodsCodes = otherGoodsCodeMap.values()
                                                                .stream()
                                                                .flatMap(Collection::stream)
                                                                .distinct()
                                                                .collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(otherGoodsCodes)) {
                    // 商品グループ詳細情報取得API
                    // 製品コードリストに基づいて製品グループリストをクエリします。
                    List<GoodsGroupDtoResponse> otherGoodsGroupListResponse =
                                    this.executeWebApiGetGoodsGroupsByGoodsCodes(otherGoodsCodes);
                    if (CollectionUtil.isNotEmpty(otherGoodsGroupListResponse)) {
                        GoodsGroupDtoResponse otherGoods = null;
                        GoodsDtoResponse otherGoodsDtoResponse = null;
                        for (GoodsStockItem goodsStockItem : goodsStockItems) {
                            if (otherGoodsCodeMap.containsKey(goodsStockItem.getGoodsCode())) {
                                // (1) 後継製品情報
                                // goodsStockItemに対応する後継品をフィルタリングする.
                                otherGoods = otherGoodsGroupListResponse.stream().filter(o -> {
                                    if (CollectionUtil.isEmpty(o.getGoodsDtoList()) || Objects.isNull(
                                                    o.getGoodsDtoList().get(0).getGoodsEntity())
                                        || CollectionUtil.isEmpty(
                                                    otherGoodsCodeMap.get(goodsStockItem.getGoodsCode()))) {
                                        return false;
                                    }
                                    return otherGoodsCodeMap.get(goodsStockItem.getGoodsCode())
                                                            .get(0)
                                                            .equalsIgnoreCase(o.getGoodsDtoList()
                                                                               .get(0)
                                                                               .getGoodsEntity()
                                                                               .getGoodsCode());
                                }).findFirst().orElse(null);
                                if (Objects.nonNull(otherGoods)) {
                                    // (1)からDTOに後継製品情報を割り当てます
                                    GoodsStockItem otherGoodsItem =
                                                    ApplicationContextUtility.getBean(GoodsStockItem.class);
                                    if (CollectionUtil.isNotEmpty(otherGoods.getGoodsDtoList())) {
                                        otherGoodsDtoResponse = otherGoods.getGoodsDtoList().get(0);
                                        if (Objects.nonNull(otherGoodsDtoResponse.getGoodsEntity())) {
                                            otherGoodsItem.setGoodsSeq(
                                                            otherGoodsDtoResponse.getGoodsEntity().getGoodsSeq());
                                            otherGoodsItem.setGcd(
                                                            otherGoodsDtoResponse.getGoodsEntity().getGoodsCode());
                                            otherGoodsItem.setGoodsPriceInTaxLow(otherGoodsDtoResponse.getGoodsEntity()
                                                                                                      .getGoodsPriceInTaxLow());
                                            otherGoodsItem.setGoodsPriceInTaxHight(
                                                            otherGoodsDtoResponse.getGoodsEntity()
                                                                                 .getGoodsPriceInTaxHight());
                                            otherGoodsItem.setPreDiscountPriceLow(otherGoodsDtoResponse.getGoodsEntity()
                                                                                                       .getPreDiscountPriceLow());
                                            otherGoodsItem.setPreDiscountPriceHight(
                                                            otherGoodsDtoResponse.getGoodsEntity()
                                                                                 .getPreDiscountPriceHight());
                                        }
                                        if (Objects.nonNull(otherGoodsDtoResponse.getUnitImage())
                                            && StringUtils.isNotBlank(
                                                        otherGoodsDtoResponse.getUnitImage().getImageFileName())) {
                                            otherGoodsItem.setGoodsImageItems(List.of(goodsUtility.getGoodsImagePath(
                                                            otherGoodsDtoResponse.getUnitImage().getImageFileName())));
                                        }
                                    }
                                    if (Objects.nonNull(otherGoods.getGoodsGroupEntity())) {
                                        otherGoodsItem.setGoodsGroupName(
                                                        otherGoods.getGoodsGroupEntity().getGoodsGroupName());
                                    }
                                    goodsStockItem.setOtherGoodsItem(otherGoodsItem);
                                }
                            }
                        }
                    }
                }

            }
            // 2023-renew No92 to here

            DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
            goodsStockItems.forEach(goodsItem -> {
                // 販売期間外
                boolean goodsItemOutOfDate =
                                dateHelper.isNoOpen(goodsItem.getSaleStartTime(), goodsItem.getSaleEndTime(),
                                                    dateHelper.getCurrentTime()
                                                   );

                // 販売状態PC＝非販売
                boolean goodsItemNoSale = false;
                if (goodsItem.getSaleStatus() != null) {
                    goodsItemNoSale = HTypeGoodsSaleStatus.NO_SALE.equals(goodsItem.getSaleStatus());
                }
                if (HTypeGoodsSaleStatus.SALE.equals(goodsItem.getSaleStatus()) && goodsItemOutOfDate) {
                    goodsOutOfDates.add(goodsItem);
                }
                if (goodsItemNoSale) {
                    goodsNoSales.add(goodsItem);
                }
                if (goodsItemOutOfDate || goodsItemNoSale) {
                    goodsNoSalesOrOutOfDates.add(goodsItem);
                }

            });

            // 紐付くすべての規格商品が「販売期間外 ※」の場合
            // 紐付くすべての規格商品が商品在庫数取得API．販売制御区分＝「2：取扱中止」の場合
            if ((!ListUtils.isEmpty(goodsOutOfDates) && goodsStockItems.size() == goodsOutOfDates.size())
                || isAllSaleControlDiscontinues.get()) {
                goodsIndexModel.setSellOutIconFlag(true);
            }

            // 紐付くすべての規格商品が「販売状態PC＝非販売」の場合
            // 商品に紐付く規格商品に、「販売状態PC＝非販売」と「販売期間外」が混在している場合
            if ((!ListUtils.isEmpty(goodsNoSales) && goodsStockItems.size() == goodsNoSales.size()) || (
                            !ListUtils.isEmpty(goodsNoSalesOrOutOfDates)
                            && goodsStockItems.size() == goodsNoSalesOrOutOfDates.size())) {
                goodsIndexModel.setGoodsPriceLow(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupMinPricePc());
                goodsIndexModel.setGoodsPriceHigh(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupMaxPricePc());
            }

        }
        // 2023-renew No11 to here

        goodsIndexModel.setGoodsStockInfoItems(goodsStockItems);

        // 2023-renew No2 from here
        goodsIndexModel.setGoodsSaleCheck(salable.get().getValue());
        // 2023-renew No2 to here
        // 2023-renew No5 to here
    }

    // 2023-renew No11 from here

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

        return goodsHelper.toWebApiGetReserveResponseDto(webapiGetReserveResponse);
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

    /**
     * WEB-API連携 後継品代替品情報取得
     *
     * @param goodsStockItems 在庫商品リスト
     * @return 後継品代替品情報取得レスポンスリスト
     */
    protected List<WebApiGetOtherGoodsDetailResponse> executeWebApiGetOtherGoods(List<GoodsStockItem> goodsStockItems) {
        List<String> goodsCodeList = new ArrayList<>();
        goodsStockItems.forEach(stockItem -> {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(stockItem.getGoodsCode());

            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        });

        WebApiGetOtherGoodsRequest webApiGetOtherGoodsRequest = new WebApiGetOtherGoodsRequest();
        webApiGetOtherGoodsRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        // 後継品代替品情報取得API
        WebApiGetOtherGoodsResponse webApiGetOtherGoodsResponse = null;
        try {
            webApiGetOtherGoodsResponse = webapiApi.getOtherGoods(webApiGetOtherGoodsRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        if (Objects.isNull(webApiGetOtherGoodsResponse)) {
            throwMessage("PDR-0015-001-A-");
        }
        return webApiGetOtherGoodsResponse.getInfo();
    }

    /**
     * WEB-API連携 商品グループ詳細情報取得
     *
     * @param goodsCodes 商品コードリスト
     * @return 商品詳細レスポンスリスト
     */
    protected List<GoodsGroupDtoResponse> executeWebApiGetGoodsGroupsByGoodsCodes(List<String> goodsCodes) {
        if (CollectionUtil.isEmpty(goodsCodes)) {
            return new ArrayList<>();
        }

        GoodsGroupDtoListByGoodsCodesGetRequest goodsGroupDtoListByGoodsCodesGetRequest =
                        new GoodsGroupDtoListByGoodsCodesGetRequest();
        goodsGroupDtoListByGoodsCodesGetRequest.setGoodsCodesList(goodsCodes);

        // 商品グループ詳細情報取得API
        List<GoodsGroupDtoResponse> goodsGroupListResponse = null;
        try {
            goodsGroupListResponse = goodsApi.getGoodsGroupsByGoodsCodes(goodsGroupDtoListByGoodsCodesGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return goodsGroupListResponse;
    }
    // 2023-renew No11 to here

    // 2023-renew No5 from here

    // 2023-renew No5 to here

    /**
     * WEB-API連携 商品在庫数取得を行います。
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> executeWebApiGetStock(List<GoodsDto> goodsDtoList) {

        WebApiGetStockRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetStockRequestDto.class);

        List<String> goodsCodeList = new ArrayList<String>();
        for (GoodsDto goodsDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            goodsDto.getGoodsEntity().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        reqDto.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        try {
            WebApiGetStockRequest webApiGetStockRequest = new WebApiGetStockRequest();
            webApiGetStockRequest.setGoodsCode(reqDto.getGoodsCode());

            WebApiGetStockResponse webApiGetStockResponse = null;
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
     * WEB-API連携 割引適用結果取得を行います。
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 割引適用結果取得MAP
     */
    public Map<String, WebApiGetDiscountsResponseDetailDto> executeWebApiGetDiscountsResult(List<GoodsDto> goodsDtoList) {
        List<String> goodsCodeList = new ArrayList<>();
        List<String> orderDisplayList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            goodsDto.getGoodsEntity().getGoodsCode());
            String orderDisplay = String.valueOf(goodsDto.getGoodsEntity().getOrderDisplay());
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

    /**
     * WEB-API連携 数量割引情報取得を行います。
     *
     * @param goodsDtoList    商品DTOリスト
     * @param goodsIndexModel 商品詳細画面のページクラス
     * @return 商品コードをキーにした商品在庫数MAP
     */
    protected WebApiGetQuantityDiscountResponseDto executeWebApiGetQuantityDiscount(List<GoodsDto> goodsDtoList,
                                                                                    GoodsIndexModel goodsIndexModel) {

        List<String> goodsCodeList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            goodsDto.getGoodsEntity().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        WebApiGetQuantityDiscountRequest webApiGetQuantityDiscountRequest = new WebApiGetQuantityDiscountRequest();
        webApiGetQuantityDiscountRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        // 2023-renew No11 from here
        webApiGetQuantityDiscountRequest.setCustomerNo(commonInfoUtility.isLogin(getCommonInfo()) ?
                                                                       commonInfoUtility.getCustomerNo(
                                                                                       getCommonInfo()) :
                                                                       null);
        // 2023-renew No11 to here

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

    // PDR Migrate Customization to here

    /**
     * 遷移元のカテゴリ情報をページにセット
     *
     * @param goodsIndexModel 商品詳細画面 Model
     */
    protected void setCategoryInfo(GoodsIndexModel goodsIndexModel) {
        String categoryId = goodsIndexModel.getCid();

        // カテゴリ情報を取得
        CategoryDetailsDto categoryDetailsDto = null;

        if (StringUtils.isNotEmpty(categoryId)) {
            CategorySearchForDaoConditionDto conditionDto =
                            ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
            conditionDto.setCategoryId(categoryId);
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

            categoryDetailsDto = goodsHelper.toCategoryDetailsDto(categoryDetailsDtoResponse);

            goodsIndexHelper.toPageForLoadCategoryDto(categoryDetailsDto, goodsIndexModel);

            // パンくず情報を取得
            List<CategoryDetailsDto> categoryDetailsDtoList = null;
            if (categoryDetailsDto != null) {

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

                categoryDetailsDtoList = goodsHelper.toCategoryDetailsDtoList(categoryDetailsDtoResponseList);
            }
            goodsIndexHelper.toPageForLoadForTopicPath(categoryDetailsDtoList, goodsIndexModel);
        }

    }

    // 2023-renew No11 from here
    // excludeNoSaleGoodsFromStockListを削除
    // 2023-renew No11 to here

    /**
     * 関連商品表示処理
     * ※アクセス修飾子は、public 商品詳細画面からサブアプリケーション切れの対応の為
     *
     * @param relatedGoodsLimit 関連商品件数
     * @param goodsIndexModel   商品詳細画面 Model
     */
    public void goodsRelationDisplay(int relatedGoodsLimit, GoodsIndexModel goodsIndexModel) {
        List<GoodsGroupDto> relatedGoodsGroupDtoList = new ArrayList<>();

        // 商品グループコード 又は、 商品コードがある場合のみ
        if (StringUtils.isNotEmpty(goodsIndexModel.getGgcd()) || StringUtils.isNotEmpty(goodsIndexModel.getGcd())) {

            // 商品グループ情報を取得
            OpenGoodsGroupDetailsGetRequest openGoodsGroupDetailsGetRequest = new OpenGoodsGroupDetailsGetRequest();

            openGoodsGroupDetailsGetRequest.setGoodsGroupCode(goodsIndexModel.getGgcd());
            openGoodsGroupDetailsGetRequest.setGoodsCode(goodsIndexModel.getGcd());

            GoodsGroupDtoResponse goodsGroupDtoResponse = null;
            try {
                goodsGroupDtoResponse = goodsApi.getOpenGoodsGroupDetails(openGoodsGroupDetailsGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            GoodsGroupDto goodsGroupDto = goodsHelper.toGoodsGroupDto(goodsGroupDtoResponse);

            if (goodsGroupDto != null) {
                Integer currentGoodsGroupSeq = goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq();
                GoodsRelationSearchForDaoConditionDto conditionDto =
                                ApplicationContextUtility.getBean(GoodsRelationSearchForDaoConditionDto.class);
                conditionDto.setGoodsGroupSeq(currentGoodsGroupSeq);
                // ページングセットアップ
                conditionDto = pageInfoHelper.setupPageInfoForSkipCount(conditionDto, relatedGoodsLimit, null, true);

                jp.co.hankyuhanshin.itec.hitmall.api.goods.param.PageInfoRequest pageInfoRequest =
                                new jp.co.hankyuhanshin.itec.hitmall.api.goods.param.PageInfoRequest();
                pageInfoHelper.setupPageRequest(pageInfoRequest, conditionDto.getPageInfo().getPnum(),
                                                conditionDto.getPageInfo().getLimit(),
                                                conditionDto.getPageInfo().getOrderField(),
                                                conditionDto.getPageInfo().isOrderAsc()
                                               );

                OpenRelatedGoodsListGetRequest openRelatedGoodsListGetRequest = new OpenRelatedGoodsListGetRequest();

                openRelatedGoodsListGetRequest.setGoodsGroupSeq(conditionDto.getGoodsGroupSeq());

                GoodsGroupDtoListResponse goodsGroupDtoListResponse = null;
                try {
                    goodsGroupDtoListResponse = goodsApi.getRelationOpenRelatedGoodsList(openRelatedGoodsListGetRequest,
                                                                                         pageInfoRequest
                                                                                        );
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }

                if (Objects.nonNull(goodsGroupDtoListResponse)) {
                    relatedGoodsGroupDtoList = goodsHelper.toGoodsGroupDtoList(
                                    goodsGroupDtoListResponse.getGoodsGroupDtoListResponse());
                }
            }
        }

        // ページに反映
        try {
            goodsIndexHelper.toPageForLoadRelated(relatedGoodsGroupDtoList, goodsIndexModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

    }

    /**
     * あしあと商品を登録（非同期処理）
     *
     * @param goodsGroupSeq 商品グループSEQ
     */
    protected void footPrintRegist(Integer goodsGroupSeq) {

        FootprintEntity entity = ApplicationContextUtility.getBean(FootprintEntity.class);
        entity.setShopSeq(getCommonInfo().getCommonInfoBase().getShopSeq());
        entity.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());
        entity.setGoodsGroupSeq(goodsGroupSeq);

        GoodsFootPrintRegistRequest goodsFootPrintRegistRequest = new GoodsFootPrintRegistRequest();
        goodsFootPrintRegistRequest.setAccessUid(entity.getAccessUid());
        goodsFootPrintRegistRequest.setGoodsGroupSeq(entity.getGoodsGroupSeq());

        try {
            goodsApi.registFootprintGoods(goodsFootPrintRegistRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
    }

    // 2023-renew No11 from here

    /**
     * セールde予約する
     *
     * @param goodsIndexModel    商品詳細画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return ページクラス
     */
    @PostMapping(value = {"/index", "/index.html"}, params = "doSaleReserveAdd")
    public String doSaleReserveAdd(GoodsIndexModel goodsIndexModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        String gcd = goodsIndexModel.getGoodsStockInfoItems().get(goodsIndexModel.getIndexStock()).getGoodsCode();
        if (StringUtils.isEmpty(gcd)) {
            // gcdが不足している場合、エラーメッセージ＋エラー画面を表示
            // goods/detailにredirectしても、最終的にエラーメッセージ＋エラー画面のため、この時点でエラー画面に遷移させる
            // 現行ではエラーメッセージ＋商品詳細画面を返すので、
            // それに合わせて、return goods/detail とすると、パラメータが残ってしまうので、ひとまずredirect:/errorで対応　
            addMessage(GoodsIndexModel.MSGCD_GOODS_NOT_FOUND_CLICK_ERROR, redirectAttributes, model);
            return "redirect:/error.html";
        }

        return "redirect:/goods/reserve.html?gcd=" + gcd + "&ggcd=" + goodsIndexModel.getGgcd() + "&cid="
               + goodsIndexModel.getCid();
    }

    /**
     * WEB-API連携 商品在庫数取得を行います。
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> executeWebApiGetStockByFavoriteList(List<FavoriteDto> goodsDtoList) {

        WebApiGetStockRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetStockRequestDto.class);

        List<String> goodsCodeList = new ArrayList<String>();
        for (FavoriteDto favoriteDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            favoriteDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        reqDto.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        try {
            WebApiGetStockRequest webApiGetStockRequest = new WebApiGetStockRequest();
            webApiGetStockRequest.setGoodsCode(reqDto.getGoodsCode());

            WebApiGetStockResponse webApiGetStockResponse = null;
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
    // 2023-renew No11 to here

    // 2023-renew No21 from here

    /**
     * よく一緒に購入される商品表示処理
     *
     * @param goodsIndexModel 商品詳細画面 Model
     * @param limit           最大取得件数
     */
    public void getTogetherBuyGoodsList(GoodsIndexModel goodsIndexModel, int limit) {
        GoodsGroupTogetherBuyRequest request = new GoodsGroupTogetherBuyRequest();
        request.setGoodsSeq(goodsIndexModel.getGoodsGroupSeq());
        request.setLimit(limit);
        GoodsGroupDtoListResponse goodsTogetherBuyListResponse = goodsApi.getGoodsTogetherBuyListByStatus(request);
        List<GoodsGroupDto> togetherBuyGoodsList =
                        goodsHelper.toGoodsGroupDtoList(goodsTogetherBuyListResponse.getGoodsGroupDtoListResponse());
        // ページに反映
        try {
            goodsIndexHelper.toPageForLoadTogetherBuy(togetherBuyGoodsList, goodsIndexModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
    }
    // 2023-renew No21 to here
}
