// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
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
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsIconItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 会員入荷お知ら入り Helperクラス<br/>
 *
 * @author hung.leviet
 * @version $Revision: 1.0 $
 */
@Component
public class MemberRestockAnnounceHelper {

    /**
     * 税込価格算出Utility
     */
    private CalculatePriceUtility calculatePriceUtility;

    /**
     * コンストラクタ
     *
     * @param calculatePriceUtility
     */
    @Autowired
    public MemberRestockAnnounceHelper(CalculatePriceUtility calculatePriceUtility) {
        this.calculatePriceUtility = calculatePriceUtility;
    }

    /**
     * ページ情報レスポンスに変換
     *
     * @param restockAnnounceItem
     * @param restockAnnounceDto           入荷お知ら入りDTO
     * @param memberFavoriteModel   ページ
     * @Param goodsUtility          商品系Helper取得
     * @return ページ情報レスポンス
     */
    public void toPageInfoResponse(GoodsStockItem restockAnnounceItem,
                                   RestockAnnounceDto restockAnnounceDto,
                                   MemberRestockAnnounceModel memberFavoriteModel,
                                   GoodsUtility goodsUtility) {
        // 各商品情報
        GoodsDetailsDto goodsDetailsDto = restockAnnounceDto.getGoodsDetailsDto();
        if (Objects.nonNull(goodsDetailsDto)) {
            if (goodsDetailsDto != null) {

                restockAnnounceItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                restockAnnounceItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
                restockAnnounceItem.setGcd(goodsDetailsDto.getGoodsCode());
                restockAnnounceItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                restockAnnounceItem.setGoodsDetailsNote(goodsDetailsDto.getGoodsNote1());
                restockAnnounceItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                restockAnnounceItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
                restockAnnounceItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                restockAnnounceItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                restockAnnounceItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(goodsDetailsDto.getPreDiscountPrice(),
                                                                          goodsDetailsDto.getTaxRate()
                                                                         ));
                restockAnnounceItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                restockAnnounceItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
                restockAnnounceItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
                restockAnnounceItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                restockAnnounceItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
                restockAnnounceItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
                restockAnnounceItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                restockAnnounceItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
                restockAnnounceItem.setDeliveryType(goodsDetailsDto.getDeliveryType());
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
                restockAnnounceItem.setWhatsnewDate(whatsnewDate);
                restockAnnounceItem.setStockStatusPc(restockAnnounceDto.getStockStatus());
                restockAnnounceItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());

                // 税率、税種別の変換
                restockAnnounceItem.setTaxRate(goodsDetailsDto.getTaxRate());
                restockAnnounceItem.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());

