/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.util.List;

/**
 * カテゴリ並び順整備対象カテゴリIDリストを取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryGetSortOrderdisplayCategoryIdLogic {

    /**
     * カテゴリ並び順整備対象カテゴリIDリストを取得
     *
     * @param shopSeq               ショップSEQ
     * @param parentCategorySeqPath 親のカテゴリSEQパス
     * @param categoryLevel         レベル
     * @param orderdisplay          表示順
     * @return カテゴリIDリスト
     */
    List<String> execute(Integer shopSeq, String parentCategorySeqPath, Integer categoryLevel, Integer orderdisplay);
}
