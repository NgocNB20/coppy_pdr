/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CartController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CartHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.ReserveValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.group.ReserveGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.OrderHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
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

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * 商品_セールde予約 Controller
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@RequestMapping("/goods")
@Controller
@SessionAttributes(value = "goodsReserveModel")
// 2023-renew No14 from here
public class GoodsReserveController extends AbstractReserveController<GoodsReserveModel, GoodsReserveHelper> {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsReserveController.class);

    /**
     * 商品_セールde予約画面 Helper
     */
    private final GoodsReserveHelper goodsReserveHelper;

    /**
     * カートHelper
     */
    private final CartHelper cartHelper;

    /**
     * カートAPI
     */
    private final CartApi cartApi;

    /**
     * コンストラクタ
     */
    @Autowired
    public GoodsReserveController(ReserveValidator reserveValidator,
                                  GoodsReserveHelper goodsReserveHelper,
                                  CartHelper cartHelper,
                                  OrderHelper orderHelper,
                                  GoodsHelper goodsHelper,
                                  GoodsApi goodsApi,
                                  WebapiApi webapiApi,
                                  ShopApi shopApi,
                                  CartApi cartApi,
                                  CommonInfoUtility commonInfoUtility,
                                  OrderUtility orderUtility,
                                  ConversionUtility conversionUtility,
                                  GoodsUtility goodsUtility,
                                  DateUtility dateUtility) {
        super(reserveValidator, goodsReserveHelper, orderHelper, goodsHelper, goodsApi, webapiApi, shopApi,
              commonInfoUtility, orderUtility, conversionUtility, goodsUtility, dateUtility
             );
        this.goodsReserveHelper = goodsReserveHelper;
        this.cartHelper = cartHelper;
        this.cartApi = cartApi;
    }

    /**
     * 商品_セールde予約画面：初期処理
     *
     * @param gcd 商品コード（リクエストパラメータ）
     * @param ggcd 商品グループコード（リクエストパラメータ）
     * @param cid カテゴリID（リクエストパラメータ）
     * @param goodsReserveModel 商品_セールde予約画面Model
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request リクエスト
     * @param model モデル
     * @return 商品詳細画面 string
     */
    @GetMapping(value = {"/reserve", "/reserve.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/reserve")
    protected String doLoadIndex(@RequestParam(required = false) String gcd,
                                 @RequestParam(required = false) String ggcd,
                                 @RequestParam(required = false) String cid,
                                 GoodsReserveModel goodsReserveModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request,
                                 Model model) {

        // モデル初期化
        clearModel(GoodsReserveModel.class, goodsReserveModel, model);

        // リクエストパラメータのチェック → 画面表示情報取得
        goodsReserveHelper.setRequestParam(goodsReserveModel, gcd, ggcd, cid);
        if (!checkRequestParam(goodsReserveModel, redirectAttributes, model) || !setDisplayInfo(
                        goodsReserveModel, redirectAttributes, model)) {
            // 当画面表示不可の場合、エラー画面に遷移
            return "redirect:/error.html";
        }

        // カートDTO（セールde予約）を取得し、セールde予約情報Itemを作成
        goodsReserveHelper.setReserveItem(goodsReserveModel, getCartDtoForReserve(goodsReserveModel));

        // 入力チェックを実行（初期値が予約可能範囲外の場合を考慮）
        validate(goodsReserveModel, error);

        return "goods/reserve";
    }

    /**
     * 「カートに入れる／変更する」ボタン押下
     *
     * @param goodsReserveModel 商品_セールde予約画面Model
     * @param error エラー
     * @param request リクエスト
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return the string
     */
    @PostMapping(value = {"/reserve", "/reserve.html"}, params = "doGoodsReserveConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/reserve")
    public String doGoodsReserveConfirm(@Validated(ReserveGroup.class) GoodsReserveModel goodsReserveModel,
                                        BindingResult error,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {

        if (error.hasErrors()) {
            return "goods/reserve";
        }

        // カートDTO（今すぐお届け分）を取得し、チェック用に数量（今すぐお届け分）をセールde予約情報Itemにセット
        goodsReserveHelper.setReserveItemForInputDeliveryNowGoodsCount(
                        goodsReserveModel, getCartDtoForDeliveryNow(goodsReserveModel));

        // 相関チェックを行う
        checkInput(goodsReserveModel, redirectAttributes, model);

        // 入力された情報からカート一括登録用引継DTOリストを作成し、セッションに格納する
        redirectAttributes.addFlashAttribute(CartController.CART_GOODS_DTO_LIST_KEY,
                                             goodsReserveHelper.getCartGoodsForTakeOverDtoList(goodsReserveModel)
                                            );

        // カート画面へ遷移
        return "redirect:/cart/index.html";
    }

    /**
     * 取得した商品詳細Dtoのチェック
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return True:正常（エラーあり当画面表示も含む）、False：異常（当画面表示不可）
     */
    @Override
    protected boolean checkGoodsDetailsDto(GoodsDetailsDto goodsDetailsDto,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {

        // 継承元の処理を実行
        if (!super.checkGoodsDetailsDto(goodsDetailsDto, redirectAttributes, model)) {
            return false;
        }

        // 公開状態チェック
        checkOpenStatus(goodsDetailsDto, redirectAttributes, model);

        // 販売状態チェック
        checkSaleStatus(goodsDetailsDto, redirectAttributes, model);

        // 販売可否チェック
        checkSalableGoodsType(goodsDetailsDto, redirectAttributes, model);

        return true;
    }

    /**
     * 公開状態をチェック
     * ※心意気商品に関しては非公開状態で登録されるため
     *  「非公開かつ販売」の商品は購入可能にする
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     */
    private void checkOpenStatus(GoodsDetailsDto goodsDetailsDto, RedirectAttributes redirectAttributes, Model model) {

        Timestamp now = dateUtility.getCurrentTime();
        boolean isError = false;

        HTypeOpenDeleteStatus openStatus = goodsDetailsDto.getGoodsOpenStatusPC();
        Timestamp openStartDate = goodsDetailsDto.getOpenStartTimePC();
        Timestamp openEndDate = goodsDetailsDto.getOpenEndTimePC();

        // 公開状態が"公開中"の場合
        if (HTypeOpenDeleteStatus.OPEN.equals(openStatus)) {
            // 公開期間をチェックする
            // ・サーバのシステム日時 ＜ 公開開始日時 の場合
            // ・サーバのシステム日時 ＞ 公開終了日時 の場合
            if (openStartDate != null && now.before(openStartDate)) {
                isError = true;
            } else if (openEndDate != null && now.after(openEndDate)) {
                isError = true;
            }

        }
        // 公開状態が"非公開"以外（削除＝公開中止）の場合
        // ※PDRでは心意気商品を非公開状態で登録するので、削除＝公開中止としている
        else if (!HTypeOpenDeleteStatus.NO_OPEN.equals(openStatus)) {
            isError = true;
        }

        if (isError) {
            // 商品状態が公開中止、公開前、公開終了のいずれか場合
            addWarnMessage(GoodsReserveModel.MSGCD_ERROR_NON_OPEN_GOODS, null, redirectAttributes, model);
        }
    }

    /**
     * 販売状態をチェック
     * ※心意気商品に関しては非販売状態で登録されるため
     *  「公開かつ非販売かつ心意気商品」の商品は購入可能にする
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     */
    private void checkSaleStatus(GoodsDetailsDto goodsDetailsDto, RedirectAttributes redirectAttributes, Model model) {

        Timestamp now = dateUtility.getCurrentTime();
        boolean isError = false;

        HTypeGoodsSaleStatus saleStatus = goodsDetailsDto.getSaleStatusPC();
        Timestamp saleStartDate = goodsDetailsDto.getSaleStartTimePC();
        Timestamp saleEndDate = goodsDetailsDto.getSaleEndTimePC();

        // 販売状態が"販売中"の場合
        if (saleStatus.equals(HTypeGoodsSaleStatus.SALE)) {
            // 販売期間をチェックする
            // ・サーバのシステム日時 ＜ 販売開始日時 の場合
            // ・サーバのシステム日時 ＞ 販売終了日時 の場合
            if (saleStartDate != null && now.before(saleStartDate)) {
                isError = true;
            } else if (saleEndDate != null && now.after(saleEndDate)) {
                isError = true;
            }
        } else {
            // 心意気商品の場合、販売状態が非販売でもエラーとしない
            if (!goodsUtility.isEmotionPriceGoods(goodsDetailsDto)) {
                isError = true;
            }
        }

        if (isError) {
            // 商品状態が非販売、販売前、販売終了のいずれか場合
            addWarnMessage(GoodsReserveModel.MSGCD_ERROR_NON_SALE_GOODS, null, redirectAttributes, model);
        }
    }

    /**
     * 販売可否をチェック
     * ※販売可否判定．販売可否判定結果 = 「0:販売不可」の場合エラー
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     */
    private void checkSalableGoodsType(GoodsDetailsDto goodsDetailsDto,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        // 心意気商品コードからkpを削除
        String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsDetailsDto.getGoodsCode());

        WebApiGetSaleCheckRequest webApiGetSaleCheckRequest = new WebApiGetSaleCheckRequest();
        webApiGetSaleCheckRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        webApiGetSaleCheckRequest.setGoodsCode(goodsCode);

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

        if (webApiGetSaleCheckResponse == null || CollectionUtil.isEmpty(webApiGetSaleCheckResponse.getInfo())) {
            throwMessage(AbstractReserveModel.MSGCD_SYSTEM_ERR);
        }

        webApiGetSaleCheckResponse.getInfo().forEach(res -> {
            if (goodsCode.equals(res.getGoodsCode()) && HTypeSaleCheckType.NO.getValue()
                                                                             .equals(res.getGoodsSaleYesNo()
                                                                                        .toString())) {
                // 販売不可の場合、エラー
                addWarnMessage(GoodsReserveModel.MSGCD_ERROR_SALE_CHECK_NO, null, redirectAttributes, model);
                return;
            }
        });
    }

    /**
     * 当画面で表示されている商品のカートDTO（セールde予約）を取得
     *
     * @param goodsReserveModel 商品_セールde予約画面Model
     * @return カートDTO（セールde予約）
     */
    private CartDto getCartDtoForReserve(GoodsReserveModel goodsReserveModel) {

        return getCartDto(cartHelper.toCartGoodsGetRequest(getCommonInfo().getCommonInfoBase().getAccessUid(),
                                                           getCommonInfo().getCommonInfoUser().getMemberInfoSeq(),
                                                           HTypeSiteType.FRONT_PC.getValue(), "reserveDeliveryDate",
                                                           true, goodsReserveModel.getGoodsSeq(),
                                                           HTypeReserveDeliveryFlag.ON
                                                          ));
    }

    /**
     * 当画面で表示されている商品のカートDTO（今すぐお届け分）を取得
     *
     * @param goodsReserveModel 商品_セールde予約画面Model
     * @return カートDTO（今すぐお届け分）
     */
    private CartDto getCartDtoForDeliveryNow(GoodsReserveModel goodsReserveModel) {

        return getCartDto(cartHelper.toCartGoodsGetRequest(getCommonInfo().getCommonInfoBase().getAccessUid(),
                                                           getCommonInfo().getCommonInfoUser().getMemberInfoSeq(),
                                                           HTypeSiteType.FRONT_PC.getValue(), "registTime", false,
                                                           goodsReserveModel.getGoodsSeq(), HTypeReserveDeliveryFlag.OFF
                                                          ));
    }

    /**
     * カートDTOを取得
     *
     * @param cartGoodsGetRequest カート情報取得リクエスト
     * @return カートDTO
     */
    private CartDto getCartDto(CartGoodsGetRequest cartGoodsGetRequest) {

        CartDtoResponse cartDtoResponse = null;
        try {
            cartDtoResponse = cartApi.getCartGoods(cartGoodsGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return cartHelper.toCartDto(cartDtoResponse);
    }

}
// 2023-renew No14 to here
