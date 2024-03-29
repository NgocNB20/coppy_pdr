// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 商品情報更新ロジック
 *
 * @author s.kume
 */
public interface GoodsPriceInfoUpdateLogic {

    /**
     * 商品情報更新<br/>
     *
     * @param goodsEntity 商品エンティティ
     * @return 更新結果
     */
    int execute(GoodsEntity goodsEntity);
}
// PDR Migrate Customization to here
