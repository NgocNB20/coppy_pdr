// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckDetailResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetStockResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetDiscountsResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsIndexModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.WebApiUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.thymeleaf.util.ListUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 入荷お知らせ一覧
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@RequestMapping("/member/restock")
@SessionAttributes(value = "memberRestockAnnounceModel")
@Controller
@RequiredArgsConstructor
public class MemberRestockAnnounceController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberRestockAnnounceController.class);

    /**
     * 入荷お知らせ一覧：デフォルトページ番号
     */
    public static final String DEFAULT_MYLIST_PNUM = "1";

    /**
     * 入荷お知らせ一覧：１ページ当たりのデフォルト最大表示件数
     */
    public static final String DEFAULT_MYLIST_LIMIT = "20";

    /**
     * 削除モードパラメータ
     */
    public static final String DELETE_MODE = "d";

    /**
     * 入荷お知らせ一覧ヘルパー
     */
    private final MemberRestockAnnounceHelper memberRestockAnnounceHelper;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * 商品Helper
     */
    private final GoodsHelper goodsHelper;

    /**
     * 商品系ヘルパー
     */
    private final GoodsUtility goodsUtility;

    /**
     * 共通情報ヘルパークラス
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * WebapiApi
     */
    private final WebapiApi webapiApi;

    /**
     * コンストラクタ
     */
    @Autowired
    public MemberRestockAnnounceController(MemberRestockAnnounceHelper memberRestockAnnounceHelper,
                                           GoodsHelper goodsHelper,
                                           ConversionUtility conversionUtility,
                                           GoodsUtility goodsUtility,
                                           CommonInfoUtility commonInfoUtility,
                                           MemberInfoApi memberInfoApi,
                                           WebapiApi webapiApi) {
        this.memberRestockAnnounceHelper = memberRestockAnnounceHelper;
        this.conversionUtility = conversionUtility;
        this.goodsHelper = goodsHelper;
        this.goodsUtility = goodsUtility;
        this.commonInfoUtility = commonInfoUtility;
        this.webapiApi = webapiApi;
        this.memberInfoApi = memberInfoApi;
    }

    /**
     * 入荷お知らせ一覧画面：初期処理
     *
     * @param memberRestockAnnounceModel 入荷お知らせ一覧Model
     * @param pnum                ページ番号(デフォルト1）
     * @param limit               最大表示件数（デフォルト10）
     * @param model               Model
     * @return お気に入り画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/member/restock/index.html")
    protected String doLoadIndex(MemberRestockAnnounceModel memberRestockAnnounceModel,
                                 @RequestParam(required = false, defaultValue = DEFAULT_MYLIST_PNUM) String pnum,
                                 @RequestParam(required = false, defaultValue = DEFAULT_MYLIST_LIMIT) int limit,
                                 Model model) {

        try {
            // 削除モードの場合
            if (DELETE_MODE.equals(memberRestockAnnounceModel.getMd()) && memberRestockAnnounceModel.getGcd() != null) {

                RestockAnnounceListDeleteRequest request = new RestockAnnounceListDeleteRequest();
                request.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
                request.setGcd(memberRestockAnnounceModel.getGcd());
                try {
                    memberInfoApi.deleteRestockAnnounceList(request);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }

                memberRestockAnnounceModel.setMd(null);
                memberRestockAnnounceModel.setGcd(null);

                return "redirect:/member/restock/index.html";
            }
        } finally {

            // 入荷一覧の検索
            searchArrivalList(memberRestockAnnounceModel, pnum, limit, model);

        }

        // ヘッダー通知設定
        try {
            // 通知フラグ=OFFまたは、セッションがすでに既読の場合はAPI呼出しやセッション設定処理をスキップする
            if (!(HTypeTopStockAnnounceFlg.OFF.equals(getCommonInfo().getCommonInfoBase().getTopStockAnnounceFlg())
                  || HTypeStockAnnounceWatchFlg.READ.equals(
                            getCommonInfo().getCommonInfoBase().getStockAnnounceWatchFlg()))) {
                // 更新失敗時は、ワーニングログを出力して、エラー画面に遷移しないようにする
                MemberInfoAnnounceUpdateRequest request =
                                memberRestockAnnounceHelper.createRequestUpdateAnnounce(Boolean.FALSE, Boolean.TRUE);
                memberInfoApi.updateAnnounce(request);
                memberRestockAnnounceHelper.updateCommonInfoAnnounceStatus(Boolean.FALSE, Boolean.TRUE);
            }
        } catch (Exception e) {
            LOGGER.warn("アナウンスデータの更新に失敗しました");
        }

        return "member/restock/index";
    }

    /**
     * 入荷お知らせ一覧の検索
     */
    protected void searchArrivalList(MemberRestockAnnounceModel memberRestockAnnounceModel,
                                     String pnum,
                                     int limit,
                                     Model model) {

        // リクエスト用のページャー項目
        PageInfoRequest pageInfoRequest = new PageInfoRequest();
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageRequest(pageInfoRequest,
                                        StringUtil.isNotEmpty(pnum) ? conversionUtility.toInteger(pnum) : null, limit,
                                        "updateTime", false
                                       );

        // 入荷お知らせ検索APIパラメータ
        // 検索条件Dto作成
        RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto =
                        memberRestockAnnounceHelper.toRestockAnnounceConditionDtoForSearchRestockAnnounceList(
                                        memberRestockAnnounceModel);
        restockAnnounceConditionDto.setRestockstatus(null);
        RestockAnnounceListGetRequest restockAnnounceListGetRequest =
                        memberRestockAnnounceHelper.toRestockAnnounceListGetRequest(restockAnnounceConditionDto);

        RestockAnnounceDtoListResponse restockAnnounceDtoListResponse = null;
        try {
            // 入荷お知らせ一覧検索
            restockAnnounceDtoListResponse =
                            memberInfoApi.getRestockAnnounceList(restockAnnounceListGetRequest, pageInfoRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        GoodsHelper goodsHelper = ApplicationContextUtility.getBean(GoodsHelper.class);

        PageInfoResponse pageInfoResponse =
                        restockAnnounceDtoListResponse != null ? restockAnnounceDtoListResponse.getPageInfo() : null;

        Integer pageInfoResponsePage = pageInfoResponse != null ? pageInfoResponse.getPage() : null;
        Integer pageInfoResponseLimit = pageInfoResponse != null ? pageInfoResponse.getLimit() : null;
        Integer pageInfoResponseNextPage = pageInfoResponse != null ? pageInfoResponse.getNextPage() : null;
        Integer pageInfoResponsePrevPage = pageInfoResponse != null ? pageInfoResponse.getPrevPage() : null;
        Integer pageInfoResponseTotal = pageInfoResponse != null ? pageInfoResponse.getTotal() : null;
        Integer pageInfoResponseTotalPages = pageInfoResponse != null ? pageInfoResponse.getTotalPages() : null;

        // APIレスポンスより画面Modelへページャーセットアップ
        pageInfoHelper.setupPageInfo(memberRestockAnnounceModel, pageInfoResponsePage, pageInfoResponseLimit,
                                     pageInfoResponseNextPage, pageInfoResponsePrevPage, pageInfoResponseTotal,
                                     pageInfoResponseTotalPages, null, null, null, false, null
                                    );

        List<RestockAnnounceDtoResponse> restockAnnounceDtoListResponses = null;
        if (restockAnnounceDtoListResponse != null
            && restockAnnounceDtoListResponse.getRestockAnnounceDtoListResponse() != null) {
            restockAnnounceDtoListResponses =
                            new ArrayList<>(restockAnnounceDtoListResponse.getRestockAnnounceDtoListResponse());
        }

        // 検索結果を画面に設定
        try {
            List<RestockAnnounceDto> stockRestockAnnounceDtoList = goodsHelper.toRestockAnnounceDtoList(
                            restockAnnounceDtoListResponses == null ? null : restockAnnounceDtoListResponses);
            setGoodsRestockAnnounceInfo(stockRestockAnnounceDtoList, memberRestockAnnounceModel);

            // 複数数量閾値フラグ設定
            goodsUtility.setMultiLevelFlag(memberRestockAnnounceModel.getRestockAnnounceItems());
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // ぺージャーセットアップ
        pageInfoHelper.setupViewPager(memberRestockAnnounceModel.getPageInfo(), model);
    }

    /**
     * 商品在庫情報の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param restockAnnounceDtos          入荷お知らグループDTO
     * @param memberRestockAnnounceModel   入荷お知ら詳細ページ
     */
    protected void setGoodsRestockAnnounceInfo(List<RestockAnnounceDto> restockAnnounceDtos,
                                               MemberRestockAnnounceModel memberRestockAnnounceModel) {
        // 在庫表示用リスト
        List<GoodsStockItem> goodsStockItems = new ArrayList<>();

        // WEB-API（数量割引情報取得）から数量割引情報を取得する
        WebApiGetQuantityDiscountResponseDto getQuantityDiscountDto =
                        executeWebApiGetQuantityDiscount(restockAnnounceDtos);

        // WEB-API（在庫情報取得）から在庫情報を取得する
        Map<String, WebApiGetStockResponseDetailDto> stockMap = executeWebApiGetStock(restockAnnounceDtos);

        WebApiGetStockResponseDetailDto webApiGetStockResponseDetailPdrDto;

        // WEB-API連携 割引適用結果取得実行
        Map<String, WebApiGetDiscountsResponseDetailDto> getDiscountsResponseDtoMap =
                        executeWebApiGetDiscountsResult(restockAnnounceDtos);

        // 扱いやすいように商品番号をキーにしたマップに詰め替え
        Map<String, RestockAnnounceDto> goodsDtoMap = new LinkedHashMap<>();
        for (RestockAnnounceDto goodsDto : restockAnnounceDtos) {
            goodsDtoMap.put(goodsDto.getGoodsDetailsDto().getGoodsCode(), goodsDto);
        }

        NumberFormat df = NumberFormat.getNumberInstance();
        int rowSpanCount = 0;
        for (WebApiGetQuantityDiscountResponseDetailDto responseInfo : getQuantityDiscountDto.getInfo()) {
            // １商品につき何行表示するのかを割り出す
            // 2023-renew No11 from here
            if (CollectionUtil.isNotEmpty(responseInfo.getSalePriceList()) || CollectionUtil.isNotEmpty(
                            responseInfo.getCustomerSalePriceList())) {
                // 2023-renew No11 to here
                // 数量割引情報が存在している場合、または顧客セール情報が存在している場合は、数量割引パターン分表示
                // 価格は何件あっても先頭行しか表示しない
                if (!CollectionUtil.isEmpty(responseInfo.getCustomerSalePriceList())) {
                    // 数量割引情報が存在していない場合
                    // 2023-renew No11 from here
                    if (responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList().size()) {
                        rowSpanCount = responseInfo.getCustomerSalePriceList().size();
                    } else {
                        rowSpanCount = responseInfo.getPriceList().size();
                    }
                    // 2023-renew No11 to here
                } else if (!CollectionUtil.isEmpty(responseInfo.getSalePriceList())) {
                    // 顧客セール情報が存在していない場合
                    // 2023-renew No11 from here
                    if (responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                        rowSpanCount = responseInfo.getSalePriceList().size();
                    } else {
                        rowSpanCount = responseInfo.getPriceList().size();
                    }
                    // 2023-renew No11 to here
                }
            } else {
                // 価格パターン分表示
                rowSpanCount = responseInfo.getPriceList().size();
            }

            for (int i = 0; i < rowSpanCount; i++) {
                GoodsStockItem restockAnnounceItem = ApplicationContextUtility.getBean(GoodsStockItem.class);

                // 結合数
                restockAnnounceItem.setGoodsCodeRowSpan(rowSpanCount);

                // 商品番号
                restockAnnounceItem.setGoodsCode(responseInfo.getGoodsCode());

                // 在庫
                if (stockMap != null) {
                    webApiGetStockResponseDetailPdrDto = stockMap.get(responseInfo.getGoodsCode());

                    // 在庫情報が取得できない場合はログを出力し、在庫なしとして処理を進める
                    if (webApiGetStockResponseDetailPdrDto == null) {
                        LOGGER.warn("商品番号：" + responseInfo.getGoodsCode() + "の在庫情報が取得できませんでした。在庫なしとして進めます。");
                        webApiGetStockResponseDetailPdrDto =
                                        ApplicationContextUtility.getBean(WebApiGetStockResponseDetailDto.class);
                        webApiGetStockResponseDetailPdrDto.setStockQuantity(BigDecimal.ZERO);
                    }
                } else {
                    LOGGER.warn("商品番号：" + responseInfo.getGoodsCode() + "の在庫情報が取得できませんでした。在庫なしとして進めます。");
                    webApiGetStockResponseDetailPdrDto =
                                    ApplicationContextUtility.getBean(WebApiGetStockResponseDetailDto.class);
                    webApiGetStockResponseDetailPdrDto.setStockQuantity(BigDecimal.ZERO);
                }

                // 問い合わせを行っていない商品番号が返却された場合はスキップする
                // 運用時はありえない前提
                if (goodsDtoMap.get(responseInfo.getGoodsCode()) == null) {
                    LOGGER.debug("数量割引情報取得で問合せを行っていない商品が返却されましたので、スキップします。商品番号：" + responseInfo.getGoodsCode());
                    continue;
                }

                // 2023-renew No11 from here
                // 商品の販売状態をチェック
                GoodsDetailsDto goodsDetailsDto = goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsDetailsDto();
                if (goodsUtility.isGoodsItemNoSales(goodsDetailsDto.getSaleStatusPC(),
                                                    goodsDetailsDto.getSaleStartTimePC(),
                                                    goodsDetailsDto.getSaleEndTimePC()
                                                   )) {
                    // 非販売の場合 在庫表示に[×」を設定
                    restockAnnounceItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                } else {
                    // 販売中の場合
                    HTypeStockManagementFlag stockManagementFlag = goodsDtoMap.get(responseInfo.getGoodsCode())
                                                                              .getGoodsDetailsDto()
                                                                              .getStockManagementFlag();
                    // 売切対象商品の場合
                    int stockQuantity = webApiGetStockResponseDetailPdrDto.getStockQuantity().intValue();
                    int remainStockQuantity = goodsDtoMap.get(responseInfo.getGoodsCode())
                                                         .getGoodsDetailsDto()
                                                         .getRemainderFewStock()
                                                         .intValue();
                    if (HTypeStockManagementFlag.ON.equals(stockManagementFlag)) {

                        if (stockQuantity > remainStockQuantity) {
                            restockAnnounceItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                        } else if (remainStockQuantity >= stockQuantity && stockQuantity > 0 || (stockQuantity > 0
                                                                                                 && "1".equals(
                                        webApiGetStockResponseDetailPdrDto.getDeliveryYesNo()))) {
                            restockAnnounceItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                        } else if (stockQuantity == 0 && "0".equals(
                                        webApiGetStockResponseDetailPdrDto.getDeliveryYesNo())) {
                            restockAnnounceItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                        }
                    } else {
                        // 売切対象外商品の場合
                        if (stockQuantity > 0) {
                            restockAnnounceItem.setStock(GoodsIndexModel.DISP_IN_STOCK);
                        } else if (stockQuantity == 0) {
                            restockAnnounceItem.setStock(GoodsIndexModel.DISP_DEPENDING_ON_RECEIPT_STOCK);
                        } else {
                            restockAnnounceItem.setStock(GoodsIndexModel.DISP_OUT_OF_STOCK);
                        }
                    }
                }
                // 2023-renew No11 to here

                // 数量割引が設定されている、または、顧客セールが設定されている場合
                // 2023-renew No11 from here
                if (CollectionUtil.isNotEmpty(responseInfo.getSalePriceList()) || CollectionUtil.isNotEmpty(
                                responseInfo.getCustomerSalePriceList())) {
                    // 価格
                    if (responseInfo.getPriceList() != null && responseInfo.getPriceList().get(0) != null) {

                        if (responseInfo.getMarketPriceFlag().equals("1")) {
                            restockAnnounceItem.setPrice("時価");
                        } else {
                            restockAnnounceItem.setPrice(
                                            df.format(responseInfo.getPriceList().get(i).getPrice()) + "円");
                            restockAnnounceItem.setLevel(responseInfo.getPriceList().get(i).getLevel());
                        }
                        // 2023-renew No11 to here

                    }
                    // 数量
                    // 数量割引が設定されていない場合
                    if (CollectionUtil.isEmpty(responseInfo.getSalePriceList())) {
                        // 顧客セール閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel() == null) {
                            restockAnnounceItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList()
                                                                                     .size()) {
                                restockAnnounceItem.setLevel(
                                                responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                        // 顧客セールが設定されていない場合
                    } else if (CollectionUtil.isEmpty(responseInfo.getCustomerSalePriceList())) {
                        // 数量割引閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getSalePriceList().get(i).getSaleLevel() == null) {
                            restockAnnounceItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                                restockAnnounceItem.setLevel(responseInfo.getSalePriceList().get(i).getSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                    } else {
                        // 顧客セール閾値が存在していない場合
                        if ((responseInfo.getPriceList() == null || responseInfo.getPriceList().size() == 1)
                            && responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel() == null) {
                            restockAnnounceItem.setLevel("1～");
                        } else {
                            // 2023-renew No11 from here
                            if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                                && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList()
                                                                                     .size()) {
                                restockAnnounceItem.setLevel(
                                                responseInfo.getCustomerSalePriceList().get(i).getCustomerSaleLevel());
                            }
                            // 2023-renew No11 to here
                        }
                    }

                    // セール価格
                    // 数量割引と顧客セールが設定されている場合
                    if (!CollectionUtil.isEmpty(responseInfo.getSalePriceList()) && !CollectionUtil.isEmpty(
                                    responseInfo.getCustomerSalePriceList())) {

                        // 顧客セール価格を設定
                        // 2023-renew No11 from here
                        if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                            && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList().size()) {
                            restockAnnounceItem.setCustomerSalePrice(df.format(responseInfo.getCustomerSalePriceList()
                                                                                           .get(i)
                                                                                           .getCustomerSalePrice())
                                                                     + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            restockAnnounceItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            restockAnnounceItem.setSalePrice(responseInfo.getSalePriceList()
                                                                         .stream()
                                                                         .filter(salePriceDto -> detailDto.getLevel()
                                                                                                          .equals(salePriceDto.getSaleLevel()))
                                                                         .findFirst()
                                                                         .map(salePriceDto -> df.format(
                                                                                         salePriceDto.getSalePrice())
                                                                                              + "円")
                                                                         .orElse(null));
                            restockAnnounceItem.setCustomerSalePrice(responseInfo.getCustomerSalePriceList()
                                                                                 .stream()
                                                                                 .filter(customerSalePriceDto -> detailDto.getLevel()
                                                                                                                          .equals(customerSalePriceDto.getCustomerSaleLevel()))
                                                                                 .findFirst()
                                                                                 .map(customerSalePriceDto ->
                                                                                                      df.format(customerSalePriceDto.getCustomerSalePrice())
                                                                                                      + "円")
                                                                                 .orElse(null));
                        }
                        // 2023-renew No11 to here

                        // 数量割引のみが設定されている場合
                    } else if (!CollectionUtil.isEmpty(responseInfo.getSalePriceList()) && CollectionUtil.isEmpty(
                                    responseInfo.getCustomerSalePriceList())) {

                        // 数量割引価格を設定
                        // 2023-renew No11 from here
                        if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                            && responseInfo.getPriceList().size() <= responseInfo.getSalePriceList().size()) {
                            restockAnnounceItem.setSalePrice(
                                            df.format(responseInfo.getSalePriceList().get(i).getSalePrice()) + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            restockAnnounceItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            restockAnnounceItem.setSalePrice(responseInfo.getSalePriceList()
                                                                         .stream()
                                                                         .filter(salePriceDto -> detailDto.getLevel()
                                                                                                          .equals(salePriceDto.getSaleLevel()))
                                                                         .findFirst()
                                                                         .map(salePriceDto -> df.format(
                                                                                         salePriceDto.getSalePrice())
                                                                                              + "円")
                                                                         .orElse(null));
                        }
                        // 2023-renew No11 to here
                        // 顧客セールのみが設定されている場合
                    } else if (CollectionUtil.isEmpty(responseInfo.getSalePriceList()) && !CollectionUtil.isEmpty(
                                    responseInfo.getCustomerSalePriceList())) {

                        // 顧客セール価格を設定
                        // 2023-renew No11 from here
                        if (CollectionUtil.isNotEmpty(responseInfo.getPriceList())
                            && responseInfo.getPriceList().size() <= responseInfo.getCustomerSalePriceList().size()) {
                            restockAnnounceItem.setCustomerSalePrice(df.format(responseInfo.getCustomerSalePriceList()
                                                                                           .get(i)
                                                                                           .getCustomerSalePrice())
                                                                     + "円");
                        } else {
                            WebApiGetQuantityDiscountResponsePriceDto detailDto = responseInfo.getPriceList().get(i);
                            restockAnnounceItem.setPrice(df.format(detailDto.getPrice()) + "円");
                            restockAnnounceItem.setCustomerSalePrice(responseInfo.getCustomerSalePriceList()
                                                                                 .stream()
                                                                                 .filter(customerSalePriceDto -> detailDto.getLevel()
                                                                                                                          .equals(customerSalePriceDto.getCustomerSaleLevel()))
                                                                                 .findFirst()
                                                                                 .map(customerSalePriceDto ->
                                                                                                      df.format(customerSalePriceDto.getCustomerSalePrice())
                                                                                                      + "円")
                                                                                 .orElse(null));
                        }
                        // 2023-renew No11 to here
                    }
                } else {
                    // 数量割引と顧客セールが設定されていない場合
                    // 価格
                    if (responseInfo.getMarketPriceFlag().equals("1")) {
                        restockAnnounceItem.setPrice("時価");
                    } else {
                        restockAnnounceItem.setPrice(df.format(responseInfo.getPriceList().get(i).getPrice()) + "円");
                    }

                    // 数量
                    if (responseInfo.getPriceList().get(i).getLevel() == null || responseInfo.getPriceList()
                                                                                             .get(i)
                                                                                             .getLevel()
                                                                                             .isEmpty()) {
                        restockAnnounceItem.setLevel("1～");
                    } else {
                        restockAnnounceItem.setLevel(responseInfo.getPriceList().get(i).getLevel());
                    }
                }

                if (StringUtils.isBlank(restockAnnounceItem.getGcnt())) {
                    restockAnnounceItem.setGcnt("1");
                }

                GoodsDetailsDto goodsEntity = goodsDtoMap.get(responseInfo.getGoodsCode()).getGoodsDetailsDto();
                restockAnnounceItem.setSaleStatus(goodsEntity.getSaleStatusPC());
                restockAnnounceItem.setSaleStartTime(goodsEntity.getSaleStartTimePC());
                restockAnnounceItem.setSaleEndTime(goodsEntity.getSaleEndTimePC());
                if (Objects.nonNull(stockMap) && Objects.nonNull(stockMap.get(responseInfo.getGoodsCode()))) {
                    restockAnnounceItem.setSaleControl(stockMap.get(responseInfo.getGoodsCode()).getSaleControl());
                    restockAnnounceItem.setStockQuantity(stockMap.get(responseInfo.getGoodsCode()).getStockQuantity());
                }

                if (Objects.nonNull(getDiscountsResponseDtoMap) && Objects.nonNull(
                                getDiscountsResponseDtoMap.get(responseInfo.getGoodsCode() + "kp"))) {
                    restockAnnounceItem.setSaleType(EnumTypeUtil.getEnumFromValue(HTypeDiscountsType.class,
                                                                                  getDiscountsResponseDtoMap.get(
                                                                                                  responseInfo.getGoodsCode()
                                                                                                  + "kp").getSaleType()
                                                                                 ));
                    restockAnnounceItem.setSaleEmotionPrice(
                                    getDiscountsResponseDtoMap.get(responseInfo.getGoodsCode() + "kp").getSalePrice()
                                    + "円");

                }

                // リスト追加
                memberRestockAnnounceHelper.toPageInfoResponse(restockAnnounceItem,
                                                               goodsDtoMap.get(responseInfo.getGoodsCode()),
                                                               memberRestockAnnounceModel, goodsUtility
                                                              );
                // 2023-renew No2 from here
                // WEB-API販売可否判定チェック
                executeWebApiGetSaleCheck(memberRestockAnnounceModel, restockAnnounceItem);
                // 2023-renew No2 to here
                goodsStockItems.add(restockAnnounceItem);
            }
        }

        // 商品コードリスト
        if (!ListUtils.isEmpty(goodsStockItems)) {
            // 販売可否判定API
            if (commonInfoUtility.isLogin(getCommonInfo())) {
                List<WebApiGetSaleCheckDetailResponse> webApiGetSaleCheckDetailResponses =
                                this.executeWebApiGetSaleCheck(goodsStockItems);
                if (CollectionUtil.isNotEmpty(webApiGetSaleCheckDetailResponses)) {
                    goodsStockItems.forEach(stockItem -> {
                        webApiGetSaleCheckDetailResponses.forEach(res -> {
                            if (stockItem.getGoodsCode().equals(res.getGoodsCode())) {
                                stockItem.setSaleCheck(res.getGoodsSaleYesNo());
                            }
                        });
                    });
                }
            }
        }

        // ぺージャーセットアップ
        // 価格情報をマージする
        memberRestockAnnounceModel.setRestockAnnounceItems(
                        goodsUtility.mergePriceInfo(goodsStockItems, goodsDtoMap.keySet(), LOGGER));
    }

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

        if (CollectionUtil.isNotEmpty(goodsCodeList)) {
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
                WebApiGetStockResponseDto stockDto = goodsHelper.toWebApiGetStockResponseDto(webApiGetStockResponse);
                if (ObjectUtils.isNotEmpty(stockDto)) {
                    return stockDto.getMap();
                }
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
            }
        }

        return null;
    }

    /**
     * WEB-API連携 数量割引情報取得を行います。
     *
     * @param restockAnnounceDtoList    商品DTOリスト
     * @return 商品コードをキーにした商品在庫数MAP
     */
    protected WebApiGetQuantityDiscountResponseDto executeWebApiGetQuantityDiscount(List<RestockAnnounceDto> restockAnnounceDtoList) {

        List<String> goodsCodeList = new ArrayList<>();
        for (RestockAnnounceDto restockAnnounceDto : restockAnnounceDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            restockAnnounceDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        WebApiGetQuantityDiscountRequest webApiGetQuantityDiscountRequest = new WebApiGetQuantityDiscountRequest();
        webApiGetQuantityDiscountRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
        webApiGetQuantityDiscountRequest.setCustomerNo(commonInfoUtility.isLogin(getCommonInfo()) ?
                                                                       commonInfoUtility.getCustomerNo(
                                                                                       getCommonInfo()) :
                                                                       null);
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
        if (quantityDiscountDto == null) {
            throwMessage("PDR-0015-001-A-");
        }

        // 詳細情報が返却されなかった場合
        if (CollectionUtil.isEmpty(quantityDiscountDto.getInfo())) {
            // ログを出力しエラーページへ遷移
            LOGGER.error("数量割引適用結果取得連携で詳細情報が取得できません。顧客番号：" + webApiGetQuantityDiscountRequest.getCustomerNo() + " 申込商品:"
                         + webApiGetQuantityDiscountRequest.getGoodsCode());
            throwMessage("PDR-0015-001-A-");
        }

        return quantityDiscountDto;
    }

    /**
     * WEB-API連携 割引適用結果取得
     *
     * @param restockAnnounceDtoList 商品DTOリスト
     * @return 割引適用結果取得MAP
     */
    public Map<String, WebApiGetDiscountsResponseDetailDto> executeWebApiGetDiscountsResult(List<RestockAnnounceDto> restockAnnounceDtoList) {
        List<String> goodsCodeList = new ArrayList<>();
        for (RestockAnnounceDto restockAnnounceDto : restockAnnounceDtoList) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            restockAnnounceDto.getGoodsDetailsDto().getGoodsCode());
            // 重複なければ商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        if (CollectionUtil.isNotEmpty(goodsCodeList)) {
            try {
                WebApiGetDiscountsResultRequest webApiGetDiscountsResultRequest = new WebApiGetDiscountsResultRequest();
                webApiGetDiscountsResultRequest.setGoodsCode(WebApiUtility.createStrPipeByList(goodsCodeList));
                webApiGetDiscountsResultRequest.setCustomerNo(commonInfoUtility.isLogin(getCommonInfo()) ?
                                                                              commonInfoUtility.getCustomerNo(
                                                                                              getCommonInfo()) :
                                                                              null);

                WebApiGetDiscountsResultResponse webApiGetDiscountsResultResponse = null;
                try {
                    webApiGetDiscountsResultResponse = webapiApi.getDiscountResult(webApiGetDiscountsResultRequest);

                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }
                WebApiGetDiscountsResponseDto discountsResponseDto =
                                goodsHelper.toWebApiGetDiscountsResponseDto(webApiGetDiscountsResultResponse);
                return discountsResponseDto.getMap();
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
            }
        }

        return null;
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

    /**
     * WEB-API販売可否判定チェック
     *
     * @param memberRestockAnnounceModel 商品詳細ページ
     * @param restockAnnounceItem 商品詳細Item
     *
     */
    public void executeWebApiGetSaleCheck(MemberRestockAnnounceModel memberRestockAnnounceModel,
                                          GoodsStockItem restockAnnounceItem) {
        Integer customerNo = commonInfoUtility.getCustomerNo(memberRestockAnnounceModel.getCommonInfo());
        WebApiGetSaleCheckRequest webApiGetSaleCheckRequest = new WebApiGetSaleCheckRequest();
        webApiGetSaleCheckRequest.setCustomerNo(customerNo);
        webApiGetSaleCheckRequest.setGoodsCode(restockAnnounceItem.getGoodsCode());
        WebApiGetSaleCheckResponse webApiGetSaleCheckResponse = webapiApi.getSaleCheck(webApiGetSaleCheckRequest);
        if (CollectionUtil.isNotEmpty(webApiGetSaleCheckResponse.getInfo())) {
            for (WebApiGetSaleCheckDetailResponse res : webApiGetSaleCheckResponse.getInfo())
                if (res.getGoodsCode().equals(restockAnnounceItem.getGoodsCode())) {
                    restockAnnounceItem.setSaleCheck(res.getGoodsSaleYesNo());
                }
        }
    }
}
// 2023-renew No65 to here
