/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.SaleGoodsListDetailGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.SaleGoodsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFavoriteSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeRestockStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleCheckType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.coupon.AbstractCouponController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 会員メニューController
 *
 * @author kaneda
 */
@SessionAttributes(value = "memberModel")
@RequestMapping("/member")
@Controller
public class MemberController extends AbstractCouponController {

    // 2023-renew No71 from here
    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    /**
     * メンバーコントローラーの共通ヘルパー
     */
    private final MemberHelper memberHelper;

    /**
     * 会員情報API
     * */
    private final MemberInfoApi memberInfoApi;
    // 2023-renew No71 to here

    /**
     * 商品詳細画面：入荷お知らせ商品デフォルト件数
     */
    private static final int DEFAULT_RESTOCK_ANNOUNCE_GOODS_LIMIT = 200;

    // 2023-renew No65 from here
    /**
     * WebapiApi
     */
    private final WebapiApi webapiApi;

    /**
     * 商品系ヘルパー
     */
    private final GoodsUtility goodsUtility;
    // 2023-renew No65 to here

    /**
     * 商品Api
     */
    private final GoodsApi goodsApi;

    /**
     * コンストラクタ
     *
     * @param webapiApi WEB-APIApi
     * @param couponHelper クーポンHelper
     * @param commonInfoUtility 共通情報ヘルパークラス
     * @param memberHelper メンバーコントローラーの共通ヘルパー
     * @param memberInfoApi 会員情報API
     * @param goodsUtility  商品系ヘルパー
     */
    @Autowired
    public MemberController(WebapiApi webapiApi,
                            CouponHelper couponHelper,
                            CommonInfoUtility commonInfoUtility,
                            MemberHelper memberHelper,
                            MemberInfoApi memberInfoApi,
                            GoodsUtility goodsUtility,
                            GoodsApi goodsApi) {
        // 2023-renew No24 from here
        super(webapiApi, couponHelper, commonInfoUtility);
        // 2023-renew No24 to here
        // 2023-renew No71 from here
        this.memberHelper = memberHelper;
        this.memberInfoApi = memberInfoApi;
        // 2023-renew No71 to here
        // 2023-renew No65 from here
        this.webapiApi = webapiApi;
        this.goodsUtility = goodsUtility;
        // 2023-renew No65 to here
        this.goodsApi = goodsApi;
    }

    /**
     * メニュー画面：初期処理
     *
     * @param memberModel        会員メニューModel
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return メニュー画面
     */
    @GetMapping(value = {"/", "/index.html"})
    protected String doLoadIndex(MemberModel memberModel, Model model, RedirectAttributes redirectAttributes) {

        // 初期化処理
        clearModel(MemberModel.class, memberModel, model);

        memberModel.setLastName(getCommonInfo().getCommonInfoUser().getMemberInfoLastName());
        memberModel.setFirstName(getCommonInfo().getCommonInfoUser().getMemberInfoFirstName());

        // 2023-renew No24 from here
        // 利用可能クーポン一覧取得APIの呼び出しを行い、セッション情報の利用可能クーポン一覧数を更新
        executeWebApiGetCouponListAndSetCouponCount(model, redirectAttributes);
        // 2023-renew No24 to here

        // 2023-renew No65 from here
        // お気に入りセールお知らせ
        setUpFavoriteAnnounceGoodsInfo(memberModel);
        // 入荷お知らせ
        setUpRestockAnnounceGoodsInfo(memberModel);
        // 2023-renew No65 to here

        // 2023-renew No71 from here
        try {
            CommonInfoBase infoBase = getCommonInfo().getCommonInfoBase();
            // 通知フラグ=OFFまたは、セッションがすでに既読の場合はAPI呼出しやセッション設定処理をスキップする
            if (!((HTypeTopSaleAnnounceFlg.OFF.equals(infoBase.getTopSaleAnnounceFlg())
                   && HTypeTopStockAnnounceFlg.OFF.equals(infoBase.getTopStockAnnounceFlg())) || (
                                  HTypeSaleAnnounceWatchFlg.READ.equals(infoBase.getSaleAnnounceWatchFlg())
                                  && HTypeStockAnnounceWatchFlg.READ.equals(infoBase.getStockAnnounceWatchFlg())))) {
                // 更新失敗時は、ワーニングログを出力して、エラー画面に遷移しないようにする
                MemberInfoAnnounceUpdateRequest request =
                                memberHelper.createRequestUpdateAnnounce(Boolean.TRUE, Boolean.TRUE);
                memberInfoApi.updateAnnounce(request);
                memberHelper.updateCommonInfoAnnounceStatus(Boolean.TRUE, Boolean.TRUE);
            }
        } catch (Exception e) {
            LOGGER.warn("アナウンスデータの更新に失敗しました");
        }
        // 2023-renew No71 to here

        return "member/index";
    }

