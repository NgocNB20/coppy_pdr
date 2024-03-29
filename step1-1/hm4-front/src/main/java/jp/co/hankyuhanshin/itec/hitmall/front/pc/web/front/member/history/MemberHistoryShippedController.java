// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsMapGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeExpirationDateMonth;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiShipmentOrderHistoryResponseBaseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文履歴 Controller
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/member/history")
@Controller
// 2023-renew No52 from here
@SessionAttributes(value = "shippedModel")
// 2023-renew No52 to here
public class MemberHistoryShippedController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberHistoryShippedController.class);

    // 2023-renew No52 from here
    /**
     * 日付から日付までを比較するとエラーが発生する
     */
    public static final String MSGCD_FROM_DATE_LARGE_OR_EQUAL_TO_DATE = "PDR-2023RENEW-52-002-E";

    /**
     * お届け日のプルダウンが不正に操作された場合エラー
     * （value値の改ざん）
     */
    public static final String MSGCD_ILLEGAL_OPERATION = "PDR-2023RENEW-52-003-E";

    /**
     * ご注文履歴（発送済）のプルダウン選択可能年数
     */
    protected static final String SHIPPED_HISTORY_YEAR_AFTER = "shipped.history.year.after";
    // 2023-renew No52 to here

    /**
     * 注文履歴（出荷済） 画面Dxoクラス
     */
    private final ShippedHelper shippedHelper;

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

    // 2023-renew No52 from here
    /**
     * 受注共通処理
     */
    private final OrderUtility orderUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * FROMお届け日の年プルダウン選択肢
     */
    private static Map<String, String> searchStartDayYearItems;

    /**
     * FROMお届け日の月プルダウン選択肢
     */
    private static Map<String, String> searchStartDayMonthItems;

    /**
     * TOお届け日の年プルダウン選択肢
     */
    private static Map<String, String> searchEndDayYearItems;

    /**
     * TOお届け日の月プルダウン選択肢
     */
    private static Map<String, String> searchEndDayMonthItems;
    // 2023-renew No52 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public MemberHistoryShippedController(ShippedHelper shippedHelper,
                                          WebapiApi webapiApi,
                                          GoodsApi goodsApi,
                                          GoodsUtility goodsUtility,
                                          // 2023-renew No52 from here
                                          OrderUtility orderUtility,
                                          DateUtility dateUtility
                                          // 2023-renew No52 to here
                                         ) {
        this.shippedHelper = shippedHelper;
        this.webapiApi = webapiApi;
        this.goodsApi = goodsApi;
        this.goodsUtility = goodsUtility;
        // 2023-renew No52 from here
        this.orderUtility = orderUtility;
        this.dateUtility = dateUtility;
        // 2023-renew No52 to here
    }

    /**
     * 一覧画面：初期処理
     *
     * @param shippedModel 注文履歴（出荷済）画面ページModel
     * @param model        Model
     * @return 一覧画面
     */
    @GetMapping(value = {"/shipped.html", "/shipped"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/shipped")
    protected String doLoadIndex(ShippedModel shippedModel, Model model) throws Exception {
        // 2023-renew No52 from here
        // お届け日のプルダウンの初期値設定
        initDefaultSelectionData(shippedModel);
        // お届け日のデフォルト値（直近12ヶ月）
        initDefaultConditionData(shippedModel);
        // Web-APIから情報を取得
        WebApiGetPreShipmentOrderHistoryResponseDto responseOrderHistoryShippedDto =
                        executeWebApiGetPreShipmentOrderHistory(shippedModel);
        // 2023-renew No52 to here

        // 実行ステータスチェック
        // 取得情報がなければ履歴の表示はしない(取得した情報リストの最初のパラメータの一部をnullチェックする)
        if (responseOrderHistoryShippedDto == null || responseOrderHistoryShippedDto.info.get(0).getOrderNo() == null
            || !responseOrderHistoryShippedDto.getResult().getStatus().equals("0")) {
            return "member/history/shipped";
        }

        // 注文履歴（出荷済）情報を取り出す
        getDisplayData(shippedModel, responseOrderHistoryShippedDto);

        return "member/history/shipped";
    }

    // 2023-renew No52 from here

    /**
     * お届け日のプルダウンの初期値設定
     * @param shippedModel 注文履歴（出荷済）画面ページModel
     */
    private void initDefaultSelectionData(ShippedModel shippedModel) {
        searchStartDayMonthItems = EnumTypeUtil.getEnumMap(HTypeExpirationDateMonth.class);
        searchStartDayYearItems = createShippedHistoryYearItemsAfter();

        searchEndDayMonthItems = EnumTypeUtil.getEnumMap(HTypeExpirationDateMonth.class);
        searchEndDayYearItems = createShippedHistoryYearItemsAfter();

        shippedModel.setSearchStartDayMonthItems(searchStartDayMonthItems);
        shippedModel.setSearchStartDayYearItems(searchStartDayYearItems);

        shippedModel.setSearchEndDayMonthItems(searchEndDayMonthItems);
        shippedModel.setSearchEndDayYearItems(searchEndDayYearItems);
    }

    /**
     * お届け日のデフォルト値（直近12ヶ月）
     * @param shippedModel 注文履歴（出荷済）画面ページModel
     */
    private void initDefaultConditionData(ShippedModel shippedModel) {
        // 現在の月から作成
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        String defaultEndMonth = endDate.format(DateTimeFormatter.ofPattern("MM"));
        String defaultEndYear = orderUtility.toKeyOfYear(endDate.getYear());
        shippedModel.setSearchEndDayMonth(defaultEndMonth);
        shippedModel.setSearchEndDayYear(defaultEndYear);

        // 12月前のデータ
        LocalDate startDate = endDate.minusMonths(11);
        String defaultStartMonth = startDate.format(DateTimeFormatter.ofPattern("MM"));
        String defaultStartYear = orderUtility.toKeyOfYear(startDate.getYear());
        shippedModel.setSearchStartDayMonth(defaultStartMonth);
        shippedModel.setSearchStartDayYear(defaultStartYear);
    }

    /**
     * <pre>
     * データチェック関数
     * ・お届け日のプルダウンが不正に操作された場合エラー
     * （value値の改ざん）
     * </pre>
     *
     * @param shippedModel 注文履歴（出荷済）画面ページModel
     * @return データチェックでエラーがある場合はTRUE
     */
    private boolean isDataError(ShippedModel shippedModel) {
        String searchStartDayMonth = shippedModel.getSearchStartDayMonth();
        String searchStartDayYear = shippedModel.getSearchStartDayYear();
        String searchEndDayMonth = shippedModel.getSearchEndDayMonth();
        String searchEndDayYear = shippedModel.getSearchEndDayYear();

        return (StringUtils.isNotEmpty(searchStartDayMonth)
                && searchStartDayMonthItems.get(searchStartDayMonth) == null) || (
                               StringUtils.isNotEmpty(searchStartDayYear)
                               && searchStartDayYearItems.get(searchStartDayYear) == null) || (
                               StringUtils.isNotEmpty(searchEndDayMonth)
                               && searchEndDayMonthItems.get(searchEndDayMonth) == null) || (
                               StringUtils.isNotEmpty(searchEndDayYear)
                               && searchEndDayYearItems.get(searchEndDayYear) == null);
    }

    /**
     * 一覧画面：データ検索
     *
     * @param shippedModel 注文履歴（出荷済）画面ページModel
     * @param error        エラー
     * @param model        Model
     * @return 一覧画面
     * @throws Exception
     */
    @PostMapping(value = {"/shipped.html", "/shipped"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/shipped")
    public String doSearchShipped(@Validated ShippedModel shippedModel, BindingResult error, Model model)
                    throws Exception {

        // データチェック実施
        if (isDataError(shippedModel)) {
            throwMessage(MSGCD_ILLEGAL_OPERATION);
        }

        if (error.hasErrors()) {
            return "/member/history/shipped";
        }

        // Web-APIから情報を取得
        WebApiGetPreShipmentOrderHistoryResponseDto responseOrderHistoryShippedDto =
                        executeWebApiGetPreShipmentOrderHistory(shippedModel);

        if (hasErrorMessage()) {
            throwMessage();
        }

        // 実行ステータスチェック
        // 取得情報がなければ履歴の表示はしない(取得した情報リストの最初のパラメータの一部をnullチェックする)
        if (responseOrderHistoryShippedDto == null || responseOrderHistoryShippedDto.info.get(0).getOrderNo() == null
            || !responseOrderHistoryShippedDto.getResult().getStatus().equals("0")) {
            return "member/history/shipped";
        }

        // 注文履歴（出荷済）情報を取り出す
        getDisplayData(shippedModel, responseOrderHistoryShippedDto);

        return "/member/history/shipped";
    }

    /**
     * 注文履歴（出荷済）情報を取り出す
     * @param shippedModel 注文履歴（出荷済）画面ページModel
     * @param responseOrderHistoryShippedDto
     */
    private void getDisplayData(ShippedModel shippedModel,
                                WebApiGetPreShipmentOrderHistoryResponseDto responseOrderHistoryShippedDto) {
        // 注文履歴（出荷済）情報を取り出す
        List<WebApiGetPreShipmentOrderHistoryResponseDetailDto> shippedOrderHistoryInfoDtoList =
                        responseOrderHistoryShippedDto.info;

        // 商品
        for (WebApiShipmentOrderHistoryResponseBaseDetailDto orderHistoryInfo : shippedOrderHistoryInfoDtoList) {

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
                            shippedHelper.goodsDetailsDtoMapGoods(goodsDetailsDtoResponseMap);
            orderHistoryInfo.setGoodsDetailsMap(goodsDetailsDtoMap);
        }

        // ページに情報反映
        try {
            shippedHelper.toPageForShippedOrderHistory(shippedOrderHistoryInfoDtoList, shippedModel);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
    }
    // 2023-renew No52 to here

    /**
     * Web-APIを実行し、レスポンス情報を返す
     *
     * @return 注文履歴（出荷済）取得Web-APIレスポンスDto
     */
    protected WebApiGetPreShipmentOrderHistoryResponseDto executeWebApiGetPreShipmentOrderHistory(ShippedModel shippedModel)
                    throws Exception {

        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);

        WebApiGetPreShipmentOrderHistoryRequest request = new WebApiGetPreShipmentOrderHistoryRequest();
        request.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));

        // 2023-renew No52 from here

        if (shippedModel.getSearchStartDayYear() != null && shippedModel.getSearchStartDayMonth() != null) {
            Timestamp searchStartDay = dateUtility.toFirstTimeOfMonth(
                            searchStartDayYearItems.get(shippedModel.getSearchStartDayYear()),
                            shippedModel.getSearchStartDayMonth()
                                                                     );
            request.setSearchStartDay(searchStartDay);
        }

        if (shippedModel.getSearchEndDayYear() != null && shippedModel.getSearchEndDayMonth() != null) {
            Timestamp searchEndDay = dateUtility.toLastTimeOfMonth(
                            searchEndDayYearItems.get(shippedModel.getSearchEndDayYear()),
                            shippedModel.getSearchEndDayMonth()
                                                                  );
            request.setSearchEndDay(searchEndDay);
        }

        if (shippedModel.getSearchStartDayYear() != null && shippedModel.getSearchStartDayMonth() != null
            && shippedModel.getSearchEndDayYear() != null && shippedModel.getSearchEndDayMonth() != null) {
            if (request.getSearchStartDay() != null && request.getSearchEndDay() != null) {
                if (request.getSearchStartDay().compareTo(request.getSearchEndDay()) > 0) {
                    this.addErrorMessage(
                                    MSGCD_FROM_DATE_LARGE_OR_EQUAL_TO_DATE,
                                    new String[] {"お届け日-From（年月）", "お届け日-To（年月）"}
                                        );
                }
            }
        }

        // お届け日の入力：Fromのみ場合
        if (shippedModel.getSearchStartDayYear() != null && shippedModel.getSearchStartDayMonth() != null && (
                        shippedModel.getSearchEndDayYear() == null || shippedModel.getSearchEndDayMonth() == null)) {
            LocalDateTime endTimeOfThisMonth =
                            LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
            String defaultEndMonth = endTimeOfThisMonth.format(DateTimeFormatter.ofPattern("MM"));
            String defaultEndYear = orderUtility.toKeyOfYear(endTimeOfThisMonth.getYear());
            shippedModel.setSearchEndDayMonth(defaultEndMonth);
            shippedModel.setSearchEndDayYear(defaultEndYear);
            Timestamp searchEndDay = Timestamp.valueOf(endTimeOfThisMonth);
            request.setSearchEndDay(searchEndDay);
        }
        // 2023-renew No52 to here

        WebApiGetPreShipmentOrderHistoryResponse response = null;
        try {
            // Web-API実行
            response = webapiApi.getPreShipmentOrderHistory(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        WebApiGetPreShipmentOrderHistoryResponseDto responseDto =
                        shippedHelper.toWebApiGetPreShipmentOrderHistoryResponseDto(response);

        return responseDto;
    }

    /**
     *  ご注文履歴（発送済）の年プルダウン作成
     * @return 現在の年の 3 年後を選択します
     */
    private Map<String, String> createShippedHistoryYearItemsAfter() {
        Map<String, String> expirationDateYearMap = new LinkedHashMap<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        // システムプロパティから何年分表示するかを取得。
        int years = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue(SHIPPED_HISTORY_YEAR_AFTER)) + 1;

        for (int i = currentYear; i > currentYear - years; i--) {
            expirationDateYearMap.put(orderUtility.toKeyOfYear(i), Integer.toString(i));
        }

        return expirationDateYearMap;
    }
}
// PDR Migrate Customization to here
