/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

/**
 * カテゴリ指定レベルの最大の並び順を取得
 *
 * @author kimura
 * @version $Revision: 1.2 $
 */
public interface CategoryGetMaxOrderdisplayLogic {

    /**
     * カテゴリ指定レベルの最大の並び順を取得<br/>
     *
     * @param shopSeq         ショップSEQ
     * @param categorySeqPath 親カテゴリSEQパス
     * @param categoryLevel   カレントのレベル
     * @return int 指定レベルの最大の並び順
     */
    int execute(Integer shopSeq, String categorySeqPath, Integer categoryLevel);
}
