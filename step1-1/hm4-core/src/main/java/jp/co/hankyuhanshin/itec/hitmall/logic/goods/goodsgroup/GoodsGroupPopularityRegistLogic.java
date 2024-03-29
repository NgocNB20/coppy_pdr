/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupPopularityEntity;

/**
 * 商品グループ人気登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupPopularityRegistLogic {
    // LGP0015

    /**
     * 商品グループ人気登録<br/>
     *
     * @param goodsGroupPopularityEntity 商品グループ人気エンティティ
     * @return 登録した件数
     */
    int execute(GoodsGroupPopularityEntity goodsGroupPopularityEntity);
}
