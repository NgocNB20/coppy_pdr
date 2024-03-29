/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

/**
 * カート商品マージ<br/>
 * ゲスト時にカート投入した商品を、会員カートへ移行させる処理。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
public interface CartGoodsMergeLogic {

    // LCC0010
    /**
     * 同一商品カートマージフラグ<br/>
     * <code>CART_GOODS_MERGE</code><br/>
     */
    public static final String CART_GOODS_MERGE = "cartgoods.merge";

    /**
     * カート商品マージ<br/>
     * ゲスト時にカート投入した商品を、会員カートへ移行させる処理。<br/>
     *
     * @param shopSeq             ショップSEQ(null可)
     * @param memberInfoSeq       会員SEQ(0許可)
     * @param accessUid           端末識別番号
     * @param changeMemberInfoSeq 変更する会員SEQ(0許可)
     * @return 移行したカート商品件数
     */
    int execute(Integer shopSeq, Integer memberInfoSeq, String accessUid, Integer changeMemberInfoSeq);
}
