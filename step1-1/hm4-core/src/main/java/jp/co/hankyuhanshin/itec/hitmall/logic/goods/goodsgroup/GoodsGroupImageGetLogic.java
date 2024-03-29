/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品グループ画像取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupImageGetLogic {
    // LGP0010

    /**
     * 商品グループ画像取得<br/>
     * 商品グループSEQリストを元に商品グループ画像マップを取得する。<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return 商品グループ画像マップ
     */
    Map<Integer, List<GoodsGroupImageEntity>> execute(List<Integer> goodsGroupSeqList);
}
