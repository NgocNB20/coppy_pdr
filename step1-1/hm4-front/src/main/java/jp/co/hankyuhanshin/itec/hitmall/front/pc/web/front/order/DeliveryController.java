/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.AbstractOrderController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.DeliveryValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.DeliveryGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.StockUtility;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 注文_配送方法選択 Controller
 *
 * @author ota-s5
 */
@RequestMapping("/order")
@Controller
@SessionAttributes({"orderCommonModel", "deliveryModel"})
// 2023-renew No14 from here
public class DeliveryController extends AbstractOrderController {

    /** メッセージコード：利用可能配送方法が0件 */
    public static final String MSGCD_NO_DELIVERY_METHOD = "AOX000304";

    // PDR Migrate Customization from here
    /** メッセージコード：お届け予定日プルダウン不正操作エラー */
    public static final String MSGCD_ILLEGAL_RESERVEDELIVERYCOUNT = "PDR-0028-005-A-E";

    /** メッセージコード：お届け時間帯プルダウン不正操作エラー */
    public static final String MSGCD_ILLEGAL_RESERVEDELIVERYDATE = "PDR-0028-006-A-E";

    /** メッセージコード：不正なお届け時間帯が選択された場合 */
    public static final String MSGCD_INVALID_SELECTION_TIMEZONE = "PDR-0226-012-A-E";

    /** メッセージコード：任意の入力エラー（今すぐお届け分） */
    public static final String MSGCD_INPUT_ERR_ANY_NOW = "PDR-0028-012-A-E";

    /** メッセージコード：今すぐお届け分数量が未入力 */
    public static final String MSGCD_INPUT_ERR_COUNT_NULL = "PDR-0028-013-A-E";

    /** メッセージコード：入荷次第お届け振り分け数に変動が発生した場合(配送方法設定画面戻り遷移時表示用) */
    public static final String MSGCD_STOCK_ALLOCATION_BACK_PAGE = "PDR-0030-001-A-W";
    // PDR Migrate Customization to here

    /**
     * 配送方法設定画面：「決済方法を選択する」ボタン押下処理 Validator
     */
    private final DeliveryValidator deliveryValidator;

    /**
     * 配送方法選択 Helper
     */
    private final DeliveryHelper deliveryHelper;

    /**
     * コンストラクタ
     */
    @Autowired
    public DeliveryController(CouponHelper couponHelper,
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
                              DeliveryValidator deliveryValidator,
                              DeliveryHelper deliveryHelper) {
        super(couponHelper, orderHelper, paymentHelper, orderUtility, conversionUtility, commonInfoUtility,
              couponUtility, comTransactionUtility, dateUtility, stockUtility, goodsUtility, orderPendingUtility,
              orderApi, memberInfoApi, webapiApi, shopApi
             );
        this.deliveryValidator = deliveryValidator;
        this.deliveryHelper = deliveryHelper;
    }

    @InitBinder
    public void initBinder(WebDataBinder error) {
        // PDR Migrate Customization from here
        // 配送方法設定画面の動的バリデータをセット
        error.addValidators(deliveryValidator);
        // PDR Migrate Customization to here
    }

