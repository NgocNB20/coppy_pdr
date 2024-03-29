/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;

import java.util.List;

/**
 * 商品検索結果リスト取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface GoodsSearchResultListGetLogic {

    // LGG0006

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 商品Dao用検索条件（管理機能）Dto
     * @return 商品検索結果DTOリスト
     */
    List<GoodsSearchResultDto> execute(GoodsSearchForBackDaoConditionDto searchCondition);
}
