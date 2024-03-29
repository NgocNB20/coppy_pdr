/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsRegistCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsGetByCodeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsMapRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.newfaces.FacesMessage;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCartInPossible;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.exception.ControllerException;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.CatalogValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoAddCartGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoGetGoodsInfoGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 一括注文画面 Controller
 *
 * @author pham-q7
 */
@SessionAttributes(value = "catalogModel")
@RequestMapping("/cart")
@Controller
// PDR Migrate Customization from here
public class CatalogController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogController.class);

    /**
     * デフォルト最大表示件数
     */
    private static final int DEFAULT_LIMIT = 10;

    /**
     * 商品情報取得エラー
     * MSGCD_GOODS_DETAILS_NOT_FOUND
     */
    public static final String MSGCD_GOODS_DETAILS_NOT_FOUND = "PDR-0002-002-A-";

    /**
     * 一括注文の商品が重複
     */
    public static final String MSGCD_CART_GOODS_DUPLICATE = "PDR-0002-003-A-";

    // 2023-renew No2 from here
    /**
     * 販売可否判定．販売可否判定結果 = 「0:販売不可」
     */
    public static final String MSGCD_GOODS_NOT_PURCHASE = "PDR-2023RENEW-2-003-E";
    // 2023-renew No2 to here
    /**
     * 一括注文画面 Helper
     */
    private final CatalogHelper catalogHelper;

    /**
     * 一括注文画面の動的バリデータクラス
     */
    private final CatalogValidator catalogValidator;

    /**
     * 商品Api
     */
    private final GoodsApi goodsApi;

    /**
     * カートApi
     */
    private final CartApi cartApi;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    // 2023-renew No2 from here
    /**
     * WebapiApi
     */
    private final WebapiApi webapiApi;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * 商品コードMap
     */
    private Map<String, Integer> goodsCodeMap;

    /**
     * コンストラクタ
     *  @param catalogHelper     一括注文画面 Helper
     * @param catalogValidator  一括注文画面の動的バリデータクラス
     * @param goodsApi          商品Api
     * @param cartApi           カートApi
     * @param conversionUtility 変換ユーティリティクラス
     * @param dateUtility       日付関連Utilityクラス
     * @param webapiApi
     * @param commonInfoUtility 共通情報ヘルパークラス
     */
    @Autowired
    public CatalogController(CatalogHelper catalogHelper,
                             CatalogValidator catalogValidator,
                             GoodsApi goodsApi,
                             CartApi cartApi,
                             ConversionUtility conversionUtility,
                             DateUtility dateUtility,
                             WebapiApi webapiApi,
                             CommonInfoUtility commonInfoUtility) {
        this.catalogHelper = catalogHelper;
        this.catalogValidator = catalogValidator;
        this.goodsApi = goodsApi;
        this.cartApi = cartApi;
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.webapiApi = webapiApi;
        this.commonInfoUtility = commonInfoUtility;
        this.goodsCodeMap = new LinkedHashMap<>();
        // 2023-renew No2 to here
    }

    /**
     * 一括注文の動的バリデータ
     *
     * @param error エラー
     */
    @InitBinder(value = "catalogModel")
    public void initBinder(WebDataBinder error) {
        // 一括注文の動的バリデータをセット
        error.addValidators(catalogValidator);
    }

    /**
     * 画像表示処理
     * 初期表示用メソッド
     *
     * @param catalogModel       一括注文Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return ページクラス
     */
    @GetMapping({"/catalog", "/catalog.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/catalog")
    public String doLoadIndex(CatalogModel catalogModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // 初期表示設定
        setDisplayInfo(catalogModel);

        // 一括注文画面を表示する
        return "cart/catalog";
    }

    /**
     * 初期表示設定
     *
     * @param catalogModel 一括注文Model
     */
    protected void setDisplayInfo(CatalogModel catalogModel) {
        // 初期表示情報をPageクラスにセット
        catalogHelper.toPageForLoad(catalogModel, DEFAULT_LIMIT);
    }

    /**
     * 商品情報取得処理
     *
     * @param catalogModel       一括注文Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return ページクラス
     */
    @PostMapping(value = {"/catalog", "/catalog.html"}, params = "doGetGoodsInfo")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/catalog")
    public String doGetGoodsInfo(@Validated(DoGetGoodsInfoGroup.class) CatalogModel catalogModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (error.hasErrors()) {
            return "cart/catalog";
        }

        // 商品番号の重複チェック
        checkDuplicateGoods(catalogModel);
        // 2023-renew No2 from here
        Map<String, String> errorMap = new HashMap<>();

        // 商品詳細情報を取得し、Modelにセットする
        setPageForGoodsDetails(catalogModel, false, errorMap);
        // 2023-renew No2 to here

        // 一括注文画面を表示する
        return "cart/catalog";
    }

    /**
     * 一括カートイン処理
     *
     * @param catalogModel       一括注文Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return ページクラス
     */
    @PostMapping(value = {"/catalog", "/catalog.html"}, params = "doAddCart")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/catalog")
    public String doAddCart(@Validated(DoAddCartGroup.class) CatalogModel catalogModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            // 2023-renew No2 from here
                            HttpServletRequest httpServletRequest,
                            // 2023-renew No2 to here
                            Model model) {

        if (error.hasErrors()) {
            return "cart/catalog";
        }

        // 商品番号の重複チェック
        checkDuplicateGoods(catalogModel);

        // 商品詳細情報を取得し、Modelにセットする
        // また、カート画面へ渡すDtoを戻り値として受け取る
        // 2023-renew No2 from here
        Map<String, String> errorMap = new HashMap<>();
        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList =
                        setPageForGoodsDetails(catalogModel, true, errorMap);

        httpServletRequest.setAttribute("errorMap", errorMap);
        if (!errorMap.isEmpty()) {
            return "cart/catalog";
        }
        // 2023-renew No2 to here

        CartGoodsRegistCheckRequest request = new CartGoodsRegistCheckRequest();
        request.setCartGoodsForTakeOverDtoListRequest(
                        catalogHelper.toCartGoodsForTakeOverDtoRequestList(cartGoodsForTakeOverDtoList));
        request.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        request.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());

        // カート内最大商品件数超過チェック
        try {
            cartApi.checkCartGoodsRegist(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 作成したDtoをセッションに格納する
        redirectAttributes.addFlashAttribute(CartController.CART_GOODS_DTO_LIST_KEY, cartGoodsForTakeOverDtoList);

        // カート画面へ遷移
        return "redirect:/cart/index.html";
    }

    /**
     * 商品詳細情報を取得し、一括注文Modelにセットする
     * また、カート一括登録用引継DTOを作成し、返却する
     *
     * @param catalogModel 一括注文Model
     * @param createDtoFlg カート画面引継用Dto作成フラグ（true:作成する、false:作成しない）
     * @return カート一括登録用引継DTOリスト
     */
    // 2023-renew No2 from here
    protected List<CartGoodsForTakeOverDto> setPageForGoodsDetails(CatalogModel catalogModel,
                                                                   boolean createDtoFlg,
                                                                   Object... additionParams) {
        // 2023-renew No2 to here
        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList = new ArrayList<>();

        // カタログ行数分処理する
        for (int i = 0; i < catalogModel.getCatalogItems().size(); i++) {
            CatalogModelItem catalogItem = catalogModel.getCatalogItems().get(i);

            GoodsDetailsDto goodsDetailsDto = null;
            // 商品番号が入力されている行のみ、商品情報を取得する
            if (catalogItem.getGoodsCode() != null) {
                // 商品情報を取得する
                try {
                    // 2023-renew No2 from here
                    goodsDetailsDto = getGoodsDetails(catalogItem.getGoodsCode(), i + 1);
                    // 2023-renew No2 to here
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }
            } else {
                // 商品番号が未入力の行は、商品名、単価を空にするため、空のDtoを作成する
                CatalogModelItem catalogItemNew = ApplicationContextUtility.getBean(CatalogModelItem.class);
                catalogItemNew.setCatalogIndex(i);
                catalogModel.getCatalogItems().set(i, catalogItemNew);
                goodsDetailsDto = ApplicationContextUtility.getBean(GoodsDetailsDto.class);
            }

            // Pageクラスへデータ反映
            // PDR Migrate Customization from here
            try {
                catalogHelper.toPageForGoodsDetails(catalogModel, catalogItem, goodsDetailsDto);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            // PDR Migrate Customization to here

            // 一括カートイン処理時にのみDtoを作成する
            if (!createDtoFlg) {
                continue;
            }

            // 商品番号が未入力の行はカートイン対象外
            if (catalogItem.getGoodsCode() == null) {
                continue;
            }

            // カート一括登録用引継DTOを作成
            CartGoodsForTakeOverDto cartGoodsForTakeOverDto =
                            ApplicationContextUtility.getBean(CartGoodsForTakeOverDto.class);
            // 商品グループSEQ
            cartGoodsForTakeOverDto.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
            // 商品SEQ
            cartGoodsForTakeOverDto.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
            // 商品番号
            cartGoodsForTakeOverDto.setGoodsCode(catalogItem.getGoodsCode());
            // 商品数量
            cartGoodsForTakeOverDto.setGoodsCount(conversionUtility.toBigDecimal(catalogItem.getOrderQuantity()));
            cartGoodsForTakeOverDtoList.add(cartGoodsForTakeOverDto);
        }

        if (hasErrorMessage()) {
            throwMessage();
        }

        // 画面から入力された順序のListをそのままカート画面に渡すと、順序が逆に表示されてしまう
        // そのため、後ろに入力された商品から順にListを作り直す
        Collections.reverse(cartGoodsForTakeOverDtoList);

        // 2023-renew No2 from here
        // 販売可否判定取得
        // 一括カートイン処理時にのみ販売可否判定を取得する
        if (createDtoFlg) {
            getSaleCheck(catalogModel, additionParams);
        }
        // 2023-renew No2 to here

        return cartGoodsForTakeOverDtoList;
    }

    /**
     * 商品詳細情報を取得する
     *
     * @param goodsCode    商品番号
     * @param catalogDspNo 行No
     * @return 商品詳細Dto
     */
    // 2023-renew No2 from here
    protected GoodsDetailsDto getGoodsDetails(String goodsCode, Integer catalogDspNo)
    // 2023-renew No2 to here
                    throws HttpClientErrorException {
        // ショップSEQ
        Integer shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq();
        // サイト区分
        HTypeSiteType siteType = this.getCommonInfo().getCommonInfoBase().getSiteType();
        // 公開状態
        HTypeOpenDeleteStatus openStatus = HTypeOpenDeleteStatus.OPEN;
        // PDR Migrate Customization from here
        // 公開情報（非公開）
        HTypeOpenDeleteStatus noOpenStatus = HTypeOpenDeleteStatus.NO_OPEN;
        // PDR Migrate Customization to here

        GoodsDetailsGetByCodeRequest goodsDetailsGetByCodeRequest = new GoodsDetailsGetByCodeRequest();
        goodsDetailsGetByCodeRequest.setCode(goodsCode);
        if (openStatus != null) {
            goodsDetailsGetByCodeRequest.setGoodsOpenStatus(openStatus.getValue());
        }

        // 商品詳細情報取得
        GoodsDetailsDtoResponse goodsDetailsDtoResponse = null;
        try {
            goodsDetailsDtoResponse = goodsApi.getDetailsByCode(goodsDetailsGetByCodeRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        GoodsDetailsDto goodsDetailsDto = catalogHelper.toGoodsDetailsDto(goodsDetailsDtoResponse);

        // PDR Migrate Customization from here
        // 公開中の商品情報が取得できない場合は、非公開での商品情報取得を試みる
        if (goodsDetailsDto == null) {
            if (noOpenStatus != null) {
                goodsDetailsGetByCodeRequest.setGoodsOpenStatus(noOpenStatus.getValue());
            }
            try {
                goodsDetailsDto = catalogHelper.toGoodsDetailsDto(
                                goodsApi.getDetailsByCode(goodsDetailsGetByCodeRequest));
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        }
        // PDR Migrate Customization to here

        // 商品情報が取得できなかった場合、空のDtoを作成し、エラーメッセージを追加する
        if (goodsDetailsDto == null) {
            goodsDetailsDto = ApplicationContextUtility.getBean(GoodsDetailsDto.class);
            this.addErrorMessage(MSGCD_GOODS_DETAILS_NOT_FOUND, new Object[] {goodsCode, catalogDspNo});
        } else {
            // 商品情報が取得できた場合、販売状態をチェックする
            if (!checkGoodsSaleStatus(goodsDetailsDto)) {
                // 販売状態チェックでエラーの場合、空のDtoを作成し、エラーメッセージを追加する
                // 受け入れNo.22 商品名は出してほしい #131
                this.addErrorMessage(MSGCD_GOODS_DETAILS_NOT_FOUND, new Object[] {goodsCode, catalogDspNo});
            }
            // 2023-renew No2 from here
            else {
                goodsCodeMap.put(goodsCode, catalogDspNo);
            }
            // 2023-renew No2 to here
        }

        // 会員価格取得
        Integer goodsSeq = goodsDetailsDto.getGoodsSeq();
        if (goodsSeq != null) {
            GoodsDetailsMapRequest goodsDetailsMapRequest = new GoodsDetailsMapRequest();
            goodsDetailsMapRequest.setGoodsSeqList(Arrays.asList(goodsSeq));
            Map<String, GoodsDetailsDtoResponse> dtoResponseMap = null;
            try {
                dtoResponseMap = goodsApi.getGoodsDetailsMap(goodsDetailsMapRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            Map<Integer, GoodsDetailsDto> goodsDtoMap = catalogHelper.toGoodsDetailsDtoMap(dtoResponseMap);

            goodsDetailsDto = goodsDtoMap.get(goodsSeq);
        }

        return goodsDetailsDto;
    }

    // 2023-renew No2 from here
    /**
     * 販売可否判定取得
     *
     * @param catalogModel  一括注文画面モデル
     * @param additionParams 追加パラメータ
     */
    protected void getSaleCheck(CatalogModel catalogModel, Object... additionParams) {
        // 取得対象商品がない場合スキップする
        if (MapUtils.isEmpty(this.goodsCodeMap)) {
            return;
        }

        // 取得対象商品一覧を編集する
        List<String> goodsCodeList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : goodsCodeMap.entrySet()) {
            goodsCodeList.add(entry.getKey());
        }

        // 販売可否判定を取得する
        WebApiGetSaleCheckRequest webApiGetSaleCheckRequest = new WebApiGetSaleCheckRequest();
        webApiGetSaleCheckRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        webApiGetSaleCheckRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        try {
            WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = webapiApi.getSaleCheck(webApiGetSaleCheckRequest);
            if (CollectionUtil.isNotEmpty(webApiGetSaleCheckResponse.getInfo())) {
                webApiGetSaleCheckResponse.getInfo().forEach(res -> {
                    if (HTypeSaleCheckType.NO.getValue()
                                             .equals(Objects.requireNonNull(res.getGoodsSaleYesNo()).toString())
                        && HTypeCartInPossible.OK.getValue().equals(catalogModel.getIsAddCartGoods().toString())) {
                        if (additionParams.length > 0) {
                            ((Map<String, String>) additionParams[0]).put(res.getGoodsCode(), createControllerException(
                                            FacesMessage.SEVERITY_ERROR, MSGCD_GOODS_NOT_PURCHASE,
                                            new Object[] {res.getGoodsCode(), goodsCodeMap.get(res.getGoodsCode())},
                                            null, null
                                                                                                                       )
                                            .getMessage());
                        }
                    }
                });
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            addAppLevelListException(e);
            throwMessage();
        }
    }
    // 2023-renew No2 to here

    /**
     * 商品の販売状態をチェック
     *
     * @param goodsDetailsBtobDto 商品詳細Dto
     * @return チェック結果(true : 問題なし 、 false : エラー)
     */
    protected boolean checkGoodsSaleStatus(GoodsDetailsDto goodsDetailsBtobDto) {
        // 現在日時を取得
        Timestamp now = dateUtility.getCurrentTime();

        HTypeGoodsSaleStatus saleStatus = goodsDetailsBtobDto.getSaleStatusPC();
        Timestamp saleStartTime = goodsDetailsBtobDto.getSaleStartTimePC();
        Timestamp saleEndTime = goodsDetailsBtobDto.getSaleEndTimePC();

        // ・商品詳細DTO．販売状態（PC）が"非販売"の場合エラー
        // ・商品詳細DTO．販売状態（PC）が"販売中止"の場合エラー
        if (saleStatus.equals(HTypeGoodsSaleStatus.NO_SALE) || saleStatus.equals(HTypeGoodsSaleStatus.DELETED)) {
            return false;
        }

        // 販売状態チェックOKの場合、販売期間をチェックする
        // ・サーバのシステム日時 ＜商品詳細DTO．販売開始日時（PC）の場合エラー
        // ・サーバのシステム日時 ＞ 商品詳細DTO．販売終了日時（PC） の場合エラー
        if ((saleStartTime != null && now.before(saleStartTime)) || (saleEndTime != null && now.after(saleEndTime))) {
            return false;
        }

        return true;
    }

    /**
     * 同じ商品番号の入力がある場合はエラー
     *
     * @param catalogModel 一括注文Model
     */
    protected void checkDuplicateGoods(CatalogModel catalogModel) {
        List<CatalogModelItem> catalogItems = catalogModel.getCatalogItems();
        if (CollectionUtil.isEmpty(catalogItems)) {
            return;
        }
        Set<String> checkGoodsCode = new HashSet<>();
        for (int i = 0; i < catalogItems.size(); i++) {
            CatalogModelItem catalogItem = catalogItems.get(i);
            if (StringUtils.isEmpty(catalogItem.getGoodsCode())) {
                continue;
            }
            if (checkGoodsCode.contains(catalogItem.getGoodsCode())) {
                addErrorMessage(MSGCD_CART_GOODS_DUPLICATE, new Object[] {catalogItem.getGoodsCode(), i + 1});
            } else {
                checkGoodsCode.add(catalogItem.getGoodsCode());
            }
        }
        if (hasErrorMessage()) {
            throwMessage(); // これ不要感あり。無くても止まる。これがあるとエラーの場合に商品名が入らない。
            // 受け入れNo.22に関連して
        }
    }

}
// PDR Migrate Customization to here
