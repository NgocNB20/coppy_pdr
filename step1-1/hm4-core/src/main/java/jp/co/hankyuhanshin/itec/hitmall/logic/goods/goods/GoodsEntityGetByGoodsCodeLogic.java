/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;

/**
 * 商品エンティティ取得（商品コード）<br/>
 * 商品コード、ショップSEQをキーに商品エンティティを取得する。<br/>
 *
 * @author MN7017
 * @version $Revision: 1.1 $
 */
public interface GoodsEntityGetByGoodsCodeLogic {

    /**
     * 取得処理実行<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param goodsCode 商品コード
     * @return 商品エンティティ
     */
    GoodsEntity execute(Integer shopSeq, String goodsCode);

}
