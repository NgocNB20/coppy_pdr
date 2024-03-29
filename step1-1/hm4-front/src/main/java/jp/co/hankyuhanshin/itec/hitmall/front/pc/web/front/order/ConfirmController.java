/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsGetByCodeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConsumptionTaxRateGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConsumptionTaxRateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderBeforePaymentResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderCompleteMailSendRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderPromotionInformationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderScreenRegistResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.AccessApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CalendarNotSelectDateEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.SettlementMethodResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.helper.crypto.MD5Helper;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverTimeZoneJapanPost;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReceiverTimeZoneYamato;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRequisitionType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateRequestDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetShipmentDateResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.order.OrderConversionHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.validation.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.AbstractOrderController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CartUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.StockUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 注文_注文内容確認 Controller
 *
 * @author ota-s5
 */
@RequestMapping("/order")
@Controller
@SessionAttributes({"orderCommonModel", "confirmModel"})
// 2023-renew No14 from here
public class ConfirmController extends AbstractOrderController {

    /** メッセージコード：表示内容不正 */
    public static final String MSGCD_DISPLAY_ILLEGAL = "AOX000503";

    // PDR Migrate Customization from here
    /** メッセージコード：お届け予定日エラー */
    public static final String MSGCD_DELIVERY_DATE_ERR = "PDR-0016-001-A-";

    /** メッセージコード：注文エラー (注文単位) */
    public static final String MSGCD_ERR_ANY_ORDER = "PDR-0003-007-A-";

    /** メッセージコード：注文エラー (注文商品単位) */
    public static final String MSGCD_ERR_ANY_ORDER_GOODS = "PDR-0003-008-A-";

    /** メッセージコード：決済方法選択不可能エラー */
    public static final String MSGCD_GET_SETTLEMENT_ERROR = "LOO003504";

    /** メッセージコード：購入限度回数≦過去の購入数の場合 */
    public static final String MSGCD_PURCHASE_LIMIT_OVER_ERROR = "PKG-3559-001-A-";

    /** メッセージコード：送料あり */
    public static final String MSGCD_CARRIAGE_ON = "PDR-0003-006-A-";

    /** メッセージコード：WEB-API連携 割引適用結果にて取得した商品でカートに投入できない場合 */
    public static final String MSGCD_ADD_GOODS_ERR = "PDR-0003-009-A-";

    /** メッセージコード：商品税抜金額合計が送料無料基本金額以下の場合 */
    public static final String MSGCD_SHIPPING_STANDARD_AMOUNT = "PDR-0431-001-A-";

    /** メッセージコード：受注金額が選択した決済方法の最大購入金額を超えている場合 */
    public static final String MSGCD_SETTLEMENT_PURCHSEDMAX_OVER = "LOX000216";

    /** メッセージコード：受注金額が選択した決済方法の最低購入金額に達していない場合 */
    public static final String MSGCD_SETTLEMENT_PURCHSEDMIN_OVER = "LOX000224";
    // PDR Migrate Customization to here
    // 2023-renew No14 from here
    /** メッセージコード：決済方法未選択 */
    public static final String MSGCD_SETTLEMENT_NO_SELECT = "PDR-2023RENEW-14-007-";

    /** メッセージコード：セールde予約不可（予約可能範囲外） */
    public static final String MSGCD_NOT_RESERVE_DELIVERY_DATE_OUT_OF_RANGE = "PDR-2023RENEW-14-008-";

    /** メッセージコード：セールde予約不可（取りおき不可） */
    public static final String MSGCD_NOT_RESERVE_ITEM = "PDR-2023RENEW-14-009-";
    // 2023-renew No14 to here

    /**
     * 注文内容確認画面 Helper
     */
    private final ConfirmHelper confirmHelper;

    /**
     * 注文完了画面 Helper
     */
    private final CompleteHelper completeHelper;

    /**
     * 注文_セールde予約画面 Helper
     */
    private final OrderReserveHelper orderReserveHelper;

    /**
     * 注文フロー共通データ変換ヘルパークラス
     */
    private final OrderConversionHelper orderConversionHelper;

    /**
     * WEB-APIのUtilityクラス
     */
    private final WebApiUtility webApiUtility;

    /**
     * カート業務Utility
     */
    private final CartUtility cartUtility;

    /**
     * カートAPI
     */
    private final CartApi cartApi;

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * アクセスAPI
     */
    private final AccessApi accessApi;

    /**
     * コンストラクタ
     */
    @Autowired
    public ConfirmController(CouponHelper couponHelper,
                             OrderHelper orderHelper,
                             PaymentHelper paymentHelper,
                             OrderUtility orderUtility,
                             ConversionUtility conversionUtility,
                             CommonInfoUtility commonInfoUtility,
                             CouponUtility couponUtility,
                             ComTransactionUtility comTransactionUtility,
                             DateUtility dateUtility,
                             StockUtility stockUtility,
                             GoodsUtility goodsUtility,
                             OrderPendingUtility orderPendingUtility,
                             OrderApi orderApi,
                             MemberInfoApi memberInfoApi,
                             WebapiApi webapiApi,
                             ShopApi shopApi,
                             ConfirmHelper confirmHelper,
                             CompleteHelper completeHelper,
                             OrderReserveHelper orderReserveHelper,
                             OrderConversionHelper orderConversionHelper,
                             WebApiUtility webApiUtility,
                             CartUtility cartUtility,
                             CartApi cartApi,
                             GoodsApi goodsApi,
                             AccessApi accessApi) {
        super(couponHelper, orderHelper, paymentHelper, orderUtility, conversionUtility, commonInfoUtility,
              couponUtility, comTransactionUtility, dateUtility, stockUtility, goodsUtility, orderPendingUtility,
              orderApi, memberInfoApi, webapiApi, shopApi
             );
        this.confirmHelper = confirmHelper;
        this.completeHelper = completeHelper;
        this.orderReserveHelper = orderReserveHelper;
        this.orderConversionHelper = orderConversionHelper;
        this.webApiUtility = webApiUtility;
        this.cartUtility = cartUtility;
        this.cartApi = cartApi;
        this.goodsApi = goodsApi;
        this.accessApi = accessApi;
    }

