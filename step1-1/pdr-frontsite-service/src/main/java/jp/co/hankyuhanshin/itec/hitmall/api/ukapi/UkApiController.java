package jp.co.hankyuhanshin.itec.hitmall.api.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.AbstractUkApiResponseHeaderDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponseInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponseInfoItemDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkRWordResponseInfoWordDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchContentsResponseInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponseInfoDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchResponseHeaderInfoFallBackDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiRWordResponseInfoItemDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchContentsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchContentsResponseDocsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchContentsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsResponseDocsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkApiUniSearchGoodsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetCategoryIdTreeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.UkApiRWordLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.UkApiUniSearchContentsLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.ukapi.UkApiUniSearchGoodsLogic;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UkApiController implements UkapiApi {
    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UkApiController.class);

    /**
     * UK-API連携クラス
     * 関連ワード
     */
    private final UkApiRWordLogic ukApiRWordLogic;

    /**
     * UK-API連携クラス
     * ユニサーチ（商品）
     */
    private final UkApiUniSearchGoodsLogic ukApiUniSearchGoodsLogic;

    /**
     * UK-API連携クラス
     * ユニサーチ（コンテンツ）
     */
    private final UkApiUniSearchContentsLogic ukApiUniSearchContentsLogic;

    /**
     * カテゴリーIDツリー取得クラス
     */
    private final CategoryGetCategoryIdTreeLogic categoryGetCategoryIdTreeLogic;

    /**
     * PageInfoヘルパークラス
     */
    private final PageInfoHelper pageInfoHelper;

    /**
     * 画像ファイル拡張子(.jpg)
     */
    private static final String FILE_NAME_EXTENTION_JPG = ".jpg";

    @Autowired
    public UkApiController(UkApiRWordLogic ukApiRWordLogic,
                           UkApiUniSearchGoodsLogic ukApiUniSearchGoodsLogic,
                           UkApiUniSearchContentsLogic ukApiUniSearchContentsLogic,
                           CategoryGetCategoryIdTreeLogic categoryGetCategoryIdTreeLogic,
                           PageInfoHelper pageInfoHelper) {
        this.ukApiRWordLogic = ukApiRWordLogic;
        this.ukApiUniSearchGoodsLogic = ukApiUniSearchGoodsLogic;
        this.ukApiUniSearchContentsLogic = ukApiUniSearchContentsLogic;
        this.categoryGetCategoryIdTreeLogic = categoryGetCategoryIdTreeLogic;
        this.pageInfoHelper = pageInfoHelper;
    }

    /**
     * GET /ukapi/rword : UK-API連携 関連ワード
     * UK-API連携関連ワード
     *
     * @param ukApiGetUkRWordRequest UK-API連携関連ワードリクエスト
     * @return UK-API連携関連ワードレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<UkApiGetUkRWordResponse> getUkRWord(@Valid UkApiGetUkRWordRequest ukApiGetUkRWordRequest) {

        UkApiRWordRequestDto reqDto = ApplicationContextUtility.getBean(UkApiRWordRequestDto.class);
        reqDto.setKw(ukApiGetUkRWordRequest.getKw());
        reqDto.setRows(ukApiGetUkRWordRequest.getRows());

        UkApiRWordResponseDto ukApiResponseDto = ukApiRWordLogic.execute(reqDto);

        UkApiGetUkRWordResponse response = new UkApiGetUkRWordResponse();
        AbstractUkApiResponseHeaderDtoResponse header = new AbstractUkApiResponseHeaderDtoResponse();
        UkApiGetUkRWordResponseInfoDtoResponse responseInfo = new UkApiGetUkRWordResponseInfoDtoResponse();

        // レスポンスヘッダー設定
        header.setStatus(ukApiResponseDto.getResponseHeader().getStatus());
        header.setQtime(ukApiResponseDto.getResponseHeader().getQTime());
        response.setResponseHeader(header);

        // 関連ワードあり
        if (ukApiResponseDto.getResponse() != null) {
            if (ukApiResponseDto.getResponse().getRelatedWord() != null) {
                UkApiGetUkRWordResponseInfoWordDtoResponse word = new UkApiGetUkRWordResponseInfoWordDtoResponse();
                UkApiGetUkRWordResponseInfoDocsDtoResponse docs = new UkApiGetUkRWordResponseInfoDocsDtoResponse();
                List<UkApiGetUkRWordResponseInfoItemDtoResponse> itemList = new ArrayList<>();
                UkApiGetUkRWordResponseInfoItemDtoResponse item = null;

                for (UkApiRWordResponseInfoItemDto dto : ukApiResponseDto.getResponse()
                                                                         .getRelatedWord()
                                                                         .getDocs()
                                                                         .getItem()) {
                    item = new UkApiGetUkRWordResponseInfoItemDtoResponse();
                    item.setWord(dto.getWord());
                    itemList.add(item);
                }
                docs.setItem(itemList);
                word.setDocs(docs);
                responseInfo.setRelatedWord(word);
            }

            // レスポンス設定
            responseInfo.setNumFound(ukApiResponseDto.getResponse().getNumFound());
            response.setResponse(responseInfo);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /ukapi/p1/vi/pdr : UK-API連携 ユニサーチ（商品）
     * UK-API連携ユニサーチ（商品）
     *
     * @param ukApiGetUkUniSearchGoodsRequest UK-API連携ユニサーチ（商品）リクエスト
     * @return UK-API連携ユニサーチ（商品）レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<UkApiGetUkUniSearchGoodsResponse> getUkUniSearchGoods(@Valid UkApiGetUkUniSearchGoodsRequest ukApiGetUkUniSearchGoodsRequest,
                                                                                @Valid PageInfoRequest pageInfoRequest) {

        // カテゴリIDをユニサーチ（商品）リクエスト用に設定
        String categoryId = categoryGetCategoryIdTreeLogic.execute(ukApiGetUkUniSearchGoodsRequest.getCategory());

        UkApiUniSearchGoodsRequestDto reqDto = ApplicationContextUtility.getBean(UkApiUniSearchGoodsRequestDto.class);
        reqDto.setCategory(categoryId);
        reqDto.setKw(ukApiGetUkUniSearchGoodsRequest.getKw());
        reqDto.setPage(ukApiGetUkUniSearchGoodsRequest.getPage());
        reqDto.setRows(ukApiGetUkUniSearchGoodsRequest.getRows());
        reqDto.setSort(ukApiGetUkUniSearchGoodsRequest.getSortType());
        reqDto.setUser(ukApiGetUkUniSearchGoodsRequest.getUser());

        PageInfo pageInfo = new PageInfo();
        // ページ情報リクエストが設定されているか確認
        // Ajaxからの呼び出しの場合、リクエストが空のためスキップする
        if (pageInfoRequest != null && pageInfoRequest.getPage() != null && pageInfoRequest.getLimit() != null) {
            pageInfo = pageInfoHelper.setupPageInfo(String.valueOf(pageInfoRequest.getPage()),
                                                    pageInfoRequest.getLimit(), false,
                                                    ukApiGetUkUniSearchGoodsRequest.getSortType()
                                                   );
        }

        // ユニサーチと通信するLogic
        UkApiUniSearchGoodsResponseDto ukApiResponseDto = ukApiUniSearchGoodsLogic.execute(reqDto);

        UkApiGetUkUniSearchGoodsResponse response = new UkApiGetUkUniSearchGoodsResponse();
        AbstractUkApiResponseHeaderDtoResponse header = new AbstractUkApiResponseHeaderDtoResponse();
        UkApiGetUkUniSearchGoodsResponseInfoDtoResponse responseInfo =
                        new UkApiGetUkUniSearchGoodsResponseInfoDtoResponse();

        // レスポンスヘッダー設定
        header.setStatus(ukApiResponseDto.getResponseHeader().getStatus());
        header.setQtime(ukApiResponseDto.getResponseHeader().getQTime());
        header.setReqID(ukApiResponseDto.getResponseHeader().getReqID());

        // フォールバック設定
        UkApiGetUkUniSearchResponseHeaderInfoFallBackDtoResponse fallbackDto =
                        new UkApiGetUkUniSearchResponseHeaderInfoFallBackDtoResponse();
        if (ukApiResponseDto.getResponseHeader().getFallback() != null) {
            fallbackDto.setType(ukApiResponseDto.getResponseHeader().getFallback().getType());
            fallbackDto.setKeyword(ukApiResponseDto.getResponseHeader().getFallback().getKeyword());
            header.setFallback(fallbackDto);
        }
        response.setResponseHeader(header);

        // 商品レスポンスあり
        if (ukApiResponseDto.getResponse() != null) {
            if (ukApiResponseDto.getResponse().getDocs() != null) {

                List<UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse> docsList = new ArrayList<>();
                UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse docs = null;

                for (UkApiUniSearchGoodsResponseDocsDto dto : ukApiResponseDto.getResponse().getDocs()) {
                    docs = new UkApiGetUkUniSearchGoodsResponseInfoDocsDtoResponse();
                    String groupId = dto.getGroupId();
                    docs.setGroupId(groupId);
                    docs.setNewFlg(dto.getNewFlg());
                    docs.setSaleFlg(dto.getSaleFlg());
                    docs.setReserveFlg(dto.getReserveFlg());
                    docs.setOutletFlg(dto.getOutletFlg());
                    docs.setItemName(dto.getItemName());
                    docs.setSaleComment(dto.getSaleComment());
                    docs.setMinPrice(dto.getMinPrice());
                    docs.setMaxPrice(dto.getMaxPrice());
                    docs.setSaleMinPrice(dto.getSaleMinPrice());
                    docs.setSaleMaxPrice(dto.getSaleMaxPrice());
                    docs.setCategoryIdList(dto.getCategoryIdList());
                    docs.setItemStatus(dto.getItemStatus());
                    docs.setDrugFlg(dto.getDrugFlg());
                    docs.setItemOverview(dto.getItemOverview());
                    docs.setNewDate(dto.getNewDate());
                    docs.setCatalogId(dto.getCatalogId());
                    docs.setGoodsImageName(groupId + "/" + groupId + "_01" + FILE_NAME_EXTENTION_JPG);
                    docsList.add(docs);
                }
                responseInfo.setDocs(docsList);

            }

            // レスポンス設定
            responseInfo.setNumFound(ukApiResponseDto.getResponse().getNumFound());
            // Pageがnullの場合はリクエストと同じ値を返却する
            if (ukApiResponseDto.getResponse().getPage() != null) {
                responseInfo.setPage(ukApiResponseDto.getResponse().getPage());
            } else {
                responseInfo.setPage(reqDto.getPage());
            }
            responseInfo.setCategory(reqDto.getCategory());
            // ページ情報レスポンスを設定
            PageInfoResponse pageInfoResponse = new PageInfoResponse();

            if (pageInfoRequest != null && pageInfoRequest.getPage() != null && pageInfoRequest.getLimit() != null) {
                try {
                    pageInfoHelper.setupResponsePager(pageInfo, pageInfoResponse, responseInfo.getNumFound());
                } catch (InvocationTargetException | IllegalAccessException e) {
                    LOGGER.info("ページ情報レスポンスの設定異常： " + e.getMessage());
                }
            }

            responseInfo.setPageInfo(pageInfoResponse);
            response.setResponse(responseInfo);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /ukapi/p1/vi/pdrcontents : UK-API連携 ユニサーチ（コンテンツ）
     * UK-API連携ユニサーチ（コンテンツ）
     *
     * @param ukApiGetUkUniSearchContentsRequest UK-API連携ユニサーチ（コンテンツ）リクエスト
     * @return UK-API連携ユニサーチ（コンテンツ）レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<UkApiGetUkUniSearchContentsResponse> getUkUniSearchContents(@Valid UkApiGetUkUniSearchContentsRequest ukApiGetUkUniSearchContentsRequest) {

        UkApiUniSearchContentsRequestDto reqDto =
                        ApplicationContextUtility.getBean(UkApiUniSearchContentsRequestDto.class);
        reqDto.setKw(ukApiGetUkUniSearchContentsRequest.getKw());
        reqDto.setPage(ukApiGetUkUniSearchContentsRequest.getPage());
        reqDto.setRows(ukApiGetUkUniSearchContentsRequest.getRows());
        reqDto.setSort(ukApiGetUkUniSearchContentsRequest.getSortType());
        reqDto.setUser(ukApiGetUkUniSearchContentsRequest.getUser());

        // ユニサーチと通信するLogic
        UkApiUniSearchContentsResponseDto ukApiResponseDto = ukApiUniSearchContentsLogic.execute(reqDto);

        UkApiGetUkUniSearchContentsResponse response = new UkApiGetUkUniSearchContentsResponse();
        AbstractUkApiResponseHeaderDtoResponse header = new AbstractUkApiResponseHeaderDtoResponse();
        UkApiGetUkUniSearchContentsResponseInfoDtoResponse responseInfo =
                        new UkApiGetUkUniSearchContentsResponseInfoDtoResponse();

        // レスポンスヘッダー設定
        header.setStatus(ukApiResponseDto.getResponseHeader().getStatus());
        header.setQtime(ukApiResponseDto.getResponseHeader().getQTime());
        header.setReqID(ukApiResponseDto.getResponseHeader().getReqID());

        // フォールバック設定
        UkApiGetUkUniSearchResponseHeaderInfoFallBackDtoResponse fallbackDto =
                        new UkApiGetUkUniSearchResponseHeaderInfoFallBackDtoResponse();
        if (ukApiResponseDto.getResponseHeader().getFallback() != null) {
            fallbackDto.setType(ukApiResponseDto.getResponseHeader().getFallback().getType());
            fallbackDto.setKeyword(ukApiResponseDto.getResponseHeader().getFallback().getKeyword());
            header.setFallback(fallbackDto);
        }
        response.setResponseHeader(header);

        // コンテンツレスポンスあり
        if (ukApiResponseDto.getResponse() != null) {
            if (ukApiResponseDto.getResponse().getDocs() != null) {

                List<UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse> docsList = new ArrayList<>();
                UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse docs = null;

                for (UkApiUniSearchContentsResponseDocsDto dto : ukApiResponseDto.getResponse().getDocs()) {
                    docs = new UkApiGetUkUniSearchContentsResponseInfoDocsDtoResponse();
                    docs.setContentId(dto.getContentId());
                    docs.setContentName(dto.getContentName());
                    docs.setTransitionUrl(dto.getTransitionUrl());
                    docs.setContentImageUrl(dto.getContentImageUrl());
                    docs.setSearchKeyword(dto.getSearchKeyword());

                    docsList.add(docs);
                }
                responseInfo.setDocs(docsList);

            }

            // レスポンス設定
            responseInfo.setNumFound(ukApiResponseDto.getResponse().getNumFound());
            // Pageがnullの場合はリクエストと同じ値を返却する
            if (ukApiResponseDto.getResponse().getPage() != null) {
                responseInfo.setPage(ukApiResponseDto.getResponse().getPage());
            } else {
                responseInfo.setPage(reqDto.getPage());
            }
            // ページ情報レスポンスを設定

            response.setResponse(responseInfo);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
