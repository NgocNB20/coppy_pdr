// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsMapGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryCancelOrderResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiShipmentOrderHistoryResponseBaseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 注文履歴 Controller
 *
 * @author kimura
 */
@RequestMapping("/member/history")
@Controller
@SessionAttributes(value = "unshippedModel")
public class MemberHistoryUnshippedController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberHistoryUnshippedController.class);

    // 2023-renew No68 from here
    /**
     * アトリビュート名（注文キャンセル成功通知ダイアログ用）
     */
    public static final String DISPLAY_DIALOG_CANCEL_ORDER = "dispCancelOrder";
    // 2023-renew No68 to here

    /**
     * 注文履歴（未出荷）画面Dxoクラス
     */
    private final UnshippedHelper unshippedHelper;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * WEB-APIApi
     */
    private final WebapiApi webapiApi;

    /**
     * 商品Api
     */
    private final GoodsApi goodsApi;

    // 2023-renew No68 from here
    /**
     * 会員情報Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;
    // 2023-renew No68 to here

    /**
     * コンストラクタ
     *
     * @param unshippedHelper 注文履歴（未出荷）画面Dxoクラス
     * @param goodsUtility    商品系ヘルパークラス
     * @param webapiApi       WEB-APIApi
     * @param goodsApi        商品Api
     * @param memberInfoApi   会員情報Api
     * @param dateUtility     日付関連Utilityクラス
     */
    @Autowired
    public MemberHistoryUnshippedController(UnshippedHelper unshippedHelper,
                                            GoodsUtility goodsUtility,
                                            WebapiApi webapiApi,
                                            GoodsApi goodsApi,
                                            MemberInfoApi memberInfoApi,
                                            DateUtility dateUtility) {
        this.unshippedHelper = unshippedHelper;
        this.goodsUtility = goodsUtility;
        this.webapiApi = webapiApi;
        this.goodsApi = goodsApi;
        // 2023-renew No68 from here
        this.memberInfoApi = memberInfoApi;
        this.dateUtility = dateUtility;
        // 2023-renew No68 to here
    }

    /**
     * 一覧画面：初期処理
     *
     * @param unshippedModel 注文履歴（未出荷）画面ページModel
     * @param model          Model
     * @return 一覧画面
     */
    @GetMapping(value = {"/unshipped", "/unshipped.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/unshipped")
    protected String doLoadIndex(UnshippedModel unshippedModel, Model model) {

        // Web-APIから情報を取得
        WebApiGetNotYetShippingOrderHistoryResponseDto responseOrderHistoryUnshippedDto =
                        executeWebApiGetNotYetShippingOrderHistory();

        // 実行ステータスチェック
        // 取得情報がなければ履歴の表示はしない(取得した情報リストの最初のパラメータの一部をnullチェックする)
        if (responseOrderHistoryUnshippedDto == null
            || responseOrderHistoryUnshippedDto.getInfo().get(0).getOrderNo() == null
            || !responseOrderHistoryUnshippedDto.getResult().getStatus().equals("0")) {
            return "member/history/unshipped";

        }

        // 注文履歴（未出荷）情報を取り出す
        List<WebApiGetNotYetShippingOrderHistoryResponseDetailDto> unshippedOrderHistoryInfoDtoList =
                        responseOrderHistoryUnshippedDto.getInfo();

        // 商品
        for (WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo : unshippedOrderHistoryInfoDtoList) {

            // 商品コードリスト
            List<String> goodsCodeList = new ArrayList<>();

            // 商品コードリストを作成
            for (WebApiOrderHistoryResponseGoodsInfoDto orderGoodsInfo : orderHistoryInfo.getGoodsList()) {
                orderGoodsInfo.setGoodsName(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(
                                orderGoodsInfo.getGoodsName(), orderGoodsInfo.getDiscountFlag()));
                goodsCodeList.add(orderGoodsInfo.getGoodsCode());
            }
            // 商品詳細情報マップ取得
            GoodsDetailsMapGetRequest request = new GoodsDetailsMapGetRequest();
            request.setGoodsCodeList(goodsCodeList);
            Map<String, GoodsDetailsDtoResponse> goodsDetailsDtoResponseMap = null;
            try {
                goodsDetailsDtoResponseMap = goodsApi.getDetailsMap(request);

            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            Map<String, GoodsDetailsDto> goodsDetailsDtoMap =
                            unshippedHelper.goodsDetailsDtoMapGoods(goodsDetailsDtoResponseMap);

            orderHistoryInfo.setGoodsDetailsMap(goodsDetailsDtoMap);
        }

        // ページに情報反映
        try {
            // 2024-renew No06 from here
            unshippedHelper.toPageForUnshippedOrderHistory(
                            unshippedOrderHistoryInfoDtoList, unshippedModel, unshippedModel.isDisplayByGoodsCode());
            // 2024-renew No06 to here

        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return "member/history/unshipped";
    }

    /**
     * Web-APIを実行し、レスポンス情報を返す<br/>
     *
     * @return 注文履歴（未出荷）取得Web-APIレスポンスDto
     */
    protected WebApiGetNotYetShippingOrderHistoryResponseDto executeWebApiGetNotYetShippingOrderHistory() {

        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);

        WebApiGetNotYetShippingOrderHistoryRequest request = new WebApiGetNotYetShippingOrderHistoryRequest();
        request.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        WebApiGetNotYetShippingOrderHistoryResponse response = null;
        try {
            // Web-API実行
            response = webapiApi.getNotYetShippingOrderHistory(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        WebApiGetNotYetShippingOrderHistoryResponseDto responseDto =
                        unshippedHelper.toWebApiGetNotYetShippingOrderHistoryResponseDto(response);

        return responseDto;
    }

    // 2024-renew No06 from here

    /**
     * 表示: 商品別一覧
     *
     * @param unshippedModel 注文履歴（未出荷）画面ページModel
     * @param model モデル
     * @return string
     */
    @PostMapping(value = {"/unshipped", "/unshipped.html"}, params = "doDisplayByGoodsCode")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/unshipped")
    public String doDisplayByGoodsCode(UnshippedModel unshippedModel, Model model) {

        unshippedModel.setDisplayByGoodsCode(true);

        // 注文内容確認画面を再表示
        return doLoadIndex(unshippedModel, model);
    }

    /**
     * 表示: お届け予定日別一覧
     *
     * @param unshippedModel 注文履歴（未出荷）画面ページModel
     * @param model モデル
     * @return string
     */
    @PostMapping(value = {"/unshipped", "/unshipped.html"}, params = "doDisplayByReceiveDate")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/unshipped")
    public String doDisplayByReceiveDate(UnshippedModel unshippedModel, Model model) {

        unshippedModel.setDisplayByGoodsCode(false);

        // 注文内容確認画面を再表示
        return doLoadIndex(unshippedModel, model);
    }

    // 2024-renew No06 to here
    // 2023-renew No68 from here

    /**
     * 注文キャンセル
     *
     * @param unshippedModel 注文履歴（未出荷）画面ページModel
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return string
     */
    @PostMapping(value = {"/unshipped", "/unshipped.html"}, params = "doCancelOrder")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/unshipped")
    public String doCancelOrder(UnshippedModel unshippedModel, RedirectAttributes redirectAttributes, Model model) {

        // キャンセル対象の受注を取得
        OrderHistoryOrderItem orderHistoryOrderItem = null;
        if (CollectionUtil.isNotEmpty(unshippedModel.getOrderHistoryOrderItems())) {
            Optional<OrderHistoryOrderItem> orderHistoryOrderItemOptional = unshippedModel.getOrderHistoryOrderItems()
                                                                                          .stream()
                                                                                          .filter(item -> item.getOrderCode()
                                                                                                              .equals(unshippedModel.getCancelledOrderNo()))
                                                                                          .findFirst();
            if (orderHistoryOrderItemOptional.isPresent()) {
                orderHistoryOrderItem = orderHistoryOrderItemOptional.get();
            }
        }

        // キャンセル処理実行
        OrderHistoryCancelOrderResponse orderHistoryCancelOrderResponse = null;
        try {
            orderHistoryCancelOrderResponse = memberInfoApi.cancelOrder(
                            unshippedHelper.toOrderHistoryCancelOrderRequest(orderHistoryOrderItem,
                                                                             getCommonInfo().getCommonInfoUser()
                                                                                            .getMemberInfoSeq(),
                                                                             getCommonInfo().getCommonInfoUser()
                                                                                            .getMemberInfoLastName()
                                                                            ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // お届け予定日（ダイアログ用）を設定
        if (ObjectUtils.isNotEmpty(orderHistoryCancelOrderResponse)) {
            if (orderHistoryCancelOrderResponse.getReceiveDate() != null) {
                // 存在する場合は設定
                unshippedModel.setCancelReceiveDate(
                                dateUtility.formatYmdWithSlash(orderHistoryCancelOrderResponse.getReceiveDate()));
            } else {
                // 存在しない場合は固定文言
                unshippedModel.setCancelReceiveDate(BaseShipModel.RECEIVEDATE_PENDING);
            }
        }

        // 注文キャンセル成功通知ダイアログ表示
        redirectAttributes.addFlashAttribute(DISPLAY_DIALOG_CANCEL_ORDER, true);

        // 注文内容確認画面を再表示
        return "redirect:/member/history/unshipped.html";
    }

    // 2023-renew No68 to here

}
// PDR Migrate Customization to here
