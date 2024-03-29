/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.favorite;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetReserveResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsIconItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 会員お気に入り Helperクラス<br/>
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class MemberFavoriteHelper {

    /**
     * 税込価格算出Utility
     */
    private CalculatePriceUtility calculatePriceUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param calculatePriceUtility
     * @param conversionUtility
     */
    @Autowired
    public MemberFavoriteHelper(CalculatePriceUtility calculatePriceUtility, ConversionUtility conversionUtility) {
        this.calculatePriceUtility = calculatePriceUtility;
        this.conversionUtility = conversionUtility;
    }
    // 2023-renew No11 from here

    /**
     * ページ情報レスポンスに変換
     *
     * @param favoriteItem
     * @param favoriteDto           お気に入りDTO
     * @param memberFavoriteModel   ページ
     * @Param goodsUtility          商品系Helper取得
     * @return ページ情報レスポンス
     */
    public void toPageInfoResponse(GoodsStockItem favoriteItem,
                                   FavoriteDto favoriteDto,
                                   MemberFavoriteModel memberFavoriteModel,
                                   GoodsUtility goodsUtility) {
        // 各商品情報
        GoodsDetailsDto goodsDetailsDto = favoriteDto.getGoodsDetailsDto();
        if (Objects.nonNull(goodsDetailsDto)) {
            if (goodsDetailsDto != null) {

                favoriteItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                favoriteItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
                favoriteItem.setGcd(goodsDetailsDto.getGoodsCode());
                favoriteItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                favoriteItem.setGoodsDetailsNote(goodsDetailsDto.getGoodsNote1());
                favoriteItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                favoriteItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
                favoriteItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                favoriteItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                favoriteItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(goodsDetailsDto.getPreDiscountPrice(),
                                                                          goodsDetailsDto.getTaxRate()
                                                                         ));
                favoriteItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                favoriteItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
                favoriteItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
                favoriteItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                favoriteItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
                favoriteItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
                favoriteItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                favoriteItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
                favoriteItem.setDeliveryType(goodsDetailsDto.getDeliveryType());
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
                favoriteItem.setWhatsnewDate(whatsnewDate);
                favoriteItem.setStockStatusPc(favoriteDto.getStockStatus());
                favoriteItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());

                // 税率、税種別の変換
                favoriteItem.setTaxRate(goodsDetailsDto.getTaxRate());
                favoriteItem.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());

                // 税込価格の計算
                favoriteItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(favoriteItem.getPreDiscountPrice(),
                                                                          favoriteItem.getTaxRate()
                                                                         ));

                // 商品画像リストを取り出す。
                // 商品画像リストを取り出す。
                List<String> goodsImageList = new ArrayList<>();
                String favoriteItemImageFileName = null;
                if (goodsDetailsDto.getUnitImage() != null) {
                    favoriteItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                    goodsImageList.add(goodsUtility.getGoodsImagePath(favoriteItemImageFileName));
                } else {
                    if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                            goodsImageList.add(
                                            goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                        }
                    }
                }

                favoriteItem.setGoodsImageItems(goodsImageList);

                // PDR Migrate Customization from here
                // 販売可能商品区分判定
                // 2023-renew No2 from here

                // 2023-renew No2 to here
                // SALEアイコン表示判定
                favoriteItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()));
                // お取りおきアイコン表示判定
                favoriteItem.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()));
                // NEWアイコン表示判定
                favoriteItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()));
                // 2023-renew No92 from here
                // アウトレットアイコンフラグ
                favoriteItem.setOutletIconFlag(HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()));
                // 2023-renew No92 to here
                // 画面表示用価格
                NumberFormat formatter = NumberFormat.getNumberInstance();
                favoriteItem.setDispPrice(goodsDetailsDto.getGoodsPrice() == null ?
                                                          null :
                                                          formatter.format(goodsDetailsDto.getGoodsPrice()) + "円");
                if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag())) {
                    favoriteItem.setDispPrice(favoriteItem.getDispPrice() + "～");
                }

                // 画面表示用セール価格
                favoriteItem.setDispSalePrice(goodsDetailsDto.getPreDiscountPrice() == null ?
                                                              null :
                                                              formatter.format(goodsDetailsDto.getPreDiscountPrice())
                                                              + "円");
                if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag())) {
                    favoriteItem.setDispSalePrice(favoriteItem.getDispSalePrice() + "～");
                }

                // セール価格整合性フラグ
                favoriteItem.setSalePriceIntegrityFlag(
                                HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()));

                // 2023-renew No11 from here
                if (StringUtils.isBlank(favoriteItem.getGcnt())) {
                    favoriteItem.setGcnt("1");
                }
                // 2023-renew No11 to here

                // PDR Migrate Customization to here

            }

            // アイコン情報の取得
            List<GoodsIconItem> goodsIconList = new ArrayList<>();
            if (favoriteDto.getGoodsInformationIconDetailsDtoList() != null) {
                for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : favoriteDto.getGoodsInformationIconDetailsDtoList()) {

                    GoodsIconItem goodsIconItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                    goodsIconItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                    goodsIconItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                    goodsIconList.add(goodsIconItem);
                }
            }
            favoriteItem.setGoodsIconItems(goodsIconList);
        }
    }
    // 2023-renew No11 to here

    /**
     * ページ変換、初期表示<br/>
     *
     * @param favoriteDtoList     お気に入りDTOリスト
     * @param memberFavoriteModel ページ
     */
    public void toPageForLoad(List<FavoriteDto> favoriteDtoList, MemberFavoriteModel memberFavoriteModel) {

        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

        List<GoodsStockItem> favoriteItems = new ArrayList<>();
        for (FavoriteDto favoriteDto : favoriteDtoList) {

            GoodsStockItem favoriteItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
            // 2023-renew No11 from here
            toPageInfoResponse(favoriteItem, favoriteDto, memberFavoriteModel, goodsUtility);
            // 2023-renew No11 to here

            // リスト追加
            favoriteItems.add(favoriteItem);
        }

        memberFavoriteModel.setFavoriteItems(favoriteItems);
    }

    /**
     * お気に入り商品検索条件Dtoの作成
     *
     * @param memberFavoriteModel 会員お気に入り Model
     * @return お気に入り検索条件Dto
     */
    public FavoriteSearchForDaoConditionDto toFavoriteConditionDtoForSearchFavoriteList(MemberFavoriteModel memberFavoriteModel) {

        // お気に入り検索条件Dtoの作成 公開状態＝指定なし
        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        ApplicationContextUtility.getBean(FavoriteSearchForDaoConditionDto.class);
        favoriteConditionDto.setMemberInfoSeq(
                        memberFavoriteModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        favoriteConditionDto.setSiteType(memberFavoriteModel.getCommonInfo().getCommonInfoBase().getSiteType());
        return favoriteConditionDto;
    }

    /**
     * お気に入り情報リスト取得リクエストに変換
     *
     * @param favoriteConditionDto お気に入りDao用検索条件Dto
     * @return お気に入り情報リスト取得リクエスト
     */
    public FavoriteListGetRequest toFavoriteListGetRequest(FavoriteSearchForDaoConditionDto favoriteConditionDto) {
        if (favoriteConditionDto == null) {
            return null;
        }

        FavoriteListGetRequest request = new FavoriteListGetRequest();
        request.setMemberInfoSeq(favoriteConditionDto.getMemberInfoSeq());
        request.setExclusionGoodsSeqList(favoriteConditionDto.getExclusionGoodsSeqList());
        if (favoriteConditionDto.getSiteType() != null) {
            request.setSiteType(favoriteConditionDto.getSiteType().getValue());
        }
        if (favoriteConditionDto.getGoodsOpenStatus() != null) {
            request.setGoodsOpenStatus(favoriteConditionDto.getGoodsOpenStatus().getValue());
        }
        if (favoriteConditionDto.getMemberRank() != null) {
            request.setMemberRank(favoriteConditionDto.getMemberRank().getValue());
        }

        return request;
    }

    /**
     * ページ情報リクエストに変換
     *
     * @param resDto お気に入りDao用検索条件Dto
     * @return ページ情報リクエスト
     */
    public PageInfoRequest toPageInfoRequest(FavoriteSearchForDaoConditionDto resDto) {
        if (resDto == null || resDto.getPageInfo() == null) {
            return null;
        }

        PageInfoRequest request = new PageInfoRequest();
        request.setPage(resDto.getPageInfo().getPnum());
        request.setLimit(resDto.getPageInfo().getLimit());
        request.setOrderBy(resDto.getPageInfo().getOrderField());
        request.setSort(resDto.getPageInfo().isOrderAsc());
        return request;
    }

    // 2023-renew No71 from here

    /**
     * 作成リクエスト更新アナウンス
     * @param isTopSale トップセールを更新
     * @param isTopStock 上位株を更新
     * @return 作成リクエスト更新アナウンス
     * */
    public MemberInfoAnnounceUpdateRequest createRequestUpdateAnnounce(Boolean isTopSale, Boolean isTopStock) {
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        MemberInfoAnnounceUpdateRequest request = new MemberInfoAnnounceUpdateRequest();
        request.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());
        if (HTypeTopSaleAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopSaleAnnounceFlg()) && isTopSale) {
            request.setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg.READ.getValue());
        }
        if (HTypeTopStockAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopStockAnnounceFlg()) && isTopStock) {
            request.setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg.READ.getValue());
        }
        return request;
    }

    /**
     * 機能アップデートのお知らせ状況
     * */
    public void updateAnnounceStatus(Boolean isTopSale, Boolean isTopStock) {
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        if (HTypeTopSaleAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopSaleAnnounceFlg()) && isTopSale) {
            commonInfo.getCommonInfoBase().setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg.READ);
        }
        if (HTypeTopStockAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopStockAnnounceFlg()) && isTopStock) {
            commonInfo.getCommonInfoBase().setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg.READ);
        }
    }
    // 2023-renew No71 to here

    // 2023-renew No65 to here

    // 2023-renew No11 from here

    /**
     * WEB-API連携取得結果DTO共通情報に変換
     *
     * @param abstractWebApiResponseResultDtoResponse API連携取得結果DTO共通情報レスポンス
     * @return WEB-API連携取得結果DTO共通情報
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
    // 2023-renew No11 to here
}
