// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import java.util.List;

/**
 * 過去半年間で購入された商品リスト取得<br/>
 *
 * @author Doan Thang
 */
public interface GoodsGroupTogetherBuyGetLogic {

    /**
     * 過去半年間で購入された商品リスト取得<br/>
     *
     * @return 商品グループSEQリスト
     */
    List<Integer> execute();
}
// 2023-renew No21 to here