    /**
     * 配送方法設定画面：初期処理
     *
     * <pre>
     *  配送情報取得、商品の振り分けの処理追加
     *  </pre>
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param deliveryModel      配送方法選択画面Model
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request            リクエスト
     * @return 配送方法設定画面
     */
    @GetMapping(value = {"/delivery", "/delivery.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "order/delivery")
    public String doLoadDelivery(OrderCommonModel orderCommonModel,
                                 DeliveryModel deliveryModel,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        // PDR Migrate Customization from here
        // ブラウザバックチェック用トークンを生成
        setToken(orderCommonModel);
        // PDR Migrate Customization to here

        // 2023-renew No14 from here
        // セールde予約画面から遷移された場合
        if (model.containsAttribute(ORDER_RESERVE_ITEM)) {
            // セールde予約画面遷移前に生成した当画面データに対して、引継ぎデータ分を反映する
            // ※今すぐお届け分の入力値を失わないよう、当画面を一から再表示はしない
            deliveryHelper.toPageReserveDeliveryItemsForReserveItem(orderCommonModel, deliveryModel,
                                                                    (ReserveItem) model.getAttribute(ORDER_RESERVE_ITEM)
                                                                   );
        }
        // 上記以外から遷移された場合
        else {
            // モデル初期化
            clearModel(DeliveryModel.class, deliveryModel, model);

            // 動的コンポーネント作成
            initComponents(orderCommonModel, deliveryModel, redirectAttributes, model);
        }
        // 2023-renew No14 to here

        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }

        return "order/delivery";
    }

    // 2023-renew No14 from here

    /**
     * 「変更」ボタン押下処理（セールde予約）
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法設定画面ページ
     * @param error エラー
     * @param model モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return
     */
    @PostMapping(value = {"/delivery", "/delivery.html"}, params = "doReserveChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/delivery")
    public String doReserveChange(OrderCommonModel orderCommonModel,
                                  @Validated(DeliveryGroup.class) DeliveryModel deliveryModel,
                                  BindingResult error,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        if (error.hasErrors()) {
            return "order/delivery";
        }

        // セールde予約情報Itemを作成（注文_セールde予約画面へのデータ渡し用）
        redirectAttributes.addFlashAttribute(
                        ORDER_RESERVE_ITEM, deliveryHelper.toReserveItem(orderCommonModel, deliveryModel));

        // 注文_セールde予約画面へ遷移
        return "redirect:/order/reserve.html?gcd=" + deliveryModel.getGoodsCodeIndex();
    }

    // 2023-renew No14 to here

    /**
     * 「お届け内容を変更する」ボタン押下処理
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     * @param error エラー
     * @param model モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request リクエスト
     * @return お支払い方法選択画面
     */
    @PostMapping(value = {"/delivery", "/delivery.html"}, params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/delivery")
    public String doConfirm(OrderCommonModel orderCommonModel,
                            @Validated(DeliveryGroup.class) DeliveryModel deliveryModel,
                            BindingResult error,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        if (error.hasErrors()) {
            return "order/delivery";
        }

        // PDR Migrate Customization from here
        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }

        checkInput(orderCommonModel, deliveryModel, redirectAttributes, model);

        // Modelから受注配送Dtoに変換
        deliveryHelper.toOrderDeliveryMethodForSettlementMethod(orderCommonModel, deliveryModel);

        // 商品在庫数取得（WEB-API連携）
        Map<String, WebApiGetStockResponseDetailDto> stockMap = getStockMap(orderCommonModel.getReceiveOrderDto()
                                                                                            .getOrderDeliveryDto()
                                                                                            .getOrderGoodsEntityList());
        // 購入制限・在庫不足のエラーチェック
        List<CheckMessageDto> checkMessageList = new ArrayList<>();
        stockUtility.checkStockByDelivery(orderCommonModel.getReceiveOrderDto(), checkMessageList, stockMap);

        // 購入制限チェック・在庫チェックで発生したエラーを表示
        addErrorInfo(checkMessageList, redirectAttributes, model, false);
        if (hasErrorMessage()) {
            throwMessage();
        }
        // PDR Migrate Customization to here

        // 配送方法マスタを再作成する
        createDeliveryMethodMaster(orderCommonModel);

        // 注文確認画面へ遷移
        return "redirect:/order/confirm.html";
    }

    // PDR Migrate Customization from here

    /**
     * 動的コンポーネント作成
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param deliveryModel      配送方法選択画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    private void initComponents(OrderCommonModel orderCommonModel,
                                DeliveryModel deliveryModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        // 配送情報取得（WEB-API連携）
        // ※エラー時は変更ボタン非表示
        deliveryModel.setHideNextPayment(
                        !executeWebApiGetDeliveryInformation(orderCommonModel, redirectAttributes, model));
        // 配送方法リスト作成
        // ※エラー時がエラーメッセージ以外は非表示
        deliveryModel.setExistDeliveryMethod(createDeliveryMethodList(orderCommonModel, redirectAttributes, model));

        // Web-APIから受注履歴情報を取得
        LOGGER.info("注文履歴（過去1年）取得メソッド呼出（開始）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        WebApiGetPreShipmentOrderHistoryResponseDto webApiResponseDto =
                        executeWebApiGetPreShipmentOrderHistoryAggregate(orderCommonModel);
        LOGGER.info("注文履歴（過去1年）取得メソッド呼出（終了）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // 今すぐお届け 取りおき 入荷次第数設定
        LOGGER.info("今すぐお届け 取りおき 入荷次第数設定（開始）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        setDeliveryCount(orderCommonModel, deliveryModel, redirectAttributes, model);
        LOGGER.info("今すぐお届け 取りおき 入荷次第数設定（終了）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // ページに変換
        deliveryHelper.toPageForLoad(orderCommonModel, deliveryModel,
                                     orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto(),
                                     orderCommonModel.getReceiveOrderDto().getMasterDto().getGoodsMaster(),
                                     webApiResponseDto
                                    );
    }

    /**
     * Web-APIを実行し、レスポンス情報を返す<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @return 注文履歴（過去1年）取得Web-APIレスポンスDto
     */
    private WebApiGetPreShipmentOrderHistoryResponseDto executeWebApiGetPreShipmentOrderHistoryAggregate(
                    OrderCommonModel orderCommonModel) {
        // Web-API実行
        LOGGER.info("注文履歴（過去1年）取得Web-API呼出（開始）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        WebApiGetPreShipmentOrderHistoryAggregateRequest webApiGetPreShipmentOrderHistoryAggregateRequest =
                        new WebApiGetPreShipmentOrderHistoryAggregateRequest();
        webApiGetPreShipmentOrderHistoryAggregateRequest.setCustomerNo(
                        orderCommonModel.getMemberInfoEntity().getCustomerNo());
        WebApiGetPreShipmentOrderHistoryAggregateResponse webApiGetPreShipmentOrderHistoryAggregateResponse = null;
        try {
            webApiGetPreShipmentOrderHistoryAggregateResponse = webapiApi.getPreShipmentOrderHistoryAeggregate(
                            webApiGetPreShipmentOrderHistoryAggregateRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        WebApiGetPreShipmentOrderHistoryResponseDto responseDto =
                        orderHelper.toWebApiGetPreShipmentOrderHistoryResponseDto(
                                        webApiGetPreShipmentOrderHistoryAggregateResponse);
        LOGGER.info("注文履歴（過去1年）取得Web-API呼出（終了）：" + dateUtility.format(
                        dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // ログ出力 ※WEB-APIはログレベルの設定によってログが出力されないため、ここで出力
        if (null == responseDto) {
            LOGGER.info("注文履歴（過去1年）取得Web-APIレスポンス　件数:null");
        } else {
            if (null != responseDto.getResult()) {
                if (StringUtil.isNotEmpty(responseDto.getResult().getStatus())) {
                    LOGGER.info("RESULT ステータス:" + responseDto.getResult().getStatus());
                } else {
                    LOGGER.info("RESULT ステータス:なし");
                }
                if (StringUtil.isNotEmpty(responseDto.getResult().getMessage())) {
                    LOGGER.info("RESULT メッセージ:" + responseDto.getResult().getMessage());
                } else {
                    LOGGER.info("RESULT メッセージ:なし");
                }
            } else {
                LOGGER.info("RESULT:無し");
            }

            LOGGER.info("注文履歴（過去1年）取得Web-APIレスポンス　件数:" + responseDto.info.size());
            for (WebApiGetPreShipmentOrderHistoryResponseDetailDto resDto : responseDto.info) {
                if (null != resDto.getOrderNo()) {
                    LOGGER.info("注文番号:" + resDto.getOrderNo());
                } else {
                    LOGGER.info("注文番号:なし");
                }
                if (null != resDto.getGoodsList() && 0 < resDto.getGoodsList().size()) {
                    LOGGER.info("商品情報:件数：" + resDto.getGoodsList().size());
                } else {
                    LOGGER.info("商品情報:なし");
                }
            }
        }
        return responseDto;
    }

    /**
     * 今すぐお届け 取りおき 入荷次第お届け数を設定します。
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param deliveryModel      配送方法選択画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    private void setDeliveryCount(OrderCommonModel orderCommonModel,
                                  DeliveryModel deliveryModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        ReceiveOrderDto receiveOrderDto = orderCommonModel.getReceiveOrderDto();
        List<CheckMessageDto> checkMessageList = new ArrayList<>();

        // 配送先情報は1件のみのため、常に先頭を取得
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 商品在庫数取得（WEB-API連携）
        LOGGER.info("商品在庫数取得（開始）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        Map<String, WebApiGetStockResponseDetailDto> stockMap = getStockMap(orderDeliveryDto.getOrderGoodsEntityList());
        LOGGER.info("商品在庫数取得（終了）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        if (stockMap == null) {
            // 在庫情報が取得できなかった場合
            deliveryModel.setHideNextPayment(true);
            addWarnMessage(MSGCD_SYSTEM_ERR, null, redirectAttributes, model);
            return;
        }

        // 取りおき情報取得（WEB-API連携）
        LOGGER.info("取りおき情報MAP作成（開始）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        Map<String, WebApiGetReserveResponseDetailDto> reserveDtoMap = getReserveMap(orderDeliveryDto);
        LOGGER.info("取りおき情報MAP作成（終了）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));

        // 2023-renew No14 from here
        // 今すぐお届け 取りおき 入荷次第 振り分け処理
        stockUtility.allocationDelivery(receiveOrderDto, stockMap, checkMessageList, MSGCD_STOCK_ALLOCATION_BACK_PAGE);
        // 2023-renew No14 to here

        // 在庫不足のエラーチェック
        stockUtility.checkStockByDelivery(receiveOrderDto, checkMessageList, stockMap);

        // メッセージ内の商品コードが心意気商品の場合、元商品の商品コードに変換する。
        // 「メッセージ重複を防止するため、商品コードをキーにチェック」を行っている処理がメッセージ引数：１に
        // 商品コードが設定されている前提のためメッセージ引数：１を対象に変換。
        // クラス名：StockUtility メソッド名：checkAllocationDependingOnReceiptGoods
        for (CheckMessageDto checkMessage : checkMessageList) {
            if (!checkMessage.getArgs()[0].toString().isEmpty()) {
                checkMessage.getArgs()[0] = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                (checkMessage.getArgs()[0].toString()));
            }
        }

        // 発生したエラーを表示
        addErrorInfo(checkMessageList, redirectAttributes, model, true);

    }

    /**
     * 入力チェックを行います。
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param deliveryModel      配送方法選択画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    private void checkInput(OrderCommonModel orderCommonModel,
                            DeliveryModel deliveryModel,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 今すぐお届け、取りおき分が存在している場合
        if (!deliveryModel.isHideDeliveryAssignFlag() && (deliveryModel.isViewDeliveryNowGoods()
                                                          || deliveryModel.isViewReserveDeliveryGoods())) {
            // お届け予定日の入力チェック
            checkDeliveryDate(orderCommonModel, deliveryModel);
            // お届け時間帯の入力チェック
            checkReceiverTimeZone(orderCommonModel, deliveryModel);
        }

        // エラーが追加されている場合は表示
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * お届け予定日の入力チェック<br/>
     * POSTされる値の書き換え対策
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    private void checkDeliveryDate(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {

        Map<String, String> deliveryDateItems = deliveryModel.getDeliveryDateItems();
        String selectedValue = deliveryModel.getDeliveryDate();
        String value;
        for (Map.Entry<String, String> deliveryDateMap : deliveryDateItems.entrySet()) {
            value = deliveryDateMap.getKey();
            if (value.equals(selectedValue)) {
                return;
            }
        }
        addErrorMessage(MSGCD_ILLEGAL_RESERVEDELIVERYCOUNT);

    }

    /**
     * お届け時間帯の入力チェック<br/>
     * POSTされる値の書き換え対策
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param deliveryModel 配送方法選択画面Model
     */
    private void checkReceiverTimeZone(OrderCommonModel orderCommonModel, DeliveryModel deliveryModel) {

        // お届け時間帯リスト
        Map<String, String> receiverTimeItems = new HashMap<>();
        // 選択されたお届け時間帯
        String selectedValue = null;

        if (deliveryModel.isViewReceiverTimeZoneYamato()) {
            // ヤマト 又は 自動設定
            receiverTimeItems = deliveryModel.getReceiverTimeZoneYamatoItems();
            selectedValue = deliveryModel.getReceiverTimeZoneYamato();
        } else if (deliveryModel.isViewReceiverTimeZoneJapanPost()) {
            // 日本郵便
            receiverTimeItems = deliveryModel.getReceiverTimeZoneJapanPostItems();
            selectedValue = deliveryModel.getReceiverTimeZoneJapanPost();
        }

        // チェック用お届け時間帯コードを取得
        List<String> codeList = deliveryModel.getChkReceiverTimeZoneCodes().get(deliveryModel.getDeliveryDate());

        String value;
        for (Map.Entry<String, String> receiverTimeMap : receiverTimeItems.entrySet()) {
            value = receiverTimeMap.getKey();
            if (value.equals(selectedValue)) {
                // 指定可能なお届け時間帯かチェック
                if (codeList != null && !codeList.contains(selectedValue)) {
                    String receiveDateText = "";
                    for (Map.Entry<String, String> receiverDateMap : deliveryModel.getDeliveryDateItems().entrySet()) {
                        if (receiverDateMap.getKey().equals(deliveryModel.getDeliveryDate())) {
                            receiveDateText = receiverDateMap.getValue();
                            break;
                        }
                    }
                    String receiveTimeZoneText = receiverTimeMap.getValue();
                    addErrorMessage(MSGCD_INVALID_SELECTION_TIMEZONE,
                                    new Object[] {receiveDateText, receiveTimeZoneText}
                                   );
                }
                return;
            }
        }
        addErrorMessage(MSGCD_ILLEGAL_RESERVEDELIVERYDATE);
    }

    /**
     * 配送方法マスタを再作成する<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     */
    private void createDeliveryMethodMaster(OrderCommonModel orderCommonModel) {
        // 配送方法マスタを再作成する
        Set<Integer> deliveryMethodSeqSet = new LinkedHashSet<>();
        // PDR Migrate Customization from here
        deliveryMethodSeqSet.add(Integer.parseInt(OrderUtility.DUMMY_DELIVERY_METHOD_SEQ));
        // PDR Migrate Customization to here

        DeliveryMethodDetailsGetRequest deliveryMethodDetailsGetRequest = new DeliveryMethodDetailsGetRequest();
        deliveryMethodDetailsGetRequest.setDeliveryMethodSeqList(new ArrayList<>(deliveryMethodSeqSet));
        DeliveryMethodDetailsDtoMapResponse deliveryMethodDetailsDtoMapResponse = null;
        try {
            deliveryMethodDetailsDtoMapResponse = shopApi.getDeliveryMethodDetails(deliveryMethodDetailsGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        orderCommonModel.getReceiveOrderDto()
                        .getMasterDto()
                        .setDeliveryMethodMaster(orderHelper.toDeliveryMethodDetailsDtoMapResponse(
                                        deliveryMethodDetailsDtoMapResponse.getDeliveryMethodMaster()));
    }

    // PDR Migrate Customization to here

}
// 2023-renew No14 to here
