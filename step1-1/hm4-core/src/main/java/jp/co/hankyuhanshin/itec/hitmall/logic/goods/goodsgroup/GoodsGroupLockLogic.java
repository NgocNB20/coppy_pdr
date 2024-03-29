/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import java.util.List;

/**
 * 商品グループレコードロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.3 $
 */
public interface GoodsGroupLockLogic {
    // LGP0012

    /**
     * 商品グループ排他取得エラー<br/>
     * <code>MSGCD_GOODSGROUP_SELECT_FOR_UPDATE_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUP_SELECT_FOR_UPDATE_FAIL = "LGP001201";

    /**
     * 商品グループレコードロック<br/>
     * 商品グループテーブルのレコードを排他取得する。<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @param versionNo         更新カウンタ
     */
    void execute(List<Integer> goodsGroupSeqList, Integer versionNo);
}
