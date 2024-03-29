/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;

import java.util.List;

/**
 * カート商品数量変更<br/>
 * カート商品の数量を更新する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface CartGoodsCountUpdateLogic {

    // LCC0008

    /**
     * カート商品数量変更<br/>
     * カート商品の数量を更新する。<br/>
     *
     * @param cartGoodsDtoList カート商品情報DTOリスト
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別情報
     * @return 更新件数
     */
    int execute(List<CartGoodsDto> cartGoodsDtoList, Integer memberInfoSeq, String accessUid);
}
