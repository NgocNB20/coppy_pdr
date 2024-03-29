/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoCorrectionScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoCorrectionScreenUpdateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsAddRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsForTakeOverDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsFootPrintDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupTogetherBuyRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenFootPrintListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteForCartCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.CartGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoAddCouponGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.coupon.AbstractCouponController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsIndexModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.AbstractOrderController;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CartUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ショッピングカートController
 * <p>
 * PDR#004 01_販売可能商品の制御
 * PDR#005 02_商品在庫数の制御
 * PDR#007 04_数量割引サービス
 *
 * @author kaneda
 */
@SessionAttributes(value = "cartModel")
@RequestMapping("/cart")
@Controller
public class CartController extends AbstractCouponController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    // PDR Migrate Customization from here

    // 2023-renew No2 from here
    /** カート投入可能商品件数 */
    public static final String SYS_KEY_MAX_CART_GOODS_COUNT = "max.cart.goods.count";
    // 2023-renew No2 to here
    
    /** カートに投入できない商品がある場合エラー */
    public static final String MSGCD_CART_ADD_GOODS_ERR = "PDR-0008-004-A-";

    /**  購入制限エラーメッセージ */
    public static final String MSGCD_SALE_NG = "PDR-0005-001-A-";

    /** 購入制限エラーメッセージ */
    public static final String MSGCD_SALE_NG_WITH_NULL_SALE_NG_MESSAGE = "PDR-0005-002-A-";

    /** 個別配送商品あり */
    public static final String MSGCD_INDIVIDUAL_DELIVERY = "LCC000617";

    /** 受注生産品あり */
    public static final String MSGCD_BUILD_TO_ORDER = "LCC000619";

    /** 酒類フラグ */
    public static final String MSGCD_ALCOHOL_FLAG = "PKG-4113-004-L-";

    /** 酒類フラグエラー */
    public static final String MSGCD_ALCOHOL_CHECK_ERROR = "PKG-4113-001-L-";

    /** 商品合計金額超過チェックメッセージ */
    public static final String MSGCD_CART_TOTAL_PRICE_MAX_OVER = "LCC000616";

    /** 公開状態チェックメッセージ<非公開> */
    public static final String MSGCD_OPEN_STATUS_HIKOUKAI = "LCC000602";

    /** 公開期間チェックメッセージ<公開前> */
    public static final String MSGCD_OPEN_BEFORE = "LCC000604";

    /** 公開期間チェックメッセージ<公開終了> */
    public static final String MSGCD_OPEN_END = "LCC000605";

    /** 販売状態チェックメッセージ<非販売> */
    public static final String MSGCD_SALE_STATUS_HIHANBAI = "LCC000606";

    /** 販売期間チェックメッセージ<販売前> */
    public static final String MSGCD_SALE_BEFORE = "LCC000608";

    /** 販売期間チェックメッセージ<販売終了> */
    public static final String MSGCD_SALE_END = "LCC000609";

    /** 在庫切れチェックメッセージ<在庫切れ> */
    public static final String MSGCD_NO_STOCK = "LCC000610";

    /** 在庫不足チェックメッセージ<在庫不足> */
    public static final String MSGCD_LESS_STOCK = "LCC000611";

    /** 最大購入可能数超過チェックメッセージ */
    public static final String MSGCD_PURCHASED_MAX_OVER = "LCC000612";

    /** 購入制限チェックメッセージ<注文数量制限> */
    public static final String MSGCD_NO_POSSIBLE = "LCC000621";
    // PDR Migrate Customization to here

    // 2023-renew No2 from here
    /** 販売可否判定結果：販売不可エラー（カート商品行単位） */
    public static final String MSGCD_ERROR_SALE_CHECK_NO = "PDR-2023RENEW-2-001-";

    /** 販売可否判定結果：販売不可エラー（商品コード単位） */
    public static final String MSGCD_ERROR_SALE_CHECK_NO_BY_GOODS = "PDR-2023RENEW-2-002-";

    /** カート内商品件数の最大投入件数超過エラー */
    public static final String MSGCD_ERROR_CARTIN_COUNT_LIMIT_OVER = "PDR-2023RENEW-2-004-A-";

    /** カート内最大商品件数チェックメッセージ */
    public static final String MSGCD_CART_GOODS_MAX_OVER = "LCC000615";
    // 2023-renew No2 to here

    // 2023-renew No14 from here
    /** セールde予約不可（カート商品行単位） */
    public static final String MSGCD_NOT_RESERVE = "PDR-2023RENEW-14-001-";

    /** セールde予約不可（商品コード単位） */
    public static final String MSGCD_NOT_RESERVE_BY_GOODS = "PDR-2023RENEW-14-002-";
    // 2023-renew No14 to here

    // 2023-renew No24 from here
    /** クーポンを選択している場合の表示メッセージ */
    public static final String MSGCD_SELECT_COUPON = "PDR-2023RENEW-24-002-";
    // 2023-renew No24 to here

    /**
     * カート一括投入用DTOリストのセッションキー
     */
    public static final String CART_GOODS_DTO_LIST_KEY = "CartGoodsForTakeOverDtoList";

    // 2023-renew No14 from here
    /**
     * アトリビュート名（同一商品チェック確認ダイアログ用）
     */
    public static final String DISPLAY_DIALOG_DUPLICATION_GOODS = "displayDialogDuplicationGoods";
    // 2023-renew No14 to here

    // 2023-renew No24 from here
    /**
     * アトリビュート名（クーポン関連INFOメッセージ用）
     */
    public static final String COUPON_INFO_FLASH_MESSAGES_ATTRIBUTE = "couponMessages";

    /**
     * アトリビュート名（クーポン関連ERRORメッセージ用）
     */
    public static final String COUPON_ERROR_FLASH_MESSAGES_ATTRIBUTE = "couponErrorMessages";
    // 2023-renew No24 to here

    /**
     * お気に入りリスト：デフォルト最大表示件数
     */
    // PDR Migrate Customization from here
    private static final int DEFAULT_FAVORITE_GOODS_LIMIT = 6;
    // PDR Migrate Customization to here

    /**
     * あしあとリスト：デフォルト最大表示件数
     */
    // PDR Migrate Customization from here
    private static final int DEFAULT_FOOTPRINT_GOODS_LIMIT = -1;
    // PDR Migrate Customization to here

    /**
     * 関連商品リスト：デフォルト最大表示件数
     */
    private static final int DEFAULT_RELATED_GOODS_LIMIT = 6;

    // 2023-renew No21 from here
    /**
     * 商品詳細画面：一緒に購入される製品のデフォルト数
     */
    public static final int DEFAULT_TOGETHER_BUY_GOODS_LIMIT = 6;
    // 2023-renew No21 to here

    // 2023-renew No11 from here
    /**
     * お気に入りリストの商品数量
     */
    public static final String FAVORITE_GOODS_QUANTITY = "1";
    // 2023-renew No11 to here

    /**
     * ショッピングカートHelper
     */
    private final CartHelper cartHelper;

    /**
     * CommonInfoUtility
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * CartUtility
     */
    private final CartUtility cartUtility;

    // PDR Migrate Customization from here
    /**
     * カートApi
     */
    private final CartApi cartApi;

    /**
     * ユーザーApi
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * ショップAPI
     */
    private final ShopApi shopApi;

    // 2023-renew No11 from here
    /**
     * WebapiApi
     */
    private final WebapiApi webapiApi;
    // 2023-renew No11 to here

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;
    // PDR Migrate Customization to here

    /**
     * コンストラクタ
     */
    @Autowired
    public CartController(CartHelper cartHelper,
                          CommonInfoUtility commonInfoUtility,
                          CartUtility cartUtility,
                          CartApi cartApi,
                          MemberInfoApi memberInfoApi,
                          GoodsApi goodsApi,
                          ShopApi shopApi,
                          WebapiApi webapiApi,
                          GoodsUtility goodsUtility,
                          CouponHelper couponHelper) {
        // 2023-renew No24 from here
        super(webapiApi, couponHelper, commonInfoUtility);
        // 2023-renew No24 to here
        this.cartHelper = cartHelper;
        this.commonInfoUtility = commonInfoUtility;
        this.cartUtility = cartUtility;
        // PDR Migrate Customization from here
        this.cartApi = cartApi;
        this.memberInfoApi = memberInfoApi;
        this.goodsApi = goodsApi;
        this.shopApi = shopApi;
        this.goodsUtility = goodsUtility;
        // PDR Migrate Customization to here
        // 2023-renew No11 from here
        this.webapiApi = webapiApi;
        // 2023-renew No11 to here
    }

    /**
     * カート画面：初期表示処理
     * 呼び出し元画面より渡された商品SEQ、数量を元にカート商品情報を作成し、
     * カート情報に追加する。
     * 現在のカート情報、お気に入り商品情報、あしあと情報を
     * 取得し、ページ情報にセットする。
     *
     * @param gcd                商品コード
     * @param gcnt               数量
     * @param optionValue        オプション値（クーポンコード用）
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 遷移先画面
     */
    @GetMapping({"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    protected String doLoadIndex(@RequestParam(required = false) String gcd,
                                 @RequestParam(required = false) String gcnt,
                                 @RequestParam(required = false) String optionValue,
                                 CartModel cartModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        clearModel(CartModel.class, cartModel, model);
        // 2023-renew No3-taglog from here
        cartModel.setCatalogCartInMap(new HashMap<>());
        // 2023-renew No3-taglog to here

        // PDR Migrate Customization from here
        // （０） カート一括登録
        if (model.containsAttribute(CART_GOODS_DTO_LIST_KEY)) {
            List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList =
                            (List<CartGoodsForTakeOverDto>) model.getAttribute(CART_GOODS_DTO_LIST_KEY);
            ((BindingAwareModelMap) model).remove(CART_GOODS_DTO_LIST_KEY);

            if (CollectionUtil.isNotEmpty(cartGoodsForTakeOverDtoList)) {
                // 一括カート投入処理
                CartScreenRegistRequest cartScreenRegistRequest =
                                cartHelper.toCartScreenRegistRequest(getCommonInfo(), cartGoodsForTakeOverDtoList);
                try {
                    cartApi.registCartScreen(cartScreenRegistRequest);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }

                // 2023-renew No3-taglog from here
                // クイックオーダーでカートインした情報をタグログ連携用に保持する
                for (CartGoodsForTakeOverDtoRequest dto : cartScreenRegistRequest.getRegistCartGoodsList()) {
                    cartModel.getCatalogCartInMap().put(dto.getGoodsCode(), dto.getGoodsCount().toString());
                }
                // 2023-renew No3-taglog to here
            }
        }
        // PDR Migrate Customization to here

        // （１） カートに追加する商品の商品情報を取得し、カート商品DTOの作成
        boolean cartAddFlag = addGoodsToCart(gcd, gcnt, redirectAttributes, model);

        // （２） ≪画面表示情報取得処理≫実行
        setDisplayInfo(cartModel, error, redirectAttributes, model);

        // カート追加を行った時URLパラメータを消す
        if (cartAddFlag) {
            return "redirect:/cart/index.html";
        }

        // 描画前処理
        prerender(cartModel, error, redirectAttributes, model);

        // 画面表示
        return displayCartHtml(optionValue, cartModel, redirectAttributes, model, request, response);
    }

    /**
     * カート画面：再計算処理
     * 再計算ボタン、商品合計再計算ボタン押下時に呼び出される。
     * カート内商品情報、数量を元に、金額、合計金額、数量の再計算を行う。
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doCalculate")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doCalculate(@Validated(CartGroup.class) CartModel cartModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        if (error.hasErrors()) {
            return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
        }

        // （１） カート画面より、カート一覧情報と数量を取得する。
        // （２） 【カート商品数量変更サービス】実行
        updateCartGoods(cartModel);

        // （３） ≪画面表示情報取得処理≫実行
        setDisplayInfo(cartModel, error, redirectAttributes, model);

        // （４） 自画面を表示
        return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
    }

    /**
     * カート画面：カート内商品削除処理（今すぐお届け）
     * 削除ボタン（今すぐお届け）押下時に呼び出される。
     * カート内の指定した商品をカート情報より削除する。
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doDeleteGoods")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doDeleteGoods(CartModel cartModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        // （１） カート画面より、カート情報を取得する。
        // 削除対象のカート商品SEQを取得
        int index = cartModel.getCartGoodsIndex();

        if (cartModel.getCartGoodsItems().size() > index) {
            Integer cartSeq = cartModel.getCartGoodsItems().get(index).getCartSeq();

            // （２） 取得したカート情報をリストにセット
            List<Integer> cartSeqList = new ArrayList<>();
            cartSeqList.add(cartSeq);

            // （３） 【カート商品削除サービス】実行
            CartGoodsDeleteRequest cartGoodsDeleteRequest =
                            cartHelper.toCartGoodsDeleteRequest(getCommonInfo().getCommonInfoUser().getMemberInfoSeq(),
                                                                getCommonInfo().getCommonInfoBase().getAccessUid(),
                                                                cartSeqList
                                                               );
            try {
                cartApi.deleteCartGoods(cartGoodsDeleteRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        }

        // （４） ≪画面表示情報取得処理≫実行
        setDisplayInfo(cartModel, error, redirectAttributes, model);

        // （５）自画面を表示
        return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
    }

    // 2023-renew No14 from here

    /**
     * カート画面：カート内商品削除処理（セールde予約）
     * 削除ボタン（セールde予約）押下時に呼び出される。
     * カート内の指定した商品をカート情報より削除する。
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doDeleteGoodsReserve")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doDeleteGoodsReserve(CartModel cartModel,
                                       BindingResult error,
                                       RedirectAttributes redirectAttributes,
                                       Model model,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        // （１） カート画面より、カート情報を取得する。
        // 削除対象のカート商品SEQを取得
        String goodsCode = cartModel.getCartGoodsReserveGoodsCode();
        Map<String, List<CartModelItem>> cartGoodsReserveItemMap = cartModel.getCartGoodsReserveItemMap();

        if (MapUtils.isNotEmpty(cartGoodsReserveItemMap) && cartGoodsReserveItemMap.containsKey(goodsCode)) {
            // （２） 取得したカート情報をリストにセット
            List<Integer> cartSeqList = new ArrayList<>();
            cartGoodsReserveItemMap.get(goodsCode).forEach(item -> cartSeqList.add(item.getCartSeq()));

            // （３） 【カート商品削除サービス】実行
            CartGoodsDeleteRequest cartGoodsDeleteRequest =
                            cartHelper.toCartGoodsDeleteRequest(getCommonInfo().getCommonInfoUser().getMemberInfoSeq(),
                                                                getCommonInfo().getCommonInfoBase().getAccessUid(),
                                                                cartSeqList
                                                               );
            try {
                cartApi.deleteCartGoods(cartGoodsDeleteRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        }

        // （４） ≪画面表示情報取得処理≫実行
        setDisplayInfo(cartModel, error, redirectAttributes, model);

        // （５）自画面を表示
        return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
    }

    /**
     * カート画面：ご注文内容を確認する
     * 「ご注文内容を確認する」ボタン押下時に呼び出される。
     * 現在のカート情報で再計算を行い、注文画面を呼び出す。
     * ※ただし、今すぐお届けとセールde予約に同一商品番号が存在する場合、カート画面に戻して確認ダイアログを表示
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doCartConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doCartConfirm(@Validated(CartGroup.class) CartModel cartModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        if (error.hasErrors()) {
            return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
        }

        // カート商品数量を最新化
        updateCartGoods(cartModel);

        // 画面表示情報の再取得＆カート商品チェック
        if (!setDisplayInfo(cartModel, error, redirectAttributes, model)) {
            // エラー時は処理終了
            return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
        }

        // 同一商品番号チェック
        if (CollectionUtil.isNotEmpty(cartModel.getDuplicationGoodsItems())) {
            // 今すぐお届けとセールde予約に同一商品番号が存在する場合、カート画面に戻して確認ダイアログを表示
            model.addAttribute(DISPLAY_DIALOG_DUPLICATION_GOODS, true);
            return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
        }

        // 今すぐお届けとセールde予約に同一商品番号が存在しない場合、そのまま「doOrderConfirm」を呼び出す
        return doOrderConfirm(cartModel, redirectAttributes, model);
    }

    /**
     * カート画面：ご注文内容を確認する → 確認ダイアログOK
     * 「ご注文内容を確認する」ボタン押下 → 確認ダイアログOK時に呼び出される。
     * 引継ぎ情報をセットし、注文画面を呼び出す。
     *
     * @param cartModel          カートModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doOrderConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doOrderConfirm(CartModel cartModel, RedirectAttributes redirectAttributes, Model model) {

        // カート情報をFlashAttributesに保存
        redirectAttributes.addFlashAttribute(AbstractOrderController.CART_DTO, getCartDto(redirectAttributes, model));
        // 遷移元フラグをFlashAttributesに保存
        redirectAttributes.addFlashAttribute(AbstractOrderController.FROM_CART, true);

        // 注文確認画面へ遷移
        return "redirect:/order/confirm.html";
    }

    // 2023-renew No14 to here
    // 2023-renew No24 from here

    /**
     * カート画面：クーポンを取得
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doAddCoupon")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doAddCoupon(@Validated(DoAddCouponGroup.class) CartModel cartModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        if (error.hasErrors()) {
            return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
        }

        // 複数タブでの動作を考慮し、≪画面表示情報取得処理≫を実行し、カート内商品の存在チェックを行う
        if (!checkCartGoodsItems(cartModel, error, redirectAttributes, model)) {
            // デザイン反映にて、クーポンエリアを常に表示するように変更（下記をコメントアウト）
            //            // カート内に商品が存在しない場合、クーポン取得処理を実行せず自画面を表示
            //            return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
        }

        // クーポン取得APIの呼び出し
        if (executeWebApiAddCouponAndSetCouponName(cartModel.getCouponCodeAdd(), model, redirectAttributes)) {
            // 成功した場合、クーポンコード（入力）を初期化
            cartModel.setCouponCodeAdd(null);
        }

        // 自画面を表示
        return displayCartHtml(null, cartModel, redirectAttributes, model, request, response);
    }

    /**
     * カート画面：クーポンラジオボタン選択（Ajax）
     *
     * @param cartModel          カートModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     */
    @GetMapping("/doSelectCoupon")
    @ResponseBody
    public ResponseEntity<String> doSelectCoupon(CartModel cartModel,
                                                 RedirectAttributes redirectAttributes,
                                                 Model model,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        // cookieへの保持は当メソッド処理前に「/common/js/coupon.js」で実施済

        // 選択中のクーポン情報を設定し、メッセージを返す
        String message = null;
        String couponName = setSelectCoupon(null, cartModel, redirectAttributes, model, request, response);
        if (StringUtils.isNotEmpty(couponName)) {
            message = AppLevelFacesMessageUtil.getAllMessage(MSGCD_SELECT_COUPON, new String[] {couponName})
                                              .getMessage();
        }
        return ResponseEntity.ok(message);
    }

    // 2023-renew No24 to here

    /**
     * カート画面：あしあと情報クリア処理(Ajax)
     * あしあとを削除するボタン押下時に呼び出される。
     * 自分のあしあと情報を全てクリアする。
     *
     * @param cartModel カートModel
     */
    @GetMapping("/doClearAccessGoods")
    @ResponseBody
    public void doClearAccessGoods(CartModel cartModel) {

        // 【あしあと商品クリアサービス】実行
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

        //  セッションで保持しているモデルの足あと履歴をクリア
        cartModel.setFootPrintGoodsItems(null);
    }

    /**
     * お気に入りリストへ遷移
     *
     * @param cartModel          カートModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 遷移先画面
     */
    @PostMapping(value = {"/index", "/index.html"}, params = "goFavoriteView")
    public String goFavoriteView(CartModel cartModel, RedirectAttributes redirectAttributes, Model model) {
        redirectAttributes.addFlashAttribute(AbstractOrderController.FROM_CART, true);
        return "redirect:/member/favorite/index.html";
    }

    /**
     * 描画前処理
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected void prerender(CartModel cartModel,
                             BindingResult error,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        // サブアプリケーションが消えた場合に画面表示再現（件数ゼロでも通常遷移ならばNULLではなく空になるはずなので）
        if (cartModel.getCartGoodsItems() == null
            // 2023-renew No14 from here
            || cartModel.getCartGoodsReserveItemMap() == null
            // 2023-renew No14 to here
        ) {
            setDisplayInfo(cartModel, error, redirectAttributes, model); // 画面表示処理
            addWarnMessage(CartModel.MSGCD_PAGE_RELOAD, null, redirectAttributes, model);
        }

        // PDR Migrate Customization from here
        // その他の送料割引キャンペーン無しの時の表示判定：フリーエリアキー「cart_promotion」
        FreeAreaGetRequest freeAreaGetRequest = new FreeAreaGetRequest();
        freeAreaGetRequest.setFreeAreaKey("cart_promotion");
        freeAreaGetRequest.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        FreeAreaEntityResponse freeAreaEntityResponse = null;
        try {
            freeAreaEntityResponse = shopApi.getNewsFreearea(freeAreaGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        FreeAreaEntity freeAreaEntity = cartHelper.toFreeAreaEntity(freeAreaEntityResponse);

        cartModel.setFreeAreCampaign(freeAreaEntity == null || StringUtils.isEmpty(freeAreaEntity.getFreeAreaBodyPc()));
        // PDR Migrate Customization to here
    }

    /**
     * カート投入処理
     * 商品コード、商品数量があれば、カートに投入する
     *
     * @param gcd                商品コード
     * @param gcnt               商品数量
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              Model
     * @param customParams       案件用引数
     * @return カート投入フラグ true..エラーなし
     */
    protected boolean addGoodsToCart(String gcd,
                                     String gcnt,
                                     RedirectAttributes redirectAttributes,
                                     Model model,
                                     Object... customParams) {

        boolean cartAddFlag = false;

        // 商品情報を受け取っていない場合
        if (StringUtils.isEmpty(gcd) || !StringUtils.isNumeric(gcnt)) {
            // カート投入しなかった
            return cartAddFlag;
        }

        // ① 商品情報が取得できた場合
        // ・【カート投入サービス】実行
        CartGoodsAddRequest cartGoodsAddRequest = cartHelper.toCartGoodsAddRequest(gcd, new BigDecimal(gcnt),
                                                                                   getCommonInfo().getCommonInfoBase()
                                                                                                  .getAccessUid(),
                                                                                   getCommonInfo().getCommonInfoBase()
                                                                                                  .getSiteType(),
                                                                                   // 2023-renew No14 from here
                                                                                   commonInfoUtility.getMemberInfoEntity(
                                                                                                   getCommonInfo())
                                                                                   // 2023-renew No14 from here
                                                                                  );
        List<CheckMessageDtoResponse> errorList = null;
        try {
            errorList = cartApi.registCartGoodsAdd(cartGoodsAddRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // ② ・カート投入サービス実行時の戻り値を、エラー情報にセットする。
        if (CollectionUtil.isNotEmpty(errorList)) {
            StringBuilder s = new StringBuilder();
            for (CheckMessageDtoResponse checkMessageDto : errorList) {
                if (s.length() > 0) {
                    // カート投入不可理由が複数ある場合は「・」で区切る
                    s.append("・");
                }
                // カート投入不可理由メッセージ(※)に引数がある場合は組み立て済みのメッセージに置き換える
                // ※エラーメッセージ引数となる別のメッセージ
                Object message = MessageFormat.format(checkMessageDto.getMessage(), checkMessageDto.getArgs());
                s.append(message);
            }
            String[] arg = new String[] {s.toString()};
            this.addMessage(CartModel.MSGCD_CART_ADD_ERROR, arg, redirectAttributes, model);
        } else {
            cartAddFlag = true;
        }

        return cartAddFlag;
    }

    /**
     * 画面表示情報取得
     * 画面に表示する情報を取得し、ページクラスにセットします。
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              Model
     * @return TRUE:正常、FALSE:エラーあり
     */
    protected boolean setDisplayInfo(CartModel cartModel,
                                     BindingResult error,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        // （１） 【カート商品リスト取得サービス】実行
        CartDto cartDto = getCartDto(redirectAttributes, model);

        // （２） 【お気に入り情報リスト取得サービス】実行
        List<FavoriteDto> favoriteDtoList = null;

        if (commonInfoUtility.isLogin(getCommonInfo())) {
            // お気に入りリスト検索
            FavoriteSearchForDaoConditionDto conditionDto =
                            cartHelper.toFavoriteConditionDtoForSearchFavoriteList(cartModel);
            FavoriteListGetRequest favoriteListGetRequest = cartHelper.toFavoriteListGetRequest(conditionDto);

            // ページング検索セットアップ
            PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
            // LIMIT指定無しの場合はデフォルト値をセット
            int limit = cartModel.getFavoriteGoodsLimit();
            if (limit == 0) {
                limit = DEFAULT_FAVORITE_GOODS_LIMIT;
            }
            PageInfoRequest pageInfoRequest = new PageInfoRequest();
            pageInfoHelper.setupPageRequest(pageInfoRequest, null, limit, "updateTime", false);

            // お気に入りリスト検索実行
            FavoriteDtoListResponse favoriteDtoListResponse = null;
            try {
                favoriteDtoListResponse = memberInfoApi.getFavoriteList(favoriteListGetRequest, pageInfoRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoResponse pageInfoResponse =
                            favoriteDtoListResponse.getPageInfo();

            if (pageInfoResponse != null) {
                pageInfoHelper.setupPageInfo(cartModel, pageInfoResponse.getPage(), pageInfoResponse.getLimit(),
                                             pageInfoResponse.getNextPage(), pageInfoResponse.getPrevPage(),
                                             pageInfoResponse.getTotal(), pageInfoResponse.getTotalPages(), null, null,
                                             null, false, null
                                            );
            }

            favoriteDtoList = cartHelper.toFavoriteDtoList(favoriteDtoListResponse);
        }

        // （３） 【公開あしあと商品詳細情報リスト取得サービス】実行
        // LIMIT指定無しの場合はデフォルト値をセット
        if (cartModel.getFootPrintGoodsLimit() == 0) {
            cartModel.setFootPrintGoodsLimit(DEFAULT_FOOTPRINT_GOODS_LIMIT);
        }
        OpenFootPrintListGetRequest openFootPrintListGetRequest = new OpenFootPrintListGetRequest();
        openFootPrintListGetRequest.setLimit(cartModel.getFootPrintGoodsLimit());
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

        List<GoodsGroupDto> footprintGoodsGroupDtoList = cartHelper.toGoodsGroupDtoList(goodsGroupDtoListResponse);

        // 2023-renew No22 from here
        // （４） 【公開関連商品情報リスト取得サービス】実行
        // 2023-renew No22 to here

        // 2023-renew No21 from here
        // よく一緒に購入される商品
        List<GoodsGroupDto> togetherBuyGoodsGroupDtoList = null;
        if (CollectionUtil.isNotEmpty(cartDto.getCartGoodsDtoList())) {
            GoodsGroupTogetherBuyRequest request = new GoodsGroupTogetherBuyRequest();
            // 直近カートインした商品に関連づいた商品を表示する。
            Integer newestGoodsGroupSeq = cartDto.getCartGoodsDtoList().get(0).getGoodsDetailsDto().getGoodsGroupSeq();
            request.setGoodsSeq(newestGoodsGroupSeq);

            // LIMIT指定無しの場合はデフォルト値をセット
            int limit = cartModel.getTogetherBuyGoodsLimit();
            if (limit == 0) {
                limit = DEFAULT_TOGETHER_BUY_GOODS_LIMIT;
            }
            request.setLimit(limit);

            GoodsGroupDtoListResponse goodsTogetherBuyListResponse = null;
            try {
                goodsTogetherBuyListResponse = goodsApi.getGoodsTogetherBuyListByStatus(request);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            togetherBuyGoodsGroupDtoList = cartHelper.toGoodsGroupDtoList(goodsTogetherBuyListResponse);
        }

        // よく一緒に購入される商品
        if (CollectionUtil.isNotEmpty(togetherBuyGoodsGroupDtoList)) {
            try {
                cartHelper.toPageForLoadTogetherBuy(togetherBuyGoodsGroupDtoList, cartModel);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        } else {
            cartModel.setTogetherBuyGoodsItems(new ArrayList<>());
        }
        // 2023-renew No21 to here

        // （６） 取得したカート商品情報、お気に入り情報、あしあと情報、関連商品情報、お知らせをModelクラスにセットする。
        // カート商品情報設定
        cartHelper.toPageForLoad(cartDto, cartModel);

        // お気に入り商品情報設定
        if (CollectionUtil.isNotEmpty(favoriteDtoList)) {
            // お気に入り情報に在庫状況表示を付与
            FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest =
                            cartHelper.toFavoriteGoodsStockStatusGetRequest(favoriteDtoList);
            FavoriteDtoListResponse favoriteDtoListResponse = null;
            try {
                favoriteDtoListResponse =
                                memberInfoApi.getFavoriteByFavoriteGoodsStockStatus(favoriteGoodsStockStatusGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            List<FavoriteDto> stockFavoriteDtoList = cartHelper.toFavoriteDtoList(favoriteDtoListResponse);
            try {
                // 2023-renew No11 from here
                this.setCartFavoriteInfo(stockFavoriteDtoList, cartModel);
                // 2023-renew No11 to here
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
            FavoriteForCartCheckRequest favoriteForCartCheckRequest =
                            cartHelper.toFavoriteForCartCheckRequest(memberInfoSeq, cartDto);
            List<FavoriteEntityResponse> favoriteEntityResponseList = null;
            try {
                favoriteEntityResponseList = memberInfoApi.getFavoriteForCartCheck(favoriteForCartCheckRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            List<FavoriteEntity> favoriteList = cartHelper.toFavoriteEntityList(favoriteEntityResponseList);
            cartHelper.setFavoriteView(favoriteList, cartModel);
        } else {
            cartModel.setFavoriteGoodsItems(new ArrayList<>());
        }

        // あしあと商品情報設定
        try {
            cartHelper.toPageForLoadFootprint(footprintGoodsGroupDtoList, cartModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 2023-renew No22 from here
        // 関連商品情報設定
        // 2023-renew No22 to here

        // （７） 【カート商品チェックサービス】実行
        CartGoodsCheckRequest cartGoodsCheckRequest = cartHelper.toCartGoodsCheckRequest(getCommonInfo(), cartDto);
        CartGoodsCheckMapResponse cartGoodsCheckMapResponse = null;
        try {
            cartGoodsCheckMapResponse = cartApi.checkCartGoods(cartGoodsCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        Map<Integer, List<CheckMessageDto>> checkMessageDtoListMap = cartHelper.toCheckMessageDtoListMap(
                        cartGoodsCheckMapResponse != null ? cartGoodsCheckMapResponse.getMessages() : null);

        // （８） カート商品チェックサービスの結果をエラー情報に追加
        if (checkMessageDtoListMap != null) {
            cartHelper.toPageForLoad(checkMessageDtoListMap, cartModel);
        }

        addErrorInfo(checkMessageDtoListMap, cartDto, error, redirectAttributes, model);

        // 2023-renew No14 from here
        return !cartUtility.isErrorAbortProcessing(checkMessageDtoListMap, cartDto.getCartGoodsDtoList());
        // 2023-renew No14 to here
    }

    // 2023-renew No11 from here

    /**
     * お気に入りの商品情報の設定
     *
     * @param stockFavoriteDtoList  お気に入りDTOリスト
     * @param cartModel             カートModel
     */
    protected void setCartFavoriteInfo(List<FavoriteDto> stockFavoriteDtoList, CartModel cartModel) {
        cartHelper.toPageForLoadFavorite(stockFavoriteDtoList, cartModel);
        Map<String, FavoriteDto> favoriteDtoMap = new LinkedHashMap<>();
        for (FavoriteDto favoriteDto : stockFavoriteDtoList) {
            favoriteDtoMap.put(favoriteDto.getGoodsDetailsDto().getGoodsCode(), favoriteDto);
        }

        // WEB-API（数量割引情報取得）から数量割引情報を取得する
        WebApiGetQuantityDiscountResponseDto getQuantityDiscountDto =
                        executeWebApiGetQuantityDiscount(stockFavoriteDtoList);

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockMap =
                        executeWebApiGetStockByFavoriteList(stockFavoriteDtoList);

        cartModel.getFavoriteGoodsItems().forEach(favoriteGoodsItem -> {
            for (WebApiGetQuantityDiscountResponseDetailDto responseInfo : getQuantityDiscountDto.getInfo()) {
                if (Objects.nonNull(stockMap) && Objects.nonNull(stockMap.get(responseInfo.getGoodsCode()))) {
                    // 商品の販売状態をチェック
                    GoodsDetailsDto goodsDetailsDto =
                                    favoriteDtoMap.get(responseInfo.getGoodsCode()).getGoodsDetailsDto();
                    if (goodsUtility.isGoodsItemNoSales(goodsDetailsDto.getSaleStatusPC(),
                                                        goodsDetailsDto.getSaleStartTimePC(),
                                                        goodsDetailsDto.getSaleEndTimePC()
                                                       )) {
                        // 非販売の場合 在庫表示に[×」を設定
                        favoriteGoodsItem.setStock(CartModel.DISP_OUT_OF_STOCK);
                    } else {
                        // 販売中の場合
                        HTypeStockManagementFlag stockManagementFlag = goodsDetailsDto.getStockManagementFlag();
                        // 売切対象商品の場合
                        WebApiGetStockResponseDetailDto webApiGetStockResponseDetailPdrDto =
                                        stockMap.get(responseInfo.getGoodsCode());
                        int stockQuantity = webApiGetStockResponseDetailPdrDto.getStockQuantity().intValue();
                        int remainStockQuantity = goodsDetailsDto.getRemainderFewStock().intValue();
                        if (HTypeStockManagementFlag.ON.equals(stockManagementFlag)) {

                            if (stockQuantity > remainStockQuantity) {
                                favoriteGoodsItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                            } else if (remainStockQuantity >= stockQuantity && stockQuantity > 0 || (stockQuantity > 0
                                                                                                     && "1".equals(
                                            webApiGetStockResponseDetailPdrDto.getDeliveryYesNo()))) {
                                favoriteGoodsItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                            } else if (stockQuantity == 0 && "0".equals(
                                            webApiGetStockResponseDetailPdrDto.getDeliveryYesNo())) {
                                favoriteGoodsItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                            }
                        } else {
                            // 売切対象外商品の場合
                            if (stockQuantity > 0) {
                                favoriteGoodsItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                            } else if (stockQuantity == 0) {
                                favoriteGoodsItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                            } else {
                                favoriteGoodsItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                            }
                        }
                    }

                    favoriteGoodsItem.setSaleControl(stockMap.get(responseInfo.getGoodsCode()).getSaleControl());
                }
            }
        });

        if (commonInfoUtility.isLogin(getCommonInfo())) {
            // 販売可否判定API
            List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                            this.executeWebApiGetSaleCheck(cartModel.getFavoriteGoodsItems());
            if (CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses)) {
                cartModel.getFavoriteGoodsItems().forEach(stockItem -> {
                    webApiGetSaleCheckDetailResponses.forEach(res -> {
                        if (Objects.equals(res.getGoodsCode(), stockItem.getGcd())) {
                            stockItem.setSaleCheck(res.getGoodsSaleYesNo());
                        }
                    });
                });
            }
        }
    }

    /**
     * WEB-API連携 数量割引情報取得を行います。
     *
     * @param goodsDtoList    商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    protected WebApiGetQuantityDiscountResponseDto executeWebApiGetQuantityDiscount(List<FavoriteDto> goodsDtoList) {

        List<String> goodsCodeList = new ArrayList<>();
        for (FavoriteDto favoriteDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            favoriteDto.getGoodsDetailsDto().getGoodsCode());
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
                        cartHelper.toWebApiGetQuantityDiscountResponseDto(webApiGetQuantityDiscountResponse);
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

    /**
     * WEB-API連携 商品在庫数取得を行います。
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> executeWebApiGetStockByFavoriteList(List<FavoriteDto> goodsDtoList) {

        WebApiGetStockRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetStockRequestDto.class);

        List<String> goodsCodeList = new ArrayList<String>();
        List<String> goodsQuantityList = new ArrayList<String>();
        for (FavoriteDto favoriteDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            favoriteDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
                goodsQuantityList.add(FAVORITE_GOODS_QUANTITY);
            }
        }

        reqDto.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        reqDto.setQuantity(WebApiUtility.createStrPipeByList(goodsQuantityList));

        try {
            CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
            WebApiGetStockRequest webApiGetStockRequest = new WebApiGetStockRequest();
            webApiGetStockRequest.setGoodsCode(reqDto.getGoodsCode());
            webApiGetStockRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
            webApiGetStockRequest.setQuantity(reqDto.getQuantity());

            WebApiGetStockResponse webApiGetStockResponse = null;
            try {
                webApiGetStockResponse = webapiApi.getStock(webApiGetStockRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            WebApiGetStockResponseDto stockDto = cartHelper.toWebApiGetStockResponseDto(webApiGetStockResponse);

            return stockDto.getMap();
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;

    }

    /**
     * WEB-API連携 販売可否判定
     *
     * @param cartModelItems 在庫商品リスト
     * @return 販売可否判定 詳細情報レスポンスリスト
     */
    protected List<WebApiGetSaleCheckDetailResponse> executeWebApiGetSaleCheck(List<CartModelItem> cartModelItems) {
        List<String> goodsCodeList = new ArrayList<>();
        cartModelItems.forEach(stockItem -> {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(stockItem.getGcd());

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
     * カート商品数量変更処理の実行
     *
     * @param cartModel          カートModel
     */
    protected void updateCartGoods(CartModel cartModel) {

        // （１） カート画面より、カート一覧情報と数量を取得する。
        CartDto cartDto = cartHelper.toCartDtoForCalculate(cartModel);

        // （２） 【カート商品数量変更サービス】実行
        try {
            cartApi.updateCartGoods(
                            cartHelper.toCartGoodsChangeRequest(getCommonInfo().getCommonInfoUser().getMemberInfoSeq(),
                                                                getCommonInfo().getCommonInfoBase().getAccessUid(),
                                                                cartDto.getCartGoodsDtoList()
                                                               ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

    }

    /**
     * エラーメッセージセット
     * <pre>
     * 販売可能商品区分が購入不可 又は 閲覧不可の商品の場合のエラーメッセージ分岐を追加
     * </pre>
     *
     * @param checkMessageDtoListMap メッセージDtoMap
     * @param cartDto                カート情報DTO
     * @param error                  エラー
     * @param redirectAttributes     リダイレクトアトリビュート
     * @param model                  モデル
     */
    protected void addErrorInfo(Map<Integer, List<CheckMessageDto>> checkMessageDtoListMap,
                                CartDto cartDto,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // PDR Migrate Customization from here
        // 購入制限エラーメッセージ重複確認用リスト
        ArrayList<String> saleNgMessageList = new ArrayList<>();
        // PDR Migrate Customization to here

        // 2023-renew No14 from here
        // エラーメッセージ重複確認用リスト
        ArrayList<String> messageList = new ArrayList<>();
        // 2023-renew No14 to here

        if (!cartUtility.isErrorAbortProcessing(checkMessageDtoListMap, cartDto.getCartGoodsDtoList())) {
            return;
        }
        boolean hasIndividualDeliveryError = false;
        // PDR Migrate Customization from here
        // 合計金額超過フラグ
        boolean priceMaxOverFlag = false;
        // PDR Migrate Customization to here
        for (int i = 0; i < cartDto.getCartGoodsDtoList().size(); i++) {
            CartGoodsDto cartGoodsDto = cartDto.getCartGoodsDtoList().get(i);
            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            GoodsDetailsDto goodsDetailsDto = cartGoodsDto.getGoodsDetailsDto();

            List<CheckMessageDto> checkMessageDtoList = checkMessageDtoListMap.get(cartGoodsEntity.getCartSeq());
            if (checkMessageDtoList == null) {
                continue;
            }

            StringBuilder s = new StringBuilder();
            StringBuilder code = new StringBuilder();
            for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                String msgCd = checkMessageDto.getMessageId();
                switch (msgCd) {
                    case MSGCD_INDIVIDUAL_DELIVERY:
                        // 個別配送商品エラー
                        hasIndividualDeliveryError = true;
                        break;
                    case MSGCD_BUILD_TO_ORDER:
                        break;
                    default:
                        if (s.length() > 0) {
                            s.append("　");
                        }
                        s.append(checkMessageDto.getMessage());
                        code.append(msgCd);
                        break;
                }
            }

            // エラーメッセージ用商品名取得
            StringBuilder displayGoodsName = new StringBuilder();
            // PDR Migrate Customization from here
            displayGoodsName.append(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto));
            // PDR Migrate Customization to here
            // 規格管理する場合は商品表示名（規格値１/規格値２）をエラーメッセージに表示する
            if (goodsDetailsDto.getUnitManagementFlag() == HTypeUnitManagementFlag.ON) {
                displayGoodsName.append("(");
                // 規格管理ありの場合は規格値１は必須項目なので必ず取得できる
                displayGoodsName.append(goodsDetailsDto.getUnitValue1());
                // 規格値２の値がNULLの場合は商品表示名（規格値１）のみをエラーメッセージに表示する
                // 規格値２の値が存在する場合は規格値２をエラーメッセージに表示する
                if (goodsDetailsDto.getUnitValue2() != null) {
                    displayGoodsName.append("/");
                    displayGoodsName.append(goodsDetailsDto.getUnitValue2());
                }
                displayGoodsName.append(")");
            }

            // 2023-renew No14 from here
            // 商品コード（重複チェック用）
            String goodsCode = goodsDetailsDto.getGoodsCode();
            // 2023-renew No14 to here

            if (code.length() > 0) {
                if (code.indexOf(MSGCD_OPEN_STATUS_HIKOUKAI) != -1 || code.indexOf(MSGCD_OPEN_BEFORE) != -1
                    || code.indexOf(MSGCD_OPEN_END) != -1) {
                    // 商品状態が非公開、公開中止、公開前、公開終了のいずれか場合
                    addWarnMessage(goodsCode, messageList, CartModel.MSGCD_CART_OPEN_ERROR,
                                   new String[] {displayGoodsName.toString()}, redirectAttributes, model
                                  );
                } else if (code.indexOf(MSGCD_SALE_STATUS_HIHANBAI) != -1 || code.indexOf(MSGCD_SALE_BEFORE) != -1
                           || code.indexOf(MSGCD_SALE_END) != -1) {
                    // 商品状態が非販売、販売前、販売終了のいずれか場合
                    addWarnMessage(goodsCode, messageList, CartModel.MSGCD_CART_SALES_ERROR,
                                   new String[] {displayGoodsName.toString()}, redirectAttributes, model
                                  );
                } else if (code.indexOf(MSGCD_NO_STOCK) != -1) {
                    // 商品状態が在庫切れの場合
                    addWarnMessage(goodsCode, messageList, CartModel.MSGCD_CART_STOCK_ERROR,
                                   new String[] {displayGoodsName.toString()}, redirectAttributes, model
                                  );
                } else if (code.indexOf(MSGCD_LESS_STOCK) != -1) {
                    // 商品状態が在庫不足の場合
                    addWarnMessage(goodsCode, messageList, CartModel.MSGCD_CART_LESS_STOCK_ERROR,
                                   new String[] {displayGoodsName.toString(),
                                                   goodsDetailsDto.getSalesPossibleStock().toString()},
                                   redirectAttributes, model
                                  );
                } else if (code.indexOf(MSGCD_ALCOHOL_CHECK_ERROR) != -1) {
                    // 酒類購入不可エラー
                    addWarnMessage(goodsCode, messageList, MSGCD_ALCOHOL_CHECK_ERROR,
                                   new String[] {displayGoodsName.toString()}, redirectAttributes, model
                                  );
                }
                // PDR Migrate Customization from here
                // 2023-renew No2 from here
                else if (code.indexOf(MSGCD_ERROR_SALE_CHECK_NO) != -1) {
                    //販売可否判定．販売可否判定結果 = 「0:販売不可」
                    addWarnMessage(goodsCode, messageList, MSGCD_ERROR_SALE_CHECK_NO_BY_GOODS,
                                   new String[] {displayGoodsName.toString(), "販売不可商品"}, redirectAttributes, model
                                  );
                } else if (code.indexOf(MSGCD_CART_GOODS_MAX_OVER) != -1) {
                    // カート内最大商品件数超過エラー
                    String maxCartGoodsCount = PropertiesUtil.getSystemPropertiesValue(SYS_KEY_MAX_CART_GOODS_COUNT);
                    addWarnMessage(goodsCode, messageList, MSGCD_ERROR_CARTIN_COUNT_LIMIT_OVER,
                                   new String[] {maxCartGoodsCount}, redirectAttributes, model
                                  );
                }
                // 2023-renew No2 to here
                else if (code.indexOf(MSGCD_CART_TOTAL_PRICE_MAX_OVER) != -1) {
                    // カート内の商品が組み合わせにより利用可能金額をオーバーした場合
                    // フラグを設定
                    priceMaxOverFlag = true;
                } else if (code.indexOf(MSGCD_NO_POSSIBLE) != -1) {
                    // 要素がリストに存在しなかった場合、メッセージを設定する
                    if (!saleNgMessageList.contains(goodsDetailsDto.getSaleNgMessage())) {
                        // 購入制限エラー
                        if (StringUtil.isEmpty(goodsDetailsDto.getSaleNgMessage())) {
                            addWarnMessage(goodsCode, messageList, MSGCD_SALE_NG_WITH_NULL_SALE_NG_MESSAGE,
                                           new String[] {goodsDetailsDto.getGoodsGroupName()}, redirectAttributes, model
                                          );
                        } else {
                            addWarnMessage(goodsCode, messageList, MSGCD_SALE_NG,
                                           new String[] {goodsDetailsDto.getSaleNgMessage()}, redirectAttributes, model
                                          );
                            saleNgMessageList.add(goodsDetailsDto.getSaleNgMessage());
                        }
                    }
                }
                // PDR Migrate Customization to here
                // 2023-renew No14 from here
                else if (code.indexOf(MSGCD_NOT_RESERVE) != -1) {
                    // セールde予約商品にセールde予約不可の商品が含まれている場合
                    addWarnMessage(goodsCode, messageList, MSGCD_NOT_RESERVE_BY_GOODS,
                                   new String[] {displayGoodsName.toString(), "販売不可商品"}, redirectAttributes, model
                                  );
                }
                // 2023-renew No14 to here
                else {
                    // 上記以外の場合
                    addWarnMessage(goodsCode, messageList, CartModel.MSGCD_CART_ERROR,
                                   new String[] {displayGoodsName.toString(), s.toString()}, redirectAttributes, model
                                  );
                }

                if (code.indexOf(MSGCD_LESS_STOCK) != -1 || code.indexOf(MSGCD_PURCHASED_MAX_OVER) != -1) {
                    // 2023-renew No14 from here
                    // 今すぐお届け商品の場合
                    if (HTypeReserveDeliveryFlag.OFF.equals(cartGoodsDto.getCartGoodsEntity().getReserveFlag())) {
                        // 在庫不足 or 最大購入可能数超過 の場合、対象商品の数量入力欄にエラースタイルを適用する
                        error.rejectValue("cartGoodsItems[" + i + "].gcnt", null, "");
                    }
                    // 2023-renew No14 to here
                }
            }
        }

        // 個別配送商品チェック。
        // 他の商品と一緒に個別配送商品が入っていたらエラー
        if (hasIndividualDeliveryError) {
            addWarnMessage(CartModel.MSGCD_INDIVIDUAL_DELIVERY_ERROR, null, redirectAttributes, model);
        }
        // PDR Migrate Customization from here
        // 合計金額超過エラーチェック
        if (priceMaxOverFlag) {
            addWarnMessage(CartModel.MSGCD_CART_TOTAL_PRICE_MAX_OVER, null, redirectAttributes, model);
        }
        // PDR Migrate Customization to here
    }

    // 2023-renew No14 from here

    /**
     * エラーメッセージを追加
     * ※ただし、重複する場合は追加しない
     *
     * @param goodsCode          商品コード
     * @param messageList        エラーメッセージ重複確認用リスト
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected void addWarnMessage(String goodsCode,
                                  ArrayList<String> messageList,
                                  String messageCode,
                                  String[] args,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        // 重複チェック用KEY文言生成
        // ※商品コード＋「:」＋メッセージコード＋「:」＋メッセージ引数
        StringBuilder key = new StringBuilder();
        key.append(goodsCode).append(":").append(messageCode);
        if (ObjectUtils.isNotEmpty(args)) {
            key.append(":").append(Arrays.toString(args));
        }

        // 重複確認用リストに存在しない場合のみ、メッセージを追加
        if (!messageList.contains(key.toString())) {
            addWarnMessage(messageCode, args, redirectAttributes, model);
            messageList.add(key.toString());
        }

    }

    // 2023-renew No14 to here

    /**
     * カートDto取得
     *
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return カートDto
     */
    protected CartDto getCartDto(RedirectAttributes redirectAttributes, Model model) {

        // PDR Migrate Customization from here
        // WEB-API 呼び出し処理
        CartDto cartDto = cartDtoCorrection(redirectAttributes, model);

        // カート情報再設定
        // カート合計数量をセット
        getCommonInfo().getCommonInfoBase().setCartGoodsSumCount(cartDto.getGoodsTotalCount());
        // カート合計金額をセット
        getCommonInfo().getCommonInfoBase()
                       .setCartGoodsSumPrice(cartDto.getGoodsTotalPrice() == null ?
                                                             BigDecimal.ZERO :
                                                             cartDto.getGoodsTotalPrice());
        // PDR Migrate Customization to here

        return cartDto;
    }

    // PDR Migrate Customization from here

    /**
     * WEB-API連携を行い、カートDTOに情報を反映します。
     * <pre>
     * 以下WEB-API連携を行い、商品をカートに投入します。
     * ・割引適用結果取得
     * ・数量割引適用結果取得
     * ・商品在庫数取得
     * ・取りおき情報取得
     * ・消費税率取得
     *
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return カートDTO
     */
    protected CartDto cartDtoCorrection(RedirectAttributes redirectAttributes, Model model) {

        String accessUid = getCommonInfo().getCommonInfoBase().getAccessUid();
        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
        String siteType = HTypeSiteType.FRONT_PC.getValue();
        String orderField = "registTime";
        Boolean orderAsc = false;
        // 2023-renew No14 from here
        MemberInfoEntity memberInfoEntity = commonInfoUtility.getMemberInfoEntity(getCommonInfo());
        Integer customerNo = memberInfoEntity.getCustomerNo();
        String zipcode = memberInfoEntity.getMemberInfoZipCode();

        CartDtoCorrectionScreenUpdateRequest cartDtoCorrectionScreenUpdateRequest =
                        cartHelper.toCartDtoCorrectionScreenUpdateRequest(accessUid, memberInfoSeq, siteType,
                                                                          orderField, orderAsc, customerNo, zipcode
                                                                         );
        // 2023-renew No14 to here

        CartDtoCorrectionScreenUpdateResponse cartDtoCorrectionScreenUpdateResponse = null;
        try {
            // カート再構築
            cartDtoCorrectionScreenUpdateResponse =
                            cartApi.updateCartDtoCorrectionScreen(cartDtoCorrectionScreenUpdateRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 返却されたエラー情報を追加
        List<CheckMessageDtoResponse> checkMessageDtoList =
                        cartDtoCorrectionScreenUpdateResponse.getCheckMessageDtoList();
        if (CollectionUtil.isNotEmpty(checkMessageDtoList)) {
            for (CheckMessageDtoResponse checkMessageDto : checkMessageDtoList) {
                if (MSGCD_CART_ADD_GOODS_ERR.equals(checkMessageDto.getMessageId())) {
                    // DBに商品が存在しない場合 エラーメッセージを表示
                    addWarnMessage(MSGCD_CART_ADD_GOODS_ERR, new String[] {}, redirectAttributes, model);
                }
            }
        }

        return cartHelper.toCartDto(cartDtoCorrectionScreenUpdateResponse.getCartDto());
    }

    /**
     * カート内商品の存在チェック
     *
     * @param cartModel          カートModel
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return true:存在する false:存在しない
     */
    public boolean checkCartGoodsItems(CartModel cartModel,
                                       BindingResult error,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        // ≪画面表示情報取得処理≫実行
        setDisplayInfo(cartModel, error, redirectAttributes, model);

        // 2023-renew No14 from here
        // カート内の商品存在チェック
        return cartModel.isCartIn();
        // 2023-renew No14 to here
    }

    // PDR Migrate Customization to here
    // 2023-renew No24 from here

    /**
     * カート画面を表示する
     */
    protected String displayCartHtml() {
        // カート画面を表示する
        return "cart/index";
    }

    /**
     * クーポン関連情報を設定してから、カート画面を表示する
     *
     * @param optionValue        オプション値（クーポン番号用）
     * @param cartModel          カートModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     */
    protected String displayCartHtml(String optionValue,
                                     CartModel cartModel,
                                     RedirectAttributes redirectAttributes,
                                     Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        // クーポン情報設定
        setCouponInfo(optionValue, cartModel, redirectAttributes, model, request, response);

        // カート画面を表示する
        return displayCartHtml();
    }

    /**
     * クーポン関連情報を設定する
     *
     * @param optionValue        オプション値（クーポン番号用）
     * @param cartModel          カートModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     */
    protected void setCouponInfo(String optionValue,
                                 CartModel cartModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        // デザイン反映にて、クーポンエリアを常に表示するように変更（下記をコメントアウト）
        //        // カート内に商品がない場合、クーポン関連の処理は不要
        //        if (!cartModel.isCartIn()) {
        //            return;
        //        }

        // 利用可能クーポン一覧取得APIの呼び出しを行い、セッション情報の利用可能クーポン一覧数を更新
        cartModel.setCouponList(executeWebApiGetCouponListAndSetCouponCount(model, redirectAttributes));

        // 選択中のクーポン情報を設定し、メッセージを返す
        String couponName = setSelectCoupon(optionValue, cartModel, redirectAttributes, model, request, response);
        if (StringUtils.isNotEmpty(couponName)) {
            addInfoMessage(MSGCD_SELECT_COUPON, new String[] {couponName}, redirectAttributes, model,
                           COUPON_INFO_FLASH_MESSAGES_ATTRIBUTE
                          );
        }

        // ログイン会員ID（cookie用）
        cartModel.setLoginUserId(getCommonInfo().getCommonInfoUser().getMemberInfoId());

    }

    /**
     * 選択中のクーポン情報を設定する
     *
     * @param optionValue        オプション値（クーポン番号用）
     * @param cartModel          カートModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 設定したクーポン名（基幹マスタに存在しない場合、NULL）
     */
    protected String setSelectCoupon(String optionValue,
                                     CartModel cartModel,
                                     RedirectAttributes redirectAttributes,
                                     Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        // 選択中のクーポンコードがある場合、初期値に設定
        cartModel.setCouponCode(getSelectCouponCode(optionValue, redirectAttributes, model, request));

        // セッション情報のクーポン関連情報を設定する（基幹マスタに存在しない場合、セッションクリア）
        String couponName =
                        setSessionCouponInfo(cartModel.getCouponCode(), cartModel.getCouponList(), request, response);
        if (StringUtils.isEmpty(couponName)) {
            // 基幹マスタに存在しない場合、クーポンコードの選択をクリア
            cartModel.setCouponCode(null);
        }
        return couponName;
    }

    /**
     * クーポン関連のメッセージ表示用のアトリビュート名を取得する
     *
     * @return アトリビュート名
     */
    @Override
    protected String getAttributeName() {
        // クーポン関連メッセージを出し分ける
        return COUPON_ERROR_FLASH_MESSAGES_ATTRIBUTE;
    }

    // 2023-renew No24 to here

}
