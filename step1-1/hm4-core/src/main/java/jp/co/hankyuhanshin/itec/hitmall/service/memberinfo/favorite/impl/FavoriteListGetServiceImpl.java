/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsInformationIconMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.FavoriteListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * お気に入り情報リスト取得サービス<br/>
 *
 * @author ueshima
 */
@Service
public class FavoriteListGetServiceImpl extends AbstractShopService implements FavoriteListGetService {

    /**
     * お気に入り情報リスト取得ロジック
     **/
    private final FavoriteListGetLogic favoriteListGetLogic;

    /**
     * 商品詳細情報マップ取得ロジック
     */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    /**
     * 商品ｲﾝﾌｫﾒｰｼｮﾝアイコンマップ取得ロジック
     */
    private final GoodsInformationIconMapGetLogic goodsInformationIconMapGetLogic;

    @Autowired
    public FavoriteListGetServiceImpl(FavoriteListGetLogic favoriteListGetLogic,
                                      GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                                      GoodsInformationIconMapGetLogic goodsInformationIconMapGetLogic) {
        this.favoriteListGetLogic = favoriteListGetLogic;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        this.goodsInformationIconMapGetLogic = goodsInformationIconMapGetLogic;
    }

    /**
     * お気に入り情報リスト取得処理<br/>
     * <p>
     * ログイン会員のお気に入り情報を取得する。<br/>
     *
     * @param favoriteConditionDto お気に入り検索条件Dto
     * @param siteType             サイト区分
     * @return お気に入りDTOリスト
     */
    @Override
    public List<FavoriteDto> execute(FavoriteSearchForDaoConditionDto favoriteConditionDto, HTypeSiteType siteType) {

        // パラメタチェック
        ArgumentCheckUtil.assertNotNull("favoriteConditionDto", favoriteConditionDto);
        ArgumentCheckUtil.assertNotNull("siteType", siteType);

        // ロジックパラメタの作成
        favoriteConditionDto.setShopSeq(1001);
        favoriteConditionDto.setSiteType(favoriteConditionDto.getSiteType());
        favoriteConditionDto.setMemberRank(favoriteConditionDto.getMemberRank());

        // お気に入り情報の検索
        List<FavoriteEntity> favoriteEntityList = favoriteListGetLogic.execute(favoriteConditionDto);
        if (favoriteEntityList == null || favoriteEntityList.isEmpty()) {
            // データ不在の場合は空リストを戻す
            return new ArrayList<>(0);
        }

        // 商品SEQリストの作成
        List<Integer> goodsSeqList = new ArrayList<>(favoriteEntityList.size());
        for (FavoriteEntity favoriteEntity : favoriteEntityList) {
            goodsSeqList.add(favoriteEntity.getGoodsSeq());
        }

        // 商品詳細マップ取得
        Map<Integer, GoodsDetailsDto> goodsDtoMap = goodsDetailsMapGetLogic.execute(goodsSeqList);

        // 商品グループSEQリストの作成
        List<Integer> goodsGroupSeqList = new ArrayList<>(favoriteEntityList.size());
        for (GoodsDetailsDto goodsDetailsDto : goodsDtoMap.values()) {
            Integer goodsGroupSeq = goodsDetailsDto.getGoodsGroupSeq();
            if (!goodsGroupSeqList.contains(goodsGroupSeq)) {
                goodsGroupSeqList.add(goodsDetailsDto.getGoodsGroupSeq());
            }
        }

        // 商品アイコンマップ取得
        Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap =
                        goodsInformationIconMapGetLogic.execute(goodsGroupSeqList, siteType);

        // お気に入りDtoリスト作成
        return getFavoriteDtoList(favoriteEntityList, goodsDtoMap, goodsIconMap);
    }

    /**
     * お気に入りDto作成<br/>
     *
     * @param favoriteEntityList       お気にリエンティティ
     * @param goodsDtoMap              商品詳細DtoMap
     * @param goodsIconMap             商品インフォメーションアイコンMap
     * @param goodsGroupMemberRankInfo 商品グループ会員ランク情報
     * @return お気に入りDtoリスト
     */
    protected List<FavoriteDto> getFavoriteDtoList(List<FavoriteEntity> favoriteEntityList,
                                                   Map<Integer, GoodsDetailsDto> goodsDtoMap,
                                                   Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap) {
        // お気に入りDTOリストの作成
        List<FavoriteDto> favoriteDtoList = new ArrayList<>(favoriteEntityList.size());
        for (FavoriteEntity favoriteEntity : favoriteEntityList) {
            FavoriteDto favoriteDto = getComponent(FavoriteDto.class);
            favoriteDto.setFavoriteEntity(favoriteEntity);
            GoodsDetailsDto goodsDetailsDto = goodsDtoMap.get(favoriteEntity.getGoodsSeq());
            favoriteDto.setGoodsDetailsDto(goodsDetailsDto);
            favoriteDto.setGoodsGroupImageEntityList(goodsDetailsDto.getGoodsGroupImageEntityList());
            favoriteDto.setGoodsInformationIconDetailsDtoList(goodsIconMap.get(goodsDetailsDto.getGoodsGroupSeq()));
            favoriteDtoList.add(favoriteDto);
        }
        return favoriteDtoList;
    }
}
