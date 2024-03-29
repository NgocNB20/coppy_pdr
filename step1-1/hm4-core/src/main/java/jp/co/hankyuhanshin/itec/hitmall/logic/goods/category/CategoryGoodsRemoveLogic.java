/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

/**
 * カテゴリ登録商品情報削除
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface CategoryGoodsRemoveLogic {

    /**
     * カテゴリ登録商品情報を削除<br/>
     *
     * @param categorySeq カテゴリSEQ
     * @return int 件数
     */
    int execute(Integer categorySeq);
}
