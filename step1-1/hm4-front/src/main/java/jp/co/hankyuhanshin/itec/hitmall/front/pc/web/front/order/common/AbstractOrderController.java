/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.BillPriceCalculateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetReserveMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetReserveRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetStockMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementMethodSelectListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementMethodSelectListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.helper.crypto.MD5Helper;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditCardType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeExpirationDateMonth;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.coupon.AbstractCouponController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.DeliveryController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.OrderHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentModelItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentModelRegistedCardItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.StockUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文関連抽象コントローラー
 *
 * @author ota-s5
 */
// 2023-renew No14 from here
public class AbstractOrderController extends AbstractCouponController {

    /** ロガー */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractOrderController.class);

    /** メッセージコード：システムエラー発生 */
    public static final String MSGCD_SYSTEM_ERR = "LOX000102";

    /** メッセージコード：不正操作（カート情報が取得できない等） */
    public static final String MSGCD_ILLEGAL_OPERATION = "AOX000101";

    /** メッセージコード：不正操作、ポストトークン不正等 */
    public static final String MSGCD_INVALID_POST_TOKEN = "PDR-0417-001-A-";

    /** メッセージコード：配送コードが存在しない区分 */
    public static final String MSGCD_UNKNOWN_DELIVERY_COMPANY_CODE = "PDR-0015-001-A-F";

    /** フラッシュスコープパラメータ：カートからの遷移判定 */
    public static final String FROM_CART = "fromCart";

    /** フラッシュスコープパラメータ：カート画面から引き継いだカートDTO */
    public static final String CART_DTO = "CartDto";

    // 2023-renew No14 from here
    /** フラッシュスコープパラメータ：セールde予約画面から引き継いだセールde予約情報Item */
    public static final String ORDER_RESERVE_ITEM = "OrderReserveItem";
    // 2023-renew No14 to here

    /**
     * 注文Helper
     */
    protected final OrderHelper orderHelper;

    /**
     * お支払い方法選択画面 Helper
     */
    protected final PaymentHelper paymentHelper;

    /**
     * 受注共通処理
     */
    protected final OrderUtility orderUtility;

    /**
     * 変換ユーティリティクラス
     */
    protected final ConversionUtility conversionUtility;

    /**
     * 共通情報ユーティリティクラス
     */
    protected final CommonInfoUtility commonInfoUtility;

    /**
     * クーポン関連ユーティリティクラス
     */
    protected final CouponUtility couponUtility;

    /**
     * 通信ユーティリティ
     */
    protected final ComTransactionUtility comTransactionUtility;

    /**
     * 日付関連Utilityクラス
     */
    protected final DateUtility dateUtility;

    /**
     * 商品在庫情報ユーティリティクラス
     */
    protected final StockUtility stockUtility;

    /**
     * 商品系ヘルパークラス
     */
    protected final GoodsUtility goodsUtility;

    /**
     * 注文保留Utilityクラス
     */
    protected final OrderPendingUtility orderPendingUtility;

    /**
     * 注文API
     */
    protected final OrderApi orderApi;

    /**
     * 会員API
     */
    protected final MemberInfoApi memberInfoApi;

    /**
     * WEB-API API
     */
    protected final WebapiApi webapiApi;

    /**
     * ショップAPI
     */
    protected final ShopApi shopApi;

    /**
     * コンストラクタ
     */
    @Autowired
    protected AbstractOrderController(CouponHelper couponHelper,
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
                                      ShopApi shopApi) {
        // 2023-renew No24 from here
        super(webapiApi, couponHelper, commonInfoUtility);
        // 2023-renew No24 to here
        this.orderHelper = orderHelper;
        this.paymentHelper = paymentHelper;
        this.orderUtility = orderUtility;
        this.conversionUtility = conversionUtility;
        this.commonInfoUtility = commonInfoUtility;
        this.couponUtility = couponUtility;
        this.comTransactionUtility = comTransactionUtility;
        this.dateUtility = dateUtility;
        this.stockUtility = stockUtility;
        this.goodsUtility = goodsUtility;
        this.orderPendingUtility = orderPendingUtility;
        this.orderApi = orderApi;
        this.memberInfoApi = memberInfoApi;
        this.webapiApi = webapiApi;
        this.shopApi = shopApi;
    }

    /**  ***********　共通部品 Start　*********** **/

    /**
     * エラーメッセージを作成する<br/>
     *
     * @param messageId メッセージID
     * @param args      引数
     * @param isError   エラーフラグ
     * @return チェックメッセージDto
     */
    protected CheckMessageDto toCheckMessageDto(String messageId, Object[] args, boolean isError) {
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessageId(messageId);
        checkMessageDto.setArgs(args);
        checkMessageDto.setError(isError);
        return checkMessageDto;
    }

    /**
     * エラーメッセージセット
     * <pre>
     * 各チェックで発生したエラーを設定します。
     * </pre>
     *
     * @param checkMessageDtoList メッセージDTOList
     * @param redirectAttributes  リダイレクトアトリビュート
     * @param model               モデル
     * @param isWarn              警告メッセージかどうか
     */
    protected void addErrorInfo(List<CheckMessageDto> checkMessageDtoList,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                boolean isWarn) {
        if (checkMessageDtoList.isEmpty()) {
            // エラーなし
            return;
        }

        // エラーを追加
        for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
            // 2023-renew No24 from here
            String messageId = checkMessageDto.getMessageId();
            Object[] args = checkMessageDto.getArgs();
            if (couponUtility.isCouponInfoMessageList(messageId)) {
                // クーポン適用メッセージ
                addInfoMessage(messageId, args, redirectAttributes, model, "couponMessages");
            } else if (couponUtility.isCouponErrorMessageList(messageId)) {
                // クーポン適用失敗メッセージ
                addWarnMessage(messageId, args, redirectAttributes, model, "couponErrorMessages");
            } else {
                // メッセージの取得
                addWarnOrErrorMessage(messageId, args, redirectAttributes, model, isWarn);
            }
            // 2023-renew No24 to here
        }
    }

    /**
     * メッセージ追加<br/>
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param isWarn             警告メッセージかどうか
     */
    protected void addWarnOrErrorMessage(String messageCode,
                                         Object[] args,
                                         RedirectAttributes redirectAttributes,
                                         Model model,
                                         boolean isWarn) {
        if (isWarn) {
            // 警告メッセージとして追加
            addWarnMessage(messageCode, args, redirectAttributes, model);
        } else {
            // エラーメッセージとして追加
            addErrorMessage(messageCode, args);
        }
    }

    /**
     * ページにトークンをセットする<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     */
    protected void setToken(OrderCommonModel orderCommonModel) {
        // PDR Migrate Customization from here
        // トークン生成
        String token = ApplicationContextUtility.getBean(MD5Helper.class).createHash(Long.toString(System.nanoTime()));
        // 生成したトークンをセッションと画面でそれぞれ保持
        orderCommonModel.setSToken(token);
        orderCommonModel.setPToken(token);
        // PDR Migrate Customization to here
    }

    /**
     * ブラウザバックチェック
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return 遷移先画面
     */
    protected String checkToken(OrderCommonModel orderCommonModel,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                HttpServletRequest request) {

        // GETで来た場合は、以下理由により常にOK
        // ・トークンがPOSTされない
        // ・必ずdoLoadを通る
        String method = request.getMethod();
        if (!"POST".equals(method)) {
            return null;
        }

        String sToken = orderCommonModel.getSToken();
        String pToken = orderCommonModel.getPToken();

        // トークンがずれている場合はカートに戻す
        // ※通常は/order/配下以外のページを表示すると、注文許可フラグが消えてエラー画面に遷移するため
        // ここに来た時点でサブアプリが消えていることはありえない
        // しかし、注文中に/login/order.html（注文ログイン画面）に直URLで遷移⇒ブラウザバックしてきた場合のみ、
        // ここに来た時点でsTokenがnullとなる
        // ⇒nullチェックが必要
        if (sToken == null || !sToken.equals(pToken)) {
            clearErrorList();
            addMessage(MSGCD_INVALID_POST_TOKEN, redirectAttributes, model);
            return "redirect:/cart/index.html";
        }
        return null;
    }

    /**
     * 受注Dtoが取得できない場合エラー
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return true..OK / false..NG
     */
    protected boolean checkReceiveOrderDto(OrderCommonModel orderCommonModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {

        ReceiveOrderDto receiveOrderDto = orderCommonModel.getReceiveOrderDto();
        boolean isOk = receiveOrderDto != null
                       // 注文主情報の存在チェック
                       && receiveOrderDto.getOrderPersonEntity() != null
                       // 配送情報の存在チェック
                       && receiveOrderDto.getOrderDeliveryDto() != null
                       // 決済方法情報の存在チェック
                       && receiveOrderDto.getOrderSettlementEntity() != null;

        // エラーの場合
        if (!isOk) {
            addMessage(MSGCD_INVALID_POST_TOKEN, redirectAttributes, model);
        }

        return isOk;
    }

    /**  ***********　共通部品 End　*********** **/
    /**  ***********　Delivery関連 共通部品 Start　*********** **/

    /**
     * 配送情報の設定
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return TRUE：正常、FALSE：異常
     */
    protected boolean executeWebApiGetDeliveryInformation(OrderCommonModel orderCommonModel,
                                                          RedirectAttributes redirectAttributes,
                                                          Model model) {

        OrderDeliveryDto orderDeliveryDto = orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto();

        // リクエストDTO
        WebApiGetDeliveryInformationRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetDeliveryInformationRequestDto.class);
        // 注文者顧客番号
        reqDto.setOrderCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        // 配送先顧客番号
        reqDto.setDeliveryCustomerNo(orderDeliveryDto.getCustomerNo());
        // 郵便番号
        reqDto.setDeliveryZipcode(orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode());

        // 配送情報取得（WEB-API連携）
        OrderDeliveryInformationDtoResponse response = getOrderDeliveryInformation(
                        orderHelper.toOrderDeliveryInformationRequest(orderDeliveryDto.getOrderGoodsEntityList(),
                                                                      reqDto, null
                                                                     ));
        List<CheckMessageDto> checkMessageDtoList =
                        orderHelper.toCheckMessageDtoListOrder(response.getCheckMessageDtoList());
        orderDeliveryDto.setDeliveryInformationDetailDto(
                        orderHelper.toWebApiGetDeliveryInformationResponseDetailDto(response));

        // 配送可の場合、連携された配送会社コードをチェック
        if (HTypeDeliveryFlag.ON.getValue()
                                .equals(orderDeliveryDto.getDeliveryInformationDetailDto().getDeliveryFlag())) {
            // 連携された配送会社コードが存在しない場合、エラー
            if (EnumTypeUtil.getEnumFromValue(HTypeDeliveryType.class,
                                              orderDeliveryDto.getDeliveryInformationDetailDto()
                                                              .getDeliveryCompanyCode()
                                             ) == null) {
                addWarnMessage(MSGCD_UNKNOWN_DELIVERY_COMPANY_CODE, null, redirectAttributes, model);
                return false;
            }
        }

        // 発生したエラーを表示
        addErrorInfo(checkMessageDtoList, redirectAttributes, model, true);

        // 配送情報取得でエラーメッセージが追加されていた場合
        if (!checkMessageDtoList.isEmpty()) {
            return false;
        }

        return true;

    }

    /**
     * 配送方法リスト作成
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return TRUE：正常、FALSE：異常
     */
    protected boolean createDeliveryMethodList(OrderCommonModel orderCommonModel,
                                               RedirectAttributes redirectAttributes,
                                               Model model) {

        ReceiveOrderDto receiveOrderDto = orderCommonModel.getReceiveOrderDto();
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 配送方法選択可能リスト取得サービス実行
        DeliveryDtoListResponse deliveryDtoListResponse = null;
        try {
            deliveryDtoListResponse = orderApi.getDeliveryMethodSelectList(
                            orderHelper.toDeliveryMethodSelectListGetRequest(receiveOrderDto, true));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        List<DeliveryDto> deliveryDtoList = orderHelper.toDeliveryDtoList(deliveryDtoListResponse);

        // 利用可能配送方法が０件の場合はエラーメッセージを表示する
        if (deliveryDtoList.isEmpty()) {
            addWarnMessage(DeliveryController.MSGCD_NO_DELIVERY_METHOD, null, redirectAttributes, model);
            return false;
        }

        // お届け日取得
        orderHelper.createReceiverDateList(deliveryDtoList, true);

        // 配送方法をセット
        orderDeliveryDto.setDeliveryDtoList(deliveryDtoList);

        return true;
    }

    /**
     * WEB-API連携 配送情報取得
     *
     * @param request 配送情報取得リクエスト
     * @return 配送情報取得レスポンス
     */
    protected OrderDeliveryInformationDtoResponse getOrderDeliveryInformation(OrderDeliveryInformationRequest request) {

        LOGGER.info("配送情報取得メソッド呼出（開始）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        OrderDeliveryInformationDtoResponse response = null;
        try {
            // WEB-API呼び出し
            response = orderApi.getOrderDeliveryInformation(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        LOGGER.info("配送情報取得メソッド呼出（終了）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        return response;
    }

    /**
     * WEB-API 商品在庫数取得
     *
     * @param orderGoodsEntityList 受注商品リスト
     * @return 商品在庫数MAP
     */
    protected Map<String, WebApiGetStockResponseDetailDto> getStockMap(List<OrderGoodsEntity> orderGoodsEntityList) {
        OrderGetStockMapResponse orderGetStockMapResponse = null;
        try {
            // WEB-API呼び出し
            orderGetStockMapResponse = orderApi.getOrderStock(
                            orderHelper.toOrderGetStockRequest(orderUtility.getGoodsCodeList(orderGoodsEntityList),
                                                               orderUtility.getQuantityList(orderGoodsEntityList),
                                                               commonInfoUtility.getCustomerNo(getCommonInfo())
                                                              ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return orderHelper.toWebApiGetStockResponseDetailDtoMap(orderGetStockMapResponse);
    }

    /**
     * WEB-API 取りおき情報取得
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     * @return 取りおき情報MAP
     */
    protected Map<String, WebApiGetReserveResponseDetailDto> getReserveMap(OrderDeliveryDto orderDeliveryDto) {
        OrderGetReserveRequest orderGetReserveRequest = new OrderGetReserveRequest();
        orderGetReserveRequest.setOrderDeliveryDto(orderHelper.toOrderDeliveryDtoRequest(orderDeliveryDto));
        orderGetReserveRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        OrderGetReserveMapResponse orderGetReserveMapResponse = null;
        try {
            // WEB-API呼び出し
            orderGetReserveMapResponse = orderApi.getOrderReserve(orderGetReserveRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return orderHelper.toWebApiGetReserveResponseDetailDtoMap(orderGetReserveMapResponse);
    }

    /**  ***********　Delivery関連 共通部品 End　*********** **/
    /**  ***********　Payment関連 共通部品 Start　*********** **/

    /**
     * お支払方法選択画面：初期表示時の共通処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param paymentModel       お支払い方法選択画面Model
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return お支払方法選択画面
     */
    protected void doLoadPayment(OrderCommonModel orderCommonModel,
                                 PaymentModel paymentModel,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        // 最新の情報を取得して利用可能お支払い方法を取得するため料金を再計算する
        BillPriceCalculateResponse billPriceCalculateResponse = null;
        try {
            billPriceCalculateResponse = orderApi.registBillPriceCalculate(
                            orderHelper.toBillPriceCalculateRequest(orderCommonModel.getReceiveOrderDto()));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        orderCommonModel.setReceiveOrderDto(
                        orderHelper.toReceiveOrderDto(billPriceCalculateResponse.getReceiveOrderDto()));

        // 適用後受注情報の初期化
        // 初期値は受注情報のコピーとし、「ご注文内容確認」、または「変更」ボタン押下時はこの値を受注情報として渡す。
        paymentModel.setDispReceiveOrderDto(CopyUtil.deepCopy(orderCommonModel.getReceiveOrderDto()));

        // 受注金額0円判定フラグを画面間保持用（確認画面でセットされる）からコピーする
        paymentModel.setOrderPriceZero(orderCommonModel.isOrderPriceZero());

        // カード情報を取得
        // Paygent Customization from here
        paymentModel.setComResultDto(getRegistedCardInfo(orderCommonModel, model, redirectAttributes));
        // Paygent Customization to here

        // 注文内容確認画面から遷移してきた場合に、前回の設定値を表示しない為に初期化
        paymentModel.setPaymentModelItems(new ArrayList<>());

        // 決済方法ラジオボタン作成
        createSettlementMethodRadio(orderCommonModel, paymentModel, redirectAttributes, model);

        // 受注情報エンティティからModelに変換
        paymentHelper.toPageForLoad(orderCommonModel, paymentModel, orderCommonModel.getReceiveOrderDto());

    }

    /**
     * ペイジェントと通信しカード情報を取得する
     * <pre>
     * 確認画面からの遷移かつ別クレジット選択時も保持カードを取得するよう修正
     * </pre>
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return ComResultDto 決済通信結果返却用Dto
     */
    private ComResultDto getRegistedCardInfo(OrderCommonModel orderCommonModel,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {

        // 会員SEQ取得
        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

        // 会員情報取得
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByMemberInfoSeq(memberInfoSeq);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        MemberInfoEntity memberEntity = null;
        try {
            memberEntity = orderHelper.toMemberInfoEntity(memberInfoEntityResponse);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        // Modelにセット
        orderCommonModel.setMemberInfoEntity(memberEntity);

        // PDR Migrate Customization from here
        // クレジットカード情報保持種別が未登録なら処理終了 削除
        // サブシステム側との連携のため、
        // 保持種別に関わらずカード情報を取得する。

        // 確認画面からの遷移かつ、別のクレジットでのお支払いの場合、処理終了 削除
        // 常に保持しているカード情報は取得する。ため
        // PDR Migrate Customization to here

        // Paygent Customization from here
        ComResultDto comResultDto;
        try {
            // カード情報照会
            CardInfoRequest cardInfoRequest = new CardInfoRequest();
            cardInfoRequest.setSessionId(getCommonInfo().getCommonInfoBase().getSessionId());
            cardInfoRequest.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());
            CardInfoResponse cardInfoResponse = null;
            try {
                cardInfoResponse = memberInfoApi.getCardInfoByMemberInfoSeq(memberInfoSeq, cardInfoRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            comResultDto = orderHelper.toComResultDto(cardInfoResponse);
        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            addWarnMessage(ComTransactionUtility.MSGCD_CREDIT_CARD_INFO_GET_ERROR, null, redirectAttributes, model);
            return null;
        }

        if (comTransactionUtility.isErrorOccurred(comResultDto)) {
            CheckMessageDto messageDto = comResultDto.getMessageList().get(0);
            addWarnMessage(messageDto.getMessageId(), messageDto.getArgs(), redirectAttributes, model);
            return null;
        }

        return comResultDto;
        // Paygent Customization to here
    }

    /**
     * 決済方法ラジオボタン作成
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param paymentModel       お支払い方法選択画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    private void createSettlementMethodRadio(OrderCommonModel orderCommonModel,
                                             PaymentModel paymentModel,
                                             RedirectAttributes redirectAttributes,
                                             Model model) {
        // PDR Migrate Customization from here
        // 決済方法選択可能リスト取得サービス実行
        // 受注金額によるチェックをここでは行わないため全ての支払方法を取得
        // また、商品ごとの支払方法可否は後にチェックを行う。
        // 支払方法可否によってラジオボタンを非活性にし表示自体は行うため、ここでの選別は行わない。
        SettlementMethodSelectListGetRequest settlementMethodSelectListGetRequest =
                        orderHelper.toSettlementMethodSelectListGetRequest(paymentModel.getDispReceiveOrderDto(), true);
        SettlementMethodSelectListResponse settlementMethodSelectListResponse = null;
        try {
            settlementMethodSelectListResponse =
                            orderApi.getSettlementMethodSelectList(settlementMethodSelectListGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        paymentModel.setDispReceiveOrderDto(
                        orderHelper.toReceiveOrderDto(settlementMethodSelectListResponse.getReceiveOrderDto()));
        List<SettlementDto> settlementDtoList = orderHelper.toSettlementDtoResponseList(
                        settlementMethodSelectListResponse.getSettlementDtoList());
        // PDR Migrate Customization to here

        // クーポン適用後の場合、決済方法リストを退避する
        List<PaymentModelItem> bkPaymentModelItems = null;
        if (paymentModel.getPaymentModelItems() != null) {
            bkPaymentModelItems = paymentModel.getPaymentModelItems();
        }

        paymentModel.setPaymentModelItems(new ArrayList<>());
        paymentModel.setSettlementDtoMap(new HashMap<>());
        PaymentModelItem paymentModelItem;

        Integer freeSettlementSeq = orderUtility.getFreeSettlementMethodSeq();

        for (SettlementDto settlementDto : settlementDtoList) {

            // PDR Migrate Customization from here
            // 利用不可決済の場合、出力しない 削除
            // ここでのチェックは行わないため
            // PDR Migrate Customization to here

            // 決済Dtoをマップに設定
            paymentModel.getSettlementDtoMap()
                        .put(settlementDto.getSettlementDetailsDto().getSettlementMethodSeq().toString(),
                             settlementDto
                            );

            // 無料決済は出力しない
            if (freeSettlementSeq.equals(settlementDto.getSettlementDetailsDto().getSettlementMethodSeq())) {
                continue;
            }

            // PDR Migrate Customization from here
            // 月締請求の場合
            if (HTypeSettlementMethodType.MONTHLY_BILLING.equals(
                            settlementDto.getSettlementDetailsDto().getSettlementMethodType())) {
                if (!HTypeMonthlyPayUseFlag.ON.equals(
                                (orderCommonModel.getMemberInfoEntity().getMonthlyPayUseFlag()))) {
                    // 使用不可 の場合は非表示とする。
                    continue;
                }
            }
            // PDR Migrate Customization to here

            // 決済方法詳細DTO取得
            SettlementDetailsDto settlementDetailsDto = settlementDto.getSettlementDetailsDto();

            // 決済種別取得
            HTypeSettlementMethodType settlementMethodType = settlementDetailsDto.getSettlementMethodType();

            // 決済方法選択画面表示用Dtoを作成
            paymentModelItem = new PaymentModelItem();

            // PDR Migrate Customization from here
            // 各支払方法の使用可否と、商品ごと利用可否を元に利用可能支払方法を制御する。
            // 利用不可のものはラジオボタンを非活性とする。
            paymentModelItem.setUsabilityFlag(
                            checkSettlementMethod(orderCommonModel.getMemberInfoEntity(), settlementDetailsDto));

            // 決済方法名設定（決済方法表示名PCを使用）
            paymentModelItem.setSettlementMethodLabel(settlementDetailsDto.getSettlementMethodDisplayNamePC());
            // PDR Migrate Customization to here

            // 決済方法SEQ設定
            paymentModelItem.setSettlementMethodValue(settlementDetailsDto.getSettlementMethodSeq().toString());
            // 決済タイプ設定
            paymentModelItem.setSettlementMethodType(settlementDetailsDto.getSettlementMethodType());
            // 決済タイプ名設定
            paymentModelItem.setSettlementMethodTypeName(settlementDetailsDto.getSettlementMethodType().toString());
            // 請求タイプ設定
            paymentModelItem.setBillType(settlementDetailsDto.getBillType());
            // 決済方法説明文PC設定
            paymentModelItem.setSettlementNotePC(settlementDetailsDto.getSettlementNotePC());
            // ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ設定
            paymentModelItem.setEnableInstallment(settlementDetailsDto.getEnableInstallment());
            // ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ設定
            paymentModelItem.setEnableRevolving(settlementDetailsDto.getEnableRevolving());

            // 決済方法リストに追加
            paymentModel.getPaymentModelItems().add(paymentModelItem);

            if (settlementMethodType.equals(HTypeSettlementMethodType.CREDIT)) {
                // 決済方法がクレジットの場合、お支払い方法のを初期値(一括)を設定する
                paymentModelItem.setPaymentType(HTypePaymentType.SINGLE.getValue());
                // PDR Migrate Customization from here
                paymentModelItem.setDispPaymentType(HTypePaymentType.SINGLE.getLabel());
                // PDR Migrate Customization to here

                // 有効期限：月のプルダウンを取得
                Map<String, String> expirationDateMonthItems = EnumTypeUtil.getEnumMap(HTypeExpirationDateMonth.class);
                // 有効期限（年）のプルダウン作成
                Map<String, String> expirationDateYearItems = orderUtility.createExpirationDateYearItems();
                // クレジット情報作成
                paymentModel.setExpirationDateMonthItems(expirationDateMonthItems);
                paymentModel.setExpirationDateYearItems(expirationDateYearItems);

                // PDR Migrate Customization from here
                // カード登録情報がある場合、画面に登録済みのカード情報を表示する。
                if (paymentModel.getComResultDto() != null) {
                    paymentHelper.setupRegistedCardInfo(orderCommonModel, paymentModel, paymentModelItem);
                }
                // クレジットカード会社リストを作成
                Map<String, String> cardBrandList =
                                orderUtility.createCardBrandItemList(settlementDto.getCardBrandEntityList());
                paymentModel.setCardBrandItems(cardBrandList);

                // この決済で登録済みのカードを使用する判定をここでは行えないため、falseを設定
                paymentModel.getDispReceiveOrderDto().getOrderTempDto().setUseRegistCardFlg(false);
                // 登録されたカードがあるかどうかの判定は不要なため trueを設定
                paymentModel.getDispReceiveOrderDto().getOrderTempDto().setRegistCredit(true);

                // カード番号
                paymentModelItem.setCardNumber(null);
                // 有効期限：月
                paymentModelItem.setExpirationDateMonth(null);
                // 有効期限：年
                paymentModelItem.setExpirationDateYear(null);

                // カード情報登録状態フラグ
                if (paymentModelItem.isRegistedCard()) {
                    paymentModel.setDisplayCredit(true);
                }
                // PDR Migrate Customization to here

            } else if (settlementMethodType.equals(HTypeSettlementMethodType.CONVENIENCE)) {
                // 決済タイプがコンビニのものが存在した場合、店舗のプルダウンを作成。
                paymentModel.setConvenienceItems(
                                orderUtility.createConvenienceItems(settlementDto.getConvenienceEntityList()));
            }
            // 選択候補が1つの場合は、ラジオボタンをチェック済みにしておく。
            if (settlementDtoList.size() == 1) {
                paymentModel.setSettlementMethod(settlementDetailsDto.getSettlementMethodSeq().toString());
            }
        }

        // PDR Migrate Customization from here
        // 利用可能な決済方法が１つもないときエラーメッセージを表示する
        if (paymentModel.getSettlementDtoMap().isEmpty() || CollectionUtil.isEmpty(
                        paymentModel.getPaymentModelItems())) {
            addMessage(PaymentController.MSGCD_NO_SETTLEMENT_METHOD, redirectAttributes, model);
        } else {
            // 使用可の決済が1つもない場合
            boolean usabilityFlagAllOff = false;
            for (PaymentModelItem item : paymentModel.getPaymentModelItems()) {
                if (item.isUsabilityFlag()) {
                    usabilityFlagAllOff = true;
                    break;
                }
            }
            if (!usabilityFlagAllOff) {
                addMessage(PaymentController.MSGCD_NO_SETTLEMENT_METHOD, redirectAttributes, model);
            }
        }
        // PDR Migrate Customization to here

        // 退避した決済方法リストが有る場合、決済方法SEQが一致する情報に入力情報を設定する
        paymentHelper.restorePaymentPageItem(paymentModel.getPaymentModelItems(), bkPaymentModelItems);
    }

    /**
     * 会員ごとに設定されている使用可否フラグをチェックし
     * 利用可能な支払方法かどうか判定します。
     *
     * <pre>
     * 以下の場合は利用可 それ以外は利用不可を返却する。
     * ・各決済タイプに対応する使用可否フラグがtrue(使用可)
     * 当てはまる支払方法使用可否フラグが存在しない場合は全てfalseを返却する。
     *
     * ・クレジット決済の場合、クレジット決済使用可否
     * ・コンビニ・郵便振込の場合、コンビニ郵便使用可否
     * ・代金引換の場合、代金引換使用可否
     * ・口座自動引落の場合、口座自動引落使用可否
     * ・月締請求の場合、別処理で使用可否判定済みのため全てtrueで返却
     * </pre>
     *
     * @param memberInfoEntity     会員情報エンティティ
     * @param settlementDetailsDto 決済詳細DTO
     * @return true=利用可、false=利用不可
     */
    private boolean checkSettlementMethod(MemberInfoEntity memberInfoEntity,
                                          SettlementDetailsDto settlementDetailsDto) {
        HTypeSettlementMethodType settlementMethodType = settlementDetailsDto.getSettlementMethodType();

        if (HTypeSettlementMethodType.CREDIT.equals(settlementMethodType)) {
            // クレジット決済
            if (HTypeCreditPaymentUseFlag.ON.equals(memberInfoEntity.getCreditPaymentUseFlag())) {
                // 使用可能
                return true;
            }
        } else if (HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER.equals(settlementMethodType)) {
            // PDR Migrate Customization from here
            // コンビニ・郵便振込
            if (HTypeTransferPaymentUseFlag.ON.equals(memberInfoEntity.getTransferPaymentUseFlag())
                || HTypeTransferPaymentUseFlag.FIRST_TIME.equals(memberInfoEntity.getTransferPaymentUseFlag())) {
                // 使用可能もしくは使用可能(初回)
                return true;
            }
            // PDR Migrate Customization to here

        } else if (HTypeSettlementMethodType.RECEIPT_PAYMENT.equals(settlementMethodType)) {
            // 代金引換
            if (HTypeCashDeliveryUseFlag.ON.equals(memberInfoEntity.getCashDeliveryUseFlag())) {
                // 使用可能
                return true;
            }
        } else if (HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL.equals(settlementMethodType)) {
            // 口座自動引落
            if (HTypeDirectDebitUseFlag.ON.equals(memberInfoEntity.getDirectDebitUseFlag())) {
                // 使用可能
                return true;
            }

        } else if (HTypeSettlementMethodType.MONTHLY_BILLING.equals(settlementMethodType)) {
            // 月締請求
            // 使用可能
            return true;
        }

        // 使用不可
        return false;
    }

    /**
     * 「お支払方法を変更する」ボタン押下時の共通処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param paymentModel       お支払い方法選択画面Model
     */
    protected void doPaymentConfirm(OrderCommonModel orderCommonModel, PaymentModel paymentModel) {

        // 選択決済の不正操作チェック
        checkOperationSettlementMethod(orderCommonModel, paymentModel);

        // クーポン変更されたので受注情報にクーポン適用後受注情報を反映
        // 当画面を再度読み込むまでは、最新の受注情報と同期を取っておくため、参照渡しする
        // ※値渡しに変更しないこと。ブラウザバック時に受注情報が古い情報で上書きされる
        orderCommonModel.setReceiveOrderDto(paymentModel.getDispReceiveOrderDto());

        if (HTypeSettlementMethodType.CREDIT.equals(paymentModel.getSettlementDtoMap()
                                                                .get(paymentModel.getSettlementMethod())
                                                                .getSettlementDetailsDto()
                                                                .getSettlementMethodType())) {
            // カード決済の場合、入力チェック
            checkCredit(paymentModel);
        }

        // ReceiveOrderDto の内容を更新する
        updateReceiveOrderDto(orderCommonModel, paymentModel);

    }

    /**
     * 不正操作が無いかチェックする
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel お支払い方法選択画面Model
     */
    private void checkOperationSettlementMethod(OrderCommonModel orderCommonModel, PaymentModel paymentModel) {
        // マップに存在しない決済方法が指定されている
        if (!paymentModel.getSettlementDtoMap().containsKey(paymentModel.getSettlementMethod())) {
            throwMessage(PaymentController.MSGCD_SELECT_NO_SETTLEMENT_METHOD);
        }
    }

    /**
     * クレジット決済の内容をチェックする
     * <pre>
     * 新しいカード選択時の保持カード件数チェックを追加
     * 保持カード選択時の不正操作チェックを追加
     * </pre>
     *
     * @param paymentModel         お支払い方法選択画面Model
     */
    private void checkCredit(PaymentModel paymentModel) {

        // 設定されている支払区分以外の値が設定されている時
        // 支払区分は settlementselectPage.selectHTypePaymentType にて設定
        // ここが設定されていない == 該当の支払区分以外のデータが渡っている
        if (paymentModel.selectHTypePaymentType() == null) {
            throwMessage(PaymentController.MSGCD_ILLEGAL_OPERATION_PAYMENT);
        }

        // クレジット決済をチェック
        for (PaymentModelItem paymentModelItem : paymentModel.getPaymentModelItems()) {
            if (paymentModel.getSettlementMethod().equals(paymentModelItem.getSettlementMethodValue())) {

                // PDR Migrate Customization from here
                if (HTypeCreditCardType.NEW_CARD.getValue().equals(paymentModelItem.getCreditCardType())) {
                    List<PaymentModelRegistedCardItem> registedCardItemList = paymentModelItem.getRegistedCardItems();

                    // 保持カードが最大件数登録されている場合
                    if (!CollectionUtil.isEmpty(registedCardItemList)
                        && registedCardItemList.size() >= PaymentController.CREDIT_MAX_REGISTED_COUNT) {
                        String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
                        throwMessage(PaymentController.MSGCD_CREDIT_MAX_REGISTED, new String[] {appComplementUrl});
                    }
                }

                // 保持カード選択時の不正操作チェック
                checkRegistedCreditCard(paymentModelItem);
                // 新しいカード選択時の不正操作チェック
                checkNewCreditCardBrand(paymentModel, paymentModelItem);
                // PDR Migrate Customization to here

                break;
            }
        }
    }

    /**
     * ReceiveOrderDto の内容を更新する
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param paymentModel  お支払い方法選択画面Model
     */
    private void updateReceiveOrderDto(OrderCommonModel orderCommonModel, PaymentModel paymentModel) {
        // Modelから受注Dtoに変換
        orderCommonModel.setReceiveOrderDto(paymentHelper.toReceiveOrderDtoForConfirm(orderCommonModel, paymentModel));
        orderCommonModel.getReceiveOrderDto()
                        .setSettlementMethodEntity(paymentHelper.toSettlementMethodEntityForConfirm(
                                        paymentModel.getSettlementDtoMap()
                                                    .get(paymentModel.getSettlementMethod())
                                                    .getSettlementDetailsDto()));
    }

    /**
     * 選択された保持カードの値が不正に操作されていないかチェックを行います。
     *
     * @param paymentModelItem 決済方法選択画面アイテム
     */
    private void checkRegistedCreditCard(PaymentModelItem paymentModelItem) {

        if (HTypeCreditCardType.NEW_CARD.getValue().equals(paymentModelItem.getCreditCardType())) {
            return;
        }

        String customerCardId = paymentModelItem.getRegistCardSelect();
        for (PaymentModelRegistedCardItem registedCardItem : paymentModelItem.getRegistedCardItems()) {
            // 登録されたカードの顧客カードIDが存在する場合は処理終了
            if (customerCardId.equals(registedCardItem.getCustomerCardId())) {
                return;
            }
        }
        // 不正な値が指定されているのでエラーとする。
        throwMessage(PaymentController.MSGCD_ILLEGAL_OPERATION_PAYMENT);
    }

    /**
     * 選択された新しいカードのクレジットカード会社が不正に操作されていないかチェックを行います。
     *
     * @param paymentModel     お支払い方法選択画面Model
     * @param paymentModelItem 決済方法選択画面アイテム
     */
    private void checkNewCreditCardBrand(PaymentModel paymentModel, PaymentModelItem paymentModelItem) {

        if (HTypeCreditCardType.REGISTERED_CARD.getValue().equals(paymentModelItem.getCreditCardType())) {
            return;
        }

        // 登録されたカードの顧客カードIDが存在する場合は処理終了
        if (paymentModel.getCardBrandItems().containsValue(paymentModelItem.getCardBrand())) {
            return;
        }
        // 不正な値が指定されているのでエラーとする。
        throwMessage(PaymentController.MSGCD_ILLEGAL_OPERATION_PAYMENT);
    }

    /**  ***********　Payment関連 共通部品 End　*********** **/

}
// 2023-renew No14 to here
