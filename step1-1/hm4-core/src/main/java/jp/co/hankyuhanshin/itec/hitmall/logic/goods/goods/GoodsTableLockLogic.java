/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

/**
 * 商品テーブルロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsTableLockLogic {
    // LGG0004

    /**
     * 商品テーブルロック<br/>
     * 商品テーブルを排他取得する。<br/>
     */
    void execute();
}
