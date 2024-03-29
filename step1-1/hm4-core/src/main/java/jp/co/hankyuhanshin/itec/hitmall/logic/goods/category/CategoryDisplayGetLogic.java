/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;

/**
 * カテゴリ表示情報取得ロジック
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CategoryDisplayGetLogic {

    /**
     * カテゴリ表示情報を取得する。<br/>
     *
     * @param categorySeq カテゴリSEQ
     * @return カテゴリ表示エンティティ
     */
    CategoryDisplayEntity execute(Integer categorySeq);
}
