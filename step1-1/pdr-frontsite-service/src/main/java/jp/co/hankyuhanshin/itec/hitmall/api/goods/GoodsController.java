package jp.co.hankyuhanshin.itec.hitmall.api.goods;

import io.swagger.annotations.ApiParam;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDataGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsGetByCodeRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsMapGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsMapRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsFootPrintDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsFootPrintRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupByGoodsGroupCodesGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListByGoodsCodesGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsGroupTogetherBuyRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsUnitDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsUnitListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryMapGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.MultipleCategoryMapResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenCategoryPassListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenFootPrintListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenGoodsGroupDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenGoodsGroupDetailsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.OpenRelatedGoodsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.SaleGoodsListDetailGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.SaleGoodsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsUnitDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.salegoods.SaleGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipleCategory.ajax.MultipleCategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.category.MultipleCategoryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsUnitListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.SaleGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.json.category.CategoryDataGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTreeNodeGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.OpenCategoryPassListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint.GoodsFootClearService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint.OpenFootPrintListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsDetailsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.OpenGoodsGroupDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.OpenGoodsGroupSearchService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.OpenRelatedGoodsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForFrontService;
import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.AsyncTaskUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品Controllerクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RestController
public class GoodsController implements GoodsApi {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);
    /**
     * カテゴリ情報DTO取得
     */
    private final CategoryDetailsGetService categoryDetailsGetService;

    /**
     * あしあとクリアサービス
     */
    private final GoodsFootClearService goodsFootClearService;

    /**
     * カテゴリ取得サービス
     */
    private final CategoryGetService categoryGetService;

    /**
     * カテゴリ情報取得
     */
    private final CategoryDetailsGetLogic categoryDetailsGetLogic;

    /**
     * 商品詳細情報リスト取得サービス
     */
    private final GoodsDetailsListGetService goodsDetailsListGetService;

    /**
     * 商品詳細情報取得クラス(商品コード)
     */
    private final GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic;

    /**
     * 商品詳細情報MAP取得
     */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    /**
     * To fetch special category list
     */
    private final MultipleCategoryLogic multipleCategoryLogic;

    /**
     * 公開あしあと商品情報取得Service
     */
    private final OpenFootPrintListGetService openFootPrintListGetService;

    /**
     * 商品規格リスト取得ロジック
     */
    private final GoodsUnitListGetLogic goodsUnitListGetLogic;

    /**
     * 公開カテゴリパスリスト取得サービスクラス
     */
    private final OpenCategoryPassListGetService openCategoryPassListGetService;

    /**
     * 公開商品グループ情報検索サービスクラス
     */
    private final OpenGoodsGroupSearchService openGoodsGroupSearchService;

    /**
     * 公開商品グループ詳細情報取得サービス
     */
    private final OpenGoodsGroupDetailsGetService openGoodsGroupDetailsGetService;

    /**
     * 公開関連商品情報取得サービス
     */
    private final OpenRelatedGoodsListGetService openRelatedGoodsListGetService;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品 グループリストを購入 フロントサービスを利用する）<br/>
     */
    private final GoodsTogetherBuyGroupListGetForFrontService goodsTogetherBuyGroupListGetForFrontService;
    // 2023-renew No21 to here

    /**
     * カテゴリ情報Dao用検索条件Dtoクラス
     */
    private final CategoryTreeNodeGetService categoryTreeNodeGetService;

    /**
     * あしあと商品情報登録サービス
     */
    private final GoodsFootPrintRegistLogic goodsFootPrintRegistLogic;

    /**
     * 非同期用共通サービス
     */
    private final AsyncService asyncService;

    /**
     * カテゴリーデータ取得ロジック
     */
    private final CategoryDataGetLogic categoryDataGetLogic;

    /**
     * 商品Helper
     */
    private final GoodsHelper goodsHelper;

    /**
     * PageInfoヘルパー
     */
    private final PageInfoHelper pageInfoHelper;

    // 2023-renew No11 from here
    /** WEB-API連携取得 取りおき情報取得 */
    private final WebApiGetReserveLogic webApiGetReserveLogic;
    // 2023-renew No92 from here

    // 2023-renew AddNo5 from here
    /**
     * 公開商品グループリスト取得
     */
    private final GoodsGroupListGetService goodsGroupListGetService;
    // 2023-renew AddNo5 to here

    // 2023-renew No65 from here
    /**
     * ...
     */
    private final SaleGoodsListGetLogic saleGoodsListGetLogic;
    // 2023-renew No65 to here

    /**
     * コンストラク
     *
     * @param categoryDetailsGetService                         カテゴリ情報DTO取得
     * @param goodsFootClearService                             あしあと商品クリアクラス
     * @param categoryGetService                                カテゴリ取得
     * @param categoryDetailsGetLogic                           カテゴリ情報DTO取得
     * @param goodsDetailsListGetService                        商品詳細DTOリスト取得
     * @param goodsDetailsGetByCodeLogic                        商品詳細情報取得クラス(商品コード)
     * @param goodsDetailsMapGetLogic                           商品詳細情報MAP取得
     * @param multipleCategoryLogic                             To fetch special category list
     * @param openFootPrintListGetService                       公開あしあと商品情報取得Service
     * @param goodsUnitListGetLogic                             商品規格リスト取得ロジック
     * @param openCategoryPassListGetService                    公開カテゴリパスリスト取得
     * @param openGoodsGroupSearchService                       公開商品グループ情報検索
     * @param openGoodsGroupDetailsGetService                   公開商品グループ詳細情報取得
     * @param openRelatedGoodsListGetService                    公開関連商品情報取得
     * @param categoryTreeNodeGetService                        カテゴリ木構造取得
     * @param goodsFootPrintRegistLogic                         あしあと商品情報登録
     * @param goodsHelper                                       商品Helper
     * @param goodsTogetherBuyGroupListGetForFrontService       よく一緒に購入される商品Service
     * @param pageInfoHelper                                    PageInfoヘルパー
     */
    @Autowired
    public GoodsController(CategoryDetailsGetService categoryDetailsGetService,
                           GoodsFootClearService goodsFootClearService,
                           CategoryGetService categoryGetService,
                           CategoryDetailsGetLogic categoryDetailsGetLogic,
                           GoodsDetailsListGetService goodsDetailsListGetService,
                           GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic,
                           GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                           MultipleCategoryLogic multipleCategoryLogic,
                           OpenFootPrintListGetService openFootPrintListGetService,
                           GoodsUnitListGetLogic goodsUnitListGetLogic,
                           OpenCategoryPassListGetService openCategoryPassListGetService,
                           OpenGoodsGroupSearchService openGoodsGroupSearchService,
                           OpenGoodsGroupDetailsGetService openGoodsGroupDetailsGetService,
                           OpenRelatedGoodsListGetService openRelatedGoodsListGetService,
                           CategoryTreeNodeGetService categoryTreeNodeGetService,
                           GoodsFootPrintRegistLogic goodsFootPrintRegistLogic,
                           AsyncService asyncService,
                           CategoryDataGetLogic categoryDataGetLogic,
                           GoodsHelper goodsHelper,
                           PageInfoHelper pageInfoHelper,
                           WebApiGetReserveLogic webApiGetReserveLogic,
                           GoodsGroupListGetService goodsGroupListGetService,
                           // 2023-renew No65 from here
                           SaleGoodsListGetLogic saleGoodsListGetLogic,
                           // 2023-renew No65 to here
                           // 2023-renew No21 from here
                           GoodsTogetherBuyGroupListGetForFrontService goodsTogetherBuyGroupListGetForFrontService) {
                           // 2023-renew No21 to here
        this.categoryDetailsGetService = categoryDetailsGetService;
        this.goodsFootClearService = goodsFootClearService;
        this.categoryGetService = categoryGetService;
        this.categoryDetailsGetLogic = categoryDetailsGetLogic;
        this.goodsDetailsListGetService = goodsDetailsListGetService;
        this.goodsDetailsGetByCodeLogic = goodsDetailsGetByCodeLogic;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        this.multipleCategoryLogic = multipleCategoryLogic;
        this.openFootPrintListGetService = openFootPrintListGetService;
        this.goodsUnitListGetLogic = goodsUnitListGetLogic;
        this.openCategoryPassListGetService = openCategoryPassListGetService;
        this.openGoodsGroupSearchService = openGoodsGroupSearchService;
        this.openGoodsGroupDetailsGetService = openGoodsGroupDetailsGetService;
        this.openRelatedGoodsListGetService = openRelatedGoodsListGetService;
        this.categoryTreeNodeGetService = categoryTreeNodeGetService;
        this.goodsFootPrintRegistLogic = goodsFootPrintRegistLogic;
        this.asyncService = asyncService;
        this.categoryDataGetLogic = categoryDataGetLogic;
        this.goodsHelper = goodsHelper;
        this.pageInfoHelper = pageInfoHelper;
        this.webApiGetReserveLogic = webApiGetReserveLogic;
        this.goodsGroupListGetService = goodsGroupListGetService;
        // 2023-renew No21 from here
        this.goodsTogetherBuyGroupListGetForFrontService = goodsTogetherBuyGroupListGetForFrontService;
        // 2023-renew No21 to here
        this.saleGoodsListGetLogic = saleGoodsListGetLogic;
    }
    // 2023-renew No11 to here
    // 2023-renew No92 to here

    /**
     * GET /goods/category/category-tree-node : カテゴリ木構造取得
     * カテゴリ木構造取得
     *
     * @param categoryTreeNodeGetRequest 複数カテゴリ取得リクエスト (optional)
     * @return カテゴリ木構造レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CategoryTreeNodeResponse> getCategoryTreeNode(@ApiParam(value = "複数カテゴリ取得リクエスト")
                                                                        @Valid CategoryTreeNodeGetRequest categoryTreeNodeGetRequest) {

        // 検索条件の作成
        CategorySearchForDaoConditionDto conditionDto =
                        goodsHelper.toCategorySearchForDaoConditionDto(categoryTreeNodeGetRequest);

        CategoryDto categoryDto = categoryTreeNodeGetService.execute(conditionDto, null, HTypeSiteType.FRONT_PC);

        CategoryEntityResponse categoryEntityResponse =
                        goodsHelper.toCategoryEntityResponse(categoryDto.getCategoryEntity());
        CategoryDisplayEntityResponse categoryDisplayEntityResponse =
                        goodsHelper.toCategoryDisplayEntityResponse(categoryDto.getCategoryDisplayEntity());
        List<CategoryTreeNodeResponse> categoryTreeNodeResponseList =
                        goodsHelper.toCategoryTreeNodeResponseList(categoryDto.getCategoryDtoList());

        CategoryTreeNodeResponse response = new CategoryTreeNodeResponse();

        response.setCategoryEntity(categoryEntityResponse);
        response.setCategoryDisplayEntity(categoryDisplayEntityResponse);
        response.setCategoryDtoList(categoryTreeNodeResponseList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * DELETE /goods/footprint/goods : あしあと商品クリアクラス
     * あしあと商品クリアクラス
     *
     * @param goodsFootPrintDeleteRequest あしあと商品情報削除リクエスト (required)
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteFootprintGoods(@Valid GoodsFootPrintDeleteRequest goodsFootPrintDeleteRequest) {
        goodsFootClearService.execute(goodsFootPrintDeleteRequest.getAccessUid());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /goods/category/{categoryId} : カテゴリ取得
     * カテゴリ取得
     *
     * @param categoryId カテゴリId (required)
     * @return カテゴリEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     **/
    @Override
    public ResponseEntity<CategoryEntityResponse> getCategory(String categoryId) {

        CategoryEntity categoryEntity = categoryGetService.execute(categoryId);
        CategoryEntityResponse response = goodsHelper.toCategoryEntityResponse(categoryEntity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/category/details : カテゴリ情報取得
     * カテゴリ情報取得
     *
     * @param categoryDetailsGetRequest カテゴリ情報取得リクエスト (required)
     * @return カテゴリDtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CategoryDetailsDtoResponse> getCategoryDetails(@NotNull
                                                                         @ApiParam(value = "カテゴリ情報取得リクエスト",
                                                                                   required = true)
                                                                         @Valid CategoryDetailsGetRequest categoryDetailsGetRequest) {

        CategorySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        conditionDto.setCategoryId(categoryDetailsGetRequest.getCategoryId());
        conditionDto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                 categoryDetailsGetRequest.getOpenStatus()
                                                                ));

        CategoryDetailsDto categoryDetailsDto = categoryDetailsGetService.execute(conditionDto);

        CategoryDetailsDtoResponse response = goodsHelper.toCategoryDetailsDtoResponse(categoryDetailsDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/category/details-list : カテゴリ情報DTO取得
     * カテゴリ情報DTO取得
     *
     * @param multipleCategoryGetRequest 複数カテゴリ情報取得リクエスト (required)
     * @return カテゴリ情報DtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<CategoryDetailsDtoResponse>> getCategoryDetailsList(@NotNull
                                                                                   @ApiParam(value = "複数カテゴリ情報取得リクエスト",
                                                                                             required = true)
                                                                                   @Valid MultipleCategoryGetRequest multipleCategoryGetRequest) {

        List<CategoryDetailsDto> categoryDetailsDtoList = categoryDetailsGetLogic.getCategoryDetailsDtoList(
                        multipleCategoryGetRequest.getCategoryIdList());
        List<CategoryDetailsDtoResponse> response =
                        goodsHelper.toListCategoryDetailsDtoResponse(categoryDetailsDtoList);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * GET /goods/details/by-code : 商品詳細情報取得(商品コード)
     * 商品詳細情報取得(商品コード)
     *
     * @param goodsDetailsGetByCodeRequest 商品詳細情報取得(商品コード)リクエスト (required)
     * @return 商品詳細Dtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<GoodsDetailsDtoResponse> getDetailsByCode(@NotNull
                                                                    @ApiParam(value = "商品詳細情報取得(商品コード)リクエスト",
                                                                              required = true)
                                                                    @Valid GoodsDetailsGetByCodeRequest goodsDetailsGetByCodeRequest) {
        GoodsDetailsDto goodsDetailsDto =
                        goodsDetailsGetByCodeLogic.execute(1001, goodsDetailsGetByCodeRequest.getCode(), null,
                                                           EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                         goodsDetailsGetByCodeRequest.getGoodsOpenStatus()
                                                                                        )
                                                          );
        GoodsDetailsDtoResponse response = null;
        try {
            response = goodsHelper.toGoodsDetailsDtoResponse(goodsDetailsDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/details-list : 商品詳細情報リスト取得
     * 商品詳細情報リスト取得
     *
     * @param goodsDetailsListGetRequest 商品詳細情報リスト取得リクエスト (required)
     * @return 商品詳細DtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<GoodsDetailsDtoResponse>> getDetailsList(@NotNull
                                                                        @ApiParam(value = "商品詳細情報リスト取得リクエスト",
                                                                                  required = true)
                                                                        @Valid GoodsDetailsListGetRequest goodsDetailsListGetRequest) {
        // 受注詳細情報リスト取得
        List<GoodsDetailsDto> goodsDetailsList =
                        goodsDetailsListGetService.execute(goodsDetailsListGetRequest.getGoodsSeqList());

        List<GoodsDetailsDtoResponse> response = null;
        try {
            response = goodsHelper.toListGoodsDetailsDtoResponse(goodsDetailsList);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/details-map : 商品詳細情報MAP取得
     * 商品詳細情報MAP取得
     *
     * @param goodsDetailsMapGetRequest 商品詳細情報MAP取得リクエスト (required)
     * @return 商品詳細DtoMAP取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Map<String, GoodsDetailsDtoResponse>> getDetailsMap(@NotNull
                                                                              @ApiParam(value = "商品詳細情報MAP取得リクエスト",
                                                                                        required = true)
                                                                              @Valid GoodsDetailsMapGetRequest goodsDetailsMapGetRequest) {

        Map<String, GoodsDetailsDto> goodsDetailsDtoMap =
                        goodsDetailsMapGetLogic.executeByExistGoodsCode(goodsDetailsMapGetRequest.getGoodsCodeList());
        Map<String, GoodsDetailsDtoResponse> goodsDetailsDtoResponseMap = null;
        try {
            goodsDetailsDtoResponseMap = goodsHelper.toGoodsDetailsDtoResponseMap(goodsDetailsDtoMap);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }
        return new ResponseEntity<>(goodsDetailsDtoResponseMap, HttpStatus.OK);
    }

    /**
     * GET /goods/footprint/open-list : 公開あしあと商品情報取得
     * 公開あしあと商品情報取得
     *
     * @param openFootPrintListGetRequest 公開あしあと商品情報取得リクエスト (required)
     * @return 商品グループDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<GoodsGroupDtoListResponse> getFootprintOpenList(@NotNull
                                                                          @ApiParam(value = "公開あしあと商品情報取得リクエスト",
                                                                                    required = true)
                                                                          @Valid OpenFootPrintListGetRequest openFootPrintListGetRequest) {
        List<GoodsGroupDto> footprintGoodsGroupDtoList =
                        openFootPrintListGetService.execute(openFootPrintListGetRequest.getLimit(), null,
                                                            openFootPrintListGetRequest.getAccessUid()
                                                           );

        GoodsGroupDtoListResponse response = new GoodsGroupDtoListResponse();
        response.setGoodsGroupDtoListResponse(goodsHelper.toListGoodsGroupDtoResponse(footprintGoodsGroupDtoList));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/goods/unit-list : 商品規格リスト取得
     * 商品規格リスト取得
     *
     * @param goodsUnitListGetRequest 商品規格リスト取得リクエスト (required)
     * @return 商品規格DtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<GoodsUnitDtoResponse>> getGoodsUnitList(@NotNull
                                                                       @ApiParam(value = "商品規格リスト取得リクエスト",
                                                                                 required = true)
                                                                       @Valid GoodsUnitListGetRequest goodsUnitListGetRequest) {
        List<GoodsUnitDto> goodsUnit2DtoList = goodsUnitListGetLogic.getUnit2List(goodsUnitListGetRequest.getGgcd(),
                                                                                  goodsUnitListGetRequest.getGcd()
                                                                                 );
        List<GoodsUnitDtoResponse> responses = goodsHelper.toListGoodsUnitDtoResponse(goodsUnit2DtoList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /**
     * GET /goods/category/open-category-pass-list : 公開カテゴリパスリスト取得
     * 公開カテゴリパスリスト取得
     *
     * @param openCategoryPassListGetRequest 公開カテゴリパスリスト取得リクエスト (required)
     * @return カテゴリDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<CategoryDetailsDtoResponse>> getOpenCategoryPassList(@NotNull
                                                                                    @ApiParam(value = "公開カテゴリパスリスト取得リクエスト",
                                                                                              required = true)
                                                                                    @Valid OpenCategoryPassListGetRequest openCategoryPassListGetRequest) {
        List<CategoryDetailsDto> categoryDetailsDtoList =
                        openCategoryPassListGetService.execute(openCategoryPassListGetRequest.getCategorySeqPath());
        List<CategoryDetailsDtoResponse> responses =
                        goodsHelper.toListCategoryDetailsDtoResponse(categoryDetailsDtoList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /**
     * GET /goods/group/open-goods-group-details : 公開商品グループ詳細情報取得
     * 公開商品グループ詳細情報取得
     *
     * @param openGoodsGroupDetailsGetRequest 公開商品グループ詳細情報取得リクエスト (required)
     * @return 商品グループDtoレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<GoodsGroupDtoResponse> getOpenGoodsGroupDetails(@NotNull
                                                                          @ApiParam(value = "公開商品グループ詳細情報取得リクエスト",
                                                                                    required = true)
                                                                          @Valid OpenGoodsGroupDetailsGetRequest openGoodsGroupDetailsGetRequest) {
        GoodsGroupDto goodsGroupDto =
                        openGoodsGroupDetailsGetService.execute(openGoodsGroupDetailsGetRequest.getGoodsGroupCode(),
                                                                openGoodsGroupDetailsGetRequest.getGoodsCode()
                                                               );
        GoodsGroupDtoResponse response = goodsHelper.toGoodsGroupDtoResponse(goodsGroupDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/group/open-goods-group-details-list : 公開商品グループ情報検索
     * 公開商品グループ情報検索
     *
     * @param openGoodsGroupDetailsListGetRequest 公開商品グループ情報検索リクエスト (required)
     * @param pageInfoRequest                     ページ情報リクエスト (optional)
     * @return 商品グループDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<GoodsGroupDtoListResponse> getOpenGoodsGroupDetailsList(@NotNull
                                                                                  @ApiParam(value = "公開商品グループ情報検索リクエスト",
                                                                                            required = true)
                                                                                  @Valid OpenGoodsGroupDetailsListGetRequest openGoodsGroupDetailsListGetRequest,
                                                                                  @Valid PageInfoRequest pageInfoRequest) {
        GoodsGroupSearchForDaoConditionDto conditionDto =
                        goodsHelper.toGoodsGroupSearchForDaoConditionDto(openGoodsGroupDetailsListGetRequest);

        pageInfoHelper.setupPageInfo(conditionDto, String.valueOf(pageInfoRequest.getPage()),
                                     pageInfoRequest.getLimit(), pageInfoRequest.getOrderBy(), pageInfoRequest.getSort()
                                    );

        List<GoodsGroupDto> goodsGroupDtoList = openGoodsGroupSearchService.execute(conditionDto);

        // ページ情報レスポンスを設定
        PageInfoResponse pageInfoResponse = new PageInfoResponse();
        try {
            pageInfoHelper.setupResponsePager(conditionDto, pageInfoResponse);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.info("ページ情報レスポンスの設定異常： " + e.getMessage());
        }

        GoodsGroupDtoListResponse response = new GoodsGroupDtoListResponse();

        response.setGoodsGroupDtoListResponse(goodsHelper.toListGoodsGroupDtoResponse(goodsGroupDtoList));
        response.setPageInfo(pageInfoResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /goods/relation/open-related-goods-list : 関連商品情報取得
     * 関連商品情報取得
     *
     * @param openRelatedGoodsListGetRequest 関連商品情報取得リクエスト (required)
     * @param pageInfoRequest                ページ情報リクエスト (optional)
     * @return 商品グループDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<GoodsGroupDtoListResponse> getRelationOpenRelatedGoodsList(@NotNull
                                                                                     @ApiParam(value = "関連商品情報取得リクエスト",
                                                                                               required = true)
                                                                                     @Valid OpenRelatedGoodsListGetRequest openRelatedGoodsListGetRequest,
                                                                                     @Valid PageInfoRequest pageInfoRequest) {
        GoodsRelationSearchForDaoConditionDto conditionDto =
                        goodsHelper.toGoodsRelationSearchForDaoConditionDto(openRelatedGoodsListGetRequest);

        List<GoodsGroupDto> relatedGoodsGroupDtoList =
                        openRelatedGoodsListGetService.execute(conditionDto, pageInfoRequest.getLimit());

        // ページング検索セットアップ
        pageInfoHelper.setupPageInfo(
                        conditionDto, pageInfoRequest.getPage() != null ?
                                        String.valueOf(pageInfoRequest.getPage()) :
                                        null, pageInfoRequest.getLimit(), pageInfoRequest.getOrderBy(),
                        pageInfoRequest.getSort()
                                    );

        GoodsGroupDtoListResponse response = new GoodsGroupDtoListResponse();
        response.setGoodsGroupDtoListResponse(goodsHelper.toListGoodsGroupDtoResponse(relatedGoodsGroupDtoList));
        response.setPageInfo(new PageInfoResponse());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /goods/footprint/goods : あしあと商品情報登録
     * あしあと商品情報登録
     *
     * @param goodsFootPrintRegistRequest あしあと商品情報登録リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> registFootprintGoods(
                    @ApiParam(value = "あしあと商品情報登録リクエスト", required = true) @Valid @RequestBody
                    GoodsFootPrintRegistRequest goodsFootPrintRegistRequest) {
        FootprintEntity footprintEntity = goodsHelper.toFootprintEntity(goodsFootPrintRegistRequest);

        // サービス登録
        Object[] args = new Object[] {footprintEntity};
        Class<?>[] argsClass = new Class<?>[] {FootprintEntity.class};
        GoodsFootPrintRegistLogic service = ApplicationContextUtility.getBean(GoodsFootPrintRegistLogic.class);

        // 非同期処理を登録
        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
            asyncService.asyncService(service, args, argsClass);
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /goods/category/multiple-category-map : 複数カテゴリ取得
     * 複数カテゴリ取得
     *
     * @param multipleCategoryMapGetRequest 複数カテゴリ取得リクエスト (optional)
     * @return 複数カテゴリMapレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MultipleCategoryMapResponse> getMultipleCategoryMap(
                    @ApiParam(value = "複数カテゴリ取得リクエスト")
                    @Valid MultipleCategoryMapGetRequest multipleCategoryMapGetRequest) {
        Map<String, List<MultipleCategoryGoodsDetailsDto>> resultMap =
                        multipleCategoryLogic.getCategoryMap(multipleCategoryMapGetRequest.getCategory(),
                                                             multipleCategoryMapGetRequest.getSeq(),
                                                             multipleCategoryMapGetRequest.getLimit(),
                                                             multipleCategoryMapGetRequest.getPriceFrom(),
                                                             multipleCategoryMapGetRequest.getPriceTo(),
                                                             multipleCategoryMapGetRequest.getStock(),
                                                             multipleCategoryMapGetRequest.getViewType(),
                                                             multipleCategoryMapGetRequest.getIsLogin(),
                                                             multipleCategoryMapGetRequest.getBusinessType(),
                                                             multipleCategoryMapGetRequest.getConfDocumentType()
                                                            );

        MultipleCategoryMapResponse multipleCategoryMapResponse = goodsHelper.toMultipleCategoryMapResponse(resultMap);

        return new ResponseEntity<>(multipleCategoryMapResponse, HttpStatus.OK);
    }

    /**
     * POST /goods/category/category-data/get : カテゴリーデータ取得
     * カテゴリーデータ取得
     *
     * @param categoryDataGetRequest カテゴリーデータ取得リクエスト (required)
     * @return カテゴリ情報DtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<CategoryDetailsDtoResponse>> getCategoryData(@Valid CategoryDataGetRequest categoryDataGetRequest) {
        CategorySearchForDaoConditionDto conditionDto =
                        goodsHelper.toCategorySearchForDaoConditionDto(categoryDataGetRequest.getConditionDto());
        HTypeCategoryType categoryType = EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class,
                                                                       categoryDataGetRequest.getCategoryType()
                                                                      );
        List<CategoryDetailsDto> categoryDetailsDtoList =
                        categoryDataGetLogic.execute(conditionDto, categoryDataGetRequest.getStartCategorylevel(),
                                                     categoryDataGetRequest.getEndCategorylevel(), categoryType
                                                    );

        List<CategoryDetailsDtoResponse> responses =
                        goodsHelper.toListCategoryDetailsDtoResponse(categoryDetailsDtoList);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /**
     * GET /goods/goods-details-map : 商品詳細情報MAP取得
     * 商品詳細情報MAP取得
     *
     * @param goodsDetailsMapRequest 商品詳細情報MAP取得リクエスト (required)
     * @return 商品詳細情報MAP取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Map<String, GoodsDetailsDtoResponse>> getGoodsDetailsMap(@NotNull @Valid GoodsDetailsMapRequest goodsDetailsMapRequest) {
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap =
                        goodsDetailsMapGetLogic.execute(goodsDetailsMapRequest.getGoodsSeqList());

        Map<String, GoodsDetailsDtoResponse> responseMap = new HashMap<>();
        try {
            responseMap = goodsHelper.toMapStringGoodsDetailsDtoResponse(goodsDetailsDtoMap);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // 2023-renew No92 to here

    /**
     * POST /goods/group/goods-codes : WEB-API商品グループ詳細情報取得
     * 商品グループ詳細情報取得
     *
     * @param goodsGroupDtoListByGoodsCodesGetRequest 商品グループ詳細情報取得リクエスト (required)
     * @return 商品グループ詳細情報取得レスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<GoodsGroupDtoResponse>> getGoodsGroupsByGoodsCodes(@NotNull @Valid GoodsGroupDtoListByGoodsCodesGetRequest goodsGroupDtoListByGoodsCodesGetRequest) {
        List<GoodsGroupDto> goodsGroupDtos =
                        openGoodsGroupDetailsGetService.execute(goodsGroupDtoListByGoodsCodesGetRequest.getGoodsCodesList());
        List<GoodsGroupDtoResponse> responses =
                        goodsGroupDtos.stream().map(goodsHelper::toGoodsGroupDtoResponse).collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    // 2023-renew No92 to here

    // 2023-renew AddNo5 from here
    /**
     * GET /goods/group/goods-group-codes : 商品グループ詳細情報取得
     * 商品グループ詳細情報取得
     *
     * @param goodsGroupByGoodsGroupCodesGetRequest    商品グループ詳細情報取得リクエスト (required)
     * @return 商品グループDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<GoodsGroupDtoResponse>> getGoodsGroupListByGoodsGroupCodes(
                    @NotNull @Valid GoodsGroupByGoodsGroupCodesGetRequest goodsGroupByGoodsGroupCodesGetRequest) {
        List<GoodsGroupDto> goodsGroupDtos = goodsGroupListGetService.execute(goodsGroupByGoodsGroupCodesGetRequest.getGoodsGroupCodesList());
        List<GoodsGroupDtoResponse> responses = goodsGroupDtos.stream()
                                                              .map(goodsHelper::toGoodsGroupDtoResponse)
                                                              .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    // 2023-renew AddNo5 to here

    // 2023-renew No21 from here
    /**
     * GET /goods/togeter-buy-status : よく一緒に購入される商品 ステータスごとにグループを購入する
     *
     * @return 商品グループDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<GoodsGroupDtoListResponse> getGoodsTogetherBuyListByStatus(@NotNull @Valid GoodsGroupTogetherBuyRequest goodsGroupTogetherBuyRequest) {
        List<GoodsGroupDto> goodsGroupDtoList =
                        goodsTogetherBuyGroupListGetForFrontService.execute(goodsGroupTogetherBuyRequest.getGoodsSeq(),
                                                                            goodsGroupTogetherBuyRequest.getLimit()
                                                                           );
        GoodsGroupDtoListResponse response = new GoodsGroupDtoListResponse();
        response.setGoodsGroupDtoListResponse(goodsHelper.toListGoodsGroupDtoResponse(goodsGroupDtoList));
        response.setPageInfo(new PageInfoResponse());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // 2023-renew No21 to here
    // 2023-renew No65 from here
    /**
     * GET /goods/salegoods/list : セール商品詳細リスト
     * セール商品詳細リスト
     *
     * @param saleGoodsListGetRequest セール商品Listクラスリクエスト (required)
     * @return セール商品Listレスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<SaleGoodsListDetailGetResponse>> getSaleGoodsList(@NotNull @Valid SaleGoodsListGetRequest saleGoodsListGetRequest) {
        List<SaleGoodsDto> results = saleGoodsListGetLogic.execute(saleGoodsListGetRequest.getSaleGoodsSeqList());
        return new ResponseEntity<>(goodsHelper.toSaleGoodsListResponse(results), HttpStatus.OK);
    }
    // 2023-renew No65 to here

}