    // 2023-renew No65 from here

    /**
     * 商品情報の入荷お知らせ 取得と設定
     *
     * @param memberModel 会員メニューModel
     */
    protected void setUpRestockAnnounceGoodsInfo(MemberModel memberModel) {

        List<RestockAnnounceDto> restockAnnounceDtoList = null;

        // 入荷お知らせリストリクエストパラメータ生成
        RestockAnnounceSearchForDaoConditionDto conditionDto = new RestockAnnounceSearchForDaoConditionDto();
        conditionDto.setMemberInfoSeq(memberModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        conditionDto.setRestockstatus(HTypeRestockStatus.RESTOCK.getValue());
        RestockAnnounceListGetRequest restockAnnounceListGetRequest =
                        memberHelper.toRestockAnnounceListGetRequest(conditionDto);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        pageInfoHelper.setupPageRequest(
                        pageInfoRequest, null, DEFAULT_RESTOCK_ANNOUNCE_GOODS_LIMIT, "updateTime", false);

        // 入荷お知らせリスト検索実行
        RestockAnnounceDtoListResponse restockAnnounceDtoListResponse = null;
        try {
            restockAnnounceDtoListResponse =
                            memberInfoApi.getRestockAnnounceList(restockAnnounceListGetRequest, pageInfoRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        if (CollectionUtil.isEmpty(restockAnnounceDtoListResponse.getRestockAnnounceDtoListResponse())) {
            return;
        }

        restockAnnounceDtoList = memberHelper.toRestockAnnounceDtoList(
                        restockAnnounceDtoListResponse.getRestockAnnounceDtoListResponse());

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockResMap = executeWebApiGetStock(restockAnnounceDtoList);
        if (stockResMap == null) {
            return;
        }

        // 在庫数 ＞ 0 のデータをフィルター
        List<RestockAnnounceDtoResponse> announceDtoResponseTargetList = new ArrayList<>();
        List<WebApiGetStockResponseDetailDto> webApiGetStockResponseDetailDtoTargetList = new ArrayList<>();
        for (RestockAnnounceDtoResponse announceDtoResponse : restockAnnounceDtoListResponse.getRestockAnnounceDtoListResponse()) {

            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            announceDtoResponse.getGoodsDetailsDtoResponse().getGoodsCode());

            WebApiGetStockResponseDetailDto stockResDto = stockResMap.get(goodsCode);
            if (stockResDto != null && stockResDto.getStockQuantity() != null) {
                int stockQuantity = stockResDto.getStockQuantity().intValue();
                if (stockQuantity > 0) {
                    announceDtoResponseTargetList.add(announceDtoResponse);
                    webApiGetStockResponseDetailDtoTargetList.add(stockResDto);
                }
            }
        }

        RestockAnnounceDtoListResponse stockRestockAnnounceDtoListResponse = new RestockAnnounceDtoListResponse();
        stockRestockAnnounceDtoListResponse.setRestockAnnounceDtoListResponse(announceDtoResponseTargetList);
        List<RestockAnnounceDto> stockRestockAnnounceDtoList = memberHelper.toRestockAnnounceDtoList(
                        stockRestockAnnounceDtoListResponse == null ?
                                        null :
                                        stockRestockAnnounceDtoListResponse.getRestockAnnounceDtoListResponse());

        // 画面Model用パラメータに変換
        List<GoodsStockItem> goodsStockItems =
                        memberHelper.toPageForLoadRestockAnnounce(stockRestockAnnounceDtoList, memberModel,
                                                                  webApiGetStockResponseDetailDtoTargetList
                                                                 );
        if (CollectionUtil.isEmpty(goodsStockItems)) {
            return;
        }

        /// カートイン可能商品へ絞り込み
        // 1段階目
        List<GoodsStockItem> goodsStockTarget1Items = new ArrayList<>();
        goodsStockItems.forEach(item -> {
            if (!Objects.isNull(item.getSaleControl())) {
                GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
                String goodsIconDisplay = goodsUtility.getGoodsIconDisplay(item);
                // 商品TBLデータ販売中 & 販売制御状態≠「在庫切れ」「受付停止中」「取扱中止」「販売終了しました」
                if (item.isGoodsSales() && StringUtils.isBlank(goodsIconDisplay)) {
                    goodsStockTarget1Items.add(item);
                }
            }
        });
        if (CollectionUtil.isEmpty(goodsStockTarget1Items)) {
            return;
        }

        // ２段階目
        List<GoodsStockItem> goodsStockTarget2Items = new ArrayList<>();
        // 販売可否API実行
        List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                        executeWebApiGetSaleCheck(goodsStockTarget1Items);
        if (CollectionUtil.isEmpty(webApiGetSaleCheckDetailResponses)) {
            return;
        }
        // 販売可否判定
        TARGET1:
        for (GoodsStockItem target1Item : goodsStockTarget1Items) {
            for (WebApiGetSaleCheckDetailResponse saleCheckRes : webApiGetSaleCheckDetailResponses) {

                if (Objects.equals(saleCheckRes.getGoodsCode(), target1Item.getGoodsCode())) {
                    // 販売可否判定API.販売可否判定結果 = 「1：販売可」
                    if (Objects.nonNull(saleCheckRes.getGoodsSaleYesNo()) && HTypeSaleCheckType.YES.getValue()
                                                                                                   .equals(String.valueOf(
                                                                                                                   saleCheckRes.getGoodsSaleYesNo()))) {
                        target1Item.setSaleCheck(saleCheckRes.getGoodsSaleYesNo());
                        goodsStockTarget2Items.add(target1Item);
                    }
                    continue TARGET1;
                }
            }
        }

        memberModel.setRestockAnnounceGoodsItems(goodsStockTarget2Items);
    }
    // 2023-renew No65 to here

    /**
     * 商品情報のお気に入りセール 取得と設定
     *
     * @param memberModel
     */
    protected void setUpFavoriteAnnounceGoodsInfo(MemberModel memberModel) {

        List<FavoriteDto> favoriteDtoList = null;
        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        memberHelper.toFavoriteConditionDtoForSearchFavoriteList(
                                        memberModel.getFavoriteAnnounceGoodsLimit(), memberModel);

        FavoriteListGetRequest favoriteListGetRequest = memberHelper.toFavoriteListGetRequest(favoriteConditionDto);
        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        pageInfoHelper.setupPageRequest(
                        pageInfoRequest, null, DEFAULT_RESTOCK_ANNOUNCE_GOODS_LIMIT, "updateTime", false);

        FavoriteDtoListResponse favoriteDtoListResponse = null;
        try {
            // お気に入り情報リスト取得
            favoriteDtoListResponse = memberInfoApi.getFavoriteList(favoriteListGetRequest, pageInfoRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        favoriteDtoList = memberHelper.toFavoriteDtoList(favoriteDtoListResponse.getFavoriteDtoListResponse());
        if (CollectionUtil.isEmpty(favoriteDtoList)) {
            return;
        }

        // お気に入り在庫状況取得リクエストパラメータ設定
        FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest =
                        new FavoriteGoodsStockStatusGetRequest();
        List<FavoriteDtoRequest> favoriteDtoListRequest = memberHelper.toFavoriteDtoListRequest(favoriteDtoList);
        favoriteGoodsStockStatusGetRequest.setFavoriteDtoListRequest(favoriteDtoListRequest);

        FavoriteDtoListResponse stockFavoriteDtoListResponse = null;
        try {
            // お気に入り在庫状況取得
            stockFavoriteDtoListResponse =
                            memberInfoApi.getFavoriteByFavoriteGoodsStockStatus(favoriteGoodsStockStatusGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // セール商品取得リクエストパラメータ
        SaleGoodsListGetRequest saleGoodsListGetRequest = new SaleGoodsListGetRequest();
        saleGoodsListGetRequest.setSaleGoodsSeqList(favoriteDtoList.stream()
                                                                   .map(favoriteDto -> favoriteDto.getFavoriteEntity()
                                                                                                  .getGoodsSeq())
                                                                   .collect(Collectors.toList()));

        List<SaleGoodsListDetailGetResponse> saleGoodsListDetailGetResponses = null;
        try {
            // セール商品取得
            saleGoodsListDetailGetResponses = goodsApi.getSaleGoodsList(saleGoodsListGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        if (CollectionUtil.isEmpty(saleGoodsListDetailGetResponses)) {
            return;
        }

        // お気に入りDtoリストに変換
        List<FavoriteDto> stockFavoriteDtoList =
                        memberHelper.toFavoriteDtoList(stockFavoriteDtoListResponse.getFavoriteDtoListResponse());
        if (CollectionUtil.isNotEmpty(stockFavoriteDtoList)) {
            try {
                // 2023-renew No11 from here
                // お気に入り商品
                setGoodsFavoriteInfo(stockFavoriteDtoList, memberModel);
                // 2023-renew No11 to here
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        }
        if (CollectionUtils.isEmpty(memberModel.getFavoriteAnnounceGoodsItems())) {
            return;
        }

        // セール商品マップに変換
        Map<Integer, SaleGoodsListDetailGetResponse> saleGoodsMapDto = saleGoodsListDetailGetResponses.stream()
                                                                                                      .collect(Collectors.toMap(
                                                                                                                      SaleGoodsListDetailGetResponse::getGoodsSeq,
                                                                                                                      Function.identity()
                                                                                                                               ));
        // セール対象商品に絞り込み
        List<GoodsStockItem> saleFavoriteItems = new ArrayList<>();
        memberModel.getFavoriteAnnounceGoodsItems().forEach(favoriteItemModel -> {

            // セール商品に含まれるかチェック
            SaleGoodsListDetailGetResponse saleGoodsDetail = saleGoodsMapDto.get(favoriteItemModel.getGoodsSeq());
            if (Objects.isNull(saleGoodsDetail)) {
                return;
            }

            HTypeFavoriteSaleStatus favoriteSaleStatus = EnumTypeUtil.getEnumFromValue(HTypeFavoriteSaleStatus.class,
                                                                                       saleGoodsDetail.getSaleStatus()
                                                                                      );
            Timestamp saleStartTime = Objects.nonNull(saleGoodsDetail.getSaleFrom()) ?
                            new Timestamp(saleGoodsDetail.getSaleFrom().getTime()) :
                            null;
            Timestamp saleEndTime = Objects.nonNull(saleGoodsDetail.getSaleTo()) ?
                            new Timestamp(saleGoodsDetail.getSaleTo().getTime()) :
                            null;
            // セール期間かチェック
            if (!goodsUtility.isFavoriteGoodsSales(favoriteSaleStatus, saleStartTime, saleEndTime)) {
                return;
            }

            // 在庫数チェック
            if (BigDecimal.ZERO.compareTo(favoriteItemModel.getStockQuantity()) >= 0) {
                return;
            }

            /// カートイン可能かチェック
            if (Objects.nonNull(favoriteItemModel.getSaleControl()) && Objects.nonNull(
                            favoriteItemModel.getSaleCheck())) {

                String goodsIconDisplay = goodsUtility.getGoodsIconDisplay(favoriteItemModel);
                // 商品TBLデータ販売中 & 販売制御状態≠「在庫切れ」「受付停止中」「取扱中止」「販売終了しました」 && 販売可否判定API.販売可否判定結果 = 「1：販売可」
                if (favoriteItemModel.isGoodsSales() && StringUtils.isBlank(goodsIconDisplay)
                    && favoriteItemModel.getSaleCheck() == 1) {
                    saleFavoriteItems.add(favoriteItemModel);
                }
            }
        });

        memberModel.setFavoriteAnnounceGoodsItems(saleFavoriteItems);
    }

    /**
     * お気に入りの商品情報の設定
     *
     * @param favoriteDtoList   お気に入りDTOリスト
     * @param memberModel   Model
     */
    protected void setGoodsFavoriteInfo(List<FavoriteDto> favoriteDtoList, MemberModel memberModel) {
        memberHelper.toPageForLoadFavorite(favoriteDtoList, memberModel);

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockMap = executeWebApiGetStockByFavoriteList(favoriteDtoList);

        memberModel.getFavoriteAnnounceGoodsItems().forEach(favoriteGoodsItem -> {
            if (favoriteGoodsItem.getStockQuantity() == null) {
                favoriteGoodsItem.setStockQuantity(BigDecimal.ZERO);
            }

            if (Objects.nonNull(stockMap) && Objects.nonNull(stockMap.get(favoriteGoodsItem.getGoodsCode()))) {
                favoriteGoodsItem.setSaleControl(stockMap.get(favoriteGoodsItem.getGoodsCode()).getSaleControl());
                favoriteGoodsItem.setStockQuantity(stockMap.get(favoriteGoodsItem.getGoodsCode()).getStockQuantity());
            }
        });

        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        if (commonInfoUtility.isLogin(getCommonInfo())) {
            // 販売可否判定API
            List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                            executeWebApiGetSaleCheck(memberModel.getFavoriteAnnounceGoodsItems());
            if (CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses)) {
                memberModel.getFavoriteAnnounceGoodsItems().forEach(stockItem -> {
                    webApiGetSaleCheckDetailResponses.forEach(res -> {
                        if (Objects.equals(res.getGoodsCode(), stockItem.getGoodsCode())) {
                            stockItem.setSaleCheck(res.getGoodsSaleYesNo());
                        }
                    });
                });
            }
        }
    }

    /**
     * WEB-API連携 販売可否判定
     *
     * @param goodsStockItems 在庫商品リスト
     * @return 販売可否判定 詳細情報レスポンスリスト
     */
    protected List<WebApiGetSaleCheckDetailResponse> executeWebApiGetSaleCheck(List<GoodsStockItem> goodsStockItems) {

        List<String> goodsCodeList = new ArrayList<>();
        goodsStockItems.forEach(stockItem -> {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(stockItem.getGoodsCode());

            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        });

        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        WebApiGetSaleCheckRequest webApiGetSaleCheckRequest = new WebApiGetSaleCheckRequest();
        webApiGetSaleCheckRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        webApiGetSaleCheckRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        // 販売可否判定API
        WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = null;
        try {
            webApiGetSaleCheckResponse = webapiApi.getSaleCheck(webApiGetSaleCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        if (Objects.isNull(webApiGetSaleCheckResponse)) {
            throwMessage("PDR-0015-001-A-");
        }
        return webApiGetSaleCheckResponse.getInfo();
    }
    // 2023-renew No65 from here

    /**
     * WEB-API連携 商品在庫数取得を行います。
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> executeWebApiGetStock(List<RestockAnnounceDto> goodsDtoList) {

        List<String> goodsCodeList = new ArrayList<String>();
        for (RestockAnnounceDto goodsDto : goodsDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            goodsDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        WebApiGetStockRequest webApiGetStockRequest = new WebApiGetStockRequest();
        webApiGetStockRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        WebApiGetStockResponse webApiGetStockResponse = null;
        try {
            try {
                webApiGetStockResponse = webapiApi.getStock(webApiGetStockRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            WebApiGetStockResponseDto stockResDto = memberHelper.toWebApiGetStockResponseDto(webApiGetStockResponse);
            if (ObjectUtils.isNotEmpty(stockResDto)) {
                return stockResDto.getMap();
            }
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;
    }

    /**
     * WEB-API連携 商品在庫数取得を行います。
     *
     * @param favoriteDtoList 商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> executeWebApiGetStockByFavoriteList(List<FavoriteDto> favoriteDtoList) {
        WebApiGetStockRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetStockRequestDto.class);

        List<String> goodsCodeList = new ArrayList<String>();
        for (FavoriteDto favoriteDto : favoriteDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            favoriteDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        reqDto.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));

        try {
            WebApiGetStockRequest webApiGetStockRequest = new WebApiGetStockRequest();
            webApiGetStockRequest.setGoodsCode(reqDto.getGoodsCode());

            WebApiGetStockResponse webApiGetStockResponse = null;
            try {
                webApiGetStockResponse = webapiApi.getStock(webApiGetStockRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            WebApiGetStockResponseDto stockDto = memberHelper.toWebApiGetStockResponseDto(webApiGetStockResponse);

            return stockDto.getMap();
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;
    }
    // 2023-renew No65 to here
}
