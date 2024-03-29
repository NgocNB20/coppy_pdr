// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 商品グループコード取得新規ロジック<br/>
 *
 * @author s.kume
 */
public interface GoodsGroupCodeGetByGoodsGroupSeqLogic {

    /**
     * 商品グループSEQから商品グループコードを取得する<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 商品グループコード
     */
    String execute(Integer goodsGroupSeq);
}
// PDR Migrate Customization to here
