package jp.co.hankyuhanshin.itec.hitmall.api.order;

import io.swagger.annotations.ApiParam;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.BillPriceCalculateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.BillPriceCalculateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConsumptionTaxRateGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConsumptionTaxRateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.ConvenienceEntityListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.DeliveryMethodSelectListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.NewOrderNoSeqResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderBeforePaymentRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderBeforePaymentResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderCompleteMailSendRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderDeliveryInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetReserveMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetReserveRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetStockMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderInfoMasterGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderPromotionInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderPromotionInformationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.OrderScreenRegistResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementMethodSelectListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.order.param.SettlementMethodSelectListResponse;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderBeforePaymentDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiAddDestinationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoRegistByNewAddressLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.AddOrderInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ConvenienceLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderNoSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBeforePaymentLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetConsumptionTaxRateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetStockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPromotionInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderQuantityDiscountLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderRegisterLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiAddDestinationLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.DeliveryMethodSelectListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderCompleteMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderInfoMasterGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.SettlementMethodSelectListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.AsyncTaskUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.web.SessionParamsUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注文Controllerクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RestController
public class OrderController extends AbstractController implements OrderApi {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    /**
     * 注文Helperクラス
     */
    private final OrderHelper orderHelper;

    /**
     * コンビニ名称リスト取得ロジック
     */
    private final ConvenienceLogic convenienceLogic;

    /**
     * 請求金額算出処理
     */
    private final BillPriceCalculateLogic billPriceCalculateLogic;

    /**
     * 決済方法選択可能リスト取得サービス
     */
    private final SettlementMethodSelectListGetService settlementMethodSelectListGetService;

    /**
     * 注文可能チェックサービス
     */
    private final OrderCheckLogic orderCheckLogic;

    /**
     * 受注連携ロジッククラス
     */
    private final AddOrderInformationLogic addOrderInformationLogic;

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * 受注番号SEQ 取得ロジック
     */
    private final NewOrderNoSeqGetLogic newOrderNoSeqGetLogic;

    /**
     * 注文登録サービス
     */
    private final OrderRegisterLogic orderRegisterLogic;

    /**
     * 注文完了メール送信サービス
     */
    private final OrderCompleteMailSendService orderCompleteMailSendService;

    /**
     * 新しいお届け先 会員登録サービス実装
     */
    private final MemberInfoRegistByNewAddressLogic memberInfoRegistByNewAddressLogic;

    /**
     * WEB-API連携お届け先登録
     */
    private final WebApiAddDestinationLogic webApiAddDestinationLogic;

    /**
     * 注文情報マスタ取得Service
     */
    private final OrderInfoMasterGetService orderInfoMasterGetService;

    /**
     * WEB-API連携 配送情報取得ロジッククラス
     */
    private final OrderDeliveryInformationLogic orderDeliveryInformationLogic;

    /**
     * 配送方法選択可能リスト取得サービス
     */
    private final DeliveryMethodSelectListGetService deliveryMethodSelectListGetService;

    /**
     * 商品在庫情報ユーティリティクラス
     */
    private final OrderGetReserveLogic orderGetReserveLogic;

    /**
     * 商品在庫情報ユーティリティクラス
     */
    private final OrderGetStockLogic orderGetStockLogic;

    /**
     * 消費税率取得ロジック
     */
    private final OrderGetConsumptionTaxRateLogic orderGetConsumptionTaxRateLogic;

    /**
     * 追加方法：お届け先情報入力から追加
     */
    public static final String ADD_TYPE_RECEIVER = "1";

    /**
     * お届け先追加チェック
     */
    private Boolean addDestinationCheck = true;

    /**
     * プロモーション連携ロジッククラス
     */
    private final OrderPromotionInformationLogic orderPromotionInformationLogic;

    /**
     * 数量割引取得結果連携ロジッククラス
     */
    private final OrderQuantityDiscountLogic orderQuantityDiscountLogic;

    /**
     * 非同期用共通サービス
     */
    private final AsyncService asyncService;

