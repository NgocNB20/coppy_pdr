/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

/**
 * 商品の規格表示順チェック
 *
 * @author nose
 */
public interface GoodsOrderDisplayCheckLogic {
    // LGG0005

    /**
     * 商品規格表示順重複エラー<br/>
     * <code>MSGCD_ORDERDISPLAY_DUPLICATE_FAIL</code>
     */
    public static final String MSGCD_ORDERDISPLAY_DUPLICATE_FAIL = "LGG000513";

    /**
     * 商品の規格表示順チェック
     *
     * @param goodsGroupSeq  商品グループSEQ
     * @param goodsGroupCode 商品グループコード
     */
    void execute(Integer goodsGroupSeq, String goodsGroupCode);
}
