/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;

import java.util.List;

/**
 * 商品規格画像リスト取得<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
public interface GoodsImageGetLogic {

    /**
     * 商品規格画像リスト取得<br/>
     *
     * @param goodsGroupSeq  商品グループSeq
     */
    List<GoodsImageEntity> execute(Integer goodsGroupSeq);

    /**
     * 商品規格画像リスト取得<br/>
     *
     * @param goodsGroupSeq  商品グループSeqリスト
     */
    List<GoodsImageEntity> execute(List<Integer> goodsGroupSeq);
}
