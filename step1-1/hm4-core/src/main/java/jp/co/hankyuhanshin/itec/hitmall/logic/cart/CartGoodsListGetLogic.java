/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;

/**
 * カート商品リスト取得<br/>
 * カート商品情報に登録されている商品リストを取得します。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CartGoodsListGetLogic {

    // LCC0001

    /**
     * カート商品リスト取得<br/>
     * カート商品情報に登録されている商品リストを取得します。<br/>
     *
     * @param cartGoodsConditionDto カート商品DAO用検索条件DTO
     * @return カートDTO
     */
    CartDto execute(CartGoodsForDaoConditionDto cartGoodsConditionDto);
}
