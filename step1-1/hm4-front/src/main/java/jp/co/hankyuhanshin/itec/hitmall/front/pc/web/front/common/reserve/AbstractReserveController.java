/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsGetByCodeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CalendarNotSelectDateEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.ReserveValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.group.ReserveGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.OrderHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

/**
 * セールde予約関連抽象コントローラー
 *
 * @author ota-s5
 */
// 2023-renew No14 from here
public class AbstractReserveController<T extends AbstractReserveModel, U extends AbstractReserveHelper>
                extends AbstractController {

    /**
     * ロガー
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractReserveController.class);

    /**
     * セールde予約画面の動的バリデータクラス
     */
    protected final ReserveValidator reserveValidator;

    /**
     * セールde予約画面 Helper
     */
    protected final U abstractReserveHelper;

    /**
     * 注文Helper
     */
    protected final OrderHelper orderHelper;

    /**
     * 商品Helper
     */
    protected final GoodsHelper goodsHelper;

    /**
     * 商品API
     */
    protected final GoodsApi goodsApi;

    /**
     * WebapiApi
     */
    protected final WebapiApi webapiApi;

    /**
     * ショップAPI
     */
    protected final ShopApi shopApi;

    /**
     * 共通情報ヘルパークラス
     */
    protected final CommonInfoUtility commonInfoUtility;

    /**
     * 受注業務ユーティリティクラス
     */
    protected final OrderUtility orderUtility;

    /**
     * 変換ユーティリティクラス
     */
    protected final ConversionUtility conversionUtility;

    /**
     * 商品系ヘルパークラス
     */
    protected final GoodsUtility goodsUtility;

    /**
     * 日付関連Utility
     */
    protected final DateUtility dateUtility;

    /**
     * コンストラクタ
     */
    @Autowired
    public AbstractReserveController(ReserveValidator reserveValidator,
                                     U abstractReserveHelper,
                                     OrderHelper orderHelper,
                                     GoodsHelper goodsHelper,
                                     GoodsApi goodsApi,
                                     WebapiApi webapiApi,
                                     ShopApi shopApi,
                                     CommonInfoUtility commonInfoUtility,
                                     OrderUtility orderUtility,
                                     ConversionUtility conversionUtility,
                                     GoodsUtility goodsUtility,
                                     DateUtility dateUtility) {
        this.reserveValidator = reserveValidator;
        this.abstractReserveHelper = abstractReserveHelper;
        this.orderHelper = orderHelper;
        this.goodsHelper = goodsHelper;
        this.goodsApi = goodsApi;
        this.webapiApi = webapiApi;
        this.shopApi = shopApi;
        this.commonInfoUtility = commonInfoUtility;
        this.orderUtility = orderUtility;
        this.conversionUtility = conversionUtility;
        this.goodsUtility = goodsUtility;
        this.dateUtility = dateUtility;
    }

    /**
     * 動的バリデータ
     *
     * @param error エラー
     */
    @InitBinder
    public void initBinder(WebDataBinder error) {
        error.addValidators(reserveValidator);
    }

    /**
     * リクエストパラメータのチェック
     *
     * @param abstractReserveModel セールde予約画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return True:正常、False：異常
     */
    protected boolean checkRequestParam(T abstractReserveModel, RedirectAttributes redirectAttributes, Model model) {

        // 商品コードがリクエストパラメータに含まれない場合、エラー
        if (StringUtils.isEmpty(abstractReserveModel.getGcd())) {
            addWarnMessage(AbstractReserveModel.MSGCD_ERROR_NON_REQUEST_PARAM,
                           new Object[] {PropertiesUtil.getSystemPropertiesValue("app.complement.url")},
                           redirectAttributes, model
                          );
            return false;
        }

        return true;
    }

    /**
     * 画面表示情報取得
     *
     * @param abstractReserveModel セールde予約画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return True:正常（エラーあり当画面表示も含む）、False：異常（当画面表示不可）
     */
    protected boolean setDisplayInfo(T abstractReserveModel, RedirectAttributes redirectAttributes, Model model) {

        // 商品情報を取得 → 取得結果のチェック
        GoodsDetailsDto goodsDetailsDto = getGoodsDetailsDto(abstractReserveModel);
        if (!checkGoodsDetailsDto(goodsDetailsDto, redirectAttributes, model)) {
            return false;
        }

        // セールde予約商品情報取得
        WebApiGetReserveResponseDto reserveResponseDto = executeWebApiGetReserve(abstractReserveModel);

        // 注文履歴（過去1年）取得
        WebApiGetPreShipmentOrderHistoryResponseDto preShipmentOrderHistoryAggregate =
                        executeWebApiGetPreShipmentOrderHistoryAggregate();

        // カレンダー選択不可日付取得
        List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList = getCalendarNotSelectDateEntityList();

        // 数量割引情報取得
        WebApiGetQuantityDiscountResponseDto quantityDiscountResponseDto =
                        executeWebApiGetQuantityDiscount(abstractReserveModel);

        // 画面表示・再表示
        abstractReserveHelper.toPageForLoad(abstractReserveModel, goodsDetailsDto, reserveResponseDto,
                                            preShipmentOrderHistoryAggregate, calendarNotSelectDateEntityList,
                                            quantityDiscountResponseDto
                                           );

        return true;
    }

    /**
     * 対象の商品詳細Dtoを取得
     *
     * @param abstractReserveModel セールde予約画面Model
     * @return 商品詳細Dto
     */
    private GoodsDetailsDto getGoodsDetailsDto(T abstractReserveModel) {

        GoodsDetailsGetByCodeRequest goodsDetailsGetByCodeRequest = new GoodsDetailsGetByCodeRequest();
        goodsDetailsGetByCodeRequest.setCode(abstractReserveModel.getGcd());

        GoodsDetailsDtoResponse goodsDetailsDtoResponse = null;
        try {
            goodsDetailsDtoResponse = goodsApi.getDetailsByCode(goodsDetailsGetByCodeRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return orderHelper.toGoodsDetailsDtoGoods(goodsDetailsDtoResponse);
    }

    /**
     * セールde予約情報を取得（取りおき情報取得API）
     *
     * @param abstractReserveModel セールde予約画面Model
     * @return セールde予約商品情報
     */
    private WebApiGetReserveResponseDto executeWebApiGetReserve(T abstractReserveModel) {

        // （１）セールde予約情報の取得（取りおき情報取得API）
        MemberInfoEntity memberInfoEntity = commonInfoUtility.getMemberInfoEntity(getCommonInfo());
        Integer customerNo = memberInfoEntity.getCustomerNo();
        String zipcode = memberInfoEntity.getMemberInfoZipCode();

        WebApiGetReserveRequest webApiGetReserveRequest = new WebApiGetReserveRequest();
        webApiGetReserveRequest.setCustomerNo(customerNo);
        webApiGetReserveRequest.setDeliveryCustomerNo(customerNo);
        webApiGetReserveRequest.setDeliveryZipcode(zipcode);
        webApiGetReserveRequest.setGoodsCode(abstractReserveModel.getGcd());

        WebApiGetReserveResponse webApiGetReserveResponse = null;
        try {
            webApiGetReserveResponse = webapiApi.getReserve(webApiGetReserveRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        WebApiGetReserveResponseDto reserveResponseDto =
                        abstractReserveHelper.toWebApiGetReserveResponseDto(webApiGetReserveResponse);

        // （２）WEB-APIの取りおき情報の取得に失敗した場合のチェック
        boolean isError = reserveResponseDto == null || CollectionUtil.isEmpty(reserveResponseDto.getInfo());

        // （３）取りおき可能な日付が存在しない場合のチェック
        if (!isError) {
            WebApiGetReserveResponseDetailDto dto = reserveResponseDto.getMap().get(abstractReserveModel.getGcd());
            isError =
                            // 取りおき可否＝取りおき不可の場合
                            HTypeReserveDeliveryFlag.OFF.getValue().equals(dto.getReserveFlag())
                            // 又は 予約可能開始日が設定されていない場合
                            || dto.getPossibleReserveFromDay() == null
                            // 又は 予約可能終了日が設定されていない
                            || dto.getPossibleReserveToDay() == null
                            // 又は サーバのシステム日時 ＞ 予約可能終了日 の場合
                            || dateUtility.getCurrentTime().after(dto.getPossibleReserveToDay());
        }

        if (isError) {
            throwMessage(AbstractReserveModel.MSGCD_ERROR_NOT_RESERVE_ITEM);
        }

        return reserveResponseDto;
    }

    /**
     * 注文履歴（過去1年）情報を取得（注文履歴（過去1年）取得API）
     *
     * @return 注文履歴（過去1年）情報
     */
    private WebApiGetPreShipmentOrderHistoryResponseDto executeWebApiGetPreShipmentOrderHistoryAggregate() {

        // （１）注文履歴（過去1年）情報の取得（注文履歴（過去1年）取得API）
        WebApiGetPreShipmentOrderHistoryAggregateRequest webApiGetPreShipmentOrderHistoryAggregateRequest =
                        new WebApiGetPreShipmentOrderHistoryAggregateRequest();
        webApiGetPreShipmentOrderHistoryAggregateRequest.setCustomerNo(
                        commonInfoUtility.getCustomerNo(getCommonInfo()));

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

        WebApiGetPreShipmentOrderHistoryResponseDto preShipmentOrderHistoryResponseDto =
                        orderHelper.toWebApiGetPreShipmentOrderHistoryResponseDto(
                                        webApiGetPreShipmentOrderHistoryAggregateResponse);

        // （２）WEB-APIの注文履歴（過去1年）情報の取得に失敗した場合
        //      ※過去1年で受注なしの場合もクラス生成されてNULLにはならないので、NULLチェックで失敗判定可能
        if (preShipmentOrderHistoryResponseDto == null) {
            throwMessage(AbstractReserveModel.MSGCD_SYSTEM_ERR);
        }

        return preShipmentOrderHistoryResponseDto;

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

        return abstractReserveHelper.toCalendarNotSelectDateEntityList(calendarNotSelectDateEntityResponseList);
    }

    /**
     * 数量割引情報を取得（数量割引情報取得API）
     *
     * @param abstractReserveModel セールde予約画面Model
     * @return 数量割引情報
     */
    private WebApiGetQuantityDiscountResponseDto executeWebApiGetQuantityDiscount(T abstractReserveModel) {

        // （１）数量割引情報の取得（数量割引情報取得API）
        WebApiGetQuantityDiscountRequest webApiGetQuantityDiscountRequest = new WebApiGetQuantityDiscountRequest();
        webApiGetQuantityDiscountRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        webApiGetQuantityDiscountRequest.setGoodsCode(abstractReserveModel.getGcd());

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

        // （２）WEB-APIの数量割引情報の取得に失敗した場合
        if (quantityDiscountDto == null || CollectionUtil.isEmpty(quantityDiscountDto.getInfo())) {
            throwMessage(AbstractReserveModel.MSGCD_SYSTEM_ERR);
        }

        return quantityDiscountDto;
    }

    /**
     * 取得した商品詳細Dtoのチェック
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return True:正常（エラーあり当画面表示も含む）、False：異常（当画面表示不可）
     */
    protected boolean checkGoodsDetailsDto(GoodsDetailsDto goodsDetailsDto,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        // 商品情報が取得できない場合エラー
        if (ObjectUtils.isEmpty(goodsDetailsDto)) {
            addWarnMessage(AbstractReserveModel.MSGCD_ERROR_NON_OPEN_GOODS, null, redirectAttributes, model);
            return false;
        }

        return true;
    }

    /**
     * 入力チェックを明示的に実行
     *
     * @param abstractReserveModel セールde予約画面Model
     * @param error エラー
     * @return True:正常、False：異常
     */
    protected boolean validate(T abstractReserveModel, BindingResult error) {
        // 当画面の入力チェックを明示的に実行
        reserveValidator.validate(abstractReserveModel, error, ReserveGroup.class);
        return !error.hasErrors();
    }

    /**
     * 相関チェック
     *
     * @param abstractReserveModel セールde予約画面Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     */
    protected void checkInput(T abstractReserveModel, RedirectAttributes redirectAttributes, Model model) {

        // 合計数量（今すぐお届け分の数量も含める）
        BigDecimal goodsTotalCount = abstractReserveModel.getReserveItem().getInputDeliveryNowGoodsCount();
        for (ReserveDetailItem reserveDetailItem : abstractReserveModel.getReserveItem().getReserveDetailItemList()) {
            // お届け希望日が未入力の場合、加算対象外
            if (StringUtil.isEmpty(reserveDetailItem.getReserveDeliveryDate())) {
                continue;
            }
            goodsTotalCount =
                            goodsTotalCount.add(conversionUtility.toBigDecimal(reserveDetailItem.getInputGoodsCount()));
        }

        // セールde予約可能商品の合計数量が最大合計購入可能数を超えた場合エラー
        if (AbstractReserveModel.RESERVE_DELIVERY_MAX_TOTAL_GOODS_COUNT.compareTo(goodsTotalCount) < 0) {
            addErrorMessage(AbstractReserveModel.MSGCD_ERROR_RESERVE_DELIVERY_MAX_TOTAL_GOODS_COUNT,
                            new String[] {goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                            abstractReserveModel.getGoodsCode()),
                                            orderUtility.createErrDispGoodsName(abstractReserveModel.getGoodsName(),
                                                                                abstractReserveModel.getUnitValue1(),
                                                                                abstractReserveModel.getUnitValue2()
                                                                               ), conversionUtility.toString(
                                            AbstractReserveModel.RESERVE_DELIVERY_MAX_TOTAL_GOODS_COUNT)}
                           );
        }

        // お届け希望日が全て未入力の場合エラー
        if (abstractReserveModel.getReserveItem()
                                .getReserveDetailItemList()
                                .stream()
                                .filter(item -> item.getReserveDeliveryDate() != null)
                                .findFirst()
                                .isEmpty()) {
            addErrorMessage(AbstractReserveModel.MSGCD_ERROR_NON_RESERVE_DELIVERY_DATE, new Object[] {"お届け希望日"});
        }

        // エラーが追加されている場合は表示
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

}
// 2023-renew No14 to here