    // 2023-renew No14 from here
    /**
     * 前回支払方法取得ロジッククラス
     */
    private final OrderBeforePaymentLogic orderBeforePaymentLogic;
    // 2023-renew No14 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public OrderController(OrderHelper orderHelper,
                           ConvenienceLogic convenienceLogic,
                           BillPriceCalculateLogic billPriceCalculateLogic,
                           SettlementMethodSelectListGetService settlementMethodSelectListGetService,
                           OrderCheckLogic orderCheckLogic,
                           AddOrderInformationLogic addOrderInformationLogic,
                           DateUtility dateUtility,
                           NewOrderNoSeqGetLogic newOrderNoSeqGetLogic,
                           OrderRegisterLogic orderRegisterLogic,
                           OrderCompleteMailSendService orderCompleteMailSendService,
                           MemberInfoRegistByNewAddressLogic memberInfoRegistByNewAddressLogic,
                           WebApiAddDestinationLogic webApiAddDestinationLogic,
                           OrderInfoMasterGetService orderInfoMasterGetService,
                           OrderDeliveryInformationLogic orderDeliveryInformationLogic,
                           DeliveryMethodSelectListGetService deliveryMethodSelectListGetService,
                           OrderGetReserveLogic orderGetReserveLogic,
                           OrderGetStockLogic orderGetStockLogic,
                           OrderGetConsumptionTaxRateLogic orderGetConsumptionTaxRateLogic,
                           OrderPromotionInformationLogic orderPromotionInformationLogic,
                           OrderQuantityDiscountLogic orderQuantityDiscountLogic,
                           AsyncService asyncService,
                           OrderBeforePaymentLogic orderBeforePaymentLogic) {
        this.orderHelper = orderHelper;
        this.convenienceLogic = convenienceLogic;
        this.billPriceCalculateLogic = billPriceCalculateLogic;
        this.settlementMethodSelectListGetService = settlementMethodSelectListGetService;
        this.orderCheckLogic = orderCheckLogic;
        this.addOrderInformationLogic = addOrderInformationLogic;
        this.dateUtility = dateUtility;
        this.newOrderNoSeqGetLogic = newOrderNoSeqGetLogic;
        this.orderRegisterLogic = orderRegisterLogic;
        this.orderCompleteMailSendService = orderCompleteMailSendService;
        this.memberInfoRegistByNewAddressLogic = memberInfoRegistByNewAddressLogic;
        this.webApiAddDestinationLogic = webApiAddDestinationLogic;
        this.orderInfoMasterGetService = orderInfoMasterGetService;
        this.orderDeliveryInformationLogic = orderDeliveryInformationLogic;
        this.deliveryMethodSelectListGetService = deliveryMethodSelectListGetService;
        this.orderGetReserveLogic = orderGetReserveLogic;
        this.orderGetStockLogic = orderGetStockLogic;
        this.orderGetConsumptionTaxRateLogic = orderGetConsumptionTaxRateLogic;
        this.orderPromotionInformationLogic = orderPromotionInformationLogic;
        this.orderQuantityDiscountLogic = orderQuantityDiscountLogic;
        this.asyncService = asyncService;
        // 2023-renew No14 from here
        this.orderBeforePaymentLogic = orderBeforePaymentLogic;
        // 2023-renew No14 to here
    }

    /**
     * POST /order/check : 注文可能チェック
     * 注文可能チェック
     *
     * @param orderCheckRequest 注文可能チェックリクエスト (required)
     * @return 注文メッセージDtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderMessageDtoResponse> check(
                    @ApiParam(value = "注文可能チェックリクエスト", required = true) @Valid @RequestBody
                                    OrderCheckRequest orderCheckRequest) {

        ReceiveOrderDto receiveOrderDto = orderHelper.toReceiveOrderDto(orderCheckRequest.getReceiveOrderDto());

        OrderMessageDto orderMessageDto = orderCheckLogic.execute(receiveOrderDto, orderCheckRequest.getMemberInfoId(),
                                                                  HTypeSiteType.FRONT_PC,
                                                                  orderCheckRequest.getMemberInfoSeq(),
                                                                  orderCheckRequest.getAdminId()
                                                                 );

        OrderMessageDtoResponse response = orderHelper.toOrderMessageDtoResponse(orderMessageDto);
        response.setReceiveOrderDto(orderHelper.toReceiveOrderDtoJS(receiveOrderDto));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * GET /order/convenience : コンビニ取得
     * コンビニ取得
     *
     * @return コンビニEntityListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ConvenienceEntityListResponse> getConvenience() {

        List<ConvenienceEntity> convenienceEntityList = convenienceLogic.getConvenienceList();

        ConvenienceEntityListResponse response = orderHelper.toConvenienceEntityListResponse(convenienceEntityList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /order/delivery-method-select-list : 配送方法選択可能リスト取得
     * 配送方法選択可能リスト取得
     *
     * @param deliveryMethodSelectListGetRequest 配送方法選択可能リスト取得リクエスト (optional)
     * @return 配送Dtoリストレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    @SneakyThrows
    public ResponseEntity<DeliveryDtoListResponse> getDeliveryMethodSelectList(@ApiParam(value = "配送方法選択可能リスト取得リクエスト")
                                                                               @Valid DeliveryMethodSelectListGetRequest deliveryMethodSelectListGetRequest) {

        ReceiveOrderDto receiveOrderDto =
                        orderHelper.toReceiveOrderDto(deliveryMethodSelectListGetRequest.getReceiveOrderDto());

        List<DeliveryDto> deliveryDtoList =
                        deliveryMethodSelectListGetService.execute(receiveOrderDto, true, HTypeSiteType.FRONT_PC);

        DeliveryDtoListResponse response = orderHelper.toDeliveryDtoListResponse(deliveryDtoList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /order/order-delivery-information/get : WEB-API連携 配送情報取得
     * WEB-API連携 配送情報取得
     *
     * @param orderDeliveryInformationRequest 休業日の配送制御リクエスト (required)
     * @return 休業日の配送制御Dtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderDeliveryInformationDtoResponse> getOrderDeliveryInformation(
                    @ApiParam(value = "配送情報取得リクエスト", required = true) @Valid @RequestBody
                                    OrderDeliveryInformationRequest orderDeliveryInformationRequest) {

        List<OrderGoodsEntity> orderGoodsEntityList =
                        orderHelper.toOrderGoodsEntityList(orderDeliveryInformationRequest.getOrderGoodsEntityList());
        WebApiGetDeliveryInformationRequestDto reqDto = orderHelper.toWebApiGetDeliveryInformationRequestDto(
                        orderDeliveryInformationRequest.getReqDto());

        WebApiGetDeliveryInformationResponseDetailDto resDto;
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();
        if (orderDeliveryInformationRequest.getCheckMessageDtoList() == null) {
            resDto = orderDeliveryInformationLogic.execute(orderGoodsEntityList, reqDto);

        } else {
            checkMessageDtoList =
                            orderHelper.toCheckMessageDtoList(orderDeliveryInformationRequest.getCheckMessageDtoList());

            resDto = orderDeliveryInformationLogic.execute(orderGoodsEntityList, reqDto, checkMessageDtoList);
        }

        OrderDeliveryInformationDtoResponse response = orderHelper.toOrderDeliveryInformationDtoResponse(resDto);

        List<CheckMessageDtoResponse> checkMessageDtoListResponse =
                        orderHelper.toCheckMessageDtoListResponse(checkMessageDtoList);
        response.setCheckMessageDtoList(checkMessageDtoListResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /order/order-info-master/get : 注文情報マスタ取得
     * 注文情報マスタ取得
     *
     * @param orderInfoMasterGetRequest 注文情報マスタ取得リクエスト (required)
     * @return 注文情報マスタDtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderInfoMasterDtoResponse> getOrderInfoMaster(
                    @ApiParam(value = "注文情報マスタ取得") @Valid OrderInfoMasterGetRequest orderInfoMasterGetRequest) {

        CartDto cartDto = orderHelper.toCartDto(orderInfoMasterGetRequest);

        OrderInfoMasterDto orderInfoMasterDto = orderInfoMasterGetService.execute(cartDto);

        OrderInfoMasterDtoResponse response = orderHelper.toOrderInfoMasterDtoResponse(orderInfoMasterDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /order/reserve/get : WEB-API連携取りおき情報
     * WEB-API連携取りおき情報
     *
     * @param orderGetReserveRequest 取りおきリクエスト (required)
     * @return 取りおきMapレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderGetReserveMapResponse> getOrderReserve(
                    @ApiParam(value = "取りおきリクエスト") @Valid OrderGetReserveRequest orderGetReserveRequest) {

        OrderDeliveryDto orderDeliveryDto =
                        orderHelper.toOrderDeliveryDto(orderGetReserveRequest.getOrderDeliveryDto());
        Integer customerNo = orderGetReserveRequest.getCustomerNo();

        Map<String, WebApiGetReserveResponseDetailDto> reserveDtoMap =
                        orderGetReserveLogic.execute(orderDeliveryDto, customerNo);

        OrderGetReserveMapResponse response = orderHelper.toOrderGetReserveMapResponse(reserveDtoMap);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /order/stock : WEB-API連携商品在庫情報
     * WEB-API連携商品在庫情報
     *
     * @param orderGetStockRequest 商品在庫情報リクエスト (optional)
     * @return 商品在庫情報Mapレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderGetStockMapResponse> getOrderStock(
                    @ApiParam(value = "商品在庫情報リクエスト") @Valid OrderGetStockRequest orderGetStockRequest) {

        List<String> goodsCodeList = orderGetStockRequest.getGoodsCodeList();
        List<String> quantityList = orderGetStockRequest.getQuantityList();
        Integer customerNo = orderGetStockRequest.getCustomerNo();

        Map<String, WebApiGetStockResponseDetailDto> stockMap =
                        orderGetStockLogic.execute(goodsCodeList, quantityList, customerNo);

        OrderGetStockMapResponse response = orderHelper.toOrderGetStockMapResponse(stockMap);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /order/settlement-method-select-list : 決済方法選択可能リスト取得
     * 決済方法選択可能リスト取得
     *
     * @param settlementMethodSelectListGetRequest 取りおきリクエスト (optional)
     * @return 決済方法選択可能リストレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<SettlementMethodSelectListResponse> getSettlementMethodSelectList(
                    @ApiParam(value = "取りおきリクエスト")
                    @Valid SettlementMethodSelectListGetRequest settlementMethodSelectListGetRequest) {

        ReceiveOrderDto receiveOrderDto =
                        orderHelper.toReceiveOrderDto(settlementMethodSelectListGetRequest.getReceiveOrderDto());

        List<SettlementDto> settlementDtoList = settlementMethodSelectListGetService.execute(receiveOrderDto,
                                                                                             settlementMethodSelectListGetRequest.getAvailable(),
                                                                                             settlementMethodSelectListGetRequest.getAllowCreditFlag(),
                                                                                             settlementMethodSelectListGetRequest.getMemberInfoId(),
                                                                                             HTypeSiteType.FRONT_PC
                                                                                            );
        SettlementMethodSelectListResponse response = new SettlementMethodSelectListResponse();

        response.settlementDtoList(orderHelper.toSettlementDtoResponseList(settlementDtoList));
        response.setReceiveOrderDto(orderHelper.toReceiveOrderDtoJS(receiveOrderDto));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /order/bill-price-calculate : 請求金額算出
     * 請求金額算出
     *
     * @param billPriceCalculateRequest 請求金額算出リクエスト (required)
     * @return 請求金額算出レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<BillPriceCalculateResponse> registBillPriceCalculate(
                    @ApiParam(value = "請求金額算出リクエスト", required = true) @Valid @RequestBody
                                    BillPriceCalculateRequest billPriceCalculateRequest) {
        ReceiveOrderDto receiveOrderDto = orderHelper.toReceiveOrderDto(billPriceCalculateRequest.getReceiveOrderDto());

        billPriceCalculateLogic.execute(
                        receiveOrderDto, false, HTypeSiteType.FRONT_PC, billPriceCalculateRequest.getAdminId());

        BillPriceCalculateResponse response = orderHelper.toBillPriceCalculateResponse(receiveOrderDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /order/order-screen : 注文画面用注文登録
     * 注文画面用注文登録
     *
     * @param orderScreenRegistRequest 注文画面用注文登録リクエスト (required)
     * @return 注文画面用注文登録レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderScreenRegistResponse> registOrderScreen(
                    @ApiParam(value = "注文画面用注文登録リクエスト", required = true) @Valid @RequestBody
                                    OrderScreenRegistRequest orderScreenRegistRequest) {

        // セッションに情報を設定（ ペイジェント通信時のインタセプタ用）
        SessionParamsUtil sessionParamsUtil = new SessionParamsUtil();
        sessionParamsUtil.setToSessionParams(orderScreenRegistRequest.getMemberInfoSeq(),
                                             orderScreenRegistRequest.getSessionId(),
                                             orderScreenRegistRequest.getAccessUid()
                                            );

        List<ReceiveOrderDto> receiveOrderDtoListRequest =
                        orderHelper.toReceiveOrderDtoList(orderScreenRegistRequest.getReceiveOrderDtoList());
        ReceiveOrderDto receiveOrderDtoRequest =
                        orderHelper.toReceiveOrderDto(orderScreenRegistRequest.getReceiveOrderDto());

        // オーソリ実行フラグ
        // オーソリを行うのは注文金額が1円以上の受注(最初の1件のみ)
        boolean executeAuthori = false;
        // 関連受注番号
        String relationOrderCode = null;
        // 現在日時
        Timestamp currentTime = dateUtility.getCurrentTime();
        // 顧客番号
        Integer customerNo = null;

        for (int i = 0; i < receiveOrderDtoListRequest.size(); i++) {

            ReceiveOrderDto receiveOrderDto = receiveOrderDtoListRequest.get(i);

            HTypeSettlementMethodType settlementMethodType =
                            receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();

            // 支払方法がクレジットの場合
            if (HTypeSettlementMethodType.CREDIT.equals(settlementMethodType)) {
                // オーソリ実行判定を行う。

                // 支払金額が1円以上の場合
                if (BigDecimal.ZERO.compareTo(receiveOrderDto.getOrderSummaryEntity().getOrderPrice()) < 0) {

                    // オーソリを実行するかどうか判定
                    if (!executeAuthori) {
                        executeAuthori = true;
                        receiveOrderDto.setExecuteAuthori(executeAuthori);
                    } else {
                        receiveOrderDto.setExecuteAuthori(false);
                    }
                }
            }

            // 関連受注番号をチェック
            if (relationOrderCode == null) {
                relationOrderCode = receiveOrderDto.getOrderSummaryEntity().getOrderCode();
            } else {
                // 受注番号を採番
                String orderCode = newOrderNoSeqGetLogic.execute().toString();
                // 値を設定
                receiveOrderDto.getOrderSummaryEntity().setOrderCode(orderCode);
                receiveOrderDto.getOrderSettlementEntity().setOrderCode(orderCode);
                // 新しいお届け先の場合
                if (ADD_TYPE_RECEIVER.equals(receiveOrderDto.getOrderDeliveryDto().getAddType())) {
                    // 顧客番号を設定
                    receiveOrderDto.getOrderDeliveryDto().setCustomerNo(customerNo);
                }
            }
            // 関連受注番号用に保持
            receiveOrderDto.setRelationOrderCode(relationOrderCode);
            // 受注日の設定
            receiveOrderDto.getOrderSummaryEntity().setOrderTime(currentTime);

            // 受注その他情報
            if (0 == i) {
                receiveOrderDto.setOrderOtherDataDto(receiveOrderDtoRequest.getOrderOtherDataDto());
            }

            // 新しいお届け先の場合
            if (ADD_TYPE_RECEIVER.equals(receiveOrderDto.getOrderDeliveryDto().getAddType()) && customerNo == null) {
                // 顧客番号を保持
                customerNo = receiveOrderDto.getOrderDeliveryDto().getCustomerNo();
            }

            // 受注登録ロジック実行
            orderRegisterLogic.execute(receiveOrderDto, orderScreenRegistRequest.getMemberInfoId(),
                                       HTypeSiteType.FRONT_PC, EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                                             orderScreenRegistRequest.getDeviceType()
                                                                                            ),
                                       orderScreenRegistRequest.getMemberInfoSeq(),
                                       orderScreenRegistRequest.getUserAgent(),
                                       orderScreenRegistRequest.getAdministratorLastName(),
                                       orderScreenRegistRequest.getAdministratorFirstName()
                                      );
        }

        // 会員用処理
        processMemberOrder(
                        receiveOrderDtoRequest, receiveOrderDtoListRequest, orderScreenRegistRequest.getCustomerNo());

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoListRequest) {
            // お届け先登録が失敗した場合、保留とする。(優先度最低の為、保留フラグの優先順位が3以下の場合に設定)
            if (!addDestinationCheck && !(HTypePendingType.PROMO_READ_ERROR.equals(receiveOrderDto.getPendingType())
                                          || HTypePendingType.ZIP_CODE_UNKNOWN.equals(
                            receiveOrderDto.getPendingType()))) {
                receiveOrderDto.setPendingType(HTypePendingType.DESTINATION_API_ERROR);
            }
        }

        // 受注連携
        addOrderInformationLogic.execute(receiveOrderDtoListRequest);

        OrderScreenRegistResponse registResponse = new OrderScreenRegistResponse();
        registResponse.setOrderTime(currentTime);
        List<String> receiveOrderDtoListRes = new ArrayList<>();
        receiveOrderDtoListRequest.forEach(receiveOrderDto -> {
            receiveOrderDtoListRes.add(orderHelper.toReceiveOrderDtoJS(receiveOrderDto));
        });
        registResponse.receiveOrderDtoList(receiveOrderDtoListRes);

        return new ResponseEntity<>(registResponse, HttpStatus.OK);
    }

    /**
     * POST /order/order-complete-mail-send : 注文受付完了メール送信
     * 注文受付完了メール送信
     *
     * @param orderCompleteMailSendRequest 注文受付完了メール送信リクエスト (required)
     * @return 結果成否レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> sendOrderCompleteMail(
                    @ApiParam(value = "注文受付完了メール送信リクエスト", required = true) @Valid @RequestBody
                                    OrderCompleteMailSendRequest orderCompleteMailSendRequest) {

        List<ReceiveOrderDto> receiveOrderDtoList =
                        orderHelper.toReceiveOrderDtoList(orderCompleteMailSendRequest.getReceiveOrderDtoList());
        Object[] args = new Object[] {receiveOrderDtoList};
        Class<?>[] argsClass = new Class<?>[] {List.class};
        // 非同期処理を登録
        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
            asyncService.asyncService(orderCompleteMailSendService, args, argsClass);
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 会員向け処理
     *
     * @param receiveOrderDtoRequest     受注Dtoクラス
     * @param receiveOrderDtoListRequest 受注Dtoクラスリスト
     * @param customerNoRequest          顧客番号
     * @param customParams               案件用引数
     */
    protected void processMemberOrder(ReceiveOrderDto receiveOrderDtoRequest,
                                      List<ReceiveOrderDto> receiveOrderDtoListRequest,
                                      Integer customerNoRequest,
                                      Object... customParams) {

        // PDR Migrate Customization from here
        // 3Dセキュアカード決済の場合は、まだ注文が確定しないため処理を実施しない
        // 3Dセキュアは使用しないため削除

        // お客様情報入力画面の「変更した内容を会員情報に反映させる」が「はい」だったら
        // 会員情報は更新しないため削除
        OrderDeliveryDto orderDeliveryDto = receiveOrderDtoListRequest.get(0).getOrderDeliveryDto();

        try {
            // お届け先情報入力から追加の場合
            if (ADD_TYPE_RECEIVER.equals(orderDeliveryDto.getAddType())) {
                // 毎回登録
                // 会員情報登録
                Integer customerNo = registMemberInfo(orderDeliveryDto);
                // 受注の届け先顧客番号を設定
                for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoListRequest) {
                    // 顧客番号を設定
                    receiveOrderDto.getOrderDeliveryDto().setCustomerNo(customerNo);
                }
                // お届け先登録
                registAddressBook(orderDeliveryDto, customerNo, customerNoRequest);
            }
        } catch (RuntimeException e) {
            addDestinationCheck = false;
            // メイン処理ではないので、ログ出力のみ
            LOGGER.warn("アドレス帳の登録に失敗しました。", e);
        }
        // PDR Migrate Customization to here
    }

    /**
     * 会員情報登録処理<br/>
     * 住所登録処理と同じトランザクションで行う。
     *
     * @param orderDeliveryDto 受注配送DTO
     * @return 顧客番号
     */
    public Integer registMemberInfo(OrderDeliveryDto orderDeliveryDto) {
        // 受注配送DTOから会員情報を作成
        MemberInfoEntity memberInfoEntity = orderHelper.toMemberInfoEntityForMemberInfoRegist(orderDeliveryDto);
        // 登録処理
        return memberInfoRegistByNewAddressLogic.execute(memberInfoEntity);
    }

    /**
     * お届け先登録処理
     * 新規トランザクションで行う。
     *
     * @param orderDeliveryDto 受注配送DTO
     * @param customerNo       顧客番号
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void registAddressBook(OrderDeliveryDto orderDeliveryDto, Integer customerNo, Integer customerNoRequest) {
        // PDR Migrate Customization from here
        // お届け先登録API実行
        // Request取得
        WebApiAddDestinationRequestDto reqDto = ApplicationContextUtility.getBean(WebApiAddDestinationRequestDto.class);

        // 顧客番号
        reqDto.setCustomerNo(customerNoRequest);
        // お届け先顧客番号
        reqDto.setReceiveCustomerNo(customerNo);
        // お届け先事業所名
        reqDto.setOfficeName(orderDeliveryDto.getOrderDeliveryEntity().getReceiverLastName());
        // お届け先事業所名フリガナ
        reqDto.setOfficeKana(orderDeliveryDto.getOrderDeliveryEntity().getReceiverLastKana());
        // お届け先代表者名
        reqDto.setRepresentative(orderDeliveryDto.getOrderDeliveryEntity().getReceiverFirstName());
        // お届け先電話番号
        reqDto.setTel(orderDeliveryDto.getOrderDeliveryEntity().getReceiverTel());
        // お届け先郵便番号
        reqDto.setZipCode(orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode());
        // お届け先都道府県コード
        reqDto.setCityCd(orderDeliveryDto.getCityCd());
        // お届け先住所(都道府県・市区町村)
        reqDto.setCity(orderDeliveryDto.getOrderDeliveryEntity().getReceiverAddress1());
        // お届け先住所(丁目・番地)
        reqDto.setAddress(orderDeliveryDto.getOrderDeliveryEntity().getReceiverAddress2());
        // お届け先住所(建物名・部屋番号)
        reqDto.setBuilding(orderDeliveryDto.getOrderDeliveryEntity().getReceiverAddress3());
        // お届け先住所(方書1、2)
        reqDto.setOther(orderDeliveryDto.getOrderDeliveryEntity().getReceiverAddress4());
        // お届け先顧客区分
        if (orderDeliveryDto.getBusinessType().equals(HTypeFrontBusinessType.OTHER)) {
            // 業種にその他が選択された場合、"-"
            reqDto.setBusinessType(HTypeBusinessType.OTHERS.getValue());
        } else {
            reqDto.setBusinessType(orderDeliveryDto.getBusinessType().getValue());
        }
        // お届け先確認書類
        if (orderDeliveryDto.getBusinessType().equals(HTypeFrontBusinessType.OTHER)) {
            // 業種にその他が選択された場合、"-"
            reqDto.setKakuninShoKbn(HTypeConfDocumentType.NOT_SET.getValue());
        } else {
            reqDto.setKakuninShoKbn(HTypeConfDocumentType.UNCONF.getValue());
        }
        // お届け先経理区分
        reqDto.setKeiriKbn(HTypeAccountingType.CUSTOMER.getValue());
        // お届け先オンラインログイン可否
        reqDto.setOnlineYesNo(HTypeOnlineLoginAdvisability.TEMPORARY_ACCOUNT.getValue());
        // お届け先名簿区分
        reqDto.setMemberListType(HTypeMemberListType.OFFLINE_GENERAL_CUSTOMER.getValue());
        // WEB-API連携 お届け先登録
        webApiAddDestinationLogic.execute(reqDto);
        // PDR Migrate Customization to here
    }

    /**
     * GET /order/get-consumption-tax-rate : WEB-API連携 消費税率取得ロジッククラス
     * WEB-API連携 消費税率取得ロジッククラス
     *
     * @param consumptionTaxRateGetRequest WEB-API連携 消費税率取得ロジッククラスリクエスト (optional)
     * @return WEB-API連携 消費税率取得ロジッククラスレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ConsumptionTaxRateResponse> getConsumptionTaxRate(
                    @ApiParam(value = "WEB-API連携 消費税率取得ロジッククラスリクエスト")
                    @Valid ConsumptionTaxRateGetRequest consumptionTaxRateGetRequest) {

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap =
                        orderGetConsumptionTaxRateLogic.execute(consumptionTaxRateGetRequest.getGoodsCodeList());

        ConsumptionTaxRateResponse response = orderHelper.toConsumptionTaxRateResponse(taxRateMap);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /order/order-promotion-information : プロモーション連携
     * プロモーション連携
     *
     * @param orderPromotionInformationRequest プロモーション連携リクエスト (required)
     * @return プロモーション連携レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderPromotionInformationResponse> registOrderPromotionInformation(
                    @ApiParam(value = "プロモーション連携リクエスト", required = true) @Valid @RequestBody
                                    OrderPromotionInformationRequest orderPromotionInformationRequest) {

        List<ReceiveOrderDto> receiveOrderDtoList =
                        orderHelper.toReceiveOrderDtoList(orderPromotionInformationRequest.getReceiveOrderDtoList());
        List<CheckMessageDto> checkMessageDtoList =
                        orderHelper.toCheckMessageDtoList(orderPromotionInformationRequest.getCheckMessageDtoList());

        orderPromotionInformationLogic.execute(receiveOrderDtoList, checkMessageDtoList);

        OrderPromotionInformationResponse orderPromotionInformationResponse = new OrderPromotionInformationResponse();

        orderPromotionInformationResponse.setCheckMessageDtoList(
                        orderHelper.toCheckMessageDtoResponseList(checkMessageDtoList));
        List<String> receiveOrderDtoListResponse = new ArrayList<>();
        receiveOrderDtoList.forEach(receiveOrderDto -> {
            receiveOrderDtoListResponse.add(orderHelper.toReceiveOrderDtoJS(receiveOrderDto));
        });
        orderPromotionInformationResponse.setReceiveOrderDtoList(receiveOrderDtoListResponse);
        return new ResponseEntity<>(orderPromotionInformationResponse, HttpStatus.OK);
    }

    /**
     * POST /order/order-quantity-discount : 数量割引取得結果連携
     * 数量割引取得結果連携
     *
     * @param orderQuantityDiscountRequest 数量割引取得結果連携リクエスト (required)
     * @return 数量割引取得結果連携レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderQuantityDiscountResponse> registOrderQuantityDiscount(
                    @ApiParam(value = "数量割引取得結果連携リクエスト", required = true) @Valid @RequestBody
                                    OrderQuantityDiscountRequest orderQuantityDiscountRequest) {

        ReceiveOrderDto receiveOrderDto =
                        orderHelper.toReceiveOrderDto(orderQuantityDiscountRequest.getReceiveOrderDto());
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap =
                        orderHelper.toConsumptionTaxRateMap(orderQuantityDiscountRequest.getTaxRateMap());
        List<CheckMessageDto> checkMessageDtoList =
                        orderHelper.toCheckMessageDtoList(orderQuantityDiscountRequest.getCheckMessageDtoList());

        orderQuantityDiscountLogic.execute(receiveOrderDto, taxRateMap, checkMessageDtoList);

        OrderQuantityDiscountResponse orderQuantityDiscountResponse = new OrderQuantityDiscountResponse();
        orderQuantityDiscountResponse.setCheckMessageDtoList(
                        orderHelper.toCheckMessageDtoResponseList(checkMessageDtoList));
        orderQuantityDiscountResponse.setReceiveOrderDto(orderHelper.toReceiveOrderDtoJS(receiveOrderDto));
        return new ResponseEntity<>(orderQuantityDiscountResponse, HttpStatus.OK);
    }

    /**
     * GET /order/new-orderno-seq : 受注番号採番
     * 受注番号採番
     *
     * @return 受注番号採番レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<NewOrderNoSeqResponse> getNewOrderNoSeq() {

        NewOrderNoSeqResponse response = new NewOrderNoSeqResponse();
        response.setOrderCode(newOrderNoSeqGetLogic.execute());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No14 from here

    /**
     * POST /order/order-before-payment : 前回支払方法取得
     * 前回支払方法取得
     *
     * @param orderBeforePaymentRequest 前回支払方法取得リクエスト (required)
     * @return 前回支払方法取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderBeforePaymentResponse> getBeforePayment(
                    @ApiParam(value = "前回支払方法取得リクエスト", required = true) @Valid @RequestBody
                                    OrderBeforePaymentRequest orderBeforePaymentRequest) {
        OrderBeforePaymentDto orderBeforePaymentDto =
                        orderBeforePaymentLogic.execute(orderBeforePaymentRequest.getCustomerNo(),
                                                        orderBeforePaymentRequest.getSettlementMethodTypeList()
                                                       );
        OrderBeforePaymentResponse response = new OrderBeforePaymentResponse();
        response.setSettlementMethodType(orderBeforePaymentDto.getSettlementMethodType());
        response.setCustomerCardId(orderBeforePaymentDto.getCustomerCardId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No14 to here

}
