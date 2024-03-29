/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;

import java.util.List;

/**
 * 親・子ノードカテゴリ情報取得<br/>
 *
 * @author wt32118
 */
public interface AllNodeCategorySearchLogic {

    /**
     * 指定したカテゴリIDから親・子カテゴリの情報リストを取得する。
     *
     * @param categoryId カテゴリId
     * @return カテゴリ詳細情報Dtoリスト
     */
    List<CategoryDetailsDto> execute(String categoryId);

}
