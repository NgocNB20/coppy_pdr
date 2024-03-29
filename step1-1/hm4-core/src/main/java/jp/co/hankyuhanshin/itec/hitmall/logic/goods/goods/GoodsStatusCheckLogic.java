/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

/**
 * 商品の公開&販売状態チェック
 *
 * @author kimura
 */
public interface GoodsStatusCheckLogic {

    /**
     * 商品の公開&販売状態チェック
     *
     * @param shopSeq   ショップSEQ
     * @param goodsCode 商品コード
     * @return true 公開&販売ともに削除ではない, false 公開か販売状態が削除されている
     */
    boolean execute(Integer shopSeq, String goodsCode);
}
