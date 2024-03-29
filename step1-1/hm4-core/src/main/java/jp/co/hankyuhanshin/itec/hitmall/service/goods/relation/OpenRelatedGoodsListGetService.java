/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.relation;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;

import java.util.List;

/**
 * 公開関連商品情報取得<br/>
 * 公開されている関連商品の一覧情報を取得します。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
public interface OpenRelatedGoodsListGetService {

    // SGT0001

    /**
     * 公開関連商品情報取得<br/>
     * 公開されている関連商品の一覧情報を取得します。<br/>
     *
     * @param conditionDto 関連商品検索条件DTO
     * @return 関連商品リスト情報DTO
     */
    List<GoodsGroupDto> execute(GoodsRelationSearchForDaoConditionDto conditionDto, Integer limit);
}
