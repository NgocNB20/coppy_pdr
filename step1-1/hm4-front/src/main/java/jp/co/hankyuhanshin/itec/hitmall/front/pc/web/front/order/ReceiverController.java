/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApprovalFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.AbstractOrderController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.ReceiverDoAddAddressBookGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.ReceiverDoAddReceiverGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.StockUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 注文_お届け先入力 Controller
 *
 * @author ota-s5
 */
@RequestMapping("/order")
@Controller
@SessionAttributes({"orderCommonModel", "receiverModel"})
// 2023-renew No14 from here
public class ReceiverController extends AbstractOrderController {

    /** メッセージコード：都道府県の値改竄 */
    public static final String MSGCD_ILLEGAL_PREFECTURE = "AOX000202";

    // PDR Migrate Customization from here
    /** メッセージコード：お届け先が未設定 */
    public static final String MSGCD_RECEIVER_NO_SET = "PKG-3556-001-A-";
    // PDR Migrate Customization to here

    /**
     * お届け先入力画面 Helper
     */
    private final ReceiverHelper receiverHelper;

    /**
     * コンストラクタ
     */
    @Autowired
    public ReceiverController(CouponHelper couponHelper,
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
                              ReceiverHelper receiverHelper) {
        super(couponHelper, orderHelper, paymentHelper, orderUtility, conversionUtility, commonInfoUtility,
              couponUtility, comTransactionUtility, dateUtility, stockUtility, goodsUtility, orderPendingUtility,
              orderApi, memberInfoApi, webapiApi, shopApi
             );
        this.receiverHelper = receiverHelper;
    }

    /**
     * 届け先入力画面：初期処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param receiverModel      お届け先入力画面Model
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request            リクエスト
     * @return 届け先入力画面 string
     */
    @GetMapping(value = {"/receiver", "receiver.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "order/receiver")
    public String doLoadReceiver(OrderCommonModel orderCommonModel,
                                 ReceiverModel receiverModel,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {

        // モデル初期化
        clearModel(ReceiverModel.class, receiverModel, model);

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        // PDR Migrate Customization from here
        // ブラウザバックチェック用トークンを生成
        setToken(orderCommonModel);
        // PDR Migrate Customization to here

        // 2023-renew No14 from here
        // 受注配送DtoリストからModelに変換
        receiverHelper.toPageForLoad(
                        orderCommonModel, receiverModel, orderCommonModel.getReceiveOrderDto().getOrderDeliveryDto());
        // 2023-renew No14 to here

        // PDR Migrate Customization from here
        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }
        // 描画前処理
        prerenderReceiver(orderCommonModel, receiverModel);
        // 住所録プルダウン
        createAddressBookItems(orderCommonModel, receiverModel);
        // PDR Migrate Customization to here

        return "order/receiver";
    }

    // PDR Migrate Customization from here

    /**
     * 「選択した住所に届ける」ボタン押下処理
     * ※「新しいお届け先に送る」ラジオボタン選択時
     *
     * <pre>
     * 後続でdoConfirm処理を行う。
     * </pre>
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param receiverModel      お届け先入力画面Model
     * @param error              エラーフラグ
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return 遷移先画面
     */
    @PostMapping(value = {"/receiver", "/receiver.html"}, params = "doAddReciever")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/receiver")
    public String doAddReciever(OrderCommonModel orderCommonModel,
                                @Validated(ReceiverDoAddReceiverGroup.class) ReceiverModel receiverModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                HttpServletRequest request) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        if (error.hasErrors()) {
            return "order/receiver";
        }

        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }

        // 都道府県のプルダウンが改竄されていないか
        checkPrefectureReceiver(orderCommonModel, receiverModel);

        return doConfirm(orderCommonModel, receiverModel, error, redirectAttributes, model, request);
    }

    /**
     * 「選択した住所に届ける」ボタン押下処理
     * ※「お届け先リストから選ぶ」ラジオボタン選択時
     *
     * <pre>
     * 後続でdoConfirm処理を行う。
     * </pre>
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param receiverModel      お届け先入力画面Model
     * @param error              エラーフラグ
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return 届け先入力画面
     */
    @PostMapping(value = {"/receiver", "/receiver.html"}, params = "doAddAddressBook")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/receiver")
    public String doAddAddressBook(OrderCommonModel orderCommonModel,
                                   @Validated(ReceiverDoAddAddressBookGroup.class) ReceiverModel receiverModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model,
                                   HttpServletRequest request) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        if (error.hasErrors()) {
            return "order/receiver";
        }

