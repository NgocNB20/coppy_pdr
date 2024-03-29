// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsInformationIconMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.RestockAnnounceListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 入荷お知らせ情報リスト取得サービス<br/>
 *
 * @author Thang Doan (VJP)
 */
@Service
public class RestockAnnounceListGetServiceImpl extends AbstractShopService implements RestockAnnounceListGetService {

    /**
     * 入荷お知らせ情報リスト取得ロジック
     **/
    private final RestockAnnounceListGetLogic restockAnnounceListGetLogic;

    /**
     * 商品詳細情報マップ取得ロジック
     */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    /**
     * 商品ｲﾝﾌｫﾒｰｼｮﾝアイコンマップ取得ロジック
     */
    private final GoodsInformationIconMapGetLogic goodsInformationIconMapGetLogic;

    /**
     * コンストラクタ
     *
     * @param restockAnnounceListGetLogic 入荷お知らせ情報リスト取得ロジック
     * @param goodsDetailsMapGetLogic 商品詳細情報マップ取得ロジック
     * @param goodsInformationIconMapGetLogic 商品ｲﾝﾌｫﾒｰｼｮﾝアイコンマップ取得ロジック
     */
    @Autowired
    public RestockAnnounceListGetServiceImpl(RestockAnnounceListGetLogic restockAnnounceListGetLogic,
                                             GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                                             GoodsInformationIconMapGetLogic goodsInformationIconMapGetLogic) {
        this.restockAnnounceListGetLogic = restockAnnounceListGetLogic;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        this.goodsInformationIconMapGetLogic = goodsInformationIconMapGetLogic;
    }

    /**
     * 入荷お知らせ情報リスト取得処理<br/>
     *
     * @param restockAnnounceConditionDto 入荷お知らせ検索条件Dto
     * @return 入荷お知らせDTOリスト
     */
    @Override
    public List<RestockAnnounceDto> execute(RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto) {

        // パラメタチェック
        ArgumentCheckUtil.assertNotNull("restockAnnounceConditionDto", restockAnnounceConditionDto);

        // 入荷お知らせ情報の検索
        List<RestockAnnounceEntity> restockAnnounceEntityList =
                        restockAnnounceListGetLogic.execute(restockAnnounceConditionDto);
        if (restockAnnounceEntityList == null || restockAnnounceEntityList.isEmpty()) {
            // データ不在の場合は空リストを戻す
            return new ArrayList<>(0);
        }

        // 商品SEQリストの作成
        List<Integer> goodsSeqList = new ArrayList<>(restockAnnounceEntityList.size());
        for (RestockAnnounceEntity restockAnnounceEntity : restockAnnounceEntityList) {
            goodsSeqList.add(restockAnnounceEntity.getGoodsSeq());
        }

        // 商品詳細マップ取得
        Map<Integer, GoodsDetailsDto> goodsDtoMap = goodsDetailsMapGetLogic.execute(goodsSeqList);

        // 商品グループSEQリストの作成
        List<Integer> goodsGroupSeqList = new ArrayList<>(restockAnnounceEntityList.size());
        for (GoodsDetailsDto goodsDetailsDto : goodsDtoMap.values()) {
            Integer goodsGroupSeq = goodsDetailsDto.getGoodsGroupSeq();
            if (!goodsGroupSeqList.contains(goodsGroupSeq)) {
                goodsGroupSeqList.add(goodsDetailsDto.getGoodsGroupSeq());
            }
        }

        // 商品アイコンマップ取得
        Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap =
                        goodsInformationIconMapGetLogic.execute(goodsGroupSeqList, HTypeSiteType.FRONT_PC);

        // 入荷お知らせDtoリスト作成
        return getRestockAnnounceDtoList(restockAnnounceEntityList, goodsDtoMap, goodsIconMap);
    }

    /**
     * 入荷お知らせDto作成<br/>
     *
     * @param restockAnnounceEntityList       お気にリエンティティ
     * @param goodsDtoMap              商品詳細DtoMap
     * @param goodsIconMap             商品インフォメーションアイコンMap
     * @return 入荷お知らせDtoリスト
     */
    protected List<RestockAnnounceDto> getRestockAnnounceDtoList(List<RestockAnnounceEntity> restockAnnounceEntityList,
                                                                 Map<Integer, GoodsDetailsDto> goodsDtoMap,
                                                                 Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap) {
        // 入荷お知らせDTOリストの作成
        List<RestockAnnounceDto> restockAnnounceDtoList = new ArrayList<>(restockAnnounceEntityList.size());
        for (RestockAnnounceEntity restockAnnounceEntity : restockAnnounceEntityList) {
            RestockAnnounceDto restockAnnounceDto = getComponent(RestockAnnounceDto.class);
            restockAnnounceDto.setRestockAnnounceEntity(restockAnnounceEntity);
            GoodsDetailsDto goodsDetailsDto = goodsDtoMap.get(restockAnnounceEntity.getGoodsSeq());
            restockAnnounceDto.setGoodsDetailsDto(goodsDetailsDto);
            restockAnnounceDto.setGoodsGroupImageEntityList(goodsDetailsDto.getGoodsGroupImageEntityList());
            restockAnnounceDto.setGoodsInformationIconDetailsDtoList(
                            goodsIconMap.get(goodsDetailsDto.getGoodsGroupSeq()));
            restockAnnounceDtoList.add(restockAnnounceDto);
        }
        return restockAnnounceDtoList;
    }
}
// 2023-renew No65 to here
