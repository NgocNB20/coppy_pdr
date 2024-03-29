/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsSearchForDaoConditionDto;

import java.util.List;

/**
 * カテゴリに紐づく商品ｸﾞﾙｰﾌﾟﾘｽﾄを取得
 *
 * @author kimura
 * @version $Revision: 1.4 $
 */
public interface CategoryGetGoodsListService {

    /**
     * カテゴリに紐づく商品ｸﾞﾙｰﾌﾟﾘｽﾄを取得
     *
     * @param conditionDto カテゴリ松陰情報Dao用検索条件Dto
     * @return カテゴリ内商品詳細DTOﾘｽﾄ
     */
    List<CategoryGoodsDetailsDto> execute(CategoryGoodsSearchForDaoConditionDto conditionDto);

}