                // 税込価格の計算
                restockAnnounceItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(restockAnnounceItem.getPreDiscountPrice(),
                                                                          restockAnnounceItem.getTaxRate()
                                                                         ));

                // 商品画像リストを取り出す。
                // 商品画像リストを取り出す。
                List<String> goodsImageList = new ArrayList<>();
                String restockAnnounceItemImageFileName = null;
                if (goodsDetailsDto.getUnitImage() != null) {
                    restockAnnounceItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                    goodsImageList.add(goodsUtility.getGoodsImagePath(restockAnnounceItemImageFileName));
                } else {
                    if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                            goodsImageList.add(
                                            goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                        }
                    }
                }

                restockAnnounceItem.setGoodsImageItems(goodsImageList);

                // SALEアイコン表示判定
                restockAnnounceItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()));
                // お取りおきアイコン表示判定
                restockAnnounceItem.setReserveIconFlag(
                                HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()));
                // NEWアイコン表示判定
                restockAnnounceItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()));
                // アウトレットアイコンフラグ
                restockAnnounceItem.setOutletIconFlag(
                                HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()));

                // 画面表示用価格
                NumberFormat formatter = NumberFormat.getNumberInstance();
                restockAnnounceItem.setDispPrice(goodsDetailsDto.getGoodsPrice() == null ?
                                                                 null :
                                                                 formatter.format(goodsDetailsDto.getGoodsPrice())
                                                                 + "円");
                if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag())) {
                    restockAnnounceItem.setDispPrice(restockAnnounceItem.getDispPrice() + "～");
                }

                // 画面表示用セール価格
                restockAnnounceItem.setDispSalePrice(goodsDetailsDto.getPreDiscountPrice() == null ?
                                                                     null :
                                                                     formatter.format(
                                                                                     goodsDetailsDto.getPreDiscountPrice())
                                                                     + "円");
                if (HTypeGroupPriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag())) {
                    restockAnnounceItem.setDispSalePrice(restockAnnounceItem.getDispSalePrice() + "～");
                }

                // セール価格整合性フラグ
                restockAnnounceItem.setSalePriceIntegrityFlag(
                                HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()));

                if (StringUtils.isBlank(restockAnnounceItem.getGcnt())) {
                    restockAnnounceItem.setGcnt("1");
                }

            }

            // アイコン情報の取得
            List<GoodsIconItem> goodsIconList = new ArrayList<>();
            if (restockAnnounceDto.getGoodsInformationIconDetailsDtoList() != null) {
                for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : restockAnnounceDto.getGoodsInformationIconDetailsDtoList()) {

                    GoodsIconItem goodsIconItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                    goodsIconItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                    goodsIconItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                    goodsIconList.add(goodsIconItem);
                }
            }
            restockAnnounceItem.setGoodsIconItems(goodsIconList);
        }
    }

    /**
     * ページ変換、初期表示<br/>
     *
     * @param restockAnnounceDtoList     入荷お知ら入りDTOリスト
     * @param memberRestockAnnounceModel 入荷お知らModel
     */
    public void toPageForLoad(List<RestockAnnounceDto> restockAnnounceDtoList,
                              MemberRestockAnnounceModel memberRestockAnnounceModel) {

        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

        List<GoodsStockItem> restockAnnounceItems = new ArrayList<>();
        for (RestockAnnounceDto restockAnnounceDto : restockAnnounceDtoList) {

            GoodsStockItem restockAnnounceItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
            toPageInfoResponse(restockAnnounceItem, restockAnnounceDto, memberRestockAnnounceModel, goodsUtility);

            // リスト追加
            restockAnnounceItems.add(restockAnnounceItem);
        }

        memberRestockAnnounceModel.setRestockAnnounceItems(restockAnnounceItems);
    }

    /**
     * 入荷お知ら入り商品検索条件Dtoの作成
     *
     * @param memberRestockAnnounceModel 会員入荷お知ら入り Model
     * @return 入荷お知ら入り検索条件Dto
     */
    public RestockAnnounceSearchForDaoConditionDto toRestockAnnounceConditionDtoForSearchRestockAnnounceList(
                    MemberRestockAnnounceModel memberRestockAnnounceModel) {

        // お気に入り検索条件Dtoの作成 公開状態＝指定なし
        RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto =
                        ApplicationContextUtility.getBean(RestockAnnounceSearchForDaoConditionDto.class);
        restockAnnounceConditionDto.setMemberInfoSeq(
                        memberRestockAnnounceModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        restockAnnounceConditionDto.setSiteType(
                        memberRestockAnnounceModel.getCommonInfo().getCommonInfoBase().getSiteType());
        return restockAnnounceConditionDto;
    }

    /**
     * 入荷お知ら入り情報リスト取得リクエストに変換
     *
     * @param restockAnnounceConditionDto 入荷お知ら入りDao用検索条件Dto
     * @return 入荷お知ら入り情報リスト取得リクエスト
     */
    public RestockAnnounceListGetRequest toRestockAnnounceListGetRequest(RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto) {
        if (restockAnnounceConditionDto == null) {
            return null;
        }

        RestockAnnounceListGetRequest request = new RestockAnnounceListGetRequest();
        request.setMemberInfoSeq(restockAnnounceConditionDto.getMemberInfoSeq());
        request.setExclusionGoodsSeqList(restockAnnounceConditionDto.getExclusionGoodsSeqList());
        if (restockAnnounceConditionDto.getSiteType() != null) {
            request.setSiteType(restockAnnounceConditionDto.getSiteType().getValue());
        }
        if (restockAnnounceConditionDto.getGoodsOpenStatus() != null) {
            request.setGoodsOpenStatus(restockAnnounceConditionDto.getGoodsOpenStatus().getValue());
        }
        request.setRestockstatus(restockAnnounceConditionDto.getRestockstatus());

        return request;
    }

    /**
     * ページ情報リクエストに変換
     *
     * @param resDto 入荷お知ら入りDao用検索条件Dto
     * @return ページ情報リクエスト
     */
    public PageInfoRequest toPageInfoRequest(RestockAnnounceSearchForDaoConditionDto resDto) {
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
     * @param isTopStock 入荷通知を更新
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
     * セッションへ既読を設定
     * */
    public void updateCommonInfoAnnounceStatus(Boolean isTopSale, Boolean isTopStock) {

        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        if (HTypeTopSaleAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopSaleAnnounceFlg()) && isTopSale) {
            commonInfo.getCommonInfoBase().setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg.READ);
        }
        if (HTypeTopStockAnnounceFlg.ON.equals(commonInfo.getCommonInfoBase().getTopStockAnnounceFlg()) && isTopStock) {
            commonInfo.getCommonInfoBase().setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg.READ);
        }
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
}
// 2023-renew No65 to here
