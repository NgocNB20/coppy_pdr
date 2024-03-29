/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

/**
 * 商品グループテーブルロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupTableLockLogic {
    // LGP0017

    /**
     * 商品グループテーブルロック<br/>
     * 商品グループテーブルを排他取得する。<br/>
     */
    void execute();

    /**
     * NOWAITを指定せずにテーブル排他ロック
     */
    void executeWait();
}
