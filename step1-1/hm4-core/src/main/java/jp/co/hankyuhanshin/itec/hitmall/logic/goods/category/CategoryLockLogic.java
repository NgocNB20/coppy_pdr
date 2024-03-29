/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import java.util.List;

/**
 * カテゴリレコードロック取得ロジック
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface CategoryLockLogic {
    // LGC0009

    /**
     * カテゴリ排他取得エラー<br/>
     * <code>MSGCD_CATEGORY_SELECT_FOR_UPDATE_FAIL</code>
     */
    public static final String MSGCD_CATEGORY_SELECT_FOR_UPDATE_FAIL = "LGC000901";

    /**
     * カテゴリレコードロック<br/>
     * カテゴリテーブルのレコードを排他取得する。<br/>
     *
     * @param categorySeqList カテゴリSEQリスト
     */
    void execute(List<Integer> categorySeqList);

}
