package jp.co.hankyuhanshin.itec.hitmall.api.cart;

import io.swagger.annotations.ApiParam;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoCorrectionScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoCorrectionScreenUpdateResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsAddRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsChangeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsMergeUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsRegistCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CommonInfoCartGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CommonInfoCartResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.ResultCountResponse;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoCartDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsRegistCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsAddLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsMergeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsRegistCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.MemberCartLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetConsumptionTaxRateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetStockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetDiscountsResultLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetQuantityDiscountResultLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetSaleCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartClearService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsAddService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsChangeService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.AsyncTaskUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CartUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.AbstractController;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CartController extends AbstractController implements CartApi {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    /**
     * カート投入エラー
     */
    public static final String MSGCD_CART_ADD_ERROR = "ACX000101";

    /**
     * システムエラー発生
     */
    public static final String MSGCD_SYSTEM_ERR = "LOX000102";

    /**
     * テーブルロックによるシステムエラー発生
     */
    public static final String MSGCD_LOCK_SYSTEM_ERR = "PDR-0453-001-A-";

    /**
     * ショップSEQ
     */
    private static final Integer SHOP_SEQ = 1001;

    /**
     * サイト種別: フロントPC
     */
    private static final HTypeSiteType FRONT_SITE = HTypeSiteType.FRONT_PC;

    /**
     * カートチェッククラス
     */
    private final CartGoodsCheckService cartGoodsCheckService;

    /**
     * カート商品削除
     */
    private final CartGoodsDeleteService cartGoodsDeleteService;

    /**
     * カート投入チェックLogic
     */
    private final CartGoodsRegistCheckLogic cartGoodsRegistCheckLogic;

    /**
     * カート商品リスト削除ロジック
     */
    private final CartGoodsListDeleteLogic cartGoodsListDeleteLogic;

    /**
     * カート商品取得サービス
     */
    private final CartGoodsGetService cartGoodsGetService;

    /**
     * カート商品追加サービス
     */
    private final CartGoodsAddService cartGoodsAddService;

    /**
     * カートクリアクラス
     */
    private final CartClearService cartClearService;

    /**
     * WEB-API連携取得  割引適用結果取得
     */
    private final WebApiGetDiscountsResultLogic webApiGetDiscountsResultLogic;

    // 2023-renew No14 from here
    /**
     * WEB-API連携取得  数量割引適用結果取得
     */
    private final WebApiGetQuantityDiscountResultLogic webApiGetQuantityDiscountResultLogic;

    /**
     * WEB-API連携取得  取りおき情報取得
     */
    private final WebApiGetReserveLogic webApiGetReserveLogic;
    // 2023-renew No14 to here

    // 2023-renew No2 from here
    /**
     * WEB-API連携取得  販売可否判定
     */
    private final WebApiGetSaleCheckLogic webApiGetSaleCheckLogic;
    // 2023-renew No2 to here

    /**
     * メンバーカートロックロジック
     */
    private final MemberCartLockLogic memberCartLockLogic;

    /**
     * カート商品追加ロジック
     */
    private final CartGoodsAddLogic cartGoodsAddLogic;

    /**
     * 商品在庫数取得ロジック
     */
    private final OrderGetStockLogic orderGetStockLogic;

    /**
     * カート金額計算ロジッククラス
     */
    private final CartGoodsCalculateLogic cartGoodsCalculateLogic;

    /**
     * 消費税率取得ロジック
     */
    private final OrderGetConsumptionTaxRateLogic orderGetConsumptionTaxRateLogic;

    /**
     * カート商品変更サービス
     */
    private final CartGoodsChangeService cartGoodsChangeService;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * CartUtility
     */
    private final CartUtility cartUtility;

    // 2023-renew No14 from here
    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;
    // 2023-renew No14 to here

    /**
     * カートHelperクラス
     */
    private final CartHelper cartHelper;

    /**
     * カート情報(共通情報)Dao
     */
    private final CartGoodsDao cartGoodsDao;

    /**
     * カート商品マージ
     */
    private final CartGoodsMergeLogic cartGoodsMergeLogic;

    /**
     * 非同期用共通サービス
     */
    private final AsyncService asyncService;

    /**
     * アクセス情報登録
     */
    private final AccessRegistService accessRegistService;

    /**
     * コンストラクター
     */
    @Autowired
    public CartController(CartGoodsCheckService cartGoodsCheckService,
                          CartGoodsDeleteService cartGoodsDeleteService,
                          CartGoodsRegistCheckLogic cartGoodsRegistCheckLogic,
                          CartGoodsListDeleteLogic cartGoodsListDeleteLogic,
                          CartGoodsGetService cartGoodsGetService,
                          CartGoodsAddService cartGoodsAddService,
                          CartClearService cartClearService,
                          WebApiGetDiscountsResultLogic webApiGetDiscountsResultLogic,
                          WebApiGetQuantityDiscountResultLogic webApiGetQuantityDiscountResultLogic,
                          WebApiGetReserveLogic webApiGetReserveLogic,
                          WebApiGetSaleCheckLogic webApiGetSaleCheckLogic,
                          MemberCartLockLogic memberCartLockLogic,
                          CartGoodsAddLogic cartGoodsAddLogic,
                          OrderGetStockLogic orderGetStockLogic,
                          CartGoodsCalculateLogic cartGoodsCalculateLogic,
                          OrderGetConsumptionTaxRateLogic orderGetConsumptionTaxRateLogic,
                          CartGoodsChangeService cartGoodsChangeService,
                          GoodsUtility goodsUtility,
                          CartUtility cartUtility,
                          ConversionUtility conversionUtility,
                          CartHelper cartHelper,
                          CartGoodsDao cartGoodsDao,
                          CartGoodsMergeLogic cartGoodsMergeLogic,
                          AsyncService asyncService,
                          AccessRegistService accessRegistService) {
        this.cartGoodsCheckService = cartGoodsCheckService;
        this.cartGoodsDeleteService = cartGoodsDeleteService;
        this.cartGoodsRegistCheckLogic = cartGoodsRegistCheckLogic;
        this.cartGoodsListDeleteLogic = cartGoodsListDeleteLogic;
        this.cartGoodsGetService = cartGoodsGetService;
        this.cartGoodsAddService = cartGoodsAddService;
        this.cartClearService = cartClearService;
        this.webApiGetDiscountsResultLogic = webApiGetDiscountsResultLogic;
        // 2023-renew No14 from here
        this.webApiGetQuantityDiscountResultLogic = webApiGetQuantityDiscountResultLogic;
        this.webApiGetReserveLogic = webApiGetReserveLogic;
        // 2023-renew No14 to here
        // 2023-renew No2 from here
        this.webApiGetSaleCheckLogic = webApiGetSaleCheckLogic;
        // 2023-renew No2 to here
        this.memberCartLockLogic = memberCartLockLogic;
        this.cartGoodsAddLogic = cartGoodsAddLogic;
        this.orderGetStockLogic = orderGetStockLogic;
        this.cartGoodsCalculateLogic = cartGoodsCalculateLogic;
        this.orderGetConsumptionTaxRateLogic = orderGetConsumptionTaxRateLogic;
        this.cartGoodsChangeService = cartGoodsChangeService;
        this.goodsUtility = goodsUtility;
        this.cartUtility = cartUtility;
        // 2023-renew No14 from here
        this.conversionUtility = conversionUtility;
        // 2023-renew No14 to here
        this.cartHelper = cartHelper;
        this.cartGoodsDao = cartGoodsDao;
        this.cartGoodsMergeLogic = cartGoodsMergeLogic;
        this.asyncService = asyncService;
        this.accessRegistService = accessRegistService;
    }

    /**
     * POST /cart/cart-goods-check : カート商品チェック
     * カート商品チェック
     *
     * ※同一アクション内でカート再構築（/cart/cart-dto-correction-screen）を事前に行っている場合は、販売可否判定MAP／取りおき情報MAPを使いまわす。
     * ※同一アクション内で上記を行っていない場合は、販売可否判定MAP／取りおき情報MAPを再取得する。（CartDto内に保持していても必ずクリアしてからリクエストを作成すること）
     *
     * @param cartGoodsCheckRequest カート商品チェックリクエスト (required)
     * @return カート商品チェックMapレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CartGoodsCheckMapResponse> checkCartGoods(@Valid CartGoodsCheckRequest cartGoodsCheckRequest) {

        CartDto cartDto = cartHelper.toCartDto(cartGoodsCheckRequest);

        // 2023-renew No14 from here
        // リクエスト内にカート商品Dtoリストが存在する場合
        if (CollectionUtil.isNotEmpty(cartDto.getCartGoodsDtoList())) {
            // リクエスト内に販売可否判定MAPが存在しない場合
            if (MapUtils.isEmpty(cartDto.getSaleCheckMap())) {
                // 販売可否判定API 呼び出し（今すぐお届け／セールde予約混在）
                cartDto.setSaleCheckMap(executeWebApiGetSaleCheckByCartDto(cartDto.getCartGoodsDtoList(),
                                                                           cartGoodsCheckRequest.getCustomerNo()
                                                                          ).getMap());
            }
            // リクエスト内に取りおき情報MAPが存在しない場合
            if (MapUtils.isEmpty(cartDto.getReserveMap())) {
                // 取りおき情報取得API 呼び出し（今すぐお届け／セールde予約混在）
                cartDto.setReserveMap(executeWebApiGetReserveByCartDto(cartDto.getCartGoodsDtoList(),
                                                                       cartGoodsCheckRequest.getCustomerNo(),
                                                                       cartGoodsCheckRequest.getZipcode()
                                                                      ).getMap());
            }
        }
        // 2023-renew No14 to here

        // カートチェック
        Map<Integer, List<CheckMessageDto>> result =
                        cartGoodsCheckService.execute(cartDto, cartGoodsCheckRequest.getIsLogin(),
                                                      cartGoodsCheckRequest.getBusinessType(),
                                                      cartGoodsCheckRequest.getConfDocumentType(),
                                                      cartGoodsCheckRequest.getMemberInfoSeq()
                                                     );
        // カート商品チェックMapレスポンスに変換
        CartGoodsCheckMapResponse response = cartHelper.toCartGoodsCheckMapResponse(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /cart/cart-goods-regist-check : カート投入チェック（一括登録用の簡易版）
     * カート投入チェック
     *
     * @param cartGoodsRegistCheckRequest カート投入チェック（一括登録用の簡易版）リクエスト (required)
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> checkCartGoodsRegist(@Valid CartGoodsRegistCheckRequest cartGoodsRegistCheckRequest) {

        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList =
                        cartHelper.toCartGoodsForTakeOverDtoListFromRequest(cartGoodsRegistCheckRequest);

        // カート商品情報を取得
        CartGoodsForDaoConditionDto conditionDto = ApplicationContextUtility.getBean(CartGoodsForDaoConditionDto.class);
        conditionDto.setShopSeq(SHOP_SEQ);
        conditionDto.setMemberInfoSeq(cartGoodsRegistCheckRequest.getMemberInfoSeq());
        conditionDto.setAccessUid(cartGoodsRegistCheckRequest.getAccessUid());
        // カート投入チェック
        cartGoodsRegistCheckLogic.execute(cartGoodsForTakeOverDtoList, conditionDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE /cart/cart-goods : カート商品削除
     * カート商品削除
     *
     * @param cartGoodsDeleteRequest カート商品削除リクエスト (required)
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteCartGoods(@NotNull @Valid CartGoodsDeleteRequest cartGoodsDeleteRequest) {
        cartGoodsDeleteService.execute(cartGoodsDeleteRequest.getCartGoodsSeqList(),
                                       cartGoodsDeleteRequest.getMemberInfoSeq(), cartGoodsDeleteRequest.getAccessUid()
                                      );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE /cart/cart-goods-list : カート商品リスト削除
     * カート商品リスト削除
     *
     * @param cartGoodsListDeleteRequest カート商品リスト削除リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> deleteCartGoodsList(@Valid CartGoodsListDeleteRequest cartGoodsListDeleteRequest) {
        ResultCountResponse response = new ResultCountResponse();

        Integer count = cartGoodsListDeleteLogic.execute(cartGoodsListDeleteRequest.getCartGoodsSeqList(),
                                                         cartGoodsListDeleteRequest.getAccessUid(),
                                                         cartGoodsListDeleteRequest.getMemberInfoSeq()
                                                        );
        response.setResultCount(count);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /cart/cart-goods : カート情報取得
     * カート情報取得
     *
     * @param cartGoodsGetRequest カート情報取得リクエスト (optional)
     * @return カートDtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CartDtoResponse> getCartGoods(@Valid CartGoodsGetRequest cartGoodsGetRequest) {

        // 2023-renew No14 from here
        // カート情報取得
        CartDto cartDto = cartGoodsGetService.execute(cartGoodsGetRequest.getAccessUid(), FRONT_SITE,
                                                      cartGoodsGetRequest.getMemberInfoSeq(),
                                                      cartGoodsGetRequest.getOrderField(),
                                                      cartGoodsGetRequest.getGoodsSeq(),
                                                      EnumTypeUtil.getEnumFromValue(HTypeReserveDeliveryFlag.class,
                                                                                    cartGoodsGetRequest.getReserveFlag()
                                                                                   )
                                                     );
        // 2023-renew No14 to here
        // カートDtoクラスレスポンスに変換
        CartDtoResponse response = cartHelper.toCartDtoResponse(cartDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /cart/cart-goods-add : カート商品追加
     * カート商品追加
     * ※通常のカートIN専用（セールde予約には非対応）
     *
     * @param cartGoodsAddRequest カート商品追加リクエスト (required)
     * @return チェックメッセージDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<CheckMessageDtoResponse>> registCartGoodsAdd(
                    @ApiParam(value = "カート商品追加リクエスト", required = true) @Valid @RequestBody
                                    CartGoodsAddRequest cartGoodsAddRequest) {

        // 2023-renew No14 from here
        // 当メソッドは通常のカートIN専用（セールde予約には非対応）
        // 2023-renew No14 to here

        List<CheckMessageDto> checkMessageDtoList =
                        cartGoodsAddService.execute(cartGoodsAddRequest.getGoodsCode(), cartGoodsAddRequest.getCount(),
                                                    cartGoodsAddRequest.getAccessUid(),
                                                    cartGoodsAddRequest.getMemberInfoSeq(), FRONT_SITE,
                                                    // 2023-renew No14 from here
                                                    createCartGoodsRegistCheckDtoForDeliveryNow(
                                                                    executeWebApiGetSaleCheck(
                                                                                    Arrays.asList(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                                                                                    cartGoodsAddRequest.getGoodsCode())),
                                                                                    cartGoodsAddRequest.getCustomerNo()
                                                                                             ).getMap())
                                                    // 2023-renew No14 to here
                                                   );

        List<CheckMessageDtoResponse> checkMessageDtoResponseList =
                        cartHelper.toCheckMessageDtoResponseList(checkMessageDtoList);

        return new ResponseEntity<>(checkMessageDtoResponseList, HttpStatus.OK);
    }

    /**
     * POST /cart/cart-screen : カート一括登録（クイックオーダー／セールde予約用）
     * カート一括登録（クイックオーダー／セールde予約用）
     *
     * @param cartScreenRegistRequest カート一括登録リクエスト (required)
     * @return チェックメッセージDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> registCartScreen(@Valid CartScreenRegistRequest cartScreenRegistRequest) {

        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList =
                        cartHelper.toCartGoodsForTakeOverDtoList(cartScreenRegistRequest.getRegistCartGoodsList());

        // もともとカートに登録されている商品をクリアする。（一括登録対象商品以外は残る）
        cartClearService.execute(cartGoodsForTakeOverDtoList, cartScreenRegistRequest.getMemberInfoSeq(),
                                 cartScreenRegistRequest.getAccessUid()
                                );

        String accessUid = cartScreenRegistRequest.getAccessUid();
        Integer memberInfoSeq = cartScreenRegistRequest.getMemberInfoSeq();
        HTypeSiteType siteType =
                        EnumTypeUtil.getEnumFromValue(HTypeSiteType.class, cartScreenRegistRequest.getSiteType());
        String campainCode = cartScreenRegistRequest.getCampainCode();

        // 2023-renew No14 from here
        // セールde予約による一括登録の場合のみ、取りおき情報取得API 呼び出し
        // ※当メソッドが呼び出される時、cartGoodsForTakeOverDtoListは必ず1件以上存在し、全件の取りおきフラグは同じ値なので1件目のデータで判定
        Map<String, WebApiGetReserveResponseDetailDto> reserveMap =
                        HTypeReserveDeliveryFlag.ON.equals(cartGoodsForTakeOverDtoList.get(0).getReserveFlag()) ?
                                        executeWebApiGetReserveByCartGoodsForTakeOverDtoList(
                                                        cartGoodsForTakeOverDtoList,
                                                        cartScreenRegistRequest.getCustomerNo(),
                                                        cartScreenRegistRequest.getZipcode()
                                                                                            ).getMap() :
                                        null;
        // 販売可否判定API 呼び出し
        Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap =
                        executeWebApiGetSaleCheckByCartGoodsForTakeOverDtoList(cartGoodsForTakeOverDtoList,
                                                                               cartScreenRegistRequest.getCustomerNo()
                                                                              ).getMap();
        // 2023-renew No14 to here

        // リスト件数分、カート一括投入処理を行う
        boolean cartAddFlag;
        for (CartGoodsForTakeOverDto dto : cartGoodsForTakeOverDtoList) {
            // カート投入処理
            cartAddFlag = addGoodsToCart(dto.getGoodsCode(), String.valueOf(dto.getGoodsCount()), accessUid,
                                         memberInfoSeq, siteType,
                                         // 2023-renew No14 from here
                                         createCartGoodsRegistCheckDto(dto.getReserveDeliveryDate(),
                                                                       dto.getReserveFlag(), reserveMap, saleCheckMap
                                                                      )
                                         // 2023-renew No14 to here
                                        );
            // アクセス情報登録処理
            if (cartAddFlag) {
                accessInfoRegist(HTypeAccessType.GOODS_CART_ADD_COUNT, dto.getGoodsGroupSeq(), null, siteType,
                                 accessUid, campainCode
                                );
            }
        }

        if (hasErrorMessage()) {
            throwMessage();
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * PUT /cart/cart-dto-correction-screen : カート再構築
     * WEB-API取得情報よりカート再計算
     *
     * @param cartDtoCorrectionScreenUpdateRequest カート再構築リクエスト (required)
     * @return カート再構築レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<CartDtoCorrectionScreenUpdateResponse> updateCartDtoCorrectionScreen(@Valid CartDtoCorrectionScreenUpdateRequest cartDtoCorrectionScreenUpdateRequest) {

        CartDtoCorrectionScreenUpdateResponse cartDtoCorrectionScreenUpdateResponse =
                        new CartDtoCorrectionScreenUpdateResponse();

        // カート情報取得（今すぐお届け／セールde予約混在）
        CartDto cartDto = cartGoodsGetService.execute(cartDtoCorrectionScreenUpdateRequest.getAccessUid(), FRONT_SITE,
                                                      cartDtoCorrectionScreenUpdateRequest.getMemberInfoSeq()
                                                     );

        // カート内商品が空の場合
        if (CollectionUtil.isEmpty(cartDto.getCartGoodsDtoList())) {
            cartDtoCorrectionScreenUpdateResponse.setCartDto(cartHelper.toCartDtoResponse(cartDto));
            // 処理を行わない
            return new ResponseEntity<>(cartDtoCorrectionScreenUpdateResponse, HttpStatus.OK);
        }

        if (cartDto.getBeforeTransferEmotionGoodsCodeMap() == null) {
            cartDto.setBeforeTransferEmotionGoodsCodeMap(new HashMap<>());
        }

        // 振替前商品コード 保持
        Map<String, String> beforeTransferGoodsCodeMap = new HashMap<>(cartDto.getBeforeTransferEmotionGoodsCodeMap());

        // 割引適用結果取得API 呼び出し（今すぐお届けのみ）
        WebApiGetDiscountsResponseDto discountsResponseDto =
                        this.executeWebApiGetDiscountsResult(cartDto, beforeTransferGoodsCodeMap,
                                                             cartDtoCorrectionScreenUpdateRequest
                                                            );

        // 2023-renew No14 from here
        // 割引適用結果が存在する場合（=今すぐお届けの商品がカートINされている場合）、カートを再構築
        if (ObjectUtils.isNotEmpty(discountsResponseDto)) {

            // カートのクリア（今すぐお届けのみ）
            try {
                // カートのロックを取得
                memberCartLockLogic.execute(cartDtoCorrectionScreenUpdateRequest.getMemberInfoSeq());
                cartClearService.execute(
                                cartDtoCorrectionScreenUpdateRequest.getAccessUid(),
                                cartDtoCorrectionScreenUpdateRequest.getMemberInfoSeq()
                                        );
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                throwMessage(MSGCD_LOCK_SYSTEM_ERR);
            }

            // 表示順、商品コードで取得結果を並べ替え
            Collections.sort(discountsResponseDto.getInfo(), (obj1, obj2) -> {
                Integer disp1 = Integer.valueOf(obj1.getOrderDisplay());
                Integer disp2 = Integer.valueOf(obj2.getOrderDisplay());
                if (disp1.compareTo(disp2) != 0) {
                    return -(disp1.compareTo(disp2));
                }
                return -((obj1.getGoodsCode()).compareTo(obj2.getGoodsCode()));
            });

            // カートに投入（今すぐお届けのみ）
            List<CheckMessageDto> errorList = new ArrayList<>();
            for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
                errorList.addAll(cartGoodsAddLogic.execute(dto.getGoodsCode(), new BigDecimal(dto.getQuantity()),
                                                           dto.getSaleType(),
                                                           cartDtoCorrectionScreenUpdateRequest.getAccessUid(),
                                                           cartDtoCorrectionScreenUpdateRequest.getMemberInfoSeq(),
                                                           FRONT_SITE, createCartGoodsRegistCheckDtoForDeliveryNow(
                                                executeWebApiGetSaleCheckByDiscountsResponseDto(
                                                                discountsResponseDto,
                                                                cartDtoCorrectionScreenUpdateRequest.getCustomerNo()
                                                                                               ).getMap())
                                                          ));
            }

            // 返却されたエラー情報を追加
            if (CollectionUtil.isNotEmpty(errorList)) {
                List<CheckMessageDtoResponse> checkMessageDtoResponseList =
                                cartHelper.toCheckMessageDtoResponseList(errorList);
                cartDtoCorrectionScreenUpdateResponse.setCheckMessageDtoList(checkMessageDtoResponseList);
            }

        }
        // 2023-renew No14 to here

        // カートDTO再取得
        cartDto = cartGoodsGetService.execute(cartDtoCorrectionScreenUpdateRequest.getAccessUid(), FRONT_SITE,
                                              cartDtoCorrectionScreenUpdateRequest.getMemberInfoSeq()
                                             );

        // カート内商品が空の場合
        if (CollectionUtil.isEmpty(cartDto.getCartGoodsDtoList())) {
            // 処理終了
            // この時点でカート内商品が空の場合は、数量割引適用結果で返却された値が不正で
            // カートに商品が追加できなかった場合のみ
            cartDtoCorrectionScreenUpdateResponse.setCartDto(cartHelper.toCartDtoResponse(cartDto));
            // 処理を行わない
            return new ResponseEntity<>(cartDtoCorrectionScreenUpdateResponse, HttpStatus.OK);
        }

        // 2023-renew No14 from here
        // 割引適用結果が存在する場合（=今すぐお届けの商品がカートINされている場合）、再取得したカートDTOに割引適用結果MAPをセット
        if (ObjectUtils.isNotEmpty(discountsResponseDto)) {
            cartDto.setDiscountsResponseDetailMap(discountsResponseDto.getMap());
        }

        // 数量割引適用結果取得API 呼び出し（今すぐお届け／セールde予約混在）
        WebApiGetQuantityDiscountResultResponseDto quantityDiscountResponseDto =
                        executeWebApiGetQuantityDiscountsResult(cartDto, beforeTransferGoodsCodeMap,
                                                                cartDtoCorrectionScreenUpdateRequest
                                                               );
        cartDto.setQuantityDiscountsResponseDetailMap(quantityDiscountResponseDto.getMap());
        // 2023-renew No14 to here

        // 商品在庫数取得API 呼び出し（今すぐお届け／セールde予約混在）
        Map<String, WebApiGetStockResponseDetailDto> stockMap =
                        orderGetStockLogic.execute(cartUtility.getGoodsCodeList(cartDto.getCartGoodsDtoList()),
                                                   cartUtility.getQuantityList(cartDto.getCartGoodsDtoList()),
                                                   cartDtoCorrectionScreenUpdateRequest.getCustomerNo()
                                                  );

        // 2023-renew No14 from here
        // 販売可否判定API 呼び出し（今すぐお届け／セールde予約混在）
        cartDto.setSaleCheckMap(executeWebApiGetSaleCheckByCartDto(cartDto.getCartGoodsDtoList(),
                                                                   cartDtoCorrectionScreenUpdateRequest.getCustomerNo()
                                                                  ).getMap());
        // 取りおき情報取得API 呼び出し（今すぐお届け／セールde予約混在）
        cartDto.setReserveMap(executeWebApiGetReserveByCartDto(cartDto.getCartGoodsDtoList(),
                                                               cartDtoCorrectionScreenUpdateRequest.getCustomerNo(),
                                                               cartDtoCorrectionScreenUpdateRequest.getZipcode()
                                                              ).getMap());
        // 2023-renew No14 to here

        // 消費税率取得API 呼び出し（今すぐお届け／セールde予約混在）
        cartDto.setConsumptionTaxRateMap(orderGetConsumptionTaxRateLogic.execute(
                        cartUtility.getGoodsCodeList(cartDto.getCartGoodsDtoList())));

        // 2023-renew No14 from here
        // 各WEB-API連携で取得した商品情報に差異がないかチェック
        // ※数量割引適用結果をベースにチェックする
        // （数量割引の有無にかかわらず、INパラメータのすべての商品について返却するため）
        for (WebApiGetQuantityDiscountResultResponseDetailDto dto : quantityDiscountResponseDto.getInfo()) {
            if (stockMap.get(dto.getGoodsCode()) == null || (cartDto.getReserveMap()).get(dto.getGoodsCode()) == null
                || (cartDto.getConsumptionTaxRateMap()).get(dto.getGoodsCode()) == null) {
                // 取得した割引適用結果と消費税率、商品在庫数の情報に差異がある場合はエラー
                throwMessage(MSGCD_SYSTEM_ERR);
            }
        }
        // 2023-renew No14 to here

        cartDto.setBeforeTransferEmotionGoodsCodeMap(beforeTransferGoodsCodeMap);

        // 金額、消費税率を上書き
        cartUtility.createCartDtoByWebApiInfo(cartDto, stockMap);

        // 再計算
        cartGoodsCalculateLogic.execute(cartDto);

        cartDtoCorrectionScreenUpdateResponse.setCartDto(cartHelper.toCartDtoResponse(cartDto));

        return new ResponseEntity<>(cartDtoCorrectionScreenUpdateResponse, HttpStatus.OK);
    }

    /**
     * PUT /cart/cart-goods : カート情報変更
     * カート情報変更
     *
     * @param cartGoodsChangeRequest カート情報変更リクエスト (required)
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> updateCartGoods(@Valid CartGoodsChangeRequest cartGoodsChangeRequest) {
        // カート商品Dtoリストに変換
        List<CartGoodsDto> cartGoodsDtoList = cartHelper.toCartGoodsDtoListFromRequest(cartGoodsChangeRequest);
        // メソッド概要
        cartGoodsChangeService.execute(cartGoodsDtoList, cartGoodsChangeRequest.getAccessUid(),
                                       cartGoodsChangeRequest.getMemberInfoSeq()
                                      );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /cart/cart-common-info : カート共通情報取得
     * カート共通情報取得
     *
     * @param commonInfoCartGetRequest カート共通情報リクエスト (required)
     * @return カート共通情報レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CommonInfoCartResponse> getCartCommonInfo(@NotNull
                                                                    @ApiParam(value = "カート共通情報リクエスト", required = true)
                                                                    @Valid CommonInfoCartGetRequest commonInfoCartGetRequest) {
        CommonInfoCartDto commonInfoCartDto =
                        cartGoodsDao.getCommonInfoCart(commonInfoCartGetRequest.getMemberInfoSeq(),
                                                       commonInfoCartGetRequest.getAccessUid()
                                                      );
        CommonInfoCartResponse response = new CommonInfoCartResponse();
        if (commonInfoCartDto != null) {
            response.setCartGoodsSumCount(commonInfoCartDto.getCartGoodsSumCount());
            response.setCartGoodsSumPrice(commonInfoCartDto.getCartGoodsSumPrice());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * PUT /cart/merge : カート商品マージ
     * カート商品マージ
     *
     * @param cartGoodsMergeUpdateRequest カート商品マージ更新リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */

    @Override
    public ResponseEntity<ResultCountResponse> merge(
                    @ApiParam(value = "カート商品マージ更新リクエスト", required = true) @Valid @RequestBody
                                    CartGoodsMergeUpdateRequest cartGoodsMergeUpdateRequest) {

        int result = cartGoodsMergeLogic.execute(1001, 0, cartGoodsMergeUpdateRequest.getAccessUid(),
                                                 cartGoodsMergeUpdateRequest.getChangeMemberInfoSeq()
                                                );
        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(result);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * カート投入処理
     * 商品コード、商品数量があれば、カートに投入する
     *
     * @param gcd           商品コード
     * @param gcnt          商品数量
     * @param accessUid     端末識別番号
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param cartGoodsRegistCheckDto カート投入チェックDto
     * @return カート投入フラグ true..エラーなし
     */
    private boolean addGoodsToCart(String gcd,
                                   String gcnt,
                                   String accessUid,
                                   Integer memberInfoSeq,
                                   HTypeSiteType siteType,
                                   CartGoodsRegistCheckDto cartGoodsRegistCheckDto) {

        boolean cartAddFlag = false;

        // 商品情報を受け取っていない場合
        if (StringUtils.isEmpty(gcd) || !StringUtils.isNumeric(gcnt)) {
            // カート投入しなかった
            return cartAddFlag;
        }

        // ① 商品情報が取得できた場合
        // ・【カート投入サービス】実行
        List<CheckMessageDto> errorList =
                        cartGoodsAddService.execute(gcd, new BigDecimal(gcnt), accessUid, memberInfoSeq, siteType,
                                                    // 2023-renew No14 from here
                                                    cartGoodsRegistCheckDto
                                                    // 2023-renew No14 to here
                                                   );

        // ② ・カート投入サービス実行時の戻り値を、エラー情報にセットする。
        if (CollectionUtil.isNotEmpty(errorList)) {
            StringBuilder s = new StringBuilder();
            for (CheckMessageDto checkMessageDto : errorList) {
                if (s.length() > 0) {
                    // カート投入不可理由が複数ある場合は「・」で区切る
                    s.append("・");
                }
                // カート投入不可理由メッセージ(※)に引数がある場合は組み立て済みのメッセージに置き換える
                // ※エラーメッセージ引数となる別のメッセージ
                Object message = MessageFormat.format(checkMessageDto.getMessage(), checkMessageDto.getArgs());
                s.append(message);
            }
            String[] arg = new String[] {s.toString()};
            addErrorMessage(MSGCD_CART_ADD_ERROR, arg);
        } else {
            cartAddFlag = true;
        }

        return cartAddFlag;
    }

    /**
     * アクセス情報登録
     * サービス実行に必要なパラメータ
     *
     * @param accessType    アクセス区分
     * @param goodsGroupSeq 商品グループSEQ
     * @param accessCount   アクセス数
     */
    private void accessInfoRegist(HTypeAccessType accessType,
                                  Integer goodsGroupSeq,
                                  Integer accessCount,
                                  HTypeSiteType siteType,
                                  String accessUid,
                                  String campaignCode) {

        // サービス登録
        Object[] args = new Object[] {accessType, goodsGroupSeq, accessCount, accessUid, siteType, campaignCode};
        Class<?>[] argsClass = new Class<?>[] {HTypeAccessType.class, Integer.class, Integer.class, String.class,
                        HTypeSiteType.class, String.class};

        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
            asyncService.asyncService(accessRegistService, args, argsClass);
        });
    }

    /**
     * WEB-API連携 割引適用結果取得 を行い、結果を返却します。
     * ※今すぐお届け商品のみが対象
     *
     * @param cartDto                              カートDTO
     * @param beforeTransferEmotionGoodsCodeMap    振替前心意気商品コードMAP(公開中の商品のみ)
     * @param cartDtoCorrectionScreenUpdateRequest カート再構築リクエスト
     * @return 割引適用結果
     */
    private WebApiGetDiscountsResponseDto executeWebApiGetDiscountsResult(CartDto cartDto,
                                                                          Map<String, String> beforeTransferEmotionGoodsCodeMap,
                                                                          CartDtoCorrectionScreenUpdateRequest cartDtoCorrectionScreenUpdateRequest) {

        // 2023-renew No14 from here
        // カート商品情報を今すぐお届け商品のみに集約
        List<CartGoodsDto> cartDtoList = cartDto.getCartGoodsDtoList()
                                                .stream()
                                                .filter(item -> !HTypeReserveDeliveryFlag.ON.equals(
                                                                item.getCartGoodsEntity().getReserveFlag()))
                                                .collect(Collectors.toList());

        if (CollectionUtil.isEmpty(cartDtoList)) {
            // 今すぐお届け商品がない場合はスキップ
            return null;
        }
        // 2023-renew No14 to here

        List<String> goodsCodeList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();
        List<String> orderDisplayList = new ArrayList<>();

        // 振替前商品コード保持用MAP
        HashMap<String, GoodsDetailsDto> map = new HashMap<>();

        // 重複商品を集約
        for (int index = 0; index < cartDtoList.size(); index++) {
            CartGoodsDto cartGoodsDto = cartDtoList.get(index);
            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            GoodsDetailsDto detailsDto = cartGoodsDto.getGoodsDetailsDto();

            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(detailsDto.getGoodsCode());
            // 商品コードリストに既に商品コードが存在する場合、数量リストから同じインデックスの数値を取り出し、加算する
            // 心意気商品と通常商品の両方がカートに存在する場合に、一つのリクエストにまとめるための処理
            if (goodsCodeList.contains(goodsCode)) {
                int quantity = new BigDecimal(quantityList.get(goodsCodeList.indexOf(goodsCode))).intValue();
                quantity = quantity + cartGoodsEntity.getCartGoodsCount().intValue();
                quantityList.set(goodsCodeList.indexOf(goodsCode), new BigDecimal(quantity).toString());
            } else {
                goodsCodeList.add(goodsCode);
                quantityList.add(cartGoodsEntity.getCartGoodsCount().toString());
                orderDisplayList.add(String.valueOf(index + 1));
            }

            // 振替前商品コード
            map.put(String.valueOf(index + 1), detailsDto);
        }

        // Web-API リクエストDTO
        WebApiGetDiscountsRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetDiscountsRequestDto.class);
        // 顧客番号
        reqDto.setCustomerNo(cartDtoCorrectionScreenUpdateRequest.getCustomerNo());

        // 複数存在する項目をパイプ区切りに変換
        // 商品コード
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));
        // 数量
        reqDto.setQuantity(AbstractWebApiLogic.createStrPipeByList(quantityList));
        // 表示順
        reqDto.setOrderDisplay(AbstractWebApiLogic.createStrPipeByList(orderDisplayList));

        // WEB-API実行
        WebApiGetDiscountsResponseDto discountsResponseDto =
                        (WebApiGetDiscountsResponseDto) webApiGetDiscountsResultLogic.execute(reqDto);
        if (discountsResponseDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        // 振替前心意気商品コード 設定
        // 心意気商品の場合、振替前の商品詳細のリンクを設定するが、
        // 振替前商品が非公開の場合はリンクを設定しないため
        // 振替前心意気商品コードMAPに格納しない
        for (WebApiGetDiscountsResponseDetailDto detailDto : discountsResponseDto.getInfo()) {
            if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(detailDto.getSaleType())) {
                GoodsDetailsDto dto = map.get(detailDto.getOrderDisplay());
                // 振替前商品の公開状態をチェック
                if (goodsUtility.isGoodsOpen(
                                dto.getGoodsOpenStatusPC(), dto.getOpenStartTimePC(), dto.getOpenEndTimePC())) {
                    // 公開中の場合は設定
                    beforeTransferEmotionGoodsCodeMap.put(detailDto.getGoodsCode(), dto.getGoodsCode());
                }
            }
        }

        return discountsResponseDto;
    }

    // 2023-renew No14 from here

    /**
     * WEB-API連携 数量割引適用結果取得 を行い、結果を返却します。
     *
     * @param cartDto カートDto
     * @param beforeTransferEmotionGoodsCodeMap 振替前心意気商品コードMAP(公開中の商品のみ)
     * @param cartDtoCorrectionScreenUpdateRequest カート再構築リクエスト
     * @return 数量割引適用結果
     */
    private WebApiGetQuantityDiscountResultResponseDto executeWebApiGetQuantityDiscountsResult(CartDto cartDto,
                                                                                               Map<String, String> beforeTransferEmotionGoodsCodeMap,
                                                                                               CartDtoCorrectionScreenUpdateRequest cartDtoCorrectionScreenUpdateRequest) {

        List<String> goodsCodeList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();

        // 振替前商品コード保持用MAP
        HashMap<String, GoodsDetailsDto> map = new HashMap<>();

        // 重複商品を集約
        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {
            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            GoodsDetailsDto detailsDto = cartGoodsDto.getGoodsDetailsDto();

            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(detailsDto.getGoodsCode());
            // 商品コードリストに既に商品コードが存在する場合、数量リストから同じインデックスの数値を取り出し、加算する
            // 心意気商品と通常商品の両方がカートに存在する場合に、一つのリクエストにまとめるための処理
            if (goodsCodeList.contains(goodsCode)) {
                int quantity = new BigDecimal(quantityList.get(goodsCodeList.indexOf(goodsCode))).intValue();
                quantity = quantity + cartGoodsEntity.getCartGoodsCount().intValue();
                quantityList.set(goodsCodeList.indexOf(goodsCode), new BigDecimal(quantity).toString());
            } else {
                goodsCodeList.add(goodsCode);
                quantityList.add(cartGoodsEntity.getCartGoodsCount().toString());
            }

            // 振替前商品コード
            map.put(goodsCode, detailsDto);
        }

        // Web-API リクエストDTO
        WebApiGetQuantityDiscountResultRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetQuantityDiscountResultRequestDto.class);
        // 顧客番号
        reqDto.setCustomerNo(conversionUtility.toString(cartDtoCorrectionScreenUpdateRequest.getCustomerNo()));

        // 複数存在する項目をパイプ区切りに変換
        // 商品コード
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));
        // 数量
        reqDto.setQuantity(AbstractWebApiLogic.createStrPipeByList(quantityList));

        // WEB-API実行
        WebApiGetQuantityDiscountResultResponseDto quantityDiscountsResultResponseDto =
                        (WebApiGetQuantityDiscountResultResponseDto) webApiGetQuantityDiscountResultLogic.execute(
                                        reqDto);
        if (quantityDiscountsResultResponseDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        // 振替前心意気商品コード 設定
        // 心意気商品の場合、振替前の商品詳細のリンクを設定するが、
        // 振替前商品が非公開の場合はリンクを設定しないため
        // 振替前心意気商品コードMAPに格納しない
        for (WebApiGetQuantityDiscountResultResponseDetailDto detailDto : quantityDiscountsResultResponseDto.getInfo()) {
            if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(detailDto.getSaleType())) {
                GoodsDetailsDto dto = map.get(detailDto.getGoodsCode());
                // 振替前商品の公開状態をチェック
                if (goodsUtility.isGoodsOpen(
                                dto.getGoodsOpenStatusPC(), dto.getOpenStartTimePC(), dto.getOpenEndTimePC())) {
                    // 公開中の場合は設定
                    beforeTransferEmotionGoodsCodeMap.put(detailDto.getGoodsCode(), dto.getGoodsCode());
                }
            }
        }

        return quantityDiscountsResultResponseDto;
    }

    /**
     * カート商品DtoリストをベースにWEB-API連携 取りおき情報取得 を行い、結果を返却します。
     *
     * @param cartDtoList カート商品Dtoリスト
     * @param customerNo 顧客番号
     * @param zipcode 郵便番号
     * @return 取りおき情報結果
     */
    private WebApiGetReserveResponseDto executeWebApiGetReserveByCartDto(List<CartGoodsDto> cartDtoList,
                                                                         Integer customerNo,
                                                                         String zipcode) {

        List<String> goodsCodeList = new ArrayList<>();

        // 重複商品を集約
        for (CartGoodsDto cartGoodsDto : cartDtoList) {
            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            cartGoodsDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        // WEB-API連携 取りおき情報取得
        return executeWebApiGetReserve(goodsCodeList, customerNo, zipcode);
    }

    /**
     * カート一括登録用引継DTOリストをベースにWEB-API連携 取りおき情報取得 を行い、結果を返却します。
     *
     * @param cartGoodsForTakeOverDtoList カート一括登録用引継DTOリスト
     * @param customerNo 顧客番号
     * @param zipcode 郵便番号
     * @return 取りおき情報結果
     */
    private WebApiGetReserveResponseDto executeWebApiGetReserveByCartGoodsForTakeOverDtoList(List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList,
                                                                                             Integer customerNo,
                                                                                             String zipcode) {

        List<String> goodsCodeList = new ArrayList<>();

        // 重複商品を集約
        for (CartGoodsForTakeOverDto cartGoodsForTakeOverDto : cartGoodsForTakeOverDtoList) {
            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            cartGoodsForTakeOverDto.getGoodsCode());
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        // WEB-API連携 取りおき情報取得
        return executeWebApiGetReserve(goodsCodeList, customerNo, zipcode);
    }

    /**
     * WEB-API連携 取りおき情報取得 を行い、結果を返却します。
     *
     * @param goodsCodeList 商品コードリスト
     * @param customerNo 顧客番号
     * @param zipcode 郵便番号
     * @return 取りおき情報結果
     */
    private WebApiGetReserveResponseDto executeWebApiGetReserve(List<String> goodsCodeList,
                                                                Integer customerNo,
                                                                String zipcode) {

        // Web-API リクエストDTO
        WebApiGetReserveRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetReserveRequestDto.class);

        // 顧客番号
        reqDto.setCustomerNo(customerNo);
        // 配送先顧客番号
        reqDto.setDeliveryCustomerNo(customerNo);
        // 配送先郵便番号
        reqDto.setDeliveryZipcode(zipcode);

        // 複数存在する項目をパイプ区切りに変換
        // 商品コード
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));

        // WEB-API実行
        WebApiGetReserveResponseDto reserveResponseDto =
                        (WebApiGetReserveResponseDto) webApiGetReserveLogic.execute(reqDto);
        if (reserveResponseDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        return reserveResponseDto;
    }

    // 2023-renew No14 to here
    // 2023-renew No2 from here

    /**
     * 割引適用結果をベースにWEB-API連携 販売可否判定 を行い、結果を返却します。
     *
     * @param discountsResponseDto カートDto
     * @param customerNo 顧客番号
     * @return 販売可否判定結果
     */
    private WebApiGetSaleCheckResponseDto executeWebApiGetSaleCheckByDiscountsResponseDto(WebApiGetDiscountsResponseDto discountsResponseDto,
                                                                                          Integer customerNo) {

        List<String> goodsCodeList = new ArrayList<>();

        // 重複商品を集約
        for (WebApiGetDiscountsResponseDetailDto dto : discountsResponseDto.getInfo()) {
            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(dto.getGoodsCode());
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        // WEB-API連携 販売可否判定
        return executeWebApiGetSaleCheck(goodsCodeList, customerNo);
    }

    /**
     * カート商品DtoリストをベースにWEB-API連携 販売可否判定 を行い、結果を返却します。
     *
     * @param cartDtoList カート商品Dtoリスト
     * @param customerNo 顧客番号
     * @return 販売可否判定結果
     */
    private WebApiGetSaleCheckResponseDto executeWebApiGetSaleCheckByCartDto(List<CartGoodsDto> cartDtoList,
                                                                             Integer customerNo) {

        List<String> goodsCodeList = new ArrayList<>();

        // 重複商品を集約
        for (CartGoodsDto cartGoodsDto : cartDtoList) {
            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            cartGoodsDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        // WEB-API連携 販売可否判定
        return executeWebApiGetSaleCheck(goodsCodeList, customerNo);
    }

    /**
     * カート一括登録用引継DTOリストをベースにWEB-API連携 販売可否判定 を行い、結果を返却します。
     *
     * @param cartGoodsForTakeOverDtoList カート一括登録用引継DTOリスト
     * @param customerNo 顧客番号
     * @return 販売可否判定結果
     */
    private WebApiGetSaleCheckResponseDto executeWebApiGetSaleCheckByCartGoodsForTakeOverDtoList(List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList,
                                                                                                 Integer customerNo) {

        List<String> goodsCodeList = new ArrayList<>();

        // 重複商品を集約
        for (CartGoodsForTakeOverDto cartGoodsForTakeOverDto : cartGoodsForTakeOverDtoList) {
            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            cartGoodsForTakeOverDto.getGoodsCode());
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        // WEB-API連携 販売可否判定
        return executeWebApiGetSaleCheck(goodsCodeList, customerNo);
    }

    /**
     * WEB-API連携 販売可否判定 を行い、結果を返却します。
     *
     * @param goodsCodeList 商品コードリスト
     * @param customerNo 顧客番号
     * @return 販売可否判定結果
     */
    private WebApiGetSaleCheckResponseDto executeWebApiGetSaleCheck(List<String> goodsCodeList, Integer customerNo) {

        // Web-API リクエストDTO
        WebApiGetSaleCheckRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetSaleCheckRequestDto.class);

        // 顧客番号
        reqDto.setCustomerNo(customerNo);

        // 複数存在する項目をパイプ区切りに変換
        // 商品コード
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));

        // WEB-API実行
        WebApiGetSaleCheckResponseDto saleCheckResponseDto =
                        (WebApiGetSaleCheckResponseDto) webApiGetSaleCheckLogic.execute(reqDto);
        if (saleCheckResponseDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        return saleCheckResponseDto;
    }

    /**
     * カート投入チェックDtoを作成
     * ※今すぐお届け用（取りおき用の項目はセットしない）
     *
     * @param saleCheckMap        販売可否判定Map
     * @return カート投入チェックDto
     */
    private CartGoodsRegistCheckDto createCartGoodsRegistCheckDtoForDeliveryNow(Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap) {
        return createCartGoodsRegistCheckDto(null, HTypeReserveDeliveryFlag.OFF, null, saleCheckMap);
    }

    /**
     * カート投入チェックDtoを作成
     *
     * @param reserveDeliveryDate 取りおきお届け希望日（カート投入対象）
     * @param reserveFlag         取りおきフラグ（カート投入対象）
     * @param reserveMap          取りおき情報MAP
     * @param saleCheckMap        販売可否判定Map
     * @return カート投入チェックDto
     */
    private CartGoodsRegistCheckDto createCartGoodsRegistCheckDto(Date reserveDeliveryDate,
                                                                  HTypeReserveDeliveryFlag reserveFlag,
                                                                  Map<String, WebApiGetReserveResponseDetailDto> reserveMap,
                                                                  Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap) {

        // カート投入チェックDtoクラス
        CartGoodsRegistCheckDto dto = ApplicationContextUtility.getBean(CartGoodsRegistCheckDto.class);

        dto.setReserveDeliveryDate(reserveDeliveryDate);
        dto.setReserveFlag(reserveFlag);
        dto.setReserveMap(reserveMap);
        dto.setSaleCheckMap(saleCheckMap);

        return dto;
    }

    // 2023-renew No2 to here

}
