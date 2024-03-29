/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForDaoConditionDto;

import java.util.List;

/**
 * 商品リスト取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface GoodsListGetLogic {

    /**
     * 商品リスト取得<br/>
     * 商品情報リストを取得する。<br/>
     *
     * @param goodsSearchForDaoConditionDto 商品Dao用検索条件DTO
     * @return 商品DTOリスト
     */
    List<GoodsDto> execute(GoodsSearchForDaoConditionDto goodsSearchForDaoConditionDto);

    // 2023-renew No92 from here
    /**
     * 商品リスト取得<br/>
     * 商品情報リストを取得する。<br/>
     *
     * @param goodsCodes    商品コードリスト
     * @return 商品DTOリスト
     */
    List<GoodsDto> execute(List<String> goodsCodes);
    // 2023-renew No92 to here
}
