/*
] * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsDetailsMapGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.OpenNewsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetPurchasedCommodityInformationResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetPurchasedCommodityInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * トップ画面 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/")
@Controller
public class IndexController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    /**
     * １ページ当たりのニュースデフォルト最大表示件数
     */
    private static final String NEWS_DEFAULT_LIMIT = "10";

    /**
     * トップ画面のHelper
     */
    private final IndexHelper indexHelper;

    /**
     * ショップAPI
     */
    private final ShopApi shopApi;

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * WEB-API
     */
    private final WebapiApi webApi;

    /**
     * コンストラクタ
     *
     * @param indexHelper トップ画面 Helper
     * @param shopApi     ショップAPI
     * @param goodsApi    商品API
     * @param webApi      WEB-API
     */
    @Autowired
    public IndexController(IndexHelper indexHelper, ShopApi shopApi, GoodsApi goodsApi, WebapiApi webApi) {
        this.indexHelper = indexHelper;
        this.shopApi = shopApi;
        this.goodsApi = goodsApi;
        this.webApi = webApi;
    }

    /**
     * トップ画面：初期処理
     *
     * @param indexModel トップ画面 Model
     * @param limit      １ページ当たりのニュースデフォルト最大表示件数
     * @param model      model
     * @return トップ画面 string
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/", "/index.html"})
    // PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "index")
    protected String doLoadIndex(IndexModel indexModel,
                                 @RequestParam(required = false, defaultValue = NEWS_DEFAULT_LIMIT) int limit,
                                 Model model) {

        // ニュースを取得
        indexHelper.toPageForLoadNews(getNewsList(limit), indexModel);
        // PDR Migrate Customization from here

        // ログイン済の会員の場合、購入済みリストの取得
        // 共通情報Helper取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        if (commonInfoUtility.isLogin(getCommonInfo())) {
            WebApiGetPurchasedCommodityInformationResponseDto purchasedResponse =
                            executeWebApiGetQuantityDiscount(commonInfoUtility);

            if (purchasedResponse != null && purchasedResponse.getInfo() != null
                && purchasedResponse.getInfo().size() > 0) {
                List<String> goodsList = new ArrayList<>();
                for (WebApiGetPurchasedCommodityInformationResponseDetailDto dto : purchasedResponse.getInfo()) {
                    goodsList.add(dto.getGoodsCode());
                }

                GoodsDetailsMapGetRequest goodsDetailsMapGetRequest = new GoodsDetailsMapGetRequest();
                goodsDetailsMapGetRequest.setGoodsCodeList(goodsList);
                // 商品詳細情報MAP取得
                Map<String, GoodsDetailsDtoResponse> goodsDetailsDtoResponseMap = null;
                try {
                    goodsDetailsDtoResponseMap = goodsApi.getDetailsMap(goodsDetailsMapGetRequest);

                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }

                // 商品詳細マップ取得
                Map<String, GoodsDetailsDto> puchasedInfoMap =
                                indexHelper.toGoodsDetailsDtoMap(goodsDetailsDtoResponseMap);

                List<GoodsDetailsDto> goodsDetailDtoList = new ArrayList<>();

                for (GoodsDetailsDto dto : puchasedInfoMap.values()) {

                    goodsDetailDtoList.add(dto);
                }

                try {
                    indexHelper.toPageForLoadPuchased(goodsDetailDtoList, indexModel);

                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }

            }
        }
        // PDR Migrate Customization to here
        return "index";

    }

    /**
     * ニュース一覧取得
     *
     * @return ニュースエンティティ
     */
    protected List<NewsEntity> getNewsList(int limit) {
        OpenNewsListGetRequest openNewsListGetRequest = new OpenNewsListGetRequest();
        openNewsListGetRequest.setSiteType(HTypeSiteType.FRONT_PC.getValue());
        openNewsListGetRequest.setOpenStatus(HTypeOpenStatus.OPEN.getValue());

        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        pageInfoRequest.setLimit(limit);
        pageInfoRequest.setOrderBy("newstime");
        pageInfoRequest.setSort(true);
        // 公開ニュースリスト情報取得
        NewsEntityListResponse response = null;
        try {
            response = shopApi.getOpenNews(openNewsListGetRequest, pageInfoRequest);

        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return indexHelper.toNewsEntityList(response);
    }

    // PDR Migrate Customization from here

    /**
     * WEB-API連携 購入済み商品情報取得を行います。<br/>
     *
     * @param commonInfoUtility 共通情報Helper取得
     * @return 商品コードをキーにした商品在庫数MAP
     */
    protected WebApiGetPurchasedCommodityInformationResponseDto executeWebApiGetQuantityDiscount(CommonInfoUtility commonInfoUtility) {

        WebApiGetPurchasedCommodityInformationRequest getPurchasedCommodityInformationRequest =
                        new WebApiGetPurchasedCommodityInformationRequest();

        // 顧客番号
        getPurchasedCommodityInformationRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        WebApiGetPurchasedCommodityInformationResponse getPurchasedCommodityInformationResponse =
                        webApi.getPurchasedCommodityInformation(getPurchasedCommodityInformationRequest);

        return indexHelper.toPurchasedCommodityInformationDto(getPurchasedCommodityInformationResponse);
    }

    // PDR Migrate Customization to here
}
