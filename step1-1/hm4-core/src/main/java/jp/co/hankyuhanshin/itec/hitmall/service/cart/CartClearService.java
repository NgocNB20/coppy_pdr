/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;

import java.util.List;

/**
 * カートクリアサービス
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CartClearService {
    // PDR Migrate Customization from here
    /**
     * 同一商品カートマージフラグ<br/>
     * <code>CART_GOODS_MERGE</code><br/>
     */
    public static final String CART_GOODS_MERGE = "cartgoods.merge";
    // PDR Migrate Customization to here

    /**
     * カート全商品削除（今すぐお届けのみ）
     *
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     */
    void execute(String accessUid, Integer memberInfoSeq);

    // PDR Migrate Customization from here

    /**
     * 一括注文用のカートクリア
     * <pre>
     * カートマージありの場合は、一括登録する商品が既にカートに投入済みの場合は、カート投入前にカートから削除しておく。
     * 例）
     * 一括登録： 商品A × 2, 商品B × 2
     * カート： 商品A × 1, 商品C × 1
     *   ↓ 投入後は以下のようにする
     * 商品A × 2, 商品B × 2, 商品C × 1
     *
     * カートマージなしの場合は処理しない
     * </pre>
     *
     * @param registCartGoodsList カート一括登録用の商品情報
     */
    void execute(List<CartGoodsForTakeOverDto> registCartGoodsList, Integer memberInfoSeq, String accessUid);

    // PDR Migrate Customization to here
}
