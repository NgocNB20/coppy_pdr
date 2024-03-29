/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint;

/**
 * あしあと商品クリアクラス<br/>
 * あしあと商品情報を削除します。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsFootClearService {

    // SGT0002

    /**
     * あしあと商品クリア<br/>
     * あしあと商品情報を削除します。<br/>
     */
    void execute(String accessUid);
}
