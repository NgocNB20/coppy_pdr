/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;

import java.util.List;

/**
 * トップ画面初期表示用Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface TopInitialaizeInfoClickRankingListGetLogic {
    /**
     * 商品ランキング情報を取得します
     *
     * @param shopSeq Integer
     * @return List&lt;GoodsRankingEntity&gt;
     */
    List<GoodsRankingEntity> execute(Integer shopSeq);
}
