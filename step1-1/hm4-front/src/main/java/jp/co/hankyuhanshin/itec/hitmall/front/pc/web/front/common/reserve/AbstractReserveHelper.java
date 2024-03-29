// 2023-renew No14 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CalendarNotSelectDateEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseCustomerSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPreShipmentOrderHistoryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiOrderHistoryResponseGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * セールde予約 HELPER
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class AbstractReserveHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReserveHelper.class);

    /**
     * 商品系Helper
     */
    private final GoodsUtility goodsUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * コンストラクタ
     */
    @Autowired
    public AbstractReserveHelper(GoodsUtility goodsUtility,
                                 ConversionUtility conversionUtility,
                                 DateUtility dateUtility) {
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
    }

    /** ***********　画面項目セット関連 Method Start　*********** **/

    /**
     * リクエストパラメータをModel保持
     *
     * @param abstractReserveModel セールde予約画面 Model
     * @param gcd 商品コード（リクエストパラメータ）
     */
    public void setRequestParam(AbstractReserveModel abstractReserveModel, String gcd) {
        abstractReserveModel.setGcd(gcd);
    }

    /**
     * 画面表示・再表示
     *
     * @param abstractReserveModel セールde予約画面 Model
     * @param goodsDetailsDto 商品詳細Dto
     * @param reserveResponseDto  セールde予約商品情報
     * @param preShipmentOrderHistoryAggregate 注文履歴（過去1年）
     * @param calendarNotSelectDateEntityList カレンダー選択不可日付
     * @param quantityDiscountResponseDto 数量割引情報
     */
    public void toPageForLoad(AbstractReserveModel abstractReserveModel,
                              GoodsDetailsDto goodsDetailsDto,
                              WebApiGetReserveResponseDto reserveResponseDto,
                              WebApiGetPreShipmentOrderHistoryResponseDto preShipmentOrderHistoryAggregate,
                              List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList,
                              WebApiGetQuantityDiscountResponseDto quantityDiscountResponseDto) {

        // 商品情報の設定
        setGoodsInfo(abstractReserveModel, goodsDetailsDto, quantityDiscountResponseDto);

        // セールde予約情報の設定
        setReserveInfo(abstractReserveModel, reserveResponseDto);

        // 過去注文数量の設定
        setReserveDeliveryGoodsHistoryCount(abstractReserveModel, preShipmentOrderHistoryAggregate);

        // カレンダー選択不可日付情報の設定
        setCalendarNotSelectDateList(abstractReserveModel, calendarNotSelectDateEntityList);

    }

    /**
     * 商品情報の設定
     *
     * @param abstractReserveModel   セールde予約画面Model
     * @param goodsDetailsDto 商品詳細Dto
     * @param quantityDiscountResponseDto 数量割引情報
     */
    private void setGoodsInfo(AbstractReserveModel abstractReserveModel,
                              GoodsDetailsDto goodsDetailsDto,
                              WebApiGetQuantityDiscountResponseDto quantityDiscountResponseDto) {

        // 商品グループSEQ
        abstractReserveModel.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());

        // 商品SEQ
        abstractReserveModel.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

        // 商品番号
        abstractReserveModel.setGoodsCode(goodsDetailsDto.getGoodsCode());

        // 画像
        String imageFileName = goodsUtility.getImageFileName(goodsDetailsDto);
        String goodsImageSrc = goodsUtility.getGoodsImagePath(imageFileName);
        String goodsName = goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsDetailsDto);
        abstractReserveModel.setGoodsImage(goodsImageSrc);
        abstractReserveModel.setGoodsImageSrc(goodsImageSrc);
        abstractReserveModel.setGoodsImageAlt(goodsName);

        // 商品表示名
        abstractReserveModel.setGoodsName(goodsName);

        // 規格1
        abstractReserveModel.setViewUnit1(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle1()));
        abstractReserveModel.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
        abstractReserveModel.setUnitValue1(goodsDetailsDto.getUnitValue1());

        // 規格2
        abstractReserveModel.setViewUnit2(StringUtil.isNotEmpty(goodsDetailsDto.getUnitTitle2()));
        abstractReserveModel.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
        abstractReserveModel.setUnitValue2(goodsDetailsDto.getUnitValue2());

        // 単位
        abstractReserveModel.setUnit(goodsDetailsDto.getUnit());

        // 金額情報の生成
        // （１）数量閾値と価格、セール価格のリストを作成
        List<GoodsStockItem> goodsItems = new ArrayList<>();
        Set<String> orderedSet = new LinkedHashSet<>();

        int rowSpanCount = 0;
        NumberFormat df = NumberFormat.getNumberInstance();

        for (WebApiGetQuantityDiscountResponseDetailDto responseInfo : quantityDiscountResponseDto.getInfo()) {
            if (abstractReserveModel.getGoodsCode().equals(responseInfo.getGoodsCode())) {
                // 価格リスト
                List<WebApiGetQuantityDiscountResponsePriceDto> priceList = responseInfo.getPriceList();
                // 割引価格リスト（数量割引）
                List<WebApiGetQuantityDiscountResponseSalePriceDto> salePriceList = responseInfo.getSalePriceList();
                // 顧客セール価格リスト
                List<WebApiGetQuantityDiscountResponseCustomerSalePriceDto> customerSalePriceList =
                                responseInfo.getCustomerSalePriceList();

                // セール価格が存在するかどうか（TRUE：数量割引あり or 顧客セールあり、FALSE：両者なし）
                abstractReserveModel.setExistSalePrice(salePriceList != null && !salePriceList.isEmpty()
                                                       || customerSalePriceList != null
                                                          && !customerSalePriceList.isEmpty());

                // まずは１商品につき何行表示するのかを割り出す
                // セールあり
                if (abstractReserveModel.isExistSalePrice()) {
                    if (!CollectionUtil.isEmpty(customerSalePriceList)) {
                        // 顧客セール情報が存在する場合、顧客セール価格リスト数をセット（顧客セール価格リスト優先）
                        rowSpanCount = customerSalePriceList.size();
                    } else {
                        // 顧客セール情報が存在しない場合、数量割引価格リスト数をセット
                        rowSpanCount = salePriceList.size();
                    }
                }
                // セールなし
                else {
                    // 価格リストをセット
                    rowSpanCount = priceList.size();
                }

                // 次に表示する金額情報を作成する
                for (int i = 0; i < rowSpanCount; i++) {
                    GoodsStockItem goodsItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
                    goodsItem.setGoodsCode(responseInfo.getGoodsCode());
                    orderedSet.add(responseInfo.getGoodsCode());
                    // セールあり
                    if (abstractReserveModel.isExistSalePrice()) {
                        // 価格
                        if (priceList != null && priceList.get(i) != null) {
                            // 価格リスト1件目の値を設定
                            goodsItem.setPrice(responseInfo.getMarketPriceFlag().equals("1") ?
                                                               "時価" :
                                                               df.format(priceList.get(i).getPrice()));
                        }

                        // 数量＆セール価格
                        if (!CollectionUtil.isEmpty(customerSalePriceList)) {
                            // 顧客セール情報が存在する場合、顧客セールから設定（顧客セール優先）
                            goodsItem.setLevel((priceList == null || priceList.size() == 1)
                                               && customerSalePriceList.get(i).getCustomerSaleLevel() == null ?
                                                               "1～" :
                                                               customerSalePriceList.get(i).getCustomerSaleLevel());
                            // 顧客セール価格を設定
                            goodsItem.setCustomerSalePrice(
                                            df.format(customerSalePriceList.get(i).getCustomerSalePrice()));
                        } else {
                            // 顧客セール情報が存在しない場合、数量割引から設定
                            goodsItem.setLevel((priceList == null || priceList.size() == 1)
                                               && salePriceList.get(i).getSaleLevel() == null ?
                                                               "1～" :
                                                               salePriceList.get(i).getSaleLevel());
                            // 数量割引価格を設定
                            goodsItem.setSalePrice(df.format(salePriceList.get(i).getSalePrice()));
                        }

                    }
                    // セールなし
                    else {
                        // 価格
                        goodsItem.setPrice(responseInfo.getMarketPriceFlag().equals("1") ?
                                                           "時価" :
                                                           df.format(priceList.get(i).getPrice()));
                        // 数量
                        goodsItem.setLevel(
                                        priceList.get(i).getLevel() == null || priceList.get(i).getLevel().isEmpty() ?
                                                        "1～" :
                                                        priceList.get(i).getLevel());
                    }

                    goodsItems.add(goodsItem);
                }
                break;
            }
        }

        // 価格情報をマージする
        abstractReserveModel.setGoodsItems(goodsUtility.mergePriceInfo(goodsItems, orderedSet, LOGGER));
        // 複数数量閾値フラグ設定
        goodsUtility.setMultiLevelFlag(abstractReserveModel.getGoodsItems());
    }

    /**
     * セールde予約情報の設定
     *
     * @param abstractReserveModel   セールde予約画面Model
     * @param reserveResponseDto  セールde予約商品情報
     */
    private void setReserveInfo(AbstractReserveModel abstractReserveModel,
                                WebApiGetReserveResponseDto reserveResponseDto) {

        // 詳細情報を取得
        WebApiGetReserveResponseDetailDto reserveResponseDetailDto =
                        reserveResponseDto.getMap().get(abstractReserveModel.getGoodsCode());

        // 予約可能開始日
        abstractReserveModel.setPossibleReserveFromDay(
                        dateUtility.formatYmdWithSlash(reserveResponseDetailDto.getPossibleReserveFromDay()));
        // 予約可能終了日
        abstractReserveModel.setPossibleReserveToDay(
                        dateUtility.formatYmdWithSlash(reserveResponseDetailDto.getPossibleReserveToDay()));
    }

    /**
     * 過去注文数量の設定
     *
     * @param abstractReserveModel セールde予約画面Model
     * @param preShipmentOrderHistoryAggregate 注文履歴（過去1年）
     */
    private void setReserveDeliveryGoodsHistoryCount(AbstractReserveModel abstractReserveModel,
                                                     WebApiGetPreShipmentOrderHistoryResponseDto preShipmentOrderHistoryAggregate) {

        // 注文履歴（過去1年）取得APIで取得したデータから、対象商品の数量をサマリする
        Integer reserveDeliveryGoodsHistoryCount = 0;
        for (WebApiGetPreShipmentOrderHistoryResponseDetailDto dto : preShipmentOrderHistoryAggregate.getInfo()) {
            for (WebApiOrderHistoryResponseGoodsInfoDto goodsDetails : dto.getGoodsList()) {
                if (goodsDetails.getGoodsCode().equals(abstractReserveModel.getGcd())) {
                    reserveDeliveryGoodsHistoryCount += goodsDetails.getGoodsCount();
                }
            }
        }

        // 過去注文数量
        abstractReserveModel.setReserveDeliveryGoodsHistoryCount(reserveDeliveryGoodsHistoryCount);
    }

    /**
     * カレンダー選択不可日付情報の設定
     *
     * @param abstractReserveModel セールde予約画面Model
     * @param calendarNotSelectDateEntityList カレンダー選択不可日付
     */
    private void setCalendarNotSelectDateList(AbstractReserveModel abstractReserveModel,
                                              List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList) {

        List<String> calendarNotSelectDateStrList = new ArrayList<>();
        calendarNotSelectDateEntityList.forEach(item -> {
            String dateStr = dateUtility.format(item.getNotPossibleReserveDate(), DateUtility.YMD_SLASH);
            calendarNotSelectDateStrList.add(dateStr);
        });

        // カレンダー選択不可日付List
        abstractReserveModel.setCalendarNotSelectDateList(calendarNotSelectDateStrList);
    }

    /**
     * 最大件数分のセールde予約情報 詳細Itemを追加生成し、初期表示時の行数を設定
     *
     * @param abstractReserveModel セールde予約画面 Model
     */
    public void setDefaultReserveItem(AbstractReserveModel abstractReserveModel) {

        // 入力済のセールde予約情報の件数
        int inputCount = abstractReserveModel.getReserveItem().getReserveDetailItemList().size();

        // デフォルト件数分の詳細Itemを生成
        for (int i = inputCount; i < ReserveItem.DEFAULT_LIMIT_DISPLAY; i++) {
            ReserveDetailItem reserveDetailItem = ApplicationContextUtility.getBean(ReserveDetailItem.class);
            reserveDetailItem.setReserveDeliveryDate(null);
            reserveDetailItem.setInputGoodsCount(ReserveItem.DEFAULT_INPUT_GOODS_COUNT);
            abstractReserveModel.getReserveItem().getReserveDetailItemList().add(reserveDetailItem);
        }

        // 初期表示時の行数を設定
        abstractReserveModel.getReserveItem()
                            .setInitCount(ReserveItem.DEFAULT_INIT_DISPLAY >= inputCount ?
                                                          ReserveItem.DEFAULT_INIT_DISPLAY :
                                                          inputCount);

    }

    /**
     * 入力された情報から引継ぎ用のセールde予約情報Itemを再生成する（未入力行の除去とお届け希望日のソートも行う）
     *
     * @param abstractReserveModel セールde予約画面 Model
     * @return 引継ぎ用のセールde予約情報Item
     */
    public ReserveItem getOrderReserveItem(AbstractReserveModel abstractReserveModel) {

        ReserveItem reserveItem = ApplicationContextUtility.getBean(ReserveItem.class);
        List<ReserveDetailItem> reserveDetailItemList = new ArrayList<>();

        abstractReserveModel.getReserveItem().getReserveDetailItemList().forEach(item -> {
            if (StringUtil.isNotEmpty(item.getReserveDeliveryDate())) {
                // お届け希望日が入力されているデータだけを格納する
                ReserveDetailItem reserveDetailItem = ApplicationContextUtility.getBean(ReserveDetailItem.class);
                reserveDetailItem.setReserveDeliveryDate(item.getReserveDeliveryDate());
                reserveDetailItem.setInputGoodsCount(item.getInputGoodsCount());
                reserveDetailItemList.add(reserveDetailItem);
            }
        });

        // お届け希望日の昇順ソート
        reserveItem.setReserveDetailItemList(reserveDetailItemList.stream()
                                                                  .sorted(Comparator.comparing(
                                                                                  ReserveDetailItem::getReserveDeliveryDate))
                                                                  .collect(Collectors.toList()));
        // 商品コード
        reserveItem.setGoodsCode(abstractReserveModel.getGoodsCode());

        return reserveItem;
    }

    /** ***********　画面項目セット関連 Method End　*********** **/
    /** ***********　APIレスポンス整形関連 Method Start　*********** **/

    /**
     * 取りおき情報取得APIのレスポンスをHM用のDTOクラスに変換
     *
     * @param webApiGetReserveResponse 取りおき情報取得Web-APIレスポンス
     * @return 取りおき情報取得
     */
    public WebApiGetReserveResponseDto toWebApiGetReserveResponseDto(WebApiGetReserveResponse webApiGetReserveResponse) {

        if (ObjectUtils.isEmpty(webApiGetReserveResponse)) {
            return null;
        }

        WebApiGetReserveResponseDto webApiGetReserveResponseDto = new WebApiGetReserveResponseDto();
        webApiGetReserveResponseDto.setResult(toAbstractWebApiResponseResultDto(webApiGetReserveResponse.getResult()));
        webApiGetReserveResponseDto.setInfo(
                        toWebApiGetReserveResponseDetailDtoList(webApiGetReserveResponse.getInfo()));

        return webApiGetReserveResponseDto;
    }

    /**
     * WEB-API連携取得結果DTO共通情報クラスに変換
     *
     * @param abstractWebApiResponseResultDtoResponse API連携取得結果DTO共通情報レスポンス
     * @return WEB-API連携取得結果DTO共通情報クラス
     */
    private AbstractWebApiResponseResultDto toAbstractWebApiResponseResultDto(AbstractWebApiResponseResultDtoResponse abstractWebApiResponseResultDtoResponse) {

        if (ObjectUtils.isEmpty(abstractWebApiResponseResultDtoResponse)) {
            return null;
        }

        AbstractWebApiResponseResultDto abstractWebApiResponseResultDto = new AbstractWebApiResponseResultDto();

        abstractWebApiResponseResultDto.setMessage(abstractWebApiResponseResultDtoResponse.getMessage());
        abstractWebApiResponseResultDto.setStatus(abstractWebApiResponseResultDtoResponse.getStatus());

        return abstractWebApiResponseResultDto;
    }

    /**
     * 取りおき情報取得(詳細情報)リストに変換
     *
     * @param webApiGetReserveResponseDetailDtoResponseList 取りおき情報取得 詳細情報
     * @return 取りおき情報取得(詳細情報)リスト
     */
    private List<WebApiGetReserveResponseDetailDto> toWebApiGetReserveResponseDetailDtoList(List<WebApiGetReserveResponseDetailDtoResponse> webApiGetReserveResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetReserveResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetReserveResponseDetailDto> webApiGetReserveResponseDetailDtoList = new ArrayList<>();

        webApiGetReserveResponseDetailDtoResponseList.forEach(item -> {
            WebApiGetReserveResponseDetailDto webApiGetReserveResponseDetailDto =
                            new WebApiGetReserveResponseDetailDto();
            webApiGetReserveResponseDetailDto.setGoodsCode(item.getGoodsCode());
            webApiGetReserveResponseDetailDto.setReserveFlag(item.getReserveFlag());
            webApiGetReserveResponseDetailDto.setDiscountFlag(item.getDiscountFlag());
            webApiGetReserveResponseDetailDto.setPossibleReserveFromDay(
                            conversionUtility.toTimeStamp(item.getPossibleReserveFromDay()));
            webApiGetReserveResponseDetailDto.setPossibleReserveToDay(
                            conversionUtility.toTimeStamp(item.getPossibleReserveToDay()));
            webApiGetReserveResponseDetailDtoList.add(webApiGetReserveResponseDetailDto);
        });

        return webApiGetReserveResponseDetailDtoList;
    }

    /**
     * カレンダー選択不可日付EntityListに変換
     *
     * @param calendarNotSelectDateEntityResponseList カレンダー選択不可日付取得レスポンス
     * @return カレンダー選択不可日付EntityList
     */
    public List<CalendarNotSelectDateEntity> toCalendarNotSelectDateEntityList(List<CalendarNotSelectDateEntityResponse> calendarNotSelectDateEntityResponseList) {

        if (CollectionUtil.isEmpty(calendarNotSelectDateEntityResponseList)) {
            return new ArrayList<>();
        }

        List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList = new ArrayList<>();
        calendarNotSelectDateEntityResponseList.forEach(item -> {
            CalendarNotSelectDateEntity calendarNotSelectDateEntity = new CalendarNotSelectDateEntity();
            calendarNotSelectDateEntity.setNotPossibleReserveDate(
                            conversionUtility.toTimeStamp(item.getNotPossibleReserveDate()));
            calendarNotSelectDateEntityList.add(calendarNotSelectDateEntity);
        });

        return calendarNotSelectDateEntityList;

    }

    /** ***********　APIレスポンス整形関連 Method End　*********** **/

}
// 2023-renew No14 to here