        // PDR Migrate Customization from here
        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }

        // アドレス帳プルダウンが改竄されていないか
        checkReceiverAddressBook(orderCommonModel, receiverModel);
        // PDR Migrate Customization to here

        // アドレス帳プルダウンが選択されている場合
        if (receiverModel.getReceiverAddressBook() != null) {
            // WEB-APIで取得したお届け先の変換処理実行
            receiverHelper.toReceiverListAddAddressBookSelect(orderCommonModel, receiverModel,
                                                              Integer.valueOf(receiverModel.getReceiverAddressBook())
                                                             );
        }

        // 2023-renew No14 from here
        return doConfirm(orderCommonModel, receiverModel, error, redirectAttributes, model, request);
        // 2023-renew No14 to here
    }

    // PDR Migrate Customization to here

    /**
     * 「選択した住所に届ける」ボタン押下処理
     * ※「請求先(ご登録住所)に送る」ラジオボタン選択時 又は それ以外を選択した際の後続処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param receiverModel      お届け先入力画面Model
     * @param error              エラーフラグ
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return 配送方法選択画面 string
     */
    @PostMapping(value = {"/receiver", "/receiver.html"}, params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/receiver")
    public String doConfirm(OrderCommonModel orderCommonModel,
                            ReceiverModel receiverModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            HttpServletRequest request) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        if (error.hasErrors()) {
            return "order/receiver";
        }

        // PDR Migrate Customization from here
        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }

        String receiverSelectType = receiverModel.getReceiverSelectTypeRadio();
        if (ReceiverModel.ADD_TYPE_ADDRESS_BOOK.equals(receiverSelectType)
            && receiverModel.getAddressBookCustomNo() == null) {
            // ラジオボタンで登録済みのお届け先を選択した際、住所が未設定 エラー
            throwMessage(MSGCD_RECEIVER_NO_SET);
        }

        // WEB-API連携 配送情報取得
        executeWebApiGetDeliveryInformationForReceiver(orderCommonModel, receiverModel, redirectAttributes, model);

        // ページから受注配送エンティティリストに変換 今回用メソッドを呼び出しに変更
        orderCommonModel.getReceiveOrderDto()
                        .setOrderDeliveryDto(receiverHelper.toOrderDeliveryDtoForDeliveryMethod(orderCommonModel,
                                                                                                receiverModel,
                                                                                                getCommonInfo()
                                                                                               ));

        // 注文保留チェック
        if (ReceiverModel.ADD_TYPE_RECEIVER.equals(receiverSelectType) || (
                        ReceiverModel.ADD_TYPE_ADDRESS_BOOK.equals(receiverSelectType)
                        && !HTypeApprovalFlag.MAIN_MEMBER.getValue().equals(receiverModel.getApprovalFlag()))) {
            // 以下の場合は注文保留とする。
            // ・新しいお届け先から選ぶ
            // ・登録しているお届け先から選ぶかつ本登録以外の住所録お届け先
            orderPendingUtility.checkPrimaryPending(
                            orderCommonModel.getReceiveOrderDto(), HTypePendingType.NEW_RECEPTION_DESK);

            // 2024/1/29 ログ出力追加
            // 【背景】
            // 承認フラグ＝「1：本登録」のお届け先を指定したにも関わらず、「新規お届け先」として注文保留が実施される事象が発生した。
            // 原因究明のために、お届け先参照APIの一部の情報をログ出力する。
            LOGGER.info("「新規お届け先」として保留区分を設定しました。追加方法：" + receiverSelectType);
            if (receiverModel.getDestinationDto() != null && receiverModel.getDestinationDto().getInfo() != null) {
                for (WebApiGetDestinationResponseDetailDto info : receiverModel.getDestinationDto().getInfo()) {
                    LOGGER.info("【お届け先参照/レスポンスデータ（一部抜粋）】　お届け先顧客番号：" + info.getReceiveCustomerNo() + ", 承認フラグ："
                                + info.getApprovalFlag());
                }
            }

        } else {
            // 上記以外の場合は保留区分を削除
            orderPendingUtility.clearPrimaryPendingSet(
                            orderCommonModel.getReceiveOrderDto(), HTypePendingType.NEW_RECEPTION_DESK);
        }
        // PDR Migrate Customization to here

        // 注文確認画面へ遷移
        return "redirect:/order/confirm.html";
    }

    /**
     * 描画前処理
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel お届け先入力画面Model
     */
    private void prerenderReceiver(OrderCommonModel orderCommonModel, ReceiverModel receiverModel) {
        // 都道府県区分値を取得（北海道：北海道）
        receiverModel.setReceiverPrefectureItems(EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true));
        // PDR Migrate Customization from here
        // 業種
        receiverModel.setReceiverBusinessTypeItems(EnumTypeUtil.getEnumMap(HTypeFrontBusinessType.class));
        // PDR Migrate Customization to here
    }

    /**
     * 住所録プルダウン作成
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel お届け先入力画面Model
     */
    private void createAddressBookItems(OrderCommonModel orderCommonModel, ReceiverModel receiverModel) {
        // PDR Migrate Customization from here
        // WEB-API連携 お届け先参照
        WebApiGetDestinationResponseDto responseGetDestinationDto = executeWebApiGetDestination();
        receiverModel.setDestinationDto(responseGetDestinationDto);
        Map<String, String> addressBookMap = new LinkedHashMap<>();
        if (responseGetDestinationDto.getInfo() != null) {
            for (WebApiGetDestinationResponseDetailDto res : responseGetDestinationDto.getInfo()) {
                // 文字数が39文字を超える場合、37文字 + 「．．」で表示する（デザインの崩れ防止のため）
                String officeName = res.getOfficeName();
                if (officeName.length() > 39) {
                    officeName = StringUtils.substring(officeName, 0, 37) + "．．";
                }
                addressBookMap.put(res.getReceiveCustomerNo().toString(), officeName);
            }
        }
        // PDR Migrate Customization to here
        receiverModel.setReceiverAddressBookItems(addressBookMap);
    }

    /**
     * 都道府県の入力チェック
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param receiverModel お届け先入力画面Model
     */
    private void checkPrefectureReceiver(OrderCommonModel orderCommonModel, ReceiverModel receiverModel) {
        // 都道府県区分値
        if (!EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true)
                         .containsKey(receiverModel.getReceiverPrefecture())) {
            throwMessage(MSGCD_ILLEGAL_PREFECTURE);
        }
    }

    /**
     * アドレス帳の入力チェック
     *
     * @param orderCommonModel 文フロー共通Model
     * @param receiverModel お届け先入力画面Model
     */
    private void checkReceiverAddressBook(OrderCommonModel orderCommonModel, ReceiverModel receiverModel) {
        // アドレス帳区分値
        if (!receiverModel.getReceiverAddressBookItems().containsKey(receiverModel.getReceiverAddressBook())) {
            throwMessage(MSGCD_ILLEGAL_OPERATION);
        }
    }

    // PDR Migrate Customization from here

    /**
     * WEB-API連携 配送情報取得を行います。
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param receiverModel      お届け先入力画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    private void executeWebApiGetDeliveryInformationForReceiver(OrderCommonModel orderCommonModel,
                                                                ReceiverModel receiverModel,
                                                                RedirectAttributes redirectAttributes,
                                                                Model model) {
        // リクエストDTO
        WebApiGetDeliveryInformationRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetDeliveryInformationRequestDto.class);

        // 注文者顧客番号
        Integer customerNo = commonInfoUtility.getCustomerNo(getCommonInfo());
        reqDto.setOrderCustomerNo(customerNo);

        // 配送先顧客番号
        // 郵便番号
        if (ReceiverModel.ADD_TYPE_ADDRESS_BOOK.equals(receiverModel.getReceiverSelectTypeRadio())) {
            // 登録しているお届け先から選ぶ を選択時
            // お届け先の会員の顧客番号(住所録 顧客番号)
            reqDto.setDeliveryCustomerNo(receiverModel.getAddressBookCustomNo());
            // 登録しているお届け先の郵便番号(住所録 郵便番号)
            reqDto.setDeliveryZipcode(receiverModel.getAddressBookZipCode1() + receiverModel.getAddressBookZipCode2());
        } else {
            reqDto.setDeliveryCustomerNo(customerNo);
            if (ReceiverModel.ADD_TYPE_RECEIVER.equals(receiverModel.getReceiverSelectTypeRadio())) {
                // 新しいお届け先選択時
                // 入力された郵便番号を設定
                reqDto.setDeliveryZipcode(receiverModel.getReceiverZipCode1() + receiverModel.getReceiverZipCode2());
            } else {
                // 会員登録されているお届け先選択時
                // 会員郵便番号を設定
                reqDto.setDeliveryZipcode(receiverModel.getOrderZipCode1() + receiverModel.getOrderZipCode2());
            }
        }

        // 配送情報取得（WEB-API連携）
        OrderDeliveryInformationDtoResponse response = getOrderDeliveryInformation(
                        orderHelper.toOrderDeliveryInformationRequest(orderCommonModel.getReceiveOrderDto()
                                                                                      .getOrderDeliveryDto()
                                                                                      .getOrderGoodsEntityList(),
                                                                      reqDto, null
                                                                     ));
        // 配送情報取得 詳細情報を保持
        orderCommonModel.getReceiveOrderDto()
                        .getOrderDeliveryDto()
                        .setDeliveryInformationDetailDto(
                                        orderHelper.toWebApiGetDeliveryInformationResponseDetailDto(response));
        // 発生したエラーを表示
        addErrorInfo(orderHelper.toCheckMessageDtoListOrder(response.getCheckMessageDtoList()), redirectAttributes,
                     model, true
                    );

    }

    /**
     * Web-APIを実行し、レスポンス情報を返す
     *
     * @return お届け先参照Web-APIレスポンスDto
     */
    private WebApiGetDestinationResponseDto executeWebApiGetDestination() {
        // Web-API実行
        WebApiGetDestinationRequest webApiGetDestinationRequest = new WebApiGetDestinationRequest();
        webApiGetDestinationRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));

        WebApiGetDestinationResponse webApiGetDestinationResponse = null;
        try {
            webApiGetDestinationResponse = webapiApi.getDestination(webApiGetDestinationRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return orderHelper.toAbstractWebApiResponseDto(webApiGetDestinationResponse);
    }

    // PDR Migrate Customization to here

}
// 2023-renew No14 to here