    /**  ***********　Confirm Start　*********** **/
    /**
     * 注文内容確認画面：初期処理
     *
     * <pre>
     * 注文分割処理を追加
     * エラー表示形式を変更
     * </pre>
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request            リクエスト
     * @return 注文内容確認画面
     */
    @GetMapping(value = {"/confirm", "/confirm.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "order/confirm")
    public String doLoadConfirm(OrderCommonModel orderCommonModel,
                                ConfirmModel confirmModel,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        // モデル初期化
        clearModel(ConfirmModel.class, confirmModel, model);

        // カートからの遷移の場合、注文フロー共通Modelの初期化処理を行う
        if (model.containsAttribute(FROM_CART)) {
            clearModel(OrderCommonModel.class, orderCommonModel, model);
        }

        // PDR Migrate Customization from here
        // ブラウザバックチェック用トークンを生成
        setToken(orderCommonModel);
        // PDR Migrate Customization to here

        // 2023-renew No14 from here
        // 注文フロー開始時（受注サマリエンティティがなかった場合は注文フローの開始となる）
        if (orderCommonModel.getReceiveOrderDto().getOrderSummaryEntity() == null) {

            // 注文フロー開始時の処理
            String returnPage = initializeOrder(orderCommonModel, redirectAttributes, model);
            if (returnPage != null) {
                return returnPage;
            }

            // 1. お客様情報を設定する
            // 会員情報生成
            returnPage = makeMemberInfo(orderCommonModel);
            if (returnPage != null) {
                return returnPage;
            }

            // 2. お届け先を設定する
            settingDeliveryForConfirm(orderCommonModel, model, redirectAttributes);

            // 3. お支払い方法を設定
            settingPaymentForConfirm(orderCommonModel, confirmModel.getPaymentModel(), model, redirectAttributes);

        }

        // 4. 商品を設定する
        // 5. 値引適用判定
        // 6. 各受注の合計金額の計算を実施
        settingGoodsInfoAndPromotion(orderCommonModel, confirmModel, model, redirectAttributes);
        // 2023-renew No14 to here

        // 今後の処理は以下の受注DTOを使用
        // 受注受付番号単位で処理を行う場合：receiveOrderDto
        // 受注番号単位で処理を行う場合:receiveOrderDtoList

        // 2023-renew No14 from here
        // 決済方法設定チェック
        checkNoSettlement(orderCommonModel, confirmModel);

        // 取りおき可否チェック
        checkReserveAvailability(orderCommonModel, confirmModel);
        // 2023-renew No14 to here

        // 7. 在庫チェックを実施
        // 在庫チェック
        checkStock(orderCommonModel, confirmModel);

        // 8. 注文可能チェックを実施
        // 注文可能チェックサービス実行。
        // 受注DTOリスト分チェックを行う。
        checkOrderByReceiveOrderDtoList(orderCommonModel, confirmModel);

        // PDR Migrate Customization from here
        // Modelへの変換処理
        confirmHelper.toPageForLoad(orderCommonModel, confirmModel, model);
        // PDR Migrate Customization to here

        // トークンをセット（確認画面用）
        setConfirmToken(orderCommonModel, confirmModel);

        // PDR Migrate Customization from here
        // 注文可能チェック エラー表示
        addOrderCheckErrorInfo(orderCommonModel, confirmModel, redirectAttributes, model, false);

        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }
        // PDR Migrate Customization to here

        return "order/confirm";
    }

    // 2023-renew No14 from here

    /**
     * 今すぐお届け／セールde予約／入荷次第お届け「変更」ボタン押下処理
     * ※「チェックボックス：注文内容を確認しました」の値を保持するためにjava経由で遷移させる
     *
     * @param orderCommonModel   注文フロー共通Model
     * @return 遷移先画面
     * @throws ServletException サーブレットエクセプション
     * @throws IOException      IOエクセプション
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doDelivery")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doDelivery(OrderCommonModel orderCommonModel) {
        // 配送方法選択画面に遷移
        return "redirect:/order/delivery.html";
    }

    /**
     * お届け先「変更」ボタン押下処理
     * ※「チェックボックス：注文内容を確認しました」の値を保持するためにjava経由で遷移させる
     *
     * @param orderCommonModel   注文フロー共通Model
     * @return 遷移先画面
     * @throws ServletException サーブレットエクセプション
     * @throws IOException      IOエクセプション
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doReceiver")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doReceiver(OrderCommonModel orderCommonModel) {
        //お届け先入力画面に遷移
        return "redirect:/order/receiver.html";
    }

    /**
     * お支払方法選択「変更」ボタン押下処理
     * ※「チェックボックス：注文内容を確認しました」の値を保持するためにjava経由で遷移させる
     *
     * @param orderCommonModel   注文フロー共通Model
     * @return 遷移先画面
     * @throws ServletException サーブレットエクセプション
     * @throws IOException      IOエクセプション
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doPayment")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doPayment(OrderCommonModel orderCommonModel) {
        // お支払方法選択画面に遷移
        return "redirect:/order/payment.html";
    }

    // 2023-renew No14 to here
    // 2023-renew No24 from here

    /**
     * 「クーポン削除」ボタン押下処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return 遷移先画面
     * @throws ServletException サーブレットエクセプション
     * @throws IOException      IOエクセプション
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doClearCoupon")
    @HEHandler(exception = AppLevelListException.class, returnView = "cart/index")
    public String doClearCoupon(OrderCommonModel orderCommonModel,
                                ConfirmModel confirmModel,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                HttpServletRequest request) throws ServletException, IOException {

        // セッション情報のクーポン関連情報を削除（cookieは残す）
        clearSessionCouponInfo();

        // 注文フロー共通Model内のクーポン関連情報を初期化
        // ※注文内容確認画面Modelは再表示時に初期化されるので不要
        orderCommonModel.getReceiveOrderDto().setCouponCode(null);
        orderCommonModel.getReceiveOrderDto().setCouponName(null);

        // 注文内容確認画面を再表示
        return doLoadConfirm(orderCommonModel, confirmModel, model, redirectAttributes, request);
    }

    // 2023-renew No24 to here

    /**
     * 「注文内容を確定する」ボタン押下処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @param response           レスポンス
     * @return 自画面(購入不可商品が存在)、注文完了画面(それ以外)
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doOnceOrderRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/confirm")
    public String doOnceOrderRegist(OrderCommonModel orderCommonModel,
                                    @Validated(ConfirmGroup.class) ConfirmModel confirmModel,
                                    BindingResult error,
                                    RedirectAttributes redirectAttributes,
                                    Model model,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        // PDR Migrate Customization from here
        // ショップ情報がnullの場合、メンテナンスページに遷移する
        if (getCommonInfo().getCommonInfoShop() == null) {
            return "redirect:/mainte.html";
        }

        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }
        // PDR Migrate Customization to here

        if (error.hasErrors()) {
            return "order/confirm";
        }

        // 注文メッセージDtoを初期化
        confirmModel.setOrderMessageDto(new OrderMessageDto());

        // 表示内容が最新であるかチェック
        // 画面に設定されているトークンと画面表示時に退避しておいたトークンが不一致の場合
        if (!confirmModel.checkOrderConfirmToken()) {
            confirmHelper.toPageForLoad(orderCommonModel, confirmModel, model);
            setConfirmToken(orderCommonModel, confirmModel);
            throwMessage(MSGCD_DISPLAY_ILLEGAL);
        }

        // 2023-renew No14 from here
        // 決済方法設定チェック
        checkNoSettlement(orderCommonModel, confirmModel);

        // 取りおき可否チェック
        checkReserveAvailability(orderCommonModel, confirmModel);
        // 2023-renew No14 to here

        // PDR Migrate Customization from here
        // 在庫チェック
        checkStock(orderCommonModel, confirmModel);

        // 配送情報取得（今すぐお届け分）
        checkShipmentDateByDeliveryNow(orderCommonModel, confirmModel);

        // 注文可能チェック
        checkOrderByReceiveOrderDtoList(orderCommonModel, confirmModel);

        // 画面にエラーを表示
        addOrderCheckErrorInfo(orderCommonModel, confirmModel, redirectAttributes, model, true);
        if (hasErrorMessage()) {
            throwMessage();
        }

        // 受注登録処理
        OrderScreenRegistResponse registResponse = null;
        try {
            registResponse = orderApi.registOrderScreen(
                            orderHelper.toOrderScreenRegistRequest(orderCommonModel, confirmModel,
                                                                   commonInfoUtility.getCustomerNo(getCommonInfo()),
                                                                   getCommonInfo()
                                                                  ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        confirmModel.setReceiveOrderDtoList(orderHelper.toReceiveOrderDtoList(registResponse.getReceiveOrderDtoList()));
        // PDR Migrate Customization to here

        // カート情報をクリアする
        try {
            this.deleteCartGoodsList(orderCommonModel.getReceiveOrderDto());
        } catch (RuntimeException e) {
            // メイン処理ではないので、ログ出力のみ
            LOGGER.warn("カート商品の削除に失敗しました。", e);
        }

        // PDR Migrate Customization from here
        // 決済種別処理 削除
        // 使用しないため
        // PDR Migrate Customization to here

        // 後処理登録
        registAfterProcess(
                        orderCommonModel, confirmModel, conversionUtility.toTimeStamp(registResponse.getOrderTime()));

        // 2023-renew No24 from here
        // 保持しているクーポン関連情報を削除
        doDeleteCookie(orderCommonModel, confirmModel, request, response);
        // 2023-renew No24 to here

        // 注文完了画面に遷移
        return "redirect:/order/complete.html";
    }

    /**
     * 注文フロー開始時の処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param customParams       案件用変数
     * @return 戻り先画面 string
     */
    private String initializeOrder(OrderCommonModel orderCommonModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model,
                                   Object... customParams) {

        // 前画面からカートDtoを取得
        CartDto cartDto = (CartDto) model.getAttribute(CART_DTO);
        if (cartDto == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            // カート一覧に遷移。
            return "redirect:/cart/index.html";
        }

        // 取得したカートDtoをページに設定
        orderCommonModel.setCartDto(cartDto);

        // カートDTOのチェック
        Map<Integer, List<CheckMessageDto>> messageMap = checkCartDto(cartDto, redirectAttributes, model);
        if (cartUtility.isErrorAbortProcessing(messageMap, cartDto.getCartGoodsDtoList())) {
            // カート一覧に遷移。
            return "redirect:/cart/index.html";
        }

        // マスター情報の作成
        OrderInfoMasterDtoResponse orderInfoMasterDtoResponse = null;
        try {
            orderInfoMasterDtoResponse = orderApi.getOrderInfoMaster(orderHelper.toOrderInfoMasterGetRequest(cartDto));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        orderCommonModel.getReceiveOrderDto()
                        .setMasterDto(orderHelper.toOrderInfoMasterDto(orderInfoMasterDtoResponse));

        // カートDtoから受注Dtoへ変換
        try {
            orderConversionHelper.toReceiveOrderDto(cartDto, orderCommonModel.getReceiveOrderDto());
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 2023-renew No24 from here
        // クーポンコード／クーポン名をセッションから受注Dtoへセット
        orderCommonModel.getReceiveOrderDto().setCouponCode(getCommonInfo().getCommonInfoBase().getCouponCode());
        orderCommonModel.getReceiveOrderDto().setCouponName(getCommonInfo().getCommonInfoBase().getCouponName());
        // 2023-renew No24 to here

        return null;
    }

    /**
     * カートDTOのチェック
     *
     * @param cartDto            カートDTO
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param customParams       案件用引数
     * @return エラー情報MAP map
     */
    private Map<Integer, List<CheckMessageDto>> checkCartDto(CartDto cartDto,
                                                             RedirectAttributes redirectAttributes,
                                                             Model model,
                                                             Object... customParams) {

        // カート商品チェックサービス実行
        CartGoodsCheckMapResponse cartGoodsCheckMapResponse = null;
        try {
            cartGoodsCheckMapResponse = cartApi.checkCartGoods(
                            orderHelper.toCartGoodsCheckRequest(cartDto, commonInfoUtility.isLogin(getCommonInfo()),
                                                                commonInfoUtility.getMemberInfoEntity(getCommonInfo()),
                                                                getCommonInfo().getCommonInfoUser().getMemberInfoSeq()
                                                               ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return orderHelper.toCheckMessageDtoListMap(cartGoodsCheckMapResponse);
    }

    /**
     * 会員情報生成
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param customParams 案件用引数
     * @return 戻り先画面 string
     */
    private String makeMemberInfo(OrderCommonModel orderCommonModel, Object... customParams) {

        // 未ログインの場合は不正
        if (!commonInfoUtility.isLogin(getCommonInfo())) {
            addErrorMessage(MSGCD_ILLEGAL_OPERATION);
            return "redirect:/cart/index.html";
        }

        // 会員情報取得サービス実行
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse =
                            memberInfoApi.getByMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        MemberInfoEntity memberInfoEntity = null;
        try {
            memberInfoEntity = orderHelper.toMemberInfoEntity(memberInfoEntityResponse);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        orderCommonModel.setMemberInfoEntity(memberInfoEntity);

        // 会員情報エンティティからページに変換（ご注文主情報の作成）
        orderConversionHelper.toReceiveOrderDto(memberInfoEntity, orderCommonModel.getReceiveOrderDto());

        // PDR Migrate Customization from here
        // お届け先情報の作成
        orderCommonModel.getReceiveOrderDto()
                        .getOrderDeliveryDto()
                        .setOrderDeliveryEntity(confirmHelper.toOrderDeliveryEntityForDeliveryMethod(orderCommonModel,
                                                                                                     memberInfoEntity
                                                                                                    ));

        // 会員登録の住所を使用 フラグ設定
        orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto().setAddType(ReceiverModel.ADD_TYPE_SENDER);

        // 会員の顧客番号を設定
        orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto().setCustomerNo(memberInfoEntity.getCustomerNo());
        // PDR Migrate Customization to here
        return null;
    }

    // 2023-renew No14 from here

    /**
     * お届け先を設定する（初回のみ実行）
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param model model
     * @param redirectAttributes リダイレクトアトリビュート
     */
    private void settingDeliveryForConfirm(OrderCommonModel orderCommonModel,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {

        // 配送情報取得（WEB-API連携）
        executeWebApiGetDeliveryInformation(orderCommonModel, redirectAttributes, model);

        // 請求書種別：同梱に設定
        orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto().setRequisitionType(HTypeRequisitionType.INCLUDE);

        // 配送方法リスト作成
        createDeliveryMethodList(orderCommonModel, redirectAttributes, model);

        // お届け希望日、お届け時間帯の初期値設定
        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();
        WebApiGetDeliveryInformationResponseDetailDto deliveryInformationDetailDto =
                        orderDeliveryDto.getDeliveryInformationDetailDto();
        String deliveryCompanyCode = deliveryInformationDetailDto.getDeliveryCompanyCode();

        // お届け希望日：共通最短お届け日を設定
        orderDeliveryDto.getOrderDeliveryEntity()
                        .setReceiverDate(deliveryInformationDetailDto.getComEarlyReceiveDate());
        // お届け時間帯：指定なしを設定
        if (HTypeDeliveryType.YAMATO.getValue().equals(deliveryCompanyCode) || HTypeDeliveryType.AUTOMATIC.getValue()
                                                                                                          .equals(deliveryCompanyCode)) {
            // ヤマトまたは自動設定の場合
            orderDeliveryDto.getOrderDeliveryEntity()
                            .setReceiverTimeZone(HTypeReceiverTimeZoneYamato.UNSPECIFIED.getValue());
        } else if (HTypeDeliveryType.JAPANPOST.getValue().equals(deliveryCompanyCode)) {
            // 日本郵便
            orderDeliveryDto.getOrderDeliveryEntity()
                            .setReceiverTimeZone(HTypeReceiverTimeZoneJapanPost.UNSPECIFIED.getValue());
        }

    }

    /**
     * お支払い方法を設定（初回のみ実行）
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel     お支払い方法選択画面Model
     * @param model モデル
     * @param redirectAttributes リダイレクトアトリビュート
     */
    private void settingPaymentForConfirm(OrderCommonModel orderCommonModel,
                                          PaymentModel paymentModel,
                                          Model model,
                                          RedirectAttributes redirectAttributes) {

        // お支払方法選択画面：初期表示時の共通処理を実行
        doLoadPayment(orderCommonModel, paymentModel, model, redirectAttributes);
        if (!paymentModel.isExistSettlementMethod()) {
            // 利用可能な決済方法が存在しない場合、スキップ
            return;
        }

        // 前回支払方法取得（WEB-API連携）
        OrderBeforePaymentResponse orderBeforePaymentResponse = null;
        try {
            orderBeforePaymentResponse = orderApi.getBeforePayment(
                            orderHelper.toOrderBeforePaymentRequest(commonInfoUtility.getCustomerNo(getCommonInfo()),
                                                                    paymentModel
                                                                   ));
        } catch (HttpClientErrorException e) {
            // ログだけ吐いて何もしない（前回支払方法が取得に失敗しても、EC上はエラーとしない）
            LOGGER.error("例外処理が発生しました", e);
        }

        // 取得した情報を基に値をセット
        paymentHelper.toPageForLoadForConfirm(paymentModel, orderBeforePaymentResponse.getSettlementMethodType(),
                                              orderBeforePaymentResponse.getCustomerCardId()
                                             );
        if (StringUtils.isEmpty(paymentModel.getSettlementMethod())) {
            // 決済方法が選択されなかった場合、スキップ
            return;
        }

        // お支払方法選択画面：「お支払方法を変更する」ボタン押下時の共通処理を実行
        doPaymentConfirm(orderCommonModel, paymentModel);

    }

    /**
     * 商品を設定する
     * 値引適用判定
     * 各受注の合計金額の計算を実施
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     * @param model model
     * @param redirectAttributes リダイレクトアトリビュート
     */
    private void settingGoodsInfoAndPromotion(OrderCommonModel orderCommonModel,
                                              ConfirmModel confirmModel,
                                              Model model,
                                              RedirectAttributes redirectAttributes) {
        // PDR Migrate Customization from here
        // 割引適用結果を設定する（WEB-API連携）
        // ※購入数によって大容量商品となるための措置
        setDiscountResponseInformation(orderCommonModel, redirectAttributes, model);
        // PDR Migrate Customization to here

        // 商品詳細DTOのリストを取り直す
        GoodsDetailsListGetRequest goodsDetailsListGetRequest = new GoodsDetailsListGetRequest();
        goodsDetailsListGetRequest.setGoodsSeqList(
                        orderUtility.getALLGoodsSeqList(orderCommonModel.getReceiveOrderDto()));
        List<GoodsDetailsDtoResponse> goodsDetailsDtoResponseList = null;
        try {
            goodsDetailsDtoResponseList = goodsApi.getDetailsList(goodsDetailsListGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        List<GoodsDetailsDto> goodsDetailDtoList;
        try {
            goodsDetailDtoList = orderHelper.toListGoodsDetailsDtoResponse(goodsDetailsDtoResponseList);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        confirmModel.setGoodsDetailDtoList(goodsDetailDtoList);

        // PDR Migrate Customization from here
        // 受注DTOリストを作成
        confirmModel.setReceiveOrderDtoList(createReceiveOrderDtoList(orderCommonModel, confirmModel));
        // PDR Migrate Customization to here
    }

    // 2023-renew No14 to here
    // PDR Migrate Customization from here

    /**
     * WEB-API連携 割引適用結果取得を行い、受注DTOを再構築
     * ※今すぐお届け分から大容量・心意気を振り分け直す
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    private void setDiscountResponseInformation(OrderCommonModel orderCommonModel,
                                                RedirectAttributes redirectAttributes,
                                                Model model) {

        // 配送先情報
        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();

        // 2023-renew No14 from here
        // 商品情報 （今すぐお届け分のみ）
        // 現時点でorderDeliveryNowDtoListは振り分け前なので、orderGoodsEntityListから取得
        List<OrderGoodsEntity> orderDeliveryNowList = orderDeliveryDto.getOrderGoodsEntityList()
                                                                      .stream()
                                                                      .filter(item -> !HTypeReserveDeliveryFlag.ON.equals(
                                                                                      item.getReserveFlag()))
                                                                      .collect(Collectors.toList());
        // 2023-renew No14 to here

        // 今すぐお届け分がない場合は、処理終了
        if (orderDeliveryNowList == null || orderDeliveryNowList.size() == 0) {
            return;
        }

        // 割引適用結果取得（WEB-API連携）
        WebApiGetDiscountsResponseDto discountsResponseDto = executeWebApiGetDiscountsResult(orderDeliveryNowList);

        // WEB-API返却情報Mapを作成
        Map<String, WebApiGetDiscountsResponseDetailDto> responseMap = new HashMap<>();
        for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
            responseMap.put(dto.getGoodsCode(), dto);
        }

        // 2023-renew No14 from here
        // 商品詳細Mapを作成(今すぐお届け分)
        // ※今すぐお届け分に限ると同一商品コードは存在しないので、KEYは商品コードでOK
        Map<String, OrderGoodsEntity> orderDeliveryNowMap = new HashMap<>();
        for (OrderGoodsEntity entity : orderDeliveryNowList) {
            orderDeliveryNowMap.put(entity.getGoodsCode(), entity);
        }
        // 2023-renew No14 to here

        // WEB-APIから返却された商品と差異がないか（大容量、心意気等）確認
        boolean isNotExistGcd = false;
        for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
            // 連携された商品コードが今すぐお届けのリストに含まれる場合、数量に変更がないか確認
            if (orderDeliveryNowMap.containsKey(dto.getGoodsCode())) {
                OrderGoodsEntity orderGoodsEntity = orderDeliveryNowMap.get(dto.getGoodsCode());
                if (StringUtil.equals(dto.getQuantity(), orderGoodsEntity.getGoodsCount().toString())) {
                    continue;
                }
            }
            isNotExistGcd = true;
        }
        // 差異がない場合は処理終了
        if (!isNotExistGcd) {
            return;
        }

        // 2023-renew No14 from here
        // 商品詳細Mapを作成(WEB-API返却分)
        Map<String, GoodsDetailsDto> goodsDetailsMap = new HashMap<>();
        for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
            GoodsDetailsDto goodsDetailsDto = getDetailsByCode(dto.getGoodsCode());
            if (goodsDetailsDto == null) {
                // 返却された商品情報がDBに存在しない場合
                addWarnMessage(MSGCD_ADD_GOODS_ERR, new String[] {}, redirectAttributes, model);
                return;
            } else {
                goodsDetailsMap.put(dto.getGoodsCode(), goodsDetailsDto);
            }
        }

        // 今すぐお届け分のみWEB-API連携したため、商品コードが変わっていない商品(大容量でない)の数量を更新
        // 商品コードが変わっている商品(大容量)の場合、大容量のとりおき情報を新規作成
        for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
            // 今すぐお届け分に含まれる場合
            if (orderDeliveryNowMap.containsKey(dto.getGoodsCode())) {
                for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {
                    if (!HTypeReserveDeliveryFlag.ON.equals(orderGoodsEntity.getReserveFlag())) {
                        continue;
                    }
                    if (orderGoodsEntity.getGoodsCode().equals(dto.getGoodsCode())) {
                        WebApiGetDiscountsResponseDetailDto responseDto =
                                        responseMap.get(orderGoodsEntity.getGoodsCode());
                        // 数量更新
                        orderGoodsEntity.setGoodsCount(new BigDecimal(responseDto.getQuantity()));
                    }
                }
            }
            // 大容量化した場合
            else {
                // 受注商品Dtoリストに追加
                orderDeliveryDto.getOrderGoodsEntityList()
                                .add(orderConversionHelper.toOrderGoodsEntityByDiscountsResult(
                                                goodsDetailsMap.get(dto.getGoodsCode()), dto));
            }
        }
        // 2023-renew No14 to here

        // 今すぐお届け商品ListとAPI取得結果Mapを比較して商品情報Listから削除する対象を特定する
        Set<String> clearGoodsCodes = new HashSet<>();
        for (String goodsCode : orderDeliveryNowMap.keySet()) {
            if (!responseMap.containsKey(goodsCode)) {
                clearGoodsCodes.add(goodsCode);
            }
        }

        // 受注商品Dtoリストと商品詳細Mapを再構築
        Map<Integer, GoodsDetailsDto> orderGoodsMap = new HashMap<>();
        List<OrderGoodsEntity> tmpOrderGoodsEntityList = new ArrayList<>();
        for (OrderGoodsEntity entity : orderDeliveryDto.getOrderGoodsEntityList()) {
            // 2023-renew No14 from here
            // 今すぐお届け分 且つ 削除対象に含まれる場合、削除フラグON
            boolean isDelete = !HTypeReserveDeliveryFlag.ON.equals(entity.getReserveFlag()) && clearGoodsCodes.contains(
                            entity.getGoodsCode());
            if (!isDelete) {
                // 受注商品Dtoリストに最新情報を追加
                tmpOrderGoodsEntityList.add(entity);

                // 商品詳細Mapに最新情報を追加（セールde予約分は同一商品もあり得るので、追加済商品は省略）
                if (!orderGoodsMap.containsKey(entity.getGoodsSeq())) {
                    GoodsDetailsDto goodsDetailsDto = getDetailsByCode(entity.getGoodsCode());
                    if (goodsDetailsDto != null) {
                        orderGoodsMap.put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);
                    }
                }
            }
            // 2023-renew No14 to here
        }

        // 受注商品Dtoリスト洗い替え
        orderDeliveryDto.setOrderGoodsEntityList(tmpOrderGoodsEntityList);

        // マスタ情報洗い替え
        orderCommonModel.getReceiveOrderDto().getMasterDto().setGoodsMaster(orderGoodsMap);
    }

    /**
     * WEB-API連携 割引適用結果取得
     *
     * @param orderDeliveryNowList 今すぐお届け分リスト
     * @return 割引適用結果
     */
    private WebApiGetDiscountsResponseDto executeWebApiGetDiscountsResult(List<OrderGoodsEntity> orderDeliveryNowList) {

        List<String> goodsCodeList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> orderDisplayList = new ArrayList<>();

        // 重複商品を集約
        for (int index = 0; index < orderDeliveryNowList.size(); index++) {
            OrderGoodsEntity entity = orderDeliveryNowList.get(index);

            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode());
            // 商品コードリストに既に商品コードが存在する場合、数量リストから同じインデックスの数値を取り出し、加算する
            // 心意気商品と通常商品の両方がお届けリストに存在する場合に、一つのリクエストにまとめるための処理
            if (goodsCodeList.contains(goodsCode)) {
                int quantity = new BigDecimal(quantityList.get(goodsCodeList.indexOf(goodsCode))).intValue();
                quantity = quantity + entity.getGoodsCount().intValue();
                quantityList.set(goodsCodeList.indexOf(goodsCode), new BigDecimal(quantity).toString());
            } else {
                goodsCodeList.add(goodsCode);
                quantityList.add(entity.getGoodsCount().toString());
                orderDisplayList.add(String.valueOf(index + 1));
            }
        }

        // Web-API リクエストDTO
        WebApiGetDiscountsRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetDiscountsRequestDto.class);
        // 顧客番号
        reqDto.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));

        // 複数存在する項目をパイプ区切りに変換
        // 商品コード
        reqDto.setGoodsCode(webApiUtility.createStrPipeByList(goodsCodeList));
        // 数量
        reqDto.setQuantity(webApiUtility.createStrPipeByList(quantityList));
        // 表示順
        reqDto.setOrderDisplay(webApiUtility.createStrPipeByList(orderDisplayList));

        // WEB-API実行
        WebApiGetDiscountsResultResponse webApiGetDiscountsResultResponse = null;
        try {
            webApiGetDiscountsResultResponse =
                            webapiApi.getDiscountResult(orderHelper.toWebApiGetDiscountsResultRequest(reqDto));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        WebApiGetDiscountsResponseDto discountsResponseDto =
                        orderHelper.toWebApiGetDiscountsResponseDto(webApiGetDiscountsResultResponse);
        if (discountsResponseDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
            // 心意気商品の場合、商品番号にkpをつける
            if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(dto.getSaleType())) {
                dto.setGoodsCode(dto.getGoodsCode() + "kp");
            }
        }

        return discountsResponseDto;
    }

    /**
     * 商品詳細Dtoを取得
     *
     * @param goodsCode 商品コード
     * @return 商品詳細Dto
     */
    private GoodsDetailsDto getDetailsByCode(String goodsCode) {
        GoodsDetailsGetByCodeRequest goodsDetailsGetByCodeRequest = new GoodsDetailsGetByCodeRequest();
        goodsDetailsGetByCodeRequest.setCode(goodsCode);
        goodsDetailsGetByCodeRequest.setGoodsOpenStatus(null);
        GoodsDetailsDtoResponse goodsDetailsDtoResponse = null;

        try {
            goodsDetailsDtoResponse = goodsApi.getDetailsByCode(goodsDetailsGetByCodeRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        GoodsDetailsDto goodsDetailsDto;
        try {
            goodsDetailsDto = orderHelper.toGoodsDetailsDtoGoods(goodsDetailsDtoResponse);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return goodsDetailsDto;
    }

    /**
     * お届け時期で分割した受注DTOリストを作成します。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     * @return 受注DTOリスト
     */
    private List<ReceiveOrderDto> createReceiveOrderDtoList(OrderCommonModel orderCommonModel,
                                                            ConfirmModel confirmModel) {

        // メッセージリスト
        OrderMessageDto orderMessageDto = confirmModel.getOrderMessageDto();
        if (orderMessageDto.getOrderMessageList() == null) {
            orderMessageDto.setOrderMessageList(new ArrayList<>());
        }

        // 配送先情報
        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();

        // 消費税率（WEB-API連携）
        // ※商品コード重複削除はOrderGetConsumptionTaxRateLogicImpl側で行われるので考慮不要
        ConsumptionTaxRateGetRequest consumptionTaxRateGetRequest = new ConsumptionTaxRateGetRequest();
        consumptionTaxRateGetRequest.setGoodsCodeList(
                        orderUtility.getGoodsCodeList(orderDeliveryDto.getOrderGoodsEntityList()));
        ConsumptionTaxRateResponse consumptionTaxRateResponse = null;
        try {
            consumptionTaxRateResponse = orderApi.getConsumptionTaxRate(consumptionTaxRateGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap =
                        orderHelper.toWebApiGetConsumptionTaxRateResponseDetailDtoMap(consumptionTaxRateResponse);

        // 数量割引適用結果取得（WEB-API連携）
        OrderQuantityDiscountResponse orderQuantityDiscountResponse = null;
        try {
            orderQuantityDiscountResponse = orderApi.registOrderQuantityDiscount(
                            orderHelper.toOrderQuantityDiscountRequest(orderCommonModel.getReceiveOrderDto(),
                                                                       taxRateMap, orderMessageDto.getOrderMessageList()
                                                                      ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        orderMessageDto.setOrderMessageList(
                        orderHelper.toCheckMessageDtoListOrder(orderQuantityDiscountResponse.getCheckMessageDtoList()));
        orderCommonModel.setReceiveOrderDto(
                        orderHelper.toReceiveOrderDto(orderQuantityDiscountResponse.getReceiveOrderDto()));

        // 商品在庫数取得（WEB-API連携）
        Map<String, WebApiGetStockResponseDetailDto> stockMap = getStockMap(orderDeliveryDto.getOrderGoodsEntityList());

        // 今すぐお届け 取りおき 入荷次第お届けに振り分け
        stockUtility.allocationDelivery(orderCommonModel.getReceiveOrderDto(), stockMap,
                                        orderMessageDto.getOrderMessageList(), StockUtility.MSGCD_DEPENDING_ON_RECEIPT
                                       );

        // 2023-renew No14 from here
        // 出荷予定日取得（WEB-API連携）
        setShipmentDate(orderCommonModel);
        // 2023-renew to from here

        // 注文内容確認画面表示用の受注DTOを作成します。
        // 受注DTOをコピー
        ReceiveOrderDto dispReceiveOrderDto = CopyUtil.deepCopy(orderCommonModel.getReceiveOrderDto());

        // 受注DTOリストを作成
        List<ReceiveOrderDto> receiveOrderDtoList = orderUtility.createSplitReceiveOrderDto(dispReceiveOrderDto);

        // プロモーション連携（WEB-API連携）
        OrderPromotionInformationResponse orderPromotionInformationResponse = null;
        try {
            orderPromotionInformationResponse = orderApi.registOrderPromotionInformation(
                            orderHelper.toOrderPromotionInformationRequest(receiveOrderDtoList,
                                                                           orderMessageDto.getOrderMessageList()
                                                                          ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        orderMessageDto.setOrderMessageList(orderHelper.toCheckMessageDtoListOrder(
                        orderPromotionInformationResponse.getCheckMessageDtoList()));
        receiveOrderDtoList =
                        orderHelper.toReceiveOrderDtoList(orderPromotionInformationResponse.getReceiveOrderDtoList());

        // 送料無料基準注文金額のチェックを行う
        checkErrFreeShippingStandardAmount(
                        orderCommonModel, confirmModel, receiveOrderDtoList, orderMessageDto.getOrderMessageList());

        // 無料決済情報を取得
        SettlementMethodEntity settlementMethodEntity;
        try {
            SettlementMethodResponse settlementMethodResponse = null;
            try {
                settlementMethodResponse = shopApi.getSettlementBySeq(orderUtility.getFreeSettlementMethodSeq());
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            settlementMethodEntity = orderHelper.toSettlementMethodEntity(settlementMethodResponse);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        // 各合計金額の計算を行います。
        orderUtility.calculationTotalPrice(
                        dispReceiveOrderDto, receiveOrderDtoList, taxRateMap, settlementMethodEntity);
        // 2023-renew No14 from here
        // 受注金額0円判定フラグをセット（合計金額計算後に無料決済かどうかで判定）
        orderCommonModel.setOrderPriceZero(orderUtility.getFreeSettlementMethodSeq()
                                                       .equals(dispReceiveOrderDto.getOrderSettlementEntity()
                                                                                  .getSettlementMethodSeq()));
        // 2023-renew No14 to here

        // 表示用の受注DTOを保持
        confirmModel.setDispReceiveOrderDto(dispReceiveOrderDto);

        return receiveOrderDtoList;
    }

    /**
     * 注意を促すメッセージ表示で使用する。送料無料対象外であることを警告する。<br/>
     *
     * @param orderCommonModel    注文フロー共通Model
     * @param confirmModel        注文内容確認画面Model
     * @param receiveOrderDtoList 受注Dto
     * @param checkMessageDtoList チェックメッセージ用List
     */
    private void checkErrFreeShippingStandardAmount(OrderCommonModel orderCommonModel,
                                                    ConfirmModel confirmModel,
                                                    List<ReceiveOrderDto> receiveOrderDtoList,
                                                    List<CheckMessageDto> checkMessageDtoList) {

        BigDecimal standardAmount = confirmModel.getStandardAmount();

        // カート時点での全商品の商品金額合計(税抜)
        BigDecimal goodsPriceTotal =
                        orderCommonModel.getReceiveOrderDto().getOrderSettlementEntity().getGoodsPriceTotal();
        BigDecimal goodsPriceDelivery;
        BigDecimal promotionShippingPrice;
        // 配送単位でのプロモーションAPIから連携される送料と商品税抜金額合計を取得
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

            // プロモーションAPIの送料を取得
            promotionShippingPrice = BigDecimal.ZERO;
            if (orderDeliveryDto.getOrderDeliveryEntity().getCarriage() != null) {
                promotionShippingPrice = orderDeliveryDto.getOrderDeliveryEntity().getCarriage();
            }
            // 配送単位で商品税抜金額合計(税抜)を取得
            goodsPriceDelivery = BigDecimal.ZERO;
            if (receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal() != null) {
                goodsPriceDelivery = receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal();
            }

            // 出荷予定日(メッセージ表示用)
            // ※実質はお届け希望日のこと（受注用の出荷予定日ではない）
            String delivery = orderUtility.getDeliveryDate(receiveOrderDto);

            // カート時点での全商品の商品金額合計(税抜)が5,800円以上 かつ
            // 配送単位での商品税抜金額合計(税抜)が5,800円未満 かつ
            // プロモーションAPI.送料が無料でない場合、警告を出す
            if (goodsPriceTotal.compareTo(standardAmount) >= 0 && goodsPriceDelivery.compareTo(standardAmount) < 0
                && promotionShippingPrice != null && BigDecimal.ZERO.compareTo(promotionShippingPrice) < 0) {
                checkMessageDtoList.add(toCheckMessageDto(MSGCD_SHIPPING_STANDARD_AMOUNT,
                                                          new String[] {String.format("%,d", standardAmount.intValue()),
                                                                          StringUtils.defaultString(delivery)}, false
                                                         ));
            } else if (promotionShippingPrice != null && BigDecimal.ZERO.compareTo(promotionShippingPrice) < 0) {
                // プロモーションAPI.送料が無料でない場合
                // 値引きありメッセージ追加
                checkMessageDtoList.add(toCheckMessageDto(MSGCD_CARRIAGE_ON,
                                                          new String[] {promotionShippingPrice.setScale(0,
                                                                                                        RoundingMode.HALF_UP
                                                                                                       ).toString(),
                                                                          StringUtils.defaultString(delivery)}, false
                                                         ));
            }

        }
    }

    // 2023-renew No14 from here

    /**
     * 決済方法が未設定かどうか
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel    注文内容確認画面Model
     */
    private void checkNoSettlement(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {

        // メッセージリスト
        OrderMessageDto orderMessageDto = confirmModel.getOrderMessageDto();
        if (orderMessageDto.getOrderMessageList() == null) {
            orderMessageDto.setOrderMessageList(new ArrayList<>());
        }

        // 決済方法が未設定の場合（表示用 受注DTOで判定）
        if (confirmModel.getDispReceiveOrderDto().getOrderSettlementEntity().getSettlementMethodSeq() == null) {
            orderMessageDto.getOrderMessageList().add(toCheckMessageDto(MSGCD_SETTLEMENT_NO_SELECT, null, true));
        }

    }

    /**
     * 取りおき可否チェック
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     */
    private void checkReserveAvailability(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {

        boolean isExistReserve = false;
        for (ReceiveOrderDto receiveOrderDto : confirmModel.getReceiveOrderDtoList()) {
            if (HTypeAllocationDeliveryType.RESERVABLE.equals(receiveOrderDto.getAllocationDeliveryType())) {
                isExistReserve = true;
                break;
            }
        }
        if (!isExistReserve) {
            // セールde予約の受注が存在しない場合、スキップ
            return;
        }

        // 取りおき情報取得（WEB-API連携）
        Map<String, WebApiGetReserveResponseDetailDto> reserveDtoMap =
                        getReserveMap(orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto());
        // カレンダー選択不可日付リストを取得
        List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList = getCalendarNotSelectDateEntityList();

        // メッセージリスト
        OrderMessageDto orderMessageDto = confirmModel.getOrderMessageDto();
        if (orderMessageDto.getOrderMessageList() == null) {
            orderMessageDto.setOrderMessageList(new ArrayList<>());
        }

        for (ReceiveOrderDto receiveOrderDto : confirmModel.getReceiveOrderDtoList()) {
            if (!HTypeAllocationDeliveryType.RESERVABLE.equals(receiveOrderDto.getAllocationDeliveryType())) {
                // セールde予約以外はスキップ
                continue;
            }

            // 出荷予定日(メッセージ表示用)
            // ※実質はお届け希望日のこと（受注用の出荷予定日ではない）
            String delivery = orderUtility.getDeliveryDate(receiveOrderDto);

            for (OrderGoodsEntity entity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {

                if (entity.isBundleFlag()) {
                    // 同梱商品はチェック対象外
                    continue;
                }

                String goodsName = orderUtility.createErrDispGoodsName(entity);
                Timestamp reserveDeliveryDate = entity.getReserveDeliveryDate();

                WebApiGetReserveResponseDetailDto reserveDetailDto = reserveDtoMap.get(entity.getGoodsCode());
                // 取りおき情報が存在しない場合、エラー
                if (ObjectUtils.isEmpty(reserveDetailDto)) {
                    orderMessageDto.getOrderMessageList()
                                   .add(toCheckMessageDto(MSGCD_NOT_RESERVE_ITEM, new String[] {goodsName, delivery},
                                                          true
                                                         ));
                    continue;
                }

                // 取りおき情報取得．取りおき可否＝取りおき不可の場合、エラー
                if (HTypeReserveDeliveryFlag.OFF.getValue().equals(reserveDetailDto.getReserveFlag())) {
                    orderMessageDto.getOrderMessageList()
                                   .add(toCheckMessageDto(MSGCD_NOT_RESERVE_ITEM, new String[] {goodsName, delivery},
                                                          true
                                                         ));
                    continue;
                }

                // 取りおき情報取得．予約可能開始日～予約可能終了日の期間外の場合、エラー
                Timestamp possibleReserveFromDay = reserveDetailDto.getPossibleReserveFromDay();
                Timestamp possibleReserveToDay = reserveDetailDto.getPossibleReserveToDay();
                if (reserveDeliveryDate == null || (possibleReserveFromDay != null && !reserveDeliveryDate.equals(
                                possibleReserveFromDay) && reserveDeliveryDate.before(possibleReserveFromDay)) || (
                                    possibleReserveToDay != null && !reserveDeliveryDate.equals(possibleReserveToDay)
                                    && reserveDeliveryDate.after(possibleReserveToDay))) {
                    orderMessageDto.getOrderMessageList()
                                   .add(toCheckMessageDto(MSGCD_NOT_RESERVE_DELIVERY_DATE_OUT_OF_RANGE,
                                                          new String[] {goodsName, delivery}, true
                                                         ));
                    continue;
                }

                // カレンダー選択不可日付TBL．予約不可日に存在する日付がお届け日予定日の場合、エラー
                for (CalendarNotSelectDateEntity calendarNotSelectDateEntity : calendarNotSelectDateEntityList) {
                    if (calendarNotSelectDateEntity.getNotPossibleReserveDate().equals(reserveDeliveryDate)) {
                        orderMessageDto.getOrderMessageList()
                                       .add(toCheckMessageDto(MSGCD_NOT_RESERVE_DELIVERY_DATE_OUT_OF_RANGE,
                                                              new String[] {goodsName, delivery}, true
                                                             ));
                        continue;
                    }
                }
            }
        }
    }

    /**
     * カレンダー選択不可日付取得
     *
     * @return カレンダー選択不可日付
     */
    private List<CalendarNotSelectDateEntity> getCalendarNotSelectDateEntityList() {

        List<CalendarNotSelectDateEntityResponse> calendarNotSelectDateEntityResponseList = null;
        try {
            calendarNotSelectDateEntityResponseList = shopApi.getCalendarNotSelectDate();
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return orderReserveHelper.toCalendarNotSelectDateEntityList(calendarNotSelectDateEntityResponseList);
    }

    // 2023-renew No14 to here

    /**
     * 在庫の最終チェックを行います。
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     */
    private void checkStock(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {

        // 商品在庫数取得（WEB-API連携）
        Map<String, WebApiGetStockResponseDetailDto> stockMap = getStockMap(orderCommonModel.getReceiveOrderDto()
                                                                                            .getOrderDeliveryDto()
                                                                                            .getOrderGoodsEntityList());

        // 在庫不足のエラーチェック
        // メッセージリスト
        OrderMessageDto orderMessageDto = confirmModel.getOrderMessageDto();
        if (orderMessageDto.getOrderMessageList() == null) {
            orderMessageDto.setOrderMessageList(new ArrayList<>());
        }

        // 在庫に関しては受注受付番号単位でチェックを行う。
        stockUtility.checkStockByConfirm(
                        confirmModel.getReceiveOrderDtoList(), orderMessageDto.getOrderMessageList(), stockMap);
        // 購入制限チェックを行う。
        stockUtility.checkPurchaseByConfirm(
                        confirmModel.getReceiveOrderDtoList(), orderMessageDto.getOrderMessageList(), stockMap);
    }

    /**
     * 今すぐお届け分の出荷予定日チェック
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     */
    private void checkShipmentDateByDeliveryNow(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {

        // フロー中の全商品のリスト
        List<OrderGoodsEntity> orderGoodsEntityList =
                        orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto().getOrderGoodsEntityList();

        // 顧客番号
        Integer customerNo = commonInfoUtility.getCustomerNo(getCommonInfo());

        for (ReceiveOrderDto receiveOrderDto : confirmModel.getReceiveOrderDtoList()) {
            if (!HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
                // 今すぐお届け以外はスキップ
                continue;
            }

            OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

            // リクエストDTO
            WebApiGetDeliveryInformationRequestDto reqDto =
                            ApplicationContextUtility.getBean(WebApiGetDeliveryInformationRequestDto.class);

            // 注文者顧客番号
            reqDto.setOrderCustomerNo(customerNo);

            // 配送先顧客番号
            if (null != orderDeliveryDto.getCustomerNo()) {
                // 新規顧客ではない場合、選択したお届け先の顧客番号を設定する
                reqDto.setDeliveryCustomerNo(orderDeliveryDto.getCustomerNo());
            } else {
                // 新規顧客の場合、会員の顧客番号を設定する。
                reqDto.setDeliveryCustomerNo(customerNo);
            }

            // 配送先郵便番号
            reqDto.setDeliveryZipcode(orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode());

            OrderDeliveryInformationRequest orderDeliveryInformationRequest =
                            orderHelper.toOrderDeliveryInformationRequest(orderGoodsEntityList, reqDto, null);
            orderDeliveryInformationRequest.setCheckMessageDtoList(null);

            // 配送情報取得（WEB-API連携）
            WebApiGetDeliveryInformationResponseDetailDto resDto =
                            orderHelper.toWebApiGetDeliveryInformationResponseDetailDto(
                                            getOrderDeliveryInformation(orderDeliveryInformationRequest));

            if (resDto == null && orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() == null) {
                // 配送情報が取得できなかった場合 かつ お届け希望日が設定されていなかった場合
                // 保留区分を設定
                orderPendingUtility.checkPrimaryPending(receiveOrderDto, HTypePendingType.ZIP_CODE_UNKNOWN);
                LOGGER.warn("配送情報取得連携で配送情報が取得できません。 受注番号(代表)：" + receiveOrderDto.getOrderSummaryEntity().getOrderCode()
                            + " 顧客番号：" + receiveOrderDto.getOrderPersonEntity().getCustomerNo());
                return;
            } else if (resDto != null && orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate() != null) {
                // 配送情報取得 詳細情報が存在する かつ お届け希望日が設定されている場合

                // 2023-renew No14 from here
                // 出荷日を取得
                if (resDto.getDeliveryCompanyCode()
                          .equals(orderDeliveryDto.getDeliveryInformationDetailDto().getDeliveryCompanyCode())) {
                    // 出荷予定日の設定（今すぐお届け）
                    if (setShipmentDateForDeliverNow(orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto())) {
                        return;
                    }
                }
                // 2023-renew No14 to here
            }

            // 以下の場合はエラー
            // ・配送情報取得ができなかった場合 かつ お届け予定日が設定されていた場合
            // ・配送情報取得 詳細情報が取得できた場合 かつ お届け予定日が設定されていなかった場合
            // ・配送設定で取得した配送会社コードと再取得した配送会社コードが一致しない場合
            // ・出荷予定日が取得できなかった場合
            throwMessage(MSGCD_DELIVERY_DATE_ERR);
        }
    }

    /**
     * 受注DTO単位で注文チェックを行い、画面にエラー情報を設定
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     */
    private void checkOrderByReceiveOrderDtoList(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {

        for (int i = 0; i < confirmModel.getReceiveOrderDtoList().size(); i++) {
            ReceiveOrderDto dto = confirmModel.getReceiveOrderDtoList().get(i);

            // 受注チェック
            OrderCheckRequest orderCheckRequest = orderHelper.toOrderCheckRequest(dto, getCommonInfo());
            OrderMessageDtoResponse orderMessageDtoResponse = null;
            try {
                orderMessageDtoResponse = orderApi.check(orderCheckRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            OrderMessageDto orderMessageDto = orderHelper.toOrderMessageDto(orderMessageDtoResponse);
            confirmModel.getReceiveOrderDtoList()
                        .set(i, orderHelper.toReceiveOrderDto(orderMessageDtoResponse.getReceiveOrderDto()));

            // メッセージなし
            if (orderMessageDto == null) {
                continue;
            }

            // 受注DTO 商品ごとのメッセージIDを追加
            if (orderMessageDto.getOrderGoodsMessageMapMap() != null) {
                confirmModel.getOrderMessageDto()
                            .addOrderGoodsMessageMapMap(orderMessageDto.getOrderGoodsMessageMapMap());
            }

            // 受注DTO単位のメッセージ
            if (!CollectionUtil.isEmpty(orderMessageDto.getOrderMessageList())) {

                List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

                // お届け希望日
                String deliveryDate = getDeliveryDate(orderCommonModel, confirmModel, dto.getOrderDeliveryDto()
                                                                                         .getOrderDeliveryEntity()
                                                                                         .getOrderConsecutiveNo());

                // メッセージIDを変換
                for (CheckMessageDto orderMessage : orderMessageDto.getOrderMessageList()) {
                    // 必要な情報がない場合 エラー
                    CheckMessageDto checkMessageDto;
                    if (MSGCD_SETTLEMENT_PURCHSEDMAX_OVER.equals(orderMessage.getMessageId())) {
                        // 上限金額エラーの場合は、客先要望通りのエラーメッセージにするため作成方法を変える
                        // 選択できない決済方法である旨を直前にくっつけ、各決済に対する上限金額を表示するように対応
                        checkMessageDtoList.add(toCheckMessageDto(MSGCD_GET_SETTLEMENT_ERROR, deliveryDate));
                        checkMessageDto = orderMessage;
                    } else {
                        checkMessageDto = toCheckMessageDto(MSGCD_ERR_ANY_ORDER,
                                                            new String[] {getMessage(orderMessage.getMessageId(),
                                                                                     orderMessage.getArgs()
                                                                                    ), deliveryDate},
                                                            orderMessage.isError()
                                                           );
                    }
                    checkMessageDtoList.add(checkMessageDto);
                }

                confirmModel.getOrderMessageDto().addOrderMessageList(checkMessageDtoList);
            }

        }
    }

    /**
     * エラーメッセージ（注文単位）を作成する<br/>
     *
     * @param messageId    メッセージID
     * @param deliveryDate 配送日時
     * @return チェックメッセージDto
     */
    private CheckMessageDto toCheckMessageDto(String messageId, String deliveryDate) {
        return toCheckMessageDto(MSGCD_ERR_ANY_ORDER, new String[] {getMessage(messageId, null), deliveryDate}, true);
    }

    /**
     * エラーメッセージセット
     * <pre>
     * 注文内容確認画面で発生したエラーを設定します。
     * </pre>
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param isOrderRegist      注文登録時のチェックかどうか
     */
    private void addOrderCheckErrorInfo(OrderCommonModel orderCommonModel,
                                        ConfirmModel confirmModel,
                                        RedirectAttributes redirectAttributes,
                                        Model model,
                                        boolean isOrderRegist) {

        OrderMessageDto orderMessageDto = confirmModel.getOrderMessageDto();
        if (orderMessageDto == null) {
            // エラーなし
            return;
        }

        if (isOrderRegist && !orderMessageDto.hasErrorMessage()) {
            // エラーなし（注文登録時は今すぐお届け／取りおき／入荷次第の振り分けメッセージ等は処理対象外）
            return;
        }

        // 以下の形式でメッセージを表示しないものに関してはリストに追加
        // {メッセージ内容} 対象：{商品表示名(規格1/規格2)}
        List<String> msgCd = new ArrayList<>();
        msgCd.add(MSGCD_SETTLEMENT_PURCHSEDMAX_OVER);
        msgCd.add(MSGCD_SETTLEMENT_PURCHSEDMIN_OVER);
        msgCd.add(MSGCD_PURCHASE_LIMIT_OVER_ERROR);

        if (orderMessageDto.getOrderGoodsMessageMapMap() != null && !orderMessageDto.getOrderGoodsMessageMapMap()
                                                                                    .isEmpty()) {
            // エラーを追加
            // 受注DTOリスト順にエラーを追加
            for (ReceiveOrderDto receiveOrderDto : confirmModel.getReceiveOrderDtoList()) {
                OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

                // 受注連番
                Integer orderConsecutiveNo = orderDeliveryDto.getOrderDeliveryEntity().getOrderConsecutiveNo();
                Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap =
                                orderMessageDto.getOrderGoodsMessageMapMap().get(orderConsecutiveNo);
                // 受注DTOにエラーなし
                if (orderGoodsMessageMap == null || orderGoodsMessageMap.isEmpty()) {
                    continue;
                }

                // お届け希望日
                String deliveryDate = getDeliveryDate(orderCommonModel, confirmModel, orderConsecutiveNo);

                // 商品順にエラーを追加
                for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {

                    List<CheckMessageDto> checkMessageDtoList =
                                    orderGoodsMessageMap.get(orderGoodsEntity.getGoodsSeq());

                    // 商品にエラーなし
                    if (CollectionUtil.isEmpty(checkMessageDtoList)) {
                        continue;
                    }

                    for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                        // リストに追加されているメッセージに関してはそのまま追加
                        if (msgCd.contains(checkMessageDto.getMessageId())) {
                            addWarnOrErrorMessage(checkMessageDto.getMessageId(), checkMessageDto.getArgs(),
                                                  redirectAttributes, model, !isOrderRegist
                                                 );
                            continue;
                        }
                        addWarnOrErrorMessage(MSGCD_ERR_ANY_ORDER_GOODS,
                                              new String[] {checkMessageDto.getMessage(), deliveryDate,
                                                              orderUtility.createErrDispGoodsName(orderGoodsEntity)},
                                              redirectAttributes, model, !isOrderRegist
                                             );
                    }
                }
            }
        }

        List<CheckMessageDto> checkMessageList = orderMessageDto.getOrderMessageList();

        // メッセージ内の商品コードが心意気商品の場合、元商品の商品コードに変換する。
        // 「メッセージ重複を防止するため、商品コードをキーにチェック」を行っている処理がメッセージ引数：１に
        // 商品コードが設定されている前提のためメッセージ引数：１を対象に変換。
        // クラス名：StockUtility メソッド名：checkAllocationDependingOnReceiptGoods
        for (CheckMessageDto checkMessageDto : checkMessageList) {
            // エラーメッセージに引数がなければ、処理をしない
            if (null != checkMessageDto.getArgs() && checkMessageDto.getArgs().length > 0) {
                // エラーメッセージの１つ目の引数があれば心意気元商品の商品番号に変換する
                if (null != checkMessageDto.getArgs()[0] && !checkMessageDto.getArgs()[0].toString().isEmpty()) {
                    checkMessageDto.getArgs()[0] = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                    (checkMessageDto.getArgs()[0].toString()));
                }
            }
        }

        // 発生したエラーを追加
        addErrorInfo(checkMessageList, redirectAttributes, model, !isOrderRegist);

    }

    /**
     * 受注連番ごとのお届け希望日を取得します。
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param orderConsecutiveNo 受注連番
     * @return お届け時期
     */
    private String getDeliveryDate(OrderCommonModel orderCommonModel,
                                   ConfirmModel confirmModel,
                                   Integer orderConsecutiveNo) {
        // お届け時期
        String deliveryDate = StringUtils.EMPTY;
        if (!CollectionUtil.isEmpty(confirmModel.getReceiveOrderDtoList())
            && confirmModel.getReceiveOrderDtoList().size() >= orderConsecutiveNo) {
            ReceiveOrderDto receiveOrderDto = confirmModel.getReceiveOrderDtoList().get(orderConsecutiveNo - 1);
            deliveryDate = orderUtility.getDeliveryDate(receiveOrderDto);
        }
        return deliveryDate;
    }

    /**
     * ページにトークンをセットする（確認画面用）
     *
     * @param orderCommonModel 注文フロー共通Model
     */
    private void setConfirmToken(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {
        // 確定ボタン押下時に表示内容が最新であるかチェックするためにトークンを生成
        String orderConfirmToken =
                        ApplicationContextUtility.getBean(MD5Helper.class).createHash(Long.toString(System.nanoTime()));
        // 画面用と比較用をそれぞれセット
        confirmModel.setOrderConfirmToken(orderConfirmToken);
        confirmModel.setDiffOrderConfirmToken(orderConfirmToken);
    }

    /**
     * カート情報クリア処理
     * 新規トランザクションで行う。
     * <pre>
     * ヘッダー部のカート情報初期化処理 追加
     * </pre>
     *
     * @param receiveOrderDto 受注DTO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    private void deleteCartGoodsList(ReceiveOrderDto receiveOrderDto) {
        // PDR Migrate Customization from here
        // 3Dセキュアは行わないため処理削除
        // PDR Migrate Customization to here

        // カート情報削除処理
        // 注文対象の商品についてカートから削除する
        String accessUid = this.getCommonInfo().getCommonInfoBase().getAccessUid();
        Integer memberInfoSeq = this.getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

        // カートDtoからカートSEQリストを作成
        List<CartGoodsDto> cartGoodsDtoList = receiveOrderDto.getOrderTempDto().getCartDto().getCartGoodsDtoList();
        List<Integer> cartGoodsSeqList = new ArrayList<>();
        for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            cartGoodsSeqList.add(cartGoodsEntity.getCartSeq());
        }

        // カート商品リスト削除ロジック実行
        CartGoodsListDeleteRequest cartGoodsListDeleteRequest = new CartGoodsListDeleteRequest();
        cartGoodsListDeleteRequest.setCartGoodsSeqList(cartGoodsSeqList);
        cartGoodsListDeleteRequest.setAccessUid(accessUid);
        cartGoodsListDeleteRequest.setMemberInfoSeq(memberInfoSeq);
        try {
            cartApi.deleteCartGoodsList(cartGoodsListDeleteRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // PDR Migrate Customization from here
        // カート情報 初期化
        getCommonInfo().getCommonInfoBase().setCartGoodsSumCount(BigDecimal.ZERO);
        getCommonInfo().getCommonInfoBase().setCartGoodsSumPrice(BigDecimal.ZERO);
        // PDR Migrate Customization to here
    }

    /**
     * 後処理登録
     * ・アクセス情報：受注回数
     * ・アクセス情報：受注個数
     * ・アクセス情報：受注金額
     * ・注文受付完了メール送信
     * <p>
     * 受注個数 受注DTOリストから作成するよう修正
     * 受注金額 受注DTOリストから算出するよう修正
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     */
    private void registAfterProcess(OrderCommonModel orderCommonModel, ConfirmModel confirmModel, Timestamp orderTime) {

        // 商品個数マップ作成
        Map<Integer, BigDecimal> goodsCountMap = new HashMap<>();

        // PDR Migrate Customization from here
        // 受注合計金額
        Integer orderPrice = 0;
        for (ReceiveOrderDto receiveOrderDto : confirmModel.getReceiveOrderDtoList()) {
            // 受注日時
            receiveOrderDto.getOrderSummaryEntity().setOrderTime(orderTime);

            // 注文商品数分ループし、以下を作成する。
            // ・Map<商品SEQ, 商品グループ単位の合計個数>
            for (OrderGoodsEntity orderGoodsEntity : orderUtility.getALLGoodsEntityList(receiveOrderDto)) {
                Integer goodsSeq = orderGoodsEntity.getGoodsSeq();
                BigDecimal goodsCount = orderGoodsEntity.getGoodsCount();
                if (!goodsCountMap.containsKey(goodsSeq)) {
                    // なかったから追加
                    goodsCountMap.put(goodsSeq, goodsCount);
                } else {
                    // あったから加算
                    goodsCountMap.put(goodsSeq, goodsCountMap.get(goodsSeq).add(goodsCount));
                }
            }
            orderPrice = orderPrice + receiveOrderDto.getOrderSummaryEntity().getOrderPrice().intValue();
        }
        // PDR Migrate Customization to here

        // アクセス情報：受注回数登録
        this.accessInfoRegist(HTypeAccessType.GOODS_ORDER_COUNT, 0, null);

        // アクセス情報：受注金額登録
        // PDR Migrate Customization from here
        // 全ての受注の合計金額を算出します。
        this.accessInfoRegist(HTypeAccessType.GOODS_ORDER_PRICE, 0, orderPrice);
        // PDR Migrate Customization to here

        // 2023-renew No79 from here
        if (HTypeOrderCompletePermitFlag.ON == orderCommonModel.getMemberInfoEntity().getOrderCompletePermitFlag()) {
            // 注文受付完了メール送信処理(非同期処理)
            OrderCompleteMailSendRequest orderCompleteMailSendRequest = new OrderCompleteMailSendRequest();
            orderCompleteMailSendRequest.setReceiveOrderDtoList(
                            orderHelper.toReceiveOrderDtoJsonList(confirmModel.getReceiveOrderDtoList()));
            try {
                orderApi.sendOrderCompleteMail(orderCompleteMailSendRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.warn("注文受付完了メール送信処理で例外処理が発生しました", e);
            }
        }
        // 2023-renew No79 to here
    }

    /**
     * アクセス情報登録
     * サービス実行に必要なパラメータ
     *
     * @param accessType    アクセス区分
     * @param goodsGroupSeq 商品グループSEQ
     * @param accessCount   アクセス数
     */
    private void accessInfoRegist(HTypeAccessType accessType, Integer goodsGroupSeq, Integer accessCount) {
        AccessRegistRequest accessRegistRequest = new AccessRegistRequest();
        accessRegistRequest.setAccessType(accessType.getValue());
        accessRegistRequest.setGoodsGroupSeq(goodsGroupSeq);
        accessRegistRequest.setAccessCount(accessCount);
        accessRegistRequest.setAccessUid(this.getCommonInfo().getCommonInfoBase().getAccessUid());
        accessRegistRequest.setCampainCode(this.getCommonInfo().getCommonInfoBase().getCampaignCode());
        // 非同期処理を登録
        try {
            accessApi.registAccess(accessRegistRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
    }

    /**
     * cookie削除処理<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel 注文内容確認画面Model
     * @param request      リクエスト
     * @param response     レスポンス
     */
    private void doDeleteCookie(OrderCommonModel orderCommonModel,
                                ConfirmModel confirmModel,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        // 2023-renew No24 from here
        String couponCode = orderCommonModel.getReceiveOrderDto().getCouponCode();
        if (StringUtils.isEmpty(couponCode)) {
            return;
        }

        // cookie、セッション等に保持されたクーポン関連情報を一括削除
        deleteCouponInfo(couponCode, request, response);

        // 利用可能クーポン一覧取得APIの呼び出しを行い、セッション情報の利用可能クーポン一覧数を更新
        // ※モデル,リダイレクトアトリビュートをNullで送り、エラー時は何もしない（ログ出力のみ）
        executeWebApiGetCouponListAndSetCouponCount(null, null);
        // 2023-renew No24 to here
    }

    // PDR Migrate Customization to here
    // 2023-renew No14 from here

    /**
     * 出荷予定日の設定
     *
     * @param orderCommonModel 注文フロー共通Model
     */
    private void setShipmentDate(OrderCommonModel orderCommonModel) {

        // 今すぐお届け分
        setShipmentDateForDeliverNow(orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto());

        // セールde予約分
        setShipmentDateForReservable(orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto());

    }

    /**
     * 出荷予定日の設定（今すぐお届け）
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     * @return TRUE:正常、FALSE:異常
     */
    private boolean setShipmentDateForDeliverNow(OrderDeliveryDto orderDeliveryDto) {

        // 今すぐお届け分が存在しない場合、スキップ
        if (CollectionUtil.isEmpty(orderDeliveryDto.getOrderDeliveryNowDtoList())) {
            return true;
        }

        Timestamp receiverDate = orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate();
        Integer deliveryDesignatedTimeCode =
                        conversionUtility.toInteger(orderDeliveryDto.getOrderDeliveryEntity().getReceiverTimeZone());

        // 今すぐお届け分
        List<WebApiGetShipmentDateRequestDetailDto> info = new ArrayList<>();
        orderDeliveryDto.getOrderDeliveryNowDtoList().forEach(dto -> {
            WebApiGetShipmentDateRequestDetailDto detailDto = new WebApiGetShipmentDateRequestDetailDto();
            OrderGoodsEntity entity = dto.getOrderGoodsEntity();
            // 申込商品
            detailDto.setGoodsCode(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode()));
            // 配達指定日
            detailDto.setDeliveryDesignatedDay(receiverDate);
            // 配達指定時間
            detailDto.setDeliveryDesignatedTimeCode(deliveryDesignatedTimeCode);
            info.add(detailDto);
        });

        // 出荷予定日（今すぐお届け）を設定
        Map<String, Timestamp> shipmentDateMap =
                        executeWebApiGetShipmentDate(orderDeliveryDto, info).getShipmentDateMap();
        for (WebApiGetShipmentDateRequestDetailDto dto : info) {
            String key = orderUtility.getShipmentDateMapKey(dto.getGoodsCode(), dto.getDeliveryDesignatedDay());
            if (!shipmentDateMap.containsKey(key)) {
                // 1件でも出荷予定日がNULLの場合、異常
                return false;
            }
            // 今すぐお届け分は全件同じ出荷予定日が返却される仕様なので、上書きしてOK
            orderDeliveryDto.getOrderDeliveryEntity().setShipmentDate(shipmentDateMap.get(key));
        }
        return true;
    }

    /**
     * 出荷予定日の設定（セールde予約）
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     */
    private void setShipmentDateForReservable(OrderDeliveryDto orderDeliveryDto) {

        // セールde予約分が存在しない場合、スキップ
        if (CollectionUtil.isEmpty(orderDeliveryDto.getOrderReserveDeliveryDtoList())) {
            return;
        }

        List<WebApiGetShipmentDateRequestDetailDto> info = new ArrayList<>();
        orderDeliveryDto.getOrderReserveDeliveryDtoList().forEach(dto -> {
            WebApiGetShipmentDateRequestDetailDto detailDto = new WebApiGetShipmentDateRequestDetailDto();
            OrderGoodsEntity entity = dto.getOrderGoodsEntity();
            // 申込商品
            detailDto.setGoodsCode(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode()));
            // 配達指定日
            detailDto.setDeliveryDesignatedDay(entity.getReserveDeliveryDate());
            info.add(detailDto);
        });

        // 出荷予定日MAP（セールde予約分のみ）を保持
        orderDeliveryDto.setShipmentDateMap(executeWebApiGetShipmentDate(orderDeliveryDto, info).getShipmentDateMap());

    }

    /**
     * WEB-API連携 出荷予定日取得
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     * @param info 出荷予定日取得 詳細情報 リスト
     * @return 出荷予定日取得結果
     */
    private WebApiGetShipmentDateResponseDto executeWebApiGetShipmentDate(OrderDeliveryDto orderDeliveryDto,
                                                                          List<WebApiGetShipmentDateRequestDetailDto> info) {

        Integer customerNo = commonInfoUtility.getCustomerNo(getCommonInfo());

        // Web-API リクエストDTO
        WebApiGetShipmentDateRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetShipmentDateRequestDto.class);

        // 注文者顧客番号
        reqDto.setOrderCustomerNo(customerNo);

        // 配送先顧客番号
        if (null != orderDeliveryDto.getCustomerNo()) {
            // 新規顧客ではない場合、選択したお届け先の顧客番号を設定する
            reqDto.setDeliveryCustomerNo(orderDeliveryDto.getCustomerNo());
        } else {
            // 新規顧客の場合、会員の顧客番号を設定する。
            reqDto.setDeliveryCustomerNo(customerNo);
        }

        // 配送先郵便番号
        reqDto.setDeliveryZipcode(orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode());

        // 配送会社コード
        reqDto.setDeliveryCompanyCode(orderDeliveryDto.getDeliveryInformationDetailDto().getDeliveryCompanyCode());

        // 情報
        reqDto.setInfo(info);

        WebApiGetShipmentDateResponse webApiGetShipmentDateResponse = null;
        try {
            // Web-API実行
            webApiGetShipmentDateResponse =
                            webapiApi.getShipmentDate(orderHelper.toWebApiGetShipmentDateRequest(reqDto));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        WebApiGetShipmentDateResponseDto getShipmentDateResponseDto =
                        orderHelper.toWebApiGetShipmentDateResponseDetailDto(webApiGetShipmentDateResponse);
        if (getShipmentDateResponseDto == null || getShipmentDateResponseDto.getShipmentDateMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        return getShipmentDateResponseDto;
    }

    // 2023-renew No14 to here

    /**  ***********　Confirm End　*********** **/
    /**  ***********　Complete Start　*********** **/

    /**
     * 注文完了画面：初期処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param confirmModel       注文内容確認画面Model
     * @param sessionStatus      セッション
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 注文完了画面
     */
    @GetMapping(value = {"/complete", "/complete.html"})
    public String doLoadComplete(OrderCommonModel orderCommonModel,
                                 ConfirmModel confirmModel,
                                 SessionStatus sessionStatus,
                                 RedirectAttributes redirectAttributes,
                                 Model model) throws JsonProcessingException {

        // Modelをセッションより破棄（コントローラー処理完了後に破棄される）
        sessionStatus.setComplete();
        completeHelper.toPageForLoad(orderCommonModel, confirmModel);

        // Google Tag Manager eコマースタグ
        GoogleAnalyticsInfo googleAnalyticsInfo =
                        completeHelper.toGoogleAnalyticsInfo(confirmModel, getCommonInfo().getCommonInfoShop());
        model.addAttribute("googleAnalyticsInfo", new ObjectMapper().writeValueAsString(googleAnalyticsInfo));

        // 2023-renew No3-taglog from here
        UkTaglogCheckoutInfo ukTaglogCheckoutInfo = completeHelper.toUkTaglogInfo(confirmModel);
        model.addAttribute("ukTaglogCheckoutInfo", new ObjectMapper().writeValueAsString(ukTaglogCheckoutInfo));
        // 2023-renew No3-taglog to here

        return "order/complete";
    }

    /**  ***********　Complete End　*********** **/

}
// 2023-renew No14 to here
