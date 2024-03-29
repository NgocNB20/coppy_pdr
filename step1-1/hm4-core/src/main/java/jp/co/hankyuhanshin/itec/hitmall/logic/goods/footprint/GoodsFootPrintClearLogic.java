/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint;

/**
 * あしあとクリア<br/>
 * あしあと商品リストを削除する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface GoodsFootPrintClearLogic {

    // LGT0003

    /**
     * あしあとクリア<br/>
     * あしあと商品リストを削除する。<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別ID
     * @return 削除件数
     */
    int execute(Integer shopSeq, String accessUid);
}
