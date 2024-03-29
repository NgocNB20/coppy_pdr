/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;

import java.util.List;

/**
 * 関連商品詳細情報リスト取得<br/>
 * 対象商品の関連商品リスト（商品Dtoリスト）を取得する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface GoodsRelationListGetLogic {

    // LGR0001

    /**
     * 関連商品詳細情報リスト取得<br/>
     *
     * @param goodsRelationSearchForDaoConditionDto 関連商品Dao用検索条件Dto
     * @return 関連商品情報リスト
     */
    List<GoodsGroupDto> execute(GoodsRelationSearchForDaoConditionDto goodsRelationSearchForDaoConditionDto);
}
