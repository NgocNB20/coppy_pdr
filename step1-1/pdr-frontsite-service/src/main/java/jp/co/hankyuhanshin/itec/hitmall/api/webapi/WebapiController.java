package jp.co.hankyuhanshin.itec.hitmall.api.webapi;

import io.swagger.annotations.ApiParam;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiCouponCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiCouponCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiDelDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetNotYetShippingOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetOtherGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryAggregateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPreShipmentOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetShipmentDateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetQuantityDiscountRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetRestockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiDelDestinationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiGetDestinationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddCouponRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddCouponResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCouponCheckRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCouponCheckResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetCouponListRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetCouponListResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetNotYetShippingOrderHistoryRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPreShipmentOrderHistoryRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPurchasedCommodityInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentDateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentDateResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiAddCouponLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiAddDestinationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiCouponCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiDelDestinationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetCouponListLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetDestinationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetDiscountsResultLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetNotYetShippingOrderHistoryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetOtherGoodsLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPreShipmentOrderHistoryAggregateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPreShipmentOrderHistoryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPurchasedCommodityInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetQuantityDiscountLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReStockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetSaleCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetShipmentDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetStockLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WebapiController implements WebapiApi {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebapiController.class);

    /**
     * WEB-APIHelper
     */
    private final WebapiHelper webapiHelper;

    /**
     * WEB-API連携クラス
     * お届け先削除
     */
    private final WebApiDelDestinationLogic webApiDelDestinationLogic;

    /**
     * WEB-API連携クラス
     * お届け先参照
     */
    private final WebApiGetDestinationLogic webApiGetDestinationLogic;

    /**
     * WEB-API連携クラス
     * 割引適用結果取得
     */
    private final WebApiGetDiscountsResultLogic webApiGetDiscountsResultLogic;

    /**
     * WEB-API連携クラス
     * 注文履歴（未配送）取得
     */
    private final WebApiGetNotYetShippingOrderHistoryLogic webApiGetNotYetShippingOrderHistoryLogic;

    /**
     * WEB-API連携クラス
     * 注文履歴（出荷済）取得
     */
    private final WebApiGetPreShipmentOrderHistoryLogic webApiGetPreShipmentOrderHistoryLogic;

    /**
     * WEB-API連携クラス
     * 注文履歴（過去1年）取得
     */
    private final WebApiGetPreShipmentOrderHistoryAggregateLogic webApiGetPreShipmentOrderHistoryAggregateLogic;

    /**
     * WEB-API連携クラス
     * 数量割引情報取得
     */
    private final WebApiGetQuantityDiscountLogic webApiGetQuantityDiscountLogic;

    /**
     * WEB-API連携クラス
     * 購入済み商品情報取得
     */
    private final WebApiGetPurchasedCommodityInformationLogic webApiGetPurchasedCommodityInformationLogic;

    /**
     * WEB-API連携クラス
     * 商品在庫数取得
     */
    private final WebApiGetStockLogic webApiGetStockLogic;

    /**
     * WEB-API連携クラス
     */
    private final WebApiAddDestinationLogic webApiAddDestinationLogic;

    /**
     * WEB-API連携クラス
     * クーポン有効性チェック
     */
    private final WebApiCouponCheckLogic webApiCouponCheckLogic;

    // 2023-renew No11 from here
    /**
     * WEB-API連携クラス
     * 販売可否判定
     */
    private final WebApiGetSaleCheckLogic webApiGetSaleCheckLogic;
    // 2023-renew No11 to here

    // 2023-renew No14 from here
    /**
     * WEB-API連携クラス
     * 取りおき情報取得
     */
    private final WebApiGetReserveLogic webApiGetReserveLogic;

    /**
     * WEB-API連携クラス
     * 出荷予定日取得
     */
    private final WebApiGetShipmentDateLogic webApiGetShipmentDateLogic;
    // 2023-renew No14 to here

    // 2023-renew No24 from here
    /**
     * WEB-API連携クラス
     * クーポン取得
     */
    private final WebApiAddCouponLogic webApiAddCouponLogic;

    /**
     * WEB-API連携クラス
     * 利用可能クーポン一覧取得
     */
    private final WebApiGetCouponListLogic webApiGetCouponListLogic;
    // 2023-renew No24 to here

    // 2023-renew No52 from here
    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;
    // 2023-renew No52 to here

    // 2023-renew No65 from here
    /**
     * WEB-API商品入荷情報取得
     * 商品入荷情報取得
     */
    private final WebApiGetReStockLogic webApiGetRestockLogic;
    // 2023-renew No65 to here

    // 2023-renew No92 from here
    /**
     * WEB-API連携クラス
     * 後継品代替品情報取得
     */
    private final WebApiGetOtherGoodsLogic webApiGetOtherGoodsLogic;
    // 2023-renew No92 to here

    @Autowired
    public WebapiController(WebapiHelper webapiHelper,
                            WebApiDelDestinationLogic webApiDelDestinationLogic,
                            WebApiGetDestinationLogic webApiGetDestinationLogic,
                            WebApiGetDiscountsResultLogic webApiGetDiscountsResultLogic,
                            WebApiGetNotYetShippingOrderHistoryLogic webApiGetNotYetShippingOrderHistoryLogic,
                            WebApiGetPreShipmentOrderHistoryLogic webApiGetPreShipmentOrderHistoryLogic,
                            WebApiGetPreShipmentOrderHistoryAggregateLogic webApiGetPreShipmentOrderHistoryAggregateLogic,
                            WebApiGetQuantityDiscountLogic webApiGetQuantityDiscountLogic,
                            WebApiGetPurchasedCommodityInformationLogic webApiGetPurchasedCommodityInformationLogic,
                            WebApiGetStockLogic webApiGetStockLogic,
                            WebApiAddDestinationLogic webApiAddDestinationLogic,
                            WebApiCouponCheckLogic webApiCouponCheckLogic,
                            WebApiGetReserveLogic webApiGetReserveLogic,
                            WebApiGetShipmentDateLogic webApiGetShipmentDateLogic,
                            WebApiAddCouponLogic webApiAddCouponLogic,
                            WebApiGetCouponListLogic webApiGetCouponListLogic,
                            WebApiGetSaleCheckLogic webApiGetSaleCheckLogic,
                            WebApiGetOtherGoodsLogic webApiGetOtherGoodsLogic,
                            WebApiGetReStockLogic webApiGetRestockLogic,
                            ConversionUtility conversionUtility) {
        this.webapiHelper = webapiHelper;
        this.webApiDelDestinationLogic = webApiDelDestinationLogic;
        this.webApiGetDestinationLogic = webApiGetDestinationLogic;
        this.webApiGetDiscountsResultLogic = webApiGetDiscountsResultLogic;
        this.webApiGetNotYetShippingOrderHistoryLogic = webApiGetNotYetShippingOrderHistoryLogic;
        this.webApiGetPreShipmentOrderHistoryLogic = webApiGetPreShipmentOrderHistoryLogic;
        this.webApiGetPreShipmentOrderHistoryAggregateLogic = webApiGetPreShipmentOrderHistoryAggregateLogic;
        this.webApiGetQuantityDiscountLogic = webApiGetQuantityDiscountLogic;
        this.webApiGetPurchasedCommodityInformationLogic = webApiGetPurchasedCommodityInformationLogic;
        this.webApiGetStockLogic = webApiGetStockLogic;
        this.webApiAddDestinationLogic = webApiAddDestinationLogic;
        this.webApiCouponCheckLogic = webApiCouponCheckLogic;
        // 2023-renew No11 from here
        this.webApiGetSaleCheckLogic = webApiGetSaleCheckLogic;
        // 2023-renew No11 to here
        // 2023-renew No14 from here
        this.webApiGetReserveLogic = webApiGetReserveLogic;
        this.webApiGetShipmentDateLogic = webApiGetShipmentDateLogic;
        // 2023-renew No14 to here
        // 2023-renew No24 from here
        this.webApiAddCouponLogic = webApiAddCouponLogic;
        this.webApiGetCouponListLogic = webApiGetCouponListLogic;
        // 2023-renew No24 to here
        // 2023-renew No52 from here
        this.conversionUtility = conversionUtility;
        // 2023-renew No52 to here
        // 2023-renew No65 from here
        this.webApiGetRestockLogic = webApiGetRestockLogic;
        // 2023-renew No65 to here
        // 2023-renew No92 from here
        this.webApiGetOtherGoodsLogic = webApiGetOtherGoodsLogic;
        // 2023-renew No92 to here
    }

    /**
     * DELETE /webapi/destinaltion : WEB-API連携お届け先削除
     * WEB-API連携お届け先削除
     *
     * @param webApiDelDestinationRequest WEB-API連携お届け先削除リクエスト (required)
     * @return WEB-API連携お届け先削除レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<AbstractWebApiResponseResultDtoResponse> deleteDestinaltion(@Valid WebApiDelDestinationRequest webApiDelDestinationRequest) {

        WebApiDelDestinationRequestDto reqDto = ApplicationContextUtility.getBean(WebApiDelDestinationRequestDto.class);
        reqDto.setCustomerNo(webApiDelDestinationRequest.getCustomerNo());
        reqDto.setReceiveCustomerNo(webApiDelDestinationRequest.getReceiveCustomerNo());

        AbstractWebApiResponseDto webApiResponseDto = webApiDelDestinationLogic.execute(reqDto);

        AbstractWebApiResponseResultDtoResponse response = new AbstractWebApiResponseResultDtoResponse();
        response.setStatus(webApiResponseDto.getResult().getStatus());
        response.setMessage(webApiResponseDto.getResult().getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/destination : WEB-API連携お届け先参照
     * WEB-API連携お届け先参照
     *
     * @param webApiGetDestinationRequest WEB-API連携お届け先参照リクエスト (optional)
     * @return WEB-API連携お届け先参照レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetDestinationResponse> getDestination(@Valid WebApiGetDestinationRequest webApiGetDestinationRequest) {
        WebApiGetDestinationRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetDestinationRequestDto.class);
        reqDto.setCustomerNo(webApiGetDestinationRequest.getCustomerNo());

        AbstractWebApiResponseDto webApiResponseDto = webApiGetDestinationLogic.execute(reqDto);

        WebApiGetDestinationResponse response = webapiHelper.toWebApiGetDestinationResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/discount-result : WEB-API連携割引適用結果取得
     * WEB-API連携割引適用結果取得
     *
     * @param webApiGetDiscountsResultRequest WEB-API連携割引適用結果取得リクエスト (optional)
     * @return WEB-API連携割引適用結果取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetDiscountsResultResponse> getDiscountResult(@Valid WebApiGetDiscountsResultRequest webApiGetDiscountsResultRequest) {
        WebApiGetDiscountsRequestDto reqDto =
                        webapiHelper.toWebApiGetDiscountsRequestDto(webApiGetDiscountsResultRequest);

        AbstractWebApiResponseDto webApiResponseDto = webApiGetDiscountsResultLogic.execute(reqDto);

        WebApiGetDiscountsResultResponse response = webapiHelper.toWebApiGetDiscountsResultResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/not-yet-shipping-order-history : WEB-API連携注文履歴（未出荷）取得
     * 注文履歴（未出荷）取得
     *
     * @param webApiGetNotYetShippingOrderHistoryRequest WEB-API連携注文履歴（未出荷）取得リクエスト (optional)
     * @return 注文履歴（未出荷）取得Web-APIレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetNotYetShippingOrderHistoryResponse> getNotYetShippingOrderHistory(@Valid WebApiGetNotYetShippingOrderHistoryRequest webApiGetNotYetShippingOrderHistoryRequest) {
        WebApiGetNotYetShippingOrderHistoryRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetNotYetShippingOrderHistoryRequestDto.class);
        reqDto.setCustomerNo(webApiGetNotYetShippingOrderHistoryRequest.getCustomerNo());

        AbstractWebApiResponseDto webApiResponseDto = webApiGetNotYetShippingOrderHistoryLogic.execute(reqDto);

        WebApiGetNotYetShippingOrderHistoryResponse response =
                        webapiHelper.toWebApiGetNotYetShippingOrderHistoryResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/pre-shipment-order-history : WEB-API連携注文履歴（出荷済）取得
     * WEB-API連携注文履歴（出荷済）取得
     *
     * @param webApiGetPreShipmentOrderHistoryRequest 注WEB-API連携文履歴（出荷済）取得リクエスト (optional)
     * @return 注文履歴（出荷済）取得Web-APIレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetPreShipmentOrderHistoryResponse> getPreShipmentOrderHistory(@Valid WebApiGetPreShipmentOrderHistoryRequest webApiGetPreShipmentOrderHistoryRequest) {
        WebApiGetPreShipmentOrderHistoryRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetPreShipmentOrderHistoryRequestDto.class);
        reqDto.setCustomerNo(webApiGetPreShipmentOrderHistoryRequest.getCustomerNo());
        // 2023-renew No52 from here
        reqDto.setSearchStartDay(
                        conversionUtility.toTimeStamp(webApiGetPreShipmentOrderHistoryRequest.getSearchStartDay()));
        reqDto.setSearchEndDay(
                        conversionUtility.toTimeStamp(webApiGetPreShipmentOrderHistoryRequest.getSearchEndDay()));
        // 2023-renew No52 to here

        AbstractWebApiResponseDto webApiResponseDto = webApiGetPreShipmentOrderHistoryLogic.execute(reqDto);

        WebApiGetPreShipmentOrderHistoryResponse response =
                        webapiHelper.toWebApigetPreShipmentOrderHistoryResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/get-pre-shipment-order-history-aeggregate : WEB-API連携注文履歴（過去1年）取得
     * WEB-API連携注文履歴（過去1年）取得
     *
     * @param webApiGetPreShipmentOrderHistoryAggregateRequest WEB-API連携注文履歴（過去1年）取得リクエスト (optional)
     * @return WEB-API連携注文履歴（過去1年）取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetPreShipmentOrderHistoryAggregateResponse> getPreShipmentOrderHistoryAeggregate(@Valid WebApiGetPreShipmentOrderHistoryAggregateRequest webApiGetPreShipmentOrderHistoryAggregateRequest) {
        WebApiGetPreShipmentOrderHistoryRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetPreShipmentOrderHistoryRequestDto.class);
        reqDto.setCustomerNo(webApiGetPreShipmentOrderHistoryAggregateRequest.getCustomerNo());

        AbstractWebApiResponseDto webApiResponseDto = webApiGetPreShipmentOrderHistoryAggregateLogic.execute(reqDto);

        WebApiGetPreShipmentOrderHistoryAggregateResponse response =
                        webapiHelper.toWebApigetPreShipmentOrderHistoryAggregateResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/quantity-discount : WEB-API連携数量割引情報取得
     * WEB-API連携数量割引情報取得
     *
     * @param webApiGetQuantityDiscountRequest 数量割引情報取得リクエスト (optional)
     * @return 数量割引情報取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetQuantityDiscountResponse> getQuantityDiscount(@Valid WebApiGetQuantityDiscountRequest webApiGetQuantityDiscountRequest) {
        WebApiGetQuantityDiscountRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetQuantityDiscountRequestDto.class);
        reqDto.setCustomerNo(webApiGetQuantityDiscountRequest.getCustomerNo() != null ?
                                             webApiGetQuantityDiscountRequest.getCustomerNo() :
                                             0);
        reqDto.setGoodsCode(webApiGetQuantityDiscountRequest.getGoodsCode());

        AbstractWebApiResponseDto webApiResponseDto = webApiGetQuantityDiscountLogic.execute(reqDto);

        WebApiGetQuantityDiscountResponse response =
                        webapiHelper.toWebApiGetQuantityDiscountResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/stock : WEB-API連携商品在庫数取得
     * WEB-API連携商品在庫数取得
     *
     * @param webApiGetStockRequest 商品在庫数取得リクエスト (optional)
     * @return 商品在庫数取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetStockResponse> getStock(@Valid WebApiGetStockRequest webApiGetStockRequest) {
        WebApiGetStockRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetStockRequestDto.class);
        reqDto.setGoodsCode(webApiGetStockRequest.getGoodsCode());
        reqDto.setCustomerNo(webApiGetStockRequest.getCustomerNo());
        reqDto.setQuantity(webApiGetStockRequest.getQuantity());

        AbstractWebApiResponseDto webApiResponseDto = webApiGetStockLogic.execute(reqDto);

        WebApiGetStockResponse response = webapiHelper.toWebApiGetStockResponse(webApiResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/purchased-commodity-information : 購入済み商品情報取得
     * 購入済み商品情報取得
     *
     * @param webApiGetPurchasedCommodityInformationRequest WEB-API連携 購入済み商品情報取得リクエスト (optional)
     * @return WEB-API連携 購入済み商品情報レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetPurchasedCommodityInformationResponse> getPurchasedCommodityInformation(@Valid WebApiGetPurchasedCommodityInformationRequest webApiGetPurchasedCommodityInformationRequest) {
        WebApiGetPurchasedCommodityInformationRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetPurchasedCommodityInformationRequestDto.class);
        reqDto.setCustomerNo(webApiGetPurchasedCommodityInformationRequest.getCustomerNo());

        WebApiGetPurchasedCommodityInformationResponseDto purchasedCommodityInformationDto = null;
        try {
            purchasedCommodityInformationDto =
                            (WebApiGetPurchasedCommodityInformationResponseDto) webApiGetPurchasedCommodityInformationLogic.execute(
                                            reqDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        WebApiGetPurchasedCommodityInformationResponse purchasedCommodityResponse =
                        webapiHelper.toWebApiGetPurchasedCommodityInformationResponse(purchasedCommodityInformationDto);

        return new ResponseEntity<>(purchasedCommodityResponse, HttpStatus.OK);
    }

    /**
     * GET /webapi/add-destination : WEB-API連携 お届け先登録
     * WEB-API連携 お届け先登録
     *
     * @param webApiAddDestinationRequest WEB-API連携 お届け先登録リクエスト (optional)
     * @return WEB-API連携 お届け先登録レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiAddDestinationResponse> getAddDestination(@ApiParam(value = "WEB-API連携 お届け先登録リクエスト")
                                                                          @Valid WebApiAddDestinationRequest webApiAddDestinationRequest) {

        AbstractWebApiResponseDto webApiResponseDto = webApiAddDestinationLogic.execute(
                        webapiHelper.toAbstractWebApiRequestDto(webApiAddDestinationRequest));

        WebApiAddDestinationResponse webApiAddDestinationResponse =
                        webapiHelper.toWebApiAddDestinationResponse(webApiResponseDto);

        return new ResponseEntity<>(webApiAddDestinationResponse, HttpStatus.OK);
    }

    /**
     * POST /webapi/coupon-check : WEB-API連携 クーポン有効性チェック
     * WEB-API連携 クーポン有効性チェック
     *
     * @param webApiCouponCheckRequest WEB-API連携クーポン有効性チェックリクエスト (required)
     * @return WEB-API連携クーポン有効性チェックレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiCouponCheckResponse> checkCoupon(@Valid WebApiCouponCheckRequest webApiCouponCheckRequest) {

        // Web-API リクエストDTO
        WebApiCouponCheckRequestDto reqDto = ApplicationContextUtility.getBean(WebApiCouponCheckRequestDto.class);
        reqDto.setCouponCode(webApiCouponCheckRequest.getCouponCode());

        // WEB-API実行
        WebApiCouponCheckResponseDto couponCheckResponseDto;

        try {
            couponCheckResponseDto = (WebApiCouponCheckResponseDto) webApiCouponCheckLogic.execute(reqDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        WebApiCouponCheckResponse response = webapiHelper.toWebApiCouponCheckResponse(couponCheckResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No14 from here

    /**
     * GET /webapi/get-reserve : WEB-API連携 取りおき情報取得
     * WEB-API連携 取りおき情報取得
     *
     * @param webApiGetReserveRequest WEB-API連携取りおき情報取得リクエスト (required)
     * @return WEB-API連携取りおき情報取得レスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetReserveResponse> getReserve(@Valid WebApiGetReserveRequest webApiGetReserveRequest) {

        // WEB-API実行
        WebApiGetReserveRequestDto reqDto = webapiHelper.toWebApiGetReserveRequestDto(webApiGetReserveRequest);

        // WEB-API実行
        WebApiGetReserveResponseDto resDto = (WebApiGetReserveResponseDto) webApiGetReserveLogic.execute(reqDto);

        WebApiGetReserveResponse response = webapiHelper.toWebApiGetReserveResponse(resDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /webapi/get-shipment-date : WEB-API連携 出荷予定日取得
     * WEB-API連携 出荷予定日取得
     *
     * @param webApiGetShipmentDateRequest WEB-API連携出荷予定日取得リクエスト (required)
     * @return WEB-API連携前出荷予定日取得レスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetShipmentDateResponse> getShipmentDate(@Valid WebApiGetShipmentDateRequest webApiGetShipmentDateRequest) {

        // Web-API リクエストDTO
        WebApiGetShipmentDateRequestDto reqDto =
                        webapiHelper.toWebApiGetShipmentDateRequestDto(webApiGetShipmentDateRequest);

        // WEB-API実行
        WebApiGetShipmentDateResponseDto getShipmentDateResponseDto =
                        (WebApiGetShipmentDateResponseDto) webApiGetShipmentDateLogic.execute(reqDto);

        WebApiGetShipmentDateResponse response =
                        webapiHelper.toWebApiGetShipmentDateResponse(getShipmentDateResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No14 to here

    // 2023-renew No24 from here

    /**
     * POST /webapi/add-coupon : WEB-API連携 クーポン取得
     * WEB-API連携 クーポン取得
     *
     * @param webApiAddCouponRequest WEB-API連携クーポン取得リクエスト (required)
     * @return WEB-API連携クーポン取得レスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiAddCouponResponse> addCoupon(@Valid WebApiAddCouponRequest webApiAddCouponRequest) {

        // Web-API リクエストDTO
        WebApiAddCouponRequestDto reqDto = ApplicationContextUtility.getBean(WebApiAddCouponRequestDto.class);
        reqDto.setCustomerNo(webApiAddCouponRequest.getCustomerNo());
        reqDto.setCouponNo(webApiAddCouponRequest.getCouponNo());

        // WEB-API実行
        WebApiAddCouponResponseDto addCouponResponseDto =
                        (WebApiAddCouponResponseDto) webApiAddCouponLogic.execute(reqDto);

        WebApiAddCouponResponse response = webapiHelper.toWebApiAddCouponResponse(addCouponResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /webapi/get-coupon-list : WEB-API連携 利用可能クーポン一覧取得
     * WEB-API連携 利用可能クーポン一覧取得
     *
     * @param webApiGetCouponListRequest WEB-API連携利用可能クーポン一覧取得リクエスト (optional)
     * @return WEB-API連携利用可能クーポン一覧取得レスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetCouponListResponse> getCouponList(@Valid WebApiGetCouponListRequest webApiGetCouponListRequest) {

        // Web-API リクエストDTO
        WebApiGetCouponListRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetCouponListRequestDto.class);
        reqDto.setCustomerNo(webApiGetCouponListRequest.getCustomerNo());

        // WEB-API実行
        WebApiGetCouponListResponseDto getCouponListResponseDto =
                        (WebApiGetCouponListResponseDto) webApiGetCouponListLogic.execute(reqDto);

        WebApiGetCouponListResponse response = webapiHelper.toWebApiGetCouponListResponse(getCouponListResponseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No24 to here

    // 2023-renew No11 from here

    /**
     * POST /webapi/sale-check : WEB-API販売可否判定チェック
     * WEB-API連携 販売可否判定チェック
     *
     * @param webApiGetSaleCheckRequest WEB-API販売可否判定チェックリクエスト (required)
     * @return WEB-API販売可否判定チェックレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetSaleCheckResponse> getSaleCheck(@Valid WebApiGetSaleCheckRequest webApiGetSaleCheckRequest) {
        WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = null;
        try {
            AbstractWebApiResponseDto webApiResponseDto = webApiGetSaleCheckLogic.execute(
                            webapiHelper.toAbstractWebApiRequestDto(webApiGetSaleCheckRequest));
            webApiGetSaleCheckResponse = webapiHelper.toWebApiGetSaleCheckResponse(webApiResponseDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        return new ResponseEntity<>(webApiGetSaleCheckResponse, HttpStatus.OK);
    }

    // 2023-renew No11 to here

    // 2023-renew No92 from here

    /**
     * POST /webapi/othergoods : WEB-API後継品代替品情報取得
     * WEB-API連携 後継品代替品情報取得
     *
     * @param webApiGetOtherGoodsRequest WEB-API後継品代替品情報取得リクエスト (required)
     * @return WEB-API後継品代替品情報取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<WebApiGetOtherGoodsResponse> getOtherGoods(@Valid WebApiGetOtherGoodsRequest webApiGetOtherGoodsRequest) {
        WebApiGetOtherGoodsResponse webApiGetOtherGoodsResponse = null;
        try {
            AbstractWebApiResponseDto webApiResponseDto = webApiGetOtherGoodsLogic.execute(
                            webapiHelper.toAbstractWebApiRequestDto(webApiGetOtherGoodsRequest));
            webApiGetOtherGoodsResponse = webapiHelper.toWebApiGetOtherGoodsResponse(webApiResponseDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        return new ResponseEntity<>(webApiGetOtherGoodsResponse, HttpStatus.OK);
    }

    // 2023-renew No92 to here

}
