/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.footprint.FootprintDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.footprint.FootprintSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupMapGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * あしあと商品リスト取得<br/>
 * あしあと商品のリストを取得する。<br/>
 *
 * @author ozaki
 * @author matsumoto(itec) 2012/02/06 #2761 対応
 */
@Component
public class GoodsFootPrintListGetLogicImpl extends AbstractShopLogic implements GoodsFootPrintListGetLogic {

    /**
     * ログクラス
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsFootPrintListGetLogicImpl.class);

    /**
     * 商品グループマップ取得ロジッククラス
     */
    private final GoodsGroupMapGetLogic goodsGroupMapGetLogic;

    /**
     * あしあと情報DAO
     */
    private final FootprintDao footprintDao;

    @Autowired
    public GoodsFootPrintListGetLogicImpl(GoodsGroupMapGetLogic goodsGroupMapGetLogic, FootprintDao footprintDao) {
        this.goodsGroupMapGetLogic = goodsGroupMapGetLogic;
        this.footprintDao = footprintDao;
    }

    /**
     * あしあと商品リスト取得<br/>
     * あしあと商品のリストを取得する。<br/>
     *
     * @param footprintSearchForDaoConditionDto あしあと商品Dao用検索条件Dto
     * @return 商品グループ一覧DTO
     */
    @Override
    public List<GoodsGroupDto> execute(FootprintSearchForDaoConditionDto footprintSearchForDaoConditionDto) {

        // (1) パラメータチェック
        // あしあと商品Dao用検索条件Dtoが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("footprintSearchForDaoConditionDto", footprintSearchForDaoConditionDto);

        // (2) あしあと商品リスト取得
        // あしあと商品Daoのあしあと商品リスト検索処理を実行する。
        List<FootprintEntity> footprintEntityList = footprintDao.getSearchFootprintList(
                        footprintSearchForDaoConditionDto,
                        footprintSearchForDaoConditionDto.getPageInfo().getSelectOptions()
                                                                                       );

        // (2) で取得したあしあと商品エンティティリストからあしあと商品エンティティ．商品SEQのリストを作成する。
        List<GoodsGroupDto> goodsGroupDtoList = new ArrayList<>();
        if (CollectionUtil.isEmpty(footprintEntityList)) {
            return goodsGroupDtoList;
        }
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        for (FootprintEntity footprintEntity : footprintEntityList) {
            goodsGroupSeqList.add(footprintEntity.getGoodsGroupSeq());
        }

        // (3) 商品グループマップ取得
        // (2) で取得したあしあと商品エンティティリストからあしあと商品エンティティ．商品グループSEQのリストを作成する。
        // 商品グループマップ取得Logicを利用して、商品グループマップを取得する
        Map<Integer, GoodsGroupDto> goodsGroupMap = goodsGroupMapGetLogic.execute(goodsGroupSeqList);

        // (4) （戻り値用）商品グループ一覧DTOを作成・編集する。
        // （戻り値用）商品グループ一覧DTOを初期化する。
        // 商品グループDTOリストを初期化する。
        // tmp未取得商品グループ数 ＝ ０ をセットする。
        // ・(2)で取得したあしあと商品エンティティリストの件数分、以下の処理を行う
        // ①あしあと商品エンティティ．商品グループSEQをキー項目として、(3)で取得した商品グループマップから商品グループDTOを取得する。
        // ・マップから商品グループDTOが取得できた場合、商品グループDTOリストに追加する
        // ・マップから商品グループDTOを取得できなかった場合、ログ出力して、tmp未取得商品グループ数をカウントアップする。
        // 商品グループDTOリストを（戻り値用）商品グループ一覧DTOにセットする。
        // （（パラメータ）あしあと商品Dao用検索条件DTO．COUNT － tmp未取得商品グループ数）
        // を（戻り値用）商品グループ一覧DTO．全件数にセットする。
        for (Integer goodsGroupSeq : goodsGroupSeqList) {
            GoodsGroupDto goodsGroupDto = goodsGroupMap.get(goodsGroupSeq);
            if (goodsGroupDto == null) {
                LOGGER.warn("商品グループSEQが『" + goodsGroupSeq + "』の商品グループが取得できませんでした。");
            } else {
                goodsGroupDtoList.add(goodsGroupDto);
            }
        }

        // (5) 戻り値
        // 戻り値用商品一覧Dtoを返す。
        return goodsGroupDtoList;
    }

}
