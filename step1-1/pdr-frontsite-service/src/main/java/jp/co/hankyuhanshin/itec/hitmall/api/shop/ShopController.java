package jp.co.hankyuhanshin.itec.hitmall.api.shop;

import io.swagger.annotations.ApiParam;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessSearchKeywordRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CalendarNotSelectDateEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CampaignEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CampaignGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.EffectiveCampaignCodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.EffectiveCampaignCodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.MulPayShopResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.OpenNewsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyDisplayDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.SettlementMethodResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ShopResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeAddressDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeAddressGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeMatchingCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CalendarNotSelectDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessSearchKeywordRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.NewsDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.OpenNewsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeAddressGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeMatchingCheckService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.AsyncTaskUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * ショップControllerクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RestController
public class ShopController implements ShopApi {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopController.class);

    /**
     * 郵便番号住所情報取得Service
     */
    private final ZipCodeAddressGetService zipCodeAddressGetService;

    /**
     * 郵便番号住所情報取得Service
     */
    private final ZipCodeMatchingCheckService zipCodeMatchingCheckService;

    /**
     * マルチペイショップロジック
     */
    private final MulPayShopLogic mulPayShopLogic;

    /**
     * アンケート情報取得。<br />
     */
    private final QuestionnaireGetLogic questionnaireGetLogic;

    /**
     * アンケートチェック。<br />
     */
    private final QuestionnaireCheckLogic questionnaireCheckLogic;

    /**
     * アンケート新規登録。<br />
     */
    private final QuestionnaireRegistLogic questionnaireRegistLogic;

    /**
     * ニュース詳細情報取得。<br />
     */
    private final NewsDetailsGetService newsDetailsGetService;

    /**
     * フリーエリア情報取得。<br />
     */
    private final FreeAreaGetService freeAreaGetService;

    /**
     * 公開ニュースリスト情報取得。<br />
     */
    private final OpenNewsListGetService openNewsListGetService;

    /**
     * 配送方法詳細取得サービス。<br />
     */
    private final DeliveryMethodDetailsGetService deliveryMethodDetailsGetService;

    /**
     * アクセス情報登録<br/>
     * 内容別にアクセス情報を登録する<br/>
     */
    private final AccessRegistService accessRegistService;

    /**
     * アクセス検索キーワード情報登録<br/>
     * 検索キーワード情報を登録する<br/>
     */
    private final AccessSearchKeywordRegistService accessSearchKeywordRegistService;

    /**
     * ショップ情報取得サービス
     */
    private final ShopGetService shopInfoGetService;

    /**
     * ショップHelper
     */
    private final ShopHelper shopHelper;

    /**
     * PageInfoヘルパー
     */
    private final PageInfoHelper pageInfoHelper;

    /**
     * 非同期処理サービス
     */
    private final AsyncService asyncService;

    /**
     * 決済方法取得ロジック
     */
    private final SettlementMethodGetLogic settlementMethodGetLogic;

    /**
     * アクセス情報Daoクラス
     */
    private final AccessInfoDao accessInfoDao;

    /**
     * キャンペーン取得（キャンペーンコード）ロジック<br/>
     */
    private final CampaignGetByCodeLogic campaignGetByCodeLogic;

    // 2023-renew No14 from here
    /**
     * カレンダー選択不可日付Logic
     */
    private final CalendarNotSelectDateLogic calendarNotSelectDateLogic;
    // 2023-renew No14 to here

    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     */
    public ShopController(ZipCodeAddressGetService zipCodeAddressGetService,
                          ZipCodeMatchingCheckService zipCodeMatchingCheckService,
                          ShopHelper shopHelper,
                          MulPayShopLogic mulPayShopLogic,
                          QuestionnaireGetLogic questionnaireGetLogic,
                          QuestionnaireCheckLogic questionnaireCheckLogic,
                          QuestionnaireRegistLogic questionnaireRegistLogic,
                          NewsDetailsGetService newsDetailsGetService,
                          FreeAreaGetService freeAreaGetService,
                          OpenNewsListGetService openNewsListGetService,
                          DeliveryMethodDetailsGetService deliveryMethodDetailsGetService,
                          AccessRegistService accessRegistService,
                          AccessSearchKeywordRegistService accessSearchKeywordRegistService,
                          PageInfoHelper pageInfoHelper,
                          AsyncService asyncService,
                          ShopGetService shopInfoGetService,
                          SettlementMethodGetLogic settlementMethodGetLogic,
                          AccessInfoDao accessInfoDao,
                          CampaignGetByCodeLogic campaignGetByCodeLogic,
                          CalendarNotSelectDateLogic calendarNotSelectDateLogic,
                          ConversionUtility conversionUtility) {
        this.zipCodeAddressGetService = zipCodeAddressGetService;
        this.zipCodeMatchingCheckService = zipCodeMatchingCheckService;
        this.shopHelper = shopHelper;
        this.mulPayShopLogic = mulPayShopLogic;
        this.questionnaireGetLogic = questionnaireGetLogic;
        this.questionnaireCheckLogic = questionnaireCheckLogic;
        this.questionnaireRegistLogic = questionnaireRegistLogic;
        this.newsDetailsGetService = newsDetailsGetService;
        this.freeAreaGetService = freeAreaGetService;
        this.openNewsListGetService = openNewsListGetService;
        this.deliveryMethodDetailsGetService = deliveryMethodDetailsGetService;
        this.accessRegistService = accessRegistService;
        this.accessSearchKeywordRegistService = accessSearchKeywordRegistService;
        this.pageInfoHelper = pageInfoHelper;
        this.asyncService = asyncService;
        this.shopInfoGetService = shopInfoGetService;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.accessInfoDao = accessInfoDao;
        this.campaignGetByCodeLogic = campaignGetByCodeLogic;
        // 2023-renew No14 from here
        this.calendarNotSelectDateLogic = calendarNotSelectDateLogic;
        // 2023-renew No14 to here
        this.conversionUtility = conversionUtility;
    }

    /**
     * GET /shop/get : ショップ情報取得
     * ショップ情報取得
     *
     * @return ショップ情報レスポンス (status code 200)
     * or その他エラー (status code 500)
     */
    @Override
    public ResponseEntity<ShopResponse> get() {

        ShopEntity shopEntity = shopInfoGetService.execute();

        ShopResponse shopResponse = shopHelper.toShopResponse(shopEntity);

        return new ResponseEntity<>(shopResponse, HttpStatus.OK);
    }

    /**
     * GET /shop/zipcode/address : 郵便番号住所情報取得
     * 郵便番号住所情報取得
     *
     * @param zipCodeAddressGetRequest 郵便番号住所情報取得リクエスト (optional)
     * @return 郵便番号住所Dtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ZipCodeAddressDtoResponse> getAddress(
                    @ApiParam(value = "郵便番号住所情報取得リクエスト") @Valid ZipCodeAddressGetRequest zipCodeAddressGetRequest) {
        ZipCodeAddressDto zipCodeAddressDto = zipCodeAddressGetService.execute(zipCodeAddressGetRequest.getZipCode());

        ZipCodeAddressDtoResponse response = shopHelper.toZipCodeAddressDtoResponse(zipCodeAddressDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/questionnaire-key/{questionnaireKey} : アンケート情報取得
     * アンケート情報取得
     *
     * @param questionnaireKey アンケート回答キー (required)
     * @return アンケートEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */

    @Override
    public ResponseEntity<QuestionnaireEntityResponse> getByQuestionnaireKey(String questionnaireKey) {
        QuestionnaireEntity entity = questionnaireGetLogic.getQuestionnaireByKey(questionnaireKey);

        QuestionnaireEntityResponse response = shopHelper.toQuestionnaireEntityResponse(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /shop/zipcode/check-zipcode-matching : 郵便番号整合性チェック
     * 郵便番号整合性チェック
     *
     * @param zipCodeMatchingCheckRequest 郵便番号整合性チェックリクエスト (required)
     * @return 結果成否レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultFlagResponse> registCheckZipcodeMatching(
                    @ApiParam(value = "郵便番号整合性チェックリクエスト", required = true) @Valid @RequestBody
                                    ZipCodeMatchingCheckRequest zipCodeMatchingCheckRequest) {
        // 配送方法登録サービス実行
        boolean resultFlag = zipCodeMatchingCheckService.execute(zipCodeMatchingCheckRequest.getZipCode(),
                                                                 zipCodeMatchingCheckRequest.getPrefecture()
                                                                );

        ResultFlagResponse response = new ResultFlagResponse();
        response.setResultFlag(resultFlag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/questionnaire-seq/{questionnaireSeq} : アンケート設問情報リスト取得
     * アンケート設問情報リスト取得
     *
     * @param questionnaireSeq アンケートSEQ（PK） (required)
     * @return アンケート回答画面表示用DtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<QuestionnaireReplyDisplayDtoListResponse> getByQuestionnaireSeq(Integer questionnaireSeq) {
        List<QuestionnaireReplyDisplayDto> entity = questionnaireGetLogic.getQuestionDtoList(questionnaireSeq);

        QuestionnaireReplyDisplayDtoListResponse response =
                        shopHelper.toQuestionnaireReplyDisplayDtoListResponse(entity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /shop/questionnaire/check : アンケートチェック
     * アンケートチェック
     *
     * @param questionnaireCheckRequest アンケートチェックリクエスト (required)
     * @return 結果レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultFlagResponse> checkQuestionnaire(@Valid QuestionnaireCheckRequest questionnaireCheckRequest) {
        boolean checkQuestionaireReply =
                        questionnaireCheckLogic.checkQuestionaireReply(questionnaireCheckRequest.getQuestionnaireSeq());

        ResultFlagResponse response = new ResultFlagResponse();
        response.setResultFlag(checkQuestionaireReply);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /shop/questionnaire : アンケート新規登録
     * アンケート新規登録
     *
     * @param questionnaireRegistRequest アンケート新規登録クリクエスト (required)
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> registQuestionnaire(@Valid QuestionnaireRegistRequest questionnaireRegistRequest) {

        QuestionnaireReplyEntity questionnaireReplyEntityRequest = shopHelper.toQuestionnaireReplyEntityRequest(
                        questionnaireRegistRequest.getQuestionnaireReplyEntityRequest());
        List<QuestionReplyEntity> questionReplyEntityRequestList = shopHelper.toQuestionReplyEntityRequest(
                        questionnaireRegistRequest.getQuestionReplyEntityRequest());

        questionnaireRegistLogic.registReplyEntity(questionnaireReplyEntityRequest, questionReplyEntityRequestList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /shop/news/details : ニュース詳細情報取得
     * ニュース詳細情報取得
     *
     * @param newsDetailsGetRequest ニュース詳細情報取得リクエスト (optional)
     * @return ニュースEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<NewsEntityResponse> getNewsDetails(@Valid NewsDetailsGetRequest newsDetailsGetRequest) {
        NewsEntity newsEntity = newsDetailsGetService.execute(newsDetailsGetRequest.getNewsSeq());

        NewsEntityResponse response = shopHelper.toNewsEntityResponse(newsEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/multipayment : マルチペイショップID取得
     * マルチペイショップID取得
     *
     * @return マルチペイショップレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MulPayShopResponse> getMultiPayment() {
        String getMulPayShopId = mulPayShopLogic.getMulPayShopId(1001);

        MulPayShopResponse response = new MulPayShopResponse();
        response.setMulPayShopId(getMulPayShopId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/news/freearea : フリーエリア情報取得
     * フリーエリア情報取得
     *
     * @param freeAreaGetRequest フリーエリア情報取得リクエスト (optional)
     * @return フリーエリアEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<FreeAreaEntityResponse> getNewsFreearea(@Valid FreeAreaGetRequest freeAreaGetRequest) {
        FreeAreaEntity freeAreaEntity = freeAreaGetService.execute(freeAreaGetRequest.getFreeAreaKey(),
                                                                   freeAreaGetRequest.getMemberInfoSeq()
                                                                  );

        FreeAreaEntityResponse response = shopHelper.toFreeAreaEntityResponse(freeAreaEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/news/open-news : 公開ニュースリスト情報取得
     * 公開ニュースリスト情報取得
     *
     * @param openNewsListGetRequest 公開ニュースリスト情報取得リクエスト (optional)
     * @return ニュースEntityListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<NewsEntityListResponse> getOpenNews(@Valid OpenNewsListGetRequest openNewsListGetRequest,
                                                              @Valid PageInfoRequest pageInfoRequest) {
        NewsSearchForDaoConditionDto reqDto = shopHelper.toNewsSearchForDaoConditionDto(openNewsListGetRequest);

        pageInfoHelper.setupPageInfo(reqDto, conversionUtility.toString(pageInfoRequest.getPage()),
                                     pageInfoRequest.getLimit(), pageInfoRequest.getOrderBy(), pageInfoRequest.getSort()
                                    );

        List<NewsEntity> newsEntityList = openNewsListGetService.execute(reqDto);

        NewsEntityListResponse response = new NewsEntityListResponse();

        // ページ情報レスポンスを設定
        PageInfoResponse pageInfoResponse = new PageInfoResponse();
        try {
            pageInfoHelper.setupResponsePager(reqDto, pageInfoResponse);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.info("ページ情報レスポンスの設定異常： " + e.getMessage());
        }

        response.setNewsEntityListResponse(shopHelper.toNewsEntityListResponse(newsEntityList));
        response.setPageInfo(pageInfoResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/delivery/delivery-method-details : 配送方法詳細取得
     * 配送方法詳細取得
     *
     * @param deliveryMethodDetailsGetRequest 配送方法詳細取得リクエスト (optional)
     * @return 配送方法詳細DtoMapレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<DeliveryMethodDetailsDtoMapResponse> getDeliveryMethodDetails(@Valid DeliveryMethodDetailsGetRequest deliveryMethodDetailsGetRequest) {

        Map<Integer, DeliveryMethodDetailsDto> deliveryMethodDetailsDtoMap = deliveryMethodDetailsGetService.execute(
                        deliveryMethodDetailsGetRequest.getDeliveryMethodSeqList());

        Map<String, DeliveryMethodDetailsDtoResponse> deliveryMethodMaster =
                        shopHelper.toDeliveryMethodDetailsDtoMapResponse(deliveryMethodDetailsDtoMap);

        DeliveryMethodDetailsDtoMapResponse response = new DeliveryMethodDetailsDtoMapResponse();

        response.setDeliveryMethodMaster(deliveryMethodMaster);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /shop/order-questionnaire : アンケート情報取得
     * アンケート情報取得
     *
     * @return アンケートEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<QuestionnaireEntityResponse> getOrderQuestionnaire() {
        QuestionnaireEntity questionnaireEntity = questionnaireGetLogic.getOrderQuestionnaire();
        QuestionnaireEntityResponse response = shopHelper.toQuestionnaireEntityResponse(questionnaireEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /shop/access : アクセス情報登録
     * アクセス情報登録
     *
     * @param accessRegistRequest アクセス情報登録リクエスト (required)
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> registAccess(@Valid AccessRegistRequest accessRegistRequest) {
        HTypeAccessType hTypeAccessType =
                        EnumTypeUtil.getEnumFromValue(HTypeAccessType.class, accessRegistRequest.getAccessType());

        String accessUid = accessRegistRequest.getAccessUid();
        String campaignCode = accessRegistRequest.getCampainCode();

        // サービス登録
        Object[] args = new Object[] {hTypeAccessType, accessRegistRequest.getGoodsGroupSeq(),
                        accessRegistRequest.getAccessCount(), accessUid, HTypeSiteType.FRONT_PC, campaignCode};
        Class<?>[] argsClass = new Class<?>[] {HTypeAccessType.class, Integer.class, Integer.class, String.class,
                        HTypeSiteType.class, String.class};

        // 非同期処理を登録
        AsyncTaskUtility.executeAfterTransactionCommit(
                        () -> asyncService.asyncService(accessRegistService, args, argsClass));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /shop/access/access-search-keyword : アクセス検索キーワード情報登録
     * アクセス検索キーワード情報登録
     *
     * @param accessSearchKeywordRegistRequest アクセス検索キーワード情報登録リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> registAccessSearchKeyword(@Valid AccessSearchKeywordRegistRequest accessSearchKeywordRegistRequest) {
        AccessSearchKeywordEntity accessSearchKeywordEntity =
                        shopHelper.toAccessSearchKeywordEntity(accessSearchKeywordRegistRequest);

        accessSearchKeywordRegistService.execute(
                        accessSearchKeywordEntity, accessSearchKeywordRegistRequest.getAccessUid());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /shop/settlement/{settlementMethodSeq} : 決済方法取得
     * 決済方法取得
     *
     * @param settlementMethodSeq 決済方法SEQ (required)
     * @return 決済方法レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<SettlementMethodResponse> getSettlementBySeq(
                    @ApiParam(value = "決済方法SEQ", required = true) @PathVariable("settlementMethodSeq")
                                    Integer settlementMethodSeq) {

        SettlementMethodEntity settlementMethodEntity = settlementMethodGetLogic.execute(settlementMethodSeq);

        SettlementMethodResponse settlementMethodResponse =
                        shopHelper.toSettlementMethodResponse(settlementMethodEntity);

        return new ResponseEntity<>(settlementMethodResponse, HttpStatus.OK);
    }

    /**
     * GET /shop/effective-campaign-code : 有効なキャンペーンコード取得
     * 有効なキャンペーンコード取得
     *
     * @param effectiveCampaignCodeGetRequest 有効なキャンペーンコード取得リクエスト (required)
     * @return 有効なキャンペーンコードレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */

    @Override
    public ResponseEntity<EffectiveCampaignCodeResponse> getEffectiveCampaignCode(@NotNull
                                                                                  @ApiParam(value = "有効なキャンペーンコード取得リクエスト",
                                                                                            required = true)
                                                                                  @Valid EffectiveCampaignCodeGetRequest effectiveCampaignCodeGetRequest) {
        // キャンペーンコード取得 取得できない場合は、空文字
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        String campaignCode =
                        accessInfoDao.getEffectiveCampaignCode(1001, effectiveCampaignCodeGetRequest.getAccessUid(),
                                                               conversionUtility.toTimeStamp(
                                                                               effectiveCampaignCodeGetRequest.getStartDate()),
                                                               conversionUtility.toTimeStamp(
                                                                               effectiveCampaignCodeGetRequest.getEndDate())
                                                              );
        EffectiveCampaignCodeResponse response = new EffectiveCampaignCodeResponse();
        response.setCampaignCode(campaignCode);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * GET /shop/campaign : キャンペーン取得
     * キャンペーン取得
     *
     * @param campaignGetRequest キャンペーン取得リクエスト (required)
     * @return キャンペーンEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */

    @Override
    public ResponseEntity<CampaignEntityResponse> getCampaign(@NotNull
                                                              @ApiParam(value = "キャンペーン取得リクエスト", required = true)
                                                              @Valid CampaignGetRequest campaignGetRequest) {
        CampaignEntity campaignEntity = campaignGetByCodeLogic.execute(1001, campaignGetRequest.getCampaignCode());
        CampaignEntityResponse response = shopHelper.toCampaignEntityResponse(campaignEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No14 from here

    /**
     * GET /shop/calendar-not-select-date : カレンダー選択不可日付取得
     * カレンダー選択不可日付取得
     *
     * @return カレンダー選択不可日付Entityレスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<CalendarNotSelectDateEntityResponse>> getCalendarNotSelectDate() {
        List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList = calendarNotSelectDateLogic.execute();
        List<CalendarNotSelectDateEntityResponse> response =
                        shopHelper.toCalendarNotSelectDateEntityResponseList(calendarNotSelectDateEntityList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No14 to here
}
