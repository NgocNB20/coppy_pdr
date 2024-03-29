/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.footprint.FootprintSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;

/**
 * あしあと商品詳細リスト取得<br/>
 * あしあと商品のリストを取得する。<br/>
 *
 * @author ozaki
 */
public interface GoodsFootPrintListGetLogic {

    /**
     * あしあと商品詳細リスト取得<br/>
     * あしあと商品のリストを取得する。<br/>
     *
     * @param footprintSearchForDaoConditionDto あしあと商品Dao用検索条件Dto
     * @return 商品グループDTO一覧
     */
    List<GoodsGroupDto> execute(FootprintSearchForDaoConditionDto footprintSearchForDaoConditionDto);

}
